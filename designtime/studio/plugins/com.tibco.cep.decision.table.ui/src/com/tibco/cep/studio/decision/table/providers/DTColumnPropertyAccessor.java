package com.tibco.cep.studio.decision.table.providers;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;

import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.impl.ModifyCommandExpression;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelFactoryImpl;
import com.tibco.cep.decisionproject.util.DTConstants;
import com.tibco.cep.decisionproject.util.DTDomainUtil;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.utils.CellEditorUtils;
import com.tibco.cep.studio.decision.table.utils.DecisionTableUtil;

/**
 * This is the main entry point into obtaining and setting the values of the TableRuleVariables (i.e. individual conditions/actions
 * of a Decision Table).  Because a GlazedListsDataProvider is used for the body of the decision table editor, this class
 * is the delegate class used by the GlazedListsDataProvider to get and set values in the underlying TableRuleVariable.
 * 
 * @author rhollom
 *
 */
public class DTColumnPropertyAccessor implements IColumnPropertyAccessor<TableRule>, IColumnObjectAccessor<Column> {
	private final TableRuleSet ruleSet;
	@SuppressWarnings("unused")
	private IDecisionTableEditor editor;
	private Table table;
	private TableTypes tableType;

	public DTColumnPropertyAccessor(IDecisionTableEditor editor,
			Table table, TableTypes tableType) {
		this.editor = editor;
		if (tableType == TableTypes.DECISION_TABLE) {
			this.ruleSet = table.getDecisionTable();
		}else {
			this.ruleSet = table.getExceptionTable();
		}
		this.table = table;
		this.tableType = tableType;
	}

	@Override
	public Column getColumnObject(int columnIdx) {
		Columns columns = ruleSet.getColumns();
		if (columns != null) {
			EList<Column> column = columns.getColumn();
			Column col = column.get(columnIdx);
			return col;
		}
		return null;
	}

	@Override
	public void setDataValue(TableRule rule, int columnIdx, Object newValueObj) {
		String rawCellValue = newValueObj == null ? null : String.valueOf(newValueObj);

		String newExpr = rawCellValue;
		String displayValue = null;		
		Columns columns = ruleSet.getColumns();
		if (columns != null) {
			EList<Column> column = columns.getColumn();
			Column col = column.get(columnIdx);
			String propertyPath = col.getPropertyPath();
			boolean hasAssociatedDomain = false;
			List<List<String>> domainValues = editor.getDomainValues(col);
			List<DomainInstance> domainInstances = DTDomainUtil.getDomains(propertyPath, table.getOwnerProjectName());
			//Fetch all display values
			if ((domainValues != null && domainValues.size() > 0) || (domainInstances != null && domainInstances.size() > 0)) { 
				hasAssociatedDomain = true;
				DomainValueDataConverter domainValuesConverter = editor.getDomainValueDataConverter(col.getColumnType() == ColumnType.CONDITION, DTDomainUtil.SEPARATOR);
				newExpr = domainValuesConverter.getExpressionValues(newValueObj,table.getOwnerProjectName(),col);
				displayValue = domainValuesConverter.getDisplayValues(newValueObj,table.getOwnerProjectName(),col);
			}
			
			if (CellEditorUtils.isAsterixedExpression(newExpr)) {
				//Only asterix should be set as expression.
				newExpr = DTConstants.ASTERISK;
			}
			if(col.isSubstitution()){
				displayValue = newExpr;
			}	
			newExpr = CellEditorUtils.massageCellEditorRawValue(newExpr, col);

			EList<TableRuleVariable> rules;
			if (col.getColumnType() == ColumnType.CONDITION || col.getColumnType() == ColumnType.CUSTOM_CONDITION) {
				rules = rule.getCondition();
			} else {
				rules = rule.getAction();
			}
			
			for (TableRuleVariable trv : rules) {
				if (trv.getColId().equals(col.getId())) {
					
					if (CellEditorUtils.isCellExpressionChanged(trv, newExpr, col)) {
						fireModify(trv, rawCellValue, newExpr, displayValue, rule, col, hasAssociatedDomain);	
					}
					return;
				}
			}
			if (newExpr != null) { // && !newExpr.isEmpty()) {
				// no TableRuleVariable exists, need to create one
				TableRuleVariable trv = DtmodelFactory.eINSTANCE.createTableRuleVariable();
				trv.setExpr("");
				trv.setColId(col.getId());
				trv.setId(rule.getId() + "_" + col.getId());
				rules.add(trv);
				fireModify(trv, rawCellValue, newExpr, displayValue, rule, col, hasAssociatedDomain);
			}
		}
	}

