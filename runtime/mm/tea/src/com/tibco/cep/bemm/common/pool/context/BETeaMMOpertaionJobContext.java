
package com.tibco.cep.bemm.common.pool.context;

import com.tibco.cep.bemm.common.pool.GroupJobExecutionContext;
import com.tibco.cep.bemm.model.ServiceInstance;

/**
 * Context for MM opertaion
 * 
 * @author dijadhav
 *
 */
public class BETeaMMOpertaionJobContext implements GroupJobExecutionContext {
	private ServiceInstance serviceInstance;
	private int agentId;

	/**
	 * @param serviceInstance
	 * @param agentId
	 */
	public BETeaMMOpertaionJobContext(ServiceInstance serviceInstance, int agentId) {
		super();
		this.serviceInstance = serviceInstance;
		this.agentId = agentId;
	}

	public ServiceInstance getServiceInstance() {
		return serviceInstance;
	}

	public int getAgentId() {
		return agentId;
	}

}
