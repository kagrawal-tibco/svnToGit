/**
 *
 */
package com.tibco.cep.query.service.impl;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.api.QueryException;
import com.tibco.cep.query.exec.codegen.QueryTemplateRegistry;
import com.tibco.cep.query.model.ProjectContext;
import com.tibco.cep.query.model.impl.ProjectContextImpl;
import com.tibco.cep.query.service.QueryModelFactory;
import com.tibco.cep.query.service.QueryServiceProvider;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

/**
 * @author pdhar
 */
public class QueryServiceProviderImpl
        implements QueryServiceProvider{


    private ProjectContext projectContext;
    private QueryModelFactory modelFactory;
    private QueryTemplateRegistry templateRegistry;


    public QueryServiceProviderImpl() throws QueryException {
        try {
            initialize(RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider());
        } catch (Exception e) {
            throw new QueryException(e);
        }
    }

    public void initialize(RuleServiceProvider rsp) throws Exception {
        this.projectContext = new ProjectContextImpl(rsp);
        this.modelFactory = new QueryModelFactoryImpl(this);
        this.templateRegistry = QueryTemplateRegistry.getInstance();
        this.templateRegistry.loadTemplates("com/tibco/cep/query/exec/codegen/QueryExecutionPlanFactory.stg");
    }

    /**
     * @param rsp RuleServiceProvider
     * @throws QueryException upon problem.
     */
    public QueryServiceProviderImpl(RuleServiceProvider rsp) throws QueryException {
        try {
            initialize(rsp);
        } catch (Exception e) {
            throw new QueryException(e);
        }
    }


    /**
     * Returns the Logger
     *
     * @param c Class
     * @return Logger
     */
    public Logger getLogger(Class c) {
        return this.projectContext.getRuleServiceProvider().getLogger(c);
    }

    /**
     * Returns the Modelfactory
     *
     * @return Modelfactory
     */
    public QueryModelFactory getModelFactory() {
        return this.modelFactory;
    }

    /**
     * Returns the static template registry
     *
     * @return TemplateRegistry
     */
    public QueryTemplateRegistry getTemplateRegistry() {
        return templateRegistry;
    }

    /**
     * Returns the QuerySession
     *
     * @param sessionName String name of the session.
     * @return QuerySession associated with that name, or null if not found or not a QuerySession.
     * @throws QueryException upon problem.
     */
    public QuerySession getQuerySession(String sessionName) throws QueryException {
        if ((null == this.projectContext) || (null == this.projectContext.getRuleServiceProvider())) {
            throw new QueryException("QueryServiceProvider has not been initialized");
        }
        final RuleSession rs = this.projectContext.getRuleServiceProvider().getRuleRuntime().getRuleSession(sessionName);
        if (rs instanceof QuerySession) {
            return (QuerySession) rs;
        }
        return null;
    }

    /**
     * Returns the project context
     *
     * @return ProjectContext
     */
    public ProjectContext getProjectContext() {
        return this.projectContext;
    }

   

}
