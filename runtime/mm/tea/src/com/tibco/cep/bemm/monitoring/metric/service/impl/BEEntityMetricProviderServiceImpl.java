package com.tibco.cep.bemm.monitoring.metric.service
.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.cep.bemm.monitoring.metric.service.BEEntityMetricProviderService;
import com.tibco.cep.bemm.monitoring.metric.view.config.AndFilterDef;
import com.tibco.cep.bemm.monitoring.metric.view.config.ChartSeries;
import com.tibco.cep.bemm.monitoring.metric.view.config.FilterDef;
import com.tibco.cep.bemm.monitoring.metric.view.config.OrFilterDef;
import com.tibco.cep.bemm.monitoring.util.BeTeaAgentMonitorable;
import com.tibco.cep.bemm.monitoring.util.MonitoringUtils;
import com.tibco.rta.MetricKey;
import com.tibco.rta.RtaSession;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.MetricResultTuple;
import com.tibco.rta.query.Query;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.QueryType;
import com.tibco.rta.query.SortOrder;
import com.tibco.rta.query.filter.AndFilter;
import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.filter.OrFilter;
import com.tibco.rta.query.impl.MetricResultTupleImpl;
import com.tibco.rta.query.impl.QueryFactory;
import com.tibco.rta.query.impl.QueryFactoryEx;
import com.tibco.tea.agent.be.util.BETeaAgentProps;

/*
 * @author vasharma
 */
public class BEEntityMetricProviderServiceImpl implements BEEntityMetricProviderService {
	Query alertsQuery=null;
	

	@Override
	public Query initSeriesQuery(Monitorable entity, String entityName,String queryMetric, ChartSeries series, Filter timeFilter,boolean isBucketed, String appName) throws Exception
	{	
		Query query=null;
		RtaSession session=BEMMServiceProviderManager.getInstance().getAggregationService().getSession();
		
		String schema="";
		if(series.getSchema() !=null )
			schema=series.getSchema().getValue();
		else
			schema=BETeaAgentProps.BEMM_FACT_SCHEMA;
		
		if(session!=null){
			query = session.createQuery();
			QueryByFilterDef queryDef =query.newQueryByFilterDef(schema,series.getCube(),series.getHierarchy(),queryMetric);
			queryDef.setName("GetChartDataQuery_"+System.currentTimeMillis());
			queryDef.setQueryType(QueryType.SNAPSHOT);
			queryDef.setBatchSize(1);
			
			//Setting ordering
			if(series.getOrderBy()!=null && series.getOrderBy().getValue()!=null && !series.getOrderBy().getValue().isEmpty()){
				String orderByMetric=series.getOrderBy().getValue();
				queryDef.addOrderByTuple(FilterKeyQualifier.MEASUREMENT_NAME,orderByMetric);
				
				if(series.getSortOrder()!=null && series.getSortOrder().getValue()!=null && !series.getSortOrder().getValue().isEmpty()){
					SortOrder sortOrder=SortOrder.DESCENDING.name().equalsIgnoreCase(series.getSortOrder().getValue())?SortOrder.DESCENDING:SortOrder.ASCENDING;
					queryDef.setSortOrder(sortOrder);
				}
			}
			else if(isBucketed){
				queryDef.addOrderByTuple(MetricQualifier.UPDATED_TIME);
				queryDef.setSortOrder(SortOrder.ASCENDING);
			}
			else {
				queryDef.addOrderByTuple(MetricQualifier.CREATED_TIME);
				queryDef.setSortOrder(SortOrder.DESCENDING);
			}			
			if(entity instanceof BeTeaAgentMonitorable){
				setFiltersForBeTeaAgent(series,queryDef,entityName,entity,appName,timeFilter);
			}
			else{
				setFilters(series,queryDef,entityName,entity,appName,timeFilter);
			}
		}
		return query;
	}

