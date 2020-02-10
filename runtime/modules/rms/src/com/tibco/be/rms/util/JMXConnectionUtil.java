/**
 * 
 */
package com.tibco.be.rms.util;

import java.io.File;
import java.text.MessageFormat;

/**
 * Utility class providing with various constants for building an JMX URL's, etc
 * 
 * @author hlouro
 * @author vpatil
 */
public class JMXConnectionUtil {
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

    public interface BE_CONFIG {
        public static final String SEPARATOR = File.separator;
        public static final String BE_HOME = System.getProperty("tibco.env.BE_HOME");
        public static final String MM_CONFIG_DIR = BE_HOME+SEPARATOR+"mm"+SEPARATOR+"config";
        public static final String JAAS_CONFIG_FILE_NAME = "jaas-config.config";
        public static final String JAAS_CONFIG_FILE_PATH = MM_CONFIG_DIR+SEPARATOR+JAAS_CONFIG_FILE_NAME;
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
}
