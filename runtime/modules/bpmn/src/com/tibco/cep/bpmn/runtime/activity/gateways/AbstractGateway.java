package com.tibco.cep.bpmn.runtime.activity.gateways;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.Gateway;
import com.tibco.cep.bpmn.runtime.activity.InitContext;
import com.tibco.cep.bpmn.runtime.activity.Transition;
import com.tibco.cep.bpmn.runtime.activity.tasks.AbstractTask;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.xml.datamodel.XiNode;

/*
* Author: Suresh Subramani / Date: 11/20/11 / Time: 6:39 PM
*/
public abstract class AbstractGateway extends AbstractTask implements Gateway {
	
	protected int direction;
	protected boolean forkCopy;
	protected boolean joinCopy;	
	protected String forkFunctionURI;
	protected String joinFunctionURI;
    private String joinExpr;


    @Override
    public void init(InitContext context, Object... args) throws Exception {
        super.init(context, args);
    }
    
    @Override
    public int getDirection() {
    	return direction;
    }
    
    @Override
    //Override the one from AbstractTask.
    protected void initTransformations() throws Exception {
    	initDirection();
    	initMappingOption();
    	EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(getTaskModel().getEInstance());
		EObjectWrapper<EClass, EObject> valueWrapper =	ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
    	if(isMixed() || isDiverging()) {
    		EEnumLiteral mappingType = valueWrapper.getEnumAttribute(BpmnMetaModelExtensionConstants.E_ATTR_FORK_MAPPING_OPTION);
//    		forkCopy = mappingType.equals(BpmnModelClass.ENUM_MAPPING_OPTION_COPY);
    		initForkTransforms();
    	}
    	if(isMixed() || isConverging()) {
    		EEnumLiteral mappingType = valueWrapper.getEnumAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOIN_MAPPING_OPTION);
//    		joinCopy = mappingType.equals(BpmnModelClass.ENUM_MAPPING_OPTION_COPY);
    		initJoinTransforms();
    	}
    }
    
    private void initMappingOption() {
    	// For Mappers
		
	}
    

	@Override
    public boolean isConverging() {
    	return (direction & CONVERGING) == CONVERGING;
    }
    
    @Override
    public boolean isDiverging() {
    	return (direction & DIVERGING) == DIVERGING;
    }
    
    @Override
    public boolean isMixed() {
    	return (direction & MIXED) == MIXED;
    }

	private void initDirection() {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(getTaskModel().getEInstance());
    	EEnumLiteral dir = flowNodeWrapper.getEnumAttribute(BpmnMetaModelConstants.E_ATTR_GATEWAY_DIRECTION);
    	if(dir.equals(BpmnModelClass.ENUM_GATEWAY_DIRECTION_CONVERGING)) {
    		this.direction |= CONVERGING;
    	}
    	if(dir.equals(BpmnModelClass.ENUM_GATEWAY_DIRECTION_DIVERGING)) {
    		this.direction |= DIVERGING;
    	}
    	if(dir.equals(BpmnModelClass.ENUM_GATEWAY_DIRECTION_MIXED)) {
    		this.direction |= MIXED;
    	}
	}

    abstract protected void initJoinTransforms() throws Exception;


	abstract protected void initForkTransforms() throws Exception;


    @Override
    public boolean isEventBased() {
        return false;
    }
    



    @Override
    public short getActivationCount() {
        return (short)this.incomingTransitions.length;
    }

    @Override
    public short getOutputTokenCount() {
        return (short)this.outgoingTranisitions.length;
    }
    
    



    @Override
    public String getJoinExpression() {

        if(joinExpr == null) {
    		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(getTaskModel().getEInstance());
    		EObjectWrapper<EClass, EObject> valueWrapper =	ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
    		String xpathExpr = valueWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_MERGE_EXPRESSION);
    		if (xpathExpr != null && !xpathExpr.trim().isEmpty()) {
				try {
					XiNode xpathNode = XSTemplateSerializer
							.deSerializeXPathString(xpathExpr);
					joinExpr = XSTemplateSerializer.getXPathExpressionAsStringValue(xpathNode);
				} catch (Exception e) {
					String id = flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
					logger.log(Level.ERROR, "Exception processing the merge Key expression for "+ id, e);
					joinExpr = "";
				}
    			
    		}else{
    			joinExpr = "";
    		}
    	}
    	return joinExpr;

    }

    protected short findIncomingTransitionId(String transitionName) throws Exception {
        if (null == transitionName) throw new Exception("findIncomingTransitionId: null transitionName specified - check the model.");
        for (int i=0; i<incomingTransitions.length; i++)
        {
            Transition transition = incomingTransitions[i];
            if (transition.getName().equals(transitionName)) return (short)i;
        }

        throw new Exception(String.format("findIncomingTransitionId:Invalid transition name specified : %s", transitionName));
    }
}
