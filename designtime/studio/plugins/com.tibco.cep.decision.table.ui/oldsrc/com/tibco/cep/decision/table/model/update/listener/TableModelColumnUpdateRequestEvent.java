/**
 * 
 */
package com.tibco.cep.decision.table.model.update.listener;

import java.util.EventObject;

import com.jidesoft.decision.DecisionField;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;

/**
 * @author aathalye
 *
 */
public class TableModelColumnUpdateRequestEvent extends EventObject implements ITableModelChangeRequestEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3717249793655506147L;

	public enum TableModelColumnUpdateRequestEventType {
		NAME,
		ALIAS;
	}
	
	/**
	 * 
	 * @param source -> The {@link DecisionField}
	 * @param projectName
	 * @param tableEModel
	 * @param tableColumnUpdateRequestEventType
	 */
	public TableModelColumnUpdateRequestEvent(Object source,
			                                  String projectName,
                                              Table tableEModel,
                                              TableModelColumnUpdateRequestEventType tableColumnUpdateRequestEventType) {
		this(source, projectName, tableEModel, tableColumnUpdateRequestEventType, TableTypes.DECISION_TABLE);
	}
	
	/**
	 * 
	 * @param source -> The The {@link DecisionField}
	 * @param projectName
	 * @param tableEModel
	 * @param tableColumnUpdateRequestEventType
	 * @param tableType
	 */
	public TableModelColumnUpdateRequestEvent(Object source,
											  String projectName,
            								  Table tableEModel,
                                              TableModelColumnUpdateRequestEventType tableColumnUpdateRequestEventType,
                                              TableTypes tableType) {
		super(source);
		this.projectName = projectName;
		this.tableEModel = tableEModel;
		this.tableColumnUpdateRequestEventType = tableColumnUpdateRequestEventType;
		this.tableType = tableType;
	}
	
	private Table tableEModel;
	
	private TableTypes tableType;
	
	private String projectName;
	
	private TableModelColumnUpdateRequestEventType tableColumnUpdateRequestEventType;
		
	/**
	 * New Name of the column.
	 */
	private String changedName;
	
	/**
	 * Old Name of the column.
	 */
	private String oldName;
	
	/**
	 * New Alias of the column.
	 */
	private String changedAlias;

	/**
	 * @return the tableEModel
	 */
	public final Table getTableEModel() {
		return tableEModel;
	}

	/**
	 * @return the tableType
	 */
	public final TableTypes getTableType() {
		return tableType;
	}

	/**
	 * @return the tableColumnUpdateRequestEventType
	 */
	public final TableModelColumnUpdateRequestEventType getTableColumnUpdateRequestEventType() {
		return tableColumnUpdateRequestEventType;
	}

	/**
	 * @return the changedName
	 */
	public final String getChangedName() {
		return changedName;
	}

	/**
	 * @param changedName the changedName to set
	 */
	public final void setChangedName(String changedName) {
		this.changedName = changedName;
	}
	
	

	/**
	 * @return the projectName
	 */
	public final String getProjectName() {
		return projectName;
	}

	/**
	 * @return the oldName
	 */
	public final String getOldName() {
		return oldName;
	}

	/**
	 * @param oldName the oldName to set
	 */
	public final void setOldName(String oldName) {
		this.oldName = oldName;
	}

	/**
	 * @return the changedAlias
	 */
	public final String getChangedAlias() {
		return changedAlias;
	}

	/**
	 * @param changedAlias the changedAlias to set
	 */
	public final void setChangedAlias(String changedAlias) {
		this.changedAlias = changedAlias;
	}
}
