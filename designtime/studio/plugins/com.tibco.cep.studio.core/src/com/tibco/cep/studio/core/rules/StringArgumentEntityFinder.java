package com.tibco.cep.studio.core.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.be.parser.tree.NodeType;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public class StringArgumentEntityFinder extends MethodArgumentASTVisitor {

	private List<RulesASTNode> fFoundArguments = new ArrayList<RulesASTNode>();
	private List<RulesASTNode> fFoundStringLiterals = new ArrayList<RulesASTNode>();
	private boolean fIncludeStringLiterals;
	
	public StringArgumentEntityFinder(String projectName,
			IResolutionContextProvider provider, List<EObject> targetEntities,
			String[] targetMethodNames) {
		this(projectName, provider, targetEntities, targetMethodNames, false);
		
	}
	
	public StringArgumentEntityFinder(String projectName,
			IResolutionContextProvider provider, List<EObject> targetEntities,
			String[] targetMethodNames, boolean includeStringLiterals) {
		super(projectName, provider, targetEntities, targetMethodNames);
		this.fIncludeStringLiterals = includeStringLiterals;
	}

	@Override
	protected void processFoundEntity(EObject entity, RulesASTNode argNode,
			NodeType type) {
		super.processFoundEntity(entity, argNode, type);
		if (!fFoundArguments.contains(argNode)) {
			fFoundArguments.add(argNode);
		}
	}
	
	protected void processFoundStringLiteral(EObject entity, RulesASTNode argNode) {
		super.processFoundEntity(entity, argNode, null);
		if (!fFoundStringLiterals.contains(argNode)) {
			fFoundStringLiterals.add(argNode);
		}
	}

	public List<RulesASTNode> getFoundArguments() {
		return fFoundArguments;
	}

	public List<RulesASTNode> getFoundLiterals() {
		return fFoundStringLiterals;
	}
	
	@Override
	public boolean visitStringLiteralNode(RulesASTNode node) {
		if (!fIncludeStringLiterals) {
			return false;
		}
		String text = getText(node);
		if (text != null && text.trim().length() > 0) {
			if (text.charAt(0) == '"') {
				text = text.substring(1, text.length()-1);
			}
			EObject entity = findTarget(text);
			if (entity != null) {
				processFoundStringLiteral(entity, node);
			}
		}
		return false;
	}

	@Override
	protected boolean processStringLiterals() {
		return !fIncludeStringLiterals; // don't include String arguments twice
	}

}
