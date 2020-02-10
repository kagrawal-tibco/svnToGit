package com.tibco.rta.test;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.validation.constraints.AssertTrue;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ibm.icu.math.BigDecimal;
import com.tibco.rta.Fact;
import com.tibco.rta.Metric;
import com.tibco.rta.MetricKey;
import com.tibco.rta.RtaException;
import com.tibco.rta.RtaSession;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.model.DataType;
import com.tibco.rta.model.rule.RuleDef;
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
import com.tibco.rta.query.impl.QueryFactory;
import com.tibco.rta.query.impl.QueryFactoryEx;
import com.tibco.rta.service.persistence.memory.InMemoryConstant;
import com.tibco.rta.service.rules.RuleService;
import com.tibco.rta.test.publish.TestFacts;
import com.tibco.rta.test.publish.TestQuery;
import com.tibco.rta.test.publish.TestRule;
import com.tibco.store.persistence.model.invm.InMemoryTable;
import com.tibco.store.persistence.model.invm.impl.InMemoryDataStore;
import com.tibco.store.persistence.service.invm.DataServiceFactory;
import com.tibco.store.persistence.service.invm.InMemoryDataStoreService;

import static org.junit.Assert.*;

public class AggTestClass1 {
	
	private RtaSession session;
	
	@Before
	public void setup()
	{
		
		try{
			session = AggtestSuite.initSession();
		}
		catch(Exception e){
			System.out.println("Could not start/get session properly");
		}
	}
	
