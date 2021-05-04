import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * TC2
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

// additionally mark the "verifyElementPresent" keyword
// so that "WebUI.verifyElementPresent(TestObject)" call give highlight to the HTML element
CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.pandemic'(['verifyElementPresent'])

WebUI.verifyElementPresent(newTestObject("//a[@id='menu-toggle']"), 10)

WebUI.delay(2)

WebUI.closeBrowser()