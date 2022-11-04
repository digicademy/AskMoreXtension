package org.adwmainz.da.extensions.askmore.operations;

import java.util.ArrayList;
import java.util.List;

import org.adwmainz.da.extensions.askmore.utils.APIAccessUtils;
import org.adwmainz.da.extensions.askmore.utils.ArgumentParser;
import org.adwmainz.da.extensions.askmore.utils.AskMoreArgumentProvider;

import ro.sync.ecss.extensions.api.ArgumentDescriptor;
import ro.sync.ecss.extensions.api.ArgumentsMap;
import ro.sync.ecss.extensions.api.AuthorAccess;
import ro.sync.ecss.extensions.api.AuthorDocumentController;
import ro.sync.ecss.extensions.api.AuthorOperation;
import ro.sync.ecss.extensions.api.AuthorOperationException;
import ro.sync.ecss.extensions.api.AuthorSelectionModel;
import ro.sync.ecss.extensions.api.ContentInterval;
import ro.sync.ecss.extensions.api.access.AuthorEditorAccess;
import ro.sync.ecss.extensions.api.node.AuthorNode;

public class FullySelectElementsOperation implements AuthorOperation {

	// field
	protected ArgumentDescriptor[] arguments;

	// constructor
	public FullySelectElementsOperation() {
		arguments = new ArgumentDescriptor[] {
			new ArgumentDescriptor(
					AskMoreArgumentProvider.ARGUMENT_LOCATION_RESTRICTION,
					ArgumentDescriptor.TYPE_XPATH_EXPRESSION,
					"An XPath expression that specifies which nodes from the current selection should be used."
							+ "\n(i.e. this operation selects all (partially) selected nodes with the name NAME and an attribute ATTR if the value "
							+ "of this param is set to //NAME[@ATTR]."
			)
		};
	}

	@Override
	public String getDescription() {
		return "Fully selects nodes matching a given XPath expression that are only partly selected in the Author Mode.";
	}

	@Override
	public void doOperation(AuthorAccess authorAccess, ArgumentsMap args)
			throws IllegalArgumentException, AuthorOperationException {
		String location = ArgumentParser.getValidString(args, AskMoreArgumentProvider.ARGUMENT_LOCATION_RESTRICTION);
		
		// get the document controller and editor access
		AuthorDocumentController documentController = authorAccess.getDocumentController();
		AuthorEditorAccess editorAccess = authorAccess.getEditorAccess();
		
		// get all possible insert locations
		AuthorNode[] targetNodes = documentController.findNodesByXPath(location, false/*include text nodes*/, true, true);
		
		// get all selection intervals
		AuthorSelectionModel selectionModel = editorAccess.getAuthorSelectionModel();
		List<ContentInterval> selectionIntervals = selectionModel.getSelectionIntervals();
		List<ContentInterval> intervals = new ArrayList<>();
		
		// iterate over nodes to find the selected ones
		for (AuthorNode targetNode: targetNodes) {
			for (ContentInterval selectionInterval: selectionIntervals) {
				if (APIAccessUtils.containsNode(selectionInterval, targetNode)) {
					intervals.add(new ContentInterval(targetNode.getStartOffset(), targetNode.getEndOffset()));
				}
			}
		}
		selectionModel.setSelectionIntervals(intervals, true);
	}

	@Override
	public ArgumentDescriptor[] getArguments() {
		return arguments;
	}

}
