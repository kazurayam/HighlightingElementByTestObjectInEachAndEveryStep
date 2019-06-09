import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// open browser and navigate to the AUT
WebUI.openBrowser('')
WebUI.setViewPortSize(1024, 768)
WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')

// Modify some of WebUI.* keywords so that they call Highlight.on() automatically
// before executing their original jobs.
// You can pass a list of keyword names as an argument to enlightKeywords(List<String>) call.
// The keywords are added to the list of highlighting-capable Keywords.

CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.enlightKeywords'()

WebUI.click(findTestObject('Page_CURA Healthcare Service_top/a_Make Appointment'))
WebUI.setText(findTestObject('Page_CURA Healthcare Service_login/input_Username_username'), 'John Doe')
WebUI.setEncryptedText(findTestObject('Page_CURA Healthcare Service_login/input_Password_password'), 'g3/DOGG74jC3Flrr3yH+3D/yKbOqqUNM')
WebUI.click(findTestObject('Page_CURA Healthcare Service_login/button_Login'))


boolean result = WebUI.verifyElementChecked(
	findTestObject('Page_CURA Healthcare Service_appointment/input_Medicare_programs'), 3, 
	FailureHandling.OPTIONAL)
WebUI.comment("Medicare is checked: ${result}")
WebUI.delay(3)

/*
result = WebUI.verifyElementChecked(
	findTestObject('Page_CURA Healthcare Service_appointment/input_Medicaid_programs'), 3,
	FailureHandling.OPTIONAL)
WebUI.comment("Medicaid is checked: ${result}")
WebUI.delay(3)

result = WebUI.verifyElementChecked(
	findTestObject('Page_CURA Healthcare Service_appointment/input_None_programs'), 3,
	FailureHandling.OPTIONAL)
WebUI.comment("None is checked: ${result}")
WebUI.delay(3)
*/

WebUI.closeBrowser()
