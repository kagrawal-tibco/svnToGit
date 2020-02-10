package com.tibco.cep.driver.as.internal.consumptionmodes.entrybrowser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.tibco.as.space.ASException;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.Browser;
import com.tibco.cep.driver.as.internal.consumptionmodes.IASConsumptionMode;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.util.SystemProperty;

public class EntryBrowserMainLoop implements Runnable {

	private static final int	MAX_POOL_SIZE = 100;

	private IASConsumptionMode	consumptionMode;
	private Browser				browser;
	private Logger				logger;
	private ExecutorService		executor;
	private volatile boolean	shutdown;
	private Space               space;

	public EntryBrowserMainLoop(
			IASConsumptionMode consumptionMode,
			Browser browser,
			Logger logger,
			Metaspace metaspace,
			Space space,
			int consumptionModeHashcode) {
		this(consumptionMode, browser, logger, metaspace, space, consumptionModeHashcode, 
		    Integer.parseInt(System.getProperty(SystemProperty.AS_CHANNEL_POOLSIZE.getPropertyName(), String.valueOf(MAX_POOL_SIZE))));
	}

	public EntryBrowserMainLoop(
			IASConsumptionMode consumptionMode,
			Browser browser,
			Logger logger,
			Metaspace metaspace,
            Space space,
			int consumptionModeHashcode,
			int maxPoolSize) {
		this.consumptionMode = consumptionMode;
		this.browser = browser;
		this.logger = logger;
		this.space = space;
		this.executor = new ThreadPoolExecutor(
				0, maxPoolSize,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                new TupleDispatcherThreadFactory(metaspace.getName(), space.getName(), consumptionModeHashcode),
                new ThreadPoolExecutor.CallerRunsPolicy());
        logger.log(Level.INFO, "AS Channel created '%s' space executor thread-pool-size=%s with browser-consumption mode=%s", 
                this.space.getName(), maxPoolSize, consumptionMode);
	}

	public void shutdown() {
		shutdown = true;
	}

	@Override
	public void run() {
		while (!shutdown) {
			try {
				Tuple tuple = browser.next();
				if (null != tuple) {
					Runnable task = new TupleDispatchingTask(consumptionMode, tuple, logger);
					executor.submit(task);
				}
			}
			catch (ASException asEx) {
				if (!shutdown) {
					logger.log(Level.ERROR, asEx, "AS Channel for %s space entry-browser main loop failed.", space.getName());
					asEx.printStackTrace();
				}
			}
		}
	}
}
