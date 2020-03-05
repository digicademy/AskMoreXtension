/**
 * ChooseActionOperation.java - is an implementation of a ro.sync.ecss.extensions.api.AuthorOperation which adds a custom operation to the Oxygen XML editor that
 * lets a user select another action from a given list. It is therefore one of the main classes within the AskMoreXtension developed at the Digital Academy of
 * the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.operations;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.adwmainz.da.extensions.askmore.exceptions.InputDialogClosedException;
import org.adwmainz.da.extensions.askmore.models.SelectableOption;
import org.adwmainz.da.extensions.askmore.utils.ArgumentParser;
import org.adwmainz.da.extensions.askmore.utils.AskMoreArgumentProvider;
import org.adwmainz.da.extensions.askmore.utils.InputDialogUtils;

import ro.sync.ecss.extensions.api.ArgumentDescriptor;
import ro.sync.ecss.extensions.api.ArgumentsMap;
import ro.sync.ecss.extensions.api.AuthorAccess;
import ro.sync.ecss.extensions.api.AuthorOperation;
import ro.sync.ecss.extensions.api.AuthorOperationException;
import ro.sync.exml.workspace.api.editor.page.author.actions.AuthorActionsProvider;

public class ChooseActionOperation implements AuthorOperation {

	// field
	protected ArgumentDescriptor[] arguments;
	
	// constructor
	/**
	 * Creates a new ChooseActionOperation
	 */
	public ChooseActionOperation() {
		// load localized data
		ResourceBundle rb = ResourceBundle.getBundle("org.adwmainz.da.extensions.askmore.resources.ArgumentTextBundle");
		
		// init argument descriptions
		arguments = new ArgumentDescriptor[] {
				AskMoreArgumentProvider.getDialogTitleArgumentDescriptor(rb.getString("EXECUTE_ACTION")),
				AskMoreArgumentProvider.getSelectionLabelArgumentDescriptor(rb.getString("CHOOSE_ACTION")),
				AskMoreArgumentProvider.getActionIdsArgumentDescriptor(),
				AskMoreArgumentProvider.getActionNamesArgumentDescriptor()
		};
	}

	// overridden methods
	@Override
	public String getDescription() {
		return "Opens a dialog to choose between actions defined in the associated document type.";
	}

	@Override
	public void doOperation(AuthorAccess authorAccess, ArgumentsMap args)
			throws IllegalArgumentException, AuthorOperationException {
		// get params
		String dialogTitle = ArgumentParser.getValidString(args, AskMoreArgumentProvider.ARGUMENT_DIALOG_TITLE);
		String selectionLabel = ArgumentParser.getValidString(args, AskMoreArgumentProvider.ARGUMENT_SELECTION_LABEL);
		Map<String, String> selectableActions = ArgumentParser.validateMappedArgs(args, AskMoreArgumentProvider.ARGUMENT_ACTION_IDS, 
				AskMoreArgumentProvider.ARGUMENT_ACTION_NAMES);
		
		// get actions
		AuthorActionsProvider actionsProvider = authorAccess.getEditorAccess().getActionsProvider();
		Map<String, Object> authorExtensionActions = actionsProvider.getAuthorExtensionActions();
		Map<String, Object> authorCommonActions = actionsProvider.getAuthorCommonActions();
		
		// create options
		Set<SelectableOption<Object>> options = new LinkedHashSet<>();
		for (String actionId: selectableActions.keySet()) {
			String actionName = selectableActions.get(actionId);

			// check if action is an AuthorExtensionAction
			if (authorExtensionActions != null) {
				Object action = authorExtensionActions.get(actionId);
				if (action != null) {
					options.add(new SelectableOption<Object>(action, actionName));
					continue;
				}
			}
			
			// check if action is an AuthorCommonAction
			if (authorCommonActions != null) {
				Object action = authorCommonActions.get(actionId);
				if (action != null) {
					options.add(new SelectableOption<Object>(action, actionName));
					continue;
				}
			}
			throw new AuthorOperationException("Could not find an extension action with the ID: \'" + actionId + "\'");
		}
		
		// invoke action selected with a dialog
		try {
			SelectableOption<Object> selectedOption = InputDialogUtils.fetchSelectedOption(dialogTitle, selectionLabel, options);
			actionsProvider.invokeAction(selectedOption.getRealValue());
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
