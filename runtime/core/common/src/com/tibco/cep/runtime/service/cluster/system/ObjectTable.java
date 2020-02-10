/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.system;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.ControlDao;

/*
* Author: Ashwin Jayaprakash Date: Apr 20, 2009 Time: 3:17:16 PM
*/
public interface ObjectTable  {
    void init(Cluster cluster) throws Exception;
    
    Collection<Tuple> getByIds(Collection<Long> ids)
            throws IOException;

    Collection<Tuple> getByExtIds(Collection<String> extIds)
            throws IOException;

    Tuple getById(long id) throws Exception;


    void markDeleted(long id) throws Exception;

    boolean containsKey(Long id);

    Tuple getByExtId(String extId) throws Exception;

    void purgeDeletedObjects();

    void putAll(Map<Long, Tuple> data);

    


    void removeAllFromObjectIdTable(Set<Long> entries);


    boolean isEventAlive(Long eventId);

    void putAll(Map<Long, Tuple> changedMap, Map<String, Tuple> changedExtIdMap) throws Exception;

    void removeAll(Collection<Long> deletedIds, Collection<String> deletedExtIds) throws Exception;

    void lockId(long id);

    void unlockId(long id);

    void shutdown();

    ElementWriter createElementWriter();

    /**
     *
     * @param extId
     * @param uri
     */
    void putExtIdToTypeCache(String extId, String uri);
    void putExtIdsToTypeCache(Collection<String> extIds, String uri);

    /**
     * Get the Entity Type URI given an extId : This call is used when useobjectTable property is set to false.
     * @param extId
     * @return
     */
    String getURI(String extId);

    void start() throws Exception;

    //-----------

    public interface ElementWriter {
        void addObject(Tuple tuple) throws Exception;

        void commit() throws Exception;
    }

    // Used by useobjecttable=false related
    <K, V> ControlDao<K, V> getExtIdToTypeMap();

    interface Tuple<E extends Entity> {
        String getExtId();

        int getTypeId();

        long getId();

        void markDeleted();

        boolean isDeleted();

        int getVersion();

        E getEntity();

        long getTimeDeleted();
    }
}
