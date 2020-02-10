package com.tibco.cep.query.stream.impl.rete.integ;

import java.util.Properties;

import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.query.service.QueryProperty;
import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.cache.CacheBuilder;
import com.tibco.cep.query.stream.cache.CacheBuilder.Type;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.impl.rete.ReteEntityClassHierarchyHelper;
import com.tibco.cep.query.stream.impl.rete.integ.container.SharedObjectSourceRepositoryImpl;
import com.tibco.cep.query.stream.impl.rete.integ.standalone.QueryObjectManager;
import com.tibco.cep.query.stream.impl.rete.integ.standalone.QueryWorkingMemory;
import com.tibco.cep.query.stream.impl.rete.service.AgentService;
import com.tibco.cep.query.stream.impl.rete.service.CacheScout;
import com.tibco.cep.query.stream.impl.rete.service.RegionManager;
import com.tibco.cep.query.stream.impl.rete.service.ReteEntityDispatcher;
import com.tibco.cep.query.stream.impl.rete.service.SnapshotQueryManager;
import com.tibco.cep.query.stream.monitor.CustomMultiSourceException;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.util.SystemProperty;

/*
 * Author: Ashwin Jayaprakash Date: Mar 7, 2008 Time: 3:06:31 PM
 */

public class RegionManagerImpl implements RegionManager {
    protected final ResourceId id;

    protected final String regionName;

    protected final QueryDataProvider queryDataProvider;

    protected final ReteEntityDispatcher dispatcher;

    protected final SharedObjectSourceRepository sosRepo;

    protected final WorkingMemory workingMemory;

    protected final AgentService agentService;

    protected final SnapshotQueryManager ssQueryManager;

    protected final ReteEntityClassHierarchyHelper hierarchyHelper;

    protected final CacheScout cacheScout;

    protected final Cache primaryCache;

    protected final Logger logger;

    private final boolean handleWMLifecycle;

    /**
     * @param parentId
     * @param regionName
     * @param properties
     * @param agentService
     * @param cachePrefetchAggressive
     * @param standalone
     */
    public RegionManagerImpl(ResourceId parentId, String regionName, Properties properties,
                             AgentService agentService,
                             boolean cachePrefetchAggressive, boolean standalone) {
        this(parentId, regionName, properties, agentService, null, null, cachePrefetchAggressive,
                standalone);
    }

