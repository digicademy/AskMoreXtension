/**
 * DisplayMessageOperation.java - is an implementation of a ro.sync.ecss.extensions.api.AuthorOperation which adds a custom operation to the Oxygen XML
 *  editor that displays a message in a simple dialog. It is one of the main classes within the AskMoreXtension developed at the Digital Academy of the
 *  Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.2.0
 */
package org.adwmainz.da.extensions.askmore.operations;

import javax.swing.JOptionPane;

import org.adwmainz.da.extensions.askmore.utils.ArgumentParser;
import org.adwmainz.da.extensions.askmore.utils.AskMoreArgumentProvider;
import org.adwmainz.da.extensions.askmore.utils.InputDialogUtils;

import ro.sync.ecss.extensions.api.ArgumentDescriptor;
import ro.sync.ecss.extensions.api.ArgumentsMap;
import ro.sync.ecss.extensions.api.AuthorAccess;
import ro.sync.ecss.extensions.api.AuthorOperation;
import ro.sync.ecss.extensions.api.AuthorOperationException;

public class DisplayMessageOperation implements AuthorOperation {

	// field
	protected ArgumentDescriptor[] arguments;

	// constructor
	/**
	 * Creates a new DisplayMessageOperation
	 */
	public DisplayMessageOperation() {
		// set argument descriptions
		arguments = new ArgumentDescriptor[] {
				AskMoreArgumentProvider.getDialogTitleArgumentDescriptor(""),
				AskMoreArgumentProvider.getMessageArgumentDescriptor(""),
				AskMoreArgumentProvider.getSeverityArgumentDescriptor()
		};
	}

	// overridden methods
	@Override
	public String getDescription() {
		return "Displays a custom message to the user.";
	}

	@Override
	public void doOperation(AuthorAccess authorAccess, ArgumentsMap args)
			throws IllegalArgumentException, AuthorOperationException {
		// get params
		String title = ArgumentParser.getValidString(args, AskMoreArgumentProvider.ARGUMENT_DIALOG_TITLE);
		String message = ArgumentParser.getValidString(args, AskMoreArgumentProvider.ARGUMENT_MESSAGE);
		int messageType = InputDialogUtils.getMessageType(ArgumentParser.getValidString(args, AskMoreArgumentProvider.ARGUMENT_SEVERITY));
		
		// display the message dialog
		JOptionPane.showMessageDialog(null, message, title, messageType);
	}

	@Override
	public ArgumentDescriptor[] getArguments() {
		return arguments;
	}

}

