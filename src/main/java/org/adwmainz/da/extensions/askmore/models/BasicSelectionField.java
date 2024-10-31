/**
 * BasicSelectionField.java - is a generic extension of a org.adwmainz.da.extensions.askmore.models.BasicInputField representing a simple selection field as used within
 *  the AskMoreXtension developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.models;

import java.util.LinkedHashSet;
import java.util.Set;

public class BasicSelectionField<T> extends BasicInputField<T> {

	// additional fields
	protected Set<SelectableOption<T>> options;
	protected boolean isEditable;
	
	// constructors
	/**
	 * Creates a new SelectionField with the specified options
	 * @param options a set of options that should be selectable from this input field
	 * @param isEditable a boolean controlling whether a user should be able to add options or not
	 */
	public BasicSelectionField(Set<SelectableOption<T>> options, boolean isEditable) {
		super();
		this.options = new LinkedHashSet<>(options);
		this.isEditable = isEditable;
	}

	/**
	 * Creates a new SelectionField with the specified options and a default option
	 * @param options a set of options that should be selectable from this input field
	 * @param isEditable a boolean controlling whether a user should be able to add options or not
	 * @param defaultOption the option that should be selected per default
	 */
	public BasicSelectionField(Set<SelectableOption<T>> options, boolean isEditable, SelectableOption<T> defaultOption) {
		super();
		this.options = new LinkedHashSet<>(options);
		this.isEditable = isEditable;
		this.defaultValue = defaultOption.getRealValue();
	}
	
	// additional getters and setters
	public Set<SelectableOption<T>> getOptions() {
		return options;
	}

	public void setOptions(Set<SelectableOption<T>> options) {
		this.options = options;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
	
}
