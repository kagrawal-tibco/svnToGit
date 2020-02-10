/**
 * 
 */
package com.tibco.cep.decision.table.command;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.command.impl.CommandStack;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;


/**
 * @author aathalye
 *
 */
public interface IExecutableCommand {
	
	void execute();
	
	EObject getCommandReceiver();
	
	/**
	 * The type of the table on which this command operates.
	 * @see TableTypes#DECISION_TABLE
	 * @see TableTypes#EXCEPTION_TABLE
	 * @return
	 */
	TableTypes getTableType();
	
	/**
	 * Whether execution of this command should create dirtiness
	 * @return
	 */
	boolean shouldDirty();
	
	/**
	 * Whether command can be undone or not
	 * @return
	 */
	boolean canUndo();
	
	CommandStack<IExecutableCommand> getOwnerStack();
	
	/**
	 * Return the object on which this command operated.
	 * <p>
	 * This could be the cell used for edit, or a new row added,
	 * or multiple rows added.
	 * </p>
	 * @return
	 */
	List<?> getAffectedObjects();
	
	/**
	 * Get the main model object
	 * @return
	 */
	Table getParent();
	
	/**
	 * To indicate whether this command is an active command
	 * @return
	 */
	boolean isDefunct();
	
	void setDefunct(boolean defunct);
}
