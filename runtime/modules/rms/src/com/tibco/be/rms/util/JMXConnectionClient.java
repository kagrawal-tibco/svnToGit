/**
 * 
 */
package com.tibco.be.rms.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * Class to create/initiate connection with the MBean Server. the connection can be secure or unsecure, this depends 
 * on whether the credentials are passed on not.
 * 
 * @author hlouro
 * @author vpatil
 */
public class JMXConnectionClient {
	private String host;
    private int port;
    private Map<String, Object> env;
    private JMXConnector jmxConnector;
    private MBeanServerConnection mbsc;

    /**
     * Base Constructor
     * 
     * @param host
     * @param port
     * @param user
     * @param pwd
     * @param invokeGetters
     */
    public JMXConnectionClient(String host, int port,
                         String user, String pwd,
                         boolean invokeGetters) {

        this.host = host;
        this.port = port;

        final String[] credentials = {user, pwd};

        this.env = new HashMap<String, Object>();
        this.env.put(JMXConnector.CREDENTIALS, credentials);

        if (invokeGetters)
            //Necessary to allow the invocation of getter methods
            System.setProperty(JMXConnectionUtil.JAVA_PROPERTIES.JMX_INVOKE_GETTERS, "true");
    }

    /**
     * If security is enabled the env Map must contain String[] credentials = {user, pwd} associated with the 
     * key JMXConnector.CREDENTIALS
     * 
     * @param host
     * @param port
     * @param env
     * @param invokeGetters
     */
    public JMXConnectionClient(String host, int port,
                         Map<String, Object> env,
                         boolean invokeGetters) {
        this.host = host;
        this.port = port;
        this.env = env;

        if (invokeGetters)
            //Necessary to allow the invocation of getter methods
            System.setProperty(JMXConnectionUtil.JAVA_PROPERTIES.JMX_INVOKE_GETTERS, "true");
    }

    /**
     * Returns an MBeanServerConnection object representing a remote MBean server.
     * 
     * @return Non null MBeanServerConnection if no exception occurs
     * @throws Exception
     */
    public MBeanServerConnection getMBeanServerConnection() throws Exception {
        if (mbsc!=null)
            return mbsc;
        else {
            try {
                return mbsc = getJMXConnector().getMBeanServerConnection();
            } catch (IOException e) {
                final String msg = String.format("Could not establish MBeanServerConnection to " +
                        "connector server @%s:%s.", host, port);
                System.out.println(msg);
                throw new Exception(msg, e);
            }
        }
    }

    /** 
     * Establishes a client connection to the remote connector server
     * 
     * @return Non null JMXConnector if no exception occurs
     * @throws Exception 
    */
    public JMXConnector getJMXConnector() throws Exception {
        try {
            if (jmxConnector != null)
                return jmxConnector;
            else {
                JMXServiceURL url =  new JMXServiceURL(JMXConnectionUtil.buildJXMUrlStr(host, port));
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
            System.out.printf("%s %s", mfe.getMessage(), msg);
            throw new Exception(msg, mfe);
        }
        catch (IOException e) {
            final String msg = String.format("Could not establish client connection" +
                    " to JMX connector server @%s:%s.", host, port);

            System.out.println(msg);
            throw new Exception(msg, e);
        }
    }

    public MBeanServerConnection connect() throws Exception {
        return getMBeanServerConnection();
    }
}
