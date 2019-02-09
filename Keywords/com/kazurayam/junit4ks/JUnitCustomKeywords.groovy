package com.kazurayam.junit4ks

import java.text.MessageFormat

import org.junit.runner.Computer
import org.junit.runner.JUnitCore
import org.junit.runner.Result
import org.junit.runner.notification.Failure

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.keyword.internal.KeywordMain
import com.kms.katalon.core.logging.KeywordLogger
import com.kms.katalon.core.model.FailureHandling

/**
 * A test case can call this Custom Keyword to execute a class annotated with JUnit4's &#64;Test.
 * In other words, you can execute a JUnit-based test within Katalon Studio.
 *
 *
 *
 * I read and learned https://github.com/katalon-studio/katalon-studio-testing-framework/blob/master/Include/scripts/groovy/com/kms/katalon/core/cucumber/keyword/CucumberBuiltinKeywords.groovy
 * This class is almost the same as com.kms.katalon.core.cucumber.keyword.CucumberBuiltingKeywords.
 *
 * @author kazurayam
 *
 */
public class JUnitCustomKeywords {

	private static final KeywordLogger logger = KeywordLogger.getInstance(JUnitCustomKeywords.class)

	/**
	 * Example:
	 *
	 * You can run the following test case `Test Cases/test/CalculatorTestRunner` in Katalon Studio
	 * just as you run a usual test case by clicking the Run button.
	 *
	 * Test Case:
	 * <PRE>
	 * import junittutorial.CalculatorTest
	 * CustomKeywords.'com.kazurayam.junit4ks.JUnitCustomKeywords.runWithJUnitRunner'(CalculatorTest.class)
	 * </PRE>
	 *
	 * The following is a JUnit test (localated at Include/scripts/groovy/junittutorial/CalculatorTest.groovy)
	 * executed by the above test case:
	 * <PRE>
	 * package junittutorial
	 *
	 * import static org.hamcrest.CoreMatchers.*
	 * import static org.junit.Assert.*
	 *
	 * import org.junit.Test
	 * import org.junit.runner.RunWith
	 * import org.junit.runners.JUnit4
	 *
	 * @RunWith(JUnit4.class)
	 * class CalculatorTest {
	 * 	@Test
	 * 	void testMultiply() {
	 * 		int expected = 21
	 * 		int actual = Calculator.multiply(7, 3)
	 * 		assertThat(actual, is(expected))
	 * 	}
	 *
	 * 	@Test
	 * 	void testDivide_wrongType() {
	 * 		double expected = 1.5f
	 * 		double actual = Calculator.divide(3, 2)
	 * 		assertThat(actual, is(not(expected)))
	 * 	}
	 *
	 * 	@Test
	 * 	void testDivide() {
	 * 		int expected = 1
	 * 		int actual = Calculator.divide(3, 2)
	 * 		assertThat(actual, is(expected))
	 * 	}
	 * }
	 * </PRE>
	 *
	 * Finally the class to be tested is located at `Keywords/junittutorial/Calculator.groovy`:
	 * <PRE>
	 * package junittutorial
	 *
	 * import com.kms.katalon.core.annotation.Keyword
	 *
	 * class Calculator {
	 *
	 * 	   @Keyword
	 * 	   static int add(int a, int b) {
	 * 		   return a + b;
	 * 	   }
	 *
	 * 	   @Keyword
	 * 	   static int subtract(int a, int b) {
	 * 		   return a - b;
	 * 	   }
	 *
	 * 	   @Keyword
	 * 	   static int multiply(int x, int y) {
	 * 		   return x * y
	 * 	   }
	 *
	 * 	   @Keyword
	 * 	   static int divide(int x, int y) {
	 * 		   return x / y
	 * 	   }
	 *
	 * 	   @Keyword
	 * 	   static int power(int a, int b){
	 * 		   int answer =a;
	 * 		   for (int x = 2; x <= b; x++){
	 * 		       answer *= a;
	 * 		   }
	 * 		   return answer;
	 *     }
	 * }
	 * </PRE>
	 *
	 * Please note that in the targeted Custom Keyword (e.g, Keywords/com/example/MiniScreenshotDriver.groovy) and
	 * JUnit test (e.g, Include/scripts/groovy/com/example/MiniScreenshotDriverTest.groovy), you can call
	 * any Katalon Studio API including org.openqa.selenium.WebDriver. Your JUnit invoked within Katalon Studio now
	 * can interact with your Application Under Test (web site) through WebDriver. This is what I wanted to achieve.
	 *
	 * @param junitRunnerClass
	 * @param flowControl
	 * @return
	 */
	@Keyword
	public static JUnitRunnerResult runWithJUnitRunner(Class junitRunnerClass, FailureHandling flowControl) {
		return KeywordMain.runKeyword({
			JUnitCore core = new JUnitCore()
			Computer computer = new Computer()
			Result result = core.run(computer, junitRunnerClass)
			boolean runSuccess = result.wasSuccessful()
			JUnitRunnerResultImpl junitResult = new JUnitRunnerResultImpl(
					runSuccess ? 'passed' : 'failed', '', result)
			if (runSuccess) {
				logger.logPassed(MessageFormat.format("RunWith ''{0}'' was passed", junitRunnerClass.getName()))
			} else {
				List failuresDescriptions = []
				for (Failure failure: result.getFailures()) {
					failuresDescriptions.add("\n>>>>\n" + failure.getTrace() + "<<<<\n")
				}
				KeywordMain.stepFailed(
						MessageFormat.format("These following reason:\n {0}", failuresDescriptions),
						flowControl)
			}
			return junitResult
		}, flowControl, "Keyword runWithJUnitRunner failed")
	}

