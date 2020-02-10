package com.tibco.cep.studio.core.functions.model;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.tibco.be.model.functions.BEFunction;
import com.tibco.be.model.functions.FunctionParamDescriptor;
import com.tibco.be.model.functions.impl.ModelJavaFunction;
import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFUberType;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.studio.core.functions.annotations.CategoryInfo;
import com.tibco.cep.studio.core.functions.annotations.FunctionInfo;
import com.tibco.cep.studio.core.functions.annotations.ParamTypeInfo;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.xml.data.primitive.ExpandedName;

public class EMFModelJavaFunction extends EMFOntologyModelFunction implements ModelJavaFunction {

    protected ExpandedName functionName;
    protected String javaFunctionName;
    protected Class [] parameterTypes;
    protected String [] parameterNames;
    protected Entity [] entityArguments;
	private CategoryInfo categoryInfo;
	private FunctionInfo functionInfo;

//    public EMFModelJavaFunction(JavaSource model, ExpandedName functionName) {
//        this(model, functionName, functionName.getLocalName());
//    }

    public EMFModelJavaFunction(JavaSource model, ExpandedName functionName, String javaFunctionName, CategoryInfo categoryInfo, FunctionInfo fnInfo) {
        super(model);
        this.functionName=functionName;
        this.javaFunctionName=javaFunctionName;
        this.categoryInfo = categoryInfo;
        this.functionInfo = fnInfo;
        
    }
    
    public boolean isVarargs() {
		// the annotation parser converts vararg params to Array types, so need to look at the actual
		// type string in theannotation to determine whether this is a vararg function
		FunctionParamDescriptor[] params = this.getAnnotation().params();
		if (params.length > 0) {
			FunctionParamDescriptor descriptor = params[params.length-1];
			String type = descriptor.type();
			if (type.trim().endsWith("...")) {
				return true;
			}
		}
		return false; 
	}

    @Override
	String getDescription() {
        return "Invokes the Java Function " + code()+ "()";
	}

	@Override
	protected String getFSName() {
        String uri = getName().getNamespaceURI();
        if(!ModelUtils.IsEmptyString(uri)) return super.getFSName();

        return getName().getLocalName();
	}

	@Override
	public Entity[] getEntityArguments() {
        putArgs(false);
        return entityArguments;
    }

	@Override
	public Entity getEntityReturnType() {
        JavaSource rf = (JavaSource) getModel();
        ParamTypeInfo returnType = functionInfo.getReturnType();
        String retTypeClassName = returnType.getTypeClassName();
        
        if(retTypeClassName == null || returnType.isPrimitive()) 
        	return null;
        Class<?> retType = null;
        if(!retTypeClassName.startsWith(String.valueOf(Folder.FOLDER_SEPARATOR_CHAR))){
        	try {
        		retType = Class.forName(retTypeClassName);
        	} catch (ClassNotFoundException e) {
        		retType=null;
        	}
        }
        if(retType == null) 
        	return null;
        if(com.tibco.cep.kernel.model.entity.Entity.class.isAssignableFrom(retType)){
        	// Java @BEFunction annotation does not capture the entity path in the Annotation at this moment
        	// Only base types allowed i.e Concept,Event,ScoreCard
        	//Entity e = IndexUtils.getEntity(getModel().getOwnerProjectName(), retType);
        	//return e;

        	// do not return an empty Entity, this confuses the return type initialization.  Return null to indicate a generic return type
//        	if( com.tibco.cep.runtime.model.element.Concept.class.isAssignableFrom(retType)){
//        		return ElementFactory.eINSTANCE.createConcept();
//        	}
//        	if( com.tibco.cep.runtime.model.event.SimpleEvent.class.isAssignableFrom(retType)){
//        		return EventFactory.eINSTANCE.createSimpleEvent();
//        	}
//        	if( com.tibco.cep.runtime.model.event.TimeEvent.class.isAssignableFrom(retType)){
//        		return EventFactory.eINSTANCE.createTimeEvent();
//        	}
        	return null;
        }
        return null;	
    }

