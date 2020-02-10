package com.tibco.be.model.functions.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.be.parser.codegen.CGConstants;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.xml.data.primitive.ExpandedName;
import static com.tibco.be.model.functions.FunctionDomain.*;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 4, 2004
 * Time: 12:36:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConceptModelFunctionImpl extends ModelFunctionImpl implements ConceptModelFunction {
    protected ExpandedName functionName;
    protected String javaFunctionName;
    protected Class [] parameterTypes;
    protected String [] parameterNames;
    protected Entity [] entityArguments;


    Class[] getParameterTypes() {
        putArgs(false);
        return parameterTypes;
    }

    String[] getParameterNames() {
        putArgs(false);
        return parameterNames;
    }

    /**
     *
     * @return
     */
    String getDescription() {
        return "Creates a new instance of type <" + model.getFullPath() + ">";
    }

    /**
     *
     * @param model
     */
    public ConceptModelFunctionImpl(Concept model, ExpandedName functionName) {
        this(model, functionName, functionName.getLocalName());
    }

    /**
     *
     * @param model
     * @param functionName
     * @param javaFunctionName
     */
    public ConceptModelFunctionImpl(Concept model, ExpandedName functionName, String javaFunctionName) {
        super(model);
        this.functionName=functionName;
        this.javaFunctionName=javaFunctionName;
        setFunctionDomains(new FunctionDomain[]{ ACTION });
    }

    /**
     *
     * @return
     */
    public ExpandedName getName() {
        return functionName;
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
    protected void putArgs(boolean refresh) {
        try {
            if (refresh || (parameterTypes == null)) {
            	final List<ArgumentDescriptor> args = new ArrayList<ArgumentDescriptor>();
            	
//                    ArrayList propNames= new ArrayList();
//                    ArrayList modelTypes = new ArrayList();
//                    
//                    List propClasses= new ArrayList();
                Collection propDefs = ((Concept) model).getPropertyDefinitions(false);

                for(Iterator it = propDefs.iterator(); it.hasNext();) {
                	final ArgumentDescriptor arg = new ArgumentDescriptor();
                    PropertyDefinition pd = (PropertyDefinition)it.next();
                    Class propClass= pd.isArray() ?  argumentsMap2[pd.getType()] : argumentsMap1[pd.getType()];
//                        propClasses.add(propClass);
//                        propNames.add(pd.getName());
                    arg.name = pd.getName();
                    arg.clazz = propClass;
                    if (pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT || pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
//                            modelTypes.add(pd.getConceptType());
                        arg.entity = pd.getConceptType();
                    }
                    args.add(arg);
                }
                // Parameter Names
//                    parameterNames = new String[propNames.size()+1];
//                    parameterNames[0]= "extId";
                final ArgumentDescriptor extIdArg = new ArgumentDescriptor();
                extIdArg.name = "extId";
                extIdArg.clazz =  java.lang.String.class;
                args.add(0, extIdArg);
//                    int i=1;
//                    for (Iterator it=propNames.iterator(); it.hasNext();) {
//                        parameterNames[i++]= (String) it.next();
//                    }
//                    // Parameter Types
//                    parameterTypes = new Class[propClasses.size()+1];
//                    parameterTypes[0] = java.lang.String.class;
//    
//                    for (i=0; i < propClasses.size();i++) {
//                        parameterTypes[i+1] = (Class) propClasses.get(i);
//                    }
//    
//                    // Entity Arguments
//                    entityArguments = new Entity [modelTypes.size()];
//                    for (i=0; i < entityArguments.length;i++) {
//                        entityArguments[i] = (Entity) modelTypes.get(i);
//                    }
                    
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
     * @return
     */
    public Class getReturnClass() {
        return com.tibco.cep.runtime.model.element.Concept.class;
    }

    /**
     *
     * @return
     */
    public Class[] getThrownExceptions() {
        return new Class[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     *
     * @return
     */
    public Class[] getArguments() {
        putArgs(false);
        return parameterTypes;
    }

    /**
     *
     * @return
     */
    public String code() {
        return this.getModelClass() + "." + javaFunctionName;
    }


    /**
     *
     * @return
     */
    public boolean isTimeSensitive() {
        return false;
    }

    /**
     *
     * @return
     */
    public boolean requiresAsync() {
        return false;
    }

    /**
     *
     * @return
     */
    public String getDocumentation() {
        return code();
    }

    /**
     *
     * @return
     */
    public boolean doesModify() {
        return false;
    }

    public Entity[] getEntityArguments() {
        putArgs(false);
        return entityArguments;
    }

    public Entity getEntityReturnType() {
        return model;
    }
    
    public boolean requiresAssert() { return true; }
    
    public boolean isVarargsCodegen() { return CGConstants.HDPROPS; }
    public Class getVarargsCodegenArgType() {
    	if(CGConstants.HDPROPS) {
    		return Object[].class;
    	} else {
    		Class[] args = getArguments();
	    	if(args != null && args.length > 0) return args[args.length - 1];
	    	else return null;
    	}
    }
}
