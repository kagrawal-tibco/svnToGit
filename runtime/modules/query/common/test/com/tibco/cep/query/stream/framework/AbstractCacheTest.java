/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 11/8/2010
 */

package com.tibco.cep.query.stream.framework;

import com.tangosol.net.NamedCache;
import com.tibco.be.util.BEProperties;
import com.tibco.be.util.config.ClusterConfig;
import com.tibco.be.util.config.ProcessingUnitConfig;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.aggregate.AggregatedStream;
import com.tibco.cep.query.stream.core.Sink;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.group.GroupAggregateInfo;
import com.tibco.cep.query.stream.group.GroupAggregateItemInfo;
import com.tibco.cep.query.stream.group.GroupAggregateTransformer;
import com.tibco.cep.query.stream.group.GroupKey;
import com.tibco.cep.query.stream.impl.rete.ReteEntityInfo;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySource;
import com.tibco.cep.query.stream.impl.rete.ReteEntityWithdrawableSource;
import com.tibco.cep.query.stream.impl.rete.ReteEntityWithdrawableSourceImpl;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.query.ReteQueryImpl;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.*;
import com.tibco.cep.query.stream.sort.SortInfo;
import com.tibco.cep.query.stream.sort.SortItemInfo;
import com.tibco.cep.query.stream.sort.SortedStream;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.repo.ResourceProviderFactory;
import com.tibco.cep.repo.provider.BEArchiveResourceProvider;
import com.tibco.cep.repo.provider.SMapContentProvider;
import com.tibco.cep.repo.provider.SharedArchiveResourceProvider;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransactionProperties;
import com.tibco.cep.runtime.service.om.impl.coherence.cluster.BEEntityCacheV2;
import com.tibco.cep.runtime.service.om.impl.coherence.cluster.CacheClusterConfiguration;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.objectrepo.vfile.VFileFactory;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.tns.cache.TnsCache;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Author: Ashwin Jayaprakash Date: Mar 10, 2008 Time: 7:55:26 PM
 */

public class AbstractCacheTest extends AbstractTest {

    private AtomicBoolean hackedRSP = new AtomicBoolean(false);
    protected NamedCache masterCache;

    protected HashMap<Class, NamedCache> entityCaches = new HashMap<Class, NamedCache>();

    protected HackedCacheCluster hackedCacheCluster;
    protected static RuleServiceProvider rsp;
    protected HackedCacheClusterConfiguration hackedCacheClusterConfig;
    RtcTransactionProperties txnProps = new RtcTransactionProperties();
    protected static final AtomicInteger txnId = new AtomicInteger(0);
    protected static int agentId = 1;
    protected static final Map<String, Integer> classToTypeMap = new HashMap<String, Integer>();
    private static final String[] classes = new String[]{
            "be.gen.PersonEvent", "be.gen.Events.WindowEvent1",
            "be.gen.Concepts.Address", "be.gen.Concepts.Order",
            "be.gen.Concepts.Customer", "be.gen.Concepts.SilverCustomer",
            "be.gen.Concepts.GoldCustomer", "be.gen.Concepts.GoldCustomerSecondaryMember"
    };
    // class instances
    protected Class customerClass;
    protected Class addressClass;
    protected Class personEventClass;
    protected Class windowEventClass;
    protected Class orderClass;
    // Rtc Transaction helper
    protected RtcTransactionHelper rtcTransactionHelper;
    // Event helper
    protected EventHelper eventHelper;
    // Concept helper
    protected ConceptHelper conceptHelper;

    static {
        System.getProperties().put("tangosol.coherence.cluster", "$TEST_CLUSTER$");
    }

