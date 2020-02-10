package com.tibco.cep.query.api.impl.local;

import java.util.List;
import java.util.concurrent.ExecutorService;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.api.QueryException;
import com.tibco.cep.query.api.QueryPolicy;
import com.tibco.cep.query.exec.QueryExecutionPlan;
import com.tibco.cep.query.exec.QueryExecutionPlanDescriptorFactory;
import com.tibco.cep.query.exec.QueryExecutionPlanFactory;
import com.tibco.cep.query.exec.codegen.CodeGenerationQueryExecutionPlanFactory;
import com.tibco.cep.query.exec.descriptors.QueryExecutionPlanDescriptor;
import com.tibco.cep.query.exec.impl.QueryExecutionPlanDescriptorFactoryImpl;
import com.tibco.cep.query.exec.impl.QueryExecutionPlanFactoryImpl;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.query.service.QueryProperty;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.runtime.session.RuleSession;

/*
* Author: Ashwin Jayaprakash Date: Mar 21, 2008 Time: 11:16:24 AM
*/

public class PlanGeneratorImpl implements PlanGenerator {

    protected static final QueryExecutionPlanDescriptorFactory qepDescriptorFactory =
            new QueryExecutionPlanDescriptorFactoryImpl();

    protected QueryExecutionPlanFactory qepFactory;

    protected Query query;
    protected int threadpoolSize;
    protected Logger logger;


    public PlanGeneratorImpl(Query query, String regionName) throws QueryException {
        this.query = query;
        logger = query.getQuerySession().getRuleSession().getRuleServiceProvider().
                getLogger(PlanGeneratorImpl.class);

        try {
            //todo make this composite, when ready.
            String planFactoryName = System.getProperty("com.tibco.cep.query.executionplan.factory", "composite");
            // System property that is used to create threadpool for continuous queries.
            threadpoolSize = Integer.parseInt(System.getProperty(
                    QueryProperty.CONTINUOUS_QUERY_MAX_THREADS.getPropName(), "0"));
            if (threadpoolSize > 0) {
                logger.log(Level.DEBUG, "RETEQuery_V2");
            } else {
                logger.log(Level.DEBUG, "RETEQuery");
            }

            if (planFactoryName.equalsIgnoreCase("codegen")) {
                this.qepFactory = new CodeGenerationQueryExecutionPlanFactory(query, regionName);
            } else if (planFactoryName.startsWith("composite:")) {
                QueryExecutionPlanDescriptor qepd =
                        qepDescriptorFactory.newQueryExecutionPlanDescriptor(query, regionName);

                int actualClassNameStart = planFactoryName.indexOf(':') + 1;
                String compositeFactoryClassName = planFactoryName.substring(actualClassNameStart);

                Class<? extends QueryExecutionPlanFactoryImpl> clazz =
                        (Class<? extends QueryExecutionPlanFactoryImpl>) Class
                                .forName(compositeFactoryClassName);

                QueryExecutionPlanFactoryImpl instance = clazz.newInstance();
                instance.setQepDescriptor(qepd);

                this.qepFactory = instance;
            } else { // composite
                QueryExecutionPlanDescriptor qepd =
                        qepDescriptorFactory.newQueryExecutionPlanDescriptor(query, regionName);

                this.qepFactory = new QueryExecutionPlanFactoryImpl();
                ((QueryExecutionPlanFactoryImpl) this.qepFactory).setQepDescriptor(qepd);
            }
        } catch (Exception e) {
            throw new QueryException(e);
        }
    }


    @Override
    public String getQueryText() {
        return query.getSourceText();
    }


    @Override
    public List<String> getColumnNames() {
        return this.qepFactory.getColumnNames();
    }


    @Override
    public List<String> getColumnTypeNames() {
        return this.qepFactory.getColumnTypeNames();
    }


    @Override
    public List<String> getSourceNames() {
        return this.qepFactory.getSourceNames();
    }


    public QueryExecutionPlanDescriptor getQueryExecutionPlanDescriptor() {
        return ((QueryExecutionPlanFactoryImpl) this.qepFactory).getQepDescriptor();
    }

    public GeneratedArtifacts generateInstance(
            String name,
            QueryPolicy policy, RuleSession ruleSession,
            SharedObjectSourceRepository sourceRepository,
            ExecutorService executorService)
            throws QueryException {
        QueryExecutionPlan qep;

        try {
            qep = this.qepFactory.newQueryExecutionPlan(name, !(policy instanceof QueryPolicy.Snapshot));
        } catch (QueryException e) {
            throw e;
        } catch (Exception e) {
            throw new QueryException(e);
        }

        return new GeneratedArtifacts(name, threadpoolSize, qep, policy, ruleSession, sourceRepository,
                executorService);
    }


    @Override
    public void discard() {
        qepFactory = null;
        query = null;
    }
}
