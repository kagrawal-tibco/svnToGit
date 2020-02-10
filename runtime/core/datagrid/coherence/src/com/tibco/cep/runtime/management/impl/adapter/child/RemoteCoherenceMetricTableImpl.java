package com.tibco.cep.runtime.management.impl.adapter.child;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.DistributedCacheService;
import com.tibco.cep.runtime.management.MetricDef;
import com.tibco.cep.runtime.management.MetricTable;
import com.tibco.cep.runtime.management.impl.adapter.RemoteCoherenceMetricDataListener;
import com.tibco.cep.runtime.management.impl.adapter.RemoteCoherenceMetricTable;
import com.tibco.cep.runtime.management.impl.cluster.CoherenceMetricTable;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Mar 17, 2009 Time: 11:58:35 PM
*/
public class RemoteCoherenceMetricTableImpl implements RemoteCoherenceMetricTable {
    protected CoherenceMetricTable target;

    protected ConcurrentHashMap<String, ListenerBroker> listenerBrokers;

    protected ExceptionCollector exceptionCollector;
    
    protected volatile Timer timer;

    public void init(String clusterName, ExceptionCollector exceptionCollector) {
        this.listenerBrokers = new ConcurrentHashMap<String, ListenerBroker>();

        this.target = new CoherenceMetricTable();
        this.target.init(clusterName, null);

        this.exceptionCollector = exceptionCollector;
    }

    public Collection<FQName> getMetricDefNames() throws RemoteException {
        ArrayList<FQName> newList = new ArrayList<FQName>(target.getMetricDefNames());

        return newList;
    }

    public void addMetricDef(MetricDef metricDef) throws RemoteException {
        target.addMetricDef(metricDef);
    }

    public MetricDef getMetricDef(FQName fqn) throws RemoteException {
        return target.getMetricDef(fqn);
    }

    public MetricDef removeMetricDef(FQName fqn) throws RemoteException {
        return target.removeMetricDef(fqn);
    }

    public Collection<FQName> getMetricNames() throws RemoteException {
        return new LinkedList<FQName>(target.getMetricNames());
    }

    public void addMetricData(FQName fqn, Data data) throws RemoteException {
        target.addMetricData(fqn, data);
    }

    public Data getMetricData(FQName fqn) throws RemoteException {
        return target.getMetricData(fqn);
    }

    public Data removeMetricData(FQName fqn) throws RemoteException {
        return target.removeMetricData(fqn);
    }

    //-----------

    public void addRemoteListener(RemoteCoherenceMetricDataListener remoteListener)
            throws RemoteException {
        ListenerBroker broker = new ListenerBroker(remoteListener);
        String name = broker.getName();

        ListenerBroker existingBroker = listenerBrokers.putIfAbsent(name, broker);
        if (existingBroker != null) {
            throw new RuntimeException(String.format("A listener already exists with the name provided: %s." +
                    " Check if you are using unique broker ports for each Master CDD file loaded.", name) );
        }
    }

    /*
     * Modified to be able to start bemm without a target cluster that is up and running. Nick Xu
     */
    public void startListening(String listenerName, FQName fqnToListenTo) throws RemoteException {
        ListenerBroker broker = listenerBrokers.get(listenerName);
            
        try{
        	target.registerListener(broker, fqnToListenTo);
        	broker.addFQNSubscription(fqnToListenTo);
        }  
        catch(Exception e){
        	System.out.println(e.getMessage());
        	System.out.println("No cluster discovered!");
        	DistributedCacheService dcs = (DistributedCacheService) CacheFactory.getService("DistributedCache");
        	if(dcs != null){
        		RegisterListenerTask regListenerTask = new RegisterListenerTask(dcs,target,broker,fqnToListenTo);
        		if (timer == null) {
        			timer = new Timer("RegisterListener.Timer.register", true);
        		}
        		timer.schedule(regListenerTask, 5000 ,5000);	
        	}
        	else{
        		System.out.println("No DistributedCacheService found!");
        	}
        } //TODO:Need to consider the situation when the listener is unregistered
    }

    public void stopListening(String listenerName, FQName fqnToListenTo) throws RemoteException {
        ListenerBroker broker = listenerBrokers.get(listenerName);

        target.unregisterListener(broker.getName(), fqnToListenTo);

        broker.removeFQNSubscription(fqnToListenTo);
    }

    public void removeRemoteListener(String remoteListenerName)
            throws RemoteException {
        ListenerBroker broker = listenerBrokers.get(remoteListenerName);
        if (broker == null) {
            return;
        }

        //Avoid concurrent mod errors during cleanup.
        ArrayList<FQName> cloneList = new ArrayList<FQName>(broker.getFQNSubscriptions());
        for (FQName fqn : cloneList) {
            stopListening(remoteListenerName, fqn);
        }
        cloneList.clear();

        listenerBrokers.remove(remoteListenerName);

        broker.discard();
    }

    //-----------

    public void discard() {
        target.discard();
    }

    //-----------

    protected class ListenerBroker implements MetricTable.DataListener {
        protected final String cachedName;

        protected RemoteCoherenceMetricDataListener remoteListener;

        protected HashSet<FQName> allFQNsSubscribedTo;

        protected int remoteListenerConnectFailures;

        public ListenerBroker(RemoteCoherenceMetricDataListener remoteListener) {
            this.remoteListener = remoteListener;

            try {
                this.cachedName = remoteListener.getName();
            }
            catch (RemoteException e) {
                throw new RuntimeException(e);
            }

            this.allFQNsSubscribedTo = new HashSet<FQName>();
        }

        public RemoteCoherenceMetricDataListener getRemoteListener() {
            return remoteListener;
        }

        public void addFQNSubscription(FQName name) {
            allFQNsSubscribedTo.add(name);
        }

        public void removeFQNSubscription(FQName name) {
            allFQNsSubscribedTo.remove(name);
        }

        public Collection<FQName> getFQNSubscriptions() {
            return allFQNsSubscribedTo;
        }

        public int getFQNSubscriptionCount() {
            return allFQNsSubscribedTo.size();
        }

        public void discard() {
            remoteListener = null;

            allFQNsSubscribedTo.clear();
            allFQNsSubscribedTo = null;
        }

        //------------

        public String getName() {
            return cachedName;
        }

        public void onNew(FQName key, Data data) {
            try {
                remoteListener.onNew(key, data);

                remoteListenerConnectFailures = 0;
            }
            catch (ConnectException e) {
                remoteListenerConnectFailures++;

                //Could be some network issue
                if (remoteListenerConnectFailures > 3) {

                    new Exception("Error occurred in listener broker [" + cachedName +
                            "]. Unable to relay calls to remote listener even after ["
                            + remoteListenerConnectFailures +
                            "] failures. Unregistering the listener.", e).printStackTrace();

                    try {
                        RemoteCoherenceMetricTableImpl.this.removeRemoteListener(cachedName);
                    }
                    catch (RemoteException e1) {
                        System.err.println(
                                "Unregisteration of listener [" + cachedName + "] failed.");
                        e1.printStackTrace();
                    }
                }
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
