package com.tibco.cep.bpmn.runtime.activity.events;


import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.functions.xpath.JXPathHelper;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.InitContext;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.TriggerIdentifier;
import com.tibco.cep.bpmn.runtime.activity.TriggerType;
import com.tibco.cep.bpmn.runtime.activity.Triggerable;
import com.tibco.cep.bpmn.runtime.activity.results.OKResult;
import com.tibco.cep.bpmn.runtime.activity.tasks.AbstractTask;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.agent.ProcessAgent;
import com.tibco.cep.bpmn.runtime.agent.ProcessRuleSession;
import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.service.om.ObjectBasedStore;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.xml.datamodel.XiNode;

/**
 * @author pdhar
 *
 */
public abstract class AbstractTriggerEvent extends AbstractTask implements Triggerable{



    protected String resourceUri;
    private EObjectWrapper<EClass, EObject> eventWrapper;
    protected com.tibco.cep.designtime.model.event.Event event;
    private Class<Event> runtimeEventClass;
    protected String conditionExpression;
    protected TriggerType triggerType;
    protected int priority = 5;
    protected String keyFilter;

    public AbstractTriggerEvent() {
        super();
    }

    @Override
    public void init(InitContext context, Object... args) throws Exception {
        super.init(context, args);

        eventWrapper = EObjectWrapper.wrap(getTaskModel().getEInstance());
        EObjectWrapper<EClass, EObject> valueWrapper =	ExtensionHelper.getAddDataExtensionValueWrapper(eventWrapper);
        resourceUri = (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT);
        if(resourceUri != null && !resourceUri.isEmpty()) { // null in case of start event none
        	Ontology ontology = context.getProcessModel().getOntology();
            this.event =  ontology.getEvent(resourceUri);
            this.runtimeEventClass = getEventClass(resourceUri);
        }
        
        // get Event Definition
        
        
        if(eventWrapper.isInstanceOf(BpmnModelClass.RECEIVE_TASK)){
        	// key expression is only available for a receive event task which waits for a 
        	// Job key @extid
        	String xpathExpr= (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOB_KEY);
        	if (xpathExpr != null && !xpathExpr.trim().isEmpty()) {
    			XiNode xpathNode = XSTemplateSerializer
    					.deSerializeXPathString(xpathExpr);
    			this.keyFilter = XSTemplateSerializer.getXPathExpressionAsStringValue(xpathNode);
    		}else{
    			this.keyFilter = "";
    		}
        } else {
        	Object val = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PRIORITY);
            if(val != null) {
            	priority = (Integer) val;
            }
        	List<ROEObjectWrapper<EClass, EObject>> eventDefWrappers = eventWrapper.getWrappedEObjectList(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
            if(eventDefWrappers.size() == 0) {
                this.triggerType = TriggerType.NONE;
            } else if(eventDefWrappers.size() == 1) {
                ROEObjectWrapper<EClass, EObject> eventDefWrapper = eventDefWrappers.get(0);
                if(eventDefWrapper.isInstanceOf(BpmnModelClass.MESSAGE_EVENT_DEFINITION)){
                    this.triggerType =  TriggerType.MESSAGE;
                } else if(eventDefWrapper.isInstanceOf(BpmnModelClass.TIMER_EVENT_DEFINITION)){
                    this.triggerType =  TriggerType.TIMER;
                } else if(eventDefWrapper.isInstanceOf(BpmnModelClass.SIGNAL_EVENT_DEFINITION)){
                    this.triggerType =  TriggerType.SIGNAL;
                } else if(eventDefWrapper.isInstanceOf(BpmnModelClass.CONDITIONAL_EVENT_DEFINITION)){
                    this.triggerType =  TriggerType.CONDITIONAL;
                    this.conditionExpression = eventDefWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_CONDITION);
                }

            } else {
                // event can have multiple event definitions
                this.triggerType =  TriggerType.MULTIPLE;
            }
        }

    }


    @Override
    protected void initInputTransforms() {
        // There are no input transforms
    }

    protected void initOutputTransforms() {
    	List<?> dataOutputAssocs = null;
        
    	if(getTaskModel().containsAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATION))
    		dataOutputAssocs = getTaskModel().getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATION);
    	else if(getTaskModel().containsAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS))
    		dataOutputAssocs = getTaskModel().getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS);
    	
        if (dataOutputAssocs != null && !dataOutputAssocs.isEmpty()) {
            ROEObjectWrapper doAssocWrap = ROEObjectWrapper.wrap((EObject) dataOutputAssocs.get(0));

            EObject transform = (EObject) doAssocWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
            ROEObjectWrapper transformWrap = ROEObjectWrapper.wrap(transform);
            outputXslt = (String) transformWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
        }
    }



    /* (non-Javadoc)
      * @see com.tibco.cep.bpmn.runtime.activity.Task#execute(com.tibco.cep.bpmn.runtime.agent.Job)
      */
    @Override
    public TaskResult execute(Job context, Variables vars, Task loopTask) {

        return new OKResult(context.getJobContext());

    }


    protected String getCondition() {
        if(this.triggerType == TriggerType.CONDITIONAL) {
            // This expression is a XPath expression string from the model
            return this.conditionExpression;
        }
        return null;
    }


    private Class<Event> getEventClass(String resourceUri) {

        RuleServiceProvider rsp = getInitContext().getProcessAgent().getRuleServiceProvider();
        TypeManager tm = rsp.getTypeManager();
        if(resourceUri != null) {
            // Timer Start Events may not have an associated BE Time Event because
            // BPMN timers are different from BE timers. This is not decided yet: TODO

            TypeDescriptor td = tm.getTypeDescriptor(resourceUri);
            return td.getImplClass();

        }
        return null;
    }


    protected boolean isTimer() {
        return false;
    }

    @Override
    public int getPriority() {
        return priority; 
    }

    @Override
    public TriggerIdentifier[] getIdentifiers() {
        //TODO once the model can take multiple - then we can have just run with the model.
    	if(getInputEvent() != null) {
    		return new TriggerIdentifier[] { new EventIdentifierImpl(resourceUri, getInputEvent().getName())};
    	}
    	// for Event Def type NONE
    	return new TriggerIdentifier[0];
    }

    @Override
    public TriggerType getTriggerType() {
        return this.triggerType;
    }



    public com.tibco.cep.designtime.model.event.Event getInputEvent() {
		return event;
	}

    protected Class<Event> getRuntimeEventClass() {
        return runtimeEventClass;
    }

    public com.tibco.cep.designtime.model.event.Event getOutputEvent() {
        return null;
    }

    class EventIdentifierImpl implements TriggerIdentifier
    {

        String name;
        Class<Event> eventClass;
        private EventIdentifierImpl(String resourceUri, String name)
        {
            eventClass = AbstractTriggerEvent.this.getEventClass(resourceUri);
            this.name = name;
        }

        @Override
        public Class<? extends Entity> getType() {
            return eventClass;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    @Override
    public String getJobKeyExpression() {
        return this.keyFilter;
    }

    @Override
    public boolean eval(Variables variables) {
        return true;
    }
    
    public String getJobKeyFromEvent(Event event) {
    	ProcessAgent pac = getInitContext().getProcessAgent();
        ProcessRuleSession processRuleSession = (ProcessRuleSession) pac.getRuleSession();
        LockManager lockManager = ((ObjectBasedStore) processRuleSession.getObjectManager()).getLockManager();


        //Event event = (Event) vars.getVariable(getInputEvent().getName());

        String extId = JXPathHelper.evalXPathAsString(getJobKeyExpression(),
                new String[]{"globalVariables", getInputEvent().getName()},
                new Object[]{processRuleSession.getRuleServiceProvider().getGlobalVariables(), event});

        if (logger.isEnabledFor(LEVEL)) {
            logger.log(LEVEL, "Looking up process job " + getClass().getSimpleName()
                   + " for Event: " + event + " using key: " + extId);
        }
        
        return extId;
    }
}
