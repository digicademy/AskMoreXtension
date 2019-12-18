/**
 * BasicInputDialog.java - is an abstract extension of a javax.swing.JDialog representing an input dialog as used within the AskMoreXtension developed at the
 *  Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.views;

import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.Group;

import org.adwmainz.da.extensions.askmore.exceptions.InputDialogClosedException;

public abstract class BasicInputDialog<T> extends JDialog implements ActionListener {

	// generated serial version id
	private static final long serialVersionUID = 3656872152420186354L;

	// constant value
	protected static final int defaultMinimumSize = 33;
	
	// associated model
	protected T userInput;

	// accessible components
	protected JButton okButton;
	protected JButton cancelButton;
	
	// component identifiers
	protected String okButtonText; 
	protected String cancelButtonText;
	
	// accessible layout components
	private GroupLayout layout;
	
	protected Alignment horizontalFormGroupAlignment;
	protected Alignment horizontalOkCancelBtnAlignment;
	protected Alignment verticalAlignment;
	
	private Group horizontalLabelGroup;
	private Group horizontalInputComponentGroup;
	private Group verticalFormGroup;
	
	// constructors
	/**
	 * Creates a new BasicInputDialog with the specified params and a default alignment (i.e. normal form components are aligned to the left with their vertical
	 *  alignment set to <code>Alignment.BASELINE</code> while the main buttons (OK/CANCEL) are aligned to the right).
	 * @param owner the Frame from which the dialog is displayed
	 * @param isModal specifies whether the dialog blocks user input to other top-level windows when shown
	 */
	public BasicInputDialog(Window owner, boolean isModal) {
		this(owner, isModal, Alignment.LEADING, Alignment.BASELINE, Alignment.TRAILING);
	}

	/**
	 * Creates a new BasicInputDialog with the specified params
	 * @param owner the Frame from which the dialog is displayed
	 * @param isModal specifies whether the dialog blocks user input to other top-level windows when shown
	 * @param horizontalAlignment specifies the horizontal alignment of all components. Use <code>Alignment.LEADING</code> to left indent,
	 *  <code>Alignment.TRAILING</code> to right indent or <code>Alignment.CENTER</code> to full indent
	 * @param verticalAlignment specifies the vertical alignment of all components. Use <code>Alignment.LEADING</code> to align them to the top edge, 
	 * <code>Alignment.TRAILING</code> to align them to the bottom edge or <code>Alignment.BASELINE</code> to align them to their baselines.
	 */
	public BasicInputDialog(Window owner, boolean isModal, Alignment horizontalAlignment, 
			Alignment verticalAlignment) {
		this(owner, isModal, horizontalAlignment, verticalAlignment, horizontalAlignment);
	}

	/**
	 * Creates a new BasicInputDialog with the specified params
	 * @param owner the Frame from which the dialog is displayed
	 * @param isModal specifies whether the dialog blocks user input to other top-level windows when shown
	 * @param horizontalAlignment specifies the horizontal alignment of normal form components. Use <code>Alignment.LEADING</code> to left indent,
	 *  <code>Alignment.TRAILING</code> to right indent or <code>Alignment.CENTER</code> to full indent
	 * @param verticalAlignment specifies the vertical alignment of all components. Use <code>Alignment.LEADING</code> to align them to the top edge,
	 *  <code>Alignment.TRAILING</code> to align them to the bottom edge or <code>Alignment.BASELINE</code> to align them to their baselines.
	 * @param horizontalOkCancelBtnAlignment specifies the horizontal alignment of normal the main buttons (OK/CANCEL). Use <code>Alignment.LEADING</code> to
	 *  left indent, <code>Alignment.TRAILING</code> to right indent or <code>Alignment.CENTER</code> to full indent
	 */
	public BasicInputDialog(Window owner, boolean isModal, Alignment horizontalFormGroupAlignment, 
			Alignment verticalAlignment, Alignment horizontalOkCancelBtnAlignment) {
		super(owner);
		setModal(isModal);
		this.horizontalFormGroupAlignment = horizontalFormGroupAlignment;
		this.horizontalOkCancelBtnAlignment = horizontalOkCancelBtnAlignment;
		this.verticalAlignment = verticalAlignment;
	}
	
	// basic getter and setter
	/**
	 * Returns the user input
	 */
	public abstract T getUserInput() throws InputDialogClosedException;

	/**
	 * Returns <code>true</code> if this dialog has labels associated with the input components
	 */
	public abstract boolean hasLabels();
	
	/**
	 * Changes the text displayed on the OK button
	 * <br /></br />Must be invoked before the method <code>initComponents()</code> is invoked
	 * @param okButtonText
	 */
	public void setOkButtonText(String okButtonText) {
		this.okButtonText = okButtonText;
	}

	/**
	 * Changes the text displayed on the CANCEL button
	 * <br /></br />Must be invoked before the method <code>initComponents()</code> is invoked
	 * @param cancelButtonText
	 */
	public void setCancelBtnTxt(String cancelButtonText) {
		this.cancelButtonText = cancelButtonText;
	}

	// basic initialization methods
	/**
	 * Initializes the main buttons (OK/CANCEL) and positions them at the bottom of the dialog and creates basic layout groups for input components. It also
	 *  adds groups for associated labels if <code>hasLabels() == true</code>.
	 */
	public void initComponents() {
		// load localized data
		ResourceBundle rb = ResourceBundle.getBundle("org.adwmainz.da.extensions.askmore.resources.DialogTextBundle");
		if (okButtonText == null)
			okButtonText = rb.getString("OK");
		if (cancelButtonText == null)
			cancelButtonText = rb.getString("CANCEL");
		
		// init OK button
		okButton = new JButton(okButtonText);
		okButton.setActionCommand(okButtonText);
		okButton.addActionListener(this);
		getRootPane().setDefaultButton(okButton);
		
		// init CANCEL button
		cancelButton = new JButton(cancelButtonText);
		cancelButton.addActionListener(this);
		
		// init layout
		initLayout();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (okButtonText.equals(event.getActionCommand()))
			okButtonActionPerformed(event);
		dispose();
	}
	
	/**
	 * Specifies what should be done after the OK button action is invoked
	 * @param event the associated event
	 */
	protected abstract void okButtonActionPerformed(ActionEvent event);
	
	// layout related methods
	/**
	 * Initializes the layout of this dialog by creating groups 
	 */
	private void initLayout() {
		// init layout
		Container contentPane = getContentPane();
		layout = new GroupLayout(contentPane);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		contentPane.setLayout(layout);
		
		// init horizontal groups
		if (hasLabels())
			horizontalLabelGroup = layout.createParallelGroup(horizontalFormGroupAlignment);
		horizontalInputComponentGroup = layout.createParallelGroup(horizontalFormGroupAlignment);
			
		Group horizontalFormGroup = layout.createSequentialGroup();
		if (hasLabels())
			horizontalFormGroup = horizontalFormGroup.addGroup(horizontalLabelGroup);
		horizontalFormGroup = horizontalFormGroup.addGroup(horizontalInputComponentGroup);
		
		Group horizontalOKCancelGroup = layout.createSequentialGroup()
				.addComponent(okButton)
				.addComponent(cancelButton);
		
		// init vertical groups
		verticalFormGroup = layout.createSequentialGroup();
		Group verticalOKCancelGroup = layout.createParallelGroup(verticalAlignment)
				.addComponent(okButton)
				.addComponent(cancelButton);
		layout.linkSize(okButton, cancelButton);
		
		// set groups
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(horizontalOkCancelBtnAlignment)
						.addGroup(horizontalFormGroup)
						.addGroup(horizontalOKCancelGroup)
				)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(verticalAlignment)
						.addGroup(verticalFormGroup)
				)
				.addGap(20)
				.addGroup(verticalOKCancelGroup)
		);
	}
	
	/**
	 * Positions a form group that consists of a sole input component by adding it to the layout.
	 * <br /><br />Should only be used if <code>hasLabel() == false</code> to prevent the LayoutManager from creating an empty label group  
	 * @param inputComponent the input component to be positioned
	 * @throws NullPointerException if the layout is not initialized (i.e. the method <code>initComponents()</code> is not invoked and this method is not
	 *  overridden to be used with another LayoutManager)
	 */
	protected void addFormElement(JComponent inputComponent) throws NullPointerException {
		horizontalInputComponentGroup = horizontalInputComponentGroup.addComponent(inputComponent);
		verticalFormGroup = verticalFormGroup.addComponent(inputComponent);
	}

	/**
	 * Positions a form group that consists of a label, an input component and some other components by adding them to the layout.
	 * @param label the label to be positioned
	 * @param inputComponent the input component to be positioned
	 * @throws NullPointerException if the layout is not initialized (i.e. the method <code>initComponents()</code> is not invoked and this method is not
	 *  overridden to be used with another LayoutManager)
	 */	
	protected void addFormGroup(JLabel label, JComponent inputComponent) throws NullPointerException {
		horizontalLabelGroup = horizontalLabelGroup.addComponent(label);
		horizontalInputComponentGroup = horizontalInputComponentGroup.addComponent(inputComponent);
		verticalFormGroup = verticalFormGroup.addGroup(layout.createParallelGroup(verticalAlignment)
			.addComponent(label)
			.addComponent(inputComponent)
		);
	}
	
	/**
	 * Positions a form group that consists of a label, an input component and some other components by adding them to the layout.
	 * @param label the label to be positioned
	 * @param inputComponent the input component to be positioned
	 * @param otherComponents the additional components to be positioned
	 * @throws NullPointerException if the layout is not initialized (i.e. the method <code>initComponents()</code> is not invoked and this method is not
	 *  overridden to be used with another LayoutManager)
	 */	
	protected void addFormGroup(JLabel label, JComponent inputComponent, JComponent... otherComponents) throws NullPointerException {
		horizontalLabelGroup = horizontalLabelGroup.addComponent(label);
		
		Group horizontalInputComponentSubGroup = layout.createSequentialGroup();
		horizontalInputComponentSubGroup = horizontalInputComponentSubGroup.addComponent(inputComponent);
		for (JComponent component: otherComponents)
			horizontalInputComponentSubGroup = horizontalInputComponentSubGroup.addComponent(component);
		horizontalInputComponentGroup = horizontalInputComponentGroup.addGroup(horizontalInputComponentSubGroup);
		
		Group verticalFormSubGroup = layout.createParallelGroup(verticalAlignment);
		verticalFormSubGroup = verticalFormSubGroup.addComponent(label);
		verticalFormSubGroup = verticalFormSubGroup.addComponent(inputComponent);
		for (JComponent component: otherComponents)
			verticalFormSubGroup = verticalFormSubGroup.addComponent(component);
		verticalFormGroup = verticalFormGroup.addGroup(verticalFormSubGroup);
	}
	
}
