/**
 * 
 */
package com.tibco.cep.driver.http.server.impl.websocket;

import javax.websocket.Session;

/**
 * @author vpatil
 *
 */
public class WSEndpointContext {
	private Session session;
	private Object data;
	
	public WSEndpointContext(Session session, Object data) {
		this.session = session;
		this.data = data;
	}

	public Session getSession() {
		return session;
	}

	public Object getData() {
		return data;
	}
}
