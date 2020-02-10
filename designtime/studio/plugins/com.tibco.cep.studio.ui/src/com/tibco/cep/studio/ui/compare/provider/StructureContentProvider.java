package com.tibco.cep.studio.ui.compare.provider;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.ui.compare.model.AbstractTreeNode;
import com.tibco.cep.studio.ui.compare.model.DestinationTreeNode;
import com.tibco.cep.studio.ui.compare.model.ExpressionTreeNode;
import com.tibco.cep.studio.ui.compare.model.PropertyDefinitionTreeNode;
import com.tibco.cep.studio.ui.compare.model.StateEntityTreeNode;
import com.tibco.cep.studio.ui.compare.model.TableRuleSetTreeNode;
import com.tibco.cep.studio.ui.compare.model.TableRuleTreeNode;
import com.tibco.cep.studio.ui.compare.model.TableRuleVariableTreeNode;
import com.tibco.cep.studio.ui.compare.util.CompareUtils;

public class StructureContentProvider implements IStructuredContentProvider, ITreeContentProvider {

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}
	
	//TODO: for Channel/Destination/Domain etc.
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof AbstractTreeNode) {
			parentElement = ((AbstractTreeNode)parentElement).getInput();
		}
		List<AbstractTreeNode> children = new ArrayList<AbstractTreeNode>();
		
		if (parentElement instanceof Concept) {
			Concept concept = (Concept) parentElement;
			EList<PropertyDefinition> properties = concept.getProperties();
			for (PropertyDefinition propertyDefinition : properties) {
				children.add(new PropertyDefinitionTreeNode(propertyDefinition, ElementPackage.CONCEPT__PROPERTIES));
			}
			return children.toArray();
		}
		
		if (parentElement instanceof Event) {
			Event event = (Event) parentElement;
			EList<PropertyDefinition> properties = event.getProperties();
			for (PropertyDefinition propertyDefinition : properties) {
				children.add(new PropertyDefinitionTreeNode(propertyDefinition, EventPackage.SIMPLE_EVENT__PROPERTIES));
			}
			return children.toArray();
		}
		
		if (parentElement instanceof Channel) {
			Channel channel = (Channel) parentElement;
			EList<Destination> destinations = channel.getDriver().getDestinations();
			for (Destination destination : destinations) {
				children.add(new DestinationTreeNode(destination, ChannelPackage.DESTINATION));
			}
			return children.toArray();	
		}
		
		if (parentElement instanceof StateMachine) {
			StateMachine stateMachine = (StateMachine) parentElement;
			EList<StateEntity> stateEntties = stateMachine.getStateEntities();
			for (StateEntity state : stateEntties) {
				children.add(new StateEntityTreeNode(state, CompareUtils.getFeatureId(state)));
			}
			return children.toArray();
		}
		
		if (parentElement instanceof StateComposite) {
			StateComposite stateComposite = (StateComposite) parentElement;
			EList<StateEntity> stateEntties = stateComposite.getStateEntities();
			for (StateEntity state : stateEntties) {
				children.add(new StateEntityTreeNode(state, CompareUtils.getFeatureId(state)));
			}
			return children.toArray();
		}
		
		if (parentElement instanceof Table) {
			Table table = (Table) parentElement;
			TableRuleSet decisionTable = table.getDecisionTable();
			if (decisionTable != null) {
				children.add(new TableRuleSetTreeNode(decisionTable, DtmodelPackage.TABLE__DECISION_TABLE, TableRuleSetTreeNode.TYPE_DECISION_TABLE));
			}
			TableRuleSet exceptionTable = table.getExceptionTable();
			if (exceptionTable != null) {
				children.add(new TableRuleSetTreeNode(exceptionTable, DtmodelPackage.TABLE__EXCEPTION_TABLE, TableRuleSetTreeNode.TYPE_EXCEPTION_TABLE));
			}
			
			return children.toArray();
		}
		if (parentElement instanceof TableRuleSet) {
			Object[] rules = ((TableRuleSet)parentElement).getRule().toArray();
			for (Object rule : rules) {
				children.add(new TableRuleTreeNode((EObject) rule, DtmodelPackage.TABLE_RULE_SET__RULE));
			}
			
			return children.toArray();
		}
		if (parentElement instanceof TableRule) {
			
			Object[] conditions = ((TableRule)parentElement).getCondition().toArray();
			for (Object condition : conditions) {
				children.add(new TableRuleVariableTreeNode((EObject) condition, DtmodelPackage.TABLE_RULE__CONDITION, TableRuleVariableTreeNode.TYPE_CONDITION));
			}
			Object[] actions = ((TableRule)parentElement).getAction().toArray();
			for (Object action : actions) {
				children.add(new TableRuleVariableTreeNode((EObject) action, DtmodelPackage.TABLE_RULE__ACTION, TableRuleVariableTreeNode.TYPE_ACTION));
			}
			
			return children.toArray();
		}
		if (parentElement instanceof TableRuleVariable) {
			children.add(new ExpressionTreeNode(((TableRuleVariable)parentElement).getExpr(), DtmodelPackage.TABLE_RULE_VARIABLE__EXPR, (TableRuleVariable)parentElement));
			return children.toArray();
		}
		return AbstractTreeNode.EMPTY_CHILDREN;
	}

	public Object getParent(Object element) {
		if (element instanceof EObject) {
			return ((EObject)element).eContainer();
		}
		return null;
	}

	public boolean hasChildren(Object element) {
		return true;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return null;
	}

}