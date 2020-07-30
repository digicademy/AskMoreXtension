package org.adwmainz.da.extensions.askmore.factories;

import org.adwmainz.da.extensions.askmore.utils.XPathAnnotationParser;

import ro.sync.document.DocumentPositionedInfo;
/**
 * PositionedInfoFactory.java - is a factory class that creates an ro.sync.ecss.component.validation.AuthorDocumentPositionedInfo as used within the AskMoreXtension
 *  developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.3.0
 */
import ro.sync.ecss.component.validation.AuthorDocumentPositionedInfo;
import ro.sync.ecss.dom.wrappers.AuthorElementDomWrapper;
import ro.sync.ecss.extensions.api.AuthorDocumentController;
import ro.sync.ecss.extensions.api.AuthorOperationException;
import ro.sync.ecss.extensions.api.node.AuthorNode;

public class PositionedInfoFactory {
	
	/**
	 * Creates a DocumentPositionedInfo by parsing a message that uses XPathAnnotations
	 * @param xPathResult the XPath result the created DocumentPositionedInfo should be assocciated with
	 * @param annotatedMessage a String that may contain annotations like <code>$$XPATH(...)$$</code> where <code>...</code> may be any XPath expression
	 * @param documentController the current AuthorDocumentController
	 * @param severity the severity of the DocumentPositionedInfo
	 * @param systemID the current systemID
	 * @throws AuthorOperationException if the XPath result cannot be serialized
	 */
	public static DocumentPositionedInfo createWithAnnotatedMessage(Object xPathResult, String annotatedMessage, AuthorDocumentController documentController, int severity, String systemID)
			throws AuthorOperationException {
		if (xPathResult instanceof AuthorElementDomWrapper) {
			AuthorNode targetNode = ((AuthorElementDomWrapper) xPathResult).getWrappedAuthorNode();
			String currentMessage = XPathAnnotationParser.replaceAnnotations(annotatedMessage, targetNode, documentController);
			return new AuthorDocumentPositionedInfo(severity, currentMessage, systemID, targetNode);
		}
			
		else {
			String currentMessage = annotatedMessage + " " + xPathResult.toString();
			return new DocumentPositionedInfo(severity, currentMessage, systemID);
		}
	}
}
