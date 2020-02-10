/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.util;

import java.io.Serializable;
import java.util.HashMap;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.system.CacheSequenceManager;
import com.tibco.cep.runtime.service.cluster.util.DefaultCacheSequenceManager.CacheSequenceRecord;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDao.ChangeListener;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Mar 11, 2009
 * Time: 10:23:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultCacheSequenceManager implements CacheSequenceManager, ChangeListener<String, CacheSequenceRecord> {
    
    Cluster cluster;
    ControlDao<String, CacheSequenceRecord> sequenceManager;
    ControlDao<String, Long> sequence;

    HashMap<String, CacheSequenceRecord> all_registrations = new HashMap<String, CacheSequenceRecord>();
    HashMap<String, CacheSequenceManager.Sequence> all_sequences = new HashMap<String, CacheSequenceManager.Sequence>();

    public DefaultCacheSequenceManager() {

    }

    public void init (Cluster cluster) throws Exception{
		this.cluster = cluster;

        sequenceManager = cluster.getDaoProvider().createControlDao(String.class, CacheSequenceRecord.class, ControlDaoType.CacheSequenceManager);

        sequence = cluster.getDaoProvider().createControlDao(String.class, Long.class, ControlDaoType.CacheSequence);
    }

    public void start() throws Exception {
        sequenceManager.start();
        sequenceManager.registerListener(this);

        sequence.start();

        init();
    }

    protected void init() throws Exception {
        java.util.Iterator it_sequences = sequenceManager.keySet().iterator();
        while (it_sequences.hasNext()) {
            String key = (String) it_sequences.next();
            CacheSequenceRecord rec = (CacheSequenceRecord) sequenceManager.get(key);
            if (rec != null) {
            	if (rec.useDB == false) {
            		registerSequence(rec, false);
            	}
            }
        }
    }

    public synchronized void createSequence(String name, long start, long end, int batchSize, boolean useDB) throws Exception {
        try {
            if (useDB && !cluster.getClusterConfig().isCacheAside()) {
                throw new Exception("CreateSequence: Flag useDB=true is only valid for cache aside configuration with Backing Store");
            }
            if (end != -1) {
                if ((end-start) <= batchSize) {
                    throw new Exception("CreateSequence: BatchSize cannot be less than (end-start)");
                }
            }
            sequenceManager.lock(name, -1);
            CacheSequenceRecord seq = (CacheSequenceRecord) sequenceManager.get(name);
            if (seq == null) {
                seq = new CacheSequenceRecord();
                seq.name = name;
                seq.start = start;
                if (end <= 0) {
                    end = Long.MAX_VALUE;
                }
                seq.end = end;
                seq.batchSize = batchSize;
                seq.useDB = useDB;
                seq.ownerIdFindPreventCyclicNotifs = getLocalNodeId();

                sequenceManager.put(name, seq);
            }
            registerSequence(seq, true);
        } finally {
            sequenceManager.unlock(name);
        }
    }

    protected String getLocalNodeId() {
        return cluster.getGroupMembershipService().getLocalMember().getMemberId().getAsString();
    }

    public synchronized void resetSequence(String name, long start) throws Exception {
        try {
            sequenceManager.lock(name, -1);
            CacheSequenceRecord seq = (CacheSequenceRecord) sequenceManager.get(name);
            if (seq != null) {
                if (seq.start != start) {
                    CacheSequenceManager.Sequence sequence = all_sequences.get(name);
                    if (sequence != null) {
                        sequence.resetShared(start);
                    }
                }
            }
            seq.start=start;

            // Do this right here instead of waiting for the notify
            resetLocalSequence(seq);

            sequenceManager.put(name, seq);
        } finally {
            sequenceManager.unlock(name);
        }
    }

    public synchronized void removeSequence(String name) throws Exception {
        sequenceManager.lock(name, -1);
        try {
            CacheSequenceRecord seq = (CacheSequenceRecord) sequenceManager.get(name);
            if (seq != null) {
                CacheSequenceManager.Sequence sequence = all_sequences.get(name);
                if (sequence != null) {
                    sequence.destroy();
                }

                // Do this right here instead of waiting for the notify
                deregisterSequence(seq);

                sequenceManager.remove(name);
            }
        } finally {
            sequenceManager.unlock(name);
        }
    }

    private synchronized void registerSequence(CacheSequenceRecord rec, boolean init) throws Exception {
        all_registrations.put(rec.name, rec);
        CacheSequenceManager.Sequence seq = all_sequences.get(rec.name);
        if (seq == null) {
            if (rec.useDB) {
                DBSequence sequence = new DBSequence(rec);
                all_sequences.put(rec.name, sequence);
                if (init) {
                    sequence.init();
                }
            } else {
                CacheSequence sequence = new CacheSequence(rec);
                all_sequences.put(rec.name, sequence);
                if (init) {
                    sequence.init();
                }
            }
        }
    }

    private synchronized void resetLocalSequence(CacheSequenceRecord rec) throws Exception {
        CacheSequenceManager.Sequence seq = all_sequences.get(rec.name);
        if (seq != null) {
            seq.resetLocalSequence();
        }
    }

    private synchronized void deregisterSequence(CacheSequenceRecord rec) throws Exception {
        all_registrations.remove(rec.name);
        all_sequences.remove(rec.name);
    }

    public void onPut(String key, CacheSequenceRecord value) {
        try {
            String myId = getLocalNodeId();
            if (myId.equals(value.ownerIdFindPreventCyclicNotifs)) {
                return;
            }

            registerSequence(value, value.useDB);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onUpdate(String key, CacheSequenceRecord oldValue, CacheSequenceRecord newValue) {
        try {
            String myId = getLocalNodeId();
            if (myId.equals(newValue.ownerIdFindPreventCyclicNotifs)) {
                return;
            }

            resetLocalSequence(newValue);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void onRemove(String key, CacheSequenceRecord value) {
        try {
            String myId = getLocalNodeId();
            if (myId.equals(value.ownerIdFindPreventCyclicNotifs)) {
                return;
            }

            deregisterSequence(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public synchronized long nextSequence(String name) throws Exception {
        CacheSequenceManager.Sequence seq = all_sequences.get(name) ;
        if (seq != null) {
            return seq.next();
        } else {
            throw new Exception("SEQUENCE " + name + " not registered");
        }
    }

    public static class CacheSequenceRecord implements Serializable {
        String name;
        long start;
        long end;
        int batchSize;
        boolean useDB;
        String ownerIdFindPreventCyclicNotifs;

        public CacheSequenceRecord() {
        }
    }

    class CacheSequence implements CacheSequenceManager.Sequence {
        CacheSequenceRecord rec;
        long cur;
        long next_fetch;
        boolean initialized = false;

        CacheSequence(CacheSequenceRecord rec) {
            this.rec = rec;
        }

        public void init() {
            sequence.lock(rec.name, -1);
            try {
            	if (sequence.get(rec.name) == null) {
            		sequence.put(rec.name, rec.start);
            	}
            } finally {
                sequence.unlock(rec.name);
            }
        }

        public synchronized void resetShared(long start) {
            sequence.lock(rec.name, -1);
            try {
                Long next = (Long) sequence.get(rec.name);
                if (next != null) {
                    if (next < start) {
                        sequence.put(rec.name, start);
                    }
                }
            } finally {
                sequence.unlock(rec.name);
            }
        }

        public synchronized void resetLocalSequence() {
            next_fetch = -1;
        }

        public synchronized long next() throws Exception {
            if (!initialized) {
                fetch();
                initialized = true;
            }
            if (cur >= next_fetch) {
                fetch();
            }
            long ret = cur;
            cur++;
            return ret;
        }

        public void destroy() {
            sequence.remove(rec.name);
        }

        void fetch() {
            sequence.lock(rec.name, -1);
            try {
                Long next = (Long) sequence.get(rec.name);
                long tmp = next + rec.batchSize;
                if (tmp >= rec.end) {
                    tmp = rec.start;
                    cur = next;
                    next_fetch = rec.end + 1;
                } else {
                    cur = next;
                    next_fetch = cur + rec.batchSize;
                }
                sequence.put(rec.name, tmp);
            } finally {
                sequence.unlock(rec.name);
            }
        }
    }

    class DBSequence implements CacheSequenceManager.Sequence {
        CacheSequenceRecord rec;
        long cur;
        long next_fetch;
        boolean initialized=false;
        BackingStore backingStore = null;

        DBSequence(CacheSequenceRecord rec) throws Exception {
            this.rec=rec;
            backingStore = cluster.getCacheAsideStore();
        }

        public void init() {
            try {
                sequence.lock(rec.name, -1);
                try {
                	backingStore.createSequence(rec.name, rec.start, rec.end, rec.start, rec.batchSize);
                } finally {
                    sequence.unlock(rec.name);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        public synchronized void resetLocalSequence() {
            next_fetch = -1;
        }

        public synchronized long next() throws Exception{
            if (!initialized) {
                fetch();
                initialized = true;
            }
            if (cur >= next_fetch) {
                fetch();
            }
            long ret = cur;
            cur++;
            return ret;
        }

        public synchronized void resetShared(long start) {
            sequence.lock(rec.name, -1);
            try {
                backingStore.removeSequence(rec.name);
                backingStore.createSequence(rec.name, rec.start, rec.end, start, rec.batchSize);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                sequence.unlock(rec.name);
            }
        }

        public synchronized void destroy() {
            try {
                backingStore.removeSequence(rec.name);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        synchronized void fetch() throws Exception {
            sequence.lock(rec.name, -1);
            try {
	            Long next = (Long) backingStore.nextSequence(rec.name);
	            cur = next;
	            next_fetch = cur + rec.batchSize;
	            next_fetch = (next_fetch <= rec.end) ? next_fetch: rec.end;
            } finally {
                sequence.unlock(rec.name);
            }
        }
    }
}
