package com.tibco.be.parser.semantic;

import com.tibco.be.parser.codegen.CGConstants;
import com.tibco.be.parser.codegen.CGUtil;
import com.tibco.be.parser.tree.NodeType;
import com.tibco.be.parser.tree.NodeType.NodeTypeFlag;
import com.tibco.be.parser.tree.NodeType.TypeContext;
import com.tibco.cep.designtime.model.process.ProcessModel;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Oct 17, 2004
 * Time: 6:45:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuiltinLookup implements ModelLookup {
    //Returns NodeType.UNKNOWN if the lookup failed
    public NodeType getPropertyType(NodeType parentType, String propertyName) {
        return NodeType.UNKNOWN;
    }

    public boolean isA(NodeType a, NodeType b) {
        //todo this isn't true for generic property
        //this doens't work for something like PropertyAtomInt because it assumes that all property objects will be unboxed into primitives
        //the final three tests are for primitives that are always java objects, even when not boxed.
        if(b.isObject() && (!CGUtil.isJavaPrimitive(a) || a.isArray())) return true;
        if(a.isArray() != b.isArray()) return false;
        //deal with the fact that while ContainedConcept isA ConceptReference
        //PropertyXYZContainedConcept is NOT A PropertyXYZConceptReference
        //Check that b is a generic property so that this only
        //is checked for function arguments where generic properties are treated as 
        //property objects and not unboxed values
        if(a.hasPropertyContext() && a.isContainedConcept() && !a.isContainedConceptReference() && b.hasPropertyContext() && b.isGeneric()  && b.isConceptReference()) return false;
        if(a.isEntity() && b.isGenericEntity()) return true;
        if(a.isProcess() && b.isGenericProcess()) return true;
        if(a.isConcept() && b.isGenericConcept()) return true;
        if(a.isConcept() && b.isGenericConceptReference()) return true;
        if(a.isContainedConcept() && b.isGenericContainedConcept()) return true;
        if(a.isEvent() && b.isGenericEvent()) return true;
        if(a.isSimpleEvent() && b.isGenericSimpleEvent()) return true;
        if(a.isAdvisoryEvent() && b.isGenericAdvisoryEvent()) return true;
        if(a.isTimeEvent() && b.isGenericTimeEvent()) return true;
        if(a.isException() && b.isGenericException()) return true;
        return false;
    }

    //Returns NodeType.UNKNOWN if the lookup failed
    public NodeType getAttributeType(NodeType parentType, String attributeName) {
        if(parentType.isArray()) {
            if(attributeName.equals(CGConstants.lengthAttrName)) {
                return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
            }
        }

        if(parentType.hasPropertyContext()) {
            if(attributeName.equals(CGConstants.propertyHistorySizeName)) {
                return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
            }
            if(attributeName.equals(CGConstants.propertySubjectAttrName)) {
                //todo return specific concept type in OntologyModelLookup?
                return new NodeType(NodeTypeFlag.GENERIC_CONCEPT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
            }
                
            if(!parentType.isArray()) {
                if(attributeName.equals(CGConstants.propertyAtomIsSetAttrName)) {
                    return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
                }
            }
        }
        
        if(parentType.isString() && !parentType.isArray()) {
            if(attributeName.equals(CGConstants.lengthAttrName)) {
                return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
            }
        }
        
        //these attributes are handled by OntologyModelLookup for non-generic types
        if(parentType.isGeneric()) {
            if(parentType.isEntity() && !parentType.isArray()) {
                if(attributeName.equals(CGConstants.entityExtIdAttrName)) {
                    return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
                } else if(attributeName.equals(CGConstants.entityIdAttrName)) {
                    return new NodeType(NodeTypeFlag.LONG_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
                }
                if(parentType.isContainedConcept()) {
                    if(attributeName.equals(CGConstants.containedConceptParentAttrName)) {
                        return new NodeType(NodeTypeFlag.GENERIC_CONCEPT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
                    }
                }
                
                if(parentType.isEvent()) {
                    if(attributeName.equals(CGConstants.eventTtlAttrName)) {
                        return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
                    }

                    if(parentType.isSimpleEvent()) {
                        if(attributeName.equals(CGConstants.simpleEventPayloadAttrName)) {
                            return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
                        }
                    }
                    if(parentType.isTimeEvent()) {
                        if(attributeName.equals(CGConstants.timeEventClosureAttrName)) {
                            return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
                        }
                        if(attributeName.equals(CGConstants.timeEventScheduledTimeAttrName)) {
                            return new NodeType(NodeTypeFlag.DATETIME_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
                        }
                        if(attributeName.equals(CGConstants.timeEventIntervalAttrName)) {
                            return new NodeType(NodeTypeFlag.LONG_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
                        }
                    }
                    if(parentType.isAdvisoryEvent()) {
                        if(attributeName.equals(CGConstants.advisoryEventCategoryAttrName)) {
                            return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
                        }
                        if(attributeName.equals(CGConstants.advisoryEventMessageAttrName)) {
                            return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
                        }
                        if(attributeName.equals(CGConstants.advisoryEventTypeAttrName)) {
                            return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
                        }
                        if(attributeName.equals(CGConstants.advisoryEventRuleUriAttrName)) {
                            return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
                        }
                        if(attributeName.equals(CGConstants.advisoryEventRuleScopeAttrName)) {
                            return new NodeType(NodeTypeFlag.OBJECT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, true);
                        }
                    }
                }
                if(parentType.isProcess()) {
                    ProcessModel.BASE_ATTRIBUTES attr = ProcessModel.BASE_ATTRIBUTES.valueOf(attributeName);
                    if(attr != null) {
                        return new NodeType(NodeType.rdfTypeIdToFlag(attr.getRdfTypeId(), true)
                                , TypeContext.PRIMITIVE_CONTEXT, false);
                    }
                }
            } else if(parentType.isException()) {
                if(!parentType.isArray()) {
                    if(attributeName.equals(CGConstants.beExceptionMessageAttrName)) {
                        return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
                    }
                    if(attributeName.equals(CGConstants.beExceptionErrorTypeAttrName)) {
                        return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
                    }
                    if(attributeName.equals(CGConstants.beExceptionStackTraceAttrName)) {
                        return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
                    }
                    if(attributeName.equals(CGConstants.beExceptionCauseAttrName)) {
                        return new NodeType(NodeTypeFlag.GENERIC_EXCEPTION_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
                    }
                }
            }
        }
        return NodeType.UNKNOWN;
    }

    public NodeType getTypeFromName(String name, boolean isMutable, boolean isProperty, int numBrackets) {
        return TypeNameUtil.typeNameToNodeType(name, isMutable, isProperty, numBrackets);
    }
}
