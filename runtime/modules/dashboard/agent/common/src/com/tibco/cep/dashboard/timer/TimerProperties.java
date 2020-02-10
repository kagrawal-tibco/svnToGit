package com.tibco.cep.dashboard.timer;

import com.tibco.cep.dashboard.config.PropertyKey;
import com.tibco.cep.dashboard.config.PropertyKeys;
import com.tibco.cep.dashboard.config.PropertyKey.DATA_TYPE;

public interface TimerProperties extends PropertyKeys {

	public static final PropertyKey THREAD_POOL_COUNT = new PropertyKey("timer.threadpool.count", "Determines the number of threads in the timer thread pool", DATA_TYPE.Integer, new Integer(2));

}
