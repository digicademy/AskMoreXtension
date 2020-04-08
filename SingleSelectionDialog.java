/**
 * SingleSelectionDialog.java - is a generic extension of an org.adwmainz.da.extensions.askmore.views.BasicInputDialog that represents an input dialog with one
 *  selection field and an optional label as used within the AskMoreXtension developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.views;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.Set;
import java.util.Vector;

import javax.swing.GroupLayout.Alignment;

import org.adwmainz.da.extensions.askmore.exceptions.InputDialogClosedException;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class SingleSelectionDialog<T> extends BasicInputDialog<T> {

	// generated serial version id
	private static final long serialVersionUID = 7915231248805046025L;

	// accessible component
	protected JComboBox<T> comboBox;

	// component identifier
	protected String labelText;
	
	// constructors
	/**
	 * Creates a new SingleSelectionDialog with the specified params and a default alignment (i.e. the selection form control is aligned to the left with a
	 *  vertical alignment set to <code>Alignment.BASELINE</code> while the main buttons (OK/CANCEL) are aligned to the right). 
	 * <br/><br/>Please use the <code>setLabelText()</code> method to add a label
	 * @param owner the Frame from which the dialog is displayed
	 * @param isModal specifies whether the dialog blocks user input to other top-level windows when shown
	 * @param options a list of options that should be selectable
	 */
	public SingleSelectionDialog(Window owner, boolean isModal, Set<T> options) {
		super(owner, isModal);
		comboBox = new JComboBox<>(new Vector<>(options));
	}
	
	/**
	 * Creates a new SingleSelectionDialog with the specified params
	 * <br/><br/>Please use the <code>setLabelText()</code> method to add a label
	 * @param owner the Frame from which the dialog is displayed
	 * @param isModal specifies whether the dialog blocks user input to other top-level windows when shown
	 * @param horizontalAlignment specifies the horizontal alignment of the selection form control. Use <code>Alignment.LEADING</code> to left indent,
	 *  <code>Alignment.TRAILING</code> to right indent or <code>Alignment.CENTER</code> to full indent
	 * @param verticalAlignment specifies the vertical alignment of the selection form control. Use <code>Alignment.LEADING</code> to align it to the top edge,
	 *  <code>Alignment.TRAILING</code> to align it to the bottom edge or <code>Alignment.BASELINE</code> to align it to the baseline.
	 * @param options a list of options that should be selectable
	 */
	public SingleSelectionDialog(Window owner, boolean isModal, Alignment horizontalAlignment,
			Alignment verticalAlignment, Set<T> options) {
		super(owner, isModal, horizontalAlignment, verticalAlignment);
		comboBox = new JComboBox<>(new Vector<>(options));
	}
	
	/**
	 * Creates a new SingleSelectionDialog with the specified params
	 * <br/><br/>Please use the <code>setLabelText()</code> method to add a label
	 * @param owner the Frame from which the dialog is displayed
	 * @param isModal specifies whether the dialog blocks user input to other top-level windows when shown
	 * @param horizontalFormGroupAlignment specifies the horizontal alignment of the selection form control. Use <code>Alignment.LEADING</code> to left indent,
	 *  <code>Alignment.TRAILING</code> to right indent or <code>Alignment.CENTER</code> to full indent
	 * @param verticalAlignment specifies the vertical alignment of the selection form control. Use <code>Alignment.LEADING</code> to align it to the top edge,
	 *  <code>Alignment.TRAILING</code> to align it to the bottom edge or <code>Alignment.BASELINE</code> to align it to the baseline.
	 * @param horizontalOkCancelBtnAlignment specifies the horizontal alignment of normal the main buttons (OK/CANCEL). Use <code>Alignment.LEADING</code> to
	 *  left indent, <code>Alignment.TRAILING</code> to right indent or <code>Alignment.CENTER</code> to full indent
	 * @param options a list of options that should be selectable
	 */
	public SingleSelectionDialog(Window owner, boolean isModal, Alignment horizontalFormGroupAlignment,
			Alignment verticalAlignment, Alignment horizontalOkCancelBtnAlignment, Set<T> options) {
		super(owner, isModal, horizontalFormGroupAlignment, verticalAlignment, horizontalOkCancelBtnAlignment);
		comboBox = new JComboBox<>(new Vector<>(options));
	}

	// basic getter and setter
	@Override
	public T getUserInput() throws InputDialogClosedException {
		if (userInput == null)
			throw new InputDialogClosedException();
		return userInput;
	}

	// other overridden methods
	/**
	 * Initializes all components and positions them
	 * <br /><br />Use <code>addLabel()</code> before invoking this method to add a label to this dialog
	 */
	@Override
	public void initComponents() {		
		// init default components and layout
		super.initComponents();
		
		// add additional components
		if (hasLabels()) {
			JLabel label = new JLabel(labelText);
			addFormGroup(label, comboBox);
		} else 
			addFormElement(comboBox);
		
		pack();
	}


	@Override
	public boolean hasLabels() {
		return (labelText != null);
	}
	
	/**
	 * Sets the value of <code>userInput</code> to the selected value
	 */
	@Override
	protected void okButtonActionPerformed(ActionEvent event) {
		userInput = (T) comboBox.getSelectedItem();
	}

	// additional method
	/**
	 * Adds a label with the specified text next to the selection form control
	 * <br /></br />Must be invoked before the method <code>initComponents()</code> is invoked
	 * @param labelText the text that should displayed on the label next to the selection form control
	 */
	public void addLabel(String labelText) {
		this.labelText = labelText;
	}
	
}
