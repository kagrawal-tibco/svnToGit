package com.tibco.cep.runtime.service.tester.model;

import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_CHANGE_TYPE;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_EXECUTION_OBJECT;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_RETE_OBJECT;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_TIMESTAMP;

import java.util.Date;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.service.tester.core.CustomConceptSerializer;
import com.tibco.cep.runtime.service.tester.core.CustomEventSerializer;
import com.tibco.cep.runtime.service.tester.core.CustomRuleSerializer;
import com.tibco.cep.runtime.service.tester.core.CustomTimeEventSerializer;
import com.tibco.cep.runtime.service.tester.core.EntitySerializer;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;



/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: May 1, 2010
 * Time: 12:27:30 PM
 * <!--
 * Add Description of the class here
 * -->
 */
public class ReteObject extends TesterObject {

    private ReteChangeType reteChangeType;

	public ReteObject() {

    }

    public ReteObject(Object wrappedObject) {
        this(wrappedObject, null);
    }

    public ReteObject(Object wrappedObject,
                      ReteChangeType reteChangeType) {
        this(null, wrappedObject, reteChangeType);
    }

    /**
     * @param invocationObject  -> The invocation object
     * @param wrappedObject
     * @param reteChangeType
     */
    public ReteObject(InvocationObject invocationObject,
                      Object wrappedObject,
                      ReteChangeType reteChangeType) {
    	super(invocationObject, wrappedObject);
        this.reteChangeType = reteChangeType;
    }

    public ReteChangeType getReteChangeType() {
        return reteChangeType;
    }

    public void setReteChangeType(ReteChangeType reteChangeType) {
        this.reteChangeType = reteChangeType;
    }

    /**
     * The id of this object
     *
     * @return
     */
    public long getId() {
        if (wrappedObject instanceof Entity) {
            return ((Entity) wrappedObject).getId();
        }
        return -1;
    }

    /**
     *
     */
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ReteObject)) {
            return false;
        }
        ReteObject other = (ReteObject) object;
        return getId() == other.getId();
    }


    /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {
        return (int) getId();
    }

    /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(wrappedObject);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }



    public class CausalObject extends ReteObject {

        /**
         * @param wrappedObject
         * @param reteChangeType
         */
        public CausalObject(Object wrappedObject, ReteChangeType reteChangeType) {
            super(wrappedObject, reteChangeType);
        }

        @Override
        public void serialize(XiFactory factory, XiNode causalObjectsNode) throws Exception {
            //Serialize wrapped object
            if (wrappedObject instanceof Concept) {
                Concept concept = (Concept)wrappedObject;
                EntitySerializer<Concept> serializer = new CustomConceptSerializer(concept, reteChangeType);
                serializer.serialize(causalObjectsNode);
            } else if (wrappedObject instanceof SimpleEvent) {
                SimpleEvent event = (SimpleEvent)wrappedObject;
                EntitySerializer<SimpleEvent> serializer = new CustomEventSerializer(event);
                serializer.serialize(causalObjectsNode);
            } else if (wrappedObject instanceof TimeEvent) {
            	TimeEvent event = (TimeEvent)wrappedObject;
                 EntitySerializer<TimeEvent> serializer = new CustomTimeEventSerializer(event);
                 serializer.serialize(causalObjectsNode);
            } else if (wrappedObject instanceof Rule) {
            	Rule rule = (Rule)wrappedObject;
            	CustomRuleSerializer serializer = new CustomRuleSerializer(rule);
                serializer.serialize(causalObjectsNode);
           }
            	
        }
    }

    public void serialize(XiFactory factory, XiNode rootNode) throws Exception {
    	if (rootNode != null && tempRoot != null) {
    		rootNode.appendChild(tempRoot.getFirstChild());
    		return;
    	}
    	if (rootNode == null) {
    		tempRoot = factory.createDocument();
    		rootNode = tempRoot;
    	}
        XiNode executionObjectNode = factory.createElement(EX_EXECUTION_OBJECT);

        XiNode reteObjectNode = factory.createElement(EX_RETE_OBJECT);

        reteObjectNode.setAttributeStringValue(EX_TIMESTAMP, getTimestamp());
        
        if (reteChangeType != null) {
            //Serialize change type
            XiNode changeTypeObject = factory.createElement(EX_CHANGE_TYPE);
            changeTypeObject.setStringValue(reteChangeType.toString());

            reteObjectNode.appendChild(changeTypeObject);
        }

        serializeWrappedObject(reteObjectNode);
        executionObjectNode.appendChild(reteObjectNode);
        rootNode.appendChild(executionObjectNode);

        InvocationObject invocationObject = getInvocationObject();
        if (invocationObject != null) {
            //serialize it
            invocationObject.serialize(factory, executionObjectNode);
        }
    }

    /**
     * Subclasses may override this
     * @param reteObjectNode
     * @throws Exception
     */
    protected void serializeWrappedObject(XiNode reteObjectNode) throws Exception {
        //Serialize wrapped object
        if (wrappedObject instanceof Concept) {
            Concept concept = (Concept)wrappedObject;
            EntitySerializer<Concept> serializer = new CustomConceptSerializer(concept, reteChangeType);
            serializer.serialize(reteObjectNode);
        } else if (wrappedObject instanceof SimpleEvent) {
            SimpleEvent event = (SimpleEvent)wrappedObject;
            EntitySerializer<SimpleEvent> serializer = new CustomEventSerializer(event);
            serializer.serialize(reteObjectNode);
        } else if (wrappedObject instanceof TimeEvent) {
        	TimeEvent event = (TimeEvent)wrappedObject;
            EntitySerializer<TimeEvent> serializer = new CustomTimeEventSerializer(event);
            serializer.serialize(reteObjectNode);
        } else if (wrappedObject instanceof Rule) {
        	Rule rule = (Rule)wrappedObject;
        	CustomRuleSerializer serializer = new CustomRuleSerializer(rule);
            serializer.serialize(reteObjectNode);
       }
    }
}
