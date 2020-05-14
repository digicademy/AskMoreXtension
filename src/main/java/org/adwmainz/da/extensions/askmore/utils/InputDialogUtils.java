/**
 * InputDialogUtils.java - is a controller like helper class providing methods to get user input from an input dialog as used within the AskMoreXtension developed
 *  at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.utils;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import org.adwmainz.da.extensions.askmore.exceptions.InputDialogClosedException;
import org.adwmainz.da.extensions.askmore.models.BasicInputField;
import org.adwmainz.da.extensions.askmore.views.BasicInputDialog;
import org.adwmainz.da.extensions.askmore.views.LabeledTextInputDialog;
import org.adwmainz.da.extensions.askmore.views.SingleSelectionDialog;

public class InputDialogUtils {

	/**
	 * Shows a dialog using default configurations like centering displaying it in the center of the view port and setting the default close operation to
	 *  <code>WindowConstants.DISPOSE_ON_CLOSE</code>
	 * @param <T> the type of user input that is expected from the dialog
	 * @param dialog a dialog
	 */
	public static <T> void showDialog(BasicInputDialog<T> dialog) {
		// configure dialog
		dialog.initComponents();
        dialog.setLocationRelativeTo(null); // center view
	    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		// show dialog
		dialog.setVisible(true);
	}
	
	/**
	 * Returns the selected option from an input dialog 
	 * @param <T> the type of the options and the returned value
	 * @param dialogTitle the title the generated dialog should have
	 * @param options the set of options that should be selectable in the generated selection field
	 * @throws InputDialogClosedException if the input dialog is closed
	 */
	public static <T> T fetchSelectedOption(String dialogTitle, Set<T> options) throws InputDialogClosedException {
		// create dialog
		SingleSelectionDialog<T> dialog = new SingleSelectionDialog<>(null, true, options);
		dialog.setTitle(dialogTitle);
		
		// configure and show dialog
		showDialog(dialog);
		
		// get user input
		return dialog.getUserInput();
	}

	/**
	 * Returns the selected option from an input dialog 
	 * @param <T> the type of the options and the returned value
	 * @param dialogTitle the title the generated dialog should have
	 * @param labelText the label that should be placed next to the selection field
	 * @param options the set of options that should be selectable in the generated selection field
	 * @throws InputDialogClosedException if the input dialog is closed
	 */
	public static <T> T fetchSelectedOption(String dialogTitle, String labelText, Set<T> options) throws InputDialogClosedException {
		// create dialog and add a label
		SingleSelectionDialog<T> dialog = new SingleSelectionDialog<>(null, true, options);
		dialog.setTitle(dialogTitle);
		dialog.addLabel(labelText);
		
		// configure and show dialog
		showDialog(dialog);
		
		// get user input
		return dialog.getUserInput();
	}
	
	/**
	 * Returns a list of user input from a DynamicTextInputDialog using the specified dialog model
	 * @param dialogTitle the title the generated dialog should have
	 * @param dialogModel a Map of labels and input fields that should be used to generate the input dialog
	 * @throws InputDialogClosedException if the input dialog is closed
	 */
	public static Map<String, String> fetchLabeledUserInput(String dialogTitle, Map<String, BasicInputField<String>> dialogModel) throws InputDialogClosedException {
		// create dialog
		LabeledTextInputDialog dialog = new LabeledTextInputDialog(null, true, dialogModel);
		dialog.setTitle(dialogTitle);
		dialog.setResizable(false);
		
		// configure and show dialog
		showDialog(dialog);
		
		// get user input
		return dialog.getUserInput();
	}
	
	/**
	 * Replaces all AskMoreAnnotations in a given String with user input by generating an input dialog
	 * @param annotatedText a String that may contain serialized annotations
	 * @throws InputDialogClosedException if the input dialog is closed
	 */
	public static String replaceAnnotationsWithUserInput(String annotatedText) throws InputDialogClosedException {
		// remove whitespace
		annotatedText = StringUtils.reduceWhitespace(annotatedText);
		
		// find annotations
		List<String> askMoreAnnotations = AskMoreAnnotationParser.findAnnotations(annotatedText);
		
		// exit early if there are no annotations
		if (askMoreAnnotations.isEmpty())
			return annotatedText;

		// load localized data
		ResourceBundle rb = ResourceBundle.getBundle("org.adwmainz.da.extensions.askmore.resources.ArgumentTextBundle");
		String dialogTitle = rb.getString("CONFIGURE_PARAMS");
				
		// get user input from dialog
		Map<String, String> userInput = fetchLabeledUserInput(dialogTitle, AskMoreAnnotationParser.createDialogModel(askMoreAnnotations));
		
		// replace annotations and return text
		return AskMoreAnnotationParser.replaceAnnotations(annotatedText, askMoreAnnotations, userInput);
	}

	/**
	 * Returns the int representation of the specified severity
	 * @param severityName the name of a severity
	 *  <br />The allowed values are: <code>Info</code>, <code>Warning</code>, <code>Error</code> and <code>Fatal</code>
	 * @throws IllegalArgumentException if <code>severityName</code> is not one of the mentioned values 
	 */
	public static int getMessageType(String severityName) throws IllegalArgumentException {
		switch (severityName) {
			case "Info":
				return JOptionPane.INFORMATION_MESSAGE;
			case "Warning":
				return JOptionPane.WARNING_MESSAGE;
			case "Error":
			case "Fatal":
				return JOptionPane.ERROR_MESSAGE;
			default:
				throw new IllegalArgumentException("Unknown severity "+severityName);
		}
	}
}
