/**
 * XMLUtils.java - is a helper class providing XML-related methods as used within the AskMoreXtension developed at the Digital Academy of the Academy of Sciences
 *  and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.utils;

public class XMLUtils {

	/**
	 * Returns an array of all XML special chars (i.e. &, <, >, ', ")
	 */
	public static char[] getSpecialChars() {
		return new char[] {'&', '<', '>', '\'', '"'};
	}
	
	/**
	 * Returns a String in which all XML special chars (&, <, >, ', ") are replaced by their escaped 
	 * references (e.g. <code>&amp;</code>)
	 * @param str a String with unescaped xml special chars
	 */
	public static String escapeXml(String str) {
		str = str.replace("&", "&amp;");
		str = str.replace("<", "&lt;");
		str = str.replace(">", "&gt;");
		str = str.replace("'", "&apos;");
		str = str.replace("\"", "&quot;");
		return str;
	}
	
	/**
	 * Returns a String in which all escaped special chars (e.g. <code>&amp;</code>) are unescaped
	 * @param str a String with escaped xml special chars
	 */
	public static String unescapeXml(String str) {
		str = str.replace("&quot;", "\"");
		str = str.replace("&apos;", "'");
		str = str.replace("&gt;", ">");
		str = str.replace("&lt;", "<");
		str = str.replace("&amp;", "&");
		return str;
	}
	
}
