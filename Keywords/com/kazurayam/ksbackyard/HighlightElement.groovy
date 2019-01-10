package com.kazurayam.ksbackyard

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords
import com.kms.katalon.core.keyword.internal.KeywordExecutor

public class HighlightElement {

	@Keyword
	public static void on(TestObject testObject) {
		influence(testObject)
	}

	private static void influence(TestObject testObject) {
		try {
			WebDriver driver = DriverFactory.getWebDriver()
			List<WebElement> elements = WebUiCommonHelper.findWebElements(testObject, 20);
			for (WebElement element : elements) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript(
						"arguments[0].setAttribute('style','outline: dashed red;');",
						element);
			}
		} catch (Exception e) {
			// TODO use Katalon Logging
			e.printStackTrace()
		}
	}

	/**
	 * overwride methods of WebUiBuiltInKeywords so that they call HighlightElement.on(testObject).
	 * http://docs.groovy-lang.org/latest/html/documentation/core-metaprogramming.html#metaprogramming_emc
	 */
	@Keyword
	public static void pandemic() {

		// click()
		WebUiBuiltInKeywords.metaClass.static.click = { TestObject to ->
			HighlightElement.on(to)
			KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_WEB, "click", to)
		}

		// selectOptionByValue()
		WebUiBuiltInKeywords.metaClass.static.selectOptionByValue = { TestObject to, String value, boolean isRegex ->
			HighlightElement.on(to)
			KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_WEB, "selectOptionByValue", to, value, isRegex)
		}

		// setEncryptedText()
		WebUiBuiltInKeywords.metaClass.static.setEncryptedText = { TestObject to, String encryptedText ->
			HighlightElement.on(to)
			KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_WEB, "setEncryptedText", to, encryptedText)
		}

		// setText()
		WebUiBuiltInKeywords.metaClass.static.setText = { TestObject to, String text ->
			HighlightElement.on(to)
			KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_WEB, "setText", to,text)
		}
	}

}