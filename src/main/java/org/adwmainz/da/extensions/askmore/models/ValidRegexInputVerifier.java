/**
 * ValidRegexInputVerifier.java - is an extension of a org.adwmainz.da.extensions.askmore.models.VerboseInputVerifier that checks if an input matches a regex as
 *  used within the AskMoreXtension developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.models;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

public class ValidRegexInputVerifier extends VerboseInputVerifier {

	// additional field
	protected String regex;

	// constructor
	/**
	 * Creates a new ValidRegexInputVerifier with the specified params
	 * @param regex the regex pattern input must match
	 * @param message a message that explains why an invalid input is not valid
	 */
	public ValidRegexInputVerifier(String regex, String message) {
		super(message);
		this.regex = regex;
	}

	@Override
	public boolean verify(JComponent input) {
		String inputAsText = ((JTextComponent)input).getText();
		return (inputAsText.matches(regex));
	}
	
}
