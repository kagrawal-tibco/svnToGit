/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.query.stream.impl.rete.integ;


import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.core.ControllableResource;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.impl.rete.service.AgentService;
import com.tibco.cep.query.stream.impl.rete.service.CacheScout;
import com.tibco.cep.query.stream.impl.rete.service.ReteEntityChangeListener;
import com.tibco.cep.query.stream.impl.rete.service.ReteEntityDispatcher;
import com.tibco.cep.query.stream.impl.rete.service.SnapshotQueryManager;
import com.tibco.cep.query.stream.monitor.CustomException;
import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.runtime.service.cluster.events.notification.ClusterEntityListener;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransaction;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.om.api.Filter;

/*
* Author: Ashwin Jayaprakash Date: Apr 28, 2008 Time: 1:55:57 PM
*/

public class CacheListener
        implements ClusterEntityListener, ControllableResource, QueryDataProvider {
    protected ReteEntityChangeListener changeListener;

    protected MetadataCache metadataCache;

    protected AgentService agentService;

    protected ResourceId resourceId;

    public CacheListener() {
    }

    public void setParentId(ResourceId parentId) {
        this.resourceId = new ResourceId(parentId, CacheListener.class.getName());
    }

    public void setProperties(Properties properties) {
    }

    public void setBEClassLoader(BEClassLoader classLoader) {
    }

    public void setLogger(Logger logger) {
    }

    public void setWorkingMemory(WorkingMemory workingMemory) {
    }

    public void setAgentService(AgentService agentService) {
        this.agentService = agentService;
    }

    public void setPrimaryCache(Cache cache) {
    }

    public void setDeadpoolCache(Cache cache) {
    }

    public void setSharedObjectSourceRepository(SharedObjectSourceRepository sourceRepository) {
    }

    public void setReteEntityDispatcher(ReteEntityDispatcher entityDispatcher) {
        this.changeListener = entityDispatcher;
    }

    public void setSnapshotQueryManager(SnapshotQueryManager queryManager) {
    }

    public void setCacheScout(CacheScout cacheScout) {
    }

    //--------------

    public boolean requireAsyncInvocation() {
        //We have our own queue.
        return false;
    }

    public String getListenerName() {
        return agentService.getName();
    }

    public void start() throws Exception {
        agentService.listenerReady(this);
    }

    public void stop() throws Exception {
        agentService = null;

        changeListener = null;

        metadataCache = null;

        resourceId.discard();
        resourceId = null;
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    //--------------

    /**
     * @return <code>null</code> for now.
     */
    public Filter getEntityFilter() {
        return null;
    }

    public void onEntity(Object obj) {
        try {
            if (metadataCache == null) {
                metadataCache = agentService.getMetadataCache();
            }

            final MetadataCache cachedMetadataCache = metadataCache;
            final ReteEntityChangeListener cachedChangeListener = changeListener;
            final AgentService cachedAgentService = agentService;

            Iterator<? extends RtcTransaction.ReadFromCache> ops = deserializeTxn((byte[]) obj);
            while (ops.hasNext()) {
                RtcTransaction.ReadFromCache entry = ops.next();

                Class entityClass = null;
                if (Flags.DEV_MODE) {
                    entityClass = cachedAgentService.getClass(entry.getTypeId());
                } else {
                    entityClass = cachedMetadataCache.getClass(entry.getTypeId());
                }

                switch (entry.getType()) {
                    case RtcTransaction.OP_NEW_CONCEPT:
                    case RtcTransaction.OP_NEW_EVENT:
                        int v = entry.getVersion();
                        /*
                        Workaround for bug in Inference Agent where it sends mods as new
                        because of keepHandles=false.
                        */
                        if (v <= 1) {
                            cachedChangeListener.onNew(entityClass, entry.getId(), v);
                        } else {
                            cachedChangeListener.onModify(entityClass, entry.getId(), v);
                        }
                        break;

                    case RtcTransaction.OP_MOD_CONCEPT:
                        cachedChangeListener
                                .onModify(entityClass, entry.getId(), entry.getVersion());
                        break;

                    case RtcTransaction.OP_DEL_CONCEPT:
                    case RtcTransaction.OP_DEL_EVENT:
                        cachedChangeListener
                                .onDelete(entityClass, entry.getId(), entry.getVersion());
                        break;

                    default:
                }
            }
        }
        catch (Throwable t) {
            Logger logger = Registry.getInstance().getComponent(Logger.class);
            logger.log(Logger.LogLevel.ERROR, new CustomException(resourceId, t));
        }
    }

    //todo Optimize this or move it?

    private Iterator<? extends RtcTransaction.ReadFromCache> deserializeTxn(byte[] b) {
        ByteArrayInputStream bufStream = new ByteArrayInputStream(b);

        DataInput buf = new DataInputStream(bufStream);

        //todo Why not reuse this?
        RtcTransaction txn = new RtcTransaction(null);

        return (Iterator<? extends RtcTransaction.ReadFromCache>) txn.readFromCacheOps(buf);
    }

	@Override
	public void entitiesAdded() {}
	
    @Override
    public void entitiesChanged(Collection<Class<Entity>> changedClasses) {}
}
