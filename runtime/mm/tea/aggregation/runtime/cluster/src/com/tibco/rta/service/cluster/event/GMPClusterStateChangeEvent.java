package com.tibco.rta.service.cluster.event;

import com.tibco.rta.service.cluster.GMPClusterStates;

import java.util.EventObject;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 29/7/13
 * Time: 11:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class GMPClusterStateChangeEvent extends EventObject {

    /**
     * The new state.
     */
    private GMPClusterStates theState;

    public GMPClusterStateChangeEvent(Object source) {
        super(source);
    }

    public GMPClusterStates getState() {
        return theState;
    }

    public void setState(GMPClusterStates theState) {
        this.theState = theState;
    }
}
