package com.tibco.cep.driver.http.client.impl.httpcomponents;

import static com.tibco.cep.driver.http.HttpUtils.HTTP_REQ_CORRELATION_ID;
import static com.tibco.cep.driver.http.HttpUtils.HTTP_RESPONSE_ERROR_CALLBACK_URI;
import static com.tibco.cep.driver.http.HttpUtils.HTTP_RESPONSE_SUCCESS_CALLBACK_URI;

import javax.net.ssl.SSLContext;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.params.CoreProtocolPNames;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.driver.http.HttpChannel;
import com.tibco.cep.driver.http.HttpChannelConfig;
import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.driver.http.HttpMethods;
import com.tibco.cep.driver.http.client.HTTPConnectionInfo;
import com.tibco.cep.driver.http.server.utils.SSLUtils;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.event.SOAPEvent;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.xml.datamodel.XiNode;

/**
 * A utility class for calling Http Requests on Http Channel Client
 *
 * @author vpatil
 */
public class HttpComponentsClientService {
	
	/**
	 * Send secure/non secure Http request, using optional proxy routing
	 * 
	 * @param url
	 * @param requestEvent
	 * @param responseEventURI
	 * @param timeOut
	 * @param httpConnectionInfo
	 * @return
	 * @throws Exception
	 */
	public final static Event sendRequest(String url, 
										   SimpleEvent requestEvent, 
										   String responseEventURI, 
										   long timeOut,
                                           HTTPConnectionInfo httpConnectionInfo) throws Exception {
		if (url == null) {
            throw new IllegalArgumentException("url can not be null");
        }

        if (requestEvent == null) {
            throw new IllegalArgumentException("requestEvent can not be null");
        }

        if (responseEventURI == null || responseEventURI.trim().isEmpty()) {
            throw new IllegalArgumentException("responseEventURI can not be null or empty");
        }
        
        RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
        HttpDestination httpDestination = (HttpDestination) rsp.getChannelManager().getDestination(requestEvent.getDestinationURI());

        if (httpDestination == null) {
            throw new RuntimeException("Default Destination is not set.");
        }
        
        if (httpDestination.isWebSocketDestination()) throw new RuntimeException("WebSocket client is not supported yet.");
        
        if (url.trim().isEmpty()) {
            HttpChannel channel = (HttpChannel) httpDestination.getChannel();
            HttpChannelConfig channelConfig = (HttpChannelConfig) channel.getConfig();
            url = "http://" + channelConfig.getHost() + ":" + channelConfig.getPort() + httpDestination.getURI();
        }
        
        boolean isExpectContinueHeaderDisabled = (httpConnectionInfo != null) ? httpConnectionInfo.isExpectContinueHeaderDisabled() : true;
        if (httpConnectionInfo.getHttpMethod() == null || httpConnectionInfo.getHttpMethod().isEmpty()) {
        	String httpMethod = HttpMethods.METHOD_GET;
        	if (requestEvent.getPayload() != null) httpMethod = HttpMethods.METHOD_POST;
        	httpConnectionInfo.setHttpMethod(httpMethod);
        }
        
        HttpUriRequest req = createRequest(url, httpConnectionInfo.getHttpMethod(), isExpectContinueHeaderDisabled);
        HttpComponentsClientRequest clientReq = new HttpComponentsClientRequest(req);
        
        Event respEvent = null;
        if (httpConnectionInfo.isSecure()) {
        	SSLContext sslContext = SSLUtils.createSSLContext(httpConnectionInfo.getClientIdKeystore(), httpConnectionInfo.getClientIdPassword(), httpConnectionInfo.getTrustedCertsKeystore(), httpConnectionInfo.getTrustedCertsPassword(), httpConnectionInfo.getSSLProtocol());
            HttpComponentsClient httpClient = new HttpComponentsClient(clientReq, sslContext, httpDestination, httpConnectionInfo.isVerifyHostName());
            httpClient.init();
            
            if (httpConnectionInfo.getProxyHost() != null && !httpConnectionInfo.getProxyHost().isEmpty() && httpConnectionInfo.getProxyPort() > 0) {
            	httpClient.setHttpProxy(httpConnectionInfo.getProxyHost(), httpConnectionInfo.getProxyPort());
            }

            if (httpConnectionInfo.isCookiesDisabled()) {
            	httpClient.removeCookieInterceptors();
            }
            
            respEvent = httpDestination.sendSyncMessage(httpClient, requestEvent, responseEventURI, timeOut);
        } else {
        	setHttpProxy(httpDestination, httpConnectionInfo.getProxyHost(), httpConnectionInfo.getProxyPort());
        	if (httpConnectionInfo.isCookiesDisabled()) {
        		disableCookies(httpDestination);
        	}

        	respEvent = httpDestination.sendSyncMessage(clientReq, requestEvent, responseEventURI, timeOut);

        	clearHttpProxy(httpDestination);
        	if (httpConnectionInfo.isCookiesDisabled()) {
        		enableCookies(httpDestination);
        	}
        }
        
        return respEvent;
	}
	
