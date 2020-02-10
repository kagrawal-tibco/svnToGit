package com.tibco.cep.driver.http.client.impl.httpcommons;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.driver.http.HttpDestination.HttpClientSerializationContext;
import com.tibco.cep.driver.http.HttpMethods;
import com.tibco.cep.driver.http.HttpUtils;
import com.tibco.cep.driver.http.client.HttpChannelClient;
import com.tibco.cep.driver.http.client.HttpChannelClientRequest;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.model.rule.impl.ParameterDescriptorImpl;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionManagerImpl;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Apr 3, 2008
 * Time: 11:45:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpCommonsClient implements HttpChannelClient, HttpMethods {

    HttpClient httpClient;
    URL destURL;
    private int port;
    private String host;
    private HttpDestination destination;
    String methodType;
    HttpCommonsClientRequest method;

    public HttpCommonsClient(String host, int port, String methodType) {
        this.host = host;
        this.port = port;
        this.methodType = methodType;
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

    public void init() {
        httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        DefaultHttpMethodRetryHandler retryhandler = new DefaultHttpMethodRetryHandler(DEFAULT_RETRY_COUNT, false);
        httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, retryhandler);
    }

    /*public void start() {
    
    }
    
    public void stop() {
    
    }*/
    public void setDestinationURL(HttpDestination destination) throws MalformedURLException {
        this.destination = destination;
        this.destURL = new URL(PROTOCOL_HTTP,
                getHost(),
                getPort(),
                destination.getURI());
        String url = destURL.toExternalForm();
        String[] optionalHeaders = {"Content-type=" + HttpMethods.DEFAULT_CONTENT_TYPE};
        method = new HttpCommonsClientRequest(HttpCommonsClientService.createRequest(url, methodType, optionalHeaders));
    }

    public void execute(HttpClientSerializationContext clientContext) {
        try {
            SimpleEvent requestEvent = clientContext.getRequestEvent();
            HttpMethodBase clientReq = null;
            HttpChannelClientRequest clientRequestToSend = clientContext.getClientRequest();
            if (clientRequestToSend == null) {
                clientReq = (HttpMethodBase) method.getRequestMethod();
            } else {
                clientReq = (HttpMethodBase) clientRequestToSend.getRequestMethod();
            }

            // Execute the method.
            int statusCode = httpClient.executeMethod(clientReq);
            handleResponse(clientReq, requestEvent);
        } catch (HttpException e) {
            System.err.println("Fatal protocol violation: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Fatal transport error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleResponse(HttpMethodBase clientReq, SimpleEvent requestEvent) {
        try {
            RuleSessionManagerImpl sessionManagerImpl = (RuleSessionManagerImpl) RuleServiceProviderManager.getInstance().getDefaultProvider().getRuleRuntime();
            RuleSession ruleSession = RuleSessionManager.getCurrentRuleSession();
            String ruleFunctionURL = (String) clientReq.getParams().getParameter(HttpUtils.HTTP_RESPONSE_SUCCESS_CALLBACK_URI);
            String correlationId = (String) clientReq.getParams().getParameter(HttpUtils.HTTP_REQ_CORRELATION_ID);
            RuleFunction rf = ((RuleSessionImpl) ruleSession).getRuleFunction(ruleFunctionURL);
            ParameterDescriptorImpl paramDesc = (ParameterDescriptorImpl) rf.getParameterDescriptors()[2];
            HttpCommonsClientResponse response = new HttpCommonsClientResponse(clientReq);
            Class responseEventType = paramDesc.getType();
            HashMap overrideData = new HashMap();
            overrideData.put(HttpUtils.HTTP_RESPONSE_EVENT_TYPE, responseEventType);
            SimpleEvent respEvent = destination.processResponse(response, overrideData);
            Object[] params = new Object[]{correlationId, requestEvent, respEvent};
            rf.invoke(params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void example(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: ChunkEncodedPost <file>");
            System.out.println("<file> - full path to a file to be posted");
            System.exit(1);
        }
        HttpClient client = new HttpClient();

        PostMethod httppost = new PostMethod("http://localhost:8080/httpclienttest/body");

        File file = new File(args[0]);
        httppost.setRequestEntity(new InputStreamRequestEntity(
                new FileInputStream(file), file.length()));

        try {
            client.executeMethod(httppost);

            if (httppost.getStatusCode() == HttpStatus.SC_OK) {
                System.out.println(httppost.getResponseBodyAsString());
            } else {
                System.out.println("Unexpected failure: " + httppost.getStatusLine().toString());
            }
        } finally {
            httppost.releaseConnection();
        }
    }

    public HttpChannelClientRequest getClientRequest() {
        return method;
    }
}
