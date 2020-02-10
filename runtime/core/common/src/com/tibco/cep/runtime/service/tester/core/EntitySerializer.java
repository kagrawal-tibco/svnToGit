package com.tibco.cep.runtime.service.tester.core;

import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_EXT_ID;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_ID;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_NAME;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_NAMESPACE;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArrayConceptReference;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtomBoolean;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtomDateTime;
import com.tibco.cep.runtime.model.element.PropertyAtomDouble;
import com.tibco.cep.runtime.model.element.PropertyAtomInt;
import com.tibco.cep.runtime.model.element.PropertyAtomLong;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: May 4, 2010
 * Time: 6:48:42 PM
 * <!--
 * Add Description of the class here
 * -->
 */
public abstract class EntitySerializer<E extends Entity> {

    protected E entity;

    protected XiFactory factory = XiSupport.getXiFactory();

    protected EntitySerializer(E entity) {
        this.entity = entity;
    }

    public abstract void serialize(XiNode documentNode) throws Exception;

    protected void serializeBaseAttributes(XiNode rootNode) {
        String extId = entity.getExtId();
        if (extId != null) {
            rootNode.setAttributeStringValue(EX_EXT_ID, extId);
        }

        long id = entity.getId();
        rootNode.setAttributeStringValue(EX_ID, Long.toString(id));
    }

    protected void serializeNS(XiNode node, String name, String namespace) {
        node.setAttributeStringValue(EX_NAMESPACE, namespace);
        node.setAttributeStringValue(EX_NAME, name);
    }

    /**
     * Serialize data type of the property
     * @param property
     * @return a string value
     */
    protected String getStringDataType(Property property) {
        if (property instanceof PropertyAtomBoolean) {
            return "BOOLEAN";
        } else if (property instanceof PropertyAtomInt) {
            return "INTEGER";
        } else if (property instanceof PropertyAtomLong) {
            return "LONG";
        } else if (property instanceof PropertyAtomDateTime) {
            return "DATETIME";
        } else if (property instanceof PropertyAtomDouble) {
            return "DOUBLE";
        } else if (property instanceof PropertyAtomContainedConcept
                || property instanceof PropertyArrayContainedConcept) {
            return "CONTAINED_CONCEPT";
        } else if (property instanceof PropertyAtomContainedConcept
                || property instanceof PropertyArrayConceptReference) {
            return "CONCEPT_REFERENCE";
        }
        return "STRING";
    }
}
