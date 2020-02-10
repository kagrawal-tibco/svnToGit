package com.tibco.cep.ui.monitor.panes{
	import com.tibco.cep.ui.monitor.panes.chart.*;
	import com.tibco.cep.ui.monitor.panes.table.*;
	
	
	public class MetricPaneFactory{
		
		private static var _instance:MetricPaneFactory;
		
		public function MetricPaneFactory(){
			
		}
		
		public static function get instance():MetricPaneFactory{
			if(_instance == null) _instance = new MetricPaneFactory();
			return _instance;
		}
		
		public function buildMetricPane(metricPaneType:String, expandedPaneParent:MetricPane=null):MetricPane{
			switch(metricPaneType){
				case MetricPaneType.ALERTS_TABLE :
					return new SystemAlertsTable(expandedPaneParent);
					break;
				case MetricPaneType.BEST_HIT_RATIO :
					return new ColumnChartPane(metricPaneType, "Best Hit to Miss Ratio", "H-M Ratio", "Machine Name");
					break;
				case MetricPaneType.BEST_JOB_QUEUE :
					return new ColumnChartPane(metricPaneType, "Job Queue Best Performers", "Jobs per Second", "Job Queues");
					break;
				case MetricPaneType.BEST_PUT_TIMES :
					return new ColumnChartPane(metricPaneType, "Best Put Times", "Put Time (ms)", "Cache Instances");
					break;
				case MetricPaneType.BEST_READ_TIMES :
					return new ColumnChartPane(metricPaneType, "Best Read Times", "Read Time (ms)", "Cache Instances");
					break;
				case MetricPaneType.BEST_RULES :
					return new ColumnChartPane(metricPaneType, "Best Rule Performers", "Avg Exec Time (ms)", "Rule ID");
					break;
				case MetricPaneType.BEST_THREADPOOL :
					return new ColumnChartPane(metricPaneType, "Thread Pool Best Performers", "Running Threads", "Thread Pools");
					break;
				case MetricPaneType.CHANNEL_TABLE :
					return new TablePane(metricPaneType, "Channel Statistics"); 
					break;
				case MetricPaneType.CLUSTER_SUMMARY_TABLE :
					return new ClusterStatsTable(metricPaneType, "Cluster Overview",expandedPaneParent);
					break;
				case MetricPaneType.CPU :
					return new TimeBasedLineChart(metricPaneType, "CPU Usage", "Percent Usage");
					break;
				case MetricPaneType.DEADLOCK :
					return new TimeBasedLineChart(metricPaneType, "Deadlocked Threads", "Threads");
					break;
				case MetricPaneType.GARBAGE_COLLECTION_TABLE :
					return new GarbageCollectionTable(expandedPaneParent);
					break;
				case MetricPaneType.HISTOGRAM_TABLE :
					return new TablePane(metricPaneType, "Histogram Statistics");
					break;
				case MetricPaneType.IO_STATS_TABLE :
					return new TablePane(metricPaneType, "IO Statistics");
					break;
				case MetricPaneType.JOB_QUEUE_USAGE :
					return new TimeBasedLineChart(metricPaneType, "Job Queue Usage", "Queue Size");
					break;
				case MetricPaneType.LOCK_HOLD_TABLE :
					return new TimeBasedLineChart(metricPaneType, "Locks Held", "Locks");
					break;
				case MetricPaneType.MEMORY :
					return new TimeBasedAreaChart(metricPaneType, "Memory Usage", "Memory (MB)");
					break;
				case MetricPaneType.NET_STATS_TABLE :
					return new TablePane(metricPaneType, "Network Statistics");
					break;
				case MetricPaneType.PRE_PROC_TABLE :
					return new TablePane(metricPaneType, "Pre-Processor Statistics");
					break;
				case MetricPaneType.RTC_TABLE :
					return new MultiAxesChart("RTC Statistics");
					break;
				case MetricPaneType.THREAD_DUMP_TABLE :
					return new TablePane(metricPaneType, "Thread Dump Info");
					break;
				case MetricPaneType.THREADPOOL_USAGE :
					return new TimeBasedLineChart(metricPaneType, "Thread Pool Usage", "Usage");
					break;
				case MetricPaneType.THREADS :
					return new TimeBasedLineChart(metricPaneType, "Running Threads", "Threads");
					break;
				case MetricPaneType.VM_STATS_TABLE :
					return new TablePane(metricPaneType, "Virtual Machine Stats");
					break;
				case MetricPaneType.WORST_HIT_RATIO :
					return new ColumnChartPane(metricPaneType, "Worst Hit to Miss Ratio", "H-M Ratio", "Machine Name");
					break;
				case MetricPaneType.WORST_JOB_QUEUE :
					return new ColumnChartPane(metricPaneType, "Job Queue Worst Performers", "Jobs per Second", "Job Queues"); 
					break;
				case MetricPaneType.WORST_PUT_TIMES :
					return new ColumnChartPane(metricPaneType, "Worst Put Times", "Put Time (ms)", "Cache Instances");
					break;
				case MetricPaneType.WORST_READ_TIMES :
					return new ColumnChartPane(metricPaneType, "Worst Read Times", "Read Time (ms)", "Cache Instances");
					break;
				case MetricPaneType.WORST_RULES :
					return new ColumnChartPane(metricPaneType, "Worst Rule Performers", "Avg Exec Time (ms)", "Rule ID");
					break;
				case MetricPaneType.WORST_THREADPOOL :
					return new ColumnChartPane(metricPaneType, "Thread Pool Worst Performers", "Running Threads", "Thread Pools"); 
					break;
				//Added By Anand 4/14/2009 to handle query agent stats - START	
				case MetricPaneType.LOCAL_CACHE_CNT :
					return new TimeBasedLineChart(metricPaneType, "Local Cache Entities", "Entities Count");
					break;	
				case MetricPaneType.INCOMING_ENTITY_CNT :
					return new TimeBasedLineChart(metricPaneType, "Incoming Entities", "Entities Count");
					break;	
				case MetricPaneType.ENTITY_CNT :
					return new TimeBasedLineChart(metricPaneType, "Entity Count", "Entities Count");
					break;						
				case MetricPaneType.SNAPSHOT_EXEC_STATS_TABLE :
					return new QueryExecutionTable(metricPaneType, "Snapshot Query Execution",expandedPaneParent);
					break;									
				case MetricPaneType.CONTINUOUS_EXEC_STATS_TABLE :
					return new QueryExecutionTable(metricPaneType, "Continuous Query Execution",expandedPaneParent);
					break;
				//Added By Anand 4/14/2009 to handle query agent stats - END
				//Added By Anand 4/23/2009 to handle cached objects stats - START
				case MetricPaneType.CACHED_OBJECTS_STATS_TABLE :
					return new CachedObjectsTable(metricPaneType, "Cached Objects",expandedPaneParent);
					break;				
				//Added By Anand 4/23/2009 to handle cached objects stats - END
				//Added By Anand 4/27/2009 to handle swap chart - START
				case MetricPaneType.SWAP :
					return new TimeBasedAreaChart(metricPaneType, "Swap File Usage", "Percent Usage");
					break;
				//Added By Anand 4/27/2009 to handle swap chart - END													 									
			}
			throw new Error("MetricPaneFactory - buildMetricPane:\nFailed build of " + metricPaneType);
		}

	}
}