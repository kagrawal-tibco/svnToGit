package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tomsawyer.graphicaldrawing.TSEGraph;


/**
 * 
 * @author pdhar
 *
 */
public class GeneralProcessPropertySection extends GeneralGraphPropertySection {

	protected Text authorText;	
	protected CCombo processTypeCombo;
	


	protected Label processTypecomboLabel;	
	private boolean refresh;	
	private String authorData;
	private int revision;
	private Text revisionText;
	


	public GeneralProcessPropertySection() {
		super();
		authorData = "";
		revision = 1;
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		
		nameText.setEditable(false);
		getWidgetFactory().createLabel(composite,BpmnMessages.getString("procesProp_authorText_Label"),  SWT.NONE);

		authorText = getWidgetFactory().createText(composite,"",  SWT.BORDER );
		GridData gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 617;
		authorText.setLayoutData(gd);
		
		getWidgetFactory().createLabel(composite, BpmnMessages.getString("procesProp_revisionText_Label"),  SWT.NONE);

		revisionText = getWidgetFactory().createText(composite,"",  SWT.BORDER );
		 gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 617;
		revisionText.setLayoutData(gd);
		revisionText.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9')) {
						e.doit = false;
						return;
					}
				}
			}
		}); 
		
		
		// Process Type
		processTypecomboLabel = getWidgetFactory().createLabel(composite, BpmnMessages.getString("procesProp_processTypecomboLabel_Label"),  SWT.NONE);
		processTypeCombo = getWidgetFactory().createCCombo(composite, SWT.READ_ONLY);
		gd = new GridData();
		gd.widthHint = 150;
		gd.heightHint = 20;
		gd.horizontalIndent = 1;
		processTypeCombo.setLayoutData(gd);
		processTypeCombo.add(BpmnUIConstants.PROCESS_TYPE_PRIVATE);
		processTypeCombo.add(BpmnUIConstants.PROCESS_TYPE_PUBLIC);
//		processTypeCombo.setEnabled(false);
		processTypeCombo.setText(BpmnUIConstants.NODE_ATTR_TASK_MODE_NONE);		
		
		
	}
	
	@Override
	public void aboutToBeHidden() {
		if (!composite.isDisposed()) {
			super.aboutToBeHidden();
			processTypeCombo.removeModifyListener(this.widgetListener);
			authorText.removeFocusListener(this.widgetListener);
			revisionText.removeFocusListener(this.widgetListener);
		}
	}
	
	@Override
	public void aboutToBeShown() {
		if (!composite.isDisposed()) {
			super.aboutToBeShown();
			processTypeCombo.addModifyListener(this.widgetListener);
			authorText.addFocusListener(this.widgetListener);
			revisionText.addFocusListener(this.widgetListener);
		}
	}
	
	@Override
	protected com.tibco.cep.bpmn.ui.graph.properties.GeneralGraphPropertySection.WidgetListener getWidgetListener() {
		// TODO Auto-generated method stub
		return new WidgetListener();
	}
	

	
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		try {
			this.refresh = true;
			super.refresh();
			if (getNode() != null) {
				processTypecomboLabel.setVisible(false);
				processTypeCombo.setVisible(true);
			}
			if (getNode() != null) {
				processTypecomboLabel.setVisible(false);
				processTypeCombo.setVisible(false);
			}
			if (getGraph() != null) {
				processTypecomboLabel.setVisible(true);
				processTypeCombo.setVisible(true);
				
				EObjectWrapper<EClass, EObject> processWrapper = null;
				TSEGraph rootGraph = getGraph();
				if(rootGraph != null){
					
					EObject userObject = (EObject) rootGraph.getUserObject();
					EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
					if(userObjWrapper.isInstanceOf(BpmnModelClass.PROCESS)) {
						processWrapper = userObjWrapper;
						Object attribute = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_PROCESS_TYPE);
						if(attribute != null){
							EEnumLiteral type =(EEnumLiteral)attribute;
							if(type!= null){
								if(type.equals(BpmnModelClass.ENUM_PROCESS_TYPE_PUBLIC))
									processTypeCombo.setText(BpmnUIConstants.PROCESS_TYPE_PUBLIC);
								else 
									processTypeCombo.setText(BpmnUIConstants.PROCESS_TYPE_PRIVATE);
							}
						}else
							processTypeCombo.setText(BpmnUIConstants.PROCESS_TYPE_PUBLIC);
					} 
				}
				if(processWrapper.getEInstance() != null) {
					refreshAuthorWidget(processWrapper);
				}
			}
		} finally {
			this.refresh = false;
		}
	}
	
	private void refreshAuthorWidget(EObjectWrapper<EClass, EObject> userObjWrapper){
		String author = null;
		Integer revision = null;
		String attrName = BpmnMetaModelExtensionConstants.E_ATTR_AUTHOR;
		if (userObjWrapper != null && attrName != null) {
			if (ExtensionHelper.isValidDataExtensionAttribute(userObjWrapper, attrName)) {
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(userObjWrapper);
				if (valueWrapper != null) {
					author = (String) valueWrapper.getAttribute(attrName);
					if (author == null)
						author = "";
					
					revision = (Integer) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VERSION);
					if (revision == null)
						revision = 1;
					
				}
			}
		}
		
		if (author != null && !author.isEmpty() ) {
			
			authorText.setText(author);
			authorData = author;
		}
		
		if (revision != null) {
			revisionText.setText(revision.toString());
			this.revision = revision;
		}
	}
	
	@Override
	protected TSEGraph getGraph() {
		// TODO Auto-generated method stub
		return getRootGraph(super.getGraph());
	}
	
	public boolean isRefresh() {
		return refresh;
	}

	
	private class WidgetListener extends GeneralGraphPropertySection.WidgetListener {
		@Override
		protected void handleTextModification(Text source) {
			// TODO Auto-generated method stub
			super.handleTextModification(source);
			Map<String, Object> updateList = new HashMap<String, Object>();
			if (source == authorText) {
				String author = authorText.getText();
				if (!author.equals(authorData)) {
					updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_AUTHOR, author);
					authorData = author;
				}
			}
			if (source == revisionText) {
				String rev = revisionText.getText();
				if (!rev.trim().isEmpty()) {
					int text = Integer.parseInt(rev);
					if (text != revision) {
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_VERSION,
								text);
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_IS_VERSION_CHANGED,
								true);
						revision = text;
					}
				}
			}
			if(updateList.size() > 0)
				updatePropertySection(updateList);
		}
		
		public void modifyText(ModifyEvent e) {
			if(isRefresh()){
				return;
			}
			
			Map<String, Object> updateList = new HashMap<String, Object>();
			Object source = e.getSource();
			 if(source == processTypeCombo ) {
				String processTypeText = processTypeCombo.getText();
				if (processTypeText != null && processTypeText.trim().length() > 0) {
					if (processTypeText.equals(BpmnUIConstants.PROCESS_TYPE_PUBLIC)) {
						updateList.put(BpmnMetaModelConstants.E_ATTR_PROCESS_TYPE, BpmnUIConstants.PROCESS_TYPE_PUBLIC);
					}
					else {
						updateList.put(BpmnMetaModelConstants.E_ATTR_PROCESS_TYPE, BpmnUIConstants.PROCESS_TYPE_PRIVATE);
					}
				}
				
			} else {
				super.modifyText(e);
			}

			 updatePropertySection(updateList);
		}
		

	}
	
	public void setProcessTypeCombo(CCombo processTypeCombo) {
		this.processTypeCombo = processTypeCombo;
	}

	
}