	/**
	 * Send Async Http request, using optional proxy routing
	 * 
	 * @param url
	 * @param requestEvent
	 * @param correlationId
	 * @param successCallbackRuleFunctionURL
	 * @param errorCallbackRuleFunctionURL
	 * @param httpConnectionInfo
	 * @param optionalHeaders
	 * @return
	 * @throws Exception
	 */
	public final static String sendAsyncRequest(String url,
									             SimpleEvent requestEvent,
									             String correlationId,
									             String successCallbackRuleFunctionURL,
									             String errorCallbackRuleFunctionURL,
									             HTTPConnectionInfo httpConnectionInfo,
									             String... optionalHeaders) throws Exception {
		if (url == null) {
            throw new IllegalArgumentException("url can not be null");
        }
        if (requestEvent == null) {
            throw new IllegalArgumentException("requestEvent can not be null");
        }

        if (successCallbackRuleFunctionURL == null || successCallbackRuleFunctionURL.trim().isEmpty()) {
            throw new IllegalArgumentException("Success callbackRuleFunctionURL can not be null or empty");
        }

        if (httpConnectionInfo.getHttpMethod() == null || httpConnectionInfo.getHttpMethod().isEmpty()) {
        	String httpMethod = HttpMethods.METHOD_GET;
        	if (requestEvent.getPayload() != null) httpMethod = HttpMethods.METHOD_POST;
        	httpConnectionInfo.setHttpMethod(httpMethod);
        }

        boolean isExpectContinueHeaderDisabled = (httpConnectionInfo != null) ? httpConnectionInfo.isExpectContinueHeaderDisabled() : true;
        HttpUriRequest req = createRequest(url, httpConnectionInfo.getHttpMethod(), isExpectContinueHeaderDisabled, optionalHeaders);
        HttpComponentsClientRequest clientReq = new HttpComponentsClientRequest(req);
        
        if (correlationId == null || correlationId.trim().length() == 0) {
            correlationId = GUIDGenerator.getGUID();
        }
        clientReq.setParameter(HTTP_REQ_CORRELATION_ID, correlationId);
        clientReq.setParameter(HTTP_RESPONSE_SUCCESS_CALLBACK_URI, successCallbackRuleFunctionURL);
        if (errorCallbackRuleFunctionURL != null) {
            clientReq.setParameter(HTTP_RESPONSE_ERROR_CALLBACK_URI, errorCallbackRuleFunctionURL);
        }
        
        RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
        HttpDestination httpDestination = (HttpDestination) rsp.getChannelManager().getDestination(requestEvent.getDestinationURI());
        if (httpDestination == null) {
            throw new RuntimeException("Default Destination is not set.");
        }
        
        if (httpConnectionInfo.isSecure()) {
        	SSLContext sslContext = SSLUtils.createSSLContext(httpConnectionInfo.getClientIdKeystore(), httpConnectionInfo.getClientIdPassword(), httpConnectionInfo.getTrustedCertsKeystore(), httpConnectionInfo.getTrustedCertsPassword(), httpConnectionInfo.getSSLProtocol());
            HttpComponentsClient httpClient = new HttpComponentsClient(clientReq, sslContext, httpDestination, httpConnectionInfo.isVerifyHostName());
            httpClient.init();
            
            if (httpConnectionInfo.getProxyHost() != null && !httpConnectionInfo.getProxyHost().isEmpty() && httpConnectionInfo.getProxyPort() > 0) {
            	httpClient.setHttpProxy(httpConnectionInfo.getProxyHost(), httpConnectionInfo.getProxyPort());
            }
            
            if (httpConnectionInfo != null && httpConnectionInfo.isCookiesDisabled()) {
            	httpClient.removeCookieInterceptors();
            }
            
            httpDestination.sendMessage(httpClient, requestEvent);
        } else {
           	setHttpProxy(httpDestination, httpConnectionInfo.getProxyHost(), httpConnectionInfo.getProxyPort());
            if (httpConnectionInfo.isCookiesDisabled()) {
            	disableCookies(httpDestination);
            }
            
            httpDestination.sendMessage(clientReq, requestEvent);
        }
        
        return correlationId;
	 }
	
