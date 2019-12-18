/**
 * InputDialogClosedException.java - is an exception class signaling that an input dialog is closed as used within the AskMoreXtension developed at the Digital
 *  Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.exceptions;

public class InputDialogClosedException extends Exception {

	// default serial version ID
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new InputDialogClosed with <code>null</code> as its detail message.
	 */
	public InputDialogClosedException() {
		super();
	}

	/**
	 * Creates a new InputDialogClosed with the specified message.
	 * @param message the detail message
	 */
	public InputDialogClosedException(String message) {
		super(message);
	}

	/**
	 * Creates a new exception with the specified cause.
	 * @param cause the cause of this exception
	 */
	public InputDialogClosedException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new exception with the specified message and cause.
	 * @param message the detail message
	 * @param cause the cause of this exception
	 */
	public InputDialogClosedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new exception with the specified message and cause as well as suppression and 
	 * writable stack trace controls.
	 * @param message the detail message
	 * @param cause the cause of this exception
	 * @param enableSuppression controls whether or not suppression should be enabled or disabled
	 * @param writableStackTrace controls whether or not the stack trace should be writable
	 */
	public InputDialogClosedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
