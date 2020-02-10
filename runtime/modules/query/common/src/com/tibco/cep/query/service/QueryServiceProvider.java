/**
 * 
 */
package com.tibco.cep.query.service;


import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.api.QueryException;
import com.tibco.cep.query.model.ProjectContext;

/**
 * @author pdhar
 */
public interface QueryServiceProvider {


    /**
     * @return QueryService the query service using the current session from RulesessionManager.getCurrentSessuion()
     * @throws com.tibco.cep.query.api.QueryException
     */
    QuerySession getQuerySession(String ruleSession) throws QueryException;


    /**
     * Gets the ProjectContext of this QSP.
     *
     * @return ProjectContext
     */
    ProjectContext getProjectContext();


    /**
     * @return TestModelFactory the model factory
     */
    public QueryModelFactory getModelFactory() throws QueryException;


    /**
     *  Returns the logger
     * @param c Class
     * @return Logger
     */
    Logger getLogger(Class c);


}
