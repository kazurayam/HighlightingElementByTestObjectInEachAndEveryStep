import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * TC0
 */

// helper function that creates a new TestObject with XPath
TestObject newTestObject(String xpath) {
	TestObject to = new TestObject(xpath)
	to.addProperty('xpath', ConditionType.EQUALS, xpath)
	return to
}

// open browser and navigate to the AUT
WebUI.openBrowser('')
WebUI.setViewPortSize(1024, 768)
WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')
WebUI.delay(1)

// modify WebUI.* keywords which take TestObject as arg0
// so that they call Highlight.on() automatically
CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.pandemic'()

// 
TestObject makeAppo = newTestObject("//a[contains(.,'Make Appointment')]") 
// the element will be highlighted
WebUI.verifyElementPresent(makeAppo, 10)
WebUI.verifyElementVisible(makeAppo, 10)
WebUI.click(makeAppo)
WebUI.delay(2)

WebUI.verifyElementPresent(newTestObject("//input[@id = 'txt-username']"), 10)
WebUI.delay(2)

WebUI.closeBrowser()