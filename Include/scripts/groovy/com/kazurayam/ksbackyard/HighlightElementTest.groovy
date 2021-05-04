package com.kazurayam.ksbackyard

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.Ignore
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4.class)
public class HighlightElementTest {
	
	HighlightElement instance
	
	@Before
	void setup() {
		instance = new HighlightElement()
	}
	
	@Test
	void test_getHighlightableBuiltinKeywords() {
		Set<String> keywords = HighlightElement.getHighlightableBuiltinKeywords()
		assertTrue("keywords.size() should be >0; keywords is ${keywords}", keywords.size() > 0)
		assertTrue("WebUI.click should be included", keywords.contains("click"))
	}
	
	@Test
	void test_getHighlightingKeywords() {
		Set<String> keywords = instance.getHighlightingKeywords()
		assertTrue("WebUI.click should be influenced by default", keywords.contains("click"))
	}
	
	@Test
	void test_markKeywords() {
		instance.markKeywords(["verifyElementPresent", "waitForElementVisible"])
		Set<String> keywords = instance.getHighlightingKeywords()
		assertTrue("keywords.size() should be >0", keywords.size() > 0)
		assertTrue("WebUI.click should be influenced by default", keywords.contains("click"))
		assertTrue("WebUI.verifyElementPresent should be marked", keywords.contains("verifyElementPresent"))
		assertTrue("WebUI.waitForElementVisible should be marked", keywords.contains("waitForElementVisible"))
	}

	@Test
	void test_on_with_verifyElementPresent() {
		WebUI.openBrowser('')
		WebUI.navigateToUrl("http://demoaut.katalon.com")
		instance.pandemic(["verifyElementPresent"])
		TestObject tObj = createTestObject("//a[@id='menu-toggle']")
		WebUI.verifyElementPresent(tObj, 10)
		WebUI.delay(2)
		WebUI.closeBrowser()
	}

	/**
	 * helper function that creates a new TestObject with XPath
	 */
	TestObject createTestObject(String xpath) {
		TestObject to = new TestObject(xpath)
		to.addProperty('xpath', ConditionType.EQUALS, xpath)
		return to
	}
}
