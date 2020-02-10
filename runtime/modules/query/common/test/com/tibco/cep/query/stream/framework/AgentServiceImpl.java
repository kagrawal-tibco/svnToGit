/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.query.stream.framework;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.MapEvent;
import com.tangosol.util.MapListener;
import com.tibco.be.util.BEProperties;
import com.tibco.be.util.config.ClusterConfig;
import com.tibco.be.util.config.ProcessingUnitConfig;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.query.stream.impl.rete.service.AgentService;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.repo.ResourceProviderFactory;
import com.tibco.cep.repo.provider.BEArchiveResourceProvider;
import com.tibco.cep.repo.provider.SMapContentProvider;
import com.tibco.cep.repo.provider.SharedArchiveResourceProvider;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.PayloadFactory;
import com.tibco.cep.runtime.service.cluster.events.notification.ClusterEntityListener;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.objectrepo.vfile.VFileFactory;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.tns.cache.TnsCache;

import java.util.*;

/*
* Author: Ashwin Jayaprakash Date: Apr 28, 2008 Time: 7:21:32 PM
*/

public class AgentServiceImpl implements AgentService, MapListener {
    protected TypeManagerImpl typeManager;

    protected HashMap<Integer, Class> typeToClassMap;

    private ClusterEntityListener listener;
    private CacheCluster cluster;

    public AgentServiceImpl() throws Exception {
        this.typeManager = new TypeManagerImpl(getClass().getClassLoader());
        this.typeToClassMap = new HashMap<Integer, Class>();

        CacheFactory.getCluster().setContextClassLoader(this.typeManager);
    }

    public void listenerReady(ClusterEntityListener listener) {
        this.listener = listener;

        getMasterCache().addMapListener(this, null, false);
    }

    public NamedCache getEntityCache(Class entityClass) {
        String s = getEntityCacheName(entityClass);

        NamedCache namedCache = CacheFactory.getCache(s, typeManager);
        namedCache.getCacheService().setContextClassLoader(typeManager);

        return namedCache;
    }

    public String getEntityCacheName(Class entityClass) {
        return "$unit-test." + entityClass.getName();
    }

    public TypeManagerImpl getEntityClassLoader() {
        return typeManager;
    }

    public void setCacheCluster(CacheCluster cluster) {
        this.cluster = cluster;
    }

    /**
     * @return <code>null</code>.
     */
    public MetadataCache getMetadataCache() {
        if (cluster != null) {
            return cluster.getMetadataCache();
        }
        return null;
    }

    /**
     * @return <code>null</code>.
     */
    public ObjectTable getObjectTableCache() {
        if (cluster != null) {
            return cluster.getObjectTableCache();
        }
        return null;
    }

    public NamedCache getMasterCache() {
        NamedCache namedCache = CacheFactory.getCache("$unit-test.master-cache", typeManager);
        namedCache.getCacheService().setContextClassLoader(typeManager);

        return namedCache;
    }

    public String getName() {
        return "$unit-test.agent-1";
    }

    public HashMap<Integer, Class> getTypeToClassMap() {
        return typeToClassMap;
    }

    public void discard() {
        if (listener != null) {
            getMasterCache().removeMapListener(this);
        }
        listener = null;

        typeManager = null;

        typeToClassMap.clear();
        typeToClassMap = null;
    }

    public int getTypeId(Class entityClz) throws Exception {
        for (Map.Entry<Integer, Class> entry : typeToClassMap.entrySet()) {
            if (entry.getValue().equals(entityClz)) {
                return entry.getKey();
            }
        }

        return -1;
    }

    public Class getClass(int typeId) throws Exception {
        return typeToClassMap.get(typeId);
    }

    //-----------

    public void entryInserted(MapEvent event) {
        Object obj = event.getNewValue();

        listener.onEntity(obj);
    }

    public void entryUpdated(MapEvent event) {
    }

    public void entryDeleted(MapEvent event) {
    }

    //-----------

    public static class HackedRSP extends RuleServiceProviderImpl {
        protected TypeManagerImpl typeManager;

        public HackedRSP() throws Exception {
            super("$$dummy-RSP$$", new BEProperties());
        }

        @Override
        protected void initialize(Properties env) throws Exception {
        }

//        @Override
//        public Logger getLogger() {
//            return new Logger() {
//                public void user(boolean on) {
//                }
//
//                public void debug(boolean on) {
//                }
//
//                public void info(boolean on) {
//                }
//
//                public void warn(boolean on) {
//                }
//
//                public void error(boolean on) {
//                }
//
//                public void fatal(boolean on) {
//                }
//
//                public boolean isUser() {
//                    return false;
//                }
//
//                public boolean isDebug() {
//                    return false;
//                }
//
//                public boolean isInfo() {
//                    return false;
//                }
//
//                public boolean isWarn() {
//                    return false;
//                }
//
//                public boolean isError() {
//                    return false;
//                }
//
//                public boolean isFatal() {
//                    return false;
//                }
//
//                public void logUser(String msg) {
//                }
//
//                public void logUser(String msg, Throwable throwable) {
//                }
//
//                public void logUser(String component, String msg, Throwable throwable) {
//                }
//
//                public void logDebug(String msg) {
//                }
//
//                public void logDebug(String msg, Throwable throwable) {
//                }
//
//                public void logDebug(String component, String msg, Throwable throwable) {
//                }
//
//                public void logInfo(String msg) {
//                }
//
//                public void logInfo(String msg, Throwable throwable) {
//                }
//
//                public void logInfo(String component, String msg, Throwable throwable) {
//                }
//
//                public void logWarn(String msg) {
//                }
//
//                public void logWarn(String msg, Throwable throwable) {
//                }
//
//                public void logWarn(String component, String msg, Throwable throwable) {
//                }
//
//                public void logError(String msg) {
//                }
//
//                public void logError(String msg, Throwable throwable) {
//                }
//
//                public void logError(String component, String msg, Throwable throwable) {
//                }
//
//                public void logFatal(String msg) {
//                }
//
//                public void logFatal(String msg, Throwable throwable) {
//                }
//
//                public void logFatal(String component, String msg, Throwable throwable) {
//                }
//
//                public void close() {
//                }
//            };
//        }

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

                @Override
                public Properties getProperties() {
                    return null;
                }
            };
        }

        public void setTypeManager(TypeManagerImpl typeManager) {
            this.typeManager = typeManager;
        }

        public TypeManager getTypeManager() {
            return typeManager;
        }
    }

    public static class TypeManagerImpl extends BEClassLoader {
        public TypeManagerImpl(ClassLoader parent) throws Exception {
            super(parent, 9090, new HackedRSP());
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
}
