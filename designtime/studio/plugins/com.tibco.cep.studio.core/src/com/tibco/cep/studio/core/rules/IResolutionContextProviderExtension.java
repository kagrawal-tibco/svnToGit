package com.tibco.cep.studio.core.rules;

import com.tibco.be.parser.tree.NodeType;

/**
 * Extension interface to IResolutionContextProvider to provide additional information
 * to the resolution process that might not be readily available from the AST.  For instance,
 * the return type of the rule
 * @author rhollom
 *
 */
public interface IResolutionContextProviderExtension {

	public NodeType getReturnType();
	
}
