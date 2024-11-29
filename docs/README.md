- Table of contents
{:toc}

# Highlighting Element by TestObject in each and every step

-   author: kazurayam

-   originally published: Jan 2019

-   last update: Nov 2024

## What is this?

This is a [Katalon Studio](https://www.katalon.com/) project for demonstration purpose.
You can download the ZIP from [Releases](https://github.com/kazurayam/HighlightingElementByTestObjectInEachAndEveryStep/releases) page,
unzip it and open with your Katalon Studio.

This project was initially developed with Katalon Studio version 5.10.1. Also I tested it using version 10.0.

This project requires the Custom Keyword feature to be avaiable to you. So you can not run this using Katalon Studio v9.x Free which does not provide the Custom Keyword feature.

This project proposes a solution to the issue discussed in the Katalon Forum:
["How to highlight test object in each and every step"](https://forum.katalon.com/t/how-to-highlight-test-object-in-each-and-every-step/17408). I would refer to this as "the forum topic" for short.

Let me start with a simple Test Case script ["TC0"](https://github.com/kazurayam/HighlightingElementByTestObjectInEachAndEveryStep/blob/master/Scripts/TC0/Script1620129794625.groovy). The TC0 is an ordinary Katalon Test Case script. An experienced Katalon user would find nothing interesting here.

In [the forum topic](https://github.com/kazurayam/HighlightingElementByTestObjectInEachAndEveryStep/blob/master/Scripts/TC0/Script1620129794625.groovy), the original poster presented a Custom Keyword implementation that can give highlight to the HTML element selected. I learned his code and created my implementation at [com.kazurayam.ksbackyard.HighlightElement.on()](https://github.com/kazurayam/HighlightingElementByTestObjectInEachAndEveryStep/blob/master/Keywords/com/kazurayam/ksbackyard/HighlightElement.groovy)

    public class HighlightElement {

        @Keyword
        public static void on(TestObject testObject) {
            influence(testObject)
        }

        private static String DEFAULT_STYLE = 'outline: dotted red'
        private static String highlightingStyle = DEFAULT_STYLE

        private static void influence(TestObject testObject) {
            try {
                WebDriver driver = DriverFactory.getWebDriver()
                List<WebElement> elements = WebUiCommonHelper.findWebElements(testObject, 20);
                for (WebElement element : elements) {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript(
                            "arguments[0].setAttribute('style', '${highlightingStyle}');",
                            element);
                }
            } catch (Exception e) {

Now I can show you how I could rewrite the "TC0" so that it gives highlight to the HTML elements which the script selected. Please see the Test Case ["TC1"](https://github.com/kazurayam/HighlightingElementByTestObjectInEachAndEveryStep/blob/develop/Scripts/TC1/Script1547070867765.groovy)

    import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

    import com.kms.katalon.core.testobject.TestObject
    import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

    /**
     * TC1
     *
     * This script visits the page at https://katalon-demo-cura.herokuapp.com/
     * and the linked pages while highlighting elements with red border.
     * This script repeats explicitly calling a custome keyword to put the highlight
     * so that this script looks tedius.
     */

    // open browser and navigate to the AUT
    WebUI.openBrowser('')
    WebUI.setViewPortSize(1024, 1024)
    WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')
    WebUI.delay(1)

    TestObject h1_CURA = findTestObject('Page_CURA Healthcare Service_top/h1_CURA Healthcare Service')
    CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(h1_CURA)
    WebUI.click(h1_CURA)

    TestObject a_MakeAppointment = findTestObject('Page_CURA Healthcare Service_top/a_Make Appointment')
    WebUI.verifyElementPresent(a_MakeAppointment, 10)

    // highlight the element
    CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(a_MakeAppointment)
    WebUI.click(a_MakeAppointment)
    WebUI.delay(1)

    TestObject input_username = findTestObject('Page_CURA Healthcare Service_login/input_Username_username')
    CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(input_username)
    WebUI.setText(input_username, 'John Doe')
    WebUI.delay(1)

    TestObject input_password = findTestObject('Page_CURA Healthcare Service_login/input_Password_password')
    CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(input_password)
    WebUI.setEncryptedText(input_password, 'g3/DOGG74jC3Flrr3yH+3D/yKbOqqUNM')
    WebUI.delay(1)

    TestObject button_Login = findTestObject('Page_CURA Healthcare Service_login/button_Login')
    CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(button_Login)
    WebUI.click(button_Login)
    WebUI.delay(1)

    TestObject select_Facility = findTestObject('Page_CURA Healthcare Service_appointment/select_Facility')
    CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(select_Facility)
    WebUI.selectOptionByIndex(select_Facility, 0)
    WebUI.delay(1)

    TestObject input_hospital_readm = findTestObject('Page_CURA Healthcare Service_appointment/input_Apply for hospital readm')
    CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(input_hospital_readm)
    WebUI.click(input_hospital_readm)
    WebUI.delay(1)

    TestObject input_Medicaid = findTestObject('Page_CURA Healthcare Service_appointment/input_Medicaid_programs')
    CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(input_Medicaid)
    WebUI.click(input_Medicaid)
    WebUI.delay(1)

    TestObject input_Visit_Date = findTestObject('Page_CURA Healthcare Service_appointment/input_Visit Date (Required)_vi')
    CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(input_Visit_Date)
    WebUI.setText(input_Visit_Date, '01/12/34')
    WebUI.delay(1)

    TestObject textarea_comment = findTestObject('Page_CURA Healthcare Service_appointment/textarea_Comment_comment')
    CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(textarea_comment)
    WebUI.setText(textarea_comment, 'This is a comment')
    WebUI.delay(1)

    TestObject button_Book_Appointment = findTestObject('Page_CURA Healthcare Service_appointment/button_Book Appointment')
    CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(button_Book_Appointment)
    WebUI.click(button_Book_Appointment)
    WebUI.delay(1)

    TestObject a_Go_to_Homepage = findTestObject('Page_CURA Healthcare Service_summary/a_Go to Homepage')
    CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(a_Go_to_Homepage)
    WebUI.click(a_Go_to_Homepage)
    WebUI.delay(1)

    WebUI.closeBrowser()

How do you find the source code of "TC1"?

## Problem to solve

I find the source of TC1 is too long and tedius. It contains a lot of repetitions. I don’t like "TC1".

In [the forum topic](https://forum.katalon.com/t/how-to-highlight-test-object-in-each-and-every-step/17408), the originl poster asked:

> I have created a keyword to highlight testobject. Please tell me how to call this keyword globally in such a way that it should highlight testobject of each step during test case execution

I believe that the original poster would put a thumbs down 👎 to the TC1. So how can I improve it?

## Solution

I further developed my Custom Keyword so that I could write a new Test Case script ["TC2"](https://github.com/kazurayam/HighlightingElementByTestObjectInEachAndEveryStep/blob/develop/Scripts/TC2/Script1547960621812.groovy)

    import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

    import com.kms.katalon.core.testobject.TestObject
    import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

    /**
     * TC2
     * 
     * This script visits the page at https://katalon-demo-cura.herokuapp.com/ 
     * and the linked pages while highlighting elements with red border.
     * This script does the same as the TC1 but is much shorter.
     * This script calls the `pandemic` method of the
     * `com.kazurayam.ksbackyard.HighlightElement` class, 
     * which dynamically modifies the `WebUI.setText` and other built-in keywords
     * using Groovy's Metaprogramming technique.
     */

    // do the magic
    CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.pandemic'()

    // open browser and navigate to the AUT
    WebUI.openBrowser('')
    WebUI.setViewPortSize(1024, 1024)
    WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')
    WebUI.delay(1)

    TestObject h1_CURA = findTestObject('Page_CURA Healthcare Service_top/h1_CURA Healthcare Service')
    WebUI.click(h1_CURA)

    TestObject a_MakeAppointment = findTestObject('Page_CURA Healthcare Service_top/a_Make Appointment')
    WebUI.verifyElementPresent(a_MakeAppointment, 10)

    // the target element for WebUI.click keyword will be highlighted
    WebUI.click(a_MakeAppointment)
    WebUI.delay(1)

    TestObject input_username = findTestObject('Page_CURA Healthcare Service_login/input_Username_username')
    WebUI.setText(input_username, 'John Doe')
    WebUI.delay(1)

    TestObject input_password = findTestObject('Page_CURA Healthcare Service_login/input_Password_password')
    WebUI.setEncryptedText(input_password, 'g3/DOGG74jC3Flrr3yH+3D/yKbOqqUNM')
    WebUI.delay(1)

    TestObject button_Login = findTestObject('Page_CURA Healthcare Service_login/button_Login')
    WebUI.click(button_Login)
    WebUI.delay(1)

    TestObject select_Facility = findTestObject('Page_CURA Healthcare Service_appointment/select_Facility')
    WebUI.selectOptionByIndex(select_Facility, 0)
    WebUI.delay(1)

    TestObject input_hospital_readm = findTestObject('Page_CURA Healthcare Service_appointment/input_Apply for hospital readm')
    WebUI.click(input_hospital_readm)
    WebUI.delay(1)

    TestObject input_Medicaid = findTestObject('Page_CURA Healthcare Service_appointment/input_Medicaid_programs')
    WebUI.click(input_Medicaid)
    WebUI.delay(1)

    TestObject input_Visit_Date = findTestObject('Page_CURA Healthcare Service_appointment/input_Visit Date (Required)_vi')
    WebUI.setText(input_Visit_Date, '01/12/34')
    WebUI.delay(1)

    TestObject textarea_comment = findTestObject('Page_CURA Healthcare Service_appointment/textarea_Comment_comment')
    WebUI.setText(textarea_comment, 'This is a comment')
    WebUI.delay(1)

    TestObject button_Book_Appointment = findTestObject('Page_CURA Healthcare Service_appointment/button_Book Appointment')
    WebUI.click(button_Book_Appointment)
    WebUI.delay(1)

    TestObject a_Go_to_Homepage = findTestObject('Page_CURA Healthcare Service_summary/a_Go to Homepage')
    WebUI.click(a_Go_to_Homepage)
    WebUI.delay(1)

    WebUI.closeBrowser()

The TC2 behaves just the same as the TC1. But the source code of TC2 is much shorter than TC1. TC2 contains minimum repetitions. The magic spell of the TC2 is the following single line:

    CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.pandemic'()

This call to `pandemic` method of the `com.kazurayam.ksbackyard.HighlightElement` class dynamically modifies the implementation of `WebUI.setText` keyword and others so that an invokation to `WebUI.setText` gives highlight to the target HTML element.

## Demonstration movie

Have a look at the movie that demonstrates how the TC2 works:

-   [the demo movie](https://kazurayam.github.io/HighlightingElementByTestObjectInEachAndEveryStep/)

## How to run the demonstration

In Katalon Studio, just open the `Test Cases/TS2` and run it.

## Implementation

I have developed a custom keyword class [`com.kazurayam.ksbackyard.HighlightElement`](https://github.com/kazurayam/HighlightingElementByTestObjectInEachAndEveryStep/blob/develop/Keywords/com/kazurayam/ksbackyard/HighlightElement.groovy).

This class implements 2 methods:

1.  `on(TestObject to)`

2.  `pandemic()`

The `on(TestObject to)` method puts highlight on the specified HTML element.

The `pandemic()` method internally overrides `WebUI.click(TestObject to)` and other methods so that each keywords automaticall calls `on(TestObject to)` before its original method body.

Please read the Groovy source to find out how these methods are implemented.

## Built-in Keywords that are influenced

The `pandemic` method influences the following Katalon built-in keywords as default:

-   [`WebUI.click`](https://docs.katalon.com/katalon-studio/docs/webui-click.html)

-   [`WebUI.selectOptionByIndex`](https://docs.katalon.com/katalon-studio/docs/webui-select-option-by-index.html)

-   [`selectOptionByLabel`](https://docs.katalon.com/katalon-studio/docs/webui-select-option-by-label.html)

-   [`selectOptionByValue`](https://docs.katalon.com/katalon-studio/docs/webui-select-option-by-value.html)

-   [`setEncryptedText`](https://docs.katalon.com/katalon-studio/docs/webui-set-encrypted-text.html)

-   [`setText`](https://docs.katalon.com/katalon-studio/docs/webui-set-text.html)

## Customization

### Style of highlight

Here is a button as example rendered by [Test Cases/TC3a](https://github.com/kazurayam/HighlightingElementByTestObjectInEachAndEveryStep/blob/develop/Scripts/TC3a/Script1732700240908.groovy), which has no highlight:

![button no highlight](https://kazurayam.github.io/HighlightingElementByTestObjectInEachAndEveryStep/images/button_no_highlight.png)

The following image is rendered by [Test Cases/TC3b](https://github.com/kazurayam/HighlightingElementByTestObjectInEachAndEveryStep/blob/develop/Scripts/TC3b/Script1732701241909.groovy). The `com.kazurayam.ksbackyard.HighlightElement.on` keyword applies the default style of highlight like this:

![button default highlight](https://kazurayam.github.io/HighlightingElementByTestObjectInEachAndEveryStep/images/button_default_highlight.png)

The default highlight is implemented by a CSS property of:

    outline: dashed red;

Now, you may want a more strong style. Is it possible?

You can do it, like this:

![button golden outline](https://kazurayam.github.io/HighlightingElementByTestObjectInEachAndEveryStep/images/button_golden_outline.png)

See [`Test Cases/TC3c`](https://github.com/kazurayam/HighlightingElementByTestObjectInEachAndEveryStep/blob/develop/Scripts/TC3c/Script1732701204510.groovy)

The following statement in the TC3c gives a custom style:

    CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.pandemic'('outline: 8px ridge Gold;')

### More WebUI keywords to highlight

As default, 6 WebUI keywords are enabled of automatic highlighting. But you may want more WebUI keywords to be capable. Is it possbile to extend the list of WebUI keywords that is capable of highlighting the target HTML element? Yes, it is supported.

See the [Test Cases/TC4](https://github.com/kazurayam/HighlightingElementByTestObjectInEachAndEveryStep/blob/develop/Scripts/TC4/Script1732709108575.groovy)

You can specify a list of WebUI keyword names as a parameter to the `pandemic` method.

    CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.pandemic'(['verifyElementPresent', 'waitForElementPresent'])

What types of WebUI keyword we can specify here? --- Any WebUI keyword that takes an instance of `com.kms.katalon.core.testobject.TestObject` class as the 1st parameter will be accepted. So, `veryfyElementPresent` and `waitForElementPresent` will be accpeted. If you add a keyword name like `delay` into the list as the 1st parameter, but it will have not effect.

### Mixing two cutomization

See the [Test Cases/TC5](https://github.com/kazurayam/HighlightingElementByTestObjectInEachAndEveryStep/blob/develop/Scripts/TC5/Script1732709969913.groovy)

    CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.pandemic'(
        'outline: 8px ridge Gold;',
        ['verifyElementPresent', 'waitForElementPresent']
        )

You can apply both ways of customization.
