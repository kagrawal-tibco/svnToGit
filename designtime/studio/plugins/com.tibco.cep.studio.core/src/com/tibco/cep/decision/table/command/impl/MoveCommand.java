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
import com.tibco.cep.decision.table.command.IMoveCommand;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;

/**
 * @author aathalye
 *
 */
public class MoveCommand<T> extends AbstractExecutableCommand<T> implements IMoveCommand {
	
	/**
	 * The object moved as a result of this command execution
	 */
	private Object movedObject;
	
	private ICommandCreationListener<MoveCommand<T>, T> listener;
	
	public MoveCommand(Table parent, 
			           EObject commandReceiver,
			           CommandStack<IExecutableCommand> ownerStack, 
			           TableTypes tableType,
			           ICommandCreationListener<MoveCommand<T>, T> listener) {
		super(parent, commandReceiver, ownerStack, tableType);
		this.listener = listener;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IMoveCommand#getMovedObject()
	 */
	@Override
	public Object getMovedObject() {
		return movedObject;
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
		CommandEvent<MoveCommand<T>> commandEvent = new CommandEvent<MoveCommand<T>>(this);
		listener.commandUndone(commandEvent);
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
	@Override
	public void execute() {
		CommandEvent<MoveCommand<T>> commandEvent = new CommandEvent<MoveCommand<T>>(this);
		movedObject = listener.commandCreated(commandEvent);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#getAffectedObjects()
	 */
	@Override
	public List<?> getAffectedObjects() {
		return listener.getAffectedObjects();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#shouldDirty()
	 */
	@Override
	public boolean shouldDirty() {
		return true;
	}
}
