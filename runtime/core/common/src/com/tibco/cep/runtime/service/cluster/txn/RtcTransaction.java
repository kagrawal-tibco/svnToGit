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

package com.tibco.cep.runtime.service.cluster.txn;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.entity.NamedInstance;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.element.VersionedObject;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.model.process.ObjectBean;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.events.EventTuple;
import com.tibco.cep.runtime.service.cluster.events.notification.CacheChangeEvent;
import com.tibco.cep.runtime.service.cluster.events.notification.CacheChangeType;
import com.tibco.cep.runtime.service.cluster.om.RtcChangeStatus;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable.Tuple;
import com.tibco.cep.runtime.service.cluster.system.impl.ObjectTupleImpl;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.util.BitMapHelper;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Mar 24, 2008
 * Time: 8:38:55 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Created by IntelliJ IDEA. User: nleong Date: Mar 17, 2008 Time: 4:59:55 PM To change this
 * template use File | Settings | File Templates.
 */
public class RtcTransaction {

    public final static byte OP_INIT = 0;

    public final static byte OP_DONE = 1;

    public final static byte OP_ACK_ONLY = 2;

    public final static byte OP_NEW_CONCEPT = 3;

    public final static byte OP_MOD_CONCEPT = 4;

    public final static byte OP_NEW_EVENT = 5;

    public final static byte OP_DEL_CONCEPT = 6;

    public final static byte OP_DEL_EVENT = 7;

    public final static byte OP_DEL_ONLY = 8;

    public final static byte OP_NEW = 9;

    public final static byte OP_MOD = 10;

    public final static byte OP_DEL = 11;

    Collection<WriteToCache> operations;

    Map<Integer, Map<Long, Concept>> concepts_insert = null;
    Map<Integer, Map<Long, Concept>> concepts_update = null;
    // Replace this w/ statuses in concepts_update
    Map<Integer, Map<Long, ConceptStatus>> concepts_update_status = null;
    Map<Integer, Set<Long>> concepts_delete = null;
    Map<Integer, Map<Long, Event>> events_insert = null;
    Map<Integer, Set<Long>> events_delete = null;
    java.util.List<SimpleEvent> events_acks = null;
    Map<Class, Map<Long, EventTuple>> eventTableChangedMap = null;
    Map<Class, Set<Long>> eventTableDeletedMap = null;
    Map<Integer,Map<String,ObjectBean>> object_tuple_insert = null;
    Map<Integer,Map<String,ObjectBean>> object_tuple_update = null;
    Map<Integer,Map<String,ObjectBean>> object_tuple_delete = null;

    private Map<Long, Tuple> objectTable_save = null;
    private Map<String, Tuple> objectExtIdTable_save = null;
    private Set<Long> objectTable_delete = null;
    private Set<String> objectExtIdTable_delete = null;
    private Map<Long, Tuple> objectTable_dbsave = null;
    private Set<Long> objectTable_dbdelete = null;
    private long txnId;
    private Cluster cluster;
    private boolean isCacheAside = false;
    private boolean isWriteBehind = false;
    private ArrayList triggers = null;

    HashSet<Integer> txnIds = new HashSet<Integer>();
    RtcTransactionProperties rtcTransactionProperties;
    boolean hasOps = false;

    final static BitMapHelper bitMapHelper = new BitMapHelper(MetadataCache.BE_TYPE_START);

    public RtcTransaction(long txnId, Cluster cluster, RtcTransactionProperties rtcTransactionProperties, Object trigger) {
        this.txnId = txnId;
        this.cluster = cluster;
        this.rtcTransactionProperties = rtcTransactionProperties;
        if (trigger != null) {
        	triggers = new ArrayList(1);
        	triggers.add(trigger);
        }
        initialize();
    }

    public RtcTransaction(Cluster cluster) {
        this.cluster = cluster;
    }

    public RtcTransactionProperties getRtcTransactionProperties() {
        return rtcTransactionProperties;
    }

