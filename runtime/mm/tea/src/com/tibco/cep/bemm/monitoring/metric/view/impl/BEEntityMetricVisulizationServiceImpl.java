package com.tibco.cep.bemm.monitoring.metric.view.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.management.service.exception.ServiceInitializationException;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.monitoring.metric.config.beentitymapconfig.BeApp;
import com.tibco.cep.bemm.monitoring.metric.config.beentitymapconfig.BeEntity;
import com.tibco.cep.bemm.monitoring.metric.config.beentitymapconfig.BeEntityMap;
import com.tibco.cep.bemm.monitoring.metric.config.beentitymapconfig.Entry;
import com.tibco.cep.bemm.monitoring.metric.config.beentitymapconfig.Property;
import com.tibco.cep.bemm.monitoring.metric.view.BEEntityMetricVisulizationService;
import com.tibco.cep.bemm.monitoring.metric.view.ViewData;
import com.tibco.cep.bemm.monitoring.metric.view.config.AndFilterDef;
import com.tibco.cep.bemm.monitoring.metric.view.config.Chart;
import com.tibco.cep.bemm.monitoring.metric.view.config.ChartSeries;
import com.tibco.cep.bemm.monitoring.metric.view.config.EqFilterDef;
import com.tibco.cep.bemm.monitoring.metric.view.config.Filters;
import com.tibco.cep.bemm.monitoring.metric.view.config.MetricsView;
import com.tibco.cep.bemm.monitoring.metric.view.config.MetricsViewConfig;
import com.tibco.cep.bemm.monitoring.metric.view.config.ObjectFactory;
import com.tibco.cep.bemm.monitoring.metric.view.config.Section;
import com.tibco.cep.bemm.monitoring.util.BeTeaAgentMonitorable;
import com.tibco.cep.bemm.monitoring.util.MonitoringUtils;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.Query;
import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.impl.QueryFactoryEx;
import com.tibco.tea.agent.be.util.BETeaAgentProps;


/**
 * @author vasharma
 *
 */
public class BEEntityMetricVisulizationServiceImpl  extends AbstractStartStopServiceImpl implements BEEntityMetricVisulizationService {

	private String metricViewConfigFile=null;
	private String beEntityConfigFile=null;
	private String beTeaAgentViewConfigFile=null;
	private Map<String,Map<String,List<Entry>>> beEntityMap=new HashMap<String,Map<String,List<Entry>>>();
	private int maxSeriesCount=10;

