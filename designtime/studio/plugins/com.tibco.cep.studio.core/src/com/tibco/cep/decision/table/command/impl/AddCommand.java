/**
 * 
 */
package com.tibco.cep.decision.table.command.impl;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.command.AbstractExecutableCommand;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.ICreationCommand;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.IMemento;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;

/**
 * @author aathalye
 *
 */
public class AddCommand<T> extends AbstractExecutableCommand<T> implements ICreationCommand {
	
	private ICommandCreationListener<AddCommand<T>, T> listener;
	
	private Object createdObject;
	
	/**
	 * @param parent
	 * @param commandReceiver
	 */
	public AddCommand(CommandStack<IExecutableCommand> commandStack,
			          Table parent, 
			          TableTypes tableType,
			          EObject commandReceiver, 
			          ICommandCreationListener<AddCommand<T>, T> listener) {
		super(parent, commandReceiver, commandStack, tableType);
		this.listener = listener;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICreationCommand#getCreatedObject()
	 */
	
	public Object getCreatedObject() {
		return createdObject;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#canUndo()
	 */
	
	public boolean canUndo() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.AbstractExecutableCommand#getAffectedObjects()
	 */
	public List<T> getAffectedObjects() {
		return listener.getAffectedObjects();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#execute()
	 */
	public void execute() {
		CommandEvent<AddCommand<T>> commandEvent = new CommandEvent<AddCommand<T>>(this);
		createdObject = listener.commandCreated(commandEvent);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#shouldDirty()
	 */
	
	public boolean shouldDirty() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IUndoableCommand#getValue()
	 */
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IUndoableCommand#saveMemento(com.tibco.cep.decision.table.command.IMemento)
	 */
	public void saveMemento(IMemento memento) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IUndoableCommand#undo()
	 */
	public void undo() {
		CommandEvent<AddCommand<T>> commandEvent = new CommandEvent<AddCommand<T>>(this);
		listener.commandUndone(commandEvent);
	}
}
