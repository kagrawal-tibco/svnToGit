package com.tibco.cep.runtime.model.pojo.exim;

import java.util.Calendar;
import java.util.TimeZone;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArrayConcept;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomConcept;
import com.tibco.cep.runtime.model.element.PropertyException;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.element.impl.property.PropertyImpl;
import com.tibco.cep.runtime.model.element.impl.property.atomic.PropertyAtomAtomic;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomConceptReferenceImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomContainedConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomImpl;
import com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaProperty;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArraySimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomSimple;
import com.tibco.cep.runtime.model.serializers.FieldType;

/*
 * Author: Ashwin Jayaprakash / Date: 12/6/11 / Time: 3:23 PM
 * History actual length is computed as in PropertyAtomImpl
 */
public class ConceptExporter extends EximParent {
    
    protected final TypeManager typeManager;

    private static final Logger logger = LogManagerFactory.getLogManager().getLogger(ConceptExporter.class);

    public ConceptExporter(TypeManager typeManager) {
        this.typeManager = typeManager;
    }

    public void exportConcept(ConceptImpl source, PortablePojo destination) {
//        long id = source.getId();
//        String extId = source.getExtId();
//        int typeId = typeManager.getTypeDescriptor(source.getClass()).getTypeId();
        int version = source.getVersion();
        boolean deleted = source.isDeleted();

//        PortablePojo destination = eximHelper.getPojoManager().createPojo(id, extId, typeId);
        destination.setPropertyValue(PortablePojoConstants.PROPERTY_CONCEPT_NAME_DELETED, deleted, FieldType.BOOLEAN.toString());
        destination.setVersion(version);
//        destination.setEntityType(source.getType()); 

        // Add "@parent" as a meta property if this is a child of another concept
        ConceptOrReference parent = source.getParentReference();
        if (parent != null) {
            logger.log(Level.TRACE, "Concept with extid=[" + source.getExtId() + "] has parent with id=[" + parent.getId() + "]");
            destination.setPropertyValue(PortablePojoConstants.PROPERTY_CONCEPT_NAME_PARENT, parent.getId(), FieldType.LONG.toString());
        }

        ConceptImpl.RevRefItr rrItr = source.rrItr();
        Object[] revRefs = null;
        if (rrItr.numReverseRefs() > 0) {
        	revRefs = new Object[rrItr.numReverseRefs()*2];
            logger.log(Level.TRACE, "Concept with extid=[" + source.getExtId() + "] has [" + (revRefs.length / 2) + "] reverse references");
            for(int ii = 0; rrItr.next(); ii+=2) {
            	revRefs[ii] = rrItr.reverseRef().getId();
            	revRefs[ii+1] = rrItr.propName();
            }
        }
        destination.setPropertyValue(PortablePojoConstants.PROPERTY_CONCEPT_NAME_REV_REFERENCES, revRefs, FieldType.BLOB.toString());

        Property[] properties = source.getProperties();
        for (Property property : properties) {
            serializeProperty(destination, property);
        }

//        return destination;
    }

