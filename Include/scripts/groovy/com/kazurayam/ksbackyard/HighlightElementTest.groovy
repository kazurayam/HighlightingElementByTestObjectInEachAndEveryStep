package com.kazurayam.ksbackyard

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import static org.junit.Assert.*

import org.junit.Test
import org.junit.Ignore
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4.class)
public class HighlightElementTest {

	@Test
	void test_getInfluencedKeywords_default() {
		Set<String> influenced = HighlightElement.getInfluencedKeywords()
		assertTrue("WebUI.click should be influenced by default",influenced.contains("click"))
	}

	@Test
	void test_getInfluencedKeywords_addOne() {
		Set<String> influenced = HighlightElement.getInfluencedKeywords(["verifyElementPresent"])
		assertTrue("WebUI.verifyElementPresent should be added",influenced.contains("verifyElementPresent"))
	}

	
	@Test
	void test_on_with_addKeyword() {
		WebUI.openBrowser('')
		WebUI.navigateToUrl("http://demoaut.katalon.com")
		com.kazurayam.ksbackyard.HighlightElement.pandemic(["verifyElementPresent"])
		TestObject tObj = createTestObject("//a[@id='menu-toggle']")
		WebUI.verifyElementPresent(tObj, 10)
		WebUI.closeBrowser()
	}

	void test_getHighlightableBuiltinKeywords() {
		Set<String> set = HighlightElement.getHighlightableBuiltinKeywords()
		assertTrue("set.size() should be >0; set is ${set}", set.size() > 0)
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
