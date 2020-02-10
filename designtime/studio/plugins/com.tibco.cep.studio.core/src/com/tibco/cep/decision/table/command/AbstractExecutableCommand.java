/**
 * 
 */
package com.tibco.cep.decision.table.command;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.command.impl.CommandStack;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;

/**
 * @author aathalye
 *
 */
public abstract class AbstractExecutableCommand<T> implements IExecutableCommand {
	
	/**
	 * The command receiver which is same as monitored object
	 */
	protected EObject commandReceiver;
	
	/**
	 * The memento for state transitions
	 */
	protected IMemento memento;
	
	/**
	 * The parent commandReceiver.
	 * 
	 */
	protected Table parent;
	
	/**
	 * Keep reference to the command stack on which this command exists
	 */
	protected CommandStack<IExecutableCommand> ownerStack;
	
	/**
	 * Decision or Exception table
	 */
	protected TableTypes tableType;
	
	private boolean defunct;
	
	
		
	public AbstractExecutableCommand(final Table parent, 
			                         final EObject commandReceiver,
			                         final CommandStack<IExecutableCommand> ownerStack,
			                         final TableTypes tableType) {
		this.commandReceiver = commandReceiver;
		this.parent = parent;
		this.ownerStack = ownerStack;
		this.tableType = tableType;
	}


	/**
	 * @return the commandReceiver
	 */
	public final EObject getCommandReceiver() {
		return commandReceiver;
	}


	/**
	 * @return the parent
	 */
	public final Table getParent() {
		return parent;
	}


	/**
	 * @return the ownerStack
	 */
	public final CommandStack<IExecutableCommand> getOwnerStack() {
		return ownerStack;
	}


	/**
	 * @return the tableType
	 */
	public final TableTypes getTableType() {
		return tableType;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#isDefunct()
	 */
	public boolean isDefunct() {
		return defunct;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#setDefunct(boolean)
	 */
	@Override
	public void setDefunct(boolean defunct) {
		this.defunct = defunct;
	}
}