    public Class[] getParameterTypes() {
        putArgs(false);
        return parameterTypes;
    }

    @Override
	String[] getParameterNames() {
        putArgs(false);
        return parameterNames;
    }
    
	public CategoryInfo getCategoryInfo() {
		return this.categoryInfo;
	}

	public FunctionInfo getFunctionInfo() {
		return this.functionInfo;
	}

	@Override
	public String code() {
        return getModelClass() + "." + this.functionInfo.getName();
	}
	
	@Override
	public BEFunction getAnnotation() {
		return functionInfo.getAnnotation();
	}

	@Override
    public String getModelClass() {
		return categoryInfo.getImplClass();
        //return com.tibco.cep.studio.core.utils.ModelUtils.javaFnClassFSName((JavaSource)getModel());
    }

    @Override
	public boolean doesModify() {
        return !isValidInCondition();
	}

	@Override
	public Class[] getArguments() {
        putArgs(false);
        return parameterTypes;
    }

	@Override
	public String getDocumentation() {
		return code();
	}
	
	@Override
	public String toString() {
		return this.getModelClass() + "." + javaFunctionName;
	}

	@Override
	public ExpandedName getName() {
		return functionName;
	}

	@Override
	public Class getReturnClass() {
        Class cls = void.class;

        JavaSource rf = (JavaSource) getModel();
        ParamTypeInfo returnTypeInfo = functionInfo.getReturnType();
        String returnType = returnTypeInfo.getTypeClassName();
        returnType = (returnType.indexOf("[") != -1) ? returnType.substring(0, returnType.indexOf("[")) : returnType;
        boolean array = returnTypeInfo.isArray();
        if (returnType == null || void.class.getName().equals(returnType)) {
        	return cls;
        }
        if(com.tibco.cep.runtime.model.element.Concept.class.getName().equals(returnType)) {
        	return array ? com.tibco.cep.runtime.model.element.Concept[].class : com.tibco.cep.runtime.model.element.Concept.class;
        }
        if(com.tibco.cep.kernel.model.entity.Event.class.getName().equals(returnType)) {
        	return array ? com.tibco.cep.kernel.model.entity.Event[].class : com.tibco.cep.kernel.model.entity.Event.class;
        }
        if(com.tibco.cep.runtime.model.event.SimpleEvent.class.getName().equals(returnType)) {
        	return array ? com.tibco.cep.runtime.model.event.SimpleEvent[].class : com.tibco.cep.runtime.model.event.SimpleEvent.class;
        }
        
        cls = getClass(returnType, returnTypeInfo.isArray());
        
        return cls;
    }

	@Override
	public Class[] getThrownExceptions() {
		return new Class[0]; 
	}

	@Override
	public boolean isTimeSensitive() {
		return false;
	}

	@Override
	public boolean isValidInAction() {
		return this.functionInfo.isAction();
	}

	@Override
	public boolean isValidInBUI() {
		return this.functionInfo.isCondition();
	}

	@Override
	public boolean isValidInCondition() {
		return this.functionInfo.isCondition();
//        RuleFunction rf = (RuleFunction) getModel();
//        return (rf.getValidity() != Validity.ACTION);
    }

	@Override
	public boolean isValidInQuery() {
		return this.functionInfo.isQuery();
	}
	
	private static class ArgumentDescriptor {
        String name;
        Class clazz;
        Entity entity;
    }


