package com.tibco.be.dbutils;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;


/**
 * Created by IntelliJ IDEA.
 * User: hokwok
 * Date: Nov 6, 2006
 * Time: 9:41:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyTypes {

    public final static byte propertyTypes_atomBoolean           = 0;
    public final static byte propertyTypes_atomChar              = 1;
    public final static byte propertyTypes_atomConceptReference  = 2;
    public final static byte propertyTypes_atomDateTime          = 3;
    public final static byte propertyTypes_atomDouble            = 4;
    public final static byte propertyTypes_atomInt               = 5;
    public final static byte propertyTypes_atomLong              = 6;
    public final static byte propertyTypes_atomString            = 7;
    public final static byte propertyTypes_arrayBoolean          = 8;
    public final static byte propertyTypes_arrayChar             = 9;
    public final static byte propertyTypes_arrayConceptReference = 10;
    public final static byte propertyTypes_arrayDateTime         = 11;
    public final static byte propertyTypes_arrayDouble           = 12;
    public final static byte propertyTypes_arrayInt              = 13;
    public final static byte propertyTypes_arrayLong             = 14;
    public final static byte propertyTypes_arrayString           = 15;
    public final static byte propertyTypes_atomContainedConcept  = 16;
    public final static byte propertyTypes_arrayContainedConcept = 17;
    public final static byte propertyTypes_stateMachineStatus    = 18;

    /*
    private final static Class[] propertyTypes = {
        PropertyAtomBooleanImpl.class,
        //PropertyAtomCharImpl.class,
        PropertyAtomConceptReferenceImpl.class,
        PropertyAtomDateTimeImpl.class,
        PropertyAtomDoubleImpl.class,
        PropertyAtomIntImpl.class,
        PropertyAtomLongImpl.class,
        PropertyAtomStringImpl.class,
        PropertyArrayBooleanImpl.class,
        //PropertyArrayCharImpl.class,
        PropertyArrayConceptReferenceImpl.class,
        PropertyArrayDateTimeImpl.class,
        PropertyArrayDoubleImpl.class,
        PropertyArrayIntImpl.class,
        PropertyArrayLongImpl.class,
        PropertyArrayStringImpl.class,
        PropertyAtomContainedConceptImpl.class,
        PropertyArrayContainedConceptImpl.class
        //StateMachineStatusImpl.class
    };

    public PropertyTypes() {

    }

    public static Class getPropertyType(byte index) {
        return propertyTypes[index];
    }

    public static byte getIndex(Class cl) {
        for (byte i = 0; i < propertyTypes.length; i ++) {
            if(propertyTypes[i] == cl) {
                return i;
            }
        }
        return -1;
    }
    */

    public static byte RDFToPropType(PropertyDefinition pd, Logger logger) {

        int rdftype = pd.getType();
        switch(rdftype) {

            case RDFTypes.STRING_TYPEID :
                if(pd.isArray())
                    return propertyTypes_arrayString;
                else
                    return propertyTypes_atomString;
            case RDFTypes.DATETIME_TYPEID :
                if(pd.isArray())
                    return propertyTypes_arrayDateTime;
                else
                    return propertyTypes_atomDateTime;
            case RDFTypes.INTEGER_TYPEID :
                if(pd.isArray())
                    return propertyTypes_arrayInt;
                else
                    return propertyTypes_atomInt;
            case RDFTypes.LONG_TYPEID :
                if(pd.isArray())
                    return propertyTypes_arrayLong;
                else
                    return propertyTypes_atomLong;
            case RDFTypes.CONCEPT_TYPEID :
                if(pd.isArray())
                    return propertyTypes_arrayContainedConcept;
                else
                    return propertyTypes_atomContainedConcept;
            case RDFTypes.CONCEPT_REFERENCE_TYPEID :
                if(pd.isArray())
                    return propertyTypes_arrayConceptReference;
                else
                    return propertyTypes_atomConceptReference;
            case RDFTypes.DOUBLE_TYPEID :
                if(pd.isArray())
                    return propertyTypes_arrayDouble;
                else
                    return propertyTypes_atomDouble;
            case RDFTypes.BOOLEAN_TYPEID :
                if(pd.isArray())
                    return propertyTypes_arrayBoolean;
                else
                    return propertyTypes_atomBoolean;

            default:
                logger.log(Level.FATAL, "Unknown RDF Type encountered with type = %s",
                        RDFTypes.driverTypeStrings[rdftype]);
                System.exit(1);
                return -1; // Never reached
        }
    }
}
