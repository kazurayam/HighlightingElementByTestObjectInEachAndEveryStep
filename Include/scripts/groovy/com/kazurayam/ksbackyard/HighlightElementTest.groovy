package com.kazurayam.ksbackyard

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4.class)
public class HighlightElementTest {

	@Test
	void test_AccessStatus_CURRENT() {
		assertEquals("color of CURRENT is to be ",
				HighlightElement.AccessStatus.CURRENT.outline, 'dashed orange')
	}

	@Test
	void test_AccessStatus_SUCCESS() {
		assertEquals("color of SUCCESS is to be ",
				HighlightElement.AccessStatus.SUCCESS.outline, 'dashed lime')
	}

	@Test
	void test_AccessStatus_EXCEPTION() {
		assertEquals("color of EXCEPTION is to be ",
				HighlightElement.AccessStatus.EXCEPTION.outline, 'dashed red')
	}

	@Test
	void test_contents_vaccinatedKeywords() {
		List<String> k = HighlightElement.vaccinatedKeywords
		assertTrue(k.contains('click'))
		assertTrue(k.contains('selectOptionByIndex'))
		assertTrue(k.contains('selectOptionByLabel'))
		assertTrue(k.contains('selectOptionByValue'))
		assertTrue(k.contains('setEncryptedText'))
		assertTrue(k.contains('setText'))
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
}