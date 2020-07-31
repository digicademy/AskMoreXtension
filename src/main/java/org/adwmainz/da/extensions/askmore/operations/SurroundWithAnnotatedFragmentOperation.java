/**
 * SurroundWithAnnotatedFragmentOperation.java - is an extension of a ro.sync.ecss.extensions.commons.operations.SurroundWithFragmentOperation which adds a
 *  custom operation to the Oxygen XML Editor that lets a user surround an XML fragment with other fragments that may be adapted dynamically with an input dialog.
 *  It is one of the main classes within the AskMoreXtension developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.operations;

import javax.swing.text.BadLocationException;

import org.adwmainz.da.extensions.askmore.exceptions.InputDialogClosedException;
import org.adwmainz.da.extensions.askmore.models.EditableArgumentDescriptor;
import org.adwmainz.da.extensions.askmore.models.HashedArgumentsMap;
import org.adwmainz.da.extensions.askmore.utils.ArgumentDescriptorUtils;
import org.adwmainz.da.extensions.askmore.utils.ArgumentParser;
import org.adwmainz.da.extensions.askmore.utils.AskMoreAnnotationParser;
import org.adwmainz.da.extensions.askmore.utils.AskMoreArgumentProvider;
import org.adwmainz.da.extensions.askmore.utils.APIAccessUtils;

import ro.sync.ecss.extensions.api.ArgumentDescriptor;
import ro.sync.ecss.extensions.api.ArgumentsMap;
import ro.sync.ecss.extensions.api.AuthorAccess;
import ro.sync.ecss.extensions.api.AuthorDocumentController;
import ro.sync.ecss.extensions.api.AuthorOperationException;
import ro.sync.ecss.extensions.api.access.AuthorEditorAccess;
import ro.sync.ecss.extensions.commons.operations.SurroundWithFragmentOperation;

public class SurroundWithAnnotatedFragmentOperation extends SurroundWithFragmentOperation {

	// static field
	protected static final String destinationAnnotation = "$$DESTINATION$$";

	// fields
	protected ArgumentDescriptor[] arguments;
	
	// constructor
	/**
	 * Creates a new SurroundWithAnnotatedFragmentOperation
	 */
	public SurroundWithAnnotatedFragmentOperation() {
		super();

		// derive arguments from arguments of super class
		ArgumentDescriptor[] basicArguments = super.getArguments();
		arguments = new ArgumentDescriptor[basicArguments.length];
		for (int i=0; i<basicArguments.length; ++i) {
			// get basic argument
			ArgumentDescriptor basicArgument = basicArguments[i];
			
			if (basicArgument.getName().equals(AskMoreArgumentProvider.ARGUMENT_FRAGMENT)) {
				// add description of how to use AskMoreAnnotations to ARGUMENT_FRAGMENT
				EditableArgumentDescriptor derivedArgument = EditableArgumentDescriptor.copyOf(basicArgument);
				derivedArgument.setDescription("The fragment to surround with. The text to be surrounded will become the first leaf per default but you"
						+ " may specify a different position using the annotation "+destinationAnnotation+".\n"+AskMoreAnnotationParser.getDescription());
				arguments[i] = derivedArgument;
			} else {
				// use basic argument
				arguments[i] = basicArgument;
			}
		}
	}

	// overridden methods
	@Override
	public String getDescription() {
		return "Extends the default SurroundWithFragmentOperation by adding the possibility to adapt the fragment dynamically with an user input dialog and "
				+ "to specify the destination of the original selection.";
	}

	@Override
	public void doOperation(AuthorAccess authorAccess, ArgumentsMap args) throws IllegalArgumentException, AuthorOperationException {
		// get all params using the argument descriptors
		HashedArgumentsMap parsedArgs = new HashedArgumentsMap(args, ArgumentDescriptorUtils.getArgumentNames(arguments));
		
		// configure ARGUMENT_FRAGMENT with an input dialog
		String parsedFragment;
		try {
			parsedFragment = ArgumentParser.getValidStringWithUserInput(args, AskMoreArgumentProvider.ARGUMENT_FRAGMENT);
		} catch (InputDialogClosedException e) {
			// abort action if user closes the dialog
			throw new IllegalArgumentException(AskMoreArgumentProvider.getClosedDialogMessage());
		}
		
		// get document controller and editor access
		AuthorDocumentController documentController = authorAccess.getDocumentController();
		AuthorEditorAccess editorAccess = authorAccess.getEditorAccess();
		
		// check if a custom destination should be used
		if (parsedFragment.contains(destinationAnnotation)) {
			// begin a compound edit
			documentController.beginCompoundEdit();
			try {
				// add selection to fragment
				String selection = APIAccessUtils.getSelection(editorAccess, documentController);
				parsedFragment = parsedFragment.replace(destinationAnnotation, selection);
				
				// delete selection but remember offset
				int startOffset = editorAccess.getSelectionStart();
				editorAccess.deleteSelection();
				
				// insert fragment
				if (ArgumentParser.getValidBoolean(args, AskMoreArgumentProvider.ARGUMENT_SCHEMA_AWARE))
					documentController.insertXMLFragment(parsedFragment, startOffset);
				else
					documentController.insertXMLFragmentSchemaAware(parsedFragment, startOffset);
			} catch (BadLocationException e) {
				// throw exception if the selection is invalid
				throw new AuthorOperationException(e.getMessage());
			} finally {
				// end the compound edit
				documentController.endCompoundEdit();
			}
		} else {
			// invoke main operation from super class
			parsedArgs.put(AskMoreArgumentProvider.ARGUMENT_FRAGMENT, parsedFragment);
			super.doOperation(authorAccess, parsedArgs);
		}
	}

	@Override
	public ArgumentDescriptor[] getArguments() {
		return arguments;
	}

}
