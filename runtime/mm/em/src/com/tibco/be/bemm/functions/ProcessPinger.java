/**
 * 
 */
package com.tibco.be.bemm.functions;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnClient;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnUtil;
import com.tibco.cep.security.authen.utils.MD5Hashing;

import javax.management.remote.JMXConnector;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.UndeclaredThrowableException;
import java.rmi.ConnectException;

class ProcessPinger extends JMXMetricTypeHandler{
	
	private TopologyEntityProperties entityProperties;
	
	private JMXConnector jconnector;
	
	private RuntimeMXBean runtimeMXBean;

    private String sysUser;
    private String sysPwd;

	ProcessPinger(TopologyEntityProperties entityProperties) {
		super();
		this.type = "Process Pinger";
		this.entityProperties = entityProperties;
        getCredentials();
	}

    private void getCredentials() {
        //get hashed credentials
        final String user = (entityProperties.getHostName() == null ||
                             entityProperties.getHostName().trim().equals("")) ?
                             JMXConnUtil.HOST.DEFAULT_IP : entityProperties.getHostName();

        final int port = entityProperties.getPort() <=0 ? JMXConnUtil.HOST.DEFAULT_PORT : entityProperties.getPort();

        this.sysUser = MD5Hashing.getMD5Hash(user);
        this.sysPwd = MD5Hashing.getMD5Hash(user+":"+port);
    }


    @Override
	protected void init() throws IOException {
        JMXConnClient jmcc =  new JMXConnClient(this.entityProperties.getHostName(),
                                    this.entityProperties.getPort(),
                                    sysUser, sysPwd,true);
        try {
            this.serverConnection = jmcc.connect();
        } catch (Exception e) {
            logger.log(Level.ERROR, e, "could not connect to " + monitoredEntityName + " due to: " + e.getCause());
            throw new RuntimeException(e);
        }

        if(serverConnection != null)
		    runtimeMXBean = ManagementFactory.newPlatformMXBeanProxy(serverConnection, ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);
	}
	
	public boolean ping(){
		int i = 3;
		boolean success = false;
		while (success == false && i > 0) {
			try {
				runtimeMXBean.getUptime();
				success = true;
			} catch (UndeclaredThrowableException e) {
				if (e.getUndeclaredThrowable() instanceof ConnectException) {
					i--;
					success = false;
				}
			}
		}
		return success;
	}
	
	public void closeConnection(){
		try {
			serverConnection = null;
			this.jconnector.close();
		} catch (Exception e) {
		}
		finally{
			this.jconnector = null;
		}
	}

	@Override
	protected SimpleEvent[] populate(EventCreator eventCreator) throws IOException {
		throw new UnsupportedOperationException("populate");
	}
}