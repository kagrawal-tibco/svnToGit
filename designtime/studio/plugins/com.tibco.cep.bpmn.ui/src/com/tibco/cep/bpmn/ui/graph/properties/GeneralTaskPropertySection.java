package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.BpmnImages;
import com.tibco.cep.bpmn.ui.graph.palette.PaletteLoader;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tomsawyer.graph.TSGraph;

/**
 * 
 * @author majha
 * 
 */
public class GeneralTaskPropertySection extends GeneralNodePropertySection {

	private static final String NONE_BOUNDARY_EVENT = "None";

	private boolean refresh;
	private WidgetListener widgetListener;
//	private Group boundaryEventDefinitionGroup;
//	// private Button cancelActivityCheck;
//	protected Label boundaryNodeTypeLabel;
//	protected Text boundaryNodeTypeText;
//	protected Button boundaryNodeTypeBrowseButton;


	private Button scriptTaskButton;

private Composite childContainer;

private Label checkPointLabel;

	public GeneralTaskPropertySection() {
		super();
		this.widgetListener = new WidgetListener();
	}

	@Override
	public void aboutToBeHidden() {
		if (!composite.isDisposed()) {
			super.aboutToBeHidden();

//			boundaryNodeTypeBrowseButton.removeSelectionListener(this.widgetListener);
			// cancelActivityCheck.removeSelectionListener(this.widgetListener);
			if (resourceText != null) {
				resourceText.removeModifyListener(this.widgetListener);
			}

			if (scriptTaskButton != null) {
				scriptTaskButton.removeSelectionListener(this.widgetListener);
			}
		}
	}

	@Override
	public void aboutToBeShown() {
		if (!composite.isDisposed() && !isListenerAttached) {
			super.aboutToBeShown();

//			boundaryNodeTypeBrowseButton.addSelectionListener(this.widgetListener);
			// cancelActivityCheck.addSelectionListener(this.widgetListener);
			if (resourceText != null) {
				resourceText.addModifyListener(this.widgetListener);
			}

			if (scriptTaskButton != null) {
				scriptTaskButton.addSelectionListener(this.widgetListener);
			}
		}
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		insertChildSpecificComponents();

		createBoundaryEventDefinitionWidget();
	}

