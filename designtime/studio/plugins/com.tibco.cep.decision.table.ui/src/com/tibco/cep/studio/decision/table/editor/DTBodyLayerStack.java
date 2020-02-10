package com.tibco.cep.studio.decision.table.editor;

import java.beans.PropertyChangeListener;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.data.IRowIdAccessor;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.GlazedListsEventLayer;
import org.eclipse.nebula.widgets.nattable.freeze.CompositeFreezeLayer;
import org.eclipse.nebula.widgets.nattable.freeze.FreezeLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupExpandCollapseLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupModel;
import org.eclipse.nebula.widgets.nattable.hideshow.ColumnHideShowLayer;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayerTransform;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.SpanningDataLayer;
import org.eclipse.nebula.widgets.nattable.layer.config.ColumnStyleChooserConfiguration;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.util.IClientAreaProvider;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;

import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.studio.decision.table.providers.SpanningGlazedListsDataProvider;

import ca.odell.glazedlists.EventList;

/**
 * Composite layer that comprises the body of the decision table.  This
 * class creates the data provider and the data layer for the body layer.
 * 
 * @author rhollom
 *
 * @param <TableRule>
 */
public class DTBodyLayerStack<TableRule> extends AbstractLayerTransform {

//	private ColumnReorderLayer columnReorderLayer;
//	private ColumnGroupReorderLayer columnGroupReorderLayer;
	private ColumnHideShowLayer columnHideShowLayer;
	private ColumnGroupExpandCollapseLayer columnGroupExpandCollapseLayer;
	private final SelectionLayer selectionLayer;
	private final ViewportLayer viewportLayer;
	private DataLayer bodyDataLayer;
	private FreezeLayer freezeLayer;
	private CompositeFreezeLayer compositeFreezeLayer;
	private SpanningGlazedListsDataProvider<TableRule> bodyDataProvider;
	private GlazedListsEventLayer<TableRule> glazedListsEventLayer;
	private TableRuleSet ruleSet;
	private DecisionTableDesignViewer dtViewer;

	public DTBodyLayerStack(String projectName,
			EventList<TableRule> eventList,
			IRowIdAccessor<TableRule> rowIdAccessor,
			IColumnPropertyAccessor<TableRule> columnPropertyAccessor,
			IConfigRegistry configRegistry,
			ColumnGroupModel columnGroupModel,
			TableRuleSet ruleSet,
			DecisionTableDesignViewer dtViewer) {
		this(projectName, eventList, rowIdAccessor, columnPropertyAccessor, configRegistry, columnGroupModel, true, ruleSet, dtViewer);
	}

	public DTBodyLayerStack(String projectName,
			EventList<TableRule> eventList,
			IRowIdAccessor<TableRule> rowIdAccessor,
			IColumnPropertyAccessor<TableRule> columnPropertyAccessor,
			IConfigRegistry configRegistry,
			ColumnGroupModel columnGroupModel,
			boolean useDefaultConfiguration, 
			TableRuleSet ruleSet,
			DecisionTableDesignViewer dtViewer) {

		this.ruleSet = ruleSet;
		this.dtViewer = dtViewer;
		bodyDataProvider = new SpanningGlazedListsDataProvider<TableRule>(projectName, eventList, columnPropertyAccessor);
		bodyDataLayer = new SpanningDataLayer(bodyDataProvider);
		bodyDataLayer.setDefaultRowHeight(20);
		bodyDataLayer.setDefaultColumnWidth(125);
		glazedListsEventLayer = new GlazedListsEventLayer<TableRule>(bodyDataLayer, eventList);

		//We are not using the built-in reordering (Drag & Drop), instead we have the context menu based reordering options 
//		columnReorderLayer = new ColumnReorderLayer(glazedListsEventLayer);
//		columnGroupReorderLayer = new ColumnGroupReorderLayer(columnReorderLayer, columnGroupModel);
        
		columnHideShowLayer = new ColumnHideShowLayer(glazedListsEventLayer);
		columnGroupExpandCollapseLayer = new ColumnGroupExpandCollapseLayer(columnHideShowLayer, columnGroupModel);
//		RowReorderLayer rowReorderLayer =
//				new RowReorderLayer(columnGroupExpandCollapseLayer);
//		rowReorderLayer.registerCommandHandler(new RowReorderEndCommandHandler(rowReorderLayer) {
//			
//			@Override
//			public Class<RowReorderEndCommand> getCommandClass() {
//				return super.getCommandClass();
//			}
//			
//			@Override
//			protected boolean doCommand(RowReorderEndCommand command) {
//				int toRowPosition = command.getToRowPosition();
//				int fromRowPosition = rowReorderLayer.getReorderFromRowPosition();
//				if (toRowPosition == fromRowPosition) {
//					return true;
//				}
//				TableRule rowObject = bodyDataProvider.getRowObject(fromRowPosition);
//				TableRule toRowObject = bodyDataProvider.getRowObject(toRowPosition);
//				int fromIdx = ruleSet.getRule().indexOf(rowObject);
//				int toIdx = ruleSet.getRule().indexOf(toRowObject);
//				if (fromIdx != toIdx && super.doCommand(command)) {
//					ruleSet.getRule().move(toIdx, (com.tibco.cep.decision.table.model.dtmodel.TableRule) rowObject);
//				}
//				dtViewer.getEditor().modified();
//				return true;
//			}
//		});
		selectionLayer = new SelectionLayer(columnGroupExpandCollapseLayer);
		viewportLayer = new ViewportLayer(selectionLayer);
		freezeLayer = new FreezeLayer(selectionLayer);
	    compositeFreezeLayer = new CompositeFreezeLayer(freezeLayer, viewportLayer, selectionLayer);

		setUnderlyingLayer(compositeFreezeLayer);

		if (useDefaultConfiguration) {
			addConfiguration(new ColumnStyleChooserConfiguration(this, selectionLayer));
		}
		
	}

	@Override
	public void setClientAreaProvider(IClientAreaProvider clientAreaProvider) {
		super.setClientAreaProvider(clientAreaProvider);
	}

//	public ColumnReorderLayer getColumnReorderLayer() {
//		return columnReorderLayer;
//	}

	public ColumnHideShowLayer getColumnHideShowLayer() {
		return columnHideShowLayer;
	}

	public SelectionLayer getSelectionLayer() {
		return selectionLayer;
	}

	public ViewportLayer getViewportLayer() {
		return viewportLayer;
	}

	public DataLayer getBodyDataLayer() {
		return bodyDataLayer;
	}

	public SpanningGlazedListsDataProvider<TableRule> getBodyDataProvider() {
		return bodyDataProvider;
	}

	public ColumnGroupExpandCollapseLayer getColumnGroupExpandCollapseLayer() {
		return columnGroupExpandCollapseLayer;
	}

	public PropertyChangeListener getGlazedListEventsLayer() {
		return glazedListsEventLayer;
	}
	
	public void setSpan(boolean span) {
		this.bodyDataProvider.setSpan(span);
	}
	
	public boolean isSetSpan() {
		return this.bodyDataProvider.isSetSpan();
	}
	
//	@Override
//	public Object getDataValueByPosition(int column, int row) {
//		
//	}
}
