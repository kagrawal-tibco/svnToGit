package com.tibco.cep.runtime.service.management.process;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.management.remote.rmi.RMIConnectorServer;
import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.management.ManagementTableMBean;
import com.tibco.cep.runtime.management.impl.cluster.ManagementTableMBeanImpl;
import com.tibco.cep.runtime.service.management.EntityMBeansManager;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnServer;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnUtil;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Mar 5, 2010
 * Time: 8:33:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class EngineMBeansManager extends EntityMBeansManager {

    //field that represents the MBean (ObjectName) Groups for this agent
    //to expose other groups as MBeans add the group name to the array mBeanGroups and write the code
    //for the MBean interface and corresponding implementation class following the pattern described below.
    private static final String[] processMBeanClassGroups = {"Channels", "Engine", "OM", "Profiler",
                                                                "RSP", "WM"};

    private static final String[] processMBeanGroups = {"Channels", "Engine", "Object Management", "Profiler",
                                                                "Rule Service Provider", "Working Memory"};

    public EngineMBeansManager(RuleServiceProvider ruleServiceProvider) {
        super(processMBeanGroups,processMBeanClassGroups);
        setRuleServiceProvider(ruleServiceProvider);
        setLogger(this.getClass());
    }

    public void registerEngineMBeans() throws IOException {
        //Necessary to allow the invocation of getter methods
        System.setProperty(JMXConnUtil.JAVA_PROPERTIES.JMX_INVOKE_GETTERS,"true");

        //In case the user does not specify the JAAS Config File Location in the CDD file, the property is set to
        //the default location under BE_HOME/mm/config/jaas-config.config
        JMXConnUtil.setJAASConfigFile();

        registerMBeanClasses(this.getClass());
        registerMBeans();
    }
    
    //MBean to get Instance info (Used by BE-TEA agent)
    public static void registerManagementTableMbean(RuleServiceProvider ruleServiceProvider) {
        ManagementTableMBeanImpl managementTableMBean = new ManagementTableMBeanImpl();
    	managementTableMBean.init(ruleServiceProvider);
        registerStdMBean(ManagementTableMBeanImpl.OBJ_NAME_MNGMT_TABLE, managementTableMBean, ManagementTableMBean.class);    
    }

    public static void createJMXConnectorServer(RuleServiceProvider ruleServiceProvider) throws IOException {
        //If the JMX connector port property is not defined, no JMX connector server is created.
        //This is the behavior identical to what the JVM does with com.sun.management.jmxremote.port
        if (System.getProperty(JMXConnUtil.BE_PROPERTIES.JMX_CONNECTOR_PORT) != null) {
            //necessary to allow the user to connect locally with the PID. Only necessary for 32 bits JVM but
            //does not hurt in 64 bits, so we always set the property.
            System.setProperty(JMXConnUtil.JAVA_PROPERTIES.JMX_REMOTE,"true");
            
            registerJMXConnectorServer(ruleServiceProvider);
        }
    }

    private void registerMBeans() {        //todo handle MBeans UNRegistration
        int i;
        logger.log(Level.INFO,"Registering all BE-Engine level Group MBeans...");
        setObjectNames("com.tibco.be:dir=Methods");

        for (i=0; i < numGroups; i++) {
            init(i);
            register(i);
        }
        logger.log(Level.INFO,"All BE-Engine level Group MBeans SUCCESSFULLY registered");
    }

    private static void registerJMXConnectorServer(RuleServiceProvider ruleServiceProvider) throws IOException {
        Map<String, Object> env = new HashMap<String, Object>();

        //this is necessary to allow the user to connect from the J-Console without
        //typing in any credentials when authentication is disabled
        if (JMXConnUtil.isSecurityEnabled(ruleServiceProvider))
            env.put(JMXConnUtil.JAVA_PROPERTIES.JMX_LOGIN_MODULE_CONFIG,
                    JMXConnUtil.getLoginModuleConfig("JMXAuthenticator"));
        
        boolean useSinglePort =
                Boolean.parseBoolean(System.getProperty(
                        JMXConnUtil.BE_PROPERTIES.JMX_CONNECTOR_USE_SINGLE_PORT, "true"));

        boolean useSsl = Boolean.parseBoolean(System.getProperty(JMXConnUtil.BE_PROPERTIES.JMX_CONNECTOR_SSL, "false"));
        if (useSsl) {
        	SslRMIClientSocketFactory csf = new SslRMIClientSocketFactory();
        	SslRMIServerSocketFactory ssf = new SslRMIServerSocketFactory();
        	env.put(RMIConnectorServer.RMI_CLIENT_SOCKET_FACTORY_ATTRIBUTE, csf);
        	env.put(RMIConnectorServer.RMI_SERVER_SOCKET_FACTORY_ATTRIBUTE, ssf);
        	useSinglePort = false;
        }
  		
        if (useSinglePort)
            JMXConnServer.createSinglePortSecureJMXConnectorServer(
                    JMXConnUtil.getConnIP(), JMXConnUtil.getConnPort(true, ruleServiceProvider), env);
        else
            JMXConnServer.createSecureJMXConnectorServer(
                    JMXConnUtil.getConnIP(), JMXConnUtil.getConnPort(true, ruleServiceProvider), env);
    }
} //class
