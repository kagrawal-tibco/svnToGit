package com.tibco.cep.studio.ui.compare.provider;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.Expression;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateSimple;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.compare.model.AbstractTreeNode;
import com.tibco.cep.studio.ui.compare.model.TableRuleSetTreeNode;
import com.tibco.cep.studio.ui.compare.model.TableRuleVariableTreeNode;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

public class ResourceLabelProvider extends LabelProvider {

	public String getText(Object parentElement) {
		Object element = parentElement;
		if (element instanceof AbstractTreeNode) {
			element = ((AbstractTreeNode)element).getInput();
		}
		if (element instanceof Entity) {
			return ((Entity)element).getName();
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
			return "Expression value: "+((Expression)element).getVarString();
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
		if (element instanceof AbstractTreeNode) {
			element = ((AbstractTreeNode)element).getInput();
		}
		if (element instanceof Concept) {
			return StudioUIPlugin.getDefault().getImage("icons/comp/concept.png");
		}
		if (element instanceof Event) {
			return StudioUIPlugin.getDefault().getImage("icons/comp/event.png");
		}
		if (element instanceof PropertyDefinition) {
			return StudioUIUtils.getPropertyImage(((PropertyDefinition)element).getType());
		}
		if (element instanceof Channel) {
			return StudioUIPlugin.getDefault().getImage("icons/channel.png");
		}
		if (element instanceof Destination) {
			return StudioUIPlugin.getDefault().getImage("icons/destination.png");
		}
		if (element instanceof Table) {
			return StudioUIPlugin.getDefault().getImage("icons/comp/detail.gif");
		}
		if (element instanceof TableRuleSet) {
			return StudioUIPlugin.getDefault().getImage("icons/comp/function_bui.gif");
		}
		if (element instanceof TableRule) {
			return StudioUIPlugin.getDefault().getImage("icons/comp/decisiontablerulefunctions_16x16.png");
		}
		if (element instanceof TableRuleVariable) {
			return StudioUIPlugin.getDefault().getImage("icons/comp/rule-function.png");
		}
		if (element instanceof Expression || element instanceof String) {
			return StudioUIPlugin.getDefault().getImage("icons/comp/genericfunction_16x16.png");
		}
		
		if(element instanceof StateMachine){
			return StudioUIPlugin.getDefault().getImage("icons/state_machine.png");
		}
		if (element instanceof StateSimple) {
			return StudioUIPlugin.getDefault().getImage("icons/SimpleState.png");
		}
		if (element instanceof StateComposite) {
			StateComposite stateComposite = (StateComposite)element;
			if(stateComposite.isConcurrentState()){
				return StudioUIPlugin.getDefault().getImage("icons/concurrent.png");
			}
			if(stateComposite.isRegion()){
				return StudioUIPlugin.getDefault().getImage("icons/region.png");
			}
			if(stateComposite instanceof StateSubmachine){
				return StudioUIPlugin.getDefault().getImage("icons/sub_machinestate.png");
			}
			return StudioUIPlugin.getDefault().getImage("icons/composite.png");
		}
		if (element instanceof StateStart) {
			return StudioUIPlugin.getDefault().getImage("icons/StartState.png");
		}
		if (element instanceof StateEnd) {
			return StudioUIPlugin.getDefault().getImage("icons/EndState.png");
		}
		if (element instanceof State) {
			return StudioUIPlugin.getDefault().getImage("icons/SimpleState.png");
		}
		
		return null;
	}

}