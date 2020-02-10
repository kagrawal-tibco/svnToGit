/**
 * 
 */
package com.jidesoft.decision.cell.editors;

import java.util.List;

import javax.swing.ListSelectionModel;

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.converter.ObjectConverter;
import com.jidesoft.decision.DecisionDataModel;
import com.jidesoft.decision.DecisionField;
import com.jidesoft.decision.DecisionTableModel;
import com.jidesoft.decision.UserObject;
import com.jidesoft.decision.cell.editors.custom.DefaultConverterContext;
import com.jidesoft.decision.cell.editors.custom.DefaultMultipleEnumConverter;
import com.jidesoft.decision.cell.editors.utils.CellEditorUtils;
import com.jidesoft.grid.TableModelWrapperUtils;
import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.impl.ModifyCommandExpression;
import com.tibco.cep.decision.table.editors.DecisionTableContentEditorInput;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.editors.IDecisionTableEditorInput;
import com.tibco.cep.decision.table.language.DTLanguageUtil;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelFactoryImpl;
import com.tibco.cep.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decisionproject.util.DTDomainUtil;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.studio.ui.resources.JarEntryEditorInput;

/**
 * @author aathalye
 *
 */
public class CellEditorController {

	private DecisionTableEditor decisionTableEditor;

	private Table tableEModel;

	/**
	 * 
	 * @param decisionTableEditor
	 */
	public CellEditorController(DecisionTableEditor decisionTableEditor) {
		this.decisionTableEditor = decisionTableEditor;
		IDecisionTableEditorInput decisionTableEditorInput= null;
		if (decisionTableEditor.isJar()) {
			decisionTableEditorInput = new DecisionTableContentEditorInput((JarEntryEditorInput)decisionTableEditor.getEditorInput());
		} else {
			decisionTableEditorInput = (IDecisionTableEditorInput)decisionTableEditor.getEditorInput();
		}
		tableEModel = decisionTableEditorInput.getTableEModel();
	}

	/**
	 * 
	 * @param converterContext
	 * @param showDomainDescriptionIfPresent
	 * @return
	 */
	public String[] getDisplayValues(ConverterContext converterContext, boolean showDomainDescriptionIfPresent) {
		Column column = getConverterContextObject(converterContext);
		IDecisionTableEditorInput decisionTableEditorInput = (IDecisionTableEditorInput)decisionTableEditor.getEditorInput();
		//Get project name
		String projectName = decisionTableEditorInput.getProjectName();
		/**
		 * TODO Optimize this by caching
		 */
		String propertyPath = column.getPropertyPath();
		//Get Domain Instances. Will be empty for no domain case.
		List<DomainInstance> domainInstances = DTDomainUtil.getDomains(propertyPath, projectName);
		String colName = column.getName();
		String substitutionFormat = 
			(column.isSubstitution()) ? 
					DTLanguageUtil.canonicalizeExpression(colName.substring(colName.indexOf(' '))) : "";

		//Fetch all display values
		String[] values = null;
		if (domainInstances != null && domainInstances.size() > 0) { 
			values = 
				DTDomainUtil.getDomainEntryStrings(domainInstances, 
						propertyPath, 
						projectName, 
						substitutionFormat,
						showDomainDescriptionIfPresent,
						column.getColumnType() == ColumnType.ACTION);
		} else {
			values = new String[0];
		}
		return values;
	}


	public List<List<String>> getDropDownValueDescription(ConverterContext converterContext) {

		Column column = getConverterContextObject(converterContext);
		IDecisionTableEditorInput decisionTableEditorInput = (IDecisionTableEditorInput)decisionTableEditor.getEditorInput();
		//Get project name
		String projectName = decisionTableEditorInput.getProjectName();
		/**
		 * TODO Optimize this by caching
		 */
		String propertyPath = column.getPropertyPath();
		//Get Domain Instances. Will be empty for no domain case.
		List<DomainInstance> domainInstances = DTDomainUtil.getDomains(propertyPath, projectName);
		String colName = column.getName();
		String substitutionFormat = 
			(column.isSubstitution()) ? 
					DTLanguageUtil.canonicalizeExpression(colName.substring(colName.indexOf(' '))) : "";

		//Fetch all display values
		if (domainInstances != null && domainInstances.size() > 0) { 
			return DTDomainUtil.getDomainEntryDropDownStrings(domainInstances, 
					propertyPath, 
					projectName, 
					substitutionFormat,
					column.getColumnType() == ColumnType.ACTION,
					true);
		} 
		return null;
	}

	/**
	 * Check if cell value changed based on current cell value's expression
	 * and raw cell value when cell is editing.
	 * @param tableRuleVariable
	 * @param rawCellValue
	 * @return
	 */
	public boolean isCellExpressionChanged(TableRuleVariable tableRuleVariable, String rawCellValue, DecisionDataModel decisionDataModel) {
		if (tableRuleVariable == null) {
			//New cell addition
			return true;
		}
		String currentExpression;
		Column col = getColumnbyId(tableRuleVariable.getColId(), decisionDataModel);		
		if(!col.isSubstitution() && DecisionTableUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.SHOW_DOMAIN_DESCRIPTION)) {
			currentExpression = tableRuleVariable.getDisplayValue();
			if (currentExpression.equals("")) {
				currentExpression = tableRuleVariable.getExpr();
			}
		} else {
			currentExpression = tableRuleVariable.getExpr();
		}
		
		IDecisionTableEditorInput decisionTableEditorInput = (IDecisionTableEditorInput)decisionTableEditor.getEditorInput();
		currentExpression = CellEditorUtils.massageCellEditorRawValue(currentExpression, decisionTableEditorInput.getProjectName(), col);
		currentExpression = currentExpression.intern();
		
