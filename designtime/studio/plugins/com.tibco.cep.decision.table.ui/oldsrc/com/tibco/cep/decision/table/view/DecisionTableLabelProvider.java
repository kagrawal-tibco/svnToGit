package com.tibco.cep.decision.table.view;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.Expression;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;

public class DecisionTableLabelProvider extends LabelProvider {

	public String getText(Object parentElement) {
		Object element = parentElement;
		if (element instanceof DecisionTableTreeNode) {
			element = ((DecisionTableTreeNode)element).getInput();
		}

		if (element instanceof Table) {
			return ((Table)element).getName();
		}
		if (element instanceof TableRuleSet) {
			TableRuleSetTreeNode node = (TableRuleSetTreeNode) parentElement;
			if (node.getType() == TableRuleSetTreeNode.TYPE_DECISION_TABLE) {
				return "Decision Table";
			} else if (node.getType() == TableRuleSetTreeNode.TYPE_EXCEPTION_TABLE) {
				return "Exception Table";
			}
			return "Table Rules";
		}
		if (element instanceof TableRule) {
			return "Rule id: "+((TableRule)element).getId();
		}
		if (element instanceof TableRuleVariable) {
			StringBuffer buffer = new StringBuffer();//getTableRuleVariableText(parentElement, element);
			buffer.append("Expression: "+((TableRuleVariable) element).getExpr());
			
			if (true) return buffer.toString();
			
//			return "TableRuleVariable id: "+((TableRuleVariable)element).getId();
		}
		if (element instanceof Expression) {
			return "Expression value: "+((Expression)element).getBodies().toString();
		}
		if (element instanceof String) {
			// assume this is from the TableRuleVariable
			return "Expression value: "+element;
		}
		if (element instanceof Argument) {
			return "Argument: direction="+((Argument)element).getDirection();
		}
		if (element == null) {
			return "null";
		}

		return element.toString();
	}

	public String getTableRuleVariableText(TableRuleVariableTreeNode node,
			TableRuleVariable element) {
		StringBuffer buffer = new StringBuffer();
		if (node.getType() == TableRuleVariableTreeNode.TYPE_ACTION) {
			TableRuleVariable trv = element;
			if (trv.getColId() != null) {
//				buffer.append("Action column id: "+trv.getColId());
				return "Action column id: "+trv.getColId();
			} else {
//				buffer.append("Action id: "+element.getId());
				return "Action id: "+element.getId();
			}
		} else if (node.getType() == TableRuleVariableTreeNode.TYPE_CONDITION) {
			TableRuleVariable trv = element;
			if (trv.getColId() != null) {
//				buffer.append("Condition column id: "+element.getColId());
				return "Condition column id: "+element.getColId();
			} else {
//				buffer.append("Condition id: "+element.getId());
				return "Condition id: "+element.getId();
			}
		}
		return buffer.toString();
	}
	
	public Image getImage(Object parentElement) {
		Object element = parentElement;
		if (element instanceof DecisionTableTreeNode) {
			element = ((DecisionTableTreeNode)element).getInput();
		}

		if (element instanceof Table) {
			return DecisionTableUIPlugin.getDefault().getImage("icons/detail.gif");
		}
		if (element instanceof TableRuleSet) {
			return DecisionTableUIPlugin.getDefault().getImage("icons/function_bui.gif");
		}
		if (element instanceof TableRule) {
			return DecisionTableUIPlugin.getDefault().getImage("icons/decisiontablerulefunctions_16x16.png");
		}
		if (element instanceof TableRuleVariable) {
			return DecisionTableUIPlugin.getDefault().getImage("icons/rule-function.png");
		}
		if (element instanceof Expression || element instanceof String) {
			return DecisionTableUIPlugin.getDefault().getImage("icons/genericfunction_16x16.png");
		}
		return null;
	}

}
