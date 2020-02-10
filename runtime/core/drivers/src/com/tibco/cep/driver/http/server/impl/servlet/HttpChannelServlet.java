package com.tibco.cep.driver.http.server.impl.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tibco.cep.driver.http.HttpChannel;
import com.tibco.cep.driver.http.HttpChannelDestination;
import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelManager;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Mar 29, 2008
 * Time: 1:06:30 PM
 * Use {@link HTTPChannelAsyncServlet} instead.
 */
@Deprecated
public class HttpChannelServlet extends HttpServlet {

    private static transient Logger LOGGER = LogManagerFactory.getLogManager().getLogger(HttpChannelServlet.class);

    private ChannelManager channelManager;

    //Cache all the destinations
    private Map<String, Channel.Destination> httpDestinations = new HashMap<String, Channel.Destination>();


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Initializing HttpChannelServlet...");
            }
            Object c = config.getServletContext().getAttribute(ChannelManager.class.getName());
            if (c instanceof ChannelManager) {
                channelManager = (ChannelManager) c;
            }
            if (channelManager == null) {
                throw new ServletException("Channel manager parameter not found in the servlet context");
            }
            Collection<Channel> channels = channelManager.getChannels();
            for (Channel channel : channels) {
                if (channel instanceof HttpChannel) {
                    HttpChannel httpChannel = (HttpChannel)channel;
                    httpDestinations.putAll(httpChannel.getDestinations());
                }
            }
        }
        catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * Called by the servlet container to indicate to a servlet that the
     * servlet is being taken out of service.  See {@link javax.servlet.Servlet#destroy()}.
     */
    public void destroy() {
        //Flush the cached destinations, and hope they will be collected
        httpDestinations.clear();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            HttpDestination httpDestination = getMatchingDestination(request);
            /**
             * Synchronization done in processMessage() as inputs are local variables
             * so need not be protected, and only potential thread-unsafe call
             * seems to be the one to process an event in the Controller using
             * a shared RuleSession. Hence the entire method call below has not been
             * wrapped in a synchronized block.
             */
            if (httpDestination != null){
            	Properties properties = channelManager.getRuleServiceProvider().getProperties();
				httpDestination.processMessage(new HttpServerRequest(request, properties),
						new HttpServerResponse(response),
						HttpChannelDestination.METHOD_GET);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private HttpDestination getMatchingDestination(final HttpServletRequest servletRequest) throws ServletException {
        String contextPath = servletRequest.getRequestURI();
        //Find a destination matching this
        if (contextPath == null) {
            throw new ServletException("Invalid context path");
        }
        return (HttpDestination)httpDestinations.get(contextPath);
    }

    /**
     * We are going to perform the same operations for POST requests
     * as for GET methods, so this method just sends the request to
     * the doGet method.
     */

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            HttpDestination httpDestination = getMatchingDestination(request);
            if (httpDestination != null){
            	Properties properties = channelManager.getRuleServiceProvider().getProperties();
				httpDestination.processMessage(new HttpServerRequest(request, properties),
						new HttpServerResponse(response),
						HttpChannelDestination.METHOD_POST);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

//    private void processrequest(HttpServletRequest request,
//                                HttpServletResponse response) throws IOException {
//        RuleServiceProvider rsp = channelManager.getRuleServiceProvider();
//        if (rsp != null) {
//            Properties beProperties = rsp.getProperties();
//            String defaultFileName = beProperties
//                    .getProperty(HttpChannelConstants.DEFAULT_DOC_PAGE_PROPERTY);
//            String docRoot = beProperties.getProperty(
//                    HttpChannelConstants.DOC_ROOT_PROPERTY, null);
//            LOGGER.log(Level.DEBUG, "Doc Root Property %1$s", docRoot);
//            String target = request.getRequestURI();
//            File file;
//            if (target.equals("/") && docRoot != null && defaultFileName != null) {
//                file = new File(docRoot, defaultFileName);
//            } else {
//                file = new File(docRoot, URLDecoder.decode(target, "UTF-8"));
//            }
//            if (file.exists() && file.canRead() && !file.isDirectory()) {
//                response.setStatus(HttpStatus.SC_OK);
//                response.setContentType("text/html");
//                String txt = getFileAsString(file);
//                response.getWriter().write(txt);
//            } else {
//                response.setStatus(HttpStatus.SC_NOT_FOUND);
//                response.setContentType("text/html");
//                response.getWriter().write(target + " not found");
//            }
//        }
//    }
//
}
