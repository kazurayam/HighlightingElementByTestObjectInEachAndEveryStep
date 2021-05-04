Highlighting Element by TestObject in each and every step
=============

- author: kazurayam
- originally published: Jan 2019
- last update: May 2021

## What is this?

This is a [Katalon Studio](https://www.katalon.com/) project for demonstration purpose.
You can download the ZIP from [Releases](https://github.com/kazurayam/HighlightingElementByTestObjectInEachAndEveryStep/releases) page,
unzip it and open with your Katalon Studio.

This project was originally developed using Katalon Studio version 5.10.1, and was tested using 7.9.1 as well.

This project proposes a solution to the issue discussed in the Katalon Forum:
[How to highlight test object in each and every step](https://forum.katalon.com/t/how-to-highlight-test-object-in-each-and-every-step/17408)
The originator asked:

>I have created a keyword to highlight testobject. Please tell me how to call this keyword globally in such a way that it should highlight testobject of each step during test case execution


## Problem to solve

The originator of the post proposed a custom keyword implementation which give highlight to a specific HTML element on a page provided with a TestObject. 

And He added his wish:

>it should highlight testobject of each step during test case execution

I thought that he does not like a code like this:

```
WebUI.openBrowser('')
WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')

// highlight a specific element
CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(
	findTestObject('Object Repository/Page_CURA Healthcare Service/h1_CURA Healthcare Service'))

CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(
	findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment'))
WebUI.click(findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment'))

CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(
	findTestObject('Object Repository/Page_CURA Healthcare Service/input_Username_username'))
WebUI.setText(findTestObject('Object Repository/Page_CURA Healthcare Service/input_Username_username'),
	'John Doe')

CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(
	findTestObject('Object Repository/Page_CURA Healthcare Service/input_Password_password'))
WebUI.setEncryptedText(findTestObject('Object Repository/Page_CURA Healthcare Service/input_Password_password'),
	'g3/DOGG74jC3Flrr3yH+3D/yKbOqqUNM')
...
```

As you can see, this code repeats highlighting HTML elements by `HightlightElement.on(TestObject to)`. This code is too verbose.

I would rather want all of the HTML elements targeted by `WebUI.click()`, `WebUI.setText()` and `WebUI.setEncryptedText()` to be automatically highlighted without explicit `HighlightElement.on(TestObject to)` calls.

## Solution

I have developed a custom class `com.kazurayam.ksbackyard.HighlightElement`. This class implements 2 methods, which are available as `@Keyword`:

1. `on(TestObject to)`
2. `pandemic()` and `pandemic(List<String> additionalKeywords)`

The `on(TestObject to)` method puts highlight on the indivisually specified HTML element.

The `pandemic()` method dynamically decorates `WebUI.click(TestObject to)` and other WebUI keywords so that they put highlight on the target HTML elements before doing their own built-in processing (such as "clicking the element").

## Description

### Demo movie

A demo movie is avaiable, which shows how the [`Test Cases/TC1`](Scripts/TC1/Script1547070867765.groovy) works. Click [this link to see the movie](https://kazurayam.github.io/HighlightingElementByTestObjectInEachAndEveryStep/).

### How to install the plugin into your project

As of the version 0.5.0 of this project, a small jar file is provided, which contains the `com.kazurayam.ksbackyard.HighlightElement` class compiled and ready for your reuse.

1. create your own Katalon Studio WebUI test project: e.g, `HighlightElement-demo` project
2. visit the [Releases](https://github.com/kazurayam/HighlightingElementByTestObjectInEachAndEveryStep/releases) page, download the latest `kazurayam-ks-highlightlement-x.x.x.jar` file.
3. save the jar file into the `<projectDir>/Plugins` folder of your project
4. stop/restart KS and reopen your project

You are ready.

### How to write your tests

Make a `Test Case/TC0` in your project. You can copy&paste the following:

-  [`Test Cases/TC0`](Scripts/TC0/Script1620129794625.groovy)

In early section the script calls this:

```
// modify WebUI.* keywords which take TestObject as arg0
// so that they call Highlight.on() automatically
CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.pandemic'()
```

Calling the `pandemic()` method, the following 6 WebUI built-in keywords are marked "highlighting". The keywords are dynamically modified to be *highlighting* so that they put hightlights on the target HTML elements.

1. [`WebUI.click`](https://docs.katalon.com/katalon-studio/docs/webui-click.html)
2. [`WebUI.selectOptionByIndex`](https://docs.katalon.com/katalon-studio/docs/webui-select-option-by-index.html)
3. [`WebUI.selectOptionByLabel`](https://docs.katalon.com/katalon-studio/docs/webui-select-option-by-label.html)
4. [`WebUI.selectOptionByValue`](https://docs.katalon.com/katalon-studio/docs/webui-select-option-by-value.html)
5. [`WebUI.setEncryptedText`](https://docs.katalon.com/katalon-studio/docs/webui-set-encrypted-text.html)
6. [`WebUI.setText`](https://docs.katalon.com/katalon-studio/docs/webui-set-text.html)

### How to mark more Keywords "highlighting"

Any WebUI built-in keywords that take an instance of `com.kms.katalon.core.testobject.TestObject` as the 1st argument can be marked as *highlighting*. The following page shows the list of possible WebUI Builtin keywords:

- [Highlight-table WebUI builtin Keywords](docs/highlightable_keywords.md)

It is possible to add more keywords as "highlighting". The following script shows how to.

- [Test Cases/TC2](Scripts/TC2/Script1620129688091.groovy)

This script has a line:

```
CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.pandemic'(['verifyElementPresent'])
```

Here, the `pandemic()` method accepts a `List<String>` which includes the names of WebUI Builtin Keyword listed
in the [doc](docs/highlightable_keywords.md).

### How the custom Keyword is implemented

Please read the source to find detail:

- [`Keywords/com.kazurayam.ksbackyard/HighlightElement.groovy`](Keywords/com/kazurayam/ksbackyard/HighlightElement.groovy)

It employs the [Groovy Metaprogramming](http://docs.groovy-lang.org/latest/html/documentation/core-metaprogramming.html#metaprogramming_emc) technique extensively.

