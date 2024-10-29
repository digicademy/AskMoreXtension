/**
 * AnnotatedXQueryUpdateOperation.java - is an extension of a ro.sync.ecss.extensions.commons.operations.XQueryUpdateOperation which adds a custom operation to
 *  the Oxygen XML Editor that lets a user execute an XQuery script that may be adapted dynamically with an input dialog. It is one of the main classes of the 
 *  AskMoreXtension developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.3.0
 */
package org.adwmainz.da.extensions.askmore.operations;

import org.adwmainz.da.extensions.askmore.exceptions.InputDialogClosedException;
import org.adwmainz.da.extensions.askmore.models.HashedArgumentsMap;
import org.adwmainz.da.extensions.askmore.utils.ArgumentDescriptorUtils;
import org.adwmainz.da.extensions.askmore.utils.ArgumentParser;
import org.adwmainz.da.extensions.askmore.utils.AskMoreArgumentProvider;

import ro.sync.ecss.extensions.api.ArgumentDescriptor;
import ro.sync.ecss.extensions.api.ArgumentsMap;
import ro.sync.ecss.extensions.api.AuthorAccess;
import ro.sync.ecss.extensions.api.AuthorOperationException;
import ro.sync.ecss.extensions.commons.operations.XQueryUpdateOperation;

public class AnnotatedXQueryUpdateOperation extends XQueryUpdateOperation {

	// fields
	protected ArgumentDescriptor[] arguments;

	// constructor
	/**
	 * Creates a new AnnotatedXQueryUpdateOperation
	 */
	public AnnotatedXQueryUpdateOperation() {
		super();
		
		// derive arguments from arguments of super class by adding descriptions to ARGUMENT_SCRIPT and ARGUMENT_EXTERNAL_PARAMS
		arguments = ArgumentDescriptorUtils.addAskMoreAnnotationDescriptions(super.getArguments(), AskMoreArgumentProvider.ARGUMENT_SCRIPT, AskMoreArgumentProvider.ARGUMENT_EXTERNAL_PARAMS);
	}

	// overridden methods
	@Override
	public String getDescription() {
		return "Extends the default XQueryUpdateOperation by adding the possibility to adapt the script dynamically with an user input dialog.";
	}

	@Override
	public void doOperation(AuthorAccess authorAccess, ArgumentsMap args) throws IllegalArgumentException, AuthorOperationException {
		// get all params using the argument descriptors
		HashedArgumentsMap parsedArgs = new HashedArgumentsMap(args, ArgumentDescriptorUtils.getArgumentNames(arguments));
		
		try {
			// parse annotated arguments
			ArgumentParser.replaceAnnotationsWithUserInput(parsedArgs, AskMoreArgumentProvider.ARGUMENT_SCRIPT);
			ArgumentParser.replaceAnnotationsWithUserInput(parsedArgs, AskMoreArgumentProvider.ARGUMENT_EXTERNAL_PARAMS, "");
			
			// invoke main operation from super class
			super.doOperation(authorAccess, parsedArgs);
		} catch (InputDialogClosedException e) {
			// abort action if user closes the dialog
			throw new IllegalArgumentException(AskMoreArgumentProvider.getClosedDialogMessage());
		}
	}

	@Override
	public ArgumentDescriptor[] getArguments() {
		return arguments;
	}

}
