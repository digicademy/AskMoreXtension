/**
 * AskMoreArgumentProvider.java - is a helper class storing common argument names and descriptors as used within the AskMoreXtension developed at the
 *  Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.1.0
 */
package org.adwmainz.da.extensions.askmore.utils;

import java.util.ResourceBundle;

import ro.sync.ecss.extensions.api.ArgumentDescriptor;
import ro.sync.ecss.extensions.api.AuthorConstants;

public class AskMoreArgumentProvider {

	// constant list of all argument names used within this AskMoreXtension
	public static final String ARGUMENT_ACTION_IDS = "actionIDs";
	public static final String ARGUMENT_ACTION_NAMES = "actionNames";
	public static final String ARGUMENT_COMMAND_LINE = "cmdLine";
	public static final String ARGUMENT_DIALOG_TITLE = "dialogTitle";
	public static final String ARGUMENT_ELEMENT_LOCATION = "elementLocation";
	public static final String ARGUMENT_EXTERNAL_PARAMS = "externalParams";
	public static final String ARGUMENT_FRAGMENT = "fragment";
	public static final String ARGUMENT_GO_TO_NEXT_EDITABLE_POSITION = "goToNextEditablePosition";
	public static final String ARGUMENT_INSERT_LOCATION = "insertLocation";
	public static final String ARGUMENT_INSERT_LOCATION_RESTRICTION = "insertLocationRestriction";
	public static final String ARGUMENT_INSERT_POSITION = "insertPosition";
	public static final String ARGUMENT_LOCATION_RESTRICTION = "locationRestriction";
	public static final String ARGUMENT_MESSAGE = "message";
	public static final String ARGUMENT_NO_RESULT_MESSAGE = "noResultMessage";
	public static final String ARGUMENT_NOTIFY_USER = "notifyUser";
	public static final String ARGUMENT_REMOVE_SELECTION = "removeSelection";
	public static final String ARGUMENT_RESULTS_TAB_NAME = "resultsTabName";
	public static final String ARGUMENT_SCHEMA_AWARE = "schemaAware";
	public static final String ARGUMENT_SCRIPT = "script";
	public static final String ARGUMENT_SELECTION_LABEL = "selectionLabel";
	public static final String ARGUMENT_SEVERITY = "severity";
	
	// methods for retrieving the ArgumentDescriptors used within this AskMoreXtension
	public static ArgumentDescriptor getActionIdsArgumentDescriptor() {
		return new ArgumentDescriptor(
				ARGUMENT_ACTION_IDS, 
				ArgumentDescriptor.TYPE_STRING, 
				"The action IDs which will be available through the dialog, separated by new lines."
				+ "\n(This list must contain as many values as the one of "+ARGUMENT_ACTION_NAMES+")"
		);
	}
	
	public static ArgumentDescriptor getActionNamesArgumentDescriptor() {
		return new ArgumentDescriptor(
				ARGUMENT_ACTION_NAMES, 
				ArgumentDescriptor.TYPE_STRING, 
				"The items which will be selectable in the dialog, separated by new lines."
				+ "\n(This list must contain as many values as the one of "+ARGUMENT_ACTION_IDS+")"
		);
	}
	
	public static ArgumentDescriptor getCopyElementLocationArgumentDescriptor() {
		return new ArgumentDescriptor(
				ARGUMENT_ELEMENT_LOCATION, 
				ArgumentDescriptor.TYPE_XPATH_EXPRESSION, 
				"An XPath expression indicating the element, attribute or text content to be copied. "
				+ "\n(Leave empty to copy the current selection within the Author Mode.)"
		);
	}
	public static ArgumentDescriptor getDialogTitleArgumentDescriptor(String defaultValue) {
		return new ArgumentDescriptor(
				ARGUMENT_DIALOG_TITLE, 
				ArgumentDescriptor.TYPE_STRING, 
				"The title of the generated dialog.",
				defaultValue
		);
	}
	
	public static ArgumentDescriptor getMessageArgumentDescriptor(String defaultValue) {
		return new ArgumentDescriptor(
				ARGUMENT_MESSAGE,
				ArgumentDescriptor.TYPE_STRING, 
				"The Message displayed in a new dialog.",
				defaultValue
		);
	}
	
	public static ArgumentDescriptor getNoResultMessageArgumentDescriptor() {
		ResourceBundle rb = ResourceBundle.getBundle("org.adwmainz.da.extensions.askmore.resources.ArgumentTextBundle");
		return new ArgumentDescriptor(
				ARGUMENT_NO_RESULT_MESSAGE,
				ArgumentDescriptor.TYPE_STRING, 
				"The Message displayed if no element is found.",
				rb.getString("NO_RESULT_MESSAGE")
		);
	}
	