	@Override
	protected void createProperties() {		
		checkPointLabel = getWidgetFactory().createLabel(composite, BpmnMessages.getString("taskProp_checkPointLabel_label"), SWT.NONE);
		childContainer = getWidgetFactory().createComposite(composite, SWT.NONE);
		GridLayout layout = new GridLayout(7, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;

		childContainer.setLayout(layout);
		childContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		timeoutEnable = new Button(childContainer, SWT.CHECK);
		
		getWidgetFactory().createLabel(childContainer, "     ", SWT.NONE);

//		getWidgetFactory().createLabel(childContainer, "Asynchronous", SWT.NONE);
//		asyncCheck = new Button(childContainer, SWT.CHECK);
		
		checkPointLabel.setVisible(false);
		childContainer.setVisible(false);

		

//		this.createCompensationProperty(childContainer);
	}
	

	protected void createCompensationProperty(Composite childContainer) {
		getWidgetFactory().createLabel(childContainer, "     ", SWT.NONE);
		getWidgetFactory().createLabel(childContainer, BpmnMessages.getString("taskProp_compensationCheck_label"), SWT.NONE);
		childContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		compensationCheck = new Button(childContainer, SWT.CHECK);
	}

	// to be used by sub class if they want to insert specific components below
	// description
	protected void insertChildSpecificComponents() {

		if (isScriptTask()) {
			scriptTaskButton = getWidgetFactory().createButton(composite, BpmnMessages.getString("taskProp_scriptTaskButton_label"), SWT.CHECK);
		}
	}

	protected boolean isServiceTask() {
		return false;
	}

	protected boolean isScriptTask() {
		return false;
	}

	private void createBoundaryEventDefinitionWidget() {
//		boundaryEventDefinitionGroup = getWidgetFactory().createGroup(composite, "Boundary Event");
//		GridData gd = new GridData();
//		gd.horizontalSpan = 2;
//		gd.heightHint = 50;
//		boundaryEventDefinitionGroup.setLayoutData(gd);
//
//		GridLayout gridLayout = new GridLayout();
//		gridLayout.numColumns = 2;
//		gridLayout.marginTop = 7;
//		boundaryEventDefinitionGroup.setLayout(gridLayout);
//
//		// / Add boundary event type tree
//		boundaryNodeTypeLabel = getWidgetFactory().createLabel(boundaryEventDefinitionGroup, "Node Type", SWT.NONE);
//		Composite nodeTypeTreeComposite = getWidgetFactory().createComposite(boundaryEventDefinitionGroup);
//		GridLayout nodeTypelayout = new GridLayout(2, false);
//		nodeTypelayout.marginWidth = 0;
//		nodeTypelayout.marginHeight = 0;
//		nodeTypeTreeComposite.setLayout(nodeTypelayout);
//		gd = new GridData(GridData.FILL_HORIZONTAL);
//		nodeTypeTreeComposite.setLayoutData(gd);
//
//		boundaryNodeTypeText = getWidgetFactory().createText(nodeTypeTreeComposite, "", SWT.BORDER | SWT.READ_ONLY);
//		gd = new GridData(/* GridData.FILL_HORIZONTAL */);
//		gd.widthHint = 562;
//		boundaryNodeTypeText.setLayoutData(gd);
//		boundaryNodeTypeBrowseButton = new Button(nodeTypeTreeComposite, SWT.NONE);
//		boundaryNodeTypeBrowseButton.setText("Browse");

		// Add cancelActivity check box
		// Label cancelActivityLabel =
		// getWidgetFactory().createLabel(boundaryEventDefinitionGroup,"Cancel Activity",
		// SWT.NONE);
		// gd = new GridData();
		// gd.widthHint = 150;
		// cancelActivityLabel.setLayoutData(gd);
		// cancelActivityCheck = new
		// Button(boundaryEventDefinitionGroup,SWT.CHECK);
		// gd = new GridData();
		// cancelActivityCheck.setLayoutData(gd);
		// handleForBoundaryEventChange(null);
	}

	/*
	 * @see
	 * org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh
	 * ()
	 */
	public void refresh() {
		this.refresh = true;
		if (fTSENode != null) {
			TSGraph ownerGraph = fTSENode.getOwnerGraph();
			EObject userObject = (EObject) ownerGraph.getUserObject();
			if (userObject != null
					&& !userObject.eClass().equals(BpmnModelClass.SUB_PROCESS)) {
				checkPointLabel.setVisible(true);
				childContainer.setVisible(true);
			}else{
				checkPointLabel.setVisible(false);
				childContainer.setVisible(false);
			}
			super.refresh();
			@SuppressWarnings("unused")
			EClass nodeType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			@SuppressWarnings("unused")
			EClass nodeExtType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
			@SuppressWarnings("unused")
			String nodeName = (String) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);

//			EObject userObject = (EObject) fTSENode.getUserObject();
//			EObjectWrapper<EClass, EObject> taskWrapper = EObjectWrapper.wrap(userObject);
//			EList<EObject> boundaryEvents = taskWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_BOUNDARY_EVENT_REFS);
//			if (boundaryEvents != null && boundaryEvents.size() > 0) {
//				EObject object = boundaryEvents.get(0);
//				EObjectWrapper<EClass, EObject> boundaryEvent = EObjectWrapper.wrap(object);
//				BpmnPaletteModel toolDefinition = PaletteLoader.getBpmnPaletteModel(fProject);
//				if (toolDefinition != null) {
//					String id = (String) ExtensionHelper.getExtensionAttributeValue(boundaryEvent, BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID);
//					BpmnPaletteGroupItem item = null;
//					if (id != null && !id.isEmpty()) {
//						item = toolDefinition.getPaletteItemById(id);
//					} else {
//						EList<EObject> listAttribute = boundaryEvent.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
//						if (listAttribute.size() > 0) {
//							EClass extType = listAttribute.get(0).eClass();
//							List<BpmnPaletteGroupItem> paletteToolByType = toolDefinition.getPaletteToolItemByType(BpmnModelClass.INTERMEDIATE_CATCH_EVENT,
//									extType);
//							if (paletteToolByType.size() > 0)
//								item = paletteToolByType.get(0);
//						}
//
//					}
//					if (item != null) {
//						boundaryNodeTypeText.setText(item.getTitle());
//						// handleForBoundaryEventChange(item);
//					} else {
//						boundaryNodeTypeText.setText(NONE_BOUNDARY_EVENT);
//					}
//
//				}
//				// boolean cancelActivity =
//				// (Boolean)boundaryEvent.getAttribute(BpmnMetaModelConstants.E_ATTR_CANCEL_ACTIVITY);
//				// cancelActivityCheck.setSelection(cancelActivity);
//			} else {
//				boundaryNodeTypeText.setText(NONE_BOUNDARY_EVENT);
//				// handleForBoundaryEventChange(null);
//			}

			refreshScriptTaskButton();

		}
		if (fTSEEdge != null) {
		}
		if (fTSEGraph != null) {
		}

		// System.out.println("Done with refresh.");
		this.refresh = false;
	}

