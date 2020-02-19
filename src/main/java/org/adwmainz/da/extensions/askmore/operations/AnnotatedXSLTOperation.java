/**
 * AnnotatedXQueryOperation.java - is an extension of a ro.sync.ecss.extensions.commons.operations.XSLTOperation which adds a custom operation to the Oxygen XML
 *  Editor that lets a user execute an XSLT script that may be adapted dynamically with an input dialog. It is one of the main classes of the AskMoreXtension
 *  developed at the Digital Academy of the Academy of Sciences and Literature | Mainz.
 * @author Patrick D. Brookshire
 * @version 1.0.0
 */
package org.adwmainz.da.extensions.askmore.operations;

import java.util.List;

import org.adwmainz.da.extensions.askmore.exceptions.InputDialogClosedException;
import org.adwmainz.da.extensions.askmore.models.HashedArgumentsMap;
import org.adwmainz.da.extensions.askmore.utils.ArgumentDescriptorUtils;
import org.adwmainz.da.extensions.askmore.utils.ArgumentParser;
import org.adwmainz.da.extensions.askmore.utils.AskMoreAnnotationParser;
import org.adwmainz.da.extensions.askmore.utils.InputDialogUtils;

import ro.sync.ecss.extensions.api.ArgumentDescriptor;
import ro.sync.ecss.extensions.api.ArgumentsMap;
import ro.sync.ecss.extensions.api.AuthorAccess;
import ro.sync.ecss.extensions.api.AuthorOperationException;
import ro.sync.ecss.extensions.commons.operations.XSLTOperation;

public class AnnotatedXSLTOperation extends XSLTOperation {

	// fields
	protected ArgumentDescriptor[] arguments;
	protected List<String> argumentNames;

	//constructor
	/**
	 * Creates a new AnnotatedXSLTOperation
	 */
	public AnnotatedXSLTOperation() {
		super();
		
		// get arguments from super class
		ArgumentDescriptor[] basicArguments = super.getArguments();
		arguments = new ArgumentDescriptor[basicArguments.length];
		for (int i=0; i<basicArguments.length; ++i)
			arguments[i] = basicArguments[i];
		
		// set argument names
		argumentNames = ArgumentDescriptorUtils.getArgumentNames(arguments);
		
		// add description of AskMoreAnnotations to ARGUMENT_SCRIPT
		String argName = ArgumentDescriptorUtils.ARGUMENT_SCRIPT;
		int argIdx = argumentNames.indexOf(argName);
		arguments[argIdx] = new ArgumentDescriptor(argName,
				basicArguments[argIdx].getType(),
				basicArguments[argIdx].getDescription()+"\n"+AskMoreAnnotationParser.getDescription()
		);
	}

	// overridden methods
	@Override
	public String getDescription() {
		return "Extends the default XQueryOperation by adding the possibility to adapt the script dynamically with an user input dialog.";
	}

	@Override
	public void doOperation(AuthorAccess authorAccess, ArgumentsMap args) throws IllegalArgumentException, AuthorOperationException {
		// get all params using the argument descriptors
		HashedArgumentsMap parsedArgs = new HashedArgumentsMap(args, argumentNames);
		
		try {
			// configure ARGUMENT_SCRIPT with an input dialog
			String parsedScript = InputDialogUtils.replaceAnnotationsWithUserInput(ArgumentParser.getValidString(args, ArgumentDescriptorUtils.ARGUMENT_SCRIPT));
			parsedArgs.put(ArgumentDescriptorUtils.ARGUMENT_SCRIPT, parsedScript);
			
			// invoke main operation from super class
			super.doOperation(authorAccess, parsedArgs);
		} catch (InputDialogClosedException e) {
			// abort action if user closes the dialog
			return;
		}
	}

	@Override
	public ArgumentDescriptor[] getArguments() {
		ArgumentDescriptor[] arguments = super.getArguments();
		return arguments;
	}

}
