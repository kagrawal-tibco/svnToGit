package com.tibco.cep.dashboard.psvr.streaming;

import com.tibco.cep.dashboard.common.utils.DateTimeUtils;
import com.tibco.cep.dashboard.config.PropertyKey;
import com.tibco.cep.dashboard.config.PropertyKeys;
import com.tibco.cep.dashboard.config.PropertyKey.DATA_TYPE;

public interface StreamingProperties extends PropertyKeys {

	//enable/disable streaming
	static final PropertyKey STREAMING_ENABLED = new PropertyKey(true,"streaming.enabled","Enables streaming", PropertyKey.DATA_TYPE.Boolean, Boolean.TRUE);

	//update frequency when using http poll
	static final PropertyKey PULL_STREAMING_FREQUENCY = new PropertyKey(true, "updates.poll.frequency", "The time delay between two consecutive update polls", PropertyKey.DATA_TYPE.Long, new Long(5*DateTimeUtils.SECOND));

	static final PropertyKey STREAMING_PORT = new PropertyKey("streamingport","The port which the agent will use for streaming data",PropertyKey.DATA_TYPE.Integer,new Integer(8787));

	//streaming publishers

	final static PropertyKey STREAMING_MODE = new PropertyKey("streaming.mode","Defines which mode to use for streaming", PropertyKey.DATA_TYPE.String,"steady");

	//streaming thread pool

	public static final PropertyKey STREAMING_THREAD_POOL_COUNT = new PropertyKey("streaming.timer.threadpool.count", "Determines the number of threads in the streaming timer thread pool", DATA_TYPE.Integer, new Integer(10));

	//steady streaming properties

	static final PropertyKey BATCHING_PERIOD = new PropertyKey("batch.streaming.period", "The time delay between two consecutive batch stream bursts", DATA_TYPE.Long, new Long(DateTimeUtils.SECOND));

	static final PropertyKey BATCHING_THRESHOLD = new PropertyKey("batch.content.threshold", "The number of packets to send in one burst", DATA_TYPE.Integer, new Integer(4));

	//period streaming properties (also used by steady streaming)

	static final PropertyKey STREAMING_PERIOD = new PropertyKey("periodic.streaming.period", "The time delay between two consecutive stream bursts", DATA_TYPE.Long, new Long(5*DateTimeUtils.SECOND));

	static final PropertyKey STREAMING_BUFFER_SIZE = new PropertyKey("streaming.buffer.capacity", "Defines the capacity of the buffer which holds subscription(s) which are to be processed and streamed", PropertyKey.DATA_TYPE.Integer, new Integer(512));

	static final PropertyKey AUTO_SUBSCRIBE_ENABLED = new PropertyKey(true,"datasource.auto.subscribe","Enables auto subscribtion for data sources", PropertyKey.DATA_TYPE.Boolean, Boolean.TRUE);
}
