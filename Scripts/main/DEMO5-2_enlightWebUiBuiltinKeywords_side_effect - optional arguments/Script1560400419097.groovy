import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')
WebUI.setViewPortSize(1024, 768)
WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')

CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.enlightWebUiBuiltInKeywords'()

WebUI.comment("DEMO5-1 started")
WebUI.verifyElementPresent(findTestObject('Page_CURA Healthcare Service_top/a_Make Appointment'),
	10, FailureHandling.STOP_ON_FAILURE)
def text = WebUI.getText(findTestObject('Page_CURA Healthcare Service_top/a_Make Appointment'),
	FailureHandling.OPTIONAL)
println "text is ${text}"
// should cast a GString to java.lang.String 
WebUI.comment("text is ${text}".toString())

WebUI.closeBrowser()
