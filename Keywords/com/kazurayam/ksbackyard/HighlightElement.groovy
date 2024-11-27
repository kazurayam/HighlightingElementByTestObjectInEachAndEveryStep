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

public class HighlightElement {

	@Keyword
	public static void on(TestObject testObject) {
		influence(testObject)
	}

	private static String DEFAULT_STYLE = 'outline: dotted red'
	private static String highlightingStyle = DEFAULT_STYLE

	private static void influence(TestObject testObject) {
		try {
			WebDriver driver = DriverFactory.getWebDriver()
			List<WebElement> elements = WebUiCommonHelper.findWebElements(testObject, 20);
			for (WebElement element : elements) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript(
						"arguments[0].setAttribute('style', '${highlightingStyle}');",
						element);
			}
		} catch (Exception e) {
			e.printStackTrace()
		}
	}

	private static List<String> influencedKeywords = [
		'click',
		'selectOptionByIndex',
		'selectOptionByLabel',
		'selectOptionByValue',
		'setEncryptedText',
		'setText'
	]

	/**
	 * change some of methods of WebUiBuiltInKeywords so that they call HighlightElement.on(testObject)
	 * before invoking their original method body.
	 *
	 * http://docs.groovy-lang.org/latest/html/documentation/core-metaprogramming.html#metaprogramming
	 */
	@Keyword
	public static void pandemic(String style, List<String> keywords) {
		highlightingStyle = style
		influencedKeywords.addAll(keywords)
		WebUiBuiltInKeywords.metaClass.'static'.invokeMethod = { String name, args ->
			if (name in influencedKeywords) {
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

	@Keyword
	public static void pandemic() {
		pandemic(DEFAULT_STYLE, [])
	}

	@Keyword
	public static void pandemic(String style) {
		pandemic(style, [])
	}

	@Keyword
	public static void pademic(List<String> keywords) {
		pandemic(DEFAULT_STYLE, [])
	}
}
