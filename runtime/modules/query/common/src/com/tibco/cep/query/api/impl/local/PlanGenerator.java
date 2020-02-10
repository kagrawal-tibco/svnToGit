package com.tibco.cep.query.api.impl.local;

import java.util.List;
import java.util.concurrent.ExecutorService;

import com.tibco.cep.query.api.QueryException;
import com.tibco.cep.query.api.QueryPolicy;
import com.tibco.cep.query.exec.descriptors.QueryExecutionPlanDescriptor;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.runtime.session.RuleSession;

/*
* Author: Karthikeyan Subramanian / Date: Jun 29, 2010 / Time: 10:54:55 AM
*/
public interface PlanGenerator {
    String getQueryText();

    List<String> getColumnNames();

    List<String> getColumnTypeNames();

    List<String> getSourceNames();

    QueryExecutionPlanDescriptor getQueryExecutionPlanDescriptor();

    GeneratedArtifacts generateInstance(
            String name,
            QueryPolicy policy, RuleSession ruleSession,
            SharedObjectSourceRepository sourceRepository,
            ExecutorService executorService)
            throws QueryException;

    void discard();
}
