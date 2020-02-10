package com.tibco.cep.runtime.management.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.management.ManagementCentral;
import com.tibco.cep.runtime.management.ManagementTable;
import com.tibco.cep.runtime.management.ManagementTableMBean;
import com.tibco.cep.runtime.management.MetricTable;
import com.tibco.cep.runtime.management.impl.cluster.InternalManagementTable;
import com.tibco.cep.runtime.management.impl.cluster.InternalMetricTable;
import com.tibco.cep.runtime.management.impl.cluster.LocalManagementTable;
import com.tibco.cep.runtime.management.impl.cluster.LocalMetricTable;
import com.tibco.cep.runtime.management.impl.cluster.ManagementTableMBeanImpl;
import com.tibco.cep.runtime.management.impl.metrics.AsyncWorkerDataPublisher;
import com.tibco.cep.runtime.management.impl.metrics.StopWatcher;
import com.tibco.cep.runtime.metrics.StopWatchManager;
import com.tibco.cep.runtime.metrics.StopWatchOwner;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.basic.DependencyWatcher;
import com.tibco.cep.runtime.service.basic.Dependent;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.management.EntityMBeansManager;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Ashwin Jayaprakash Date: Jan 27, 2009 Time: 5:26:50 PM
*/
public class DefaultManagementCentral implements ManagementCentral, Dependent {
    protected InternalManagementTable managementTable;

    protected InternalMetricTable metricTable;

    protected ScheduledExecutorService executorService;

    protected AsyncWorkerDataPublisher workerDataPublisher;

    protected StopWatcher stopWatcher;
    
    private String classNamePrefix = "com.tibco.cep.runtime.management.impl.cluster.";

    public static final String OBJ_NAME_MNGMT_TABLE = "com.tibco.be:dir=Methods,Group=Management Table";

    public String getId() {
        return getClass().getName();
    }

    public void init(Configuration configuration, Object... otherArgs) throws Exception {
        ManagementCentralThreadFactory threadFactory = new ManagementCentralThreadFactory();
        executorService = Executors.newScheduledThreadPool(1, threadFactory);
        stopWatcher = new StopWatcher();
        workerDataPublisher = new AsyncWorkerDataPublisher();
    }

    public void start() throws Exception {
        //Delayed init.
        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        DependencyWatcher watcher = registry.getDependencyWatcher();
        watcher.registerDependency(DEP_KEY_CLUSTER_INIT, this);
    }

    public void stop() throws Exception {
        executorService.shutdown();

        workerDataPublisher.discard();

        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        StopWatchManager stopWatchManager = registry.getService(StopWatchManager.class);
        if(stopWatchManager != null) {
        	if(stopWatcher != null){
        		stopWatchManager.removeListener(stopWatcher);
        		stopWatcher.discard();
        	}
        }
        if(metricTable != null) {
        	metricTable.discard();
        }
        if(managementTable != null){
        	managementTable.discard();
        }
    }

    //------------

    public String whoAmI() {
        return DEP_KEY_CLUSTER_INIT + ":" + getClass().getName() + ":Dependent";
    }

    public Class[] getNotificationParameterTypes() {
        return new Class[]{Boolean.class, String.class};
    }

    public Object[] getNotificationParameterDesc() {
        return new Object[]{SystemProperty.VM_NETWORK_MODE_STANDALONE,
                SystemProperty.VM_NETWORK_CLUSTER_NAME};
    }

