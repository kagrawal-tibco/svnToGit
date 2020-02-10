package com.tibco.cep.runtime.service.management.jmx.connectors;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.MBeanServerForwarder;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 1/26/11
 * Time: 6:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class JMXConnServer {
    private static Logger logger= LogManagerFactory.getLogManager().getLogger(JMXConnServer.class);

    private static JMXConnectorServer handleConnServerCreation (String host,
                                                                int port,
                                                                Map<String, Object> env,
                                                                boolean isSinglePort) throws IOException {

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        JMXConnectorServer connServer = null;
        JMXServiceURL jmxUrl = null;

        try {
            createRegistry(port);

            if (isSinglePort)
            	jmxUrl = new JMXServiceURL(JMXConnUtil.buildSinglePortJXMUrlStr(host, port));
            else
            	jmxUrl = new JMXServiceURL(JMXConnUtil.buildJXMUrlStr(host, port));
            
            logger.log(Level.INFO, "JMX server URL: " + jmxUrl);
            
            connServer = JMXConnectorServerFactory.newJMXConnectorServer(jmxUrl, env, mbs);

        } catch (MalformedURLException mfe) {
            logger.log(Level.ERROR, mfe, "Malformed URL. %s", mfe.getMessage());
            connServer = null;
            throw mfe;
        } catch (RemoteException re) {
            logger.log(Level.ERROR, "Could not create registry @%s:%s - verify if port is not in use. Excep Msg: %s", host, port, re.getMessage());
            connServer = null;
            throw re;
        } catch (IOException e) {
            logger.log(Level.ERROR, e, "Error creating JMX connector server @%s:%s. \n%s", host, port, e.getMessage());
            connServer = null;
            throw e;
        }

        return connServer;
    }


    private static JMXConnectorServer createJMXConnectorServer (String host,
                                                                int port,
                                                                Map<String, Object> env,
                                                                boolean isSinglePort) throws IOException {
    	host = JMXConnUtil.sanitizeIPv6(host);
    	
        logger.log(Level.INFO,"Starting JMX connector server @%s:%s",host,port);

        JMXConnectorServer connServer = handleConnServerCreation(host, port, env, isSinglePort);

        if (connServer!=null) {
            try {
                connServer.start();     //starts connector server
            } catch (IOException e) {
                logger.log(Level.ERROR,e,"Failed to start JMX connector server @%s:%s. \n%s", host, port, e.getMessage());
                return null;
            }
        }
        logger.log(Level.INFO,"JMX Connector server SUCCESSFULLY started @%s:%s",host,port);
        return connServer;
    }

    /** Creates and starts an insecure JMX connector server in the host and port specified, using the environment properties
        passed in the env map. It opens only one port, hence be careful when using SSL.
        If you do not wish to specify any environment properties, env can be null */
    public static JMXConnectorServer createSinglePortJMXConnectorServer (String host,
                                                                         int port,
                                                                         Map<String, Object> env) throws IOException {
        return createJMXConnectorServer(host, port, env, true);
    }

    /** Creates and starts an insecure JMX connector server in the host and port specified, using the environment properties
        passed in the env map. Note that this method opens two ports, one for the registry (the port specified), and one
        (random) to export the JMX RMI connection objects. Beware when using in an environment with firewall.
        If you do not wish to specify any environment properties, env can be null */
    public static JMXConnectorServer createJMXConnectorServer (String host,
                                                               int port,
                                                               Map<String, Object> env) throws IOException {
        return createJMXConnectorServer(host, port, env, false);
    }

    private static JMXConnectorServer createSecureJMXConnectorServer(String host,
                                                                     int port,
                                                                     Map<String, Object> env,
                                                                     boolean isSinglePort) throws IOException {
    	host = JMXConnUtil.sanitizeIPv6(host);

        logger.log(Level.INFO,"Starting secure JMX connector server @%s:%s",host,port);

        //first create an insecure connector server
        JMXConnectorServer secConnServer = handleConnServerCreation(host, port, env, isSinglePort);

        if (secConnServer != null) {
            //add a proxy to make it secure
            MBeanServerForwarder mbsf = SecureJMXProxy.MBSFInvocationHandler.newProxyInstance();
            secConnServer.setMBeanServerForwarder(mbsf);
            try {
                secConnServer.start();  //starts secure connector server
            } catch (IOException e) {
                logger.log(Level.ERROR,e,"Failed to start secure JMX connector server @%s:%s. \n%s", host, port, e.getMessage());
                secConnServer = null;
            }
        }
        logger.log(Level.INFO,"Secure JMX connector server SUCCESSFULLY started @%s:%s",host,port);
        return secConnServer;
    }

    /** Creates and starts a secure JMX connector server in the host and port specified, using the environment properties
        passed in the env map. Note that this method opens two ports, one for the registry (the port specified), and one
        (random) to export the JMX RMI connection objects. Beware when using in an environment with firewall.
        Server requests will be forwarded through a secure proxy which performs the authorization tasks.
        If you do not wish to specify any environment properties, env can be null.
     */
    public static JMXConnectorServer createSecureJMXConnectorServer(String host,
                                                                    int port,
                                                                    Map<String, Object> env) throws IOException {
        return createSecureJMXConnectorServer(host, port, env, false);
    }

    /** Creates and starts a secure JMX connector server in the host and port specified, using the environment properties
        passed in the env map. It opens only one port, hence be careful when using SSL.
        Server requests will be forwarded through a secure proxy which performs the authorization tasks.
        If you do not wish to specify any environment properties, env can be null.
     */
    public static JMXConnectorServer createSinglePortSecureJMXConnectorServer(String host,
                                                                              int port,
                                                                              Map<String, Object> env) throws IOException {
        return createSecureJMXConnectorServer(host, port, env, true);
    }

    private static void createRegistry(int port) throws RemoteException {
        logger.log(Level.DEBUG,"Creating RMI registry on port:" + port);
        LocateRegistry.createRegistry(port);
        logger.log(Level.DEBUG,"RMI registry SUCCESSFULLY created on port:" + port);
    }
}