		rawCellValue = rawCellValue.intern();
		return currentExpression != rawCellValue;
	}

	/**
	 * 
	 * @param decisionField
	 * @return
	 */
	public TableRuleVariable createCellEditorModelObject(DecisionField decisionField) {
		//This will be model for the cell editor.(i.e. the cell value)
		TableRuleVariable ruleVariable = DtmodelFactoryImpl.eINSTANCE.createTableRuleVariable();
		//Set properties of the TableRuleVariable
		ruleVariable.setColId(decisionField.getId());
		//Set blank expression on the new one.
		ruleVariable.setExpr("");
		return ruleVariable;
	}

	/**
	 * 
	 * @param converterContext
	 * @return
	 */
	private Column getConverterContextObject(ConverterContext converterContext) {
		if (converterContext == null) {
			throw new IllegalArgumentException("Converter Context cannot be null");
		}
		UserObject userObject = (UserObject)converterContext.getUserObject();
		return userObject.getColumn();
	}

	/**
	 * Switch selection mode on the UI component based on column type
	 * @param converterContext
	 * @return
	 */
	public int getDomainSelectionMode(ConverterContext converterContext) {
		Column column = getConverterContextObject(converterContext);
		//BE-16122
		int selectionMode = 
			(column.getColumnType() == ColumnType.ACTION) ?
					(column.isArrayProperty()) ? ListSelectionModel.SINGLE_SELECTION : ListSelectionModel.SINGLE_SELECTION
							: ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
		return selectionMode;
	}

	/**
	 * 
	 * @param decisionDataModel
	 * @return
	 */
	private TableRuleSet getTableRuleSet(DecisionDataModel decisionDataModel) {
		TableTypes tableType = decisionDataModel.getTableType();
		TableRuleSet tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();
		return tableRuleSet;
	}

	/**
	 * 
	 * @param selectedColumnNumber
	 * @param decisionDataModel
	 * @return
	 */
	public Column getColumnAt(int selectedColumnNumber, DecisionDataModel decisionDataModel) {
		TableRuleSet tableRuleSet = getTableRuleSet(decisionDataModel);
		Columns columnsModel = tableRuleSet.getColumns();
		return columnsModel.getColumn().get(selectedColumnNumber);
	}

	/**
	 * 
	 * @param columnId
	 * @param decisionDataModel
	 * @return
	 */
	public Column getColumnbyId(String columnId, DecisionDataModel decisionDataModel) {
		TableRuleSet tableRuleSet = getTableRuleSet(decisionDataModel);
		Columns columnsModel = tableRuleSet.getColumns();
		return columnsModel.search(columnId);
	}
	/**
	 * 
	 * @param actualRowIndex -> The actual row index 
	 * @see TableModelWrapperUtils#getActualRowAt(javax.swing.table.TableModel, int, javax.swing.table.TableModel)
	 * @param columnIndex
	 * @param decisionTableModel
	 * @param tableRuleVariable -> On which id has to be set.
	 */
	public void setTableRuleVariableID(int actualRowIndex, 
			int columnIndex,
			DecisionTableModel decisionTableModel,
			TableRuleVariable tableRuleVariable) {
		DecisionDataModel decisionDataModel = decisionTableModel.getDecisionDataModel();
		//Get the tableruleset
		TableRuleSet tableRuleSet = getTableRuleSet(decisionDataModel);
		//Get the backend rule at the selected row index using actual row index
		TableRule tableRule = tableRuleSet.getRule().get(actualRowIndex);
		DecisionField decisionField = decisionTableModel.getDecisionFieldAt(actualRowIndex, columnIndex);
		//Get its id which is also same as column id
		String fieldId = decisionField.getId();
		String tableRuleVariableId = 
			new StringBuilder(tableRule.getId()).append("_").append(fieldId).toString();
		tableRuleVariable.setId(tableRuleVariableId);
	}

	/**
	 * 
	 * @param decisionDataModel
	 * @param oldValue
	 * @param newValue
	 * @param changedExpression
	 */
	public void executeCellModification(DecisionDataModel decisionDataModel, 
			TableRuleVariable oldValue,
			TableRuleVariable newValue,
			String changedExpression, 
			ObjectConverter converter, 
			DefaultConverterContext context) {
		IDecisionTableEditorInput decisionTableEditorInput = (IDecisionTableEditorInput)decisionTableEditor.getEditorInput();
		if (!decisionTableEditorInput.isReadOnly()) {
			String project = decisionTableEditorInput.getProjectName();
			TableTypes tableType = decisionDataModel.getTableType();
			ModifyCommandExpression modifyCommandExpression = new ModifyCommandExpression(changedExpression);

			Column col = getColumnbyId(oldValue.getColId(), decisionDataModel);			
			if(col != null && !col.isSubstitution())
				setCellDisplayValue(modifyCommandExpression, project, col, converter, context);
			
			CommandFacade.
			getInstance().executeModify(project,
					tableEModel,
					tableType,
					newValue,
					oldValue,
					modifyCommandExpression					                       
			);

		}
	}

	private void setCellDisplayValue(
			ModifyCommandExpression modifyCommandExpression, String project, Column column, ObjectConverter converter, DefaultConverterContext context) {
		@SuppressWarnings("unused")
		DefaultMultipleEnumConverter enumConverter;
		if(converter instanceof DefaultMultipleEnumConverter) {
			enumConverter = (DefaultMultipleEnumConverter) converter;
			modifyCommandExpression.setCellValue(((ObjectConverter)converter).toString(((DefaultConverterContext)context).getDisplayValues(), context));
			modifyCommandExpression.setExpr(((ObjectConverter)converter).toString(((DefaultConverterContext)context).getExpressionValues(), context));
		} else {
			modifyCommandExpression.setCellValue(modifyCommandExpression.getExpr());
		}
	}
}
