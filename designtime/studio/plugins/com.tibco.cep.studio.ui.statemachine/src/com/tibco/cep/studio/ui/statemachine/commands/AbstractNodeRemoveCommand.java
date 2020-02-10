package com.tibco.cep.studio.ui.statemachine.commands;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;

/**
 * 
 * @author sasahoo
 *
 */
@SuppressWarnings("unused")
public abstract class AbstractNodeRemoveCommand extends RemoveCommand {

	private StateMachine stateMachine;
	private StateMachineDiagramManager manager;

	/**
	 * @param manager
	 * @param domain
	 * @param list
	 * @param value
	 */
	public AbstractNodeRemoveCommand(StateMachineDiagramManager manager,
								   EditingDomain domain, 
								   EList<?> list,
			                       Object value) {
		super(domain, list, value);
		this.manager = manager;
		this.stateMachine = manager.getStateMachine();
	}

	abstract public void runForChange(StateMachineDiagramManager manager);
}