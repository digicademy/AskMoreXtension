/**
 * SelectableOptionFactory.java - is a factory class that creates an org.adwmainz.da.extensions.askmore.models.SelectableOption as used within the AskMoreXtension
 *  developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.factories;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.adwmainz.da.extensions.askmore.models.SelectableOption;
import org.adwmainz.da.extensions.askmore.utils.StringUtils;

public class SelectableOptionFactory {

	/**
	 * Creates a SelectableOption by parsing a String representation like <code>"real|rendered"</code>
	 * @param serializedOption the String to be parsed
	 * @param delimiter the delimiter used between real and rendered values
	 * @param realValueFirst specifies whether the String to be parsed starts with the real or rendered value 
	 * @param reduceWhitespace specifies whether whitespace should be reduced or not
	 * (i.e. <code>"real|rendered"</code> vs. <code>"rendered|real"</code>))
	 */
	public static SelectableOption<String> createOption(String serializedOption, CharSequence delimiter, boolean realValueFirst, boolean reduceWhitespace) {
		String[] values = serializedOption.split(Pattern.quote("\""+delimiter+"\""));
		switch (values.length) {
			case 1:
				if (reduceWhitespace)
					serializedOption = StringUtils.reduceWhitespace(serializedOption);
				serializedOption = StringUtils.removeEnclosingQuotes(serializedOption);
				return new SelectableOption<String>(StringUtils.removeEscapedQuotes(serializedOption));
			case 2:
				// get values
				String realValue = StringUtils.removeEnclosingQuotes(realValueFirst ? values[0] : values[1]);
				String renderedValue = StringUtils.removeEnclosingQuotes(realValueFirst ? values[1] : values[0]);
				
				// reduce whitespace
				if (reduceWhitespace) {
					realValue = StringUtils.reduceWhitespace(realValue);
					renderedValue = StringUtils.reduceWhitespace(renderedValue);
				}
				
				// return option
				return new SelectableOption<String>(StringUtils.removeEscapedQuotes(realValue), StringUtils.removeEscapedQuotes(renderedValue));
			default:
				throw new IllegalArgumentException("Could not parse "+serializedOption);
		}
	}
	
	/**
	 * Creates a set of SelectableOptions that do not differentiate between real and rendered values from a given set of elements
	 * @param <T> the type of the option (Please note that its <code>toString()</code> method will be used to create rendered values
	 * @param singleValueOptions a set of options
	 * @param sortValues specifies whether the returned set should be sorted or not
	 */
	public static <T> Set<SelectableOption<T>> createOptions(Set<T> singleValueOptions, boolean sortValues) {
		Set<SelectableOption<T>> options;
		if (sortValues)
			options = new TreeSet<>();
		else
			options = new LinkedHashSet<>();
		for (T singleValueOption: singleValueOptions) 
			options.add(new SelectableOption<>(singleValueOption));
		return options;
	}

}
