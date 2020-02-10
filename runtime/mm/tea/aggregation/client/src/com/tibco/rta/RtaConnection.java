package com.tibco.rta;


import com.tibco.rta.annotations.ThreadSafe;
import com.tibco.rta.property.PropertyAtom;

import java.util.Map;


/**
 * A connection to the metrics engine.
 * 
 * A client application gets an instance of a connection using {@code RtaConnectionFactory}
 * This connection is used by the {@code RtaSession} for all communication with the engine.
 * 
 *
 * <p>
 *     example :
 *     <code>
 *         RtaConnectionFactory connectionFac = new RtaConnectionFactory();
 *
 *         RtaConnection connection = connectionFac.getConnection(connectionUrl, "username", "", new HashMap<ConfigProperty, PropertyAtom<?>>());
 *
 *         RtaSession session = connection.createSession(new HashMap<ConfigProperty, PropertyAtom<?>>());
 *     </code>
 * </p>
 * 
 */
@ThreadSafe
public interface RtaConnection {
	
	/**
	 * Creates the session.
	 *
	 * @param sessionProps the session props
	 * 
	 * @return the rta session
	 * @throws RtaException
     * @throws NullPointerException if any key or value in the properties is null
	 */
	RtaSession createSession(Map<ConfigProperty, PropertyAtom<?>> sessionProps) throws RtaException;

    /**
   	 * Creates the session with name
   	 * @param name Name for session.
   	 * @param sessionProps the session props
   	 *
   	 * @return the rta session
   	 * @throws RtaException
     * @throws NullPointerException if any key or value in the properties is null
   	 */
   	RtaSession createSession(String name, Map<ConfigProperty, PropertyAtom<?>> sessionProps) throws RtaException;
	
	/**
	 * Close the connection and release any resources used.
	 * 
	 * @throws RtaException if the connection could not be closed.
	 */
	void close() throws RtaException;

}