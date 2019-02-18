package com.kazurayam.ksbackyard

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords
import com.kms.katalon.core.webui.driver.DriverFactory

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
		TOUCHED('dashed #9966cc'),  // purple
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
		return examine(DriverFactory.getWebDriver(), testObject, AccessStatus.TOUCHED)
	}

	private static final List<WebElement> current(TestObject testObject) {
		return examine(DriverFactory.getWebDriver(), testObject, AccessStatus.CURRENT)
	}

	private static final List<WebElement> success(TestObject testObject) {
		return examine(DriverFactory.getWebDriver(), testObject, AccessStatus.SUCCESS)
	}

	private static final List<WebElement> exception(TestObject testObject) {
		return examine(DriverFactory.getWebDriver(), testObject, AccessStatus.EXCEPTION)
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
	 * draw outline on Web elements that match the given test object,
	 * depending on their access status:
	 * either orange (current), green (successful), or red (faulty).
	 */
	private final static List<WebElement> examine(WebDriver driver,
			TestObject testObject, AccessStatus accessStatus) {
		List<WebElement> elements
		try {
			elements = WebUiCommonHelper.findWebElements(testObject, 5)
			for (WebElement element : elements) {
				JavascriptExecutor js = (JavascriptExecutor) driver
				js.executeScript(
						"arguments[0].setAttribute('style', 'outline: " +
						"${accessStatus.outline};');",
						element)
			}
		} catch (Exception e) {
			KeywordUtil.markFailed(e.getMessage())
		} finally {
			return elements
		}
	}

	/**
	 * pandemic() is aliased to inspect().
	 * pandemic() is retained for backward compatibility.
	 *
	 * @deprecated use inspect() instead
	 */
	@Keyword
	static final void pandemic() {
		inspect()
	}

	/**
	 * <p>Call to inspect() modifies the Katalon-built-in keywords.</p>
	 * <p>
	 * When invoked, the vaccinated keywords will highlight the target 
	 * web elements before and after each access with different styles:
	 * CURRENT, SUCCESS, EXCEPTION.</p>
	 * 
	 * <p>
	 * In case of an error, all relevant information about
	 * its circumstances, e.e. keywordName, testObject, testObjectString,
	 * inputParams, webElements, exceptions (with type and message) and
	 * even the lastWebElements that were recognized in the immediately
	 * preceding test step. 
	 * These info is recorded in GlobalVariable.${GVNAME}.</p>
	 */
	@Keyword
	static final void inspect() {
		Inspector inspector = new Inspector()  // inner class "Inspector"
		WebUiBuiltInKeywords.metaClass.'static'.invokeMethod = { String keywordName, Object args ->
			println "isVaccinated(${keywordName},${args}) returned ${isVaccinated(keywordName,args)}"
			if (isVaccinated(keywordName, args)) {
				TestObject to = (TestObject)args[0]
				HighlightElement.current(to)
			}
			//
			def result
			println "isToBeTraced(${keywordName},${args}) returned ${isToBeTraced(keywordName,args)}"
			if (isToBeTraced(keywordName, args)) {
				TestObject to = (TestObject)args[0]
				List<WebElement> target
				try {
					// execute the built-in keyword's method body with tracing
					result = delegate.metaClass.getMetaMethod(keywordName, args).invoke(delegate, args)
					target = HighlightElement.success(to)
					inspector.logPassed(target, keywordName, args)
				}
				catch (StepFailedException e) {
					target = HighlightElement.exception(to)
					inspector.logFailure(target, keywordName, args, e)
					throw e
				}
				catch (StepErrorException e) {
					target = HighlightElement.exception(to)
					inspector.logError(target, keywordName, args, e)
					throw e
				}
				catch (Exception e) {
					target = HighlightElement.exception(to)
					inspector.logGeneral(target, keywordName, args, e)
					throw e
				}
			} else {
				// execute the built-in keyword's method body without tracing
				result = delegate.metaClass.getMetaMethod(keywordName, args).invoke(delegate, args)
			}
			return result
		}
	}

	/**
	 * Check if the keyword is to be vaccinated or not
	 *
	 * @param name a String as name of keyword
	 * @param args arguments to the keyword when called, not checked
	 * @return true if the name is found in the vaccinatedKeywords, otherwise false
	 */
	private static final boolean isVaccinated(String keywordName, Object args) {
		return (keywordName in vaccinatedKeywords)
	}

	/**
	 * Check if the keyword is to be traced or not.
	 * If the args is an array with 1 or more elements, and args[0] is an instance of TestObject,
	 * then its internal detail info will be recorded in a GlobalVarialbe for tracing.
	 * 
	 * Remember, there is some built-in keywords with ZERO arguments; e.g., WeUI.closeBrowser()
	 *
	 * @param name a String as name of keyword, not checked
	 * @param args an array of arguments to the keyword call
	 * @return true if the args[0] is instance of TestObject; otherwise false
	 */
	private static final boolean isToBeTraced(String keywordName, Object args) {
		Class clazz = args.getClass()
		//println "keywordName:${keywordName},args:${args.toString()}," +
		//		"args.getClass():${args.getClass()}"
		if (clazz.isArray()) {
			Object[] objects = (Object[])args
			//println "objects.length:${objects.length}"
			if (objects.length > 0) {
				//println "args[0] instanceof TestObject:${args[0] instanceof TestObject}"
				return (args[0] instanceof TestObject)
			} else
				return false
		} else
			return false
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
	 * The vaccinated built-in keywords will record the detail information 
	 * of how each keyword acted. You can trace back what was carried on 
	 * before a StepFailureException was thrown.
	 * 
	 * You can print the content of the GlobalVariable by the following 
	 * Groovy code as test script.
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



	// ------------------------------------------------------------------------

	/**
	 * <p>Inspector encapsulates data about execution of vaccinated built-in keywords.
	 * The data includes:
	 * </p>
	 * <p><ol>
	 * <li>which web element was targeted by the last call</li>
	 * <li>which web element was targeted by the current call</li>
	 * <li>Exception thrown by the current call if any</li>
	 * </ol></p>
	 * 
	 * <p>This class implements  methods to operate the contained data.</p>
	 */
	static final class Inspector {

		/**
		 * constructor
		 */
		Inspector() {
			if (!GlobalVariable.metaClass.hasProperty(GlobalVariable, GVNAME)) {
				HighlightElement.addGlobalVariable(GVNAME, initValue())
			}
		}

		/**
		 * <p>
		 * When any of influenced keywords has been called before, shift the trace
		 * from the current slot to the previous slot.
		 * </p>
		 */
		void record(List<WebElement> currentTarget, String keywordName, Object args) {
			GlobalVariable[GVNAME]['lastWebElements'] =
					GlobalVariable[GVNAME]['currentTestStep']['webElements']
			//
			GlobalVariable[GVNAME]['currentTestStep'] =
					testStep(currentTarget, keywordName, args)
		}

		/**
		 * @param currentTarget
		 * @param name
		 * @param args
		 * @return
		 */
		def logPassed(List<WebElement> currentTarget, String keywordName, Object args) {
			record(currentTarget, keywordName, args)
			GlobalVariable[GVNAME]['exceptions']['Failure'] = null
			GlobalVariable[GVNAME]['exceptions']['Error']   = null
			GlobalVariable[GVNAME]['exceptions']['General'] = null
		}

		/**
		 * @param name
		 * @param args
		 */
		def logFailure(List<WebElement> currentTarget, String keywordName, Object args, Exception e) {
			record(currentTarget, keywordName, args)
			GlobalVariable[GVNAME]['exceptions']['Failure'] = e
			GlobalVariable[GVNAME]['exceptions']['Error']   = null
			GlobalVariable[GVNAME]['exceptions']['General'] = null
		}

		/**
		 *
		 * @param name
		 * @param args
		 * @return
		 */
		def logError(List<WebElement> currentTarget, String keywordName, Object args, Exception e) {
			record(currentTarget, keywordName, args)
			GlobalVariable[GVNAME]['exceptions']['Error']   = null
			GlobalVariable[GVNAME]['exceptions']['General'] = e
			GlobalVariable[GVNAME]['exceptions']['Error']   = null
		}

		/**
		 * @param name
		 * @param args
		 */
		def logGeneral(List<WebElement> currentTarget, String keywordName, Object args, Exception e) {
			record(currentTarget, keywordName, args)
			GlobalVariable[GVNAME]['exceptions']['Failure'] = null
			GlobalVariable[GVNAME]['exceptions']['Error']   = null
			GlobalVariable[GVNAME]['exceptions']['General'] = e
		}

		// ------------------------ data models ----------------------

		/**
		 * @return a Map object as the initial value for 
		 * the GlobalVariable.${GVNAME}
		 */
		private static final Map initValue() {
			return [
				'currentTestStep': [
					'webElements': null,
					'keywordName': null,
					'testObject' : null,
					'testObjectString' : null,
					'inputParams': null
				],
				'lastWebElements': null,
				'exceptions' : [
					'Failure'  : [],
					'Error'    : [],
					'General'  : []]
			]
		}

		/**
		 * 
		 */
		private static final Map testStep(List<WebElement> currentTarget, String keywordName, Object args) {
			if (!(args[0] instanceof TestObject)) {
				throw new RuntimeException(
				"args[0] is supposed be an instance of TestObject")
			}
			TestObject testObject = (TestObject)args[0]

			Map testStep = [
				'webElements': currentTarget,
				'keywordName': keywordName,
				'testObject' : testObject,
				'testObjectString' : simplifyTestObjectString(testObject),
				'inputParams': toListOfString(args)
			]
			return testStep
		}

		// ------------------------ helper methods ----------------------------

		/**
		 *
		 * @param testObject
		 * @return
		 */
		static String simplifyTestObjectString(TestObject testObject) {
			return testObject.toString().replaceFirst("^TestObject - \\'(.*?)\\'\$", '$1')
		}

		/**
		 *
		 * @param args
		 * @return
		 */
		static List<String> toListOfString(Object args) {
			return args.collect{it}.withIndex().findResults{ it, id -> (id > 0) ? it: null }
		}
	}
}
