package groovy.runtime.metaclass.com.kms.katalon.core.webui.keyword.builtin

import com.kazurayam.ksbackyard.HighlightElement
import com.kms.katalon.core.testobject.TestObject

/**
 * Changes 
 *     com.kms.katalon.core.webui.keyword.builtin.ClickKeyword class # click(TestObject) method
 * so that it calls 
 *     com.kazurayam.ksbackyard.HighlightElement#on(TestObject)
 * before the method body.  
 * 
 * This class makes use of Groovy's Magic Package.
 * http://docs.groovy-lang.org/latest/html/documentation/core-metaprogramming.html#_magic_package
 * 
 * @author kazurayam
 *
 */
public class ClickKeywordMetaClass extends DelegatingMetaClass {

	ClickKeywordMetaClass(MetaClass metaClass) {
		super(metaClass)
	}

	ClickKeywordMetaClass(Class theClass) {
		super(theClass)
	}

	Object invokeMethod(Object object, String name, Object[] args) {
		if (name =~ /click/) {
			TestObject testObject = (TestObject)args[0]
			HighlightElement.on(testObject)
		}
		return super.invokeMethod(object, name, args);
	}
}

