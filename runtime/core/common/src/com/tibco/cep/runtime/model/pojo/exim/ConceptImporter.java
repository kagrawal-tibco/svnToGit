package com.tibco.cep.runtime.model.pojo.exim;

import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.TimeZone;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.Property.PropertyStateMachine;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.Reference;
import com.tibco.cep.runtime.model.element.impl.property.PropertyImpl;
import com.tibco.cep.runtime.model.element.impl.property.atomic.PropertyAtomAtomic;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomConceptReferenceImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomContainedConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomImpl;
import com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaProperty;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArraySimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomConceptReferenceSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomContainedConceptSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomSimple;
import com.tibco.cep.runtime.model.serializers.FieldType;
import com.tibco.cep.runtime.session.impl.HotDeployListener;

/*
* Author: Ashwin Jayaprakash / Date: 12/6/11 / Time: 3:23 PM
*/
public class ConceptImporter extends EximParent implements HotDeployListener {
    protected final TypeManager typeManager;

   protected final HashMap<Object, Constructor<? extends ConceptImpl>> typesAndConceptCtors;
    
    public ConceptImporter(TypeManager typeManager) throws Exception {
        this.typeManager = typeManager;
        
        Collection concepts = typeManager.getTypeDescriptors(TypeManager.TYPE_CONCEPT);
        Collection scorecards = typeManager.getTypeDescriptors(TypeManager.TYPE_NAMEDINSTANCE);
        typesAndConceptCtors = new HashMap<Object, Constructor<? extends ConceptImpl>>(concepts.size() + scorecards.size());
        _initTypesAndCtors(concepts, scorecards);
    }
    
    public void initTypesAndCtors() throws SecurityException, NoSuchMethodException {
    	Collection concepts = typeManager.getTypeDescriptors(TypeManager.TYPE_CONCEPT);
        Collection scorecards = typeManager.getTypeDescriptors(TypeManager.TYPE_NAMEDINSTANCE);
    	_initTypesAndCtors(concepts, scorecards);
    }
    
    protected void _initTypesAndCtors(Collection concepts, Collection scorecards) throws SecurityException, NoSuchMethodException {
        for (Object o : concepts) {
        	initTypesAndCtorsLoopBody(o);
        }
        
        for (Object o : scorecards) {
        	initTypesAndCtorsLoopBody(o);
        }
    }
    
    protected void initTypesAndCtorsLoopBody(Object o) throws SecurityException, NoSuchMethodException {
    	TypeDescriptor td = (TypeDescriptor) o;
        Class implKlass = td.getImplClass();
        
        if (isClusterMode()) {
        	int typeId = typeManager.getTypeDescriptor(implKlass).getTypeId();
        	if (!typesAndConceptCtors.containsKey(typeId)) {
        		Constructor<? extends ConceptImpl> ctor = implKlass.getConstructor(Long.TYPE, String.class);
        		typesAndConceptCtors.put(typeId, ctor);
        	}
        } else {
        	if (!typesAndConceptCtors.containsKey(td.getURI())) {
        		Constructor<? extends ConceptImpl> ctor = implKlass.getConstructor(Long.TYPE, String.class);
        		typesAndConceptCtors.put(td.getURI(), ctor);
        	}
        }
    }
    
    @Override
    public void entitiesAdded() throws SecurityException, NoSuchMethodException {
		initTypesAndCtors();
    }
    
    @Override
    public void entitiesChanged(Collection<Class<Entity>> changedClasses) {}

//    public ConceptImpl importConcept(long id) {
//        PortablePojo source = eximHelper.getPojoManager().findPojoByIdForUpdate(id);
//        if (source == null) {
//            return null;
//        }
//
//        return importConcept(source);
//    }
//
//    public ConceptImpl importConcept(String extId) {
//        PortablePojo source = (PortablePojo) eximHelper.getPojoManager().findPojoByExtIdForUpdate(extId);
//        if (source == null) {
//            return null;
//        }
//
//        return importConcept(source);
//    }

