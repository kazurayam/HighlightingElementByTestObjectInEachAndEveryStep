package com.kazurayam.ksbackyard

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

@RunWith(JUnit4.class)
public class HighlightElementTest {

	@Test
	void test_AccessStatus_CURRENT() {
		WebUI.comment("test_AccessStatus_CURRENT")
		assertEquals("color of CURRENT is to be ",
				HighlightElement.AccessStatus.CURRENT.outline, 'dashed orange')
	}

	@Test
	void test_AccessStatus_SUCCESS() {
		WebUI.comment("test_AccessStatus_SUCCESS")
		assertEquals("color of SUCCESS is to be ",
				HighlightElement.AccessStatus.SUCCESS.outline, 'dashed lime')
	}

	@Test
	void test_AccessStatus_EXCEPTION() {
		WebUI.comment("test_AccessStatus_EXCEPTION")
		assertEquals("color of EXCEPTION is to be ",
				HighlightElement.AccessStatus.EXCEPTION.outline, 'dashed red')
	}

	@Test
	void test_contents_vaccinatedKeywords() {
		WebUI.comment("test_vaccinatedKeywords")
		List<String> k = HighlightElement.vaccinatedKeywords
		assertTrue(k.contains('click'))
		assertTrue(k.contains('selectOptionByIndex'))
		assertTrue(k.contains('selectOptionByLabel'))
		assertTrue(k.contains('selectOptionByValue'))
		assertTrue(k.contains('setEncryptedText'))
		assertTrue(k.contains('setText'))
	}

	@Test
	void test_isVaccinated() {
		WebUI.comment("test_isVaccinated")
		assertTrue(HighlightElement.isVaccinated('click', null))
		assertTrue(HighlightElement.isVaccinated('selectOptionByIndex', null))
		assertTrue(HighlightElement.isVaccinated('selectOptionByLabel', null))
		assertTrue(HighlightElement.isVaccinated('selectOptionByValue', null))
		assertTrue(HighlightElement.isVaccinated('setEncryptedText', null))
		assertTrue(HighlightElement.isVaccinated('setText', null))
	}
	
	
	@Test
	void test_isToBeTraced() {
		WebUI.comment("test_isToBeTraced")
		assertTrue(HighlightElement.isToBeTraced("click", [new TestObject()]))
		assertTrue(HighlightElement.isToBeTraced("selectOptionByIndex", [new TestObject()]))
		assertTrue(HighlightElement.isToBeTraced("selectOptionByLabel", [new TestObject()]))
		assertTrue(HighlightElement.isToBeTraced("selectOptionByValue", [new TestObject()]))
		assertTrue(HighlightElement.isToBeTraced("setEncryptedText", [new TestObject()]))
		assertTrue(HighlightElement.isToBeTraced("setText", [new TestObject()]))
		assertFalse(HighlightElement.isToBeTraced("openBrowser", ["foo"]))
	}

	/**
	 * initValue() should return a Map object as follows:
	 * <PRE>
	 * [
	 *     'exceptions' : [
	 *         'Failure'  : [],
	 *         'Error'    : [],
	 *         'General'  : []
	 *     ],
	 *     'currentTestStep' : [
	 *         'webElements': null
	 *     ],
	 *     'lastWebElements': null
	 * ]
	 * </PRE>
	 */
	@Test
	void test_Karte_initValue() {
		WebUI.comment("test_Karte_initValue")
		Map v = HighlightElement.Karte.initValue()
		//println v
		assertTrue("Key exception is not contained", v.containsKey('exceptions'))
		assertTrue("Key currentTestStep is not contained", v.containsKey('currentTestStep'))
		assertTrue("Key lastWebElements is not contained", v.containsKey('lastWebElements'))
		Map e = v.exceptions
		assertTrue("Key Failure is not contained", e.containsKey('Failure'))
		assertTrue("e.Failure is not instance of List", e.Failure instanceof List)
		assertTrue("Key Error is not contained",   e.containsKey('Error'))
		assertTrue("e.Error is not instance of List", e.Error instanceof List)
		assertTrue("Key General is not contained", e.containsKey('General'))
		assertTrue("e.General is not instance of List", e.General instanceof List)
		Map c = v.currentTestStep
		assertTrue("Key webElements is not contained", c.containsKey('webElements'))
		assertNull("c.webElements is expected to be null", c.webElements)
		def l = v.lastWebElements
		assertNull("l is expected to be null", l)
	}


	@Test
	void test_addGlobalVariable() {
		WebUI.comment("test_addGlobalVariable")
		HighlightElement.addGlobalVariable("foo", HighlightElement.Karte.initValue())
		// println JsonOutput.prettyPrint(JsonOutput.toJson(GlobalVariable.foo))
		assertNotNull(GlobalVariable.foo)
		assertTrue(GlobalVariable.foo instanceof Map)
	}
	
	@Test
	void test_examine() {
		WebUI.comment("test_examine")
		TestObject to = findTestObject('Object Repository/Page_CURA Healthcare Service_top/a_Make Appointment')
		WebUI.openBrowser('http://demoaut.katalon.com')
		WebUI.verifyElementPresent(to, 10)
		WebDriver driver = DriverFactory.getWebDriver()
		List<WebElement> list = HighlightElement.examine(driver, to, HighlightElement.AccessStatus.TOUCHED)
		WebUI.delay(3)
		WebUI.closeBrowser()
		assertNotNull("HighlightElement.examine returned null", list)
		assertEquals("HighlightElement.examine", 1, list.size()) 
	}
	
	
	
}