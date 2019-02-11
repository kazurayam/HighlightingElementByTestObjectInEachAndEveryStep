
/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */

import java.lang.Class

import com.kms.katalon.core.model.FailureHandling

import com.kms.katalon.core.testobject.TestObject

import java.lang.String


def static "com.kazurayam.junit4ks.JUnitCustomKeywords.runWithJUnitRunner"(
    	Class junitRunnerClass	
     , 	FailureHandling flowControl	) {
    (new com.kazurayam.junit4ks.JUnitCustomKeywords()).runWithJUnitRunner(
        	junitRunnerClass
         , 	flowControl)
}

def static "com.kazurayam.junit4ks.JUnitCustomKeywords.runWithJUnitRunner"(
    	Class junitRunnerClass	) {
    (new com.kazurayam.junit4ks.JUnitCustomKeywords()).runWithJUnitRunner(
        	junitRunnerClass)
}

def static "com.kazurayam.ksbackyard.HighlightElement.on"(
    	TestObject testObject	) {
    (new com.kazurayam.ksbackyard.HighlightElement()).on(
        	testObject)
}

def static "com.kazurayam.ksbackyard.HighlightElement.pandemic"() {
    (new com.kazurayam.ksbackyard.HighlightElement()).pandemic()
}

def static "com.kazurayam.ksbackyard.HighlightElement.quarantine"() {
    (new com.kazurayam.ksbackyard.HighlightElement()).quarantine()
}

def static "com.kazurayam.ksbackyard.HighlightElement.addGlobalVariable"(
    	String name	
     , 	Object value	) {
    (new com.kazurayam.ksbackyard.HighlightElement()).addGlobalVariable(
        	name
         , 	value)
}
