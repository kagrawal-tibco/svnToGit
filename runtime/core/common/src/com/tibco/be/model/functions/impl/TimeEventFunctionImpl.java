package com.tibco.be.model.functions.impl;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.xml.data.primitive.ExpandedName;


/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 5, 2004
 * Time: 8:29:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class TimeEventFunctionImpl extends ModelFunctionImpl implements TimeEventFunction{
    ExpandedName functionName;
    Class [] parameterTypes;
    String [] parameterNames;
    Entity [] entityArguments;

    /**
     *
     * @param model
     */
    TimeEventFunctionImpl(Event model, ExpandedName functionName) {
        super(model);
        this.functionName=functionName;
        setFunctionDomains(new FunctionDomain[]{ ACTION });
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return "Creates a new time event of type <" + model.getFullPath() + ">";
    }


    /**
     *
     * @return
     */
    public ExpandedName getName() {
        return functionName;
    }

    /**
     *
     * @param refresh
     */
    void putArgs(boolean refresh) {

        try {
            if (refresh || (parameterTypes == null)) {
                parameterTypes = new Class[] {long.class, String.class, long.class};
                parameterNames = new String[] {"delay", "closure", "ttl"};
                entityArguments = new Entity[3];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public Class getReturnClass() {
        //return void.class;
        return com.tibco.cep.kernel.model.entity.Event.class;
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
    public String[] getParameterNames() {
        putArgs(false);
        return parameterNames;
    }

    /**
     *
     * @return
     */
    public String code() {
        return this.getModelClass() + "." + functionName.getLocalName();
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

    /**
     *
     * @return
     */
    public Entity[] getEntityArguments() {
        return entityArguments;
    }

    /**
     *
     * @return
     */
    public Entity getEntityReturnType() {
        return null;
    }

    public boolean requiresAssert() { return true; }
}

