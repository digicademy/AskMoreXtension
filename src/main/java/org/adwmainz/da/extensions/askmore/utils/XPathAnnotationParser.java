/**
 * XPathAnnotationParser.java - is a helper class that provides methods for parsing Strings containing XPathAnnotations as used within the AskMoreXtension
 *  developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.3.0
 */
package org.adwmainz.da.extensions.askmore.utils;

import java.util.HashSet;
import java.util.List;

import ro.sync.ecss.extensions.api.AuthorDocumentController;
import ro.sync.ecss.extensions.api.AuthorOperationException;
import ro.sync.ecss.extensions.api.node.AuthorNode;

public abstract class XPathAnnotationParser {

	// relevant regex pattern
	protected static final String XPATH_ANNOTATION_PATTERN = "\\$\\$XPATH" + "\\(" + "(.*?)" /* XPATH */ + "\\)" + "\\$\\$";
	
	/**
	 * Replaces all XPathAnnotations in a given String with the respective XPath results relative to a given context node
	 * @param documentController the current AuthorDocumentController
	 * @param annotatedText a String that may contain annotations like <code>$$XPATH(...)$$</code> where <code>...</code> may be any XPath expression
	 * @param contextNode the context node to be used to evaluate the encoded XPath expression(s) 
	 * @throws AuthorOperationException if the XPath result cannot be serialized
	 */
	public static String replaceAnnotations(String annotatedText, AuthorNode contextNode, AuthorDocumentController documentController)
			throws AuthorOperationException {
		for (String annotation: new HashSet<>(findAnnotations(annotatedText))) {
			String xPathExpression = RegexUtils.getFirstMatch(annotation, XPATH_ANNOTATION_PATTERN, 1);
			List<String> xPathResults = APIAccessUtils.getSerializedXPathResults(documentController, contextNode, xPathExpression);
			if (xPathResults.isEmpty())
				annotatedText = annotatedText.replace(annotation, "");
			else
				annotatedText = annotatedText.replace(annotation, xPathResults.get(0));
		}
		return annotatedText;
	}
	
	/**
	 * Returns a List of XPathAnnotations from an annotated String
	 * @param annotatedText a String that may contain annotations like <code>$$XPATH(...)$$</code> where <code>...</code> may be any XPath expression
	 */
	public static List<String> findAnnotations(String annotatedText) {
		return RegexUtils.getMatches(annotatedText, XPATH_ANNOTATION_PATTERN);
	}
	
}
