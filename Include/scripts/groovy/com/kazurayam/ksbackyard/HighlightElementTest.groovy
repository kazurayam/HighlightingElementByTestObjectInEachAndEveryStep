package com.kazurayam.ksbackyard

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import java.nio.file.Path
import java.nio.file.Paths
import com.kms.katalon.core.configuration.RunConfiguration

@RunWith(JUnit4.class)
public class HighlightElementTest {
	
	@Test
	void test_highlightableWebUIKeywords() {
		List<String> list = HighlightElement.highlightableWebUIKeywords()
		assertTrue("list.size() should be >0", list.size() > 0)
	}
	
	@Test
	void compile_highlitable_keywords_doc() {
		StringBuilder sb = new StringBuilder()
		sb.append("## Highlight-table WebUI Keywords\n\n")
		sb.append("| No. | Keyword (click to doc) | highlighted by default? |\n")
		sb.append("|-----|---------|-------------|\n")
		List<String> list = HighlightElement.highlightableWebUIKeywords()
		String urlPrefix = "https://docs.katalon.com/katalon-studio/docs"
		list.eachWithIndex { keyword, index ->
			sb.append("| ${index} ")
			String vaccinated = (HighlightElement.vaccinatedKeywords.contains(keyword)) ? 'Yes' : ''
			sb.append("| [WebUI.${keyword}](${urlPrefix}/webui-${this.camel2chain(keyword)}.html) ")
			sb.append("| ${vaccinated} ")
			sb.append("|")
			sb.append("\n") 
		}
		Path projectDir = Paths.get(RunConfiguration.getProjectDir())
		Path doc = projectDir.resolve("docs/highlightable_keywords.md")
		doc.toFile().text = sb.toString()
	}
	
	@Test
	void test_camel2chain() {
		String given = "verifyElementPresent"
		String expected = "verify-element-present"
		String actual = this.camel2chain(given)
		assertThat(actual, is(expected))
	}
	
	/**
	 * turn a string in Camel case to a chain of lower case strings concatinated by hyphens
	 * 
	 * e.g, 'verifyElementPresent' -> 'verify-element-present'
	 * 
	 * @param name
	 * @return
	 */
	private String camel2chain(String given) {
		StringBuilder sb = new StringBuilder()
		char[] chars = given.toCharArray()
		chars.each { c ->
			if (Character.isUpperCase(c)) {
				sb.append('-')
				sb.append(Character.toLowerCase(c))
			} else {
				sb.append(c)
			}
		}
		return sb.toString()
	}
	
	@Test
	void test_getInfluencedKeywords() {
		Set<String> influenced = HighlightElement.getInfluencedKeywords(["verifyElementPresent"])
		assertTrue("WebUI.click should be influenced",influenced.contains("click"))
		assertTrue("WebUI.verifyElementPresent should be added",influenced.contains("verifyElementPresent"))
	}
}
