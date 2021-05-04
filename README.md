Highlighting Element by TestObject in each and every step
=============

- author: kazurayam
- last update: May 2021

## What is this?

This is a [Katalon Studio](https://www.katalon.com/) project for demonstration purpose.
You can download the ZIP from [Releases](https://github.com/kazurayam/HighlightingElementByTestObjectInEachAndEveryStep/releases) page,
unzip it and open with your Katalon Studio.

This project was developed with Katalon Studio version 5.10.1

This project proposes a solution to the issue discussed in the Katalon Forum:
[How to highlight test object in each and every step](https://forum.katalon.com/t/how-to-highlight-test-object-in-each-and-every-step/17408)
The originator asked:
>I have created a keyword to highlight testobject. Please tell me how to call this keyword globally in such a way that it should highlight testobject of each step during test case execution

He proposed a custom keyword implementation which give highlight to a specific HTML element on a page provided with a TestObject.

## Problem to solve

The originator wrote:
>it should highlight testobject of each step during test case execution

This implies that he do not like such code:
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

As you can see, it is boring to repeat invoking Custom Keyword for each indivisual HTML elements to put highlights. I would rather want all of the HTML elements targeted by `WebUI.click()`, `WebUI.setText()` and `WebUI.setEncryptedText()` are highlighted without repetitive calls.

## Solution

I have developed a custom keyword class `com.kazurayam.ksbackyard.HighlightElement`.
This class implements 2 methods:
1. `on(TestObject to)`
2. `pandemic()`

The `on(TestObject to)` method puts highlight on the specified HTML element.

The `pandemic()` method internally overrides `WebUI.click(TestObject to)` and other methods
so that each keywords automaticall calls `on(TestObject to)` before its method body.

In the version 0.5.0 of this project, I made a small jar file which contains the class compiled and ready for your use.

## Description

### Demo movie

Or click [this link to see the movie](https://kazurayam.github.io/HighlightingElementByTestObjectInEachAndEveryStep/)

### How to install the plugin into your project

1. create your own Katalon Studio WebUI test project: e.g, `HighlightElement-demo` project
2. visit the [Releases](https://github.com/kazurayam/HighlightingElementByTestObjectInEachAndEveryStep/releases) page, download the latest `kazurayam-ks-highlightlement-x.x.x.jar` file.
3. save the jar file into the `<projectDir>/Plugins` folder of your project
4. stop/restart KS and reopen your project

You are done.

### How to write your tests while

Make a `Test Case/TC1` in your project, which should look like:

-  [`Test Cases/TC1`](Scripts/TC1/Script1547070867765.groovy)

In the starting section of your test case script, you want to call this:

```
// modify WebUI.* keywords which take TestObject as arg0 
// so that they call Highlight.on() automatically  
CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.pandemic'()
```

Calling the `pandemic()` method, the custom keyword docorates the following Katalon-built-in keywords so that the target elements are highlighted.

- [`click`](https://docs.katalon.com/katalon-studio/docs/webui-click.html)
- [`selectOptionByIndex`](https://docs.katalon.com/katalon-studio/docs/webui-select-option-by-index.html)
- [`selectOptionByLabel`](https://docs.katalon.com/katalon-studio/docs/webui-select-option-by-label.html)
- [`selectOptionByValue`](https://docs.katalon.com/katalon-studio/docs/webui-select-option-by-value.html)
- [`setEncryptedText()`](https://docs.katalon.com/katalon-studio/docs/webui-set-encrypted-text.html)
- [`setText`](https://docs.katalon.com/katalon-studio/docs/webui-set-text.html)

### How the custom Keyword is implemented

Read the source:

- [`Keywords/com.kazurayam.ksbackyard/HighlightElement.groovy`](Keywords/com/kazurayam/ksbackyard/HighlightElement.groovy)

I would not talk much about the code. It uses magical Grovy [ExpandoMetaClass](http://docs.groovy-lang.org/latest/html/documentation/core-metaprogramming.html#metaprogramming_emc).
