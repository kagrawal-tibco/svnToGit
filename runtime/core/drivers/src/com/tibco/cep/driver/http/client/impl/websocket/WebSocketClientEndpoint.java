/**
 * 
 */
package com.tibco.cep.driver.http.client.impl.websocket;

import java.net.URL;
import java.nio.ByteBuffer;

import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.driver.http.HttpDestination.WebSocketSerializationContext;
import com.tibco.cep.driver.http.server.impl.websocket.WSEndpoint;
import com.tibco.cep.driver.http.server.impl.websocket.WSEndpointContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;

/**
 * @author vpatil
 *
 */
public class WebSocketClientEndpoint {
	private Session session;
	private String responseEventUrl;
	private HttpDestination httpDestination;

    public WebSocketClientEndpoint(String endpointURI, String responseEventUrl) {
    	this.responseEventUrl = responseEventUrl;
    	
        try {
        	URL url = new URL(endpointURI);
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, url.toURI());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("opening websocket");
        this.session = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        this.session = null;
    }

    @OnMessage
    public void onMessage(String message) {
    	
    }
    
    @OnMessage
    public void onMessage(byte[] message) {
    	
    }
    
    public Session getSession() {
    	return this.session;
    }
    
    public void sendMessage(SimpleEvent event) throws Exception {
    	WSEndpointContext wsEndpointContext = new WSEndpointContext(session, null);
		WebSocketSerializationContext ctx = httpDestination.new WebSocketSerializationContext(httpDestination, null, wsEndpointContext);
		Object message = httpDestination.getEventSerializer().serialize(event, ctx);
		
		if (!session.isOpen()) throw new RuntimeException("Session associated to this websocket endpoint is no longer open");
		
		if (message instanceof String) {
			session.getAsyncRemote().sendText((String)message);
		} else {
			session.getAsyncRemote().sendBinary(ByteBuffer.wrap((byte[])message));
		}
    }
    
    public void close() throws Exception {
    	this.session.close();
    }
    
    public void setDestination(HttpDestination httpDestination) {
    	this.httpDestination = httpDestination;
    }
}
