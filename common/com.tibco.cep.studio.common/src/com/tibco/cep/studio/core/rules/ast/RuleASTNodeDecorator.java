/**
 * 
 */
package com.tibco.cep.studio.core.rules.ast;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.studio.core.rules.grammar.RulesParser;

/**
 * TODO Add the other wrapper methods in {@link RulesASTNode}
 * @author aathalye
 *
 */
public class RuleASTNodeDecorator<R extends RulesASTNode> extends CommonTree {
	
	/**
	 * The decorated resource
	 */
	private R wrapped;
	
	/**
	 * If this node was visited
	 */
	private boolean visited;
	
	/**
	 * Children of this resource
	 */
	private List<RuleASTNodeDecorator<R>> wrappedChildren = 
							new ArrayList<RuleASTNodeDecorator<R>>();
	
		
	@SuppressWarnings("unchecked")
	public RuleASTNodeDecorator(final R wrapped) {
		this.wrapped = wrapped;
		//List<R> children = wrapped.getChildren();
		List<R> children = null;
		/**
		 * This is not good. It should return an empty list
		 * if no children are present.
		 */
		if (children != null) {
			for (R child : children) {
				RuleASTNodeDecorator<R> wrappedChild = new RuleASTNodeDecorator<R>(child);
				wrappedChild.setParent(this);
				wrappedChildren.add(wrappedChild);
			}
		}
	}
	
	public boolean isVisited() {
		return visited;
	}
	
	public void setVisited(final boolean visited) {
		this.visited = visited;
	}
	
	public boolean hasUnvisitedChildren() {
		for (RuleASTNodeDecorator<R> child : wrappedChildren) {
			//If a single child is also unvisited return true
			if (!child.isVisited()) {
				return true;
			}
		}
		return false;
	}
	
	public List<RuleASTNodeDecorator<R>> getUnvisitedChildren() {
		List<RuleASTNodeDecorator<R>> unvisited = 
			new ArrayList<RuleASTNodeDecorator<R>>();
		if (!wrappedChildren.isEmpty()) {
			for (RuleASTNodeDecorator<R> child : wrappedChildren) {
				if (!child.isVisited()) {
					unvisited.add(child);
				}
			}
		} 
		return unvisited;
	}
	
	public R getWrappedResource() {
		return wrapped;
	}
	
	public String getNodeText() {
		return wrapped.getText();
	}
	
	/**
	 * @return whether this is a "name" token
	 * @see RulesParser#NAME
	 */
	public boolean isNameToken() {
		boolean b = RulesParser.NAME == wrapped.getType();
		return b;
	}
	
	/**
	 * @return whether this is a "rulefunction" token
	 * @see RulesParser#RULE_FUNC_DECL
	 */
	public boolean isRuleFunctionToken() {
		boolean b = RulesParser.RULE_FUNC_DECL == wrapped.getType();
		return b;
	}
	
	/**
	 * @return whether this is a "type" token
	 * @see RulesParser#TYPE
	 */
	public boolean isTypeToken() {
		boolean b = RulesParser.TYPE == wrapped.getType();
		return b;
	}
	
	/**
	 * @return whether this is a "declarator" token
	 * @see RulesParser#SCOPE_DECLARATOR
	 */
	public boolean isDeclaratorToken() {
		boolean b = RulesParser.SCOPE_DECLARATOR == wrapped.getType();
		return b;
	}
	
	/*public boolean isConceptToken() {
		return RulesParser.CONCEPT == wrapped.getType();
	}
	
	public boolean isEventToken() {
		return RulesParser.EVENT == wrapped.getType();
	}
	
	public boolean isAnnotationToken() {
		return RulesParser.ANNOTATE == wrapped.getType();
	}*/
	
	@SuppressWarnings("unchecked")
	public RuleASTNodeDecorator<R> getParent() {
		return (RuleASTNodeDecorator<R>)this.parent;
	}

	@Override
	public String toString() {
		return wrapped.getText();
	}
}
