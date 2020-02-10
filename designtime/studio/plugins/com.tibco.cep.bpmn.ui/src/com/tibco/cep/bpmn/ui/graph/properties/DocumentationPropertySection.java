package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.PropertiesType;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tibco.cep.bpmn.ui.graph.model.command.ICommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.IGraphCommand;
import com.tibco.cep.studio.ui.doc.ExtendedRichTextEditor;
import com.tibco.cep.studio.ui.doc.RichTextEditorToolkit;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.interactive.command.TSCommand;


/**
 * 
 * @author majha
 *
 */
public class DocumentationPropertySection extends AbstractBpmnPropertySection {

	protected Composite composite;
	private boolean refresh;
	private ExtendedRichTextEditor editor;
	private WidgetListener listener;
	
	public DocumentationPropertySection() {
		super();
		listener =new WidgetListener();
	}
	
	@Override
	public void aboutToBeHidden() {
		if (!composite.isDisposed()) {
			editor.removeFocusListener(listener);
		}
	}
	
	@Override
	public void aboutToBeShown() {
		if (!composite.isDisposed()) {
			editor.addFocusListener(listener);
		}
	}
	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		composite = parent;
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 1;
		parent.setLayout(layout);

		fEditor = (BpmnEditor) tabbedPropertySheetPage.getSite().getPage().getActiveEditor();
		editor = RichTextEditorToolkit.createEditor(parent, fEditor.getEditorSite(), true);
	}
	
	protected void refreshPropertySheetLabel() {
		// TODO
		// BpmnGraphPropertyLabelProvider.getText and getImage need to be called.
	}


	@SuppressWarnings("rawtypes")
	protected void fireUpdate(Map<String, Object> updateList){
		if( updateList.size() == 0)
			return;
		
		TSGraphObject obj =null;
		if (getNode() != null) {
			obj =  getNode();
		}
		if (getEdge() != null) { 
			obj = getEdge();
		}
		if (getGraph() != null) {
			obj = getGraph();

		}
		if(obj == null)
			return;
		EClass nodeType = (EClass) obj.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		if(nodeType  == null){
			Object userObject = obj.getUserObject();
			if(userObject != null && userObject instanceof EObject){
				EObject eObj = (EObject)userObject;
				nodeType = eObj.eClass();
			}
		}
			
		ENamedElement extType = (EClass) obj.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
		BpmnDiagramManager bpmnGraphDiagramManager = fPropertySheetPage.getEditor().getBpmnGraphDiagramManager();
		ICommandFactory cf =   bpmnGraphDiagramManager.getModelGraphFactory().getCommandFactory(nodeType,extType);
		if(cf != null) {
			IGraphCommand cmd = cf.getCommand(IGraphCommand.COMMAND_UPDATE, obj, PropertiesType.GENERAL_PROPERTIES, updateList);
			if (cmd != null) {
				bpmnGraphDiagramManager.executeCommand((TSCommand)cmd);
			}
		}
	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		try {
			this.refresh = true;
			EObject userObject= null;
			if (getNode() != null) {
				 userObject = (EObject) getNode().getUserObject();
			}
			if (getEdge() != null) { 
				userObject = (EObject) getEdge().getUserObject();
			}
			
			if (getGraph() != null) {
				userObject = (EObject) getGraph().getUserObject();

			}
			
			String description = "";
			if(userObject != null){
				EObjectWrapper<EClass, EObject> objWrapper = EObjectWrapper.wrap(userObject);
				EList<EObject> listAttribute = objWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
				if(listAttribute.size() > 0 ){
					EObjectWrapper<EClass, EObject> doc = EObjectWrapper.wrap(listAttribute.get(0));
					description = (String)doc.getAttribute(BpmnMetaModelConstants.E_ATTR_TEXT);
				}
			}
			editor.setText(description);
		} finally {
			// System.out.println("Done with refresh.");
			this.refresh = false;
		}
	}
	
	public boolean isRefresh() {
		return refresh;
	}
	
	protected TSEGraph getGraph() {
		if(fTSENode != null){
			EObject userObject = (EObject)fTSENode.getUserObject();
			if(userObject.eClass().equals(BpmnModelClass.LANE) && (super.getGraph() != null)){
				return getRootGraph(super.getGraph());
			}
		}
		return super.getGraph();
	}

	public void updatePropertySection(Map<String, Object> updateList) {
		if(updateList.size() == 0)
			return;
//		refreshPropertySheetLabel();
		fireUpdate(updateList);
//		getDiagramManager().refreshGraph(fTSEGraph);
	}
	
	private String getSavedDescription(){
		EObject userObject= null;
		if (getNode() != null) {
			 userObject = (EObject) getNode().getUserObject();
		}
		if (getEdge() != null) { 
			userObject = (EObject) getEdge().getUserObject();
		}
		
		if (getGraph() != null) {
			userObject = (EObject) getGraph().getUserObject();

		}
		
		String description = "";
		if(userObject != null){
			EObjectWrapper<EClass, EObject> objWrapper = EObjectWrapper.wrap(userObject);
			EList<EObject> listAttribute = objWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
			if(listAttribute.size() > 0 ){
				EObjectWrapper<EClass, EObject> doc = EObjectWrapper.wrap(listAttribute.get(0));
				description = (String)doc.getAttribute(BpmnMetaModelConstants.E_ATTR_TEXT);
			}
		}
		
		return description;
	}
	
	private class WidgetListener implements FocusListener {

//		boolean focus = false;
		@Override
		public void focusGained(FocusEvent e) {
//			if (e.getSource() == editor.getEditorControl() || e.getSource() == editor.getSourceEdit()) {
//				focus = true;
//			} else {
//				focus = false;
//			}
		}

		@Override
		public void focusLost(FocusEvent e) {
//			if (focus) {
//				return;
//			}
			String text = editor.getText();
			if(!refresh){
				if(!getSavedDescription().equals(text)){
					Map<String, Object> updateList = new HashMap<String, Object>();
					updateList.put(BpmnMetaModelConstants.E_ATTR_TEXT, text);
					updatePropertySection(updateList);
				}
				getNode().setAttribute(BpmnUIConstants.NODE_ATTR_TASK_DESCRIPTION, getSavedDescription());
			}			
		}
	}
}