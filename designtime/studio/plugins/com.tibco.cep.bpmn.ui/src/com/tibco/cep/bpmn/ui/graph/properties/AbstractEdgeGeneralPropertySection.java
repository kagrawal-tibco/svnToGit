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
import org.eclipse.swt.widgets.Label;
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
import com.tibco.cep.bpmn.ui.graph.model.AbstractEdgeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.AbstractEdgeCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.IGraphCommand;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.interactive.command.TSCommand;

/**
 * 
 * @author ggrigore
 *
 */
abstract public class AbstractEdgeGeneralPropertySection extends AbstractBpmnPropertySection {
	
	protected Text nameText;
	protected Text labelText;
	protected Label inlineLabel;
	protected Button inlineCheck;
    protected Composite composite;
    
	private boolean refresh;
	private WidgetListener widgetListener;	
	
	private String name;
	private String label;

	public AbstractEdgeGeneralPropertySection() {
		super();
		this.widgetListener = new WidgetListener();
		name = "";
		label = null;
	}
	
	@Override
	public void aboutToBeHidden() {
		if(!composite.isDisposed()) {
			nameText.removeSelectionListener(this.widgetListener);
			nameText.removeFocusListener(this.widgetListener);
			labelText.removeSelectionListener(this.widgetListener);
			labelText.removeFocusListener(this.widgetListener);
			labelText.removeModifyListener(this.widgetListener);
//			inlineCheck.removeSelectionListener(this.widgetListener);
		}
	}
	
	@Override
	public void aboutToBeShown() {
		if(!composite.isDisposed()) {
			nameText.addSelectionListener(this.widgetListener);
			nameText.addFocusListener(this.widgetListener);
			labelText.addSelectionListener(this.widgetListener);
			labelText.addFocusListener(this.widgetListener);
			labelText.addModifyListener(this.widgetListener);
//			inlineCheck.addSelectionListener(this.widgetListener);
		}
	}
	
	protected void updatePropertySection(Map<String, Object> updateList) {
		refreshPropertySheetLabel();
		fireUpdate(updateList);
	}
	
	private void fireUpdate(Map<String, Object> updateList){
		if(updateList.size() == 0)
			return ;
		
		EClass nodeType = (EClass) fTSEEdge.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		ENamedElement extType = (EClass) fTSEEdge.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);

		BpmnDiagramManager bpmnGraphDiagramManager = fPropertySheetPage.getEditor().getBpmnGraphDiagramManager();
		AbstractEdgeCommandFactory cf = (AbstractEdgeCommandFactory) bpmnGraphDiagramManager.getModelGraphFactory().getCommandFactory(nodeType,extType);
		IGraphCommand<TSEEdge> cmd = cf.getCommand(IGraphCommand.COMMAND_UPDATE, fTSEEdge, PropertiesType.GENERAL_PROPERTIES, updateList);
			
		bpmnGraphDiagramManager.executeCommand((TSCommand)cmd);
	}
	
	private void refreshPropertySheetLabel() {
		// TODO
		// BpmnGraphPropertyLabelProvider.getText and getImage need to be called.
	}
	
	public boolean isRefresh() {
		return refresh;
	}
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));

		getWidgetFactory().createLabel(composite, BpmnMessages.getString("name_Label"),  SWT.NONE);
		nameText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		GridData gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		nameText.setLayoutData(gd);
		nameText.setEditable(false);
		nameText.setBackground(COLOR_GRAY);
	
		getWidgetFactory().createLabel(composite, BpmnMessages.getString("label_Label"),  SWT.NONE);
		labelText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		labelText.setLayoutData(gd);

