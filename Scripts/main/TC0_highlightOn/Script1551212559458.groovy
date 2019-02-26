import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// open browser and navigate to the AUT
WebUI.openBrowser('')
WebUI.setViewPortSize(1024, 768)
WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')
WebUI.delay(1)

// highlight a specific element
CustomKeywords.'com.kazurayam.ksbackyard.HighlightElement.on'(
	findTestObject('Page_CURA Healthcare Service_top/h1_CURA Healthcare Service'))

WebUI.delay(3)

WebUI.closeBrowser()
