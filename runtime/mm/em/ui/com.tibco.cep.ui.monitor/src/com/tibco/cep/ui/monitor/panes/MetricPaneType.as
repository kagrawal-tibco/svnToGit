package com.tibco.cep.ui.monitor.panes{
	
	public class MetricPaneType{
		
		//Charts
		public static const BEST_HIT_RATIO:String = "besthitratio";
		public static const BEST_JOB_QUEUE:String = "bestjqueue";
		public static const BEST_PUT_TIMES:String = "bestptimes";
		public static const BEST_READ_TIMES:String = "bestrtimes";
		public static const BEST_RULES:String = "bestrules";
		public static const BEST_THREADPOOL:String = "besttpool";
		
		public static const CPU:String = "cpustats";
		public static const DEADLOCK:String = "dthreads";
		public static const HIT_MISS_RATIO:String = "hitmissratio";
		public static const JOB_QUEUE_USAGE:String = "jqueue";
		public static const MEMORY:String = "memory";
		public static const THREADPOOL_USAGE:String = "tpool";
		public static const THREADS:String = "rthreads";		
		
		public static const WORST_HIT_RATIO:String = "worsthitratio";		
		public static const WORST_JOB_QUEUE:String = "worstjqueue";
		public static const WORST_PUT_TIMES:String = "worstptimes";
		public static const WORST_READ_TIMES:String = "worstrtimes";
		public static const WORST_RULES:String = "worstrules";
		public static const WORST_THREADPOOL:String = "worsttpool";
		
		//Added By Anand - 4/14/09 to handle query agent stats
		public static const LOCAL_CACHE_CNT:String = "lcachestats";
		public static const INCOMING_ENTITY_CNT:String = "inentitystat";
		public static const ENTITY_CNT:String = "entitystats";
				
		//Tables
		public static const ALERTS_TABLE:String = "sysalerts";
		public static const CHANNEL_TABLE:String = "chstats";
		public static const CLUSTER_SUMMARY_TABLE:String = "cstats";
		public static const GARBAGE_COLLECTION_TABLE:String = "gc";
		public static const HISTOGRAM_TABLE:String = "histogramtable";
		public static const IO_STATS_TABLE:String = "iostats";
		public static const LOCK_HOLD_TABLE:String = "locks";
		public static const NET_STATS_TABLE:String = "nwstats";
		public static const PRE_PROC_TABLE:String = "ppstats";
		public static const RTC_TABLE:String = "rtcstats";
		public static const THREAD_DUMP_TABLE:String = "threaddumptable";
		public static const VM_STATS_TABLE:String = "vmstats";
		//Added By Anand 4/23/2009 to handle cached objects stats
		public static const CACHED_OBJECTS_STATS_TABLE:String = "mcacheobjectstats";
		

		//Added By Anand - 4/14/09 to handle query agent stats
		public static const SNAPSHOT_EXEC_STATS_TABLE:String = "ssqestats";
		public static const CONTINUOUS_EXEC_STATS_TABLE:String = "cqestats";

		//Added By Anand - 4/27/09 to handle machine level swap stats
		public static const SWAP:String = "swap";
	}
}
