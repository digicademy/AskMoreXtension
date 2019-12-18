/**
 * IllegalCharInputVerifier.java - is an extension of a org.adwmainz.da.extensions.askmore.models.VerboseInputVerifier that checks if an input contains illegal
 *  chars as used within the AskMoreXtension developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.models;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

public class IllegalCharInputVerifier extends VerboseInputVerifier {

	// additional field
	char[] illegalChars;

	// constructor
	/**
	 * Creates a new IllegalCharInputVerifier with the specified params
	 * @param illegalChars an array of chars that may not appear in the input to be verified
	 * @param message a message that explains why an invalid input is not valid
	 */
	public IllegalCharInputVerifier(char[] illegalChars, String message) {
		super(message);
		this.illegalChars = illegalChars;
	}

	@Override
	public boolean verify(JComponent input) {
		String inputAsText = ((JTextComponent)input).getText();
		for (char c: illegalChars)
			if (inputAsText.contains(""+c))
				return false;
		return true;
	}

}
