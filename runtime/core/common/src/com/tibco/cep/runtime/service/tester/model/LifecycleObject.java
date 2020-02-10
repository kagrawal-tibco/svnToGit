package com.tibco.cep.runtime.service.tester.model;

import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkEntry;
import com.tibco.cep.runtime.service.tester.core.CustomEventSerializer;
import com.tibco.cep.runtime.service.tester.core.CustomTimeEventSerializer;
import com.tibco.cep.runtime.service.tester.core.EntitySerializer;
import com.tibco.cep.runtime.service.tester.core.TesterConstants;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

public class LifecycleObject extends TesterObject {
	
	LifecycleEventType eventType;

	public LifecycleObject(InvocationObject invocationObject, Object executionObject, LifecycleEventType eventType) {
		super(invocationObject, executionObject);
		this.eventType = eventType;
	}

	public LifecycleEventType getLifecycleEventType() {
		return eventType;
	}

	@Override
	public void serialize(XiFactory factory, XiNode rootNode) throws Exception {
    	if (rootNode != null && tempRoot != null) {
    		rootNode.appendChild(tempRoot.getFirstChild());
    		return;
    	}
    	if (rootNode == null) {
    		tempRoot = factory.createDocument();
    		rootNode = tempRoot;
    	}
        XiNode executionObjectNode = factory.createElement(TesterConstants.EX_EXECUTION_OBJECT);

        XiNode lifecycleNode = factory.createElement(TesterConstants.EX_LIFECYCLE_OBJECT);

        lifecycleNode.setAttributeStringValue(TesterConstants.EX_TIMESTAMP, getTimestamp());
        
        if (eventType != null) {
            //Serialize change type
            XiNode lifecycleEventTypeObject = factory.createElement(TesterConstants.EX_LIFECYCLE_EVENT_TYPE);
            lifecycleEventTypeObject.setStringValue(eventType.toString());

            lifecycleNode.appendChild(lifecycleEventTypeObject);
        }

        serializeWrappedObject(lifecycleNode);
        executionObjectNode.appendChild(lifecycleNode);
        rootNode.appendChild(executionObjectNode);

        if (invocationObject != null) {
            //serialize it
            invocationObject.serialize(factory, executionObjectNode);
        }
    }
	
    protected void serializeWrappedObject(XiNode lifecycleNode) throws Exception {
        //Serialize wrapped object
        if (wrappedObject instanceof WorkEntry) {
        	WorkEntry entry = (WorkEntry) wrappedObject;
//            EntitySerializer<Concept> serializer = new CustomConceptSerializer(concept, reteChangeType);
//            serializer.serialize(reteObjectNode);
        } else if (wrappedObject instanceof SimpleEvent) {
            SimpleEvent event = (SimpleEvent)wrappedObject;
            EntitySerializer<SimpleEvent> serializer = new CustomEventSerializer(event);
            serializer.serialize(lifecycleNode);
        } else if (wrappedObject instanceof TimeEvent) {
        	TimeEvent event = (TimeEvent)wrappedObject;
            EntitySerializer<TimeEvent> serializer = new CustomTimeEventSerializer(event);
            serializer.serialize(lifecycleNode);
        }
    }
}
