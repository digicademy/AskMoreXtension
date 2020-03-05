/**
 * EditableArgumentDescriptor.java - is an extension of a ro.sync.ecss.extensions.api.ArgumentDescriptor as used within the AskMoreXtension developed at the Digital
 *  Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.1.0
 */
package org.adwmainz.da.extensions.askmore.models;

import ro.sync.ecss.extensions.api.ArgumentDescriptor;

public class EditableArgumentDescriptor extends ArgumentDescriptor {
	
	// constructors from super class
	public EditableArgumentDescriptor(String name, int type, String description) {
		super(name, type, description);
	}

	public EditableArgumentDescriptor(String name, int type, String description, String defaultValue) {
		super(name, type, description, defaultValue);
	}

	public EditableArgumentDescriptor(String name, int type, String description, String[] allowedValues, String defaultValue) {
		super(name, type, description, allowedValues, defaultValue);
	}

	// setters
	public void setAllowedValues(String[] allowedValues) {
		super.allowedValues = allowedValues;
	}

	public void setDefaultValue(String defaultValue) {
		super.defaultValue = defaultValue;
	}

	public void setDescription(String description) {
		super.description = description;
	}

	public void setName(String name) {
		super.name = name;
	}

	public void setType(int type) {
		super.type = type;
	}
	
	// copy method
	public static EditableArgumentDescriptor copyOf(ArgumentDescriptor other) {
		if (other.getType() == ArgumentDescriptor.TYPE_CONSTANT_LIST)
			return new EditableArgumentDescriptor(other.getName(), other.getType(), other.getDescription(), other.getAllowedValues(), other.getDefaultValue());
		else
			return new EditableArgumentDescriptor(other.getName(), other.getType(), other.getDescription(), other.getDefaultValue());	
	}
}
