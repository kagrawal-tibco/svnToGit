package com.tibco.cep.studio.ui.compare.model;

import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;


public class ExpressionTreeNode extends AbstractTreeNode {

	private TableRuleVariable fParent;

	public ExpressionTreeNode(String input, int featureId, TableRuleVariable parent) {
		super(input, featureId);
		this.fParent= parent;
	}

	@Override
	public boolean isEqualTo(Object obj) {
		if (!(obj instanceof ExpressionTreeNode)) {
			return false;
		}
		
		if (((AbstractTreeNode)obj).getInput() instanceof String) {
			String exp1 = (String)input;
			String exp2 = (String)((AbstractTreeNode)obj).getInput();
			return exp1.equals(exp2);
		}
		
		return false;
	}

	@Override
	public boolean isUnsettable() {
		return false; // the expression can not be set to null
	}

	public TableRuleVariable getParentVariable() {
		return fParent;
	}
}
