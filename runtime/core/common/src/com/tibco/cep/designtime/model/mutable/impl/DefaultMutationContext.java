package com.tibco.cep.designtime.model.mutable.impl;


import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.designtime.model.MutationContext;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 6, 2006
 * Time: 5:19:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultMutationContext implements MutationContext {


    private byte type;
    private Object mutated;
    private Map changes;


    public DefaultMutationContext(byte type) {
        this(type, null);
    }


    public DefaultMutationContext(byte type, Object mutated) {
        this(type, mutated, new HashMap());
    }


    public DefaultMutationContext(byte type, Object mutated, Map changes) {
        this.type = type;
        this.mutated = mutated;
        this.changes = changes;
    }


    public Map getChanges() {
        return this.changes;
    }


    public byte getMutationType() {
        return this.type;
    }


    public Object getMutatedObject() {
        return this.mutated;
    }
}
