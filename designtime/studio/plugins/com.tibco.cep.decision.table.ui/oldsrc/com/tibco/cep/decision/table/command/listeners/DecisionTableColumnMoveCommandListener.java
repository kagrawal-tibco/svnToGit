/**
 * 
 */
package com.tibco.cep.decision.table.command.listeners;

import java.util.ArrayList;
import java.util.List;

import com.jidesoft.decision.DecisionDataModel;
import com.jidesoft.decision.DecisionDataModelListener;
import com.jidesoft.decision.DecisionField;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.IMoveCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.impl.MoveCommand;
import com.tibco.cep.decision.table.command.memento.ColumnPositionStateMemento;
import com.tibco.cep.decision.table.model.dtmodel.Table;

/**
 * Handle backend model updates for column moves.
 * @author aathalye
 *
 */
public class DecisionTableColumnMoveCommandListener implements ICommandCreationListener<MoveCommand<ColumnPositionStateMemento>, ColumnPositionStateMemento> {
	
	/**
	 * The UI column moved
	 */
	private DecisionField fieldToMove;
	
	/**
	 * The UI model
	 */
	private DecisionDataModel decisionDataModel;
	
	/**
	 * A reference to this as this executes the actual backend model shuffle.
	 */
	private DecisionTableColumnOrderShufflingListener decisionTableColumnOrderShufflingListener;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandCreated(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	@Override
	public Object commandCreated(CommandEvent<MoveCommand<ColumnPositionStateMemento>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof IMoveCommand) {
			decisionDataModel.moveUpdateConditionFields(fieldToMove);
			decisionDataModel.moveUpdateActionFields(fieldToMove);
		}
		return null;
	}
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandUndone(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	@Override
	public void commandUndone(CommandEvent<MoveCommand<ColumnPositionStateMemento>> commandEvent) {
		//TODO Implement Undo later
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#getAffectedObjects()
	 */
	@Override
	public List<ColumnPositionStateMemento> getAffectedObjects() {
		ColumnPositionStateMemento columnPositionStateMemento = decisionTableColumnOrderShufflingListener.getMovedColumnStateMemento();
		List<ColumnPositionStateMemento> affectedObjects = new ArrayList<ColumnPositionStateMemento>(0);
		affectedObjects.add(columnPositionStateMemento);
		return affectedObjects;
	}
	
	/**
	 * 
	 * @param projectName
	 * @param tableEModel
	 * @param fieldToMove
	 * @param decisionDataModel
	 */
	public DecisionTableColumnMoveCommandListener(String projectName,
			                                      Table tableEModel,
			                                      DecisionField fieldToMove,
			                                      DecisionDataModel decisionDataModel) {
		this.fieldToMove = fieldToMove;
		this.decisionDataModel = decisionDataModel;
		registerColumnShufflingListener(projectName, tableEModel);
	}
	
	/**
	 * 
	 * @param projectName
	 * @param tableEModel
	 */
	private void registerColumnShufflingListener(String projectName, Table tableEModel) {
		DecisionDataModelListener[] decisionDataModelListeners = decisionDataModel.getDecisionDataModelListeners();
		for (DecisionDataModelListener decisionDataModelListener : decisionDataModelListeners) {
			//Check based on class name only
			if (DecisionTableColumnOrderShufflingListener.class.isAssignableFrom(decisionDataModelListener.getClass())) {
				decisionTableColumnOrderShufflingListener = (DecisionTableColumnOrderShufflingListener)decisionDataModelListener;
				return;
			}
		}
		decisionTableColumnOrderShufflingListener = 
			new DecisionTableColumnOrderShufflingListener(projectName, tableEModel, decisionDataModel.getTableType());
		decisionDataModel.addDecisionDataModelListener(decisionTableColumnOrderShufflingListener);
	}
}
