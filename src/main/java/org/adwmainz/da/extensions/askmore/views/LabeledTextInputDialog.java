/**
 * LabeledTextInputDialog.java - is an extension of an org.adwmainz.da.extensions.askmore.views.BasicInputDialog that represents an input dynamically dialog built
 *  from an associated dialog model as used within the AskMoreXtension developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.views;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.adwmainz.da.extensions.askmore.exceptions.InputDialogClosedException;
import org.adwmainz.da.extensions.askmore.models.BasicInputField;
import org.adwmainz.da.extensions.askmore.models.SelectableOption;
import org.adwmainz.da.extensions.askmore.models.BasicSelectionField;
import org.adwmainz.da.extensions.askmore.models.VerboseInputVerifier;

public class LabeledTextInputDialog extends BasicInputDialog<Map<String, String>> {
	
	// generated serial version id
	private static final long serialVersionUID = 3848297703500807943L;
	
	// model used for building the dialog
	protected Map<String, BasicInputField<String>> dialogModel;

	// input components
	Map<String, JComponent> inputComponents;
	
	// component names
	protected String addItemDialogTitle;
	protected String noAdditionMessage;
	
	
	// constructors
	/**
	 * Creates a new DynamicTextInputDialog with the specified params
	 * @param owner the Frame from which the dialog is displayed
	 * @param isModal specifies whether the dialog blocks user input to other top-level windows when shown
	 * @param dialogModel a map of BasicInputFields and their respective label texts
	 */
	public LabeledTextInputDialog(Window owner, boolean isModal, Map<String, BasicInputField<String>> dialogModel) {
		super(owner, isModal);
		this.dialogModel = dialogModel;
		inputComponents = new HashMap<>(dialogModel.size());
	}

	/**
	 * Creates a new DynamicTextInputDialog with the specified params
	 * @param owner the Frame from which the dialog is displayed
	 * @param isModal specifies whether the dialog blocks user input to other top-level windows when shown
	 * @param horizontalAlignment specifies the horizontal alignment of normal form components. Use <code>Alignment.LEADING</code> to left indent,
	 *  <code>Alignment.TRAILING</code> to right indent or <code>Alignment.CENTER</code> to full indent
	 * @param verticalAlignment specifies the vertical alignment of all components. Use <code>Alignment.LEADING</code> to align them to the top edge,
	 *  <code>Alignment.TRAILING</code> to align them to the bottom edge or <code>Alignment.BASELINE</code> to align them to their baselines.
	 * @param verticalAlignment
	 * @param dialogModel a map of BasicInputFields and their respective label texts
	 */
	public LabeledTextInputDialog(Window owner, boolean isModal, Alignment horizontalAlignment, Alignment verticalAlignment, 
			Map<String, BasicInputField<String>> dialogModel) {
		super(owner, isModal, horizontalAlignment, verticalAlignment);
		this.dialogModel = dialogModel;
		inputComponents = new HashMap<>(dialogModel.size());
	}
	
	/**
	 * Creates a new DynamicTextInputDialog with the specified params
	 * @param owner the Frame from which the dialog is displayed
	 * @param isModal specifies whether the dialog blocks user input to other top-level windows when shown
	 * @param horizontalFormGroupAlignment specifies the horizontal alignment of normal form components. Use <code>Alignment.LEADING</code> to left indent,
	 *  <code>Alignment.TRAILING</code> to right indent or <code>Alignment.CENTER</code> to full indent
	 * @param verticalAlignment specifies the vertical alignment of all components. Use <code>Alignment.LEADING</code> to align them to the top edge,
	 *  <code>Alignment.TRAILING</code> to align them to the bottom edge or <code>Alignment.BASELINE</code> to align them to their baselines.
	 * @param horizontalOkCancelBtnAlignment specifies the horizontal alignment of normal the main buttons (OK/CANCEL). Use <code>Alignment.LEADING</code> to
	 *  left indent, <code>Alignment.TRAILING</code> to right indent or <code>Alignment.CENTER</code> to full indent
	 * @param dialogModel a map of BasicInputFields and their respective label texts
	 */
	public LabeledTextInputDialog(Window owner, boolean isModal, Alignment horizontalFormGroupAlignment,
			Alignment verticalAlignment, Alignment horizontalOkCancelBtnAlignment, Map<String, BasicInputField<String>> dialogModel) {
		super(owner, isModal, horizontalFormGroupAlignment, verticalAlignment, horizontalOkCancelBtnAlignment);
		this.dialogModel = dialogModel;
		inputComponents = new HashMap<>(dialogModel.size());
	}

	// basic getter and setter
	@Override
	public Map<String, String> getUserInput() throws InputDialogClosedException {
		if (userInput == null)
			throw new InputDialogClosedException();
		return userInput;
	}

	
	// overridden methods
	/**
	 * Initializes all components and positions them
	 */
	@Override
	public void initComponents() {
		// load localized data
		ResourceBundle rb = ResourceBundle.getBundle("org.adwmainz.da.extensions.askmore.resources.DialogTextBundle");
		addItemDialogTitle = rb.getString("ADD_ITEM");
		noAdditionMessage = rb.getString("NO_ADDITION");
		
		
		// init default components and layout
		super.initComponents();
		
		// position components and add labels
		for (String labelText: dialogModel.keySet()) {
			// create a label
			JLabel label = new JLabel(labelText);
			
			// create the input field
			BasicInputField<String> inputField = dialogModel.get(labelText);
			
			if (inputField instanceof BasicSelectionField) {
				// create a combo box and add it to the list of input components
				BasicSelectionField<String> selectionField = (BasicSelectionField<String>) inputField;
				JComboBox<SelectableOption<String>> comboBox = new JComboBox<>(new Vector<SelectableOption<String>>(selectionField.getOptions()));
				inputComponents.put(labelText, comboBox);
				if (selectionField.hasDefaultValue())
					comboBox.setSelectedItem(new SelectableOption<String>(selectionField.getDefaultValue()));
				
				if (selectionField.isEditable()) {
					// create a button for dynamically adding options
					JButton addOptionButton = new JButton("+");
					addOptionButton.addActionListener(new AddItemActionListener(getOwner(), isModal(), labelText, selectionField, comboBox));
					
					// position components
					addFormGroup(label, comboBox, addOptionButton);
				} else {
					// position components
					addFormGroup(label, comboBox);
				}				
			} else {
				// create a text field and add it to the list of input components
				JTextField textField;
				if (inputField.hasDefaultValue())
					textField = new JTextField(inputField.getDefaultValue());
				else
					textField = new JTextField(defaultMinimumSize);
				inputComponents.put(labelText, textField);
				
				// add input verifier actions
				if (inputField.hasInputVerifiers())
					textField.addKeyListener(new DynamicTextInputVerifierKeyAdapter(textField, inputField.getInputVerifiers()));
				
				// position components
				addFormGroup(label, textField);
			}
		}
		
		pack();
	}

	/**
	 * AddItemActionListener is a private class used to control how to add items to an editable selection field
	 */
	private class AddItemActionListener implements ActionListener {
		
		// fields
		private Window owner;
		private boolean isModal;
		private String labelText;
		private BasicSelectionField<String> selectionField;
		private JComboBox<SelectableOption<String>> comboBox;
		
		// constructors
		public AddItemActionListener(Window owner, boolean isModal, String labelText, BasicSelectionField<String> selectionField,
				JComboBox<SelectableOption<String>> comboBox) {
			this.owner = owner;
			this.isModal = isModal;
			this.labelText = labelText;
			this.selectionField = selectionField;
			this.comboBox = comboBox;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// build a new dialog with a similar object
			Map<String, BasicInputField<String>> addItemDialogModel = new HashMap<>();
			BasicInputField<String> addItemInputField = new BasicInputField<>();
			if (selectionField.hasInputVerifiers())
				addItemInputField.setInputVerifiers(selectionField.getInputVerifiers());
			addItemDialogModel.put(labelText, addItemInputField);
			
			LabeledTextInputDialog addItemDialog = new LabeledTextInputDialog(owner, isModal, addItemDialogModel);
			addItemDialog.setTitle(addItemDialogTitle);
			addItemDialog.initComponents();
			addItemDialog.setResizable(isResizable());
			addItemDialog.setLocationRelativeTo(owner); // center view
			addItemDialog.setVisible(true);
			addItemDialog.setDefaultCloseOperation(getDefaultCloseOperation());
			
			// try to get user input
			try {
				String addItemUserInput = addItemDialog.getUserInput().get(labelText);
				
				// add item to combo box and select it
				SelectableOption<String> addedOption = new SelectableOption<>(addItemUserInput);
				if (selectionField.getOptions().contains(addedOption))
					JOptionPane.showMessageDialog(owner, noAdditionMessage);
				else {
					comboBox.addItem(addedOption);
					comboBox.setSelectedItem(addedOption);
				}
			} catch (InputDialogClosedException e1) {
				// notify user
				JOptionPane.showMessageDialog(owner, noAdditionMessage);
			}
		}
		
	}
	
	/**
	 * DynamicTextInputVerifierKeyAdapter is a private class used to control how to react to invalid input
	 */
	private class DynamicTextInputVerifierKeyAdapter extends KeyAdapter {

		// fields
		protected JComponent inputComponent;
		protected Set<VerboseInputVerifier> inputVerifiers;
		
		// constructor
		public DynamicTextInputVerifierKeyAdapter(JComponent inputComponent, Set<VerboseInputVerifier> inputVerifiers) {
			this.inputComponent = inputComponent;
			this.inputVerifiers = inputVerifiers;
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// handle invalid input
			for (VerboseInputVerifier inputVerifier: inputVerifiers) {
				if (!inputVerifier.verify(inputComponent)) {
					inputComponent.setBackground(Color.RED);
					inputComponent.setToolTipText(inputVerifier.getMessage());
					okButton.setEnabled(false);
					return;
				}
			}
			// handle valid input
			inputComponent.setBackground(Color.WHITE);
			inputComponent.setToolTipText("");
			okButton.setEnabled(true);
		}
		
	}
	
	@Override
	public boolean hasLabels() {
		return true;
	}

	/**
	 * Fetches all user input and and adds it to the field <code>userInput</code>
	 */
	@Override
	protected void okButtonActionPerformed(ActionEvent event) {
		userInput = new HashMap<>(dialogModel.size());
		for (String labelText: dialogModel.keySet()) {
			JComponent inputComponent = inputComponents.get(labelText);
			if (inputComponent instanceof JComboBox) {
				// get real value of selected option from combo box
				JComboBox<SelectableOption<String>> comboBox = (JComboBox<SelectableOption<String>>) inputComponent;
				SelectableOption<String> selectedOption = (SelectableOption<String>) comboBox.getSelectedItem();
				userInput.put(labelText, selectedOption.getRealValue());
			} else {
				// get input from text field
				JTextField textField = (JTextField) inputComponent;
				userInput.put(labelText, textField.getText());
			}
		}
	}

}
