package com.tibco.cep.studio.decision.table.configuration;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.IConfigLabelAccumulator;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.PropertyType;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.studio.decision.table.editor.DTBodyLayerStack;
import com.tibco.cep.studio.decision.table.editor.DecisionTableDesignViewer;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.decision.table.utils.DecisionTableUtil;

public class DTLabelAccumulator implements IConfigLabelAccumulator{

	IDecisionTableEditor editor;
	NatTable natTable;
	private String projectName;
	EList<Column> columns;
	
	public DTLabelAccumulator(DTBodyLayerStack<TableRule> bodyLayer, IDecisionTableEditor editor, NatTable natTable,TableRuleSet ruleSet, String projectName) {
		this.editor = editor;
		this.natTable = natTable;
		this.projectName = projectName;
		columns = (ruleSet.getColumns() != null) ? ruleSet.getColumns().getColumn() : null;
	}
	
	@Override
	public void accumulateConfigLabels(LabelStack configLabels, int columnPosition, int rowPosition) {
//		int columnIndex = bodyLayer.getColumnIndexByPosition(columnPosition);
//		int rowIndex = bodyLayer.getRowIndexByPosition(rowPosition);
		int columnIndex = columnPosition;
		int rowIndex = rowPosition;
		if(columns != null && columnIndex >= 0 && rowIndex >= 0) {
			Column col = columns.get(columnIndex);
			
			List<String> rowsToHighLight = editor.getRowsToHighlight();
			if (rowsToHighLight != null) {
				for (String rowNum : rowsToHighLight) {
					int row = Integer.parseInt(rowNum);
					if (rowIndex == row) {
						configLabels.addLabel(DecisionTableDesignViewer.ANALYZER_COVERAGE_LABEL);
					}
				}
			}
			
			Object value = editor.getModelDataByPosition(columnIndex, rowIndex, natTable);
			TableRuleVariable trv = null;
			if (value != null && value instanceof TableRuleVariable) {
				trv = (TableRuleVariable) value;
				// determine whether cell is disabled
				if(!trv.isEnabled()) {
					configLabels.addLabel(DecisionTableDesignViewer.DISABLE_LABEL);
				}
			} else if (value == null) {
				//configLabels.addLabel(DecisionTableDesignViewer.VALUE_NOTSET_LABEL);
			}
			
			// Style action columns differently
			boolean alternateColors = DecisionTableUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.ALTERNATE_ROW_COLORS);
			if (col.getColumnType() == ColumnType.ACTION || col.getColumnType() == ColumnType.CUSTOM_ACTION) {
				if (!alternateColors || rowPosition % 2 == 0) {
					configLabels.addLabel(DecisionTableDesignViewer.ACTIONS_GROUP_LABEL);
				} else {
					configLabels.addLabel(DecisionTableDesignViewer.ACTIONS_GROUP_ALT_LABEL);
				}
			}
			else if (col.getColumnType() == ColumnType.CONDITION || col.getColumnType() == ColumnType.CUSTOM_CONDITION) {
				if (!alternateColors || rowPosition % 2 == 0) {
					configLabels.addLabel(DecisionTableDesignViewer.CONDITIONS_GROUP_LABEL);
				} else {
					configLabels.addLabel(DecisionTableDesignViewer.CONDITIONS_GROUP_ALT_LABEL);
				}
			}
			if (DecisionTableUtil.checkDateType(col.getPropertyType())) {
				configLabels.addLabel(DecisionTableDesignViewer.DATETIME_LABEL);
			}
//			Map<String, DomainEntry> domainValues = DTDomainUtil.getDomainEntries(projectName, col);
			List<List<String>> domainValues = editor.getDomainValues(col);
			
			// determine whether column type is a combo-box or boolean type
			if (domainValues != null && domainValues.size() > 1) {
				// determine whether comment is present
				if(trv != null && trv.getComment() != null && !trv.getComment().isEmpty()) {
					configLabels.addLabel(DecisionTableDesignViewer.COMBO_BOX_COMMENT_LABEL);
				}
				if (col.getColumnType() == ColumnType.ACTION) {
					configLabels.addLabel(DecisionTableDesignViewer.COMBO_BOX_CONFIG_LABEL);
					configLabels.addLabel(DecisionTableDesignViewer.COMBO_BOX_EDITOR_CONFIG_LABEL);
					configLabels.addLabel(DecisionTableDesignViewer.COMBO_BOX_DISPLAY_EDITOR_CONFIG_LABEL);
				} else {
					configLabels.addLabel(DecisionTableDesignViewer.MULTI_COMBO_BOX_CONFIG_LABEL);
					configLabels.addLabel(DecisionTableDesignViewer.MULTI_COMBO_BOX_EDITOR_CONFIG_LABEL);
					configLabels.addLabel(DecisionTableDesignViewer.MULTI_COMBO_BOX_DISPLAY_EDITOR_CONFIG_LABEL);
				}
			} else if (domainValues!= null && domainValues.size() == 1 && !domainValues.get(0).contains("*")) {
				// determine whether comment is present
				if(trv != null && trv.getComment() != null && !trv.getComment().isEmpty()) {
					configLabels.addLabel(DecisionTableDesignViewer.COMBO_BOX_COMMENT_LABEL);
				}
				if (col.getColumnType() == ColumnType.ACTION) {
					configLabels.addLabel(DecisionTableDesignViewer.COMBO_BOX_CONFIG_LABEL);
					configLabels.addLabel(DecisionTableDesignViewer.COMBO_BOX_EDITOR_CONFIG_LABEL);
					configLabels.addLabel(DecisionTableDesignViewer.COMBO_BOX_DISPLAY_EDITOR_CONFIG_LABEL);
				} else {
					configLabels.addLabel(DecisionTableDesignViewer.MULTI_COMBO_BOX_CONFIG_LABEL);
					configLabels.addLabel(DecisionTableDesignViewer.MULTI_COMBO_BOX_EDITOR_CONFIG_LABEL);
					configLabels.addLabel(DecisionTableDesignViewer.MULTI_COMBO_BOX_DISPLAY_EDITOR_CONFIG_LABEL);
				}	
			} else if (col.getPropertyType() == PropertyType.PROPERTY_TYPE_BOOLEAN_VALUE) {
				// determine whether comment is present
				if(trv != null && trv.getComment() != null && !trv.getComment().isEmpty()) {
					configLabels.addLabel(DecisionTableDesignViewer.CHECK_BOX_COMMENT_LABEL);
				}
				if (col.getColumnType() != ColumnType.CUSTOM_CONDITION && col.getColumnType() != ColumnType.CUSTOM_ACTION && !col.isArrayProperty()) {
					// don't present checkbox for custom conditions/actions, as it prevents user from typing in expression
					// don't present checkbox for boolean property array
					configLabels.addLabel(DecisionTableDesignViewer.CHECK_BOX_CONFIG_LABEL);
					configLabels.addLabel(DecisionTableDesignViewer.CHECK_BOX_EDITOR_CONFIG_LABEL);
				}
			} else {
				if(trv != null && trv.getComment() != null && !trv.getComment().isEmpty()) {
					configLabels.addLabel(DecisionTableDesignViewer.TEXT_BOX_COMMENT_LABEL);
				}				
			}
		}
	}
}
