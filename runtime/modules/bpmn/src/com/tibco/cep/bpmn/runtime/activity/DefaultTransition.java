package com.tibco.cep.bpmn.runtime.activity;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.functions.xpath.JXPathHelper;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.templates.MapperConstants;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.xml.datamodel.XiNode;

public class DefaultTransition implements Transition {

    private InitContext ctx;
    private String name;
    private boolean defaultTransiton = false;
    private Task fromTask, toTask;
    ROEObjectWrapper transitionModel;
    private String xpathExpr;
    private static Logger logger = LogManagerFactory.getLogManager().getLogger(Transition.class);

    public DefaultTransition() {
    }

    @Override
    public void init(InitContext ctx,  Object... args) throws Exception  {
        this.ctx = ctx;

        transitionModel = (ROEObjectWrapper) args[1];
        name = (String) transitionModel.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
        
        EObject sourceRef = (EObject) transitionModel.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF);
        ROEObjectWrapper<EClass, EObject> fromTaskWrapper = ROEObjectWrapper.wrap(sourceRef);
        String fromTaskId = (String)fromTaskWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
        this.fromTask = ctx.getProcessTemplate().getTask(fromTaskId);

        EObject exprObj = (EObject) transitionModel.getAttribute(BpmnMetaModelConstants.E_ATTR_CONDITION_EXPRESSION);
        if(exprObj != null) {
        	ROEObjectWrapper<EClass, EObject> xpWrapper = ROEObjectWrapper.wrap(exprObj);
        	xpathExpr = (String) xpWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
        }

        EObject targetRef = (EObject) transitionModel.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF);
        ROEObjectWrapper toTaskWrapper = ROEObjectWrapper.wrap(targetRef);
        String taskType = BpmnMetaModel.INSTANCE.getExpandedName(targetRef.eClass()).toString();
        String id = (String)toTaskWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);

        toTask = ctx.getProcessTemplate().getTask(id);
        ROEObjectWrapper<EClass, EObject> fromTaskModel = fromTask.getTaskModel();
        if(fromTaskModel.isInstanceOf(BpmnModelClass.INCLUSIVE_GATEWAY)||
        		fromTaskModel.isInstanceOf(BpmnModelClass.EXCLUSIVE_GATEWAY)||
        		fromTaskModel.isInstanceOf(BpmnModelClass.COMPLEX_GATEWAY)){
        	
        	EEnumLiteral dir = fromTaskModel.getEnumAttribute(BpmnMetaModelConstants.E_ATTR_GATEWAY_DIRECTION);
        	if(dir.equals(BpmnModelClass.ENUM_GATEWAY_DIRECTION_DIVERGING) || dir.equals(BpmnModelClass.ENUM_GATEWAY_DIRECTION_MIXED)) {
        		
        		EObject defaultTransitionObj = (EObject) fromTaskModel.getAttribute(BpmnMetaModelConstants.E_ATTR_DEFAULT);
	           	 if(defaultTransitionObj != null) {
	           		 ROEObjectWrapper<EClass, EObject> defaultTransitionWrapper = ROEObjectWrapper.wrap(defaultTransitionObj);
	           		 String defaultTransitionId = defaultTransitionWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
	               	 if(defaultTransitionId.equals(name)) {
	               		 defaultTransiton = true;
	               	 }
	           	 }
           	 
        	} 
        	 
        	
        }        


    }



    @Override
    public String getName() {
        return name;
    }


    @Override
    public boolean eval(Job context) {

        if (xpathExpr == null) return false;

        GlobalVariables gvars = ctx.getProcessAgent().getRuleServiceProvider().getGlobalVariables();
        JobContext job = context.getJobContext();
        try {
			XiNode xpathNode = XSTemplateSerializer.deSerializeXPathString(xpathExpr);
			String value = XSTemplateSerializer.getXPathExpressionAsStringValue(xpathNode);
			List varNames = XSTemplateSerializer.getVariablesinXPath(xpathNode);
			// TODO : where do we get the value of each variable?
			return JXPathHelper.evalXPathAsBoolean(value, new String[] {MapperConstants.JOB,MapperConstants.GLOBALVARIABLES}, new Object[] { job, gvars });
		} catch (Exception e) {
			throw new RuntimeException(String.format("Expression: %S \n cannot be evaluated at %s",xpathExpr,getName()),e);
		}
        
    }

    @Override
    public Task fromTask() {
        return fromTask;
    }

    @Override
    public Task toTask() {
        return toTask;

    }

    protected ROEObjectWrapper getTransitionModel() {
        return transitionModel;
    }

    protected String getXpathExpr() {
        return xpathExpr;
    }

    @Override
    public boolean isDefault() {
        return defaultTransiton; 
    }
}
