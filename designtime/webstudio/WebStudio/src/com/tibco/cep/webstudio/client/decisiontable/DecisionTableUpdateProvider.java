package com.tibco.cep.webstudio.client.decisiontable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.webstudio.client.decisiontable.model.Table;
import com.tibco.cep.webstudio.client.decisiontable.model.TableColumn;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRule;

/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTableUpdateProvider {

	/**
	 * Manage Records > Rule
	 */
	//Record > New Rule ID and TableRule map
	protected Map<String, TableRule> newRecords = new HashMap<String, TableRule>();

	//Record > Existing Rule ID - Table Columns map
	protected Map<String /*Rule IDs*/, Set<String>/*Column IDs*/> modifiedRecordColumns = new HashMap<String, Set<String>>();
	
	//Record > Existing Rule ID - TableRule
	protected Map<String, TableRule> modifiedRecords = new HashMap<String, TableRule>();

	//Record > Deleted Rule ID
	protected List<String> deletedRecords  = new ArrayList<String>();

	/**
	 * Manage Fields > Table Column
	 */
	//New Table Columns
	protected List<TableColumn> newColumns  = new ArrayList<TableColumn>();
	
	//Modified Columns
	protected List<TableColumn> modifiedColumns  = new ArrayList<TableColumn>();
	
	//Deleted Table Column Name/ID 
	protected Map<String, String> deletedColumns = new HashMap<String, String>();
	
	private Table table;
	
	public DecisionTableUpdateProvider(Table table) {
		this.table = table;
	}
	

	public Table getTable() {
		return table;
	}


	public void setTable(Table table) {
		this.table = table;
	}

	public Map<String, TableRule> getNewRecords() {
		return newRecords;
	}

	public void setNewRecords(Map<String, TableRule> newRecords) {
		this.newRecords = newRecords;
	}

	public Map<String, TableRule> getModifiedRecords() {
		return modifiedRecords;
	}

	public void setModifiedRecords(Map<String, TableRule> modifiedRecords) {
		this.modifiedRecords = modifiedRecords;
	}
	
	public Map<String, Set<String>> getModifiedRecordColumns() {
		return modifiedRecordColumns;
	}

	public void seModifiedRecordColumns(Map<String, Set<String>> modifiedRecordColumns) {
		this.modifiedRecordColumns = modifiedRecordColumns;
	}

	public List<String> getDeletedRecords() {
		return deletedRecords;
	}

	public void setDeletedRecords(List<String> deletedRecords) {
		this.deletedRecords = deletedRecords;
	}

	public List<TableColumn> getNewColumns() {
		return newColumns;
	}

	public void setNewColumns(List<TableColumn> newColumns) {
		this.newColumns = newColumns;
	}

	public List<TableColumn> getModifiedColumns() {
		return modifiedColumns;
	}

	public void setModifiedColumns(List<TableColumn> modifiedColumns) {
		this.modifiedColumns = modifiedColumns;
	}

	public Map<String, String> getDeletedColumns() {
		return deletedColumns;
	}

	public void setDeletedColumns(Map<String, String> deletedColumns) {
		this.deletedColumns = deletedColumns;
	}
	
}