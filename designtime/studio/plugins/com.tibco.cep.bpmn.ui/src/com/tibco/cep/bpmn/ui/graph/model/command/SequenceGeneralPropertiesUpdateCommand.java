package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCoreConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSEEdge;

/**
 * 
 * @author majha
 *
 */
public class SequenceGeneralPropertiesUpdateCommand extends AbstractUpdateEdgeCommand implements
		IGraphCommand<TSEEdge> {


	private static final long serialVersionUID = 4511335520125851014L;
	private Map<String, Object> updateList;
	private Map<String, Object> oldProperties;
	protected EObjectWrapper<EClass, EObject> edgeWrapper;
	
	public SequenceGeneralPropertiesUpdateCommand(int type, 
								ModelController controller,
								EClass modelType, 
								TSEEdge edge,
								Map<String, Object> updateList) {
		super(type, controller, modelType,edge);
		this.updateList = updateList;
		oldProperties = new HashMap<String, Object>();
	}

	protected void doAction() throws Throwable {
		TSEEdge edge = getNodeOrGraph();
		EObject userObject = (EObject) edge.getUserObject();
		edgeWrapper = EObjectWrapper.wrap(userObject);
		String desc = (String) updateList.get(BpmnMetaModelConstants.E_ATTR_TEXT);
		if (desc != null) {
			EList<EObject> listAttribute = edgeWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
			if(listAttribute.size() == 0 ){
				getModelController().createDocumentation(edgeWrapper, desc);
			}
		}
		
		String conditionExpression = (String) updateList.get(BpmnMetaModelConstants.E_ATTR_BODY);
		if (conditionExpression != null) {
				EObjectWrapper<EClass, EObject> createFormalExpression = getModelController().createFormalExpression(conditionExpression, "boolean", BpmnCoreConstants.BPMN_EXPRESSION_LANGUAGE_XPATH);
				updateList.put(BpmnMetaModelConstants.E_ATTR_CONDITION_EXPRESSION, createFormalExpression.getEInstance());
			
		}

		oldProperties = updateModel(updateList);
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
		@SuppressWarnings("unused")
		String label = (String) model.get(BpmnMetaModelConstants.E_ATTR_NAME);
		String desc = (String) model.get(BpmnMetaModelConstants.E_ATTR_TEXT);
		
		if(name == null && model.containsKey(BpmnMetaModelConstants.E_ATTR_NAME))
			name = "";

//		if(label == null && model.containsKey(BpmnMetaModelConstants.E_ATTR_LABEL))
//			label = "";
		
		if(desc == null && model.containsKey(BpmnMetaModelConstants.E_ATTR_TEXT))
			desc = "";

		TSEEdge edge = getNodeOrGraph();

		if (name != null)
			edge.setAttribute(BpmnUIConstants.NODE_ATTR_NAME, name);

//		if (label != null)
//			edge.setAttribute(BpmnUIConstants.NODE_ATTR_LABEL, label);
		
		if (desc != null) {
			edge.setAttribute(BpmnUIConstants.NODE_ATTR_TASK_DESCRIPTION, desc);
			EList<EObject> listAttribute = edgeWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
			EObject docObj = listAttribute.get(0);
			props.putAll(getModelController().updateEmfModel(
						EObjectWrapper.wrap(docObj), model));
		}
//		String conditionExpression = (String) model.get(BpmnMetaModelConstants.E_ATTR_BODY);
//		if (conditionExpression != null) {
//			EObject expression = (EObject)edgeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_CONDITION_EXPRESSION);
//			EObjectWrapper<EClass, EObject> expressionWrapper = EObjectWrapper.wrap(expression);
//			props.putAll(getModelController().updateEmfModel(expressionWrapper,	model));
//		}

		props.putAll(getModelController().updateEmfModel(edgeWrapper, model));

		return props;
	}
	
	protected void finalize() throws Throwable {
		updateList.clear();
		updateList = null;
		oldProperties.clear();
		oldProperties = null;
		edgeWrapper = null;
		super.finalize();
	}
}
