/**
 * 
 */
package com.tibco.cep.driver.http.server.impl.websocket;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;

import org.apache.tomcat.websocket.server.Constants;
import org.apache.tomcat.websocket.server.WsContextListener;

import com.tibco.cep.driver.http.HttpChannel;
import com.tibco.cep.driver.http.HttpChannelConfig;
import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author vpatil
 *
 */
public class WSContextListener extends WsContextListener {
	private static transient Logger LOGGER = LogManagerFactory.getLogManager().getLogger(WSContextListener.class);
	
	private HttpChannel httpChannel;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		super.contextInitialized(sce);
		
		Object c = sce.getServletContext().getAttribute(HttpChannel.class.getName());
        if (c instanceof HttpChannel) {
            httpChannel = (HttpChannel) c;
        }
        if (httpChannel == null) {
            throw new RuntimeException("Channel parameter not found in the servlet context");
        }
        HttpChannelConfig channelConfig = httpChannel.getConfig();
		
		addWebSocketEndpoints(sce.getServletContext());
	}
	
	private void addWebSocketEndpoints(ServletContext servletContext) {
		ServerContainer sc =
				(ServerContainer) servletContext.getAttribute(
						Constants.SERVER_CONTAINER_SERVLET_CONTEXT_ATTRIBUTE);
		
		Collection<HttpDestination> destinations = httpChannel.getDestinations().values();
        for (HttpDestination dest : destinations) {      
        	if (dest.isWebSocketDestination()) {
        		try {
        			ServerEndpointConfig endPointConfig = ServerEndpointConfig.Builder
        					.create(WSEndpoint.class, dest.getURI())
        					.configurator(new HttpChannelConfigurator(dest))
        					.build();
        			sc.addEndpoint(endPointConfig);
        		} catch(DeploymentException de) {
        			LOGGER.log(Level.ERROR, "Error deploying WebSocket endpoint", de);
        		}
        	}
        }
	}
}