	private static ArrayList<Section> viewRegistry = new ArrayList<Section>();
	private static ArrayList<Section> beTeaViewRegistry = new ArrayList<Section>();
	private static final String METRIC_VIEW_JAXB_PKG = "com.tibco.cep.bemm.monitoring.metric.view.config";
	private static final String BEENTITY_VIEW_JAXB_PKG = "com.tibco.cep.bemm.monitoring.metric.config.beentitymapconfig";
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BEEntityMetricVisulizationService.class);
	/**
	 * @throws Exception
	 */
	public void init(Properties configuration) throws ServiceInitializationException {
		try {
			this.metricViewConfigFile = (String) ConfigProperty.BE_TEA_AGENT_METRICS_VIEW_CONFIG_FILE.getValue(configuration);
			this.beTeaAgentViewConfigFile = (String) ConfigProperty.BE_TEA_AGENT_SELF_METRICS_VIEW_CONFIG_FILE.getValue(configuration);
			this.beEntityConfigFile   = (String) ConfigProperty.BE_TEA_AGENT_METRICS_VIEW_BEENTITY_FILE.getValue(configuration);
			
			//POPULATING VIEW REGISTRY
			File metricViewFile = new File(metricViewConfigFile);
			JAXBContext jaxbContext1 = JAXBContext.newInstance(METRIC_VIEW_JAXB_PKG, this.getClass().getClassLoader());
			Unmarshaller jaxbUnmarshaller1 = jaxbContext1.createUnmarshaller();
			if(metricViewFile!=null){
				MetricsViewConfig metricsViewConfig=(MetricsViewConfig)jaxbUnmarshaller1.unmarshal(metricViewFile);

				for(Section section : metricsViewConfig.getSection()){
					viewRegistry.add(section);
				}
			}
			
			//POPULATING SELF MONITORING VIEW REGISTRY
			File beTeaAgentMetricViewFile = new File(beTeaAgentViewConfigFile);
			if(beTeaAgentMetricViewFile!=null){
				MetricsViewConfig metricsViewConfig=(MetricsViewConfig)jaxbUnmarshaller1.unmarshal(beTeaAgentMetricViewFile);
				for(Section section : metricsViewConfig.getSection()){
					beTeaViewRegistry.add(section);
				}
			}
			
			//POPULATING BEENTITY MAP
			File beEntityFile = new File(beEntityConfigFile);
			JAXBContext jaxbContext2 = JAXBContext.newInstance(BEENTITY_VIEW_JAXB_PKG, this.getClass().getClassLoader());
			Unmarshaller jaxbUnmarshaller2 = jaxbContext2.createUnmarshaller();
			if(beEntityFile!=null){
				BeEntityMap beEntityConfig=(BeEntityMap)jaxbUnmarshaller2.unmarshal(beEntityFile);
				populateBeEntityMap(beEntityConfig);
			}
		} catch (JAXBException e) {
			try {
				LOGGER.log(Level.ERROR,BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.POPULATING_VIEW_REGISTERY_ERROR),e);
			} catch (ObjectCreationException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	private void populateBeEntityMap(BeEntityMap beEntityConfig) {
		
		if(beEntityConfig!=null){
			
			Object count=(getPropertyValue(BETeaAgentProps.PROP_CHART_MAX_SERIES,beEntityConfig.getProperties().getProperty()));
			
			if(count!=null)
				this.maxSeriesCount=(Integer)count;
			
			for(BeApp app:beEntityConfig.getApp()){
				String appName=app.getName();
				Map<String,List<Entry>> entityMap=new HashMap<String,List<Entry>>();

				for(BeEntity beEntity:app.getEntityGroup()){
					entityMap.put(beEntity.getType(), beEntity.getEntity());
				}
				beEntityMap.put(appName, entityMap);
			}
		}
	}
	
	
	@Override
	public ViewData getChart(Monitorable entity, int sectionId, int viewId, String dimLevel,Long threshold, String appName) throws ObjectCreationException, Exception {
		
		List<Chart> chartRegistry = new ArrayList<Chart>();
		if(entity instanceof BeTeaAgentMonitorable){
			for(Section section : beTeaViewRegistry){
				if(section.getSectionId() == sectionId){
					chartRegistry = section.getChart();
					break;
				}
			}
		}
		else{
			for(Section section : viewRegistry){
				if(section.getSectionId() == sectionId){
					chartRegistry = section.getChart();
					break;
				}
			}
		}
		MetricsView metricView=null;
		for(Chart chart : chartRegistry){
			if(chart.getId() == viewId){
				 Object clone= MonitoringUtils.clone(chart);	
				 if(clone!=null)
					 metricView=(MetricsView) clone;
				break;
			}
		}
		if(metricView!=null && metricView instanceof Chart ){
			Chart chart=(Chart)metricView;
			if(chart.isIsBucketed()){
				if(threshold!=null && threshold>0)
					chart.setThreshold(threshold);
				List<ChartSeries> newSeries=new ArrayList<ChartSeries>();
				List<ChartSeries> oldSeries=new ArrayList<ChartSeries>();
				for(ChartSeries series:chart.getSeries()){
					if(dimLevel!=null && !dimLevel.isEmpty())
						series.setDimLevel(dimLevel);
					if("multiple".equals(series.getType())){
						String dim=series.getDim();
						List<Property> dimList=getDimList(dim,entity,metricView.getEntity(),series,appName);
						Collections.sort(dimList,new PropertyNameComparator());
						if(dimList!=null && dimList.size()>0){
							newSeries.addAll(getSeriesPerDimensionVlaue(series,dimList,dim));
						}
						oldSeries.add(series);
					}
				}
				
				if(newSeries.size()>0){
					chart.getSeries().removeAll(oldSeries);
					chart.getSeries().addAll(newSeries);
				}
			}
		}
		Map<String, Object> data=getViewData(entity,metricView,appName);
		
		ViewData view=new ViewData();
		view.setView(metricView);
		view.setQueryData(data);
		
		return view;
	}
	
	
	@Override
	public Map<String, Object> getViewData(Monitorable entity,MetricsView view, String appName) throws Exception {
		
		return getChartData((Chart)view,entity,appName);

	}
	private List<ChartSeries> getSeriesPerDimensionVlaue(ChartSeries series, List<Property> dimList, String dim) {

		List<ChartSeries> newSeriesList=new ArrayList<ChartSeries>();
		for(Property val : dimList){
			ChartSeries newSeries=(ChartSeries) MonitoringUtils.clone(series);
			AndFilterDef andFilter=new AndFilterDef();
			
			EqFilterDef eq=new EqFilterDef();
			eq.setDatatype("STRING");
			eq.setName(dim);
			eq.setQualifier("FilterKeyQualifier");
			eq.setValue(val.getName());
			eq.setQualifier("DIMENSION_NAME");
			
			andFilter.getEqFilter().add(eq);
			Filters filters =new Filters();
			filters.getAndFilter().add(andFilter);
			newSeries.setFilters(filters);
			newSeries.setType("single");
			newSeries.setDisplayName(val.getValue());
			newSeries.setName(val.getValue());
			newSeriesList.add(newSeries);
		}
		return newSeriesList;
	}
	private List<Property> getDimList(String dim, Monitorable entity,String entityDimName, ChartSeries series, String appName) throws ObjectCreationException, Exception {

		Query query= BEMMServiceProviderManager.getInstance().getMetricProviderService().initSeriesQuery(entity,entityDimName,"",series,null,false,appName);

		ArrayList<String> metrics=new ArrayList<String>();
		HashSet<String> queryList=new HashSet<String>();

		List<Property> filteredList=new ArrayList<Property>();

		boolean notPresentFlag=false;

		metrics.add(dim);
		ArrayList<HashMap<String, Object>> result=BEMMServiceProviderManager.getInstance().getMetricProviderService().getQueryData((Query)query,metrics);

		for(HashMap<String,Object> value : result ){
			if(value.get(dim)!=null ){
				queryList.add((String)value.get(dim));
			}
		}

		Map<String, List<Entry>> entityMap=beEntityMap.get(appName);

		if(queryList.size()>0){
			if(entityMap!=null){
				List<Entry> entries=entityMap.get(dim);

				if(entries!=null){
					for(Entry entry:entries){
						Property prop=new Property();
						for(String queryProp :queryList){
							if(queryProp.equals(entry.getName())){
								prop.setName(entry.getName());
								prop.setValue(entry.getName());
								if(entry.getAlias()!=null&&!entry.getAlias().isEmpty()){
									prop.setValue(entry.getAlias());
								}
								filteredList.add(prop);
								break;
							}
						}
					}
				}
				else
					notPresentFlag=true;
			}
			else
				notPresentFlag=true;
		}

		if(notPresentFlag==true){
			for(String queryProp :queryList){
				Property prop=new Property();
				prop.setName(queryProp);
				prop.setValue(queryProp);
				filteredList.add(prop);
			}
			if(filteredList.size()>maxSeriesCount){
				filteredList=(List<Property>) filteredList.subList(0, maxSeriesCount);
			}
		}

		return filteredList;
	}
	@Override
	public Map<String, Object> getChartData(Chart metricView,Monitorable entity, String appName) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		for(ChartSeries series:metricView.getSeries())
		{	
			if("multiple".equals(series.getType()))
				continue;
			
			try {
				ArrayList<String> chartMetrics=new ArrayList<String>();
				if(series.getXAttribute()!=null&&!"".equals(series.getXAttribute().trim()))
					chartMetrics.add(series.getXAttribute());
				if(series.getYAttribute()!=null&&!"".equals(series.getYAttribute().trim()))
					chartMetrics.add(series.getYAttribute());
				Object query=null;
				JAXBElement<Object> queryJaxb = series.getQuery();
				
				if(queryJaxb==null||queryJaxb.toString().indexOf("query: null")>0||queryJaxb.getValue()==null) {						
					Filter timeFilter=null;
					
					//Adding time filter
					if(metricView.isIsBucketed()) {
						
						if( metricView.getThreshold()!=null && metricView.getThreshold()>0){
							
							Long time= System.currentTimeMillis()-(metricView.getThreshold())*1000;
							
							if(entity instanceof ServiceInstance){
								Long startTime=((ServiceInstance)entity).getStartingTime();
								
								if(time<startTime)
									time=startTime;
							}
							else if(entity instanceof Agent){
								Long startTime=((Agent)entity).getInstance().getStartingTime();
								
								if(time<startTime)
									time=startTime;
							}
							else if(entity instanceof BeTeaAgentMonitorable){
								Long startTime=((BeTeaAgentMonitorable)entity).getStartingTime();
								
								if(time<startTime)
									time=startTime;
							}
							timeFilter = QueryFactoryEx.INSTANCE.newGtFilter(MetricQualifier.CREATED_TIME,new Timestamp(time));
						}
					}
					
					query=BEMMServiceProviderManager.getInstance().getMetricProviderService().initSeriesQuery(entity,metricView.getEntity(),"",series,timeFilter,metricView.isIsBucketed(),appName);

					ObjectFactory factory=new ObjectFactory();
					
					if(!(metricView.isIsBucketed()))
						series.setQuery(factory.createChartSeriesQuery(query));
				}
				
				if(!(metricView.isIsBucketed()||"multiple".equals(series.getType())))
					query=series.getQuery().getValue();
				
				if(query!=null){
					List<HashMap<String, Object>> resultList=BEMMServiceProviderManager.getInstance()
							.getMetricProviderService()
							.getQueryData((Query)query,chartMetrics);
					
					if(metricView.getMaxDataPoints()!=null && resultList!=null && resultList.size()>metricView.getMaxDataPoints()){
						if(metricView.getChartType().equals("column") || metricView.getChartType().equals("bar")){
							resultList=(List<HashMap<String, Object>>) resultList.subList(0, metricView.getMaxDataPoints());
						}
					}
					map.put(series.getName(),resultList);
				}
			} catch (Exception e) {
				LOGGER.log(Level.ERROR,BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.GETTING_DATA_FOR_CHART_SERIES_ERROR, series.getName()),e);
			}
		}
		return map;
	}
	
	/*@Override
	public Map<Integer, MetricsView> getEntityViewsConfig(String entityType)
	{	
		Map<Integer,MetricsView> entityViewRegistry=new HashMap<Integer,MetricsView>();
		for(Map.Entry<Integer, MetricsView> view :viewRegistry.entrySet()) {
			if(entityType!=null&&!entityType.isEmpty()) {
				if(view.getValue().getEntity().equals(entityType)) {
					entityViewRegistry.put(view.getKey(),view.getValue());
				}
			}
		}
		return entityViewRegistry;
	}*/

	@Override
	public ArrayList<Section> getAllViewsConfig()
	{
		return viewRegistry;
	}
	
	@Override
	public ArrayList<Section> getAllBeTeaAgentViewsConfig()
	{
		return beTeaViewRegistry;
	}
	
	public Object getPropertyValue(String propertyName, List<Property> propList)
	{
		for(Property prop : propList) {
			if(prop!=null)
				if(prop.getName().equals(propertyName)){
					return MonitoringUtils.parseValue(prop.getValue(),prop.getType());
				}
		}
		return null;
	}
	
	public class PropertyNameComparator implements Comparator<Property>
	{
	    public int compare(Property p1, Property p2)
	    {
	       return p1.getName().compareTo(p2.getName());
	   }
	}
	

}

