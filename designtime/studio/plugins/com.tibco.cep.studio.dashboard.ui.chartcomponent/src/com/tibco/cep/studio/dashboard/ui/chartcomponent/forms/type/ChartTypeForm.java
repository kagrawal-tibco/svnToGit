package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.DashboardChartPlugin;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartSubType;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartSubTypeGroup;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartType;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartTypeRegistry;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

public class ChartTypeForm extends BaseForm {
	
	private Table tbl_Types;
	private TableViewer tblViewer_Types;
	private AbstractSelectionListener tbl_TypesSelectionListener;
	
	private Composite subTypeUIComposite;
	private StackLayout subTypeUILayout;
	private Map<String,Composite> subTypeUIMap;
	private Map<String,List<SubTypeViewer>> subTypeViewerMap;
	private SubTypeViewerSelectionChangeListener subTypeViewerSelectionChangeListener;
	
	public ChartTypeForm(FormToolkit formToolKit, Composite parent, boolean showGroup) {
		super("Type", formToolKit, parent, showGroup);
		subTypeUIMap = new HashMap<String, Composite>();
		subTypeViewerMap = new HashMap<String, List<SubTypeViewer>>();
	}

	@Override
	public void init() {
		formComposite.setLayout(new GridLayout(2,false));
		// type table
		tbl_Types = createTable(formComposite, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
		tbl_Types.setHeaderVisible(false);
		tbl_Types.setLinesVisible(false);
		TableAutoResizeLayout tableLayout = new TableAutoResizeLayout(tbl_Types);
		TableColumn column = new TableColumn(tbl_Types, SWT.LEFT, 0);
		column.setText("");
		tableLayout.addColumnData(new ColumnWeightData(100,false));
		
		GridData tbl_TypesLayoutData = new GridData(SWT.BEGINNING, SWT.TOP, false, false);
		tbl_TypesLayoutData.widthHint = 150;
		tbl_TypesLayoutData.heightHint = 250;
		tbl_Types.setLayoutData(tbl_TypesLayoutData);
		
		tblViewer_Types = new TableViewer(tbl_Types);
		tblViewer_Types.setContentProvider(new ArrayContentProvider());
		tblViewer_Types.setLabelProvider(new LabelProvider(){
			
			@Override
			public Image getImage(Object element) {
				ChartType chartType = (ChartType) element;
				ImageRegistry imageRegistry = DashboardChartPlugin.getDefault().getImageRegistry();
				return imageRegistry.get("types/"+chartType.getIconName());
			}
			
			@Override
			public String getText(Object element) {
				ChartType chartType = (ChartType) element;
				return chartType.getName();
			}
			
		});
		
		//sub type composite 
		subTypeUIComposite = createComposite(formComposite, SWT.NONE);
		subTypeUILayout = new StackLayout();
		subTypeUIComposite.setLayout(subTypeUILayout);
		subTypeUIComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		//we are initializing the subTypeViewerSelectionChangeListener since refresh selection could trigger creation of the sub type UI 
		//the refresh selection is called before enableListeners
		subTypeViewerSelectionChangeListener = new SubTypeViewerSelectionChangeListener();
		addControl(subTypeUIComposite);
	}

	@Override
	protected void doEnableListeners() {
		if (tbl_TypesSelectionListener == null){
			tbl_TypesSelectionListener = new AbstractSelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) {
					typeSelectionChanged();
				}
				
			};
		}
		tbl_Types.addSelectionListener(tbl_TypesSelectionListener);		

