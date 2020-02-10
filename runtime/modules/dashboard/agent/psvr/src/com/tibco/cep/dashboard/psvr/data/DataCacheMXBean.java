package com.tibco.cep.dashboard.psvr.data;

import javax.management.MBeanOperationInfo;

import com.tibco.cep.runtime.jmx.Description;
import com.tibco.cep.runtime.jmx.Impact;
import com.tibco.cep.runtime.jmx.Parameter;

@Description("Provides data cache related information for dashboard agent")
public interface DataCacheMXBean {

	@Description("Total number of data source handlers in the cache")
	@Impact(MBeanOperationInfo.INFO)
	public int getDataSourceHandlerCount();

	@Description("All data source handlers in the cache")
	@Impact(MBeanOperationInfo.INFO)
	public DataSourceHandlerRuntimeInfo[] getDataSourceHandlers();

	@Description("Searches data source handlers by id")
	@Impact(MBeanOperationInfo.INFO)
	public DataSourceHandlerRuntimeInfo searchDataSourceHandlersById(@Description("The data source handler id") @Parameter("id") String id);

	@Description("Searches data source handlers used by referencer. * is supported as a wild card")
	@Impact(MBeanOperationInfo.INFO)
	public DataSourceHandlerRuntimeInfo[] searchDataSourceHandlersByReferencer(@Description("The data source handler referencer. * is supported as a wild card") @Parameter("referencer") String referencer);

	@Description("Searches data source handlers using source. * is supported as a wild card")
	@Impact(MBeanOperationInfo.INFO)
	public DataSourceHandlerRuntimeInfo[] searchDataSourceHandlersBySource(@Description("The metric which is being queried. * is supported as a wild card") @Parameter("source") String source);

}