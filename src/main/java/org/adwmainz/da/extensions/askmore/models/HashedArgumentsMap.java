/**
 * HashedArgumentsMap.java - is an implementation of a ro.sync.ecss.extensions.api.ArgumentsMap as used within the AskMoreXtension developed at the Digital
 *  Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.models;

import java.util.LinkedHashMap;
import java.util.List;

import ro.sync.ecss.extensions.api.ArgumentsMap;

public class HashedArgumentsMap extends LinkedHashMap<String, Object> implements ArgumentsMap {
	
	// generated serial version id
	private static final long serialVersionUID = 8054982584798591832L;


	// constructor
	/**
	 * Creates an empty HashedArgumentsMap
	 */
	public HashedArgumentsMap() {
		super();
	}
	
	/**
	 * Creates a new HashedArgumentsMap from an ArgumentsMap and a list of argument names
	 * @param args an ArgumentsMap
	 * @param argumentNames the argument names the given ArgumentsMaps stores values for 
	 */
	public HashedArgumentsMap(ArgumentsMap args, List<String> argumentNames) {
		super();
		for (String argName: argumentNames)
			this.put(argName, args.getArgumentValue(argName));
	}
	
	
	// overridden methods
	/**
	 * Returns the argument value associated with the specified argument name
	 * @param argumentName an argument name
	 */
	@Override
	public Object getArgumentValue(String argName) {
		return get(argName);
	}

}
