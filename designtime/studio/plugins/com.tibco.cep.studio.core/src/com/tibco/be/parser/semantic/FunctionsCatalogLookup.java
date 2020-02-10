package com.tibco.be.parser.semantic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.PredicateWithXSLT;
import com.tibco.be.model.functions.VariableList;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.ModelFunction;
import com.tibco.be.parser.tree.NodeType;
import com.tibco.be.parser.tree.NodeType.NodeTypeFlag;
import com.tibco.be.parser.tree.NodeType.TypeContext;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.event.EVENT_TYPE;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyArrayBoolean;
import com.tibco.cep.runtime.model.element.PropertyArrayConcept;
import com.tibco.cep.runtime.model.element.PropertyArrayConceptReference;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyArrayDateTime;
import com.tibco.cep.runtime.model.element.PropertyArrayDouble;
import com.tibco.cep.runtime.model.element.PropertyArrayInt;
import com.tibco.cep.runtime.model.element.PropertyArrayLong;
import com.tibco.cep.runtime.model.element.PropertyArrayString;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomBoolean;
import com.tibco.cep.runtime.model.element.PropertyAtomConcept;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtomDateTime;
import com.tibco.cep.runtime.model.element.PropertyAtomDouble;
import com.tibco.cep.runtime.model.element.PropertyAtomInt;
import com.tibco.cep.runtime.model.element.PropertyAtomLong;
import com.tibco.cep.runtime.model.element.PropertyAtomString;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.exception.BEException;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Aug 1, 2004
 * Time: 11:50:12 PM
 * To change this template use File | Settings | File Templates.
 */

public class FunctionsCatalogLookup implements FunctionLookup {
	
	private String fProjectName;

    public FunctionsCatalogLookup(String projectName) {
    	this.fProjectName = projectName;
    	FunctionsCatalogManager.getInstance().getOntologyCategory(projectName); // make sure these ontology functions are loaded
    }

    public static final String SHOW_COMPILER_WARNINGS = "tibco.be.showCompilerWarnings";

    //funcName may be either fully specified (Category.name) or a single identifier (no preceeding dots)
    public FunctionRec lookupFunction(String funcName, NodeType[] argTypes) {
        if(FunctionsCatalogManager.getInstance().getStaticRegistry() == null) return null;

        boolean showWarnings = Boolean.getBoolean(SHOW_COMPILER_WARNINGS);

        boolean bypassPath=true;
        if (funcName.indexOf('.') > 0) {
            bypassPath=false;
        }

        Predicate p=null;

        Object match= lookupStaticFunction(funcName, bypassPath);//registry.lookup(funcName, bypassPath);
        if (match == null) {
        	match = lookupOntologyFunction(funcName, bypassPath);
        }
        if (match == null) {
        	match = lookupCustomFunction(funcName, bypassPath);
        }
        if ((match != null) && (match instanceof Predicate)) {
            p = (Predicate) match;
        }
        //If there are multiple matches then probably a short, single identifier name was used
        //and multiple functions match that name.  In this case no match will
        //be returned unless one of the functions is in the root folder, since
        //the full name of these functions is a single indentifier.
        else if(match instanceof Collection) {
            Collection c = (Collection) match;
            Iterator it = c.iterator();
            while(it.hasNext()) {
                p = (Predicate) it.next();
                if(ModelUtils.IsEmptyString(p.getName().getNamespaceURI())) {
                    
                    if(showWarnings) System.out.println("Ambiguous function '" + funcName + "'.  Using '/" + funcName + "()'" + ".");
                    break;
                }
                else p = null;
            }
        }

        if (p == null) {
        	// try the EMF Model Functions
        }
        
        if(p == null) return null;
        
        return makeFunctionRec(p);
    }
    
