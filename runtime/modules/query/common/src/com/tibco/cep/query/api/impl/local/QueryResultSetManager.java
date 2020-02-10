package com.tibco.cep.query.api.impl.local;

import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;

/*
* Author: Ashwin Jayaprakash Date: Mar 21, 2008 Time: 7:55:35 PM
*/
interface QueryResultSetManager {
    void unregisterAndStopQuery(ReteQuery query) throws Exception;
}
