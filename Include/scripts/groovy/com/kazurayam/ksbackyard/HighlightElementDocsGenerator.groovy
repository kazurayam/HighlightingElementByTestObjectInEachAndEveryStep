package com.kazurayam.ksbackyard

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import java.nio.file.Path
import java.nio.file.Paths

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kms.katalon.core.configuration.RunConfiguration

@RunWith(JUnit4.class)
public class HighlightElementDocsGenerator {

	@Test
	void compile_highlitable_keywords_doc() {
		StringBuilder sb = new StringBuilder()
		sb.append("## Highlight-table WebUI Keywords\n\n")
		sb.append("| No. | Keyword (click to doc) | highlighted by default? |\n")
		sb.append("|-----|---------|-------------|\n")

		List<String> list =
				new ArrayList<String>(HighlightElement.getHighlightableBuiltinKeywords()).toSorted()

		String urlPrefix = "https://docs.katalon.com/katalon-studio/docs"
		list.eachWithIndex { keyword, index ->
			sb.append("| ${index + 1} ")
			String vaccinated = (HighlightElement.defaultHighlighted.contains(keyword)) ? 'Yes' : ''
			sb.append("| [WebUI.${keyword}](${urlPrefix}/webui-${this.camel2chain(keyword)}.html) ")
			sb.append("| ${vaccinated} ")
			sb.append("|")
			sb.append("\n")
		}
		Path projectDir = Paths.get(RunConfiguration.getProjectDir())
		Path doc = projectDir.resolve("docs/highlightable_keywords.md")
		doc.toFile().text = sb.toString()
	}

	@Test
	void test_camel2chain() {
		String given = "verifyElementPresent"
		String expected = "verify-element-present"
		String actual = this.camel2chain(given)
		assertThat(actual, is(expected))
	}

	/**
	 * turn a string in Camel case to a chain of lower case strings concatinated by hyphens
	 *
	 * e.g, 'verifyElementPresent' -> 'verify-element-present'
	 *
	 * @param name
	 * @return
	 */
	private String camel2chain(String given) {
		StringBuilder sb = new StringBuilder()
		char[] chars = replaceSymbols(given).toCharArray()
		chars.each { c ->
			if (Character.isUpperCase(c)) {
				sb.append('-')
				sb.append(Character.toLowerCase(c))
			} else {
				sb.append(c)
			}
		}
		return sb.toString()
	}

	private String replaceSymbols(String given) {
		String result
		result = given.replaceAll('CSS', 'Css')
		result = result.replaceAll('XML', 'Xml')
		// more replacement
		return result
	}
}

