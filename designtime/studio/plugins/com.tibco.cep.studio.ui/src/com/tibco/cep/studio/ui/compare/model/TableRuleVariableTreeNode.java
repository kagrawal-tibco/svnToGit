package com.tibco.cep.studio.ui.compare.model;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.compare.preferences.ComparePreferenceConstants;

public class TableRuleVariableTreeNode extends AbstractTreeNode {

	public static final int TYPE_CONDITION	= 0;
	public static final int TYPE_ACTION		= 1;
	
	private int type;
	
	public TableRuleVariableTreeNode(EObject input, int featureId) {
		super(input, featureId);
	}

	public TableRuleVariableTreeNode(EObject input, int featureId, int type) {
		this(input, featureId);
		this.type = type;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public boolean isEqualTo(Object obj) {
		if (!(obj instanceof TableRuleVariableTreeNode)) {
			return false;
		}
		
		if (input instanceof TableRuleVariable && ((AbstractTreeNode)obj).getInput() instanceof TableRuleVariable) {
			TableRuleVariable rule1 = (TableRuleVariable) input;
			TableRuleVariable rule2 = (TableRuleVariable) ((AbstractTreeNode)obj).getInput();
			// use column ID instead of ID, as ID is reassigned after each save
			if (rule1.getColId() == null) {
				// try to use 'ID' instead
				if (rule1.getId() != null) {
					return rule1.getId().equals(rule2.getId());
				}
				return false;
			}
			return rule1.getColId().equals(rule2.getColId());
		}
		return false;
	}

	@Override
	public void handleReplace(AbstractTreeNode dest,
			AbstractTreeNode src, EObject newObject) {
		if (newObject instanceof TableRuleVariable) {
			((TableRuleVariable)newObject).setModified(true);
		}
		super.handleReplace(dest, src, newObject);
	}

	@Override
	public boolean isUnsettable() {
		if (StudioUIPlugin.getDefault().getPreferenceStore().getBoolean(ComparePreferenceConstants.COMPARE_MERGE_AUTOMERGE_COLUMNS)) {
			// The preference allows columns to be merged, and therefore set to null
			return true;
		}
		return false;
	}

}
