/**
 * AnnotatedCommandLineOperation.java - is an extension of a ro.sync.ecss.extensions.commons.operations.ExecuteCommandLineOperation which adds a custom
 *  operation to the Oxygen XML Editor that lets a user execute an XQuery script that may be adapted dynamically with an input dialog. It is one of 
 *  the main classes of the AskMoreXtension developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.1.0
 */
package org.adwmainz.da.extensions.askmore.operations;

import org.adwmainz.da.extensions.askmore.exceptions.InputDialogClosedException;
import org.adwmainz.da.extensions.askmore.models.EditableArgumentDescriptor;
import org.adwmainz.da.extensions.askmore.models.HashedArgumentsMap;
import org.adwmainz.da.extensions.askmore.utils.ArgumentDescriptorUtils;
import org.adwmainz.da.extensions.askmore.utils.ArgumentParser;
import org.adwmainz.da.extensions.askmore.utils.AskMoreAnnotationParser;
import org.adwmainz.da.extensions.askmore.utils.AskMoreArgumentProvider;
import org.adwmainz.da.extensions.askmore.utils.InputDialogUtils;

import ro.sync.ecss.extensions.api.ArgumentDescriptor;
import ro.sync.ecss.extensions.api.ArgumentsMap;
import ro.sync.ecss.extensions.api.AuthorAccess;
import ro.sync.ecss.extensions.api.AuthorOperationException;
import ro.sync.ecss.extensions.commons.operations.ExecuteCommandLineOperation;

public class AnnotatedCommandLineOperation extends ExecuteCommandLineOperation {

	// fields
	protected ArgumentDescriptor[] arguments;

	// constructor
	/**
	 * Creates a new AnnotatedCommandLineOperation
	 */
	public AnnotatedCommandLineOperation() {
		super();
		
		// derive arguments from arguments of super class
		ArgumentDescriptor[] basicArguments = super.getArguments();
		arguments = new ArgumentDescriptor[basicArguments.length];
		for (int i=0; i<basicArguments.length; ++i) {
			// get basic argument
			ArgumentDescriptor basicArgument = basicArguments[i];
			EditableArgumentDescriptor derivedArgument = EditableArgumentDescriptor.copyOf(basicArgument);
			
			// add description of how to use AskMoreAnnotations to ARGUMENT_COMMAND_LINE
			if (basicArgument.getName().equals(AskMoreArgumentProvider.ARGUMENT_COMMAND_LINE))
				derivedArgument.setDescription(basicArgument.getDescription()+"\n"+AskMoreAnnotationParser.getDescription());
			
			// set argument
			arguments[i] = derivedArgument;
		}
	}

	// overridden methods
	@Override
	public String getDescription() {
		return "Extends the default ExecuteCommandLineOperation by adding the possibility to adapt the script dynamically with an user input dialog.";
	}

	@Override
	public void doOperation(AuthorAccess authorAccess, ArgumentsMap args) throws IllegalArgumentException, AuthorOperationException {
		// get all params using the argument descriptors
		HashedArgumentsMap parsedArgs = new HashedArgumentsMap(args, ArgumentDescriptorUtils.getArgumentNames(arguments));
		
		try {
			// configure ARGUMENT_SCRIPT with an input dialog
			String parsedCommand = InputDialogUtils.replaceAnnotationsWithUserInput(ArgumentParser.getValidString(args, AskMoreArgumentProvider.ARGUMENT_COMMAND_LINE));
			parsedArgs.put(AskMoreArgumentProvider.ARGUMENT_COMMAND_LINE, parsedCommand);
			
			// invoke main operation from super class
			super.doOperation(authorAccess, parsedArgs);
		} catch (InputDialogClosedException e) {
			// abort action if user closes the dialog
			return;
		}
		
	}

	@Override
	public ArgumentDescriptor[] getArguments() {
		return arguments;
	}

}
