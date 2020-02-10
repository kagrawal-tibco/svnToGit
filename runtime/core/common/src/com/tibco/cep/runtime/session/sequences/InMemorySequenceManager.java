package com.tibco.cep.runtime.session.sequences;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Mar 11, 2009
 * Time: 3:12:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class InMemorySequenceManager implements SequenceManager{
    ConcurrentHashMap<String, _Sequence> m_sequences = new ConcurrentHashMap<String, _Sequence>();

    public InMemorySequenceManager() {
    }
    public synchronized void createSequence(String name, long start, long end, int batchSize, boolean useDB) throws Exception {
        _Sequence seq=m_sequences.get(name);
        if (seq == null) {
            seq = new _Sequence();
            seq.next=start;
            m_sequences.put(name, seq);
        }
    }

    public void removeSequence(String name) throws Exception {
        m_sequences.remove(name);
    }

    public void resetSequence(String name, long start) throws Exception {
        _Sequence seq=m_sequences.get(name);
        if (seq != null) {
            seq.reset(start);
        } else {
            throw new Exception("Sequence " + name + " not registered");
        }
    }

    public long nextSequence(String name) throws Exception {
        _Sequence seq=m_sequences.get(name);
        if (seq != null) {
            return seq.next();
        } else {
            throw new Exception("Sequence " + name + " not registered");
        }
    }

    class _Sequence {
        volatile long next=0L;
        long end=-1;
        long next() {
            synchronized(this) {
                return ++next;
            }
        }

        void reset(long next) {
            synchronized(this) {
                if (next > this.next)
                    this.next=next;
            }
        }
    }
}
