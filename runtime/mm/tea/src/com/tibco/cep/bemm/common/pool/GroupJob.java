package com.tibco.cep.bemm.common.pool;

/**
 * A Job that needs to be executed multiple times with different settings.
 * @author moshaikh
 *
 * @param <V> The Job result type.
 */
public interface GroupJob<V> {
	
	/**
	 * Execute the job using the passed ExecutionContext.
	 * 
	 * @param executionContext
	 * @return The result of job execution
	 * @throws Exception
	 */
	public V callUsingExecutionContext(GroupJobExecutionContext executionContext) throws Exception;
}
