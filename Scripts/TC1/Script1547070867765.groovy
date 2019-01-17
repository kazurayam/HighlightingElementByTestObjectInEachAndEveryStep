import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

// open browser and navigate to the AUT
WebUI.openBrowser('')
WebUI.setViewPortSize(1024, 768)
WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')
WebUI.delay(1)

// highlight a specific element
CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(
	findTestObject('Page_CURA Healthcare Service_top/h1_CURA Healthcare Service'))
WebUI.delay(2)

// modify WebUI.* keywords which take TestObject as arg0 
// so that they call Highlight.on() automatically  
CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.pandemic'()


WebUI.click(findTestObject('Page_CURA Healthcare Service_top/a_Make Appointment'))
WebUI.delay(1)

WebUI.setText(findTestObject('Page_CURA Healthcare Service_login/input_Username_username'), 'John Doe')
WebUI.delay(1)

WebUI.setEncryptedText(findTestObject('Page_CURA Healthcare Service_login/input_Password_password'), 'g3/DOGG74jC3Flrr3yH+3D/yKbOqqUNM')
WebUI.delay(1)

WebUI.click(findTestObject('Page_CURA Healthcare Service_login/button_Login'))
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

WebUI.setText(findTestObject('Page_CURA Healthcare Service_appointment/input_Visit Date (Required)_vi'), '01/12/34')
WebUI.delay(1)

WebUI.setText(findTestObject('Page_CURA Healthcare Service_appointment/textarea_Comment_comment'), 'This is a comment')
WebUI.delay(1)

WebUI.click(findTestObject('Page_CURA Healthcare Service_appointment/button_Book Appointment'))
WebUI.delay(1)

WebUI.click(findTestObject('Page_CURA Healthcare Service_summary/a_Go to Homepage'))
WebUI.delay(1)

WebUI.closeBrowser()