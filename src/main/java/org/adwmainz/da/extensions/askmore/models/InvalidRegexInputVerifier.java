/**
 * InvalidRegexInputVerifier.java - is an extension of a org.adwmainz.da.extensions.askmore.models.ValidRegexInputVerifier that checks if an input matches a regex
 *  as used within the AskMoreXtension developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.models;

import javax.swing.JComponent;

public class InvalidRegexInputVerifier extends ValidRegexInputVerifier {

	// constructor
	/**
	 * Creates a new InvalidRegexInputVerifier with the specified params
	 * @param regex the regex pattern input must not match
	 * @param message a message that explains why an invalid input is not valid
	 */
	public InvalidRegexInputVerifier(String regex, String message) {
		super(regex, message);
	}

	@Override
	public boolean verify(JComponent input) {
		return (!super.verify(input));
	}

}
