package com.tibco.cep.dashboard.plugin.beviews.drilldown.query;

import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.model.NextInLine;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.kernel.service.logging.Logger;

public interface TypeSpecificDrilldownProvider {
	
	QuerySpec getDrillDownQuery(Logger logger, Tuple tuple, String typeId);

	List<NextInLine> getNextInLines(Logger logger, Tuple tuple, Map<String,String> additionalParameters, DrilldownProvider drilldownProvider) throws QueryException;
	
}