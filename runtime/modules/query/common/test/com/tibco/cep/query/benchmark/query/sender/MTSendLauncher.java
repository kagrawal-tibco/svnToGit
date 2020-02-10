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

package com.tibco.cep.query.benchmark.query.sender;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.UID;
import com.tibco.be.util.BEProperties;
import com.tibco.be.util.config.ClusterConfig;
import com.tibco.be.util.config.ProcessingUnitConfig;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.repo.ResourceProviderFactory;
import com.tibco.cep.repo.provider.BEArchiveResourceProvider;
import com.tibco.cep.repo.provider.SMapContentProvider;
import com.tibco.cep.repo.provider.SharedArchiveResourceProvider;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.PayloadFactory;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.om.impl.coherence.cluster.AgentTableCache;
import com.tibco.cep.runtime.service.om.impl.coherence.cluster.CacheClusterConfiguration;
import com.tibco.cep.runtime.service.om.impl.coherence.cluster.EntityCacheName;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.objectrepo.vfile.VFileFactory;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.tns.cache.TnsCache;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/*
* Author: Ashwin Jayaprakash Date: Aug 26, 2008 Time: 8:04:43 PM
*/

public class MTSendLauncher {
    protected NamedCache masterCache;

    protected NamedCache cardSwipeCache;

    public MTSendLauncher(String clusterName, String agentName) throws Exception {
        int agentIdgentId = (int) System.currentTimeMillis();
        String masterCacheName = EntityCacheName.getCacheName(
                EntityCacheName.DISTRIBUTED_CACHE_AGENTTXNLOG, clusterName, agentName,
                "AgentTxn-" + agentIdgentId).toCacheName();
        this.masterCache = CacheFactory.getCache(masterCacheName);

        this.cardSwipeCache = CacheFactory
                .getCache("dist-unlimited-nobs$" + clusterName +
                        "$$be.gen.ObjectDefinitions.CardSwipeEvent");

        NamedCache agentTable = CacheFactory
                .getCache(EntityCacheName.getCacheName(EntityCacheName.REPLICATED_CACHE_UNLIMITED,
                        clusterName, "", "AgentTable").toCacheName());

        System.err.println();
        System.err.println();
        System.err.println("Master: " + masterCache);
        System.err.println("CardSwipe: " + cardSwipeCache);
        System.err.println("Agent: " + agentTable);
        System.err.println();
        System.err.println();

        //--------------

        TypeManagerImpl beClassLoader = new TypeManagerImpl(MTSendLauncher.class.getClassLoader());

        Field qMgr = CacheClusterProvider.class.getDeclaredField("qMgr");
        qMgr.setAccessible(true);

        qMgr.set(null, new CacheClusterProvider());

        Field m_cacheCluster = CacheClusterProvider.class.getDeclaredField("m_cacheCluster");
        m_cacheCluster.setAccessible(true);

        RuleServiceProvider rsp = new HackedRSP(beClassLoader);

        CacheCluster cacheCluster = new HackedCacheCluster(null, rsp);
        m_cacheCluster.set(null, cacheCluster);

        CacheClusterProvider.getInstance().getCacheCluster().initialize();

        AgentTableCache.AgentNode node = new AgentTableCache.AgentNode();
        Field f = AgentTableCache.AgentNode.class.getDeclaredField("agentType");
        f.setAccessible(true);
        f.setInt(node, CacheAgent.AGENT_TYPE_INFERENCE);

        f = AgentTableCache.AgentNode.class.getDeclaredField("agentId");
        f.setAccessible(true);
        f.setInt(node, agentIdgentId);

        f = AgentTableCache.AgentNode.class.getDeclaredField("agentName");
        f.setAccessible(true);
        f.set(node, agentName);

        f = AgentTableCache.AgentNode.class.getDeclaredField("nodeId");
        f.setAccessible(true);
        f.set(node, new UID());

        f = AgentTableCache.AgentNode.class.getDeclaredField("state");
        f.setAccessible(true);
        f.setByte(node, CacheAgent.AGENT_ACTIVATED);

        f = AgentTableCache.AgentNode.class.getDeclaredField("priority");
        f.setAccessible(true);
        f.setInt(node, 1);

        f = AgentTableCache.AgentNode.class.getDeclaredField("suspended");
        f.setAccessible(true);
        f.setBoolean(node, false);

        f = AgentTableCache.AgentNode.class.getDeclaredField("maxActive");
        f.setAccessible(true);
        f.setInt(node, 0);
        agentTable.put(agentIdgentId, node);
    }

