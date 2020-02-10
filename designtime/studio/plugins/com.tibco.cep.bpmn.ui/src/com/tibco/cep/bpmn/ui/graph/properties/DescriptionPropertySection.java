package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.PropertiesType;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.model.command.ICommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.IGraphCommand;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.interactive.command.TSCommand;


/**
 * 
 * @author majha
 *
 */
public class DescriptionPropertySection extends AbstractBpmnPropertySection {

	protected Text descriptionText;
	protected Composite composite;
	private boolean refresh;
	protected WidgetListener widgetListener;

	private String description;
	
	public DescriptionPropertySection() {
		super();
		this.widgetListener = getWidgetListener();
		description = "";
	}
	
	@Override
	public void aboutToBeHidden() {
		if (!composite.isDisposed()) {
			descriptionText.removeModifyListener(this.widgetListener);
		}
	}
	
	@Override
	public void aboutToBeShown() {
		if (!composite.isDisposed()) {
			descriptionText.addModifyListener(this.widgetListener);
		}
	}
	
	protected  WidgetListener getWidgetListener(){
		return new WidgetListener();
	}
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));

		
		getWidgetFactory().createLabel(composite,BpmnMessages.getString("descriptionProp_description_label"),  SWT.NONE)
			.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		descriptionText = getWidgetFactory().createText(composite,
				"",
				SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);

		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.verticalSpan = 20;
		descriptionText.setLayoutData(gd);
		
	}

	
	protected void refreshPropertySheetLabel() {
		// TODO
		// BpmnGraphPropertyLabelProvider.getText and getImage need to be called.
	}


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
			EObject userObject= (EObject) obj.getUserObject();
			EObjectWrapper<EClass, EObject> userObjectWrapper = EObjectWrapper.wrap(userObject);
			if(userObjectWrapper.isInstanceOf(BpmnModelClass.LANE)){
				obj= getDiagramManager().getModelController().getRootGraph(fTSEGraph);
			}

		}
		if(obj == null)
			return;
		EClass nodeType = (EClass) obj.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		ENamedElement extType = (EClass) obj.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
		BpmnDiagramManager bpmnGraphDiagramManager = fPropertySheetPage.getEditor().getBpmnGraphDiagramManager();
		ICommandFactory cf =   bpmnGraphDiagramManager.getModelGraphFactory().getCommandFactory(nodeType,extType);
		if(cf != null) {
			IGraphCommand<?> cmd = cf.getCommand(IGraphCommand.COMMAND_UPDATE, obj, PropertiesType.GENERAL_PROPERTIES, updateList);
			bpmnGraphDiagramManager.executeCommand((TSCommand)cmd);
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
				EObjectWrapper<EClass, EObject> userObjectWrapper = EObjectWrapper.wrap(userObject);
				if(userObjectWrapper.isInstanceOf(BpmnModelClass.LANE)){
					userObject= (EObject)getDiagramManager().getModelController().getRootGraph(fTSEGraph).getUserObject();
				}

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
			
			this.description = description;
			descriptionText.setText(description);
			
		} finally {
			// System.out.println("Done with refresh.");
			this.refresh = false;
		}
	}
	
	public boolean isRefresh() {
		return refresh;
	}

	protected void updatePropertySection(Map<String, Object> updateList) {
		if(updateList.size() == 0)
			return;
		refreshPropertySheetLabel();
		fireUpdate(updateList);
		getDiagramManager().refreshGraph(fTSEGraph);
	}

	
	
	protected class WidgetListener  implements ModifyListener {
		
		@Override
		public void modifyText(ModifyEvent e) {
			Object object = e.getSource();
			if(object instanceof Text)
				handleTextModification((Text)object);
			
		}
		private void handleTextModification(Text source) {
			if(isRefresh()){
				return;
			}
			Map<String, Object> updateList = new HashMap<String, Object>();
			if (source == descriptionText ) {
				String descText = descriptionText.getText();
				if (!descText.equalsIgnoreCase(description)) {
					description = descText;
					updateList.put(BpmnMetaModelConstants.E_ATTR_TEXT,descText);
					updatePropertySection(updateList);
				}
			}

		}
		
	}
}