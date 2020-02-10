package com.tibco.cep.driver.http.client.impl.httpcomponents;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.RequestAddCookies;
import org.apache.http.client.protocol.ResponseProcessCookies;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import com.tibco.cep.driver.http.HttpChannelConstants;
import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.driver.http.HttpDestination.HttpClientSerializationContext;
import com.tibco.cep.driver.http.HttpMethods;
import com.tibco.cep.driver.http.HttpUtils;
import com.tibco.cep.driver.http.client.HttpChannelClient;
import com.tibco.cep.driver.http.client.HttpChannelClientRequest;
import com.tibco.cep.driver.http.server.utils.CustomSSLSocketFactory;
import com.tibco.cep.driver.http.server.utils.SSLUtils.SSLProtocol;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.exception.impl.BEExceptionImpl;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

public class HttpComponentsClient implements HttpChannelClient {

    private int port;
    private String host;
    private String methodType;

    private HttpDestination destination;
    private URL destURL;

    /**
     * The default parameters.
     * Instantiated in {@link #setup setup}.
     */
    private static HttpParams defaultParameters = null;
    private static ExecutorService newRequestThreadPool;

    /**
     * The scheme registry.
     * Instantiated in {@link #setup setup}.
     */
    private SchemeRegistry supportedSchemes;
    private boolean initialized = false;
    private HttpClient httpClient;
    private HttpChannelClientRequest clientRequest;
    private SSLContext sslContext;
    private boolean verifyHostName;
    private int clientSocketTimeout;
    private LogManager m_LogManager;
    private Logger m_Logger;
    private Properties beProperties;
    private int maxConnectionPerRoute;
    private BEExceptionImpl communicationException;

    public HttpComponentsClient(String host, int port, String methodType, SSLContext sslContext)
            throws Exception {
        this.host = host;
        this.port = port;
        this.methodType = methodType;
        this.sslContext = sslContext;

        // Get the LogManager object from LogManagerFactory
        m_LogManager = LogManagerFactory.getLogManager();
        // Get the logger from LogManager
        m_Logger = m_LogManager.getLogger(HttpComponentsClient.class);
    }

    public HttpComponentsClient(HttpChannelClientRequest clientRequest, SSLContext sslContext, HttpDestination destination, boolean verifyHostName)
            throws Exception {
        this.clientRequest = clientRequest;
        this.sslContext = sslContext;
        this.destination = destination;

        this.verifyHostName = verifyHostName;


        // Get the LogManager object from LogManagerFactory
        m_LogManager = LogManagerFactory.getLogManager();
        // Get the logger from LogManager
        m_Logger = m_LogManager.getLogger(HttpComponentsClient.class);
    }

    public String getHost() {
        return host;
    }

    public String getMethodType() {
        return methodType;
    }

    public int getPort() {
        return port;
    }

    public void setDestinationURL(HttpDestination destination) throws MalformedURLException {
        this.destination = destination;
        this.destURL = new URL(HttpMethods.PROTOCOL_HTTP,
                getHost(),
                getPort(),
                destination.getURI());
        String url = destURL.toExternalForm();
        String[] optionalHeaders = {"Content-type=" + HttpMethods.DEFAULT_CONTENT_TYPE};
        clientRequest = new HttpComponentsClientRequest(HttpComponentsClientService.createRequest(url, methodType, true, optionalHeaders));
    }

