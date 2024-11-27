import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// TC4

CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.pandemic'(['verifyElementPresent', 'waitForElementPresent'])

WebUI.openBrowser('')
WebUI.setViewPortSize(1024, 1024)
WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')
WebUI.delay(1)

TestObject h1_CURA = findTestObject('Page_CURA Healthcare Service_top/h1_CURA Healthcare Service')
WebUI.verifyElementPresent(h1_CURA, 10)

WebUI.delay(3)

WebUI.closeBrowser()