	@After
	public void teardown()
	{
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			session.close();
		} catch (RtaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void startTest()
	{
		
		//case1();
		//case2();
		//case3();
		//case4();
		//case5();
		//case6();
		//case7();
		//case8();
		case9();
		
		//caseBugFix1();
		/*try {
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export1.csv");
		} catch (Exception e1) {
			e1.printStackTrace();
		}*/
		
		/*try {
			TestFacts.testInstanceHealthFact(session, 0);
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}*/
		
		/*testRule();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}*/
		
		//caseSnapshotQuery();
		
		//TestUtilities.displayInMemTables();
		
		/*try {
			TestFacts.testInstanceHealthFact1(session, 0);
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}*/
		
	//	caseSnapshotQuery();
		
	}
	
	
	
	/**
	 * publish fact 
	 * publish fact
	 * snapshotquery
	 */
	private void case1()
	{
		/*try {
				TestFacts.testInstanceHealthFact(session, 0);
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
		}
		
		try {
			TestFacts.testInstanceHealthFact1(session, 0);
			Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
		}*/
		
		caseSnapshotQuery1();
		
	}
	
	/**
	 * publish fact from export2.csv
	 * chk for health metric :- 
	 * 			App level - instanceisactive, agentcount, instancecount, instancepercetactive
	 * 			
	 * 
	 */
	
	private void case2()
	{
		try {
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export2.csv");
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		caseSnapshotQuery2();
	}
	
	private void caseBugFix1()
	{
		try {
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/bugfix1.csv");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//publish rule
		QueryFactory qfac = QueryFactoryEx.INSTANCE;
		QueryByFilterDef setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_INSTANCE_PERCENT_OK_HEALTH);
		
			qfac = QueryFactoryEx.INSTANCE;
			setCondition.setName("SetCondition4_5");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(0);
	        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        Filter eqFilter2 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_INSTANCE_PERCENT_OK_HEALTH, 50L);
	        AndFilter andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2);
	        setCondition.setFilter(andFilter1);
	        
	        try{
	        	TestRule.createRule(session, setCondition, null, "Rule2000_1", 2001);
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        
	        //insert fact to trigger rule
	        try{
		        Fact fact = TestFacts.getFactInstance(session);
		        TestFacts.insertAttToFact(fact, TestConstants.ATTRIB_APPLICATION, "Application-3");
		        TestFacts.insertAttToFact(fact, TestConstants.ATTRIB_PU_NAME, "cache");
				TestFacts.insertAttToFact(fact, TestConstants.ATTRIB_INSTANCE_NAME, "CacheServer");
				TestFacts.insertAttToFact(fact, TestConstants.ATTRIB_AGENT_TYPE, "cache");
				TestFacts.insertAttToFact(fact, TestConstants.ATTRIB_AGENT_NAME, "cache-class2");
				TestFacts.insertAttToFact(fact, TestConstants.ATTRIB_INSTANCE_HEALTH, "warning");
				TestFacts.publishFact(session, fact);
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	}
	
	//test for purging of data
	private void case9()
	{
		/*
		 * To test this case, below changes needs to be done.
		 * 
		 * 1. In DatabasePersistenceService.java (for database)
		 * 		setMetricValueToUpdateStatement function - replace this 
		 * 						//pst.setTimestamp(index++, new Timestamp(metricNode.getLastModifiedTime()));
         *						long timestamp = (long)((SingleValueMetric)metricNode.getMetric("timestamp")).getValue();
         *						pst.setTimestamp(index++,  new Timestamp(timestamp ));
         *
         *		setMetricValueToInsertStatement function replace this
         *						//pst.setTimestamp(index++, ts);
         *						long timestamp = (long)((SingleValueMetric)metricNode.getMetric("timestamp")).getValue();
         *						pst.setTimestamp(index++,  new Timestamp(timestamp ));
         *
         *		setInsertFactStatementValue
         *						//pst.setTimestamp(index++, ts);
         *						pst.setTimestamp(index++,  new Timestamp( (long)fact.getAttribute("timestamp")));
         *
         * 2. In SPMDataServiceAdapterFacade.java (for in mem)
         * 		saveMetricNode
         * 						//tuple.setAttribute(InMemoryConstant.UPDATED_DATE_TIME_FIELD, new Timestamp(System.currentTimeMillis()));		
		 *						tuple.setAttribute(InMemoryConstant.UPDATED_DATE_TIME_FIELD, new Timestamp((long)tuple.getAttributeValue("timestamp")));
		 *
		 *		saveFact
		 *						tuple.setAttribute(InMemoryConstant.CREATED_DATE_TIME_FIELD, new Timestamp((long)tuple.getAttributeValue("timestamp")));
		 *						//tuple.setAttribute(InMemoryConstant.CREATED_DATE_TIME_FIELD, new Timestamp(System.currentTimeMillis()));
		 *
		 * 3. In the csv file used in this case - make last row timestamp value equal to System.currentTimeMillis()
		
		 * 
		 */
		
		
		//publish set of facts from csv
		try {
			//for 9_1
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export10.csv");
			Thread.sleep(5000);

			try { //delaying engine stop until purge
				Thread.sleep(2*70*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			snapshotQuery9_1(1);
			
			
			//start fresh by commenting upper section
			/*TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export10_1.csv");
			Thread.sleep(5000);
			
			
			try { //delaying engine stop until purge
				Thread.sleep(2*70*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			snapshotQuery9_2(1);*/
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		try { //delaying engine stop until purge
			Thread.sleep(2*70*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//check instance health at instance level
	// check for purging few hours of data. In this case  104 hours
		private void snapshotQuery9_1(int i) 
		{
			System.out.println("starting snapshotQuery9_1");
			
			try 
			{
				Thread.sleep(1000);
		        final Query query = session.createQuery();
		
		        QueryByFilterDef queryDef = query.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_INSTANCE_HEALTH);
		        queryDef.setName("SnapshotQueryDef9_1");
		        queryDef.setQueryType(QueryType.SNAPSHOT);
		        queryDef.setBatchSize(5);
		
		        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newGtFilter(MetricQualifier.CREATED_TIME, new Timestamp(System.currentTimeMillis() - (1000*60*60*104) ));
		        //Filter eqFilter2 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, "minutes");
		        Filter eqFilter3 = QueryFactoryEx.INSTANCE.newGtFilter(MetricQualifier.DIMENSION_LEVEL_NO, 2);
		        
		        AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
		        andFilter.addFilter(eqFilter1);
		        queryDef.setFilter(andFilter);
		        queryDef.addOrderByTuple(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT);
		        
		        Browser<MetricResultTuple> browser = query.execute();
		        processMetricResults9_1(browser,i, 1);
		        query.close();
			} catch (Exception e) {
				e.printStackTrace();
			}   
			
		}
		
		//check instance health at instance level
		// check for purging few hours of data. In this case  104 hours
		private void snapshotQuery9_2(int i) 
		{
			System.out.println("starting snapshotQuery9_1");
			
			try 
			{
				Thread.sleep(1000);
		        final Query query = session.createQuery();
		
		        QueryByFilterDef queryDef = query.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_INSTANCE_HEALTH);
		        queryDef.setName("SnapshotQueryDef9_1");
		        queryDef.setQueryType(QueryType.SNAPSHOT);
		        queryDef.setBatchSize(5);
		
		        int time_1min = 1000*60;
		        int time_1hour = time_1min * 60;
		        int time_1day = time_1hour * 24;
		        int time_1week = time_1day * 7;
		        
		        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newGtFilter(MetricQualifier.CREATED_TIME, new Timestamp(System.currentTimeMillis() - (time_1week) ));
		        //Filter eqFilter2 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, "minutes");
		        Filter eqFilter3 = QueryFactoryEx.INSTANCE.newGtFilter(MetricQualifier.DIMENSION_LEVEL_NO, 2);
		        
		        AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
		        andFilter.addFilter(eqFilter1/*, eqFilter2*/);
		        queryDef.setFilter(andFilter);
		        queryDef.addOrderByTuple(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT);
		        
		        Browser<MetricResultTuple> browser = query.execute();
		        processMetricResults9_1(browser,i, 1);
		        query.close();
			} catch (Exception e) {
				e.printStackTrace();
			}   
			
		}
		
		private void processMetricResults9_1(Browser<MetricResultTuple> browser, int i, int queryNumber) {

			int count =0;
			while (browser.hasNext()) {
				count++;
	            MetricResultTuple rs = browser.next();
	            Map<String, Metric> metricMap = rs.getMetrics();
	            
	            for(Map.Entry<String, Metric> entry : metricMap.entrySet()){
	            	MetricKey key = null;
	            	Metric metric = entry.getValue();
	            	
	            	if(metric instanceof SingleValueMetric){
	            		key = (MetricKey) (metric.getKey());
	            	}

	            	Object metricVal = ((SingleValueMetric)metric).getValue();
	            	String measurementName = metric.getDescriptor().getMeasurementName();
	            	
	            	if(measurementName.equalsIgnoreCase("deadlockedthreadcount")){
	            		System.out.println(metricVal.toString());
	            		assertEquals(39, metricVal);
	            	}
	            	
	            }
	        }
				assertEquals(5, count);
		}
	
	/**
	 * Pulish facts from csv 9
	 * publish 2 rules
	 * 		Rule 1 : instancehealthhierarchy - if tottalrulesfired>=50 and <=70 and total thread count >= 50 set app health as warning clear to ok if both less than 50
	 * 		Rule 2 : instancehealthhierarchy - if tottalrulesfired>70 and threadocunt >=50 set app health as critical clear if both less than 50 
	 * 
	 * 		asserts for app health
	 * 
	 */
	private void case8()
	{
		//publish set of facts from csv
		try {
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export9.csv");
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//publish rule
		publishRule8_1();
		
		//query and chk asserts for set
		snapshotQuery8_1(1);
		
		
		//for set condition 
		try {
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export9_1.csv");
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		snapshotQuery8_1(2);
		
		
		//for set condition 
		try {
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export9_2.csv");
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		snapshotQuery8_1(3);
		
		
	}
	
	//check instance health at instance level
	private void snapshotQuery8_1(int i) 
	{
		System.out.println("starting snapshotQuery8_1");
		
		try 
		{
			Thread.sleep(1000);
	        final Query query = session.createQuery();
	
	        QueryByFilterDef queryDef = query.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_INSTANCE_HEALTH);
	        queryDef.setName("SnapshotQueryDef8_1");
	        queryDef.setQueryType(QueryType.SNAPSHOT);
	        queryDef.setBatchSize(5);
	
	        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_INSTANCE);
	        
	        
	        AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter.addFilter(eqFilter1);
	        queryDef.setFilter(andFilter);
	        
	        Browser<MetricResultTuple> browser = query.execute();
	        processMetricResults8_1(browser,i, 1);
	        query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}   
		
	}
	
	//check instancepercent ok, warn, crit health at app level
	private void snapshotQuery8_2(int i) 
	{
		System.out.println("starting snapshotQuery8_1");
		
		try 
		{
			Thread.sleep(1000);
	        final Query query = session.createQuery();
	
	        QueryByFilterDef queryDef = query.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_INSTANCE_PERCENT_OK_HEALTH);
	        queryDef.setName("SnapshotQueryDef8_1");
	        queryDef.setQueryType(QueryType.SNAPSHOT);
	        queryDef.setBatchSize(5);
	
	        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        
	        
	        AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter.addFilter(eqFilter1);
	        queryDef.setFilter(andFilter);
	        
	        Browser<MetricResultTuple> browser = query.execute();
	        processMetricResults8_1(browser,i, 2);
	        query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}   
	}
	
	//check total rules fired and thread count at app level in clusterhelthhierarchy
	private void snapshotQuery8_3(int i) 
	{
		System.out.println("starting snapshotQuery8_1");
		
		try 
		{
			Thread.sleep(1000);
	        final Query query = session.createQuery();
	
	        QueryByFilterDef queryDef = query.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_THREAD_COUNT);
	        queryDef.setName("SnapshotQueryDef8_1");
	        queryDef.setQueryType(QueryType.SNAPSHOT);
	        queryDef.setBatchSize(5);
	
	        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        
	        
	        AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter.addFilter(eqFilter1);
	        queryDef.setFilter(andFilter);
	        
	        Browser<MetricResultTuple> browser = query.execute();
	        processMetricResults8_1(browser,i, 3);
	        query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}   
		
	}
	
	// i=1,2,3 = initial fact publish, after set condition facts, after clear condition facts
		private void processMetricResults8_1(Browser<MetricResultTuple> browser, int i, int queryNumber) {

			while (browser.hasNext()) {
	            MetricResultTuple rs = browser.next();
	            Map<String, Metric> metricMap = rs.getMetrics();
	            
	            for(Map.Entry<String, Metric> entry : metricMap.entrySet()){
	            	MetricKey key = null;
	            	Metric metric = entry.getValue();
	            	
	            	if(metric instanceof SingleValueMetric){
	            		key = (MetricKey) (metric.getKey());
	            	}

	            	Object metricVal = ((SingleValueMetric)metric).getValue();
	            	String measurementName = metric.getDescriptor().getMeasurementName();
	            	
	            	if(queryNumber == 1){
	            		if( (metricVal != null) && (TestConstants.MEASUREMENT_INSTANCE_HEALTH.equalsIgnoreCase(measurementName)) ){
	            			if(i==1){////
	            				if( (key.getDimensionValue("instancehealth").equals("InferenceAgent")) && (key.getDimensionValue("app").equals("FraudDetection")) ){
		            				assertEquals(33L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent1")) && (key.getDimensionValue("app").equals("FraudDetection")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent2")) && (key.getDimensionValue("app").equals("FraudDetection")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("CacheServer")) && (key.getDimensionValue("app").equals("FraudDetection")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent")) && (key.getDimensionValue("app").equals("Application-2")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent")) && (key.getDimensionValue("app").equals("Application-3")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("CacheServer")) && (key.getDimensionValue("app").equals("Application-3")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("CacheServer-2")) && (key.getDimensionValue("app").equals("Application-3")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent-3")) && (key.getDimensionValue("app").equals("Application-3")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceServer")) && (key.getDimensionValue("app").equals("Application-3")) ){
		            				assertEquals(0L, metricVal);
		            			}
	            			}else if(i==2){/////
	            				if( (key.getDimensionValue("instancehealth").equals("InferenceAgent")) && (key.getDimensionValue("app").equals("FraudDetection")) ){
		            				assertEquals(33L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent1")) && (key.getDimensionValue("app").equals("FraudDetection")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent2")) && (key.getDimensionValue("app").equals("FraudDetection")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("CacheServer")) && (key.getDimensionValue("app").equals("FraudDetection")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent")) && (key.getDimensionValue("app").equals("Application-2")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent")) && (key.getDimensionValue("app").equals("Application-3")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("CacheServer")) && (key.getDimensionValue("app").equals("Application-3")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("CacheServer-2")) && (key.getDimensionValue("app").equals("Application-3")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent-3")) && (key.getDimensionValue("app").equals("Application-3")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceServer")) && (key.getDimensionValue("app").equals("Application-3")) ){
		            				assertEquals(0L, metricVal);
		            			}
	            			}else if(i==3){/////
	            				if( (key.getDimensionValue("instancehealth").equals("InferenceAgent")) && (key.getDimensionValue("app").equals("FraudDetection")) ){
		            				assertEquals(33L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent1")) && (key.getDimensionValue("app").equals("FraudDetection")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent2")) && (key.getDimensionValue("app").equals("FraudDetection")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("CacheServer")) && (key.getDimensionValue("app").equals("FraudDetection")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent")) && (key.getDimensionValue("app").equals("Application-2")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent")) && (key.getDimensionValue("app").equals("Application-3")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("CacheServer")) && (key.getDimensionValue("app").equals("Application-3")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("CacheServer-2")) && (key.getDimensionValue("app").equals("Application-3")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent-3")) && (key.getDimensionValue("app").equals("Application-3")) ){
		            				assertEquals(0L, metricVal);
		            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceServer")) && (key.getDimensionValue("app").equals("Application-3")) ){
		            				assertEquals(0L, metricVal);
		            			}
	            			}
	            		}
	            	}else if(queryNumber == 2){

	            		if( (metricVal != null) && (TestConstants.MEASUREMENT_INSTANCE_PERCENT_OK_HEALTH.equalsIgnoreCase(measurementName)) ){
	            			if(i==1){////
	            				assertEquals(100L, metricVal);
	            			}else if(i==2){/////
	            				if(key.getDimensionValue("app").equals("FraudDetection") ){
	            					assertEquals(0L, metricVal);
		            			}else if(key.getDimensionValue("app").equals("Application-2") ){
		            				assertEquals(0L, metricVal);
		            			}else if(key.getDimensionValue("app").equals("Application-3") ){
		            				assertEquals(0L, metricVal);
		            			}
	            			}else if(i==3){/////
	            				if(key.getDimensionValue("app").equals("FraudDetection") ){
	            					assertEquals(0L, metricVal);
		            			}else if(key.getDimensionValue("app").equals("Application-2") ){
		            				assertEquals(0L, metricVal);
		            			}else if(key.getDimensionValue("app").equals("Application-3") ){
		            				assertEquals(0L, metricVal);
		            			}
	            			}
	            		}else if( (metricVal != null) && (TestConstants.MEASUREMENT_INSTANCE_PERCENT_WARN_HEALTH.equalsIgnoreCase(measurementName)) ){
	            			if(i==1){////
	            				assertEquals(0L, metricVal);
	            			}else if(i==2){/////
	            				if(key.getDimensionValue("app").equals("FraudDetection") ){
	            					assertEquals(0L, metricVal);
		            			}else if(key.getDimensionValue("app").equals("Application-2") ){
		            				assertEquals(0L, metricVal);
		            			}else if(key.getDimensionValue("app").equals("Application-3") ){
		            				assertEquals(0L, metricVal);
		            			}
	            			}else if(i==3){/////
	            				if(key.getDimensionValue("app").equals("FraudDetection") ){
	            					assertEquals(0L, metricVal);
		            			}else if(key.getDimensionValue("app").equals("Application-2") ){
		            				assertEquals(0L, metricVal);
		            			}else if(key.getDimensionValue("app").equals("Application-3") ){
		            				assertEquals(0L, metricVal);
		            			}
	            			}
	            		}else if( (metricVal != null) && (TestConstants.MEASUREMENT_INSTANCE_PERCENT_CRIT_HEALTH.equalsIgnoreCase(measurementName)) ){
	            			if(i==1){////
	            				assertEquals(0L, metricVal);
	            			}else if(i==2){/////
	            				if(key.getDimensionValue("app").equals("FraudDetection") ){
	            					assertEquals(0L, metricVal);
		            			}else if(key.getDimensionValue("app").equals("Application-2") ){
		            				assertEquals(0L, metricVal);
		            			}else if(key.getDimensionValue("app").equals("Application-3") ){
		            				assertEquals(0L, metricVal);
		            			}
	            			}else if(i==3){/////
	            				if(key.getDimensionValue("app").equals("FraudDetection") ){
	            					assertEquals(0L, metricVal);
		            			}else if(key.getDimensionValue("app").equals("Application-2") ){
		            				assertEquals(0L, metricVal);
		            			}else if(key.getDimensionValue("app").equals("Application-3") ){
		            				assertEquals(0L, metricVal);
		            			}
	            			}
	            		}
	            	}else if(queryNumber == 3){
	            		
	            	}
	            	
	            	if( (metricVal != null) && (TestConstants.MEASUREMENT_INSTANCE_PERCENT_OK_HEALTH.equalsIgnoreCase(measurementName)) ){
	            		if(i==1){
	            			assertEquals(100L, metricVal);
	            		}else if(i==2){
	            			if( (key.getDimensionValue("instancehealth").equals("InferenceAgent")) && (key.getDimensionValue("app").equals("FraudDetection")) ){
	            				assertEquals(33L, metricVal);
	            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent1")) && (key.getDimensionValue("app").equals("FraudDetection")) ){
	            				assertEquals(0L, metricVal);
	            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent2")) && (key.getDimensionValue("app").equals("FraudDetection")) ){
	            				assertEquals(0L, metricVal);
	            			}else if( (key.getDimensionValue("instancehealth").equals("CacheServer")) && (key.getDimensionValue("app").equals("FraudDetection")) ){
	            				assertEquals(0L, metricVal);
	            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent")) && (key.getDimensionValue("app").equals("Application-2")) ){
	            				assertEquals(0L, metricVal);
	            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent")) && (key.getDimensionValue("app").equals("Application-3")) ){
	            				assertEquals(0L, metricVal);
	            			}else if( (key.getDimensionValue("instancehealth").equals("CacheServer")) && (key.getDimensionValue("app").equals("Application-3")) ){
	            				assertEquals(0L, metricVal);
	            			}else if( (key.getDimensionValue("instancehealth").equals("CacheServer-2")) && (key.getDimensionValue("app").equals("Application-3")) ){
	            				assertEquals(0L, metricVal);
	            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceAgent-3")) && (key.getDimensionValue("app").equals("Application-3")) ){
	            				assertEquals(0L, metricVal);
	            			}else if( (key.getDimensionValue("instancehealth").equals("InferenceServer")) && (key.getDimensionValue("app").equals("Application-3")) ){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else{
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(55L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(75L, metricVal);
	            			}
	            		}
	            	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_INSTANCE_PERCENT_WARN_HEALTH.equalsIgnoreCase(measurementName)) ){
	            		if(i==1){
	            			assertEquals(0L, metricVal);
	            		}else if(i==2){
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(44L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else{
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(33L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}
	            	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_INSTANCE_PERCENT_CRIT_HEALTH.equalsIgnoreCase(measurementName)) ){
	            		if(i==1){
	            			assertEquals(0L, metricVal);
	            		}else if(i==2){
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(22L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else{
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(11L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}
	            	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_APP_HEALTH.equalsIgnoreCase(measurementName)) ){
	            		if(i==1){
	            			assertEquals("ok", metricVal);
	            		}else if(i==2){
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("warning", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("critical", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("ok", metricVal);
	            			}
	            		}else{
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("warning", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("warning", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("ok", metricVal);
	            			}
	            		}
	            	}
	            }
	        }
			
		}
	
	
	private void publishRule8_1() 
	{
		try{
			QueryFactory qfac = QueryFactoryEx.INSTANCE;
			QueryByFilterDef setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_TOTAL_NO_OF_RULES_FIRED);
			setCondition.setName("SetCondition8_1");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        
	        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_INSTANCE);
	        Filter eqFilter2 = QueryFactoryEx.INSTANCE.newGEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_TOTAL_NO_OF_RULES_FIRED, 50.0);
	        Filter eqFilter3 = QueryFactoryEx.INSTANCE.newLEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_TOTAL_NO_OF_RULES_FIRED, 70.0);
	        Filter eqFilter4 = QueryFactoryEx.INSTANCE.newGEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_THREAD_COUNT, 50);
	        
	        AndFilter andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2, eqFilter3, eqFilter4);
	        setCondition.setFilter(andFilter1);
	        
	        QueryByFilterDef clearCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_TOTAL_NO_OF_RULES_FIRED);
	        clearCondition.setName("ClearCondition8_1");
	        clearCondition.setQueryType(QueryType.STREAMING);
	        clearCondition.setBatchSize(6);
	        Filter eqFilter5 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_INSTANCE);
	        Filter eqFilter6 = QueryFactoryEx.INSTANCE.newLtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_TOTAL_NO_OF_RULES_FIRED, 50.0);
	        Filter eqFilter7 = QueryFactoryEx.INSTANCE.newLtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_THREAD_COUNT, 50);
	        
	        AndFilter andFilter2 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter2.addFilter(eqFilter5, eqFilter6, eqFilter7);
	        clearCondition.setFilter(andFilter2);
	        
	        TestRule.createRule(session, setCondition, clearCondition, "Rule8_1", 8);
	        
	       ///////////
	        
	        qfac = QueryFactoryEx.INSTANCE;
	        setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_TOTAL_NO_OF_RULES_FIRED);
			setCondition.setName("SetCondition8_2");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_INSTANCE);
	        eqFilter2 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_TOTAL_NO_OF_RULES_FIRED, 70.0);
	        eqFilter3 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_THREAD_COUNT, 50);
	        andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2, eqFilter3);
	        setCondition.setFilter(andFilter1);
	        
	        TestRule.createRule(session, setCondition, null, "Rule8_2", 8);
	       
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Pulish facts from csv 7
	 * publish 2 rules
	 * 		Rule 1 : instancehealthhierarchy - if deadlockthreadcount at agent level - 10<=DTC<=20 set agent health as warning clear if DTC<10 to ok
	 * 		Rule 2 : instancehealthhierarchy - if deadlockthreadcount at agent level - DTC>20 set agent health as critical 
	 * 		Rule 3 : cluterhealthhierarchy - warning app health rule
	 * 		Rule 4 : cluterhealthhierarchy - critical app health rule
	 * 		Rule 5 : cluterhealthhierarchy - ok app health rule
	 * 
	 * 		setting agent health triggers and sets  percentok, percentwarn, percentcrit metrics which triggers app health rule which should be checked in asserts
	 * 
	 */
	private void case7()
	{
		//publish set of facts from csv
		try {
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export8.csv");
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//publish rule
		publishRule7_1();
		
		//query and chk asserts for set
		snapshotQuery7_1(1);
		
		
		//for set condition 
		try {
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export8_1.csv");
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		snapshotQuery7_1(2);
		
		
		//for set condition 
		try {
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export8_2.csv");
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		snapshotQuery7_1(3);
		
		
	}
	
	
	private void snapshotQuery7_1(int i) 
	{
		System.out.println("starting snapshotQuery7_1");
		
		try 
		{
			Thread.sleep(1000);
	        final Query query = session.createQuery();
	
	        QueryByFilterDef queryDef = query.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_AGENT_PERCENT_OK_HEALTH);
	        queryDef.setName("SnapshotQueryDef7_1");
	        queryDef.setQueryType(QueryType.SNAPSHOT);
	        queryDef.setBatchSize(5);
	
	        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        
	       // Filter eqFilter2 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_INSTANCE_PERCENT_OK_HEALTH, 100);
	        
	        AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter.addFilter(eqFilter1/*, eqFilter2*/);
	        queryDef.setFilter(andFilter);
	        
	        Browser<MetricResultTuple> browser = query.execute();
	        processMetricResults7_1(browser,i);
	        query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}   
		
	}
	
	// i=1,2,3 = initial fact publish, after set condition facts, after clear condition facts
		private void processMetricResults7_1(Browser<MetricResultTuple> browser, int i) {

			while (browser.hasNext()) {
	            MetricResultTuple rs = browser.next();
	            Map<String, Metric> metricMap = rs.getMetrics();
	            
	            for(Map.Entry<String, Metric> entry : metricMap.entrySet()){
	            	MetricKey key = null;
	            	Metric metric = entry.getValue();
	            	
	            	if(metric instanceof SingleValueMetric){
	            		key = (MetricKey) (metric.getKey());
	            	}

	            	Object metricVal = ((SingleValueMetric)metric).getValue();
	            	String measurementName = metric.getDescriptor().getMeasurementName();
	            	
	            	if( (metricVal != null) && (TestConstants.MEASUREMENT_AGENT_PERCENT_OK_HEALTH.equalsIgnoreCase(measurementName)) ){
	            		if(i==1){
	            			assertEquals(100L, metricVal);
	            		}else if(i==2){
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(33L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(50L, metricVal);
	            			}
	            		}else{
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(55L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(75L, metricVal);
	            			}
	            		}
	            	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_AGENT_PERCENT_WARN_HEALTH.equalsIgnoreCase(measurementName)) ){
	            		if(i==1){
	            			assertEquals(0L, metricVal);
	            		}else if(i==2){
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(44L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else{
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(33L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}
	            	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_AGENT_PERCENT_CRIT_HEALTH.equalsIgnoreCase(measurementName)) ){
	            		if(i==1){
	            			assertEquals(0L, metricVal);
	            		}else if(i==2){
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(22L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else{
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(11L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}
	            	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_APP_HEALTH.equalsIgnoreCase(measurementName)) ){
	            		if(i==1){
	            			assertEquals("ok", metricVal);
	            		}else if(i==2){
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("warning", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("critical", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("ok", metricVal);
	            			}
	            		}else{
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("warning", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("warning", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("ok", metricVal);
	            			}
	            		}
	            	}
	            }
	        }
			
		}
	
	

	/**
	 * cheking deadlock thread count at agent level and setting agent health in action
	 */
	private void publishRule7_1() 
	{
		try{
			QueryFactory qfac = QueryFactoryEx.INSTANCE;
			QueryByFilterDef setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT);
			setCondition.setName("SetCondition7_1");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        
	        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_AGENT);
	        Filter eqFilter2 = QueryFactoryEx.INSTANCE.newGEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT, 10);
	        Filter eqFilter3 = QueryFactoryEx.INSTANCE.newLEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT, 20);
	        
	        AndFilter andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2, eqFilter3);
	        setCondition.setFilter(andFilter1);
	        
	        QueryByFilterDef clearCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT);
	        clearCondition.setName("ClearCondition7_1");
	        clearCondition.setQueryType(QueryType.STREAMING);
	        clearCondition.setBatchSize(6);
	        Filter eqFilter4 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_AGENT);
	        Filter eqFilter5 = QueryFactoryEx.INSTANCE.newLtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT, 10);
	        
	        AndFilter andFilter2 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter2.addFilter(eqFilter4, eqFilter5);
	        clearCondition.setFilter(andFilter2);
	        
	        TestRule.createRule(session, setCondition, clearCondition, "Rule7_1", 7);
	        
	       ///////////
	        
	        qfac = QueryFactoryEx.INSTANCE;
	        setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT);
			setCondition.setName("SetCondition7_2");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_AGENT);
	        eqFilter2 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT, 20);
	        andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2);
	        setCondition.setFilter(andFilter1);
	        
	        TestRule.createRule(session, setCondition, null, "Rule7_2", 7);
	        
	        
	        /***************************************************************************/
	        
	        qfac = QueryFactoryEx.INSTANCE;
	        setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_AGENT_PERCENT_WARN_HEALTH);
			setCondition.setName("SetCondition7_3");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        
	        eqFilter2 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_AGENT_PERCENT_WARN_HEALTH, 30L);
	        eqFilter3 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_AGENT_PERCENT_CRIT_HEALTH, 20L);
	        
	        eqFilter4 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_AGENT_PERCENT_WARN_HEALTH, 50L);
	        
	        
	        andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter2,eqFilter3);
	        
	        andFilter2 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter2.addFilter(eqFilter1, andFilter1);
	        
	        AndFilter andFilter3 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter3.addFilter(eqFilter1, eqFilter4);
	        
	        OrFilter orFilter1 = QueryFactoryEx.INSTANCE.newOrFilter();
	        orFilter1.addFilter(andFilter2, andFilter3);
	        setCondition.setFilter(orFilter1);
	        
	        TestRule.createRule(session, setCondition, null, "Rule7_3", 7);
	        
	        //
	        
	        qfac = QueryFactoryEx.INSTANCE;
	        setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_AGENT_PERCENT_CRIT_HEALTH);
			setCondition.setName("SetCondition7_4");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        eqFilter2 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_AGENT_PERCENT_CRIT_HEALTH, 50L);
	        andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2);
	        setCondition.setFilter(andFilter1);
	        
	        TestRule.createRule(session, setCondition, null, "Rule7_4", 7);
	        
	        //
	        
	        qfac = QueryFactoryEx.INSTANCE;
	        setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_AGENT_PERCENT_OK_HEALTH);
			setCondition.setName("SetCondition7_5");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        eqFilter2 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_AGENT_PERCENT_OK_HEALTH, 50L);
	        andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2);
	        setCondition.setFilter(andFilter1);
	        
	        TestRule.createRule(session, setCondition, null, "Rule7_5", 7);
	       
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Pulish facts from csv 7
	 * publish 5 rules
	 * 		Rule 1 : instancehealthhierarchy - if deadlockthreadcount at agent level - 10<=DTC<=20 set agent health as warning clear if DTC<10 to ok
	 * 		Rule 2 : instancehealthhierarchy - if deadlockthreadcount at agent level - DTC>20 set agent health as critical 
	 * 		Rule 3 : cluterhealthhierarchy - if agentpercentwarnhealth >50 set app health as warning
	 * 		Rule 4 : cluterhealthhierarchy - if agentpercentcrithealth >50 set app health as critical
	 * 		Rule 5 : cluterhealthhierarchy - if agentpercentokhealth >50 set app health as ok
	 * 
	 * 		setting agent health triggers and sets  percentok, percentwarn, percentcrit metrics which triggers app health rule which should be checked in asserts
	 * 
	 */
	private void case6()
	{
		//publish set of facts from csv
		try {
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export7.csv");
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//publish rule
		publishRule6_1();
		
		//query and chk asserts for set
		snapshotQuery6_1(1);
		
		
		//for set condition 
		try {
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export7_1.csv");
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		snapshotQuery6_1(2);
		
		
		//for set condition 
		try {
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export7_2.csv");
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		snapshotQuery6_1(3);
		
		
	}
	
	
	private void snapshotQuery6_1(int i) 
	{
		System.out.println("starting snapshotQuery6_1");
		
		try 
		{
			Thread.sleep(1000);
	        final Query query = session.createQuery();
	
	        QueryByFilterDef queryDef = query.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_AGENT_PERCENT_OK_HEALTH);
	        queryDef.setName("SnapshotQueryDef6_1");
	        queryDef.setQueryType(QueryType.SNAPSHOT);
	        queryDef.setBatchSize(5);
	
	        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        
	       // Filter eqFilter2 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_INSTANCE_PERCENT_OK_HEALTH, 100);
	        
	        AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter.addFilter(eqFilter1/*, eqFilter2*/);
	        queryDef.setFilter(andFilter);
	        
	        Browser<MetricResultTuple> browser = query.execute();
	        processMetricResults6_1(browser,i);
	        query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}   
		
	}
	
	// i=1,2,3 = initial fact publish, after set condition facts, after clear condition facts
		private void processMetricResults6_1(Browser<MetricResultTuple> browser, int i) {

			while (browser.hasNext()) {
	            MetricResultTuple rs = browser.next();
	            Map<String, Metric> metricMap = rs.getMetrics();
	            
	            for(Map.Entry<String, Metric> entry : metricMap.entrySet()){
	            	MetricKey key = null;
	            	Metric metric = entry.getValue();
	            	
	            	if(metric instanceof SingleValueMetric){
	            		key = (MetricKey) (metric.getKey());
	            	}

	            	Object metricVal = ((SingleValueMetric)metric).getValue();
	            	String measurementName = metric.getDescriptor().getMeasurementName();
	            	
	            	if( (metricVal != null) && (TestConstants.MEASUREMENT_AGENT_PERCENT_OK_HEALTH.equalsIgnoreCase(measurementName)) ){
	            		if(i==1){
	            			assertEquals(100L, metricVal);
	            		}else if(i==2){
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(33L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(50L, metricVal);
	            			}
	            		}else{
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(55L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(75L, metricVal);
	            			}
	            		}
	            	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_AGENT_PERCENT_WARN_HEALTH.equalsIgnoreCase(measurementName)) ){
	            		if(i==1){
	            			assertEquals(0L, metricVal);
	            		}else if(i==2){
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(44L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else{
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(33L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}
	            	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_AGENT_PERCENT_CRIT_HEALTH.equalsIgnoreCase(measurementName)) ){
	            		if(i==1){
	            			assertEquals(0L, metricVal);
	            		}else if(i==2){
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(22L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else{
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(11L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}
	            	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_APP_HEALTH.equalsIgnoreCase(measurementName)) ){
	            		if(i==1){
	            			assertEquals("ok", metricVal);
	            		}else if(i==2){
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("critical", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("ok", metricVal);
	            			}
	            		}else{
	            			if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("warning", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("ok", metricVal);
	            			}
	            		}
	            	}
	            }
	        }
			
		}
	
	

	/**
	 * cheking deadlock thread count at agent level and setting agent health in action
	 */
	private void publishRule6_1() 
	{
		try{
			QueryFactory qfac = QueryFactoryEx.INSTANCE;
			QueryByFilterDef setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT);
			setCondition.setName("SetCondition6_1");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        
	        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_AGENT);
	        Filter eqFilter2 = QueryFactoryEx.INSTANCE.newGEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT, 10);
	        Filter eqFilter3 = QueryFactoryEx.INSTANCE.newLEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT, 20);
	        
	        AndFilter andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2, eqFilter3);
	        setCondition.setFilter(andFilter1);
	        
	        QueryByFilterDef clearCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT);
	        clearCondition.setName("ClearCondition6_1");
	        clearCondition.setQueryType(QueryType.STREAMING);
	        clearCondition.setBatchSize(6);
	        Filter eqFilter4 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_AGENT);
	        Filter eqFilter5 = QueryFactoryEx.INSTANCE.newLtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT, 10);
	        
	        AndFilter andFilter2 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter2.addFilter(eqFilter4, eqFilter5);
	        clearCondition.setFilter(andFilter2);
	        
	        TestRule.createRule(session, setCondition, clearCondition, "Rule6_1", 6);
	        
	       ///////////
	        
	        qfac = QueryFactoryEx.INSTANCE;
	        setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT);
			setCondition.setName("SetCondition6_2");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_AGENT);
	        eqFilter2 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT, 20);
	        andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2);
	        setCondition.setFilter(andFilter1);
	        
	        TestRule.createRule(session, setCondition, null, "Rule6_2", 6);
	        
	        
	        /***************************************************************************/
	        
	        qfac = QueryFactoryEx.INSTANCE;
	        setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_AGENT_PERCENT_WARN_HEALTH);
			setCondition.setName("SetCondition6_3");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        eqFilter2 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_AGENT_PERCENT_WARN_HEALTH, 50L);
	        andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2);
	        setCondition.setFilter(andFilter1);
	        
	        TestRule.createRule(session, setCondition, null, "Rule6_3", 6);
	        
	        //
	        
	        qfac = QueryFactoryEx.INSTANCE;
	        setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_AGENT_PERCENT_CRIT_HEALTH);
			setCondition.setName("SetCondition6_4");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        eqFilter2 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_AGENT_PERCENT_CRIT_HEALTH, 50L);
	        andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2);
	        setCondition.setFilter(andFilter1);
	        
	        TestRule.createRule(session, setCondition, null, "Rule6_4", 6);
	        
