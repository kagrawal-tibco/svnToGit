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
import com.tibco.cep.decision.table.command.IRenameCommand;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;

/**
 * @author aathalye
 *
 */
public class RenameCommand<T> extends AbstractExecutableCommand<T> implements IRenameCommand {
	
	private ICommandCreationListener<RenameCommand<T>, T> commandcreationListener;
	
	private Object renamedObject;
	
	/**
	 * 
	 * @param parent
	 * @param commandReceiver
	 * @param ownerStack
	 * @param tableType
	 * @param commandcreationListener
	 */
	public RenameCommand(Table parent,
			             EObject commandReceiver,
			             CommandStack<IExecutableCommand> ownerStack,
			             TableTypes tableType,
		                 ICommandCreationListener<RenameCommand<T>, T> commandcreationListener) {
		super(parent, commandReceiver, ownerStack, tableType);
		this.commandcreationListener = commandcreationListener;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IRenameCommand#getRenamedObject()
	 */
	@Override
	public Object getRenamedObject() {
		return renamedObject;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IUndoableCommand#getValue()
	 */
	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IUndoableCommand#saveMemento(com.tibco.cep.decision.table.command.IMemento)
	 */
	@Override
	public void saveMemento(IMemento memento) {
		this.memento = memento;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IUndoableCommand#undo()
	 */
	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return true;
	}

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#execute()
	 */
	
	public void execute() {
		CommandEvent<RenameCommand<T>> commandEvent = new CommandEvent<RenameCommand<T>>(this);
		renamedObject = commandcreationListener.commandCreated(commandEvent);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#shouldDirty()
	 */
	
	public boolean shouldDirty() {
		if (commandcreationListener.getAffectedObjects().isEmpty()) {
			return false;
		}
		return true;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#getAffectedObjects()
	 */
	public List<T> getAffectedObjects() {
		return commandcreationListener.getAffectedObjects();
	}
}
