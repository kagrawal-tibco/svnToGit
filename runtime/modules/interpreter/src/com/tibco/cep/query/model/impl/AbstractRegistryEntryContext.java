package com.tibco.cep.query.model.impl;

import java.util.Map;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.RegistryContext;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 25, 2007
 * Time: 5:41:50 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractRegistryEntryContext extends AbstractTypedContext implements RegistryContext {


    public AbstractRegistryEntryContext(ModelContext parentContext) {
        super(parentContext,null);
    }


    /**
     * @return Map of name to ModelContext valid in this context
     */
    public Map<Object,ModelContext> getContextMap() {
        return this.getRootContext().getContextMap();
    }

}
