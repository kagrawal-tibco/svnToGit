/**
 *
 */
package com.tibco.cep.query.service;

import com.tibco.cep.query.api.QueryException;
import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.impl.rete.service.ReteEntityChangeListener;
import com.tibco.cep.runtime.session.RuleSession;

/**
 * @author pdhar
 */
public interface QuerySession {
    public static final String QUERY_ID_PREFIX = "Q_";

    public static final String QUERY_LOG_FILE_EXTN = ".jar";

    /**
     * creates a Query object for a given oql
     *
     * @param name
     * @param oql  @return Query
     * @throws com.tibco.cep.query.api.QueryException
     *
     */
    Query createQuery(String name, String oql) throws QueryException;

    /**
     * @return Rulesession the session associated with the Query
     */
    RuleSession getRuleSession();

    /**
     * @return QueryServiceProvider the parent QueryServicerProvider
     */
    QueryServiceProvider getQueryServiceProvider() throws QueryException;


    /**
     * Gets the name of the region identifying the agent / session.
     *
     * @return String name of the region.
     */
    String getRegionName();

    void setChangeListener(ReteEntityChangeListener changeListener);

    void setPrimaryCache(Cache primaryCache);

    void setSharedObjectSourceRepository(SharedObjectSourceRepository sosRepository);
}
