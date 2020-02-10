package com.tibco.be.monitor.thread.util;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.rmi.RMIConnectorServer;
import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;

/**
 * This class provides various JMX related utility methods.
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
public class JMXUtil {

    private static final Logger logger = Logger.getLogger(
            JMXUtil.class.getName());
    private static ObjectName mxBeanName;
    private static final int defaultPort = 1090;
    private static final String SIMPLE_JMX_URL_PREFIX =
            "service:jmx:rmi:///jndi/rmi://";
    private static final String SSL_JMX_URL_PREFIX =
            "service:jmx:rmi:///jndi/rmi://";
    private static final String SIMPLE_JMX_URL_SUFFIX = "/jmxrmi";
    private static final String SSL_JMX_URL_SUFFIX = "/server";
    private static final String JMX_URL_DELIMIT = ":";

    static {
        try {
            mxBeanName = new ObjectName(
                    ManagementFactory.THREAD_MXBEAN_NAME);
        } catch (MalformedObjectNameException ex) {
            logger.log(Level.SEVERE, "MX Bean name not valid.", ex);
        }
    }

    /**
     * Returns the threadmxBean for the given connector
     * @param connector
     * @return ThreadMXBean
     * @throws IOException
     */
    public static final ThreadMXBean getThreadMXBean(
            JMXConnector connector) throws IOException {
        MBeanServerConnection serverConnection = getServerConnection(
                connector);
        logger.info("Creating ThreadMXBean proxy");
        return JMX.newMXBeanProxy(serverConnection, mxBeanName,
                ThreadMXBean.class);
    }

    private static final MBeanServerConnection getServerConnection(
            JMXConnector connector) throws IOException {
        MBeanServerConnection serverConnection =
                connector.getMBeanServerConnection();
        logger.info("Created JMX Connection with ID:"
                + connector.getConnectionId());
        return serverConnection;
    }

    /**
     * Returns the JMX Connector for the given host and port.
     * @param host
     * @param port
     * @return JMXConnector
     * @throws IOException
     */
    public static final JMXConnector getJMXConnector(
            String host, int port) throws IOException {
        JMXServiceURL url = new JMXServiceURL(constructSimpleJXMUrl(host, port));
        logger.info("Connecting to JMX Server:" + url);
        return JMXConnectorFactory.connect(url);
    }

    public static final JMXConnector getJMXConnector(
            String host, int port, String user, String pass) throws IOException {
        JMXServiceURL url = new JMXServiceURL(constructSimpleJXMUrl(host, port));
        logger.info("Connecting to JMX Server:" + url);
        String[] credentials = new String[] {user, pass};
        Map<String, String[]> env = new HashMap<String, String[]>();
        env.put(JMXConnector.CREDENTIALS, credentials);
        return JMXConnectorFactory.connect(url, env); 
    }

    /**
     * Returns the JMXConnectorServer for the given port.
     * @param port
     * @return JMXConnectorServer
     * @throws Exception
     */
    public static final JMXConnectorServer createSimpleJMXServer(int port) throws Exception {
        createRegistry(port);
        MBeanServer mbeanServer = getMBeanServer();

        // Environment map.
        logger.log(Level.INFO, "Initialize the environment map");
        Map<String, Object> env = new HashMap<String, Object>();

        // Create an RMI connector server.
        // As specified in the JMXServiceURL the RMIServer stub will be
        // registered in the RMI registry running in the local host on
        // specified port with the name "jmxrmi". This is the same name the
        // out-of-the-box management agent uses to register the RMIServer
        // stub too.
        //
        logger.log(Level.INFO, "Create a RMI connector server");
        JMXServiceURL jmxUrl = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:"
                + port + "/jmxrmi");
        JMXConnectorServer connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(
                jmxUrl, env, mbeanServer);

        // Start the RMI connector server.
        logger.log(Level.INFO, "Start the RMI connector server");
        connectorServer.start();
        return connectorServer;
    }

    public static final JMXConnectorServer createSSLJMXServer(int port) throws Exception {
        createRegistry(port);
        MBeanServer mbeanServer = getMBeanServer();
        // Environment map.
        logger.log(Level.INFO, "Initialize the environment map");
        Map<String, Object> env = new HashMap<String, Object>();
        
        SslRMIClientSocketFactory clientSocketFactory =
                new SslRMIClientSocketFactory();
        SslRMIServerSocketFactory serverSocketFactory =
                new SslRMIServerSocketFactory();
        env.put(RMIConnectorServer.RMI_CLIENT_SOCKET_FACTORY_ATTRIBUTE,
                clientSocketFactory);
        env.put(RMIConnectorServer.RMI_SERVER_SOCKET_FACTORY_ATTRIBUTE,
                serverSocketFactory);

        env.put("jmx.remote.x.password.file",
                "config" + File.separator + "password.properties");
        env.put("jmx.remote.x.access.file",
                "config" + File.separator + "access.properties");

        JMXServiceURL jmxUrl = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" +
                "localhost:" + port + "/server");
        JMXConnectorServer connectorServer = JMXConnectorServerFactory.
                newJMXConnectorServer(jmxUrl, env, mbeanServer);
        connectorServer.start();
        return connectorServer;
    }

    private static final String constructSimpleJXMUrl(String host, int port) {
        return constructJMXUrl(SIMPLE_JMX_URL_PREFIX, SIMPLE_JMX_URL_SUFFIX,
                host, port);
    }

    private static final String constructSSLJMXUrl(String host, int port) {
        return constructJMXUrl(SSL_JMX_URL_PREFIX, SSL_JMX_URL_SUFFIX,
                host, port);
    }

    private static final String constructJMXUrl(String prefix, String suffix,
            String host, int port) {
        StringBuilder builder = new StringBuilder(prefix);
        builder.append(host);
        builder.append(JMX_URL_DELIMIT);
        builder.append(port);
        builder.append(suffix);
        return builder.toString();
    }

    private static final void createRegistry(int port) throws RemoteException {
        // Start an RMI registry on port 3000.
        logger.log(Level.INFO, "Create RMI registry on port:" + port);
        LocateRegistry.createRegistry(port);
    }

    private static final MBeanServer getMBeanServer() {
        // Retrieve the PlatformMBeanServer.
        logger.log(Level.INFO, "Get the platform's MBean server");
        return ManagementFactory.getPlatformMBeanServer();
    }
}
