package com.tibco.cep.runtime.management.impl.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.as.space.ASCommon;
import com.tibco.as.space.ASException;
import com.tibco.as.space.MemberDef;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.Member.DistributionRole;
import com.tibco.as.space.SpaceDef.DistributionPolicy;
import com.tibco.as.space.event.ExpireEvent;
import com.tibco.as.space.event.PutEvent;
import com.tibco.as.space.event.TakeEvent;
import com.tibco.as.space.listener.ExpireListener;
import com.tibco.as.space.listener.ListenerDef;
import com.tibco.as.space.listener.PutListener;
import com.tibco.as.space.listener.TakeListener;
import com.tibco.as.space.listener.ListenerDef.DistributionScope;
import com.tibco.as.space.listener.ListenerDef.TimeScope;
import com.tibco.cep.as.kit.map.KeyValueTupleAdaptor;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.as.kit.map.SpaceMapCreator;
import com.tibco.cep.as.kit.tuple.SerializableTupleCodec;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.ASUtil;
import com.tibco.cep.runtime.management.MetricDef;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.service.basic.AsyncWorkerServiceWatcher;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASConstants;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.GvCommonUtils;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * @author Nick
 *
 */
public class ASMetricTable implements InternalMetricTable {

    public static final String METRIC_DEF_CACHE = "tibco-be-internal-metric-def-cache";
    public static final String METRIC_DATA_CACHE = "tibco-be-internal-metric-data-cache";
    public static final long EXPIRY_MILLIS = 10 * 60 * 1000; 
    protected static final FQName NAME_WILDCARD = new FQName("*");
    protected ReentrantLock adaptersLock;
    protected HashMap<FQName, ASMLAdapter> adapters;
	Metaspace metaspace;
	SpaceMap<FQName, MetricDef> defMap;
	SpaceMap<FQName, Data> dataMap;
	MemberDef cd;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.management.impl.cluster.InternalMetricTable#discard()
	 */
	@Override
	public void discard() {
		unregisterAllListener();
	    dataMap = null;
	}

    public void setConnectionDef(MemberDef cd) {
    	this.cd = cd;
    }
    
	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.management.impl.cluster.InternalMetricTable#init(java.lang.String)
	 */
	@Override
	public void init(String clusterName, String role) {
        String metaspaceName = this.sanitizeName(clusterName);
        String backupCountStr = "1";
        if (cd == null) {
        	Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        	String listenUrl,remoteListenUrl,discoverUrl;
        	if (cluster != null) {
        		GlobalVariables gVs = cluster.getRuleServiceProvider().getGlobalVariables();
    	    	listenUrl = gVs.substituteVariables(System.getProperty(ASConstants.PROP_LISTEN_URL)).toString();
    	        remoteListenUrl = gVs.substituteVariables(System.getProperty(ASConstants.PROP_REMOTE_LISTEN_URL)).toString();
    	    	discoverUrl = gVs.substituteVariables(System.getProperty(ASConstants.PROP_DISCOVER_URL)).toString();
    	        backupCountStr = gVs.substituteVariables(System.getProperty(SystemProperty.CLUSTER_BACKUP_COUNT.getPropertyName(), "1")).toString();
    	        if(GvCommonUtils.isGlobalVar(metaspaceName)){
                 	GlobalVariableDescriptor gv =gVs.getVariable(GvCommonUtils.stripGvMarkers(metaspaceName));
                 	metaspaceName=gv.getValueAsString();
                }
            } else {
        		listenUrl = System.getProperty(ASConstants.PROP_LISTEN_URL).toString();
    	        remoteListenUrl = System.getProperty(ASConstants.PROP_REMOTE_LISTEN_URL);
    	    	discoverUrl = System.getProperty(ASConstants.PROP_DISCOVER_URL);
    	        backupCountStr = System.getProperty(SystemProperty.CLUSTER_BACKUP_COUNT.getPropertyName(), "1");
            }
            cd = MemberDef.create()
                .setDiscovery(discoverUrl)
                .setListen(listenUrl)
                .setRemoteListen(remoteListenUrl);
        }
        adaptersLock = new ReentrantLock();
        adapters = new HashMap<FQName, ASMLAdapter>();
        metaspace = ASCommon.getMetaspace(metaspaceName);
        if (metaspace == null) {
            try {
                metaspace = Metaspace.connect(metaspaceName, cd);
            } catch (ASException e) {
            	throw new RuntimeException(e);
            }
        }
        DistributionRole drole = null;
        if (role != null) {
            if (role.equals("seeder")) {
                drole = DistributionRole.SEEDER;
            } else {
                drole = DistributionRole.LEECH;
            }
        } else {
            drole = DistributionRole.LEECH;
        }
        
        SpaceMapCreator.Parameters<FQName, MetricDef> defParameters =
                new SpaceMapCreator.Parameters<FQName, MetricDef>()
                      .setSpaceName(ASMetricTable.METRIC_DEF_CACHE)
                      .setRole(drole)
                      .setDistributionPolicy(DistributionPolicy.DISTRIBUTED)
                      .setKeyClass(FQName.class)
                      .setValueClass(MetricDef.class)
                      .setTupleCodec(new SerializableTupleCodec())
                      .setReplicationCount(Integer.parseInt(backupCountStr))
                      .setMinSeeders(1);

        SpaceMapCreator.Parameters<FQName, Data> dataParameters =
                new SpaceMapCreator.Parameters<FQName, Data>()
                      .setSpaceName(ASMetricTable.METRIC_DATA_CACHE)
                      .setRole(drole)
                      .setDistributionPolicy(DistributionPolicy.DISTRIBUTED)
                      .setKeyClass(FQName.class)
                      .setValueClass(Data.class)
                      .setTupleCodec(new SerializableTupleCodec())
                      .setTtl(EXPIRY_MILLIS)
                      .setReplicationCount(Integer.parseInt(backupCountStr))
                      .setMinSeeders(1);
      
        try {
            defMap = SpaceMapCreator.create(metaspace, defParameters);
            dataMap = SpaceMapCreator.create(metaspace, dataParameters);
        } catch (ASException e) {
        	throw new RuntimeException(e);
        }
    }
    
