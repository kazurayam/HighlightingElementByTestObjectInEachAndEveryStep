import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// TC3b

CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.pandemic'()

WebUI.openBrowser('')
WebUI.setViewPortSize(1024, 1024)
WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')
WebUI.delay(1)

TestObject a_MakeAppointment = findTestObject('Page_CURA Healthcare Service_top/a_Make Appointment')
WebUI.click(a_MakeAppointment, 10)

WebUI.closeBrowser()