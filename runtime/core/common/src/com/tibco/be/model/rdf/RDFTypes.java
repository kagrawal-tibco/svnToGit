package com.tibco.be.model.rdf;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.tibco.be.model.rdf.primitives.RDFBaseAdvisoryEventTerm;
import com.tibco.be.model.rdf.primitives.RDFBaseConceptTerm;
import com.tibco.be.model.rdf.primitives.RDFBaseEntityTerm;
import com.tibco.be.model.rdf.primitives.RDFBaseEventTerm;
import com.tibco.be.model.rdf.primitives.RDFBaseExceptionTerm;
import com.tibco.be.model.rdf.primitives.RDFBaseObjectTerm;
import com.tibco.be.model.rdf.primitives.RDFBaseProcessTerm;
import com.tibco.be.model.rdf.primitives.RDFBaseSOAPEventTerm;
import com.tibco.be.model.rdf.primitives.RDFBaseTerm;
import com.tibco.be.model.rdf.primitives.RDFBooleanTerm;
import com.tibco.be.model.rdf.primitives.RDFBooleanWrapTerm;
import com.tibco.be.model.rdf.primitives.RDFConceptReferenceTerm;
import com.tibco.be.model.rdf.primitives.RDFConceptTerm;
import com.tibco.be.model.rdf.primitives.RDFDateTimeTerm;
import com.tibco.be.model.rdf.primitives.RDFDoubleTerm;
import com.tibco.be.model.rdf.primitives.RDFDoubleWrapTerm;
import com.tibco.be.model.rdf.primitives.RDFEventTerm;
import com.tibco.be.model.rdf.primitives.RDFIntegerTerm;
import com.tibco.be.model.rdf.primitives.RDFIntegerWrapTerm;
import com.tibco.be.model.rdf.primitives.RDFLongTerm;
import com.tibco.be.model.rdf.primitives.RDFLongWrapTerm;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.model.rdf.primitives.RDFStringTerm;
import com.tibco.be.model.rdf.primitives.RDFTimeEventTerm;
import com.tibco.be.model.rdf.primitives.RDFUberType;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Aug 5, 2004
 * Time: 7:12:25 PM
 * To change this template use Options | File Templates.
 */

/**
 * The term takes the runtime type as a string. This is because we dont want the engine's dependencies
 * in either place.
 */

public class RDFTypes {

    public final static RDFBaseTerm BASE_ENTITY = new RDFBaseEntityTerm(com.tibco.cep.kernel.model.entity.Entity.class.getName(), false);
    public final static RDFBaseTerm BASE_CONCEPT = new RDFBaseConceptTerm(com.tibco.cep.runtime.model.element.Concept.class.getName(), false);
    public final static RDFBaseTerm BASE_PROCESS = new RDFBaseProcessTerm("com.tibco.cep.bpmn.runtime.model.JobContext", false);
    public final static RDFBaseTerm BASE_EVENT = new RDFBaseEventTerm(com.tibco.cep.kernel.model.entity.Event.class.getName(), false);
    public final static RDFBaseTerm OBJECT = new RDFBaseObjectTerm("java.lang.Object", false);
    public final static RDFBaseTerm EXCEPTION = new RDFBaseExceptionTerm(com.tibco.cep.runtime.model.exception.BEException.class.getName(), false);
    public final static RDFBaseTerm ADVISORY_EVENT = new RDFBaseAdvisoryEventTerm(com.tibco.cep.runtime.model.event.AdvisoryEvent.class.getName(), false);
    public final static RDFBaseTerm SOAP_EVENT = new RDFBaseSOAPEventTerm(com.tibco.cep.runtime.model.event.SOAPEvent.class.getName(), false);
    
    public final static RDFUberType STRING =
            new RDFStringTerm(com.tibco.cep.runtime.model.element.PropertyAtomString.class.getName(), false, 0);

    public final static RDFUberType INTEGER =
            new RDFIntegerTerm(com.tibco.cep.runtime.model.element.PropertyAtomInt.class.getName(), false, 1);

