package com.tibco.cep.studio.ui.compare.model;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.model.dtmodel.Table;

public class TableTreeNode extends AbstractTreeNode {

	public TableTreeNode(EObject input, int featureId) {
		super(input, featureId);
	}

	@Override
	public boolean isEqualTo(Object obj) {
		if (input instanceof Table && ((AbstractTreeNode)obj).getInput() instanceof Table) {
//			Table table1 = (Table) input;
//			Table table2 = (Table) ((TableChildNode)obj).input;
//			return table1.getName().equals(table2.getName());
			return true; // always return true here, or tables of different names will be considered "removed/added" rather than "changed"
		}
		return false;
	}

}
