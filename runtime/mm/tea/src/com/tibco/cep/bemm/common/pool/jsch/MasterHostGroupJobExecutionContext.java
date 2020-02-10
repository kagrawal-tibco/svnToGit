/**
 * 
 */
package com.tibco.cep.bemm.common.pool.jsch;

import com.tibco.cep.bemm.common.pool.GroupJobExecutionContext;
import com.tibco.cep.bemm.model.MasterHost;

/**
 * Group job execution context for master host
 * 
 * @author dijadhav
 *
 */
public class MasterHostGroupJobExecutionContext implements GroupJobExecutionContext {

	/**
	 * Master host object
	 */
	private MasterHost masterHost;

	/**
	 * @param masterHost
	 */
	public MasterHostGroupJobExecutionContext(MasterHost masterHost) {
		super();
		this.masterHost = masterHost;
	}

	/**
	 * @return the masterHost
	 */
	public MasterHost getMasterHost() {
		return masterHost;
	}

}
