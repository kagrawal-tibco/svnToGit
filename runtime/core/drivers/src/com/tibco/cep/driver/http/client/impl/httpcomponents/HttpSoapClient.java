package com.tibco.cep.driver.http.client.impl.httpcomponents;

import static com.tibco.cep.driver.http.HttpChannelConstants.MESSAGE_HEADER_SOAPACTION_PROPERTY;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.Callable;

import javax.net.ssl.SSLContext;

import com.tibco.cep.driver.soap.SoapHelper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import com.tibco.cep.driver.http.HttpMethods;
import com.tibco.cep.driver.http.client.HttpSoapClientRequest;
import com.tibco.cep.driver.http.serializer.SOAPMessageSerializer;
import com.tibco.cep.driver.http.serializer.soap.SOAPTransportContext;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.soap.api.transport.TransportMessage;
import com.tibco.xml.soap.impl.transport.DefaultTransportMessage;

/**
 * 
 * @author majha
 *
 */
public class HttpSoapClient  {


    private LogManager m_LogManager;
    private Logger m_Logger;
	private SOAPMessageSerializer messageSerializer;
	private Properties beProperties;
	private int maxConnectionPerRoute;
	private int clientSocketTimeout;
	private SchemeRegistry supportedSchemes;
	private HttpParams defaultParameters;
	private DefaultHttpClient httpClient;
	private SSLContext sslContext;
	private boolean verifyHostName;

    public HttpSoapClient()
            throws Exception {
    	
    	this.messageSerializer = new SOAPMessageSerializer(); 

        // Get the LogManager object from LogManagerFactory
        m_LogManager = LogManagerFactory.getLogManager();
        // Get the logger from LogManager
        m_Logger = m_LogManager.getLogger(HttpSoapClient.class);
		init();
    }
    
    public HttpSoapClient(SSLContext sslContext, boolean verifyHostName)
            throws Exception {
    	
    	this.sslContext = sslContext;
        this.verifyHostName = verifyHostName;
    	this.messageSerializer = new SOAPMessageSerializer(); 
    	

        // Get the LogManager object from LogManagerFactory
        m_LogManager = LogManagerFactory.getLogManager();
        // Get the logger from LogManager
        m_Logger = m_LogManager.getLogger(HttpSoapClient.class);
		init();
    }
    

    public void init() {
            RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
            beProperties = rsp.getProperties();

            this.maxConnectionPerRoute = Integer.parseInt(beProperties.getProperty("com.tibco.be.http.client.maxConnPerRoute", "10"));
            this.clientSocketTimeout = Integer.parseInt(beProperties.getProperty("com.tibco.be.http.client.socketTimeout", "0"));
            int maxTotalConnections = Integer.parseInt(beProperties.getProperty("com.tibco.be.http.client.maxTotalConnnections", "20"));

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

//            initializeRequestThreadPool();;

    }
    
