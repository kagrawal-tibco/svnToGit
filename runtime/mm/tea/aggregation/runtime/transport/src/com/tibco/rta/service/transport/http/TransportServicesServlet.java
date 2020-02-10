package com.tibco.rta.service.transport.http;

import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.StartStopService;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.service.cluster.GroupMembershipService;
import com.tibco.rta.service.transport.Message;
import com.tibco.rta.service.transport.ServiceDelegate;
import com.tibco.rta.util.IOUtils;
import com.tibco.rta.util.ServiceConstants;

import javax.servlet.AsyncContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 25/10/12
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
@WebServlet(asyncSupported = true)
public class TransportServicesServlet extends HttpServlet {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_TRANSPORT.getCategory());

    /**
     * Keep an instance of this to route calls to actual service.
     */
    protected ServiceDelegate serviceDelegate;

    protected HTTPTransportMessageSerializer messageSerializer;

    public void init(ServletConfig config) throws ServletException {
        try {
            super.init(config);
            serviceDelegate = new ServiceDelegate();
            messageSerializer = new HTTPTransportMessageSerializer();
            GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();
            groupMembershipService.signalPrimary();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    protected void deserialize(HttpServletRequest httpServletRequest,
                               boolean commit) throws IOException, ServletException {
        try {
            AsyncContext asyncContext = httpServletRequest.startAsync();
            //TODO make this configurable?
            asyncContext.setTimeout(-1);
            //Get request uri
            String requestUri = httpServletRequest.getRequestURI();
            //Get matching endpoint
            HTTPServiceEndpoints serviceEndpoint = HTTPServiceEndpoints.getByURI(requestUri);
            //Get op param
            String operation = httpServletRequest.getHeader(ServiceConstants.REQUEST_OP);
            if (operation == null || operation.isEmpty()) {
                //Define checked exception for this.
                throw new RuntimeException("Operation cannot be empty");
            }
            StartStopService service = HTTPServiceProviderFactory.INSTANCE.getServiceProviderInstance(serviceEndpoint);
            if (service != null) {
                //Reflectively invoke the operation after deserializing payload
                byte[] payload = IOUtils.readInputStream(httpServletRequest.getInputStream(), httpServletRequest.getContentLength());
                Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
                Properties properties = new Properties();

                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    properties.put(headerName, httpServletRequest.getHeader(headerName));
                }
                Message outputMessage = invokeService(service, operation, properties, payload);
                //serialize output
                if (outputMessage != null) {
                    messageSerializer.serialize(outputMessage, new HTTPMessageSerializationContext(asyncContext), commit);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    @Override
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        deserialize(httpServletRequest, true);
    }

    /**
     * @param startStopService
     * @param operation
     * @param payload
     * @throws Exception
     */
    protected Message invokeService(StartStopService startStopService, String operation, Properties properties, byte[] payload) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(payload);
        Method[] allMethods = serviceDelegate.getClass().getMethods();

        Method invokeMethod = null;
        for (Method method : allMethods) {
            if (method.getName().equals(operation)) {
                //TODO overloaded methods?
                invokeMethod = method;
                break;
            }
        }
        if (invokeMethod != null) {
            return (Message) invokeMethod.invoke(serviceDelegate, startStopService, properties, bis);
        }
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
