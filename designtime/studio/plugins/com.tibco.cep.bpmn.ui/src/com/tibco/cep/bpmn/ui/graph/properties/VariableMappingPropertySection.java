package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tibco.cep.bpmn.ui.editor.BpmnEditorInput;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.BpmnImages;
import com.tibco.cep.bpmn.ui.graph.model.SupportedProcessPropertiesType;
import com.tibco.cep.bpmn.ui.graph.model.command.EmfModelPropertiesUpdateCommand;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.bpmn.ui.refactoring.BpmnProcessRenameElementAction;
import com.tibco.cep.bpmn.ui.refactoring.BpmnProcessRenameProcessor;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.ui.editors.utils.ToolBarProvider;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;
import com.tomsawyer.graphicaldrawing.TSEGraph;

/**
 * 
 * @author sshekhar
 *
 */
public class VariableMappingPropertySection extends AbstractBpmnPropertySection {

	protected Composite composite;
	private TableViewer tableViewer;
	private static List<ProcessVariables> prVarList= new ArrayList<ProcessVariables>();
	public static final String NAME =BpmnMessages.getString("variable.property.columnName.name");

    public static final String TYPE =BpmnMessages.getString("variable.property.columnType.name");

    public static final String MULTIPLE =BpmnMessages.getString("variable.property.columnMultiple.name");


	public static final String[] PROPS = { NAME, TYPE, MULTIPLE};
	public static Image checked=null;
	
	public static Image unchecked=null;
	public ModelController controller;
	public ToolBarProvider toolBarProvider;
	public static String procName = null ;
	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		fEditor = (BpmnEditor) tabbedPropertySheetPage.getSite().getPage().getActiveEditor();
		GridData gd = new GridData(GridData.FILL_BOTH);
    	gd.widthHint = 500;
    	gd.heightHint = 200;
    	parent.setLayoutData(gd);
    	 gd = new GridData(GridData.FILL_BOTH);
     	gd.widthHint = 500;
     	gd.heightHint = 200;
     	tabbedPropertySheetPage.getControl().setLayoutData(gd);
		
		
		composite =getWidgetFactory().createComposite(parent, SWT.NONE);
//		composite.setLayout(new GridLayout(1,true));
		
		checked= BpmnImages.getInstance().getImage(BpmnImages.CHECKED);
		unchecked=BpmnImages.getInstance().getImage(BpmnImages.UNCHECKED);

		
		
		controller = fEditor.getBpmnGraphDiagramManager().getController();
		
		fProject = ((BpmnEditorInput) fEditor.getEditorInput()).getFile().getProject();
		EObjectWrapper<EClass, EObject> eobj = controller.getModelRoot();
		
		procName = ( String ) eobj.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		if (fProject == null || fEditor == null) {
			return;
		}
		toolBarProvider=new ToolBarProvider(composite,null);
		toolBarProvider.createPropertiesToolbar((Entity)null);
		toolBarProvider.getAddItem().addSelectionListener(new SelectionAdapter() {
			@Override
	    	public void widgetSelected(SelectionEvent event){
		    	ProcessVariables prVar= new ProcessVariables();
		    	String name = getPropertyName(fProject.getProject().getName(), tableViewer.getTable());
		    	
		    	prVar.setType("String");
		    	prVar.setPropTypeValue(prVar.getType());
		    	prVar.setName(name);
		    	prVar.setMultiple(false);
		    	
		    	prVarList.add(prVar);
		    	tableViewer.setInput(prVarList);
		    	tableViewer.refresh();
		    	if(!(toolBarProvider.getDelItem().isEnabled()))
		    			toolBarProvider.enableActionButton(toolBarProvider.getDelItem(),true);
		    	try{
		    		addRowToTable(prVar);
		    	}catch(Exception e){
		    		prVarList.remove(prVar);
		    		tableViewer.refresh();
		    	}
	    	//createModel(name);
	    	
	    	}
	    });
		toolBarProvider.getDelItem().addSelectionListener(new SelectionAdapter(){
			
			@Override
			public void widgetSelected(SelectionEvent event){
				StructuredSelection strSel=(StructuredSelection) tableViewer.getSelection();
				ProcessVariables propName=(ProcessVariables)strSel.getFirstElement();
				if(propName!=null){
					for(ProcessVariables prVar:prVarList){
						if(prVar.getName().equals(propName.getName())){
							prVarList.remove(prVar);
							break;
						}
					}
					removeRowfromTable(propName.getName());
					tableViewer.setInput(prVarList);
					tableViewer.refresh();
			    	if(tableViewer.getTable().getItemCount()==0)
						toolBarProvider.enableActionButton(toolBarProvider.getDelItem(),false);
			    	
			    	}
			     }
		} );
		// Create property table
		