    /**
     * Typically used for sendEvent
     * 
     * @param requestEvent
     * @param responseEventURI
     * @param timeOut
     * @param endPtUri
     * @return
     * @throws Exception
     */
    public final static Event sendEvent(SimpleEvent requestEvent,
                                          String responseEventURI, long timeOut, String endPtUri) throws Exception {
        if (endPtUri == null || endPtUri.trim().isEmpty()) {
            new IllegalArgumentException("url can not be null or empty");
        }

        RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
        HttpDestination httpDestination = (HttpDestination) rsp.getChannelManager().getDestination(requestEvent.getDestinationURI());
        if (httpDestination == null) {
            throw new RuntimeException("Default Destination is not set.");
        }
        String url;
        if (endPtUri.endsWith("/")) {
            int length = endPtUri.length();
            endPtUri = endPtUri.substring(0, length - 1);
        }
        if (requestEvent instanceof SOAPEvent) {
            url = endPtUri + httpDestination.getChannel().getURI();
        } else {
            url = endPtUri + httpDestination.getURI();
        }
        return sendRequest(url, requestEvent, responseEventURI, timeOut, new HTTPConnectionInfo(false));
    }

   /**
    * Send SOAP request
    * 
    * @param url
    * @param requestEvent
    * @param responseEventURI
    * @param timeOut
    * @return
    * @throws Exception
    */
   public final static XiNode sendSoapRequest(String url, XiNode soapMessage, String soapAction, long timeOut, boolean isInonly)
           throws Exception {

       if (url == null) {
           throw new IllegalArgumentException("url can not be null");
       }

       if (soapMessage == null) {
           throw new IllegalArgumentException("Soap message can not be null");
       }

       if (soapAction == null || soapAction.trim().isEmpty()) {
           throw new IllegalArgumentException("Soap action can not be null or empty");
       }

       HttpUriRequest req = new HttpPost(url);
       HttpSoapClientRequestImpl clientReq = new HttpSoapClientRequestImpl(req, soapAction);
       HttpSoapClient httpSoapClient = new HttpSoapClient();

       XiNode response = httpSoapClient.sendRequest(clientReq, soapMessage, timeOut, isInonly);
       // add code send request
       return response;
   }
   
   /**
    * Send secure SOAP request
    * 
    * @param url
    * @param soapMessage
    * @param soapAction
    * @param sslContext
    * @param verifyHostName
    * @param timeOut
    * @param isInonly
    * @return
    * @throws Exception
    */
   public final static XiNode sendSecureSoapRequest(String url, XiNode soapMessage, String soapAction, SSLContext sslContext,boolean verifyHostName,long timeOut, boolean isInonly )
           throws Exception {

       if (url == null) {
           throw new IllegalArgumentException("url can not be null");
       }

       if (soapMessage == null) {
           throw new IllegalArgumentException("Soap message can not be null");
       }

       if (soapAction == null || soapAction.trim().isEmpty()) {
           throw new IllegalArgumentException("Soap action can not be null or empty");
       }
       
       
       if (sslContext == null) {
           throw new IllegalArgumentException("Invalid SSL properties");
       }

       HttpUriRequest req = new HttpPost(url);
       HttpSoapClientRequestImpl clientReq = new HttpSoapClientRequestImpl(req, soapAction);
       HttpSoapClient httpSoapClient = new HttpSoapClient(sslContext, verifyHostName);

       XiNode response = httpSoapClient.sendRequest(clientReq, soapMessage, timeOut, isInonly);
       // add code send request
       return response;
   }

