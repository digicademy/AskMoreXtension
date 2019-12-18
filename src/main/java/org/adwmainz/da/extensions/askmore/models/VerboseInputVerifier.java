/**
 * PosIntInputVerifier.java - is an abstract implementation of a javax.swing.InputVerifier that adds a custom message to all subclasses as used within the
 *  AskMoreXtension developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.models;

import javax.swing.InputVerifier;

public abstract class VerboseInputVerifier extends InputVerifier {
	
	// additional field
	protected String message;

	// constructor
	/**
	 * Creates a new VerboseInputVerifier with the specified params
	 * @param message a message that explains why an invalid input is not valid
	 */
	public VerboseInputVerifier(String message) {
		super();
		this.message = message;
	}

	/**
	 * Returns the message that explains why an invalid input is not valid
	 */
	public String getMessage() {
		return message;
	}
	
}