    @Override
    @BeforeMethod(groups = {TestGroupNames.RUNTIME})
    public void beforeTest() {
        super.beforeTest();
        masterCache = master.getAgentService().getMasterCache();
        hackAndInterceptRSP();
        try {
            initClassTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        rtcTransactionHelper = new RtcTransactionHelper(master, hackedCacheCluster, txnProps, txnId);
        conceptHelper = new ConceptHelper(master, rtcTransactionHelper, masterCache, agentId,
                hackedCacheCluster, entityCaches, classToTypeMap);
        eventHelper = new EventHelper(new String[]{
                "be.gen.PersonEvent", "be.gen.Events.WindowEvent1"}, classToTypeMap, rtcTransactionHelper,
                conceptHelper, txnId, agentId, masterCache);
    }

    protected void initClassTypes() throws Exception {
        HashMap<Integer, Class> map = master.getAgentService().getTypeToClassMap();
        int id = MetadataCache.BE_TYPE_START;
        for (String name : classes) {
            Class klass = Class.forName(name);
            if (name.equals("be.gen.Concepts.Address")) {
                addressClass = klass;
            } else if (name.equals("be.gen.Concepts.Customer")) {
                customerClass = klass;
            } else if (name.equals("be.gen.PersonEvent")) {
                personEventClass = klass;
            } else if (name.equals("be.gen.Events.WindowEvent1")) {
                windowEventClass = klass;
            } else if (name.equals("be.gen.Concepts.Order")) {
                orderClass = klass;
            }
            map.put(id, klass);
            classToTypeMap.put(name, id++);
        }
    }

    @AfterMethod(groups = {TestGroupNames.RUNTIME}, alwaysRun = true)
    public void afterTest() {
        super.afterTest();
        System.getProperties().remove("tangosol.coherence.cluster");
        masterCache.clear();
        masterCache = null;

        for (NamedCache nc : entityCaches.values()) {
            nc.clear();
        }
        entityCaches.clear();
        entityCaches = null;
    }

    @Override
    public ReteQuery getReteQuery(String resourceId, Source[] sources) {
        return new ReteQueryImpl(master.getAgentService().getName(), new ResourceId(resourceId),
                (ReteEntitySource[]) sources, sink, false, null);
    }

    protected void hackAndInterceptRSP() {
        if (hackedRSP.compareAndSet(false, true) == false) {
            return;
        }
        try {
            AgentServiceImpl.TypeManagerImpl beClassLoader =
                    master.getAgentService().getEntityClassLoader();

            Field qMgr = CacheClusterProvider.class.getDeclaredField("qMgr");
            qMgr.setAccessible(true);

            qMgr.set(null, new CacheClusterProvider());

            Field m_cacheCluster = CacheClusterProvider.class.getDeclaredField("m_cacheCluster");
            m_cacheCluster.setAccessible(true);

            rsp = new HackedRSP(beClassLoader);
            RuleServiceProviderManager.getInstance().setDefaultProvider(rsp);

            hackedCacheClusterConfig = new HackedCacheClusterConfiguration();
            hackedCacheCluster = new HackedCacheCluster(hackedCacheClusterConfig, rsp);
            hackedCacheCluster.getMetadataCache().setAgentService(master.getAgentService());
            m_cacheCluster.set(null, hackedCacheCluster);
            master.setCacheCluster(hackedCacheCluster);


        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected ReteEntityWithdrawableSource getSource(Class klass) {
        final ReteEntityInfo reteEntityInfo = eventHelper.getReteEntityInfo(klass);
        return initPersonEventsSource(reteEntityInfo);
    }

    protected Sink getSink(Class klass, ReteEntityWithdrawableSource source) {
        return new StreamedSink(getSourceStream(klass, source), new ResourceId("Sink"));
    }

    // Private methods.
    private ReteEntityWithdrawableSource initPersonEventsSource(ReteEntityInfo reteEntityInfo) {
        ReteEntityWithdrawableSource source = new ReteEntityWithdrawableSourceImpl(new ResourceId(
                "source"), reteEntityInfo, personEventClass);
        sources.put("source1", source);
        return source;
    }

    protected Stream getSourceStream(Class klass, ReteEntityWithdrawableSource source) {
        LinkedHashMap<String, GroupAggregateItemInfo> itemInfos = eventHelper.getItemInfos(klass);
        GroupAggregateInfo groupAggregateInfo = new GroupAggregateInfo(itemInfos);
        final TupleInfo outputTupleInfo = eventHelper.getTupleInfo(klass);
        WindowBuilder builder = getWindowBuilder(source.getOutputInfo());
        AggregatedPartitionedStream stream = new AggregatedPartitionedStream(source
                .getInternalStream(), new ResourceId("aggregate-partition"), outputTupleInfo,
                groupAggregateInfo, builder);
        TupleValueExtractor[] extractors = eventHelper.getPersonEventTupleValueExtractors();
        List<Comparator<Object>> comparators = eventHelper.getPersonEventComparators();
        SortInfo sortInfo =
                new SortInfo(new SortItemInfo[]{new SortItemInfo(true), new SortItemInfo(true)});
        return new SortedStream(stream, new ResourceId("sort"), sortInfo, extractors, comparators);
    }

    protected WindowBuilder getWindowBuilder(final TupleInfo tupleInfo) {
        return new WindowBuilder() {
            public Window buildAndInit(
                    ResourceId parentResourceId, String id, GroupKey groupKey,
                    AggregateInfo aggregateInfo,
                    WindowInfo windowInfo,
                    WindowOwner windowOwner,
                    GroupAggregateTransformer aggregateTransformer) {
                Window window = new SimpleAggregatedStreamWindow(new ResourceId(parentResourceId,
                        "SimpleWindow:" + id), tupleInfo, groupKey, windowOwner);

                AggregatedStream aggregatedStream = new AggregatedStream(window, new ResourceId(
                        parentResourceId, "Aggregate:" + id), aggregateInfo,
                        true);

                window.init(aggregateTransformer, aggregatedStream, null);

                return window;
            }
        };
    }

    //-----------

    protected static class HackedCacheCluster extends CacheCluster {

        protected HackedMetadataCache metadataCache;

        protected HackedCacheCluster(
                CacheClusterConfiguration clusterConfig,
                RuleServiceProvider RSP)
                throws Exception {
            super(clusterConfig, RSP);

            this.metadataCache = new HackedMetadataCache(this);
            initialize();
            loadMetadata();
        }

        @Override
        public HackedMetadataCache getMetadataCache() {
            return metadataCache;
        }

        @Override
        public String getClusterName() {
            return "$dummy-cluster$";
        }

        @Override
        public com.tibco.cep.runtime.service.om.api.ClusterEntityProvider getEntityProvider(int typeId) {
            return new BEEntityCacheV2();
        }
    }

    protected static class HackedMetadataCache extends MetadataCache {

        protected AgentServiceImpl agentService;

        public HackedMetadataCache(CacheCluster cluster) throws Exception {
            super(cluster);
            // This hack is required to initialize the Topic Registry internal data structures properly.
            Class metadataCacheClass = Class.forName(MetadataCache.class.getName());
            Field field = metadataCacheClass.getDeclaredField("typeIdToClass");
            field.setAccessible(true);
            field.set(this, new Class[classes.length]);
        }

        public AgentServiceImpl getAgentService() {
            return agentService;
        }

        public void setAgentService(AgentServiceImpl agentService) {
            this.agentService = agentService;
        }

        @Override
        public Integer getTypeId(Class entityClz) throws Exception {
            for (Map.Entry<Integer, Class> o : agentService.getTypeToClassMap().entrySet()) {
                if (o.getValue().equals(entityClz)) {
                    return o.getKey();
                }
            }

            return -1;
        }

        @Override
        public Class getClass(int typeId) {
            return agentService.getTypeToClassMap().get(typeId);
        }

        @Override
        protected void loadOntology() throws Exception {

        }

        @Override
        protected void loadClasses(Map typeMap) throws Exception {

        }

    }

    protected static class HackedRSP extends RuleServiceProviderImpl {

        protected AgentServiceImpl.TypeManagerImpl typeManager;
        protected Logger logger = new SimpleLogger();

        public HackedRSP(AgentServiceImpl.TypeManagerImpl typeManager)
                throws Exception {
            super("$$dummy-RSP$$", new BEProperties());
            this.typeManager = typeManager;
        }

        @Override
        protected void initialize(Properties env) throws Exception {
        }

        @Override
        public Properties getProperties() {
            return new BEProperties();
        }

        @Override
        public ClassLoader getClassLoader() {
            return typeManager;
        }

        public DeployedProject getProject() {
            return new DeployedProject() {
                @Override
                public Properties getProperties() {
                    return null;
                }

                public BEArchiveResourceProvider getBEArchiveResourceProvider() {
                    return null;
                }

                public SharedArchiveResourceProvider getSharedArchiveResourceProvider() {
                    return null;
                }

                public ArchiveResourceProvider getJavaArchiveResourceProvider() {
                    return null;
                }

                public Collection getDeployedBEArchives() {
                    return Collections.emptyList();
                }

                public String getName() {
                    return null;
                }

                public String getOwner() {
                    return null;
                }

                public Date getCreationDate() {
                    return null;
                }

                public String getVersion() {
                    return null;
                }

                public boolean isCacheEnabled() {
                    return false;
                }

                public Ontology getOntology() {
                    return new DefaultMutableOntology();
                }

                public GlobalVariables getGlobalVariables() {
                    return null;
                }

                public TnsCache getTnsCache() {
                    return null;
                }

                public VFileFactory getVFileFactory() {
                    return null;
                }

                public void load() throws Exception {
                }

                public void close() {
                }

                public SmElement getSmElement(com.tibco.cep.designtime.model.Entity e) {
                    return null;
                }

                public ResourceProviderFactory getProviderFactory() {
                    return null;
                }

                public SmElement getSmElement(ExpandedName name) {
                    return null;
                }

                public boolean isValidDesignerProject() {
                    return false;
                }

                @Override
                public SMapContentProvider getSMapContenProvider() {
                    return null;  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void startHotDeploy(RuleServiceProvider rsp) throws Exception {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void stopHotDeploy(RuleServiceProvider rsp) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public String getRepoPath() {
                    return null;  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public ClusterConfig getClusterConfig() {
                    return null;  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public ProcessingUnitConfig getProcessingUnitConfig() {
                    return null;  //To change body of implemented methods use File | Settings | File Templates.
                }
            };
        }

        public Logger getLogger() {
            return logger;
        }

        public TypeManager getTypeManager() {
            return typeManager;
        }

        @Override
        public boolean isMultiEngineMode() {
            return false;
        }
    }

    protected static class HackedCacheClusterConfiguration extends CacheClusterConfiguration {

        private HackedCacheClusterConfiguration() {
            // fake initialization.
            super(null, false, false, false, 300, 2, null, false, null, false, null, 100, true, 0L, null, null, null,
                    false, false, false, false, false, false);
            CacheClusterConfiguration config = null;
            try {
                config = CacheClusterConfiguration.getCacheConfig(new Properties(), rsp);
                // Now set the default values back.
                this.setAutoFailover(config.isAutoFailover());
                this.setAutoFailoverInterval(config.getAutoFailoverInterval());
                this.setCacheAside(config.isCacheAside());
                this.setClusterName(config.getClusterName());
                this.setConfigXML(config.getConfigXML());
                this.setEntityMediatorType(config.getEntityMediatorType());
                this.setHasBackingStore(config.isHasBackingStore());
                this.setKeepDeleted(config.isKeepDeleted());
                this.setMinCacheServers(config.getMinCacheServers());
                this.setUsePrimaryDatasource(config.isUsePrimaryDatasource());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