    private Object lookupCustomFunction(String funcName, boolean bypassPath) {
    	FunctionsCatalog registry = null;
		try {
			registry = FunctionsCatalogManager.getInstance().getCustomRegistry(fProjectName);
		} catch (Exception e) {
		}
    	if (registry == null) {
    		return null;
    	}
    	List ret= new ArrayList();
    	Iterator catalogs = registry.catalogs();
    	while (catalogs.hasNext()) {
    		FunctionsCategory cat= (FunctionsCategory) catalogs.next();
    		Object retCat=cat.lookup(funcName, bypassPath);
    		if (retCat != null) {
    			if (!bypassPath) {
    				return retCat;
    			} else {
    				if (retCat instanceof Collection) {
    					ret.addAll((Collection) retCat);
    				} else {
    					ret.add(retCat);
    				}
    			}
    		}
    	}
    	if (ret.size() == 0) {
    		return null;
    	}
    	if (ret.size() == 1) {
    		return ret.get(0);
    	}
    	return ret;
	}

	private Object lookupStaticFunction(String functionName, boolean doUnQualifiedSearch) {
    	// copied from FunctionsCatalog::lookup(), as we cannot search the Ontology category here
    	List ret= new ArrayList();
    	Iterator all = FunctionsCatalogManager.getInstance().getStaticRegistry().catalogs();

    	while (all.hasNext()) {

    		FunctionsCategory cat= (FunctionsCategory) all.next();
    		if ("Ontology".equals(cat.getName().localName)) {
    			// do not look in Ontology category, those exist in the ontology_registry
    			continue;
    		}
    		Object retCat=cat.lookup(functionName, doUnQualifiedSearch);
    		if (retCat != null) {
    			if (!doUnQualifiedSearch) {
    				return retCat;
    			} else {
    				if (retCat instanceof Collection) {
    					ret.addAll((Collection) retCat);
    				} else {
    					ret.add(retCat);
    				}
    			}
    		}
    	}
    	if (ret.size() == 0) {
    		return null;
    	}
    	if (ret.size() == 1) {
    		return ret.get(0);
    	}
    	return ret;
    }

	private Object lookupOntologyFunction(String funcName, boolean bypassPath) {
    	FunctionsCategory catalog = FunctionsCatalogManager.getInstance().getOntologyCategory(fProjectName);
    	if (catalog != null) {
    		return catalog.lookup(funcName, bypassPath);
    	}
		return null;
	}

	public FunctionRec makeFunctionRec(Predicate p) {
        //todo enforce in registry
        //a function flagged as a mapper function will be given mapper arguments
        //if it doesn't actually take the expected mapper arguments there will be a javac error
        if(isMapperFn(p) && !hasMapperArgs(p)) {
            return null;
        }

        FunctionRec funcRec = new FunctionRec();
        funcRec.function = p;
        initArgAndReturnTypes(funcRec);
        funcRec.isMapper = isMapperFn(p);
        //if(!doesFunctionMatch(funcRec, argTypes)) {
        //    funcRec.returnType = NodeType.UNKNOWN;
        //}
        funcRec.thrownExceptions = p.getThrownExceptions();
        return funcRec;
    }

    protected boolean isMapperFn(Predicate predicate) {
        return predicate instanceof PredicateWithXSLT;
    }
    protected boolean isModelFn(Predicate predicate) {
        return predicate instanceof ModelFunction;
    }
    private static Class[] mapperArgClasses = new Class[] {String.class, String.class, VariableList.class};
    protected boolean hasMapperArgs(Predicate mf) {
        Class[] fnArgClasses = mf.getArguments();
        if(fnArgClasses.length != mapperArgClasses.length) {
            return false;
        }

        for(int ii = 0; ii < fnArgClasses.length; ii++) {
            if(!fnArgClasses[ii].equals(mapperArgClasses[ii])) {
                return false;
            }
        }
        return true;
    }
    /*
    //returns true if method was successfully matched, false otherwise.
    protected boolean doesFunctionMatch(FunctionRec fnRec, NodeType[] userArgTypes) {
    Class[] fnArgClasses = fnRec.function.getArguments();
    //Verify that a function that is declared as a mapper function
    //has the correct signature.  The arguments generated by the
    //mapper dialog always are of the types in mapperArgClasses
    //and are not type checked since they are assumed to be correct.
    if(isMapperFn(fnRec.function)) {
    if(fnArgClasses.length != mapperArgClasses.length) {
    return false;
    }

    for(int ii = 0; ii < fnArgClasses.length; ii++) {
    if(!fnArgClasses[ii].equals(mapperArgClasses[ii])) {
    return false;
    }
    }
    } else {
    if(userArgTypes.length != fnArgClasses.length) {
    return false;
    }

    for(int ii = 0; ii < userArgTypes.length; ii++) {
    if(!argIsCompatible(fnRec.argTypes[ii], userArgTypes[ii])) {
    return false;
    }
    }
    }
    return true;
    }
    */

