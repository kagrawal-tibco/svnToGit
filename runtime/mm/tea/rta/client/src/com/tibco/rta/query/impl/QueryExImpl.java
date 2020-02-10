package com.tibco.rta.query.impl;

import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.query.Query;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.QueryByKeyDef;
import com.tibco.rta.query.QueryResultHandler;
import com.tibco.rta.query.QueryType;

/**
 * @author vdhumal
 * Implementation of Query model interface.
 */
public class QueryExImpl extends QueryImpl implements Query {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    private QueryFactory queryFactoryEx;
    
    private String id;

    private QueryByKeyDef queryByKeyDefEx;

    private QueryByFilterDef queryByFilterDefEx;

    transient protected QueryResultHandler resultHandler;

    public QueryExImpl(DefaultRtaSession session) {
    	super(session);
    	queryFactoryEx = QueryFactoryEx.INSTANCE;
    }
    
   
    public void setId(String id) {
        this.id = id;
    }
    

    public String getId(){
    	return id;
    }
    
    @Override
    public String getName() {
        if (queryByKeyDefEx != null) {
            return queryByKeyDefEx.getName();
        } else if (queryByFilterDefEx != null) {
            return queryByFilterDefEx.getName();
        }
        return null;
    }


    @Override
    public QueryByKeyDef newQueryByKeyDef() {
        return queryByKeyDefEx = queryFactoryEx.newQueryByKeyDef();
    }

    @Override
    public QueryByFilterDef newQueryByFilterDef(String schemaName, String cubeName, String hierarchyName, String measurementName) {
        return queryByFilterDefEx = queryFactoryEx.newQueryByFilterDef(schemaName, cubeName, hierarchyName, measurementName);
    }

    @Override
    public QueryByKeyDef getQueryByKeyDef() {
        return queryByKeyDefEx;
    }

    @Override
    public QueryByFilterDef getQueryByFilterDef() {
        return queryByFilterDefEx;
    }

    @Override
    public QueryType getQueryType() {
        if (queryByKeyDefEx != null) {
            return queryByKeyDefEx.getQueryType();
        } else if (queryByFilterDefEx != null) {
            return queryByFilterDefEx.getQueryType();
        }
        return null;
    }

    @Override
    public String getOwnerSchemaName() {
        if (queryByKeyDefEx != null) {
            return queryByKeyDefEx.getSchemaName();
        } else if (queryByFilterDefEx != null) {
            return queryByFilterDefEx.getSchemaName();
        }
        return null;
    }
}
