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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.PropertiesType;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.model.command.AbstractNodeCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.IGraphCommand;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.TSCommand;


/**
 * 
 * @author ggrigore
 *
 */
public class GeneralTextAnnotationPropertySection extends AbstractBpmnPropertySection {

	protected Composite composite;
	protected Text nameText;
	protected Text labelText;
//	protected Text descriptionText;
	protected Text text;
	private Button applyButton;
	private Button resetButton;
	
	private boolean refresh;
	private WidgetListener widgetListener;
	
	private String name;
//	private String description;
	private String content;
	private String label;
	

	public GeneralTextAnnotationPropertySection() {
		super();
		this.widgetListener = new WidgetListener();
		name = "";
		label= "";
//		description = "";
		content = "";
	}
	
	@Override
	public void aboutToBeHidden() {
		if (!composite.isDisposed()) {
			nameText.removeSelectionListener(this.widgetListener);
			nameText.removeFocusListener(this.widgetListener);
			labelText.removeSelectionListener(this.widgetListener);
			labelText.removeFocusListener(this.widgetListener);
//			labelText.removeModifyListener(this.widgetListener);
//			descriptionText.removeSelectionListener(this.widgetListener);
//			descriptionText.removeFocusListener(this.widgetListener);
//			text.removeSelectionListener(this.widgetListener);
//			text.removeFocusListener(this.widgetListener);
			text.removeModifyListener(this.widgetListener);
			applyButton.removeSelectionListener(this.widgetListener);
			resetButton.removeSelectionListener(this.widgetListener);
		}
	}
	
	@Override
	public void aboutToBeShown() {
		if (!composite.isDisposed()) {
			nameText.addSelectionListener(this.widgetListener);
			nameText.addFocusListener(this.widgetListener);
			labelText.addSelectionListener(this.widgetListener);
			labelText.addFocusListener(this.widgetListener);
//			labelText.addModifyListener(this.widgetListener);
//			descriptionText.addSelectionListener(this.widgetListener);
//			descriptionText.addFocusListener(this.widgetListener);
//			text.addSelectionListener(this.widgetListener);
//			text.addFocusListener(this.widgetListener);
			text.addModifyListener(this.widgetListener);
			applyButton.addSelectionListener(this.widgetListener);
			resetButton.addSelectionListener(this.widgetListener);
		}
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));
		getWidgetFactory().createLabel(composite,  BpmnMessages.getString("name_Label"),  SWT.NONE);
		nameText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		nameText.setEditable(false);
		nameText.setBackground(COLOR_GRAY);
		
		GridData gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		nameText.setLayoutData(gd);
		
		getWidgetFactory().createLabel(composite, BpmnMessages.getString("label_Label"),  SWT.NONE);
		labelText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		labelText.setLayoutData(gd);
		

//		getWidgetFactory().createLabel(composite, "Description",  SWT.NONE)
//			.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
//		descriptionText = getWidgetFactory().createText(composite,
//				"",
//				SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
//
//		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
//		gd.widthHint = 598;
//		gd.heightHint = 30;
//		descriptionText.setLayoutData(gd);
		getWidgetFactory().createLabel(composite, BpmnMessages.getString("text_label"),  SWT.NONE)
		.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		
		Composite buttonComp = getWidgetFactory().createComposite(composite);
		gd = new GridData(GridData.FILL, GridData.BEGINNING, true, false);
		buttonComp.setLayoutData(gd);
		buttonComp.setLayout(new GridLayout(2, false));
		
		
		text = getWidgetFactory().createText(buttonComp,
				"",
				SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
	
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 598;
		gd.heightHint = 120;
		gd.verticalSpan = 7;
		text.setLayoutData(gd);
		
		
		
		applyButton = getWidgetFactory().createButton(buttonComp, BpmnMessages.getString("textAnnotatioprop_applyButton_label"), SWT.PUSH);
		gd = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false);
		applyButton.setLayoutData(gd);
		
		resetButton = getWidgetFactory().createButton(buttonComp, BpmnMessages.getString("textAnnotatioprop_resetButton_label"), SWT.PUSH);
		gd = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false);
		resetButton.setLayoutData(gd);
		
		resetButton.setEnabled(false);
		applyButton.setEnabled(false);
	}
	
	
	private void refreshPropertySheetLabel() {
		// TODO
		// BpmnGraphPropertyLabelProvider.getText and getImage need to be called.
	}


	private void fireUpdate(Map<String, Object> updateList){
		if(updateList.size() == 0)
			return ;
		
		EClass nodeType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		ENamedElement extType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);

		BpmnDiagramManager bpmnGraphDiagramManager = fPropertySheetPage.getEditor().getBpmnGraphDiagramManager();
		AbstractNodeCommandFactory cf = (AbstractNodeCommandFactory) bpmnGraphDiagramManager.getModelGraphFactory().getCommandFactory(nodeType,extType);
		IGraphCommand<TSENode> cmd = cf.getCommand(IGraphCommand.COMMAND_UPDATE, fTSENode, PropertiesType.GENERAL_PROPERTIES, updateList);
			
		bpmnGraphDiagramManager.executeCommand((TSCommand)cmd);
	}
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		try{
			this.refresh = true;
			if (fTSENode != null) { 
				String description = (String) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TASK_DESCRIPTION);
				EObject userObject = (EObject) fTSENode.getUserObject();
				EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
				String textContent = (String)userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TEXT);
				
				nameText.setText("");
				labelText.setText("");
