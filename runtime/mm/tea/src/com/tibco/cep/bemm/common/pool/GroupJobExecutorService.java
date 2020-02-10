package com.tibco.cep.bemm.common.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.rta.common.service.StartStopService;
import com.tibco.rta.common.service.WorkItem;
import com.tibco.rta.common.service.WorkItemService;

/**
 * An executor service for GroupOpJobs.
 * @author moshaikh
 */
public class GroupJobExecutorService implements StartStopService {
	
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupJobExecutorService.class);
	
	private final WorkItemService workItemService;
	
	/**
	 * The WorkItemService to use to submit jobs.
	 * @param workItemService
	 */
	public GroupJobExecutorService(WorkItemService workItemService) {
		this.workItemService = workItemService;
	}
	
	/**
	 * Executes the job multiple times, once for each execution context. The method returns only when results are received from all the executions of the job.
	 * @param job
	 * @param executionContexts
	 * @return List of returned values as objects
	 */
	public List<Object> submitJobs(final GroupJob<Object> job, List<GroupJobExecutionContext> executionContexts) {
		if (executionContexts == null || executionContexts.isEmpty()) {
			return new ArrayList<Object>();
		}
		
		List<Future<Object>> futures = new ArrayList<Future<Object>>(executionContexts.size());
		List<Object> results = new ArrayList<>(executionContexts.size());
		
		LOGGER.log(Level.DEBUG, "Submitting a batch of " + executionContexts.size() + " Group jobs");
		
		for (final GroupJobExecutionContext executionContext : executionContexts) {
			Future<Object> future = workItemService.addWorkItem(new WorkItem<Object>() {
				@Override
				public Object call() throws Exception {
					Object future = job.callUsingExecutionContext(executionContext);
					return future;
				}

				@Override
				public Object get() {
					return null;
				}
			});
			futures.add(future);
		}
		for (Future<Object> future : futures) {
			try {
				results.add(future.get());//TODO: should use the api with timeout
			} catch(Exception e) {
				results.add(e);
			}
		}
		return results;
	}

	@Override
	public void init(Properties configuration) throws Exception {
		workItemService.init(configuration);
	}

	@Override
	public void start() throws Exception {
		workItemService.start();
	}

	@Override
	public void stop() throws Exception {
		workItemService.stop();
	}

	@Override
	public void suspend() {
		workItemService.suspend();
	}

	@Override
	public void resume() {
		workItemService.resume();
	}

	@Override
	public boolean isStarted() {
		return workItemService.isStarted();
	}
}
