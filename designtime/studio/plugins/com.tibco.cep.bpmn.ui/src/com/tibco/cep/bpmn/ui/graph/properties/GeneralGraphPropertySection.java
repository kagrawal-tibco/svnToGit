package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.PropertiesType;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.model.AbstractNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.ICommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.IGraphCommand;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.interactive.command.TSCommand;


/**
 * 
 * @author pdhar
 *
 */
public abstract class GeneralGraphPropertySection extends AbstractBpmnPropertySection {

	protected Text nameText;
	protected Text labelText;
	protected Composite composite;
	private boolean refresh;
	protected WidgetListener widgetListener;

	private String name;
	private String label;
	
	public GeneralGraphPropertySection() {
		super();
		this.widgetListener = getWidgetListener();
		name = "";
		label="";
	}
	
	@Override
	public void aboutToBeHidden() {
		if (!composite.isDisposed()) {
			nameText.removeSelectionListener(this.widgetListener);
			nameText.removeFocusListener(this.widgetListener);
			labelText.removeSelectionListener(this.widgetListener);
			labelText.removeFocusListener(this.widgetListener);
		}
	}
	
	@Override
	public void aboutToBeShown() {
		if (!composite.isDisposed()) {
			nameText.addSelectionListener(this.widgetListener);
			nameText.addFocusListener(this.widgetListener);
			labelText.addSelectionListener(this.widgetListener);
			labelText.addFocusListener(this.widgetListener);
		}
	}
	
