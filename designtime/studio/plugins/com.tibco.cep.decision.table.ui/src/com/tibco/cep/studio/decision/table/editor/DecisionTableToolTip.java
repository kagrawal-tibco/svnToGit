package com.tibco.cep.studio.decision.table.editor;

import org.eclipse.jface.window.DefaultToolTip;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.RowHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.Property;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.studio.decision.table.providers.SpanningGlazedListsDataProvider;

/**
 * Responsible for calculating the tooltips that are
 * displayed when the user hovers over a cell
 * 
 * @author rhollom
 *
 */
public class DecisionTableToolTip extends DefaultToolTip {

	protected NatTable natTable;
	IDecisionTableEditor editor;
	
	public DecisionTableToolTip(NatTable natTable, IDecisionTableEditor editor) {
		super(natTable, ToolTip.NO_RECREATE, false);
		this.natTable = natTable;
		this.editor = editor;
	}
	
	protected Object getToolTipArea(Event event) {
		int col = natTable.getColumnPositionByX(event.x);
		int row = natTable.getRowPositionByY(event.y);
		
		return new Point(col, row);
	}
	
	protected String getText(Event event) {
		int col = natTable.getColumnPositionByX(event.x);
		int row = natTable.getRowPositionByY(event.y);
		ILayerCell cell= natTable.getCellByPosition(col, row);
		if (cell == null) {
			return null;
		}
		ILayer underlyingLayer = natTable.getLayer().getUnderlyingLayerByPosition(col, row);
		if (underlyingLayer instanceof RowHeaderLayer) {
			DTBodyLayerStack<TableRule> targetLayer = (DTBodyLayerStack<TableRule>) ((GridLayer) natTable.getLayer()).getBodyLayer();
			SpanningGlazedListsDataProvider<TableRule> dataProvider = targetLayer.getBodyDataProvider();
			TableRule rowObject = dataProvider.getRowObject(cell.getRowIndex());
			if (rowObject != null) {
				return getTableRuleText(rowObject);
			}
		}
		if (underlyingLayer instanceof DTColumnHeaderLayerStack) {
			Column column = editor.getTable().getDecisionTable().getColumns().getColumn().get(col-1);
			if (column != null) {
				return getColumnText(column);
			}
		}
		TableRuleVariable ruleVariable = (TableRuleVariable) editor.getModelDataByPosition(cell.getColumnIndex(), cell.getRowIndex(), natTable);
		if (ruleVariable != null) {
			return getTableRuleVariableText(ruleVariable);
		}
		return null;
	}
	
	protected String getColumnText(Column column) {
		switch (column.getColumnType()) {
		case CUSTOM_ACTION:
			break;

		case CUSTOM_CONDITION:
			break;
			
		case ACTION:
			break;
			
		case CONDITION:
			break;
			
		default:
			break;
		}
		return null;
	}

	protected String getTableRuleVariableText(TableRuleVariable ruleVariable) {
		String comment = (ruleVariable == null) ? null : ruleVariable.getComment();
//		return "Value: "+cell.getDataValue()+"\nCell Position: ("+cell.getColumnIndex()+","+cell.getRowIndex()+")";
		if (comment == null || comment.isEmpty()) {
			return null;
		}
		else {
			return comment;
		}
	}

	protected String getTableRuleText(TableRule rule) {
		// The Rule comments are stored as a MetaData property named "Description"
		MetaData metaData = rule.getMd();
		if (metaData != null) {
			for (Property prop : metaData.getProp()) {
				if (prop.getName() != null && prop.getName().equalsIgnoreCase("Description")) {
					return prop.getValue();
				}
			}
		}
		return null;
	}

	protected Composite createToolTipContentArea(Event event, Composite parent) {
		// If we want more elaborate tooltips we can create them here
		return super.createToolTipContentArea(event, parent);
	}
	
	@Override
	protected boolean shouldCreateToolTip(Event event) {
	// Only show tooltip if comment is present
	if (getText(event) != null) {
		return super.shouldCreateToolTip(event);
	}
	return false;
	}
		
	
}