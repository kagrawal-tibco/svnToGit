package com.tibco.cep.studio.ui.compare.model;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

public class TableRuleTreeNode extends AbstractTreeNode {

	public TableRuleTreeNode(EObject input, int featureId) {
		super(input, featureId);
	}

	@Override
	public boolean isEqualTo(Object obj) {
		if (input instanceof TableRule && ((AbstractTreeNode)obj).getInput() instanceof TableRule) {
			TableRule rule1 = (TableRule) input;
			TableRule rule2 = (TableRule) ((AbstractTreeNode)obj).getInput();
			return rule1.getId().equals(rule2.getId());
		}
		return false;
	}

	@Override
	public void handleReplace(AbstractTreeNode dest,
			AbstractTreeNode src, EObject newObject) {
		// if the new node is a TableRuleVariable object, mark the new node as 'modified'
		if (newObject instanceof TableRuleVariable) {
			TableRuleVariable ruleVar = (TableRuleVariable) newObject;
			ruleVar.setModified(true);
		}
		super.handleReplace(dest, src, newObject);
	}

	@Override
	public boolean isUnsettable() {
		return true;
	}

}
