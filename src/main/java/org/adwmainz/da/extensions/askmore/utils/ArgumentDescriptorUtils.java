/**
 * ArgumentDescriptorUtils.java - is a helper class providing methods related to a ro.sync.ecss.extensions.api.ArgumentDescriptor as used within the
 *  AskMoreXtension developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.utils;

import java.util.ArrayList;
import java.util.List;

import ro.sync.ecss.extensions.api.ArgumentDescriptor;

public class ArgumentDescriptorUtils {

	// constant list of all argument names used within this AskMoreXtension
	public static final String ARGUMENT_ACTION_IDS = "actionIDs";
	public static final String ARGUMENT_ACTION_NAMES = "actionNames";
	public static final String ARGUMENT_DIALOG_TITLE = "dialogTitle";
	public static final String ARGUMENT_ELEMENT_LOCATION = "elementLocation";
	public static final String ARGUMENT_FRAGMENT = "fragment";
	public static final String ARGUMENT_GO_TO_NEXT_EDITABLE_POSITION = "goToNextEditablePosition";
	public static final String ARGUMENT_INSERT_LOCATION = "insertLocation";
	public static final String ARGUMENT_INSERT_LOCATION_RESTRICTION = "insertLocationRestriction";
	public static final String ARGUMENT_INSERT_POSITION = "insertPosition";
	public static final String ARGUMENT_MESSAGE = "message";
	public static final String ARGUMENT_NO_RESULT_MESSAGE = "noResultMessage";
	public static final String ARGUMENT_NOTIFY_USER = "notifyUser";
	public static final String ARGUMENT_RESULTS_TAB_NAME = "resultsTabName";
	public static final String ARGUMENT_SCHEMA_AWARE = "schemaAware";
	public static final String ARGUMENT_SCRIPT = "script";
	public static final String ARGUMENT_SELECTION_LABEL = "selectionLabel";
	public static final String ARGUMENT_SEVERITY = "severity";
	
	/**
	 * Returns a list of all argument names in the given array
	 * @param arguments an array of ArgumentDescriptors
	 */
	public static List<String> getArgumentNames(ArgumentDescriptor[] arguments) {
		List<String> argumentNames = new ArrayList<>(arguments.length);
		for (ArgumentDescriptor argument: arguments)
			argumentNames.add(argument.getName());
		return argumentNames;
	}

}