//
	        
	        qfac = QueryFactoryEx.INSTANCE;
	        setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_AGENT_PERCENT_OK_HEALTH);
			setCondition.setName("SetCondition6_5");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        eqFilter2 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_AGENT_PERCENT_OK_HEALTH, 50L);
	        andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2);
	        setCondition.setFilter(andFilter1);
	        
	        TestRule.createRule(session, setCondition, null, "Rule6_5", 6);
	       
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Pulish facts from csv 6
	 * publish 2 rules
	 * 		Rule 1 : instancehealthhierarchy - if deadlockthreadcount at agent level - 10<=DTC<=20 set agent health as warning clear if DTC<10 to ok
	 * 		Rule 2 : instancehealthhierarchy - if deadlockthreadcount at agent level - DTC>20 set agent health as critical 
	 * 		setting agent health triggers and sets  percentok, percentwarn, percentcrit metrics which should be checked in asserts  
	 * 
	 */
	private void case5()
	{
		//publish set of facts from csv
		try {
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export6.csv");
			Thread.sleep(15000);
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export_init_health.csv");
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//publish rule
		publishRule5_1();
		
		//query and chk asserts - initial facts
		snapshotQuery5_1(1);
		
		//set conditions
		lineFact_5("C:/Users/ssinghal/Desktop/factdata/export6_1.csv", 1);
		
		//for set condition 
		lineFact_5("C:/Users/ssinghal/Desktop/factdata/export6_2.csv", 12);
		
		System.out.println("Finish - case 5");
		
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		snapshotQuery5_2();
		
	}
	
	private void lineFact_5(String csvfilePath, int j)
	{
		try {
			List<String> lines = TestUtilities.readCsv(csvfilePath);
			Map<String, DataType> attDataType = TestFacts.getSchemaAttrDataTypeMap(session);
			
			boolean firstLine = true;
			String[] headers = null;
			Fact fact = null;
			
			for(String line : lines)
			{
				String[] array = line.split(",");
				if(firstLine){
					firstLine = false;
					headers = array;
				}else{
					j++;
					fact = TestFacts.getFactInstance(session);
					for(int i = 0; i<headers.length; i++)
					{
						if(i == array.length)
						{
							break;
						}
						if((array[i]!=null) && (!array[i].equals(""))){
							TestFacts.insertAttToFact(fact, headers[i], TestFacts.getOjectOnDataType(attDataType.get(headers[i]), array[i]));
						}
					}
					TestFacts.publishFact(session, fact);
					Thread.sleep(3000);
					//snapshotQuery5_1(j);
				}
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
	private void snapshotQuery5_1(int i) 
	{
		System.out.println("starting snapshotQuery5_1");
		
		try 
		{
			Thread.sleep(1000);
	        final Query query = session.createQuery();
	
	        QueryByFilterDef queryDef = query.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_AGENT_PERCENT_OK_HEALTH);
	        queryDef.setName("SnapshotQueryDef5_1");
	        queryDef.setQueryType(QueryType.SNAPSHOT);
	        queryDef.setBatchSize(5);
	
	        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        
	       // Filter eqFilter2 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_INSTANCE_PERCENT_OK_HEALTH, 100);
	        
	        AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter.addFilter(eqFilter1/*, eqFilter2*/);
	        /*queryDef.setFilter(andFilter);*/
	        queryDef.setFilter(eqFilter1);
	        
	        Browser<MetricResultTuple> browser = query.execute();
	        processMetricResults5_1(browser,i);
	        query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}   
		
	}
	
	private void snapshotQuery5_2()
	{
		
		try 
		{
			Thread.sleep(1000);
	        Query query = session.createQuery();
	
	        QueryByFilterDef queryDef = query.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_AGENT_PERCENT_OK_HEALTH);
	        queryDef.setName("SnapshotQueryDef5_2");
	        queryDef.setQueryType(QueryType.SNAPSHOT);
	        queryDef.setBatchSize(5);
	
	        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        Filter eqFilter2 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, TestConstants.DIM_APP, "Application-3");
	        Filter eqFilter3 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_APP_HEALTH, "ok");
	        
	        AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter.addFilter(eqFilter1, eqFilter2, eqFilter3);
	        queryDef.setFilter(andFilter);
	        
	        
	        Browser<MetricResultTuple> browser = query.execute();
	        processMetricResults5_2(browser, 2);
	        query.close();
	        
	        //////////////////////////////////////////////////////////////////////
	        
	        System.out.println("starting snapshotQuery5_3");
			Thread.sleep(1000);
	        query = session.createQuery();
	
	        queryDef = query.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_AGENT_PERCENT_OK_HEALTH);
	        queryDef.setName("SnapshotQueryDef5_3");
	        queryDef.setQueryType(QueryType.SNAPSHOT);
	        queryDef.setBatchSize(5);
	
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        
	        eqFilter2 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, TestConstants.DIM_APP, "Application-3");
	        eqFilter3 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, TestConstants.DIM_APP, "Application-2");
	        OrFilter orfilter = QueryFactoryEx.INSTANCE.newOrFilter();
	        orfilter.addFilter(eqFilter2, eqFilter3);
	        
	        Filter eqFilter4 = QueryFactoryEx.INSTANCE.newGEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_AGENT_PERCENT_OK_HEALTH, 50L);
	        
	        andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter.addFilter(eqFilter1, orfilter, eqFilter4);
	        queryDef.setFilter(andFilter);
	        
	        browser = query.execute();
	        processMetricResults5_2(browser, 3);
	        query.close();
	        
	        ////////////////////////////////////////////////
	        
	        System.out.println("starting snapshotQuery5_4");
			Thread.sleep(1000);
	        query = session.createQuery();
	
	        queryDef = query.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT);
	        queryDef.setName("SnapshotQueryDef5_4");
	        queryDef.setQueryType(QueryType.SNAPSHOT);
	        queryDef.setBatchSize(5);
	
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_INSTANCE);
	        
	        eqFilter2 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, TestConstants.DIM_APP, "Application-3");
	        eqFilter3 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, TestConstants.DIM_APP, "Application-2");
	        orfilter = QueryFactoryEx.INSTANCE.newOrFilter();
	        orfilter.addFilter(eqFilter2, eqFilter3);
	        
	        eqFilter4 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT,6);
	        Filter eqFilter5 = QueryFactoryEx.INSTANCE.newLtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT,20);
	        AndFilter andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter4, eqFilter5);
	        
	        
	        andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter.addFilter(eqFilter1, orfilter, andFilter1);
	        queryDef.setFilter(andFilter);
	        
	        browser = query.execute();
	        processMetricResults5_2(browser, 4);
	        query.close();
	        
	        ////////////////////////////////////////////////
	        
	        System.out.println("starting snapshotQuery5_5");
			Thread.sleep(1000);
	        query = session.createQuery();
	
	        queryDef = query.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT);
	        queryDef.setName("SnapshotQueryDef5_5");
	        queryDef.setQueryType(QueryType.SNAPSHOT);
	        queryDef.setBatchSize(5);
	
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_INSTANCE);
	        
	        eqFilter2 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, TestConstants.DIM_APP, "Application-3");
	        
	        eqFilter3 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT,6);
	        eqFilter4 = QueryFactoryEx.INSTANCE.newLtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT,20);	        
	        andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter3, eqFilter4);
	        
	        orfilter = QueryFactoryEx.INSTANCE.newOrFilter();
	        orfilter.addFilter(eqFilter2, andFilter1);
	        
	        
	        andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter.addFilter(eqFilter1, orfilter);
	        queryDef.setFilter(andFilter);
	        
	        browser = query.execute();
	        processMetricResults5_2(browser, 5);
	        query.close();
	        
	        
	        ////////////////////////////////////////////////
	        
	        System.out.println("starting snapshotQuery5_6");
			Thread.sleep(1000);
	        query = session.createQuery();
	
	        queryDef = query.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT);
	        queryDef.setName("SnapshotQueryDef5_6");
	        queryDef.setQueryType(QueryType.SNAPSHOT);
	        queryDef.setBatchSize(5);
	
	        eqFilter1 = QueryFactoryEx.INSTANCE.newLtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_CPU_USEAGE,11);
	        eqFilter2 = QueryFactoryEx.INSTANCE.newLtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_THREAD_COUNT,40);
	        orfilter = QueryFactoryEx.INSTANCE.newOrFilter();
	        orfilter.addFilter(eqFilter1, eqFilter2);
	        
	        eqFilter3 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_INSTANCE);
	        eqFilter4 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, TestConstants.DIM_APP, "Application-2");
	        
	        andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter.addFilter(orfilter, eqFilter3, eqFilter4);
	        
	       
	        queryDef.setFilter(andFilter);
	        
	        browser = query.execute();
	        processMetricResults5_2(browser, 6);
	        query.close();
	        

	        ////////////////////////////////////////////////
	        
	        System.out.println("starting snapshotQuery5_7");
			Thread.sleep(1000);
	        query = session.createQuery();
	
	        queryDef = query.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_CPU_COUNT);
	        queryDef.setName("SnapshotQueryDef5_7");
	        queryDef.setQueryType(QueryType.SNAPSHOT);
	        queryDef.setBatchSize(5);
	
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_INSTANCE);
	        eqFilter2 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_CPU_COUNT,4);
	        orfilter = QueryFactoryEx.INSTANCE.newOrFilter();
	        orfilter.addFilter(eqFilter1, eqFilter2);
	        
	        
	        eqFilter3 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_AGENT);
	        eqFilter4 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_CPU_USEAGE,10);     
	        OrFilter orfilter1 = QueryFactoryEx.INSTANCE.newOrFilter();
	        orfilter1.addFilter(eqFilter3, eqFilter4);
	        
	        OrFilter orfilter2 = QueryFactoryEx.INSTANCE.newOrFilter();
	        orfilter2.addFilter(orfilter, orfilter1);
	        
	        queryDef.setFilter(orfilter2);
	       // queryDef.addOrderByTuple(MetricQualifier.DIMENSION_LEVEL);
	        queryDef.addOrderByTuple(MetricQualifier.DIMENSION_LEVEL_NO);
	        queryDef.addOrderByTuple(FilterKeyQualifier.DIMENSION_NAME, "instance");
	       // queryDef.addOrderByTuple(MetricQualifier.CREATED_TIME);
	       
	       // queryDef.setSortOrder(SortOrder.DESCENDING);
	        
	        browser = query.execute();
	        processMetricResults5_2(browser, 7);
	        query.close();
	        
	        
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private void processMetricResults5_2(Browser<MetricResultTuple> browser, int queryNumber) {

		while (browser.hasNext()) {
            MetricResultTuple rs = browser.next();
            Map<String, Metric> metricMap = rs.getMetrics();
            
            for(Map.Entry<String, Metric> entry : metricMap.entrySet()){
            	MetricKey key = null;
            	Metric metric = entry.getValue();
            	
            	if(metric instanceof SingleValueMetric){
            		key = (MetricKey) (metric.getKey());
            	}

            	Object metricVal = ((SingleValueMetric)metric).getValue();
            	String measurementName = metric.getDescriptor().getMeasurementName();
            	
            	switch(queryNumber)
            	{
	            	case 2 :
	            	{
	            		if( (metricVal != null) && (TestConstants.MEASUREMENT_APP_HEALTH.equalsIgnoreCase(measurementName)) ){
	                		if(true){
	                			assertEquals("ok", metricVal);
	                		}
	                	}
	            		break;
	            	}
	            	case 3 :
	            	{
	            		if( (metricVal != null) && (TestConstants.MEASUREMENT_AGENT_PERCENT_OK_HEALTH.equalsIgnoreCase(measurementName)) ){
	                		if(true){
	                			assertEquals(55L, metricVal);
	                		}
	                	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_APP_HEALTH.equalsIgnoreCase(measurementName)) ){
	                		if(true){
	                			assertEquals("ok", metricVal);
	                		}
	                	}
	            		break;
	            	}
	            	case 4 :
	            	{
	            		if( (metricVal != null) && (TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT.equalsIgnoreCase(measurementName)) ){
	            			if(key.getDimensionValue("instance").equals("InferenceAgent")){
	            				assertEquals(12, metricVal);
	            			}else if(key.getDimensionValue("instance").equals("InferenceServer")){
	            				assertEquals(18, metricVal);
	            			}else if(key.getDimensionValue("instance").equals("CacheServer")){
	            				assertEquals(11, metricVal);
	            			}
	                	}
	            		break;
	            	}
	            	case 5 : //total 7 tuples in batch of 5 and 2
	            	{
	            		if( (metricVal != null) && (TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT.equalsIgnoreCase(measurementName)) && ((int)metricVal>20)){
	            			assertEquals(21, metricVal);
	                	}
	            		break;
	            	}
	            	case 6 : //total 1 tuple
	            	{
	            		if( (metricVal != null) && (TestConstants.MEASUREMENT_CPU_USEAGE.equalsIgnoreCase(measurementName)) ){
	            			assertEquals(10.328, metricVal);
	                	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_THREAD_COUNT.equalsIgnoreCase(measurementName)) ){
	                		assertEquals(38, metricVal);
	                	}
	            		break;
	            	}
	            	case 7 : //total 31 tuple
	            	{
	            		break;
	            	}
            	}
            }
        }
	}
	
	
	
	// i=1,2,3 = initial fact publish, after set condition facts, after clear condition facts
		private void processMetricResults5_1(Browser<MetricResultTuple> browser, int i) {

			while (browser.hasNext()) {
	            MetricResultTuple rs = browser.next();
	            Map<String, Metric> metricMap = rs.getMetrics();
	            
	            for(Map.Entry<String, Metric> entry : metricMap.entrySet()){
	            	MetricKey key = null;
	            	Metric metric = entry.getValue();
	            	
	            	if(metric instanceof SingleValueMetric){
	            		key = (MetricKey) (metric.getKey());
	            	}

	            	Object metricVal = ((SingleValueMetric)metric).getValue();
	            	String measurementName = metric.getDescriptor().getMeasurementName();
	            	
	            	if( (metricVal != null) && (TestConstants.MEASUREMENT_AGENT_PERCENT_OK_HEALTH.equalsIgnoreCase(measurementName)) ){
	            		if(i==1){
	            			assertEquals(100L, metricVal);
	            		}else if(i==2){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(100L, metricVal);
	            			}
	            		}else if(i==3){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(88L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(100L, metricVal);
	            			}
	            		}else if(i==4){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(77L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(100L, metricVal);
	            			}
	            		}else if(i==5){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(66L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(100L, metricVal);
	            			}
	            		}else if(i==6){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(55L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(100L, metricVal);
	            			}
	            		}else if(i==7){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(44L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(100L, metricVal);
	            			}
	            		}else if(i==8){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(33L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(100L, metricVal);
	            			}
	            		}else if(i==9){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(33L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(75L, metricVal);
	            			}
	            		}else if(i==10){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(33L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(50L, metricVal);
	            			}
	            		}else if(i==11){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(33L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else if(i==12){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(33L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else if(i==13){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(33L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else if(i==14){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(44L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else if(i==15){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(44L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else if(i==16){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(44L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else if(i==17){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(44L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else if(i==18){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(55L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else if(i==19){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(55L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else if(i==20){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(55L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(50L, metricVal);
	            			}
	            		}else if(i==21){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(55L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(50L, metricVal);
	            			}
	            		}else if(i==22){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(55L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(50L, metricVal);
	            			}
	            		}
	            	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_AGENT_PERCENT_WARN_HEALTH.equalsIgnoreCase(measurementName)) ){
	            		if(i==1){
	            			assertEquals(0L, metricVal);
	            		}else if(i==2){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==3){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(11L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==4){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(11L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==5){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(22L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==6){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(22L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==7){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(33L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==8){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(44L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==9){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(44L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else if(i==10){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(44L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else if(i==11){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(44L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else if(i==12){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(44L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==13){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(44L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==14){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(33L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==15){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(44L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==16){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(44L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==17){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(44L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==18){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(33L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==19){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(33L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==20){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(33L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==21){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(33L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else if(i==22){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(33L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}
	            	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_AGENT_PERCENT_CRIT_HEALTH.equalsIgnoreCase(measurementName)) ){
	            		if(i==1){
	            			assertEquals(0L, metricVal);
	            		}else if(i==2){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==3){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==4){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(11L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==5){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(11L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==6){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(22L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==7){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(22L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==8){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(22L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==9){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(22L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(0L, metricVal);
	            			}
	            		}else if(i==10){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(22L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else if(i==11){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(22L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(50L, metricVal);
	            			}
	            		}else if(i==12){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(100L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(22L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(75L, metricVal);
	            			}
	            		}else if(i==13){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(22L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(75L, metricVal);
	            			}
	            		}else if(i==14){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(22L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(75L, metricVal);
	            			}
	            		}else if(i==15){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(11L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(75L, metricVal);
	            			}
	            		}else if(i==16){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(11L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(75L, metricVal);
	            			}
	            		}else if(i==17){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(11L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(75L, metricVal);
	            			}
	            		}else if(i==18){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(11L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(75L, metricVal);
	            			}
	            		}else if(i==19){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(11L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(75L, metricVal);
	            			}
	            		}else if(i==20){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(11L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(50L, metricVal);
	            			}
	            		}else if(i==21){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(11L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}else if(i==22){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals(0L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals(11L, metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals(25L, metricVal);
	            			}
	            		}
	            	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_APP_HEALTH.equalsIgnoreCase(measurementName)) ){
	            		if(i==1){
	            			assertEquals("ok", metricVal);
	            		}else if(i==2){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("critical", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("ok", metricVal);
	            			}
	            		}else if(i==3){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("critical", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("ok", metricVal);
	            			}
	            		}else if(i==4){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("critical", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("ok", metricVal);
	            			}
	            		}else if(i==5){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("critical", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("ok", metricVal);
	            			}
	            		}else if(i==6){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("critical", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("ok", metricVal);
	            			}
	            		}else if(i==7){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("critical", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("ok", metricVal);
	            			}
	            		}else if(i==8){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("critical", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("ok", metricVal);
	            			}
	            		}else if(i==9){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("critical", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("ok", metricVal);
	            			}
	            		}else if(i==10){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("critical", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("ok", metricVal);
	            			}
	            		}else if(i==11){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("critical", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("ok", metricVal);
	            			}
	            		}else if(i==12){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("critical", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("critical", metricVal);
	            			}
	            		}else if(i==13){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("warning", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("critical", metricVal);
	            			}
	            		}else if(i==14){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("warning", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("critical", metricVal);
	            			}
	            		}else if(i==15){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("warning", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("critical", metricVal);
	            			}
	            		}else if(i==16){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("warning", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("critical", metricVal);
	            			}
	            		}else if(i==17){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("warning", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("critical", metricVal);
	            			}
	            		}else if(i==18){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("warning", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("critical", metricVal);
	            			}
	            		}else if(i==19){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("warning", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("critical", metricVal);
	            			}
	            		}else if(i==20){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("warning", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("critical", metricVal);
	            			}
	            		}else if(i==21){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("warning", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("critical", metricVal);
	            			}
	            		}else if(i==22){
	            			if(key.getDimensionValue("app").equals("Application-2")){
	            				assertEquals("warning", metricVal);
	            			}else if(key.getDimensionValue("app").equals("Application-3")){
	            				assertEquals("ok", metricVal);
	            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
	            				assertEquals("critical", metricVal);
	            			}
	            		}
	            	}
	            }
	        }
			
		}
	
	/**
	 * cheking deadlock thread count at agent level and setting agent health in action
	 */
	private void publishRule5_1() 
	{
		try{
			QueryFactory qfac = QueryFactoryEx.INSTANCE;
			QueryByFilterDef setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT);
			setCondition.setName("SetCondition5_1");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        
	        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_AGENT);
	        Filter eqFilter2 = QueryFactoryEx.INSTANCE.newGEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT, 10);
	        Filter eqFilter3 = QueryFactoryEx.INSTANCE.newLEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT, 20);
	        
	        AndFilter andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2, eqFilter3);
	        setCondition.setFilter(andFilter1);
	        
	        QueryByFilterDef clearCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT);
	        clearCondition.setName("ClearCondition5_1");
	        clearCondition.setQueryType(QueryType.STREAMING);
	        clearCondition.setBatchSize(6);
	        Filter eqFilter4 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_AGENT);
	        Filter eqFilter5 = QueryFactoryEx.INSTANCE.newLtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT, 10);
	        
	        AndFilter andFilter2 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter2.addFilter(eqFilter4, eqFilter5);
	        clearCondition.setFilter(andFilter2);
	        
	        TestRule.createRule(session, setCondition, clearCondition, "Rule5_1", 51);
	        
	       ///////////
	        
	        qfac = QueryFactoryEx.INSTANCE;
	        setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT);
			setCondition.setName("SetCondition5_2");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_AGENT);
	        eqFilter2 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT, 20);
	        andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2);
	        setCondition.setFilter(andFilter1);
	        
	        TestRule.createRule(session, setCondition, null, "Rule5_2", 52);
	        
 /***************************************************************************/
	        
	        
	        qfac = QueryFactoryEx.INSTANCE;
	        setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_AGENT_PERCENT_WARN_HEALTH);
			setCondition.setName("SetCondition5_3");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        eqFilter2 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_AGENT_PERCENT_WARN_HEALTH, 50L);
	        andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2);
	        setCondition.setFilter(andFilter1);
	        
	        clearCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_AGENT_PERCENT_WARN_HEALTH);
	        clearCondition.setName("ClearCondition5_3");
	        clearCondition.setQueryType(QueryType.STREAMING);
	        clearCondition.setBatchSize(6);
	        eqFilter4 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        eqFilter5 = QueryFactoryEx.INSTANCE.newLEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_AGENT_PERCENT_WARN_HEALTH, 50L);
	        
	        andFilter2 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter2.addFilter(eqFilter4, eqFilter5);
	        clearCondition.setFilter(andFilter2);
	        
	        TestRule.createRule(session, setCondition, clearCondition, "Rule5_3", 53);
	        Thread.sleep(1000);
	        
	        //
	        
	        qfac = QueryFactoryEx.INSTANCE;
	        setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_AGENT_PERCENT_CRIT_HEALTH);
			setCondition.setName("SetCondition5_4");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        eqFilter2 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_AGENT_PERCENT_CRIT_HEALTH, 50L);
	        andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2);
	        setCondition.setFilter(andFilter1);
	        
	        clearCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_AGENT_PERCENT_CRIT_HEALTH);
	        clearCondition.setName("ClearCondition5_4");
	        clearCondition.setQueryType(QueryType.STREAMING);
	        clearCondition.setBatchSize(6);
	        eqFilter4 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        eqFilter5 = QueryFactoryEx.INSTANCE.newLEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_AGENT_PERCENT_CRIT_HEALTH, 50L);
	        
	        andFilter2 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter2.addFilter(eqFilter4, eqFilter5);
	        clearCondition.setFilter(andFilter2);
	        
	        TestRule.createRule(session, setCondition, clearCondition, "Rule5_4", 54);
	        Thread.sleep(1000);
	        
//
	        
	        qfac = QueryFactoryEx.INSTANCE;
	        setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_AGENT_PERCENT_OK_HEALTH);
			setCondition.setName("SetCondition5_5");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(0);
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        eqFilter2 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_AGENT_PERCENT_OK_HEALTH, 50L);
	        andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2);
	        setCondition.setFilter(andFilter1);
	        
	        clearCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_AGENT_PERCENT_OK_HEALTH);
	        clearCondition.setName("ClearCondition5_5");
	        clearCondition.setQueryType(QueryType.STREAMING);
	        clearCondition.setBatchSize(6);
	        eqFilter4 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        eqFilter5 = QueryFactoryEx.INSTANCE.newLEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_AGENT_PERCENT_OK_HEALTH, 50L);
	        
	        andFilter2 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter2.addFilter(eqFilter4, eqFilter5);
	        clearCondition.setFilter(andFilter2);
	        
	        TestRule.createRule(session, setCondition, clearCondition, "Rule5_5", 55);
	        
	       
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Publish fact from csv 5
	 * publish 2 rules
	 * 		Rule 1 - instancehelathHierarchy - set-> instancehealth=warning on 50<=cpuuseage<=70 ,  clear-> instancehealth=ok on cpuuseage<50
	 * 		Rule 2 - instancehelathHierarchy - set-> instancehealth=critical on cpuuseage>70 ,  clear - NA
	 * Assert total instance,Instancepercentokhealth, Instancepercentwarninghealth, Instancepercentcriticalhealth at app level
	 * Publish fact to change cpuuseage for set conditions
	 * Assert same values again
	 * Publish fact to change cpuusegae for clear condition
	 * Assert same values again
	 */
	private void case4()
	{
		//publish set of facts from csv
		try {
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export5.csv");
			Thread.sleep(15000);
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export5_0.csv");
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//publish rule
		publishRule4_1();
		
		//query and chk asserts for set
		snapshotQuery4_1(0);
		
		
		try {
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export5_1_1.csv");
			Thread.sleep(5000);
			snapshotQuery4_1(1);
			
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export5_1_2.csv");
			Thread.sleep(5000);
			snapshotQuery4_1(2);
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export5_1_3.csv");
			Thread.sleep(5000);
			snapshotQuery4_1(3);
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export5_1_4.csv");
			Thread.sleep(5000);
			snapshotQuery4_1(4);
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export5_1_5.csv");
			Thread.sleep(5000);
			snapshotQuery4_1(5);
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export5_1_6.csv");
			Thread.sleep(5000);
			snapshotQuery4_1(6);
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export5_1_7.csv");
			Thread.sleep(5000);
			snapshotQuery4_1(7);
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export5_1_8.csv");
			Thread.sleep(5000);
			snapshotQuery4_1(8);
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export5_1_9.csv");
			Thread.sleep(5000);
			snapshotQuery4_1(9);
			
			//clear
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export5_2_1.csv");
			Thread.sleep(5000);
			snapshotQuery4_1(10);
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export5_2_2.csv");
			Thread.sleep(5000);
			snapshotQuery4_1(11);
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export5_2_3.csv");
			Thread.sleep(5000);
			snapshotQuery4_1(12);
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export5_2_4.csv");
			Thread.sleep(5000);
			snapshotQuery4_1(13);
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export5_2_5.csv");
			Thread.sleep(5000);
			snapshotQuery4_1(14);
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export5_2_6.csv");
			Thread.sleep(5000);
			snapshotQuery4_1(15);
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
	}
	
	private void snapshotQuery4_1(int i) 
	{
		System.out.println("starting snapshotQuery4_1");
		
		try 
		{
			Thread.sleep(1000);
	        final Query query = session.createQuery();
	
	        QueryByFilterDef queryDef = query.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_INSTANCE_PERCENT_OK_HEALTH);
	        queryDef.setName("SnapshotQueryDef4_1");
	        queryDef.setQueryType(QueryType.SNAPSHOT);
	        queryDef.setBatchSize(5);
	
	        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        
	        AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter.addFilter(eqFilter1/*, eqFilter2*/);
	       // queryDef.setFilter(andFilter);
	        queryDef.setFilter(eqFilter1);
	        
	        Browser<MetricResultTuple> browser = query.execute();
	        processMetricResults4_1(browser,i);
	        query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}   
		
	}

	// i=1,2,3 = initial fact publish, after set condition facts, after clear condition facts
	private void processMetricResults4_1(Browser<MetricResultTuple> browser, int i) {

		while (browser.hasNext()) {
            MetricResultTuple rs = browser.next();
            Map<String, Metric> metricMap = rs.getMetrics();
            
            for(Map.Entry<String, Metric> entry : metricMap.entrySet()){
            	MetricKey key = null;
            	Metric metric = entry.getValue();
            	
            	if(metric instanceof SingleValueMetric){
            		key = (MetricKey) (metric.getKey());
            	}

            	Object metricVal = ((SingleValueMetric)metric).getValue();
            	String measurementName = metric.getDescriptor().getMeasurementName();
            	
            	if( (metricVal != null) && (TestConstants.MEASUREMENT_INSTANCE_PERCENT_OK_HEALTH.equalsIgnoreCase(measurementName)) ){
            		if(i==0){
            			assertEquals(100L, metricVal);
            		}else if(i==1){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(100L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(100L, metricVal);
            			}
            		}else if(i==2){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(80L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(100L, metricVal);
            			}
            		}else if(i==3){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(60L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(100L, metricVal);
            			}
            		}else if(i==4){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(40L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(100L, metricVal);
            			}
            		}else if(i==5){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(20L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(100L, metricVal);
            			}
            		}else if(i==6){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(20L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(75L, metricVal);
            			}
            		}else if(i==7){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(20L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(50L, metricVal);
            			}
            		}else if(i==8){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(20L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(50L, metricVal);
            			}
            		}else if(i==9){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(50L, metricVal);
            			}
            		}else if(i==10){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(100L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(50L, metricVal);
            			}
            		}else if(i==11){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(100L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(20L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(50L, metricVal);
            			}
            		}
            		else if(i==12){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(100L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(40L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(50L, metricVal);
            			}
            		}else if(i==13){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(100L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(60L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(50L, metricVal);
            			}
            		}else if(i==14){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(100L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(60L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(50L, metricVal);
            			}
            		}else if(i==15){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(100L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(60L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(75L, metricVal);
            			}
            		}
            	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_INSTANCE_PERCENT_WARN_HEALTH.equalsIgnoreCase(measurementName)) ){
            		if(i==0){
            			assertEquals(0L, metricVal);
            		}else if(i==1){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(100L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(0L, metricVal);
            			}
            		}else if(i==2){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(100L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(20L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(0L, metricVal);
            			}
            		}else if(i==3){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(100L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(40L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(0L, metricVal);
            			}
            		}else if(i==4){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(100L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(60L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(0L, metricVal);
            			}
            		}else if(i==5){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(100L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(60L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(0L, metricVal);
            			}
            		}else if(i==6){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(100L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(60L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==7){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(100L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(60L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==8){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(100L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(40L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==9){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(100L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(40L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==10){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(40L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==11){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(20L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==12){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(20L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==13){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==14){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(20L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==15){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(20L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(0L, metricVal);
            			}
            		}
            	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_INSTANCE_PERCENT_CRIT_HEALTH.equalsIgnoreCase(measurementName)) ){
            		if(i==0){
            			assertEquals(0L, metricVal);
            		}else if(i==1){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(0L, metricVal);
            			}
            		}else if(i==2){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(0L, metricVal);
            			}
            		}else if(i==3){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(0L, metricVal);
            			}
            		}else if(i==4){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(0L, metricVal);
            			}
            		}else if(i==5){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(20L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(0L, metricVal);
            			}
            		}else if(i==6){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(20L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(0L, metricVal);
            			}
            		}else if(i==7){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(20L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==8){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(40L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==9){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(60L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==10){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(60L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==11){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(60L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==12){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(40L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==13){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(40L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==14){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(20L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==15){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(20L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}
            	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_APP_HEALTH.equalsIgnoreCase(measurementName)) ){
            		if(i==0){
            			assertEquals("ok", metricVal);
            		}else if(i==1){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==2){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==3){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==4){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==5){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==6){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==7){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==8){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==9){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("critical", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==10){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("critical", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==11){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("critical", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==12){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("critical", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==13){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==14){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==15){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}
            	}
            }
        }
	}

	private void publishRule4_1() {
		
		try{
			QueryFactory qfac = QueryFactoryEx.INSTANCE;
			QueryByFilterDef setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_CPU_USEAGE);
			setCondition.setName("SetCondition4_1");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        
	        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_INSTANCE);
	        Filter eqFilter2 = QueryFactoryEx.INSTANCE.newGEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_CPU_USEAGE, 50.0);
	        Filter eqFilter3 = QueryFactoryEx.INSTANCE.newLEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_CPU_USEAGE, 70.0);
	        
	        
	        AndFilter andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2, eqFilter3);
	        setCondition.setFilter(andFilter1);
	        
	        QueryByFilterDef clearCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_CPU_USEAGE);
	        clearCondition.setName("ClearCondition4_1");
	        clearCondition.setQueryType(QueryType.STREAMING);
	        clearCondition.setBatchSize(6);
	        Filter eqFilter4 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_INSTANCE);
	        Filter eqFilter5 = QueryFactoryEx.INSTANCE.newLtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_CPU_USEAGE, 50.0);
	        
	        AndFilter andFilter2 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter2.addFilter(eqFilter4, eqFilter5);
	        clearCondition.setFilter(andFilter2);
	        
	        TestRule.createRule(session, setCondition, clearCondition, "Rule4_1", 41);
	        
	       //////////////////////////////////////////////////////////////////////////////////////
	        
	        qfac = QueryFactoryEx.INSTANCE;
	        setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_CPU_USEAGE);
			setCondition.setName("SetCondition4_2");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_INSTANCE);
	        eqFilter2 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_CPU_USEAGE, 70.0);
	        andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2);
	        setCondition.setFilter(andFilter1);
	        
	        clearCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_CPU_USEAGE);
	        clearCondition.setName("ClearCondition4_2");
	        clearCondition.setQueryType(QueryType.STREAMING);
	        clearCondition.setBatchSize(6);
	        eqFilter4 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_INSTANCE);
	        eqFilter5 = QueryFactoryEx.INSTANCE.newLtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_CPU_USEAGE, 70.0);
	        
	        andFilter2 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter2.addFilter(eqFilter4, eqFilter5);
	        clearCondition.setFilter(andFilter2);
	        
	        TestRule.createRule(session, setCondition, clearCondition, "Rule4_2", 42);
	        Thread.sleep(1000);
	        

	        /***************************************************************************/
	        
	        
	        qfac = QueryFactoryEx.INSTANCE;
	        setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_INSTANCE_PERCENT_WARN_HEALTH);
			setCondition.setName("SetCondition4_3");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        eqFilter2 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_INSTANCE_PERCENT_WARN_HEALTH, 50L);
	        andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2);
	        setCondition.setFilter(andFilter1);
	        
	        clearCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_INSTANCE_PERCENT_WARN_HEALTH);
	        clearCondition.setName("ClearCondition4_3");
	        clearCondition.setQueryType(QueryType.STREAMING);
	        clearCondition.setBatchSize(6);
	        eqFilter4 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        eqFilter5 = QueryFactoryEx.INSTANCE.newLEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_INSTANCE_PERCENT_WARN_HEALTH, 50L);
	        
	        andFilter2 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter2.addFilter(eqFilter4, eqFilter5);
	        clearCondition.setFilter(andFilter2);
	        
	        TestRule.createRule(session, setCondition, clearCondition, "Rule4_3", 43);
	        Thread.sleep(1000);
	        
	        //
	        
	        qfac = QueryFactoryEx.INSTANCE;
	        setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_INSTANCE_PERCENT_CRIT_HEALTH);
			setCondition.setName("SetCondition4_4");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        eqFilter2 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_INSTANCE_PERCENT_CRIT_HEALTH, 50L);
	        andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2);
	        setCondition.setFilter(andFilter1);
	        
	        clearCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_INSTANCE_PERCENT_CRIT_HEALTH);
	        clearCondition.setName("ClearCondition4_4");
	        clearCondition.setQueryType(QueryType.STREAMING);
	        clearCondition.setBatchSize(6);
	        eqFilter4 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        eqFilter5 = QueryFactoryEx.INSTANCE.newLEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_INSTANCE_PERCENT_CRIT_HEALTH, 50L);
	        
	        andFilter2 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter2.addFilter(eqFilter4, eqFilter5);
	        clearCondition.setFilter(andFilter2);
	        
	        TestRule.createRule(session, setCondition, clearCondition, "Rule4_4", 44);
	        Thread.sleep(1000);
	        
//
	        
	        qfac = QueryFactoryEx.INSTANCE;
	        setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_INSTANCE_PERCENT_OK_HEALTH);
			setCondition.setName("SetCondition4_5");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(0);
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        eqFilter2 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_INSTANCE_PERCENT_OK_HEALTH, 50L);
	        andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2);
	        setCondition.setFilter(andFilter1);
	        
	        clearCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_INSTANCE_PERCENT_OK_HEALTH);
	        clearCondition.setName("ClearCondition4_5");
	        clearCondition.setQueryType(QueryType.STREAMING);
	        clearCondition.setBatchSize(6);
	        eqFilter4 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        eqFilter5 = QueryFactoryEx.INSTANCE.newLEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_INSTANCE_PERCENT_OK_HEALTH, 50L);
	        
	        andFilter2 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter2.addFilter(eqFilter4, eqFilter5);
	        clearCondition.setFilter(andFilter2);
	        
	        TestRule.createRule(session, setCondition, clearCondition, "Rule4_5", 45);
	       
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * publich fact from csv 4
	 * publish rules with set and clear condition
	 * publish fact to trigger set condition of rule
	 * publish fact to trigger clear condition
	 * 
	 */
	private void case3()
	{
		//publish set of facts from csv
		try {
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export4.csv");
			Thread.sleep(25000);
			TestFacts.publishFactFromCsv(session, "C:/Users/ssinghal/Desktop/factdata/export_init_health.csv");
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		//publish rule
		publishRule3_1();
				
		//query and chk asserts - initial facts
		snapshotQuery5_1(1);
				
		//set conditions
		lineFact_3("C:/Users/ssinghal/Desktop/factdata/export4_1.csv", 1);
		
		//for set condition 
		lineFact_3("C:/Users/ssinghal/Desktop/factdata/export4_2.csv", 12);
		
		System.out.println("Finish - case 5");
	}
	
	private void lineFact_3(String csvfilePath, int j)
	{
		try {
			List<String> lines = TestUtilities.readCsv(csvfilePath);
			Map<String, DataType> attDataType = TestFacts.getSchemaAttrDataTypeMap(session);
			
			boolean firstLine = true;
			String[] headers = null;
			Fact fact = null;
			
			for(String line : lines)
			{
				String[] array = line.split(",");
				if(firstLine){
					firstLine = false;
					headers = array;
				}else{
					j++;
					fact = TestFacts.getFactInstance(session);
					for(int i = 0; i<headers.length; i++)
					{
						if(i == array.length)
						{
							break;
						}
						if((array[i]!=null) && (!array[i].equals(""))){
							TestFacts.insertAttToFact(fact, headers[i], TestFacts.getOjectOnDataType(attDataType.get(headers[i]), array[i]));
						}
					}
					TestFacts.publishFact(session, fact);
					Thread.sleep(5000);
					snapshotQuery3_1(j);
				}
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void snapshotQuery3_1(int i)
	{
		System.out.println("starting snapshotQuery3_1");
		
		try 
		{
			Thread.sleep(1000);
	        final Query query = session.createQuery();
	
	        QueryByFilterDef queryDef = query.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_APP_HEALTH);
	        queryDef.setName("SnapshotQueryDef3_1");
	        queryDef.setQueryType(QueryType.SNAPSHOT);
	        queryDef.setBatchSize(5);
	
	        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        queryDef.setFilter(eqFilter1);
	        
	        Browser<MetricResultTuple> browser = query.execute();
	        processMetricResults3_1(browser,i);
	        query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}   
	}
	
	public static void processMetricResults3_1(Browser<MetricResultTuple> browser, int i) throws Exception {
		


		while (browser.hasNext()) {
            MetricResultTuple rs = browser.next();
            Map<String, Metric> metricMap = rs.getMetrics();
            
            for(Map.Entry<String, Metric> entry : metricMap.entrySet()){
            	MetricKey key = null;
            	Metric metric = entry.getValue();
            	
            	if(metric instanceof SingleValueMetric){
            		key = (MetricKey) (metric.getKey());
            	}

            	Object metricVal = ((SingleValueMetric)metric).getValue();
            	String measurementName = metric.getDescriptor().getMeasurementName();
            	
            	if( (metricVal != null) && (TestConstants.MEASUREMENT_INSTANCE_PERCENT_ACTIVE.equalsIgnoreCase(measurementName)) ){
            		if(i==1){
            			assertEquals(100L, metricVal);
            		}else if(i==2){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(100L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(100L, metricVal);
            			}
            		}else if(i==3){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(88L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(100L, metricVal);
            			}
            		}else if(i==4){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(77L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(100L, metricVal);
            			}
            		}else if(i==5){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(66L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(100L, metricVal);
            			}
            		}else if(i==6){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(55L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(100L, metricVal);
            			}
            		}else if(i==7){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(44L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(100L, metricVal);
            			}
            		}else if(i==8){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(33L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(100L, metricVal);
            			}
            		}else if(i==9){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(33L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(75L, metricVal);
            			}
            		}else if(i==10){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(33L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(50L, metricVal);
            			}
            		}else if(i==11){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(33L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==12){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(33L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==13){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(33L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==14){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(44L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==15){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(44L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==16){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(44L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==17){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(44L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==18){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(55L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==19){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(55L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(25L, metricVal);
            			}
            		}else if(i==20){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(55L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(50L, metricVal);
            			}
            		}else if(i==21){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(55L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(50L, metricVal);
            			}
            		}else if(i==22){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals(0L, metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals(55L, metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals(50L, metricVal);
            			}
            		}
            	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_APP_HEALTH.equalsIgnoreCase(measurementName)) ){
            		if(i==1){
            			assertEquals("ok", metricVal);
            		}else if(i==2){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("critical", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==3){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("critical", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==4){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("critical", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==5){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("critical", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==6){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("critical", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==7){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("critical", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==8){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("critical", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==9){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("critical", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==10){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("critical", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==11){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("critical", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("ok", metricVal);
            			}
            		}else if(i==12){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("critical", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("critical", metricVal);
            			}
            		}else if(i==13){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("critical", metricVal);
            			}
            		}else if(i==14){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("critical", metricVal);
            			}
            		}else if(i==15){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("critical", metricVal);
            			}
            		}else if(i==16){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("critical", metricVal);
            			}
            		}else if(i==17){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("critical", metricVal);
            			}
            		}else if(i==18){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("critical", metricVal);
            			}
            		}else if(i==19){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("critical", metricVal);
            			}
            		}else if(i==20){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("critical", metricVal);
            			}
            		}else if(i==21){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("critical", metricVal);
            			}
            		}else if(i==22){
            			if(key.getDimensionValue("app").equals("Application-2")){
            				assertEquals("warning", metricVal);
            			}else if(key.getDimensionValue("app").equals("Application-3")){
            				assertEquals("ok", metricVal);
            			}else if(key.getDimensionValue("app").equals("FraudDetection")){
            				assertEquals("critical", metricVal);
            			}
            		}
            	}
            }
        }
		
	
    }

	/**
	 * 
	 */
	private void publishRule3_1() {

		try{
			QueryFactory qfac = QueryFactoryEx.INSTANCE;
			QueryByFilterDef setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_INSTANCE_PERCENT_ACTIVE);
			setCondition.setName("SetCondition3_1");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(6);
	        
	        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        Filter eqFilter2 = QueryFactoryEx.INSTANCE.newGEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_INSTANCE_PERCENT_ACTIVE, 50L);
	        Filter eqFilter3 = QueryFactoryEx.INSTANCE.newLEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_INSTANCE_PERCENT_ACTIVE, 70L);
	        
	        AndFilter andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2);
	        setCondition.setFilter(andFilter1);
	        
	        QueryByFilterDef clearCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_INSTANCE_PERCENT_ACTIVE);
	        clearCondition.setName("ClearCondition3_1");
	        clearCondition.setQueryType(QueryType.STREAMING);
	        clearCondition.setBatchSize(6);
	        Filter eqFilter4 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        Filter ltFilter5 = QueryFactoryEx.INSTANCE.newLtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_INSTANCE_PERCENT_ACTIVE, 50L);

	        
	        AndFilter andFilter2 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter2.addFilter(eqFilter4, ltFilter5);
	        clearCondition.setFilter(andFilter2);
	        
	        TestRule.createRule(session, setCondition, clearCondition, "Rule3_1", 31);
	        
	        
	        //////////////////////////////////////////////////////////////////////////////////////
	        
	        qfac = QueryFactoryEx.INSTANCE;
	        setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_INSTANCE_PERCENT_ACTIVE);
			setCondition.setName("SetCondition3_2");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(10);
	        eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        eqFilter2 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_INSTANCE_PERCENT_ACTIVE, 70L);
	        andFilter1 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter1.addFilter(eqFilter1, eqFilter2);
	        setCondition.setFilter(andFilter1);
	        
	        clearCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_INSTANCE_PERCENT_ACTIVE);
	        clearCondition.setName("ClearCondition3_2");
	        clearCondition.setQueryType(QueryType.STREAMING);
	        clearCondition.setBatchSize(6);
	        eqFilter4 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        ltFilter5 = QueryFactoryEx.INSTANCE.newLEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_INSTANCE_PERCENT_ACTIVE, 50L);
	        
	        andFilter2 = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter2.addFilter(eqFilter4, ltFilter5);
	        clearCondition.setFilter(andFilter2);
	        
	        TestRule.createRule(session, setCondition, clearCondition, "Rule3_2", 32);
	        Thread.sleep(1000);
	        
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public void testRule1()
	{
		System.out.println("starting test1 case2");
		try 
		{
			QueryFactory qfac = QueryFactoryEx.INSTANCE;
			QueryByFilterDef setCondition = qfac.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_INSTANCE_ACTIVE);
			setCondition.setName("Condition 1");
			setCondition.setQueryType(QueryType.STREAMING);
	        setCondition.setBatchSize(6);
	        
	        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_AGENT);
	        Filter eqFilter2 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, TestConstants.DIM_AGENT_TYPE, TestConstants.AGENT_TYPE_VALUE_INFERENCE);
	        Filter eqFilter3 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_INSTANCE_ACTIVE, 0);

	      
	        AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter.addFilter(eqFilter1, eqFilter2, eqFilter3);
	        setCondition.setFilter(andFilter);
			
			TestRule.createRule(session, setCondition, null, "Rule1", 1);
			
			
			RuleService ruleService = ServiceProviderManager.getInstance().getRuleService();
			List<RuleDef> rulesFromMemory = ruleService.getRules();
			
			//TestFacts.testClusterHealthFact(session, 0);
		
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void caseSnapshotQuery1()
	{
		System.out.println("starting test1 case3");
		try 
		{
			Thread.sleep(1000);
	        final Query query = session.createQuery();
	
	        QueryByFilterDef queryDef = query.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_INSTANCE_HEALTH, TestConstants.MEASUREMENT_INSTANCE_ACTIVE);
	        queryDef.setName("SnapshotQueryDef_ISACTIVE");
	        queryDef.setQueryType(QueryType.SNAPSHOT);
	        queryDef.setBatchSize(5);
	
	        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_AGENT);
	        Filter eqFilter2 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, TestConstants.DIM_AGENT_TYPE, TestConstants.AGENT_TYPE_VALUE_INFERENCE);
	        Filter eqFilter3 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_INSTANCE_ACTIVE, 0);
	        
	        AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter.addFilter(eqFilter1, eqFilter2, eqFilter3);
	        queryDef.setFilter(andFilter);
	        
	        Browser<MetricResultTuple> browser = query.execute();
	        processMetricResults1(browser);
	        query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}   
	}
	
	public static void processMetricResults1(Browser<MetricResultTuple> browser) throws Exception {

        while (browser.hasNext()) {
            MetricResultTuple rs = browser.next();
            Map<String, Metric> metricMap = rs.getMetrics();
            
            for(Map.Entry<String, Metric> entry : metricMap.entrySet()){
            	MetricKey key = null;
            	Metric metric = entry.getValue();
            	
            	if(metric instanceof SingleValueMetric){
            		key = (MetricKey) (metric.getKey());
            	}

            	if( (((SingleValueMetric)metric).getValue() != null) && ("instanceisactive".equalsIgnoreCase(metric.getDescriptor().getMeasurementName()))){
            		assertEquals("agent", key.getDimensionLevelName());
            		assertEquals(false, ((SingleValueMetric)metric).getValue());
            	}
            }
        }
    }
	
	public void caseSnapshotQuery2()
	{
		System.out.println("starting test1 case3");
		try 
		{
			Thread.sleep(1000);
	        final Query query = session.createQuery();
	
	        QueryByFilterDef queryDef = query.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_INSTANCE_ACTIVE);
	        queryDef.setName("SnapshotQueryDef_1");
	        queryDef.setQueryType(QueryType.SNAPSHOT);
	        queryDef.setBatchSize(5);
	
	        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, TestConstants.DIM_APP);
	        Filter eqFilter2 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, TestConstants.DIM_APP, "Application-3");
	        Filter eqFilter3 = QueryFactoryEx.INSTANCE.newLEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_INSTANCE_PERCENT_ACTIVE, 50);
	        
	        AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter.addFilter(eqFilter1, eqFilter2/*, eqFilter3*/);
	        queryDef.setFilter(andFilter);
	        
	        Browser<MetricResultTuple> browser = query.execute();
	        processMetricResults2(browser);
	        query.close();
	        
	        System.out.println("############# Case 2 Qery 2 ##################");
	        
	        final Query query2 = session.createQuery();
	        queryDef = query2.newQueryByFilterDef(TestConstants.SCHEMA_NAME, TestConstants.CUBE_NAME, TestConstants.DIM_HIERARCHY_CLUSTER_HEALTH, TestConstants.MEASUREMENT_INSTANCE_ACTIVE);
	        queryDef.setName("SnapshotQueryDef_2");
	        queryDef.setQueryType(QueryType.SNAPSHOT);
	        queryDef.setBatchSize(5);
	        eqFilter3 = QueryFactoryEx.INSTANCE.newLEFilter(FilterKeyQualifier.MEASUREMENT_NAME, TestConstants.MEASUREMENT_INSTANCE_PERCENT_ACTIVE, 50);
	        andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
	        andFilter.addFilter(eqFilter1, eqFilter3);
	        queryDef.setFilter(andFilter);
	        browser = query2.execute();
	        processMetricResults2_2(browser);
	        query2.close();
	        
	        
		} catch (Exception e) {
			e.printStackTrace();
		}   
	}
	
	public static void processMetricResults2(Browser<MetricResultTuple> browser) throws Exception {
		
		while (browser.hasNext()) {
            MetricResultTuple rs = browser.next();
            Map<String, Metric> metricMap = rs.getMetrics();
            
            for(Map.Entry<String, Metric> entry : metricMap.entrySet()){
            	MetricKey key = null;
            	Metric metric = entry.getValue();
            	
            	if(metric instanceof SingleValueMetric){
            		key = (MetricKey) (metric.getKey());
            	}

            	Object metricVal = ((SingleValueMetric)metric).getValue();
            	String measurementName = metric.getDescriptor().getMeasurementName();
            	if( (metricVal != null) && (TestConstants.MEASUREMENT_INSTANCE_COUNT.equalsIgnoreCase(measurementName)) ){
            		assertEquals(5, metricVal);
            	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_INSTANCE_ACTIVE.equalsIgnoreCase(measurementName)) ){
            		assertEquals(2, metricVal);
            	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_INSTANCE_PERCENT_ACTIVE.equalsIgnoreCase(measurementName))){
            		assertEquals(40L, metricVal);
            	}else if( (metricVal != null) && (TestConstants.MEASUREMENT_AGENT_COUNT.equalsIgnoreCase(measurementName)) ){
            		assertEquals(6, metricVal);
            	}
            }
        }
    }
	

	public static void processMetricResults2_2(Browser<MetricResultTuple> browser) throws Exception {
		
        while (browser.hasNext()) {
            MetricResultTuple rs = browser.next();
            Map<String, Metric> metricMap = rs.getMetrics();
            
            for(Map.Entry<String, Metric> entry : metricMap.entrySet()){
            	MetricKey key = null;
            	Metric metric = entry.getValue();
            	
            	if(metric instanceof SingleValueMetric){
            		key = (MetricKey) (metric.getKey());
            	}

            	Object metricVal = ((SingleValueMetric)metric).getValue();
            	String measurementName = metric.getDescriptor().getMeasurementName();
            	if( (metricVal != null) && (TestConstants.MEASUREMENT_INSTANCE_PERCENT_ACTIVE.equalsIgnoreCase(measurementName)) ){
            		assertTrue((long)metricVal <= 50);
            	}
            }
        }
    }

}
