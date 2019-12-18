/**
 * SurroundWithAnnotatedFragmentOperation.java - is an extension of a ro.sync.ecss.extensions.commons.operations.SurroundWithFragmentOperation which adds a
 *  custom operation to the Oxygen XML Editor that lets a user surround an XML fragment with other fragments that may be adapted dynamically with an input dialog.
 *  It is one of the main classes within the AskMoreXtension developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.operations;

import java.util.List;

import javax.swing.text.BadLocationException;

import org.adwmainz.da.extensions.askmore.exceptions.InputDialogClosedException;
import org.adwmainz.da.extensions.askmore.models.HashedArgumentsMap;
import org.adwmainz.da.extensions.askmore.utils.ArgumentDescriptorUtils;
import org.adwmainz.da.extensions.askmore.utils.ArgumentParser;
import org.adwmainz.da.extensions.askmore.utils.AskMoreAnnotationParser;
import org.adwmainz.da.extensions.askmore.utils.InputDialogUtils;
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
	protected List<String> argumentNames;
	
	// constructor
	/**
	 * Creates a new SurroundWithAnnotatedFragmentOperation
	 */
	public SurroundWithAnnotatedFragmentOperation() {
		super();
		
		// get arguments from super class
		ArgumentDescriptor[] basicArguments = super.getArguments();
		arguments = basicArguments;
		
		// set argument names
		argumentNames = ArgumentDescriptorUtils.getArgumentNames(arguments);
		
		// add description of AskMoreAnnotations to ARGUMENT_FRAGMENT
		String argName = ArgumentDescriptorUtils.ARGUMENT_FRAGMENT;
		int argIdx = argumentNames.indexOf(argName);
		arguments[argIdx] = new ArgumentDescriptor(argName,
				basicArguments[argIdx].getType(),
				basicArguments[argIdx].getDescription()+"\n"+AskMoreAnnotationParser.getDescription()
		);
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
		HashedArgumentsMap parsedArgs = new HashedArgumentsMap(args, argumentNames);

		// configure ARGUMENT_FRAGMENT with an input dialog
		String parsedFragment;
		try {
			parsedFragment = InputDialogUtils.replaceAnnotationsWithUserInput(ArgumentParser.getValidString(args, ArgumentDescriptorUtils.ARGUMENT_FRAGMENT));
		} catch (InputDialogClosedException e) {
			// abort action if user closes the dialog
			return;
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
				if (ArgumentParser.getValidBoolean(args, ArgumentDescriptorUtils.ARGUMENT_SCHEMA_AWARE))
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
			parsedArgs.put(ArgumentDescriptorUtils.ARGUMENT_FRAGMENT, parsedFragment);
			super.doOperation(authorAccess, parsedArgs);
		}
	}

	@Override
	public ArgumentDescriptor[] getArguments() {
		return arguments;
	}

}