    public ConceptImpl importConcept(PortablePojo source) {
        ConceptImpl destination = null;
        try {
            Constructor<? extends ConceptImpl> ctor = null;
            if (isClusterMode()) {
            	ctor = typesAndConceptCtors.get(source.getTypeId());
            } else {
            	ctor = typesAndConceptCtors.get(source.getEntity().getFullPath());
            }
        	
            destination = ctor.newInstance(source.getId(), source.getExtId());

            destination.setVersion(source.getVersion());

            boolean deleted = (Boolean) source.getPropertyValue(PortablePojoConstants.PROPERTY_CONCEPT_NAME_DELETED, FieldType.BOOLEAN.toString());
            if (deleted) {
                destination.markDeleted();
            }

            // check if this is a child concept
            if (destination instanceof ContainedConcept) {
                Long parentId = (Long)source.getPropertyValue(PortablePojoConstants.PROPERTY_CONCEPT_NAME_PARENT, FieldType.LONG.toString());
                if (parentId != null) {
                    destination.setParentReference(new Reference(parentId));
                }
            }

            // check for reverse references
            Object[] revRefs = source.getArrayPropertyValues(PortablePojoConstants.PROPERTY_CONCEPT_NAME_REV_REFERENCES, FieldType.BLOB.toString());
            if (revRefs != null) {
	            for (int idx = 0; idx < revRefs.length; idx = idx + 2) {
	            	long reference = (Long) revRefs[idx];
	            	String property = (String) revRefs[idx + 1];
	            	destination.setReverseRef(property, new Reference(reference));
	            }
            }
            
            Property[] properties = destination.getProperties();
            for (Property property : properties) {
                deserializeProperty(source, destination, property);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return destination;
    }

    private void deserializeProperty(PortablePojo source, ConceptImpl destination, Property property) throws Exception {
        if (property instanceof PropertyAtomSimple) {
            if (deserializeAtom(source, destination, (PropertyAtomSimple) property)) {
                return;
            }
        }
        else if (property instanceof PropertyAtomAtomic) {
            if (deserializeAtomic(source, destination, (PropertyAtomAtomic) property)) {
                return;
            }
        }
        else if (property instanceof PropertyArraySimple) {
            if (deserializeArray(source, destination, (PropertyArraySimple) property)) {
                return;
            }
        }
        else if (property instanceof PropertyAtomImpl) {
            if (deserializeHistory(source, destination, (PropertyAtomImpl) property)) {
                return;
            }
        }
        else if (property instanceof PropertyArrayImpl) {
            if (deserializeArrayHistory(source, destination, (PropertyArrayImpl) property)) {
                return;
            }
        }
        
        throw new UnsupportedOperationException(
                "Unable to handle deserialization for [" + destination.getId() + "] property [" + property.getName() + "] of type" +
                        " [" + property.getClass().getName() + "]");
    }

    private boolean deserializeAtom(PortablePojo source, ConceptImpl destination, PropertyAtomSimple property)
            throws Exception {
        String propertyName = property.getName();
        MetaProperty mp = ((PropertyImpl)property).getMetaProperty();
        
        String type = RDFTypes.getTypeString(mp.getRDFTypeId());
        if (type.equals(RDFTypes.CONCEPT.toString()) || type.equals(RDFTypes.CONCEPT_REFERENCE.toString())) {
        	type = FieldType.LONG.toString();
        }
        Object propertyValue = source.getPropertyValue(propertyName, type.toLowerCase());

        switch(mp.getRDFTypeId()) {
            case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
                if (mp.isStateMachine()) {
                    String pn = normalizeStateMachineName(propertyName);
                    for (PropertyStateMachine machine : destination.getStateMachineProperties()) {
                        if (machine.getName().equals(propertyName)) {
                            PropertyAtomContainedConceptSimple paccs = (PropertyAtomContainedConceptSimple) machine;
                            Long val = (Long)source.getPropertyValue(pn);
                            if (val != null && val != PropertyAtomConcept.NULL) {
                                paccs.setM_value(new Reference(val));
                            }
                            return true;
                        }
                    }
                }
                else {
                    Long pv = (Long)propertyValue;
                    if (pv != null && pv != PropertyAtomConcept.NULL) {
                        PropertyAtomContainedConceptSimple paccs =
                                (PropertyAtomContainedConceptSimple) destination.getProperty(propertyName);
                        paccs.setM_value(new Reference(pv));
                    }
                    return true;
                }
                return false;
                
            case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
                if (propertyValue != null) {
                    PropertyAtomConceptReferenceSimple pacrs =
                            (PropertyAtomConceptReferenceSimple) destination.getProperty(propertyName);
                    pacrs.setM_value(new Reference((Long) propertyValue));
                }
                return true;

            case PropertyDefinition.PROPERTY_TYPE_DATETIME:
                if (separateTimezone) {
                    String tzId = (String)source.getPropertyValue(timeZoneName(propertyName));
                    if (tzId != null) {
                        TimeZone tz = TimeZone.getTimeZone(tzId);
                        if (tz != null) {
                            ((Calendar)propertyValue).setTimeZone(tz);
                        }
                    }
                }
            case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
            case PropertyDefinition.PROPERTY_TYPE_REAL:
            case PropertyDefinition.PROPERTY_TYPE_INTEGER:
            case PropertyDefinition.PROPERTY_TYPE_LONG:
            case PropertyDefinition.PROPERTY_TYPE_STRING:
                destination.setPropertyValue(propertyName, propertyValue);
                return true;

            default:
                return false;
        }
    }
    
    private boolean deserializeAtomic(PortablePojo source, ConceptImpl destination, PropertyAtomAtomic property)
            throws Exception {
        String propertyClassName = normalizeToSimpleType(property);
        String propertyName = property.getName();
        
        MetaProperty mp = ((PropertyImpl)property).getMetaProperty();

        if (propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.atomic.PropertyAtomBooleanAtomic")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.atomic.PropertyAtomDoubleAtomic")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.atomic.PropertyAtomIntAtomic")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.atomic.PropertyAtomLongAtomic")) {
            destination.setPropertyValue(propertyName, source.getPropertyValue(propertyName, RDFTypes.getTypeString(mp.getRDFTypeId()).toLowerCase()));
            return true;
        }

        return false;
    }

