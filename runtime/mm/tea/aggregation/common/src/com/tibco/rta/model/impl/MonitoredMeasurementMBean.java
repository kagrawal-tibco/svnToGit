package com.tibco.rta.model.impl;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/6/13
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MonitoredMeasurementMBean {

    /**
     * Get hierarchy name.
     * @return name
     */
    public String getName();

    /**
     * Get owner schema name.
     * @return schema nam
     */
    public String getOwnerSchemaName();


    /**
     * Get total facts received for hierarchy.
     * @return total facts
     */
    public long getTotalFacts();

    /**
     * Total facts actually processed for this hierarchy.
     * @return facts processed for this hierarchy.
     */
    public long getProcessedFacts();

    /**
     * Total facts rejected for this hierarchy.
     * @return rejected fact count.
     */
    public long getRejectedFacts();
}
