/**
 * 
 */
package com.tibco.be.bemm.functions;

import javax.management.MBeanServerConnection;


/**
 * @author anpatil
 *
 */
public abstract class JMXMetricTypeHandler extends MetricTypeHandler {
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

	protected MBeanServerConnection serverConnection;
	
	JMXMetricTypeHandler() {
		super();
	}

	void setServerConnection(MBeanServerConnection serverConnection) {
		this.serverConnection = serverConnection;
	}
	
}