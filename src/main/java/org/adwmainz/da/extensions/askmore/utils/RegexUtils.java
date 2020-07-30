/**
 * RegexUtils.java - is a helper class providing regex-related methods as used within the AskMoreXtension developed at the Digital Academy of the Academy of
 *  Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.3.0
 */
package org.adwmainz.da.extensions.askmore.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RegexUtils {

	/**
	 * Returns all substrings of a given String that match a given regex pattern
	 * @param str a String
	 * @param pattern a regex pattern
	 */
	public static List<String> getMatches(String str, String pattern) {
		List<String> matches = new ArrayList<>();
		Matcher matcher = Pattern.compile(pattern).matcher(str);
		while (matcher.find())
			matches.add(matcher.group(0));
		return matches;
	}

	/**
	 * Returns the first substring of a given String that matches the nth group of n regex pattern
	 * @param str a String
	 * @param pattern a regex pattern
	 * @param groupNo the number of the group to be returned
	 * @throws IllegalArgumentException if there is no nth group
	 */
	public static String getFirstMatch(String str, String pattern, int groupNo) throws IllegalArgumentException {
		Matcher matcher = Pattern.compile(pattern).matcher(str);
		if (matcher.find())
			return matcher.group(groupNo);
		throw new IllegalArgumentException("Could not find the group with the number " +groupNo + " in "+ str);
	}

}
