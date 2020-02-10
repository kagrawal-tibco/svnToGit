/**
 * 
 */
package com.tibco.cep.decision.table.command.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.command.AbstractExecutableCommand;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;

/**
 * @author aathalye
 *
 */
public class CheckPointCommand extends AbstractExecutableCommand<Table> {
	
	/**
	 * Keeping a timestamp of when it was created
	 */
	private Date creationTimeStamp;
	/**
	 * @param parent
	 * @param commandReceiver
	 */
	public CheckPointCommand(CommandStack<IExecutableCommand> commandStack,
			                 Table parent,
			                 TableTypes tableType,
			                 EObject commandReceiver,
			                 Date creationTimeStamp) {
		super(parent, commandReceiver, commandStack, tableType);
		this.creationTimeStamp = creationTimeStamp;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#execute()
	 */
	public void execute() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#shouldDirty()
	 */
	
	public boolean shouldDirty() {
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Checkpoint at " + creationTimeStamp;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#canUndo()
	 */
	public boolean canUndo() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#getAffectedObjects()
	 */
	public List<Table> getAffectedObjects() {
		return new ArrayList<Table>(0);
	}
	
}