    public String sanitizeName(String s) {
        return ASUtil.asFriendlyEncode(s, null);
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.runtime.management.MetricTable#addMetricData(com.tibco.cep.runtime.util.FQName, com.tibco.cep.runtime.metrics.Data)
     */
    @Override
    public void addMetricData(FQName fqn, Data data) {
    	try {
    		dataMap.put(fqn, data);
        } catch(Throwable t) {
        	if (t.getMessage().contains("not_enough_seeders")){
        		LogManagerFactory.getLogManager().getLogger(getClass()).log(Level.DEBUG, "Metric publication failed", t);
        	}
        	else {
        		throw new RuntimeException(t);
        	}
        }
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.runtime.management.MetricTable#addMetricDef(com.tibco.cep.runtime.management.MetricDef)
     */
    @Override
    public void addMetricDef(MetricDef metricDef) {
    	try {
    		defMap.put(metricDef.getName(), metricDef);
        } catch(Throwable t) {
        	if (t.getMessage().contains("not_enough_seeders")){
        		LogManagerFactory.getLogManager().getLogger(getClass()).log(Level.DEBUG, "Metric publication failed", t);
        	}
        	else {
        		throw new RuntimeException(t);
        	}
        }
    	
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.runtime.management.MetricTable#getMetricData(com.tibco.cep.runtime.util.FQName)
     */
    @Override
    public Data getMetricData(FQName fqn) {
        return dataMap.get(fqn);
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.runtime.management.MetricTable#getMetricDef(com.tibco.cep.runtime.util.FQName)
     */
    @Override
    public MetricDef getMetricDef(FQName fqn) {
        return defMap.get(fqn);
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.runtime.management.MetricTable#getMetricDefNames()
     */
    @Override
    public Collection<FQName> getMetricDefNames() {
        return defMap.keySet();
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.runtime.management.MetricTable#getMetricNames()
     */
    @Override
    public Collection<FQName> getMetricNames() {
        return dataMap.keySet();
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.runtime.management.MetricTable#registerListener(com.tibco.cep.runtime.management.MetricTable.DataListener, com.tibco.cep.runtime.util.FQName)
     */
    @Override
    public void registerListener(DataListener listener, FQName fqnToListenTo) {
        if (fqnToListenTo == null) {
            fqnToListenTo = NAME_WILDCARD;
        }

        adaptersLock.lock();
        try {
            ASMLAdapter adapter = adapters.get(fqnToListenTo);

            if (adapter == null) {
                adapter = new ASMLAdapter();
                adapter.addListener(listener);
                ListenerDef listenerDef = ListenerDef.create(TimeScope.NEW_EVENTS, DistributionScope.ALL);

                if (fqnToListenTo == NAME_WILDCARD) {
                    dataMap.getSpace().listen(adapter, listenerDef);
                }
                else {
                    dataMap.getSpace().listen(adapter, listenerDef); /*Need filter.*/
                }
                
                adapters.put(fqnToListenTo, adapter);
                return;
            }
            if (adapter.listeners.containsKey(listener.getName())) {
            	return;
            }
            adapter.addListener(listener);
        }
        catch (ASException e) {
            throw new RuntimeException(e);
        }
        finally {
            adaptersLock.unlock();
        }
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.runtime.management.MetricTable#removeMetricData(com.tibco.cep.runtime.util.FQName)
     */
    @Override
    public Data removeMetricData(FQName fqn) {
        return dataMap.remove(fqn);
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.runtime.management.MetricTable#removeMetricDef(com.tibco.cep.runtime.util.FQName)
     */
    @Override
    public MetricDef removeMetricDef(FQName fqn) {
        return defMap.remove(fqn);
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.runtime.management.MetricTable#unregisterListener(java.lang.String, com.tibco.cep.runtime.util.FQName)
     */
    @Override
    public void unregisterListener(String listenerName, FQName fqnToListenTo) {
        if (fqnToListenTo == null) {
            fqnToListenTo = NAME_WILDCARD;
        }

        adaptersLock.lock();
        try {
            ASMLAdapter adapter = adapters.get(fqnToListenTo);

            if (adapter != null) {
                int numListenersRemaining = adapter.removeListener(listenerName);

                if (numListenersRemaining == 0) {
                    adapters.remove(fqnToListenTo);
                    adapter.discard();
                    if(dataMap!=null){
                    	dataMap.getSpace().stopListener(adapter);
                    }
                }
            }
        }
        catch(ASException e){
            throw new RuntimeException(e);
        }
        finally {
            adaptersLock.unlock();
        }
    }
    
    private void unregisterAllListener(){
    	adaptersLock.lock();
    	try{
    		List<FQName> adaptorsToBeRemoved = new ArrayList<FQName>();
    		for(Entry<FQName,ASMLAdapter> entry : adapters.entrySet()){
    			ASMLAdapter adapter = entry.getValue();
    			if (adapter != null) {
                    adaptorsToBeRemoved.add(entry.getKey());
                    adapter.discard();
                    dataMap.getSpace().stopListener(adapter);                    
                }
    		}
    		for(FQName adaptorName : adaptorsToBeRemoved){
    			adapters.remove(adaptorName);
    		}
    	}catch(ASException e){
            throw new RuntimeException(e);
        }
        finally {
            adaptersLock.unlock();
        }
    }
    
    protected class ASMLAdapter implements PutListener, TakeListener, ExpireListener {
        protected HashMap<String, DataListener> listeners;

        protected volatile DataListener[] cachedListeners;

        public ASMLAdapter() {
            listeners = new HashMap<String, DataListener>();
        }

        public void discard() {
            if(listeners!=null){
            	listeners.clear();
            }
            listeners = null;

            cachedListeners = null;
        }

        public Collection<DataListener> getListeners() {
            return listeners.values();
        }

        /**
         * Uses {@link com.tibco.cep.runtime.management.MetricTable.DataListener#getName()}.
         *
         * @param listener
         */
        public void addListener(DataListener listener) {
            String name = listener.getName();

            if (listeners.containsKey(name)) {
                String allNames = listeners.keySet().toString();

                throw new RuntimeException(
                        "Another listener already exists with the same name: " + allNames);
            }

            listeners.put(name, listener);
            cachedListeners = recacheListeners();
        }

        private DataListener[] recacheListeners() {
            int size = listeners.size();
            return listeners.values().toArray(new DataListener[size]);
        }

        /**
         * @param listenerName
         * @return Current number of listeners.
         */
        public int removeListener(String listenerName) {
            DataListener listener = listeners.remove(listenerName);

            if (listener != null) {
                cachedListeners = recacheListeners();
            }

            return listeners.size();
        }

        //--------------
        @Override
        public void onExpire(ExpireEvent expireEvent) {
        }

        @Override
        public void onPut(PutEvent putEvent) {
            FQName key = null;
            Data newValue = null;

            Tuple tuple = putEvent.getOldTuple();
            try {
                KeyValueTupleAdaptor<FQName, Data> tupleAdaptor = dataMap.getTupleAdaptor();

                key = tupleAdaptor.extractKey(tuple);
                newValue = tupleAdaptor.extractValue(tuple);
            }
            finally {

            }
            notifyListeners(key, newValue);
        }

        @Override
        public void onTake(TakeEvent takeEvent) {
        }
    
        protected void notifyListeners(FQName key, Data value) {
            DataListener[] listeners = cachedListeners;
            if (listeners == null) {
                return;
            }
    
            FQName fqname = (FQName) key;
            Data data = (Data) value;
            for (DataListener listener : listeners) {
                try {
                	if((fqname!=null) && (fqname.getComponentNames().length>=4) && (fqname.getComponentNames()[2]!=null) && (fqname.getComponentNames()[3]!=null)
                			&&((fqname.getComponentNames()[2]+fqname.getComponentNames()[3]).equalsIgnoreCase(listener.getName()))){
                		listener.onNew(fqname, data);
                	}else if ((fqname!=null) && (fqname.getComponentNames().length>=4) && fqname.getComponentNames()[4].equals(AsyncWorkerServiceWatcher.AsyncWorkerService.class.getSimpleName())){
                		listener.onNew(fqname, data);
                	}
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