	private void setSupportedSchemes() {
		supportedSchemes = new SchemeRegistry();
		if (sslContext != null) {
			SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext);
			supportedSchemes.register(new Scheme(HttpMethods.PROTOCOL_HTTPS,
					socketFactory, 443));
			if (!verifyHostName)
				socketFactory
						.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		} else {

			SocketFactory sf = PlainSocketFactory.getSocketFactory();
			supportedSchemes.register(new Scheme(HttpMethods.PROTOCOL_HTTP, sf,
					80));
		}

	}
	
	private void attachSoapMessage(HttpSoapClientRequest clientRequestToSend, XiNode soapMessage) throws Exception{
		TransportMessage message = new DefaultTransportMessage();
		
		//Get the envelope part
        SoapHelper.buildSoapEnvelope(soapMessage, message);
        SoapHelper.buildAttachments(soapMessage, message);
        
        HashMap<String, String> nvp = new HashMap<String, String>();
        nvp.put(MESSAGE_HEADER_SOAPACTION_PROPERTY, "\"" + clientRequestToSend.getSoapAction() + "\"");

        clientRequestToSend.writeContent(nvp);
        //Associate a transport context
        message.setTransportContext(new SOAPTransportContext(clientRequestToSend));

        ByteArrayOutputStream oStream = new ByteArrayOutputStream();
        messageSerializer.serialize(message, clientRequestToSend, oStream);
        clientRequestToSend.writeContent(oStream.toByteArray());
	}

 
    public XiNode sendRequest(final HttpSoapClientRequest clientRequestToSend, XiNode message, long timeOut, final boolean isInOnly) throws Exception{
    	XiNode soapResponseMessageAsXiNode = null;
    	attachSoapMessage(clientRequestToSend, message);
        final java.util.List<org.apache.http.Header> responseHeadersList = new java.util.ArrayList<org.apache.http.Header>();
        Callable<byte[]> job = new Callable<byte[]>() {
            @Override
            public byte[] call() throws Exception {
                HttpUriRequest clientReq= (HttpUriRequest) clientRequestToSend.getRequestMethod();
             
                org.apache.http.client.ResponseHandler<byte[]> handler = new org.apache.http.client.ResponseHandler<byte[]>() {
                    public byte[] handleResponse(HttpResponse response) throws java.io.IOException {
                    	StatusLine statusLine = response.getStatusLine();
                        if (statusLine.getStatusCode() >= 300) {
                        	if(isInOnly)
                        		throw new HttpResponseException(statusLine.getStatusCode(),
                                    statusLine.getReasonPhrase());
                        	else if(statusLine.getStatusCode() != 500){
                        		throw new HttpResponseException(statusLine.getStatusCode(),
                                        statusLine.getReasonPhrase());
                        	}
                        }
                    	if(isInOnly){
                    		return null;
                    	}
                    	if(isSoapRequestSuccess(response)){
                    		 org.apache.http.HttpEntity entity = response.getEntity();
                             //TODO Handle params
                             org.apache.http.Header[] responseHeaders = response.getAllHeaders();
                             for (org.apache.http.Header header : responseHeaders) {
                                 responseHeadersList.add(header);
                             }
                             if (entity != null) {
                                 //This will ensure connection and other low level resources
                                 //are closed
                                 return org.apache.http.util.EntityUtils.toByteArray(entity);
                             } else {
                                 return null;
                             }
                    	}else{
                    		return null;
                    	}
                       
                    }
                };
                try {
                    return httpClient.execute(clientReq, handler);
                } catch (Throwable e) {
                    m_Logger.log(Level.ERROR, e, e.getMessage());
                    throw new Exception(e);
                }
            }
        };

        long tOut = timeOut <= 0? -1: timeOut;
        JobProcessor<byte[]> jobProcessor = new JobProcessor<byte[]>(job, "HttpRequest", tOut);
        byte[] bytesResponse = jobProcessor.perform();
        if (bytesResponse != null) {
            HttpComponentsClientResponse clientResponse = new HttpComponentsClientResponse(bytesResponse, responseHeadersList, null);
            soapResponseMessageAsXiNode = getSoapResponseMessageAsXiNode(clientResponse);
        } 
        return soapResponseMessageAsXiNode;
    }
    
    private boolean isSoapRequestSuccess(HttpResponse response){
    	int statusCode = response.getStatusLine().getStatusCode();
    	if(statusCode == 200 || statusCode == 500/*for fault message*/){
    		return true;
    	}
    	return false;
    }
    
    
    private XiNode getSoapResponseMessageAsXiNode(HttpComponentsClientResponse clientResponse) throws Exception{
    	//create a transport context
        SOAPTransportContext context = new SOAPTransportContext(clientResponse);

        //Get input stream
        byte[] data = clientResponse.getEntity();

        InputStream iStream = new BufferedInputStream((new ByteArrayInputStream(data)));

        //Get the incoming message
        TransportMessage transportMessage = messageSerializer.buildTransportMessage(iStream, context);
    	
    	return messageSerializer.buildMessageAsXiNode(transportMessage, new ExpandedName("message"));
    }



}
