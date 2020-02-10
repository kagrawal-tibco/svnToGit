package com.tibco.cep.decision.tree.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.tibco.cep.decision.tree.common.model.DecisionTree;
import com.tibco.cep.decision.tree.common.model.node.NodeElement;
import com.tibco.cep.decision.tree.common.model.node.terminal.Start;
import com.tibco.cep.decision.tree.core.util.DecisionTreeCoreUtil;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.core.validation.ValidationContext;


/*
@author ssailapp
@date Nov 1, 2011
 */

public class DecisionTreeValidator extends DefaultResourceValidator {

	public DecisionTreeValidator() {
	}

	@Override
	public boolean canContinue() {
		return true;
	}
	
	@Override
	public boolean validate(ValidationContext validationContext) {
		IResource resource = validationContext.getResource();	
		if (resource == null) return true;
		super.validate(validationContext);
		
		DecisionTree decisionTree = DecisionTreeCoreUtil.parseFile((IFile) resource);
		boolean valid = validateTree(decisionTree, resource);
		return valid;
	}

	private boolean validateTree(DecisionTree tree, IResource resource) {
		boolean valid = true;
		
		if (tree == null) {
			reportProblem(resource, Messages.getString("tree.invalid"), SEVERITY_ERROR);
			return false;
		}

		for (NodeElement nodeElement: tree.getNodes()) {
			if (!(nodeElement instanceof Start) && nodeElement.getInEdges().size() == 0) {
				reportProblem(resource, Messages.getString("node.inedge.zero", nodeElement.getName()), SEVERITY_ERROR);
				valid = false;
			}
		}
		return valid;
	}
	
}
