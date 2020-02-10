/**
 * 
 */
package com.tibco.cep.decision.table.command.listeners;

import com.jidesoft.decision.DecisionDataModel;
import com.jidesoft.decision.DecisionDataModelEvent;
import com.jidesoft.decision.DecisionDataModelListener;
import com.tibco.cep.decision.table.command.memento.ColumnPositionStateMemento;
import com.tibco.cep.decision.table.model.controller.ColumnModelController;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;

/**
 * Handle shuffling of backend column indexes here.
 * @author aathalye
 *
 */
public class DecisionTableColumnOrderShufflingListener implements DecisionDataModelListener {
	
	private String projectName;
	
	/**
	 * The backend object 
	 */
	private Table tableEModel;
	
	private TableTypes tableType;
	
	/**
	 * TODO Need to find a right place for initialization of this.
	 */
	private ColumnModelController columnModelController;
	
	/**
	 * The backend column object moved represented.
	 */
	private ColumnPositionStateMemento movedColumnStateMemento;
		
	/**
	 * 
	 * @param projectName
	 * @param decisionDataModel
	 * @param tableEModel
	 * @param tableType
	 */
	public DecisionTableColumnOrderShufflingListener(String projectName,
			                                         Table tableEModel, 
			                                         TableTypes tableType) {
		this.projectName = projectName;
		this.tableEModel = tableEModel;
		this.tableType = tableType;
		columnModelController = new ColumnModelController(this.projectName);
	}

		
	/* (non-Javadoc)
	 * @see com.jidesoft.decision.DecisionDataModelListener#eventHappened(com.jidesoft.decision.DecisionDataModelEvent)
	 */
	@Override
	public void eventHappened(DecisionDataModelEvent decisionDataModelEvent) {
		DecisionDataModel decisionDataModel = decisionDataModelEvent.getDecisionDataModel();
		TableRuleSet tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();
		int type = decisionDataModelEvent.getType();
		//Get old/new indexes in case it was a move
		int beforeIndex = decisionDataModelEvent.getIndex();
		int afterIndex = decisionDataModelEvent.getEndIndex();
		switch (type) {
			case DecisionDataModelEvent.DECISION_ACTION_MOVED : {
				//In this case the indexes are inside action fields
				//so they need to be adjusted according to the backend model.
				int conditionCount = decisionDataModel.getConditionFieldCount();
				beforeIndex += conditionCount;
				afterIndex += conditionCount;
			}
			case DecisionDataModelEvent.DECISION_CONDITION_MOVED :
				updateColumnModel(tableRuleSet, beforeIndex, afterIndex);
		        break;
		}
	}
	
	/**
	 * Update backend column model so as to reshuffle columns.
	 * @param tableRuleSet
	 * @param beforeIndex
	 * @param afterIndex
	 */
	private void updateColumnModel(TableRuleSet tableRuleSet, int beforeIndex, int afterIndex) {
		//Get its columns
		Columns columnsModel = tableRuleSet.getColumns();
		Column movedColumn = columnModelController.move(columnsModel, beforeIndex, afterIndex);
		movedColumnStateMemento = new ColumnPositionStateMemento(movedColumn, beforeIndex);
		movedColumnStateMemento.setCurrentOrderIndex(afterIndex);
	}
	
	/**
	 * Return this moved object wrapper.
	 * @return
	 */
	ColumnPositionStateMemento getMovedColumnStateMemento() {
		return movedColumnStateMemento;
	}
}
