/**
 * SelectableOption.java - is a class representing an option of some selection control with a real and a rendered value as used within the AskMoreXtension
 *  developed at the Digital Academy of the  Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.models;


public class SelectableOption<T> implements Comparable<SelectableOption<T>> {

	// fields
	protected T realValue;
	protected String renderedValue;
	
	// constructors
	/**
	 * Creates a new SelectableOption that does not differentiate between a real and a rendered value
	 * @param realValue the real value of this SelectionOption
	 *  <br/>(the rendered value will be generated using its <code>toString()</code> method)
	 */
	public SelectableOption(T realValue) {
		this.realValue = realValue;
		renderedValue = realValue.toString();
	}
	
	/**
	 * Creates a new SelectableOption with the specified real and rendered value
	 * @param realValue the real value of this SelectionOption
	 * @param renderedValue the value to be returned from the <code>toString()</code> method
	 */
	public SelectableOption(T realValue, String renderedValue) {
		this.realValue = realValue;
		this.renderedValue = renderedValue;
	}

	// basic getters and setters
	/**
	 * Returns the real value of this SelectableOption
	 */
	public T getRealValue() {
		return realValue;
	}

	/**
	 * Replaces the real value of this SelectableOption with a new one
	 * @param realValue the new real value
	 */
	public void setRealValue(T realValue) {
		this.realValue = realValue;
	}

	/**
	 * Returns the rendered value of this SelectableOption
	 */
	public String getRenderedValue() {
		return renderedValue;
	}

	/**
	 * Replaces the rendered value of this SelectionOption with a new one
	 * @param renderedValue the new rendered value
	 */
	public void setRenderedValue(String renderedValue) {
		this.renderedValue = renderedValue;
	}

	// overridden methods
	/**
	 *  Returns the rendered value of this SelectableOption
	 */
	@Override
	public String toString() {
		return renderedValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((realValue == null) ? 0 : realValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SelectableOption<T> other = (SelectableOption<T>) obj;
		if (realValue == null) {
			if (other.realValue != null)
				return false;
		} else if (!realValue.equals(other.realValue))
			return false;
		return true;
	}

	@Override
	public int compareTo(SelectableOption<T> other) {
		return this.renderedValue.compareTo(other.renderedValue);
	}
	
}
