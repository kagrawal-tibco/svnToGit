package com.tibco.cep.runtime.service.management.jmx.connectors;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.management.exception.JMXConnClientException;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 1/26/11
 * Time: 6:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class JMXConnClient {
    private static final Logger logger= LogManagerFactory.getLogManager().getLogger(JMXConnClient.class);
    private String host;
    private int port;
    private Map<String, Object> env;
    private JMXConnector jmxConnector;
    private MBeanServerConnection mbsc;

    public JMXConnClient(String host, int port,
                         String user, String pwd,
                         boolean invokeGetters) {

        this.host = host;
        this.port = port;

        final String[] credentials = {user, pwd};

        this.env = new HashMap<String, Object>();
        this.env.put(JMXConnector.CREDENTIALS, credentials);

        if (invokeGetters)
            //Necessary to allow the invocation of getter methods
            System.setProperty(JMXConnUtil.JAVA_PROPERTIES.JMX_INVOKE_GETTERS,"true");
    }

    //If security is enabled the env Map must contain String[] credentials = {user, pwd} associated with the key JMXConnector.CREDENTIALS
    public JMXConnClient(String host, int port,
                         Map<String, Object> env,
                         boolean invokeGetters) {
        this.host = host;
        this.port = port;
        this.env = env;

        if (invokeGetters)
            //Necessary to allow the invocation of getter methods
            System.setProperty(JMXConnUtil.JAVA_PROPERTIES.JMX_INVOKE_GETTERS,"true");
    }

    /** Returns an MBeanServerConnection object representing a remote MBean server.
     * @return Non null MBeanServerConnection if no exception occurs */
    public MBeanServerConnection getMBeanServerConnection() throws JMXConnClientException {
        if (mbsc!=null)
            return mbsc;
        else {
            try {
                return mbsc = getJMXConnector().getMBeanServerConnection();
            } catch (IOException e) {
                final String msg = String.format("Could not establish MBeanServerConnection to " +
                                                        "connector server @%s:%s.",host, port);
                logger.log(Level.DEBUG, msg);
                throw new JMXConnClientException(msg, e);
            }
        }
    }

    /** Establishes a client connection to the remote connector server
     * @return Non null JMXConnector if no exception occurs
     * @throws JMXConnClientException */
    public JMXConnector getJMXConnector() throws JMXConnClientException {
        try {
            if (jmxConnector != null)
                return jmxConnector;
            else {
                JMXServiceURL url =  new JMXServiceURL(JMXConnUtil.buildJXMUrlStr(host, port));
                //We need to disable these loggers in the JVM code because they were printing some misleading warning information in
                //the MM log. It was occurring when shutting down be-engines form the MM UI. The engine was shutting down,
                //hence the connector server was shutting down... but the JMX client connection was still active
                //for a bit until removed, and it was causing these warning logs to get printed.
                java.util.logging.Logger.getLogger("javax.management.remote.rmi").setLevel(java.util.logging.Level.OFF);
                java.util.logging.Logger.getLogger("javax.management.remote.misc").setLevel(java.util.logging.Level.OFF);

                return jmxConnector = JMXConnectorFactory.connect(url, env);
            }
        } catch (MalformedURLException mfe) {
            final String msg = "Malformed URL. " + mfe.getMessage();
            logger.log(Level.ERROR, mfe, msg);
            throw new JMXConnClientException(msg, mfe);
        }
        catch (IOException e) {
            final String msg = String.format("Could not establish client connection" +
                                                    " to JMX connector server @%s:%s.", host, port);

            logger.log(Level.DEBUG, msg);
            throw new JMXConnClientException(msg, e);
        }
    }

    public MBeanServerConnection connect() throws JMXConnClientException {
        return getMBeanServerConnection();
    }
    
    public void setLoggerLevel(Level level) {
        if (level == null || Level.valueOf(level.toString()) == null)
            return;

        logger.setLevel(level);
    }
}