    /**
     * Performs general setup.
     * This should be called only once.
     */
    public void init() {
        if (!initialized) {
            RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
            beProperties = rsp.getProperties();

            this.maxConnectionPerRoute = Integer.parseInt(beProperties.getProperty("com.tibco.be.http.client.maxConnPerRoute", "10"));
            this.clientSocketTimeout = Integer.parseInt(beProperties.getProperty("com.tibco.be.http.client.socketTimeout", "0"));
            int maxTotalConnections = Integer.parseInt(beProperties.getProperty("com.tibco.be.http.client.maxTotalConnnections", "20"));
            int retryCnt = Integer.parseInt(beProperties.getProperty("com.tibco.be.http.client.retryCount", "0"));

            setSupportedSchemes();
            String defaultEncoding = "UTF-8";
            // prepare parameters
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, defaultEncoding);
            HttpProtocolParams.setHttpElementCharset(params, defaultEncoding);
            HttpProtocolParams.setUseExpectContinue(params, true);
            params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, clientSocketTimeout); //this should be configurable, a value of 0 means server will never timeout
            params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, maxTotalConnections);
            params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRoute() {

                public int getMaxForRoute(HttpRoute route) {
                    return maxConnectionPerRoute;
                }

            });
            defaultParameters = params;
            ThreadSafeClientConnManager ccm = new ThreadSafeClientConnManager(defaultParameters, supportedSchemes);
            httpClient = new DefaultHttpClient(ccm, defaultParameters);
            ((DefaultHttpClient)httpClient).setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(retryCnt, false));

            initializeRequestThreadPool();
            initialized = true;
        }

    } // setup

    
    synchronized private void initializeRequestThreadPool() {
        if (newRequestThreadPool == null) {
            ThreadFactory factory = new ThreadFactory() {
                private int n = 0;

                public Thread newThread(Runnable r) {
                    Thread t = Executors.defaultThreadFactory().newThread(r);
                    n++;
                    t.setName("HTTP Request Thread " + n);
                    t.setDaemon(true);
                    return t;
                }
            };
            newRequestThreadPool = Executors.newCachedThreadPool(factory);
        }
    }


    private void setSupportedSchemes() {
        supportedSchemes = new SchemeRegistry();
        // Register the "http" protocol scheme, it is required
        // by the default operator to look up socket factories.
        if (sslContext != null) {
        	CustomSSLSocketFactory socketFactory = new CustomSSLSocketFactory(sslContext);
        	
        	// restrict to SSLv3 if the protocol expected is SSL
        	if (sslContext.getProtocol().contains(SSLProtocol.SSL.getProtocol())) {
        		socketFactory.setRestrictedSSLProtocol(SSLProtocol.SSLV3.getProtocol());
        	}
            supportedSchemes.register(new Scheme(HttpMethods.PROTOCOL_HTTPS, socketFactory, 443)); 
            if (!verifyHostName)
                socketFactory
                        .setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } else {
            SocketFactory sf = PlainSocketFactory.getSocketFactory();
            supportedSchemes.register(new Scheme(HttpMethods.PROTOCOL_HTTP, sf, 80));
        }
    }

    public void writeContent(byte[] bytes) {
        if (clientRequest instanceof HttpGet) {
            throw new RuntimeException("HTTP GET method cannot be used to send byte array");
        } else if (clientRequest instanceof HttpPost) {
            ((HttpPost) clientRequest).setEntity(new ByteArrayEntity(bytes));
        }
    }


    public void writeContent(NameValuePair[] params) {
        for (int i = 0; i < params.length; i++) {
            clientRequest.setParameter(params[i].getName(), params[i].getValue());
        }
    }

    public void execute(HttpClientSerializationContext clientContext) {
        SimpleEvent requestEvent = clientContext.getRequestEvent();
        HttpUriRequest clientReq = null;
        HttpChannelClientRequest clientRequestToSend = clientContext.getClientRequest();
        if (clientRequestToSend == null) {
            clientReq = (HttpUriRequest) clientRequest.getRequestMethod();
        } else {
            clientReq = (HttpUriRequest) clientRequestToSend.getRequestMethod();
        }
        HttpClientRequestExecutor clientServiceExecutor = new HttpClientRequestExecutor(httpClient, clientReq, destination, requestEvent);

        if (newRequestThreadPool == null)
            initializeRequestThreadPool();

        newRequestThreadPool.execute(clientServiceExecutor);
    }

    //TODO explore removal of job processor.
    public Event executeSync(final HttpClientSerializationContext clientContext, final String responseEventURI, long timeOut) {
    	communicationException = null;
        final java.util.List<org.apache.http.Header> responseHeadersList = new java.util.ArrayList<org.apache.http.Header>();
        Callable<ResponseWrapper> job = new Callable<ResponseWrapper>() {
            @Override
            public ResponseWrapper call() throws Exception {
                HttpUriRequest clientReq;
                HttpChannelClientRequest clientRequestToSend = clientContext.getClientRequest();
                if (clientRequestToSend == null) {
                    clientReq = (HttpUriRequest) clientRequest.getRequestMethod();
                } else {
                    clientReq = (HttpUriRequest) clientRequestToSend.getRequestMethod();
                }
                org.apache.http.client.ResponseHandler<ResponseWrapper> handler = new org.apache.http.client.ResponseHandler<ResponseWrapper>() {
                    public ResponseWrapper handleResponse(HttpResponse response) throws java.io.IOException {
                        org.apache.http.HttpEntity entity = response.getEntity();
                        
                        org.apache.http.Header[] responseHeaders = response.getAllHeaders();
                        for (org.apache.http.Header header : responseHeaders) {
                            responseHeadersList.add(header);
                        }
                        
                        byte[] data = null;
                        if (entity != null) {
                            //This will ensure connection and other low level resources
                            //are closed
                        	entity = HttpUtils.decompressEntity(entity);
                            data = org.apache.http.util.EntityUtils.toByteArray(entity);
                        }
                        return new ResponseWrapper(data, response.getStatusLine().getStatusCode());
                    }
                };
                try {
                    return httpClient.execute(clientReq, handler);
                } catch (Throwable e) {
                	Throwable baseException = e.getCause() != null ? e.getCause() : e;
                	communicationException = new BEExceptionImpl("REQUEST_ERROR", baseException.getMessage(), BEExceptionImpl.wrapThrowable(baseException));
                	return null;
                }
            }
        };

        final RuleSession session = RuleSessionManager.getCurrentRuleSession();
        JobProcessor<ResponseWrapper> jobProcessor = new JobProcessor<ResponseWrapper>(job, "HttpRequest", timeOut);
        ResponseWrapper responseWrapper = null;
        try {
        	responseWrapper = jobProcessor.perform();
        } catch(Exception e) {
        	Throwable baseException = e.getCause() != null ? e.getCause() : e;
        	String errorType = (baseException instanceof TimeoutException) ? "REQUEST_TIMEOUT_ERROR" : "REQUEST_ERROR";
        	String errorMessage = (baseException instanceof TimeoutException) ? "Timeout error while making http request to : " + clientRequest.getPath() : baseException.getMessage();
        	communicationException = new BEExceptionImpl(errorType, errorMessage, BEExceptionImpl.wrapThrowable(baseException));
        }
        Event respEvent = null;
        if (communicationException != null) {
        	throw communicationException;
        } else {
        	HttpComponentsClientResponse clientResponse = new HttpComponentsClientResponse(responseWrapper.getData(), responseHeadersList, null);
        	TypeDescriptor typeDescriptor = session.getRuleServiceProvider().getTypeManager().getTypeDescriptor(responseEventURI);
        	if (typeDescriptor == null) {
        		throw new IllegalArgumentException("Invalid response event uri: " + responseEventURI);
        	}
        	Class responseEventType = typeDescriptor.getImplClass();
        	HashMap overrideData = new HashMap();
        	overrideData.put(HttpUtils.HTTP_RESPONSE_EVENT_TYPE, responseEventType);
        	overrideData.put(RuleSession.class.getName(), session);
        	try {
        		respEvent = clientContext.getDestination().processResponse(clientResponse, overrideData);
        		if (responseWrapper.getResponseStatus() > 0 && respEvent instanceof SimpleEvent) {
        			SimpleEvent responseEvent = (SimpleEvent) respEvent;
        			for (String propertyName : responseEvent.getPropertyNames()) {
        				if (propertyName.equalsIgnoreCase(HttpChannelConstants.HTTP_STATUS_CODE_HEADER)) {
        					try {
        						responseEvent.setPropertyValue(propertyName, responseWrapper.getResponseStatus());
        					} catch(ClassCastException cce) {
        						// event property of type String
        						responseEvent.setPropertyValue(propertyName, responseWrapper.getResponseStatus()+"");
        					}
        					break;
        				}
        			}                	
        		}
        	} catch (Exception e) {
        		m_Logger.log(Level.ERROR, e, "Error happened while processing creating response event : %1$s ", e.getMessage());
        	}
        }
        return respEvent;
    }

    public HttpChannelClientRequest getClientRequest() {
        return clientRequest;
    }

    public void shutDown() {
        newRequestThreadPool.shutdown();
        newRequestThreadPool = null;
    }
    
    /**
     * Remove cookie interceptors
     */
    public void removeCookieInterceptors() {
    	((DefaultHttpClient)httpClient).removeRequestInterceptorByClass(RequestAddCookies.class);
    	((DefaultHttpClient)httpClient).removeResponseInterceptorByClass(ResponseProcessCookies.class);
    }
    
    /**
     * Add cookie interceptors
     */
    public void addCookieInterceptors() {
    	if (((DefaultHttpClient)httpClient).getRequestInterceptorCount() < 10) {
    		((DefaultHttpClient)httpClient).addRequestInterceptor(new RequestAddCookies());
    		((DefaultHttpClient)httpClient).addResponseInterceptor(new ResponseProcessCookies());
    	}
    }
    
    /**
     * Set's an Http proxy route
     * 
     * @param host
     * @param port
     */
    public void setHttpProxy(String host, int port) {
    	ClientConnectionManager manager = httpClient.getConnectionManager();
    	if (manager != null && manager.getSchemeRegistry().get(HttpMethods.PROTOCOL_HTTP) == null) {
    		manager.getSchemeRegistry().register(new Scheme(HttpMethods.PROTOCOL_HTTP, PlainSocketFactory.getSocketFactory(), 80));
    	}
//    	((DefaultHttpClient)httpClient).getCredentialsProvider().setCredentials(
//    			new AuthScope(host, port), 
//    			new UsernamePasswordCredentials("admin", "admin"));
        HttpHost proxy = new HttpHost(host, port);
    	((DefaultHttpClient)httpClient).getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
    }
    
    /**
     * Clear's any http proxy route that is set
     */
    public void clearHttpProxy() {
    	((DefaultHttpClient)httpClient).getParams().removeParameter(ConnRoutePNames.DEFAULT_PROXY);
    }
}

/**
 * Wrap response contents data and status code
 */
class ResponseWrapper {
	private byte[] data;
	private int responseStatus;
	
	public ResponseWrapper(byte[] data, int responseStatus) {
		this.data = data;
		this.responseStatus = responseStatus;
	}

	public byte[] getData() {
		return data;
	}

	public int getResponseStatus() {
		return responseStatus;
	}
}
