Highlighting Element by TestObject in each and every step
=============

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
He would not like to repeat invoking Custom Keyword which puts highlight on HTML element on the page.
He want all the HTML element targeted by `WebUI.click()`, `WebUI.setText()` and `WebUI.setEncryptedText()` to be highlighted automatically.

## Solution

I have developed a custom keyword class `com.kazurayam.ksbackyard.HighlightElement`. 
This class implements 2 methods:
1. `on(TestObject to)`
2. `pandemic()`

The `on(TestObject to)` method puts highlight on the specified HTML element.

The `pandemic()` method internally overrides `WebUI.click(TestObject to)`, `WebUI.setText(TestObject to)` and `WebUI.setEncryptedText(TestObject to)`
so that each keywords automaticall calls `on(TestObject to)` before its method body.

## Description

### How to run the demo project

run `Test Suites/TS1`. Then you will see the demo running.

see the following movie:

### How the code implemented

1. [`Test Cases/TC1`](Scripts/TC1/Script1547070867765.groovy)
2. [`Keywords/com.kazurayam.ksbackyard/HighlightElement.groovy`](Keywords/com/kazurayam/ksbackyard/HighlightElement.groovy)

I would not talk much about the code. It uses magical Grovy [ExpandoMetaClass](http://docs.groovy-lang.org/latest/html/documentation/core-metaprogramming.html#metaprogramming_emc).