//		// Add inline check box
//		inlineLabel = getWidgetFactory().createLabel(composite,"Inline",  SWT.NONE);
//		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
//		gd.widthHint = 617;
//		inlineCheck = new Button(composite,SWT.CHECK);
				
	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		this.refresh = true;
		if (fTSENode != null){}
		if (fTSEEdge != null){ 
			nameText.setText(fTSEEdge.getName().toString());
			// labelText.setText(""); //?
			EObject userObject = (EObject) fTSEEdge.getUserObject();
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
			String edgeName = userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			@SuppressWarnings("unused")
			EClass edgeType = (EClass) fTSEEdge.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			
			if (edgeName != null) {
				nameText.setText(edgeName);
				name = edgeName;
			}
			
			label = "";
			labelText.setText("");
			String lbl = null;
			if (userObjWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_NAME)) {
				lbl =userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
				
			}else if (ExtensionHelper.isValidDataExtensionAttribute(
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
			

			if (inlineCheck!= null && inlineCheck.isVisible()) {
				if (ExtensionHelper.isValidDataExtensionAttribute(
						userObjWrapper,
						BpmnMetaModelExtensionConstants.E_ATTR_INLINE)) {
					EObjectWrapper<EClass, EObject> valWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(userObjWrapper);
					boolean isInline = false;
					if (valWrapper != null) {
						isInline = (Boolean) valWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_INLINE);
					}
					inlineCheck.setSelection(isInline);
				}
			}
		}
		if (fTSEGraph != null){}
		this.refresh = false;
	}
	
	abstract String getLabelAttribute();

	private class WidgetListener extends SelectionAdapter implements FocusListener, ModifyListener {
		
		private void handleTextModification(Text source) {
			if(isRefresh()){
				return;
			}
			Map<String, Object> updateList = new HashMap<String, Object>();
			if(source == nameText ) {
				if (fTSEEdge != null) {
					if (!name.equalsIgnoreCase(nameText.getText().trim())) {
						name = nameText.getText();
						updateList.put(BpmnMetaModelConstants.E_ATTR_ID, nameText.getText());
					}
				}
			} else if (source == labelText) {
				if (fTSEEdge != null) {
					if (!label.equalsIgnoreCase(labelText.getText().trim())) {
						label = labelText.getText();
						this.modifyLabel();
						refreshOverviewView();
						updateList.put(getLabelAttribute(),	labelText.getText());
					}
				}
			}

			updatePropertySection(updateList);
		}
		
		@Override
		public void modifyText(ModifyEvent e) {
			if(isRefresh()){
				return;
			}
			@SuppressWarnings("unused")
			Object source = e.getSource();
			@SuppressWarnings("unused")
			Map<String, Object> updateList = new HashMap<String, Object>();
//			if (source == labelText) {
//				this.modifyLabel();
//			}
			
			// updatePropertySection(updateList);
		}		

		
		private void modifyLabel() {
			EClass edgeType = (EClass) fTSEEdge.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			AbstractEdgeUIFactory edgeUIFactory = BpmnGraphUIFactory.getInstance(null)
			.getEdgeUIFactory(name,  edgeType);
			edgeUIFactory.addEdgeLabel(fTSEEdge, labelText.getText());
		
			getDiagramManager().refreshEdge(fTSEEdge);
		}		
		

		public void widgetSelected(SelectionEvent e) {
			if(isRefresh()){
				return;
			}
			Object source = e.getSource();
			Map<String, Object> updateList = new HashMap<String, Object>();
			if (source == inlineCheck) {
				boolean optionInline = inlineCheck.getSelection();
				EObject seqFlow = (EObject)fTSEEdge.getUserObject();
				EObject valueObj = ExtensionHelper.getAddDataExtensionValue(seqFlow);
				if (valueObj != null) {
					EObjectWrapper<EClass, EObject> valWrapper = EObjectWrapper
							.wrap(valueObj);
					boolean isInline = (Boolean) valWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_INLINE);
					if (optionInline != isInline) {
						updateList.put(
								BpmnMetaModelExtensionConstants.E_ATTR_INLINE,
								optionInline);
					}
				}
			}
			updatePropertySection(updateList);
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