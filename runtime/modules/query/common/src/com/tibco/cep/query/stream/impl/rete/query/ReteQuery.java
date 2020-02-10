package com.tibco.cep.query.stream.impl.rete.query;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.impl.rete.GenericReteEntityHandle;
import com.tibco.cep.query.stream.impl.rete.ReteEntityBatchHandle;
import com.tibco.cep.query.stream.impl.rete.ReteEntityHandle;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySource;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;
import com.tibco.cep.query.stream.query.continuous.ContinuousQuery;
import com.tibco.cep.runtime.session.RuleSession;

/*
* Author: Karthikeyan Subramanian / Date: Feb 22, 2010 / Time: 5:16:08 PM
*/
public interface ReteQuery<I> extends ContinuousQuery<I> {

    String getName();

    LinkedHashMap<Class, Class[]> getSourceClassAndHierarchy();

    boolean[] getSnapshotRequiredForSource();

    Collection<Class> getReteEntityClasses();

    Map<Class, ReteEntityFilter[]> getReteEntityClassAndFilters();

    Map<Class, ReteEntitySource[]> getReteEntityClassAndListeners();

    Map<String, ReteEntityFilter[]> getReteEntityResourceIdAndFilters();

    void enableReteEntityFilters();

    void disableReteEntityFilters();

    RuleSession getRuleSession();

    void enqueueInput(ReteEntityHandle handle, Sender sender) throws Exception;

    void enqueueInput(ReteEntityBatchHandle batchHandle, Sender sender) throws Exception;

    LinkedBlockingQueue<GenericReteEntityHandle> getAccumulatedItemsDuringSSA();

    void init(SharedObjectSourceRepository sourceRepository, Map<String, Object> externalData) throws Exception;

    void setSelfJoin(boolean selfJoin);

    boolean isSelfJoin();
}
