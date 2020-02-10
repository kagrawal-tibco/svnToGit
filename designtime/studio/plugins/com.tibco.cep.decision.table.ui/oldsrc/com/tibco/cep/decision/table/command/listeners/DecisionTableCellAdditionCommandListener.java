/**
 * 
 */
package com.tibco.cep.decision.table.command.listeners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.command.AbstractExecutableCommand;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.ICreationCommand;
import com.tibco.cep.decision.table.command.impl.AddCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

/**
 * This is slightly different from other command listeners as it purely
 * works on the bacnkend model unlike other ones which work with the UI
 * model.
 * @author aathalye
 *
 */
public class DecisionTableCellAdditionCommandListener implements
	ICommandCreationListener<AddCommand<TableRuleVariable>, TableRuleVariable> {
	
		
	private List<TableRuleVariable> affectedObjects;
	
	/**
	 * The model for cell to add
	 */
	private TableRuleVariable tableRuleVariable;
	
	/**
	 * Whether condition/action to add.
	 */
	private ColumnType cellType;

	
	/**
	 * @param tableRuleVariable -> The new {@link TableRuleVariable} to resulting from cell addition
	 * @param cellType -> {@link ColumnType#CONDITION} | {@link ColumnType#ACTION}
	 */
	public DecisionTableCellAdditionCommandListener(TableRuleVariable tableRuleVariable,
			                                        ColumnType cellType) {
		this.tableRuleVariable = tableRuleVariable;
		this.cellType = cellType;
		this.affectedObjects = new ArrayList<TableRuleVariable>();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandCreated(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	
	@SuppressWarnings("unchecked")
	public Object commandCreated(CommandEvent<AddCommand<TableRuleVariable>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof ICreationCommand 
				&& source instanceof AbstractExecutableCommand) {
			AbstractExecutableCommand<TableRuleVariable> command = 
								(AbstractExecutableCommand<TableRuleVariable>)source;
			EObject receiver = command.getCommandReceiver();
			if (receiver instanceof TableRule) {
				TableRule tableRule = (TableRule)receiver;
				switch (cellType) {
				case CONDITION :
				case CUSTOM_CONDITION :
					tableRule.getCondition().add(tableRuleVariable);
					break;
				case ACTION :
				case CUSTOM_ACTION :
					tableRule.getAction().add(tableRuleVariable);
					break;	
				}
			}
			affectedObjects.add(tableRuleVariable);
			//Return the same trv as created object
			return tableRuleVariable;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandUndone(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	public void commandUndone(CommandEvent<AddCommand<TableRuleVariable>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof ICreationCommand) {
			ICreationCommand command = (ICreationCommand)source;
			Object createdObject = command.getCreatedObject();
			if (createdObject instanceof TableRuleVariable) {
				//Get command's receiver
				EObject commandReceiver = command.getCommandReceiver();
				if (commandReceiver instanceof TableRule) {
					TableRule tableRule = (TableRule)commandReceiver;
					switch (cellType) {
					case CONDITION :
					case CUSTOM_CONDITION :
						tableRule.getCondition().remove(tableRuleVariable);
						break;
					case ACTION :
					case CUSTOM_ACTION :
						tableRule.getAction().remove(tableRuleVariable);
						break;	
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#getAffectedObjects()
	 */
	public List<TableRuleVariable> getAffectedObjects() {
		return affectedObjects;
	}
}
