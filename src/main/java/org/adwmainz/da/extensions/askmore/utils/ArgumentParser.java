/**
 * ArgumentParser.java - is a helper class that validates arguments of an ro.sync.ecss.extensions.api.AuthorOperation as used within the AskMoreXtension
 *  developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.adwmainz.da.extensions.askmore.exceptions.InputDialogClosedException;
import org.adwmainz.da.extensions.askmore.models.HashedArgumentsMap;

import ro.sync.ecss.extensions.api.ArgumentsMap;
import ro.sync.ecss.extensions.api.AuthorConstants;

public class ArgumentParser {

	/**
	 * Returns a valid String argument value of an AuthorOperation
	 * @param args the ArgumentMap of the operation
	 * @param argumentName the name of the argument
	 * @throws IllegalArgumentException if the given argument is empty or otherwise invalid
	 */
	public static String getValidString(ArgumentsMap args, String argumentName) throws IllegalArgumentException {
		Object argValue = args.getArgumentValue(argumentName);
		if (!(argValue instanceof String))
			throw new IllegalArgumentException("'" + argumentName + "' is not specified as a parameter or invalid.");
		
		String argValueAsString = argValue.toString();
		if (argValueAsString.isEmpty())
			throw new IllegalArgumentException("'" + argumentName + "' is not specified as a parameter or invalid.");
		return argValueAsString;
	}
	
	/**
	 * Returns a valid String argument value of an AuthorOperation using a default value
	 * @param args the ArgumentMap of the operation
	 * @param argumentName the name of the argument
	 * @param defaultValue the value to be returned if the specified argument is empty or otherwise invalid
	 */
	public static String getValidString(ArgumentsMap args, String argumentName, String defaultValue) {
		try {
			return getValidString(args, argumentName);
		} catch (IllegalArgumentException e) {
			return defaultValue;
		}
	}
	
	/**
	 * Returns a valid String argument value of an AuthorOperation by replacing AskMoreAnnotations with user input
	 * @param args the ArgumentMap of the operation
	 * @param argumentName the name of the argument
	 * @throws IllegalArgumentException if the given argument is empty or otherwise invalid
	 * @throws InputDialogClosedException if the input dialog is closed
	 */
	public static String getValidStringWithUserInput(ArgumentsMap args, String argumentName) throws IllegalArgumentException, InputDialogClosedException {
		String argValue = getValidString(args, argumentName);
		return InputDialogUtils.replaceAnnotationsWithUserInput(argValue);
	}
	
	/**
	 * Returns a valid String argument value of an AuthorOperation using a default value by replacing AskMoreAnnotations with user input
	 * @param args the ArgumentMap of the operation
	 * @param argumentName the name of the argument
	 * @param defaultValue the value to be returned if the specified argument is empty or otherwise invalid
	 * @throws InputDialogClosedException if the input dialog is closed
	 */
	public static String getValidStringWithUserInput(ArgumentsMap args, String argumentName, String defaultValue) throws InputDialogClosedException {
		String argValue = getValidString(args, argumentName, defaultValue);
		return InputDialogUtils.replaceAnnotationsWithUserInput(argValue);
	}

	/**
	 * Returns a valid int argument value of an AuthorOperation
	 * @param args the ArgumentMap of the operation
	 * @param argumentName the name of the argument
	 * @throws IllegalArgumentException if the given argument is empty or otherwise invalid
	 */
	public static int getValidInt(ArgumentsMap args, String argumentName) throws IllegalArgumentException {
		String argValue = getValidString(args, argumentName);
		try {
			return Integer.parseInt(argValue);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * Returns a valid int argument value of an AuthorOperation using a default value
	 * @param args the ArgumentMap of the operation
	 * @param argumentName the name of the argument
	 * @param defaultValue the value to be returned if the specified argument is empty or otherwise invalid
	 */
	public static int getValidInt(ArgumentsMap args, String argumentName, int defaultValue) {
		try {
			return getValidInt(args, argumentName);
		} catch (IllegalArgumentException e) {
			return defaultValue;
		}
	}
	
	/**
	 * Returns a valid boolean argument value of an AuthorOperation as defined by <code>AuthorConstants.ARG_VALUE_TRUE</code> and
	 *  <code>AuthorConstants.ARG_VALUE_FALSE</code>
	 * @param args the ArgumentMap of the operation
	 * @param argumentName the name of the argument
	 * @throws IllegalArgumentException if the given argument is empty or otherwise invalid
	 */
	public static boolean getValidBoolean(ArgumentsMap args, String argumentName) throws IllegalArgumentException {
		String argValue = getValidString(args, argumentName);
		if (argValue.equals(AuthorConstants.ARG_VALUE_TRUE))
			return true;
		if (argValue.equals(AuthorConstants.ARG_VALUE_FALSE))
			return false;
		throw new IllegalArgumentException("'" + argumentName + "' is not specified as a parameter or invalid.");
	}
	
	/**
	 * Returns a valid boolean argument value of an AuthorOperation as defined by <code>AuthorConstants.ARG_VALUE_TRUE</code> and
	 *  <code>AuthorConstants.ARG_VALUE_FALSE</code> with a default value
	 * @param args the ArgumentMap of the operation
	 * @param argumentName the name of the argument
	 * @param defaultValue the value to be returned if the specified argument is empty or otherwise invalid
	 */
	public static boolean getValidBoolean(ArgumentsMap args, String argumentName, boolean defaultValue) {
		try {
			return getValidBoolean(args, argumentName);
		} catch (IllegalArgumentException e) {
			return defaultValue;
		}
	}
	
	/**
	 * Returns a valid List argument value of an AuthorOperation
	 * @param args the ArgumentMap of the operation
	 * @param argumentName the name of the argument
	 * @param delimiter the delimiter used to separate each argument value in the list
	 * @throws IllegalArgumentException if the given argument is empty or otherwise invalid
	 */
	public static List<String> getValidList(ArgumentsMap args, String argumentName, String delimiter) 
			throws IllegalArgumentException {
		List<String> list = new ArrayList<>();
		for (String argValue: getValidString(args, argumentName).split(delimiter))
			list.add(argValue);
		return list;
	}
	
	/**
	 * Returns a valid List argument value of an AuthorOperation that is separated by new lines
	 * @param args the ArgumentMap of the operation
	 * @param argumentName the name of the argument
	 * @throws IllegalArgumentException if the given argument is empty or otherwise invalid
	 */
	public static List<String> getValidList(ArgumentsMap args, String argumentName) 
			throws IllegalArgumentException {
		return getValidList(args, argumentName, "\n");
	}

	/**
	 * Returns a Map of two List argument values of an AuthorOperation that act as key value pairs
	 * @param args the ArgumentMap of the operation
	 * @param keyArgumentName the name of the list argument providing the keys
	 * @param valueArgumentName the name of the list argument providing the values
	 * @param delimiter the delimiter used to separate each argument value in the list
	 * @throws IllegalArgumentException if the given argument is empty or otherwise invalid
	 */
	public static Map<String,String> getValidMap(ArgumentsMap args, String keyArgumentName, String valueArgumentName, String delimiter) 
			throws IllegalArgumentException {
		List<String> keys = getValidList(args, keyArgumentName, delimiter);
		List<String> values = getValidList(args, valueArgumentName, delimiter);
		if (keys.size() != values.size())
			throw new IllegalArgumentException("The number of '"+keyArgumentName+"' params must match the number of '"+valueArgumentName+"' params");
		
		Map<String, String> map = new LinkedHashMap<>();
		for (int i=0; i<keys.size(); ++i)
			map.put(keys.get(i), values.get(i));
		return map;
	}
	
	/**
	 * Returns a Map of two List argument values of an AuthorOperation that act as key value pairs and are both separated by new lines
	 * @param args the ArgumentMap of the operation
	 * @param keyArgumentName the name of the list argument providing the keys
	 * @param valueArgumentName the name of the list argument providing the values
	 * @throws IllegalArgumentException if the given argument is empty or otherwise invalid
	 */
	public static Map<String,String> validateMappedArgs(ArgumentsMap args, String keyArgumentName, String valueArgumentName) throws IllegalArgumentException {
		return getValidMap(args, keyArgumentName, valueArgumentName, "\n");
	}
	
	/**
	 * Updates a HashedArgumentsMap by replacing AskMoreAnnotations within a specified argument with user input
	 * @param args a HashedArgumentsMap
	 * @param argumentName the name of the argument
	 * @throws IllegalArgumentException if the given argument is empty or otherwise invalid
	 * @throws InputDialogClosedException if the input dialog is closed
	 */
	public static void replaceAnnotationsWithUserInput(HashedArgumentsMap args, String argumentName) throws IllegalArgumentException, InputDialogClosedException {
		String parsedArgValue = getValidStringWithUserInput(args, argumentName);
		args.put(argumentName, parsedArgValue);
	}
	
	/**
	 * Updates a HashedArgumentsMap by replacing AskMoreAnnotations within a specified argument with user input using a default value
	 * @param args a HashedArgumentsMap
	 * @param argumentName the name of the argument
	 * @param defaultValue the value to be returned if the specified argument is empty or otherwise invalid
	 * @throws InputDialogClosedException if the input dialog is closed
	 */
	public static void replaceAnnotationsWithUserInput(HashedArgumentsMap args, String argumentName, String defaultValue) throws InputDialogClosedException {
		String parsedArgValue = getValidStringWithUserInput(args, argumentName, defaultValue);
		args.put(argumentName, parsedArgValue);
	}
	
}
