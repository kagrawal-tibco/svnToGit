package com.tibco.cep.webstudio.client.decisiontable;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableConstants.BE_DATE_TIME_FORMAT;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableConstants.DATE_FORMAT;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableConstants.TIME_FORMAT;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTablePersistenceManager.getInstance;
import static com.tibco.cep.webstudio.client.problems.ProblemMarkerUtils.addMarkers;
import static com.tibco.cep.webstudio.client.problems.ProblemMarkerUtils.removeMarker;
import static com.tibco.cep.webstudio.client.problems.ProblemMarkerUtils.updateProblemRecords;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.util.logger.problems.ProblemEvent;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.model.ArgumentData;
import com.tibco.cep.webstudio.client.decisiontable.model.ArgumentResource;
import com.tibco.cep.webstudio.client.decisiontable.model.ColumnType;
import com.tibco.cep.webstudio.client.decisiontable.model.PROPERTY_TYPES;
import com.tibco.cep.webstudio.client.decisiontable.model.ParentArgumentResource;
import com.tibco.cep.webstudio.client.decisiontable.model.Table;
import com.tibco.cep.webstudio.client.decisiontable.model.TableColumn;
import com.tibco.cep.webstudio.client.decisiontable.model.TableColumnField;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRule;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRuleSet;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRuleVariable;
import com.tibco.cep.webstudio.client.editor.TableDataGrid;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.i18n.DTMessages;
import com.tibco.cep.webstudio.client.i18n.DisplayUtils;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.problems.ProblemMarker;
import com.tibco.cep.webstudio.client.problems.ProblemRecord;
import com.tibco.cep.webstudio.client.problems.RULE_ERROR_TYPES;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.client.util.SerializeArtifact;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.client.widgets.DateTimeDialog;
/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTableUtils {

	private static DTMessages dtMsgs = (DTMessages) I18nRegistry.getResourceBundle(I18nRegistry.DT_MESSAGES);
	
	public static final RegExp COND_SUBSTITUTION_PATTERN = RegExp.compile("(.*)[(!=)><(==)\\|\\|\\&\\&][(!=)><(==)\\|\\|\\&\\&]?(.*)\\{(.*)\\}(.*)");
	public static final RegExp COND_SUBSTITUTION_PATTERN_BEFORE = RegExp.compile("(.*)\\{(.*)\\}(.*)[(!=)><(==)\\|\\|\\&\\&][(!=)><(==)\\|\\|\\&\\&]?(.*)");
	public static final RegExp ACT_SUBSTITUTION_ASSIGNMENT_PATTERN = RegExp.compile("(.*)[^><]=(.*)\\{(.*)\\}(.*)");	
	public static final RegExp ACT_SUBSTITUTION_ASSIGNMENT_PATTERN_BEFORE = RegExp.compile("(.*)\\{(.*)\\}(.*)[^><]=(.*)");	
	public static final RegExp ACT_SUBSTITUTION_EQUALITY_PATTERN = RegExp.compile("(.*)[^><]==(.*)\\{(.*)\\}(.*)");
	public static final RegExp ACT_SUBSTITUTION_EQUALITY_PATTERN_BEFORE = RegExp.compile("(.*)\\{(.*)\\}(.*)[^><]==(.*)");
	private static final String[] EMPTY_ARRAY = new String[0];
	private static final String SUBSTITUTION_DELIM = ";";
	
	public enum COND_OPERATORS {
		OR("||", "||"), AND("&&", "&&"), EQUALS("==", "=="), GT(">", "(gt)"), LT("<", "(lt)"), GTE(">=", "(gte)"), LTE("<=", "(lte)"), NE("!=", "!="), NE2("<>", "(ne)"), ASSIGNMNT("=", "=");
		
		private final String opString;
		private final String opLiteral;
		
		private COND_OPERATORS(String value, String literal) {
			this.opString = value;
			opLiteral = literal;
		}
		
		public String opString() {
			return opString;
		}

		public String opLiteral() {
			return opLiteral;
		}

		public static String ASTERISK = "*";
		
		private static final COND_OPERATORS[] VALUES_ARRAY =
			new COND_OPERATORS[] {
				OR,
				AND,
				EQUALS,
				GTE,
				LTE,
				NE,
				NE2,
				GT,
				LT,
				ASSIGNMNT
			};
		
		public static COND_OPERATORS[] getConditionalOperators(){
			return VALUES_ARRAY;
		}
		
		public static COND_OPERATORS getByOpString(String opString) {
			for (int i = 0; i < VALUES_ARRAY.length; ++i) {
				COND_OPERATORS result = VALUES_ARRAY[i];
				if (result.opString.equals(opString)) {
					return result;
				}
			}
			return null;
		}
	}	
	
	/**
	 * Fetch Records for the given RuleSet
	 * @param ruleset {@link TableRuleSet}
	 * @return
	 */
	public static ListGridRecord[] fetchRecords(TableRuleSet ruleset) {
		if (ruleset.getTableRules() == null) {
			List<TableRule> tableRules = new ArrayList<TableRule>();
			ruleset.setTableRules(tableRules);
		}
		TableRuleVariableRecord[] records = new TableRuleVariableRecord[ruleset.getTableRules().size()];
		for ( int r = 0 ; r <ruleset.getTableRules().size() ; r++) {
			TableRule rule = ruleset.getTableRules().get(r);
			TableRuleVariableRecord record = addNewRecord(rule, ruleset);
			records[r] = record;
		}
		return records;
	}
	
	public static TableRuleVariableRecord addNewRecord(TableRule rule, TableRuleSet ruleset) {
		TableRuleVariableRecord record = new TableRuleVariableRecord(rule);
		record.setAttribute("id", Long.parseLong(rule.getId()));
		//First add empty values to all rows 
		for (TableColumn col : ruleset.getColumns() ) {
			record.setAttribute(col.getName(), "");
		}
		for (TableRuleVariable var : rule.getConditions() ) {
			String colName = getColumnName( ruleset.getColumns(),var.getColumnId());
			if (colName.isEmpty()) {
				continue;
			}
			PROPERTY_TYPES type = PROPERTY_TYPES.get(Integer.parseInt(getColumn(ruleset.getColumns(), colName).getPropertyType()));
			if (type == PROPERTY_TYPES.BOOLEAN && !getColumn(ruleset.getColumns(), colName).isArrayProperty()) {
				if (var.getExpression().isEmpty()) {
					var.setExpression("false");
				}
				record.setAttribute(colName, new Boolean(var.getExpression()));
			}else if(type == PROPERTY_TYPES.BOOLEAN && getColumn(ruleset.getColumns(), colName).isArrayProperty()){
				record.setAttribute(colName, !var.getExpression().isEmpty() ? var.getExpression() : "");
			}else {
				setRecordAttributeByType(record, type, colName, var.getExpression());
			}
		}
		for (TableRuleVariable var : rule.getActions() ) {
			String colName = getColumnName(ruleset.getColumns(), var.getColumnId());
			if (colName.isEmpty()) {//At times a condition/action can have a colId of non existing column (Ref: CCApp BU DT)
				continue;
			}
			PROPERTY_TYPES type = PROPERTY_TYPES.get(Integer.parseInt(getColumn(ruleset.getColumns(), colName).getPropertyType()));
			if (var.getExpression().equalsIgnoreCase("true") || var.getExpression().equalsIgnoreCase("false")) {
				record.setAttribute(colName, new Boolean(var.getExpression()));
			} else {
				setRecordAttributeByType(record, type, colName, var.getExpression());
			}
		}
		return record;
	}
	
	/**
	 * Sets a record attribute after converting it to proper column type.
	 * @param record
	 * @param type
	 * @param colName
	 * @param expression
	 */
	public static void setRecordAttributeByType(TableRuleVariableRecord record, PROPERTY_TYPES type, String colName, String expression) {
		record.setAttribute(colName, getValueByType(type, expression));
	}
	
	/**
	 * Converts the passed String value to a Proper type (Integer/Double etc.)
	 * @param type
	 * @param value
	 * @return
	 */
	public static Object getValueByType(PROPERTY_TYPES type, String value) {
		try {
			switch(type) {
			case BOOLEAN: return new Boolean(value);
			case DOUBLE: return Double.parseDouble(value);
			case INTEGER: return Integer.parseInt(value);
			case LONG: return Long.parseLong(value);
			case DATE_TIME:
			case CONCEPT:
			case CONCEPT_ENTITY:
			case CONCEPT_REFERENCE:
			case EVENT_ENTITY:
			case STRING:
			default: return value;
			}
		}
		catch(Exception e) {
			return value;
		}
	}
	
	public static void initializeCellEnabledCache( AbstractTableEditor editor, 
			                                       ListGridRecord[] records, 
			                                       Table table, 
			                                       boolean isDecisionTable) {
		for (ListGridRecord record : records) {
			if (record instanceof TableRuleVariableRecord) {
				TableRuleVariableRecord trvRecord = (TableRuleVariableRecord)record;
				for (TableRuleVariable ruleVar : trvRecord.getRule().getConditions()) {
					cacheCellEnables(ruleVar, table, editor, isDecisionTable);
				}
				for (TableRuleVariable ruleVar : trvRecord.getRule().getActions()) {
					cacheCellEnables(ruleVar, table, editor, isDecisionTable);
				}
			}
		}
	}
	
	public static void cacheCellEnables(TableRuleVariable ruleVar, 
			                            Table table, 
			                            AbstractTableEditor editor, 
			                            boolean isDecisionTable) {
		String colName = getColumnName(isDecisionTable ? table.getDecisionTable().getColumns() 
				: table.getExceptionTable().getColumns(), ruleVar.getColumnId());
		if (!ruleVar.isEnabled()) {
			if (isDecisionTable) {
				if (!editor.getDtTableCellsToDisableMap().containsKey(ruleVar.getTableRule().getId())) {
					Set<String> colList = new HashSet<String>();
					colList.add(colName);
					editor.getDtTableCellsToDisableMap().put(ruleVar.getTableRule().getId(), colList);
				} else {
					editor.getDtTableCellsToDisableMap().get(ruleVar.getTableRule().getId()).add(colName);
				}
			} else {
				if (!editor.getExpTableCellsToDisableMap().containsKey(ruleVar.getTableRule().getId())) {
					Set<String> colList = new HashSet<String>();
					colList.add(colName);
					editor.getExpTableCellsToDisableMap().put(ruleVar.getTableRule().getId(), colList);
				} else {
					editor.getExpTableCellsToDisableMap().get(ruleVar.getTableRule().getId()).add(colName);
				}
			}
		} else {
			if (isDecisionTable) {
				if (editor.getDtTableCellsToDisableMap().containsKey(ruleVar.getTableRule().getId())) {
					if (editor.getDtTableCellsToDisableMap().get(ruleVar.getTableRule().getId()).contains(colName)) {
						editor.getDtTableCellsToDisableMap().get(ruleVar.getTableRule().getId()).remove(colName);
					}
				}
			} else {
				if (editor.getExpTableCellsToDisableMap().containsKey(ruleVar.getTableRule().getId())) {
					if (editor.getExpTableCellsToDisableMap().get(ruleVar.getTableRule().getId()).contains(colName)) {
						editor.getExpTableCellsToDisableMap().get(ruleVar.getTableRule().getId()).remove(colName);
					}
				}
			}
		}
	}
	
	public static void removeFromCellToDisableMap(TableRule rule, 
												  AbstractTableEditor editor, 
												  Table table, 
												  String columnName,
												  boolean isDecisionTable, 
												  boolean isRecordRemoval) {
		for (TableRuleVariable ruleVar : rule.getConditions()) {
			removeCacheCellEnables(ruleVar, editor, table, columnName, isDecisionTable, isRecordRemoval);
		}
		for (TableRuleVariable ruleVar : rule.getActions()) {
			removeCacheCellEnables(ruleVar, editor, table, columnName, isDecisionTable, isRecordRemoval);
		}		
	}

	public static void removeCacheCellEnables(TableRuleVariable ruleVar, 
											 AbstractTableEditor editor, 
											 Table table, 
											 String colName,
											 boolean isDecisionTable, 
											 boolean isRecordRemoval) {
		if (isDecisionTable) {
			if (editor.getDtTableCellsToDisableMap().containsKey(ruleVar.getTableRule().getId())) {
				if (isRecordRemoval) {
					editor.getDtTableCellsToDisableMap().remove(ruleVar.getTableRule().getId());
				} else {
					if (editor.getDtTableCellsToDisableMap().get(ruleVar.getTableRule().getId()).contains(colName)) {
						editor.getDtTableCellsToDisableMap().get(ruleVar.getTableRule().getId()).remove(colName);
					}
				}
			}
		} else {
			if (editor.getExpTableCellsToDisableMap().containsKey(ruleVar.getTableRule().getId())) {
				if (isRecordRemoval) {
					editor.getExpTableCellsToDisableMap().remove(ruleVar.getTableRule().getId());
				} else {
					if (editor.getExpTableCellsToDisableMap().get(ruleVar.getTableRule().getId()).contains(colName)) {
						editor.getExpTableCellsToDisableMap().get(ruleVar.getTableRule().getId()).remove(colName);
					}
				}
			}
		}
	}
	
	
	/**
	 * @param newruleId
	 * @param newRule
	 * @param dtAreaColumns
	 * @param vars
	 */
	public static void addNewTableRule(String newruleId, 
										TableRule newRule, 
										Map<Integer, TableColumn> dtAreaColumns, 
										List<TableRuleVariable> vars) {
		for (int index : dtAreaColumns.keySet()) {
			String condId = Integer.toString(index);
			String tableRulevarId = newruleId + "_" + condId;
			TableRuleVariable ruleVar = null;
			String defaultText;
			if(dtAreaColumns.get(index).getDefaultCellText()==null){
				defaultText = "";
			}else if(dtAreaColumns.get(index).getDefaultCellText().equalsIgnoreCase("null")){
				defaultText = "";
			}else{
				defaultText = dtAreaColumns.get(index).getDefaultCellText();
			}
			ruleVar = new TableRuleVariable(tableRulevarId, condId, defaultText, true, "", newRule);
			vars.add(ruleVar);
		}
	}
	
	/**
	 * Populate Fields
	 * @param ruleset
	 * @param conditionAreaIndices
	 * @param conditionAreaColumns
	 * @param actionAreaIndices
	 * @param actionAreaColumns
	 * @return
	 */
	public static ListGridField[] populateFields(TableRuleSet ruleset, 
			                                     List<Integer> conditionAreaIndices, 
			                                     Map<Integer, TableColumn> conditionAreaColumns, 
			                                     List<Integer> actionAreaIndices, 
			                                     Map<Integer, TableColumn> actionAreaColumns, 
			                                     Map<Integer, String> dtConditionAreaFieldMap, 
			                                     Map<Integer, String> dtActionAreaFieldMap,
			                                     List<ArgumentData> arguments) {
		ListGridField[] fields = new ListGridField[ruleset.getColumns().size() + 1];
		ListGridField idfield = new ListGridField("id", "ID", 10);
		idfield.setCanEdit(false);
		fields[0] = idfield;
		for (int c= 0; c < ruleset.getColumns().size(); c++) {
			TableColumn column = ruleset.getColumns().get(c);
			if (column.getColumnType().trim().equals(ColumnType.CONDITION.getName().trim()) 
					|| column.getColumnType().trim().equals(ColumnType.CUSTOM_CONDITION.getName().trim())) {
				int id = Integer.parseInt(column.getId());
				conditionAreaIndices.add(id);
				conditionAreaColumns.put(id, column);
			}
			if (column.getColumnType().trim().equals(ColumnType.ACTION.getName().trim()) 
					|| column.getColumnType().trim().equals(ColumnType.CUSTOM_ACTION.getName().trim())) {
				int id = Integer.parseInt(column.getId());
				actionAreaIndices.add(id);
				actionAreaColumns.put(id, column);
			}
		}
		
		//Loading Conditions First, then Actions
		int lastIndex = -1;
		for (int i = 0; i < conditionAreaIndices.size() ; i++) {
			int index = conditionAreaIndices.get(i);
			TableColumn col = conditionAreaColumns.get(index);
			TableColumnField field = new TableColumnField(col, 100, arguments);
			lastIndex = i + 1;
			dtConditionAreaFieldMap.put(lastIndex, col.getName());
			fields[lastIndex] = field;
		}

		for (int i = 0; i < actionAreaIndices.size() ; i++) {
			int index = actionAreaIndices.get(i);
			TableColumn col = actionAreaColumns.get(index);
			TableColumnField field = new TableColumnField(col, 100, arguments);
			lastIndex = lastIndex + 1;
			dtActionAreaFieldMap.put(lastIndex, col.getName());
			fields[lastIndex] = field;
		}
		return fields;
	}
	
	public static String adjustColumnName(String colName, boolean switchDelimeter) {
		if (switchDelimeter) {
			if (colName.contains(DecisionTableConstants.ACTION_COLUMN_DELIMITER)) {
				colName = colName.replace(DecisionTableConstants.ACTION_COLUMN_DELIMITER, CommonIndexUtils.DOT);
			}
			if (colName.contains(DecisionTableConstants.CONDITION_COLUMN_DELIMITER)) {
				colName = colName.replace(DecisionTableConstants.CONDITION_COLUMN_DELIMITER, CommonIndexUtils.DOT);
			}
		} else {
			if (colName.contains(CommonIndexUtils.DOT)) {
				colName = colName.replace(CommonIndexUtils.DOT, DecisionTableConstants.CONDITION_COLUMN_DELIMITER);
			}
		}
		return colName;
	}
	
	public static String adjustActionColumnName(String colName) {
		if (colName.contains(DecisionTableConstants.CONDITION_COLUMN_DELIMITER)) {
			colName = colName.replace(DecisionTableConstants.CONDITION_COLUMN_DELIMITER, DecisionTableConstants.ACTION_COLUMN_DELIMITER);
		}
		return colName;
	}
	
	/**
	 * Get Column Name for the given column Id from the list of Columns
	 * @param columns
	 * @param colId
	 * @return
	 */
	public static String getColumnName(List<TableColumn> columns,  String colId) {
		for (TableColumn col : columns ) {
			if (col.getId().equals(colId)) {
				return col.getName();
			}
		}
		return "";
	}
	
	/**
	 * @param columns
	 * @param colName
	 * @return
	 */
	public static TableColumn getColumn(List<TableColumn> columns,  String colName) {
		for (TableColumn col : columns ) {
			if (col.getName().equals(colName)) {
				return col;
			}
		}
		return null;
	}
	
	public static List<TableColumn> getColumns(List<TableColumn> columns,  String colName) {
		List<TableColumn> tableColumns = new ArrayList<TableColumn>();
		for (TableColumn col : columns ) {
			if (col.getName().equals(colName)) {
				tableColumns.add(col);
			}
		}
		return tableColumns;
	}
	
	public static int getColumnNameMaxCount(List<TableColumn> columns,  String colName) {
		List<Integer> counts = new ArrayList<Integer>();
		for (TableColumn col : columns ) {
			if (col.getName().startsWith(colName)) {
				int s = col.getName().lastIndexOf("_");
				if (s != -1) {
					String cnt = col.getName().substring(s + 1);
					counts.add(Integer.parseInt(cnt));
				} else {
					counts.add(0);
				}
			}
		}
		if (counts.size() == 0) {
			return 0;
		} else {
			return Collections.max(counts) + 1;
		}
	}
	
	
	public static int getColumnMaxID(List<TableColumn> columns) {
		List<Integer> counts = new ArrayList<Integer>();
		for (TableColumn col : columns ) {
			counts.add(Integer.parseInt(col.getId()));
		}
		if (counts.size() == 0) {
			return 1;
		} else {
			return Collections.max(counts) + 1;
		}
	}
	
	
	/**
	 * @param ruleSet
	 * @return
	 */
	public static List<TableColumn> getConditionColumns(TableRuleSet ruleSet) {
		List<TableColumn> tableColumns = new ArrayList<TableColumn>();
		for (TableColumn column : ruleSet.getColumns()) {
			if (column.getColumnType().equals(ColumnType.CONDITION.getName()) 
					|| column.getColumnType().equals(ColumnType.CUSTOM_CONDITION.getName())) {
				tableColumns.add(column);
			}
		}
		return tableColumns;
	}
	
	/**
	 * @param ruleSet
	 * @return
	 */
	public static List<TableColumn> getActionColumns(TableRuleSet ruleSet) {
		List<TableColumn> tableColumns = new ArrayList<TableColumn>();
		for (TableColumn column : ruleSet.getColumns()) {
			if (column.getColumnType().equals(ColumnType.ACTION.getName()) 
					|| column.getColumnType().equals(ColumnType.CUSTOM_ACTION.getName())) {
				tableColumns.add(column);
			}
		}
		return tableColumns;
	}
	
	/**
	 * @param rule
	 * @param colId
	 * @return
	 */
	public static TableRuleVariable getTableRuleVariable(TableRule rule, String colId) {
		for (TableRuleVariable ruleVar : rule.getConditions()) {
			if (ruleVar.getColumnId().equals(colId)) {
				return ruleVar;
			}
		}
		for (TableRuleVariable ruleVar : rule.getActions()) {
			if (ruleVar.getColumnId().equals(colId)) {
				return ruleVar;
			}
		}
		return null;
	}
	
	/**
	 * @param type
	 * @param columnName
	 * @return
	 */
	public static DataSourceField getDataSourceField(PROPERTY_TYPES type, String columnName, String columnTitle, boolean isArray) {
		String fieldName = columnName;
		String fieldTitle = adjustColumnName(columnTitle, true);
		switch (type) {
		case INTEGER:
			return new DataSourceTextField(fieldName, fieldTitle);
		case DOUBLE:
			return new DataSourceTextField(fieldName, fieldTitle);
		case LONG:
			return new DataSourceTextField(fieldName, fieldTitle);
		case BOOLEAN:
			if(!isArray){
			   return new DataSourceBooleanField(fieldName, fieldTitle);
			}else{
			   return new DataSourceTextField(fieldName, fieldTitle);	
			}
		case DATE_TIME:
			return new DataSourceTextField(fieldName, fieldTitle);
		case STRING:
			return new DataSourceTextField(fieldName, fieldTitle);
		default:
			break;
		}
		return new DataSourceTextField(fieldName, fieldTitle);
	}

	public static void indeterminateProgress(String message, boolean done) {
		if (done) {
			SC.clearPrompt();
		} else {
			SC.showPrompt(Canvas.imgHTML(Page.getAppImgDir() + "icons/16/wait.gif") + message);
		}
	}
	
	public static Date getDate(String text) {
		Date date = null;
		try {
			date = BE_DATE_TIME_FORMAT.parseStrict(text.trim());
		} catch(Exception e){
			e.printStackTrace();
		}		
		return date;
	}
	
	public static String getBEDate(Date date) {
		return BE_DATE_TIME_FORMAT.format(date);
	}

	public static String getBEDateTime(Date date) {
		return DATE_FORMAT.format(date) + "T" + TIME_FORMAT.format(date);
	}
	
	/**
	 * @param property
	 * @return
	 */
	public static String getArgumentIcon(PROPERTY_TYPES property) {
		String path = Page.getAppImgDir() + "icons/16/";
		switch(property) {
			case CONCEPT_ENTITY:
				return path + "concept_argument.png";
			case EVENT_ENTITY:
				return path + "event_argument.png";
			case STRING:
				return path + "string_argument.png";
			case INTEGER:	
				return path + "int_argument.png";
			case LONG:
				return path + "long_argument.png";
			case DOUBLE:
				return path + "double_argument.png";
			case BOOLEAN:
				return path + "boolean_argument.png";
			case DATE_TIME:
				return path + "datetime_argument.png";
			case CONCEPT:
				return path + "concept_argument.png";
			case CONCEPT_REFERENCE:	
				return path + "concept_argument.png";
			default:
				return path + "argument.png";

		}
	}
	
	/**
	 * @param coltype
	 * @param property
	 * @return
	 */
	public static String getPropertyIcon(ColumnType coltype, PROPERTY_TYPES property) {
		if (coltype ==  ColumnType.CUSTOM_CONDITION 
				|| coltype == ColumnType.CUSTOM_ACTION) {
			return null;
		}
		String path = Page.getAppImgDir() + "icons/16/";
		switch(property) {
			case STRING:
				return path + "iconString16.gif";
			case INTEGER:	
				return path + "iconInteger16.gif";
			case LONG:
				return path + "iconLong16.gif";
			case DOUBLE:
				return path + "iconReal16.gif";
			case BOOLEAN:
				return path + "iconBoolean16.gif";
			case DATE_TIME:
				return path + "iconDate16.gif";
			case CONCEPT:
				return path + "iconConcept16.gif";
			case CONCEPT_REFERENCE:	
				return path + "iconConceptRef16.gif";
			default:
				return null;

		}
	}
	
	/**
	 * @param selectedResource
	 */
	public static void getVirtualRuleFunction(NavigatorResource selectedResource) {
		String projectName = selectedResource.getId().substring(0,selectedResource.getId().indexOf("$"));
		String path = selectedResource.getId().substring(selectedResource.getId().indexOf("$"), selectedResource.getId().length()).replace("$", "/");
		String virtualRuleFunctionPath = path.substring(path.indexOf("/"), path.lastIndexOf('.'));
		String type = path.substring(path.lastIndexOf('.') + 1, path.length());
		
		HttpRequest request = new HttpRequest();
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectName));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, virtualRuleFunctionPath));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_TYPE, ARTIFACT_TYPES.valueOf(type.toUpperCase())));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_FILE_EXTN, type));
		
		request.submit(ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL());
	}

	/**
	 * @param selectedResource
	 */
	public static void getArguments(NavigatorResource selectedResource) {
		String projectName = selectedResource.getId().substring(0,selectedResource.getId().indexOf("$"));
		String parent = selectedResource.getParent() == null ? selectedResource.parseParentId():selectedResource.getParent();
		String path = (parent + "$" + selectedResource.getName()).replace("$", "/");
		String ruleFunctionImplPath = path.substring(path.indexOf("/"), path.lastIndexOf('.'));
		String type = path.substring(path.lastIndexOf('.') + 1, path.length());
		
		HttpRequest request = new HttpRequest();
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectName));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, ruleFunctionImplPath));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_TYPE, ARTIFACT_TYPES.valueOf(type.toUpperCase())));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_FILE_EXTN, type));
		
		request.submit(ServerEndpoints.RMS_SHOW_DT_ARGUMENTS.getURL());
	}
	
	/**
	 * @param selectedResource
	 */
	public static void getArgumentProperties(NavigatorResource selectedResource) {
		if (selectedResource instanceof ParentArgumentNavigatorResource) {
			String projectName = selectedResource.getId().substring(0,selectedResource.getId().indexOf("$"));
			ParentArgumentResource resource = (ParentArgumentResource) ((ParentArgumentNavigatorResource)selectedResource).getResource();
			String path = null;
			String type = null;
			if (PROPERTY_TYPES.get(resource.getType()) == PROPERTY_TYPES.CONCEPT 
					|| PROPERTY_TYPES.get(resource.getType()) == PROPERTY_TYPES.CONCEPT_REFERENCE) { //Reference & Contained concept in the Argument concept
				path = resource.getConceptTypePath();
				type = ARTIFACT_TYPES.CONCEPT.toString();
			} else if (PROPERTY_TYPES.get(resource.getType()) == PROPERTY_TYPES.CONCEPT_ENTITY) { //Argument Concept
				path = resource.getPath() + resource.getName();
				type = ARTIFACT_TYPES.CONCEPT.toString();
			} else if (PROPERTY_TYPES.get(resource.getType()) == PROPERTY_TYPES.EVENT_ENTITY) { //Argument Event
				path = resource.getPath() + resource.getName();
				type = ARTIFACT_TYPES.EVENT.toString();				
			}

			HttpRequest request = new HttpRequest();
			request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectName));
			request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, path));
			request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_TYPE, type));
			request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_FILE_EXTN, type.toLowerCase()));

			request.submit(ServerEndpoints.RMS_SHOW_DT_ARGUMENTS.getURL());
		}
	}

	/**
	 * @param selectedResource
	 * @param doc
	 * @param fetchProperties
	 */
	public static void populateArguments(NavigatorResource selectedResource, Element doc, boolean fetchProperties) {
		List<ArgumentResource> arguments = null;
		String displayText = null;
		
		String projectName = selectedResource.getId().substring(0, selectedResource.getId().indexOf("$"));
		
		if (fetchProperties) {
			NodeList argumentNodeList = doc.getElementsByTagName("argument");
			if (argumentNodeList.getLength() > 0) {
				arguments = DecisionTableModelLoader.getArgumentProperties(argumentNodeList.item(0), 
						(ParentArgumentResource)((ParentArgumentNavigatorResource)selectedResource).getResource());
			}
			if (arguments != null) {
				for (ArgumentResource argres: arguments) {
					ArgumentNavigatorResource argNavResource = null;
					
					if (DisplayUtils.isHidden(projectName, argres.getOwnerPath(), argres.getName())) continue;
					
					if (argres instanceof ParentArgumentResource) {
						displayText = DisplayUtils.getDisplayText(projectName, ((ParentArgumentResource)argres).getPath() + argres.getName(), null);
						argNavResource = new ParentArgumentNavigatorResource(displayText, selectedResource.getId(), 
								selectedResource.getId() + "$" + argres.getName() , 
								argres.getType(), null, ARTIFACT_TYPES.ARGUMENT, argres);
					} else {
						
						displayText = DisplayUtils.getDisplayText(projectName, argres.getOwnerPath(), argres.getName());
						argNavResource = new ArgumentNavigatorResource(displayText, selectedResource.getId(), 
								selectedResource.getId() + "$" + argres.getName() , 
								argres.getType(), null, ARTIFACT_TYPES.ARGUMENT, argres);
					}
					selectedResource.getChildren().add(argNavResource);
					WebStudio.get().getWorkspacePage().getGroupContentsWindow().add(argNavResource);
					ProjectExplorerUtil.updateGroups(argNavResource, true);
				}
			}	
		}  else {
			arguments = DecisionTableModelLoader.getArguments(doc);
			for (ArgumentResource resource: arguments) {
				if (resource instanceof ParentArgumentResource) {
					ParentArgumentResource pResource = (ParentArgumentResource)resource;
					
					displayText = DisplayUtils.getDisplayText(projectName, pResource.getPath()+pResource.getName(), null);
					String id = null;
					if(pResource.getAlias() != null && !pResource.getAlias().isEmpty()) {
						id = selectedResource.getId() + "$" + pResource.getAlias();
					} else {
						id = selectedResource.getId() + "$" + pResource.getName();
					}
					ParentArgumentNavigatorResource parentNavResource = new ParentArgumentNavigatorResource(displayText, selectedResource.getId(), 
							id , pResource.getType(), null, null, pResource);
					selectedResource.getChildren().add(parentNavResource);
					WebStudio.get().getWorkspacePage().getGroupContentsWindow().add(parentNavResource);
					ProjectExplorerUtil.updateGroups(parentNavResource, true);
					
					for (ArgumentResource argres : pResource.getChildren()) {
						ArgumentNavigatorResource argNavResource = null;
						
						if (DisplayUtils.isHidden(projectName, argres.getOwnerPath(), argres.getName())) continue;
						ParentArgumentResource parent = argres.getParent();
						displayText = DisplayUtils.getDisplayText(projectName, parent.getPath()+parent.getName(), argres.getName());
						
						if (argres instanceof ParentArgumentResource) {
//							displayText = DisplayUtils.getDisplayText(projectName, ((ParentArgumentResource)argres).getPath() + "/" + argres.getName(), null);
							argNavResource = new ParentArgumentNavigatorResource(displayText, parentNavResource.getId(), 
									parentNavResource.getId() + "$" + argres.getName() , 
									argres.getType(), null, ARTIFACT_TYPES.ARGUMENT, argres);
						} else {
//							displayText = DisplayUtils.getDisplayText(projectName, parent.getPath()+parent.getName(), argres.getName());
							if (argres.isArray() && !(PROPERTY_TYPES.get(argres.getType()) == PROPERTY_TYPES.CONCEPT 
																			|| PROPERTY_TYPES.get(argres.getType()) == PROPERTY_TYPES.CONCEPT_REFERENCE)) {
								displayText = displayText + "[]";
							}
							argNavResource = new ArgumentNavigatorResource(displayText, parentNavResource.getId(), 
									parentNavResource.getId() + "$" + argres.getName() , 
									argres.getType(), null, ARTIFACT_TYPES.ARGUMENT, argres);
						}
						parentNavResource.getChildren().add(argNavResource);
						WebStudio.get().getWorkspacePage().getGroupContentsWindow().add(argNavResource);
						ProjectExplorerUtil.updateGroups(argNavResource, true);
					}
				}
				if (resource.isPrimitive()) {
					displayText = DisplayUtils.getDisplayText(projectName, resource.getOwnerPath(), resource.getName());
					if (resource.isArray()) {
						displayText = displayText + "[]";
					}
					ArgumentNavigatorResource argNavResource = new ArgumentNavigatorResource(displayText, selectedResource.getId(), 
							selectedResource.getId() + "$" + resource.getName() , 
							resource.getType(), null, ARTIFACT_TYPES.ARGUMENT, resource);
					selectedResource.getChildren().add(argNavResource);
					WebStudio.get().getWorkspacePage().getGroupContentsWindow().add(argNavResource);
					ProjectExplorerUtil.updateGroups(argNavResource, true);
				}
			}
		}
		indeterminateProgress(dtMsgs.dtArgumentFetchProgressDone(), true);
	}
	
	/**
	 * @param columns
	 * @param colName
	 * @param isConditionArea
	 * @return
	 */
	public static boolean isDuplicateColumn(List<TableColumn> columns,  String colName, boolean isConditionArea) {
		TableColumn column = getColumn(columns, colName);
		if (column != null) {
			boolean present = isConditionArea ? (ColumnType.get(column.getColumnType()) == ColumnType.CONDITION ||
					ColumnType.get(column.getColumnType()) == ColumnType.CUSTOM_CONDITION) :
						(ColumnType.get(column.getColumnType()) == ColumnType.ACTION ||
					ColumnType.get(column.getColumnType()) == ColumnType.CUSTOM_ACTION);
			if (present){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param table
	 * @param dtupdateprovider
	 * @param incremental
	 * @param selectedResource
	 * @param request
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void saveDecisionTable(Table table, 
										 DecisionTableUpdateProvider dtupdateprovider, 
										 boolean incremental, 
										 boolean isSyncMerge,
										 NavigatorResource selectedResource, 
										 HttpRequest request){
		DecisionTableDataItem decisionTableDataItem = new DecisionTableDataItem(table, dtupdateprovider, incremental, isSyncMerge);
		String projectName = selectedResource.getId().substring(0,selectedResource.getId().indexOf("$"));
		
		Map<String, Object> requestParameters = new HashMap<String, Object>();
		requestParameters.put(RequestParameter.REQUEST_PROJECT_NAME, projectName);
		requestParameters.put(RequestParameter.REQUEST_ARTIFACT_DATA_ITEM, decisionTableDataItem);

		String xmlData = new SerializeArtifact().generateXML(ServerEndpoints.RMS_POST_ARTIFACT_SAVE, requestParameters);
		
		if (request == null) {
			request = new HttpRequest();
		}
		request.clearParameters();
		request.setPostData(xmlData);
		request.setMethod(HttpMethod.POST);
		
		request.submit(ServerEndpoints.RMS_POST_ARTIFACT_SAVE.getURL());
	}

	/**
	 * @param selectedResource
	 * @param table
	 * @param request
	 */
	public static void validateDecisionTable(NavigatorResource selectedResource, Table table, HttpRequest request) {
		String projectName = selectedResource.getId().substring(0,selectedResource.getId().indexOf("$"));
		String path = (selectedResource.getParent() + "$" + selectedResource.getName()).replace("$", "/");
		if (selectedResource.getParent() == null) {
			path = projectName + table.getFolder() + selectedResource.getName();
		}
		String ruleFunctionImplPath = path.substring(path.indexOf("/"), path.lastIndexOf('.'));
		String type = path.substring(path.lastIndexOf('.') + 1, path.length());
		
		if (request == null) {
			request = new HttpRequest();
		}
		
		request.clearParameters();
		request.setMethod(HttpMethod.GET);
		request.setPostData(null);
		
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectName));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, ruleFunctionImplPath));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_TYPE, ARTIFACT_TYPES.valueOf(type.toUpperCase())));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_FILE_EXTN, type));

		request.submit(ServerEndpoints.RMS_VALIDATE.getURL());
	}
	
	/**
	 * The errors is the following format
	   <errors>
	     <ruleerror message="&quot;[&quot; expected" rowId="1" colId="1" type="0"/>
	     <ruleerror message="&quot;[&quot; expected" rowId="2" colId="1" type="0"/>
		</errors>
	 * @param doc
	 * @param table
	 * @param projectName
	 * @return
	 */
	public static String postProblems(Element docElement, Table table) {
		String msg = "";
		Node rootNode = docElement.getElementsByTagName("artifactDetails").item(0);
		
		String projectName = docElement.getElementsByTagName("projectName").item(0).getFirstChild().getNodeValue();
		String artifactPath = docElement.getElementsByTagName("artifactPath").item(0).getFirstChild().getNodeValue();
		String uri = projectName +  artifactPath;
		
		NodeList problemsNode = rootNode.getOwnerDocument().getElementsByTagName("problem");
		List<String> types = new ArrayList<String>();
		types.add(RULE_ERROR_TYPES.SEMANTIC_TYPE.getLiteral());
		types.add(RULE_ERROR_TYPES.SYNTAX_TYPE.getLiteral());
		removeMarker(uri, types, false);
		
		Map<String, List<DecisionTableRuleError>> pageToErrorsMap = new HashMap<String, List<DecisionTableRuleError>>();
		if (problemsNode.getLength() == 0) {
			updateProblemRecords(null, null, true);
		} else {
			String pageNum = null;
			for (int i = 0; i < problemsNode.getLength(); i++) {
				Node problemNode = problemsNode.item(i);
				DecisionTableRuleError ruleError = loadProblem(problemNode);
				NodeList problemChildNodes = problemNode.getChildNodes();
				for (int j = 0; j < problemChildNodes.getLength(); j++) {
					Node problemChildNode = problemChildNodes.item(j);
					if (problemChildNode.getNodeType() == Node.ELEMENT_NODE && problemChildNode.getFirstChild() != null 
																						&& problemChildNode.getNodeName().equals("pageNum")) {					
						pageNum = problemChildNode.getFirstChild().getNodeValue();
						break;
					}
				}

				List<DecisionTableRuleError> problems = pageToErrorsMap.get(pageNum);
				if (problems == null) {
					problems = new ArrayList<DecisionTableRuleError>();
					pageToErrorsMap.put(pageNum, problems);
				}
				problems.add(ruleError);				
			}
		}

		List<ProblemRecord> warnrecords = new ArrayList<ProblemRecord>();
		List<ProblemRecord> errorrecords = new ArrayList<ProblemRecord>();
		Set<String> pageNums = pageToErrorsMap.keySet();
		for (String pageNum : pageNums) {
			List<DecisionTableRuleError> problems = pageToErrorsMap.get(pageNum);
			for (DecisionTableRuleError problem : problems) {
				ProblemMarker marker = new DecisionTableProblemMarker(table.getName(), uri, table.getFolder(), 
												ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue().toLowerCase(), RULE_ERROR_TYPES.SYNTAX_TYPE.getLiteral(), pageNum);
				marker.setMessage(problem.getMessage());
				int severity = -1;
				if (RULE_ERROR_TYPES.get(problem.getErrorType()) == RULE_ERROR_TYPES.WARNING_TYPE) {
					severity = ProblemEvent.WARNING;
				} else {
					severity = ProblemEvent.ERROR;
				}
				marker.getAttributes().put(ProblemMarker.SEVERITY, severity);

				String location = problem.getRowId() + "_" + problem.getColumnId();
				
				marker.setLocation(location);
				marker.setProject(projectName);
				addMarkers(marker, severity, warnrecords, errorrecords);
				
				for (ProblemRecord warnrecord : warnrecords) {				
					enrichProblemRecord(table, warnrecord,problem.getColumnName());
				}
				for (ProblemRecord errorrecord : errorrecords) {
					enrichProblemRecord(table, errorrecord,problem.getColumnName());
				}				
			}
		}

		updateProblemRecords(warnrecords, errorrecords, true);
		return msg;
	}
	

	public static String postProjectProblems(Element node, String projectName, String artifactPath,List<ProblemRecord> errorrecords,List<ProblemRecord> warnrecords) {
		String msg = "";
		String uri = projectName +  artifactPath;
		String artifactName = artifactPath.substring(artifactPath.lastIndexOf("/")+1,artifactPath.length());
		String folderName = artifactPath.substring(0,artifactPath.lastIndexOf("/")+1);
		
		NodeList problemsNode = node.getElementsByTagName("problem");
		List<String> types = new ArrayList<String>();
		types.add(RULE_ERROR_TYPES.SEMANTIC_TYPE.getLiteral());
		types.add(RULE_ERROR_TYPES.SYNTAX_TYPE.getLiteral());
		removeMarker(uri, types, false);
		
		Map<String, List<DecisionTableRuleError>> pageToErrorsMap = new HashMap<String, List<DecisionTableRuleError>>();
		if (problemsNode.getLength() == 0) {
			updateProblemRecords(null, null, true);
		} else {
			String pageNum = null;
			for (int i = 0; i < problemsNode.getLength(); i++) {
				Node problemNode = problemsNode.item(i);
				DecisionTableRuleError ruleError = loadProblem(problemNode);
				NodeList problemChildNodes = problemNode.getChildNodes();
				for (int j = 0; j < problemChildNodes.getLength(); j++) {
					Node problemChildNode = problemChildNodes.item(j);
					if (problemChildNode.getNodeType() == Node.ELEMENT_NODE && problemChildNode.getFirstChild() != null 
																						&& problemChildNode.getNodeName().equals("pageNum")) {					
						pageNum = problemChildNode.getFirstChild().getNodeValue();
						break;
					}
				}

				List<DecisionTableRuleError> problems = pageToErrorsMap.get(pageNum);
				if (problems == null) {
					problems = new ArrayList<DecisionTableRuleError>();
					pageToErrorsMap.put(pageNum, problems);
				}
				problems.add(ruleError);				
			}
		}

		List<ProblemRecord> tempWarnrecords = new ArrayList<ProblemRecord>();
		List<ProblemRecord> tempErrorrecords = new ArrayList<ProblemRecord>();
		Set<String> pageNums = pageToErrorsMap.keySet();
		for (String pageNum : pageNums) {
			List<DecisionTableRuleError> problems = pageToErrorsMap.get(pageNum);
			for (DecisionTableRuleError problem : problems) {
				ProblemMarker marker = new DecisionTableProblemMarker(artifactName, uri, folderName, 
												ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue().toLowerCase(), RULE_ERROR_TYPES.SYNTAX_TYPE.getLiteral(), pageNum);
				marker.setMessage(problem.getMessage());
				int severity = -1;
				if (RULE_ERROR_TYPES.get(problem.getErrorType()) == RULE_ERROR_TYPES.WARNING_TYPE) {
					severity = ProblemEvent.WARNING;
				} else {
					severity = ProblemEvent.ERROR;
				}
				marker.getAttributes().put(ProblemMarker.SEVERITY, severity);

				String location = problem.getRowId() + "_" + problem.getColumnId();
				marker.setLocation(location);
				marker.setProject(projectName);
				addMarkers(marker, severity, warnrecords, errorrecords);
				
				for (ProblemRecord warnrecord : warnrecords) {				
					enrichProblemRecord(null, warnrecord,problem.getColumnName());
				}
				for (ProblemRecord errorrecord : errorrecords) {
					enrichProblemRecord(null, errorrecord,problem.getColumnName());
				}				
			}
		}
		errorrecords.addAll(tempErrorrecords);
		warnrecords.addAll(tempWarnrecords);
		return msg;
	}
	
	public static DecisionTableRuleError loadProblem(Node problemNode) {
		
		NodeList problemChildNodes = problemNode.getChildNodes();			
		String message = null, rowId = null, colId = null, columnName = null;
		int problemType = 0;
		for (int j = 0; j < problemChildNodes.getLength(); j++) {
			Node problemChildNode = problemChildNodes.item(j);
			if (problemChildNode.getNodeType() == Node.ELEMENT_NODE && problemChildNode.getFirstChild() != null) {					
				if (problemChildNode.getNodeName().equals("errorMessage")) {
					message = problemChildNode.getFirstChild().getNodeValue(); 
				} else if (problemChildNode.getNodeName().equals("location")) {
					rowId = problemChildNode.getFirstChild().getNodeValue();					 
				} else if (problemChildNode.getNodeName().equals("problemType")) {
					try {
						problemType = Integer.parseInt(problemChildNode.getFirstChild().getNodeValue());
					} catch (NumberFormatException nfe) { } //do nothing						 
				} else if (problemChildNode.getNodeName().equals("columnName")) {
					colId = problemChildNode.getFirstChild().getNodeValue(); 
				} else if (problemChildNode.getNodeName().equals("columnUIName")) {
					columnName = problemChildNode.getFirstChild().getNodeValue(); 
				}
			}
		}
		DecisionTableRuleError ruleError = new DecisionTableRuleError(message, rowId, colId, problemType,columnName);
		return ruleError;		
	}
	
	private static void enrichProblemRecord(Table table, ProblemRecord record, String colName) {
		ProblemMarker marker = record.getMarker();
		StringBuffer strBuff = new StringBuffer();
		if (marker instanceof DecisionTableProblemMarker) {
			DecisionTableProblemMarker dtProblemMarker = (DecisionTableProblemMarker) marker;
			boolean showAllPages = false;
			if(table != null) {
				showAllPages = table.getDecisionTable().isShowAll();
			}
			if (!showAllPages) {
				if (dtProblemMarker.getPageNum() != null) {
					strBuff.append(dtMsgs.dt_pagination_page() + " - ").append(dtProblemMarker.getPageNum()).append(", ");
				} 
			}
			
			if (dtProblemMarker.getLocation() != null) {
				String[] rowColIds = dtProblemMarker.getRuleAndColumnId();
				strBuff.append(dtMsgs.dtPropertyTabRule() + " - ").append(rowColIds[0]);
				if (rowColIds.length == 2 ) {
					colName = adjustColumnName(colName, true);
					strBuff.append(", " + dtMsgs.dt_column() + " - ").append(colName);
				}	
			}
		} else {
			if (marker.getLocation() != null) {
				strBuff.append(dtMsgs.dtPropertyTabRule() + " - ").append(marker.getLocation());
			}			
		}
		record.setAttribute(ProblemMarker.LOCATION, strBuff.toString());		
		record.setAttribute(ProblemMarker.RESOURCE, marker.getResource());
	}
	
	/**
	 * @param table
	 * @return
	 */
	public static String getSerializedTable(Table table) {
		Document rootDocument = XMLParser.createDocument();
		getInstance(rootDocument, rootDocument, table, null, false, false);
		String doc = rootDocument.toString();
		return doc;
	}
	
	/**
	 * @param table
	 * @param rowIndexes
	 * @param style
	 */
	public static void highlightCoverageRecords(AbstractTableEditor fCurrentEditor, String style) {
		TableDataGrid tableGrid = fCurrentEditor.getDecisionTableDataGrid();
		List<Integer> rowsToHighlight = fCurrentEditor.getRowsToHighlight(); 
		for (int i = 0; i < rowsToHighlight.size(); i++) {
			int rowNumber = rowsToHighlight.get(i);
			tableGrid.getRecord(rowNumber).setCustomStyle(style);
			tableGrid.refreshRow(rowNumber);
		}
	}

	public static void highlightProblemRecord(AbstractTableEditor fCurrentEditor, DecisionTableProblemMarker marker) {
		TableDataGrid tableGrid = fCurrentEditor.getDecisionTableDataGrid();
		ListGridRecord record = tableGrid.getRecord(marker.getRowNum());
		if (record != null) {
			/*record.setCustomStyle(marker.getSeverity() == ProblemMarker.SEVERITY_WARNING 
																				? "ws-dt-warning-row-style" : "ws-dt-error-row-style");
			tableGrid.refreshRow(marker.getRowNum());*/
			
			tableGrid.deselectAllRecords();
			tableGrid.selectRecords(new int[]{marker.getRowNum()});
		}	
	}

	/**
	 * @param editor
	 */
	public static void removeHighlights(AbstractTableEditor editor) {		
		removeProblemHighlights(editor);
		removeCoverageHighlights(editor);
	}
	
	/**
	 * @param editor
	 */
	public static void removeProblemHighlights(AbstractTableEditor editor) {		
		//remove all cell highlights
		editor.removeCellHighlights();
		
		//Clear open marker
		editor.setOnOpenMarker(null);		
	}

	public static void removeCoverageHighlights(AbstractTableEditor editor) {			
		//remove all row highlights
		List<Integer> rowsToHighlight = editor.getRowsToHighlight();
		for (int i = 0; i < rowsToHighlight.size(); i++) {
			int rowNumber = rowsToHighlight.get(i);
			editor.getDecisionTableDataGrid().getRecord(rowNumber).setCustomStyle(null);
			editor.getDecisionTableDataGrid().refreshRow(rowNumber);
		}
		editor.removeRowHighlights();
		//Set the rules map to NULL
		editor.setRulesToHighlight(null);
	}
	
		
	/**
	 * @param docElement
	 * @return
	 */
	public static boolean isVirtual(Element docElement) {
		boolean isVirtual = false;
		Node artifactNode = docElement.getElementsByTagName("artifactDetails").item(0);
		NodeList artifactChildNodes = artifactNode.getChildNodes();
		for (int j = 0; j < artifactChildNodes.getLength(); j++) {
			Node artifactChildNode = artifactChildNodes.item(j);
			if (artifactChildNode.getNodeType() == Node.ELEMENT_NODE && artifactChildNode.getFirstChild() != null) {
				if (artifactChildNode.getNodeName().equalsIgnoreCase("isVirtual")) {
					isVirtual = Boolean.parseBoolean(artifactChildNode.getFirstChild().getNodeValue());
				}
			}	
		}		
		return isVirtual;
	}
	
	/**
	 * Check if the expr is a substitution pattern for condition column
	 * @param value the expression
	 * @return boolean
	 */
	public static boolean isConditionVarString (String value) {
		if (value == null)
			return false;
		return COND_SUBSTITUTION_PATTERN.exec(value) != null || COND_SUBSTITUTION_PATTERN_BEFORE.exec(value) != null;
	}
	
	/**
	 * Check if the expr is a substitution pattern for action column.
	 * <p>
	 * Also check that it is not an equality expression.
	 * </p>
	 * @param value the expression
	 * @return boolean
	 */
	public static boolean isActionVarString (String value) {
		if (value == null)
			return false;
		return (ACT_SUBSTITUTION_ASSIGNMENT_PATTERN.exec(value) != null 
				|| ACT_SUBSTITUTION_ASSIGNMENT_PATTERN_BEFORE.exec(value) != null)
					&& ACT_SUBSTITUTION_EQUALITY_PATTERN.exec(value) == null && ACT_SUBSTITUTION_EQUALITY_PATTERN_BEFORE.exec(value) == null;
	}
	
	/**
	 * TODO this will be later addressed in a cleaner manner.
	 * @param column
	 * @param variables
	 * @return
	 */
	public static String getFormattedString(TableColumn column, String variables) {
		String columnName = unEscapeColumnSubstitutionExpression(column.getName());
		String text = adjustColumnName(columnName, true);
		String variableStringOrig = variables;
		if (text.isEmpty() == true || variables.isEmpty() == true)
			return "";
		
		RegExp regExp = RegExp.compile("\\{([0-9])*\\}", "g");
		MatchResult result = regExp.exec(text);
		List<Integer> patterns = new ArrayList<Integer>();
		while (result != null) {
			int count  = result.getGroupCount();
			if (count == 2) {
				int position = Integer.parseInt(result.getGroup(1));
				patterns.add(position);
			}	
			result = regExp.exec(text);
		}	
		
		String[] arguments = getArguments(text, variables, patterns); // get the substitutes
		StringBuilder sb = new StringBuilder();
		boolean matchesPattern = true;
		String formattedExpr = text;
		if (arguments.length == patterns.size()) {
			Iterator<Integer> itr = patterns.iterator();
			while (itr.hasNext()) {
				Integer position = itr.next();
				String escapedArgument = escapeRegExp(arguments[position]);
				formattedExpr = formattedExpr.replaceAll("\\{" + position + "\\}" , escapedArgument);
			}	
			sb.append(formattedExpr);
		} else {
			matchesPattern = false;
		}
		if (!matchesPattern) {
			//If formats do not match no substitution to be done.
			sb.append(variableStringOrig);
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param varString
	 * @param body
	 * @return
	 */
	private static String[] getArguments(String text, String value, List<Integer> patterns) {
		Iterator<Integer> itr = patterns.iterator();
		List<String> textPartsList = new ArrayList<String>();
		String textRemainingPart = text.trim();
		while (itr.hasNext()) {
			Integer position = itr.next();
			String[] textParts = textRemainingPart.split("\\{" + position + "\\}");
			if (textParts.length == 0) {
				textPartsList.add("{" + position + "}");
				textRemainingPart = "";
			} else if (textParts.length == 1) {
				if (textParts[0] != null && !textParts[0].trim().isEmpty()) {
					textPartsList.add(textParts[0].trim());
				}
				textPartsList.add("{" + position + "}");
				textRemainingPart = "";
			} else if (textParts.length == 2) {
				if (textParts[0] != null && !textParts[0].trim().isEmpty()) {
					textPartsList.add(textParts[0].trim());
				}
				textPartsList.add("{" + position + "}");
				if (textParts[1] != null && !textParts[1].trim().isEmpty()) {
					textRemainingPart = textParts[1].trim();
				}
			}
		}
		if (!textRemainingPart.isEmpty()) {
			textPartsList.add(textRemainingPart);
		}

		List<String> valuePartsList = new ArrayList<String>();
		Iterator<String> itr2 = textPartsList.iterator();
		String valuePart = value.trim();
		while (itr2.hasNext()) {
			String textPart = itr2.next();
			if (!textPart.startsWith("{")) {
				String[] valueParts = valuePart.split(escapeRegExp(textPart), 2);
				if (valueParts.length == 0) {
					valuePartsList.add(textPart);
					valuePart = "";
				} else if (valueParts.length == 1) {
					if (valueParts[0] != null && !valueParts[0].trim().isEmpty()) {
						valuePartsList.add(valueParts[0].trim());
					}
					valuePartsList.add(textPart);
					valuePart = "";
				} else if (valueParts.length == 2) {
					if (valueParts[0] != null && !valueParts[0].trim().isEmpty()) {
						valuePartsList.add(valueParts[0].trim());
					}
					valuePartsList.add(textPart);
					if (valueParts[1] != null && !valueParts[1].trim().isEmpty()) {
						valuePart = valueParts[1].trim();
					} else {
						valuePart = "";
					}
				}
			}	
		}		
		if (!valuePart.isEmpty()) {
			valuePartsList.add(valuePart);
		}

		boolean alreadySubstituted = false;
		List<String> existingArgs = new ArrayList<String>();
		if (textPartsList.size() == valuePartsList.size()) {
			alreadySubstituted = true;
			for(int i = 0; i < textPartsList.size(); i++) {
				if (textPartsList.get(i).startsWith("{")) {
					existingArgs.add(valuePartsList.get(i));
				} else if (!textPartsList.get(i).equals(valuePartsList.get(i))) {
					alreadySubstituted = false;
					break;
				}
			}
		}
		String[] args = null;
		if (alreadySubstituted) {
			args = existingArgs.toArray(new String[existingArgs.size()]);
		} else {				
			args = value.split(SUBSTITUTION_DELIM);
		}	
		return args;		
	}
	
	public static native String escapeRegExp(String str) /*-{
	  return str.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&");
	}-*/;

	public static String escapeColumnSubstitutionExpression(String str) {
		COND_OPERATORS[] conditionalOperators = COND_OPERATORS.getConditionalOperators();
		for (int i = 0; i < conditionalOperators.length; i++) {
			str = str.replace(conditionalOperators[i].opString(), conditionalOperators[i].opLiteral());
		}
		return str;
	}

	public static String unEscapeColumnSubstitutionExpression(String str) {
		COND_OPERATORS[] conditionalOperators = COND_OPERATORS.getConditionalOperators();
		for (int i = 0; i < conditionalOperators.length; i++) {
			str = str.replace(conditionalOperators[i].opLiteral(), conditionalOperators[i].opString());
		}
		return str;
	}
	
	public static void getDateTimeDialog(final TextItem textItem){
		
		DateTimeDialog dtd = new DateTimeDialog(){
			@Override
			public void onAction() {
				if(cbItem.getValue().toString().equalsIgnoreCase("Matches")){
					Date a = dtp.getValue();
					dateText = (a.getYear()+1900)+"-"+getPropperValue((a.getMonth()+1))+"-"+getPropperValue(a.getDate())+"T"+getPropperValue(a.getHours())+":"+getPropperValue(a.getMinutes())+":"+getPropperValue(a.getSeconds());
				}else if(cbItem.getValue().toString().equalsIgnoreCase("Before")){
					Date a = dtp.getValue();
					dateText = "<"+(a.getYear()+1900)+"-"+getPropperValue((a.getMonth()+1))+"-"+getPropperValue(a.getDate())+"T"+getPropperValue(a.getHours())+":"+getPropperValue(a.getMinutes())+":"+getPropperValue(a.getSeconds());
				}else if(cbItem.getValue().toString().equalsIgnoreCase("After")){
					Date a = dtp.getValue();
					dateText = ">"+(a.getYear()+1900)+"-"+getPropperValue((a.getMonth()+1))+"-"+getPropperValue(a.getDate())+"T"+getPropperValue(a.getHours())+":"+getPropperValue(a.getMinutes())+":"+getPropperValue(a.getSeconds());
				}else{
					Date a = dtp.getValue();
					Date b = dtp1.getValue();
					dateText = ">"+(a.getYear()+1900)+"-"+getPropperValue((a.getMonth()+1))+"-"+getPropperValue(a.getDate())+"T"+getPropperValue(a.getHours())+":"+getPropperValue(a.getMinutes())+":"+getPropperValue(a.getSeconds())+ " && <"+(b.getYear()+1900)+"-"+getPropperValue((b.getMonth()+1))+"-"+getPropperValue(b.getDate())+"T"+getPropperValue(b.getHours())+":"+getPropperValue(b.getMinutes())+":"+getPropperValue(b.getSeconds());
					
				}
				textItem.setValue(dateText);
				destroy();
			}
		};
		dtd.show();
	}
		
}