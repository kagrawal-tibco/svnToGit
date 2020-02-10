/**
 * 
 */
package com.tibco.be.bemm.functions;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.service.management.exception.JMXConnClientException;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnClient;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnUtil;
import com.tibco.cep.security.authen.utils.MD5Hashing;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import java.io.IOException;

/**
 * @author anpatil
 *
 */
public class RemoteMetricsCollectorJmxImpl extends RemoteMetricsCollectorImpl {    //todo changed to public

    //JMX specific information
    protected String jmxURL;
    protected JMXConnector jmxc;
    protected MBeanServerConnection serverConnection;

    private String sysUser;
    private String sysPwd;
    private String clusterName;

    RemoteMetricsCollectorJmxImpl(String monitoredEntityName, COLLECTOR_TYPE type, String[] properties) {
        this(monitoredEntityName, type, properties, null);
    }

    RemoteMetricsCollectorJmxImpl(String monitoredEntityName, COLLECTOR_TYPE type, 
                                  String[] properties, String entityFqName) {
        super(type, properties, monitoredEntityName);
        this.clusterName = getClusterName(entityFqName);
    }

    private String getClusterName (String fqName) {
        if (fqName == null)
            return "";
        
        final String[] fqNameSplit = fqName.split(":");

        return (fqNameSplit.length < 2 || fqNameSplit[1] == null) ?
                "" : fqNameSplit[1].trim();
    }
    
    private void getCredentials() {
        //get hashed credentials
        final String user = (entityProperties.getHostName() == null ||
                             entityProperties.getHostName().trim().equals("")) ?
                             "127.0.0.1" : entityProperties.getHostName();

        final int port = entityProperties.getPort() <=0 ? JMXConnUtil.HOST.DEFAULT_PORT : entityProperties.getPort();

        this.sysUser = MD5Hashing.getMD5Hash(user);
        this.sysPwd = MD5Hashing.getMD5Hash(user + ":" + port);
    }

    protected boolean initConnection() {
        if (sysPwd == null || sysUser == null)
            getCredentials();

		if (collectorType == COLLECTOR_TYPE.JMX) {
            JMXConnClient jmcc =  new JMXConnClient(this.entityProperties.getHostName(),
                                    this.entityProperties.getPort(),
                                    sysUser, sysPwd,true);

            try {
                this.serverConnection = jmcc.connect();
            } catch (JMXConnClientException e) {
                logger.log(Level.ERROR,e,"could not connect to "+monitoredEntityName+" due to: " +e.getCause());
            }
		}
		else if (collectorType == COLLECTOR_TYPE.CACHE){
			//no initialization needed for cache connection
		}
		return true;
	}

    protected void closeConnection() {
        if (collectorType == COLLECTOR_TYPE.JMX){
            try {
                serverConnection = null;
                jmxc.close();
            } catch (Exception ignore) {
            } finally {
                jmxc = null;
                jmxURL = null;
            }
        }
    }

    protected MetricTypeHandler getHandler(String type) {
		try {
			MetricTypeHandler handler = typeHandlerFactory.getHandler(type);
			handler.setLogger(logger);
			handler.setMonitoredEntityName(monitoredEntityName);
			handler.setDelay(frequency);

			if (collectorType == COLLECTOR_TYPE.JMX){
				((JMXMetricTypeHandler) handler).setServerConnection(serverConnection);
			} else if (collectorType == COLLECTOR_TYPE.CACHE){
                if (handler instanceof CachedObjectsMetricHandler) //For Multicluster support for Coherence
                    ((CachedObjectsMetricHandler) handler).setClusterName(clusterName);
            }

			handler.init();
			return handler;
		} catch (IOException e) {
			if (logger.isEnabledFor(Level.DEBUG)){
				logger.log(Level.DEBUG, "could not initialize "+collectorType+" agent(s) for "+monitoredEntityName+"...",e);
			}
			else {
				logger.log(Level.WARN, "could not initialize "+collectorType+" agent(s) for "+monitoredEntityName+"...");
			}
			return null;
		}
	}

}