    /**
     *
     * @param refresh
     */
    public void putArgs(boolean refresh) {
        try {
            if (refresh || (parameterTypes == null)) {

                final List<ArgumentDescriptor> args = new ArrayList<ArgumentDescriptor>();

                JavaSource rf = (JavaSource) getModel();
                for(ParamTypeInfo p: this.functionInfo.getArgs()) {
                	String type = p.getTypeClassName();
                	type = (type.indexOf("[") != -1) ? type.substring(0, type.indexOf("[")) : type;

                	final ArgumentDescriptor arg = new ArgumentDescriptor();
                	final String identifier = p.getName();
                    arg.name = identifier;
                    
                    Class<?> typeClass = getClass(type, p.isArray());
                    if (typeClass == null) {
                    	typeClass = p.isArray() ? Array.newInstance(Object.class, 0).getClass() : Object.class;
                    }
                    arg.clazz = typeClass;
                    
                    Entity entityType = getEntityType(typeClass);
        			if(entityType != null){
        				arg.entity = entityType;
        			}
        			
        			args.add(arg);
                }
                    
                // Parameter Names
                final int argNum = args.size();
                parameterNames = new String[argNum];
                parameterTypes = new Class[argNum];
                entityArguments = new Entity[argNum];
                
                int i=0;
                for (ArgumentDescriptor arg : args) {
                    parameterNames[i] = arg.name;
                    parameterTypes[i] = arg.clazz;
                    entityArguments[i] = arg.entity;
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param type
     * @param isArray
     * @return
     */
    private Class getClass(String type, boolean isArray) {
    	Class<?> typeClass = void.class;
    	
    	RDFUberType rdfType = RDFTypes.getType(type);
        if (rdfType != null) {
            typeClass = isArray ? (Class)RDF_2_PRIMITIVE_ARG_MAP2.get(rdfType) : (Class)RDF_2_PRIMITIVE_ARG_MAP.get(rdfType);
        } else {
        	 if (com.tibco.cep.runtime.model.element.Concept.class.isAssignableFrom(typeClass)) {
        		 typeClass = isArray ? (Class) RDF_2_PRIMITIVE_ARG_MAP2.get(RDFTypes.BASE_CONCEPT) : (Class) RDF_2_PRIMITIVE_ARG_MAP.get(RDFTypes.BASE_CONCEPT);
        	 } else if (com.tibco.cep.kernel.model.entity.Event.class.isAssignableFrom(typeClass)) {
        		 typeClass = isArray ? (Class) RDF_2_PRIMITIVE_ARG_MAP2.get(RDFTypes.BASE_EVENT) : (Class) RDF_2_PRIMITIVE_ARG_MAP.get(RDFTypes.BASE_EVENT);
        	 } else {
        		 typeClass = isArray ? (Class)RDF_2_PRIMITIVE_ARG_MAP2.get(type) : (Class)RDF_2_PRIMITIVE_ARG_MAP.get(type);
        	 }
        }
        if (typeClass == null) {
        	int idx = type.indexOf('<');
        	if (idx > 0) {
        		type = type.substring(0, idx);
        	}
        	try {
        		// TODO : if the class is only available on the project's classpath, the forName will fail.  Should use the project's classpath to look
				if (java.lang.Object.class.isAssignableFrom(Class.forName(type))) {
					// just return Object here, to allow additional return types such as List
					return isArray ? (Class)RDF_2_PRIMITIVE_ARG_MAP2.get(Object.class.getName()) : (Class)RDF_2_PRIMITIVE_ARG_MAP.get(Object.class.getName());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
        return typeClass;
    }


	private Entity getEntityType(Class<?> typeClass) throws Exception {
		Field typeField =null;
		try {
			typeField = typeClass.getDeclaredField("type");
		} catch(NoSuchFieldException e) {
			return null;
		}
		Object val = typeField.get(null);
		if(val != null && val instanceof String){
			String rdfType = (String) val;
			String type=null;
			if(rdfType.startsWith(RDFTnsFlavor.BE_NAMESPACE)){
				type = rdfType.substring(RDFTnsFlavor.BE_NAMESPACE.length());
				return IndexUtils.getEntity(getModel().getOwnerProjectName(), type);
			}
		}
		return null;
	}

}
