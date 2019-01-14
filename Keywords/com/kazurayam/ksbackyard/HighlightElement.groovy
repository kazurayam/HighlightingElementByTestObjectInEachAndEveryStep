package com.kazurayam.ksbackyard

import java.text.MessageFormat

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.keyword.internal.IKeyword
import com.kms.katalon.core.keyword.internal.KeywordExecutor
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.constants.StringConstants
import com.kms.katalon.core.webui.driver.DriverFactory

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
		def influencedKeywords = [
			'click',
			'setEncriptedText',
			'selectOptionByValue'
			]
		KeywordExecutor.metaClass.static.executeKeywordForPlatform = { String platform, String keyword, Object ...params ->
			if (keyword in influencedKeywords) {
				HighlightElement.on((TestObject)params[0])	
				println "keyword=${keyword} is influeced"
			} else {
				println "keyword=${keyword} is not influenced"
			}
			IKeyword[] actions = getActions(keyword, getSuitablePackage(platform))
			if (actions.length != 1) {
				throw new StepFailedException(MessageFormat.format(StringConstants.KEYWORD_X_DOES_NOT_EXIST_ON_PLATFORM_Y, [keyword, platform] as Object[]))
			}
			return actions[0].execute(params)
		}
	}

}