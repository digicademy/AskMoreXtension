/**
 * InsertOrReplaceAnnotatedFragmentOperation.java - is an extension of a ro.sync.ecss.extensions.commons.operations.InsertOrReplaceFragmentOperation which adds a
 *  custom operation to the Oxygen XML Editor that lets a user insert an XML fragment (or replace an existing one) that may be adapted dynamically with an input
 *  dialog. It is one of the main classes within the AskMoreXtension developed at the Digital Academy of the Academy of Sciences 
 * and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
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
import ro.sync.ecss.extensions.api.access.AuthorEditorAccess;
import ro.sync.ecss.extensions.commons.operations.InsertOrReplaceFragmentOperation;

public class InsertOrReplaceAnnotatedFragmentOperation extends InsertOrReplaceFragmentOperation {

	// fields
	protected ArgumentDescriptor[] arguments;
	
	// constructors
	/**
	 * Creates a new InsertOrReplaceAnnotatedFragmentOperation
	 */
	public InsertOrReplaceAnnotatedFragmentOperation() {
		super();
		
		// derive arguments from arguments of super class
		ArgumentDescriptor[] basicArguments = super.getArguments();
		arguments = new ArgumentDescriptor[basicArguments.length + 1];
		for (int i=0; i<basicArguments.length; ++i) {
			// get basic argument
			ArgumentDescriptor basicArgument = basicArguments[i];
			EditableArgumentDescriptor derivedArgument = EditableArgumentDescriptor.copyOf(basicArgument);
			
			// add description of how to use AskMoreAnnotations to ARGUMENT_FRAGMENT
			if (basicArgument.getName().equals(AskMoreArgumentProvider.ARGUMENT_FRAGMENT))
				derivedArgument.setDescription(basicArgument.getDescription()+"\n"+AskMoreAnnotationParser.getDescription());
			
			// set argument
			arguments[i] = derivedArgument;
		}
		
		// add custom argument
		arguments[basicArguments.length] = AskMoreArgumentProvider.getRemoveSelectionArgumentDescriptor();
	}

	// overridden methods
	@Override
	public String getDescription() {
		return "Extends the default InsertOrReplaceFragmentOperation by adding the possibility to adapt the fragment dynamically with an user input dialog.";
	}

	@Override
	public void doOperation(AuthorAccess authorAccess, ArgumentsMap args) throws IllegalArgumentException, AuthorOperationException {
		// get all params using the argument descriptors
		boolean removeSelection = ArgumentParser.getValidBoolean(args, AskMoreArgumentProvider.ARGUMENT_REMOVE_SELECTION);
		HashedArgumentsMap parsedArgs = new HashedArgumentsMap(args, ArgumentDescriptorUtils.getArgumentNames(arguments));
		
		try {
			// configure ARGUMENT_FRAGMENT with an input dialog
			String parsedFragment = InputDialogUtils.replaceAnnotationsWithUserInput(ArgumentParser.getValidString(args, AskMoreArgumentProvider.ARGUMENT_FRAGMENT));
			parsedArgs.put(AskMoreArgumentProvider.ARGUMENT_FRAGMENT, parsedFragment);
			
			// invoke main operation from super class
			if (!removeSelection) {
				// prevent super class from removing all selections per default
				String insertLocation = ArgumentParser.getValidString(args, AskMoreArgumentProvider.ARGUMENT_INSERT_LOCATION, "");
				if (!insertLocation.isEmpty()) {
					AuthorEditorAccess editorAccess = authorAccess.getEditorAccess();
					editorAccess.setCaretPosition(editorAccess.getSelectionStart() + 1);
				}
			}
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
