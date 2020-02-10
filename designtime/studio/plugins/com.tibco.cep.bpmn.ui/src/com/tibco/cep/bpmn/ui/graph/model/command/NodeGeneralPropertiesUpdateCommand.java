package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * @author majha
 *
 */
public abstract class NodeGeneralPropertiesUpdateCommand extends AbstractUpdateNodeCommand {
	private static final long serialVersionUID = 8861622707869666761L;
	protected Map<String, Object> updateList;

	protected EObjectWrapper<EClass, EObject> elementWrapper;
	protected Map<String, Object> oldProperties;

	
	public NodeGeneralPropertiesUpdateCommand(int type,ModelController controller,EClass modelType, ENamedElement extType,TSENode node, Map<String, Object> updateList) {
		super(type,controller,modelType,extType,node);
		this.updateList = updateList;
		oldProperties = new HashMap<String, Object>();
		EObject userObject = (EObject) node.getUserObject();
		elementWrapper = EObjectWrapper.wrap(userObject);
	}

	protected void doAction() throws Throwable {
		String desc = (String) updateList.get(BpmnMetaModelConstants.E_ATTR_TEXT);
		if (desc != null) {
			EList<EObject> listAttribute = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
			if(listAttribute.size() == 0 ){
				getModelController().createDocumentation(elementWrapper, desc);
			}
		}
		
		processForAttachedResource();
		oldProperties = updateModel(updateList);
	}

	protected void processForAttachedResource() {
		// TODO Auto-generated method stub
		
	}

	protected void undoAction() throws Throwable {
		updateModel(oldProperties);
	}
	
	@Override
	protected void redoAction() throws Throwable {
		updateModel(updateList);
	}
	
	protected Map<String, Object> updateModel(Map<String, Object> model) {
		Map<String, Object> props = new HashMap<String, Object>();
		String name = (String) model.get(BpmnMetaModelConstants.E_ATTR_ID);
		String label = (String) model.get(BpmnMetaModelConstants.E_ATTR_NAME);
		String desc = (String) model.get(BpmnMetaModelConstants.E_ATTR_TEXT);
		
		if(name == null && model.containsKey(BpmnMetaModelConstants.E_ATTR_NAME))
			name = "";
//
//		if(label == null && model.containsKey(BpmnMetaModelConstants.E_ATTR_LABEL))
//			label = "";
		
		if(desc == null && model.containsKey(BpmnMetaModelConstants.E_ATTR_TEXT))
			desc = "";

		TSENode node = getNodeOrGraph();

		if (name != null)
			node.setAttribute(BpmnUIConstants.NODE_ATTR_NAME, name);

		if (label != null) {
//			node.setAttribute(BpmnUIConstants.NODE_ATTR_LABEL, label);
		}

		if (desc != null) {
			node.setAttribute(BpmnUIConstants.NODE_ATTR_TASK_DESCRIPTION, desc);
			EList<EObject> listAttribute = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
			EObject docObj = listAttribute.get(0);
			props.putAll(getModelController().updateEmfModel(
						EObjectWrapper.wrap(docObj), model));
		}

		props.putAll(getModelController().updateEmfModel(elementWrapper, model));

		return props;
	}
	
	@Override
	protected void finalize() throws Throwable {
		oldProperties.clear();
		oldProperties = null;
		updateList.clear();
		updateList= null;
		elementWrapper = null;
		super.finalize();
	}
	
}
