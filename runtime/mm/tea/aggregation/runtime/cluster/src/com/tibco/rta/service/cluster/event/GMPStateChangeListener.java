package com.tibco.rta.service.cluster.event;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 29/7/13
 * Time: 11:27 AM
 * To change this template use File | Settings | File Templates.
 */
public interface GMPStateChangeListener {

    /**
     *
     * @param stateChangeEvent
     */
    void onStateChange(GMPClusterStateChangeEvent stateChangeEvent);
}