	private void refreshScriptTaskButton() {
		if (isScriptTask() && scriptTaskButton != null) {
			EClass nodeType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			scriptTaskButton.setVisible(true);
			scriptTaskButton.setSelection(false);
			if (nodeType != null && nodeType.equals(BpmnModelClass.SCRIPT_TASK)) {
				scriptTaskButton.setSelection(true);
			}
		} else {
			if (scriptTaskButton != null)
				scriptTaskButton.setVisible(false);
		}
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.bpmn.ui.graph.properties.GeneralNodePropertySection#isRefresh
	 * ()
	 */
	public boolean isRefresh() {
		return refresh;
	}

	protected List<BpmnPaletteGroupItem> getIntermediateCatchEventPaletteItems() {
		List<BpmnPaletteGroupItem> items = new ArrayList<BpmnPaletteGroupItem>();
		BpmnPaletteModel toolDefs = PaletteLoader.getBpmnPaletteModel(fProject);
		items.addAll(toolDefs.getPaletteToolItemByType(BpmnModelClass.INTERMEDIATE_CATCH_EVENT, null, true));

		return items;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected PropertyNodeTypeGroup getBoundaryEventType() {
		List intermediateCatchEventPaletteItems = getIntermediateCatchEventPaletteItems();
		intermediateCatchEventPaletteItems.add(NONE_BOUNDARY_EVENT);
		PropertyNodeTypeGroup ievents = new PropertyNodeTypeGroup("roots", intermediateCatchEventPaletteItems, BpmnImages.EVENTS_PALETTE_GRAPH);

		return ievents;
	}

	// void handleForBoundaryEventChange(BpmnPaletteGroupItem item){
	// EClass boundaryEventDefinitionType = null;
	// if (item != null) {
	// boundaryEventDefinitionType = getBoundaryEventDefinitionType(item);
	// if (boundaryEventDefinitionType != null) {
	// if (MESSAGE_EVENT_DEFINITION
	// .isSuperTypeOf(boundaryEventDefinitionType)
	// || SIGNAL_EVENT_DEFINITION
	// .isSuperTypeOf(boundaryEventDefinitionType)
	// || TIMER_EVENT_DEFINITION
	// .isSuperTypeOf(boundaryEventDefinitionType)) {
	// cancelActivityCheck.setEnabled(true);
	// cancelActivityCheck.setSelection(true);
	// } else if (ERROR_EVENT_DEFINITION
	// .isSuperTypeOf(boundaryEventDefinitionType)) {
	// cancelActivityCheck.setEnabled(false);
	// cancelActivityCheck.setSelection(true);
	// }
	// } else {
	// cancelActivityCheck.setEnabled(false);
	// cancelActivityCheck.setSelection(false);
	// }
	// }else{
	// cancelActivityCheck.setEnabled(false);
	// cancelActivityCheck.setSelection(false);
	// }
	// boundaryEventDefinitionGroup.redraw();
	// }

//	private EClass getBoundaryEventDefinitionType(BpmnPaletteGroupItem item) {
//		BpmnPaletteGroupItemType itemType = item.getItemType();
//		EClass modelType = null;
//
//		if (itemType.getType() == BpmnPaletteGroupItemType.EMF_TYPE) {
//			ExpandedName extClassSpec = ((BpmnPaletteGroupEmfItemType) itemType).getExtendedType();
//			modelType = BpmnMetaModel.getInstance().getEClass(extClassSpec);
//		}
//		return modelType;
//	}

	private class WidgetListener implements SelectionListener, ModifyListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			Object object = e.getSource();

			Map<String, Object> updateList = new HashMap<String, Object>();
//			if (object == boundaryNodeTypeBrowseButton) {
//				PropertyNodeTypeGroup input = getBoundaryEventType();
//				ITreeContentProvider contentProvider = new PropertyNodeTypeTreeContentProvider(input);
//				IBaseLabelProvider labelProvider = new PropertyNodeTypeLabelProvider();
//				ISelection selected = getPopupTreeSelection(boundaryNodeTypeText, input, labelProvider, contentProvider, null);
//				if (selected instanceof IStructuredSelection) {
//					Object element = ((IStructuredSelection) selected).getFirstElement();
//					if (element instanceof BpmnPaletteGroupItem) {
//						BpmnPaletteGroupItem pn = (BpmnPaletteGroupItem) element;
//						boundaryNodeTypeText.setData(pn);
//						boundaryNodeTypeText.setText(pn.getTitle());
//						// handleForBoundaryEventChange(pn);
//						EClass boundaryEventDefinitionType = getBoundaryEventDefinitionType(pn);
//						if (boundaryEventDefinitionType != null) {
//							updateList.put(BpmnMetaModelConstants.E_ATTR_BOUNDARY_EVENT_REFS, boundaryEventDefinitionType);
//							updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID, pn.getId());
//						}
//					} else if (element instanceof String) {
//						boundaryNodeTypeText.setText((String) element);
//						// handleForBoundaryEventChange(null);
//						updateList.put(BpmnMetaModelConstants.E_ATTR_BOUNDARY_EVENT_REFS, null);
//					}
//				}
//			}
//			// else if(object == cancelActivityCheck) {
//			// updateList.put(BpmnMetaModelConstants.E_ATTR_CANCEL_ACTIVITY,
//			// cancelActivityCheck.getSelection());
//			// }
//			else 
				if (object == scriptTaskButton) {
				boolean sel = scriptTaskButton.getSelection();
				EClass nodeType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
				if (sel && nodeType.equals(BpmnModelClass.RULE_FUNCTION_TASK)) {
					updateList.put(BpmnUIConstants.NODE_TYPE_CHANGE, BpmnModelClass.SCRIPT_TASK);
				} else if (!sel && nodeType.equals(BpmnModelClass.SCRIPT_TASK)) {
					updateList.put(BpmnUIConstants.NODE_TYPE_CHANGE, BpmnModelClass.RULE_FUNCTION_TASK);
				}

			}
			updatePropertySection(updateList);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);

		}

