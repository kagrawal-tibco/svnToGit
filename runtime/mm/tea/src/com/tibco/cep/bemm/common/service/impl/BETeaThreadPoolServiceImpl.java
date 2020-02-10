/**
 * 
 */
package com.tibco.cep.bemm.common.service.impl;

import java.util.Properties;
import java.util.concurrent.Future;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.BETeaThreadPoolService;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.WorkItem;
import com.tibco.rta.common.service.WorkItemService;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;

/**
 * Implementation of thread pool service
 * 
 * @author dijadhav
 *
 */
public class BETeaThreadPoolServiceImpl  implements BETeaThreadPoolService {
	private MessageService messageService;
	/**
	 * Logger Object
	 */
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BETeaThreadPoolService.class);
	/**
	 * Thread pool
	 */
	protected WorkItemService threadPool;

	/**
	 * Default Constructor 
	 */
	public BETeaThreadPoolServiceImpl() throws ObjectCreationException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.bemm.common.service.BETEAThreadPoolService#init(java.util.
	 * Properties)
	 */
	@Override
	public void init(Properties configuration) throws Exception {
		threadPool = ServiceProviderManager.getInstance().newWorkItemService("be-tea-thread-pool");
		threadPool.init(ServiceProviderManager.getInstance().getConfiguration());
		messageService = BEMMServiceProviderManager.getInstance().getMessageService();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Future<WorkItem> processJob(WorkItem job) {
		return threadPool.addWorkItem(job);
	}

	@Override
	public void stop() throws Exception {
		LOGGER.log(Level.INFO, messageService.getMessage(MessageKey.STOPPING_BE_TEA_THREADPOOL));
		try {
			if (threadPool != null) {
				threadPool.stop();
			}
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.STOPPING_BE_TEA_THREADPOOL_ERROR));
		}

	}

	@Override
	public void start() throws Exception {
	}

	@Override
	public void suspend() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isStarted() {
		// TODO Auto-generated method stub
		return false;
	}
}
