package com.tibco.cep.studio.core.grammar;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.rules.ast.IASTNodeVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public interface EntityCreatorASTVisitor extends IASTNodeVisitor<RulesASTNode> {
	
	public Entity getEntity();

}