    protected void initialize() {
        concepts_insert = new TreeMap<Integer, Map<Long, Concept>>();
        concepts_update = new TreeMap<Integer, Map<Long, Concept>>();
        concepts_update_status = new TreeMap<Integer, Map<Long, ConceptStatus>>();
        concepts_delete = new TreeMap<Integer, Set<Long>>();
        events_insert = new TreeMap<Integer, Map<Long, Event>>();
        events_delete = new TreeMap<Integer, Set<Long>>();
        events_acks = new ArrayList<SimpleEvent>();
        eventTableChangedMap = new HashMap();
        eventTableDeletedMap = new HashMap();
        object_tuple_insert = new TreeMap<Integer,Map<String,ObjectBean>>();
        object_tuple_update = new TreeMap<Integer,Map<String,ObjectBean>>();
        object_tuple_delete = new TreeMap<Integer,Map<String,ObjectBean>>();

        objectTable_save = new HashMap<Long, Tuple>();
        objectExtIdTable_save = new HashMap<String, Tuple>();
        objectTable_delete = new HashSet<Long>();
        objectExtIdTable_delete = new HashSet<String>();

        isCacheAside = cluster.getClusterConfig().isHasBackingStore() && (cluster.getClusterConfig().isCacheAside() == true);
        isWriteBehind = cluster.getClusterConfig().isHasBackingStore() && (cluster.getClusterConfig().isCacheAside() == false);

        if (isCacheAside) {
            objectTable_dbsave = new HashMap<Long, Tuple>();
            objectTable_dbdelete = new HashSet<Long>();
        }
    }

    public boolean hasOperations() {
        return hasOps;
    }

    public Collection<WriteToCache> getOperations() {
        return operations;
    }

    public HashSet<Integer> getTxnIds() {
        return txnIds;
    }

    public long getTxnId() {
        return this.txnId;
    }

    public RtcKey getKey(int[] topic) {
        return new RtcKey(txnId, topic);
    }

    public void recordModifyConcept(Concept ref, int typeId, int [] mutableBitArray, byte rtcStatus, boolean publishSubscription) throws IOException {
        hasOps = true;
        ((VersionedObject) ref).incrementVersion();
        if (!cluster.getClusterConfig().use2xMode()) {
            if (!(ref instanceof NamedInstance) && !(ref instanceof StateMachineConcept)) {
                if (publishSubscription && cluster.getTopicRegistry().isRegisteredForChange(typeId)) {
                    if (operations == null) {
                        operations = new LinkedList<WriteToCache>();
                    }
                    operations.add(new WriteToCache(OP_MOD_CONCEPT, ref, typeId, mutableBitArray));
                    Integer typeIdInteger = typeId;
                    if (!txnIds.contains(typeIdInteger)) {
                        txnIds.add(typeIdInteger);
                    }
                }
            }
        }
        Map<Long, Concept> c = concepts_update.get(typeId);
        Map<Long, ConceptStatus> stats = concepts_update_status.get(typeId);
        if (c == null) {
            c = new TreeMap<Long, Concept>();
            concepts_update.put(typeId, c);
        }
        if (stats == null) {
            stats = new TreeMap<Long, ConceptStatus>();
            concepts_update_status.put(typeId, stats);
        }
        c.put(ref.getId(), ref);
        stats.put(ref.getId(), new ConceptStatus(mutableBitArray, rtcStatus));
    }

