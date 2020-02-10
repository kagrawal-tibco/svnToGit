package com.tibco.cep.webstudio.client.decisiontable;

import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.tibco.cep.webstudio.client.decisiontable.model.ColumnType;
import com.tibco.cep.webstudio.client.decisiontable.model.TableColumn;

/**
 * @author sasahoo
 *
 */
public class TableCellHoverCustomizer extends HoverCustomizer {

	private AbstractTableEditor editor;
	private boolean isDecisionTable;
	
	public TableCellHoverCustomizer(AbstractTableEditor editor, boolean isDecisionTable) {
		this.editor = editor; 
		this.isDecisionTable = isDecisionTable;
	}
	
	@Override
	public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
		String fieldName = "";
		boolean isCustom = false;
		if (isDecisionTable) {
			fieldName = editor.getDecisionTableDataGrid().getField(colNum).getName();
		} else {
			fieldName = editor.getExceptionTableDataGrid().getField(colNum).getName();
		}

		if (!fieldName.isEmpty()) {
			TableColumn column = DecisionTableUtils.getColumn(isDecisionTable ? 
					editor.getTable().getDecisionTable().getColumns() : editor.getTable().getExceptionTable().getColumns(), fieldName);
			if (column.getColumnType().equals(ColumnType.CUSTOM_CONDITION.getName()) 
					|| column.getColumnType().equals(ColumnType.CUSTOM_ACTION.getName())) {
				isCustom = true;
			}
		}
		if (colNum > 0  && isCustom) {
			String description = record.getAttribute(fieldName);
			description = description == null ? "" : description;
			String html = "<b>"+ fieldName +"</b> :<br> " + description;
			return html;
		}
		return null;
	}

}