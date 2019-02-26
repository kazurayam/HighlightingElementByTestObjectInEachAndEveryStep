import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

import groovy.json.JsonOutput
import internal.GlobalVariable

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
WebUI.delay(1)

// now move on 
WebUI.click(findTestObject('Page_CURA Healthcare Service_top/a_Make Appointment'))
WebUI.delay(1)

WebUI.setText(findTestObject('Page_CURA Healthcare Service_login/input_Username_username'),
	'John Doe')
WebUI.delay(1)

WebUI.setEncryptedText(findTestObject('Page_CURA Healthcare Service_login/input_Password_password'),
	'g3/DOGG74jC3Flrr3yH+3D/yKbOqqUNM')
WebUI.delay(1)

WebUI.verifyElementPresent(findTestObject('Page_CURA Healthcare Service_login/button_Login'), 10,
	FailureHandling.STOP_ON_FAILURE)
WebUI.click(findTestObject('Page_CURA Healthcare Service_login/button_Login'),
	FailureHandling.STOP_ON_FAILURE)

/* A problem occurs here. You will see a message in the Log Viewer, saying
 * "Unable to find the element located by 'By.cssSelector: #btn-login'. Please recheck the objects properties to make sure the desired element is located." 
 * And TC1_passing waits for a few seconds trying to locate an element #btn-login untime timeout expires.
 * There is a reason.
 * The HighligtElement keywords gives influence to WebUI.click() keyword so that 
 * 1. WebUI.click() gives the target element a CURRENT style
 * 2. WebUI.click() do its original stuff
 * 3. WebUI.click() tries to give the target element a SUCCESS style
 * 
 * However, once WebUI.click() does its own stuff = clicking a button on a Web page, the page transition occurs.
 * And the target element (a button) disappers. So WebUI.click() always misses the target element when it wants 
 * to put a SUCCESS style, when a few seconds of wait will be seen.
 * 
 */

WebUI.delay(1)

//WebUI.selectOptionByValue(findTestObject('Page_CURA Healthcare Service_appointment/select_Facility'), 
//    'Hongkong CURA Healthcare Center', false)
//WebUI.delay(1)

//WebUI.selectOptionByLabel(findTestObject('Page_CURA Healthcare Service_appointment/select_Facility'),
//	'Seoul CURA Healthcare Center', false)
//WebUI.delay(1)

WebUI.selectOptionByIndex(findTestObject('Page_CURA Healthcare Service_appointment/select_Facility'), 0)
WebUI.delay(1)

WebUI.click(findTestObject('Page_CURA Healthcare Service_appointment/input_Apply for hospital readm'))
WebUI.delay(1)

WebUI.click(findTestObject('Page_CURA Healthcare Service_appointment/input_Medicaid_programs'))
WebUI.delay(1)

WebUI.setText(findTestObject('Page_CURA Healthcare Service_appointment/input_Visit Date (Required)_vi'),
	'01/12/34')
WebUI.delay(1)

WebUI.setText(findTestObject('Page_CURA Healthcare Service_appointment/textarea_Comment_comment'),
	'This is a comment')
WebUI.delay(1)

WebUI.click(findTestObject('Page_CURA Healthcare Service_appointment/button_Book Appointment'))
WebUI.delay(1)

// following verification will fail. This is intentional. Just to see what happens when a built-in keyword failed. 
def verificationResult = WebUI.verifyElementAttributeValue(
							findTestObject('Page_CURA Healthcare Service_summary/a_Go to Homepage'),
							'href',
							'https://katalon-demo-cura.herokuapp.com/',
							5,
							FailureHandling.CONTINUE_ON_FAILURE)
if (!verificationResult) {
	println ">>> GlobalVariable.tcExceptionEvents: \n" + prettyPrint(GlobalVariable.tcExceptionEvents)
}
WebUI.delay(3)

WebUI.click(findTestObject('Page_CURA Healthcare Service_summary/a_Go to Homepage'))
WebUI.delay(1)


WebUI.closeBrowser()