	public static ArgumentDescriptor getResultsTabNameArgumentDescriptor(String defaultValue) {
		return new ArgumentDescriptor(
				ARGUMENT_RESULTS_TAB_NAME,
				ArgumentDescriptor.TYPE_STRING, 
				"The Name of the results view tab used for displaying the results.",
				defaultValue
		);
	}
	
	public static ArgumentDescriptor getAnnotatedResultsViewMessageArgumentDescriptor(String defaultValue) {
		return new ArgumentDescriptor(
				ARGUMENT_MESSAGE,
				ArgumentDescriptor.TYPE_STRING, 
				"The Message displayed in the results view tab for each element."
						+ "You may build part(s) of the message dynamically from each contxt node by using the annotation $$XPATH( xpath )$$ where xpath denotes"
						+ " an XPath expression relative to the result(s) of the param '" + AskMoreArgumentProvider.ARGUMENT_ELEMENT_LOCATION + "'.",
				defaultValue
		);
	}
	
	public static ArgumentDescriptor getResultsViewMessageArgumentDescriptor(String defaultValue) {
		return new ArgumentDescriptor(
				ARGUMENT_MESSAGE,
				ArgumentDescriptor.TYPE_STRING, 
				"The Message displayed in the results view tab for each element.",
				defaultValue
		);
	}
	
	public static ArgumentDescriptor getRemoveSelectionArgumentDescriptor() {
		return new ArgumentDescriptor(
				ARGUMENT_REMOVE_SELECTION, 
				ArgumentDescriptor.TYPE_CONSTANT_LIST, 
				"Specifies whether all selections in the author mode should be removed or not.", 
				new String[] {
						AuthorConstants.ARG_VALUE_TRUE, 
						AuthorConstants.ARG_VALUE_FALSE},
				AuthorConstants.ARG_VALUE_TRUE
		);
	}
	
	public static ArgumentDescriptor getSelectAnnotatedElementLocationArgumentDescriptor() {
		return new ArgumentDescriptor(
				ARGUMENT_ELEMENT_LOCATION, 
				ArgumentDescriptor.TYPE_XPATH_EXPRESSION, 
				"An XPath expression indicating the element or elements that shall be selected.\n"
					+	"Note: If this is not defined then the element at the caret position will be used.\n"
					+ 	AskMoreAnnotationParser.getDescription(),
				"."
		);
	}
	
	public static ArgumentDescriptor getSelectElementLocationArgumentDescriptor() {
		return new ArgumentDescriptor(
				ARGUMENT_ELEMENT_LOCATION, 
				ArgumentDescriptor.TYPE_XPATH_EXPRESSION, 
				"An XPath expression indicating the element or elements that shall be selected.\n"
					+	"Note: If this is not defined then the element at the caret position will be used.",
				"."
		);
	}
	
	public static ArgumentDescriptor getSelectionLabelArgumentDescriptor(String defaultValue) {
		return new ArgumentDescriptor(
				ARGUMENT_SELECTION_LABEL, 
				ArgumentDescriptor.TYPE_STRING, 
				"The text that should displayed next to the selection.",
				defaultValue
		);
	}
	
	public static ArgumentDescriptor getSeverityArgumentDescriptor() {
		String[] severityOptions = APIAccessUtils.getSeverityNames();
		return new ArgumentDescriptor(
				AskMoreArgumentProvider.ARGUMENT_SEVERITY,
				ArgumentDescriptor.TYPE_CONSTANT_LIST, 
				"The severity of the message.",
				severityOptions,
				severityOptions[0]
		);
	}
	
	public static ArgumentDescriptor getNotifyUserWithMessageArgumentDescriptor() {
		return new ArgumentDescriptor(
				ARGUMENT_NOTIFY_USER, 
				ArgumentDescriptor.TYPE_CONSTANT_LIST, 
				"Specifies whether the message from the argument " + ARGUMENT_MESSAGE + " should be displayed or not.", 
				new String[] {
						AuthorConstants.ARG_VALUE_TRUE, 
						AuthorConstants.ARG_VALUE_FALSE},
				AuthorConstants.ARG_VALUE_FALSE
		);
	};
	
	// other methods
	public static String getClosedDialogMessage() {
		ResourceBundle rb = ResourceBundle.getBundle("org.adwmainz.da.extensions.askmore.resources.DialogTextBundle");
		return rb.getString("INPUT_DIALOG_CLOSED");
	}
	
}