    public void onNotify(Object... args) {
        boolean standalone = false;
        String clusterName = null;
        Cluster cluster = null;

        try {
            standalone = (Boolean) args[0];
            clusterName = (String) args[1];
            cluster = (args.length > 2) ? (Cluster) args[2] : null;
        }
        catch (Exception e) {
            throw new RuntimeException(
                    "The following parameters: " + Arrays.asList(getNotificationParameterTypes()) +
                            " are expected. Received: " + Arrays.asList(args));
        }

        //------------

        if (cluster == null) {
            standalone = true;
        } else {
             //todo Cache provider type = cluster.getDaoProvider();
        }
        if (standalone) {
        	managementTable = new LocalManagementTable();
        	metricTable = new LocalMetricTable();
            //Used by MM to monitor BE engines running In-Memory mode
            EntityMBeansManager.registerStdMBean(OBJ_NAME_MNGMT_TABLE, managementTable, ManagementTable.class);
        }
        else {
        	try {
        	    // TODO: Do not create space if metrics are not enabled
	            String className = System.getProperty(SystemProperty.VM_DAOPROVIDER_CLASSNAME.getPropertyName(),
	                    SystemProperty.VM_DAOPROVIDER_CLASSNAME.getValidValues()[0].toString());
	            if (className.equals(SystemProperty.VM_DAOPROVIDER_CLASSNAME.getValidValues()[0].toString())){
	            	//case AS
	                Class<InternalManagementTable> cls = (Class<InternalManagementTable>) Class.forName(classNamePrefix+"ASManagementTable");
	                managementTable = cls.newInstance();
	                Class<InternalMetricTable> cls1 = (Class<InternalMetricTable>) Class.forName(classNamePrefix+"ASMetricTable");
	                metricTable = cls1.newInstance();
	            }
	            else { //case coherence
	                Class<InternalManagementTable> cls = (Class<InternalManagementTable>) Class.forName(classNamePrefix+"CoherenceManagementTable");
	                managementTable = cls.newInstance();
	                Class<InternalMetricTable> cls1 = (Class<InternalMetricTable>) Class.forName(classNamePrefix+"CoherenceMetricTable");
	                metricTable = cls1.newInstance();	            	
	            }
	            
        	} catch(Exception e) {
        		e.printStackTrace();
        		throw new RuntimeException("Could not initialize managementTable and metricTable: "+e.getMessage());
        	}
        }

        String s = System.getProperty(SystemProperty.CLUSTER_NODE_ISSEEDER.getPropertyName(), "false");
        boolean storageEnabled = Boolean.parseBoolean(s);
        if (storageEnabled) {
            s = "seeder";
        }
        else {
            s = "leech";
        }
        managementTable.init(clusterName, s);
        metricTable.init(clusterName, s);

        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();

        stopWatcher.init(metricTable);

        StopWatchManager stopWatchManager = registry.getService(StopWatchManager.class);
        //There must've been some already running. Get those first.
        Collection<? extends StopWatchOwner> alreadyRunningOwners = stopWatchManager.fetchOwners();
        for (StopWatchOwner alreadyRunningOwner : alreadyRunningOwners) {
            stopWatcher.onNew(alreadyRunningOwner);
        }
        //Register for new watches.
        stopWatchManager.addListener(stopWatcher);

        DependencyWatcher watcher = registry.getDependencyWatcher();
        watcher.unregisterDependency(DEP_KEY_CLUSTER_INIT, this);

        workerDataPublisher.init(executorService, metricTable);
        
    }

    //------------

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }

    public ManagementTable getManagementTable() {
        return managementTable;
    }

    public MetricTable getMetricTable() {
        return metricTable;
    }

    //------------

    protected static class ManagementCentralThreadFactory implements ThreadFactory {
        protected AtomicInteger counter;

        protected ThreadGroup threadGroup;

        public ManagementCentralThreadFactory() {
            this.counter = new AtomicInteger();
            this.threadGroup = new ThreadGroup(DefaultManagementCentral.class.getSimpleName());
        }

        public Thread newThread(Runnable r) {
            String name = ManagementCentral.class.getSimpleName()
                    + ":" + ScheduledExecutorService.class.getSimpleName()
                    + ":" + counter.incrementAndGet();

            Thread t = new Thread(threadGroup, r, name);
            t.setDaemon(true);
            t.setPriority(Thread.MIN_PRIORITY);

            return t;
        }
    }
}
