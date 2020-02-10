package com.tibco.cep.query.model.impl;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.RegistryContext;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 25, 2007
 * Time: 6:19:24 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractRegistryContext extends AbstractModelContext implements RegistryContext {

    private Map<Object,  ModelContext> contextMap = new HashMap<Object,  ModelContext>();


    public AbstractRegistryContext(ModelContext parentContext) {
        super(parentContext);
    }

    /**
     * @return Map of name to ModelContext valid in this context
     */
    public Map<Object,ModelContext> getContextMap() {
        return this.contextMap;
    }


}