    public void recordNewConcept(Concept ref, int typeId, RtcChangeStatus rtcChangeStatus) throws IOException {
        hasOps = true;
        ((VersionedObject) ref).incrementVersion();
        if (!cluster.getClusterConfig().use2xMode() && !(ref instanceof StateMachineConcept)) {
            if (!(ref instanceof NamedInstance)) {
                if (cluster.getTopicRegistry().isRegisteredForChange(typeId)) {
                    if (operations == null) {
                        operations = new LinkedList<WriteToCache>();
                    }
                    operations.add(new WriteToCache(OP_NEW_CONCEPT, ref, typeId, rtcChangeStatus));
                    Integer typeIdInteger = typeId;
                    if (!txnIds.contains(typeIdInteger)) {
                        txnIds.add(typeIdInteger);
                    }
                }
            }
        }
        Map<Long, Concept> c = concepts_insert.get(typeId);
        if (c == null) {
            c = new TreeMap<Long, Concept>();
            concepts_insert.put(typeId, c);
        }
        c.put(ref.getId(), ref);

        Tuple b = new ObjectTupleImpl(typeId, ref.getId(), ref.getExtId(), false);
        getObjectTable_save().put(ref.getId(), b);

        if (isCacheAside && cluster.getMetadataCache().getEntityDao(typeId).getConfig().hasBackingStore()) {
            objectTable_dbsave.put(ref.getId(), b);
        }

        if ((ref.getExtId() != null) && (ref.getExtId().length() > 0)) {
            getObjectExtIdTable_save().put(ref.getExtId(), b);
        }
    }

    public void recordNewEvent(Event ref, int typeId, int agentId) throws IOException {
        hasOps = true;
        if (!cluster.getClusterConfig().use2xMode()) {
            if (cluster.getTopicRegistry().isRegisteredForChange(typeId)) {
                if (ref instanceof SimpleEvent || (ref instanceof TimeEvent && !((TimeEvent) ref).isRepeating())) {
                    // CR - 1-9VKV73
                    // if (((SimpleEvent) ref).getContext() == null) {
                    if (operations == null) {
                        operations = new LinkedList<WriteToCache>();
                    }
                    operations.add(new WriteToCache(OP_NEW_EVENT, ref, typeId));
                    Integer typeIdInteger = typeId;
                    if (!txnIds.contains(typeIdInteger)) {
                        txnIds.add(typeIdInteger);
                    }
//                  CR - 1-9VKV73
//                  }
//              } else if (ref instanceof TimeEvent) {
//                  if (operations == null) {
//                      operations = new LinkedList<WriteToCache>();
//                  }
//                  operations.add(new WriteToCache(OP_NEW_EVENT, ref, typeId));
//                  Integer typeIdInteger = typeId;
//                  if (!txnIds.contains(typeIdInteger)) {
//                      txnIds.add(typeIdInteger);
//                  }
//              }

                    Map<Long, EventTuple> eqMap = eventTableChangedMap.get(ref.getClass());
                    if (eqMap == null) {
                        eqMap = new HashMap();
                        eventTableChangedMap.put(ref.getClass(), eqMap);
                    }
                    eqMap.put(ref.getId(), new EventTuple(ref.getId(), agentId));
                }
            }
        }

        Map<Long, Event> c = events_insert.get(typeId);
        if (c == null) {
            c = new TreeMap<Long, Event>();
            events_insert.put(typeId, c);
        }
        c.put(ref.getId(), ref);

        Tuple b = new ObjectTupleImpl(typeId, ref.getId(), ref.getExtId(), false);
        getObjectTable_save().put(ref.getId(), b);
        if (isCacheAside && cluster.getMetadataCache().getEntityDao(typeId).getConfig().hasBackingStore()) {
            objectTable_dbsave.put(ref.getId(), b);
        }

        if ((ref.getExtId() != null) && (ref.getExtId().length() > 0)) {
            getObjectExtIdTable_save().put(ref.getExtId(), b);
        }

        if (ref instanceof SimpleEvent) {
            SimpleEventImpl se = (SimpleEventImpl) ref;
            if (se.getContext() != null) {
                events_acks.add(se);
            }
        }
    }

