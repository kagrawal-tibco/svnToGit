package com.tibco.cep.studio.decision.table.ui.constraintpane;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.window.DefaultToolTip;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultColumnHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultCornerDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultRowHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.CornerLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.RowHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.IConfigLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.layer.stack.DefaultBodyLayerStack;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.ViewPart;

import com.tibco.cep.studio.decision.table.configuration.DTColumnHeaderStyleConfiguration;
import com.tibco.cep.studio.decision.table.configuration.DTRowHeaderStyleConfiguration;
import com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerView;
import com.tibco.cep.studio.decision.table.constraintpane.IDecisionTableTestDataCoverageView;
import com.tibco.cep.studio.decision.table.editor.DecisionTableDesignViewer;
import com.tibco.cep.studio.tester.core.model.TestDataModel;
import com.tibco.cep.studio.ui.util.ColorConstants;
import com.tibco.util.StringUtilities;

/**
 * @author vdhumal
 *
 */
public class DecisionTableTestDataCoverageView extends ViewPart implements IDecisionTableTestDataCoverageView {

	Composite composite = null;
	private NatTable natTable = null;
	
	private TestDataModel testDataModel = null;
	private Map<Integer, List<String>> testDataCoverageResult = null;
	
	public DecisionTableTestDataCoverageView() {
	}
	
	@Override
	public void createPartControl(Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		refreshResultsView();
	}
	
	@Override
	public void setFocus() {
		this.composite.setFocus();		
	}	
	
	public void refreshResultsView() {
		DecisionTableAnalyzerView view = (DecisionTableAnalyzerView) getSite().getPage().findView(DecisionTableAnalyzerView.ID);
		if (view != null) {		
		testDataModel = (TestDataModel) view.getTestDataModel();
		testDataCoverageResult = view.getTestDataCoverageResult();
		createTestDataTable();
		this.composite.layout();
		}
	}
	