	private void fireModify(TableRuleVariable trv, String rawNewExpr, String newExpr, String displayValue,
			TableRule rule, Column col, boolean hasAssociatedDomain) {
		ModifyCommandExpression modifyCommandExpression = new ModifyCommandExpression(newExpr);
		//create dummy TableRuleVariable
		TableRuleVariable oldValue = DtmodelFactoryImpl.eINSTANCE.createTableRuleVariable();
		//Set properties of the TableRuleVariable
		oldValue.setColId(col.getId());
		//Set blank expression on the new one.
		oldValue.setExpr(trv.getExpr());
		oldValue.setId(rule.getId() + "_" + col.getId());
				
		if(col != null && !col.isSubstitution()) {
			if (hasAssociatedDomain) {
				modifyCommandExpression.setExpr(newExpr);
				modifyCommandExpression.setCellValue(displayValue);				
			} else {
				modifyCommandExpression.setExpr(rawNewExpr);
				modifyCommandExpression.setCellValue(displayValue);
			}	
		}	   
		if(col.isSubstitution()){
			modifyCommandExpression.setCellValue(displayValue);
		}
		CommandFacade.
		getInstance().executeModify(table.getOwnerProjectName(),
				table,
				tableType,
				trv,
				oldValue,
				modifyCommandExpression					                       
		);
		
	}

	@Override
	public Object getDataValue(TableRule rule, int colIdx) {
		Object dataObj = null;
		Columns columns = ruleSet.getColumns();
		if (columns != null) {
			EList<Column> column = columns.getColumn();
			Column col = column.get(colIdx);
			EList<TableRuleVariable> rules;
			if (col.getColumnType() == ColumnType.CONDITION || col.getColumnType() == ColumnType.CUSTOM_CONDITION) {
				rules = rule.getCondition();
			} else {
				rules = rule.getAction();
			}
			for (TableRuleVariable trv : rules) {
				if (trv.getColId().equals(col.getId())) {
					String propertyPath = col.getPropertyPath();
					//Fetch all display values
					List<List<String>> domainValues = editor.getDomainValues(col);
					List<DomainInstance> domainInstances = DTDomainUtil.getDomains(propertyPath, table.getOwnerProjectName());
					if (domainValues != null && domainValues.size() > 0) {
						IDataConverter domainValuesConverter = new MultiValueDataConverter(col.getColumnType() == ColumnType.CONDITION, DTDomainUtil.SEPARATOR);
						String domainValue = (trv.getExpr() == null) ? "" : trv.getExpr();
						dataObj = domainValuesConverter.fromString(domainValue);
					} else if (domainInstances != null && domainInstances.size() > 0) { 
						IDataConverter domainValuesConverter = new MultiValueDataConverter(col.getColumnType() == ColumnType.CONDITION, DTDomainUtil.SEPARATOR);
						String domainValue = (trv.getExpr() == null) ? "" : trv.getExpr();
						dataObj = domainValuesConverter.fromString(domainValue);
					} else {
						boolean toggleTextAliasFlag = editor.getDecisionTableDesignViewer().isToggleTextAliasFlag();
						boolean expToggleTextAliasFlag = editor.getDecisionTableDesignViewer().isExpToggleTextAliasFlag();
						if(!(ColumnType.CUSTOM_CONDITION.equals(col.getColumnType())  ||ColumnType.CUSTOM_ACTION.equals(col.getColumnType())) ){
							if(TableTypes.DECISION_TABLE.equals(tableType) && toggleTextAliasFlag){
								dataObj = DecisionTableUtil.computeExprWithAlias(trv.getExpr(), trv, table,toggleTextAliasFlag);	
							}else if(TableTypes.EXCEPTION_TABLE.equals(tableType) && toggleTextAliasFlag){
								dataObj = DecisionTableUtil.computeExprWithAlias(trv.getExpr(), trv, table,expToggleTextAliasFlag);	
							}else {
								if(col.isSubstitution() && !trv.getDisplayValue().isEmpty()){
									dataObj = trv.getDisplayValue();
								}else{
									dataObj = trv.getExpr();
								}
							}
						}else {
							if(col.isSubstitution() && !trv.getDisplayValue().isEmpty()){
								dataObj = trv.getDisplayValue();
							}else{
								dataObj = trv.getExpr();
							}
						}
					}					
				}
			}
		}
		
		if (dataObj == null) {
			dataObj = new String();
		}

		return dataObj;
	}

	@Override
	public int getColumnCount() {
		if (ruleSet == null || ruleSet.getColumns() == null) return 0;
		return ruleSet.getColumns().getColumn().size();
	}

	@Override
	public int getColumnIndex(String arg0) {
		return 0;
	}

	@Override
	public String getColumnProperty(int arg0) {
		return "ColProp"+arg0;
	}
	
	public IDecisionTableEditor getEditor() {
		return editor;
	}
}