    public final static RDFUberType INTEGER_WRAP =
            new RDFIntegerWrapTerm("java.lang.Integer", false);

    public final static RDFUberType LONG =
            new RDFLongTerm(com.tibco.cep.runtime.model.element.PropertyAtomLong.class.getName(), false, 2);

    public final static RDFUberType LONG_WRAP =
            new RDFLongWrapTerm("java.lang.Long", false);

    public final static RDFUberType DOUBLE =
            new RDFDoubleTerm(com.tibco.cep.runtime.model.element.PropertyAtomDouble.class.getName(), false, 3);

    public final static RDFUberType DOUBLE_WRAP =
            new RDFDoubleWrapTerm("java.lang.Double", false);

    public final static RDFUberType BOOLEAN =
            new RDFBooleanTerm(com.tibco.cep.runtime.model.element.PropertyAtomBoolean.class.getName(), false, 4);
    
    public final static RDFUberType BOOLEAN_WRAP =
            new RDFBooleanWrapTerm("java.lang.Boolean", false);
    
    public final static RDFUberType DATETIME =
            new RDFDateTimeTerm(com.tibco.cep.runtime.model.element.PropertyAtomDateTime.class.getName(), false, 5);

    public final static RDFUberType CONCEPT =
            new RDFConceptTerm(com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept.class.getName(), false);

    public final static RDFUberType CONCEPT_REFERENCE =
            new RDFConceptReferenceTerm(com.tibco.cep.runtime.model.element.PropertyAtomConceptReference.class.getName(), true);

    public final static RDFUberType EVENT = 
            new RDFEventTerm(com.tibco.cep.runtime.model.event.SimpleEvent.class.getName(), false);
    
    public final static RDFUberType TIME_EVENT =
            new RDFTimeEventTerm(com.tibco.cep.runtime.model.event.TimeEvent.class.getName(), false);

    //public final static RDFUberType DECISION_GRAPH =
    //        new RDFDecisionGraphTerm(com.tibco.cep.designtime.model.rule.decisiongraph.MutableDecisionGraph.class.getName(), false);

    public final static RDFUberType[] types =
            new RDFUberType[] {STRING, INTEGER, LONG, DOUBLE, BOOLEAN, DATETIME, CONCEPT, CONCEPT_REFERENCE, EVENT,
                    TIME_EVENT, ADVISORY_EVENT, SOAP_EVENT, BASE_PROCESS};

    public final static String[] primitiveTypeStrings =
            new String[] {STRING.getName(), INTEGER.getName(), LONG.getName(), DOUBLE.getName(), BOOLEAN.getName(),
                        DATETIME.getName()};


    public final static String[] typeStrings =
            new String [] {STRING.getName(), INTEGER.getName(), LONG.getName(), DOUBLE.getName(), BOOLEAN.getName(),
                    DATETIME.getName(), CONCEPT.getName(), CONCEPT_REFERENCE.getName(), EVENT.getName(),
                    TIME_EVENT.getName(), ADVISORY_EVENT.getName(), SOAP_EVENT.getName(), BASE_PROCESS.getName()};

    public static final String[] driverTypeStrings = new String[] { STRING.getName(), "Integer", "Long", "Double",
            "Boolean", DATETIME.getName(), CONCEPT.getName(), CONCEPT_REFERENCE.getName(), EVENT.getName(),
            TIME_EVENT.getName(), ADVISORY_EVENT.getName(), SOAP_EVENT.getName(),BASE_PROCESS.getName()};



    //Index into the array
    public final static int STRING_TYPEID = 0;
    public final static int INTEGER_TYPEID = 1;
    public final static int LONG_TYPEID = 2;
    public final static int DOUBLE_TYPEID = 3;
    public final static int BOOLEAN_TYPEID = 4;
    public final static int DATETIME_TYPEID = 5;
    public final static int CONCEPT_TYPEID = 6;
    public final static int CONCEPT_REFERENCE_TYPEID = 7;
    public final static int EVENT_TYPEID = 8;
    public final static int TIME_EVENT_TYPEID = 9;
    public final static int ADVISORY_EVENT_TYPEID = 10;
    //public final static int SOAP_EVENT_TYPEID = 11;
    public final static int PROCESS_TYPEID = 12;
    //public final static int DECISION_GRAPH_TYPEID = 11;