		subTypeViewerSelectionChangeListener.enable = true;
	}
	
	@Override
	protected void doDisableListeners() {
		if (tbl_TypesSelectionListener != null){
			tbl_Types.removeSelectionListener(tbl_TypesSelectionListener);	
		}
		subTypeViewerSelectionChangeListener.enable = false;
	}
	
	@Override
	public void enableAll() {
		super.enableAll();
		List<SubTypeViewer> subTypeViewers = subTypeViewerMap.get(((ChartType) ((StructuredSelection)tblViewer_Types.getSelection()).getFirstElement()).getId());
		for (SubTypeViewer subTypeViewer : subTypeViewers) {
			subTypeViewer.setEnabled(true);
		}
	}

	@Override
	public void disableAll() {
		StructuredSelection selection = (StructuredSelection)tblViewer_Types.getSelection();
		if (selection.isEmpty() == false) {
			List<SubTypeViewer> subTypeViewers = subTypeViewerMap.get(((ChartType) selection.getFirstElement()).getId());
			if (subTypeViewers != null) {
				for (SubTypeViewer subTypeViewer : subTypeViewers) {
					subTypeViewer.setEnabled(false);
				}
			}
		}
		super.disableAll();
	}

	@Override
	public void refreshEnumerations() {
		tblViewer_Types.setInput(ChartTypeRegistry.getInstance().getTypes());
	}

	@Override
	public void refreshSelections() {
		String propertyName = LocalUnifiedComponent.PROP_KEY_TYPE;
		try {
			//read chart type 
			String value = localElement.getPropertyValue(propertyName);
			ChartType chartType = ChartTypeRegistry.getInstance().get(value);
			//set chart type selection
			tblViewer_Types.setSelection(new StructuredSelection(chartType));
			//show the new sub type UI 
			showSubTypeUI(value);
			//read chart sub types
			propertyName = LocalUnifiedComponent.PROP_KEY_SUBTYPES;
			SynProperty property = (SynProperty) localElement.getProperty(propertyName);
			List<String> values = property.getValues();
			//set chart sub type selections
			List<SubTypeViewer> subTypeViewers = subTypeViewerMap.get(value);
			for (int i = 0; i < subTypeViewers.size(); i++) {
				subTypeViewers.get(i).setSelection(new StructuredSelection(chartType.getSubType(values.get(i))));
			}
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not read "+propertyName,e));
		}
	}
	
	protected void showSubTypeUI(String typeId){
		//get the sub type composite 
		Composite subTypeUI = getSubTypeUI(typeId,subTypeUIComposite);
		//make it the top level control 
		subTypeUILayout.topControl = subTypeUI;
		//force the re-layout
		subTypeUIComposite.layout();
	}
	
	protected void typeSelectionChanged() {
		try {
			StructuredSelection selection = (StructuredSelection)tblViewer_Types.getSelection();
			if (selection != null){
				//get the new chart type selection
				ChartType type = (ChartType) selection.getFirstElement();
				//update the local element , this will trigger udpating the sub types also in controller
				localElement.setPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE, type.getId());
				//show the new sub type UI 
				showSubTypeUI(type.getId());
				//refresh UI based on sub types in local element
				//read chart sub types
				SynProperty property = (SynProperty) localElement.getProperty(LocalUnifiedComponent.PROP_KEY_SUBTYPES);
				List<String> values = property.getValues();
				//set chart sub type selections
				List<SubTypeViewer> subTypeViewers = subTypeViewerMap.get(type.getId());
				for (int i = 0; i < subTypeViewers.size(); i++) {
					subTypeViewers.get(i).setSelection(new StructuredSelection(type.getSubType(values.get(i))));
				}
			}
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not update "+LocalUnifiedComponent.PROP_KEY_TYPE,e));
		}
	}
	
	protected void subTypeSelectionChanged() {
		try {
			List<SubTypeViewer> subTypeViewers = subTypeViewerMap.get(((ChartType) ((StructuredSelection)tblViewer_Types.getSelection()).getFirstElement()).getId());
			List<String> subTypes = new ArrayList<String>(subTypeViewers.size());
			for (int i = 0; i < subTypeViewers.size(); i++) {
				StructuredSelection selection = (StructuredSelection) subTypeViewers.get(i).getSelection();
				ChartSubType chartSubType = (ChartSubType) selection.getFirstElement();
				subTypes.add(chartSubType.getId());
			}
			localElement.setPropertyValues(LocalUnifiedComponent.PROP_KEY_SUBTYPES, subTypes);
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not update "+LocalUnifiedComponent.PROP_KEY_SUBTYPES,e));
		}
	}
	
	private Composite getSubTypeUI(String id, Composite parent){
		Composite composite = subTypeUIMap.get(id);
		if (composite == null){
			ChartType chartType = ChartTypeRegistry.getInstance().get(id);
			composite = createComposite(parent, SWT.NONE);
			//INFO we are keeping the column count as 3 in sub type UI
			composite.setLayout(new GridLayout());
			List<SubTypeViewer> viewers = new ArrayList<SubTypeViewer>(chartType.getSubTypeGroups().length);
			for (ChartSubTypeGroup subTypeGroup : chartType.getSubTypeGroups()) {
				//sub type group heading 
				Label lbl_subTypeGroupHeading = createLabel(composite, subTypeGroup.getName()+" :", SWT.NONE);
				lbl_subTypeGroupHeading.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,false));
				//sub type viewer 
				SubTypeViewer subTypeViewer = new SubTypeViewer(formToolKit,composite);
				subTypeViewer.setInput(subTypeGroup);
				//set the selections in the viewer
				//subTypeViewer.setSelection(new StructuredSelection(subTypeGroup.getSubTypes()[0]));
				//subTypeViewer.getControl().setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false));
				subTypeViewer.addSelectionChangedListener(subTypeViewerSelectionChangeListener);
				viewers.add(subTypeViewer);
			}
			subTypeUIMap.put(id, composite);
			subTypeViewerMap.put(id, viewers);
		}
		return composite;
	}
	
	class SubTypeViewerSelectionChangeListener implements ISelectionChangedListener {
		
		boolean enable;
		
		SubTypeViewerSelectionChangeListener() {
			enable = true;
		}

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			if (enable == true) {
				ChartTypeForm.this.subTypeSelectionChanged();
			}
		}
		
	}
}
