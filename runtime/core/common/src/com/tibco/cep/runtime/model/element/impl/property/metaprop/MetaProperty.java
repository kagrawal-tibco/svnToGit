package com.tibco.cep.runtime.model.element.impl.property.metaprop;

import java.util.Calendar;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.kernel.helper.BitSet;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.PropertyImpl;
import com.tibco.cep.runtime.model.element.impl.property.atomic.PropertyArrayBooleanAtomic;
import com.tibco.cep.runtime.model.element.impl.property.atomic.PropertyArrayDoubleAtomic;
import com.tibco.cep.runtime.model.element.impl.property.atomic.PropertyArrayIntAtomic;
import com.tibco.cep.runtime.model.element.impl.property.atomic.PropertyArrayLongAtomic;
import com.tibco.cep.runtime.model.element.impl.property.atomic.PropertyAtomBooleanAtomic;
import com.tibco.cep.runtime.model.element.impl.property.atomic.PropertyAtomDoubleAtomic;
import com.tibco.cep.runtime.model.element.impl.property.atomic.PropertyAtomIntAtomic;
import com.tibco.cep.runtime.model.element.impl.property.atomic.PropertyAtomLongAtomic;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayBooleanImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayConceptReferenceImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayContainedConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayDateTimeImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayDoubleImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayIntImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayLongImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayStringImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomBooleanImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomConceptReferenceImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomContainedConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomDateTimeImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomDoubleImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomIntImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomLongImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomStringImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyArrayBooleanImplBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyArrayConceptReferenceImplBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyArrayContainedConceptImplBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyArrayDateTimeImplBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyArrayDoubleImplBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyArrayIntImplBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyArrayLongImplBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyArrayStringImplBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyAtomBooleanImplBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyAtomConceptReferenceImplBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyAtomContainedConceptImplBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyAtomDateTimeImplBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyAtomDoubleImplBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyAtomIntImplBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyAtomLongImplBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.history.bigindex.PropertyAtomStringImplBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayBooleanSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayConceptReferenceSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayContainedConceptSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayDateTimeSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayDoubleSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayIntSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayLongSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArraySimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayStringSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomBooleanSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomConceptReferenceSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomContainedConceptSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomDateTimeSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomDoubleSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomIntSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomLongSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomStringSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineImpl;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyArrayBooleanSimpleBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyArrayConceptReferenceSimpleBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyArrayContainedConceptSimpleBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyArrayDateTimeSimpleBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyArrayDoubleSimpleBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyArrayIntSimpleBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyArrayLongSimpleBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyArrayStringSimpleBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyAtomBooleanSimpleBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyAtomConceptReferenceSimpleBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyAtomContainedConceptSimpleBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyAtomDateTimeSimpleBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyAtomDoubleSimpleBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyAtomIntSimpleBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyAtomLongSimpleBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyAtomStringSimpleBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyStateMachineImplBigIndex;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: 11/30/11
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class MetaProperty //<DEFAULT>
{
    final private String name;
    //only for concept properties; the user's specific concept type
    final private Class type;
    final private int historySize;
    final private int containedPropIndex;
    final private byte propType;
    final private Object defaultValue;
    //bitset of maintain reverse refs, isArray, history policy
    final private byte opts;

    final private static int OPT_ATOMIC_PROPS = 3;
    final private static int OPT_RR = 2;
    final private static int OPT_ARR = 1;
    //don't change value without changing getHistoryPolicy
    final private static int OPT_HIST_POLICY = 0;

    final private static Object NO_DEFAULT = null;
    final public static byte SM_STATE = 126;
    final private static int SM_STATE_INT = 126;
    final public static byte SM_CONCEPT = 127;
    final private static int SM_CONCEPT_INT = 127;

    final public static int INDEX_LIMIT = 127;

    //public MetaProperty(String name, int historyPolicy, int historySize, int propType) {
    //    this(name, historyPolicy, historySize, propType);  //, DEFAULT_NOT_SET);
    //}

    public MetaProperty(String name, byte historyPolicy, int historySize, byte propType, boolean isArray, Object defaultValue, boolean isAtomic)
    {
        this(name, historyPolicy, historySize, null, false, -1, propType, isArray, defaultValue, isAtomic);
    }

    //public MetaProperty(String name, int historyPolicy, int historySize, Class type, boolean maintainReverseRefs, int containedPropIndex, int propType) {
    //    this(name, historyPolicy, historySize, type, maintainReverseRefs, containedPropIndex, propType);  //, DEFAULT_NOT_SET);
    //}

    public MetaProperty(String name, byte historyPolicy, int historySize, Class type, boolean maintainReverseRefs
    		, int containedPropIndex, byte propType, boolean isArray, Object defaultValue, boolean isAtomic)
    {
        this.name = name;
        byte tempOpts = 0;
        if(historyPolicy != 0) tempOpts = BitSet.setBit(OPT_HIST_POLICY, tempOpts);
        this.type = type;
        this.historySize = historySize;
        if(maintainReverseRefs) tempOpts = BitSet.setBit(OPT_RR, tempOpts);
        this.containedPropIndex = containedPropIndex;
        this.propType = propType;
        this.defaultValue = defaultValue;
        if(isArray) tempOpts = BitSet.setBit(OPT_ARR, tempOpts);
        if(historySize == 0 && isAtomic) tempOpts = BitSet.setBit(OPT_ATOMIC_PROPS, tempOpts);
        
        opts = tempOpts;
    }

    public String getName() {
        return name;
    }

    public int getHistoryPolicy() {
        //only works if OPT_HIST_POLICY is 0
        return opts & 1;
    }

    public Class getType() {
        return type;
    }

    public int getHistorySize() {
        return historySize;
    }

    public boolean getMaintainReverseRefs() {
        return BitSet.checkBit(OPT_RR, opts);
    }

    public int getContainedPropIndex() {
        return containedPropIndex;
    }

    protected boolean isArray() {
        return BitSet.checkBit(OPT_ARR, opts);
    }

    private int getPropType() {
        return maskByte(propType);
    }

    public int getRDFTypeId() {
    	int propType = getPropType();
    	switch(propType) {
    		case SM_CONCEPT_INT: return RDFTypes.CONCEPT_TYPEID;
    		case SM_STATE_INT: return RDFTypes.INTEGER_TYPEID;
    		default: return propType; 
    	}
    }
    
    private static int maskByte(byte b) {
        return 0xFF & b;
    }

    public boolean isContainedConcept() {
        return getPropType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT || getPropType() == SM_CONCEPT_INT;
    }

    public boolean isConceptReference() {
        return getPropType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE;
    }

    public boolean isStateMachine() {
        return getPropType() == SM_CONCEPT_INT;
    }

    public PropertyImpl newProperty(ConceptImpl owner, int propertyIndex) {
        return newProperty(owner, propertyIndex, NO_DEFAULT);
    }

    public PropertyImpl newProperty(ConceptImpl owner, int propertyIndex, Object initialValue) {
        PropertyImpl ret;
        switch(getPropType()) {
            case SM_STATE_INT:
                StateMachineConceptImpl smc = ((StateMachineConceptImpl)owner);
                ret = smc.newStateWithIndex(propertyIndex);
                break;
           case SM_CONCEPT_INT:
               if(propertyIndex > INDEX_LIMIT) {
                   ret = new PropertyStateMachineImplBigIndex(owner);
               } else {
                ret = new PropertyStateMachineImpl(owner);
               }
               break;
           default:
               if(isArray()) {
                   if(historySize > 0) {
                       ret = newPArrImpl(owner, 0, propertyIndex > INDEX_LIMIT);
                   } else {
                	   if(BitSet.checkBit(OPT_ATOMIC_PROPS, opts)) {
                		   ret = newPArrAtomic(owner, 0, propertyIndex > INDEX_LIMIT);
                	   } else {
                		   ret = newPArrSimple(owner, 0, propertyIndex > INDEX_LIMIT);
                	   }
                   }
               } else {
                   if(historySize > 0) {
                       ret = newPAtmImpl(owner, initialValue, propertyIndex > INDEX_LIMIT);
                   } else {
                	   if(BitSet.checkBit(OPT_ATOMIC_PROPS, opts)) {
                		   ret = newPAtmAtomic(owner, initialValue, propertyIndex > INDEX_LIMIT);
                	   } else {
                		   ret = newPAtmSimple(owner, initialValue, propertyIndex > INDEX_LIMIT);
                	   }
                   }
               }
        }
        ret.setPropertyIndex(propertyIndex);
        return ret;
    }

    private PropertyAtomImpl newPAtmImpl(ConceptImpl owner, Object initialValue, boolean bigIdx) {
        if(initialValue == NO_DEFAULT) {
            if(bigIdx) {
                return newPAtmImplNoDefaultBigIndex(owner);
            } else {
                return newPAtmImplNoDefault(owner);
            }
        } else {
            if(bigIdx) {
                return newPAtmImplDefaultBigIndex(owner, initialValue);
            } else {
                return newPAtmImplDefault(owner, initialValue);
            }
        }
    }

    private PropertyAtomImpl newPAtmImplNoDefaultBigIndex(ConceptImpl owner) {
        switch(getPropType()) {
            case RDFTypes.STRING_TYPEID:
                return new PropertyAtomStringImplBigIndex(historySize, owner);
            case RDFTypes.INTEGER_TYPEID:
                return new PropertyAtomIntImplBigIndex(historySize, owner);
            case RDFTypes.LONG_TYPEID:
                return new PropertyAtomLongImplBigIndex(historySize, owner);
            case RDFTypes.DOUBLE_TYPEID:
                return new PropertyAtomDoubleImplBigIndex(historySize, owner);
            case RDFTypes.BOOLEAN_TYPEID:
                return new PropertyAtomBooleanImplBigIndex(historySize, owner);
            case RDFTypes.DATETIME_TYPEID:
                return new PropertyAtomDateTimeImplBigIndex(historySize, owner);
            case RDFTypes.CONCEPT_TYPEID:
                return new PropertyAtomContainedConceptImplBigIndex(historySize, owner);
            case RDFTypes.CONCEPT_REFERENCE_TYPEID:
                return new PropertyAtomConceptReferenceImplBigIndex(historySize, owner);
        }
        return null;
    }

    private PropertyAtomImpl newPAtmImplNoDefault(ConceptImpl owner) {
        switch(getPropType()) {
            case RDFTypes.STRING_TYPEID:
                return new PropertyAtomStringImpl(historySize, owner);
            case RDFTypes.INTEGER_TYPEID:
                return new PropertyAtomIntImpl(historySize, owner);
            case RDFTypes.LONG_TYPEID:
                return new PropertyAtomLongImpl(historySize, owner);
            case RDFTypes.DOUBLE_TYPEID:
                return new PropertyAtomDoubleImpl(historySize, owner);
            case RDFTypes.BOOLEAN_TYPEID:
                return new PropertyAtomBooleanImpl(historySize, owner);
            case RDFTypes.DATETIME_TYPEID:
                return new PropertyAtomDateTimeImpl(historySize, owner);
            case RDFTypes.CONCEPT_TYPEID:
                return new PropertyAtomContainedConceptImpl(historySize, owner);
            case RDFTypes.CONCEPT_REFERENCE_TYPEID:
                return new PropertyAtomConceptReferenceImpl(historySize, owner);
        }
        return null;
    }

    private PropertyAtomImpl newPAtmImplDefaultBigIndex(ConceptImpl owner, Object initialValue) {
        switch(getPropType()) {
            case RDFTypes.STRING_TYPEID:
                return new PropertyAtomStringImplBigIndex(historySize, owner, (String)initialValue);
            case RDFTypes.INTEGER_TYPEID:
                return new PropertyAtomIntImplBigIndex(historySize, owner, (Integer)initialValue);
            case RDFTypes.LONG_TYPEID:
                return new PropertyAtomLongImplBigIndex(historySize, owner, (Long)initialValue);
            case RDFTypes.DOUBLE_TYPEID:
                return new PropertyAtomDoubleImplBigIndex(historySize, owner, (Double)initialValue);
            case RDFTypes.BOOLEAN_TYPEID:
                return new PropertyAtomBooleanImplBigIndex(historySize, owner, (Boolean)initialValue);
            case RDFTypes.DATETIME_TYPEID:
                return new PropertyAtomDateTimeImplBigIndex(historySize, owner, (Calendar)initialValue);
            case RDFTypes.CONCEPT_TYPEID:
                return new PropertyAtomContainedConceptImplBigIndex(historySize, owner, (ContainedConcept)initialValue);
            case RDFTypes.CONCEPT_REFERENCE_TYPEID:
                return new PropertyAtomConceptReferenceImplBigIndex(historySize, owner, (Concept)initialValue);
        }
        return null;
    }

    private PropertyAtomImpl newPAtmImplDefault(ConceptImpl owner, Object initialValue) {
        switch(getPropType()) {
            case RDFTypes.STRING_TYPEID:
                return new PropertyAtomStringImpl(historySize, owner, (String)initialValue);
            case RDFTypes.INTEGER_TYPEID:
                return new PropertyAtomIntImpl(historySize, owner, (Integer)initialValue);
            case RDFTypes.LONG_TYPEID:
                return new PropertyAtomLongImpl(historySize, owner, (Long)initialValue);
            case RDFTypes.DOUBLE_TYPEID:
                return new PropertyAtomDoubleImpl(historySize, owner, (Double)initialValue);
            case RDFTypes.BOOLEAN_TYPEID:
                return new PropertyAtomBooleanImpl(historySize, owner, (Boolean)initialValue);
            case RDFTypes.DATETIME_TYPEID:
                return new PropertyAtomDateTimeImpl(historySize, owner, (Calendar)initialValue);
            case RDFTypes.CONCEPT_TYPEID:
                return new PropertyAtomContainedConceptImpl(historySize, owner, (ContainedConcept)initialValue);
            case RDFTypes.CONCEPT_REFERENCE_TYPEID:
                return new PropertyAtomConceptReferenceImpl(historySize, owner, (Concept)initialValue);
        }
        return null;
    }

    private PropertyAtomSimple newPAtmSimple(ConceptImpl owner, Object initialValue, boolean bigIdx) {
        if(initialValue == NO_DEFAULT) {
            if(bigIdx) {
                return newPAtmSimpleNoDefaultBigIndex(owner);
            } else {
                return newPAtmSimpleNoDefault(owner);
            }
        } else {
            if(bigIdx) {
                return newPAtmSimpleDefaultBigIndex(owner, initialValue);
            } else {
                return newPAtmSimpleDefault(owner, initialValue);
            }

        }
    }

    private PropertyAtomSimple newPAtmSimpleNoDefaultBigIndex(ConceptImpl owner) {
        switch(getPropType()) {
            case RDFTypes.STRING_TYPEID:
                return new PropertyAtomStringSimpleBigIndex(owner);
            case RDFTypes.INTEGER_TYPEID:
                return new PropertyAtomIntSimpleBigIndex(owner);
            case RDFTypes.LONG_TYPEID:
                return new PropertyAtomLongSimpleBigIndex(owner);
            case RDFTypes.DOUBLE_TYPEID:
                return new PropertyAtomDoubleSimpleBigIndex(owner);
            case RDFTypes.BOOLEAN_TYPEID:
                return new PropertyAtomBooleanSimpleBigIndex(owner);
            case RDFTypes.DATETIME_TYPEID:
                return new PropertyAtomDateTimeSimpleBigIndex(owner);
            case RDFTypes.CONCEPT_TYPEID:
                return new PropertyAtomContainedConceptSimpleBigIndex(owner);
            case RDFTypes.CONCEPT_REFERENCE_TYPEID:
                return new PropertyAtomConceptReferenceSimpleBigIndex(owner);
        }
        return null;
    }

    private PropertyAtomSimple newPAtmSimpleNoDefault(ConceptImpl owner) {
        switch(getPropType()) {
            case RDFTypes.STRING_TYPEID:
                return new PropertyAtomStringSimple(owner);
            case RDFTypes.INTEGER_TYPEID:
                return new PropertyAtomIntSimple(owner);
            case RDFTypes.LONG_TYPEID:
                return new PropertyAtomLongSimple(owner);
            case RDFTypes.DOUBLE_TYPEID:
                return new PropertyAtomDoubleSimple(owner);
            case RDFTypes.BOOLEAN_TYPEID:
                return new PropertyAtomBooleanSimple(owner);
            case RDFTypes.DATETIME_TYPEID:
                return new PropertyAtomDateTimeSimple(owner);
            case RDFTypes.CONCEPT_TYPEID:
                return new PropertyAtomContainedConceptSimple(owner);
            case RDFTypes.CONCEPT_REFERENCE_TYPEID:
                return new PropertyAtomConceptReferenceSimple(owner);
        }
        return null;
    }

    private PropertyImpl newPAtmAtomic(ConceptImpl owner, Object initialValue, boolean bigIdx) {
    	switch(getPropType()) {
	        case RDFTypes.INTEGER_TYPEID:
	        	if(initialValue == NO_DEFAULT) return new PropertyAtomIntAtomic(owner);
	        	else return new PropertyAtomIntAtomic(owner, (Integer)initialValue);
	        case RDFTypes.LONG_TYPEID:
	        	if(initialValue == NO_DEFAULT) return new PropertyAtomLongAtomic(owner);
	        	else return new PropertyAtomLongAtomic(owner, (Long)initialValue);
	        case RDFTypes.BOOLEAN_TYPEID:
	        	if(initialValue == NO_DEFAULT) return new PropertyAtomBooleanAtomic(owner);
	        	else return new PropertyAtomBooleanAtomic(owner, (Boolean)initialValue);
	        case RDFTypes.DOUBLE_TYPEID:
	        	if(initialValue == NO_DEFAULT) return new PropertyAtomDoubleAtomic(owner);
	        	else return new PropertyAtomDoubleAtomic(owner, (Double)initialValue);
	        default:
	        	return newPAtmSimple(owner, initialValue, bigIdx);
    	}
    }
    
    private PropertyAtomSimple newPAtmSimpleDefaultBigIndex(ConceptImpl owner, Object initialValue) {
        switch(getPropType()) {
            case RDFTypes.STRING_TYPEID:
                return new PropertyAtomStringSimpleBigIndex(owner, (String)initialValue);
            case RDFTypes.INTEGER_TYPEID:
                return new PropertyAtomIntSimpleBigIndex(owner, (Integer)initialValue);
            case RDFTypes.LONG_TYPEID:
                return new PropertyAtomLongSimpleBigIndex(owner, (Long)initialValue);
            case RDFTypes.DOUBLE_TYPEID:
                return new PropertyAtomDoubleSimpleBigIndex(owner, (Double)initialValue);
            case RDFTypes.BOOLEAN_TYPEID:
                return new PropertyAtomBooleanSimpleBigIndex(owner, (Boolean)initialValue);
            case RDFTypes.DATETIME_TYPEID:
                return new PropertyAtomDateTimeSimpleBigIndex(owner, (Calendar)initialValue);
            case RDFTypes.CONCEPT_TYPEID:
                return new PropertyAtomContainedConceptSimpleBigIndex(owner, (ContainedConcept)initialValue);
            case RDFTypes.CONCEPT_REFERENCE_TYPEID:
                return new PropertyAtomConceptReferenceSimpleBigIndex(owner, (Concept)initialValue);
        }
        return null;
    }
    
    private PropertyAtomSimple newPAtmSimpleDefault(ConceptImpl owner, Object initialValue) {
        switch(getPropType()) {
            case RDFTypes.STRING_TYPEID:
                return new PropertyAtomStringSimple(owner, (String)initialValue);
            case RDFTypes.INTEGER_TYPEID:
                return new PropertyAtomIntSimple(owner, (Integer)initialValue);
            case RDFTypes.LONG_TYPEID:
                return new PropertyAtomLongSimple(owner, (Long)initialValue);
            case RDFTypes.DOUBLE_TYPEID:
                return new PropertyAtomDoubleSimple(owner, (Double)initialValue);
            case RDFTypes.BOOLEAN_TYPEID:
                return new PropertyAtomBooleanSimple(owner, (Boolean)initialValue);
            case RDFTypes.DATETIME_TYPEID:
                return new PropertyAtomDateTimeSimple(owner, (Calendar)initialValue);
            case RDFTypes.CONCEPT_TYPEID:
                return new PropertyAtomContainedConceptSimple(owner, (ContainedConcept)initialValue);
            case RDFTypes.CONCEPT_REFERENCE_TYPEID:
                return new PropertyAtomConceptReferenceSimple(owner, (Concept)initialValue);
        }
        return null;
    }

    private PropertyArraySimple newPArrSimple(ConceptImpl owner, int initialSize, boolean bigIndex) {
        if(bigIndex) {
    		return newPArrSimpleBigIndex(owner, initialSize);
        } else {
    		return newPArrSimpleSmallIndex(owner, initialSize);
        }
    }
    private PropertyArraySimple newPArrSimpleSmallIndex(ConceptImpl owner, int initialSize) {
        switch(getPropType()) {
            case RDFTypes.STRING_TYPEID:
                return new PropertyArrayStringSimple(owner, initialSize);
            case RDFTypes.INTEGER_TYPEID:
                return new PropertyArrayIntSimple(owner, initialSize);
            case RDFTypes.LONG_TYPEID:
                return new PropertyArrayLongSimple(owner, initialSize);
            case RDFTypes.DOUBLE_TYPEID:
                return new PropertyArrayDoubleSimple(owner, initialSize);
            case RDFTypes.BOOLEAN_TYPEID:
                return new PropertyArrayBooleanSimple(owner, initialSize);
            case RDFTypes.DATETIME_TYPEID:
                return new PropertyArrayDateTimeSimple(owner, initialSize);
            case RDFTypes.CONCEPT_TYPEID:
                return new PropertyArrayContainedConceptSimple(owner, initialSize);
            case RDFTypes.CONCEPT_REFERENCE_TYPEID:
                return new PropertyArrayConceptReferenceSimple(owner, initialSize);
        }
        return null;
    }

    private PropertyArraySimple newPArrSimpleBigIndex(ConceptImpl owner, int initialSize) {
        switch(getPropType()) {
            case RDFTypes.STRING_TYPEID:
                return new PropertyArrayStringSimpleBigIndex(owner, initialSize);
            case RDFTypes.INTEGER_TYPEID:
                return new PropertyArrayIntSimpleBigIndex(owner, initialSize);
            case RDFTypes.LONG_TYPEID:
                return new PropertyArrayLongSimpleBigIndex(owner, initialSize);
            case RDFTypes.DOUBLE_TYPEID:
                return new PropertyArrayDoubleSimpleBigIndex(owner, initialSize);
            case RDFTypes.BOOLEAN_TYPEID:
                return new PropertyArrayBooleanSimpleBigIndex(owner, initialSize);
            case RDFTypes.DATETIME_TYPEID:
                return new PropertyArrayDateTimeSimpleBigIndex(owner, initialSize);
            case RDFTypes.CONCEPT_TYPEID:
                return new PropertyArrayContainedConceptSimpleBigIndex(owner, initialSize);
            case RDFTypes.CONCEPT_REFERENCE_TYPEID:
                return new PropertyArrayConceptReferenceSimpleBigIndex(owner, initialSize);
        }
        return null;
    }

    private PropertyImpl newPArrAtomic(ConceptImpl owner, int initialSize, boolean bigIndex) {
    	   switch(getPropType()) {
           case RDFTypes.INTEGER_TYPEID:
               return new PropertyArrayIntAtomic(owner, initialSize);
           case RDFTypes.LONG_TYPEID:
               return new PropertyArrayLongAtomic(owner, initialSize);
           case RDFTypes.BOOLEAN_TYPEID:
               return new PropertyArrayBooleanAtomic(owner, initialSize);
           case RDFTypes.DOUBLE_TYPEID:
        	   return new PropertyArrayDoubleAtomic(owner, initialSize);
           default:
    		   return newPArrSimple(owner, initialSize, bigIndex);
       }
    }
    
    private PropertyArrayImpl newPArrImpl(ConceptImpl owner, int initialSize, boolean bigIndex) {
        if(bigIndex) {
            return newPArrImplBigIndex(owner, initialSize);
        } else {
            return newPArrImplSmallIndex(owner, initialSize);
        }
    }
    private PropertyArrayImpl newPArrImplSmallIndex(ConceptImpl owner, int initialSize) {
        switch(getPropType()) {
            case RDFTypes.STRING_TYPEID:
                return new PropertyArrayStringImpl(owner, initialSize);
            case RDFTypes.INTEGER_TYPEID:
                return new PropertyArrayIntImpl(owner, initialSize);
            case RDFTypes.LONG_TYPEID:
                return new PropertyArrayLongImpl(owner, initialSize);
            case RDFTypes.DOUBLE_TYPEID:
                return new PropertyArrayDoubleImpl(owner, initialSize);
            case RDFTypes.BOOLEAN_TYPEID:
                return new PropertyArrayBooleanImpl(owner, initialSize);
            case RDFTypes.DATETIME_TYPEID:
                return new PropertyArrayDateTimeImpl(owner, initialSize);
            case RDFTypes.CONCEPT_TYPEID:
                return new PropertyArrayContainedConceptImpl(owner, initialSize);
            case RDFTypes.CONCEPT_REFERENCE_TYPEID:
                return new PropertyArrayConceptReferenceImpl(owner, initialSize);
        }
        return null;
    }

    private PropertyArrayImpl newPArrImplBigIndex(ConceptImpl owner, int initialSize) {
        switch(getPropType()) {
            case RDFTypes.STRING_TYPEID:
                return new PropertyArrayStringImplBigIndex(owner, initialSize);
            case RDFTypes.INTEGER_TYPEID:
                return new PropertyArrayIntImplBigIndex(owner, initialSize);
            case RDFTypes.LONG_TYPEID:
                return new PropertyArrayLongImplBigIndex(owner, initialSize);
            case RDFTypes.DOUBLE_TYPEID:
                return new PropertyArrayDoubleImplBigIndex(owner, initialSize);
            case RDFTypes.BOOLEAN_TYPEID:
                return new PropertyArrayBooleanImplBigIndex(owner, initialSize);
            case RDFTypes.DATETIME_TYPEID:
                return new PropertyArrayDateTimeImplBigIndex(owner, initialSize);
            case RDFTypes.CONCEPT_TYPEID:
                return new PropertyArrayContainedConceptImplBigIndex(owner, initialSize);
            case RDFTypes.CONCEPT_REFERENCE_TYPEID:
                return new PropertyArrayConceptReferenceImplBigIndex(owner, initialSize);
        }
        return null;
    }

    //public DEFAULT getDefaultValue() {
    //    return defaultValue;
    //}
}
