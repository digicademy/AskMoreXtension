/**
 * DisplayInResultsViewOperation.java - is an implementation of a ro.sync.ecss.extensions.api.AuthorOperation which adds a custom operation to the Oxygen XML
 *  editor that displays XPath results in the so called ResultsView. It is one of the main classes within the AskMoreXtension developed at the Digital Academy of
 *  the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.operations;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.adwmainz.da.extensions.askmore.utils.APIAccessUtils;
import org.adwmainz.da.extensions.askmore.utils.ArgumentDescriptorUtils;
import org.adwmainz.da.extensions.askmore.utils.ArgumentParser;

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
		
		// get severity options
		String[] severityOptions = APIAccessUtils.getSeverityNames();
		
		// set argument descriptions
		arguments = new ArgumentDescriptor[] {
				new ArgumentDescriptor(
						ArgumentDescriptorUtils.ARGUMENT_ELEMENT_LOCATION, 
						ArgumentDescriptor.TYPE_XPATH_EXPRESSION, 
						"An XPath expression indicating the element or elements that shall be selected.\n"
							+	"Note: If this is not defined then the element at the caret position will be used.",
						"."
				),
				new ArgumentDescriptor(
						ArgumentDescriptorUtils.ARGUMENT_RESULTS_TAB_NAME,
						ArgumentDescriptor.TYPE_STRING, 
						"The Name of the results view tab used for displaying the results.",
						"XPath results"
				),
				new ArgumentDescriptor(
						ArgumentDescriptorUtils.ARGUMENT_MESSAGE,
						ArgumentDescriptor.TYPE_STRING, 
						"The Message displayed in the results view tab for each element.",
						rb.getString("ELEMENT_FOUND")
				),
				new ArgumentDescriptor(
						ArgumentDescriptorUtils.ARGUMENT_NO_RESULT_MESSAGE,
						ArgumentDescriptor.TYPE_STRING, 
						"The Message displayed if no element is found.",
						rb.getString("NO_RESULT_MESSAGE")
				),
				new ArgumentDescriptor(
						ArgumentDescriptorUtils.ARGUMENT_SEVERITY,
						ArgumentDescriptor.TYPE_CONSTANT_LIST, 
						"The severity of the message.",
						severityOptions,
						severityOptions[0]
				)
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
		String elementLocation = ArgumentParser.getValidString(args, ArgumentDescriptorUtils.ARGUMENT_ELEMENT_LOCATION);
		String resultsTabName = ArgumentParser.getValidString(args, ArgumentDescriptorUtils.ARGUMENT_RESULTS_TAB_NAME);
		String message = ArgumentParser.getValidString(args, ArgumentDescriptorUtils.ARGUMENT_MESSAGE);
		String noResultMessage = ArgumentParser.getValidString(args, ArgumentDescriptorUtils.ARGUMENT_NO_RESULT_MESSAGE);
		int severity = APIAccessUtils.getSeverity(ArgumentParser.getValidString(args, ArgumentDescriptorUtils.ARGUMENT_SEVERITY));
		
		// get document controller
		AuthorDocumentController documentController = authorAccess.getDocumentController();
		
		// try to add file name to tab name
		String systemID = documentController.getAuthorDocumentNode().getSystemID();
		try {
			String fileName = APIAccessUtils.getFileName(systemID);
			resultsTabName += " - " + fileName;
		} catch (MalformedURLException | URISyntaxException e) {
			// leave the tab name as is
		}
		
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
