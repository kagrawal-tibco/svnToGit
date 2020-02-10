package com.tibco.cep.dashboard.psvr.streaming;

import javax.management.MBeanOperationInfo;

import com.tibco.cep.runtime.jmx.Description;
import com.tibco.cep.runtime.jmx.Impact;
import com.tibco.cep.runtime.jmx.Parameter;

@Description("Provides data cache updating related information for dashboard agent")
public interface DataCacheUpdatersMXBean {

	@Description("Count of all the data source updaters")
	@Impact(MBeanOperationInfo.INFO)
	public int getDataSourceUpdaterCount();

	@Description("All the data source updaters")
	@Impact(MBeanOperationInfo.INFO)
	public DataSourceUpdaterRuntimeInfo[] getDataSourceUpdaters();

	@Description("Searches the data source updaters by id")
	@Impact(MBeanOperationInfo.INFO)
	public DataSourceUpdaterRuntimeInfo searchDataSourceUpdatersById(@Description("The data source update handler id") @Parameter("id") String id);

	@Description("Searches the data source updaters by referencer. * is supported as a wild card")
	@Impact(MBeanOperationInfo.INFO)
	public DataSourceUpdaterRuntimeInfo[] searchDataSourceUpdatersByReferencer(@Description("The data source referencer. * is supported as a wild card") @Parameter("referencer") String referencer);

	@Description("Searches the data source updaters by source. * is supported as a wild card")
	@Impact(MBeanOperationInfo.INFO)
	public DataSourceUpdaterRuntimeInfo[] searchDataSourceUpdatersBySource(@Description("The metric which is being queried. * is supported as a wild card") @Parameter("source") String source);

	@Description("Resets a specific data source given a id")
	@Impact(MBeanOperationInfo.ACTION)
	public void resetById(@Description("The data source update handler id") @Parameter("id") String id);

	@Description("Purges a specific data source given a id")
	@Impact(MBeanOperationInfo.ACTION)
	public void purgeById(@Description("The data source update handler id") @Parameter("id") String id);

	@Description("Resets specific data sources used by a referencer. * is supported as a wild card")
	@Impact(MBeanOperationInfo.ACTION)
	public int resetByReferencer(@Description("The data source handler referencer. * is supported as a wild card") @Parameter("referencer") String referencer);

	@Description("Purges specific data sources used by a referencer. * is supported as a wild card")
	@Impact(MBeanOperationInfo.ACTION)
	public int purgeByReferencer(@Description("The data source handler referencer. * is supported as a wild card") @Parameter("referencer") String referencer);

	@Description("Resets specific data sources using a source. * is supported as a wild card")
	@Impact(MBeanOperationInfo.ACTION)
	public int resetBySource(@Description("The metric which is being queried. * is supported as a wild card") @Parameter("source") String source);

	@Description("Purges specific data sources using a source. * is supported as a wild card")
	@Impact(MBeanOperationInfo.ACTION)
	public int purgeBySource(@Description("The metric which is being queried. * is supported as a wild card") @Parameter("source") String source);

	@Description("Resets all data sources")
	@Impact(MBeanOperationInfo.ACTION)
	public int resetAll();

	@Description("Purges all data sources")
	@Impact(MBeanOperationInfo.ACTION)
	public int purgeAll();

	@Description("Average time(msecs) spent in updating the data sources")
	@Impact(MBeanOperationInfo.INFO)
	public double getAverageUpdateProcessingTime();

	@Description("Average time(msecs) spent in resetting the data sources")
	@Impact(MBeanOperationInfo.INFO)
	public double getAverageResetProcessingTime();

	@Description("Data source updater which spent the longest time processing updates")
	@Impact(MBeanOperationInfo.INFO)
	public DataSourceUpdaterRuntimeInfo getPeakUpdateProcessingTimeUpdater();

	@Description("Data source updater which spent the longest time processing reset")
	@Impact(MBeanOperationInfo.INFO)
	public DataSourceUpdaterRuntimeInfo getPeakResetProcessingTimeUpdater();

	@Description("Resets the stats of a specific data source given a id")
	@Impact(MBeanOperationInfo.ACTION)
	public void resetStatsById(@Description("The data source update handler id") @Parameter("id") String id);

	@Description("Resets the stats of data source updaters used by a referencer. * is supported as a wild card")
	@Impact(MBeanOperationInfo.ACTION)
	public int resetStatsByReferencer(@Description("The data source handler referencer. * is supported as a wild card") @Parameter("referencer") String referencer);

	@Description("Resets the stats of data source updaters using a source. * is supported as a wild card")
	@Impact(MBeanOperationInfo.ACTION)
	public int resetStatsBySource(@Description("The metric which is being queried. * is supported as a wild card") @Parameter("source") String source);

	@Description("Resets the stats of all data source updaters")
	@Impact(MBeanOperationInfo.ACTION)
	public int resetStatsAll();

}