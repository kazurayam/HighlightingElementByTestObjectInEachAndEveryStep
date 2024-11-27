#!/bin/bash

# Under the current working directory, convert all the files
# with name ending with `_.adoc` into `.md`.
# A file with name `*_.adoc` is an Asciidoc document file.
# A file with name `*.md` is a Markdown document file.
# E.g, `index_.adoc` will be converted into `index.md`
#
# However, ffiles with name which starts with with `_` will be ignored.
# E.g, `_index_.adoc` will be ignored.
#
# How to run this: in the command line, just type
#
#     `> ./adoc2md.sh`
#
# By specifying `-t` option in the command line,
#
#     `> ./adoc2md.sh -t`
#
# you can prepend a text segment into the output .md file:
#
# ```
# - Table of Contents
# {:toc}
#
# ```
# This text segment will affect the [Jekyll](https://docs.github.com/en/pages/setting-up-a-github-pages-site-with-jekyll/about-github-pages-and-jekyll)
# used by GitHub Pages to generate a table of contents.
#

requireTOC=false

optstring="t"
while getopts ${optstring} arg; do
  case ${arg} in
    t)
        requireTOC=true
        ;;
    ?)
        ;;
  esac
done

function processFile() {
  fname=$1
  #echo "fname=${fname}"
  #  using Asciidoctor, convert a *.adoc file into a docbook in XML
  md=${fname//adoc/md}
  xml=${fname//adoc/xml}
  echo "converting $fname into $md"
  asciidoctor -b docbook -a leveloffset=+1 -o - "$fname" > "$xml"
  # using Pandoc, generate a Markdown file without TOC
  cat "$xml" | pandoc --markdown-headings=atx --wrap=preserve -t markdown_strict -f docbook - > "$md"
  #echo deleting $xml
  rm -f "$xml"

  # We named `index_.adoc` rather than `index.adoc` because GitHub puts precedence to `index.adoc` over `index.md`. We want `index.md` to be presented first, not `*.adoc`. Therefore we named our adoc file with `*_.adoc` postfix.
  # This trick required further treatment.
  # `index_.adoc` will result `index_.adoc`. But we
  # want the final result to be `index.md`.
  # So, we will rename `*_.md` into `*.md`.
  # in other words, chomp an underline character (_) before `.md``
  # e.g,
  #   ./index_.adoc    -> ./index.md
  #   ./index-ja_.md -> ./index-ja.md
  newmd=${md%_.md}.md
  echo renaming $md to $newmd
  mv $md $newmd

  # Slightly modify the generated *.md file.
  # Prepend a text segment:
  # ```
  # - Table of contents
  # {:toc}
  # ```
  if [ $requireTOC = true ]; then
    echo "- Table of contents" > temp.md
    echo "{:toc}" >> temp.md
    echo "" >> temp.md
    cat $newmd >> temp.md
    cat temp.md > $newmd
    rm temp.md
    echo prepended the TOC segement in $newmd
  fi
  # just a blank line to separate the *.adoc files processed
  echo ""
}



# iterate over all *.adoc files
find . -iname "*_.adoc" -type f -maxdepth 1 -not -name "_*.adoc" | while read fname; do
  processFile $fname
done
