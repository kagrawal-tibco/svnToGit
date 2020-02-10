package com.tibco.be.parser.tree;

import static com.tibco.be.parser.tree.NodeType.NodeTypeFlag.*;
import static com.tibco.be.parser.tree.NodeType.TypeContext.*;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.parser.CompileErrors;


/**
 * Usage notes:
 * The type checking system uses NodeTypes to pass around information 
 * about what type variables / expressions have and additionally
 * if these types should be treated as Properties, Declarations or
 * Primitives (this doesn't follow the usual meaning of primitive, 
 * it simply means that the type isn't a Property and doesn't have special unboxing behavior)
 *  
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jun 11, 2004
 * Time: 2:32:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodeType {

	public static enum NodeTypeFlag {
      _START_PRIMITIVE_,
      INT_FLAG,
      LONG_FLAG,
      DOUBLE_FLAG,
      BOOLEAN_FLAG,
      STRING_FLAG,
      DATETIME_FLAG,
      NULL_FLAG,
      UNKNOWN_FLAG,
      VOID_FLAG,
      OBJECT_FLAG,
      //integer literals promoted to longs
      LONG_PROMOTED_FLAG,
      _END_PRIMITIVE_,
      
      /**
       * Concepts, Contained Concepts and Concept References
       * ===================================================
       * As properties (has a specific model type, specified in the name):
       * ContainedConcept: CONTAINED_CONCEPT_FLAG + PROPERTY_CONTEXT
       * ConceptReference: CONCEPT_FLAG + PROPERTY_CONTEXT
       * 
       * As declared variables (has a specific model type, specified in the name)
       * Concept: CONCEPT_FLAG + PRIMITIVE_CONTEXT
       * ContainedConcept: CONTAINED_CONCEPT_FLAG + PRIMITIVE_CONTEXT
       * 
       * As generics (local variables / function arguments)
       * PropertyConcept: GENERIC_CONCEPT_FLAG + PROPERTY_CONTEXT (PropertyReference / PropertyContainedConcept / PropertyConcept)
       * PropertyReference: GENERIC_PROPERTY_REFERENCE_FLAG + PROPERTY_CONTEXT
       * PropertyContainedConcept: GENERIC_CONTAINED_CONCEPT_FLAG + PROPERTY_CONTEXT
       * Concept: GENERIC_CONCEPT_FLAG + PRIMITIVE_CONTEXT (PropertyConcept / PropertyReference / PropertyContainedConcept / ContainedConcept / Concept)
       * ContainedConcept: GENERIC_CONTAINED_CONCEPT_FLAG + PRIMITIVE_CONTEXT (PropertyContainedConcept / ContainedConcept)
       */

      _START_MODEL_OBJ_,
      CONCEPT_FLAG,
      CONTAINED_CONCEPT_FLAG,
      CONCEPT_REFERENCE_FLAG,
      CONTAINED_CONCEPT_REFERENCE_FLAG,
    //  EVENT_FLAG,
      SIMPLE_EVENT_FLAG,
      TIME_EVENT_FLAG,
      SCORECARD_FLAG,
      PROCESS_FLAG,
      _END_MODEL_OBJ_,

    //Update GENERIC_DECL_NAMES when adding types to this list
      _START_GENERIC_,
      _START_GENERIC_DECL_,
      GENERIC_ENTITY_FLAG,
      GENERIC_CONCEPT_FLAG,
      GENERIC_CONCEPT_REFERENCE_FLAG,
      GENERIC_CONTAINED_CONCEPT_FLAG,
      GENERIC_EVENT_FLAG,
      GENERIC_SIMPLE_EVENT_FLAG,
      GENERIC_TIME_EVENT_FLAG,
      GENERIC_ADVISORY_EVENT_FLAG,
      GENERIC_SOAP_EVENT_FLAG,
      GENERIC_PROCESS_FLAG,
      _END_GENERIC_DECL_,
      _START_GENERIC_PROP_,
      GENERIC_PROP_FLAG,
      GENERIC_PROP_ATOM_FLAG,
      GENERIC_PROP_ARRAY_FLAG,
      _END_GENERIC_PROP_,

      GENERIC_EXCEPTION_FLAG,
      _END_GENERIC_,
	}

    //these names correspond to the above flags and must be kept in the same order as the above flags.
    protected static final String[] PRIMITIVE_NAMES = {"int", "long", "double", "boolean", "String", "DateTime", "null", CompileErrors.unknownTypeName(), "void", "Object", "long"};
    protected static final String[] BOXED_NAMES = {"Integer", "Long", "Double", "Boolean"};

    protected static final String[] PRIMITIVE_PROP_ATOM_NAMES = {"PropertyAtomInt", "PropertyAtomLong", "PropertyAtomDouble", "PropertyAtomBoolean", "PropertyAtomString", "PropertyAtomDateTime"};
    protected static final String PROPERTY_ATOM_CONCEPT_NAME = "PropertyAtomConcept";
    protected static final String PROPERTY_ATOM_CONCEPT_REF_NAME = "PropertyAtomConceptReference";
    protected static final String PROPERTY_ATOM_CONTAINED_CONCEPT_NAME = "PropertyAtomContainedConcept";

    protected static final String[] PRIMITIVE_PROP_ARRAY_NAMES = {"PropertyArrayInt", "PropertyArrayLong", "PropertyArrayDouble", "PropertyArrayBoolean", "PropertyArrayString", "PropertyArrayDateTime"};
    protected static final String PROPERTY_ARRAY_CONCEPT_NAME = "PropertyArrayConcept";
    protected static final String PROPERTY_ARRAY_CONCEPT_REF_NAME = "PropertyArrayConceptReference";
    protected static final String PROPERTY_ARRAY_CONTAINED_CONCEPT_NAME = "PropertyArrayContainedConcept";

    private static final String[] GENERIC_DECL_NAMES = {RDFTypes.BASE_ENTITY.getName(), RDFTypes.BASE_CONCEPT.getName(), RDFTypes.CONCEPT_REFERENCE.getName(), RDFTypes.CONCEPT.getName(), RDFTypes.BASE_EVENT.getName(), RDFTypes.EVENT.getName(), RDFTypes.TIME_EVENT.getName(), RDFTypes.ADVISORY_EVENT.getName(), RDFTypes.SOAP_EVENT.getName(), RDFTypes.BASE_PROCESS.getName()};
    private static final String[] GENERIC_PROP_NAMES = {"Property", "PropertyAtom", "PropertyArray"};
    
    public static enum TypeContext {
    	PROPERTY_CONTEXT, PRIMITIVE_CONTEXT, BOXED_CONTEXT,
    }

    //these names correspond to the above flags and must be kept in the same order as the above flags.
    protected static final String[] TYPE_CONTEXT_NAMES = {"property", "primitive", "boxed"};//{"property", "decl", "primitive"};

    public static final NodeType NULL = new NodeType(NULL_FLAG, PRIMITIVE_CONTEXT, false, "null");
    public static final NodeType VOID = new NodeType(VOID_FLAG, PRIMITIVE_CONTEXT, false, "void");
    public static final NodeType UNKNOWN = new NodeType(UNKNOWN_FLAG, PRIMITIVE_CONTEXT, false, CompileErrors.unknownTypeName());
    public static final NodeType TEST_FLAG = new NodeType(UNKNOWN_FLAG, PRIMITIVE_CONTEXT, false, "TEST FLAG");

    //used by the copy constructor -- not sure if it will be interned
    public static final String NULL_NAME = new String();

    private static final NodeTypeFlag[] rdfTypeIdToFlagPrimitive = {STRING_FLAG, INT_FLAG, LONG_FLAG, DOUBLE_FLAG, BOOLEAN_FLAG, DATETIME_FLAG};
    private static final NodeTypeFlag[] rdfTypeIdToFlagGeneric = {GENERIC_CONTAINED_CONCEPT_FLAG, GENERIC_CONCEPT_FLAG, GENERIC_SIMPLE_EVENT_FLAG, GENERIC_TIME_EVENT_FLAG, GENERIC_ADVISORY_EVENT_FLAG, GENERIC_SOAP_EVENT_FLAG, GENERIC_PROCESS_FLAG};
    private static final NodeTypeFlag[] rdfTypeIdToFlagModel = {CONTAINED_CONCEPT_FLAG, CONCEPT_FLAG, SIMPLE_EVENT_FLAG, TIME_EVENT_FLAG, GENERIC_ADVISORY_EVENT_FLAG, GENERIC_SOAP_EVENT_FLAG, PROCESS_FLAG};

    protected final NodeTypeFlag typeFlag;
    protected final String name;
    protected final TypeContext typeContext;
    protected final boolean mutable;
    protected final boolean isArrayType;
    protected final boolean isVariableType;

    public NodeType(NodeTypeFlag typeFlag, TypeContext typeContext, boolean mutable, boolean isArray, String name) {
    	this(typeFlag, typeContext, mutable, isArray, name, false);
    }
    
    public NodeType(NodeTypeFlag typeFlag, TypeContext typeContext, boolean mutable, boolean isArray, String name, boolean isVarDef) {
        this.typeFlag = typeFlag;
        this.typeContext = typeContext;
        this.mutable = mutable;
        this.isArrayType = isArray;
    	this.isVariableType = isVarDef;

        if(name == null) {
            this.name = "";
        } else {
            this.name = name;
        }
    }
    
    //clone with overrides
    public NodeType(NodeType clone, NodeTypeFlag typeFlag, TypeContext typeContext, Boolean mutable, Boolean isArray, String name, Boolean isVarDef) {
        this(typeFlag == null ? clone.typeFlag : typeFlag, typeContext == null ? clone.typeContext : typeContext, 
                mutable == null ? clone.mutable : mutable, isArray == null ? clone.isArrayType : isArray,
                (name == null || name == NULL_NAME) ? clone.name : name, isVarDef == null ? clone.isVariableType : isVarDef);
    }

    public NodeType(NodeTypeFlag typeFlag, TypeContext typeContext, boolean mutable, String name) {
        this(typeFlag, typeContext, mutable, false, name);
    }

    public NodeType(NodeType.NodeTypeFlag typeFlag, TypeContext typeContext, boolean mutable, boolean isArray) {
        this(typeFlag, typeContext, mutable, isArray, null);
    }

    public NodeType(NodeType.NodeTypeFlag typeFlag, TypeContext typeContext, boolean mutable) {
        this(typeFlag, typeContext, mutable, null);
    }

    public String toString() {
        return "Type Name: " + getName() + " Type Context: " + TYPE_CONTEXT_NAMES[typeContext.ordinal()];
    }

    public String getDisplayName() {
        String tempName;
        if(hasPropertyContext()) {
            String[] primitiveNameArray = isArray() ? PRIMITIVE_PROP_ARRAY_NAMES : PRIMITIVE_PROP_ATOM_NAMES;
            if(isPrimitiveType()) {
                return primitiveNameArray[typeFlag.ordinal() - _START_PRIMITIVE_.ordinal() - 1];
            }

            if(isGenericProperty() || isGenericPropertyArray() || isGenericPropertyAtom()) {
                return getName(false);
            }

            if(isContainedConcept()) {
                tempName = isArray() ? PROPERTY_ARRAY_CONTAINED_CONCEPT_NAME : PROPERTY_ATOM_CONTAINED_CONCEPT_NAME;
            } else if(isConceptReference()) {
                tempName = isArray() ? PROPERTY_ARRAY_CONCEPT_REF_NAME : PROPERTY_ATOM_CONCEPT_REF_NAME;
            } else {
                assert(isConcept());
                tempName = isArray() ? PROPERTY_ARRAY_CONCEPT_NAME : PROPERTY_ATOM_CONCEPT_NAME;
            }

            if(isGeneric()) {
                return tempName;
            }  else {
                return tempName + "(" + name + ")";
            }
        } else {
            return getName(true);
        }
    }

    public String getName() {
        return getName(false);
    }
    public String getName(boolean appendArraySuffix) {
        String tempName = name;
        if(isPrimitiveType()) {
            tempName = PRIMITIVE_NAMES[typeFlag.ordinal() - _START_PRIMITIVE_.ordinal() - 1];
        }
        if(isGeneric()) {
            if(isEntity()) {
                tempName = GENERIC_DECL_NAMES[typeFlag.ordinal() - _START_GENERIC_DECL_.ordinal() - 1];
            } else if(isException()) {
                tempName = RDFTypes.EXCEPTION.getName();
            } else {
                tempName = GENERIC_PROP_NAMES[typeFlag.ordinal() - _START_GENERIC_PROP_.ordinal() - 1];
            }
        }
        if(hasBoxedContext()) {
            tempName = BOXED_NAMES[typeFlag.ordinal() - _START_PRIMITIVE_.ordinal() - 1];
        }
        if(appendArraySuffix && isArray() && !isGenericPropertyArray()) {
            return tempName + "[]";
        } else {
            return tempName;
        }
    }

    public boolean isConcept() {
        return
               typeFlag == CONCEPT_FLAG
            || typeFlag == CONTAINED_CONCEPT_FLAG
            || typeFlag == CONCEPT_REFERENCE_FLAG
            || typeFlag == CONTAINED_CONCEPT_REFERENCE_FLAG
            || typeFlag == GENERIC_CONCEPT_FLAG
            || typeFlag == GENERIC_CONCEPT_REFERENCE_FLAG
            || typeFlag == GENERIC_CONTAINED_CONCEPT_FLAG
            || typeFlag == SCORECARD_FLAG
            || typeFlag == PROCESS_FLAG
            || typeFlag == GENERIC_PROCESS_FLAG;
    }

    public boolean isProcess() {
        return typeFlag == PROCESS_FLAG || typeFlag == GENERIC_PROCESS_FLAG;
    }

    public boolean isGenericProcess() {
        return typeFlag == GENERIC_PROCESS_FLAG;
    }

    public boolean isConceptReference() {
        //non-generic concept properties that don't have the contained concept flag are concept references
        return typeFlag == GENERIC_CONCEPT_REFERENCE_FLAG || typeFlag == CONCEPT_REFERENCE_FLAG || typeFlag == CONTAINED_CONCEPT_REFERENCE_FLAG;
    }

    public boolean isContainedConceptReference() {
        return typeFlag == CONTAINED_CONCEPT_REFERENCE_FLAG;
    }

    public boolean isContainedConcept() {
        return typeFlag == CONTAINED_CONCEPT_FLAG || typeFlag == GENERIC_CONTAINED_CONCEPT_FLAG || typeFlag == CONTAINED_CONCEPT_REFERENCE_FLAG;
    }

    public boolean isGenericConcept() {
        return  typeFlag == GENERIC_CONCEPT_FLAG;

    }

    public boolean isGenericContainedConcept() {
        return typeFlag == GENERIC_CONTAINED_CONCEPT_FLAG;
    }

    public boolean isGenericConceptReference() {
        return typeFlag == GENERIC_CONCEPT_REFERENCE_FLAG;
    }

    public boolean isScorecard() {
        return typeFlag == SCORECARD_FLAG;
    }

    public boolean isGenericEntity() {
        return typeFlag == GENERIC_ENTITY_FLAG;
    }

    public boolean isEvent() {
        return
            typeFlag == SIMPLE_EVENT_FLAG
         || typeFlag == TIME_EVENT_FLAG
         || typeFlag == GENERIC_EVENT_FLAG
         || typeFlag == GENERIC_SIMPLE_EVENT_FLAG
         || typeFlag == GENERIC_TIME_EVENT_FLAG
         || typeFlag == GENERIC_ADVISORY_EVENT_FLAG;
    }

    public boolean isSimpleEvent() {
        return
           typeFlag == SIMPLE_EVENT_FLAG
        || typeFlag == GENERIC_SIMPLE_EVENT_FLAG;

    }

    public boolean isTimeEvent() {
        return typeFlag == TIME_EVENT_FLAG || typeFlag == GENERIC_TIME_EVENT_FLAG;
    }

    public boolean isException() {
        return isGenericException();
    }
    public boolean isGenericException() {
        return typeFlag == GENERIC_EXCEPTION_FLAG;
    }

    public boolean isAdvisoryEvent() {
        return isGenericAdvisoryEvent();
    }
    public boolean isGenericAdvisoryEvent() {
        return typeFlag == GENERIC_ADVISORY_EVENT_FLAG;
    }
    
    public boolean isGenericEvent() {
        return typeFlag == GENERIC_EVENT_FLAG;
    }

    public boolean isGenericSimpleEvent() {
        return typeFlag == GENERIC_SIMPLE_EVENT_FLAG;
    }

    public boolean isGenericTimeEvent() {
        return typeFlag == GENERIC_TIME_EVENT_FLAG;
    }

    public boolean isEntity() {
        return (typeFlag.ordinal() > _START_MODEL_OBJ_.ordinal() && typeFlag.ordinal() < _END_MODEL_OBJ_.ordinal())
            || (typeFlag.ordinal() > _START_GENERIC_DECL_.ordinal() && typeFlag.ordinal() < _END_GENERIC_DECL_.ordinal());
    }

    public boolean isGeneric() {
        return typeFlag.ordinal() > _START_GENERIC_.ordinal() && typeFlag.ordinal() < _END_GENERIC_.ordinal();
    }

    public boolean isGenericInCodegen() {
        return isGeneric() || isProcess();
    }

    //todo generalize multiple inheritance & array agnostic types (ex. null, PropertyInt)
    /**
     * @return true if the type represents a generic Property ONLY (generic PropertyArray or PropertyAtom return false)
     */
    public boolean isGenericProperty() {
        return typeFlag == GENERIC_PROP_FLAG;
    }

    public boolean isGenericPropertyAtom() {
        return typeFlag == GENERIC_PROP_ATOM_FLAG;
    }

    public boolean isGenericPropertyArray() {
        return typeFlag == GENERIC_PROP_ARRAY_FLAG;
    }

    public boolean isNumber() {
        return typeFlag == INT_FLAG || typeFlag == LONG_FLAG || typeFlag == DOUBLE_FLAG || typeFlag == NodeTypeFlag.LONG_PROMOTED_FLAG;
    }

    //can't use somehting like START_VALUE_TYPES flag and END_VALUE_TYPES flag 
    //because it messes up the index into the PRIMITIVE_NAMES array
    public boolean isValueType() {
        return typeFlag == INT_FLAG || typeFlag == LONG_FLAG || typeFlag == DOUBLE_FLAG || typeFlag == BOOLEAN_FLAG || typeFlag == NodeTypeFlag.LONG_PROMOTED_FLAG; 
    }
    
    public boolean isPrimitiveType() {
        return (typeFlag.ordinal() > _START_PRIMITIVE_.ordinal()) && (typeFlag.ordinal() < _END_PRIMITIVE_.ordinal());
    }

    public boolean isInt() {
        return typeFlag == INT_FLAG;
    }

    public boolean isLong() {
        return typeFlag == LONG_FLAG || typeFlag == LONG_PROMOTED_FLAG;
    }

    public boolean isPromotedLong() {
    	return typeFlag == LONG_PROMOTED_FLAG;
    }
    
    public boolean isDouble() {
        return typeFlag == DOUBLE_FLAG;
    }

    public boolean isBoolean() {
        return typeFlag == BOOLEAN_FLAG;
    }

    public boolean isNull() {
        return typeFlag == NULL_FLAG;
    }

    public boolean isString() {
        return typeFlag == STRING_FLAG;
    }

    public boolean isDateTime() {
        return typeFlag == DATETIME_FLAG;
    }

    public boolean isUnknown() {
        return typeFlag == UNKNOWN_FLAG;
    }

    public boolean isVoid() {
        return typeFlag == VOID_FLAG;
    }

    public boolean isObject() {
        return typeFlag == OBJECT_FLAG;
    }

    public boolean hasPropertyContext() {
        return typeContext == PROPERTY_CONTEXT;
    }

    //public boolean hasDeclContext() {
    //    return typeContext == DECL_CONTEXT;
    //}

    public boolean hasPrimitiveContext() {
        return typeContext == PRIMITIVE_CONTEXT;
    }

    public boolean hasBoxedContext() {
        return typeContext == BOXED_CONTEXT;
    }

    public boolean isMutable() {
        return mutable;
    }

    public boolean isArray() {
        return isArrayType;
    }

    /**
     * @param nodeType
     * @return true if this object is equivalent to the argument
     */
    public boolean isEqual(NodeType nodeType) {
        return (this == nodeType) || ((nodeType.typeFlag.ordinal() == typeFlag.ordinal()) && nodeType.name.equals(name) && (nodeType.typeContext == typeContext) && (nodeType.isArrayType == isArrayType));
    }

    /**
     * @param nodeType
     * @return true if this object has the same type as the argument (disregarding type context)
     */
    public boolean isTypeEqual(NodeType nodeType) {
        //todo for concept types, use the ontology instead of direct name comparision?
        return (this == nodeType) || ((nodeType.typeFlag.ordinal() == typeFlag.ordinal()) && nodeType.name.equals(name) && (nodeType.isArrayType == isArrayType));
    }

    public NodeType getComponentType(boolean isComponentMutable) {
        if(!isArray()) return UNKNOWN;
        if(isEntity() && !isGeneric()) {
            return new NodeType(typeFlag, typeContext, isComponentMutable, false, name);
        } else {
            return new NodeType(typeFlag, typeContext, isComponentMutable, false);
        }
    }

    public boolean isVariableType() {
    	return isVariableType;
    }

    public static NodeTypeFlag rdfTypeIdToFlag(int typeId, boolean generic) {
        if(typeId < 0) return null;
        if(typeId < rdfTypeIdToFlagPrimitive.length) {
            return rdfTypeIdToFlagPrimitive[typeId];
        } else if(typeId < rdfTypeIdToFlagPrimitive.length + rdfTypeIdToFlagGeneric.length && generic) {
            return rdfTypeIdToFlagGeneric[typeId - rdfTypeIdToFlagPrimitive.length];
        } else if(typeId < rdfTypeIdToFlagPrimitive.length + rdfTypeIdToFlagModel.length && !generic) {
            return rdfTypeIdToFlagModel[typeId - rdfTypeIdToFlagPrimitive.length];
        }
        return null;
    }
    
//    public static class TypeContext{
//        private static int upperBound = 0;
//        private final int ord;
//        private TypeContext(){
//            ord = upperBound++;
//        }
//    }
}   