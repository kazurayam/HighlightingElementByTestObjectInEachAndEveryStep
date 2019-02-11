package com.kazurayam.ksbackyard

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

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

import internal.GlobalVariable

import com.kazurayam.ksbackyard.HighlightElement.AccessStatus

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4.class)
public class HighlightElementKarteTest {

	@Test
	void test_simplifyTestObjectString() {
		TestObject to = findTestObject('Object Repository/Page_CURA Healthcare Service_top/a_Make Appointment')
		//println to.toString()
		assertEquals("TestObject - 'Object Repository/Page_CURA Healthcare Service_top/a_Make Appointment'", to.toString())
		String simplified = HighlightElement.Karte.simplifyTestObjectString(to)
		assertEquals("Object Repository/Page_CURA Healthcare Service_top/a_Make Appointment", simplified)
	}
}