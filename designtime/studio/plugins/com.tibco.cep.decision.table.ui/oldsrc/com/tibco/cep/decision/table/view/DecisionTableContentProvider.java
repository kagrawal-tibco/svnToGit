package com.tibco.cep.decision.table.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

public class DecisionTableContentProvider implements
		IStructuredContentProvider, ITreeContentProvider {

	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof IViewSite) {
			IViewSite site = (IViewSite) inputElement;
			IWorkbenchWindow window = site.getWorkbenchWindow();
			ISelection selection = window.getSelectionService().getSelection();
			if (selection == null) {
				/*try {
					IViewPart part = window.getActivePage().findView(NavigationView.ID);
					if (part != null) {
						selection = ((NavigationView)part).getViewer().getSelection();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}*/
			}
			if (selection instanceof StructuredSelection) {
				Object obj = ((StructuredSelection)selection).getFirstElement();
				if (obj != null) {
					return new Object[] { obj };
				}
			}
		}
		return new Object[0];
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof DecisionTableTreeNode) {
			parentElement = ((DecisionTableTreeNode)parentElement).getInput();
		}
		List<DecisionTableTreeNode> children = new ArrayList<DecisionTableTreeNode>();
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
		return DecisionTableTreeNode.EMPTY_CHILDREN;
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

}