    public void keepSending(int numThreads) {
        final AtomicLong eventIdCounter = new AtomicLong(0);
        final ConcurrentLinkedQueue<Long> deleteTxnIdQ = new ConcurrentLinkedQueue<Long>();
        final AtomicBoolean stopFlag = new AtomicBoolean(false);

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < threads.length; i++) {
            String name = "Sender:" + i;
            threads[i] = new Thread(new MTSender(eventIdCounter, deleteTxnIdQ, masterCache,
                    cardSwipeCache, name, stopFlag));
            threads[i].setDaemon(true);
        }

        for (Thread thread1 : threads) {
            thread1.start();
        }

        Thread cleanerThread = new Thread() {
            @Override
            public void run() {
                int counter = 0;

                while (stopFlag.get() == false) {
                    Long oldTxnId = deleteTxnIdQ.poll();
                    if (oldTxnId != null) {
                        masterCache.remove(oldTxnId);

                        counter++;

                        if ((counter & 255) == 0) {
                            Thread.yield();
                        }

                        continue;
                    }

                    try {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e) {
                    }
                }
            }
        };
        cleanerThread.setPriority(Thread.MIN_PRIORITY);
        cleanerThread.setDaemon(true);
        cleanerThread.start();

        Scanner scanner = new Scanner(System.in);
        for (; ;) {
            System.err.println("Type 'stop' to stop sending.");
            String s = scanner.nextLine();
            if (s != null && s.equalsIgnoreCase("stop")) {
                System.err.println("Stopping...");

                break;
            }
        }

        stopFlag.set(true);

        for (Thread thread : threads) {
            try {
                thread.join(5000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            cleanerThread.join(5000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    public static void main(String[] args) {
        try {
            String clusterName = args[0];
            String agentName = "queryperf.agent.loadproc.Queries";
            MTSendLauncher launcher = new MTSendLauncher(clusterName, agentName);

            int numThreads = Integer.parseInt(args[1]);
            launcher.keepSending(numThreads);
        }
        catch (Exception e) {
            System.err.println("Exception! Exiting..");
            e.printStackTrace(System.err);
        }
    }

    public static class TypeManagerImpl extends BEClassLoader {
        public TypeManagerImpl(ClassLoader parent) {
            super(parent, 9090, null);
        }

        public Entity createEntity(ExpandedName en) throws Exception {
            return null;
        }

        public Entity createEntity(String uri) throws Exception {
            return null;
        }

        public PayloadFactory getPayloadFactory() {
            return null;
        }

        public TypeDescriptor getTypeDescriptor(Class cls) {
            return null;
        }

        public TypeDescriptor getTypeDescriptor(ExpandedName en) {
            return null;
        }

        public TypeDescriptor getTypeDescriptor(String uri) {
            return null;
        }

        public Collection getTypeDescriptors(int type) {
            return null;
        }
    }


    public static class HackedCacheCluster extends CacheCluster {
        protected HackedCacheCluster(CacheClusterConfiguration clusterConfig,
                                     RuleServiceProvider RSP)
                throws Exception {
            super(clusterConfig, RSP);
        }

        @Override
        public String getClusterName() {
            return "darknet";
        }

        @Override
        protected void loadExternalClasses() throws Exception {

        }

        @Override
        protected void loadEntity(Class entityClz) {

        }

        @Override
        public MetadataCache getMetadataCache() {
            try {
                return new HackedMetadataCache(this);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public int getQuorum() {
            return 1;
        }

        @Override
        public String getCacheType() {
            return "dist-unlimited-nobs";
        }

        protected void initCoherence() throws Exception {

        }

        @Override
        public boolean isBackingStoreEnabled() {
            return false;
        }
    }

    protected static class HackedMetadataCache extends MetadataCache {
        /**
         * @param cluster
         * @throws Exception
         */
        protected HackedMetadataCache(CacheCluster cluster) throws Exception {
            super(cluster);
        }

        @Override
        public Integer getTypeId(Class entityClz) throws Exception {
            return 1;
        }

        @Override
        protected int getTypeId(Class entityClz, Map typeMap) {
            return 1;
        }
    }

    public static class HackedRSP extends RuleServiceProviderImpl {
        protected TypeManagerImpl typeManager;

        public HackedRSP(TypeManagerImpl typeManager)
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
                    return null;
                }

                public String getName() {
                    return null;
                }

                @Override
                public Properties getProperties() {
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

        public TypeManager getTypeManager() {
            return typeManager;
        }
    }
}