	/**
	 * Run the given <code>junitRunnerClass</code> that is annotated with
	 * {@link JUnit} runner by invoke JUnit runner.
	 *
	 * @param junitRunnerClass a class that is annotated with {@link JUnit} runner.
	 *
	 * <p>
	 * Example of <code>junitRunnerClass</code>:
	 * <ul>
	 * <li>
	 * <pre>
	 * import org.junit.runner.RunWith
	 * import org.junit.runners.JUnit4
	 * &#64;RunWith(JUnit4.class)
	 * public class MyJunitRunner {}
	 *
	 * </pre>
	 * </li>
	 * </ul>
	 * </p>
	 * @return
	 */
	@Keyword
	public static JUnitRunnerResult runWithJUnitRunner(Class junitRunnerClass) {
		return runWithJUnitRunner(junitRunnerClass, RunConfiguration.getDefaultFailureHandling())
	}




	/**
	 *
	 * @author urayamakazuaki
	 *
	 */
	public static interface JUnitRunnerResult {

		/**
		 * @return passed or failed
		 */
		String getStatus()

		/**
		 * Optional:
		 * @return absolute path of generated junit report
		 */
		String getReportLocation()

		/**
		 * Optional: Used when the keyword is {@link JUnitCustomKeywords#runWithJUnitRunner(Class)}
		 * @return an instance of JUnit Result, null if the keyword is NOT
		 * {@link JUnitBuiltinKeywords#runWithJUnitRunner(Class)}
		 */
		Result getJUnitRunnerResult()
	}


	/**
	 *
	 * @author urayamakazuaki
	 *
	 */
	public static class JUnitRunnerResultImpl implements JUnitRunnerResult {
		private String status
		private String reportLocation
		private Result result

		public JUnitRunnerResultImpl(String status, String reportLocation) {
			this(status, reportLocation, null)
		}

		public JUnitRunnerResultImpl(String status, String reportLocation, Result result) {
			this.status = status
			this.reportLocation = reportLocation
			this.result = result
		}

		@Override
		public String getStatus() {
			return status
		}

		@Override
		public String getReportLocation() {
			return reportLocation
		}

		@Override
		public Result getJUnitRunnerResult() {
			return result
		}

		@Override
		public String toString() {
			return "JUnitRunnerResultImpl{"
			+ "status: " + status
			+ ", reportLocation: " + reportLocation
			+ ", junitRunnerResult: " + (result != null ? result.toString() : "null")
			+ "}"
		}
	}
}