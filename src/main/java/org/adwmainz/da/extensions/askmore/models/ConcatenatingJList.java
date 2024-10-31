/**
 * ConcatenatingJList.java - is an generic extension of a javax.swing.JList as used within the AskMoreXtension developed at the Digital Academy of the Academy
 *  of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.6.0
 */
package org.adwmainz.da.extensions.askmore.models;

import java.util.Vector;

import javax.swing.JList;
import javax.swing.ListModel;

public class ConcatenatingJList<T> extends JList<T> {

	// generated serial version id
	private static final long serialVersionUID = 4940774911177923632L;
	
	// additional fields
	protected String concatenator;

	// constructors
	public ConcatenatingJList() {
		super();
	}

	public ConcatenatingJList(ListModel<T> dataModel, String concatenator) {
		super(dataModel);
		this.concatenator = concatenator;
	}

	public ConcatenatingJList(T[] listData, String concatenator) {
		super(listData);
		this.concatenator = concatenator;
	}

	public ConcatenatingJList(Vector<? extends T> listData, String concatenator) {
		super(listData);
		this.concatenator = concatenator;
	}

	// additional getters and setters
	public String getConcatenator() {
		return concatenator;
	}

	public void setConcatenator(String concatenator) {
		this.concatenator = concatenator;
	}
	

}
