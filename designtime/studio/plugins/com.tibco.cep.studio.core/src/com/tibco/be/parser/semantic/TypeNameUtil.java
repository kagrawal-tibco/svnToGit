package com.tibco.be.parser.semantic;

import java.util.Iterator;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFUberType;
import com.tibco.be.parser.tree.NodeType;
import com.tibco.be.parser.tree.NodeType.NodeTypeFlag;
import com.tibco.be.parser.tree.NodeType.TypeContext;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.process.ProcessModel;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Mar 7, 2005
 * Time: 6:30:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class TypeNameUtil {
    
    private static String rewriteRDFPrimitiveTypeName(String name) {
        if(name.equals("Double")) return RDFTypes.DOUBLE_WRAP.getName();
        if(name.equals("Integer")) return RDFTypes.INTEGER_WRAP.getName();
        if(name.equals("Long")) return RDFTypes.LONG_WRAP.getName();
        if(name.equals("Boolean")) return RDFTypes.BOOLEAN_WRAP.getName();
        return name;
    }
    
    private static NodeType rdfTypeNameToNodeType(String typeName, boolean isMutable, boolean isProperty, int numBrackets) {
        typeName = rewriteRDFPrimitiveTypeName(typeName);
        NodeType.TypeContext context = isProperty ? TypeContext.PROPERTY_CONTEXT : TypeContext.PRIMITIVE_CONTEXT;
        boolean isArray = numBrackets >= 1;
        if(typeName != null) {
            RDFUberType rdfut = RDFTypes.getType(typeName);
            if(rdfut == null) {
                return NodeType.UNKNOWN;
            }
            
            if(rdfut.equals(RDFTypes.BASE_ENTITY)) {
                return new NodeType(NodeTypeFlag.GENERIC_ENTITY_FLAG, context, isMutable, isArray);
            } else if(rdfut.equals(RDFTypes.BASE_CONCEPT)) {
                return new NodeType(NodeTypeFlag.GENERIC_CONCEPT_FLAG, context, isMutable, isArray);
            } else if(rdfut.equals(RDFTypes.BASE_EVENT)) {
                return new NodeType(NodeTypeFlag.GENERIC_EVENT_FLAG, context, isMutable, isArray);
            } else if(rdfut.equals(RDFTypes.BASE_PROCESS)) {
                return new NodeType(NodeTypeFlag.GENERIC_PROCESS_FLAG, context, isMutable, isArray);

            } else if(rdfut.equals(RDFTypes.CONCEPT)) {
                return new NodeType(NodeTypeFlag.GENERIC_CONTAINED_CONCEPT_FLAG, context, isMutable, isArray);
            } else if(rdfut.equals(RDFTypes.CONCEPT_REFERENCE)) {
                return new NodeType(NodeTypeFlag.GENERIC_CONCEPT_FLAG, context, isMutable, isArray);
            } else if(rdfut.equals(RDFTypes.EVENT)) {
                return new NodeType(NodeTypeFlag.GENERIC_SIMPLE_EVENT_FLAG, context, isMutable, isArray);
            } else if(rdfut.equals(RDFTypes.TIME_EVENT)) {
                return new NodeType(NodeTypeFlag.GENERIC_TIME_EVENT_FLAG, context, isMutable, isArray);
            } else if(rdfut.equals(RDFTypes.EXCEPTION)) {
                return new NodeType(NodeTypeFlag.GENERIC_EXCEPTION_FLAG, context, isMutable, isArray);
            } else if(rdfut.equals(RDFTypes.ADVISORY_EVENT)) {
                return new NodeType(NodeTypeFlag.GENERIC_ADVISORY_EVENT_FLAG, context, isMutable, isArray);

            } else if(rdfut.equals(RDFTypes.BOOLEAN)) {
                return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, context, isMutable, isArray);
            } else if(rdfut.equals(RDFTypes.BOOLEAN_WRAP)) {
                assert(context != TypeContext.PROPERTY_CONTEXT);
                return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.BOXED_CONTEXT, isMutable, isArray);
            } else if(rdfut.equals(RDFTypes.DATETIME)) {
                return new NodeType(NodeTypeFlag.DATETIME_FLAG, context, isMutable, isArray);
            } else if(rdfut.equals(RDFTypes.DOUBLE)) {
                return new NodeType(NodeTypeFlag.DOUBLE_FLAG, context, isMutable, isArray);
            } else if(rdfut.equals(RDFTypes.DOUBLE_WRAP)) {
                assert(context != TypeContext.PROPERTY_CONTEXT);
                return new NodeType(NodeTypeFlag.DOUBLE_FLAG, TypeContext.BOXED_CONTEXT, isMutable, isArray);
            } else if(rdfut.equals(RDFTypes.INTEGER)) {
                return new NodeType(NodeTypeFlag.INT_FLAG, context, isMutable, isArray);
            } else if(rdfut.equals(RDFTypes.INTEGER_WRAP)) {
                assert(context != TypeContext.PROPERTY_CONTEXT);
                return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.BOXED_CONTEXT, isMutable, isArray); 
            } else if(rdfut.equals(RDFTypes.LONG)) {
                return new NodeType(NodeTypeFlag.LONG_FLAG, context, isMutable, isArray);
            } else if(rdfut.equals(RDFTypes.LONG_WRAP)) {
                assert(context != TypeContext.PROPERTY_CONTEXT);
                return new NodeType(NodeTypeFlag.LONG_FLAG, TypeContext.BOXED_CONTEXT, isMutable, isArray);
            } else if(rdfut.equals(RDFTypes.STRING)) {
                return new NodeType(NodeTypeFlag.STRING_FLAG, context, isMutable, isArray);
            } else if(rdfut.equals(RDFTypes.OBJECT)) {
                return new NodeType(NodeTypeFlag.OBJECT_FLAG, context, isMutable, isArray);
            }
        }
        return NodeType.UNKNOWN;
    }
    
    private static NodeType modelTypeNameToNodeType(String typeName, Ontology ontology, boolean isMutable, boolean isProperty, int numBrackets) {
        String path = typeName;
        if(typeName.length() > 0 && typeName.charAt(0) != Folder.FOLDER_SEPARATOR_CHAR) {
            path = ModelUtils.convertPackageToPath(typeName);
        }
        NodeType type = modelPathToNodeType(path, ontology, isMutable, isProperty, numBrackets);
        if(!type.isUnknown()) return type;
        //shortcut name
        type = shortModelNameToNodeType(typeName, ontology, isMutable, numBrackets);
        return type;
    }
    
    private static NodeType modelPathToNodeType(String path, Ontology ontology, boolean isMutable, boolean isProperty, int numBrackets) {
        NodeType.TypeContext context = isProperty ? TypeContext.PROPERTY_CONTEXT : TypeContext.PRIMITIVE_CONTEXT;
        if(ontology != null && path != null) {
            Entity entity = ontology.getEntity(path);
            return entityToNodeType(entity, context, isMutable, numBrackets);
        }
        return NodeType.UNKNOWN;
    }
    
    private static NodeType shortModelNameToNodeType(String name, Ontology ontology, boolean isMutable, int numBrackets) {
    	if(ontology != null && name != null) {
            if(name.length() > 0 && name.charAt(0) == Folder.FOLDER_SEPARATOR_CHAR) {
                name = name.substring(1);
            }
            Entity foundEntity = null;
            
            for(Iterator it = ontology.getEntities().iterator(); it.hasNext();) {
                Entity entity = (Entity)it.next();
                String entityName = entity.getName();
                if(entityName.equals(name)) {
                    //if more than one type has the same name, don't allow shortcut name
                    if(foundEntity != null) return NodeType.UNKNOWN;
                    else foundEntity = entity;
                }
            }
            if(foundEntity != null) {
                return entityToNodeType(foundEntity, TypeContext.PRIMITIVE_CONTEXT, isMutable, numBrackets);
            }
        }
        return NodeType.UNKNOWN;
    }
    
    private static NodeType entityToNodeType(Entity entity, NodeType.TypeContext context, boolean isMutable, int numBrackets) {
        boolean isArray = numBrackets >= 1; 
        if(entity != null) {
            if(entity instanceof ProcessModel) {
                return new NodeType(NodeTypeFlag.PROCESS_FLAG, context, isMutable, isArray, entity.getFullPath());
            }
            else if(entity instanceof Concept) {
                if(((Concept)entity).isAScorecard()) {
                    return new NodeType(NodeTypeFlag.SCORECARD_FLAG, context, isMutable, isArray, entity.getFullPath()); 
                } else if(((Concept)entity).isContained()) {
                    return new NodeType(NodeTypeFlag.CONTAINED_CONCEPT_FLAG, context, isMutable, isArray, entity.getFullPath());    
                } else {
                    return new NodeType(NodeTypeFlag.CONCEPT_FLAG, context, isMutable, isArray, entity.getFullPath());
                }
            }
            else if (entity instanceof Event) {
                if(((Event)entity).getType() == Event.TIME_EVENT) {
                    return new NodeType(NodeTypeFlag.TIME_EVENT_FLAG, context, isMutable, isArray, entity.getFullPath());
                } else {
                    return new NodeType(NodeTypeFlag.SIMPLE_EVENT_FLAG, context, isMutable, isArray, entity.getFullPath());
                }
                //} else if(entity instanceof Instance) {
                //    //instances have the type of the concept
                //    // they are an instace of, rather than a separate type
                //    return new NodeType(NodeTypeFlag.CONCEPT_FLAG, context, false, NameConvert.modelPathToExternalForm(((Instance)entity).getConcept().getFullPath()));
            }
        }
        return NodeType.UNKNOWN;
    }
    
    public static NodeType typeNameToNodeType(String typeName, boolean isMutable, boolean isProperty, int numBrackets) {
        return typeNameToNodeType(typeName, null, isMutable, isProperty, numBrackets);
    }
    //numBrackets is how many trailing "[]"'s there are after the type name
    public static NodeType typeNameToNodeType(String typeName, Ontology ontology, boolean isMutable, boolean isProperty, int numBrackets) {
        NodeType type;
        
        if(typeName == null) return NodeType.UNKNOWN;
        
        if(void.class.getName().equals(typeName)) return NodeType.VOID;
        
        type = rdfTypeNameToNodeType(typeName, isMutable, isProperty, numBrackets);
        if(!type.isUnknown()) return type;
        
        type = modelTypeNameToNodeType(typeName, ontology, isMutable, isProperty, numBrackets);
        return type;
        
    }
}