/**
 * 
 */
package com.tibco.be.functions.channel.http;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.BUI;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.Session;

import com.tibco.cep.driver.http.HttpDestination.WebSocketMessageContext;
import com.tibco.cep.driver.http.server.impl.websocket.WSEndpoint;
import com.tibco.cep.driver.http.server.impl.websocket.WSEndpointContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;

/**
 * @author vpatil
 *
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "HTTP.WebSocket",
        synopsis = "HTTP WebSocket functions")
public class HTTPChannelWebSocketFunctions {
		
	@com.tibco.be.model.functions.BEFunction(
        name = "getSessionContext",
        signature = "Object getSessionContext(SimpleEvent event)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "Accepts a SimpleEvent associated to WebSocket destination"),
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Websocket session context"),
        version = "5.6",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the underlying websocket session context",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
	public static Object getSessionContext(SimpleEvent event) {
		return getSession(event);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "sendMessage",
        signature = "void sendMessage(Object eventOrSessionContext, Object message, boolean broadcast)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "eventOrSessionContext", type = "Object", desc = "Accepts a SimpleEvent associated to WebSocket destination or the underlying websocket session context retrieved via getSessionContext api."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "String", desc = "The message to send."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "broadcast", type = "boolean", desc = "Send to only the current session or all session associated to this endpoint")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.6",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sends message to current session or broadcasts to all sessions associated to the underlying websocket endpoint",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
	public static void sendMessage(Object eventOrSessionContext, Object message, boolean broadcast) {
		Session session = (Session)getSession(eventOrSessionContext);
		if (message instanceof String) {
			if (broadcast) {
				for (Session s : WSEndpoint.getSessions(session.getRequestURI().getPath()).values()) {
					s.getAsyncRemote().sendText((String)message);
				}
			} else
				session.getAsyncRemote().sendText((String)message);
		} else {
			if (broadcast) {
				for (Session s : WSEndpoint.getSessions(session.getRequestURI().getPath()).values()) {
					s.getAsyncRemote().sendBinary(ByteBuffer.wrap((byte[])message));
				}
			} else 
				session.getAsyncRemote().sendBinary(ByteBuffer.wrap((byte[])message));
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "closeConnection",
		signature = "void closeConnection(Object eventOrSessionContext, int closeCode, String closeReason, boolean all)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "eventOrSessionContext", type = "Object", desc = "Accepts a SimpleEvent associated to WebSocket destination or the underlying websocket session context retrieved via getSessionContext api."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "closeCode", type = "int", desc = "Valid Websocket close code for closure.Few valid codes are 1001,1002,1003."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "closeReason", type = "String", desc = "Reason for the websocket close connection"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "all", type = "boolean", desc = "Closes the current or all websocket connections associated to the underlying endpoint")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.6",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Closes single/all websocket connection/s to the underlying endpoint",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static void closeConnection(Object eventOrSessionContext, int closeCode, String closeReason, boolean all) {
		CloseReason cr = new CloseReason(CloseCodes.getCloseCode(closeCode), closeReason);
		Session session = (Session)getSession(eventOrSessionContext);
		if (all) {
			for (Session s : WSEndpoint.getSessions(session.getRequestURI().getPath()).values()) {
				try {
					s.close(cr);
				} catch (IOException e) {
					throw new RuntimeException("Error closing session");
				} 
			}
		} else {
			try {
				session.close(cr);
			} catch (IOException e) {
				throw new RuntimeException("Error closing session");
			}
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getQueryString",
		signature = "String getQueryString(Object eventOrSessionContext)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "eventOrSessionContext", type = "Object", desc = "Accepts a SimpleEvent associated to WebSocket destination or the underlying websocket endpoint context retreived via getSessionContext api.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Returns the query string"),
		version = "5.6",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns the query string",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static String getQueryString(Object eventOrSessionContext) {
		return ((Session)getSession(eventOrSessionContext)).getQueryString();
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getRequestParameters",
		signature = "Object getRequestParameters(Object eventOrSessionContext)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "eventOrSessionContext", type = "Object", desc = "Accepts a SimpleEvent associated to WebSocket destination or the underlying websocket endpoint context retreived via getSessionContext api.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Returns a map of the request parameters"),
		version = "5.6",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns a map of the request parameters",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static Object getRequestParameters(Object eventOrSessionContext) {
		return ((Session)getSession(eventOrSessionContext)).getRequestParameterMap();
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "isConnectionOpen",
		signature = "boolean isConnectionOpen(Object eventOrSessionContext)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "eventOrSessionContext", type = "Object", desc = "Accepts a SimpleEvent associated to WebSocket destination or the underlying websocket endpoint context retreived via getSessionContext api.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns if the underlying websocket endpoint connection is open"),
		version = "5.6",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns if the underlying websocket endpoint connection is open",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static boolean isConnectionOpen(Object eventOrSessionContext) {
		return ((Session)getSession(eventOrSessionContext)).isOpen();
	}
	
	private static Object getSession(Object eventOrSession) {
		Session session = null;
		if (eventOrSession instanceof SimpleEvent) {
			Object wsContext = null;
			if (((SimpleEvent)eventOrSession).getContext() instanceof WebSocketMessageContext) {
				wsContext = ((SimpleEvent)eventOrSession).getContext();
				if (wsContext != null) {			
					WSEndpointContext wsEndpointContext = (WSEndpointContext) ((WebSocketMessageContext)wsContext).getMessage();
					session = wsEndpointContext.getSession();
				}
			}
		} else if (eventOrSession instanceof Session) session = (Session) eventOrSession;

		if (session != null && !session.isOpen()) throw new RuntimeException("Session associated to this websocket endpoint is no longer open");
		return session;
	}
}
