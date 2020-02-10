/**
 * 
 */
package com.tibco.cep.bemm.common.jmx;

import javax.management.MBeanServerConnection;

/**
 * @author ssinghal
 *
 */
public interface Callback {
	
	public Object perform(MBeanServerConnection conn) throws Exception;

}