    private boolean deserializeArray(PortablePojo source, ConceptImpl destination, PropertyArraySimple property) {
        String propertyClassName = normalizeToSimpleType(property);
        String propertyName = property.getName();
        Object[] values = source.getArrayPropertyValues(propertyName, FieldType.BLOB.toString());

        if (propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayConceptReferenceSimple")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayContainedConceptSimple")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyArrayConceptReferenceSimpleBigIndex")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyArrayContainedConceptSimpleBigIndex")) {
            PropertyArray array = destination.getPropertyArray(propertyName);
            if (values != null) {
                for (Object value : values) {
                    PropertyAtom atom = array.add();
                    if (value != null) {
                        if (atom instanceof PropertyAtomContainedConceptSimple) {
                            ((PropertyAtomContainedConceptSimple) atom).setM_value(new Reference((Long) value));
                        }
                        else {
                            ((PropertyAtomConceptReferenceSimple) atom).setM_value(new Reference((Long) value));
                        }
                    }
                }
            }
            return true;
        }

        if (propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayBooleanSimple")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayDateTimeSimple")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayDoubleSimple")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayIntSimple")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayLongSimple")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayStringSimple")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyArrayBooleanSimpleBigIndex")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyArrayDateTimeSimpleBigIndex")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyArrayDoubleSimpleBigIndex")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyArrayIntSimpleBigIndex")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyArrayLongSimpleBigIndex")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyArrayStringSimpleBigIndex")) {
            PropertyArray array = destination.getPropertyArray(propertyName);
            if (values != null) {
                for (Object value : values) {
                    array.add(value);
                }
            }
            return true;
        }

        return false;
    }

    private boolean deserializeHistory(PortablePojo source, ConceptImpl destination, PropertyAtomImpl property) {
        String propertyClassName = normalizeToSimpleType(property);
        String propertyName = property.getName();
        Object[] values = source.getHistoryPropertyValues(propertyName, FieldType.BLOB.toString());
        
        if (propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomConceptReferenceImpl")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomContainedConceptImpl")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyAtomConceptReferenceImplBigIndex")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyAtomContainedConceptImplBigIndex")) {
            PropertyAtom hist = destination.getPropertyAtom(propertyName);
            if (values != null) {
                int idx = 0;
                for (Object value : values) {
                    if ((value != null) && (value instanceof Object[])) {
                        Object[] histValue = (Object[]) value;
                        if (histValue.length == 2) {
                            if (hist instanceof PropertyAtomConceptReferenceImpl) {
                                ((PropertyAtomConceptReferenceImpl) hist).setM_value(new Reference((Long) histValue[0]), (Long) histValue[1], idx++);
                            }
                            else {
                                ((PropertyAtomContainedConceptImpl) hist).setM_value(new Reference((Long) histValue[0]), (Long) histValue[1], idx++);
                            }
                        }
                    }
                }
            }
            return true;
        }

        if (propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomBooleanImpl")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomDateTimeImpl")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomDoubleImpl")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomIntImpl")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomLongImpl")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomStringImpl")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyAtomBooleanImplBigIndex")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyAtomDateTimeImplBigIndex")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyAtomDoubleImplBigIndex")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyAtomIntImplBigIndex")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyAtomLongImplBigIndex")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyAtomStringImplBigIndex")) {
            PropertyAtom hist = destination.getPropertyAtom(propertyName);
            if (values != null) {
                for (Object value : values) {
                    if ((value != null) && (value instanceof Object[])) {
                        Object[] histValue = (Object[]) value;
                        if (histValue.length == 2) {
                            if (histValue[0] != null) {
                                ((PropertyAtomImpl) hist).setValue(histValue[0], (Long) histValue[1]);
                            }
                        }
                    }
                }
            }
            return true;
        }

        return false;
    }

    private boolean deserializeArrayHistory(PortablePojo source, ConceptImpl destination, PropertyArrayImpl property) {
        String propertyClassName = normalizeToSimpleType(property);
        String propertyName = property.getName();
        Object[] values = source.getArrayPropertyValues(propertyName, FieldType.BLOB.toString());

        if (propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayConceptReferenceImpl")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayContainedConceptImpl")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyArrayConceptReferenceImplBigIndex")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyArrayContainedConceptImplBigIndex")) {
            PropertyArray array = destination.getPropertyArray(propertyName);
            if (values != null) {
                for (Object value : values) {
                    PropertyAtom hist = array.add();
                    if ((value != null) && (value instanceof Object[])) {
                        Object[] arrValue = (Object[]) value;
                        if (arrValue.length == 2) {
                            Object[] histValues = (Object[]) arrValue[0];
                            long[] histTimes = (long[]) arrValue[1];
                            for (int idx = 0; idx < histValues.length; idx++) {
                                if (histValues[idx] != null) {
                                    if (hist instanceof PropertyAtomConceptReferenceImpl) {
                                        ((PropertyAtomConceptReferenceImpl) hist).setM_value(new Reference((Long) histValues[idx]), (Long) histTimes[idx], idx);
                                    }
                                    else {
                                        ((PropertyAtomContainedConceptImpl) hist).setM_value(new Reference((Long) histValues[idx]), (Long) histTimes[idx], idx);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }

        if (propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayBooleanImpl")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayDateTimeImpl")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayDoubleImpl")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayIntImpl")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayLongImpl")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayStringImpl")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyArrayBooleanImplBigIndex")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyArrayDateTimeImplBigIndex")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyArrayDoubleImplBigIndex")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyArrayIntImplBigIndex")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyArrayLongImplBigIndex")
            || propertyClassName.equals("com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyArrayStringImplBigIndex")) {
            PropertyArrayImpl array = (PropertyArrayImpl) destination.getPropertyArray(propertyName);
            if (values != null) {
                for (Object value : values) {
                    PropertyAtomImpl hist = (PropertyAtomImpl) array.add();
                    if ((value != null) && (value instanceof Object[])) {
                        Object[] arrValue = (Object[]) value;
                        if (arrValue.length == 2) {
                            Object[] histValues = (Object[]) arrValue[0];
                            long[] histTimes = (long[]) arrValue[1];
                            for (int idx = 0; idx < histValues.length; idx++) {
                                if (histValues[idx] != null) {
                                    hist.setValue(histValues[idx], histTimes[idx]);
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }

        return false;
    }
}