    public void recordDeleteConcept(Concept ref, int typeId) throws IOException {
        hasOps = true;
        if (!cluster.getClusterConfig().use2xMode()) {
            if (cluster.getTopicRegistry().isRegisteredForDelete(typeId)) {
                if (operations == null) {
                    operations = new LinkedList<WriteToCache>();
                }
                operations.add(new WriteToCache(OP_DEL_CONCEPT, ref, typeId));
                Integer typeIdInteger = typeId;
                if (!txnIds.contains(typeIdInteger)) {
                    txnIds.add(typeIdInteger);
                }
            }
        }
        Set<Long> c = concepts_delete.get(typeId);
        if (c == null) {
            c = new TreeSet();
            concepts_delete.put(typeId, c);
        }
        c.add(ref.getId());

        EntityDao provider = cluster.getMetadataCache().getEntityDao(typeId);
        boolean hasExtId = (ref.getExtId() != null) && (ref.getExtId().length() > 0);

        if (isWriteBehind && provider.getConfig().hasBackingStore()) {
            if (cluster.getClusterConfig().isKeepDeleted()) {
                Tuple b = new ObjectTupleImpl(typeId, ref.getId(), ref.getExtId(), true);
                if(hasExtId) getObjectExtIdTable_save().put(ref.getExtId(), b);
                getObjectTable_save().put(ref.getId(), b);
            } else {
                if(hasExtId) getObjectExtIdTable_delete().add(ref.getExtId());
                getObjectTable_delete().add(ref.getId());
            }
        } else {
            if(hasExtId) getObjectExtIdTable_delete().add(ref.getExtId());
            getObjectTable_delete().add(ref.getId());

            if (isCacheAside && provider.getConfig().hasBackingStore(true)) {
                objectTable_dbdelete.add(ref.getId());
            }
        }
    }

    public void recordDeleteEvent(Event ref, int typeId) throws IOException {
        hasOps = true;
        if (!cluster.getClusterConfig().use2xMode()) {
            if (cluster.getTopicRegistry().isRegisteredForDelete(typeId)) {
                if (operations == null) {
                    operations = new LinkedList<WriteToCache>();
                }
                operations.add(new WriteToCache(OP_DEL_EVENT, ref, typeId));
                Integer typeIdInteger = typeId;
                if (!txnIds.contains(typeIdInteger)) {
                    txnIds.add(typeIdInteger);
                }
            }

            if (typeId != StateMachineConceptImpl.StateTimeoutEvent.STATETIMEOUTEVENT_TYPEID) {
                Set<Long> eqSet = eventTableDeletedMap.get(ref.getClass());
                if (eqSet == null) {
                    eqSet = new HashSet<Long>();
                    eventTableDeletedMap.put(ref.getClass(), eqSet);
                }
                eqSet.add(ref.getId());
            }
        }

        Set<Long> c = events_delete.get(typeId);
        if (c == null) {
            c = new TreeSet<Long>();
            events_delete.put(typeId, c);
        }
        c.add(ref.getId());

        EntityDao provider = cluster.getMetadataCache().getEntityDao(typeId);
        boolean hasExtId = (ref.getExtId() != null) && (ref.getExtId().length() > 0); 

        if (isWriteBehind && provider.getConfig().hasBackingStore()) {
            if (cluster.getClusterConfig().isKeepDeleted()) {
                Tuple b = new ObjectTupleImpl(typeId, ref.getId(), ref.getExtId(), true);
                if(hasExtId) getObjectExtIdTable_save().put(ref.getExtId(), b);
                getObjectTable_save().put(ref.getId(), b);
            } else {
                if(hasExtId) getObjectExtIdTable_delete().add(ref.getExtId());
                getObjectTable_delete().add(ref.getId());
            }
        } else {
            if(hasExtId) getObjectExtIdTable_delete().add(ref.getExtId());
            getObjectTable_delete().add(ref.getId());

            if (isCacheAside && provider.getConfig().hasBackingStore(true)) {
                objectTable_dbdelete.add(ref.getId());
            }
        }

        if (ref instanceof SimpleEvent) {
            SimpleEventImpl se = (SimpleEventImpl) ref;
            if (se.getContext() != null) {
                events_acks.add(se);
            }
        }
    }

