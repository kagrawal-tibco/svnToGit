package com.tibco.rta.service.rules.impl;

import java.util.List;
import java.util.Properties;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.impl.MetricKeyImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.TimeDimension;
import com.tibco.rta.model.impl.DimensionHierarchyImpl;
import com.tibco.rta.model.impl.TimeDimensionImpl;
import com.tibco.rta.model.impl.TimeUnitsImpl;
import com.tibco.rta.query.MetricEventType;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.QueryType;
import com.tibco.rta.query.SnapshotQueryExecutor;
import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.impl.QueryFactory;
import com.tibco.rta.query.impl.QueryFactoryEx;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.MetricNodeEvent;
import com.tibco.rta.runtime.model.MutableMetricNode;
import com.tibco.rta.runtime.model.impl.MetricNodeEventImpl;
import com.tibco.rta.service.om.ObjectManager;
import com.tibco.rta.service.rules.SnapshotRuleEvalService;
import com.tibco.rta.util.ServiceConstants;

/*
 * @author vasharma
 * 
 */

public class SnapshotRuleEvalServiceImpl implements SnapshotRuleEvalService,Runnable{

	protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_METRIC.getCategory());

	private long evalTime=30000; //default 10 seconds
	private long lastRunTime=0;
	private Thread runningThread=null;
	private boolean isRunning=true;
	private ObjectManager omService;

	@Override
	public void init(Properties configuration) throws Exception{

		//this.evalTime=evalTime;
		try{
			evalTime = Long.parseLong((String) ConfigProperty.RTA_RULE_SNAPSHOT_EVAL_FREQUENCY.getValue(configuration));
		}
		catch( NumberFormatException e){
			evalTime=30000;
		}
		this.omService = ServiceProviderManager.getInstance().getObjectManager();

	}

	@Override
	public void run() {
		try {

			LOGGER.log(Level.DEBUG, "Snapshot rule evaluation : querying for new nodes.");	
			while(isRunning){
				long newRunTime=System.currentTimeMillis();
	
				List<RtaSchema> allSchemas = ModelRegistry.INSTANCE.getAllRegistryEntries();
				for (RtaSchema schema : allSchemas) {
					for (Cube cube : schema.getCubes()) {
						for (DimensionHierarchy hierarchy : cube.getDimensionHierarchies()) {
							if (!snapShotRuleEval(hierarchy)) {
								continue;
							}
							if (DimensionHierarchyImpl.isAssetHierarchy(hierarchy)) {
								continue;
							}

							try {
								QueryDef query = queryForAllUpdatedNodes(hierarchy);
								SnapshotQueryExecutor executor = new SnapshotQueryExecutor(query);
								List<MetricNode> nodes = executor.getNextBatch();

								while (nodes.size() > 0) {
									for (MetricNode node : nodes) {
										if(node.getKey() instanceof MetricKeyImpl){                		
											Dimension dim=hierarchy.getDimension(((MetricKeyImpl)node.getKey()).getDimensionLevelName());
											if(dim instanceof TimeDimensionImpl){

												long bucketWidth=TimeUnitsImpl.getTimePeriod(((TimeDimensionImpl)dim).getTimeUnit().getTimeUnit(),((TimeDimensionImpl)dim).getMultiplier());

												if(newRunTime>(((long)((MetricKeyImpl)node.getKey()).getDimensionValue(dim.getName()))+bucketWidth)){

													MetricNodeEvent event=new MetricNodeEventImpl(node,MetricEventType.UPDATE);
													ServiceProviderManager.getInstance().getMetricsService().publishSnapshotRuleJob(event);
													((MutableMetricNode)node).setProcessed(true);
													//Saving updated node with isProcessed flag set as true
													omService.save(node);
													omService.commit(true);
												}
											}
										}
									}
									nodes = executor.getNextBatch();
								}

							} catch (Exception e) {
								LOGGER.log(Level.ERROR, "Snapshot rule evaluation : Error while fetching updated nodes.");
							}
						}
					}
				}
				this.lastRunTime=newRunTime;
				Thread.sleep(evalTime);
			}

		} catch (InterruptedException e) {
			LOGGER.log(Level.DEBUG, e.getMessage());
		}
	}

	private QueryDef queryForAllUpdatedNodes(DimensionHierarchy hierarchy) throws Exception {
		QueryFactory queryFac = QueryFactory.INSTANCE;

		QueryByFilterDef queryDef = queryFac.newQueryByFilterDef(hierarchy.getOwnerSchema().getName(), hierarchy.getOwnerCube().getName(),hierarchy.getName(), null);
		queryDef.setBatchSize(5000);
		queryDef.setName("SnapshotRuleEvalQuery");
		queryDef.setQueryType(QueryType.SNAPSHOT);

		Filter eqFilter = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.IS_PROCESSED, false);
		queryDef.setFilter(eqFilter);

		return queryDef;
	}

	@Override
	public void start (){
		isRunning=true;
		Thread t = new Thread(this, "teagent-Snapshot-rule-eval-thread");
		t.start();
		this.runningThread=t;
	}

	@Override
	public void stop(){
		isRunning=false;
		if(runningThread!=null)
			runningThread.interrupt();
	}

	private boolean snapShotRuleEval(DimensionHierarchy hierarchy) {
    	if(hierarchy!=null)
    		if(hierarchy.getProperty(ServiceConstants.HIERARCHY_SNAPSHOT_RULE_EVAL)!=null 
    		&& !hierarchy.getProperty(ServiceConstants.HIERARCHY_SNAPSHOT_RULE_EVAL).isEmpty()
    		&&hierarchy.getProperty(ServiceConstants.HIERARCHY_SNAPSHOT_RULE_EVAL).equals("true"))
    			return true;
    	return false;
    }

}
