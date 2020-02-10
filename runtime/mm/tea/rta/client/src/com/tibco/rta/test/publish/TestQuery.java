package com.tibco.rta.test.publish;

import java.util.Iterator;
import java.util.Map;

import com.tibco.rta.Metric;
import com.tibco.rta.MetricKey;
import com.tibco.rta.RtaSession;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.impl.FactKeyImpl;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.FactResultTuple;
import com.tibco.rta.query.MetricResultTuple;
import com.tibco.rta.query.Query;
import com.tibco.rta.query.QueryResultHandler;
import com.tibco.rta.query.QueryResultTuple;
import com.tibco.rta.test.TestConstants;

public class TestQuery {
	
	public void executeStreamingquery(Query query) throws Exception
	{
        query.setResultHandler(new MyQueryResultHandler());
        query.execute();
	}
	
	public static void executeSnapshotquery(RtaSession session, Query query) throws Exception
	{
		Browser<MetricResultTuple> browser = query.execute();
        processMetricResults(session, browser);
        closeSnapshotQuery(query);
	}
	
	public static void closeSnapshotQuery(Query query) throws Exception
	{
        query.close();
	}
	
	 public static void processMetricResults(RtaSession session, Browser<MetricResultTuple> browser) throws Exception {

	        while (browser.hasNext()) {
	            MetricResultTuple rs = browser.next();
	            Map<String, Metric> metricMap = rs.getMetrics();
	            
	            for(Map.Entry<String, Metric> entry : metricMap.entrySet())
	            {
	            	MetricKey key = null;
	            	Metric metric = entry.getValue();
	            	
	            	if(metric instanceof SingleValueMetric)
	            	{
	            		key = (MetricKey) (metric.getKey());
	            	}

	            	if(((SingleValueMetric)metric).getValue() != null)
	            	{
			            System.out.println(String.format("  Level = %s", key.getDimensionLevelName()));
			            for (String dimName : key.getDimensionNames()) {
			                System.out.println(String.format("    Dimension name = %s, value = %s", dimName, key.getDimensionValue(dimName)));
			            }
			            System.out.println(String.format("    Metric = %s, value = %s createdTime = %d, updatedTime = %d",
			                    metric.getDescriptor().getMeasurementName(), ((SingleValueMetric)metric).getValue(), metric.getCreatedTime(), metric.getLastModifiedTime()));
			            System.out.println();
	            	}
	
//		            getChildMetrics(session, metric);
//		            getConstituentFacts(session, metric);
	            }
	        }
	    }
	 
	 private static void getChildMetrics(RtaSession session, Metric<?> metric) throws Exception {
	        System.out.println("\n\nDemonstrating getChildMetrics.");

	        Browser<QueryResultTuple> childMetricsBrowser = session.getChildMetrics(metric, null);

	        if (childMetricsBrowser != null) {
	            while (childMetricsBrowser.hasNext()) {
	                QueryResultTuple queryResultTuple = (QueryResultTuple) childMetricsBrowser.next();
	                MetricResultTuple metricResultTuple = queryResultTuple.getMetricResultTuple();

	                for (String metricName1 : metricResultTuple.getMetricNames()) {
	                    SingleValueMetric<?> childMetric = (SingleValueMetric<?>) metricResultTuple.getMetric(metricName1);
	                    MetricKey key = (MetricKey) childMetric.getKey();
	                    System.out.println(String.format( "   Level = %s", key.getDimensionLevelName()));

	                    for (String dimName : key.getDimensionNames()) {
	                        System.out.println(String.format("   Dimension name = %s, value = %s", dimName, key.getDimensionValue(dimName)));
	                    }

	                    System.out.println(String.format("   Metric = %s, value = %s", childMetric.getDescriptor().getMeasurementName(), childMetric.getValue()));

	                    System.out.println();
	                    Thread.sleep(1000);
	                }
	            }
	        }
	        System.out.println("Demonstrating getChildMetrics Complete.");
	        System.out.println("\n********************************************************************************");

	    }

