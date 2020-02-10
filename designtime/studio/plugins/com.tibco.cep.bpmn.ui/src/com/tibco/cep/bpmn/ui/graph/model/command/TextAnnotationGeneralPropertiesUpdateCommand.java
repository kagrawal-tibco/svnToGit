package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeListener;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSENode;

public class TextAnnotationGeneralPropertiesUpdateCommand  extends AbstractUpdateNodeCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8583932427830206152L;
	protected Map<String, Object> updateList;

	protected EObjectWrapper<EClass, EObject> elementWrapper;
	protected Map<String, Object> oldProperties;
	
	public TextAnnotationGeneralPropertiesUpdateCommand(int type,ModelController controller,EClass modelType, ENamedElement extType,TSENode node, Map<String, Object> updateList) {
		super(type,controller,modelType,extType,node);
		this.updateList = updateList;
		oldProperties = new HashMap<String, Object>();
	}

	protected void doAction() throws Throwable {
		TSENode node = getNodeOrGraph();
		EObject userObject = (EObject) node.getUserObject();
		elementWrapper = EObjectWrapper.wrap(userObject);
		String desc = (String) updateList
				.get(BpmnMetaModelConstants.E_ATTR_TEXT);
		if (desc != null) {
			EList<EObject> listAttribute = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
			if(listAttribute.size() == 0 ){
				getModelController().createDocumentation(elementWrapper, desc);
			}
		}

		oldProperties = updateModel(updateList);
		if (elementWrapper != null)
			getModelController().getModelChangeAdapterFactory().adapt(
					elementWrapper, ModelChangeListener.class);
	}

	protected void undoAction() throws Throwable {
		updateModel(oldProperties);
		if (elementWrapper != null)
			getModelController().getModelChangeAdapterFactory().adapt(
					elementWrapper, ModelChangeListener.class);
	}
	
	@Override
	protected void redoAction() throws Throwable {
		updateModel(updateList);
	}
	
	protected Map<String, Object> updateModel(Map<String, Object> model) {
		Map<String, Object> props = new HashMap<String, Object>();
		String name = (String) model
				.get(BpmnMetaModelConstants.E_ATTR_LABEL);
		String desc = (String) model
				.get(BpmnMetaModelConstants.E_ATTR_TEXT);
		String content = (String) model
			.get(BpmnUIConstants.ATTR_ANOOTATION_TEXT);
		
		if(name == null && model.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_LABEL))
			name = "";
		
		if(desc == null && model.containsKey(BpmnMetaModelConstants.E_ATTR_TEXT))
			desc = "";
		
		if(content == null && model.containsKey(BpmnUIConstants.ATTR_ANOOTATION_TEXT))
			content = "";
		

		TSENode node = getNodeOrGraph();

		if (name != null)
			node.setAttribute(BpmnUIConstants.NODE_ATTR_NAME, name);

		if (desc != null) {
			node.setAttribute(BpmnUIConstants.NODE_ATTR_TASK_DESCRIPTION, desc);
			EList<EObject> listAttribute = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
			EObject docObj = listAttribute.get(0);
			props.putAll(getModelController().updateEmfModel(
						EObjectWrapper.wrap(docObj), model));
			model.remove(BpmnMetaModelConstants.E_ATTR_TEXT);
		}
		
		if(content != null){
			model.put(BpmnMetaModelConstants.E_ATTR_TEXT, content);
			Map<String, Object> updateEmfModel = getModelController().updateEmfModel(elementWrapper,
					model);
			Object remove = updateEmfModel.remove(BpmnMetaModelConstants.E_ATTR_TEXT);
			props.put(BpmnUIConstants.ATTR_ANOOTATION_TEXT, remove);
			props.putAll(updateEmfModel);
		}else{
			props.putAll(getModelController().updateEmfModel(elementWrapper,
					model));
		}

		return props;
	}
	
	protected void finalize() throws Throwable {
		elementWrapper = null;
		oldProperties.clear();
		oldProperties = null;
		updateList.clear();
		updateList = null;
		super.finalize();
	}

}
