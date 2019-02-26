import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.json.JsonOutput
import internal.GlobalVariable as GlobalVariable

String prettyPrint(Object obj) {
	String json = JsonOutput.toJson(obj)
	return JsonOutput.prettyPrint(json)
}

// modify WebUI.* keywords which take TestObject as args[0]
// so that they get highlighted automatically
CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.pandemic'()

// open browser and navigate to the AUT
WebUI.openBrowser('')
WebUI.setViewPortSize(1024, 768)
WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')
WebUI.verifyElementPresent(findTestObject(
	'Page_CURA Healthcare Service_top/h1_CURA Healthcare Service'),
	10,
	FailureHandling.STOP_ON_FAILURE)

// following verification will fail. This is intentional.
// just to see what happens when a built-in keyword failed.
def verificationResult = WebUI.verifyElementAttributeValue(
				findTestObject('Page_CURA Healthcare Service_top/a_Make Appointment'),
				'href',
				'https://www.google.com/',   // this is a wrong URL
				5,
				FailureHandling.CONTINUE_ON_FAILURE)
WebUI.delay(3)

WebUI.closeBrowser()