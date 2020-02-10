/**
 * 
 */
package com.tibco.cep.studio.core.rules.ast;

/**
 * @author rhollom
 *
 */
public interface IHeaderASTNodeVisitor<R extends RulesASTNode> extends IASTNodeVisitor<R> {
	
	boolean visitHeaderBlockNode(R node);

	boolean visitAuthorStatementNode(R node);

	boolean visitDescriptionStatementNode(R node);

}
