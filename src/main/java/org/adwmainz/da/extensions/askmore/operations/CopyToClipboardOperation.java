/**
 * CopyToClipboardOperation.java - is an implementation of a ro.sync.ecss.extensions.api.AuthorOperation which adds a custom operation to the Oxygen XML editor
 *  that lets a user copy a selection or an XPath result from the Author Mode to the system clipboard. It is tone of the main classes within the AskMoreXtension
 *  developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.operations;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;

import org.adwmainz.da.extensions.askmore.utils.APIAccessUtils;
import org.adwmainz.da.extensions.askmore.utils.ArgumentDescriptorUtils;
import org.adwmainz.da.extensions.askmore.utils.ArgumentParser;

import ro.sync.ecss.dom.wrappers.AuthorNodeDomWrapper;
import ro.sync.ecss.extensions.api.ArgumentDescriptor;
import ro.sync.ecss.extensions.api.ArgumentsMap;
import ro.sync.ecss.extensions.api.AuthorAccess;
import ro.sync.ecss.extensions.api.AuthorConstants;
import ro.sync.ecss.extensions.api.AuthorDocumentController;
import ro.sync.ecss.extensions.api.AuthorOperation;
import ro.sync.ecss.extensions.api.AuthorOperationException;

public class CopyToClipboardOperation implements AuthorOperation {

	// field
	protected ArgumentDescriptor[] arguments;

	// constructor
	/**
	 * Creates a new CopyToClipboardOperation
	 */
	public CopyToClipboardOperation() {
		// load localized data
		ResourceBundle rb = ResourceBundle.getBundle("org.adwmainz.da.extensions.askmore.resources.ArgumentTextBundle");
		
		// init argument descriptions
		arguments = new ArgumentDescriptor[] {
				new ArgumentDescriptor(
						ArgumentDescriptorUtils.ARGUMENT_ELEMENT_LOCATION, 
						ArgumentDescriptor.TYPE_XPATH_EXPRESSION, 
						"An XPath expression indicating the element, attribute or text content to be copied. "
						+ "\n(Leave empty to copy the current selection within the Author Mode.)"
				),
				new ArgumentDescriptor(
						ArgumentDescriptorUtils.ARGUMENT_MESSAGE,
						ArgumentDescriptor.TYPE_STRING, 
						"The Message displayed in the results view tab for each element.",
						rb.getString("COPIED_TO_CLIPBOARD")
				),
				new ArgumentDescriptor(
						ArgumentDescriptorUtils.ARGUMENT_NOTIFY_USER, 
						ArgumentDescriptor.TYPE_CONSTANT_LIST, 
						"Specifies whether the message from the argument " + ArgumentDescriptorUtils.ARGUMENT_MESSAGE + " should be displayed or not.", 
						new String[] {
								AuthorConstants.ARG_VALUE_TRUE, 
								AuthorConstants.ARG_VALUE_FALSE},
						AuthorConstants.ARG_VALUE_FALSE
				)
		};
	}

	// overridden methods
	@Override
	public String getDescription() {
		return "Copies an XPath result or a selected fragment to the system clipboard.";
	}

	@Override
	public void doOperation(AuthorAccess authorAccess, ArgumentsMap args)
			throws IllegalArgumentException, AuthorOperationException {
		// get params
		String xPath = ArgumentParser.getValidString(args, ArgumentDescriptorUtils.ARGUMENT_ELEMENT_LOCATION, "");
		String message = ArgumentParser.getValidString(args, ArgumentDescriptorUtils.ARGUMENT_MESSAGE);
		boolean notifyUser = ArgumentParser.getValidBoolean(args, ArgumentDescriptorUtils.ARGUMENT_NOTIFY_USER);
		
		// get document controller
		AuthorDocumentController documentController = authorAccess.getDocumentController();
		
		// get selection
		String selection = "";
		if (xPath.isEmpty()) {
			// get the current selection from the Author Mode
			try {
				selection = APIAccessUtils.getSelection(authorAccess.getEditorAccess(), documentController);
			} catch (BadLocationException e) {
				// throw exception if the selection is invalid
				throw new AuthorOperationException(e.getMessage());
			}
		} else {
			// get the XPath result(s)
			for (Object obj: documentController.evaluateXPath(xPath, false, true, true)) {
				if (obj instanceof AuthorNodeDomWrapper)
					selection += ((AuthorNodeDomWrapper) obj).getTextContent();
				else {
					if (obj instanceof Number || obj instanceof String)
						selection += ""+obj;
				}
			}
		}
		
		// copy to clipboard
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(new StringSelection(selection), null);

		// notify user
		if (notifyUser)
			JOptionPane.showMessageDialog(null, message);
	}

	@Override
	public ArgumentDescriptor[] getArguments() {
		return arguments;
	}

}
