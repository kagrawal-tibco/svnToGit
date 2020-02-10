package com.tibco.rta.query;

import com.tibco.rta.impl.FactKeyImpl;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/5/14
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class FactResultTuple implements Serializable, ResultTuple {

    private FactKeyImpl factKey;

    private Map<String, Object> factAttributes;

    public FactResultTuple() {
    }

    public FactResultTuple(FactKeyImpl factKey, Map<String, Object> factAttributes) {
        this.factKey = factKey;
        this.factAttributes = factAttributes;
    }

    public FactKeyImpl getFactKey() {
        return factKey;
    }

    public Map<String, Object> getFactAttributes() {
        return factAttributes;
    }

    public void setFactKey(FactKeyImpl factKey) {
        this.factKey = factKey;
    }

    public void setFactAttributes(Map<String, Object> factAttributes) {
        this.factAttributes = factAttributes;
    }
}
