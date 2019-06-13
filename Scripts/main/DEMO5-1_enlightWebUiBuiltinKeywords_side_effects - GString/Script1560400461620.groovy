import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')
WebUI.setViewPortSize(1024, 768)
WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')

CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.enlightWebUiBuiltInKeywords'()

WebUI.comment("DEMO5-1 started")

boolean result = WebUI.verifyElementPresent(findTestObject('Page_CURA Healthcare Service_top/a_Make Appointment'),
	10, FailureHandling.STOP_ON_FAILURE)
println "result is ${result}"

def text = WebUI.getText(findTestObject('Page_CURA Healthcare Service_top/a_Make Appointment'),
	FailureHandling.STOP_ON_FAILURE)

WebUI.comment("text is ${text}")   // raises IllegalArgumentException
//WebUI.comment("text is ${text}".toString())     // would work

WebUI.closeBrowser()
