package com.tibco.cep.runtime.service.management.jmx.connectors;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;

import sun.net.util.IPAddressUtil;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 1/26/11
 * Time: 6:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class JMXConnUtil {
    private static Logger logger = LogManagerFactory.getLogManager().getLogger(JMXConnUtil.class);

    public interface URL {
        public static final String SIMPLE_PREFIX = "service:jmx:rmi:///jndi/rmi://";
        public static final String SIMPLE_SUFFIX = "/jmxrmi";

        public static final String SIMPLE_SINGLE_PORT_PREFIX = "service:jmx:rmi://{0}:{1,number,#####}/jndi/rmi://";

        public static final String SSL_PREFIX = "service:jmx:rmi:///jndi/rmi://";
        public static final String SSL_SUFFIX = "/server";

        public static final String DELIMITER = ":";
    }

    public interface JAVA_PROPERTIES {
        public static final String JMX_LOGIN_MODULE_CONFIG = "jmx.remote.x.login.config";
        public static final String JMX_REMOTE_AUTHENTICATE = "com.sun.management.jmxremote.authenticate";
        public static final String JMX_REMOTE = "com.sun.management.jmxremote";
        public static final String JMX_REMOTE_LOGIN_CONFIG_ENTRY = "com.sun.management.jmxremote.login.config";
        public static final String JMX_REMOTE_LOGIN_CONFIG_FILE = "java.security.auth.login.config";
        public static final String JMX_INVOKE_GETTERS= "jmx.invoke.getters";
    }

    public interface BE_PROPERTIES {
        // The JMX_CONNECTOR_PORT property does NOT NEED to be preceded by 'java.property.' in here. It is necessary
        // only in the be-engine.tra file.
        // For example, if the be-engine.tra has the property java.property.be.engine.jmx.connector.port=%jmx_port%,
        // the following two (System and BE) properties will always be created.
        // java.property.be.engine.jmx.connector.port=%jmx_port% (the value is the literal string as specified in be-engine.tra)
        // be.engine.jmx.connector.port=6666 (the value is the actual value passed in the commandline using --propVar jmx_port=6666)
        // Hence we can just use the later because the former is just overhead created by the wrapper.

        public static final String JMX_CONNECTOR_PORT = "be.engine.jmx.connector.port";
        public static final String JMX_CONNECTOR_HOST = "be.engine.jmx.connector.host";
        public static final String JMX_CONNECTOR_AUTHENTICATE = "be.engine.jmx.connector.authenticate";
        public static final String JMX_CONNECTOR_USE_SINGLE_PORT = "be.engine.jmx.connector.usesingleport";
        public static final String JMX_CONNECTOR_SSL = "be.engine.jmx.connector.ssl";
        public static final String AUTH_TYPE = "be.mm.auth.type";
        public static final String AUTH_FILE= "be.mm.auth.file.location";
    }

    public interface HOST {
        public static final String DEFAULT_NAME = "localhost";
        public static final String DEFAULT_IP = "127.0.0.1";
        public static final int DEFAULT_PORT = 5555;
    }

    public interface WRAPPER {
        public static final String JMX_CONN_PORT_VAR = "%jmx_port%";
    }

    public interface BE_CONFIG {
        public static final String SEPARATOR = File.separator;
        public static final String BE_HOME = System.getProperty("tibco.env.BE_HOME");
        public static final String MM_CONFIG_DIR = BE_HOME+SEPARATOR+"mm"+SEPARATOR+"config";
        public static final String JAAS_CONFIG_FILE_NAME = "jaas-config.config";
        public static final String JAAS_CONFIG_FILE_PATH = MM_CONFIG_DIR+SEPARATOR+JAAS_CONFIG_FILE_NAME;
    }

    public static void setJAASConfigFile() {
        if (System.getProperty(JAVA_PROPERTIES.JMX_REMOTE_LOGIN_CONFIG_FILE) == null) {
            System.setProperty(JAVA_PROPERTIES.JMX_REMOTE_LOGIN_CONFIG_FILE, BE_CONFIG.JAAS_CONFIG_FILE_PATH);
        }
    }

    public static String buildJXMUrlStr(String host, int port) {
        return buildJMXUrl(URL.SIMPLE_PREFIX, URL.SIMPLE_SUFFIX, host, port);
    }

    public static String buildSinglePortJXMUrlStr(String host, int port) {
        return buildJMXUrl(URL.SIMPLE_SINGLE_PORT_PREFIX, URL.SIMPLE_SUFFIX, host, port);
    }

    private static String buildJMXUrl(String prefix, String suffix, String host, int port) {
        if (prefix.equals(URL.SIMPLE_SINGLE_PORT_PREFIX))
            prefix = MessageFormat.format(prefix, host, port);

        StringBuilder builder = new StringBuilder(prefix);
        builder.append(host);
        builder.append(URL.DELIMITER);
        builder.append(port);
        builder.append(suffix);
        return builder.toString();
    }

    public static String buildSSLJMXUrlStr(String host, int port) {
        return buildJMXUrl(URL.SSL_PREFIX, URL.SSL_SUFFIX, host, port);
    }

    public static String getLoginModuleConfig() {
        return System.getProperty(JAVA_PROPERTIES.JMX_LOGIN_MODULE_CONFIG);
    }

    public static String getLoginModuleConfig(String defValue) {
        return System.getProperty(JAVA_PROPERTIES.JMX_LOGIN_MODULE_CONFIG,defValue);
    }

    public static String getSSL() {
        return System.getProperty(BE_PROPERTIES.JMX_CONNECTOR_SSL,"false");    //JMX Connector with no SSL by default
    }

    /** Returns the IP address where the connector server is to be started */
    public static String getConnIP(){
        try {
            return System.getProperty(BE_PROPERTIES.JMX_CONNECTOR_HOST) == null ?
                                         InetAddress.getLocalHost().getHostAddress() :
                                         System.getProperty(BE_PROPERTIES.JMX_CONNECTOR_HOST).trim();
        } catch (UnknownHostException e) {
            logger.log(Level.INFO, e, "Unknown host IP found. Defaulting to:  " + HOST.DEFAULT_IP + "\n" + e.getMessage());
            return HOST.DEFAULT_IP;
        }
    }

    public static String getHostName(){
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            logger.log(Level.INFO, e, "Unknown host name found. Defaulting to:  " + HOST.DEFAULT_NAME + "\n" + e.getMessage());
            return HOST.DEFAULT_NAME;
        }
    }

    /** Returns the port to be used by the custom JMX connector.
     *  If property is enabled but no value was assigned, set port to default value (5555)
     *  It alows the property to be set in the CDD file, and value specified using a Global Variable
     *  @param printLog If set to true prints log messages. Otherwise it does not print any log messages
     *  @return the port to be used by the custom JMX connector
     **/
    public static Integer getConnPort(boolean printLog, RuleServiceProvider ruleServiceProvider) throws NumberFormatException {
        final CharSequence portChar = System.getProperty(JMXConnUtil.BE_PROPERTIES.JMX_CONNECTOR_PORT);
        String portStr="";

        if (null != portChar) {
            //replaces the value of a global variable if one was assigned as value of the property
            portStr = ruleServiceProvider.getProject().getGlobalVariables().substituteVariables(portChar).toString().trim();
        }

        //If property is enabled but no value assigned, set port to default value
        if (portStr.isEmpty() || "%jmx_port%".equals(portStr)) {
            if(printLog)
                logger.log(Level.WARN,"Property '%s' enabled but no value was assigned. " +
                        "Using default value for port: %s", BE_PROPERTIES.JMX_CONNECTOR_PORT, HOST.DEFAULT_PORT);

            return  HOST.DEFAULT_PORT;
        }

        Integer port = Integer.valueOf(portStr);

        if (port <= 0)
            throw new NumberFormatException("Negative port number specified: " + portChar);
        else
            return port;
    }

    public static Boolean isSecurityEnabled(RuleServiceProvider ruleServiceProvider) {
        final CharSequence isSecureChar = System.getProperty(BE_PROPERTIES.JMX_CONNECTOR_AUTHENTICATE);
        if (null == isSecureChar || isSecureChar.toString().trim().isEmpty() ) {
            return false;
        }
        //replaces the value of a global variable if one was assigned as value of the property
        final String isSecureStr = ruleServiceProvider.getProject().getGlobalVariables().
                substituteVariables(isSecureChar).toString().trim().toLowerCase();

        return Boolean.valueOf(isSecureStr);
    }

    /** Returns the port to be used by the custom JMX connector. If the port is not specified, it returns
     *  the default port 5555. This method does not print the log message. It is identical to JMXConnUtil.getConnPort(false)
     *  @return Custom JMX connector port
     **/
    public static int getConnPort(RuleServiceProvider ruleServiceProvider) throws NumberFormatException {
        return getConnPort(false, ruleServiceProvider);
    }
    
	/**
	 * Sanitizes IPv6 address such that it can then be used along with port in urls.
	 * Returns the string as it is, if it is not an IPv6.
	 * 
	 * @param host
	 * @return
	 */
	public static String sanitizeIPv6(String host) {
		if (!IPAddressUtil.isIPv6LiteralAddress(host)) {
			return host;
		}
		if (host.contains("%")) {
			host = host.substring(0, host.indexOf('%'));
		}
		if (IPAddressUtil.isIPv6LiteralAddress(host)) {
			host = "[" + host + "]";
		}
		return host;
	}
}