	public void createTestDataTable(){			
		if (testDataModel == null)
			return;

		final String[] propertyNames = testDataModel.getTableColumnNames().toArray(new String[testDataModel.getTableColumnNames().size()]);		
		IColumnPropertyAccessor<List<String>> columnPropertyAccessor = new TestDataCoverageTableColumnPropertyAccessor(propertyNames, testDataModel);
		List<List<String>> selectedTestData = DecisionTableTestDataCoverageUtils.getSelectedTestData(testDataModel);
		IDataProvider bodyDataProvider = new ListDataProvider<List<String>>(selectedTestData, columnPropertyAccessor);

		DataLayer bodyDataLayer = new DataLayer(bodyDataProvider);
		bodyDataLayer.setDefaultRowHeight(20);
		bodyDataLayer.setDefaultColumnWidth(125);
		DefaultBodyLayerStack bodyLayer = new DefaultBodyLayerStack(bodyDataLayer);
		bodyLayer.setConfigLabelAccumulator(new IConfigLabelAccumulator() {
			
			@Override
			public void accumulateConfigLabels(LabelStack configLabels, int columnPosition, int rowPosition) {
				if(propertyNames != null && columnPosition >= 0 && rowPosition >= 0) {
					
					if (testDataCoverageResult != null) {
						Set<Integer> testDataRowNumSet = testDataCoverageResult.keySet();
						for (Integer rowNum : testDataRowNumSet) {
							if (rowNum.equals(rowPosition)) {
								configLabels.addLabel(DecisionTableDesignViewer.ANALYZER_COVERAGE_LABEL);
							}
						}
					}
				}	
			}
		});		
		
		DefaultColumnHeaderDataProvider colHeaderDataProvider = new DefaultColumnHeaderDataProvider(propertyNames, null);
		DataLayer collayer = new DataLayer(colHeaderDataProvider);
		ColumnHeaderLayer columnHeaderLayer = new ColumnHeaderLayer(collayer, bodyLayer, bodyLayer.getSelectionLayer());

		//RowHeaderLayerStack rowHeaderLayer = new RowHeaderLayerStack(rowHeaderDataProvider);
		DefaultRowHeaderDataProvider rowHeaderDataProvider = new DefaultRowHeaderDataProvider(bodyDataProvider);
		DataLayer rowlayer = new DataLayer(rowHeaderDataProvider);
		RowHeaderLayer rowHeaderLayer = new RowHeaderLayer(rowlayer, bodyLayer, bodyLayer.getSelectionLayer());
		
		DefaultCornerDataProvider cornerDataProvider = new DefaultCornerDataProvider(colHeaderDataProvider, rowHeaderDataProvider);
		CornerLayer cornerLayer = new CornerLayer(new DataLayer(cornerDataProvider), rowHeaderLayer, columnHeaderLayer);
		GridLayer gridLayer = new GridLayer(bodyLayer, columnHeaderLayer, rowHeaderLayer, cornerLayer);
		
		TableWrapLayout gld = new TableWrapLayout();
		gld.makeColumnsEqualWidth = true;

		TableWrapData gd1 = new TableWrapData(TableWrapData.FILL);
		gd1.grabHorizontal = true;
		gd1.heightHint = 100;
		if(natTable != null){
			natTable.dispose();
		}

		natTable = new NatTable(composite, NatTable.DEFAULT_STYLE_OPTIONS | SWT.BORDER, gridLayer, false);
		natTable.setLayout(gld);
		natTable.setLayoutData(gd1);		
		natTable.addConfiguration(new DefaultNatTableStyleConfiguration() {
			@Override
			public void configureRegistry(IConfigRegistry configRegistry) {
				Style testDataCoverageStyle = new Style();
				testDataCoverageStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, ColorConstants.lightGreen);
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, testDataCoverageStyle, DisplayMode.NORMAL, DecisionTableDesignViewer.ANALYZER_COVERAGE_LABEL);
				super.configureRegistry(configRegistry);
			}
		});
		natTable.addConfiguration(new DTRowHeaderStyleConfiguration());
		natTable.addConfiguration(new DTColumnHeaderStyleConfiguration());
		attachToolTip(natTable);
		natTable.configure();		
	}
		
	private void attachToolTip(NatTable natTable) {
		DefaultToolTip toolTip = new TestResultsInfoToolTip(natTable);
		toolTip.setBackgroundColor(natTable.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		toolTip.setPopupDelay(300);
		toolTip.activate();
		toolTip.setShift(new Point(10, 10));
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.decision.table.ui.constraintpane.IDecisionTableTestDataCoverageView#clear()
	 */
	@Override
	public void clear() {
		if (natTable != null) {
			natTable.dispose();
			natTable = null;
		}
	}
	
	class TestResultsInfoToolTip extends DefaultToolTip {

		private NatTable natTable = null;
		
		public TestResultsInfoToolTip(NatTable natTable) {
			super(natTable, ToolTip.NO_RECREATE, false);
			this.natTable = natTable;
		}
		
		@Override
		protected Object getToolTipArea(Event event) {
			int col = natTable.getColumnPositionByX(event.x);
			int row = natTable.getRowPositionByY(event.y);
			return new Point(col, row);
		}
		
		@Override
		protected String getText(Event event) {
			int row = natTable.getRowPositionByY(event.y);
			row--; //Corresponding Id in the results map
			if (testDataCoverageResult != null) {
				List<String> ruleIdList = testDataCoverageResult.get(row);
				if (ruleIdList != null) {
					return "Rules: " + StringUtilities.join(ruleIdList.toArray(new String[ruleIdList.size()]), " , ");
				}	
			}			
			return null;
		}
		
		@Override
		protected boolean shouldCreateToolTip(Event event) {
			int row = natTable.getRowPositionByY(event.y);
			row--; //Corresponding Id in the results map
			if (testDataCoverageResult != null && testDataCoverageResult.containsKey(row)) {
				return super.shouldCreateToolTip(event);
			}
			return false;
		}	
	}
	
}
