package com.tibco.cep.query.api.impl.local;

import com.tibco.cep.query.api.*;
import com.tibco.cep.query.api.QueryPolicy.Continuous;
import com.tibco.cep.query.api.QueryPolicy.SnapshotThenContinuous;
import com.tibco.cep.query.exec.descriptors.QueryExecutionPlanDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.ExtractorDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.FilterDescriptor;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.query.service.QueryFeatures;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.query.stream.cache.SharedObjectSource;
import com.tibco.cep.query.stream.impl.expression.SimpleGlobalContext;
import com.tibco.cep.query.stream.impl.expression.SimpleQueryContext;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilterImpl;
import com.tibco.cep.query.stream.impl.rete.integ.filter.FilterHelper;
import com.tibco.cep.query.stream.impl.rete.integ.standalone.DefaultQueryTypeInfo;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.service.RegionManager;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.Filter;

import java.util.*;
import java.util.Map.Entry;

/*
* Author: Karthikeyan Subramanian / Date: Jun 28, 2010 / Time: 5:33:37 PM
*/
public class DeleteQueryStatementImpl implements QueryStatement, QueryResultSetManager {
    protected QueryConnection connection;
    protected QueryStatementManager statementManager;
    protected QuerySession querySession;
    protected String queryText;
    protected DeletePlanGeneratorImpl planGenerator;
    protected RegionManager regionManager;
    protected EntityDeleter entityDeleter;

    //For Storing the mapping of bindVariables
    protected HashMap<String, Object> queryBindParams;
    
    public DeleteQueryStatementImpl(QueryConnection connection, QueryStatementManager statementManager,
                              QuerySession querySession, RegionManager regionManager, DeletePlanGeneratorImpl generator) {
        this.statementManager = statementManager;
        this.connection = connection;
        this.querySession = querySession;
        this.queryText = generator.getQueryText();
        this.planGenerator = generator;
        this.regionManager = regionManager;
        this.entityDeleter = new EntityDeleter(this.regionManager.getAgentService().getObjectTableCache());
    }

    @Override
    public void close() {
    }

    @Override
    public void unregisterAndStopQuery(ReteQuery query) throws Exception {
    }

    @Override
    public QueryResultSet executeQuery(QueryPolicy policy, QueryFeatures queryFeatures) throws QueryException {
        if (policy instanceof Continuous || policy instanceof SnapshotThenContinuous) {
            throw new QueryException("Delete query cannot be run in continuous mode.");
        }
        List<String> sources = planGenerator.getSourceNames();
        if (sources.size() != 1) {
            throw new QueryException("Delete Query Statement cannot handle more than one source");
        }
        SharedObjectSource source = this.regionManager.getSOSRepository().getSource(sources.get(0));
        EntityDao cache = (EntityDao) source.getInternalSource();
        Class klass = null;
        if (source instanceof DefaultQueryTypeInfo) {
            klass = ((DefaultQueryTypeInfo) source).getType();
        }
        if (klass == null) {
            try {
                klass = Class.forName(sources.get(0));
            } catch (ClassNotFoundException e) {
                Collection values = cache.getAll();
                if (values != null && values.size() != 0) {
                    klass = values.iterator().next().getClass();
                } else {
                    throw new QueryException(e);
                }
            }

        }
        QueryExecutionPlanDescriptor qepd = planGenerator.getQueryExecutionPlanDescriptor();
        LinkedHashMap<String, Object> nameToObjectMap = qepd.getNameToObject();
        List<FilterDescriptor> filterDescriptors = qepd.getFilterDescriptors();
        ReteEntityFilter[] filters = new ReteEntityFilter[filterDescriptors.size()];
        Query query = planGenerator.getQuery();

        SimpleQueryContext queryContext = new SimpleQueryContext(regionManager.getRegionName(), query.getName(), queryBindParams);//new HashMap<String, Object>());
        int i = 0;
        for (FilterDescriptor filterDescriptor : filterDescriptors) {
            final ExtractorDescriptor ed = (ExtractorDescriptor) nameToObjectMap.get(filterDescriptor.getFilterExpressionName());
            filters[i++] = new ReteEntityFilterImpl(ed.getExtractor());
        }
        SimpleGlobalContext simpleGlobalContext = new SimpleGlobalContext();

        Filter filter = FilterHelper.createFilter(klass, filters, simpleGlobalContext, queryContext, regionManager.getSOSRepository());

        try {
            Map<Long, String> results = entityDeleter.deleteEntities(cache, filter);
            return new DeleteQueryResultSet(this, results);
        } catch (Exception e) {
            throw new QueryException(e);
        }
    }

    @Override
    public QueryResultSet executeQuery(QueryPolicy policy) throws QueryException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public QueryListenerHandle executeQuery(QueryPolicy policy, QueryListener listener)
            throws QueryException {
        throw new QueryException("Delete query cannot be run in continuous mode.");
    }

    @Override
    public QueryListenerHandle executeQuery(QueryPolicy policy, QueryListener listener, QueryFeatures queryFeatures) throws QueryException {
        throw new QueryException("Delete query cannot be run in continuous mode.");
    }

    @Override
    public QueryConnection getConnection() {
        return this.connection;
    }

    @Override
    public void setObject(String parameterName, Object o) {
        if (queryBindParams == null) {
            queryBindParams = new HashMap<String, Object>();
        }

        queryBindParams.put("BV$" + parameterName, o);
    }

    @Override
    public void clearParameters() {
    }

    @Override
    public String getText() {
        return this.queryText;
    }

    @Override
    public SharedObjectSource getFakeSharedObjectSource() {
        return regionManager.getSOSRepository().getDefaultSource();
    }

    @Override
    public ReteQuery[] getCachedReteQueries() {
        return new ReteQuery[0];
    }

    private static class DeleteQueryResultSet implements QueryResultSet {
        private Map<Integer, String> indexToColumnMap = new HashMap<Integer, String>();
        private Map<String, Integer> columnToIndexMap = new HashMap<String, Integer>();
        private int rowCount;
        private Iterator<Entry<Long, String>> iterator;
        private Entry<Long, String> entry;
        private QueryStatement statement;

        private DeleteQueryResultSet(QueryStatement statement, Map<Long, String> results) {
            this.indexToColumnMap.put(0, "id");
            this.indexToColumnMap.put(1, "extId");
            this.columnToIndexMap.put("id", 0);
            this.columnToIndexMap.put("extId", 1);
            this.statement = statement;
            this.iterator = results.entrySet().iterator();
            rowCount = results.size();
        }

        @Override
        public void close() throws Exception {

        }

        @Override
        public int findColumn(String name) {
            return columnToIndexMap.get(name);
        }

        @Override
        public Object getObject(int columnIndex) throws IndexOutOfBoundsException {
            if (columnIndex == 0) {
                return entry.getKey();
            } else if (columnIndex == 1) {
                return entry.getValue();
            } else {
                throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public int getRowCountIfPossible() {
            return rowCount;
        }

        @Override
        public QueryStatement getStatement() {
            return statement;
        }

        @Override
        public boolean isBatchEnd() {
            return iterator.hasNext();
        }

        @Override
        public boolean next() {
            boolean hasNext = iterator.hasNext();
            if (hasNext == true) {
                entry = iterator.next();
            }
            return hasNext;
        }
    }
}