    protected void initArgAndReturnTypes(FunctionRec fnRec) {
        Predicate p = fnRec.function;

        fnRec.argTypes = new NodeType[p.getArguments().length];
        for(int ii = 0; ii < fnRec.argTypes.length; ii++) {
            fnRec.argTypes[ii] = classToNodeType(p.getArguments()[ii]);
        }

        fnRec.returnType = classToNodeType(p.getReturnClass());
        
        if(p.isVarargsCodegen()) {
        	fnRec.varargsCodegenArgType = classToNodeType(p.getVarargsCodegenArgType());
        }


        if(isModelFn(p)) {
            initModelArgAndReturnTypes(fnRec);
        }
    }

    protected void initModelArgAndReturnTypes(FunctionRec fnRec) {
        ModelFunction mf = (ModelFunction)fnRec.function;
        com.tibco.cep.designtime.core.model.Entity[] entityArgs = (com.tibco.cep.designtime.core.model.Entity[]) mf.getEntityArguments();
        NodeType[] fnArgs = fnRec.argTypes;
      
        // for each entity type in fnArgs set the model path etc to what is in
		// the corresponding entityArgs element
		for (int entityIdx = 0; entityIdx < entityArgs.length; entityIdx++) {
			// if the entityArgs array has a non null entry, then this entity
			// argument is a user created model type
			if (entityArgs[entityIdx] != null) {
				fnArgs[entityIdx] = modelEntityType(
						mf.getArguments()[entityIdx],
						(com.tibco.cep.designtime.core.model.Entity) entityArgs[entityIdx]);
			}
		}
        
        //handle the return type
        if(mf.getEntityReturnType() != null) {
            fnRec.returnType = modelEntityType(mf.getReturnClass(), (com.tibco.cep.designtime.core.model.Entity) mf.getEntityReturnType());
        }
    }

    //ModelFunctions may restrict arguments to specific types in the ontology
    //via the array returned by getEntityArguments.  This array has an entry corresponding to each
    //Entity type in the array returned by getArguments().  For an argument A from getArguments, and its
    //corresponding entry from getEntityArguments E, if E is null, then A will continue to be treated as
    //its default generic type.  Otherwise, A will be treated as having the specific (generated class) type 
    //of the Entity object E.
    protected NodeType modelEntityType(Class declaredType, com.tibco.cep.designtime.core.model.Entity modelType) {
        if(PropertyAtomConcept.class.isAssignableFrom(declaredType)) {
            if(modelType == null) return classToNodeType(declaredType);
            else return entityToNodeType(modelType, false, true);
        } else if(PropertyArrayConcept.class.isAssignableFrom(declaredType)) {
            if(modelType == null) return classToNodeType(declaredType);
            return entityToNodeType(modelType, true, true);
        } else if(Entity.class.isAssignableFrom(declaredType)) {
            if(modelType == null) return classToNodeType(declaredType);
            return entityToNodeType(modelType, false, false);
        } else if(Entity[].class.isAssignableFrom(declaredType)) {
            if(modelType == null) return classToNodeType(declaredType);
            return entityToNodeType(modelType, true, false);
        } else {
            //todo was it safe to switch from returning null to returning UNKNOWN?
            //return null;
            return NodeType.UNKNOWN;
        }
    }

