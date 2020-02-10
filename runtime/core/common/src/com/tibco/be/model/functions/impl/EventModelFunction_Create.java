package com.tibco.be.model.functions.impl;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 10, 2004
 * Time: 9:13:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventModelFunction_Create extends ModelFunctionImpl {
    ExpandedName functionName;
    String javaFunctionName;
    Class [] parameterTypes;
    String [] parameterNames;
    Entity [] entityArguments;


    private static int getRDFTermTypeFlag(RDFPrimitiveTerm type) {
        if(RDFTypes.BOOLEAN.equals(type)) {
            return RDFTypes.BOOLEAN_TYPEID;
        } else if(RDFTypes.INTEGER.equals(type)) {
            return RDFTypes.INTEGER_TYPEID;
        } else if(RDFTypes.LONG.equals(type)) {
            return RDFTypes.LONG_TYPEID;
        } else if(RDFTypes.DOUBLE.equals(type)) {
            return RDFTypes.DOUBLE_TYPEID;
        } else if(RDFTypes.DATETIME.equals(type)) {
            return RDFTypes.DATETIME_TYPEID;
        } else {
            return RDFTypes.STRING_TYPEID;
        }
    }

    /**
     *
     * @return
     */
    String getDescription() {
        return "Creates a new event of type " + model.getFullPath() ;
    }

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
     * @param model
     */
    EventModelFunction_Create(Event model, ExpandedName functionName) {
        this(model, functionName, functionName.getLocalName());
    }

    /**
     *
     * @param model
     * @param functionName
     * @param javaFunctionName
     */
    EventModelFunction_Create(Event model, ExpandedName functionName, String javaFunctionName) {
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
    void putArgs(boolean refresh) {

        try {
            if (refresh || (parameterTypes == null)) {
            	
            	final List<ArgumentDescriptor> args = new ArrayList<ArgumentDescriptor>();
            	
//                ArrayList propNames= new ArrayList();
//                List propClasses= new ArrayList();
                Collection propDefs = ((Event) model).getAllUserProperties();

                for(Iterator it = propDefs.iterator(); it.hasNext();) {
                	final ArgumentDescriptor arg = new ArgumentDescriptor();
                    EventPropertyDefinition pd = (EventPropertyDefinition)it.next();
                    arg.name = pd.getPropertyName();
                    arg.clazz = argumentsMap1[(getRDFTermTypeFlag(pd.getType()))];
//                    propNames.add(pd.getPropertyName());
//                    propClasses.add(argumentsMap1[(getRDFTermTypeFlag(pd.getType()))]);
                }
//                parameterNames = new String[propNames.size()+2];
//                parameterNames[0]= "extId";
//                parameterNames[1]="payload";
                final ArgumentDescriptor extIdArg = new ArgumentDescriptor();
                extIdArg.name = "extId";
                extIdArg.clazz = java.lang.String.class;
                args.add(0, extIdArg);
                final ArgumentDescriptor payLoadArg = new ArgumentDescriptor();
                payLoadArg.name = "payload";
                payLoadArg.clazz = java.lang.String.class;
                args.add(1, payLoadArg);

//                int i=2;
//                for (Iterator it=propNames.iterator(); it.hasNext();) {
//                    parameterNames[i++]= (String) it.next();
//                }
//                parameterTypes = new Class[propClasses.size()+2];
//                parameterTypes[0] = java.lang.String.class;
//                parameterTypes[1] = java.lang.String.class;
//
//                for (i=0; i < propClasses.size();i++) {
//                    parameterTypes[i+2] = (Class) propClasses.get(i);
//                }
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
                //System.out.println(functionName + " returning " + parameterTypes.length + " parameters");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    public Class getReturnClass() {
//        return com.tibco.cep.runtime.model.event.SimpleEvent.class;
        return com.tibco.cep.kernel.model.entity.Event.class;  //todo - change this to simple Event?
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
        return entityArguments;
    }

    public Entity getEntityReturnType() {
        return model;
    }
    
    public boolean requiresAssert() { return true; }
}
