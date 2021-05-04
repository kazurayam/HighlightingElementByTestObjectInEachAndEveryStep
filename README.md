Highlighting Element by TestObject in each and every step
=============

- author: kazurayam
- last update: May 2021

## What is this?

This is a [Katalon Studio](https://www.katalon.com/) project for demonstration purpose.
You can download the ZIP from [Releases](https://github.com/kazurayam/HighlightingElementByTestObjectInEachAndEveryStep/releases) page,
unzip it and open with your Katalon Studio.

This project was originally developed using Katalon Studio version 5.10.1, was tested using 7.9.1 as well.

This project proposes a solution to the issue discussed in the Katalon Forum:
[How to highlight test object in each and every step](https://forum.katalon.com/t/how-to-highlight-test-object-in-each-and-every-step/17408)
The originator asked:
>I have created a keyword to highlight testobject. Please tell me how to call this keyword globally in such a way that it should highlight testobject of each step during test case execution


## Problem to solve

The originator of the post proposed a custom keyword implementation which give highlight to a specific HTML element on a page provided with a TestObject. He also wrote:

>it should highlight testobject of each step during test case execution

I thought that he does not like such code:

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

As you can see, this code repeats lines for each indivisual HTML elements to put highlights on by `HightlightElement.on(TestObject to)`. It is too verbose.

I would rather want all of the HTML elements targeted by `WebUI.click()`, `WebUI.setText()` and `WebUI.setEncryptedText()` are highlighted without repetitively calling `HighlightElement.on(TestObject to)` methods.

## Solution

I have developed a custom keyword class `com.kazurayam.ksbackyard.HighlightElement`.
This class implements 2 methods:
1. `on(TestObject to)`
2. `pandemic()`

The `on(TestObject to)` method puts highlight on the specified HTML element.

The `pandemic()` method **internally decorates** `WebUI.click(TestObject to)` and other methods
so that each keywords automaticall calls `on(TestObject to)` before its built-in method body.

## Description

### Demo movie

I made a demo movie how this custom keyword works. Click [this link to see the movie](https://kazurayam.github.io/HighlightingElementByTestObjectInEachAndEveryStep/).

### How to install the plugin into your project

As of the version 0.5.0 of this project, a small jar file is provided, which contains the `com.kazurayam.ksbackyard.HighlightElement` class compiled and ready for your reuse.

1. create your own Katalon Studio WebUI test project: e.g, `HighlightElement-demo` project
2. visit the [Releases](https://github.com/kazurayam/HighlightingElementByTestObjectInEachAndEveryStep/releases) page, download the latest `kazurayam-ks-highlightlement-x.x.x.jar` file.
3. save the jar file into the `<projectDir>/Plugins` folder of your project
4. stop/restart KS and reopen your project

You are done.

### How to write your tests while

Make a `Test Case/TC0` in your project. You can copy&paste the following:

-  [`Test Cases/TC0`](Scripts/TC0/Script1620092194024.groovy)

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
- [`setEncryptedText`](https://docs.katalon.com/katalon-studio/docs/webui-set-encrypted-text.html)
- [`setText`](https://docs.katalon.com/katalon-studio/docs/webui-set-text.html)


[check, clearText, click, clickImage, clickOffset, deselectAllOption, deselectOptionByIndex, deselectOptionByLabel, deselectOptionByValue, doubleClick, dragAndDropByOffset, dragAndDropToObject, enhancedClick, findWebElement, findWebElements, focus, getAttribute, getCSSValue, getElementHeight, getElementLeftPosition, getElementWidth, getNumberOfSelectedOption, getNumberOfTotalOption, getText, modifyObjectProperty, mouseOver, mouseOverOffset, removeObjectProperty, rightClick, rightClickOffset, scrollToElement, selectAllOption, selectOptionByIndex, selectOptionByLabel, selectOptionByValue, sendKeys, setEncryptedText, setMaskedText, setText, submit, switchToFrame, takeElementScreenshot, typeOnImage, uncheck, uploadFile, uploadFileWithDragAndDrop, verifyElementAttributeValue, verifyElementChecked, verifyElementClickable, verifyElementHasAttribute, verifyElementInViewport, verifyElementNotChecked, verifyElementNotClickable, verifyElementNotHasAttribute, verifyElementNotInViewport, verifyElementNotPresent, verifyElementNotVisible, verifyElementNotVisibleInViewport, verifyElementPresent, verifyElementText, verifyElementVisible, verifyElementVisibleInViewport, verifyImagePresent, verifyOptionNotPresentByLabel, verifyOptionNotPresentByValue, verifyOptionNotSelectedByIndex, verifyOptionNotSelectedByLabel, verifyOptionNotSelectedByValue, verifyOptionPresentByLabel, verifyOptionPresentByValue, verifyOptionSelectedByIndex, verifyOptionSelectedByLabel, verifyOptionSelectedByValue, verifyOptionsPresent, waitForElementAttributeValue, waitForElementClickable, waitForElementHasAttribute, waitForElementNotClickable, waitForElementNotHasAttribute, waitForElementNotPresent, waitForElementNotVisible, waitForElementPresent, waitForElementVisible, waitForImagePresent]

### Another Test Case example

- [Test Cases/TC1](Scripts/TC1/Script1547070867765.groovy)

Please note that this test case depends on a set of Test Objects prepared in the `Object Reporitory` folder.

### How the custom Keyword is implemented

Read the source:

- [`Keywords/com.kazurayam.ksbackyard/HighlightElement.groovy`](Keywords/com/kazurayam/ksbackyard/HighlightElement.groovy)

I would not talk much about this. It uses magical [Groovy Metaprogramming](http://docs.groovy-lang.org/latest/html/documentation/core-metaprogramming.html#metaprogramming_emc) technique.
