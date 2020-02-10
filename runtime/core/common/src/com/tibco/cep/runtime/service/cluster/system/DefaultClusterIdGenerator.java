/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 27/8/2010
 */

package com.tibco.cep.runtime.service.cluster.system;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 16, 2010
 * Time: 2:52:41 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This Id Generator issues out Blocked Ids to an entity or for it type;
 */
public class DefaultClusterIdGenerator implements ClusterIdGenerator {

	final static long BLOCK_SIZE = Long.parseLong(System.getProperty(SystemProperty.CLUSTER_BLOCK_ID_SIZE.getPropertyName(), "10000"));
    private long lastIssueNo = 0;
    private long thisBlockLastNo = -1;
    int siteId;
    Cluster cluster;
    ControlDao<Integer, Long> idCache;
    private final Lock idLock = new ReentrantLock();
    private boolean bUseObjectTable;

    public DefaultClusterIdGenerator() {

    }

    public void init(Cluster cluster) {
        this.cluster = cluster;

        ((RuleServiceProviderImpl) cluster.getRuleServiceProvider()).setIdGenerator(this);
        this.siteId = cluster.getClusterConfig().getSiteId();
        idCache = cluster.getDaoProvider().createControlDao(Integer.class, Long.class, ControlDaoType.MasterId);
        bUseObjectTable = cluster.getClusterConfig().useObjectTable();
    }

    public void start() {
        idCache.start();
    }

    public int getLocalSiteId() {
        return siteId;
    }

    public long lastEntityId() {
        return lastIssueNo;
    }

    public long nextEntityId(Class clz) {
        idLock.lock();
        try {
            long nextId = nextId();

            // Most cases, simply return the next Id from global pool
            if (bUseObjectTable) {
                //siteId encoded
                nextId = EntityIdMask.mask(siteId, nextId);
                return nextId;
            }

            int typeId = 0;
            if (clz != null) {
            	typeId = cluster.getMetadataCache().getTypeId(clz);
            }
            nextId = IDEncoder.encodeTypeId(nextId, typeId);
            
            //siteId encoded
            nextId = EntityIdMask.mask(siteId, nextId);
            return nextId;
        }
        finally {
            idLock.unlock();
        }
    }

    public void setMaxEntityId(long max) {

    }

    public void setMinEntityId(long min) {
        idLock.lock();
        try {
            idCache.lockAll(-1);
            Long tmp = idCache.get(CacheConstants.ENTITYID);
            min = (tmp != null && tmp > min) ? tmp : min;
            idCache.put(CacheConstants.ENTITYID, min);

        } catch (Exception ex) {
            System.err.println("Exception in set min entity id : " + ex);
            // SURESH TODO : Log the exception it could be very important.
        } finally {
            idCache.unlockAll();
            idLock.unlock();
        }
    }

    private long nextId() {
        long ret;
        if (lastIssueNo >= thisBlockLastNo) {
            getNextBlock();
        }
        ret = ++lastIssueNo;
        return ret;
    }

    private void getNextBlock() {
        try {
            idCache.lockAll(-1);
            Long lastBlockIssued = idCache.get(CacheConstants.ENTITYID);
            thisBlockLastNo = (lastBlockIssued == null) ? BLOCK_SIZE : lastBlockIssued + BLOCK_SIZE;
            lastIssueNo = (lastBlockIssued == null) ? 0 : lastBlockIssued;
            idCache.put(CacheConstants.ENTITYID, thisBlockLastNo);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            idCache.unlockAll();
        }
    }

    public long nextEntityId() {
        idLock.lock();
        try {
            long nextId = nextId();
            // Most cases, simply return the next Id from global pool
            if (bUseObjectTable) {
                //siteId encoded
                nextId = EntityIdMask.mask(siteId, nextId);
                return nextId;
            }
            nextId = IDEncoder.encodeTypeId(nextId, 0);
            
            //siteId encoded
            nextId = EntityIdMask.mask(siteId, nextId);
            return nextId;
        }
        finally {
            idLock.unlock();
        }
    }
}