    /**
     * @param parentId
     * @param regionName
     * @param properties
     * @param agentService
     * @param wm                      Can be <code>null</code>.
     * @param om                      Can be <code>null</code> if "wm" is <code>null</code>.
     * @param cachePrefetchAggressive
     * @param standalone
     */
    public RegionManagerImpl(ResourceId parentId, String regionName, Properties properties,
                             AgentService agentService,
                             WorkingMemory wm,
                             BaseObjectManager om,
                             boolean cachePrefetchAggressive, boolean standalone) {
        this.id = new ResourceId(parentId, regionName);
        this.regionName = regionName;

        this.logger = Registry.getInstance().getComponent(Logger.class);

        this.agentService = agentService;

        BEClassLoader beClassLoader = agentService.getEntityClassLoader();
        this.hierarchyHelper = new ReteEntityClassHierarchyHelper(beClassLoader);

        CacheBuilder cacheBuilder = Registry.getInstance().getComponent(CacheBuilder.class);
        Cache deadPoolCache = cacheBuilder.build(regionName, this.id, Type.DEADPOOL);

        if (wm == null) {
            this.primaryCache = cacheBuilder.build(regionName, this.id, Type.PRIMARY);

            QueryObjectManager qom = new QueryObjectManager(this.id, regionName, agentService,
                    primaryCache, deadPoolCache, standalone);

            this.sosRepo = qom;

            LogManager logManager = LogManagerFactory.getLogManager();

            this.workingMemory = new QueryWorkingMemory(regionName, qom, logManager);

            this.handleWMLifecycle = true;
        }
        else {
            this.sosRepo = new SharedObjectSourceRepositoryImpl(this.id, regionName, om,
                    deadPoolCache, agentService);

            this.primaryCache = this.sosRepo.getPrimaryCache();

            this.workingMemory = wm;
            this.handleWMLifecycle = false;
        }

        this.dispatcher = new ReteEntityDispatcher(this.id, this.sosRepo);

        this.ssQueryManager = new SnapshotQueryManager(this.id, this.sosRepo);

        this.cacheScout = new DefaultCacheScout(this.id, this.sosRepo, cachePrefetchAggressive);

        //-------------

        try {
            String propName = QueryProperty.QUERYDATAPROVIDER_CLASSNAME.getPropName();
            String qdpClassName = properties.getProperty(propName, CacheListener.class.getName());
            //for MM usage: determine if the cache is AS or Coherence
            if(qdpClassName!=null){
            	if(qdpClassName.endsWith("MetricDataListener")){
            		qdpClassName = this.determineClassName(qdpClassName);
            	}
            }
            Class clazz = Class.forName(qdpClassName, true, getClass().getClassLoader());

            if (QueryDataProvider.class.isAssignableFrom(clazz) == false) {
                throw new IllegalArgumentException(
                        "The implementation class specified by the property [" + propName
                                + "] must implement [" + QueryDataProvider.class.getName() + "].");
            }

            this.queryDataProvider = (QueryDataProvider) clazz.newInstance();

            this.queryDataProvider.setParentId(this.id);
            this.queryDataProvider.setProperties(properties);
            this.queryDataProvider.setBEClassLoader(beClassLoader);
            this.queryDataProvider.setWorkingMemory(this.workingMemory);
            this.queryDataProvider.setLogger(this.logger);
            this.queryDataProvider.setAgentService(this.agentService);
            this.queryDataProvider.setPrimaryCache(this.primaryCache);
            this.queryDataProvider.setDeadpoolCache(deadPoolCache);
            this.queryDataProvider.setSharedObjectSourceRepository(this.sosRepo);
            this.queryDataProvider.setReteEntityDispatcher(this.dispatcher);
            this.queryDataProvider.setSnapshotQueryManager(this.ssQueryManager);
            this.queryDataProvider.setCacheScout(this.cacheScout);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public ResourceId getResourceId() {
        return id;
    }

    public AgentService getAgentService() {
        return agentService;
    }

    public Cache getPrimaryCache() {
        return primaryCache;
    }

    public CacheScout getCacheScout() {
        return cacheScout;
    }

    public QueryDataProvider getQueryDataProvider() {
        return queryDataProvider;
    }

    public ReteEntityClassHierarchyHelper getHierarchyHelper() {
        return hierarchyHelper;
    }

    public ReteEntityDispatcher getDispatcher() {
        return dispatcher;
    }

    public SharedObjectSourceRepository getSOSRepository() {
        return sosRepo;
    }

    public String getRegionName() {
        return regionName;
    }

    public WorkingMemory getWorkingMemory() {
        return workingMemory;
    }

    public SnapshotQueryManager getSSQueryManager() {
        return ssQueryManager;
    }

    public void start() throws Exception {
        try {
            hierarchyHelper.start();

            if (handleWMLifecycle) {
                workingMemory.start(true);
            }

            dispatcher.start();
            ssQueryManager.start();

            queryDataProvider.start();

            cacheScout.start();
        }
        catch (Exception e) {
            doStop(false);

            throw e;
        }
    }

    private void doStop(boolean logErrors) throws CustomMultiSourceException {
        CustomMultiSourceException errorRecorder =
                logErrors ? new CustomMultiSourceException(id) : null;

        try {
            cacheScout.stop();
        }
        catch (Exception e) {
            if (logErrors) {
                errorRecorder.addSource(new CustomMultiSourceException.Source(e));
            }
        }

        try {
            queryDataProvider.stop();
        }
        catch (Exception e) {
            if (logErrors) {
                errorRecorder.addSource(new CustomMultiSourceException.Source(e));
            }
        }

        try {
            ssQueryManager.stop();
        }
        catch (Exception e) {
            if (logErrors) {
                errorRecorder.addSource(new CustomMultiSourceException.Source(e));
            }
        }

        try {
            dispatcher.stop();
        }
        catch (Exception e) {
            if (logErrors) {
                errorRecorder.addSource(new CustomMultiSourceException.Source(e));
            }
        }

        if (handleWMLifecycle) {
            try {
                workingMemory.stop();
            }
            catch (Exception e) {
                if (logErrors) {
                    errorRecorder.addSource(new CustomMultiSourceException.Source(e));
                }
            }
        }

        hierarchyHelper.stop();

        id.discard();

        if (logErrors && errorRecorder.hasSources()) {
            throw errorRecorder;
        }
    }

    public void stop() throws Exception {
        doStop(true);
    }
    
    private String determineClassName(String oldClassName){
        String className = System.getProperty(SystemProperty.VM_DAOPROVIDER_CLASSNAME.getPropertyName(),
                SystemProperty.VM_DAOPROVIDER_CLASSNAME.getValidValues()[0].toString());
        int index = oldClassName.lastIndexOf(".");
        String prefix = oldClassName.substring(0,index+1);
        if(className.equals(SystemProperty.VM_DAOPROVIDER_CLASSNAME.getValidValues()[0].toString())){
        	//case AS
            return prefix + "ASMetricDataListener";
        }
        else{//case coherence
            return prefix + "CoherenceMetricDataListener";	            	
        }
    }
}
