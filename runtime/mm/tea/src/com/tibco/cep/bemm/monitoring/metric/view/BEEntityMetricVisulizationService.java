package com.tibco.cep.bemm.monitoring.metric.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.management.service.exception.ServiceInitializationException;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.monitoring.metric.view.config.Chart;
import com.tibco.cep.bemm.monitoring.metric.view.config.ChartSeries;
import com.tibco.cep.bemm.monitoring.metric.view.config.MetricsView;
import com.tibco.cep.bemm.monitoring.metric.view.config.Section;
import com.tibco.rta.common.service.StartStopService;


/**
 * @author vasharma
 *
 */
public interface BEEntityMetricVisulizationService extends StartStopService {


	/**
	 * Initializes MetricVisulization service
	 * 
	 * @throws ServiceInitializationException
	 */
	public void init(Properties configuration) throws ServiceInitializationException;
	
	
	/**
	 * Gets data for plotting/populating view
	 * @param chartId 
	 * 
	 * @throws Exception
	 */
	public Map<String, Object> getViewData(Monitorable entity,MetricsView view,String appName) throws Exception;
	
	
	/**
	 * Gets data for plotting chart
	 * 
	 * @throws Exception
	 */
	public Map<String, Object> getChartData(Chart metricView,Monitorable entity ,String appName) throws Exception;
	
	/**
	 * Get entity type's chart config
	 * 
	 * @throws Exception
	 */
	//public Map<Integer, MetricsView> getEntityViewsConfig(String entityType);
	
	
	/**
	 * Gets all charts config
	 * 
	 * @throws Exception
	 */
	public ArrayList<Section> getAllViewsConfig();
	
	
	/**
	 * Gets all charts config for BeTeaAgent
	 * 
	 * @throws Exception
	 */
	public ArrayList<Section> getAllBeTeaAgentViewsConfig();

	
	/**
	 * Gets chart data
	 * @param appName 
	 * @throws ObjectCreationException 
	 * 
	 * @throws Exception
	 */
	public ViewData getChart(Monitorable entity, int sectionId, int viewId, String dimLevel,Long threshold, String appName) throws ObjectCreationException, Exception;

}
