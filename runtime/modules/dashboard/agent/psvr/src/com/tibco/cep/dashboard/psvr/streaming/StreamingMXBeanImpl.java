package com.tibco.cep.dashboard.psvr.streaming;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StreamingMXBeanImpl implements StreamingMXBean {

	@Override
	public double getAverageMarshallingTime() {
		double sum = 0.0;
		long count = 0;
		Collection<Channel> channels = ChannelGroup.getInstance().channels();
		if (channels.isEmpty() == true) {
			return 0.0;
		}
		for (Channel channel : channels) {
			sum = sum + channel.getPublisher().getMarshallingTime().getAverage();
			count++;
		}
		return sum / count;
	}

	@Override
	public double getAverageStreamingTime() {
		double sum = 0.0;
		long count = 0;
		Collection<Channel> channels = ChannelGroup.getInstance().channels();
		if (channels.isEmpty() == true) {
			return 0.0;
		}
		for (Channel channel : channels) {
			sum = sum + channel.getPublisher().getStreamingTime().getAverage();
			count++;
		}
		return sum / count;
	}

	@Override
	public double getAverageUpdateProcessingTime() {
		double sum = 0.0;
		long count = 0;
		Collection<Channel> channels = ChannelGroup.getInstance().channels();
		if (channels.isEmpty() == true) {
			return 0.0;
		}
		for (Channel channel : channels) {
			sum = sum + channel.getPublisher().getUpdateProcessingTime().getAverage();
			count++;
		}
		return sum / count;
	}

	@Override
	public ChannelRuntimeInfo[] getConnectedChannels() {
		Collection<Channel> channels = ChannelGroup.getInstance().channels();
		ChannelRuntimeInfo[] channelInfos = new ChannelRuntimeInfo[channels.size()];
		int i = 0;
		for (Channel channel : channels) {
			channelInfos[i] = getRuntimeInfo(channel);
			i++;
		}
		return channelInfos;
	}

	@Override
	public int getConnectedChannelsCount() {
		return ChannelGroup.getInstance().channels().size();
	}

	@Override
	public ChannelRuntimeInfo getPeakMarshallingTimeChannel() {
		Channel highestChannel = null;
		Collection<Channel> channels = ChannelGroup.getInstance().channels();
		if (channels.isEmpty() == true){
			return null;
		}
		for (Channel channel : channels) {
			if (highestChannel == null || channel.getPublisher().getMarshallingTime().getAverage() > highestChannel.getPublisher().getMarshallingTime().getAverage()) {
				highestChannel = channel;
			}
		}
		return getRuntimeInfo(highestChannel);
	}

	@Override
	public ChannelRuntimeInfo getPeakStreamingTimeChannel() {
		Channel highestChannel = null;
		Collection<Channel> channels = ChannelGroup.getInstance().channels();
		if (channels.isEmpty() == true){
			return null;
		}
		for (Channel channel : channels) {
			if (highestChannel == null || channel.getPublisher().getStreamingTime().getAverage() > highestChannel.getPublisher().getStreamingTime().getAverage()) {
				highestChannel = channel;
			}
		}
		return getRuntimeInfo(highestChannel);
	}

	@Override
	public ChannelRuntimeInfo getPeakUpdateProcessingTimeChannel() {
		Channel highestChannel = null;
		Collection<Channel> channels = ChannelGroup.getInstance().channels();
		if (channels.isEmpty() == true){
			return null;
		}
		for (Channel channel : channels) {
			if (highestChannel == null || channel.getPublisher().getUpdateProcessingTime().getAverage() > highestChannel.getPublisher().getUpdateProcessingTime().getAverage()) {
				highestChannel = channel;
			}
		}
		return getRuntimeInfo(highestChannel);
	}

	@Override
	public ChannelRuntimeInfo[] searchConnectedChannelByPublishingType(String type) {
		List<ChannelRuntimeInfo> infos = new ArrayList<ChannelRuntimeInfo>();
		Collection<Channel> channels = ChannelGroup.getInstance().channels();
		for (Channel channel : channels) {
			if (channel.getPublisher().getType().equalsIgnoreCase(type) == true) {
				infos.add(getRuntimeInfo(channel));
			}
		}
		return infos.toArray(new ChannelRuntimeInfo[infos.size()]);
	}

	@Override
	public ChannelRuntimeInfo[] searchConnectedChannelsByUserId(String userid) {
		List<ChannelRuntimeInfo> infos = new ArrayList<ChannelRuntimeInfo>();
		Collection<Channel> channels = ChannelGroup.getInstance().channels();
		for (Channel channel : channels) {
			if (channel.getToken().getUserID().equals(userid) == true) {
				infos.add(getRuntimeInfo(channel));
			}
		}
		return infos.toArray(new ChannelRuntimeInfo[infos.size()]);
	}

	private ChannelRuntimeInfo getRuntimeInfo(Channel channel) {
		ChannelRuntimeInfo info = new ChannelRuntimeInfo();
		info.setUserID(channel.getToken().getUserID());
		info.setName(channel.getName());
		info.setStreamingMode(channel.getPublisher().getType());
		info.setState(channel.getState().toString());
		Collection<SubscriptionHandler> handlers = channel.getSubscriptions();
		String[] subscriptions = new String[handlers.size()];
		int i = 0;
		for (SubscriptionHandler subscriptionHandler : handlers) {
			subscriptions[i] = subscriptionHandler.getSubscribedTarget();
			i++;
		}
		info.setSubscriptions(subscriptions);

		info.setAverageUpdateProcessingTime(channel.getPublisher().getUpdateProcessingTime().getAverage());
		info.setPeakUpdateProcessingTime(channel.getPublisher().getUpdateProcessingTime().getHighestPeak());

		info.setAverageMarshallingTime(channel.getPublisher().getMarshallingTime().getAverage());
		info.setPeakMarshallingTime(channel.getPublisher().getMarshallingTime().getHighestPeak());

		info.setAverageStreamingTime(channel.getPublisher().getStreamingTime().getAverage());
		info.setPeakStreamingTime(channel.getPublisher().getStreamingTime().getHighestPeak());

		return info;
	}

	@Override
	public int resetStatsAll() {
		Collection<Channel> channels = ChannelGroup.getInstance().channels();
		int i = 0;
		for (Channel channel : channels) {
			StreamPublisher publisher = channel.getPublisher();
			publisher.getUpdateProcessingTime().reset();
			publisher.getMarshallingTime().reset();
			publisher.getStreamingTime().reset();
			i++;
		}
		return i;
	}

	@Override
	public int resetStatsByPublishingType(String type) {
		Collection<Channel> channels = ChannelGroup.getInstance().channels();
		int i = 0;
		for (Channel channel : channels) {
			StreamPublisher publisher = channel.getPublisher();
			if (publisher.getType().equalsIgnoreCase(type) == true) {
				publisher.getUpdateProcessingTime().reset();
				publisher.getMarshallingTime().reset();
				publisher.getStreamingTime().reset();
				i++;
			}
		}
		return i;
	}

	@Override
	public int resetStatsByUserId(String userid) {
		Collection<Channel> channels = ChannelGroup.getInstance().channels();
		int i = 0;
		for (Channel channel : channels) {
			if (channel.getToken().getUserID().equals(userid) == true) {
				StreamPublisher publisher = channel.getPublisher();
				publisher.getUpdateProcessingTime().reset();
				publisher.getMarshallingTime().reset();
				publisher.getStreamingTime().reset();
				i++;
			}
		}
		return i;
	}

}