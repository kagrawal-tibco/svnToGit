package com.tibco.rta;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.UUID;

import com.tibco.rta.property.PropertyAtom;


/**
 * A factory for creating {@link RtaConnection} objects.
 */
public class RtaConnectionFactoryEx extends RtaConnectionFactory {

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
        RtaConnection rtaConnection = null;
        try {
        	rtaConnection = super.getConnection(connectionUrl, username, password, configuration);
        	if (rtaConnection == null && connectionUrl.startsWith("local")) {
        		Class<?> localConnClazz = this.getClass().getClassLoader().loadClass("com.tibco.rta.client.local.LocalRtaConnection");
                Constructor<?> constructor = localConnClazz.getConstructor();
                rtaConnection = (RtaConnectionEx) constructor.newInstance();
        	}
        } catch (Throwable e) {
            throw new RtaException(e);
        }
        if (rtaConnection != null && rtaConnection instanceof RtaConnectionEx) {
       		((RtaConnectionEx)rtaConnection).setClientId(UUID.randomUUID().toString());	
        }
        return rtaConnection;
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