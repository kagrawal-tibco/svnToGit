package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeListener;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.SubProcessNodeUIFactory;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * @author majha
 *
 */
public class SubProcessGeneralPropertiesUpdateCommand extends TaskGeneralPropertiesUpdateCommand {

	private static final long serialVersionUID = -439168385571854781L;

	public SubProcessGeneralPropertiesUpdateCommand(int type,ModelController controller,EClass modelType, ENamedElement extType,TSENode node, Map<String, Object> updateList) {
		super(type,controller,modelType,extType,node, updateList);
	}	
	
	@Override
	protected void doAction() throws Throwable {
		// TODO Auto-generated method stub
		if(updateList.containsKey(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT)){
			boolean optionTrigByEvent = (Boolean)updateList.get(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT);
			TSENode fTSENode = getNodeOrGraph();
			// update UI
			if (fTSENode != null) {
				changeNodeUI(optionTrigByEvent);
				oldProperties.put(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT,elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT));
			}
		}
		super.doAction();
		
		
		if (elementWrapper != null)
			getModelController().getModelChangeAdapterFactory().adapt(
					elementWrapper, ModelChangeListener.class);
	}
	
	@Override
	protected Map<String, Object> updateModel(Map<String, Object> model) {
		if(model.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT)){
			String expression = (String)updateList.get(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT);
			EObjectWrapper<EClass, EObject> createInputDataAssociation = getModelController()
					.createInputDataAssociation(null, null,
							expression);
			List<EObject> associations = new ArrayList<EObject>();
			associations.add(createInputDataAssociation.getEInstance());
			elementWrapper
			.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS,
					associations);
		}
		
		if(model.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_MAP_XSLT)){
			String expression = (String)updateList.get(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_MAP_XSLT);
			EObjectWrapper<EClass, EObject> createOutputDataAssociation = getModelController()
					.createOutputDataAssociation(null, null,
							expression);
			List<EObject> associations = new ArrayList<EObject>();
			associations.add(createOutputDataAssociation.getEInstance());
			elementWrapper
			.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS,
					associations);
		}
		
		
		return super.updateModel(model);
	}
	
	@Override
	protected void undoAction() throws Throwable {
		super.undoAction();
		if(oldProperties.containsKey(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT)){
			changeNodeUI((Boolean)oldProperties.get(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT));
		}
		if(elementWrapper != null)
			getModelController().getModelChangeAdapterFactory().adapt(elementWrapper, ModelChangeListener.class);
	}
	
	@Override
	protected void redoAction() throws Throwable {
		doAction();
	}
	
	private void changeNodeUI(boolean trigByEvent){
		TSENode fTSENode = getNodeOrGraph();
		String toolId = (String)ExtensionHelper.getExtensionAttributeValue(elementWrapper, BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID);
		String resUrl = (String)getNodeOrGraph().getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
		EClass type = elementWrapper.getEClassType();
		SubProcessNodeUIFactory uiFactory = (SubProcessNodeUIFactory)BpmnGraphUIFactory.getInstance(null)
			.getNodeUIFactory((String)elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME),resUrl,
					toolId,
					type);
		uiFactory.setTrigByevent(trigByEvent,fTSENode);
	}
}