	private void setFilters(ChartSeries series, QueryByFilterDef queryDef, String entityName, Monitorable entity,String appName, Filter timeFilter) {
		if(series.getFilters()!=null&&series.getFilters().getAndFilter().size()>0) {

			Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, series.getDimLevel());
			Filter eqFilter2 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME,entityName, entity.getName());
			Filter instanceFilter = null;
			if(entity instanceof Agent){
				instanceFilter = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME,"instance", ((Agent)entity).getInstance().getName());
			}
			
			Filter appFilter = QueryFactory.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, MetricAttribute.DIM_APP,appName);
			
			AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
			andFilter.addFilter(eqFilter1, eqFilter2,appFilter);
			
			if(timeFilter!=null)
				andFilter.addFilter(timeFilter);
			
			if(instanceFilter!=null){
				andFilter.addFilter(instanceFilter);
			}

			for(AndFilterDef andFilterDef:series.getFilters().getAndFilter()) {
				Filter filter=null;

				for(FilterDef filterDef : andFilterDef.getEqFilter()) {
					if("MetricQualifier".equals(filterDef.getQualifier())){
						filter = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.valueOf(filterDef.getQualifier()), MonitoringUtils.parseValue(filterDef.getValue(),filterDef.getDatatype()));
						andFilter.addFilter(filter);
					}
					else{
						filter = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.valueOf(filterDef.getQualifier()), filterDef.getName(),MonitoringUtils.parseValue(filterDef.getValue(),filterDef.getDatatype()));
						andFilter.addFilter(filter);
					}
				}
				for(FilterDef filterDef : andFilterDef.getGtFilter()) {
					filter = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.valueOf(filterDef.getQualifier()), filterDef.getName(),MonitoringUtils.parseValue(filterDef.getValue(),filterDef.getDatatype()));
					andFilter.addFilter(filter);
				}
				for(FilterDef filterDef : andFilterDef.getLtFilter()) {
					filter = QueryFactoryEx.INSTANCE.newLtFilter(FilterKeyQualifier.valueOf(filterDef.getQualifier()), filterDef.getName(),MonitoringUtils.parseValue(filterDef.getValue(),filterDef.getDatatype()));
					andFilter.addFilter(filter);
				}
			}
			queryDef.setFilter(andFilter);
		}
		else {	
			Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, series.getDimLevel());
			Filter eqFilter2 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME,entityName, entity.getName());
			Filter instanceFilter = null;
			if(entity instanceof Agent){
				instanceFilter = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME,"instance", ((Agent)entity).getInstance().getName());
			}
			Filter appFilter = QueryFactory.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, MetricAttribute.DIM_APP,appName);
			
			AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
			andFilter.addFilter(eqFilter1, eqFilter2,appFilter);
			if(timeFilter!=null)
				andFilter.addFilter(timeFilter);
			
			if(instanceFilter!=null){
				andFilter.addFilter(instanceFilter);
			}
			
			queryDef.setFilter(andFilter);
		}
		if(series.getFilters()!=null)
		{
			//Setting OR Filters
			for(OrFilterDef orFilterDef:series.getFilters().getOrFilter())
			{	
				OrFilter orFilter = QueryFactoryEx.INSTANCE.newOrFilter();
				Filter filter=null;

				for(FilterDef filterDef : orFilterDef.getEqFilter()){
					if("MetricQualifier".equals(filterDef.getQualifier())){
						filter = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.valueOf(filterDef.getQualifier()), MonitoringUtils.parseValue(filterDef.getValue(),filterDef.getDatatype()));
						orFilter.addFilter(filter);
					}
					else{
						filter = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.valueOf(filterDef.getQualifier()), filterDef.getName(),MonitoringUtils.parseValue(filterDef.getValue(),filterDef.getDatatype()));
						orFilter.addFilter(filter);
					}
				}
				for(FilterDef filterDef : orFilterDef.getGtFilter())
				{
					filter = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.valueOf(filterDef.getQualifier()), filterDef.getName(),MonitoringUtils.parseValue(filterDef.getValue(),filterDef.getDatatype()));
					orFilter.addFilter(filter);
				}
				for(FilterDef filterDef : orFilterDef.getLtFilter())
				{
					filter = QueryFactoryEx.INSTANCE.newLtFilter(FilterKeyQualifier.valueOf(filterDef.getQualifier()), filterDef.getName(),MonitoringUtils.parseValue(filterDef.getValue(),filterDef.getDatatype()));
					orFilter.addFilter(filter);
				}

				queryDef.setFilter(orFilter);
			}
		}
	}
		
	private void setFiltersForBeTeaAgent(ChartSeries series, QueryByFilterDef queryDef, String entityName,
			Monitorable entity,String appName, Filter timeFilter) {
		
		if(series.getFilters()!=null&&series.getFilters().getAndFilter().size()>0) {
			Filter eqFilter = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, series.getDimLevel());
			Filter appFilter = QueryFactory.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, MetricAttribute.DIM_SELF_AGENT,appName);
			
			AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
			andFilter.addFilter(eqFilter,appFilter);
			
			if(timeFilter!=null)
				andFilter.addFilter(timeFilter);
			
			for(AndFilterDef andFilterDef:series.getFilters().getAndFilter()) {
				Filter filter=null;

				for(FilterDef filterDef : andFilterDef.getEqFilter()) {
					if("MetricQualifier".equals(filterDef.getQualifier())){
						filter = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.valueOf(filterDef.getQualifier()), MonitoringUtils.parseValue(filterDef.getValue(),filterDef.getDatatype()));
						andFilter.addFilter(filter);
					}
					else{
						filter = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.valueOf(filterDef.getQualifier()), filterDef.getName(),MonitoringUtils.parseValue(filterDef.getValue(),filterDef.getDatatype()));
						andFilter.addFilter(filter);
					}
				}
				for(FilterDef filterDef : andFilterDef.getGtFilter()) {
					filter = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.valueOf(filterDef.getQualifier()), filterDef.getName(),MonitoringUtils.parseValue(filterDef.getValue(),filterDef.getDatatype()));
					andFilter.addFilter(filter);
				}
				for(FilterDef filterDef : andFilterDef.getLtFilter()) {
					filter = QueryFactoryEx.INSTANCE.newLtFilter(FilterKeyQualifier.valueOf(filterDef.getQualifier()), filterDef.getName(),MonitoringUtils.parseValue(filterDef.getValue(),filterDef.getDatatype()));
					andFilter.addFilter(filter);
				}
			}
			queryDef.setFilter(andFilter);
		}
		else {	
			Filter eqFilter = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, series.getDimLevel());
			Filter appFilter = QueryFactory.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, MetricAttribute.DIM_SELF_AGENT,appName);
			
			AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
			andFilter.addFilter(eqFilter,appFilter);
			
			if(timeFilter!=null)
				andFilter.addFilter(timeFilter);
			
			queryDef.setFilter(andFilter);
		}
		if(series.getFilters()!=null){

			//Setting OR Filters
			for(OrFilterDef orFilterDef:series.getFilters().getOrFilter())
			{	
				OrFilter orFilter = QueryFactoryEx.INSTANCE.newOrFilter();
				Filter filter=null;

				for(FilterDef filterDef : orFilterDef.getEqFilter()){
					if("MetricQualifier".equals(filterDef.getQualifier())){
						filter = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.valueOf(filterDef.getQualifier()), MonitoringUtils.parseValue(filterDef.getValue(),filterDef.getDatatype()));
						orFilter.addFilter(filter);
					}
					else{
						filter = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.valueOf(filterDef.getQualifier()), filterDef.getName(),MonitoringUtils.parseValue(filterDef.getValue(),filterDef.getDatatype()));
						orFilter.addFilter(filter);
					}
				}
				for(FilterDef filterDef : orFilterDef.getGtFilter())
				{
					filter = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.valueOf(filterDef.getQualifier()), filterDef.getName(),MonitoringUtils.parseValue(filterDef.getValue(),filterDef.getDatatype()));
					orFilter.addFilter(filter);
				}
				for(FilterDef filterDef : orFilterDef.getLtFilter())
				{
					filter = QueryFactoryEx.INSTANCE.newLtFilter(FilterKeyQualifier.valueOf(filterDef.getQualifier()), filterDef.getName(),MonitoringUtils.parseValue(filterDef.getValue(),filterDef.getDatatype()));
					orFilter.addFilter(filter);
				}

				queryDef.setFilter(orFilter);
			}
		}
	}

	@Override
	public ArrayList<HashMap<String, Object>> getQueryData(Query query,ArrayList<String> chartMetrics) throws Exception {
		ArrayList<HashMap<String,Object>> resultList=new ArrayList<HashMap<String,Object>>();

		if(query!=null)
		{	
			Browser<MetricResultTuple> browser = (Browser<MetricResultTuple>) query.execute();
			resultList=processMetricResults(browser,chartMetrics);
			query.close();
		}
		return resultList;
	}

	private ArrayList<HashMap<String, Object>> processMetricResults(Browser<MetricResultTuple> browser,ArrayList<String> chartMetrics) throws Exception {
		ArrayList<HashMap<String, Object>> list=new ArrayList<HashMap<String, Object>>();
		
		while (browser.hasNext()) {
			HashMap<String,Object> metricValueMap=new HashMap<String,Object>();
			MetricKey metricKey=null;
			MetricResultTuple rs = browser.next();
			if(rs instanceof MetricResultTupleImpl) {
				metricKey=((MetricResultTupleImpl)rs).getMetricKey();
				metricValueMap.put(MetricAttribute.ATTR_UPDATED_TIME, ((MetricResultTupleImpl)rs).getUpdatedTime());
				metricValueMap.put(MetricAttribute.ATTR_CREATED_TIME, ((MetricResultTupleImpl)rs).getCreatedTime());
			}
			for(String metricName :chartMetrics ) {
				@SuppressWarnings("unchecked")
				SingleValueMetric<Object> metric = (SingleValueMetric<Object>) rs.getMetric(metricName);
				if(metric!=null&&metric.getValue()!=null)
					metricValueMap.put(metricName, metric.getValue());
				else if((metric==null||metric.getValue()==null)&&MetricAttribute.VIEW_ATTR_CUSTOM_TIME.equalsIgnoreCase(metricName))
					metricValueMap.put(metricName, System.currentTimeMillis());
				else if((metric==null || metric.getValue()==null)&&metricKey!=null) {
					Object value=metricKey.getDimensionValue(metricName);
					if(value!=null)
						metricValueMap.put(metricName, value);
				}
			}
			list.add(metricValueMap);
		}
		return list;
	}


	private ArrayList<Map<String, Object>> processAlertResults(Browser<MetricResultTuple> browser,ArrayList<String> chartMetrics) throws Exception {
		ArrayList<Map<String,Object>> alerts=new ArrayList<Map<String,Object>>();
		
		while (browser.hasNext()) {
			MetricKey metricKey=null;
			MetricResultTuple rs = browser.next();
			Map<String,Object> metricValueMap=new HashMap<String,Object>();
			if(rs instanceof MetricResultTupleImpl) {
				metricKey=((MetricResultTupleImpl)rs).getMetricKey();
				metricValueMap.put(MetricAttribute.ATTR_UPDATED_TIME, ((MetricResultTupleImpl)rs).getUpdatedTime());
				metricValueMap.put(MetricAttribute.ATTR_CREATED_TIME, ((MetricResultTupleImpl)rs).getCreatedTime());
			}
			for(String metricName :chartMetrics )
			{	
				@SuppressWarnings("unchecked")
				SingleValueMetric<Object> metric = (SingleValueMetric<Object>) rs.getMetric(metricName);
				if(metric!=null&&metric.getValue()!=null)
					metricValueMap.put(metricName, metric.getValue());
				else if((metric==null || metric.getValue()==null)&&metricKey!=null) {
					Object value=metricKey.getDimensionValue(metricName);
					if(value!=null)
						metricValueMap.put(metricName, value);
				}
			}
			metricValueMap.put("SetEntityName",MonitoringUtils.getEntityFromNodeNode((String)metricValueMap.get("SetNode"),false));
			metricValueMap.put("SetEntityType",MonitoringUtils.getEntityFromNodeNode((String)metricValueMap.get("SetNode"),true));
			metricValueMap.put("ClearEntityName",MonitoringUtils.getEntityFromNodeNode((String)metricValueMap.get("ClearNode"),false));
			metricValueMap.put("ClearEntityType",MonitoringUtils.getEntityFromNodeNode((String)metricValueMap.get("ClearNode"),true));
			alerts.add(metricValueMap);
		}
		return alerts;
	}


	@Override
	public ArrayList<Map<String, Object>> getAlerts(ArrayList<String> chartMetrics, String appName,boolean isAdmin,String currentUser) throws Exception {	
		ArrayList<Map<String,Object>> alerts=new ArrayList<Map<String,Object>>();

		//if(alertsQuery==null)
		initAlertsQuery(appName,isAdmin,currentUser);

		if(alertsQuery!=null)
		{	
			Browser<MetricResultTuple> browser = (Browser<MetricResultTuple>) alertsQuery.execute();
			alerts=processAlertResults(browser,chartMetrics);
			alertsQuery.close();
		}
		return alerts;
	}

	public void initAlertsQuery(String appName, boolean isAdmin,String currentUser) throws Exception
	{	
		RtaSession session=BEMMServiceProviderManager.getInstance().getAggregationService().getSession();
		if(session!=null){
			alertsQuery = session.createQuery();
			QueryByFilterDef queryDef =alertsQuery.newQueryByFilterDef(MetricAttribute.ALERTS_SCHEMA,MetricAttribute.ALERTS_CUBE,MetricAttribute.ALERTS_HIERARCHY,MetricAttribute.ALERTS_MEASUREMENT_ALERTTEXT);
			queryDef.setName("GetAlerts");
			queryDef.setQueryType(QueryType.SNAPSHOT);
			Filter eqFilter1= QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.MEASUREMENT_NAME, MetricAttribute.ALERTS_MEASUREMENT_ISALERTCLEARED,false);
			Filter eqFilter2= QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, MetricAttribute.ALERTS_DIM_APP,appName);
			OrFilter orFilter=null;
			if(!isAdmin){
				orFilter=QueryFactoryEx.INSTANCE.newOrFilter();
				
				Filter adminFilter1= QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.MEASUREMENT_NAME, MetricAttribute.ALERTS_MEASUREMENT_ISADMIN,true);
				Filter adminFilter2= QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, MetricAttribute.ALERTS_DIM_USERNAME,currentUser);
				orFilter.addFilter(adminFilter1,adminFilter2);
			}

			AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
			if(orFilter!=null)
				andFilter.addFilter(eqFilter1, eqFilter2,orFilter);
			else
				andFilter.addFilter(eqFilter1, eqFilter2);
			queryDef.setFilter(andFilter);
			queryDef.setBatchSize(1);
		}
	}

	@Override
	public String clearAlerts(List<String> alertIds) throws Exception {
		MessageService messageService=BEMMServiceProviderManager.getInstance().getMessageService();

		RtaSession session=BEMMServiceProviderManager.getInstance().getAggregationService().getSession();
		if(session!=null){
				session.clearAlerts(alertIds);
		}
		return messageService.getMessage(MessageKey.ALERTS_CLEARED_SUCCESS_MESSAGE);
	}
}






