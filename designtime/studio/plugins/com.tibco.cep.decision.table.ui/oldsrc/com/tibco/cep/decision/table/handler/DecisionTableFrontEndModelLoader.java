package com.tibco.cep.decision.table.handler;

import java.util.List;

import com.jidesoft.decision.DecisionConstants;
import com.jidesoft.decision.DecisionDataModel;
import com.jidesoft.decision.DecisionEntry;
import com.jidesoft.decision.DecisionField;
import com.jidesoft.decision.DecisionRule;
import com.jidesoft.decision.UserObject;
import com.jidesoft.decision.cell.editors.custom.DefaultConverterContext;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.utils.RuleIDGeneratorManager;

/**
 * Load front end model {@link DecisionDataModel} from
 * an existing {@link Table}.
 * <p>
 * This does not use EMF notifications as our design 
 * will not permit this.
 * </p>
 * @author aathalye
 *
 */
public class DecisionTableFrontEndModelLoader {
	
	private static final String CLASS = DecisionTableFrontEndModelLoader.class.getName();
	
	/**
	 * The project we are working with.
	 */
	private String projectName;
	
	/**
	 * The backend EMF model.
	 */
	private Table tableEModel;
		
	/**
	 * Fetch from model itself.
	 */
	private String tablePath;
	
	/**
	 * 
	 * @param projectName
	 * @param tableEModel
	 * 
	 */
	public DecisionTableFrontEndModelLoader(String projectName, Table tableEModel) {
		this.projectName = projectName;
		this.tableEModel = tableEModel;
		tablePath = tableEModel.getFolder() + tableEModel.getName();
	}
	
	/**
	 * Load the {@link DecisionDataModel} from the EMF model.
	 * @param tableType
	 * @return
	 */
	public DecisionDataModel loadFrontEndModel(TableTypes tableType) {
		if (tableType == null) {
			tableType = TableTypes.DECISION_TABLE;
		}
		//The model to create.
		DecisionDataModel decisionDataModel = new DecisionDataModel();
		decisionDataModel.setTableType(tableType);
		TableRuleSet tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();
		//Check if any columns are present.
		Columns columnsModel = tableRuleSet.getColumns();
		if (columnsModel != null) {
			//Load columns.
			loadColumns(decisionDataModel, columnsModel);
			//Load rows.
			loadRows(decisionDataModel, tableRuleSet.getRule());
		}
		return decisionDataModel;
	}
	
	/**
	 * 
	 * @param decisionDataModel
	 * @param columnsModel
	 */
	private void loadColumns(DecisionDataModel decisionDataModel, Columns columnsModel) {
		//TODO load as per UI model
		List<Column> allColumns = columnsModel.getColumn();
		for (Column column : allColumns) {
			DecisionTableUIPlugin.debug(CLASS, "Loading Column {0}", column);
			loadColumn(decisionDataModel, column);
		}
	}
	
	/**
	 * 
	 * @param decisionDataModel
	 * @param column
	 */
	private void loadColumn(DecisionDataModel decisionDataModel, Column column) {
		//Create field for each
		String columnName = column.getName();
		ColumnType columnType = column.getColumnType();
		String columnId = column.getId();
		String columnAlias = column.getAlias();
		
		UserObject userObject = new UserObject();
		userObject.setColumn(column);
		//Create a field for this column.
		DecisionField decisionField = null;
		switch (columnType) {
			case CUSTOM_CONDITION :
			case CONDITION :
				decisionDataModel.incrementAndSet(DecisionConstants.AREA_CONDITION);
				decisionField = createField(DecisionConstants.AREA_CONDITION, columnName, columnId, columnAlias);
				decisionDataModel.addConditionField(decisionField);
				break;
				
			case CUSTOM_ACTION :
			case ACTION :
				decisionDataModel.incrementAndSet(DecisionConstants.AREA_ACTION);
				decisionField = createField(DecisionConstants.AREA_ACTION, columnName, columnId, columnAlias);
				decisionDataModel.addActionField(decisionField);
				break;
		}
		//Set converter context required in cell editor.
		decisionField.setConverterContext(new DefaultConverterContext(column.getId(), userObject, column.getColumnType() == ColumnType.ACTION, decisionDataModel.getTableType()));		
	}
	
	/**
	 * Create a new {@link DecisionField} from a {@link Column}
	 * @param fieldType
	 * @param fieldName
	 * @param fieldId
	 * @param alias
	 * @return
	 */
	private DecisionField createField(int fieldType, String fieldName, String fieldId, String alias) {
		//Check if this is show alias enabled for column
		boolean showColumnAlias = 
			DecisionTableUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.SHOW_COLUMN_ALIAS);
		DecisionField decisionField = null;
		if (showColumnAlias) {
			if (alias == null || alias.length() == 0) {
				alias = fieldName;
			}
		}
		decisionField = (showColumnAlias) ? 
				new DecisionField(fieldName, alias, TableRuleVariable.class) :
					new DecisionField(fieldName, TableRuleVariable.class);	
		decisionField.setAreaType(fieldType);
		decisionField.setAlias(alias);
		decisionField.setId(fieldId);
		return decisionField;
	}
	
	/**
	 * 
	 * @param decisionDataModel
	 * @param tableRules
	 */
	private void loadRows(DecisionDataModel decisionDataModel, List<TableRule> tableRules) {
		for (TableRule tableRule : tableRules) {
			//Load each row
			DecisionTableUIPlugin.debug(CLASS, "Loading Row {0}", tableRule);
			loadRow(decisionDataModel, tableRule);
		}
	}
	
	/**
	 * Load a {@link DecisionRule} from the backend {@link TableRule}
	 * @param decisionDataModel
	 * @param tableRule
	 */
	private void loadRow(DecisionDataModel decisionDataModel, TableRule tableRule) {
		DecisionRule decisionRule = new DecisionRule(tableRule);
		DecisionTableUIPlugin.debug(CLASS, "Rule Id {0}", tableRule.getId());
		//While loading from existing table use its id
		decisionRule.setId(Integer.parseInt(tableRule.getId()));
		//Load conditions/actions in it
		loadCells(decisionDataModel, decisionRule, tableRule.getCondition(), DecisionConstants.AREA_CONDITION);
		loadCells(decisionDataModel, decisionRule, tableRule.getAction(), DecisionConstants.AREA_ACTION);
		//Add to model
		decisionDataModel.addRule(decisionRule);
		//Increment ID
		RuleIDGeneratorManager.INSTANCE.steadyIncrement(projectName, tablePath, decisionRule.getId());
	}
	
	/**
	 * Load front end model for cells of each row. 
	 * @param decisionDataModel
	 * @param tableRuleVariables
	 * @param fieldType
	 */
	private void loadCells(DecisionDataModel decisionDataModel, 
				           DecisionRule decisionRule,
			               List<TableRuleVariable> tableRuleVariables,
			               int fieldType) {
		for (TableRuleVariable tableRuleVariable : tableRuleVariables) {
			String columnId = tableRuleVariable.getColId();
			//Get a field for this
			DecisionField matchingField = 
				(fieldType == DecisionConstants.AREA_CONDITION) ? 
						decisionDataModel.getConditionFieldForId(columnId) :
							decisionDataModel.getActionFieldForId(columnId);
			if (matchingField != null) {
				//Create a DecisionEntry
				DecisionEntry decisionEntry = new DecisionEntry(matchingField, tableRuleVariable);
				if (fieldType == DecisionConstants.AREA_CONDITION) {
					decisionRule.addCondition(decisionEntry);
				} else {
					decisionRule.addAction(decisionEntry);
				}
			}
		}
	}
}
