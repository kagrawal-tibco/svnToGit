package com.tibco.cep.query.stream.impl.monitor.model;

import com.tibco.cep.query.stream.impl.monitor.QueryMonitor;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Jul 25, 2008
 * Time: 3:15:03 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class QueryInfo {

    protected QueryMonitor monitor;
    protected ReteQuery currentReteQueryObj;

    //constructor
   public QueryInfo(ReteQuery currentReteQueryObj, QueryMonitor monitor) {
        this.monitor = monitor;
        this.currentReteQueryObj = currentReteQueryObj;
   }

    //Attributes Methods                               TODO xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    public boolean isPaused() {
        return monitor.isQueryPaused(currentReteQueryObj);
    }

    public boolean isStopped() {
        return monitor.hasQueryStopped(currentReteQueryObj);
    }

    public boolean isTracingEnabled() {
        return monitor.isTracingEnabled(currentReteQueryObj);
    }

    public int getPendingEntityCount() {
        return monitor.getPendingMainQueueCount(currentReteQueryObj);
    }

    public int getAccumulatedEntityCountDuringSS(){
        return monitor.getPendingLiveQueueCount(currentReteQueryObj);
    }

    //Operations Methods
    abstract public void ping();

    abstract public void pause();

    abstract public void resumeQuery();

    abstract public void enableTracing() throws Exception;

    abstract public void disableTracing() throws Exception;
}
