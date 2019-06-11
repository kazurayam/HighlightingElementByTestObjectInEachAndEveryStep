package com.kazurayam.ksbackyard

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.keyword.internal.KeywordExecutor
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords
import com.kms.katalon.core.keyword.BuiltinKeywords

public final class HighlightElement {

	@Keyword
	public static final List<WebElement> on(TestObject testObject) {
		return examine(DriverFactory.getWebDriver(), testObject, OutlineStyle.TOUCHED)
	}

	// style of outline to highlight web element
	private static final enum OutlineStyle {
		TOUCHED('dashed hotpink');
		String value;
		OutlineStyle(String value) {
			this.value = value
		}
	}

	/**
	 * <p>List of names of Katalon Studio built-in keywords that can be highlighted.
	 * Keywords are supposed to have TestObject as the first argument of the call (args[0]).</p> 
	 */
	private static final List<String> highlightingCapableKeywords_ = [
		'click',
		'getText',
		'selectOptionByIndex',
		'selectOptionByLabel',
		'selectOptionByValue',
		'sendKeys',
		'setEncryptedText',
		'setText'
	]

	private final static List<WebElement> examine(WebDriver driver,
			TestObject testObject, OutlineStyle outlineStyle) {
		List<WebElement> elements
		try {
			elements = WebUiCommonHelper.findWebElements(testObject, 1);  // timeout should be minimum
			for (WebElement element : elements) {
				JavascriptExecutor js = (JavascriptExecutor) driver
				js.executeScript(
						"arguments[0].setAttribute('style', 'outline: " +
						"${outlineStyle.value};');",
						element);
			}
		} catch (Exception e) {
			;
			// Here we should ignore the Exception silently.
			// But why? --- because WebUI.click() will fall down into here.
		} finally {
			return elements
		}
	}


	/**
	 * <p>Test case script should call enlightKeywords() before calling WebUI keywords 
	 * listed in the highlightableKeywords list.
	 * enlightKeywords() modifies the behavior of keywords.
	 * When your test case calls a WebUI keyword, the keyword put highlight on the target
	 * element and then do its own original behavior.
	 * </p>
	 *
	 * <p>This method employs the Metaprogramming feature of Groovy language.
	 * See the following document.</p>
	 * http://docs.groovy-lang.org/latest/html/documentation/core-metaprogramming.html#metaprogramming
	 */
	@Keyword
	public static final void enlightWebUiBuiltInKeywords() {
		List<String> additionalKeywords = []
		enlightWebUiBuiltInKeywords(additionalKeywords)
	}

	@Keyword
	public static final void enlightWebUiBuiltInKeywords(List<String> additionalKeywords) {
		highlightingCapableKeywords_.add(additionalKeywords)
		WebUiBuiltInKeywords.metaClass.'static'.invokeMethod = { String keywordName, Object args ->
			if (isHighlightingCapable(highlightingCapableKeywords_, keywordName, args)) {
				TestObject to = (TestObject)args[0]
				HighlightElement.on(to)
			}
			return delegate.metaClass.getMetaMethod(keywordName, args).invoke(delegate, args)
		}
	}

	/**
	 * <p>check if the keyword call can be modified to highlight the target element.</p>
	 * 
	 * @param keywordName
	 * @param args
	 * @return
	 */
	private static final boolean isHighlightingCapable(List<String> highlightingCapableKeywords, String keywordName, Object args) {
		return (keywordName in highlightingCapableKeywords && hasTestObjectAs1stArg(args))
	}

	/**
	 * Check if the args is an array with 1 or more elements, and 
	 * args[0] is an instance of TestObject,
	 * then return true, otherwise false.
	 * 
	 * Remember, there is some built-in keywords with ZERO arguments; e.g., WeUI.closeBrowser()
	 *
	 * @param name a String as name of keyword, not checked
	 * @param args an array of arguments to the keyword call
	 * @return true if the args[0] is instance of TestObject; otherwise false
	 */
	private static final boolean hasTestObjectAs1stArg(Object args) {
		Class clazz = args.getClass()
		if (clazz.isArray()) {
			Object[] objects = (Object[])args
			if (objects.length > 0) {
				return (args[0] instanceof TestObject)
			} else
				return false
		} else
			return false
	}

}