    /**
     * Creates a request to execute
     *
     * @return a request without an entity
     */
    public final static HttpUriRequest createRequest(String url, final String httpMethod, boolean isExpectContinueHeaderDisabled, String... optionalHeaders) {
        HttpUriRequest req = null;
        
    	switch(httpMethod) {
    	case HttpMethods.METHOD_GET : req = new HttpGet(url); break;
    	case HttpMethods.METHOD_POST : req = new HttpPost(url); break;
    	case HttpMethods.METHOD_PUT : req = new HttpPut(url); break;
    	case HttpMethods.METHOD_DELETE : req = new HttpDelete(url); break;
    	case HttpMethods.METHOD_OPTIONS: req = new HttpOptions(url); break;
    	case HttpMethods.METHOD_TRACE: req = new HttpTrace(url); break;
    	case HttpMethods.METHOD_HEAD: req = new HttpHead(url); break;
    	case HttpMethods.METHOD_PATCH: req = new HttpPatch(url); break;
    	default: throw new RuntimeException("Invalid HTTP method specified '" + httpMethod + "'. Valid method types are GET/POST/PUT/DELETE/OPTIONS/HEAD/TRACE/PATCH.");
    	}
    	
        if (req != null && isExpectContinueHeaderDisabled) {
        	req.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
        }
        
        // Optional headers
        if (optionalHeaders != null) {
            for (int i = 0; i < optionalHeaders.length; i++) {
                int separator = optionalHeaders[i].indexOf('=');
                if (separator != -1) {
                    String optionalHeaderName = optionalHeaders[i].substring(0, separator);
                    if (separator < optionalHeaders[i].length()) {
                        String optionalHeaderValue = optionalHeaders[i].substring(separator + 1, optionalHeaders[i].length());
                        req.addHeader(optionalHeaderName, optionalHeaderValue);
                    }
                }
            }
        }

        return req;
    }

    /**
     * Disable cookies interactions i.e. accepting from the server as well as sending from the client.
     * 
     * @param httpDestination
     */
    private final static void disableCookies(HttpDestination httpDestination) {
    	HttpChannel channel = (HttpChannel) httpDestination.getChannel();
    	HttpComponentsClient client = (HttpComponentsClient) channel.getHttpChannelClient();
    	client.removeCookieInterceptors();
    }
    
    /**
     * Enable cookies interactions i.e. accepting from the server as well as sending from the client.
     * 
     * @param httpDestination
     */
    private final static void enableCookies(HttpDestination httpDestination) {
    	HttpChannel channel = (HttpChannel) httpDestination.getChannel();
    	HttpComponentsClient client = (HttpComponentsClient) channel.getHttpChannelClient();
    	client.addCookieInterceptors();
    }
    
    /**
     * Set Http Proxy route for a given transaction
     * 
     * @param requestEvent
     * @param host
     * @param port
     */
    private final static void setHttpProxy(HttpDestination httpDestination, String proxyHost, int proxyPort) {
    	if (proxyHost != null && !proxyHost.isEmpty() && proxyPort > 0) {
        	HttpChannel channel = (HttpChannel) httpDestination.getChannel();
        	HttpComponentsClient client = (HttpComponentsClient) channel.getHttpChannelClient();
        	client.setHttpProxy(proxyHost, proxyPort);
        }
    }
    
    /**
     * Clear Http Proxy route if already set
     * 
     * @param requestEvent
     */
    private final static void clearHttpProxy(HttpDestination httpDestination) {
    	HttpChannel channel = (HttpChannel) httpDestination.getChannel();
    	HttpComponentsClient client = (HttpComponentsClient) channel.getHttpChannelClient();
    	client.clearHttpProxy();
    }
}
