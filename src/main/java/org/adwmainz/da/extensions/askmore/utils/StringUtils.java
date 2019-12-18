/**
 * StringUtils.java - is a helper class providing String-related methods as used within the AskMoreXtension developed at the Digital Academy of the Academy of
 *  Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.utils;

public class StringUtils {

	/**
	 * Removes all enclosing quotes from an String (e.g. <code>"foo"</code> will be replaced with <code>foo</code>)
	 * @param str a String
	 * @return a String without enclosing quotes
	 */
	public static String removeEnclosingQuotes(String str) {
		return str.replaceAll("(?<!\\\\)\"", "");
	}
	
	/**
	 * Removes all escaped quotes from an String (e.g. <code>\"foo\"</code> will be replaced with <code>"foo"</code>)
	 * @param str a String
	 * @return a String without escaped quotes
	 */
	public static String removeEscapedQuotes(String str) {
		return str.replaceAll("\\\\\"", "\"");
	}
	
	/**
	 * Checks if a String is not <code>null</code> or empty
	 * @param str a String
	 * @return <code>true</code> if the specified String is not <code>null</code> or empty
	 */
	public static boolean isNonEmpty(String str) {
		if (str == null)
			return false;
		return (!str.isEmpty());
	}
	
	/**
	 * Removes leading, trailing and multiple whitespace (as well as line breaks) from a given String
	 * @param string a String
	 * @return a String without leading, trailing and multiple whitespace or line breaks
	 */
	public static String reduceWhitespace(String string) {
		return string
				.trim() // replace leading and trailing whitespace
				.replaceAll("[\r\n]", " ") // replace line breaks
				.replaceAll("[\\s]+", " "); // replace multiple whitespace
	}
}
