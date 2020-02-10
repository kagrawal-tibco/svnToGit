package com.tibco.cep.bemm.common.pool.jsch;

import com.tibco.cep.bemm.common.pool.GroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.PooledConnectionConfig;
import com.tibco.cep.bemm.model.ServiceInstance;

/**
 * JSch GroupJob Execution context.
 * 
 * @author moshaikh
 */
public class JSchGroupJobExecutionContext implements GroupJobExecutionContext, PooledConnectionConfig {

	/**
	 * ServiceInstance which holds all the data required for a new connection as
	 * well to execute a GroupJob.
	 */
	private ServiceInstance serviceInstance;
	/**
	 * SSH Config details
	 */
	private SshConfig sshConfig;

	public JSchGroupJobExecutionContext(ServiceInstance serviceInstance, SshConfig sshConfig) {
		this.serviceInstance = serviceInstance;
		this.sshConfig = sshConfig;
	}

	public ServiceInstance getServiceInstance() {
		return this.serviceInstance;
	}

	public SshConfig getSshConfig() {
		return this.sshConfig;
	}

	@Override
	public String getConnectionIdentifierString() {
		return "JSCH_" + sshConfig.getHostIp() + "_" + sshConfig.getPort() + "_" + sshConfig.getUserName();
	}

}
