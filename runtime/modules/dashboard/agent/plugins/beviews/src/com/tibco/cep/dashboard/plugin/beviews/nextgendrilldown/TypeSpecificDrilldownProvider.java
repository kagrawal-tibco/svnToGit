package com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown;

import java.util.List;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.psvr.common.query.QueryExecutor;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.kernel.service.logging.Logger;

interface TypeSpecificDrilldownProvider {
	
	public QuerySpec getDrillDownQuery(Logger logger, Entity entity, String entityInstanceId, String nextInLineTypeId, QueryExecutor queryExecutor) throws QueryException;

	public List<Tuple> getNextInLines(Logger logger, Entity entity, String entityInstanceId, QueryExecutor queryExecutor) throws QueryException;
	
}