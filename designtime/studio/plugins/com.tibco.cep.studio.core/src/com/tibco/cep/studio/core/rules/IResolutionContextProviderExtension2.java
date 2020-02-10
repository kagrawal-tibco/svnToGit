package com.tibco.cep.studio.core.rules;

import org.eclipse.core.resources.IFile;

/**
 * Extension interface to IResolutionContextProvider to provide additional information
 * to the resolution process that might not be readily available from the AST.  For instance,
 * the rule file itself
 * @author rhollom
 *
 */
public interface IResolutionContextProviderExtension2 {

	public IFile getRuleFile();
	
}
