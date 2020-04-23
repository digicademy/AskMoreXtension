/**
 * DisplayInResultsViewOperation.java - is an implementation of a ro.sync.ecss.extensions.api.AuthorOperation which adds a custom operation to the Oxygen XML
 *  editor that displays XPath results in the so called ResultsView. It is one of the main classes within the AskMoreXtension developed at the Digital Academy of
 *  the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.operations;

import java.io.File;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.adwmainz.da.extensions.askmore.utils.APIAccessUtils;
import org.adwmainz.da.extensions.askmore.utils.ArgumentParser;
import org.adwmainz.da.extensions.askmore.utils.AskMoreArgumentProvider;

import ro.sync.ecss.component.validation.AuthorDocumentPositionedInfo;
import ro.sync.ecss.extensions.api.ArgumentDescriptor;
import ro.sync.ecss.extensions.api.ArgumentsMap;
import ro.sync.ecss.extensions.api.AuthorAccess;
import ro.sync.ecss.extensions.api.AuthorDocumentController;
import ro.sync.ecss.extensions.api.AuthorOperation;
import ro.sync.ecss.extensions.api.AuthorOperationException;
import ro.sync.ecss.extensions.api.node.AuthorNode;
import ro.sync.exml.workspace.api.PluginWorkspaceProvider;
import ro.sync.exml.workspace.api.results.ResultsManager;
import ro.sync.exml.workspace.api.results.ResultsManager.ResultType;

public class DisplayInResultsViewOperation implements AuthorOperation {

	// field
	protected ArgumentDescriptor[] arguments;

	// constructor
	/**
	 * Creates a new DisplayInResultsViewOperation
	 */
	public DisplayInResultsViewOperation() {
		// load localized data
		ResourceBundle rb = ResourceBundle.getBundle("org.adwmainz.da.extensions.askmore.resources.ArgumentTextBundle");
		
		// set argument descriptions
		arguments = new ArgumentDescriptor[] {
				AskMoreArgumentProvider.getSelectElementLocationArgumentDescriptor(),
				AskMoreArgumentProvider.getResultsTabNameArgumentDescriptor(rb.getString("XPATH_RESULTS")),
				AskMoreArgumentProvider.getResultsViewMessageArgumentDescriptor(rb.getString("ELEMENT_FOUND")),
				AskMoreArgumentProvider.getNoResultMessageArgumentDescriptor(),
				AskMoreArgumentProvider.getSeverityArgumentDescriptor()
		};
	}

	// overridden methods
	@Override
	public String getDescription() {
		return "Displays an XPath result with a custom message in the results view.";
	}

	@Override
	public void doOperation(AuthorAccess authorAccess, ArgumentsMap args)
			throws IllegalArgumentException, AuthorOperationException {
		// get params
		String elementLocation = ArgumentParser.getValidString(args, AskMoreArgumentProvider.ARGUMENT_ELEMENT_LOCATION);
		String resultsTabName = ArgumentParser.getValidString(args, AskMoreArgumentProvider.ARGUMENT_RESULTS_TAB_NAME);
		String message = ArgumentParser.getValidString(args, AskMoreArgumentProvider.ARGUMENT_MESSAGE);
		String noResultMessage = ArgumentParser.getValidString(args, AskMoreArgumentProvider.ARGUMENT_NO_RESULT_MESSAGE);
		int severity = APIAccessUtils.getSeverity(ArgumentParser.getValidString(args, AskMoreArgumentProvider.ARGUMENT_SEVERITY));
		
		// get document controller
		AuthorDocumentController documentController = authorAccess.getDocumentController();
		
		// try to add file name to tab name
		String systemID = documentController.getAuthorDocumentNode().getSystemID();
		resultsTabName += " - " + new File(systemID).getName();
		
		// prepare results view by removing previous results
		ResultsManager resultsManager = PluginWorkspaceProvider.getPluginWorkspace().getResultsManager();
		resultsManager.setResults(resultsTabName, null, null);
		

		// get target nodes
		AuthorNode[] targetNodes = documentController.findNodesByXPath(elementLocation, false, true, true);

		// notify user if there are no results and exit early
		if (targetNodes.length == 0) {
			JOptionPane.showMessageDialog(null, noResultMessage);
			return;
		}
		
		// add results
		for (AuthorNode targetNode: targetNodes) {
			AuthorDocumentPositionedInfo result = new AuthorDocumentPositionedInfo(severity, message, systemID, targetNode);
			resultsManager.addResult(resultsTabName, result, ResultType.PROBLEM, true, false);
		}
	}

	@Override
	public ArgumentDescriptor[] getArguments() {
		return arguments;
	}

}
