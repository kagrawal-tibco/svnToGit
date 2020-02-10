package com.tibco.cep.query.rule;

import javassist.ClassPool;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.api.QueryException;
import com.tibco.cep.query.exec.codegen.QueryExecutionClassHelper;
import com.tibco.cep.query.service.QueryStatement;
import com.tibco.cep.query.service.impl.QueryImpl;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA. User: pdhar Date: Jul 8, 2007 Time: 9:07:50 PM To
 * change this template use File | Settings | File Templates.
 */
public interface QueryRuleFactory {
    /**
     * @param impl
     * @param stmt
     * @return QueryRule the rule created by this factory
     * @throws QueryException
     */

    QueryExecutionClassHelper createQueryRule(QueryImpl impl, QueryStatement stmt)
            throws QueryException;

    /**
     * @return ClassPool
     */
    ClassPool getClassPool();

    RuleServiceProvider getRuleServiceProvider();

    Logger getLogger();
}