	    private static void getConstituentFacts(RtaSession session, Metric<?> metric) throws Exception {
	        System.out.println("\n\nDemonstrating getConstituentFacts.");

	        Browser<FactResultTuple> childFactsBrowser = session.getConstituentFacts(metric, null);

	        if (childFactsBrowser != null) {
	            while (childFactsBrowser.hasNext()) {
	                FactResultTuple childFactResultTuple = childFactsBrowser.next();
	                FactKeyImpl factKey = childFactResultTuple.getFactKey();
	                Map<String, Object> attributes = childFactResultTuple.getFactAttributes();

	                System.out.println("Fact Details");
	                for (Map.Entry<String, Object> entry : attributes.entrySet()) {
	                    System.out.println(String.format( "   Attribute name = %s, value = %s", entry.getKey(), entry.getValue()));
	                }
	            }
	        }
	        System.out.println("\n\nDemonstrating getConstituentFacts Complete.");
	        System.out.println("\n********************************************************************************");
	    }

	
	private class MyQueryResultHandler implements QueryResultHandler {

        @Override
        public void onData(QueryResultTuple queryResultTuple) {
            synchronized (MyQueryResultHandler.class) {
                String pad = new String(new char[queryResultTuple.getQueryName().length()]);
                MetricResultTuple rs = queryResultTuple.getMetricResultTuple();
                if (rs != null) {
                    for (String metricName : rs.getMetricNames()) {

                        SingleValueMetric metric = (SingleValueMetric) rs.getMetric(metricName);
                        if (metric != null) 
                        {
                            String measurementName = metric.getDescriptor().getMeasurementName();
                            MetricKey key = (MetricKey) metric.getKey();
                            
                            if (measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_INSTANCE_COUNT) || measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_AGENT_COUNT)
                            		|| measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_TOTAL_NO_OF_RULES_FIRED) || measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_TOTAL_SUCCESS_DB_TRANSACTION)
                            		|| measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_TOTAL_SUCCESS_TRANSACTION) || measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_CPU_TIME)
                            		|| measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_CPU_COUNT) || measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_CPU_USEAGE)
                            		|| measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_AVG_CPU_USEAGE) || measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_USED_MEM)
                            		|| measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_MAX_MEM) || measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_COMMITTED_MEM)
                            		|| measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_INIT_MEM) || measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_AVG_MEM_USEAGE)
                            		|| measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_JVM_UP_TIME) || measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_THREAD_COUNT)
                            		|| measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_TOTAL_THREAD_COUNT) || measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_DEADLOCKED_THREAD_COUNT)
                            		|| measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_TIME) || measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_AGENT_HEALTH)
                            		|| measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_INSTANCE_HEALTH) || measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_INSTANCE_ACTIVE)
                            		|| measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_APP_HEALTH) || measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_INSTANCE_PERCENT_OK_HEALTH)
                            		|| measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_INSTANCE_PERCENT_WARN_HEALTH) || measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_INSTANCE_PERCENT_CRIT_HEALTH)
                            		|| measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_AGENT_PERCENT_OK_HEALTH) || measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_AGENT_PERCENT_WARN_HEALTH)
                            		|| measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_AGENT_PERCENT_CRIT_HEALTH) || measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_INF_AGENT_PERCENT_OK_HEALTH)
                            		|| measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_INF_AGENT_PERCENT_WARN_HEALTH) || measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_INF_AGENT_PERCENT_CRIT_HEALTH)
                            		|| measurementName.equalsIgnoreCase(TestConstants.MEASUREMENT_INSTANCE_PERCENT_ACTIVE) ) 
                            {
                                System.out.println(String.format("%s: Level = %s", queryResultTuple.getQueryName(), key.getDimensionLevelName()));
                                for (String dimName : key.getDimensionNames()) 
                                {
                                    System.out.println(String.format("%s: Dimension name = %s, value = %s", pad, dimName, key.getDimensionValue(dimName)));
                                }
                                System.out.println(String.format("%s: Metric = %s, value = %s created = %d, updated = %d", pad,
                                        metric.getDescriptor().getMeasurementName(), metric.getValue(), metric.getCreatedTime(), metric.getLastModifiedTime()));
                                System.out.println();
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void onError(Object errorContext) {
            System.out.println("OnError...");
        }
    }

}
