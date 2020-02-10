package com.tibco.cep.driver.http.server;

/**
 * HTTP Channel Server for BE to serve plain HTTP requests and SOAP requests, this server
 * is started when the HTTP Channel is started in active mode 
 * @author vjavere
 *
 */
public interface HttpChannelServer {

	/**
	 * Initializes the startup configuration for the HTTP Server
	 * @throws Exception
	 */
    public void init() throws Exception;
    
    /**
     * Starts the server, this method is called when the HTTP Channel is started after initialization
     * @throws Exception
     */
    public void start() throws Exception;
    
    /**
     * Stops the server, called when the HTTP Channel is stopped
     * @throws Exception
     */
    public void stop() throws Exception;

    /**
     * Return true if this channel server implementation
     * supports deployment of j2ee webapps.
     * @see com.tibco.cep.driver.http.server.impl.TomcatServer
     * @return
     */
    public boolean supportsWebApp();

    /**
     * Deploy J2ee compliant webapp onto this server.
     * @param contextPath
     * @param webAppPath
     */
    public void deployWebApp(String contextPath, String webAppPath) throws Exception;
    
    /**
     * Return connector information associated with this server.
     * @return 
     */
    public ConnectorInfo getConnectorInfo();
    
    /**
     * Return if the server is started
     * @return
     */
    public boolean isStarted();

}
