import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// open browser and navigate to the AUT
WebUI.openBrowser('')
WebUI.setViewPortSize(1024, 768)
WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')

// Modify some of WebUI builtin keywords so that they call Highlight.on() automatically
// before executing their original jobs.
// You can pass a list of keyword names as an argument to enlightKeywords(List<String>) call.
// The keywords are added to the list of highlighting-capable Keywords.

CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.enlightWebUiBuiltInKeywords'([
		'verifyElementChecked',
		'verifyElementPresent',
		'waitForElementPresent'
	])

WebUI.comment("DEMO4 started")

WebUI.click(findTestObject('Page_CURA Healthcare Service_top/a_Make Appointment'))
WebUI.delay(1)

WebUI.setText(findTestObject('Page_CURA Healthcare Service_login/input_Username_username'), 'John Doe')
WebUI.delay(1)

WebUI.setEncryptedText(findTestObject('Page_CURA Healthcare Service_login/input_Password_password'), 'g3/DOGG74jC3Flrr3yH+3D/yKbOqqUNM')
WebUI.delay(1)

WebUI.click(findTestObject('Page_CURA Healthcare Service_login/button_Login'))
WebUI.delay(1)

boolean result

result = WebUI.waitForElementPresent(
	findTestObject('Page_CURA Healthcare Service_appointment/select_Facility'),
	3, FailureHandling.OPTIONAL)
WebUI.delay(1)

result = WebUI.verifyElementPresent(
	findTestObject('Page_CURA Healthcare Service_appointment/input_Apply for hospital readm'),
	3, FailureHandling.OPTIONAL)
WebUI.delay(1)

result = WebUI.verifyElementChecked(
	findTestObject('Page_CURA Healthcare Service_appointment/input_Medicare_programs'),
	3, FailureHandling.OPTIONAL)
WebUI.delay(1)

result = WebUI.verifyElementChecked(
	findTestObject('Page_CURA Healthcare Service_appointment/input_Medicaid_programs'),
	3, FailureHandling.OPTIONAL)

WebUI.comment("Medicaid is checked: ${result}".toString())
/* If you have a line as follows:
 *     WebUI.comment("Medicaid is checked: ${result}")
 * This will cause an Exception raised.
 * <PRE>Test Cases/main/DEMO4_calling_enlightWebUiBuiltInKeywords_with_arg FAILED.
 * Reason:
 * java.lang.IllegalArgumentException: argument type mismatch
 * 	at com.kazurayam.ksbackyard.HighlightElement$_enlightWebUiBuiltInKeywords_closure1.doCall(HighlightElement.groovy:100)
 * 	...
 * </PRE>
 * Why?
 * An instance of grooy.lang.GString is not always converted to String when calling a Java method. See the following URL:
 * https://issues.apache.org/jira/browse/GROOVY-309
 * 
 * A possible workaround is to call "${GString}".toString() explicitly.
 */
WebUI.delay(1)


result = WebUI.verifyElementChecked(
	findTestObject('Page_CURA Healthcare Service_appointment/input_None_programs'),
	3, FailureHandling.OPTIONAL)
WebUI.comment("None is checked: ${result}".toString())
WebUI.delay(1)


WebUI.closeBrowser()
