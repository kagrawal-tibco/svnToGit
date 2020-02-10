package com.tibco.cep.runtime.service.cluster.system;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.session.sequences.SequenceManager;

public interface CacheSequenceManager extends SequenceManager {

    public void init(Cluster cluster) throws Exception;

    void start() throws Exception;

    interface Sequence {
        long next() throws Exception;
        void destroy();
        void resetShared(long start);
        void init();
        void resetLocalSequence();
    }
}
