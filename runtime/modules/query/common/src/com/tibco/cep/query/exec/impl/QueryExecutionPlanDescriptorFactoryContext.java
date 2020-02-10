package com.tibco.cep.query.exec.impl;

import com.tibco.be.util.idgenerators.IdentifierGenerator;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.runtime.model.TypeManager;

/**
 * Contains objects used by a {@link com.tibco.cep.query.exec.QueryExecutionPlanDescriptorFactory}
 * when building the execution plan descriptor
 * for a given Query in a given region.
 */
class QueryExecutionPlanDescriptorFactoryContext {


    protected final QueryExecutionPlanDescriptorBuilder builder;
    protected final ColumnNameFactory columnNameFactory;
    protected final IdentifierGenerator idGenerator;
    protected final Query query;
    protected final TypeManager typeManager;


    /**
     * @param query      Query described by the execution plan descriptor.
     * @param regionName String name of the region queried.
     * @throws Exception upon error
     */
    public QueryExecutionPlanDescriptorFactoryContext(
            Query query,
            String regionName)
            throws Exception {

        this.builder = new QueryExecutionPlanDescriptorBuilder(query, regionName);
        this.idGenerator = this.builder.getQueryExecutionPlanDescriptor().getIdGenerator();
        this.query = query;
        this.typeManager = query.getQuerySession().getRuleSession().getRuleServiceProvider().getTypeManager();
        this.columnNameFactory = new ColumnNameFactory(this.typeManager);
    }


    public QueryExecutionPlanDescriptorBuilder getBuilder() {
        return builder;
    }


    public ColumnNameFactory getColumnNameFactory() {
        return columnNameFactory;
    }


    public IdentifierGenerator getIdGenerator() {
        return idGenerator;
    }


    public Query getQuery() {
        return this.query;
    }


    public TypeManager getTypeManager() {
        return typeManager;
    }
}
