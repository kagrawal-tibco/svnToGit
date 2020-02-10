package com.tibco.cep.driver.http;


import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.driver.http.client.HttpChannelClient;
import com.tibco.cep.driver.http.client.HttpChannelClientRequest;
import com.tibco.cep.driver.http.client.HttpChannelClientResponse;
import com.tibco.cep.driver.http.client.impl.httpcomponents.HttpComponentsClient;
import com.tibco.cep.driver.http.server.HttpChannelServerRequest;
import com.tibco.cep.driver.http.server.HttpChannelServerResponse;
import com.tibco.cep.driver.http.server.impl.websocket.WSEndpointContext;
import com.tibco.cep.driver.util.IncludeEventType;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.channel.impl.AbstractDestination;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AbstractEventContext;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionConfig;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Mar 27, 2008
 * Time: 12:26:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpDestination extends AbstractDestination<HttpChannel> implements HttpChannelDestination {

//    private boolean isPageFlowDestination;
    private boolean isDeSerializingEventType;
    private boolean isSerializingEventType;
    private boolean isJSONPayload;
    private DestinationType destinationType;

    private String contextUri;

    private String pageFlowRuleFunction;

    /**
     * constructor
     *
     * @param channel
     * @param destinationConfig
     */
    public HttpDestination(HttpChannel channel, DestinationConfig destinationConfig) {
        super(channel, destinationConfig);
        Properties configProperties = destinationConfig.getProperties();
        
        //Fixed for old be.http.PageFlow property if new property is not exist
        String type=configProperties.getProperty("be.http.type");
        if(null==type || type.trim().isEmpty()){
            boolean isPageFlowDestination = Boolean.parseBoolean(configProperties.getProperty("be.http.PageFlow","false"));
            if(isPageFlowDestination)
            	type="PAGEFLOW";
            else
            	type="DEFAULT";
        }
//        isPageFlowDestination = Boolean.parseBoolean(configProperties.getProperty("be.http.PageFlow"));
        //destinationType = DestinationType.valueOf(configProperties.getProperty("be.http.type"));
        destinationType = DestinationType.valueOf(type);        
        final IncludeEventType includeEventType = IncludeEventType.valueOf(channel.getGlobalVariables()
                .substituteVariables(
                        configProperties.getProperty("be.http.IncludeEventType", IncludeEventType.ALWAYS.toString()))
                .toString());
        this.isDeSerializingEventType = includeEventType.isOkOnDeserialize();
        this.isSerializingEventType = includeEventType.isOkOnSerialize();
        contextUri = configProperties.getProperty("be.http.contextPath");
        pageFlowRuleFunction = configProperties.getProperty("be.http.pageFlowFunction");
        isJSONPayload = Boolean.parseBoolean(configProperties.getProperty("be.http.jsonPayload"));
    }

    public void connect() throws Exception {

    }

    public void init() throws Exception {
        super.init();
        channel.getHttpChannelClient().setDestinationURL(this);
    }


    protected void destroy(RuleSession session) throws Exception {

    }

    protected void rebind() throws Exception {
        stop();
        Collection<RuleSession> ruleSessions = getBoundSessions();
        Iterator<RuleSession> r = ruleSessions.iterator();
        while (r.hasNext()) {
            RuleSession rsess = r.next();
            bind(rsess);
        }
    }

    public int send(SimpleEvent event, Map overrideData) throws Exception {
    	if (isWebSocketDestination()) throw new RuntimeException("WebSocket client is not supported yet.");
        HttpChannelClient client = channel.getHttpChannelClient();
        return sendMessage(event, client, overrideData, null);
    }

    public SimpleEvent processResponse(HttpChannelClientResponse response, Map<String, Object> overrideData) throws Exception {
        HttpChannelClient client = channel.getHttpChannelClient();
        RuleSession session = (RuleSession)overrideData.get(RuleSession.class.getName());
        HttpClientSerializationContext ctx = new HttpClientSerializationContext(this, session, client, null, null, overrideData);
        return this.serializer.deserialize(response, ctx);
    }


    public int sendMessage(HttpChannelClientRequest request, SimpleEvent requestEvent) throws Exception {
        HttpChannelClient client = channel.getHttpChannelClient();
        return sendMessage(requestEvent, client, null, request);
    }

    public int sendMessage(HttpChannelClient client, SimpleEvent requestEvent) throws Exception {
        return sendMessage(requestEvent, client, null, client.getClientRequest());
    }

    private int sendMessage(SimpleEvent event, HttpChannelClient client, Map overrideData, HttpChannelClientRequest clientRequest) throws Exception {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        HttpClientSerializationContext ctx = new HttpClientSerializationContext(this, session, client, clientRequest, event, overrideData);
        if ((channel.getState() == Channel.State.CONNECTED) || (channel.getState() == Channel.State.STARTED)) {
            if (clientRequest == null) {
                client.setDestinationURL(this);
            }
            Object msg = this.serializer.serialize(event, ctx);
            client.execute(ctx);
        } else if (channel.getState() == Channel.State.INITIALIZED) {
            //TODO: decide what to do
            return -1;
        } else {
            this.channel.getLogger().log(Level.ERROR, "Channel in an invalid state: %s", this.channel.getState());
            return -1;
        }
        return super.send(event, null);
    }

    public Event sendSyncMessage(HttpChannelClientRequest request, SimpleEvent requestEvent, String responseEventURI, long timeOut) throws Exception {
        HttpChannelClient client = channel.getHttpChannelClient();
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        HttpClientSerializationContext ctx = new HttpClientSerializationContext(this, session, client, request, requestEvent, null);
        return sendSyncMessage(ctx, responseEventURI, timeOut);
    }

    public Event sendSyncMessage(HttpChannelClient client, SimpleEvent requestEvent, String responseEventURI, long timeOut) throws Exception {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        HttpClientSerializationContext ctx = new HttpClientSerializationContext(this, session, client, client.getClientRequest(), requestEvent, null);
        return sendSyncMessage(ctx, responseEventURI, timeOut);
    }

    private Event sendSyncMessage(HttpClientSerializationContext ctx, String responseEventURI, long timeOut) throws Exception {
        Event respEvent = null;
        HttpComponentsClient client = (HttpComponentsClient) ctx.getClient();
        SimpleEvent event = ctx.getRequestEvent();
        HttpChannelClientRequest clientRequest = ctx.getClientRequest();
        if ((channel.getState() == Channel.State.CONNECTED) || (channel.getState() == Channel.State.STARTED)) {
            if (clientRequest == null) {
                client.setDestinationURL(this);
            }
            this.serializer.serialize(event, ctx);
            respEvent = client.executeSync(ctx, responseEventURI, timeOut);
        } else if (channel.getState() == Channel.State.INITIALIZED) {
            //TODO: decide what to do
            return null;
        } else {
            this.channel.getLogger().log(Level.ERROR, "Channel in an invalid state: %s", this.channel.getState());
            return null;
        }
        super.send(event, null);

        return respEvent;
    }

    public boolean isPageFlowDestination() {
        return destinationType == DestinationType.PAGEFLOW;
    }

    public String getContextUri() {
        return contextUri;
    }

    public String getPageFlowRuleFunction() {
        return pageFlowRuleFunction;
    }

    public boolean isDeSerializingEventType() {
        return this.isDeSerializingEventType;
    }

    public boolean isSerializingEventType() {
        return this.isSerializingEventType;
    }

    @Override
    public boolean isJSONPayload() {
		return isJSONPayload;
	}
    
    public boolean isWebSocketDestination() {
    	return destinationType == DestinationType.WEBSOCKET;
    }

	/**
     * Processes the Servlet actions
     *
     * @param request
     * @param response
     * @param method
     * @throws Exception
     */
    public void processMessage(HttpChannelServerRequest request, HttpChannelServerResponse response, String method) throws Exception {
        HttpMessageContext ctx = new HttpMessageContext(this, request, response, method);
        Collection<RuleSession> ruleSessions = getBoundSessions();
        Iterator<RuleSession> r = ruleSessions.iterator();
        if (ruleSessions.isEmpty())
            this.channel.getLogger().log(Level.WARN, "No ruleSessions that are bound to destination: %s", getURI());

        while (r.hasNext()) {
            RuleSession rsess = r.next();
            //TODO Do we need to synchronize here?
            //synchronized(lock) {
            this.onMessage(rsess, ctx);
            //}
        }
    }
    
    /**
     * Processes WebSocket actions
     * 
     * @param endpointContext
     * @throws Exception
     */
    public void proccessWebSocketMessage(WSEndpointContext endpointContext) throws Exception {
    	WebSocketMessageContext ctx = new WebSocketMessageContext(this, endpointContext);

    	Collection<RuleSession> ruleSessions = getBoundSessions();
    	if (ruleSessions.isEmpty())
    		this.channel.getLogger().log(Level.WARN, "No ruleSessions that are bound to destination: %s", getURI());

    	Iterator<RuleSession> r = ruleSessions.iterator();
    	while (r.hasNext()) {
    		RuleSession rsess = r.next();
    		this.onMessage(rsess, ctx);
    	}
    }

    /**
     * HttpMessageContext
     */
    public class HttpMessageContext extends AbstractEventContext {
        HttpDestination dest;
        HttpChannelServerRequest request;
        HttpChannelServerResponse response;
        String method;

        HttpMessageContext(HttpDestination dest,
                           HttpChannelServerRequest request,
                           HttpChannelServerResponse response,
                           String method) {
            this.dest = dest;
            this.method = method;
            this.request = request;
            this.response = response;
        }

        public String getMethod() {
            return method;
        }

        public HttpChannelServerRequest getRequest() {
            return request;
        }

        public HttpChannelServerResponse getResponse() {
            return response;
        }

        /**
         *
         */
        public void acknowledge() {

        }

        /**
         * @param replyEvent
         * @return
         */
        public boolean reply(SimpleEvent replyEvent) {
            try {
                RuleSession session = RuleSessionManager.getCurrentRuleSession();
                HttpServerSerializationContext replCtx = new HttpServerSerializationContext(dest, session, response);
                dest.getEventSerializer().serialize(replyEvent, replCtx);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        public boolean canReply() {

            return false;

        }


        /**
         * @return
         */
        public Channel.Destination getDestination() {
            return dest;
        }

        /**
         * @return
         */
        public Object getMessage() {
            //Changed this to greater than 0
            if (dest.getBoundedRuleSessions().size() > 0) {
                return request;
            } else {
                return null;
            }
        }

		@Override
		public String replyImmediate(SimpleEvent replyEvent) {
			// TODO Auto-generated method stub
			return null;
		}
    }

    /**
     * HttpServerSerializationContext
     */
    public class HttpServerSerializationContext implements SerializationContext {
        RuleSession session;
        Channel.Destination destination;
        RuleSessionConfig.InputDestinationConfig configs;
        HttpChannelServerResponse response;

        HttpServerSerializationContext(Channel.Destination destination, RuleSession session, HttpChannelServerResponse response) {
            this.destination = destination;
            this.session = session;
            this.response = response;
            if (session != null) {
                final RuleSessionConfig.InputDestinationConfig cfgs[] = session.getConfig().getInputDestinations();
                for (int i = 0; i < cfgs.length; i++) {
                    final RuleSessionConfig.InputDestinationConfig cfg = cfgs[i];
                    if (destination.getURI().equals(cfg.getURI())) {
                        this.configs = cfg;
                        break;
                    }
                }
            }
        }

        public RuleSessionConfig.InputDestinationConfig getDeployedDestinationConfig() {
            return configs;
        }

        public Channel.Destination getDestination() {
            return destination;
        }

        public RuleSession getRuleSession() {
            return session;
        }

        public HttpChannelServerResponse getResponse() {
            return response;
        }
    }

    public class HttpClientSerializationContext implements SerializationContext {

        RuleSession session;
        HttpDestination destination;
        RuleSessionConfig.InputDestinationConfig configs;
        HttpChannelClient client;
        HttpChannelClientRequest clientRequest;
        SimpleEvent requestEvent;
        Map overrideData;

        HttpClientSerializationContext(HttpDestination destination, RuleSession session, HttpChannelClient client,
                                       HttpChannelClientRequest clientRequest, SimpleEvent requestEvent, Map overrideData) {
            this.destination = destination;
            this.session = session;
            this.client = client;
            this.clientRequest = clientRequest;
            this.overrideData = overrideData;
            this.requestEvent = requestEvent;
            if (session != null) {
                final RuleSessionConfig.InputDestinationConfig cfgs[] = session.getConfig().getInputDestinations();
                for (int i = 0; i < cfgs.length; i++) {
                    final RuleSessionConfig.InputDestinationConfig cfg = cfgs[i];
                    if (destination.getURI().equals(cfg.getURI())) {
                        this.configs = cfg;
                        break;
                    }
                }
            }
        }

        public RuleSessionConfig.InputDestinationConfig getDeployedDestinationConfig() {
            return configs;
        }

        public HttpDestination getDestination() {
            return destination;
        }

        public RuleSession getRuleSession() {
            return session;
        }

        public HttpChannelClient getClient() {
            return client;
        }

        public HttpChannelClientRequest getClientRequest() {
            return clientRequest;
        }

        public Map getOverrideData() {
            return overrideData;
        }

        public SimpleEvent getRequestEvent() {
            return requestEvent;
        }

    }

	@Override
	public String sendImmediate(SimpleEvent event, Map overrideData)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object requestEvent(SimpleEvent outevent, ExpandedName responseEventURI, String serializerClass, long timeout, Map overrideData)
			throws Exception {
		throw new Exception("requestEvent() is not supported with Http Channel");
	}
	
	enum DestinationType {
		DEFAULT,
		PAGEFLOW,
		WEBSOCKET
	}
	
	public class WebSocketSerializationContext implements SerializationContext {
		private RuleSession session;
		private HttpDestination destination;
		private RuleSessionConfig.InputDestinationConfig configs;
		private WSEndpointContext endpointContext;

		public WebSocketSerializationContext(HttpDestination destination, RuleSession session, WSEndpointContext endpointContext) {
			this.destination = destination;
			this.session = session;
			this.endpointContext = endpointContext;

			if (session != null) {
				final RuleSessionConfig.InputDestinationConfig cfgs[] = session.getConfig().getInputDestinations();
				for (int i = 0; i < cfgs.length; i++) {
					final RuleSessionConfig.InputDestinationConfig cfg = cfgs[i];
					if (destination.getURI().equals(cfg.getURI())) {
						this.configs = cfg;
						break;
					}
				}
			}
		}

		@Override
		public RuleSession getRuleSession() {
			return session;
		}

		@Override
		public HttpDestination getDestination() {
			return destination;
		}

		@Override
		public RuleSessionConfig.InputDestinationConfig getDeployedDestinationConfig() {
			return configs;
		}

		public WSEndpointContext getEndpointContext() {
			return endpointContext;
		}
	}

	public class WebSocketMessageContext extends AbstractEventContext {
		private HttpDestination dest;
		private WSEndpointContext endpointContext;

		public WebSocketMessageContext(HttpDestination dest, WSEndpointContext endpointContext) {
			this.dest = dest;
			this.endpointContext = endpointContext;
		}

		@Override
		public boolean reply(SimpleEvent replyEvent) {
			try {
				RuleSession session = RuleSessionManager.getCurrentRuleSession();
				WebSocketSerializationContext replCtx = new WebSocketSerializationContext(dest, session, null);
				Object serializedEvent = dest.getEventSerializer().serialize(replyEvent, replCtx);
				endpointContext.getSession().getAsyncRemote().sendText((String)serializedEvent);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		public String replyImmediate(SimpleEvent replyEvent) {
			return null;
		}

		@Override
		public Channel.Destination getDestination() {
			return dest;
		}

		@Override
		public Object getMessage() {
			return endpointContext;
		}
	}
}