    private void serializeProperty(PortablePojo destination, Property source) {
        if (source instanceof PropertyImpl) {
            if (source instanceof PropertyAtomSimple || source instanceof PropertyAtomAtomic) {
                if (serializeAtom(destination, (PropertyAtom)source)) {
                    return;
                }
            }
            else if (source instanceof PropertyArraySimple) {
                if (serializeArray(destination, (PropertyArraySimple) source)) {
                    return;
                }
            }
            else if (source instanceof PropertyAtomImpl) {
                try {
                    if (serializeHistory(destination, (PropertyAtomImpl) source)) {
                        return;
                    }
                } catch (PropertyException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (source instanceof PropertyArrayImpl) {
                try {
                    if (serializeArrayHistory(destination, (PropertyArrayImpl) source)) {
                        return;
                    }
                } catch (PropertyException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        
        throw new UnsupportedOperationException(
                "Unable to handle serialization for source [" + source.getName() + "] of type" +
                        " [" + source.getClass().getName() + "]");
    }

    private boolean serializeAtom(PortablePojo destination, PropertyAtom source) {
        if (!source.isSet()) {
            return true;
        }

        String propertyName = source.getName();
        MetaProperty mp = ((PropertyImpl)source).getMetaProperty();

        switch(mp.getRDFTypeId()) {
            case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
                if (mp.isStateMachine()) {
                    propertyName = normalizeStateMachineName(propertyName);
                }
            case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
                long childId = ((PropertyAtomConcept) source).getConceptId();
                if (childId != PropertyAtomConcept.NULL) {
                    destination.setPropertyValue(propertyName, childId, FieldType.LONG.toString());
                }
                return true;

            case PropertyDefinition.PROPERTY_TYPE_DATETIME:
                if (separateTimezone) {
                    Calendar cal = (Calendar)source.getValue();
                    if (cal != null) {
                        TimeZone tz = cal.getTimeZone();
                        if (tz != null) {
                            String tz_id = tz.getID();
                            if (tz_id != null) {
                                destination.setPropertyValue(timeZoneName(propertyName), tz_id);
                            }
                        }
                    }
                }
            case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
            case PropertyDefinition.PROPERTY_TYPE_REAL:
            case PropertyDefinition.PROPERTY_TYPE_INTEGER:
            case PropertyDefinition.PROPERTY_TYPE_LONG:
            case PropertyDefinition.PROPERTY_TYPE_STRING:
                destination.setPropertyValue(propertyName, source.getValue(), RDFTypes.getTypeString(mp.getRDFTypeId()).toLowerCase());
                return true;

            default:
                return false;
        }
    }

    private boolean serializeArray(PortablePojo destination, PropertyArraySimple source) {
        String propertyName = source.getName();

        switch(((PropertyImpl)source).getMetaProperty().getRDFTypeId()) {
            case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
            case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
            {
                PropertyArrayConcept array = ((PropertyArrayConcept) source);
                int length = array.length();
                Object[] values = new Object[length];
                for (int i = length - 1; i >= 0; i--) {
                    PropertyAtomConcept p = (PropertyAtomConcept) array.get(i);
                    if (p.isSet()) {
//                        destination.setArrayPropertyValue(propertyName, length, p.getConceptId(), i);
                        values[i] = p.getConceptId();
                    }
                }
                destination.setPropertyValue(propertyName, values, FieldType.BLOB.toString());
                return true;
            }

            case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
            case PropertyDefinition.PROPERTY_TYPE_DATETIME:
            case PropertyDefinition.PROPERTY_TYPE_REAL:
            case PropertyDefinition.PROPERTY_TYPE_INTEGER:
            case PropertyDefinition.PROPERTY_TYPE_LONG:
            case PropertyDefinition.PROPERTY_TYPE_STRING:
            {
                int length = source.length();
                Object[] values = new Object[length];
                for (int i = length - 1; i >= 0; i--) {
                    PropertyAtom p = source.get(i);
                    if (p.isSet()) {
//                        destination.setArrayPropertyValue(propertyName, length, p.getValue(), i);
                        values[i] = p.getValue();
                    }
                }
                destination.setPropertyValue(propertyName, values, FieldType.BLOB.toString());
                return true;
            }

            default:
                return false;
        }
    }

    private boolean serializeHistory(PortablePojo destination, PropertyAtomImpl source) throws PropertyException {
        String propertyName = source.getName();
        
        switch(((PropertyImpl)source).getMetaProperty().getRDFTypeId()) {
            case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
            {
                PropertyAtomContainedConceptImpl history = ((PropertyAtomContainedConceptImpl) source);
                if (history.isSet()) {
	                int length = history.howMany(0,0);
	                Object[] values = new Object[length];
	                for (int i = length - 1; i >= 0; i--) {
	                    if (history.getConceptIdAtIdx(i) > 0) {
	                    	values[i] = new Object[] {history.getConceptIdAtIdx(i), history.getTimeAtIdx(i)};
//	                    	destination.setHistoryPropertyValue(propertyName, length, history.getConceptIdAtIdx(i), history.getTimeAtIdx(i), i);
	                    }
	                }
	                destination.setPropertyValue(propertyName, values, FieldType.BLOB.toString());
                }
                return true;
            }

            case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
            {
                PropertyAtomConceptReferenceImpl history = ((PropertyAtomConceptReferenceImpl) source);
                if (history.isSet()) {
	                int length = history.howMany(0,0);
	                Object[] values = new Object[length];
	                for (int i = length - 1; i >= 0; i--) {
	                    if (history.getConceptIdAtIdx(i) > 0) { 
	                    	values[i] = new Object[] {history.getConceptIdAtIdx(i), history.getTimeAtIdx(i)};
//	                    	destination.setHistoryPropertyValue(propertyName, length, history.getConceptIdAtIdx(i), history.getTimeAtIdx(i), i);
	                    }
	                }
	                destination.setPropertyValue(propertyName, values, FieldType.BLOB.toString());
                }
                return true;
            }

            case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
            case PropertyDefinition.PROPERTY_TYPE_DATETIME:
            case PropertyDefinition.PROPERTY_TYPE_REAL:
            case PropertyDefinition.PROPERTY_TYPE_INTEGER:
            case PropertyDefinition.PROPERTY_TYPE_LONG:
            case PropertyDefinition.PROPERTY_TYPE_STRING:
            {
                if (source.isSet()) {
                    int length = source.howMany(0,0);
                    Object[] values = new Object[length];
                    for (int i = length - 1; i >= 0; i--) {
                        Object value = source.getValueAtIdx(i);
                        if (value != null) {
                            long time = source.getTimeAtIdx(i);
                            values[i] = new Object[] {value, time};
//                            destination.setHistoryPropertyValue(propertyName, length, value, time, i);
                        }
                    }
                    destination.setPropertyValue(propertyName, values, FieldType.BLOB.toString());
                }
                return true;
            }

            default:
                return false;
        }
    }

    private boolean serializeArrayHistory(PortablePojo destination, PropertyArrayImpl source) throws PropertyException {
        String propertyName = source.getName();
        
        switch(((PropertyImpl)source).getMetaProperty().getRDFTypeId()) {
            case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
            {
                int length = source.length();
                Object[] values = new Object[length];
                for (int i = length - 1; i >= 0; i--) {
                    Object arrValue = source.get(i);
                    if (arrValue != null) {
                        PropertyAtomContainedConceptImpl history = ((PropertyAtomContainedConceptImpl) arrValue);
		                if (history.isSet()) {
	                        int size = history.howMany(0,0);
	                        Object[] histValues = new Object[size];
	                        long[] histTimes = new long[size];
	                        for (int j = size - 1; j >= 0; j--) {
	                            if (history.getConceptIdAtIdx(j) > 0) {
	                                histValues[j] = history.getConceptIdAtIdx(j);
	                                histTimes[j] = history.getTimeAtIdx(j);
	                            }
	                        }
	                        values[i] = new Object[] {histValues, histTimes};
//	                        destination.setArrayHistoryPropertyValue(propertyName, length, histValues, histTimes, i);
                        }
                    }
                }
                destination.setPropertyValue(propertyName, values, FieldType.BLOB.toString());
                return true;
            }

            case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
            {
                int length = source.length();
                Object[] values = new Object[length];
                for (int i = length - 1; i >= 0; i--) {
                    Object arrValue = source.get(i);
                    if (arrValue != null) {
                        PropertyAtomConceptReferenceImpl history = ((PropertyAtomConceptReferenceImpl) arrValue);
		                if (history.isSet()) {
	                        int size = history.howMany(0,0);
	                        Object[] histValues = new Object[size];
	                        long[] histTimes = new long[size];
	                        for (int j = size - 1; j >= 0; j--) {
	                            if (history.getConceptIdAtIdx(j) > 0) {
	                                histValues[j] = history.getConceptIdAtIdx(j);
	                                histTimes[j] = history.getTimeAtIdx(j);
	                            }
	                        }
	                        values[i] = new Object[] {histValues, histTimes};
//	                        destination.setArrayHistoryPropertyValue(propertyName, length, histValues, histTimes, i);
                        }
                    }
                }
                destination.setPropertyValue(propertyName, values, FieldType.BLOB.toString());
                return true;
            }

            case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
            case PropertyDefinition.PROPERTY_TYPE_DATETIME:
            case PropertyDefinition.PROPERTY_TYPE_REAL:
            case PropertyDefinition.PROPERTY_TYPE_INTEGER:
            case PropertyDefinition.PROPERTY_TYPE_LONG:
            case PropertyDefinition.PROPERTY_TYPE_STRING:
            {
                int length = source.length();
                Object[] values = new Object[length];
                for (int i = length - 1; i >= 0; i--) {
                    Object arrValue = source.get(i);
                    if (arrValue != null) {
                        PropertyAtomImpl history = (PropertyAtomImpl) arrValue;
		                if (history.isSet()) {
	                        int size = history.howMany(0,0);
	                        Object[] histValues = new Object[size];
	                        long[] histTimes = new long[size];
	                        for (int j = size - 1; j >= 0; j--) {
	                            histValues[j] = history.getValueAtIdx(j);
	                            histTimes[j] = history.getTimeAtIdx(j);
	                        }
	                        values[i] = new Object[] {histValues, histTimes};
//	                        destination.setArrayHistoryPropertyValue(propertyName, length, histValues, histTimes, i);
                        }
                    }
                }
                destination.setPropertyValue(propertyName, values, FieldType.BLOB.toString());
                return true;
            }

            default:
                return false;
        }
    }
}