		createPropertyTable(composite);
		prVarList=getModel();
		tableViewer.setInput(prVarList);
		tableViewer.refresh();

		tableViewer.getTable().addListener(SWT.PaintItem, new Listener() {
		@Override
		public void handleEvent(Event event) {
			
		if(event.index == 2) {
		try{
			TableItem item = (TableItem)event.item;
			ProcessVariables propName=(ProcessVariables)item.getData();
			Image tmpImage = null;
			if(propName!=null){
				if(propName.getMultiple())
					tmpImage=checked;
				else
					tmpImage=unchecked;
			}
			if(tmpImage!=null){
				int tmpWidth = 0;
				int tmpHeight = 0;
				int tmpX = 0;
				int tmpY = 0;
				tmpWidth = tableViewer.getTable().getColumn(event.index).getWidth();
				tmpHeight = ((TableItem)event.item).getBounds().height;
		
				tmpX = tmpImage.getBounds().width;
				tmpX = (tmpWidth / 2 - tmpX / 2);
				tmpY = tmpImage.getBounds().height;
				tmpY = (tmpHeight / 2 - tmpY / 2);
				if(tmpX <= 0) tmpX = event.x;
				else tmpX += event.x;
				if(tmpY <= 0) tmpY = event.y;
				else tmpY += event.y;
				event.gc.drawImage(tmpImage, tmpX, tmpY);
				}
			}catch(Exception e){
				System.out.println("Exception while handling event");
			}
		}
		 }});
		if(tableViewer.getTable().getItemCount()!=0)
			toolBarProvider.enableActionButton(toolBarProvider.getDelItem(),true);
		else
			toolBarProvider.enableActionButton(toolBarProvider.getDelItem(),false);
	}
	
	
	
	public static String getPropertyName(String entityName, Table model) {
		String name = entityName /*+ "_"+procName*/+"_property_";
		List<Integer> noList = new ArrayList<Integer>();
		for (int row = 0; row < model.getItemCount(); row++) {
			ProcessVariables pr =(ProcessVariables) model.getItem(row).getData();
			if (pr.getName().startsWith(name)) {
				String subname =pr.getName().substring(entityName.length() + 10);
				if (StudioUIUtils.isNumeric(subname)) {
					noList.add(Integer.parseInt(subname));
				}
			}
		}
		try {
			if (noList.size() > 0) {
				java.util.Arrays.sort(noList.toArray());
				int no = noList.get(noList.size() - 1).intValue();
				no++;
				return name + no;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return name + "0";

	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#refresh()
	 */
	@Override
	public void refresh() {
		EObjectWrapper<EClass, EObject> useInstance = getUserObject();
		if(useInstance != null){
//			id = useInstance.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			prVarList=getModel();
			tableViewer.setInput(prVarList);
			tableViewer.refresh();	
			if(tableViewer.getTable().getItemCount()!=0)
				toolBarProvider.enableActionButton(toolBarProvider.getDelItem(),true);
		}
	}
	protected EObjectWrapper<EClass, EObject> getUserObject(){
		EObject userObject = null;
		if (fTSENode != null) {
			userObject= (EObject) fTSENode.getUserObject();
		}
		
		if (fTSEGraph != null) {
			userObject= (EObject) fTSEGraph.getUserObject();
			EObjectWrapper<EClass, EObject> userObjectWrapper = EObjectWrapper.wrap(userObject);
			if(userObjectWrapper.isInstanceOf(BpmnModelClass.LANE)){
				TSEGraph rootGraph = controller.getRootGraph(fTSEGraph);
				if(rootGraph != null)
					userObject= (EObject) rootGraph.getUserObject();
				else
					userObject =null;
			}
		}
		if(userObject != null)
			return EObjectWrapper.wrap(userObject);
		else 
			return null;
	}
	
	
	public void addRowToTable(ProcessVariables prvar){
		EObjectWrapper<EClass, EObject> useInstance = getUserObject();
		String val;
		EObjectWrapper<EClass, EObject> propDef = controller.createPropertyDefinition(useInstance,prvar.getName(),
															SupportedProcessPropertiesType.getExpandedName(prvar.getType()),SupportedProcessPropertiesType.getEmfType(prvar.getType()));
		//EObjectWrapper<EClass, EObject> itemDef = EObjectWrapper.wrap((EObject)propDef.getAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF));
		EObjectWrapper<EClass, EObject> itemDefinition = null;
		if(prvar.getPropTypeValue().equalsIgnoreCase(prvar.getType())){
			if(prvar.getType().equalsIgnoreCase("int")){
				 val= SupportedProcessPropertiesType.Integer.getName();
				 itemDefinition = controller.getItemDefinition(fProject.getName(),SupportedProcessPropertiesType.getPropertyType(val).getType(), prvar.getMultiple());
			}
			else 
			itemDefinition = controller.getItemDefinition(fProject.getName(), SupportedProcessPropertiesType.getPropertyType(prvar.getType()).getType(), prvar.getMultiple());
		}else{
			itemDefinition = controller.getItemDefinitionUsingEntity( prvar.getPropTypeValue(),  prvar.getMultiple());
		}
		propDef.setAttribute(
				BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF,
				itemDefinition.getEInstance());
		if(itemDefinition.getAttribute(BpmnMetaModelConstants.E_ATTR_ID)!= null){
			itemDefinition.setAttribute(BpmnMetaModelConstants.E_ATTR_IS_COLLECTION,prvar.getMultiple());
			
		}
		prvar.setPropDef(propDef);
		//useInstance.addToListAttribute(BpmnMetaModelConstants.E_ATTR_PROPERTIES, propDef.getEInstance());
	}

	public void updateRowTable(String name,ProcessVariables prvar,String propChanged){
		EObjectWrapper<EClass, EObject> useInstance = getUserObject();
		Boolean flag=false;
		List<EObjectWrapper<EClass, EObject>> propDefs = (List<EObjectWrapper<EClass, EObject>>)controller.getPropertyDefinitions(useInstance);
		//EObjectWrapper<EClass, EObject> property = EObjectWrapper.createInstance(BpmnModelClass.PROPERTY);
		for(EObjectWrapper<EClass, EObject> propDef:propDefs){
			if(name.equals(controller.getPropertyName(propDef))){
				
				if(VariableMappingPropertySection.NAME.equals(propChanged)){
					flag=true;
					propDef.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, prvar.getName());
				}
				else if(VariableMappingPropertySection.TYPE.equals(propChanged) || VariableMappingPropertySection.MULTIPLE.equals(propChanged)){
					flag=true;
					 EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(propDef);
						EObjectWrapper<EClass, EObject> itemDefinition = null;
						String val;
						if(prvar.getPropTypeValue().equalsIgnoreCase(prvar.getType())){
							
							if(prvar.getType().equalsIgnoreCase("int")){
								 val= SupportedProcessPropertiesType.Integer.getName();
								 itemDefinition = controller.getItemDefinition(fProject.getName(),SupportedProcessPropertiesType.getPropertyType(val).getType(), prvar.getMultiple());
								 if(addDataExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROP_TYPE)){
										addDataExtensionValueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROP_TYPE, SupportedProcessPropertiesType.getPropertyType(val).getEmfType());
									} 
							}
							else 
								itemDefinition = controller.getItemDefinition(fProject.getName(), SupportedProcessPropertiesType.getPropertyType(prvar.getType()).getType(), prvar.getMultiple());
							
							if(addDataExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROP_TYPE)){
								addDataExtensionValueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROP_TYPE, SupportedProcessPropertiesType.getPropertyType(prvar.getType()).getEmfType());
							} 
							
						}else{
							itemDefinition = controller.getItemDefinitionUsingEntity( prvar.getPropTypeValue(),  prvar.getMultiple());
							if(addDataExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROP_TYPE))
								addDataExtensionValueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROP_TYPE, SupportedProcessPropertiesType.getPropertyType(prvar.getType()).getEmfType());
						}
						itemDefinition.setAttribute(BpmnMetaModelConstants.E_ATTR_IS_COLLECTION,prvar.getMultiple());
					if(itemDefinition!=null)
						propDef.setAttribute(
								BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF,
								itemDefinition.getEInstance());
					
					 
				}

			}
			
			if(flag){
				prvar.setPropDef(propDef);
				break;
			}	
		}
	
		updatePropertyList();
	}
	

	public void removeRowfromTable(String name){
		int l = -1;
		EObjectWrapper<EClass, EObject> useInstance = getUserObject();
		List<EObjectWrapper<EClass, EObject>> propDefs = (ArrayList<EObjectWrapper<EClass, EObject>>)controller.getPropertyDefinitions(useInstance);
		List<EObjectWrapper<EClass, EObject>> toBeRemovedProps = new ArrayList<EObjectWrapper<EClass,EObject>>();
		for(int index = 0; index < propDefs.size(); index++){
			if(controller.getPropertyName(propDefs.get(index)).equalsIgnoreCase(name)){
				l = index;
				toBeRemovedProps.add(propDefs.get(index));
				break;	
			}
		}
		
		if (l >-1) {
			for (EObjectWrapper<EClass, EObject> i : toBeRemovedProps) {
				useInstance.removeFromListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROPERTIES, i);
				updateXslt(name , useInstance);
			}
		}
	}
	
	public void updateXslt(String name ,EObjectWrapper<EClass, EObject>  process){
		String path = "$" + "job/" + name ;
		List<EObject> allFlowNodes = BpmnModelUtils.getAllFlowNodes(getUserObject().getEInstance());
		allFlowNodes.add(0, getUserObject().getEInstance());
		boolean changeBool = false;
		for (EObject eObject : allFlowNodes) {
		
			String inputMapperXslt = BpmnModelUtils.getInputMapperXslt(eObject);
			if(inputMapperXslt != null && !inputMapperXslt.isEmpty()){
				if ( inputMapperXslt.contains(path)) {
					
					EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
							.wrap(eObject);
					if (BpmnModelClass.ACTIVITY.isSuperTypeOf(eObject.eClass())) {
						List<EObject> dataInAssocs = userObjWrapper
								.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS);
						if (!dataInAssocs.isEmpty()) {
							ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper
									.wrap((EObject) dataInAssocs.get(0));

							EObject transform = (EObject) doAssocWrap
									.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
							EObjectWrapper<EClass, EObject> transformWrap = EObjectWrapper
									.wrap(transform);
							transformWrap
									.setAttribute(BpmnMetaModelConstants.E_ATTR_BODY, "");
						}
					}
				}
			}
			
			String outputMapperXslt =  BpmnModelUtils.getOutputMapperXslt(eObject);
			IFile file =  BpmnIndexUtils.getFile(fProject.getName() , process.getEInstance() );
			if(outputMapperXslt != null && !outputMapperXslt.isEmpty()){
				String namespace = RDFTnsFlavor.BE_NAMESPACE + "/" +file.getProjectRelativePath().removeFileExtension().toString();
				String ns =BpmnProcessRenameProcessor.getNamespace( outputMapperXslt , namespace);
				String oldopPath = ns + ":" + name ;
				if ( outputMapperXslt.contains(oldopPath)) {
					BpmnModelUtils.setOutputMapperXslt(eObject, "");
				}
			}
		}
	}

	protected void autoTableLayout(Table table) {
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(
		table,false);
		for (int loop = 0; loop < table.getColumns().length; loop++) {
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		}
		table.setLayout(autoTableLayout);
		}
	
	protected void createPropertyTable(Composite parent){
		
		 GridData gdtableComp=new GridData(GridData.BEGINNING);
			gdtableComp.horizontalSpan=SWT.FILL;
			gdtableComp.verticalSpan=SWT.FILL;
			gdtableComp.grabExcessHorizontalSpace=false;
			gdtableComp.grabExcessVerticalSpace=true;
			gdtableComp.minimumHeight=140;
			parent.setLayoutData(gdtableComp);
	     GridLayout tablelayout = new GridLayout(1,false);
	     
			tablelayout.numColumns=1;
			parent.setLayout(tablelayout);
			
		ArrayList<String> editorList=new ArrayList<String>();
		CellEditor[] editors = new CellEditor[3];
		
		tableViewer = new TableViewer(parent,SWT.BORDER |SWT.FULL_SELECTION);
		tableViewer.setContentProvider(new ArrayContentProvider()/*VariableContentProvider()*/);
		tableViewer.setLabelProvider(new VariableLabelProvider());
		 Table table = tableViewer.getTable();
	    table.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL| GridData.GRAB_VERTICAL| GridData.FILL_BOTH));
	    GridData gd = new GridData(GridData.FILL_BOTH);
	    gd.minimumHeight=140;
  		gd.grabExcessHorizontalSpace=false;
  		gd.grabExcessVerticalSpace=true;
  		table.setLayoutData(gd);
		TableColumn column1= new TableColumn(table, SWT.CENTER);
		column1.setText(NAME);
		column1.setAlignment(SWT.CENTER);
	    editorList.add(NAME);
	    TableColumn column2= new TableColumn(table, SWT.LEFT);
	    column2.setText(TYPE);
	    editorList.add(TYPE);
	    column1.setAlignment(SWT.LEFT);
	    TableColumn column3= new TableColumn(table, SWT.CENTER);
	    column3.setText(MULTIPLE);
	    editorList.add(MULTIPLE);
	    column3.setAlignment(SWT.CENTER);
	    for (int i = 0, n = table.getColumnCount(); i < n; i++) {
	       table.getColumn(i).pack();
	        
	      }
	    
	    editors[0]=new TextCellEditor(table);
	    editors[1]=new TextCellEditorDialog(fProject.getName(),table, this);
	    editors[2]=new CheckboxCellEditor(table,SWT.NONE);
	    MenuManager popupMenu = new MenuManager();
		popupMenu.add(new RenamePropertyAction());
		Menu menu = popupMenu.createContextMenu(table);
		table.setMenu(menu);
	    table.setHeaderVisible(true);
	    table.setLinesVisible(true);
	    tableViewer.setCellModifier(new VariableTableCellModifier(tableViewer,this));
	    autoTableLayout(table);
	    tableViewer.setColumnProperties(PROPS);
	    tableViewer.setCellEditors(editors);
		
	}
	
	
	protected void updatePropertyList(){
		EObjectWrapper<EClass, EObject> useInstance = getUserObject();
		List<EObject> propDefs = new ArrayList<EObject>(useInstance.getListAttribute(BpmnMetaModelConstants.E_ATTR_PROPERTIES));
		Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> updateMap = new HashMap<EObjectWrapper<EClass,EObject>, Map<String,Object>>();
		Map<String, Object> updateList = new HashMap<String, Object>();
		updateList.put(BpmnMetaModelConstants.E_ATTR_PROPERTIES, propDefs);
		
		updateMap.put(useInstance, updateList);
		EmfModelPropertiesUpdateCommand emfModelPropertiesUpdateCommand = new EmfModelPropertiesUpdateCommand(controller, updateMap);
		getDiagramManager().executeCommand(emfModelPropertiesUpdateCommand);
	}
	
	protected List<ProcessVariables> getModel(){
		EObjectWrapper<EClass, EObject> useInstance = getUserObject();
		List<ProcessVariables> tempPrVarList = new ArrayList<ProcessVariables>();
		
		if(useInstance != null){
//			id = useInstance.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			List<EObjectWrapper<EClass, EObject>> propDefs = (List<EObjectWrapper<EClass, EObject>>)controller.getPropertyDefinitions(useInstance);
			if(propDefs!=null)
			for(EObjectWrapper<EClass, EObject> propDef:propDefs){
				ProcessVariables prvar= new ProcessVariables();
				prvar.setName(controller.getPropertyName(propDef));
				//prvar.setPropDef(propDef);
				EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(propDef);
				EEnumLiteral propType= addDataExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROP_TYPE);
				prvar.setType(propType.getName());
				EObjectWrapper<EClass, EObject> itemDef = EObjectWrapper.wrap((EObject)propDef.getAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF));
				final String value = SupportedProcessPropertiesType.getResourceType(propDef);
				
				prvar.setPropTypeValue(/*itemDefinitionType.localName*/value);
				if(itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_ID)!= null){
					//ExpandedName itemDefinitionType = SupportedProcessPropertiesType.getItemDefinitionType(itemDef);
					//prvar.setPropTypeValue(itemDefinitionType.toString());
					//prvar.setPropTypeValue(itemDefinitionType.localName);
					prvar.setMultiple((Boolean)itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_COLLECTION));
					prvar.setPropDef(propDef);
				}
				tempPrVarList.add(prvar);
		}
			
	}
		
		return tempPrVarList;
		
	}
	
	@Override
	protected void updatePropertySection(Map<String, Object> updateList) {
		// TODO Auto-generated method stub
		
	}
	

	class VariableLabelProvider implements ITableLabelProvider{

		@Override
		public void addListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}

		public Image getColumnImage(Object element, int columnIndex) {
			
			ProcessVariables prVar= (ProcessVariables)element;
			//String imagePropType=SupportedProcessPropertiesType.getImageIcon((prVar.getPropDef()));
			
			switch (columnIndex){
			case 1:
				if(prVar.getImageIcon()!=null)
				 return BpmnImages.getInstance().getImage(prVar.getImageIcon());
				//return new Image(tableViewer.getTable().getDisplay(),TOOLBAR_ICON_PATH+imagePropType);
			}
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			ProcessVariables prVar= (ProcessVariables)element;
			switch (columnIndex){
			case 0:
				return prVar.getName();
			case 1:
				return prVar.getPropTypeValue();
			case 2:
				return null;
			}
			return null;
		}
		
	}
	
	
	protected void renameElement(ProcessVariables propertyDefinition) {
//		updateRowTable("name",propertyDefinition,NAME) ;
		
		BpmnProcessRenameElementAction act = new BpmnProcessRenameElementAction();
		act.selectionChanged(null, new StructuredSelection(propertyDefinition));
		act.run(null);
	}
	
	protected class RenamePropertyAction extends
	org.eclipse.jface.action.Action {

	public RenamePropertyAction() {
		super("Rename");
	}
	
	
/*
 * (non-Javadoc)
 * 
 * @see org.eclipse.jface.action.Action#run()
 */
	public void run() {
		if (!tableViewer.getSelection().isEmpty()) {
			ProcessVariables propertyDefinition = (ProcessVariables) ((IStructuredSelection) tableViewer.getSelection()).getFirstElement();
			propertyDefinition.setProcess( getUserObject() );
			propertyDefinition.setProjectName(fProject.getProject().getName());
			renameElement(propertyDefinition);
			prVarList.clear();
			prVarList = getModel();
			tableViewer.setInput(prVarList);
			tableViewer.refresh();
		}
	}
}


	public TableViewer getTableViewer() {
		return tableViewer;
	}



	public void setTableViewer(TableViewer tableViewer) {
		this.tableViewer = tableViewer;
	}
	
}
