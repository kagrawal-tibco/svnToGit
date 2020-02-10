package com.tibco.cep.studio.decision.table.editor;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.GlazedListsSortModel;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.filterrow.DefaultGlazedListsFilterStrategy;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterRowHeaderComposite;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.DefaultColumnHeaderDataLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupHeaderLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupModel;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayerTransform;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.sort.SortHeaderLayer;
import org.eclipse.nebula.widgets.nattable.util.IClientAreaProvider;

import com.tibco.cep.studio.decision.table.configuration.DTColumnHeaderStyleConfiguration;

import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.matchers.CompositeMatcherEditor;

/**
 * Composite layer that comprises the column header layer of the decision table.
 * This class creates the data provider and data layer for the column header layer.
 * 
 * @author rhollom
 *
 * @param <TableRule>
 */
public class DTColumnHeaderLayerStack<TableRule> extends AbstractLayerTransform {

	private final ColumnHeaderLayer columnHeaderLayer;
	private final ColumnGroupHeaderLayer columnGroupHeaderLayer;
	private final SortHeaderLayer<TableRule> sortableColumnHeaderLayer;
	private final IDataProvider columnHeaderDataProvider;
	private final DefaultColumnHeaderDataLayer columnHeaderDataLayer;
	private FilterRowHeaderComposite<TableRule> filterRowHeaderComposite;
	private IColumnPropertyAccessor<TableRule> columnPropertyAccessor;

	public DTColumnHeaderLayerStack(SortedList<TableRule> sortedList,
												FilterList<TableRule> filterList,
												IDataProvider columnHeaderDataProvider,
												IColumnPropertyAccessor<TableRule> columnAccessor,
												ILayer bodyLayer,
												SelectionLayer selectionLayer,
												ColumnGroupModel columnGroupModel,
												IConfigRegistry configRegistry) {

		this.columnHeaderDataProvider = columnHeaderDataProvider;//new DefaultColumnHeaderDataProvider(propertyNames, propertyToLabelMap);
		
		this.columnPropertyAccessor = columnAccessor;

		columnHeaderDataLayer = new DefaultColumnHeaderDataLayer(columnHeaderDataProvider);

		columnHeaderLayer = new ColumnHeaderLayer(columnHeaderDataLayer, bodyLayer, selectionLayer);
		
		columnGroupHeaderLayer = new ColumnGroupHeaderLayer(columnHeaderLayer, selectionLayer, columnGroupModel);
		columnGroupHeaderLayer.addConfiguration(new DTColumnHeaderStyleConfiguration());
		columnGroupHeaderLayer.setCalculateHeight(true);
		columnGroupHeaderLayer.setRowHeight(30);
		sortableColumnHeaderLayer = new SortHeaderLayer<TableRule>(
				columnGroupHeaderLayer,
				new GlazedListsSortModel<TableRule>(
						sortedList,
						columnAccessor,
						configRegistry,
						columnHeaderDataLayer
				)
		);

		CompositeMatcherEditor<TableRule> autoFilterMatcherEditor = new CompositeMatcherEditor<TableRule>();
		filterList.setMatcherEditor(autoFilterMatcherEditor);		

		filterRowHeaderComposite =
			new FilterRowHeaderComposite<TableRule>(
					new DefaultGlazedListsFilterStrategy<TableRule>(filterList,
																	autoFilterMatcherEditor,
																	columnAccessor,
																	configRegistry
					),
					sortableColumnHeaderLayer, columnHeaderDataProvider, configRegistry);

		setUnderlyingLayer(filterRowHeaderComposite);
	}
	
	public void setFilterRowVisible(boolean visible) {
		filterRowHeaderComposite.setFilterRowVisible(visible);
	}
	
	@Override
	public void setClientAreaProvider(IClientAreaProvider clientAreaProvider) {
		super.setClientAreaProvider(clientAreaProvider);
	}

	public ColumnGroupHeaderLayer getColumnGroupHeaderLayer() {
		return columnGroupHeaderLayer;
	}

	public ColumnHeaderLayer getColumnHeaderLayer() {
		return columnHeaderLayer;
	}

	public IDataProvider getColumnHeaderDataProvider() {
		return columnHeaderDataProvider;
	}

	public DefaultColumnHeaderDataLayer getColumnHeaderDataLayer() {
		return columnHeaderDataLayer;
	}
	
	public IColumnPropertyAccessor<TableRule> getColumnPropertyAccessor() {
		return columnPropertyAccessor;
	}
}
