package com.tibco.cep.runtime.service.tester.model;

import java.util.List;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.service.tester.core.CustomConceptSerializer;
import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Sep 20, 2010
 * Time: 3:46:44 PM
 * Decorator for {@link ReteObject} to handle modifications
 * @see ReteChangeType#MODIFY
 */
public class ModifiedReteObject extends ReteObject {

    private List<Property> modifiedProperties;

    private CustomConceptSerializer serializer;
    
    private ReteObject wrappedObject; // TODO : is this re-declaration needed?

    /**
     * @param wrappedObject
     * @param modifiedProperties
     */
    public ModifiedReteObject(ReteObject wrappedObject,
                              List<Property> modifiedProperties) {
        super(wrappedObject.getWrappedObject(), wrappedObject.getReteChangeType());

        this.wrappedObject = wrappedObject;

        this.modifiedProperties = modifiedProperties;
    }


    /**
     * Subclasses may override this
     *
     * @param reteObjectNode
     * @throws Exception
     */
    @Override
    protected void serializeWrappedObject(XiNode reteObjectNode) throws Exception {
        ReteChangeType reteChangeType = wrappedObject.getReteChangeType();
        if (wrappedObject.getWrappedObject() instanceof Concept) {
            Concept concept = (Concept)wrappedObject.getWrappedObject();
            serializer = new CustomConceptSerializer(concept, reteChangeType, modifiedProperties);
            serializer.serialize(reteObjectNode);
        }
    }

    /**
     * @return the invocationObject
     */
    @Override
    public InvocationObject getInvocationObject() {
        return wrappedObject.getInvocationObject();
    }
    
    public void updateInitialValues(ReteObject initialObject) {
    	if (serializer != null) {
    		serializer.updateInitialValues(initialObject);
    	}
    }
    
    public List<Property> getModifiedProperties() {
		return modifiedProperties;
	}

}
