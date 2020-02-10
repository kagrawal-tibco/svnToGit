package com.tibco.rta.service.rs.tomcat;

import com.tibco.rta.common.service.session.ServerSession;
import com.tibco.rta.common.service.session.ServerSessionRegistry;
import com.tibco.rta.common.service.session.SessionCreationException;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.service.transport.http.tomcat.TCPSessionOutbound;
import com.tibco.rta.util.ServiceConstants;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 20/2/14
 * Time: 12:20 PM
 * To change this template use File | Settings | File Templates.
 */
@WebServlet(asyncSupported = true, value = "/service/async")
public class SPMRestAsyncServlet extends HttpServlet {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_TRANSPORT.getCategory());

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AsyncContext asyncContext = request.startAsync();
        asyncContext.addListener(new AsyncContextListener());
        asyncContext.setTimeout(-1);
        handleBeginEvent(request, response);
    }

    /**
     * @throws IOException
     * @throws ServletException
     */
    @SuppressWarnings("unchecked")
    private void handleBeginEvent(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException, ServletException {
        String sessionId = servletRequest.getParameter(ServiceConstants.SESSION_ID);
        String sessionName = servletRequest.getParameter(ServiceConstants.SESSION_NAME);
        if (sessionId != null) {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Creating session with id [%s]", sessionId);
            }
            OutputStream outputStream = servletResponse.getOutputStream();
            try {
                ServerSession<TCPSessionOutbound> serverSession = (ServerSession<TCPSessionOutbound>) ServerSessionRegistry.INSTANCE.addOrCreateSession(sessionId, sessionName);
                serverSession.setSessionOutbound(new TCPSessionOutbound(outputStream));
            } catch (SessionCreationException sce) {
                LOGGER.log(Level.ERROR, "", sce);
                outputStream.write(sce.getMessage().getBytes());
                outputStream.flush();
            }
        }
    }

    private void handleEndEvent(HttpServletRequest servletRequest) throws IOException {
        String sessionId = servletRequest.getHeader(ServiceConstants.SESSION_ID);
        if (sessionId != null) {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Removing session with id [%s]", sessionId);
            }
            ServerSessionRegistry.INSTANCE.removeServerSession(sessionId);
        }
    }

    private class AsyncContextListener implements AsyncListener {

        @Override
        public void onComplete(AsyncEvent event) throws IOException {
            System.out.println(event);
        }

        @Override
        public void onTimeout(AsyncEvent event) throws IOException {
            handleEndEvent((HttpServletRequest) event.getSuppliedRequest());
        }

        @Override
        public void onError(AsyncEvent event) throws IOException {
            handleEndEvent((HttpServletRequest) event.getSuppliedRequest());
        }

        @Override
        public void onStartAsync(AsyncEvent event) throws IOException {
            System.out.println(event);
        }
    }
}
