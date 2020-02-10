package com.tibco.cep.query.stream.impl.rete.integ;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.query.stream.cache.SharedObjectSource;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.cache.SharedPointer;
import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.impl.expression.SimpleGlobalContext;
import com.tibco.cep.query.stream.impl.expression.SimpleQueryContext;
import com.tibco.cep.query.stream.impl.rete.LiteReteEntity;
import com.tibco.cep.query.stream.impl.rete.ReteEntityHandle;
import com.tibco.cep.query.stream.impl.rete.ReteEntityHandleType;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;
import com.tibco.cep.query.stream.impl.rete.integ.filter.FilterHelper;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.query.Sender;
import com.tibco.cep.query.stream.impl.rete.service.EntityLoader;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.Filter;

/*
 * Author: Ashwin Jayaprakash Date: Mar 17, 2008 Time: 11:11:17 AM
 */
public class EntityLoaderImpl implements EntityLoader {
    private Iterator allEntriesIterator;

    protected final int maxPerBatch;

    private BatchResult batchResult;

    protected Class entityClass;

    protected ReteQuery query;

    protected SharedObjectSourceRepository sosRepo;

    protected SharedObjectSource source;

    protected ReteEntityFilter[] reteFilters;

    protected Filter filter;

    public EntityLoaderImpl(Class klass, ReteQuery query, SharedObjectSourceRepository repository,
                            int maxPerBatch, ReteEntityFilter[] reteFilters) {
        this.entityClass = klass;
        this.query = query;
        this.sosRepo = repository;
        this.batchResult = new BatchResult();
        this.maxPerBatch = maxPerBatch;
        this.reteFilters = reteFilters;

        //-----------

        SimpleGlobalContext simpleGlobalContext = new SimpleGlobalContext();

        Context context = query.getContext();
        DefaultQueryContext defaultQueryContext = context.getQueryContext();

        SimpleQueryContext simpleQueryContext =
                new SimpleQueryContext(defaultQueryContext.getRegionName(), defaultQueryContext.getQueryName(),
                        defaultQueryContext.getGenericStore());

        filter = FilterHelper.createFilter(klass, reteFilters, simpleGlobalContext, simpleQueryContext, repository);
    }

    public Class getEntityClass() {
        return entityClass;
    }

    private void init() {
        source = sosRepo.getSource(entityClass.getName());
        EntityDao entityDao = (EntityDao) source.getInternalSource();

        Set allEntries = entityDao.entrySet(filter, Integer.MAX_VALUE);

        allEntriesIterator = allEntries.iterator();
    }

    public BatchResult resumeOrStartBatchLoad() throws Exception {
        if (allEntriesIterator == null) {
            init();
        }

        final Iterator<Entry> cachedAllEntriesIterator = allEntriesIterator;
        final int cachedMaxPerBatch = maxPerBatch;
        int i = 0;
        for (; cachedAllEntriesIterator.hasNext() && i < cachedMaxPerBatch; i++) {
            Entity entity = (Entity) cachedAllEntriesIterator.next().getValue();

            send(entity);
        }

        batchResult.setHasMore(cachedAllEntriesIterator.hasNext());
        batchResult.setNumSent(i);

        return batchResult;
    }

    private void send(Entity entity) throws Exception {
        ReteEntityHandle handle = new ReteEntityHandle(entityClass, entity.getId(), ReteEntityHandleType.NEW);
        SharedPointer sharedPointer = new LiteReteEntity(entity, source);
        handle.setSharedPointer(sharedPointer);

        query.enqueueInput(handle, Sender.SNAPSHOT);
    }

    public void end() {
        entityClass = null;
        query = null;
        sosRepo = null;
        allEntriesIterator = null;
        batchResult = null;
        filter = null;
    }
}