	protected abstract WidgetListener getWidgetListener();
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));

		getWidgetFactory().createLabel(composite,BpmnMessages.getString("name_Label"),  SWT.NONE);
		nameText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		nameText.setEditable(false);
		nameText.setBackground(COLOR_GRAY);
		GridData gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		nameText.setLayoutData(gd);

		getWidgetFactory().createLabel(composite,BpmnMessages.getString("label_Label"),  SWT.NONE);
		labelText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		labelText.setLayoutData(gd);
		
		
	}

	
	protected void refreshPropertySheetLabel() {
		// TODO
		// BpmnGraphPropertyLabelProvider.getText and getImage need to be called.
	}


	protected void fireUpdate(Map<String, Object> updateList){
		if( updateList.size() == 0)
			return;
		TSEGraph graph = getGraph();
		TSGraphObject obj = graph == null? getNode():graph;
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
	
	protected void fireLabelUpdate(Map<String, Object> updateList){
		if(updateList.size() == 0)
			return;
		TSEGraph graph = getGraph();
		TSGraphObject obj = graph == null? getNode():graph;
		if (obj != null) {
			EClass nodeType = (EClass) obj.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			ENamedElement extType = (EClass) obj.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
			BpmnDiagramManager bpmnGraphDiagramManager = fPropertySheetPage
					.getEditor().getBpmnGraphDiagramManager();
			ICommandFactory cf =  bpmnGraphDiagramManager
					.getModelGraphFactory()
					.getCommandFactory(nodeType, extType);
			if (cf != null) {
				IGraphCommand<?> cmd = cf.getCommand(
						IGraphCommand.COMMAND_UPDATE, obj,
						PropertiesType.GENERAL_PROPERTIES, updateList);
				if(cmd != null)
					bpmnGraphDiagramManager.executeCommand((TSCommand) cmd);
			}
		}
	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		try {
			this.refresh = true;
			@SuppressWarnings("unused")
			String nodeName = null;
			EObject userObject= null;
			if (getNode() != null) {
				 userObject = (EObject) getNode().getUserObject();
				 nodeName = (String)getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
			}
			if (getEdge() != null) { }
			if (getGraph() != null) {
				nodeName = (String) getGraph().getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
//				String nodeLabel = (String) getGraph().getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);
				userObject = (EObject) getGraph().getUserObject();

			}
			
			if (userObject != null) {
				EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
						.wrap(userObject);
				
				name = userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				nameText.setText(name);
				
				String lbl = null;
				if (ExtensionHelper.isValidDataExtensionAttribute(
						userObjWrapper,
						BpmnMetaModelExtensionConstants.E_ATTR_LABEL)) {
					EObjectWrapper<EClass, EObject> valWrapper = ExtensionHelper
							.getAddDataExtensionValueWrapper(userObjWrapper);
					if (valWrapper != null)
						lbl = valWrapper
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL);
				}

				if (lbl != null && !lbl.trim().isEmpty()) {
					label = lbl;
					labelText.setText(label);
				} else {
					label = "";
					labelText.setText("");
				}

			}
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
	
	private void modifyLabel() {
		if (fTSENode != null) {
			EClass nodeType = (EClass) fTSENode
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			EClass nodeExtType = (EClass) fTSENode
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
			String toolId = (String) fTSENode
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
			String resUrl = (String) fTSENode
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
			AbstractNodeUIFactory nodeUIFactory;
			if (nodeExtType != null){
				ExpandedName classSpec = BpmnMetaModel.INSTANCE
				.getExpandedName(nodeExtType);
				nodeUIFactory = BpmnGraphUIFactory.getInstance(null)
						.getNodeUIFactory(name,resUrl, toolId, nodeType, classSpec);
			}
			else
				nodeUIFactory = BpmnGraphUIFactory.getInstance(null)
						.getNodeUIFactory(name,resUrl, toolId, nodeType);
			// ((TSENodeLabel)(((TSENode)fTSENode).labels().get(0))).setDefaultOffset();
			nodeUIFactory.addNodeLabel(fTSENode, labelText.getText());
			getDiagramManager().refreshNode(fTSENode);
		}
	}
	
	private void modifyNodeToolTip(){
		if (fTSENode != null) {
		EClass nodeType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		EClass nodeExtType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
		String toolId = (String)fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		String resUrl = (String)fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
		AbstractNodeUIFactory nodeUIFactory ;
		if (nodeExtType != null) {
			ExpandedName classSpec = BpmnMetaModel.INSTANCE
					.getExpandedName(nodeExtType);
			nodeUIFactory = BpmnGraphUIFactory.getInstance(null)
					.getNodeUIFactory(name, resUrl, toolId, nodeType, classSpec);
		}
		else
			nodeUIFactory = BpmnGraphUIFactory.getInstance(null).getNodeUIFactory(name,resUrl, toolId, nodeType);
		nodeUIFactory.updateNodeToolTip(fTSENode);
		}
	}
	
	protected class WidgetListener extends  SelectionAdapter implements ModifyListener, FocusListener {
		
		@Override
		public void modifyText(ModifyEvent e) {
			// TODO Auto-generated method stub
			
		}
		protected void handleTextModification(Text source) {
			if(isRefresh()){
				return;
			}
			Map<String, Object> updateList = new HashMap<String, Object>();
			boolean labelUpdate= false;
			if (source == nameText) {
				if(!name.equalsIgnoreCase(nameText.getText().trim())) {
					name = nameText.getText();
					updateList.put(BpmnMetaModelConstants.E_ATTR_ID,name);
				}
								
			} else if (source == labelText) {
				if(!label.equalsIgnoreCase(labelText.getText().trim())) {
					label = labelText.getText();
					modifyLabel();
					refreshOverviewView();
					updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_LABEL,label);
					labelUpdate = true;
				}
			}
			if(labelUpdate)
				fireLabelUpdate(updateList);
			else
				updatePropertySection(updateList);
			
			if(updateList.size() > 0 )
				modifyNodeToolTip();
		}
		

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			Object object = e.getSource();
			if(object instanceof Text)
				handleTextModification((Text)object);

		}

		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void focusLost(FocusEvent e) {
			Object object = e.getSource();
			if(object instanceof Text)
				handleTextModification((Text)object);
			
		}
	}
}