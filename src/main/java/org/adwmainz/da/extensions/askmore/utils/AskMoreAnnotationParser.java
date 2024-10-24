/**
 * AskMoreAnnotationParser.java - is a helper class that provides methods for parsing Strings containing AskMoreAnnotations like finding them or deserializing
 *  them to org.adwmainz.da.extensions.askmore.models.BasicInputField objects. It is one of the main classes within the AskMoreXtension developed at the Digital
 *   Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.adwmainz.da.extensions.askmore.factories.BasicInputFieldFactory;
import org.adwmainz.da.extensions.askmore.factories.SelectableOptionFactory;
import org.adwmainz.da.extensions.askmore.models.BasicInputField;
import org.adwmainz.da.extensions.askmore.models.IllegalCharInputVerifier;
import org.adwmainz.da.extensions.askmore.models.InvalidRegexInputVerifier;
import org.adwmainz.da.extensions.askmore.models.PosIntInputVerifier;
import org.adwmainz.da.extensions.askmore.models.SelectableOption;
import org.adwmainz.da.extensions.askmore.models.ValidRegexInputVerifier;
import org.adwmainz.da.extensions.askmore.models.VerboseInputVerifier;

public class AskMoreAnnotationParser {
	
	// relevant regex patterns
	protected static final String ASK_MORE_ANNOTATION_PATTERN = "\\$\\$" + "(\".*?\")" /* Label */ 
			+ " ?: ?\\(" + "(.*?)" /* Options */ + "\\)" + "((![^$]+)*)" /* Flags */ + "\\$\\$";
	protected static final String DEFAULT_VALUE_PATTERN = "DEFAULT\\(\"" + "(.*?)" /* value */ + "\"\\)";
	protected static final String IS_EDITABLE_PATTERN = "EDITABLE";
	protected static final String URL_ENCODE_PATTERN = "URL_ENCODE";
	protected static final String REGEX_FLAG_PATTERN = "REGEX\\(\"" + "(.*?)" /* regex */ + "\"\\)";
	protected static final String CONTAINS_WHITESPACE_PATTERN = ".*?\\s+.*?";
	protected static final String XML_ESCAPE_PATTERN = "XML_ESCAPE";
	
	/**
	 * Returns a List of AskMoreAnnotations from an annotated String
	 * @param annotatedText a String that may contain AskMoreAnnotations
	 */
	public static List<String> findAnnotations(String annotatedText) {
		return RegexUtils.getMatches(annotatedText, ASK_MORE_ANNOTATION_PATTERN);
	}
	
	/**
	 * Creates a Map of labels and input fields that may be used as the dialog model of an DynamicTextInputDialogDeserializes from a List of AskMoreAnnotations
	 * @param askMoreAnnotations a List of AskMoreAnnotations (c.f. {@link #getDescription()})
	 */
	public static Map<String, BasicInputField<String>> createDialogModel(List<String> askMoreAnnotations) {
		Map<String, BasicInputField<String>> dialogModel = new LinkedHashMap<>();
		
		for (String askMoreAnnotation: askMoreAnnotations) {
			Matcher matcher = Pattern.compile(ASK_MORE_ANNOTATION_PATTERN).matcher(askMoreAnnotation);
			if (matcher.find()) {
				// get label with pattern and format quotation marks
				String label = StringUtils.removeEnclosingQuotes(matcher.group(1));
				label = StringUtils.removeEnclosingQuotes(label);
				label = StringUtils.removeEscapedQuotes(label);

				// get options
				Set<SelectableOption<String>> options = new LinkedHashSet<>();
				String optionGroup = matcher.group(2);
				if (optionGroup != null && !optionGroup.isEmpty()) {
					String[] encodedOptions = matcher.group(2).split("(?<=\" ?),");
					for (String encodedOption: encodedOptions) {
						options.add(SelectableOptionFactory.createOption(encodedOption, "|", true, true));
					}
				}
				
				// get default value, editability info and other flags
				String defaultValue = "";
				boolean isEditable = false;
				Set<VerboseInputVerifier> inputVerifiers = new HashSet<>();
				
				String flags = matcher.group(3);
				if (flags.length() > 1) {
					flags = flags.substring(1); // remove first exclamation mark
					for (String flag: flags.split("!")) {
						if (flag.matches(DEFAULT_VALUE_PATTERN)) {
							// get default value with pattern and format quotation marks
							defaultValue = flag.replaceAll(DEFAULT_VALUE_PATTERN, "$1");
							defaultValue = StringUtils.removeEnclosingQuotes(defaultValue);
							defaultValue = StringUtils.removeEscapedQuotes(defaultValue);
						}
						else if (flag.matches(IS_EDITABLE_PATTERN))
							isEditable = true; // set editable
						else if (flag.matches(URL_ENCODE_PATTERN))
							;
						else if (flag.matches(XML_ESCAPE_PATTERN))
							;
						else 
							inputVerifiers.add(createInputVerifier(flag)); // create input verifier
					}
				}

				// create input field
				dialogModel.put(label, BasicInputFieldFactory.createInputField(defaultValue, options, isEditable, inputVerifiers)); 
			} else {
				throw new IllegalArgumentException("The String \""+askMoreAnnotation+"\" is not a valid AskMoreAnnotation");
			}
		}
		
		return dialogModel;
	}
	
	/**
	 * Creates a subclass of a VerboseInputVerifier from a specified flag
	 * @param flag a flag representing a specific subclass of a VerboseInputVerifier
	 *  <br>The allowed values are: <code>!REGEX("regex")</code>, <code>!NO_XML</code>, <code>!NO_SPACE</code> and <code>!POS_INT</code>
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static VerboseInputVerifier createInputVerifier(String flag) throws IllegalArgumentException{
		// load localized data
		ResourceBundle rb = ResourceBundle.getBundle("org.adwmainz.da.extensions.askmore.resources.InputVerifierTextBundle");

		String message;
		// parse flags with params
		if (flag.matches(REGEX_FLAG_PATTERN)) {
			String regex = flag.replaceAll(REGEX_FLAG_PATTERN, "$1");
			regex = StringUtils.removeEnclosingQuotes(regex);
			regex = StringUtils.removeEscapedQuotes(regex);
			message = rb.getString("REGEX_INPUT_VERIFIER_MESSAGE") + " " + regex;
			return new ValidRegexInputVerifier(regex, message);
		}
		
		// parse simple flags
		switch (flag) {
		case "NO_XML":
			char[] xmlSpecialChars = XMLUtils.getSpecialChars();
			message = rb.getString("ILLEGAL_CHAR_INPUT_VERIFIER_MESSAGE") + " " + Arrays.toString(xmlSpecialChars);
			return new IllegalCharInputVerifier(xmlSpecialChars, message);
		case "NO_SPACE":
			String whitespaceRegex = CONTAINS_WHITESPACE_PATTERN;
			message = rb.getString("NO_WHITESPACE_INPUT_VERIFIER_MESSAGE");
			return new InvalidRegexInputVerifier(whitespaceRegex, message);
		case "POS_INT":
			message = rb.getString("POS_INT_INPUT_VERIFIER_MESSAGE");
			return new PosIntInputVerifier(message);
		default:
			throw new IllegalArgumentException("Unsupported flag " + flag);
		}
	}

	/**
	 * Replaces all AskMoreAnnotations in a given String with the specified user input
	 * @param annotatedText a String that may contain AskMoreAnnotations (c.f. {@link #getDescription()})
	 * @param askMoreAnnotations a list of AskMoreAnnotations (c.f. {@link #getDescription()})
	 * @param userInput user input from a dialog created from the specified list of serialized annotations
	 * @return
	 */
	public static String replaceAnnotations(String annotatedText, List<String> askMoreAnnotations, Map<String, String> userInput) {
		if (askMoreAnnotations.size() != userInput.size())
			throw new IllegalArgumentException("The size of askMoreAnnotations must match the number of userInput");
		
		for (String askMoreAnnotation: askMoreAnnotations) {
			Matcher matcher = Pattern.compile(ASK_MORE_ANNOTATION_PATTERN).matcher(askMoreAnnotation);
			if (matcher.find()) {
				// get label with pattern and format quotation marks
				String label = StringUtils.removeEnclosingQuotes(matcher.group(1));
				label = StringUtils.removeEnclosingQuotes(label);
				label = StringUtils.removeEscapedQuotes(label);
				
				// replace annotation with user input
				String input = userInput.get(label);
				String flags = matcher.group(3);
				if (flags.length() > 1) {
					flags = flags.substring(1); // remove first exclamation mark
					for (String flag: flags.split("!")) {
						if (flag.matches(URL_ENCODE_PATTERN)) {
							try {
								input = URLEncoder.encode(input, StandardCharsets.UTF_8.toString());
							} catch (UnsupportedEncodingException e) {
								throw new IllegalArgumentException(e.getCause());
							}
						}
						
						// escape entities if ESCAPE flag is set
						if (flag.matches(XML_ESCAPE_PATTERN)) {
							input = input.replace("&", "&amp;");
							input = input.replace("<", "&lt;");
						}
					}
				}
				annotatedText = annotatedText.replace(askMoreAnnotation, input);
			}
		}
		return annotatedText;
	}

	protected static String getLabel(String askMoreAnnotation) {
		Matcher matcher = Pattern.compile(ASK_MORE_ANNOTATION_PATTERN).matcher(askMoreAnnotation);
		if (matcher.find())
			return StringUtils.removeEnclosingQuotes(matcher.group(1));
		throw new IllegalArgumentException();
	}
	
	/**
	 * Describes how to use AskMoreAnnotations
	 */
	public static String getDescription() {
		return "You may delegate one or more parts of this argument value to a single input dialog by using the following annotations:\n"
				+ "- $$\"LABEL1\":()$$ creates a simple text input field with the label LABEL1\n"
				+ "- $$\"LABEL2\":()!DEFAULT(\"D\")$$ creates another text input field with the default value D\n"
				+ "- $$\"LABEL3\":(\"A\", \"B\", \"C\")$$ creates a combo box with the label LABEL3\n"
				+ "(Please note that you may use the form \"REAL_VALUE\"|\"RENDERED_VALUE\" for options that should display a value different from the one to be"
				+ " inserted and the !DEFAULT() flag for a preselection which must always reference a real value: "
				+ "e.g. $$\"LABEL4\":(\"A\", \"REAL_B\"|\"RENDERED_B\")!DEFAULT(\"REAL_B\")$$ is valid)\n"
				+ "- $$\"LABEL5\":(\"A\", \"B\")!DEFAULT(\"B\")!EDITABLE$$ creates an editable combo box with the label LABEL5\n"
				+ "You can also use the the following encoding flags:\n"
				+ "- !URL_ENCODE to let the input be URL encoded\n"
				+ "- !XML_ESCAPE to escape < and &\n"
				+ "Besides the !DEFAULT(), !EDITABLE and the encoding flags you may also add the following restriction flags to any annotation:\n"
				+ "- !NO_XML for text input without the chars " + Arrays.toString(XMLUtils.getSpecialChars()) + "\n"
				+ "- !NO_SPACE for text input without whitespace\n"
				+ "- !POS_INT for positive integer input\n"
				+ "- !REGEX(\"regex\") for text input matching the regex regex\n"
				+ "⚠️ Please note that you have to escape quotation marks in labels, options and regex patterns with a backslash (e.g. !REGEX(\"\\\".*?\\\"\")).";
	}
}
