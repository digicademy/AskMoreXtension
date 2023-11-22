/**
 * BasicInputFieldFactory.java - is a factory class that creates an org.adwmainz.da.extensions.askmore.models.BasicInputField as used within the AskMoreXtension 
 *  developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.factories;

import java.util.Set;

import org.adwmainz.da.extensions.askmore.models.BasicInputField;
import org.adwmainz.da.extensions.askmore.models.SelectableOption;
import org.adwmainz.da.extensions.askmore.models.BasicSelectionField;
import org.adwmainz.da.extensions.askmore.models.VerboseInputVerifier;
import org.adwmainz.da.extensions.askmore.utils.StringUtils;

public class BasicInputFieldFactory {

	/**
	 * Creates a subclass of a BasicOnputField that matches the following params
	 * @param defaultValue a value that should be displayed on the created input field per default
	 * @param options a set of options that should be selectable in the created input field
	 *  <br>(a basic text input field will be created if the set is empty or <code>null</code>)
	 * @param isEditable specifies whether the set of options should be editable or not
	 *  <br>(will be ignored if there are no options)
	 * @param inputVerifiers a set of input verifiers that should be added to the input field
	 */
	public static BasicInputField<String> createInputField(String defaultValue, Set<SelectableOption<String>> options, boolean isEditable, 
			Set<VerboseInputVerifier> inputVerifiers) {
		// create an input field
		BasicInputField<String> inputField;
		if (options == null || options.isEmpty()) {
			// create a text field
			inputField = new BasicInputField<>();
		} else {
			// create a selection field
			inputField = new BasicSelectionField<>(options, isEditable);
		}
		
		// add default value if there is one
		if (StringUtils.isNonEmpty(defaultValue))
			inputField.setDefaultValue(defaultValue);
		
		// add input verifiers
		if (!inputVerifiers.isEmpty())
			inputField.setInputVerifiers(inputVerifiers);
		return inputField;
	}

}
