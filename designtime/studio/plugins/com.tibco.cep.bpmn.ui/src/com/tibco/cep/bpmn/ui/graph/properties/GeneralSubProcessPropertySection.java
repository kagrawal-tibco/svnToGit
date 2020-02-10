package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;

/**
 * 
 * @author majha
 *
 */
public class GeneralSubProcessPropertySection extends
		GeneralTaskPropertySection {

	private Button triggerByEventButton;
	private WidgetListener widgetListener;
	
	public GeneralSubProcessPropertySection() {
		super();
		this.widgetListener = new WidgetListener();
	}
	
	@Override
	public void aboutToBeHidden() {
		if (!composite.isDisposed()) {
			super.aboutToBeHidden();
			triggerByEventButton.removeSelectionListener(this.widgetListener);
		}
	}
	
	@Override
	public void aboutToBeShown() {
		if (!composite.isDisposed()) {
			super.aboutToBeShown();
			triggerByEventButton.addSelectionListener(this.widgetListener);
		}
	}
	
	@Override
	protected void createProperties() {		
		super.createProperties();
		this.timeoutEnable.setEnabled(false);
		this.timeoutEnable.setSelection(true);
	}
	@Override
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		// TODO Auto-generated method stub
		super.createControls(parent, tabbedPropertySheetPage);
	}
	
	@Override
	protected void insertChildSpecificComponents() {
		getWidgetFactory().createLabel(composite,BpmnMessages.getString(("subProcessProp_triggerByEventButton_label")),  SWT.NONE);
		triggerByEventButton = new Button(composite,SWT.CHECK);
	}
	
	@Override
	protected boolean isResourcePropertyVisible() {
		return false;
	}
	
	@Override
	protected boolean isNodeTypePropertyVisible() {
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		super.refresh();
		this.timeoutEnable.setEnabled(false);
		this.timeoutEnable.setSelection(true);
		
		EObject userObject = (EObject) fTSENode.getUserObject();
		EObjectWrapper<EClass, EObject> taskWrapper = EObjectWrapper
				.wrap(userObject);
		List inEdges = getNode().inEdges();
		List outEdges = getNode().outEdges();
		@SuppressWarnings("unused")
		boolean enableTriggerByEvent = true;
		if(inEdges.size() > 0 || outEdges.size() > 0){
			enableTriggerByEvent = false;
		}else{
			Collection<EObject> flowNodes = BpmnModelUtils.getFlowNodes(taskWrapper, BpmnModelClass.START_EVENT);
			if(flowNodes.size()> 1)
				enableTriggerByEvent = false;
			else if(flowNodes.size() == 1){
				for (EObject object : flowNodes) {
					EObjectWrapper<EClass, EObject> useInstance = EObjectWrapper.wrap(object);
					EList<EObject> listAttribute = useInstance.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
					if(listAttribute.size() == 0)
						enableTriggerByEvent = false;
				}
			}
		}
//		triggerByEventButton.setEnabled(enableTriggerByEvent);
//		if (enableTriggerByEvent) {
			boolean triggerByEvent =
				(Boolean) taskWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT);
			triggerByEventButton.setSelection(triggerByEvent);
//		}
//		else {
//			triggerByEventButton.setSelection(false);
//		}
	}
	
	private class WidgetListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void widgetSelected(SelectionEvent e) {
			Object object = e.getSource();
			Map<String, Object> updateList = new HashMap<String, Object>();
			if( object == triggerByEventButton) {
				boolean optionTrigByEvent = triggerByEventButton.getSelection();
				EObject task = (EObject)fTSENode.getUserObject();
				EObjectWrapper<EClass, EObject> taskWrapper = EObjectWrapper.wrap(task);
				boolean triggeredByEvent = (Boolean) taskWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT);
				if (optionTrigByEvent != triggeredByEvent) {
					updateList.put(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT, optionTrigByEvent);						
				}
			}
			updatePropertySection(updateList);
		}
		
	}

}