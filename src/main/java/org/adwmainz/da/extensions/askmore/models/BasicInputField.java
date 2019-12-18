/**
 * BasicInputField.java - is an abstract class representing an input field as used within the AskMoreXtension developed at the Digital Academy of the Academy of
 *  Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.models;

import java.util.HashSet;
import java.util.Set;

public class BasicInputField<T> {
	
	// fields
	protected T defaultValue;
	protected Set<VerboseInputVerifier> inputVerifiers;
	
	// constructor
	/**
	 * Creates an empty BasicInputField
	 */
	public BasicInputField() {
		inputVerifiers = new HashSet<>();
	}

	public BasicInputField(T defaultValue) {
		this.defaultValue = defaultValue;
		inputVerifiers = new HashSet<>();
	}

	// basic getters and setters
	/**
	 * Returns the default value of this BasicInputField
	 */
	public T getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Replaces the default value of this BasicInputField with a new one and displays it per default
	 * @param defaultValue the new default value that should be displayed per default
	 */
	public void setDefaultValue(T defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Returns the input verifiers associated with this BasicInputField
	 */
	public Set<VerboseInputVerifier> getInputVerifiers() {
		return new HashSet<>(inputVerifiers);
	}

	/**
	 * Replaces the input verifiers associated with this BasicInputField with new ones
	 * @param inputVerifiers the new set of input verifiers
	 */
	public void setInputVerifiers(Set<VerboseInputVerifier> inputVerifiers) {
		this.inputVerifiers = new HashSet<>(inputVerifiers);
	}
	
	// additional methods
	/**
	 * Checks whether this BasicInputField has a default value or not
	 * @return <code>true</code> if it has a default value
	 */
	public boolean hasDefaultValue() {
		return (defaultValue != null);
	}

	/**
	 * Checks whether this BasicInputField has input verifiers or not
	 * @return <code>true</code> if it has input verifiers
	 */
	public boolean hasInputVerifiers() {
		if (inputVerifiers == null)
			return false;
		return (!inputVerifiers.isEmpty());
	}
	
	/**
	 * Adds an input verifier to this BasicInputField 
	 * @param inputVerifier a VerboseInputVerifier
	 */
	public void addInputVerifier(VerboseInputVerifier inputVerifier) {
		inputVerifiers.add(inputVerifier);
	}

}
