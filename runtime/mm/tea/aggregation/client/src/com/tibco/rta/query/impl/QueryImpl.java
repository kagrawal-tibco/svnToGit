package com.tibco.rta.query.impl;

import com.tibco.rta.RtaException;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.MetricResultTuple;
import com.tibco.rta.query.Query;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.QueryByKeyDef;
import com.tibco.rta.query.QueryResultHandler;
import com.tibco.rta.query.QueryType;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 15/1/13
 * Time: 4:09 PM
 * Default implementation of Query model interface.
 */
public class QueryImpl implements Query {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    private QueryFactory queryFactory;
    
    private String id;

    /**
     * Reference to session
     */
    private DefaultRtaSession session;

    private QueryByKeyDef queryByKeyDef;

    private QueryByFilterDef queryByFilterDef;

    transient protected QueryResultHandler resultHandler;

    public QueryImpl(DefaultRtaSession session) {
        this.session = session;
        queryFactory = QueryFactory.INSTANCE;
    }
    
   
    public void setId(String id) {
        this.id = id;
    }
    

    public String getId(){
    	return id;
    }
    
    @Override
    public String getName() {
        if (queryByKeyDef != null) {
            return queryByKeyDef.getName();
        } else if (queryByFilterDef != null) {
            return queryByFilterDef.getName();
        }
        return null;
    }


    @Override
    public QueryByKeyDef newQueryByKeyDef() {
        return queryByKeyDef = queryFactory.newQueryByKeyDef();
    }

    @Override
    public QueryByFilterDef newQueryByFilterDef(String schemaName, String cubeName, String hierarchyName, String measurementName) {
        return queryByFilterDef = queryFactory.newQueryByFilterDef(schemaName, cubeName, hierarchyName, measurementName);
    }


    @Override
    public <T extends MetricResultTuple> Browser<T> execute() throws RtaException {
        //Register with server and then execute
        try {
			return session.registerQuery(this);
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Exception during registration of query %s", e, getName());
			throw new RtaException(e);
		}
    }

    public QueryByKeyDef getQueryByKeyDef() {
        return queryByKeyDef;
    }

    public QueryByFilterDef getQueryByFilterDef() {
        return queryByFilterDef;
    }

    @Override
    public void close() throws RtaException {
        //Close an existing query
        try {
            session.unregisterQuery(this);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Exception during closing of query %s", e, getName());
            throw new RtaException(e);
        }
    }

    @Override
   	public QueryResultHandler getResultHandler() {
   		return resultHandler;
   	}

   	@Override
   	public void setResultHandler(QueryResultHandler resultHandler) {
   		this.resultHandler = resultHandler;
   	}

    public QueryType getQueryType() {
        if (queryByKeyDef != null) {
            return queryByKeyDef.getQueryType();
        } else if (queryByFilterDef != null) {
            return queryByFilterDef.getQueryType();
        }
        return null;
    }

    public String getOwnerSchemaName() {
        if (queryByKeyDef != null) {
            return queryByKeyDef.getSchemaName();
        } else if (queryByFilterDef != null) {
            return queryByFilterDef.getSchemaName();
        }
        return null;
    }
}
