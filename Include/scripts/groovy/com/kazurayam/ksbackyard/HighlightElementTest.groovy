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
		assertEquals("CURRENT color is expected to be orange",
			HighlightElement.AccessStatus.CURRENT.color, 'orange')
	}
}
