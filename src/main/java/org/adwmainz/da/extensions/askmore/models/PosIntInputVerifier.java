/**
 * PosIntInputVerifier.java - is an extension of a org.adwmainz.da.extensions.askmore.models.VerboseInputVerifier that checks if an input is a positive integer
 *  as used within the AskMoreXtension developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.models;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

public class PosIntInputVerifier extends VerboseInputVerifier {

	// constructor
	/**
	 * Creates a new PosIntInputVerifier with the specified params
	 * @param message a message that explains why an invalid input is not valid
	 */
	public PosIntInputVerifier(String message) {
		super(message);
	}

	@Override
	public boolean verify(JComponent input) {
		String inputAsText = ((JTextComponent)input).getText();
		try {
			int inputAsInt = Integer.parseInt(inputAsText);
			return (inputAsInt > 0);
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
