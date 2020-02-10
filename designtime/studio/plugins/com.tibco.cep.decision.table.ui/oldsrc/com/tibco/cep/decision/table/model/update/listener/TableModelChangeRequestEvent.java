package com.tibco.cep.decision.table.model.update.listener;

import java.util.EventObject;

import com.jidesoft.decision.DecisionDataModel;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;

public class TableModelChangeRequestEvent extends EventObject implements ITableModelChangeRequestEvent {
	
	public enum TableModelChangeRequestEventType {
		INSERT,
		UPDATE,
		DELETE;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6316758065624958523L;
	
	/**
	 * 
	 * @param source -> The {@link TableRuleVariable}
	 * @param tableEModel
	 * @param decisionDataModel
	 * @param eventType
	 */
	public TableModelChangeRequestEvent(Object source,
			                            Table tableEModel,
			                            DecisionDataModel decisionDataModel,
			                            TableModelChangeRequestEventType eventType) {
		this(source, tableEModel, decisionDataModel, eventType, TableTypes.DECISION_TABLE);
	}
	
	/**
	 * 
	 * @param source -> The {@link TableRuleVariable}
	 * @param tableEModel
	 * @param decisionDataModel
	 * @param eventType
	 * @param tableType
	 */
	public TableModelChangeRequestEvent(Object source, 
			                            Table tableEModel,
			                            DecisionDataModel decisionDataModel, 
			                            TableModelChangeRequestEventType eventType,
			                            TableTypes tableType) {
		super(source);
		this.eventType = eventType;
		this.tableEModel = tableEModel;
		this.decisionDataModel = decisionDataModel;
		this.tableType = tableType;
	}
	
	/**
	 * Which table type was changed.
	 */
	private TableTypes tableType;
	
	private TableModelChangeRequestEventType eventType;
	
	/**
	 * Keep a reference to this model too.
	 */
	private DecisionDataModel decisionDataModel;
	
	/**
	 * The backend model to update.
	 */
	private Table tableEModel;

	/**
	 * @return the eventType
	 */
	public final TableModelChangeRequestEventType getEventType() {
		return eventType;
	}

	/**
	 * @return the tableEModel
	 */
	public final Table getTableEModel() {
		return tableEModel;
	}
	
	

	/**
	 * @return the decisionDataModel
	 */
	public final DecisionDataModel getDecisionDataModel() {
		return decisionDataModel;
	}

	/**
	 * @return the tableType
	 */
	public final TableTypes getTableType() {
		return tableType;
	}
	
	private int affectedColumn;
	
	private int firstRow;
	
	private int lastRow;

	/**
	 * @return the affectedColumn
	 */
	public final int getAffectedColumn() {
		return affectedColumn;
	}

	/**
	 * @param affectedColumn the affectedColumn to set
	 */
	public final void setAffectedColumn(int affectedColumn) {
		this.affectedColumn = affectedColumn;
	}

	/**
	 * @return the firstRow
	 */
	public final int getFirstRow() {
		return firstRow;
	}

	/**
	 * @param firstRow the firstRow to set
	 */
	public final void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	/**
	 * @return the lastRow
	 */
	public final int getLastRow() {
		return lastRow;
	}

	/**
	 * @param lastRow the lastRow to set
	 */
	public final void setLastRow(int lastRow) {
		this.lastRow = lastRow;
	}
}
