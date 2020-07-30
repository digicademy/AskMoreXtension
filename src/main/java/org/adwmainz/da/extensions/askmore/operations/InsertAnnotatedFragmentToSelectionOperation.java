/**
 * InsertAnnotatedFragmentToSelectionOperation.java - is an extension of a ro.sync.ecss.extensions.commons.operations.InsertFragmentOperation which adds a custom
 *  operation to the Oxygen XML editor that lets a user insert fragments to multiple selected elements. It is one of the main classes within the AskMoreXtension
 *  developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.operations;

import java.util.List;

import javax.swing.text.BadLocationException;

import org.adwmainz.da.extensions.askmore.exceptions.InputDialogClosedException;
import org.adwmainz.da.extensions.askmore.models.EditableArgumentDescriptor;
import org.adwmainz.da.extensions.askmore.utils.APIAccessUtils;
import org.adwmainz.da.extensions.askmore.utils.ArgumentParser;
import org.adwmainz.da.extensions.askmore.utils.AskMoreAnnotationParser;
import org.adwmainz.da.extensions.askmore.utils.AskMoreArgumentProvider;
import org.adwmainz.da.extensions.askmore.utils.InputDialogUtils;

import ro.sync.ecss.extensions.api.ArgumentDescriptor;
import ro.sync.ecss.extensions.api.ArgumentsMap;
import ro.sync.ecss.extensions.api.AuthorAccess;
import ro.sync.ecss.extensions.api.AuthorDocumentController;
import ro.sync.ecss.extensions.api.AuthorOperationException;
import ro.sync.ecss.extensions.api.ContentInterval;
import ro.sync.ecss.extensions.api.access.AuthorEditorAccess;
import ro.sync.ecss.extensions.api.node.AuthorNode;
import ro.sync.ecss.extensions.commons.operations.InsertFragmentOperation;

public class InsertAnnotatedFragmentToSelectionOperation extends InsertFragmentOperation {

	// field
	protected ArgumentDescriptor[] arguments;

	// constructor
	/**
	 * Creates a new InsertAnnotatedFragmentToSelectionOperation
	 */
	public InsertAnnotatedFragmentToSelectionOperation() {
		super();

		// derive arguments from arguments of super class
		ArgumentDescriptor[] basicArguments = super.getArguments();
		arguments = new ArgumentDescriptor[basicArguments.length];
		for (int i=0; i<basicArguments.length; ++i) {
			// get basic argument
			ArgumentDescriptor basicArgument = basicArguments[i];
			EditableArgumentDescriptor derivedArgument = EditableArgumentDescriptor.copyOf(basicArgument);
			
			// add description of how to use AskMoreAnnotations to ARGUMENT_FRAGMENT
			if (basicArgument.getName().equals(AskMoreArgumentProvider.ARGUMENT_FRAGMENT))
				derivedArgument.setDescription(basicArgument.getDescription()+"\n"+AskMoreAnnotationParser.getDescription());
			
			// replace ARGUMENT_INSERT_LOCATION with ARGUMENT_INSERT_LOCATION_RESTRICTION
			else if (basicArgument.getName().equals(AskMoreArgumentProvider.ARGUMENT_INSERT_LOCATION))
				derivedArgument = new EditableArgumentDescriptor(
						AskMoreArgumentProvider.ARGUMENT_INSERT_LOCATION_RESTRICTION,
						ArgumentDescriptor.TYPE_XPATH_EXPRESSION,
						"An XPath expression that specifies which nodes from the current selection should be used."
								+ "\n(i.e. this operation inserts the fragment to all selected nodes with the name NAME and an attribute ATTR if the value of this param "
								+ "is set to //NAME[@ATTR]."
				);
			
			// set argument
			arguments[i] = derivedArgument;
		}
	}

	// overridden methods
	@Override
	public String getDescription() {
		return "Extends the InsertAnnotatedFragmentOperation of this AskMoreXtension by applying it to (a subset) of a selection made in the Author Mode.";
	}

	@Override
	public void doOperation(AuthorAccess authorAccess, ArgumentsMap args)
			throws IllegalArgumentException, AuthorOperationException {
		String parsedFragment = ArgumentParser.getValidString(args, AskMoreArgumentProvider.ARGUMENT_FRAGMENT);
		try {
			parsedFragment = InputDialogUtils.replaceAnnotationsWithUserInput(parsedFragment);
		} catch (InputDialogClosedException e) {
			// abort action if user closes the dialog
			throw new IllegalArgumentException(AskMoreArgumentProvider.getClosedDialogMessage());
		}
		
		// get other params
		String insertLocation = ArgumentParser.getValidString(args, AskMoreArgumentProvider.ARGUMENT_INSERT_LOCATION_RESTRICTION);
		String insertPosition = ArgumentParser.getValidString(args, AskMoreArgumentProvider.ARGUMENT_INSERT_POSITION);
		boolean goToNextEditablePosition = ArgumentParser.getValidBoolean(args, AskMoreArgumentProvider.ARGUMENT_GO_TO_NEXT_EDITABLE_POSITION);
		
		// get the document controller and editor access
		AuthorDocumentController documentController = authorAccess.getDocumentController();
		AuthorEditorAccess editorAccess = authorAccess.getEditorAccess();
		
		// get all possible insert locations
		AuthorNode[] targetNodes = documentController.findNodesByXPath(insertLocation, false/*include text nodes*/, true, true);
		
		// get all selection intervals
		List<ContentInterval> selectionIntervals = editorAccess.getAuthorSelectionModel().getSelectionIntervals();
		
		// iterate over nodes to find the selected ones and insert fragments
		int insertionStartOffset = 0;
		for (AuthorNode targetNode: targetNodes) {
			for (ContentInterval selectionInterval: selectionIntervals) {
				if (APIAccessUtils.containsNode(selectionInterval, targetNode)) {
					// insert fragment and remember offset for the "goToNextEditablePosition" feature
					insertionStartOffset = targetNode.getStartOffset();
					documentController.insertXMLFragment(parsedFragment, targetNode, insertPosition);
					break;
				}
			}
		}
		
		if (goToNextEditablePosition) {
			try {
				editorAccess.goToNextEditablePosition(insertionStartOffset, insertionStartOffset+1);
			} catch (BadLocationException e) {
				throw new AuthorOperationException(e.getMessage());
			}
		}
	}

	@Override
	public ArgumentDescriptor[] getArguments() {
		return arguments;
	}

}
