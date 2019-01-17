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
		
		// click() with FailuraHandling
		WebUiBuiltInKeywords.metaClass.static.click = { TestObject to, FailureHandling flowControl ->
			HighlightElement.on(to)
			KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_WEB, "click", to, flowControl)
		}
		
		// click() without FailuraHandling
		WebUiBuiltInKeywords.metaClass.static.click = { TestObject to ->
			HighlightElement.on(to)
			KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_WEB, "click", to)
		}
		
		// selectOptionByIndex() with FailureHandling
		WebUiBuiltInKeywords.metaClass.static.selectOptionByIndex = { TestObject to, Object range, FailureHandling flowControl ->
			HighlightElement.on(to)
			KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_WEB, "selectOptionByIndex", to, range, flowControl)
		}
		
		// selectOptionByIndex() without FailureHandling
		WebUiBuiltInKeywords.metaClass.static.selectOptionByIndex = { TestObject to, Object range ->
			HighlightElement.on(to)
			KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_WEB, "selectOptionByIndex", to, range)
		}

		// selectOptionByLabel() with FailureHandling
		WebUiBuiltInKeywords.metaClass.static.selectOptionByLabel = { TestObject to, String value, boolean isRegex, FailureHandling flowControl ->
			HighlightElement.on(to)
			KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_WEB, "selectOptionByLabel", to, value, isRegex, flowControl)
		}

		// selectOptionByLabel() without FailureHandling
		WebUiBuiltInKeywords.metaClass.static.selectOptionByLabel = { TestObject to, String value, boolean isRegex ->
			HighlightElement.on(to)
			KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_WEB, "selectOptionByLabel", to, value, isRegex)
		}

		// selectOptionByValue() with FailureHandling
		WebUiBuiltInKeywords.metaClass.static.selectOptionByValue = { TestObject to, String value, boolean isRegex, FailureHandling flowControl ->
			HighlightElement.on(to)
			KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_WEB, "selectOptionByValue", to, value, isRegex, flowControl)
		}

		// selectOptionByValue() without FailureHandling
		WebUiBuiltInKeywords.metaClass.static.selectOptionByValue = { TestObject to, String value, boolean isRegex ->
			HighlightElement.on(to)
			KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_WEB, "selectOptionByValue", to, value, isRegex)
		}

		// setEncryptedText() with FailureHandling
		WebUiBuiltInKeywords.metaClass.static.setEncryptedText = { TestObject to, String encryptedText, FailureHandling flowControl ->
			HighlightElement.on(to)
			KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_WEB, "setEncryptedText", to, encryptedText, flowControl)
		}
		
		// setEncryptedText() without FailureHandling
		WebUiBuiltInKeywords.metaClass.static.setEncryptedText = { TestObject to, String encryptedText ->
			HighlightElement.on(to)
			KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_WEB, "setEncryptedText", to, encryptedText)
		}

		// setText() with FailureHandling
		WebUiBuiltInKeywords.metaClass.static.setText = { TestObject to, String text, FailureHandling flowControl ->
			HighlightElement.on(to)
			KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_WEB, "setText", to, text, flowControl)
		}

		// setText() without FailureHandling
		WebUiBuiltInKeywords.metaClass.static.setText = { TestObject to, String text ->
			HighlightElement.on(to)
			KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_WEB, "setText", to, text)
		}
	}
}