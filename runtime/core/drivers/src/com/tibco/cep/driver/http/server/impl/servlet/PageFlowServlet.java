package com.tibco.cep.driver.http.server.impl.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.AsyncContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Globals;

import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.scheduler.TaskController;
import com.tibco.cep.runtime.session.RuleSession;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 01/8/11
 * Time: 3:09 PM
 * 
 * @author Vikram
 */
public class PageFlowServlet extends GenericChannelServlet {

	private static final long serialVersionUID = 6096483646833995104L;
	private static transient Logger LOGGER = LogManagerFactory.getLogManager().getLogger(PageFlowServlet.class);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        httpDestinations = new TreeMap<String, HttpDestination>(new KeyComparator());
        //Only put this channel's destinations.
        Collection<HttpDestination> pageFlowDestinations = httpChannel.getDestinations().values();
        for (HttpDestination destination : pageFlowDestinations) {
            boolean isPageFlow = destination.isPageFlowDestination();
            if (isPageFlow) {
                String contextUri = destination.getContextUri();
                httpDestinations.put(contextUri, destination);
            }
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        boolean isAsyncSupported = (Boolean)httpServletRequest.getAttribute(Globals.ASYNC_SUPPORTED_ATTR);
        if (!isAsyncSupported) {
            httpServletRequest.setAttribute(Globals.ASYNC_SUPPORTED_ATTR, true);
        }
        LOGGER.log(Level.DEBUG, "Servlet request method type %s", httpServletRequest.getMethod());
        
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
        asyncContext.setTimeout(DEFAULT_TIMEOUT_INTERVAL);
        HttpDestination httpDestination = getMatchingDestination(httpServletRequest);
        if (httpDestination == null) {
            //No use anyway
            LOGGER.log(Level.WARN, "No destination found matching url-pattern %s", httpServletRequest.getRequestURI());
            
            return;
        }

        String rfPath = httpDestination.getPageFlowRuleFunction();
        if (rfPath == null) {
            LOGGER.log(Level.WARN, "No rule function configured for destination %s", httpDestination.getName());
            return;
        }
        Collection<RuleSession> boundRuleSessions = httpDestination.getBoundedRuleSessions();
        if (boundRuleSessions != null) {
            for (RuleSession ruleSession : boundRuleSessions) {
                //Do not assert for time-being.
                LOGGER.log(Level.DEBUG, "Invoking rule function %s for session %s", rfPath, ruleSession.getName());
                TaskController taskController = ruleSession.getTaskController();
                try {
                    taskController.processTask(httpDestination.getURI(),
                                               new RuleFunctionExecTask(ruleSession, asyncContext, rfPath));
                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, "Exception while invoking rule function", e);
                }
            }
        }
    }

    /**
     * Called by the server (via the <code>service</code> method)
     * to allow a servlet to handle a POST request.
     * <p/>
     * The HTTP POST method allows the client to send
     * data of unlimited length to the Web server a single time
     * and is useful when posting information such as
     * credit card numbers.
     * <p/>
     * <p>When overriding this method, read the request data,
     * write the response headers, get the response's writer or output
     * stream object, and finally, write the response data. It's best
     * to include content type and encoding. When using a
     * <code>PrintWriter</code> object to return the response, set the
     * content type before accessing the <code>PrintWriter</code> object.
     * <p/>
     * <p>The servlet container must write the headers before committing the
     * response, because in HTTP the headers must be sent before the
     * response body.
     * <p/>
     * <p>Where possible, set the Content-Length header (with the
     * {@link javax.servlet.ServletResponse#setContentLength} method),
     * to allow the servlet container to use a persistent connection
     * to return its response to the client, improving performance.
     * The content length is automatically set if the entire response fits
     * inside the response buffer.
     * <p/>
     * <p>When using HTTP 1.1 chunked encoding (which means that the response
     * has a Transfer-Encoding header), do not set the Content-Length header.
     * <p/>
     * <p>This method does not need to be either safe or idempotent.
     * Operations requested through POST can have side effects for
     * which the user can be held accountable, for example,
     * updating stored data or buying items online.
     * <p/>
     * <p>If the HTTP POST request is incorrectly formatted,
     * <code>doPost</code> returns an HTTP "Bad Request" message.
     *
     * @param httpServletRequest  an {@link javax.servlet.http.HttpServletRequest} object that
     *             contains the request the client has made
     *             of the servlet
     * @param httpServletResponse an {@link javax.servlet.http.HttpServletResponse} object that
     *             contains the response the servlet sends
     *             to the client
     * @throws java.io.IOException            if an input or output error is
     *                                        detected when the servlet handles
     *                                        the request
     * @throws javax.servlet.ServletException if the request for the POST
     *                                        could not be handled
     * @see javax.servlet.ServletOutputStream
     * @see javax.servlet.ServletResponse#setContentType
     */
    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        doGet(httpServletRequest, httpServletResponse);
    }
    
    /**
     * Called by the server (via the <code>service</code> method) 
     * to allow a servlet to handle a OPTIONS request. 
     * The OPTIONS request determines which HTTP methods the server supports and returns 
     * an appropriate header. For example, if a servlet overrides doGet, this method returns the following header:
     * <p>
     *     Allow: GET, HEAD, TRACE, OPTIONS
     * </p>
     * <p>
     * There's no need to override this method unless the servlet implements new HTTP methods, 
     * beyond those implemented by HTTP 1.1.
     * </p>
     * <p>
     * @param httpServletRequest - {@link javax.servlet.http.HttpServletRequest} object that
     *             contains the request the client has made of the servlet
     * @param httpServletResponse - {@link javax.servlet.http.HttpServletResponse} object that
     *             contains the response the servlet sends to the client
     *             
     * @throws java.io.IOException - if an input or output error occurs while the servlet is handling the OPTIONS request
     * @throws javax.servlet.ServletException - if the request for the OPTIONS could not be handled
     * </p>
     */
    @Override
    protected void doOptions(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    		throws ServletException, IOException {
    	 doGet(httpServletRequest, httpServletResponse);
    }
    
    /**
     * Called by the server (via the <code>service</code> method) 
     * to allow a servlet to handle a DELETE request. 
     * The DELETE operation allows a client to remove a document or Web page from the server.
     * <p>
     * This method does not need to be either safe or idempotent. Operations requested through DELETE 
     * can have side effects for which users can be held accountable. When using this method, it may be 
     * useful to save a copy of the affected URL in temporary storage.
     * </p>
     * <p>
     * If the HTTP DELETE request is incorrectly formatted, doDelete returns an HTTP "Bad Request" message.
     * </p>
     * <p>
     * 
     * @param httpServletRequest - {@link javax.servlet.http.HttpServletRequest} object that
     *             contains the request the client has made of the servlet
     * @param httpServletResponse - {@link javax.servlet.http.HttpServletResponse} object that
     *             contains the response the servlet sends to the client
     *             
     * @throws java.io.IOException - if an input or output error occurs while the servlet is handling the DELETE request
     * @throws javax.servlet.ServletException - if the request for the DELETE could not be handled
     * </p>
     */
    @Override
    protected void doDelete(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    		throws ServletException, IOException {
    	 doGet(httpServletRequest, httpServletResponse);
    }
    
    /**
     * Called by the server (via the service method) to allow a servlet to handle a TRACE request. 
     * A TRACE returns the headers sent with the TRACE request to the client, so that they can be used in debugging. 
     * There's no need to override this method.
     * <p>
     * 
     * @param httpServletRequest - {@link javax.servlet.http.HttpServletRequest} object that
     *             contains the request the client has made of the servlet
     * @param httpServletResponse - {@link javax.servlet.http.HttpServletResponse} object that
     *             contains the response the servlet sends to the client
     *             
     * @throws java.io.IOException - if an input or output error occurs while the servlet is handling the TRACE request
     * @throws javax.servlet.ServletException - if the request for the TRACE could not be handled
     * </p>
     */
    @Override
    protected void doTrace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    		throws ServletException, IOException {
    	 doGet(httpServletRequest, httpServletResponse);
    }
    
    /**
     * Called by the server (via the service method) to allow a servlet to handle a PUT request. 
     * The PUT operation allows a client to place a file on the server and is similar to sending a file by FTP.
     * <p>
     * When overriding this method, leave intact any content headers sent with the request 
     * (including Content-Length, Content-Type, Content-Transfer-Encoding, Content-Encoding, Content-Base, 
     * Content-Language, Content-Location, Content-MD5, and Content-Range). If your method cannot handle a 
     * content header, it must issue an error message (HTTP 501 - Not Implemented) and discard the request. 
     * For more information on HTTP 1.1, see RFC 2616.
     * </p>
     * <p>
     * This method does not need to be either safe or idempotent. Operations that doPut performs can have 
     * side effects for which the user can be held accountable. When using this method, it may be useful to 
     * save a copy of the affected URL in temporary storage.
     * </p>
     * <p>
     * If the HTTP PUT request is incorrectly formatted, doPut returns an HTTP "Bad Request" message.
     * </p>
     * <p>
     * @param httpServletRequest - {@link javax.servlet.http.HttpServletRequest} object that
     *             contains the request the client has made of the servlet
     * @param httpServletResponse - {@link javax.servlet.http.HttpServletResponse} object that
     *             contains the response the servlet sends to the client
     *             
     * @throws java.io.IOException - if an input or output error occurs while the servlet is handling the PUT request
     * @throws javax.servlet.ServletException - if the request for the PUT could not be handled
     * </p>
     */
    @Override
    protected void doPut(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    		throws ServletException, IOException {
    	 doGet(httpServletRequest, httpServletResponse);
    }
    
    /**
     * Receives an HTTP HEAD request from the protected service method and handles the request. 
     * The client sends a HEAD request when it wants to see only the headers of a response, such 
     * as Content-Type or Content-Length. The HTTP HEAD method counts the output bytes in the response to 
     * set the Content-Length header accurately.
     * <p>
     * If you override this method, you can avoid computing the response body and just set the 
     * response headers directly to improve performance. Make sure that the doHead method you write 
     * is both safe and idempotent (that is, protects itself from being called multiple times for one 
     * HTTP HEAD request).
     * </p>
     * <p>
     * If the HTTP HEAD request is incorrectly formatted, doHead returns an HTTP "Bad Request" message.
     * </p>
     * @param httpServletRequest - {@link javax.servlet.http.HttpServletRequest} object that
     *             contains the request the client has made of the servlet
     * @param httpServletResponse - {@link javax.servlet.http.HttpServletResponse} object that
     *             contains the response the servlet sends to the client
     *             
     * @throws java.io.IOException - if an input or output error occurs while the servlet is handling the HEAD request
     * @throws javax.servlet.ServletException - if the request for the HEAD could not be handled
     * </p>
     */
    @Override
    protected void doHead(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    		throws ServletException, IOException {
    	doGet(httpServletRequest, httpServletResponse);
    }
    
    /**
     * Add support for HTTP PATCH, while falling back of default support for other non supported requests
     */
    @Override
    public void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        if (httpServletRequest.getMethod().equalsIgnoreCase("PATCH")) {
        	doGet(httpServletRequest, httpServletResponse);
        } else {
            super.service(httpServletRequest, httpServletResponse);
        }
    }
 
    @Override
    protected HttpDestination getMatchingDestination(final HttpServletRequest servletRequest) throws ServletException {
        HttpDestination destination = null;
        String contextPath = servletRequest.getRequestURI();
        //Find a destination matching this
        if (contextPath == null) {
            throw new ServletException("Invalid context path");
        }

        Set<String> keySet = httpDestinations.keySet();
        /**
         * TODO Temporary logic. Revisit later.
         */
        for (String key : keySet) {
            //Try matching
            if (key.endsWith("*")) {
                if (contextPath.length() > key.length()) {
                    if (contextPath.regionMatches(0, key, 0, key.length() - 1)) {
                        //Match found
                        destination = httpDestinations.get(key);
                    }
                }
            } else {
                destination = httpDestinations.get(contextPath);
            }
            if (destination != null) {
                break;
            }
        }
        //equality check
        return destination;
    }
    
    class RuleFunctionExecTask implements Runnable {

        private RuleSession ruleSession;
        private AsyncContext asyncContext;
        private String callbackRFPath;
        
        RuleFunctionExecTask(RuleSession ruleSession,
        		AsyncContext asyncContext,
        		String callbackRFPath) {
        	this.ruleSession = ruleSession;
        	this.asyncContext = asyncContext;
        	this.callbackRFPath = callbackRFPath;
        }

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p/>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            try {
                ruleSession.invokeFunction(callbackRFPath, new Object[]{asyncContext}, false);
            } catch (Exception re) {
                LOGGER.log(Level.ERROR, "Exception while invoking rule function", re);
            }
        }
    }
}

/**
 * Simple key comparator
 * @author vpatil
 */
class KeyComparator implements Comparator<String> {
    @Override
    public int compare(String s1, String s2) {
        if (s1.length() < s2.length()) {
            return 1;
        } else if (s1.length() > s2.length()) {
            return -1;
        } else {
            if (s1.intern() == s2.intern()) {
                return 0;
            } else {
                //Compare hashcodes
                if (s1.hashCode() < s2.hashCode()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
    }
}
