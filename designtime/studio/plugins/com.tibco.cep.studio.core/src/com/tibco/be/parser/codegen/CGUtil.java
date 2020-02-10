package com.tibco.be.parser.codegen;

import java.util.Collection;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.parser.tree.NodeType;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.event.impl.EventImpl;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 9, 2004
 * Time: 12:41:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class CGUtil extends RDFUtil {
    

    public static String genericJavaTypeName(NodeType type, boolean ignorePropertyContext, boolean appendPrimitiveArraySuffix, boolean ignoreBoxedContext) {
        if(!ignorePropertyContext && type.hasPropertyContext()) {
        	
        	if(type.isGenericProperty()) {
        		return CGConstants.propertyGenericInterface + appendPrimitiveArraySuffix;
        	} else if(type.isGenericPropertyArray()) {
                return CGConstants.propertyArrayGenericInterface;
            } else if(type.isGenericPropertyAtom()) {
                return CGConstants.propertyAtomGenericInterface;
            }
        	
            String[] typeNameArr;
            if(type.isArray()) {
                typeNameArr = CGConstants.propertyArrayInterfaceFSClassNames;
            } else {
                typeNameArr = CGConstants.propertyAtomInterfaceFSClassNames;
            }

            if(type.isContainedConcept()) {
                return typeNameArr[RDFTypes.CONCEPT_TYPEID];
            } else if(type.isConceptReference()) {
                return typeNameArr[RDFTypes.CONCEPT_REFERENCE_TYPEID];
            } else if(type.isConcept()) {
                return type.isArray() ? CGConstants.propertyArrayConceptFSClassName : CGConstants.propertyAtomConceptFSClassName;
            } else if(type.isBoolean()) {
                return typeNameArr[RDFTypes.BOOLEAN_TYPEID];
            } else if(type.isDateTime()) {
                return typeNameArr[RDFTypes.DATETIME_TYPEID];
            } else if(type.isDouble()) {
                return typeNameArr[RDFTypes.DOUBLE_TYPEID];
            } else if(type.isInt()) {
                return typeNameArr[RDFTypes.INTEGER_TYPEID];
            } else if(type.isLong()) {
                return typeNameArr[RDFTypes.LONG_TYPEID];
            } else if(type.isString()) {
                return typeNameArr[RDFTypes.STRING_TYPEID];
            } else {
                assert(false);
                return "Unknown_Property_Type";
            }
        }

        String arraySuffix = "";
        if(appendPrimitiveArraySuffix && type.isArray()) arraySuffix = "[]";

        if(!ignoreBoxedContext && type.hasBoxedContext()) {
            if(type.isBoolean()) {
                return "java.lang.Boolean" + arraySuffix;
            } else if(type.isDouble()) {
                return "java.lang.Double" + arraySuffix;
            } else if(type.isInt()) {
                return "java.lang.Integer" + arraySuffix;
            } else if(type.isLong()) {
                return "java.lang.Long" + arraySuffix;
            }
        }
        
        if(type.isGenericProperty()) {
            return CGConstants.propertyGenericInterface + arraySuffix;
        } else if(type.isAdvisoryEvent()) {
            return CGConstants.engineAdvisoryEventInterface + arraySuffix;
        } else if(type.isException()) {
            return CGConstants.engineExceptionInterface + arraySuffix;
        } else if(type.isSimpleEvent()) {
            return CGConstants.engineSimpleEventInterface + arraySuffix;
        } else if(type.isTimeEvent()) {
            return CGConstants.engineTimeEventInterface + arraySuffix;
        } else if(type.isEvent()) {
            return CGConstants.engineEventInterface + arraySuffix;
        } else if(type.isProcess()) {
        	return CGConstants.engineProcessBaseClass + arraySuffix;
        } else if(type.isContainedConcept()) {
            return CGConstants.engineContainedConceptInterface + arraySuffix;
        } else if(type.isConcept()) {
            return CGConstants.engineConceptInterface + arraySuffix;
        } else if(type.isEntity()) {
            return CGConstants.engineEntityInterface + arraySuffix;
        } else if(type.isBoolean()) {
            return "boolean" + arraySuffix;
        } else if(type.isDateTime()) {
            return "java.util.Calendar" + arraySuffix;
        } else if(type.isDouble()) {
            return "double" + arraySuffix;
        } else if(type.isInt()) {
            return "int" + arraySuffix;
        } else if(type.isLong()) {
            return "long" + arraySuffix;
        } else if(type.isString()) {
            return "java.lang.String" + arraySuffix;
        } else if(type.isVoid()) {
            return "void";
        } else if (type.isObject()) {
            return CGConstants.objectImpl + arraySuffix;
        }

        assert(false);
        if(type.isNull()) {
            return "null";
        } else if(type.isUnknown()) {
            return "unknown";
        } else {
            return "Invalid_Type";
        }
    }

    public static boolean isJavaPrimitive(NodeType type) {
        return type.hasPrimitiveContext() && type.isPrimitiveType() && !type.isObject() && !type.isString() && !type.isDateTime();
    }

	/**
	 * @param type
	 * @return
	 */
	public static String convertNonboxedToBoxed(String type) {
	    if(type.equals("int")) {
	        return Integer.class.getName();
	    }
	    else if (type.equals("long")) {
	        return Long.class.getName();
	    }
	    else if (type.equals("double")) {
	        return Double.class.getName();
	    }
	    else if (type.equals("boolean")) {
	        return Boolean.class.getName();
	    }
	    else
	        return type;
	}
	
    //limit on args is 255, longs and double count as two args and implicit this for methods counts as one
    public static boolean areEventConstructorArgsOversize(Event evt) {
        Collection<EventPropertyDefinition> properties = evt.getAllUserProperties();
    	//+2 for extId, payload
    	int size = 2 + properties.size();
    	if(size <= 127) return false;
    	if(size > 255) return true;
        
        size = 2;
        for(EventPropertyDefinition propDef : properties) {
            if(propDef == null) {
                size += 2;
            } else {
                RDFPrimitiveTerm rdfType = propDef.getType();
                if(rdfType == null) size += 2;
                else size += ModelUtils.fnArgSize(rdfType.getTypeId());
            }
        }
        return size > 255;
    }
    public static boolean areEventConstructorArgsOversize(EventImpl evt) {
        Collection<com.tibco.cep.designtime.core.model.element.PropertyDefinition> properties = evt.getProperties();
    	//+2 for extId, payload
    	int size = 2 + properties.size();
    	if(size <= 127) return false;
    	if(size > 255) return true;
        
        size = 2;
        for(com.tibco.cep.designtime.core.model.element.PropertyDefinition propDef : properties) {
            if(propDef == null) {
                size += 2;
            } else {
                PROPERTY_TYPES propType = propDef.getType();
                if(propType == null) size += 2;
                else size += ModelUtils.fnArgSize(propType.getValue());
            }
        }
        return size > 255;
    }
    
    public static String conceptPropertyAtomAccessExp(int propIdx, int typeIdx, boolean generateNullContainedConcept, boolean noPfx) {
    	String type = CGConstants.propertyAtomInterfaceFSClassNames[typeIdx];
    	if (typeIdx == RDFTypes.CONCEPT_TYPEID && generateNullContainedConcept) {
            type = CGConstants.propertyAtomContainedConcept_CalledFromCondition;
    	}
    	return String.format("%s<%s>getProperty(%s)", noPfx ? "this." : "", type, propIdx);
    }
    
    public static String conceptPropertyArrayAccessExp(int propIdx, int typeIdx, boolean noPfx) {
    	return String.format("%s<%s>getProperty(%s)", noPfx ? "this." : "", CGConstants.propertyArrayInterfaceFSClassNames[typeIdx], propIdx);
    }
}