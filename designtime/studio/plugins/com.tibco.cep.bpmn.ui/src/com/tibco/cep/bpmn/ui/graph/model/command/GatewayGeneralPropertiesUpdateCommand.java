package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCoreConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeListener;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * @author majha
 *
 */
public class GatewayGeneralPropertiesUpdateCommand extends NodeGeneralPropertiesUpdateCommand {

	private static final long serialVersionUID = -439168385571854781L;

	public GatewayGeneralPropertiesUpdateCommand(int type,ModelController controller,EClass modelType, ENamedElement extType,TSENode node, Map<String, Object> updateList) {
		super(type,controller,modelType,extType,node, updateList);
	}	
	
	@Override
	protected void doAction() throws Throwable {
		// TODO Auto-generated method stub
		super.doAction();
		if (elementWrapper != null)
			getModelController().getModelChangeAdapterFactory().adapt(
					elementWrapper, ModelChangeListener.class);
	}
	
	@Override
	protected void undoAction() throws Throwable {
		super.undoAction();
		if(elementWrapper != null)
			getModelController().getModelChangeAdapterFactory().adapt(elementWrapper, ModelChangeListener.class);
	}
	
	@Override
	protected void redoAction() throws Throwable {
		doAction();
	}
	
	protected void processForAttachedResource() {
		if (updateList.containsKey(BpmnMetaModelConstants.E_ATTR_ACTIVATION_CONDITION)) {
			String body = (String) updateList.remove(BpmnMetaModelConstants.E_ATTR_ACTIVATION_CONDITION);
			EObjectWrapper<EClass, EObject> formalExpression = getModelController().createFormalExpression(body, "boolean", BpmnCoreConstants.BPMN_EXPRESSION_LANGUAGE_XPATH);
			updateList.put(BpmnMetaModelConstants.E_ATTR_ACTIVATION_CONDITION, formalExpression.getEInstance());
		}
	}
}
