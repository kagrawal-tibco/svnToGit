package com.tibco.be.parser.semantic;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.parser.tree.NodeType;
import com.tibco.be.parser.tree.NodeType.NodeTypeFlag;
import com.tibco.be.parser.tree.NodeType.TypeContext;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.designtime.model.process.ProcessModel;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Aug 1, 2004
 * Time: 7:46:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class OntologyModelLookup implements ModelLookup {
    protected Ontology ontology;

    public OntologyModelLookup(Ontology ontology) {
        this.ontology = ontology;
    }

    public void reset(Ontology ontology) {
        this.ontology = ontology;
    }

    public NodeType getPropertyType(NodeType parentType, String propertyName) {
        if(parentType == null
        || parentType == NodeType.UNKNOWN
        || parentType.isGeneric()
        || parentType.isArray()) {
            return NodeType.UNKNOWN;
        }

        if(parentType.isConcept()) {
            Concept cept = ontology.getConcept(parentType.getName());
            if(cept == null) cept = getProcessConcept(parentType.getName());
            if(cept == null) {
                return NodeType.UNKNOWN;
            }
            return typeOfPropertyDef(cept.getPropertyDefinition(propertyName, false), false);
        } else if(parentType.isEvent()) {
            Event evt = ontology.getEvent(parentType.getName());
            if(evt == null) {
                return NodeType.UNKNOWN;
            }
            return typeOfEventPropertyDef(evt.getPropertyDefinition(propertyName, true), false);
        } else {
            return NodeType.UNKNOWN;
        }
    }

    //cannot overwrite the array reference of a concept, so array properties are non-mutable
    protected static NodeType typeOfPropertyDef(PropertyDefinition pd, boolean isAttr) {
        if(pd == null) return NodeType.UNKNOWN;

        boolean isPrimitive = pd.getOwner().isPOJO() || isAttr;
        switch(pd.getType()) {
            case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, isPrimitive ? TypeContext.PRIMITIVE_CONTEXT : TypeContext.PROPERTY_CONTEXT, !pd.isArray() && !isAttr, pd.isArray());
            case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
                return new NodeType(NodeTypeFlag.CONTAINED_CONCEPT_FLAG, isPrimitive ? TypeContext.PRIMITIVE_CONTEXT : TypeContext.PROPERTY_CONTEXT, !pd.isArray() && !isAttr, pd.isArray(), pd.getConceptTypePath());
            case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
                return new NodeType(pd.getConceptType().isContained() ? NodeTypeFlag.CONTAINED_CONCEPT_REFERENCE_FLAG : NodeTypeFlag.CONCEPT_REFERENCE_FLAG, isPrimitive ? TypeContext.PRIMITIVE_CONTEXT : TypeContext.PROPERTY_CONTEXT, !pd.isArray() && !isAttr, pd.isArray(), pd.getConceptTypePath());
            case PropertyDefinition.PROPERTY_TYPE_DATETIME:
                return new NodeType(NodeTypeFlag.DATETIME_FLAG, isPrimitive ? TypeContext.PRIMITIVE_CONTEXT : TypeContext.PROPERTY_CONTEXT, !pd.isArray() && !isAttr, pd.isArray());
            case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                return new NodeType(NodeTypeFlag.INT_FLAG, isPrimitive ? TypeContext.PRIMITIVE_CONTEXT : TypeContext.PROPERTY_CONTEXT, !pd.isArray() && !isAttr, pd.isArray());
            case PropertyDefinition.PROPERTY_TYPE_LONG:
                return new NodeType(NodeTypeFlag.LONG_FLAG, isPrimitive ? TypeContext.PRIMITIVE_CONTEXT : TypeContext.PROPERTY_CONTEXT, !pd.isArray() && !isAttr, pd.isArray());
            case PropertyDefinition.PROPERTY_TYPE_REAL:
                return new NodeType(NodeTypeFlag.DOUBLE_FLAG, isPrimitive ? TypeContext.PRIMITIVE_CONTEXT : TypeContext.PROPERTY_CONTEXT, !pd.isArray() && !isAttr, pd.isArray());
            case PropertyDefinition.PROPERTY_TYPE_STRING:
                return new NodeType(NodeTypeFlag.STRING_FLAG, isPrimitive ? TypeContext.PRIMITIVE_CONTEXT : TypeContext.PROPERTY_CONTEXT, !pd.isArray() && !isAttr, pd.isArray());
            case RDFTypes.PROCESS_TYPEID:
                return new NodeType(NodeTypeFlag.PROCESS_FLAG, isPrimitive ? TypeContext.PRIMITIVE_CONTEXT : TypeContext.PROPERTY_CONTEXT, !pd.isArray() && !isAttr, pd.isArray(), pd.getConceptTypePath());
            default:
                return NodeType.UNKNOWN;
        }
    }

    //todo replace with a lookup based on RDF names?
    protected static NodeType typeOfEventPropertyDef(EventPropertyDefinition propDef, boolean isAttr) {
        if(propDef == null) return NodeType.UNKNOWN;
        RDFPrimitiveTerm rdfTerm = propDef.getType();
            if(RDFTypes.BOOLEAN.equals(rdfTerm)) {
                return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.PRIMITIVE_CONTEXT, !isAttr);
            } else if(RDFTypes.INTEGER.equals(rdfTerm)) {
                return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PRIMITIVE_CONTEXT, !isAttr);
            } else if(RDFTypes.LONG.equals(rdfTerm)) {
                return new NodeType(NodeTypeFlag.LONG_FLAG, TypeContext.PRIMITIVE_CONTEXT, !isAttr);
            } else if(RDFTypes.DOUBLE.equals(rdfTerm)) {
                return new NodeType(NodeTypeFlag.DOUBLE_FLAG, TypeContext.PRIMITIVE_CONTEXT, !isAttr);
            } else if(RDFTypes.STRING.equals(rdfTerm)) {
                return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, !isAttr);
            } else if(RDFTypes.DATETIME.equals(rdfTerm)) {
                return new NodeType(NodeTypeFlag.DATETIME_FLAG, TypeContext.PRIMITIVE_CONTEXT, !isAttr);
            }
        return NodeType.UNKNOWN;
    }

    protected Concept getProcessConcept(String path) {
        Entity e = ontology.getEntity(path);
        if(e instanceof Concept) {
            return (Concept)e;
        } else if(e instanceof ProcessModel) {
            return ((ProcessModel)e).cast(Concept.class);
        }
        return null;
    }

    protected ProcessModel getProcessModel(String path) {
        Entity e = ontology.getEntity(path);
        if(e instanceof ProcessModel) {
            return (ProcessModel) e;
        }
        return null;
    }

    //tests if a inherits from b, ie a isA b
    public boolean isA(NodeType a, NodeType b) {
        if(a.isArray() != b.isArray()) return false;
        if(a != null && b != null && !a.isUnknown() && !b.isUnknown()) {
            if(a.isConcept() && b.isConcept()) {
                Concept A = ontology.getConcept(a.getName());
                if(A == null) A = getProcessConcept(a.getName());
                Concept B = ontology.getConcept(b.getName());
                if(B == null) B = getProcessConcept(b.getName());

                if(A != null && B != null) {
                    return A.isA(B);
                }
            } else if(a.isEvent() && b.isEvent()) {
                Event A = ontology.getEvent(a.getName());
                Event B = ontology.getEvent(b.getName());
                if(A != null && B != null) {
                    return A.isA(B);
                }
            }
        }
        return false;
    }

    //Returns NodeType.UNKNOWN if the lookup failed
    public NodeType getAttributeType(NodeType parentType, String attributeName) {
        if(!parentType.isGeneric() && !parentType.isArray()) {
            //isConcept() is also true for processes but ontology.getConcept will return null for process paths
//            if(parentType.isProcess()) {
//                ProcessModel pm = getProcessModel(parentType.getName());
//                if(pm != null) {
//                    PropertyDefinition attr = pm.getAttributeDefinition(attributeName);
//                    return typeOfPropertyDef(attr, true);
//                }
//            }
//            else 
            	if(parentType.isConcept()) {
                Concept c = ontology.getConcept(parentType.getName());
                if(c == null) c= getProcessConcept(parentType.getName());
                PropertyDefinition attr = c.getAttributeDefinition(attributeName);
                return typeOfPropertyDef(attr, true);
            }
            else if(parentType.isEvent()) {
                Event e = ontology.getEvent(parentType.getName());
                EventPropertyDefinition epd = e.getAttributeDefinition(attributeName);
                return typeOfEventPropertyDef(epd, true);
            }
        }
        return NodeType.UNKNOWN;
    }

    public NodeType getTypeFromName(String name, boolean isMutable, boolean isProperty, int numBrackets) {
        return TypeNameUtil.typeNameToNodeType(name, ontology, isMutable, isProperty, numBrackets);
    }
}