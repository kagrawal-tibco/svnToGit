package com.tibco.cep.runtime.service.cluster.om;

import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransactionProperties;
import com.tibco.cep.runtime.service.om.ObjectBasedStore;

public interface DistributedCacheBasedStore extends ObjectBasedStore, ObjectManagerInfo {

    void init() throws Exception;

    CacheAgent getCacheAgent();

    void refreshEntity(long id, int typeId, int version) throws Exception;

    Element getElement(long id, Class entityClz);

    //TODO remove, and use getEntity.
    Event getEvent(long id, Class entityClz, boolean eventClzIsAccurate);

    //TODO remove, and use getEntity.
    Event getEvent(String extId, Class entityClz);


    String getEntityCacheName(String entityPath) throws Exception;

    RtcTransactionProperties getRtcTransactionProperties();


    void flushCaches() throws Exception;

    Element getElement(String extId, Class entityClass);

    Handle getAddElementHandle(Element element, boolean forceSet) throws DuplicateExtIdException;

    Handle getAddEventHandle(Event event, boolean forceSet) throws DuplicateExtIdException;

    void removeHandleForCacheOnly(Handle handle, Object ref);

    void reset();

    void resetThreadLocalMaps();

    long getNumHandlesRecovered();

    long getNumHandlesError();

    long getNumHandlesInStore();

    enum CacheType {
        MASTER(0),
        METADATA(1),
        CONCEPT(2),
        EVENT(3),
        RECOVERY(4),
        CACHEIDGENERATOR(5);

        private int value;

        CacheType(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }


}
