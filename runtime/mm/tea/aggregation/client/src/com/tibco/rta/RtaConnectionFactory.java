package com.tibco.rta;

import com.tibco.rta.client.taskdefs.IdempotentRetryTask;
import com.tibco.rta.client.taskdefs.impl.jms.EstablishJMSConnectionTask;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManager;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.property.impl.PropertyAtomString;
import com.tibco.rta.util.PasswordObfuscation;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * A factory for creating {@link RtaConnection} objects.
 */
public class RtaConnectionFactory {

    Map<ConfigProperty, PropertyAtom<?>> configuration = new HashMap<ConfigProperty, PropertyAtom<?>>();

    private LogManager initializeLogger() {
        LogManager logManager = (LogManager) ConfigProperty.LOG_MANAGER.getValue(configuration);
        if (logManager == null) {
            LogManagerFactory.init((String) ConfigProperty.LOG_MANAGER_CLASS_NAME.getValue(configuration));
        } else {
            LogManagerFactory.setLogManager(logManager);
        }
        return logManager;
    }

    /**
     * Creates a new RtaConnection object.
     * The format of the URL is "&lt;protocol>://&lt;host>:&lt;port>. Currently supported protocol is http
     * e.g "http://localhost:4444"
     *
     * @param connectionUrl The server url to connect to.
     * @param username      The user name to use.
     * @param password      The password.
     * @param configuration
     * @return the connection
     * @throws RtaException if any exception was thrown during establishment of connection.
     */
    public RtaConnection getConnection(String connectionUrl, String username, char[] password, Map<ConfigProperty, PropertyAtom<?>> configuration) throws RtaException {
        this.configuration = configuration;
        //Preventing logging of rta props
        //LogManager logManager = initializeLogger();
        //logClientConfiguration(logManager);

        RtaConnectionEx rtaConnection = null;
        try {
            if (connectionUrl.startsWith("http")) {
                Class<?> httpConnClazz = this.getClass().getClassLoader().loadClass("com.tibco.rta.client.http.HTTPRtaConnection");
                Constructor<?> constructor = httpConnClazz.getConstructor(String.class, String.class, String.class);
                rtaConnection = (RtaConnectionEx) constructor.newInstance(connectionUrl, username, new String(password));
            } else if (connectionUrl.startsWith("tcp")) {
                Class<?> tcpConnClazz = this.getClass().getClassLoader().loadClass("com.tibco.rta.client.tcp.TCPRtaConnection");
                Constructor<?> constructor = tcpConnClazz.getConstructor(String.class, String.class, String.class);
                rtaConnection = (RtaConnectionEx) constructor.newInstance(connectionUrl, username, new String(password));
            } else if (connectionUrl.startsWith("tibjmsnaming")) {
                configuration.put(ConfigProperty.JMS_PROVIDER_JNDI_URL, new PropertyAtomString(connectionUrl));
                configuration.put(ConfigProperty.CONNECTION_USERNAME, new PropertyAtomString(username));
                configuration.put(ConfigProperty.CONNECTION_PASSWORD, new PropertyAtomString(PasswordObfuscation.encrypt(new String(password))));

                //Can pass null here because we do not need transmission strategy
                EstablishJMSConnectionTask establishConnectionTask = new EstablishJMSConnectionTask("com.tibco.rta.client.jms.JMSRtaConnection", null);
                establishConnectionTask.setConfiguration(configuration);
                IdempotentRetryTask retryTask = new IdempotentRetryTask((Integer) ConfigProperty.NUM_RETRIES_CONNECTION_ESTABLISH.getValue(configuration),
                        (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration), establishConnectionTask);
                rtaConnection = (RtaConnectionEx) retryTask.perform();
            }
        } catch (Throwable e) {
            throw new RtaException(e);
        }
        if (rtaConnection != null) {
            rtaConnection.setClientId(UUID.randomUUID().toString());
        }
        return rtaConnection;
    }

    private void logClientConfiguration(LogManager logManager) {
        Logger logger = logManager.getLogger(LoggerCategory.RTA_CLIENT.getCategory());

        if (logger.isEnabledFor(Level.INFO)) {
            logger.log(Level.INFO, "Logging client configuration");
            for (ConfigProperty configProperty : ConfigProperty.values()) {
                logger.log(Level.INFO, "Property Name [%s] : Value [%s]", configProperty.getPropertyName(), ConfigProperty.getByPropertyName(configProperty.getPropertyName()).getValue(configuration));
            }
        }
    }

    /**
     * Creates a new RtaConnection object.
     * The format of the URL is "&lt;protocol>://&lt;host>:&lt;port>. Currently supported protocol is http
     * e.g "http://localhost:4444"
     *
     * @param connectionUrl The server url to connect to.
     * @param username      The user name to use
     * @param password      The password
     * @param configuration A property map
     * @return the connection
     * @throws RtaException
     */
    public RtaConnection getConnection(String connectionUrl, String username, String password, Map<ConfigProperty, PropertyAtom<?>> configuration) throws RtaException {
        return getConnection(connectionUrl, username, password.toCharArray(), configuration);
    }
}