package com.tibco.rta.impl.util;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 13/3/13
 * Time: 2:36 PM
 * A synchronizer object
 */
public class ConnectionExchanger {

    private Object exchanged;

    public Object getExchanged() {
        return exchanged;
    }

    public void setExchanged(Object exchanged) {
        this.exchanged = exchanged;
    }
}
