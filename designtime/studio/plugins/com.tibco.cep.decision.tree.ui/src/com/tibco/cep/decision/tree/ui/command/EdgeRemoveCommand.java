package com.tibco.cep.decision.tree.ui.command;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.cep.decision.tree.ui.editor.DecisionTreeDiagramManager;

public class EdgeRemoveCommand extends RemoveCommand{

	//private DecisionTree decisionTree;
	@SuppressWarnings("unused")
	private DecisionTreeDiagramManager manager;

	/**
	 * @param manager
	 * @param domain
	 * @param list
	 * @param value
	 */
	public EdgeRemoveCommand(DecisionTreeDiagramManager manager,
							 EditingDomain domain, 
						     EList<?> list,
			                 Object value) {
		super(domain, list, value);
		this.manager = manager;
		//this.decisionTree = manager.getDecisionTree();
	}
	public void runForChange (DecisionTreeDiagramManager manager){
		
	}
	
}
