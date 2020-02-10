/**
 * 
 */
package com.tibco.cep.driver.http.server.impl;

import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.IntrospectionUtils;

/**
 * This class creates Connector object based on the Channel configuration, both
 * BIO and NIO type of connectors are supported
 * 
 * @author vpatil
 */
public class CustomHttpConnector extends Connector {

	/**
	 * @param connectorProtocol
	 * @param inetAddress
	 * @param port
	 * @param isSecure
	 * @param maxExecutorThreads
	 * @throws Exception
	 */
	public CustomHttpConnector(Class connectorProtocol,
			InetAddress inetAddress,
            int port,
            boolean isSecure,
            int maxExecutorThreads) throws Exception {
		
		super(connectorProtocol.getName());
		
		if (inetAddress != null) {
            String address = inetAddress.toString();
            if (address != null) {
                int index = address.indexOf('/');
                if (index != -1) {
                    address = address.substring(index + 1);
                }
            }
            IntrospectionUtils.setProperty(this, "address", (new StringBuilder()).append("").append(address).toString());
        }
		
        IntrospectionUtils.setProperty(this, "port", (new StringBuilder()).append("").append(port).toString());
        
//        Class<?> protocolHandlerClazz = Class.forName(connectorProtocol.getName());
//        this.protocolHandler = (ProtocolHandler) protocolHandlerClazz.newInstance();
        
        AbstractHttp11Protocol httpProtocol = (AbstractHttp11Protocol) protocolHandler;
        httpProtocol.setPort(port);
        httpProtocol.setSecure(isSecure);
        httpProtocol.setExecutor(Executors.newFixedThreadPool(maxExecutorThreads, new ConnectorThreadFactory(connectorProtocol)));
	}
	
	/**
	 * Thread factory for the connector. Helps to expand the number of threads as per the load
	 * @author vpatil
	 */
	class ConnectorThreadFactory implements ThreadFactory {

        private ThreadGroup threadGroup;
        private AtomicInteger threadNumber = new AtomicInteger(1);
        private AtomicInteger poolNumber = new AtomicInteger(1);
        private String threadName;

        private static final String NIO_CONNECTOR_NAME_PREFIX = "HTTP-NIO-Worker-";
        private static final String APR_CONNECTOR_NAME_PREFIX = "HTTP-APR-Worker-";

        /**
         * Default Constructor
         * @param connectorProtocol
         */
        public ConnectorThreadFactory(Class connectorProtocol) {
            SecurityManager systemManager = System.getSecurityManager();
            threadGroup = (systemManager != null) ? systemManager.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            threadName = getThreadNamePrefix(connectorProtocol.getName()) + poolNumber.getAndIncrement();
        }

        /**
         * Constructs a new <tt>Thread</tt>.  Implementations may also initialize
         * priority, name, daemon status, <tt>ThreadGroup</tt>, etc.
         *
         * @param r a runnable to be executed by new thread instance
         * @return constructed thread
         */
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(threadGroup, r,
                    threadName + "-" + threadNumber.getAndIncrement());
            //Similar to default implementation
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            if (thread.getPriority() != Thread.NORM_PRIORITY) {
                thread.setPriority(Thread.NORM_PRIORITY);
            }
            return thread;
        }
        
		/**
		 * Get the appropriate Thread prefix based on the Connector Protocol
		 * type
		 * 
		 * @param connectorProtocol
		 * @return
		 */
		private String getThreadNamePrefix(String connectorProtocol) {
			return connectorProtocol.trim().equals(
							Http11NioProtocol.class.getName()) ? NIO_CONNECTOR_NAME_PREFIX
									: APR_CONNECTOR_NAME_PREFIX;
		}
    }
}
