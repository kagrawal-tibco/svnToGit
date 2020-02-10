package com.tibco.cep.studio.tester.ui.editor.result;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultColumnHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultCornerDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultRowHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.CornerLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.RowHeaderLayer;
import org.eclipse.nebula.widgets.nattable.hideshow.ColumnHideShowLayer;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayerTransform;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.IConfigLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.layer.stack.DefaultBodyLayerStack;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.CustomLineBorderDecorator;
import org.eclipse.nebula.widgets.nattable.reorder.ColumnReorderLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.style.BorderStyle;
import org.eclipse.nebula.widgets.nattable.style.BorderStyle.LineStyleEnum;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.tester.core.model.PropertyModificationType;
import com.tibco.cep.studio.tester.core.model.PropertyType;
import com.tibco.cep.studio.tester.core.utils.TesterCoreUtils;
import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.tester.ui.editor.result.ResultTableColumnPropertyAccessor.TABLE_VALUE_TYPE;
import com.tibco.cep.studio.tester.ui.preferences.TesterPreferenceConstants;
import com.tibco.cep.studio.tester.ui.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class ResultDetailsPage extends AbstractResultDetailsPage {

	private String[] propertyNames;
	private Map<String, String> propertyToLabels;
	private NatTable initNatTable = null;
	private NatTable prevNatTable = null;
	private NatTable newNatTable = null;
	@SuppressWarnings("unused")
	private boolean modifyInstance = false;
	private Map<Integer,String> modifiedColumnNameToNumberMap=null;
	   
	/**
	 * @param projectName
	 * @param projectPath
	 * @param ruleSessionName 
	 */
	public ResultDetailsPage(String projectName, 
			String projectPath, String ruleSessionName) {
		this.projectPath = projectPath;	
		this.projectName = projectName;
		this.ruleSessionName = ruleSessionName;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#refresh()
	 */
	@Override
	public void refresh() {
		try {
			update();
		}
		catch(Exception e) {
			StudioTesterUIPlugin.log(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IPartSelectionListener#selectionChanged(org.eclipse.ui.forms.IFormPart, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		super.selectionChanged(part, selection);
		try {
			update();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @throws Exception
	 */
	private void update() throws Exception {
		if (conceptType == null && eventType == null) {
			return;
		}
		if (conceptType == null || eventType == null) {
			populateData();
		}
	}
	
	private String fullElementPath = null;

	
	private void populateData() throws Exception {
		
		if (conceptType != null) {
			fullElementPath = TesterCoreUtils.getFullPath(conceptType);
		}
		if (eventType != null) {
			fullElementPath = TesterCoreUtils.getFullPath(eventType);
		}
		 
		ELEMENT_TYPES type = null;
		if (conceptType != null) {
			Entity entity = IndexUtils.getEntity(projectName, fullElementPath);
			if (IndexUtils.getElementType(entity) == ELEMENT_TYPES.CONCEPT) {
				type =  ELEMENT_TYPES.CONCEPT;
			} else {
				type =  ELEMENT_TYPES.SCORECARD;
			}
		}
		if (eventType != null) {
			type =  ELEMENT_TYPES.SIMPLE_EVENT;
		}
		final Entity entity = IndexUtils.getEntity(projectName, fullElementPath, type);
		
		EList<PropertyDefinition> lst = null;
		if (entity instanceof Event) {
			lst = ((Event) entity).getAllUserProperties();
		} else if (entity instanceof Scorecard) {
			lst = ((Scorecard) entity).getAllProperties();
		} else if (entity instanceof Concept) {
			lst = ((Concept) entity).getAllProperties();
		}
		int i=0;
		propertyNames=new String[lst.size()+2];
		propertyNames[i++]="ID";
		propertyNames[i++]="ExtId";
		for(PropertyDefinition def:lst){
			propertyNames[i++]=def.getName();
		}
		
//		List<PropertyModificationType> modifiedPropertyList = entityType.getModifiedProperty();
//		List<PropertyType> list=entityType.getProperty();
		
		boolean editable = !(form instanceof AbstractResultFormViewer);
		
		if (newNatTable != null) {
			newNatTable.dispose();
		}
		newNatTable = createNatTable(newValSectionClient, editable, TABLE_VALUE_TYPE.NEW);
		if (entity != null) {
			if (modifiedResult) {
				testResultNewSection.setText(Messages.getString("result.show.after") + "         ");
				testResultInitValSection.setVisible(true);
				testResultPrevValSection.setVisible(true);
				if (prevNatTable != null) {
					prevNatTable.dispose();
				}
				prevNatTable = createNatTable(previousValSectionClient, editable, TABLE_VALUE_TYPE.PREVIOUS);
				if (initNatTable != null) {
					initNatTable.dispose();
				}
				initNatTable = createNatTable(initialValSectionClient, editable, TABLE_VALUE_TYPE.INITIAL);
			} else {
				testResultNewSection.setText(Messages.getString("result.show") + "         ");
				testResultPrevValSection.setVisible(false);
				testResultInitValSection.setVisible(false);
			}
			if(form instanceof AbstractResultFormViewer) {
				modifyInstance = false;
				modifyInstanceButton.setVisible(false);
			} else {
				modifyInstance = true;
				modifyInstanceButton.setVisible(true);
			}
		}
	}

	/**
	 * @param list 
	 * @param table
	 * @param fullElementPath
	 * @param entityType
	 * @throws Exception
	 */
	/*private void refreshResultData(JTable table, String fullElementPath, EntityType entityType, boolean isOldResultTable) throws Exception {
		Vector<Vector<? extends Object>> dataVector = new Vector<Vector<? extends Object>>();
		Map<String, Vector<Vector<? extends Object>>> map = saveResult(dataVector, entityType, fullElementPath, projectName, isOldResultTable);
		if (map != null) {
			final Set<String> keys = map.keySet();
			DefaultTableModel model = (DefaultTableModel) TableModelWrapperUtils.getActualTableModel(table.getModel());
			for (int k = model.getRowCount() - 1; k > -1; k--) {
				model.removeRow(k);
			}
			
			for (String key : keys) {
				Vector<Vector<? extends Object>> data = map.get(key);

				int count = 0;
				for(Vector<? extends Object> v : data){
					model.insertRow(count, v);
					count++;
				}
			}
		}
	}*/

	
	
	public class BodyLayerStack extends AbstractLayerTransform {
		private SelectionLayer selectionLayer;
		public BodyLayerStack(IDataProvider dataProvider) {
			DataLayer bodyDataLayer = new DataLayer(dataProvider);
			ColumnReorderLayer columnReorderLayer = new ColumnReorderLayer(bodyDataLayer);
			ColumnHideShowLayer columnHideShowLayer = new ColumnHideShowLayer(columnReorderLayer);
			selectionLayer = new SelectionLayer(columnHideShowLayer);
			ViewportLayer viewportLayer = new ViewportLayer(selectionLayer);
			setUnderlyingLayer(viewportLayer);
		}
		public SelectionLayer getSelectionLayer() {
			return selectionLayer;
		}
	}
	
	/*public class ColumnHeaderLayerStack extends AbstractLayerTransform {
		public ColumnHeaderLayerStack(IDataProvider dataProvider) {
			DataLayer dataLayer = new DataLayer(dataProvider);
			ColumnHeaderLayer colHeaderLayer = new ColumnHeaderLayer(dataLayer, bodyLayer, bodyLayer.getSelectionLayer());
			setUnderlyingLayer(colHeaderLayer);
		}
	}
	
	public class RowHeaderLayerStack extends AbstractLayerTransform {
		public RowHeaderLayerStack(IDataProvider dataProvider) {
			DataLayer dataLayer = new DataLayer(dataProvider, 50, 20);
			RowHeaderLayer rowHeaderLayer = new RowHeaderLayer(dataLayer, bodyLayer, bodyLayer.getSelectionLayer());
			setUnderlyingLayer(rowHeaderLayer);
		}
	}*/
	
	private NatTable createNatTable(Composite parentSection, boolean editable, TABLE_VALUE_TYPE tableValueType){
		
		List<Object> list=new LinkedList<Object>();
		List<PropertyType> propertyList=entityType.getProperty();
		List<PropertyModificationType> modifiedPropertylist=entityType.getModifiedProperty();
		
		list.add(entityType.getId());
		list.add(entityType.getExtId());
		for(String propName : propertyNames){
			for(PropertyType pt:propertyList){
				if(pt.getName().equalsIgnoreCase(propName)){
					list.add(pt);
					break;
				}
			}
			for(PropertyModificationType pmt:modifiedPropertylist){
				if(pmt.getName().equalsIgnoreCase(propName)){
					list.add(pmt);
					break;
				}
			}
		}
		modifiedColumnNameToNumberMap=new LinkedHashMap<Integer,String>();
		for(PropertyModificationType pName: modifiedPropertylist){
			int colNum=0;
			for(String str:propertyNames){
				if(str.equalsIgnoreCase(pName.getName())){
					break;
				}
				colNum++;
			}
			modifiedColumnNameToNumberMap.put(colNum,pName.getName());
		}

		//List<Object> list=entityType.getProperty();
		List<List<Object>> myList=new ArrayList<List<Object>>();
		myList.add(list);
	
		IColumnPropertyAccessor<List<Object>> columnPropertyAccessor = new ResultTableColumnPropertyAccessor(propertyNames, this, tableValueType);
		IDataProvider bodyDataProvider = new ListDataProvider<List<Object>>(myList, columnPropertyAccessor);
		final DataLayer bodyDataLayer = new DataLayer(bodyDataProvider);
		bodyDataLayer.setDefaultRowHeight(20);
		bodyDataLayer.setDefaultColumnWidth(125);
		IConfigLabelAccumulator cellLabelAccumulator = new IConfigLabelAccumulator() {
			public void accumulateConfigLabels(LabelStack configLabels, int columnPosition, int rowPosition) {
				int columnIndex = bodyDataLayer.getColumnIndexByPosition(columnPosition);
				int rowIndex = bodyDataLayer.getRowIndexByPosition(rowPosition);
				if (modifiedColumnNameToNumberMap.keySet().contains(columnIndex) && rowIndex == 0) {
					configLabels.addLabel("foo");
				}
				/*if (columnIndex == 2 && rowIndex == 0) {
					configLabels.addLabel("bar");
				}
				
				//add labels for surrounding borders
				if (columnIndex == 5) {
					configLabels.addLabel(CustomLineBorderDecorator.TOP_LINE_BORDER_LABEL);
					configLabels.addLabel(CustomLineBorderDecorator.BOTTOM_LINE_BORDER_LABEL);
					
					if (columnIndex == 0) {
						configLabels.addLabel(CustomLineBorderDecorator.LEFT_LINE_BORDER_LABEL);
					}
					if (columnIndex == 2) {
						configLabels.addLabel(CustomLineBorderDecorator.RIGHT_LINE_BORDER_LABEL);
					}
				}*/
			}
		};
		
		bodyDataLayer.setConfigLabelAccumulator(cellLabelAccumulator);
		
		DefaultBodyLayerStack bodyLayer = new DefaultBodyLayerStack(bodyDataLayer);
		bodyLayer = new DefaultBodyLayerStack(bodyDataLayer);
		
		DefaultColumnHeaderDataProvider colHeaderDataProvider = new DefaultColumnHeaderDataProvider(propertyNames, propertyToLabels);
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

//		layout1.numColumns = 1;
		TableWrapData gd1 = new TableWrapData(TableWrapData.FILL);
		gd1.grabHorizontal = true;
		gd1.heightHint = 100;

		String modifiedColor = StudioTesterUIPlugin.getDefault().getPreferenceStore().getString(TesterPreferenceConstants.TEST_RESULT_CHANGED_VALUE_BACK_GROUND_COLOR);
		String[] colorArray=modifiedColor.split(",");
		final Color color=new Color(Display.getCurrent (),Integer.parseInt(colorArray[0]),Integer.parseInt(colorArray[1]),Integer.parseInt(colorArray[2]));
		
		NatTable table = new NatTable(parentSection,NatTable.DEFAULT_STYLE_OPTIONS | SWT.BORDER, gridLayer,false);
	//	table.addConfiguration(new ResultTableBodyStyleConfiguration());
		table.addConfiguration(new DefaultNatTableStyleConfiguration() {
			{
				cellPainter = new CustomLineBorderDecorator(new TextPainter());
				borderStyle = new BorderStyle(2, GUIHelper.COLOR_BLUE, LineStyleEnum.DASHDOT);
			}
		});
		table.addConfiguration(new AbstractRegistryConfiguration() {
			public void configureRegistry(IConfigRegistry configRegistry) {
				Style cellStyle = new Style();
				cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, color);
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL, "foo");
			}
		});
		if (editable) {
			IEditableRule editableRule = new WMResultsTableCellEditableRule(bodyLayer, editor);
			table.getConfigRegistry().registerConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE, editableRule, DisplayMode.EDIT);
		}
		table.configure();
		table.setLayout(gld);
		table.setLayoutData(gd1);
		table.layout();
		parentSection.layout();
		return table;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}