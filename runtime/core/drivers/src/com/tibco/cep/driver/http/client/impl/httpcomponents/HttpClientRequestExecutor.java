package com.tibco.cep.driver.http.client.impl.httpcomponents;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import com.tibco.cep.driver.http.HttpChannelConstants;
import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.driver.http.HttpUtils;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.exception.BEException;
import com.tibco.cep.runtime.model.exception.impl.BEExceptionImpl;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

public class HttpClientRequestExecutor implements Runnable {
    
    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(HttpClientRequestExecutor.class);

    private HttpClient client;

    private HttpUriRequest request;

    private HttpDestination destination;

    private RuleSession currentRuleSession;

    private SimpleEvent requestEvent;
    
    private static final String RESPONSE_EVENT_ON_ERROR_ENABLED_PROPERTY = "be.engine.http.client.async.responseEventOnError.enabled";

    public HttpClientRequestExecutor(HttpClient client,
                                     HttpUriRequest request,
                                     HttpDestination destination,
                                     SimpleEvent requestEvent) {
        this.client = client;
        this.request = request;
        this.destination = destination;
        this.requestEvent = requestEvent;
        this.currentRuleSession = RuleSessionManager.getCurrentRuleSession();
    }

    public void run() {
        HttpResponse httpResponse = null;
        String correlationId = null;
        StatusLine statusLine = null;
        try {
            httpResponse = client.execute(request);
            String successRuleFunctionURL = (String) httpResponse.getParams().getParameter(HttpUtils.HTTP_RESPONSE_SUCCESS_CALLBACK_URI);
            correlationId = (String) httpResponse.getParams().getParameter(HttpUtils.HTTP_REQ_CORRELATION_ID);
            statusLine = httpResponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode != 200) {
                throw new RuntimeException("Request sent to :" + request.getURI() + " failed with HTTP status code " + statusLine);
            }

            if (successRuleFunctionURL != null) {
                RuleFunction rf = ((RuleSessionImpl) currentRuleSession).getRuleFunction(successRuleFunctionURL);
                RuleFunction.ParameterDescriptor paramDesc = rf.getParameterDescriptors()[2];                
                SimpleEvent responseEvent = getResponseEvent(httpResponse, paramDesc.getType());
                if (responseEvent != null) setStatusResponseCode(responseEvent, statusLine);
                Object[] params = new Object[]{correlationId, requestEvent, responseEvent};
                //Not running in PP context to allow concept modification in callback RF.
                currentRuleSession.invokeFunction(successRuleFunctionURL, params, true);
                
                // finally clear if any proxy is set
                if (client instanceof HttpComponentsClient) {
                	((HttpComponentsClient) client).clearHttpProxy();
                	((HttpComponentsClient) client).addCookieInterceptors();                	
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new RuntimeException("Callback function parameter order is not correct", ex);
        } catch (Exception ex) {
        	String errorRuleFunctionURL = (String) request.getParams().getParameter(HttpUtils.HTTP_RESPONSE_ERROR_CALLBACK_URI);
        	
        	if (errorRuleFunctionURL != null) {
        		String propValue = ((RuleSessionImpl) currentRuleSession).getRuleServiceProvider().getProperties().getProperty(RESPONSE_EVENT_ON_ERROR_ENABLED_PROPERTY, "false");
        		boolean responseEventOnError = Boolean.parseBoolean(propValue);
        		
        		BEException beException = null;
        		SimpleEvent responseEvent = null;
        		if (httpResponse == null) {    		
            		correlationId = (String) request.getParams().getParameter(HttpUtils.HTTP_REQ_CORRELATION_ID);
            		beException = new BEExceptionImpl("REQUEST_TIMEOUT_ERROR", "Timeout error while sending request to : " + request.getURI(), BEExceptionImpl.wrapThrowable(ex));
            	} else {
            		beException = new BEExceptionImpl("REQUEST_ERROR", "Error while sending request to : " + request.getURI() + ", failed with HTTP status code " + statusLine, BEExceptionImpl.wrapThrowable(ex));
            		
            		if (responseEventOnError) {
            			RuleFunction rf = ((RuleSessionImpl) currentRuleSession).getRuleFunction(errorRuleFunctionURL);
            			RuleFunction.ParameterDescriptor paramDesc = rf.getParameterDescriptors()[3];
            			try {
            				responseEvent = getResponseEvent(httpResponse, paramDesc.getType());
            				if (responseEvent != null) setStatusResponseCode(responseEvent, statusLine);
            			} catch(Exception exception) {
            				throw new RuntimeException(exception);
            			}
            		}
            	}
        		
            	//invoke this
                Object[] params = (responseEventOnError) ? new Object[] {correlationId, requestEvent, beException, responseEvent} : new Object[] {correlationId, requestEvent, beException};
                //Not running in PP context to allow concept modification in callback RF.
                currentRuleSession.invokeFunction(errorRuleFunctionURL, params, true);
        	}
        } finally {
            if (httpResponse != null && httpResponse.getEntity() != null) {
                HttpEntity entity = httpResponse.getEntity();
                try {
                    entity.consumeContent();
                } catch (IOException e) {
                    throw new RuntimeException("Error while sending request to :" + request.getURI(), e);
                }
            }
        }
    }
    
    /**
     * Get the response event based on the RF argument type
     * 
     * @param httpResponse
     * @param responseEventType
     * @return
     * @throws Exception
     */
    private SimpleEvent getResponseEvent( HttpResponse httpResponse, Class responseEventType) throws Exception {
    	HttpComponentsClientResponse clientResponse = new HttpComponentsClientResponse(httpResponse);
        
    	Map<String, Object> overrideData = new HashMap<String, Object>();
        overrideData.put(HttpUtils.HTTP_RESPONSE_EVENT_TYPE, responseEventType);
        overrideData.put(RuleSession.class.getName(), currentRuleSession);
        
        SimpleEvent responseEvent = destination.processResponse(clientResponse, overrideData);        
        return responseEvent;
    }
    
    /**
     * Set Http Response status
     * 
     * @param respEvent
     * @param statusLine
     */
    private void setStatusResponseCode(Event respEvent, StatusLine statusLine) {
    	int statusCode = statusLine.getStatusCode();
    	try {
    		SimpleEvent responseEvent = (SimpleEvent) respEvent;
    		for(String propertyName : responseEvent.getPropertyNames()) {
    			if (propertyName.equalsIgnoreCase(HttpChannelConstants.HTTP_STATUS_CODE_HEADER)) {
    				try {
    					responseEvent.setPropertyValue(propertyName, statusCode);
    				} catch(ClassCastException cce) {
    					// event property of type String
    					responseEvent.setPropertyValue(propertyName, statusCode+"");
    				}
    				break;
    			}
    		}
    	} catch (Exception e) {
    		LOGGER.log(Level.ERROR, e, "Error happened while processing creating response event : %1$s ", e.getMessage());
    	}
    }
}