//				descriptionText.setText("");
				text.setText("");
				
				if (userObject != null) {
					name = userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
					nameText.setText(name);
				}
				
				String lbl = null;
				if (ExtensionHelper.isValidDataExtensionAttribute(
						userObjWrapper,
						BpmnMetaModelExtensionConstants.E_ATTR_LABEL)){
					EObjectWrapper<EClass, EObject> valWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(userObjWrapper);
					if (valWrapper != null)
						lbl = valWrapper
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL);
				}
				
				if (lbl != null && !lbl.trim().isEmpty()) {
					label = lbl ;
					labelText.setText(label);
				}
				
				if (description != null){
//					this.description = description;
//					descriptionText.setText(description);
				}
				if(textContent != null){
					this.content = textContent;
					text.setText(content);
					resetButton.setEnabled(false);
					applyButton.setEnabled(false);
				}
			}
			if (fTSEEdge != null) {
				
			}
			if (fTSEGraph != null) {

			}
			
		}finally{
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
		getDiagramManager().refreshNode(fTSENode);
	}
	
	private class WidgetListener extends SelectionAdapter implements FocusListener, ModifyListener {
		
		
//		private void modifyLabel() {
//			EClass nodeType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
//			EClass nodeExtType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
//			String toolId = (String)fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
//			String resUrl = (String)fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
//			AbstractNodeUIFactory nodeUIFactory ;
//			if (nodeExtType != null) {
//				ExpandedName classSpec = BpmnMetaModel.INSTANCE
//						.getExpandedName(nodeExtType);
//				nodeUIFactory = BpmnGraphUIFactory.getInstance(null)
//						.getNodeUIFactory(name, resUrl,toolId, nodeType, classSpec);
//			}
//			else
//				nodeUIFactory = BpmnGraphUIFactory.getInstance(null).getNodeUIFactory(name,resUrl, toolId, nodeType);
//			// ((TSENodeLabel)(((TSENode)fTSENode).labels().get(0))).setDefaultOffset();
//			nodeUIFactory.addNodeLabel(fTSENode, labelText.getText());
//			getDiagramManager().refreshNode(fTSENode);
//		}
		
		private void handleTextModification(Text source) {
			if(isRefresh()){
				return;
			}
			Map<String, Object> updateList = new HashMap<String, Object>();
			if(source == labelText ) {
			if (fTSENode != null) {
					if (!label.equalsIgnoreCase(labelText.getText().trim())) {
						label = labelText.getText();
						updateList.put(BpmnMetaModelConstants.E_ATTR_LABEL,
								labelText.getText());
//						modifyLabel();
					}
				}
			}
//			else if(source == descriptionText ) {
//				String descText = descriptionText.getText();
//				if (!descText.equalsIgnoreCase(description)) {
//					description = descText;
//					updateList.put(BpmnMetaModelConstants.E_ATTR_TEXT, descText);
//				}
//			}
			else if(source == text ) {
				String contentText = text.getText();
				if (!contentText.equalsIgnoreCase(content)) {
					content = contentText;
					updateList.put(BpmnUIConstants.ATTR_ANOOTATION_TEXT, contentText);
				}
			} 
			updatePropertySection(updateList);
			if(source == text ) {
				getNode().setName(content);
				getDiagramManager().refreshNode(getNode());
			}
		}
		

		@Override
		public void widgetSelected(SelectionEvent e) {
			Object object = e.getSource();
			 if(object == applyButton){
				String contentText = text.getText();
				if(contentText.trim().isEmpty()){
					contentText = "Note";
					refresh = true;
					text.setText(contentText);
					refresh= false;
				}
				if (!contentText .equalsIgnoreCase(content)) {
					Map<String, Object> updateList = new HashMap<String, Object>();
					content = contentText;
					updateList.put(BpmnUIConstants.ATTR_ANOOTATION_TEXT, contentText);
					updatePropertySection(updateList);
					getNode().setName(content);
					getDiagramManager().refreshNode(getNode());
					applyButton.setEnabled(false);
					resetButton.setEnabled(false);
				}
			}else if(object == resetButton){
				text.setText(content);
				getNode().setName(content);
				getDiagramManager().refreshNode(getNode());
				applyButton.setEnabled(false);
				resetButton.setEnabled(false);
			}
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


		@Override
		public void modifyText(ModifyEvent e) {
			if(isRefresh())
				return;
			if(e.getSource() == text){
				EObject userObject = (EObject) fTSENode.getUserObject();
				EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
				String textContent = (String)userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TEXT);
				if(textContent.equals(text.getText())){
					applyButton.setEnabled(false);
					resetButton.setEnabled(false);
				}else{
					applyButton.setEnabled(true);
					resetButton.setEnabled(true);
				}
			}
			
		}
	}
	
	
}