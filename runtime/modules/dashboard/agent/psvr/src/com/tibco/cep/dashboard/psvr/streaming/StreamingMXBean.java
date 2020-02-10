package com.tibco.cep.dashboard.psvr.streaming;

import javax.management.MBeanOperationInfo;

import com.tibco.cep.runtime.jmx.Description;
import com.tibco.cep.runtime.jmx.Impact;
import com.tibco.cep.runtime.jmx.Parameter;

@Description("Provides streaming related information for dashboard agent")
public interface StreamingMXBean {

	@Description("Count of all the connected channels")
	@Impact(MBeanOperationInfo.INFO)
	public int getConnectedChannelsCount();

	@Description("All the connected channels")
	@Impact(MBeanOperationInfo.INFO)
	public ChannelRuntimeInfo[] getConnectedChannels();

	@Description("Searches all the connected clients by user id")
	@Impact(MBeanOperationInfo.INFO)
	public ChannelRuntimeInfo[] searchConnectedChannelsByUserId(@Description("The user id") @Parameter("userid") String userid);

	@Description("Searches all the connected clients by publishing type")
	@Impact(MBeanOperationInfo.INFO)
	public ChannelRuntimeInfo[] searchConnectedChannelByPublishingType(@Description("The publishing type") @Parameter("type") String type);

	@Description("Average update processing time(msecs)")
	@Impact(MBeanOperationInfo.INFO)
	public double getAverageUpdateProcessingTime();

	@Description("Average time(msecs) spent in converting POJO's to XML")
	@Impact(MBeanOperationInfo.INFO)
	public double getAverageMarshallingTime();

	@Description("Average time(msecs) spent in streaming XML to clients")
	@Impact(MBeanOperationInfo.INFO)
	public double getAverageStreamingTime();

	@Description("Channel which has the longest update processing time")
	@Impact(MBeanOperationInfo.INFO)
	public ChannelRuntimeInfo getPeakUpdateProcessingTimeChannel();

	@Description("Channel which has the longest marshalling time")
	@Impact(MBeanOperationInfo.INFO)
	public ChannelRuntimeInfo getPeakMarshallingTimeChannel();

	@Description("Channel which has the longest streaming time")
	@Impact(MBeanOperationInfo.INFO)
	public ChannelRuntimeInfo getPeakStreamingTimeChannel();

	@Description("Resets the stats for all the connected channels")
	@Impact(MBeanOperationInfo.ACTION)
	public int resetStatsAll();

	@Description("Resets the stats for all the connected channels of a specific user id")
	@Impact(MBeanOperationInfo.ACTION)
	public int resetStatsByUserId(@Description("The user id") @Parameter("userid") String userid);

	@Description("Resets the stats for all the connected channels of a specific publishing type")
	@Impact(MBeanOperationInfo.ACTION)
	public int resetStatsByPublishingType(@Description("The publishing type") @Parameter("type") String type);

}