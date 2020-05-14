/**
 * APIAccessUtils.java - is a helper class providing methods related to the main Oxygen API as used within the AskMoreXtension developed at the Digital Academy
 *  of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.text.BadLocationException;

import ro.sync.document.DocumentPositionedInfo;
import ro.sync.ecss.extensions.api.AuthorDocumentController;
import ro.sync.ecss.extensions.api.ContentInterval;
import ro.sync.ecss.extensions.api.access.AuthorEditorAccess;
import ro.sync.ecss.extensions.api.node.AuthorDocumentFragment;
import ro.sync.ecss.extensions.api.node.AuthorNode;

public class APIAccessUtils {

	/**
	 * Returns the name of the file opened in the AuthorMode
	 * @param systemID the systemID of the AuthorDocumentNode
	 * @throws MalformedURLException
	 * @throws URISyntaxException
	 * @deprecated use new File(systemID).getName()
	 */
	public static String getFileName(String systemID) throws MalformedURLException, URISyntaxException {
		File file = new File(new URL(systemID).toURI());
		return file.getName();
	}
	
	/**
	 * Returns a serialized form of the current selection in the AuthorMode
	 * @param editorAccess the current AuthorEditorAccess
	 * @param documentController the current AuthorDocumentController
	 * @throws BadLocationException
	 */
	public static String getSelection(AuthorEditorAccess editorAccess, AuthorDocumentController documentController) throws BadLocationException {
		// get position of selection
		int selectionStart = editorAccess.getSelectionStart();
		int selectionEnd = editorAccess.getSelectionEnd() - 1;
		
		// create a fragment and return its serialization 
		AuthorDocumentFragment fragment = documentController.createDocumentFragment(selectionStart, selectionEnd);
		return documentController.serializeFragmentToXML(fragment);
	}
	
	/**
	 * Checks if the specified ContentInterval contains the specified target node or not
	 * @param targetNode an AuthorNode
	 * @param interval a ContentInterval
	 * @return <code>true</code> if the ContentInterval contains the target node
	 */
	public static boolean containsNode(ContentInterval interval, AuthorNode targetNode) {
		if (interval.getStartOffset() > targetNode.getEndOffset())
			return false;
		if (interval.getEndOffset() < targetNode.getStartOffset())
			return false;
		return true;
	}
	
	/**
	 * Returns an array of all supported severity names
	 */
	public static String[] getSeverityNames() {
		return new String[] {"Info", "Warning", "Error", "Fatal"};
	}

	/**
	 * Returns the int representation of the specified severity
	 * @param severityName the name of a severity
	 *  <br />The allowed values are: <code>Info</code>, <code>Warning</code>, <code>Error</code> and <code>Fatal</code>
	 * @throws IllegalArgumentException if <code>severityName</code> is not one of the mentioned values 
	 */
	public static int getSeverity(String severityName) throws IllegalArgumentException {
		switch (severityName) {
			case "Info":
				return DocumentPositionedInfo.SEVERITY_INFO;
			case "Warning":
				return DocumentPositionedInfo.SEVERITY_WARN;
			case "Error":
				return DocumentPositionedInfo.SEVERITY_ERROR;
			case "Fatal":
				return DocumentPositionedInfo.SEVERITY_FATAL;
			default:
				throw new IllegalArgumentException("Unknown severity "+severityName);
		}
	}
	
}
