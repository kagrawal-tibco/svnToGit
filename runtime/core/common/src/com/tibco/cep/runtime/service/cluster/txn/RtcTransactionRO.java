package com.tibco.cep.runtime.service.cluster.txn;

import java.util.Map;
import java.util.Set;

import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.element.Concept;

/**
 * @author bala
 * 
 */
public interface RtcTransactionRO {

    Map<Integer, Map<Long, Concept>> getAddedConcepts();
    
    Map<Integer, Map<Long, Concept>> getModifiedConcepts();
    
    Map<Integer, Set<Long>> getDeletedConcepts();

    Map<Integer, Map<Long, Event>> getAddedEvents();

    Map<Integer, Set<Long>> getDeletedEvents();
}
