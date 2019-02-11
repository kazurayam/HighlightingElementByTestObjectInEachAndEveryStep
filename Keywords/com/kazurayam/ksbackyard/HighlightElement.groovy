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

	// style of outline which highlights web element
	private static final enum AccessStatus {
		TOUCHED('dashed #9966cc'),
		CURRENT('dashed orange'),
		SUCCESS('dashed lime'),
		EXCEPTION('dashed red');
		String outline;
		AccessStatus(String outline) {
			this.outline = outline
		}
	}

	@Keyword
	public static final List<WebElement> on(TestObject testObject) {
		return vaccinate(testObject, AccessStatus.TOUCHED)
	}

	private static final List<WebElement> current(TestObject testObject) {
		return vaccinate(testObject, AccessStatus.CURRENT)
	}

	private static final List<WebElement> success(TestObject testObject) {
		return vaccinate(testObject, AccessStatus.SUCCESS)
	}

	private static final List<WebElement> exception(TestObject testObject) {
		return vaccinate(testObject, AccessStatus.EXCEPTION)
	}

	/*
	 * marks all Web elements that match the given test object,
	 * depending on their access status: 
	 * either orage (current), green (successful), or red (faulty).
	 */
	private final static List<WebElement> vaccinate(TestObject testObject, AccessStatus accessStatus) {
		List<WebElement> elements
		try {
			WebDriver driver = DriverFactory.getWebDriver()
			elements = WebUiCommonHelper.findWebElements(testObject, 5)
			for (WebElement element : elements) {
				JavascriptExecutor js = (JavascriptExecutor) driver
				js.executeScript(
						"arguments[0].setAttribute('style', 'outline: ${accessStatus.outline};');",
						element)
			}
		} catch (Exception e) {
			KeywordUtil.markFailed(e.getMessage())
		} finally {
			return elements
		}
	}

	/**
	 * <p>List of built-in keyword names that can be highlighted.
	 * The args[0] to the keyword call must be a TestObject.</p>
	 * <p>Built-in keywords only. 
	 * Custom keywords made by users are not covered by HighlightELement.</p>
	 */
	public static final List<String> vaccinatedKeywords = [
		'click',
		'selectOptionByIndex',
		'selectOptionByLabel',
		'selectOptionByValue',
		'setEncryptedText',
		'setText'
	]

	/**
	 * Check if the keyword is to be vaccinated or not
	 * 
	 * @param name a String as name of keyword
	 * @param args arguments to the keyword when called, not checked
	 * @return true if the name is found in the vaccinatedKeywords, otherwise false
	 */
	private static final boolean isVaccinated(String name, args) {
		return (name in vaccinatedKeywords)
	}

	/**
	 * Check if the keyword is to be traced or not.
	 * If the args[0] is an instance of TestObject, 
	 * then its internal detail info will be recorded in a GlobalVarialbe for tracing
	 * 
	 * @param name a String as name of keyword, not checked
	 * @param args arguments to the keyword when called
	 * @return true if the args[0] is instance of TestObject; otherwise false
	 */
	private static final boolean isToBeTraced(String name, args) {
		return (args[0] instanceof TestObject)
	}

	/**
	 * Call to pandemic() will modifies the Katalon-built-in keywords
	 * listed in the influecedKeywords.
	 * When invoked, the vaccinated keywords will mark the affected web elements 
	 * before and after each access with different styles: CURRENT, SUCCESS, EXCEPTION.
	 * 
	 * In case of an error, all relevant information about
	 * its circumstances, e.e. keywordName, testObject, testObjectString,
	 * inputParams, webElements, exceptions (with type and message) and
	 * even the lastWebElements that were recognized in the immediately
	 * preceding test step, in the dynamically generated variable of type Map.
	 */
	@Keyword
	static final void pandemic() {

		Karte karte = new Karte()
		karte.shiftRecord()

		Closure highlightingClosure = { String name, args ->
			if (isVaccinated(name, args)) {
				TestObject to = (TestObject)args[0]
				HighlightElement.current(to)
			}
			return delegate.metaClass.getMetaMethod(name, args).invoke(delegate, args)
		}

		Closure tracingClosure = { String keywordName, args ->
			def result
			if (isToBeTraced(keywordName, args)) {
				TestObject to = (TestObject)args[0]
				List<WebElement> target
				try {
					result = delegate.metaClass.getMetaMethod(keywordName, args).invoke(delegate, args)
					target = HighlightElement.success(to)
				} catch (StepFailedException e) {
					target = HighlightElement.exception(to)
					karte.logFailure(target, keywordName, args)
					throw e
				} catch (StepErrorException e) {
					target = HighlightElement.exception(to)
					karte.logError(target, keywordName, args)
					throw e
				} catch (Exception e) {
					target = HighlightElement.exception(to)
					karte.logGeneral(target, keywordName, args)
					throw e
				}
			} else {
				//
				result = delegate.metaClass.getMetaMethod(keywordName, args).invoke(delegate, args)
			}
			return result
		}

		// So, let's make built-in keywords vaccinated

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
		//WebUiBuiltinKeywords.metaClass.'static'.invokeMethod = { String name, args ->
		//}


	}

	/**
	 * Adds a GlobalVariable named as 'name' with value of 'value'
	 * dynamically at runtime
	 * 
	 * @param name name of GlobalVariable to be created on the fly
	 * @param value 
	 */
	@Keyword
	static void addGlobalVariable(String name, def value) {
		GroovyShell sh = new GroovyShell()
		MetaClass mc = sh.evaluate("internal.GlobalVariable").metaClass
		String getterName = 'get' + ((CharSequence)name).capitalize()
		mc.'static'."${getterName}" = {-> return value }
		mc.'static'."${name}"       = value
	}

	/**
	 * The name of GlobalVariable which pandemic() creates.
	 * The GlobalVariable will have type of java.util.Map.
	 * The vaccinated built-in keywords will record the detail information of how each
	 * keyword acted. You can trace back what was carried on before a StepFailureException was thrown.
	 * 
	 * You can print the content of the GlobalVariable by the following Groovy code as test script.
	 * 
	 * <PRE>
	 * import internal.GlobalVariable
	 * import groovy.json.JsonOutput
	 * import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
	 * 
	 * def traceInfo = JsonOutput.toJson(GlobalVariable.tcExceptionEvements)
	 * println JsonOutput.prettyPrint(traceInfo)
	 * </PRE>
	 */
	public static final String GVNAME = 'tcExceptionEvents'



	/**
	 * <p>Karte (Medical record) of a call for the influenced keyword.
	 * This class encapsulates data about influenced built-in keywords execution,
	 * which includes:
	 * </p>
	 * <p><ol>
	 * <li>which web element was targeted by the last call</li>
	 * <li>which web element was targeted by the current call</li>
	 * <li>Exception thrown by the current call</li
	 * </ol></p>
	 * 
	 * <p>This class implements  methods to operate the contained data.</p>
	 */
	static final class Karte {

		Karte() {}

		/**
		 * <p>
		 * When any of influenced keywords has been called before, shift the trace
		 * from the current slot to the previous slot.</p>
		 * <p>
		 * When none of influenced keywords have been called before (the very 1st call
		 * to those), then create the GlobalVariable.${GVNAME}.</p>
		 */
		void shiftRecord() {
			if (GlobalVariable.metaClass.hasProperty(GlobalVariable, GVNAME)) {
				GlobalVariable[GVNAME]['lastWebElements'] =
						GlobalVariable[GVNAME]['currentTestStep']['webElements']
			}
			else {
				HighlightElement.addGlobalVariable(GVNAME, initValue())
			}
		}

		/**
		 * @return a Map object as the initial value for the GlobalVariable.${GVNAME} 
		 */
		private static final Map initValue() {
			return ['exceptions' : [
					'Failure'  : [],
					'Error'    : [],
					'General'  : []],
				'currentTestStep' : [
					'webElements': null
				],
				'lastWebElements': null
			]
		}

		/**
		 * @param name
		 * @param args
		 */
		def logFailure(List<WebElement> currentTarget, String keywordName, args) {
			if (!(args[0] instanceof TestObject)) {
				throw new RuntimeException("args[0] is supposed be an instance of TestObject")
			}
			TestObject testObject = (TestObject)args[0]
			List<String> inputParams = args.collect{it}.withIndex().findResults{ it, id -> (id > 0) ? it: null }

			Map currentTestStep = [
				'webElements': currentTarget,
				'keywordName': keywordName,
				'testObject': testObject,
				'testObjectString': simplifyTestObjectString(testObject),
				'inputParams': inputParams
			]
		}

		/**
		 * 
		 * @param name
		 * @param args
		 * @return
		 */
		def logError(List<WebElement> currentTarget, String keywordName, args) {
			throw new UnsupportedOperationException("TODO")
		}

		/**
		 * @param name
		 * @param args
		 */
		def logGeneral(List<WebElement> currentTarget, String keywordName, args) {
			throw new UnsupportedOperationException("TODO")
		}

		/**
		 * 
		 * @param testObject
		 * @return
		 */
		static String simplifyTestObjectString(TestObject testObject) {
			return testObject.toString().replaceFirst("^TestObject - \\'(.*?)\\'\$", '$1')
		}
	}
}


