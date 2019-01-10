package com.kazurayam.ksbackyard

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.webui.keyword.builtin.SetTextKeyword

public class HighlightElement {
	
	@Keyword
	public static void onAny() {
		SetTextKeyword.metaClass.'static'.invokeMethod = { Object object, String name, Object[] args ->
			println "${name} invoked"
			if (name =~ /setText/) {
				TestObject testObject = (TestObject)args[0]
				HighlightElement.on(testObject)
			}
			return super.invokeMethod(object, name, args)
		}
	}

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
}