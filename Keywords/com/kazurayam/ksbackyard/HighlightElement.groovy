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

public final class HighlightElement {

	// style of outline to highlight web element
	private static final enum OutlineStyle {
		TOUCHED('dashed aqua');
		String value;
		OutlineStyle(String value) {
			this.value = value
		}
	}
	
	
	@Keyword
	public static final List<WebElement> on(TestObject testObject) {
		return examine(DriverFactory.getWebDriver(), testObject, OutlineStyle.TOUCHED)
	}

	/**
	 * <p>List of names of Katalon Studio built-in keywords that can be highlighted.
	 * Keywords are supposed to have TestObject as the first argument of the call (args[0]).</p> 
	 */
	private static final List<String> highlightableKeywords = [
		'click',
		'selectOptionByIndex',
		'selectOptionByLabel',
		'selectOptionByValue',
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
	public static final void enlightKeywords() {
		WebUiBuiltInKeywords.metaClass.'static'.invokeMethod = { String name, args ->
			if (isHighlightable(name, args)) {
				TestObject to = (TestObject)args[0]
				HighlightElement.on(to)
			}
			def result
			try {
				result = delegate.metaClass.getMetaMethod(name, args).invoke(delegate, args)
				
			} catch(Exception e) {
				System.out.println("Handling exception for method $name")
			}
			return result
		}
	}

	/**
	 * <p>check if the keyword can be modified to highlight target element.</p>
	 * 
	 * @param keywordName
	 * @param args
	 * @return
	 */
	private static final boolean isHighlightable(String keywordName, Object args) {
		return (keywordName in highlightableKeywords)
	}

}
