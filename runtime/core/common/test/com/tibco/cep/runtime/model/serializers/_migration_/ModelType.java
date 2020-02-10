package com.tibco.cep.runtime.model.serializers._migration_;

import com.tibco.cep.runtime.model.element.*;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.element.impl.DateTimeTupleImpl;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;

/*
* Author: Ashwin Jayaprakash Date: Jun 10, 2008 Time: 12:30:07 PM
*/
public enum ModelType {
    SIMPLE_EVENT(SimpleEventImpl.class),
    CONCEPT(ConceptImpl.class),

    PROPERTY_ARRAY_BOOLEAN(PropertyArrayBoolean.class, PropertyAtomBoolean[].class),
    PROPERTY_ARRAY_CONCEPT(PropertyArrayConcept.class, PropertyAtomConcept[].class),
    PROPERTY_ARRAY_CONCEPT_REFERENCE(PropertyArrayConceptReference.class,
            PropertyAtomConceptReference[].class),
    PROPERTY_ARRAY_CONTAINED_CONCEPT(PropertyArrayContainedConcept.class,
            PropertyAtomContainedConcept[].class),
    PROPERTY_ARRAY_DATETIME(PropertyArrayDateTime.class, PropertyAtomDateTime[].class),
    PROPERTY_ARRAY_DOUBLE(PropertyArrayDouble.class, PropertyAtomDouble[].class),
    PROPERTY_ARRAY_INT(PropertyArrayInt.class, PropertyAtomInt[].class),
    PROPERTY_ARRAY_LONG(PropertyArrayLong.class, PropertyAtomLong[].class),
    PROPERTY_ARRAY_STRING(PropertyArrayString.class, PropertyAtomString[].class),

    PROPERTY_ATOM_BOOLEAN(PropertyAtomBoolean.class, Boolean.class),
    PROPERTY_ATOM_CONCEPT(PropertyAtomConcept.class, ConceptOrReference.class),
    PROPERTY_ATOM_CONCEPT_REFERENCE(PropertyAtomConceptReference.class),
    PROPERTY_ATOM_CONTAINED_CONCEPT(PropertyAtomContainedConcept.class),
    PROPERTY_ATOM_DATETIME(PropertyAtomDateTime.class, DateTimeTupleImpl.class),
    PROPERTY_ATOM_DOUBLE(PropertyAtomDouble.class, Double.class),
    PROPERTY_ATOM_INT(PropertyAtomInt.class, Integer.class),
    PROPERTY_ATOM_LONG(PropertyAtomLong.class, Long.class),
    PROPERTY_ATOM_STRING(PropertyAtomString.class, String.class);

    //-----------

    Class containerDataType;

    Class internalDataType;

    ModelType(Class containerDataType) {
        this.containerDataType = containerDataType;
    }

    ModelType(Class containerDataType, Class internalDataType) {
        this.containerDataType = containerDataType;
        this.internalDataType = internalDataType;
    }

    public Class getContainerDataType() {
        return containerDataType;
    }

    public Class getInternalDataType() {
        return internalDataType;
    }
}