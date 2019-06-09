import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// open browser and navigate to the AUT
WebUI.openBrowser('')
WebUI.setViewPortSize(1024, 768)
WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')

// do highlighting a element, then perform some operation on it
CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(
	findTestObject('Page_CURA Healthcare Service_top/a_Make Appointment'))
WebUI.click(findTestObject('Page_CURA Healthcare Service_top/a_Make Appointment'))
WebUI.delay(1)

// repeat doing highlighting and operation over a target element
CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(
	findTestObject('Page_CURA Healthcare Service_login/input_Username_username'))
WebUI.setText(findTestObject('Page_CURA Healthcare Service_login/input_Username_username'),
	'John Doe')
WebUI.delay(1)

// and more
CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(
	findTestObject('Page_CURA Healthcare Service_login/input_Password_password'))
WebUI.setEncryptedText(findTestObject('Page_CURA Healthcare Service_login/input_Password_password'),
	'g3/DOGG74jC3Flrr3yH+3D/yKbOqqUNM')
WebUI.delay(1)

// ...
CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(
	findTestObject('Page_CURA Healthcare Service_login/button_Login'))
WebUI.click(findTestObject('Page_CURA Healthcare Service_login/button_Login'))
WebUI.delay(1)

CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(
	findTestObject('Page_CURA Healthcare Service_appointment/select_Facility'))
WebUI.selectOptionByIndex(findTestObject('Page_CURA Healthcare Service_appointment/select_Facility'),
	0)
WebUI.delay(1)

CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(
	findTestObject('Page_CURA Healthcare Service_appointment/input_Apply for hospital readm'))
WebUI.click(findTestObject('Page_CURA Healthcare Service_appointment/input_Apply for hospital readm'))
WebUI.delay(1)

CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(
	findTestObject('Page_CURA Healthcare Service_appointment/input_Medicaid_programs'))
WebUI.click(findTestObject('Page_CURA Healthcare Service_appointment/input_Medicaid_programs'))
WebUI.delay(1)

CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(
	findTestObject('Page_CURA Healthcare Service_appointment/input_Visit Date (Required)_vi'))
WebUI.setText(findTestObject('Page_CURA Healthcare Service_appointment/input_Visit Date (Required)_vi'),
	'01/12/34')
WebUI.delay(1)

CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(
	findTestObject('Page_CURA Healthcare Service_appointment/textarea_Comment_comment'))
WebUI.setText(findTestObject('Page_CURA Healthcare Service_appointment/textarea_Comment_comment'),
	'This is a comment')
WebUI.delay(1)

CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(
	findTestObject('Page_CURA Healthcare Service_appointment/button_Book Appointment'))
WebUI.click(findTestObject('Page_CURA Healthcare Service_appointment/button_Book Appointment'))
WebUI.delay(1)

CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(
	findTestObject('Page_CURA Healthcare Service_summary/a_Go to Homepage'))
WebUI.click(findTestObject('Page_CURA Healthcare Service_summary/a_Go to Homepage'))
WebUI.delay(1)

WebUI.closeBrowser()
