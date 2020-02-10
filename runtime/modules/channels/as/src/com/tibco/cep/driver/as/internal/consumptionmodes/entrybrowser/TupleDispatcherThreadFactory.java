package com.tibco.cep.driver.as.internal.consumptionmodes.entrybrowser;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

class TupleDispatcherThreadFactory implements ThreadFactory {

	private static final String	_NAME_PRFEX	= "EntryBrowser-TupleDispatcherThread-";
	private final String metaspaceName;
	private final String spaceName;
	private final int consumptionModeHashCode;
	private final AtomicInteger	threadNumber	= new AtomicInteger(1);

	TupleDispatcherThreadFactory(String metaspaceName, String spaceName, int consumptionModeHashCode) {
		this.metaspaceName = metaspaceName;
		this.spaceName = spaceName;
		this.consumptionModeHashCode = consumptionModeHashCode;
	}

    @Override
    public Thread newThread(Runnable task) {
        Thread newThread = new Thread(task,
        		_NAME_PRFEX
        		+ metaspaceName +"." + spaceName + "@" + consumptionModeHashCode
        		+ "-" + threadNumber.getAndIncrement());
        newThread.setDaemon(true);
        return newThread;
    }
}