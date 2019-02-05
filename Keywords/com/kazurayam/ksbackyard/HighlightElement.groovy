package com.kazurayam.ksbackyard

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.keyword.internal.KeywordExecutor
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords
import com.kms.katalon.core.util.KeywordUtil

import internal.GlobalVariable

/**
 * A custom keyword for Katalon Studio.
 * You can put highlight on web elements you targeted on your web page using
 * WebUI.* elements ().
 * 
 * [usage]
 * in your test case of Katalon Studio, just write one line:
 * 
 * <pre>
 * CustomKeywords.'com.karurayam.ksbackyard.HighlightElement.pandemic'()
 * </pre>
 * 
 * 
 * @author kazurayam
 * @author drundanibel
 */
public final class HighlightElement {

	private final static enum AccessStatus {
		TOUCHED('#9966cc'),
		CURRENT('orange'),
		SUCCESS('lime'),
		EXCEPTION('red');
		String color;
		AccessStatus(String color) {
			this.color = color
		}
	}

	@Keyword
	public final static List<WebElement> on(TestObject testObject) {
		return influence(testObject, AccessStatus.TOUCHED)
	}

	@Keyword
	public final static List<WebElement> current(TestObject testObject) {
		return influence(testObject, AccessStatus.CURRENT)
	}

	@Keyword
	public final static List<WebElement> success(TestObject testObject) {
		return influence(testObject, AccessStatus.SUCCESS)
	}

	@Keyword
	public final static List<WebElement> exception(TestObject testObject) {
		return influence(testObject, AccessStatus.EXCEPTION)
	}

	/*
	 * marks all Web elements that match the given test object,
	 * depending on their access status, 
	 * either orage (current), green (successful), or red (faulty).
	 */
	private final static List<WebElement> influence(TestObject testObject, AccessStatus accessStatus) {
		List<WebElement> elements
		try {
			WebDriver driver = DriverFactory.getWebDriver()
			elements = WebUiCommonHelper.findWebElements(testObject, 5)
			for (WebElement element : elements) {
				JavascriptExecutor js = (JavascriptExecutor) driver
				js.executeScript(
						"arguments[0].setAttribute('style', 'outline: dashed ${accessStatus.color};');",
						element)
			}
		} catch (Exception e) {
			KeywordUtil.markFailed(e.getMessage())
		} finally {
			return elements
		}
	}

	static final List<String> influencedKeywords = [
		'click',
		'selectOptionByIndex',
		'selectOptionByLabel',
		'selectOptionByValue',
		'setEncryptedText',
		'setText'
	]

	private static final boolean isInfluenced(String name, args) {
		return (name in influencedKeywords)
	}

	private static final boolean isToBeMonitored(String name, args) {
		return (args[0] instanceof TestObject)
	}

	private static final String GVNAME = 'tcExceptionEvents'

	/**
	 * Manipulates all keyword methods contained in the list influencedKeywords
	 * when called in the respective test case in order to mark the affected
	 * web elements before and after each access with different colors and
	 * in case of an error to temporarily store all relevant information about
	 * its circumstances, e.e. keywordName, testObject, testObjectString,
	 * inputParams, webElements, exceptions (with type and message) and
	 * even the lastWebElements that were recognized in the immediately
	 * preceding test step, in the dynamically generated variable of type Map.
	 */
	@Keyword
	static final void pandemic() {
		Karte karte = new Karte()

		/*
		 WebUiBuiltInKeywords.metaClass.'static'.invokeMethod = { String name, args ->
		 if (influenced(name, args)) {
		 TestObject to = (TestObject)args[0]
		 karte.record(name, to, args)
		 List<WebElement> currentWebElements = HighlightElement.current(to)
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
		 */
		WebUiBuiltinKeywords.metaClass.'static'.invokeMethod = { String name, args ->
		}

		//
		def highlightingCurrentElementClosure = { String name, args ->
			if (isInfluenced(name, args)) {
				TestObject to = (TestObject)args[0]
				HighlightElement.current(to)
			}
			return delegate.metaClass.getMetaMethod(name, args).invoke(delegate, args)
		}

		//
		def monitoringClosure = { String name, args ->
			def result
			if (isToBeMonitored(name, args)) {
				TestObject to = (TestObject)args[0]
				try {
					result = delegate.metaClass.getMetaMethod(name, args).invoke(delegate, args)
					HighlightElement.success(to)
				} catch (StepFailedException e) {
					HighlightElement.exception(to)
					karte.logFailure(e)
					throw e
				} catch (StepErrorException e) {
					HighlightElement.exception(to)
					karte.logError(e)
					throw e
				} catch (Exception e) {
					HighlightElement.exception(to)
					karte.logGeneral(e)
					throw e
				}
			} else {
				result = delegate.metaClass.getMetaMethod(name, args).invoke(delegate, args)
			}
			return result
		}
	}


	/**
	 * Medical record of the test execution
	 */
	private static final class Karte {
		Karte() {
			if (GlobalVariable.metaClass.hasProperty(GlobalVariable, GVNAME)) {
				GlobalVariable[GVNAME]['lastWebElements'] =
						GlobalVariable[GVNAME]['currentTestStep']['webElements']
			}
			else {
				addGlobalVariable(GVNAME, [
					'exceptions' : [
						'Failure'  : [],
						'Error'    : [],
						'General'  : []],
					'currentTestStep' : [
						'webElements': null
					],
					'lastWebElements': null
				])
			}
		}
		/**
		 * adds GlobalVariable dynamically at script runtime
		 */
		void addGlobalVariable(String name, def value) {
			GroovyShell sh = new GroovyShell()
			MetaClass mc = sh.evaluate("internal.GlobalVariable").metaClass
			String getterName = 'get' + name.capitalize()
			mc.'static'."${getterName}" = {-> return value }
			mc.'static'."${name}" = value
		}
		/**
		 * 
		 */
		def logFailure(String name, args) {
			if (!(args[0] instanceof TestObject)) {
				throw new RuntimeException("args[0] is supposed be an instance of TestObject")
			}
			TestObject to = (TestObject)args[0]
			String toStr = to.toString().replaceFirst(/^TestObject - '(.*?)'$/, '$1')

			// what are you doing here?
			List<String> inputParams = args.collect{it}.withIndex().
			findResults{ it, id -> (id > 0) ? it: null }

			Map currentTestStep = [
				'keywordName': name,
				'testObject': to,
				'testObjectString': toStr,
				'inputParams': inputParams,
				'webElements': currentWebElements
			]
		}
		def logError(String name, args) {
			throw new UnsupportedOperationException("TODO")
		}
		def logGeneral(String name, args) {
			throw new UnsupportedOperationException("TODO")
		}
	}

}


