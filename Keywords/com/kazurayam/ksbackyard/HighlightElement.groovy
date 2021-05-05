package com.kazurayam.ksbackyard

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords

public class HighlightElement {

	@Keyword
	public static void on(TestObject testObject) {
		drawOutline(testObject)
	}

	private static void drawOutline(TestObject testObject) {
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
	 * @return list of the built-in WebUI.* keywords that can be highlighted by this.on(TestObject to)
	 */
	public static Set<String> getHighlightableBuiltinKeywords() {
		List<MetaMethod> metaMethods = WebUiBuiltInKeywords.metaClass.getMethods()
		//println "metaMethods.size() is ${metaMethods.size()}"
		Set<String> highlightables = new HashSet<String>()
		for (MetaMethod method in metaMethods) {
			if (method.isStatic() && method.isPublic()) {
				Class<?>[] parameterTypes = method.nativeParameterTypes
				// select a Keyword if it requires a TestObject as the 1st argument
				if ( parameterTypes.size() > 0 && parameterTypes[0].is(TestObject.class)) {
					//println "method: ${method.getName()}(${parameterTypes[0].getName()})"
					highlightables.add(method.getName())
				}
			}
		}
		return highlightables
	}


	public static final Set<String> DEFAULT_HIGHLIGHTING_KW = new HashSet([
		'click',
		'selectOptionByIndex',
		'selectOptionByLabel',
		'selectOptionByValue',
		'setEncryptedText',
		'setText'
	])

	// instance variables
	private final Set<String> highlightingKW

	/**
	 * constructor
	 */
	HighlightElement() {
		this.highlightingKW = new HashSet(DEFAULT_HIGHLIGHTING_KW)
	}

	/**
	 * change some of methods of WebUiBuiltInKeywords so that they call HighlightElement.on(testObject)
	 * before invoking their original method body.
	 *
	 * http://docs.groovy-lang.org/latest/html/documentation/core-metaprogramming.html#metaprogramming
	 */
	@Keyword
	public void pandemic(List<String> keywordsToAdd = []) {
		this.markKeywords(keywordsToAdd)
		Set<String> influencedKeywords = this.getHighlightingKeywords()
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

	/**
	 * 
	 * @param keywordsToAdd
	 */
	public void markKeywords(List<String> keywordsToAdd = []) {
		Objects.requireNonNull(keywordsToAdd)
		Set<String> highlightables = getHighlightableBuiltinKeywords()
		keywordsToAdd.each { kw ->
			// if the specified keyword is highlight-able, then accept it
			if (highlightables.contains(kw)) {
				//println "specified keyword \"${kw}\" is marked as highlight-able"
				this.highlightingKW.add(kw)
			} else {
				println "specified keyword \"${kw}\" is not highlight-able; just ignored"
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public Set<String> getHighlightingKeywords() {
		return highlightingKW.clone()
	}


	// previous implementation of pandemic without Groovy's Metaprogramming
	/*
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
	 */
}