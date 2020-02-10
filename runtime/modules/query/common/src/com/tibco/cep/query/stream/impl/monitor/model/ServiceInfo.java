package com.tibco.cep.query.stream.impl.monitor.model;

import java.util.Collection;

import com.tibco.cep.query.stream.impl.monitor.QueryMonitor;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Jul 25, 2008
 * Time: 3:14:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceInfo {

    protected QueryMonitor monitor;

    public ServiceInfo(QueryMonitor monitor) {
        this.monitor = monitor;
    }

    protected Collection<String> listRegionNames() {
       return monitor.listRegionNames();
    }

    protected Collection<ReteQuery> listContinuousQueries(String regionName) {
        return monitor.listContinuousQueries(regionName);
    }

    protected Collection<ReteQuery> listSnapshotQueries(String regionName) {
        return monitor.listSnapshotQueries(regionName);
    }
}
