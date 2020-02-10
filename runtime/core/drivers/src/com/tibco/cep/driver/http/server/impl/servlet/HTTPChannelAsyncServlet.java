package com.tibco.cep.driver.http.server.impl.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.AsyncContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 22/7/11
 * Time: 3:28 PM
 * Servlet 3.0 implementation for sending async responses to clients.
 * <p>
 * This will remove the necessity of tomcat based channel to use caller's thread
 * only for its destinations as now the library dispatcher is free to service
 * different client request once it invokes the service method of the servlet.
 * </p>
 * <p>
 * Since <b>replyEvent</b> is usually sent on different thread, it makes sense
 * to free the dispatcher thread and then let the BE worker send response upon completion of
 * RTC.
 * </p>
 */
public class HTTPChannelAsyncServlet extends GenericChannelServlet {

	private static final long serialVersionUID = 8905250803882714707L;

	private static transient Logger LOGGER = LogManagerFactory.getLogManager().getLogger(HTTPChannelAsyncServlet.class);
    int sessionTimeout = -1;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        httpDestinations = new HashMap<String, HttpDestination>();
        //Only put this channel's destinations.
        httpDestinations.putAll(httpChannel.getDestinations());
    }
    
    @Override
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
    	LOGGER.log(Level.DEBUG, "Servlet httpServletRequest method type %s", httpServletRequest.getMethod());
        try {
        	String propValue = httpChannel
    				.getChannelManager()
    				.getRuleServiceProvider()
    				.getProperties()
    				.getProperty(
    						HTTP_LATEST_REQUEST_RESPONSE_ENABLED_PROPERTY,
    						"true");
            boolean useLatestRequestResponse = Boolean.parseBoolean(propValue);
            //Get async context
            AsyncContext asyncContext = (useLatestRequestResponse) ? httpServletRequest.startAsync(httpServletRequest, httpServletResponse) : httpServletRequest.startAsync();
            //setting a higher timeout interval than default(10ms), to avoid timeout for cases like buildEar/generate classes
            asyncContext.setTimeout(DEFAULT_TIMEOUT_INTERVAL);
            HttpDestination httpDestination = getMatchingDestination(httpServletRequest);
            
            /**
             * Synchronization done in processMessage() as inputs are local variables
             * so need not be protected, and only potential thread-unsafe call
             * seems to be the one to process an event in the Controller using
             * a shared RuleSession. Hence the entire method call below has not been
             * wrapped in a synchronized block.
             */
            if (httpDestination != null) {
                Properties properties = httpChannel.getConfig().getProperties();
                httpDestination.processMessage(new HttpServerRequest(httpServletRequest, properties),
                        new HttpServerResponse(httpServletResponse, asyncContext),
                        httpServletRequest.getMethod());
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e, "Error processing request");
            throw new ServletException(e);
        }
    }

    protected HttpDestination getMatchingDestination(final HttpServletRequest servletRequest) throws ServletException {
        String contextPath = servletRequest.getRequestURI();
        contextPath = decodeUrl(contextPath);  //BE-24248
        //Find a destination matching this
        if (contextPath == null) {
            throw new ServletException("Invalid context path");
        }
        return httpDestinations.get(contextPath);
    }

    /**
     * We are going to perform the same operations for POST requests
     * as for GET methods, so this method just sends the request to
     * the doGet method.
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }   

    /**
     * It will decode encoded url, as destination URIs are not in encoded format.
     * @param url
     * @return decoded url
     * @throws ServletException 
     */
    private String decodeUrl(String url) throws ServletException  
    {  
    		  try 
              {  
            	  String uriEncoding = httpChannel.getConfig().getConnectorInfo().getUriEncoding();
            	  if (uriEncoding == null || (uriEncoding.isEmpty())) 
            	  {
            		  uriEncoding=httpChannel.getConfig().getConnectorInfo().getDefaultEncoding();
            	  }
            	  String decodeURL=URLDecoder.decode(url,uriEncoding);
                  return decodeURL;  
              } 
    		  catch (UnsupportedEncodingException e) {  
            	  LOGGER.log(Level.ERROR, e, "Error in decoding url");
            	  throw new ServletException(e);
              }  
    }
}
