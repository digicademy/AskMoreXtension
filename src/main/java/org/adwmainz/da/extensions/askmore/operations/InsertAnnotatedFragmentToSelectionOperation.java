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
import org.adwmainz.da.extensions.askmore.utils.APIAccessUtils;
import org.adwmainz.da.extensions.askmore.utils.ArgumentDescriptorUtils;
import org.adwmainz.da.extensions.askmore.utils.ArgumentParser;
import org.adwmainz.da.extensions.askmore.utils.AskMoreAnnotationParser;
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
		
		// get arguments from super class
		ArgumentDescriptor[] basicArguments = super.getArguments();
		
		// set argument names
		List<String> argumentNames = ArgumentDescriptorUtils.getArgumentNames(basicArguments);
		
		// get indices of relevant arguments
		int fragmentArgIdx = argumentNames.indexOf(ArgumentDescriptorUtils.ARGUMENT_FRAGMENT);
		int insertLocationArgIdx = argumentNames.indexOf(ArgumentDescriptorUtils.ARGUMENT_INSERT_LOCATION);
		int insertPositionArgIdx = argumentNames.indexOf(ArgumentDescriptorUtils.ARGUMENT_INSERT_POSITION);
		int goToNextEditablePositionIdx = argumentNames.indexOf(ArgumentDescriptorUtils.ARGUMENT_GO_TO_NEXT_EDITABLE_POSITION);
		
		// set arguments
		arguments = new ArgumentDescriptor[] {
			new ArgumentDescriptor(ArgumentDescriptorUtils.ARGUMENT_FRAGMENT,
					basicArguments[fragmentArgIdx].getType(),
					basicArguments[fragmentArgIdx].getDescription()+"\n"+AskMoreAnnotationParser.getDescription()),
			new ArgumentDescriptor(ArgumentDescriptorUtils.ARGUMENT_INSERT_LOCATION_RESTRICTION,
					basicArguments[insertLocationArgIdx].getType(),
					"An XPath expression that specifies which nodes from the current selection should be used."
					+ "\n(i.e. this operation inserts the fragment to all selected nodes with the name NAME and an attribute ATTR if the value of this param "
					+ "is set to //NAME[@ATTR]."),
			basicArguments[insertPositionArgIdx],
			basicArguments[goToNextEditablePositionIdx]
		};
	}

	// overridden methods
	@Override
	public String getDescription() {
		return "Extends the InsertAnnotatedFragmentOperation of this AskMoreXtension by applying it to (a subset) of a selection made in the Author Mode.";
	}

	@Override
	public void doOperation(AuthorAccess authorAccess, ArgumentsMap args)
			throws IllegalArgumentException, AuthorOperationException {
		String parsedFragment = ArgumentParser.getValidString(args, ArgumentDescriptorUtils.ARGUMENT_FRAGMENT);
		try {
			parsedFragment = InputDialogUtils.replaceAnnotationsWithUserInput(parsedFragment);
		} catch (InputDialogClosedException e) {
			// abort action if user closes the dialog
			return;
		}
		
		// get other params
		String insertLocation = ArgumentParser.getValidString(args, ArgumentDescriptorUtils.ARGUMENT_INSERT_LOCATION_RESTRICTION);
		String insertPosition = ArgumentParser.getValidString(args, ArgumentDescriptorUtils.ARGUMENT_INSERT_POSITION);
		boolean goToNextEditablePosition = ArgumentParser.getValidBoolean(args, ArgumentDescriptorUtils.ARGUMENT_GO_TO_NEXT_EDITABLE_POSITION);
		
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
