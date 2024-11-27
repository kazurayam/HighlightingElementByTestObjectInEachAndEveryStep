import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * TC0
 * 
 * This script visits the page at https://katalon-demo-cura.herokuapp.com/ and the linked pages
 * without highlighting elements
 */

// open browser and navigate to the AUT
WebUI.openBrowser('')
WebUI.setViewPortSize(1024, 1024)
WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')
WebUI.delay(1)

TestObject h1_CURA = findTestObject('Page_CURA Healthcare Service_top/h1_CURA Healthcare Service')
WebUI.click(h1_CURA)

TestObject a_MakeAppointment = findTestObject('Page_CURA Healthcare Service_top/a_Make Appointment')
WebUI.verifyElementPresent(a_MakeAppointment, 10)
WebUI.click(a_MakeAppointment)
WebUI.delay(1)

TestObject input_username = findTestObject('Page_CURA Healthcare Service_login/input_Username_username')
WebUI.setText(input_username, 'John Doe')
WebUI.delay(1)

TestObject input_password = findTestObject('Page_CURA Healthcare Service_login/input_Password_password')
WebUI.setEncryptedText(input_password, 'g3/DOGG74jC3Flrr3yH+3D/yKbOqqUNM')
WebUI.delay(1)

TestObject button_Login = findTestObject('Page_CURA Healthcare Service_login/button_Login')
WebUI.click(button_Login)
WebUI.delay(1)

TestObject select_Facility = findTestObject('Page_CURA Healthcare Service_appointment/select_Facility')
WebUI.selectOptionByIndex(select_Facility, 0)
WebUI.delay(1)

TestObject input_hospital_readm = findTestObject('Page_CURA Healthcare Service_appointment/input_Apply for hospital readm')
WebUI.click(input_hospital_readm)
WebUI.delay(1)

TestObject input_Medicaid = findTestObject('Page_CURA Healthcare Service_appointment/input_Medicaid_programs') 
WebUI.click(input_Medicaid)
WebUI.delay(1)

TestObject input_Visit_Date = findTestObject('Page_CURA Healthcare Service_appointment/input_Visit Date (Required)_vi')
WebUI.setText(input_Visit_Date, '01/12/34')
WebUI.delay(1)

TestObject textarea_comment = findTestObject('Page_CURA Healthcare Service_appointment/textarea_Comment_comment')
WebUI.setText(textarea_comment, 'This is a comment')
WebUI.delay(1)

TestObject button_Book_Appointment = findTestObject('Page_CURA Healthcare Service_appointment/button_Book Appointment') 
WebUI.click(button_Book_Appointment)
WebUI.delay(1)

TestObject a_Go_to_Homepage = findTestObject('Page_CURA Healthcare Service_summary/a_Go to Homepage')
WebUI.click(a_Go_to_Homepage)
WebUI.delay(1)

WebUI.closeBrowser()