    public void recordAckOnlyEvent(SimpleEvent event, int typeId) throws IOException {
        //hasOps = true;
        events_acks.add(event);
    }

    public Map<Integer, Map<Long, Concept>> getAddedConcepts() {
    	return this.concepts_insert;
    }
    
    public Map<Integer, Map<Long, Concept>> getModifiedConcepts() {
    	return this.concepts_update;
    }
    
    public Map<Integer, Map<Long, ConceptStatus>> getModifiedConceptsDirtyBits() {
    	return this.concepts_update_status;
    }
    
    public Map<Integer, Set<Long>> getDeletedConcepts() {
    	return this.concepts_delete;
    }

    public Map<Integer, Map<String, ObjectBean>> getAddedObjectTuples() {
        return this.object_tuple_insert;
    }

    public Map<Integer, Map<String, ObjectBean>> getModifiedObjectTuples() {
        return this.object_tuple_update;
    }
    
    public Map<Integer, Map<String,ObjectBean>> getDeletedObjectTuples() {
        return this.object_tuple_delete;
    }

    public Map<Integer, Map<Long, Event>> getAddedEvents() {
        return this.events_insert;
    }

    public Map<Integer, Set<Long>> getDeletedEvents() {
        return this.events_delete;
    }

    public java.util.List<SimpleEvent> getAckEvents() {
        return this.events_acks;
    }

    public Collection<Integer> getTypeIds() {
        return txnIds;
    }

    public boolean doPublish() {
        return ((operations != null) && (operations.size() > 0));
    }

    /**
     * @param dataOutput
     * @return
     */
    public Iterator writeToCacheOps(DataOutput dataOutput) {
        if ((operations != null) && (operations.size() > 0))
            return new WriteToCacheIterator(dataOutput);
        else
            return null;
    }

    public Iterator readFromCacheOps(DataInput dataInput) {
        return new ReadFromCacheOpsIterator(dataInput);
    }

    /**
     * Invoke {@link #serializeWriteToCacheEnd(java.io.DataOutput)} after writing all {WriteToCache
     * entries}.
     *
     * @param dataOutput
     * @param entry
     * @throws IOException
     */
    public static void serializeWriteToCache(DataOutput dataOutput, WriteToCache entry)
            throws IOException {
        //Don't need to write the byte array (cache) for ack_only
        if (entry.type == OP_ACK_ONLY || entry.type == OP_DEL_ONLY) {
            return;
        }

        dataOutput.writeByte(entry.type);
        dataOutput.writeInt(entry.typeId);
        dataOutput.writeLong(entry.ref.getId());

        if (entry.ref.getExtId() != null) {
            dataOutput.writeBoolean(true);
            dataOutput.writeUTF(entry.ref.getExtId());
        } else {
            dataOutput.writeBoolean(false);
        }

        if (entry.dirtyBits != null) {
            dataOutput.writeInt(entry.dirtyBits.length);
            for (int i = 0; i < entry.dirtyBits.length; i++) {
                dataOutput.writeInt(entry.dirtyBits[i]);
            }
        } else {
            dataOutput.writeInt(0);
        }

        if (entry.ref instanceof VersionedObject) {
            dataOutput.writeInt(
                    (Integer) ((VersionedObject) entry.ref).getVersionIndicator());
        } else {
            dataOutput.writeInt(-1);
        }

        dataOutput.writeByte(entry.rtcStatus);
    }

    /**
     * @param dataOutput
     * @throws IOException
     * @see
     */
    public static void serializeWriteToCacheEnd(DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(OP_DONE);
    }

    public Map<Long, Tuple> getObjectTable_save() {
        return objectTable_save;
    }

    public Map<Class, Map<Long, EventTuple>> getEventTableChanged() {
        return eventTableChangedMap;
    }

    public Map<Class, Set<Long>> getEventTableDeleted() {
        return eventTableDeletedMap;
    }

    public Map<Long, Tuple> getDBObjectTable_save() {
        return objectTable_dbsave;
    }