    protected NodeType classToNodeType(Class clazz) {
    	return classToNodeType(clazz, true);
    }
    
    protected NodeType classToNodeType(Class clazz, boolean useLocalClassloader) {
    	if (clazz == null) {
    		return NodeType.UNKNOWN; // error -- perhaps due to invalid/corrupt source code
    	}
    	
    	boolean isArray = false;
    	Class comp = clazz.getComponentType();
    	if(comp != null) {
    		isArray = true;
    		//don't support multi-dimensional arrays
    		if(comp.getComponentType() != null) {
    			return NodeType.UNKNOWN;
    		}
    	} else {
    		comp = clazz;
    	}
    	
        if(comp.equals(boolean.class)) {
            return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, isArray);
        } else if(comp.equals(double.class)) {
            return new NodeType(NodeTypeFlag.DOUBLE_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, isArray);
        } else if(comp.equals(int.class)) {
            return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, isArray);
        } else if(comp.equals(long.class)) {
            return new NodeType(NodeTypeFlag.LONG_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, isArray);
        } else if(comp.equals(Boolean.class)) {
                return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.BOXED_CONTEXT, false, isArray);
        } else if(comp.equals(Double.class)) {
            return new NodeType(NodeTypeFlag.DOUBLE_FLAG, TypeContext.BOXED_CONTEXT, false, isArray);
        } else if(comp.equals(Integer.class)) {
            return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.BOXED_CONTEXT, false, isArray);
        } else if(comp.equals(Long.class)) {
            return new NodeType(NodeTypeFlag.LONG_FLAG, TypeContext.BOXED_CONTEXT, false, isArray);
        } else if(comp.equals(String.class)) {
            return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, isArray);
        } else if(comp.equals(java.util.Calendar.class)) {
            return new NodeType(NodeTypeFlag.DATETIME_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, isArray);
        } else if(comp.equals(void.class)) {
            return NodeType.VOID;
        } else if(comp.equals(Object.class)) {
            return new NodeType(NodeTypeFlag.OBJECT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, isArray);
        }

        else if(comp.equals(Entity.class)) {
            return new NodeType(NodeTypeFlag.GENERIC_ENTITY_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, isArray);
        } else if(comp.equals(Concept.class)) {
            return new NodeType(NodeTypeFlag.GENERIC_CONCEPT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, isArray);
        } else if(comp.equals(Metric.class)) {
        	return new NodeType(NodeTypeFlag.GENERIC_CONCEPT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, isArray);
        } else if(comp.equals(ContainedConcept.class)) {
            return new NodeType(NodeTypeFlag.GENERIC_CONTAINED_CONCEPT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, isArray);
        } else if(comp.equals(Event.class)) {
            return new NodeType(NodeTypeFlag.GENERIC_EVENT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, isArray);
        } else if(comp.equals(TimeEvent.class)) {
            return new NodeType(NodeTypeFlag.GENERIC_TIME_EVENT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, isArray);
        } else if(comp.equals(SimpleEvent.class)) {
            return new NodeType(NodeTypeFlag.GENERIC_SIMPLE_EVENT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, isArray);
        } else if (comp.equals(AdvisoryEvent.class)) {
            return new NodeType(NodeTypeFlag.GENERIC_ADVISORY_EVENT_FLAG, TypeContext.PRIMITIVE_CONTEXT,  false, isArray);
        } else if(comp.equals(BEException.class)) {
            return new NodeType(NodeTypeFlag.GENERIC_EXCEPTION_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, isArray);
        }

        //use primitive context for generic Property to support Property[]
        else if(comp.equals(Property.class)) {
            return new NodeType(NodeTypeFlag.GENERIC_PROP_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, isArray);
        } 

        //don't support arrays for the Property classes
        if(!isArray) {
	        if(clazz.equals(PropertyAtom.class)) {
	            return new NodeType(NodeTypeFlag.GENERIC_PROP_ATOM_FLAG, TypeContext.PROPERTY_CONTEXT, false);
	        } else if(clazz.equals(PropertyArray.class)) {
	            return new NodeType(NodeTypeFlag.GENERIC_PROP_ARRAY_FLAG, TypeContext.PROPERTY_CONTEXT, false, true);
	        }
	
	        //todo how to get these to be compatible with arrays AND non-arrays
	        else if(clazz.equals(Property.PropertyBoolean.class)) {
	            return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.PROPERTY_CONTEXT, false);
	        } else if(clazz.equals(Property.PropertyDouble.class)) {
	            return new NodeType(NodeTypeFlag.DOUBLE_FLAG, TypeContext.PROPERTY_CONTEXT, false);
	        } else if(clazz.equals(Property.PropertyInt.class)) {
	            return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PROPERTY_CONTEXT, false);
	        } else if(clazz.equals(Property.PropertyLong.class)) {
	            return new NodeType(NodeTypeFlag.LONG_FLAG, TypeContext.PROPERTY_CONTEXT, false);
	        } else if(clazz.equals(Property.PropertyString.class)) {
	            return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PROPERTY_CONTEXT, false);
	        } else if(clazz.equals(Property.PropertyDateTime.class)) {
	            return new NodeType(NodeTypeFlag.DATETIME_FLAG, TypeContext.PROPERTY_CONTEXT, false);
	        } else if(clazz.equals(Property.PropertyConcept.class)) {
	            return new NodeType(NodeTypeFlag.GENERIC_CONCEPT_FLAG, TypeContext.PROPERTY_CONTEXT, false);
	        } else if(clazz.equals(Property.PropertyConceptReference.class)) {
	            return new NodeType(NodeTypeFlag.GENERIC_CONCEPT_REFERENCE_FLAG, TypeContext.PROPERTY_CONTEXT, false);
	        } else if(clazz.equals(Property.PropertyContainedConcept.class)) {
	            return new NodeType(NodeTypeFlag.GENERIC_CONTAINED_CONCEPT_FLAG, TypeContext.PROPERTY_CONTEXT, false);
	        }
	
	        else if(clazz.equals(PropertyAtomBoolean.class)) {
	            return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.PROPERTY_CONTEXT, false, false);
	        } else if(clazz.equals(PropertyAtomDouble.class)) {
	            return new NodeType(NodeTypeFlag.DOUBLE_FLAG, TypeContext.PROPERTY_CONTEXT, false, false);
	        } else if(clazz.equals(PropertyAtomInt.class)) {
	            return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PROPERTY_CONTEXT, false, false);
	        } else if(clazz.equals(PropertyAtomLong.class)) {
	            return new NodeType(NodeTypeFlag.LONG_FLAG, TypeContext.PROPERTY_CONTEXT, false, false);
	        } else if(clazz.equals(PropertyAtomString.class)) {
	            return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PROPERTY_CONTEXT, false, false);
	        } else if(clazz.equals(PropertyAtomDateTime.class)) {
	            return new NodeType(NodeTypeFlag.DATETIME_FLAG, TypeContext.PROPERTY_CONTEXT, false, false);
	        } else if(clazz.equals(PropertyAtomConcept.class)) {
	            return new NodeType(NodeTypeFlag.GENERIC_CONCEPT_FLAG, TypeContext.PROPERTY_CONTEXT, false, false);
	        } else if(clazz.equals(PropertyAtomConceptReference.class)) {
	            return new NodeType(NodeTypeFlag.GENERIC_CONCEPT_REFERENCE_FLAG, TypeContext.PROPERTY_CONTEXT, false, false);
	        } else if(clazz.equals(PropertyAtomContainedConcept.class)) {
	            return new NodeType(NodeTypeFlag.GENERIC_CONTAINED_CONCEPT_FLAG, TypeContext.PROPERTY_CONTEXT, false, false);
	        }
	
	        else if(clazz.equals(PropertyArrayBoolean.class)) {
	            return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.PROPERTY_CONTEXT, false, true);
	        } else if(clazz.equals(PropertyArrayDouble.class)) {
	            return new NodeType(NodeTypeFlag.DOUBLE_FLAG, TypeContext.PROPERTY_CONTEXT, false, true);
	        } else if(clazz.equals(PropertyArrayInt.class)) {
	            return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PROPERTY_CONTEXT, false, true);
	        } else if(clazz.equals(PropertyArrayLong.class)) {
	            return new NodeType(NodeTypeFlag.LONG_FLAG, TypeContext.PROPERTY_CONTEXT, false, true);
	        } else if(clazz.equals(PropertyArrayString.class)) {
	            return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PROPERTY_CONTEXT, false, true);
	        } else if(clazz.equals(PropertyArrayDateTime.class)) {
	            return new NodeType(NodeTypeFlag.DATETIME_FLAG, TypeContext.PROPERTY_CONTEXT, false, true);
	        } else if(clazz.equals(PropertyArrayConcept.class)) {
	            return new NodeType(NodeTypeFlag.GENERIC_CONCEPT_FLAG, TypeContext.PROPERTY_CONTEXT, false, true);
	        } else if(clazz.equals(PropertyArrayConceptReference.class)) {
	            return new NodeType(NodeTypeFlag.GENERIC_CONCEPT_REFERENCE_FLAG, TypeContext.PROPERTY_CONTEXT, false, true);
	        } else if(clazz.equals(PropertyArrayContainedConcept.class)) {
	            return new NodeType(NodeTypeFlag.GENERIC_CONTAINED_CONCEPT_FLAG, TypeContext.PROPERTY_CONTEXT, false, true);
	        }
        }
        if (useLocalClassloader) {
        	// the equality check could have failed due to comp having a different classloader
        	// try to use the current classloader to load the class and re-check
            try {
				comp = Class.forName(clazz.getName());
				return classToNodeType(comp, false);
			} catch (ClassNotFoundException e) {
				// ignore and continue
			}
        }
        return NodeType.UNKNOWN;
    }

    protected NodeType entityToNodeType(com.tibco.cep.designtime.core.model.Entity e, boolean isArray, boolean isProperty) {
        if(e instanceof com.tibco.cep.designtime.core.model.element.Concept) {
            com.tibco.cep.designtime.core.model.element.Concept c = (com.tibco.cep.designtime.core.model.element.Concept)e;
            if(c.isContained()) {
                return new NodeType(NodeTypeFlag.CONTAINED_CONCEPT_FLAG, isProperty? TypeContext.PROPERTY_CONTEXT : TypeContext.PRIMITIVE_CONTEXT, false, isArray, c.getFullPath());
            } else if(isProperty) {
                return new NodeType(NodeTypeFlag.CONCEPT_REFERENCE_FLAG, TypeContext.PROPERTY_CONTEXT, false, isArray, c.getFullPath());
            } else {
                return new NodeType(NodeTypeFlag.CONCEPT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, isArray, c.getFullPath());
            }
        } else if (e instanceof com.tibco.cep.designtime.core.model.event.Event) {
            com.tibco.cep.designtime.core.model.event.Event ev = (com.tibco.cep.designtime.core.model.event.Event)e;

            if(ev.getType() == EVENT_TYPE.TIME_EVENT) {
                return new NodeType(NodeTypeFlag.TIME_EVENT_FLAG, isProperty? TypeContext.PROPERTY_CONTEXT : TypeContext.PRIMITIVE_CONTEXT, false, isArray, ev.getFullPath());
            } else {
                return new NodeType(NodeTypeFlag.SIMPLE_EVENT_FLAG, isProperty? TypeContext.PROPERTY_CONTEXT : TypeContext.PRIMITIVE_CONTEXT, false, isArray, ev.getFullPath());
            }
        } else {
            return NodeType.UNKNOWN;
        }
    }

}