		public void modifyText(ModifyEvent e) {
			if (isRefresh()) {
				return;
			}

			Map<String, Object> updateList = new HashMap<String, Object>();

			updatePropertySection(updateList);
		}

	}

	protected PropertyNodeTypeGroup getNodeTypeData() {
		PropertyNodeTypeGroup activities = new PropertyNodeTypeGroup("Activities", getPaletteItems(), BpmnImages.ACTIVITIES_PALETTE_GRAPH);

		PropertyNodeTypeGroup roots = new PropertyNodeTypeGroup("roots", Arrays.asList(new PropertyNodeTypeGroup[] { activities }),
				BpmnImages.GATEWAY_PALETTE_GRAPH);

		return roots;
	}

	protected List<BpmnPaletteGroupItem> getPaletteItems() {
		List<BpmnPaletteGroupItem> items = new ArrayList<BpmnPaletteGroupItem>();
		BpmnPaletteModel toolDefs = PaletteLoader.getBpmnPaletteModel(fProject);
		items.addAll(toolDefs.getPaletteToolBySubType(BpmnModelClass.ACTIVITY, false, BpmnModelClass.SUB_PROCESS));

		return items;
	}
	
	
	protected boolean isInsideTableViewer(TableViewer tableviewer,Event event,boolean flagopen,String ext){
		
		boolean flag=false;
		Table table =tableviewer.getTable();
	        Point pt = new Point(event.x, event.y);
	        int index = table.getTopIndex();
	        while (index < table.getItemCount()) {
	        	 TableItem item = table.getItem(index);
	        	 for (int i = 0; i < 1; i++) {
	        		  Rectangle rect = item.getBounds(i);
	        		  if (rect.contains(pt)) {
	        			  flag=true;
	        			  if(flagopen){
	     					 try{
	     						 IFile file=getDiagramManager().getProject().getFile(item.getText()+"."+ext);
	     						 if(file!=null && !file.exists()){
	     							IFile fileLoc=IndexUtils.getLinkedResource(this.getDiagramManager().getProject().getName(),item.getText());
	     							if(fileLoc!=null)
	     								file=fileLoc;	 
	     						 }
	     						  IDE.openEditor(StudioUIPlugin.getActivePage(),file);
	     						  }catch(Exception e){
	     							e.printStackTrace();  
	     						  }
	     					}
	        		  }
	        		  index++;
	        	 }
	        }
	        return flag;
	}

}