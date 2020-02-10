/**
 * 
 */
package com.tibco.be.ws.jmx;

/**
 * @author vpatil
 *
 */
public interface JMXHotDeploy {

	/**
	 * Deploy one DT or multiple DT's associated to a VRF
	 * 
	 * @param host
	 * @param port
	 * @param user
	 * @param pwd
	 * @param cn
	 * @param vrfURI
	 * @param implName
	 * @param inMemory
	 * @return
	 */
	public boolean deployVRF(String host,
            int port,
            String user,
            String pwd,
            String cn,
            String vrfURI,
            String implName,
            boolean inMemory);
	
	/**
	 * Undeploy an DT
	 * 
	 * @param host
	 * @param port
	 * @param user
	 * @param pwd
	 * @param cn
	 * @param vrfURI
	 * @param implName
	 * @param inMemory
	 * @return
	 */
	public boolean unDeployVRF(String host,
            int port,
            String user,
            String pwd,
            String cn,
            String vrfURI,
            String implName,
            boolean inMemory);
	
	/**
	 * Deploy one RTI or multiple RTI's associated to RT
	 * 
	 * @param host
	 * @param port
	 * @param user
	 * @param pwd
	 * @param agentName
	 * @param projectName
	 * @param ruleTemplateInstanceFQN
	 * @param inMemory
	 * @return
	 */
	public boolean deployRTI(String host,
            int port,
            String user,
            String pwd,
            String agentName,
            String projectName,
            String ruleTemplateInstanceFQN,
            boolean inMemory);
	
	/**
	 * Undeploy an RTI
	 * 
	 * @param host
	 * @param port
	 * @param user
	 * @param pwd
	 * @param agentName
	 * @param projectName
	 * @param ruleTemplateInstanceFQN
	 * @param inMemory
	 * @return
	 */
	public boolean unDeployRTI(String host,
            int port,
            String user,
            String pwd,
            String agentName,
            String projectName,
            String ruleTemplateInstanceFQN,
            boolean inMemory);	
}
