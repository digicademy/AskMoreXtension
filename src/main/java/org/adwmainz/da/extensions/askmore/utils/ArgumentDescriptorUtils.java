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
	
	// deprecated constant list of all argument names used within this AskMoreXtension
	/**
	 * @deprecated use AskMoreArgumentProvider.ARGUMENT_ACTION_IDS
	 */
	public static final String ARGUMENT_ACTION_IDS = "actionIDs";
	/**
	 * @deprecated use AskMoreArgumentProvider.ARGUMENT_ACTION_NAMES
	 */
	public static final String ARGUMENT_ACTION_NAMES = "actionNames";
	/**
	 * @deprecated use AskMoreArgumentProvider.ARGUMENT_DIALOG_TITLE
	 */
	public static final String ARGUMENT_DIALOG_TITLE = "dialogTitle";
	/**
	 * @deprecated use AskMoreArgumentProvider.ARGUMENT_ELEMENT_LOCATION
	 */
	public static final String ARGUMENT_ELEMENT_LOCATION = "elementLocation";
	/**
	 * @deprecated use AskMoreArgumentProvider.ARGUMENT_FRAGMENT
	 */
	public static final String ARGUMENT_FRAGMENT = "fragment";
	/**
	 * @deprecated use AskMoreArgumentProvider.ARGUMENT_GO_TO_NEXT_EDITABLE_POSITION
	 */
	public static final String ARGUMENT_GO_TO_NEXT_EDITABLE_POSITION = "goToNextEditablePosition";
	/**
	 * @deprecated use AskMoreArgumentProvider.ARGUMENT_INSERT_LOCATION
	 */
	public static final String ARGUMENT_INSERT_LOCATION = "insertLocation";
	/**
	 * @deprecated use AskMoreArgumentProvider.ARGUMENT_INSERT_LOCATION_RESTRICTION
	 */
	public static final String ARGUMENT_INSERT_LOCATION_RESTRICTION = "insertLocationRestriction";
	/**
	 * @deprecated use AskMoreArgumentProvider.ARGUMENT_INSERT_POSITION
	 */
	public static final String ARGUMENT_INSERT_POSITION = "insertPosition";
	/**
	 * @deprecated use AskMoreArgumentProvider.ARGUMENT_MESSAGE
	 */
	public static final String ARGUMENT_MESSAGE = "message";
	/**
	 * @deprecated use AskMoreArgumentProvider.ARGUMENT_NO_RESULT_MESSAGE
	 */
	public static final String ARGUMENT_NO_RESULT_MESSAGE = "noResultMessage";
	/**
	 * @deprecated use AskMoreArgumentProvider.ARGUMENT_NOTIFY_USER
	 */
	public static final String ARGUMENT_NOTIFY_USER = "notifyUser";
	/**
	 * @deprecated use AskMoreArgumentProvider.ARGUMENT_RESULTS_TAB_NAME
	 */
	public static final String ARGUMENT_RESULTS_TAB_NAME = "resultsTabName";
	/**
	 * @deprecated use AskMoreArgumentProvider.ARGUMENT_SCHEMA_AWARE
	 */
	public static final String ARGUMENT_SCHEMA_AWARE = "schemaAware";
	/**
	 * @deprecated use AskMoreArgumentProvider.ARGUMENT_SCRIPT
	 */
	public static final String ARGUMENT_SCRIPT = "script";
	/**
	 * @deprecated use AskMoreArgumentProvider.ARGUMENT_SELECTION_LABEL
	 */
	public static final String ARGUMENT_SELECTION_LABEL = "selectionLabel";
	/**
	 * @deprecated use AskMoreArgumentProvider.ARGUMENT_SEVERITY
	 */
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