    public final static HashMap typeMap = makeTypeMap();
    public final static HashMap primitiveTypeMap = makePrimitiveTypeMap();
    public final static HashMap primitiveTypeWrapperMap = makePrimitiveTypeWrapperMap();
    public final static HashMap driverTypeMap = makeDriverTypeMap();
    public final static HashMap indexMap = makeIndexMap();

    private static HashMap makePrimitiveTypeMap() {
        LinkedHashMap map = new LinkedHashMap();

        for(int i = 0; i < primitiveTypeStrings.length; i++) {
            RDFUberType type = types[i];
            map.put(type.getName(), type);
        }

        return map;
    }

    private static HashMap makePrimitiveTypeWrapperMap() {
        LinkedHashMap map = new LinkedHashMap();

        addTypeToMap(map, INTEGER_WRAP);
        addTypeToMap(map, LONG_WRAP);
        addTypeToMap(map, DOUBLE_WRAP);
        addTypeToMap(map, BOOLEAN_WRAP);
        
        return map;
    }

    private static void addTypeToMap(Map m, RDFUberType type) {
        m.put(type.getName(), type);
    }

    private static HashMap makeTypeMap() {
        LinkedHashMap map = new LinkedHashMap();

        for (int i=0; i < types.length; i++) {
            RDFUberType type = types[i];
            map.put(type.getName(), type);
        }

        map.put(BASE_ENTITY.getName(), BASE_ENTITY);
        map.put(BASE_CONCEPT.getName(), BASE_CONCEPT);
        map.put(BASE_EVENT.getName(), BASE_EVENT);
        map.put(BOOLEAN_WRAP.getName(), BOOLEAN_WRAP);
        map.put(DOUBLE_WRAP.getName(), DOUBLE_WRAP);
        map.put(LONG_WRAP.getName(), LONG_WRAP);
        map.put(INTEGER_WRAP.getName(), INTEGER_WRAP);
        map.put(OBJECT.getName(), OBJECT);
        map.put(EXCEPTION.getName(), EXCEPTION);
        map.put(BASE_PROCESS.getName(), BASE_PROCESS);

        return map;
    }

    private static HashMap makeDriverTypeMap() {
        LinkedHashMap map = new LinkedHashMap();

        for (int i=0; i < types.length; i++) {
            RDFUberType type = types[i];
            map.put(driverTypeStrings[i], type);
        }

        map.put(BASE_CONCEPT.getName(), BASE_CONCEPT);
        map.put(BASE_EVENT.getName(), BASE_EVENT);

        return map;
    }

    private static HashMap makeIndexMap() {
        LinkedHashMap map = new LinkedHashMap();
        for(int i = 0; i < types.length; i++) {
            map.put(types[i], new Integer(i));
        }

        return map;
    }

    public static RDFUberType getType(String typeName) {
        RDFUberType type = (RDFUberType) typeMap.get(typeName); 
        return type;
    }

    public static RDFUberType getPrimitiveType(String typeName) {
        return (RDFUberType) primitiveTypeMap.get(typeName);
    }

    public static RDFPrimitiveTerm getPrimitiveTypeWrapper(String typeName) {
        return (RDFPrimitiveTerm) primitiveTypeWrapperMap.get(typeName);
    }

    public static RDFUberType getDriverType(String typeName) {
        return (RDFUberType) driverTypeMap.get(typeName);
    }

    public static int getIndex(RDFUberType type) {
        Integer i = (Integer) indexMap.get(type);
        if(i == null) return -1;
        return i.intValue();
    }
    
    // [Sridhar] Needed for DebuggerSupport
    public static String getTypeString(int typeIndex) {
    	return (typeStrings[typeIndex]);
    }
}