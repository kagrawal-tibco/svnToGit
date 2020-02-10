/**
 * 
 */
package com.tibco.cep.driver.http.server.impl.websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.HandshakeResponse;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleSession;

/**
 * 
 * @author vpatil
 */
public class WSEndpoint extends Endpoint {
	private static transient Logger LOGGER = LogManagerFactory.getLogManager().getLogger(WSEndpoint.class);
	
	private static Map<String, Map<String,Session>> urlToSessionMap = new ConcurrentHashMap<String, Map<String,Session>>();

	/* (non-Javadoc)
	 * @see javax.websocket.Endpoint#onOpen(javax.websocket.Session, javax.websocket.EndpointConfig)
	 */
	@Override
	public void onOpen(Session session, EndpointConfig endpointConfig) {
		LOGGER.log(Level.DEBUG, String.format("Opened a new session with Id[%s] associated to endpoint[%s]", session.getId(), session.getRequestURI().getPath()));
		addSession(session);
		
		HttpDestination httpDestination = (HttpDestination)endpointConfig.getUserProperties().get(HttpDestination.class.getName());
		
		session.addMessageHandler(new MessageHandler.Whole<String>() {
			@Override
			public void onMessage(String data) {
				processMessage(httpDestination, session, data);
			}
		});
		
		session.addMessageHandler(new MessageHandler.Whole<byte[]>() {
			@Override
			public void onMessage(byte[] data) {
				processMessage(httpDestination, session, data);
			}
		});
	}
	
	private void processMessage(HttpDestination httpDestination, Session session, Object data) {
		try {
			httpDestination.proccessWebSocketMessage(new WSEndpointContext(session, data));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void onClose(Session session, CloseReason closeReason) {
		LOGGER.log(Level.DEBUG, String.format("Closing the connection to endpoint[%s] for session Id[%s] with close code [%s] & reason [%s]", session.getRequestURI().getPath(), session.getId(), closeReason.getCloseCode(), closeReason.getReasonPhrase()));
		super.onClose(session, closeReason);
		removeSession(session);
	}
	
	@Override
	public void onError(Session session, Throwable throwable) {
		LOGGER.log(Level.ERROR, String.format("Error[%s] occurred on session Id[%s] associated to endpoint[%s]", throwable.getMessage(), session.getId(), session.getRequestURI().getPath()));
		super.onError(session, throwable);
	}
	
	private synchronized void addSession(Session session) {
		Map<String, Session> sessionMap = urlToSessionMap.get(session.getRequestURI().getPath());
		if (sessionMap == null) {
			sessionMap =  new ConcurrentHashMap<String, Session>();
			urlToSessionMap.put(session.getRequestURI().getPath(), sessionMap);
		}
		sessionMap.put(session.getId(), session);
	}
	
	private synchronized void removeSession(Session session) {
		Map<String, Session> sessionMap = urlToSessionMap.get(session.getRequestURI().getPath());
		if (sessionMap != null) {
			sessionMap.remove(session.getId());
			if (sessionMap.size() == 0) urlToSessionMap.remove(session.getRequestURI().getPath());
		}
	}
	
	public static Map<String, Session> getSessions(String url) {
		return urlToSessionMap.get(url);
	}

	/**
	 * 
	 */
	class WebSocketRuleFunctionExecTask implements Runnable {

		private RuleSession ruleSession;
		private WSEndpointContext endpointContext;
		private String callbackRFPath;

		WebSocketRuleFunctionExecTask(RuleSession ruleSession,
				Session session, Object data,
				String callbackRFPath) {
			this.ruleSession = ruleSession;
			this.endpointContext = new WSEndpointContext(session, data);
			this.callbackRFPath = callbackRFPath;
		}

		@Override
		public void run() {
			try {
				ruleSession.invokeFunction(callbackRFPath, new Object[]{endpointContext}, false);
			} catch (Exception re) {
				LOGGER.log(Level.ERROR, "Exception while invoking rule function", re);
			}
		}
	}
}

/**
 *
 */
class HttpChannelConfigurator extends ServerEndpointConfig.Configurator {
	private HttpDestination httpDestination;

	public HttpChannelConfigurator(HttpDestination httpDestination) {
		this.httpDestination = httpDestination;
	}

	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		sec.getUserProperties().put(HttpDestination.class.getName(), httpDestination);
	}
}
