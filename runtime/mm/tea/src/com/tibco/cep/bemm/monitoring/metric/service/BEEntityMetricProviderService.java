package com.tibco.cep.bemm.monitoring.metric.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.monitoring.metric.view.config.ChartSeries;
import com.tibco.cep.bemm.monitoring.metric.view.config.Filters;
import com.tibco.rta.query.Query;
import com.tibco.rta.query.filter.Filter;

public interface BEEntityMetricProviderService {
	
	public ArrayList<HashMap<String, Object>> getQueryData(Query query,ArrayList<String> chartMetrics) throws Exception;
	
	public Query initSeriesQuery(Monitorable entity, String entityName,String queryMetric, ChartSeries series, Filter timeFilter,boolean isBucketed, String appName) throws Exception;
	
	public ArrayList<Map<String, Object>> getAlerts(ArrayList<String> chartMetrics, String appName, boolean isAdmin,String userName) throws Exception;

	public String clearAlerts(List<String> alertIds) throws Exception;
	
}
