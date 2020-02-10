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

package com.tibco.cep.query.stream.framework;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.service.cluster.om.RtcChangeStatus;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransaction;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransactionProperties;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/*
* Author: Karthikeyan Subramanian / Date: Feb 3, 2010 / Time: 12:29:32 PM
*/
public class RtcTransactionHelper {
    private CacheCluster cacheCluster;
    private RtcTransactionProperties rtcTransactionProperties;
    private final AtomicInteger txnId;
    protected CustomMaster master;

    public RtcTransactionHelper(CustomMaster master, CacheCluster cacheCluster, RtcTransactionProperties properties, AtomicInteger txnId) {
        this.cacheCluster = cacheCluster;
        this.rtcTransactionProperties = properties;
        this.master = master;
        this.txnId = txnId;
    }

    public byte[] serializeTxn(RtcTransaction txn) {
        ByteArrayOutputStream bufStream = new ByteArrayOutputStream(32 * 4);
        DataOutput buf = new DataOutputStream(bufStream);
        Iterator writeIterator = txn.writeToCahceOps(buf);
        if (writeIterator != null) {
            while (writeIterator.hasNext()) {
                writeIterator.next();
            }
        }

        return bufStream.toByteArray();
    }

    ////////////////////////////////////
    // RTC Transaction related methods.
    ////////////////////////////////////
    public RtcTransaction addEvent(int i, int agentId, int typeId, Object event) throws Exception {
        RtcTransaction txn = new RtcTransaction(i, cacheCluster, rtcTransactionProperties);
        addEvent(txn, agentId, typeId, event);
        return txn;
    }

    public void addEvent(RtcTransaction txn, int agentId, int typeId, Object event) throws Exception {
        txn.recordNewEvent((Event) event, typeId, agentId);
        addOperation(txn, RtcTransaction.OP_NEW_EVENT, (Entity) event, typeId);
    }

    public void addConcept(RtcTransaction txn, Concept concept, int typeId) throws Exception {
        txn.recordNewConcept(concept, typeId, null);
        addOperation(txn, RtcTransaction.OP_NEW_CONCEPT, concept, typeId);
    }


    public void addConcept(RtcTransaction txn, Concept concept, int typeId, RtcChangeStatus changeStatus) throws Exception {
        txn.recordNewConcept(concept, typeId, changeStatus);
        addOperation(txn, RtcTransaction.OP_NEW_CONCEPT, concept, typeId);
    }

    public void modifyConcept(RtcTransaction txn, Concept concept, int typeId) throws Exception {
        txn.recordModifyConcept(concept, typeId, new int[0], true);
        addOperation(txn, RtcTransaction.OP_MOD_CONCEPT, concept, typeId);
    }

    public void deleteEvent(RtcTransaction txn, Object event, int typeId) throws Exception {
        txn.recordDeleteEvent((Event) event, typeId);
        // Hack to set the operation value.
        addOperation(txn, RtcTransaction.OP_DEL_EVENT, (Entity) event, typeId);
    }

    public void deleteConcept(RtcTransaction txn, Concept entity, int typeId) throws Exception {
        txn.recordDeleteConcept(entity, typeId);
        addOperation(txn, RtcTransaction.OP_DEL_CONCEPT, entity, typeId);
    }

    public RtcTransaction createNewRtcTransaction() {
        return new RtcTransaction(txnId.incrementAndGet(), cacheCluster, rtcTransactionProperties);
    }

    private void addOperation(RtcTransaction txn, byte operation, Entity entity, int typeId) throws Exception {
        // Hack to set the operation value.
        Field operationsField = txn.getClass().getDeclaredField("operations");
        operationsField.setAccessible(true);
        Collection<RtcTransaction.WriteToCache> collection = null;
        if (operationsField.get(txn) != null) {
            collection = (Collection<RtcTransaction.WriteToCache>) operationsField.get(txn);
        } else {
            collection = new LinkedList<RtcTransaction.WriteToCache>();
        }
        // Hack to initialize the class with package private constructor.
        Constructor construct = RtcTransaction.WriteToCache.class.getDeclaredConstructor(new Class[]{
                byte.class, Entity.class, int.class});
        construct.setAccessible(true);
        RtcTransaction.WriteToCache w2Cache = (RtcTransaction.WriteToCache) construct.newInstance(
                operation, entity, typeId);
        collection.add(w2Cache);
        operationsField.set(txn, collection);
    }
}