    public Map<String, Tuple> getObjectExtIdTable_save() {
        return objectExtIdTable_save;
    }

    public Set<Long> getObjectTable_delete() {
        return objectTable_delete;
    }

    public Set<Long> getDBObjectTable_delete() {
        return objectTable_dbdelete;
    }

    public Set<String> getObjectExtIdTable_delete() {
        return objectExtIdTable_delete;
    }

    public StringBuffer writeReport() {
        StringBuffer buf = new StringBuffer();
        buf.append("------ Start of Report -------");
        buf.append("<Trigger>");
        for(Object o : getTriggers()) buf.append(o).append(" , ");
        buf.append("</Trigger>");

        Map map = getAddedConcepts();
        if (map != null) {
            Iterator i = map.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry entry = (Map.Entry) i.next();
            }
        }
        return null;
    }

    public static RtcTransaction combineTransactions(Collection<RtcTransaction> txns, Cluster cluster) {
        RtcTransaction combinedTxn = new RtcTransaction(cluster);
        combinedTxn.initialize();
        combinedTxn.triggers = new ArrayList(txns.size());

        for (RtcTransaction txn : txns) {
            for (Integer typeId : txn.concepts_insert.keySet()) {
                if (combinedTxn.concepts_insert.get(typeId) == null) {
                    combinedTxn.concepts_insert.put(typeId, new TreeMap<Long, Concept>());
                }
                Map map = combinedTxn.concepts_insert.get(typeId);
                map.putAll(txn.concepts_insert.get(typeId));
            }

            for (Integer typeId : txn.concepts_update.keySet()) {
                if (combinedTxn.concepts_update.get(typeId) == null) {
                    combinedTxn.concepts_update.put(typeId, new TreeMap<Long, Concept>());
                }
                Map map = combinedTxn.concepts_update.get(typeId);
                map.putAll(txn.concepts_update.get(typeId));
            }

            for (Integer typeId : txn.concepts_delete.keySet()) {
                if (combinedTxn.concepts_delete.get(typeId) == null) {
                    combinedTxn.concepts_delete.put(typeId, new TreeSet<Long>());
                }
                Set set = combinedTxn.concepts_delete.get(typeId);
                set.addAll(txn.concepts_delete.get(typeId));
            }

            for (Integer typeId : txn.events_insert.keySet()) {
                if (combinedTxn.events_insert.get(typeId) == null) {
                    combinedTxn.events_insert.put(typeId, new TreeMap<Long, Event>());
                }
                Map map = combinedTxn.events_insert.get(typeId);
                map.putAll(txn.events_insert.get(typeId));
            }

            for (Integer typeId : txn.events_delete.keySet()) {
                if (combinedTxn.events_delete.get(typeId) == null) {
                    combinedTxn.events_delete.put(typeId, new TreeSet<Long>());
                }
                Set set = combinedTxn.events_delete.get(typeId);
                set.addAll(txn.events_delete.get(typeId));
            }

            combinedTxn.objectTable_dbsave.putAll(txn.objectTable_dbsave);
            combinedTxn.objectTable_dbdelete.addAll(txn.objectTable_dbdelete);

            for(Map.Entry<Integer, Map<Long, ConceptStatus>> entry : txn.concepts_update_status.entrySet()) {
            	Integer typeId = entry.getKey();
            	Map<Long, ConceptStatus> stats = entry.getValue();
            	Map<Long, ConceptStatus> combinedStats = combinedTxn.concepts_update_status.get(typeId); 
            	if(combinedStats == null) {
            		//re-use existing map
            		combinedTxn.concepts_update_status.put(typeId, stats);
            	} else {
            		for(Map.Entry<Long, ConceptStatus> statEntry : stats.entrySet()) {
            			Long id = statEntry.getKey();
            			ConceptStatus stat = statEntry.getValue();
            			ConceptStatus combinedStat = combinedStats.get(id);
            			if(combinedStat == null) {
                    		//re-use existing status
            				combinedStats.put(id,  stat);
            			} else {
            				combinedStat.rtcStatus |= stat.rtcStatus;
                            if(stat.dirtyBitArray != null) {
                                if(combinedStat.dirtyBitArray == null) {
                                    //re-use existing
                                    combinedStat.dirtyBitArray = stat.dirtyBitArray;
                                } else {
                                    for(int ii = 0; ii < combinedStat.dirtyBitArray.length; ii++) {
                                        combinedStat.dirtyBitArray[ii] |= stat.dirtyBitArray[ii];
                                    }
                                }
                            }
            			}
            		}
            	}
            }
            combinedTxn.triggers.addAll(txn.getTriggers());
        }
        
        return combinedTxn;
    }

    public Collection<Object> getTriggers() {
    	if(triggers == null) {
    		return Collections.EMPTY_LIST;
    	}
    	return triggers;
    }

    class ReadFromCacheOpsIterator implements java.util.Iterator {
        DataInput dataInput;

        byte lastOp;

        ReadFromCacheOpsIterator(DataInput dataInput_) {
            dataInput = dataInput_;
            lastOp = OP_INIT;
        }

        public boolean hasNext() {
            try {
                if (lastOp == OP_DONE) {
                    return false;
                } else {
                    lastOp = dataInput.readByte();

                    return lastOp != OP_DONE;
                }
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        public Object next() {
            try {
                if (lastOp == OP_DONE) {
                    return null;
                }

                int typeId = dataInput.readInt();
                long id = dataInput.readLong();
                String extId = null;
                if (dataInput.readBoolean()) {
                    extId = dataInput.readUTF();
                }
                int bitlen = dataInput.readInt();
                int[] bits = null;
                if (bitlen > 0) {
                    bits = new int[bitlen];
                    for (int i = 0; i < bitlen; i++) {
                        bits[i] = dataInput.readInt();
                    }
                }
//                String str = null;
//                if(lastOp == OP_NEW) {
//                    str = dataInput.readUTF();
//                }
                int version = dataInput.readInt();
                byte rtcStatus = dataInput.readByte();
                return new ReadFromCache(lastOp, id, extId, typeId, bits, version, rtcStatus);
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        public void remove() {
            throw new RuntimeException("NOT IMPLEMENTED");
        }
    }

    class WriteToCacheIterator implements java.util.Iterator {
        Iterator ite;

        DataOutput dataOutput;

        WriteToCacheIterator(DataOutput dataOutput_) {
            ite = operations.iterator();
            dataOutput = dataOutput_;
        }

        public boolean hasNext() {
            try {
                boolean b = ite.hasNext();
                if (b) {
                    return true;
                }

                RtcTransaction.serializeWriteToCacheEnd(dataOutput);

                return false;
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        public Object next() {
            try {
                WriteToCache ret = (WriteToCache) ite.next();

                RtcTransaction.serializeWriteToCache(dataOutput, ret);

                return ret;
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        public void remove() {
            throw new RuntimeException("NOT IMPLEMENTED");
        }
    }

    static public class ReadFromCache implements CacheChangeEvent {
        byte type;

        long id;

        String extId;

        int typeId;

        int[] bits;

        byte rtcStatus;

        int version;

        Class entityClass;

        ReadFromCache(byte t, long i, String extId, int typeId, int[] bits, int version,
                      byte rtcStatus) {
            type = t;
            id = i;
            this.extId = extId;
            this.typeId = typeId;
            this.bits = bits;
            this.version = version;
        }

        public byte getType() {
            return type;
        }

        public long getId() {
            return id;
        }

        public int getTypeId() {
            return typeId;
        }

        public int getVersion() {
            return version;
        }

        public String getExtId() {
            return extId;
        }

        public int[] getDirtyBits() {
            return bits;
        }

        //------------

        public CacheChangeType getChangeType() {
            switch (type) {
                case OP_NEW:
                case OP_NEW_CONCEPT:
                case OP_NEW_EVENT:
                    return CacheChangeType.NEW;

                case OP_MOD:
                case OP_MOD_CONCEPT:
                    return CacheChangeType.UPDATE;

                case OP_DEL:
                case OP_DEL_ONLY:
                case OP_DEL_EVENT:
                case OP_DEL_CONCEPT:
                    return CacheChangeType.DELETE;
            }

            return null;
        }

        public long getEntityId() {
            return id;
        }

        public String getEntityExtId() {
            return extId;
        }

        public void setEntityClass(Class entityClass) {
            this.entityClass = entityClass;
        }

        public Class getEntityClass() {
            return entityClass;
        }

        public int getEntityTypeId() {
            return typeId;
        }

        public int getEntityChangeVersion() {
            return version;
        }

        public int[] getEntityDirtyBits() {
            return bits;
        }
    }

    static public class WriteToCache {
        byte type;

        Entity ref;

        int typeId;

        int[] dirtyBits;

        byte rtcStatus;

        WriteToCache(byte type_, Entity ref_, int typeId) {
            type = type_;
            ref = ref_;
            this.typeId = typeId;
        }

        WriteToCache(byte type_, Entity ref_, int typeId, RtcChangeStatus rtcChangeStatus) {
            type = type_;
            ref = ref_;
            this.typeId = typeId;
            if ((rtcChangeStatus != null) && (rtcChangeStatus.getDirtyBitArray() != null)) {
                dirtyBits = new int[rtcChangeStatus.getDirtyBitArray().length];

                System.arraycopy(rtcChangeStatus.getDirtyBitArray(), 0, dirtyBits, 0,
                        rtcChangeStatus.getDirtyBitArray().length);
            }
            if (rtcChangeStatus != null)
                this.rtcStatus = rtcChangeStatus.getRtcStatus();
        }

        WriteToCache(byte type_, Entity ref_, int typeId, int[] dirtyBitArray) {
            type = type_;
            ref = ref_;
            this.typeId = typeId;
            if ((dirtyBitArray != null) && (dirtyBitArray.length > 0)) {
                dirtyBits = new int[dirtyBitArray.length];

                System.arraycopy(dirtyBitArray, 0, dirtyBits, 0, dirtyBitArray.length);
            }
        }

        public byte getType() {
            return type;
        }

        public Entity getRef() {
            return ref;
        }

        public int getTypeId() {
            return typeId;
        }

        public byte getRtcStatus() {
            return rtcStatus;
        }
    }
    
    static public class ConceptStatus {
		public int[] dirtyBitArray;
		public byte rtcStatus;
		
		public ConceptStatus(int[] dirtyBitArray, byte rtcStatus) {
			this.dirtyBitArray = dirtyBitArray;
			this.rtcStatus = rtcStatus;
		}
    }

	public void recordNewObjectTuple(ObjectBean ot, int typeId, Object object) {
		hasOps = true;
		Map<String, ObjectBean> c = object_tuple_insert.get(typeId);
        if (c == null) {
            c = new TreeMap<String, ObjectBean>();
            object_tuple_insert.put(typeId, c);
        }
        c.put(ot.getKey(), ot);
	}

	public void recordDeleteObjectTuple(ObjectBean ot, int typeId) {
		hasOps = true;
		Map<String,ObjectBean> c = object_tuple_delete.get(typeId);
        if (c == null) {
            c = new TreeMap<String,ObjectBean>();
            object_tuple_delete.put(typeId, c);
        }
        c.put(ot.getKey(),ot);
		
	}

	public void recordModifyObjectTuple(ObjectBean ot, int typeId, int[] mutableDirtyBitArray, byte rtcStatus, boolean rtcAnyModificationButReverseRef) {
		hasOps = true;
		 Map<String, ObjectBean> c = object_tuple_update.get(typeId);
        if (c == null) {
            c = new TreeMap<String, ObjectBean>();
            object_tuple_update.put(typeId, c);
        }
        
        c.put(ot.getKey(), ot);
		
	}
}
