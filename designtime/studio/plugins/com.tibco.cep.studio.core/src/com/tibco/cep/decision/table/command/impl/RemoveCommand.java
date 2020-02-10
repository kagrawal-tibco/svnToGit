/**
 * 
 */
package com.tibco.cep.decision.table.command.impl;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.command.AbstractExecutableCommand;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.IMemento;
import com.tibco.cep.decision.table.command.IRemovalCommand;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;

/**
 * @author aathalye
 *
 */
public class RemoveCommand<T> extends AbstractExecutableCommand<T> implements
		IRemovalCommand {
	
	/**
	 * The object removed as a result of this command execution
	 */
	private Object removedObject;
	
	private ICommandCreationListener<RemoveCommand<T>, T> listener;
	
	/**
	 * @param parent
	 * @param commandReceiver
	 * @param ownerStack
	 */
	public RemoveCommand(Table parent, 
			             TableTypes tableType,
			             EObject commandReceiver,
			             CommandStack<IExecutableCommand> ownerStack,
			             ICommandCreationListener<RemoveCommand<T>, T> listener) {
		super(parent, commandReceiver, ownerStack, tableType);
		this.listener = listener;
	}

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IRemovalCommand#getRemovedObject()
	 */
	public Object getRemovedObject() {
		return removedObject;
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
		this.memento = memento;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IUndoableCommand#undo()
	 */
	
	public void undo() {
		CommandEvent<RemoveCommand<T>> commandEvent = new CommandEvent<RemoveCommand<T>>(this);
		listener.commandUndone(commandEvent);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#canUndo()
	 */
	
	public boolean canUndo() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#execute()
	 */
	
	public void execute() {
		CommandEvent<RemoveCommand<T>> commandEvent = new CommandEvent<RemoveCommand<T>>(this);
		removedObject = listener.commandCreated(commandEvent);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#shouldDirty()
	 */
	
	public boolean shouldDirty() {
		if (listener.getAffectedObjects().isEmpty()) {
			return false;
		}
		return true;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#getAffectedObjects()
	 */
	public List<T> getAffectedObjects() {
		return listener.getAffectedObjects();
	}
}
