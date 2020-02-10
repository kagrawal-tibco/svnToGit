package com.tibco.cep.runtime.management.impl.cluster;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.management.MetricDef;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.service.basic.AsyncWorkerServiceWatcher;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Apr 10, 2009 Time: 4:57:47 PM
*/
public class LocalMetricTable implements InternalMetricTable {

    @SuppressWarnings("unused")
	private Logger logger;

    private Map<FQName,MetricDef> metricDefinitions;

    private Map<FQName, Data> metricData;

    private Map<String,DataListener> wildCardListeners;

    private Map<FQName, Map<String, DataListener>> specificListeners;

    public void init(String clusterURL, String role) {
        logger = LogManagerFactory.getLogManager().getLogger(LocalMetricTable.class);
//        logger.log(Level.WARN, "init(%s,%s)", clusterURL, role);
        metricDefinitions = new ConcurrentHashMap<FQName, MetricDef>();
        metricData = new ConcurrentHashMap<FQName, Data>();
        wildCardListeners = new ConcurrentHashMap<String, DataListener>();
        specificListeners = new ConcurrentHashMap<FQName, Map<String, DataListener>>();
    }

    public void discard() {
//        logger.log(Level.WARN, "discard()");
    }

    public Collection<FQName> getMetricDefNames() {
//    	logger.log(Level.WARN, "getMetricDefNames()");
        return new LinkedList<FQName>(metricDefinitions.keySet());
    }

    public void addMetricDef(MetricDef metricDef) {
//    	logger.log(Level.WARN, "addMetricDef(%s)", String.valueOf(metricDef));
        metricDefinitions.put(metricDef.getName(), metricDef);
    }

    public MetricDef getMetricDef(FQName fqn) {
//    	logger.log(Level.WARN, "getMetricDef(%s)", String.valueOf(fqn));
        return metricDefinitions.get(fqn);
    }

    public MetricDef removeMetricDef(FQName fqn) {
//    	logger.log(Level.WARN, "removeMetricDef(%s)", String.valueOf(fqn));
        return metricDefinitions.remove(fqn);
    }

    public Collection<FQName> getMetricNames() {
//    	logger.log(Level.WARN, "getMetricNames()");
        return new LinkedList<FQName>(metricData.keySet());
    }

    public void addMetricData(FQName fqn, Data data) {
//    	logger.log(Level.WARN, "addMetricData(%s,%s)", String.valueOf(fqn), String.valueOf(data));
        metricData.put(fqn,data);
        fireOnNew(fqn, data);
    }


    public Data getMetricData(FQName fqn) {
//    	logger.log(Level.WARN, "getMetricData(%s)", String.valueOf(fqn));
        return metricData.get(fqn);
    }

    public Data removeMetricData(FQName fqn) {
//    	logger.log(Level.WARN, "removeMetricData(%s)", String.valueOf(fqn));
        return metricData.remove(fqn);
    }

    public void registerListener(DataListener listener, FQName fqnToListenTo) {
//    	logger.log(Level.WARN, "registerListener(%s, %s)", String.valueOf(listener), String.valueOf(fqnToListenTo));
        if (fqnToListenTo == null) {
            wildCardListeners.put(listener.getName(), listener);
            return;
        }
        Map<String, DataListener> listeners = specificListeners.get(fqnToListenTo);
        if (listeners == null) {
            synchronized (specificListeners) {
                listeners = specificListeners.get(fqnToListenTo);
                if (listeners == null) {
                    listeners = new HashMap<String, DataListener>();
                    specificListeners.put(fqnToListenTo, listeners);
                }
            }
        }
        listeners.put(listener.getName(), listener);
    }

    public void unregisterListener(String listenerName, FQName fqnToListenTo) {
//    	logger.log(Level.WARN, "unregisterListener(%s, %s)", String.valueOf(listenerName), String.valueOf(fqnToListenTo));
        if (fqnToListenTo == null) {
            wildCardListeners.remove(listenerName);
            return;
        }
        Map<String, DataListener> listeners = specificListeners.get(fqnToListenTo);
        if (listeners != null) {
            listeners.remove(listenerName);
        }
    }

    private void fireOnNew(FQName fqn, Data data) {
        for (DataListener listener : wildCardListeners.values()) {
            listener.onNew(fqn, data);
        }
        if((fqn!=null) && (fqn.getComponentNames().length>=4) && (fqn.getComponentNames()[2]!=null) && (fqn.getComponentNames()[3]!=null)){
    		String listnerNameKey = fqn.getComponentNames()[2]+fqn.getComponentNames()[3];
    	
	        Map<String, DataListener> listeners = specificListeners.get(new FQName(listnerNameKey));
	        if (listeners != null) {
	            for (DataListener listener : listeners.values()) {
	                listener.onNew(fqn, data);
	            }
	        }else if(fqn.getComponentNames()[4].equals(AsyncWorkerServiceWatcher.AsyncWorkerService.class.getSimpleName())){
	        	for(Entry<FQName, Map<String, DataListener>> aListners : specificListeners.entrySet()){
	        		for(Entry<String, DataListener> l_ : aListners.getValue().entrySet()){
	        			DataListener listener = l_.getValue();
	        			listener.onNew(fqn, data);
	        		}
	        	}
	        }
        }
    }

}
