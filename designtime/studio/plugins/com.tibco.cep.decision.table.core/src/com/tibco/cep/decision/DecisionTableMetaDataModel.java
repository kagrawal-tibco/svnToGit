package com.tibco.cep.decision;

import java.util.List;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;

public class DecisionTableMetaDataModel {
	/**
	 * Whether this is UI model for decision/exception table
	 */
	private TableTypes tableType;

	/**
	 * Number of custom conditions
	 */
	private int customConditionsCounter = 0;

	/**
	 * Number of custom actions
	 */
	private int customActionsCounter = 0;

	public DecisionTableMetaDataModel(TableTypes tableType) {
		this.tableType = tableType;
	}
	
	public void initializeCustomColumnCounters(Table table) {
		TableRuleSet tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? table.getDecisionTable() : table.getExceptionTable();
		Columns columnsModel = tableRuleSet.getColumns();
		if (columnsModel != null) {
			List<Column> allColumns = columnsModel.getColumn();
			for (Column column : allColumns) {
				ColumnType columnType = column.getColumnType();
				switch (columnType) {
				case CUSTOM_CONDITION :
				case CONDITION :
					customConditionsCounter++; 
					break;
					
				case CUSTOM_ACTION :
				case ACTION :
					customActionsCounter++;
					break;
				}			
			}
		}	
	}
	
	public int getNextCustomActionCount(){
		customActionsCounter++;
		return customActionsCounter;
	}

	public int getNextCustomConditionCount(){
		customConditionsCounter++;
		return customConditionsCounter;
	}

	public TableTypes getTableType() {
		return tableType;
	}
}
