/**
 * 
 */
package com.tibco.be.ws.jmx.impl;

import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;

import com.tibco.be.rms.util.JMXConnectionClient;
import com.tibco.be.ws.jmx.JMXHotDeploy;

/**
 * @author vpatil
 *
 */
public class NativeJMXHotDeployImpl implements JMXHotDeploy {
	
	private static final String OBJ_NAME_PATTERN =
            "com.tibco.be:service=Cluster,name=%s$cluster";
	
	private static final String OBJ_NAME_PATTERN_IM =
            "com.tibco.be:dir=Hot Deploy,name=Decision Table Deployer";
    
    private static final String RT_OBJ_NAME_PATTERN =
            "com.tibco.be:service=RuleTemplateDeployer,name=RuleTemplateInstanceDeployer";
    
    private static final String RT_OBJ_NAME_PATTERN_IM =
            "com.tibco.be:dir=Hot Deploy,Name=Rule Template Instance Deployer";

    private static final String LOAD_AND_DEPLOY = "loadAndDeploy";
    private static final String VRF_UNDEPLOY = "unloadClass";
    
    private static final String RT_LOAD_AND_DEPLOY_INSTANCES = "loadAndDeployRuleTemplateInstances";
    private static final String RT_LOAD_AND_DEPLOY_INSTANCE = "loadAndDeployRuleTemplateInstance";
    private static final String RT_UNDEPLOY = "unDeployRuleTemplateInstances";

	/* (non-Javadoc)
	 * @see com.tibco.be.ws.jmx.JMXHotDeploy#deployVRF(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public boolean deployVRF(String host, int port, String user, String pwd, String cn, String vrfURI, String implName,
			boolean inMemory) {
		boolean bSuccess = false;
		
		MBeanServerConnection msc;
		JMXConnectionClient jmxcc = null;

		try {
			final String[] credentials = {user, pwd};

			Map<String, Object> env = new HashMap<String, Object>();

			env.put(JMXConnector.CREDENTIALS, credentials);

			jmxcc = new JMXConnectionClient(host, port, env, true);
			msc = jmxcc.connect();

			String objPattern = (inMemory) ? OBJ_NAME_PATTERN_IM : String.format(OBJ_NAME_PATTERN, cn);
			ObjectName on = new ObjectName(objPattern);

			if (vrfURI == null && implName == null) {
				msc.invoke(on, LOAD_AND_DEPLOY, null, null);
			} else if (vrfURI!= null && !vrfURI.trim().isEmpty() &&
					implName != null && !implName.trim().isEmpty()) {
				msc.invoke(on,
						LOAD_AND_DEPLOY,
						new String[]{vrfURI, implName},
						new String[]{String.class.getName(),
								String.class.getName()} );

			}
			bSuccess = true;
		} catch(Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				jmxcc.getJMXConnector().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return bSuccess;
	}

	/* (non-Javadoc)
	 * @see com.tibco.be.ws.jmx.JMXHotDeploy#unDeployVRF(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public boolean unDeployVRF(String host, int port, String user, String pwd, String cn, String vrfURI,
			String implName, boolean inMemory) {
		boolean bSuccess = false;
		
		MBeanServerConnection msc;
        JMXConnectionClient jmxcc = null;

        try {
            final String[] credentials = {user, pwd};

            Map<String, Object> env = new HashMap<String, Object>();

            env.put(JMXConnector.CREDENTIALS, credentials);

            jmxcc = new JMXConnectionClient(host, port, env, true);
            msc = jmxcc.connect();

            String objPattern = (inMemory) ? OBJ_NAME_PATTERN_IM : String.format(OBJ_NAME_PATTERN, cn);
            ObjectName on = new ObjectName(objPattern);

            if (vrfURI!= null && !vrfURI.trim().isEmpty() &&
                       implName != null && !implName.trim().isEmpty()) {
                msc.invoke(on,
                           VRF_UNDEPLOY,
                           new String[]{vrfURI, implName},
                           new String[]{String.class.getName(),
                                        String.class.getName()} );
                bSuccess = true;

            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                jmxcc.getJMXConnector().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		return bSuccess;
	}

	/* (non-Javadoc)
	 * @see com.tibco.be.ws.jmx.JMXHotDeploy#deployRTI(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public boolean deployRTI(String host, int port, String user, String pwd, String agentName, String projectName,
			String ruleTemplateInstanceFQN, boolean inMemory) {
		boolean bSuccess = false;
		
		MBeanServerConnection msc;
    	JMXConnectionClient jmxcc = null;

        try {
        	final String[] credentials = {user, pwd};

            Map<String, Object> env = new HashMap<String, Object>();
            env.put(JMXConnector.CREDENTIALS, credentials);
            
            jmxcc = new JMXConnectionClient(host, port, env, true);
            msc = jmxcc.connect();

            String objPattern = (inMemory) ? RT_OBJ_NAME_PATTERN_IM : RT_OBJ_NAME_PATTERN;
            ObjectName on = new ObjectName(objPattern);
            if (agentName != null && !agentName.isEmpty()) {
            	if (projectName != null && !projectName.isEmpty() && ruleTemplateInstanceFQN != null && !ruleTemplateInstanceFQN.isEmpty()) {
            		msc.invoke(on, 
            				RT_LOAD_AND_DEPLOY_INSTANCE, 
            				new String[]{agentName, projectName, ruleTemplateInstanceFQN},
            				new String[]{String.class.getName(),
			            				 String.class.getName(),
			            				 String.class.getName()});
            	} else {
            		msc.invoke(on, 
            				RT_LOAD_AND_DEPLOY_INSTANCES, 
            				new String[]{agentName},
            				new String[]{String.class.getName()});
            	}
            	bSuccess = true;
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                jmxcc.getJMXConnector().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return bSuccess;
	}

	/* (non-Javadoc)
	 * @see com.tibco.be.ws.jmx.JMXHotDeploy#unDeployRTI(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public boolean unDeployRTI(String host, int port, String user, String pwd, String agentName, String projectName,
			String ruleTemplateInstanceFQN, boolean inMemory) {
		boolean bSuccess = false;
		
		MBeanServerConnection msc;
    	JMXConnectionClient jmxcc = null;

        try {
        	final String[] credentials = {user, pwd};

            Map<String, Object> env = new HashMap<String, Object>();
            env.put(JMXConnector.CREDENTIALS, credentials);
            
            jmxcc = new JMXConnectionClient(host, port, env, true);
            msc = jmxcc.connect();

            String objPattern = (inMemory) ? RT_OBJ_NAME_PATTERN_IM : RT_OBJ_NAME_PATTERN;
            ObjectName on = new ObjectName(objPattern);
            if (agentName != null && !agentName.isEmpty()) {
            	if (projectName != null && !projectName.isEmpty() && ruleTemplateInstanceFQN != null && !ruleTemplateInstanceFQN.isEmpty()) {
            		msc.invoke(on, 
            				RT_UNDEPLOY, 
            				new String[]{agentName, projectName, ruleTemplateInstanceFQN},
            				new String[]{String.class.getName(),
			            				 String.class.getName(),
			            				 String.class.getName()});
            	} else {
            		msc.invoke(on, 
            				RT_UNDEPLOY, 
            				new String[]{agentName},
            				new String[]{String.class.getName()});
            	}
            	bSuccess = true;
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                jmxcc.getJMXConnector().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		return bSuccess;
	}

}
