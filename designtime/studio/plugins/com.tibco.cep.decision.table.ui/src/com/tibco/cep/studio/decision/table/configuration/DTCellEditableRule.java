package com.tibco.cep.studio.decision.table.configuration;

import org.eclipse.emf.common.util.EList;
import org.eclipse.nebula.widgets.nattable.config.EditableRule;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.decision.table.editor.DTBodyLayerStack;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.providers.SpanningGlazedListsDataProvider;

/**
 * NatTable based rule configured to check whether a cell is editable or not
 * @author vdhumal
 *
 */
public class DTCellEditableRule extends EditableRule {

	private DTBodyLayerStack<TableRule> bodyLayer = null;
	private IDecisionTableEditor editor = null;
	private TableRuleSet tableRuleSet = null;
	private TableTypes tableType;
	public DTCellEditableRule(DTBodyLayerStack<TableRule> bodyLayer, IDecisionTableEditor editor, TableRuleSet tableRuleSet,TableTypes tableType) {
		this.bodyLayer = bodyLayer;
		this.editor = editor;
		this.tableRuleSet = tableRuleSet;
		this.tableType=tableType;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.nebula.widgets.nattable.config.EditableRule#isEditable(int, int)
	 */
	public boolean isEditable(int columnIndex, int rowIndex) {
		Object value = getModelDataByPosition(columnIndex, rowIndex);
		boolean isEditable = true;
		if(TableTypes.DECISION_TABLE.equals(this.tableType) && editor.getDecisionTableDesignViewer().isToggleTextAliasFlag()){
			isEditable=false;
		}else if(TableTypes.EXCEPTION_TABLE.equals(this.tableType) && editor.getDecisionTableDesignViewer().isExpToggleTextAliasFlag()){
			isEditable=false;
		}else if (editor.isEnabled()) {
			if (value != null && value instanceof TableRuleVariable) {
				isEditable = ((TableRuleVariable)value).isEnabled();
			}
		} else {
			isEditable = false;
		}
		return isEditable;
	}	

	private Object getModelDataByPosition(int columnIndex, int rowIndex) {		
		SpanningGlazedListsDataProvider<TableRule> dataProvider = bodyLayer.getBodyDataProvider();
		if(rowIndex >= 0 && columnIndex >= 0 && dataProvider.getRowCount() > 0) {
			TableRule rule = dataProvider.getRowObject(rowIndex);
			Column column = tableRuleSet.getColumns().getColumn().get(columnIndex);
			EList<TableRuleVariable> trvs = null;
			if (column.getColumnType() == ColumnType.CONDITION || column.getColumnType() == ColumnType.CUSTOM_CONDITION) {
				trvs = rule.getCondition();
			} else {
				trvs = rule.getAction();
			}
			for (TableRuleVariable tableRuleVariable : trvs) {
				if (tableRuleVariable.getColId().equals(column.getId())) {
					return tableRuleVariable;
				}
			}
		}
		return null;
	}	
}
