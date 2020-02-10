package com.tibco.cep.studio.ui.editors.rules.text;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

// TODO : Make these node types preferences
public class ProjectionNodeFinder extends DefaultRulesASTNodeVisitor {

	List<RulesASTNode> fNodes = new ArrayList<RulesASTNode>();
	
	@Override
	public boolean visitBodyBlockNode(RulesASTNode node) {
		fNodes.add(node);
		return true;
	}

	@Override
	public boolean visitDeclareBlockNode(RulesASTNode node) {
		fNodes.add(node);
		return true;
	}

	@Override
	public boolean visitThenBlockNode(RulesASTNode node) {
		fNodes.add(node);
		return true;
	}

	@Override
	public boolean visitWhenBlockNode(RulesASTNode node) {
		fNodes.add(node);
		return true;
	}

	@Override
	public boolean visitScopeBlockNode(RulesASTNode node) {
		fNodes.add(node);
		return true;
	}

	@Override
	public boolean visitMappingBlockNode(RulesASTNode node) {
		fNodes.add(node);
		return true;
	}

	public List<RulesASTNode> getProjectionNodes() {
		return fNodes;
	}

}
