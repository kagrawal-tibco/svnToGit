package com.tibco.cep.query.api.impl.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.api.QueryException;
import com.tibco.cep.query.api.QueryPolicy;
import com.tibco.cep.query.exec.QueryExecutionPlanDescriptorFactory;
import com.tibco.cep.query.exec.descriptors.QueryExecutionPlanDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.FilterDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.SourceDescriptor;
import com.tibco.cep.query.exec.impl.EvaluatorDescriptorFactory;
import com.tibco.cep.query.exec.impl.QueryExecutionPlanDescriptorFactoryImpl;
import com.tibco.cep.query.model.AliasedIdentifier;
import com.tibco.cep.query.model.DeleteContext;
import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.FromClause;
import com.tibco.cep.query.model.QueryContext;
import com.tibco.cep.query.model.WhereClause;
import com.tibco.cep.query.model.visitor.impl.BindResolutionVisitor;
import com.tibco.cep.query.model.visitor.impl.ContextResolutionVisitor;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.session.RuleSession;

/*
* Author: Karthikeyan Subramanian / Date: Jun 29, 2010 / Time: 12:01:13 PM
*/
public class DeletePlanGeneratorImpl implements PlanGenerator {

    protected Query query;
    protected Logger logger;
    protected static final List<String> columnNames = Arrays.asList(new String[]{"id", "ext_id"});
    protected static final List<String> columnTypeNames = Arrays.asList(new String[]{"long", "String"});
    protected List<String> sourceNames = new ArrayList<String>(1);
    protected TypeManager typeManager;
    protected EvaluatorDescriptorFactory evaluatorDescriptorFactory;
    protected QueryExecutionPlanDescriptorFactory qepDescriptorFactory = new QueryExecutionPlanDescriptorFactoryImpl();
    protected QueryExecutionPlanDescriptor qepd;

    public DeletePlanGeneratorImpl(Query query, String regionName) throws QueryException {
        this.query = query;
        logger = query.getQuerySession().getRuleSession().getRuleServiceProvider().
                getLogger(PlanGeneratorImpl.class);
        this.typeManager = query.getQuerySession().getRuleSession().getRuleServiceProvider().getTypeManager();
        this.evaluatorDescriptorFactory = new EvaluatorDescriptorFactory(this.typeManager);
        QueryContext context = query.getModel().getContext();
        if(!(context instanceof DeleteContext)) {
            throw new QueryException("DeletePlanGenerator can be used only by Delete queries.");
        }
        try {
            validateQuery(context);
            resolveQuery(context);
            qepd = qepDescriptorFactory.newQueryExecutionPlanDescriptor(query, regionName);
            buildFilters(qepd.getFilterDescriptors());
            buildSources(qepd.getSourceDescriptors());
        } catch (Exception e) {
            throw new QueryException(e);
        }
    }

    private void buildSources(List<SourceDescriptor> sourceDescriptors) {
        for (SourceDescriptor sourceDescriptor : sourceDescriptors) {
            sourceNames.add(sourceDescriptor.getEntityClassName());
        }
    }

    public QueryExecutionPlanDescriptor getQueryExecutionPlanDescriptor() {
        return qepd;
    }

    public Query getQuery() {
        return query;
    }

    private void resolveQuery(QueryContext context) throws Exception {
        AliasedIdentifier aliasedIdentifier = (AliasedIdentifier)context.getFromClause().getChildren()[0];
        aliasedIdentifier.resolveContext();
        FromClause fromClause = context.getFromClause();
        fromClause.accept(new ContextResolutionVisitor());
        fromClause.accept(new BindResolutionVisitor());
        WhereClause whereClause = context.getWhereClause();
        whereClause.accept(new ContextResolutionVisitor());
        whereClause.accept(new BindResolutionVisitor());
        Expression expression = context.getWhereClause().getExpression();
        expression.resolveContext();
    }

    private void validateQuery(QueryContext context) throws QueryException {
        try {
            // Validate from clause
            FromClause fromClause = context.getFromClause();
            if(fromClause == null || fromClause.getChildCount() != 1) {
                throw new QueryException("Multiple source cannot be specified in Delete query");
            }

            // Validate where clause
            WhereClause whereClause = context.getWhereClause();
            if(whereClause == null || whereClause.getChildren().length == 0) {
                throw new QueryException("Delete Query should have a where clause.");
            }
        } catch (Exception e) {
            throw new QueryException(e);
        }
    }

    private void buildFilters(List<FilterDescriptor> filterDescriptors) throws Exception {
        for (FilterDescriptor filterDescriptor : filterDescriptors) {
            if(filterDescriptor != null) {
                System.out.println("");
            }
        }
    }

    @Override
    public String getQueryText() {
        return query.getSourceText();
    }

    @Override
    public List<String> getColumnNames() {
        return columnNames;
    }

    @Override
    public List<String> getColumnTypeNames() {
        return columnTypeNames;
    }

    @Override
    public List<String> getSourceNames() {
        return this.sourceNames;
    }

    @Override
    public GeneratedArtifacts generateInstance(String name, QueryPolicy policy,
                                               RuleSession ruleSession,
                                               SharedObjectSourceRepository sourceRepository,
                                               ExecutorService executorService)
            throws QueryException {
        return null;
    }

    @Override
    public void discard() {        
    }
}
