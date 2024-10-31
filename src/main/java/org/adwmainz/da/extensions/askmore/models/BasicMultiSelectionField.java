/**
 * BasicMultiSelectionField.java - is a generic extension of a org.adwmainz.da.extensions.askmore.models.BasicBasicField representing a simple selection field that allows
 *  multiselection as used within the AskMoreXtension developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.6.0
 */
package org.adwmainz.da.extensions.askmore.models;

import java.util.Set;

public class BasicMultiSelectionField<T> extends BasicSelectionField<T> {

	// additional fields
	protected String separator;

	// constructors
	public BasicMultiSelectionField(Set<SelectableOption<T>> options, boolean isEditable, String separator) {
		super(options, isEditable);
		this.separator = separator;
	}

	// additional getters and setters
	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

}
