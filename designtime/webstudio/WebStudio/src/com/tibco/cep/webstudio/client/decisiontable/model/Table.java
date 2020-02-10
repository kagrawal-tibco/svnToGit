package com.tibco.cep.webstudio.client.decisiontable.model;

import java.util.List;


/**
 * 
 * @author sasahoo
 *
 */
public class Table {
	
	public static final String TABLE_TYPE = "table.type";
	public static final String DECISION_TABLE = "decision.table";
	public static final String EXCEPTION_TABLE = "exception.table";
//	public static final String CONDITION = "CONDITION";
//	public static final String ACTION = "ACTION";
//	public static final String CUSTOM_CONDITION = "CUSTOM_CONDITION";
//	public static final String CUSTOM_ACTION = "CUSTOM_ACTION";
	
	private String tableName;
	private String folder;
	private String rfimplements;
	private String since;
	private String projectName;

	private List<MetaData> tableMetaDataList;
	private List<ArgumentData> argumentList;
	
	private TableRuleSet decisionTableRuleSet;
	private TableRuleSet exceptionTableRuleSet;

	public String getName() {
		return tableName;
	}

	public void setName(String tableName) {
		this.tableName = tableName;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getImplements() {
		return rfimplements;
	}

	public void setImplements(String rfimplements) {
		this.rfimplements = rfimplements;
	}

	public List<MetaData> getMetaData() {
		return tableMetaDataList;
	}

	public void setMetaData(List<MetaData> tableMetaDataList) {
		this.tableMetaDataList = tableMetaDataList;
	}

	public List<ArgumentData> getArguments() {
		return argumentList;
	}

	public void setArguments(List<ArgumentData> argumentList) {
		this.argumentList = argumentList;
	}

	public TableRuleSet getDecisionTable() {
		return decisionTableRuleSet;
	}

	public void setDecisionTable(TableRuleSet decisionTableRuleSet) {
		this.decisionTableRuleSet = decisionTableRuleSet;
	}

	public TableRuleSet getExceptionTable() {
		return exceptionTableRuleSet;
	}

	public void setExceptionTable(TableRuleSet exceptionTableRuleSet) {
		this.exceptionTableRuleSet = exceptionTableRuleSet;
	}
	
	public String getSince() {
		return since;
	}

	public void setSince(String since) {
		this.since = since;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}