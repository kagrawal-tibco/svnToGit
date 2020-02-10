package com.tibco.cep.runtime.management.impl.adapter.util;

import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.cep.runtime.management.MetricDef;
import com.tibco.cep.runtime.management.MetricTable;
import com.tibco.cep.runtime.management.impl.adapter.RemoteCoherenceMetricDataListener;
import com.tibco.cep.runtime.management.impl.adapter.RemoteCoherenceMetricTable;
import com.tibco.cep.runtime.management.impl.adapter.parent.RemoteCoherenceMetricDataListenerImpl;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.SimpleThreadFactory;

/*
* Author: Ashwin Jayaprakash Date: Mar 18, 2009 Time: 12:53:47 AM
*/
public class ProxyCoherenceMetricTable implements MetricTable {
    protected volatile RemoteCoherenceMetricTable target;

    protected final ReentrantLock listenerBrokerLock;

    protected HashMap<String, ListenerBroker> listenerBrokers;

    protected ExecutorService singleThreadedListenerDispatcher;

    public ProxyCoherenceMetricTable(RemoteCoherenceMetricTable target) {
        this.target = target;
        this.listenerBrokerLock = new ReentrantLock();
        this.listenerBrokers = new HashMap<String, ListenerBroker>();

        this.singleThreadedListenerDispatcher =
                Executors.newSingleThreadExecutor(
                        new SimpleThreadFactory("SingleThreadedListenerDispatcher"));
    }

    public void resetTarget(RemoteCoherenceMetricTable target) {
        this.target = target;
    }

    public RemoteCoherenceMetricTable getTarget() {
        return target;
    }

    public Collection<FQName> getMetricDefNames() {
        try {
            return target.getMetricDefNames();
        }
        catch (Exception e) {
            throw new RuntimeException("Error from remote process.", e);
        }
    }

    public void addMetricDef(MetricDef metricDef) {
        try {
            target.addMetricDef(metricDef);
        }
        catch (Exception e) {
            throw new RuntimeException("Error from remote process.", e);
        }
    }

    public MetricDef getMetricDef(FQName fqn) {
        try {
            return target.getMetricDef(fqn);
        }
        catch (Exception e) {
            throw new RuntimeException("Error from remote process.", e);
        }
    }

    public MetricDef removeMetricDef(FQName fqn) {
        try {
            return target.removeMetricDef(fqn);
        }
        catch (Exception e) {
            throw new RuntimeException("Error from remote process.", e);
        }
    }

    public Collection<FQName> getMetricNames() {
        try {
            return target.getMetricNames();
        }
        catch (Exception e) {
            throw new RuntimeException("Error from remote process.", e);
        }
    }

    public void addMetricData(FQName fqn, Data data) {
        try {
            target.addMetricData(fqn, data);
        }
        catch (Exception e) {
            throw new RuntimeException("Error from remote process.", e);
        }
    }

    public Data getMetricData(FQName fqn) {
        try {
            return target.getMetricData(fqn);
        }
        catch (Exception e) {
            throw new RuntimeException("Error from remote process.", e);
        }
    }

    public Data removeMetricData(FQName fqn) {
        try {
            return target.removeMetricData(fqn);
        }
        catch (Exception e) {
            throw new RuntimeException("Error from remote process.", e);
        }
    }

    public void registerListener(DataListener listener, FQName fqnToListenTo) {
        String name = listener.getName();

        listenerBrokerLock.lock();
        try {
            try {
                ListenerBroker broker = listenerBrokers.get(name);

                if (broker == null) {
                    RemoteCoherenceMetricDataListenerImpl remoteListener =
                            new RemoteCoherenceMetricDataListenerImpl(name, listener,
                                    singleThreadedListenerDispatcher);

                    RemoteCoherenceMetricDataListener remoteListenerStub =
                            (RemoteCoherenceMetricDataListener) UnicastRemoteObject
                                    .exportObject(remoteListener,0);

                    broker = new ListenerBroker(remoteListenerStub, listener);

                    target.addRemoteListener(remoteListenerStub);

                    listenerBrokers.put(name, broker);
                }

                target.startListening(name, fqnToListenTo);

                broker.addSubscription(fqnToListenTo);
            }
            catch (Exception e) {
                throw new RuntimeException("Error from remote process.", e);
            }
        }
        finally {
            listenerBrokerLock.unlock();
        }
    }

    public void unregisterListener(String listenerName, FQName fqnToListenTo) {
        listenerBrokerLock.lock();
        try {
            try {
                ListenerBroker broker = listenerBrokers.get(listenerName);

                if (broker == null) {
                    return;
                }
                else {
                    target.stopListening(listenerName, fqnToListenTo);

                    broker.removeSubscription(fqnToListenTo);

                    if (broker.getSubscriptionCount() == 0) {
                        target.removeRemoteListener(listenerName);
                    }

                    listenerBrokers.remove(listenerName);

                    broker.discard();
                }
            }
            catch (Exception e) {
                throw new RuntimeException("Error from remote process.", e);
            }
        }
        finally {
            listenerBrokerLock.unlock();
        }
    }

    public void discard() {
        singleThreadedListenerDispatcher.shutdown();
    }

    //----------

    protected static class ListenerBroker {
        protected RemoteCoherenceMetricDataListener exportedListener;

        protected DataListener localListener;

        protected HashSet<FQName> subscriptions;

        public ListenerBroker(RemoteCoherenceMetricDataListener exportedListener,
                              DataListener localListener) {
            this.exportedListener = exportedListener;
            this.localListener = localListener;
            this.subscriptions = new HashSet<FQName>();
        }

        public RemoteCoherenceMetricDataListener getExportedListener() {
            return exportedListener;
        }

        public DataListener getLocalListener() {
            return localListener;
        }

        public void addSubscription(FQName name) {
            subscriptions.add(name);
        }

        public void removeSubscription(FQName name) {
            subscriptions.remove(name);
        }

        public int getSubscriptionCount() {
            return subscriptions.size();
        }

        public Collection<FQName> getSubscriptions() {
            return subscriptions;
        }

        public void discard() {
            subscriptions.clear();
            subscriptions = null;

            localListener = null;

            exportedListener = null;
        }
    }
}
