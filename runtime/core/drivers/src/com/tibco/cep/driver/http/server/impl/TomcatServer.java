package com.tibco.cep.driver.http.server.impl;

import static com.tibco.cep.driver.http.HttpChannelConstants.AJP_CONNECTOR_PORT;
import static com.tibco.cep.driver.http.HttpChannelConstants.CLUSTER_MULTICAST_ADDRESS;
import static com.tibco.cep.driver.http.HttpChannelConstants.CLUSTER_MULTICAST_FREQUENCY;
import static com.tibco.cep.driver.http.HttpChannelConstants.CLUSTER_MULTICAST_MEMBER_DROP_THRESHOLD;
import static com.tibco.cep.driver.http.HttpChannelConstants.CLUSTER_MULTICAST_PORT;
import static com.tibco.cep.driver.http.HttpChannelConstants.CLUSTER_MULTICAST_RECEIVER_MAX_THREADS;
import static com.tibco.cep.driver.http.HttpChannelConstants.CLUSTER_MULTICAST_RECEIVER_PORT;
import static com.tibco.cep.driver.http.HttpChannelConstants.CLUSTER_MULTICAST_RECEIVER_SELECTOR_TIMEOUT;
import static com.tibco.cep.driver.http.HttpChannelConstants.CLUSTER_MULTICAST_TCP_LISTEN_HOST;
import static com.tibco.cep.driver.http.HttpChannelConstants.CLUSTER_MULTICAST_TCP_LISTEN_PORT;
import static com.tibco.cep.driver.http.HttpChannelConstants.GENERIC_CONTEXT_URI;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.HttpConstraintElement;
import javax.servlet.HttpMethodConstraintElement;
import javax.servlet.ServletSecurityElement;
import javax.servlet.annotation.ServletSecurity.EmptyRoleSemantic;

import org.apache.catalina.Cluster;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Realm;
import org.apache.catalina.Server;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.ContainerBase;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.apache.catalina.ha.session.DeltaManager;
import org.apache.catalina.ha.tcp.SimpleTcpCluster;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.realm.MemoryRealm;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.tribes.ChannelSender;
import org.apache.catalina.tribes.MembershipService;
import org.apache.catalina.tribes.group.GroupChannel;
import org.apache.catalina.tribes.group.interceptors.MessageDispatchInterceptor;
import org.apache.catalina.tribes.group.interceptors.TcpFailureDetector;
import org.apache.catalina.tribes.group.interceptors.ThroughputInterceptor;
import org.apache.catalina.tribes.membership.McastService;
import org.apache.catalina.tribes.transport.ReceiverBase;
import org.apache.catalina.tribes.transport.ReplicationTransmitter;
import org.apache.catalina.tribes.transport.nio.NioReceiver;
import org.apache.catalina.valves.AccessLogValve;
import org.apache.catalina.valves.ErrorReportValve;
import org.apache.coyote.ajp.AjpNioProtocol;
import org.apache.coyote.http11.Http11AprProtocol;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.designtime.model.service.channel.WebApplicationDescriptor;
import com.tibco.cep.driver.http.HttpChannel;
import com.tibco.cep.driver.http.HttpChannelConstants;
import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.driver.http.security.HttpChannelRealm;
import com.tibco.cep.driver.http.server.ConnectorInfo;
import com.tibco.cep.driver.http.server.FilterConfiguration;
import com.tibco.cep.driver.http.server.HttpChannelServer;
import com.tibco.cep.driver.http.server.HttpRoot;
import com.tibco.cep.driver.http.server.impl.websocket.WSContextListener;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.security.ObfuscationEngine;


/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Mar 27, 2008
 * Time: 1:36:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class TomcatServer implements HttpChannelServer {

    private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(TomcatServer.class);

    private Tomcat tomcat = null;
    private StandardHost host = null;
    protected Engine engine;
    protected StandardContext rootContext;
    private boolean debug;
    protected ClassLoader contextClassLoader;
    protected String name = "localhost";
    protected Realm realm;
    private HttpRoot httpRoot;
    private HttpChannel httpChannel;

    private boolean started = false;
    private boolean initialized = false;

    private Properties beProperties;
    private ConnectorInfo cInfo;
    private Properties tomcatProperties;
    private Cluster cluster;
    private boolean setWSEndpointInWar;

    private static final int TOMCAT_DEFAULT_MAX_THREADS = 200;
    private static final String PAGEFLOW_CHANNEL_TO_GENERIC_CONTEXT_ENABLED_PROPERTY = "be.engine.channel.pageflow.genericContext.enabled";
    private static final String HTTP_REQUEST_COMPRESSION_ENABLED_PROPERTY = "be.engine.channel.http.request.compression.enabled";
    private static final String DISABLE_HTTP_METHODS = "disableHttpMethods";

    /**
     * Default Constructor
     */
    public TomcatServer(HttpChannel httpChannel, ConnectorInfo cInfo, HttpChannelRealm realm) throws Exception {
        this.httpChannel = httpChannel;
        this.cInfo = cInfo;
        this.realm = realm;
        if (null != this.httpChannel) {
            contextClassLoader = httpChannel.getChannelManager().getRuleServiceProvider().getClassLoader();
        } else {
            contextClassLoader = Thread.currentThread().getContextClassLoader();
        }

        tomcatProperties = TomcatServerUtils.readPropsFromExternalProps("be.tomcat.props");
    }

    /**
     * This method Starts the Tomcat server.
     */
    public void init() throws Exception {
        beProperties = httpChannel.getConfig().getProperties();
        debug = cInfo.isDebugRequest();
        engine = null;
        // Set the home directory
        httpRoot = new HttpRoot(httpChannel, beProperties);

        httpRoot.create();
        System.setProperty("catalina.home", httpRoot.getTomcatDirectory());

        // Create an embedded server
        tomcat = new Tomcat();
        //This is a must to avoid default webapploader throwing classnotfound exceptions.
        ClassLoader classLoader = this.getClass().getClassLoader();
        WebappLoader loader = new WebappLoader(classLoader);
        
        // set the Realm for this engine
        if (realm == null) {
            realm = new MemoryRealm();
        }

        addHttpConnectors(cInfo);

        // set Engine properties
        String baseEngineName = "CatalinaHttpServer_" + cInfo.getPort();
        
        // check if the specified host is a local one
        String hostName = "localhost";
        InetAddress addr = cInfo.getAddress();
        // fix for BE#24582
        final boolean enableHostNameValidation = Boolean.parseBoolean(
        		httpChannel.getChannelManager().getRuleServiceProvider().getProperties().getProperty(SystemProperty.HTTP_ENABLE_HOSTNAME_VALIDATION.getPropertyName(), 
        		Boolean.TRUE.toString()));
        if (enableHostNameValidation) {
            if (!addr.isAnyLocalAddress() && !addr.isLoopbackAddress() && NetworkInterface.getByInetAddress(addr) == null) {
                String errorMessage = "Not a local address (" + cInfo.getAddress().getHostAddress() +") to start the server.";
                LOGGER.log(Level.ERROR, errorMessage);
                throw new RuntimeException(errorMessage);
            }
        }

        engine = tomcat.getEngine();
        engine.setName(baseEngineName);
        engine.setDefaultHost(hostName);
        
        String docBase;
        if (httpRoot.getDocRoot() != null && httpRoot.getDocRoot().trim().length() > 0) {
            docBase = httpRoot.getDocRoot();
        } else {
            docBase = httpRoot.getHostPath();
        }
        rootContext = (StandardContext) tomcat.addContext("", docBase);
        //Set loader here
        rootContext.setLoader(loader);

        host = (StandardHost) tomcat.getHost();
        host.setAppBase(httpRoot.getHostPath());
        host.setName(hostName);
        host.setAutoDeploy(true);
        host.setUnpackWARs(true);
        host.setDeployOnStartup(true);
        
        // Request Dumper
        setUpRequestDumperAccessLog();
        
        // session clustering
        setupCluster();

        tomcat.setHost(host);

        // filters
        configureFilters();
        
        // servlets
        addWrapper(rootContext, "default", "org.apache.catalina.servlets.DefaultServlet", false);
        addWrapper(rootContext, "page-flow", "com.tibco.cep.driver.http.server.impl.servlet.PageFlowServlet", true);
        addWrapper(rootContext, "jsp", "org.apache.jasper.servlet.JspServlet", false);
        addWrapper(rootContext, "http-channel", "com.tibco.cep.driver.http.server.impl.servlet.HTTPChannelAsyncServlet", true);
        addWrapper(rootContext, "soap", "com.tibco.cep.driver.http.server.impl.servlet.SOAPServlet", true);
        
        // add security constraints at channel level
        addSecurityConstraints(GENERIC_CONTEXT_URI, true, false);

        // Conditionally do the mapping for generic context to default tomcat
        // this will allow for generic context to be applied to BE servlets as well.
		String propValue = httpChannel
				.getChannelManager()
				.getRuleServiceProvider()
				.getProperties()
				.getProperty(
						PAGEFLOW_CHANNEL_TO_GENERIC_CONTEXT_ENABLED_PROPERTY,
						"false");
		boolean enablePageflowToGenericCtx = Boolean.parseBoolean(propValue);
		if (!enablePageflowToGenericCtx) {
			rootContext.addServletMappingDecoded(GENERIC_CONTEXT_URI, "default");
		}
        addDestinationMappings(enablePageflowToGenericCtx);

        rootContext.getServletContext().setAttribute(HttpChannel.class.getName(), httpChannel);
        
        // set the doc page
        String docPage = beProperties.getProperty(HttpChannelConstants.DEFAULT_DOC_PAGE_PROPERTY);
        if (docPage != null && docPage.trim().length() > 0) {
            rootContext.setReplaceWelcomeFiles(true);
            rootContext.addWelcomeFile(docPage);
            rootContext.getServletContext().setAttribute("welcome-page", docPage);
        }
        
        hideServerInfoOnError();
                
        setUpMessagesLogger(rootContext);
        this.initialized = true;
    }

    private void setupCluster() {
    	/**
    	 * Additional channel specific configuration can come from extended
    	 * properties file for now which can have all config not listed in drivers.xml
    	 * The format is
    	 * Folder1.Folder2.<ChannelName>.<propertyName>=value
    	 */
    	//Check if channel external config properties exists.
    	Properties properties = TomcatServerUtils.readPropsFromExternalProps("be.channel.external.config.file");
    	BEProperties beExtendedProperties = new BEProperties(properties);
    	//Check if this channel has any properties
    	String channelNs = this.httpChannel.getURI().substring(1).replace("/", ".");
    	BEProperties channelExtendedProperties = beExtendedProperties.getBranch(channelNs);

    	if (!channelExtendedProperties.isEmpty()) {
    		//Add AJP connector
    		Connector ajpConnector = new Connector(AjpNioProtocol.class.getName());
    		ajpConnector.setPort(Integer.parseInt(channelExtendedProperties.getProperty(AJP_CONNECTOR_PORT, "8010")));
    		tomcat.getService().addConnector(ajpConnector);

    		this.cluster = new SimpleTcpCluster();
    		host.setCluster(cluster);
    		//Add channel to it
    		addClusterChannel(channelExtendedProperties);
    	}
    }

    private void addManager(Context context) {
    	if (cluster != null) {
    		DeltaManager deltaManager = new DeltaManager();
    		deltaManager.setName(context.getPath());
    		deltaManager.setExpireSessionsOnShutdown(false);
    		deltaManager.setNotifySessionListenersOnReplication(true);
    		this.cluster.registerManager(deltaManager);
    		context.setManager(deltaManager);
    	}
    }

    private void addClusterChannel(Properties channelExtendedProperties) {
    	GroupChannel groupChannel = new GroupChannel();
    	//We need multicast address for all members to join.
    	MembershipService clusterMembershipService = new McastService();
    	Properties multicastProperties = new Properties();

    	multicastProperties.setProperty("mcastAddress", channelExtendedProperties.getProperty(CLUSTER_MULTICAST_ADDRESS, "228.0.0.4"));
    	multicastProperties.setProperty("mcastPort", channelExtendedProperties.getProperty(CLUSTER_MULTICAST_PORT, "45564"));
    	multicastProperties.setProperty("mcastFrequency", channelExtendedProperties.getProperty(CLUSTER_MULTICAST_FREQUENCY, "500"));
    	multicastProperties.setProperty("memberDropTime", channelExtendedProperties.getProperty(CLUSTER_MULTICAST_MEMBER_DROP_THRESHOLD, "3000"));
    	multicastProperties.setProperty("tcpListenPort", channelExtendedProperties.getProperty(CLUSTER_MULTICAST_TCP_LISTEN_PORT, "3000"));
    	multicastProperties.setProperty("tcpListenHost", channelExtendedProperties.getProperty(CLUSTER_MULTICAST_TCP_LISTEN_HOST, "127.0.0.1"));

    	clusterMembershipService.setProperties(multicastProperties);
    	groupChannel.setMembershipService(clusterMembershipService);
    	//Set sender/receiver
    	ReceiverBase receiverBase = new NioReceiver();
    	receiverBase.setAddress("auto");
    	receiverBase.setPort(Integer.parseInt(channelExtendedProperties.getProperty(CLUSTER_MULTICAST_RECEIVER_PORT, "5000")));
    	receiverBase.setSelectorTimeout(Long.parseLong(channelExtendedProperties.getProperty(CLUSTER_MULTICAST_RECEIVER_SELECTOR_TIMEOUT, "100")));
    	receiverBase.setMaxThreads(Integer.parseInt(channelExtendedProperties.getProperty(CLUSTER_MULTICAST_RECEIVER_MAX_THREADS, "6")));
    	//TODO other config for receiver
    	groupChannel.setChannelReceiver(receiverBase);

    	ChannelSender sender = new ReplicationTransmitter();
    	groupChannel.setChannelSender(sender);
    	
    	//Add interceptors
    	addChannelInterceptors(groupChannel);
    }

    /**
     * Add all the interceptors for the group
     * @param groupChannel
     */
    private void addChannelInterceptors(GroupChannel groupChannel) {
    	groupChannel.addInterceptor(new TcpFailureDetector());
    	groupChannel.addInterceptor(new MessageDispatchInterceptor());
    	groupChannel.addInterceptor(new ThroughputInterceptor());
    }

    /**
     * Configure any system/custom filters with the application
     */
    private void configureFilters() {
        FilterDef filter;
        FilterMap filterMap;

        for (final FilterConfiguration filterConfig : this.httpChannel.getConfig().getFilterConfigurations()) {

            final String filterName = filterConfig.getName();
            final String className = filterConfig.getClassName();

            filter = new FilterDef();
            filter.setFilterName(filterName);
            filter.setFilterClass(className);
            LOGGER.log(Level.DEBUG, "Tomcat filter class: '%s' %s", filterName, className);
            for (final Map.Entry<String, String> e : filterConfig.getParameters().entrySet()) {
                filter.addInitParameter(e.getKey(), e.getValue());
                LOGGER.log(Level.DEBUG, "Tomcat filter parameter: '%s' %s = %s", filterName, e.getKey(), e.getValue());
            }
            this.rootContext.addFilterDef(filter);

            filterMap = new FilterMap();
            filterMap.setFilterName(filterName);
            for (final String urlPattern : filterConfig.getUrlPatterns()) {
                filterMap.addURLPattern(urlPattern);
                LOGGER.log(Level.DEBUG, "Tomcat filter mapping: '%s' %s", filterName, urlPattern);
            }
            this.rootContext.addFilterMap(filterMap);                        
        }
        
        // check if request compression is enabled
        String requestCompressionEnabledPropValue = httpChannel
				.getChannelManager()
				.getRuleServiceProvider()
				.getProperties()
				.getProperty(
						HTTP_REQUEST_COMPRESSION_ENABLED_PROPERTY,
						"false");
        boolean requestCompressionEnabled = Boolean.parseBoolean(requestCompressionEnabledPropValue);
        
        if (requestCompressionEnabled) {
        	filter = new FilterDef();
        	filter.setFilterName("gzipFilter");
        	filter.setAsyncSupported("true");
        	filter.setFilterClass("com.tibco.cep.driver.http.server.impl.filter.GZIPFilter");        	                       
        	rootContext.addFilterDef(filter);

        	filterMap = new FilterMap();
        	filterMap.setFilterName("gzipFilter");
        	filterMap.addURLPattern(GENERIC_CONTEXT_URI);
        	rootContext.addFilterMap(filterMap);
        }

        filter = new FilterDef();
        filter.setFilterName("dispatcher");
        filter.setAsyncSupported("true");
        filter.setFilterClass("com.tibco.cep.driver.http.server.impl.filter.RequestDispatcherFilter");
        rootContext.addFilterDef(filter);

        filterMap = new FilterMap();
        filterMap.setFilterName("dispatcher");
        filterMap.addURLPattern(GENERIC_CONTEXT_URI);
        rootContext.addFilterMap(filterMap);
    }

    @Override
    public ConnectorInfo getConnectorInfo() {
        return cInfo;
    }

    /**
     * Register a servlet with name under the {@link StandardContext}
     *
     * @param context
     * @param name
     * @param className
     * @param asyncSupported
     */
    private void addWrapper(Context context,
                            String name,
                            String className,
                            boolean asyncSupported) {
        Wrapper wrapper = Tomcat.addServlet(context, name, className);
        wrapper.setAsyncSupported(asyncSupported);
    }

    @Override
    public boolean supportsWebApp() {
        return true;
    }

    @Override
    public void deployWebApp(String contextPath, String webAppPath) throws Exception {
        if (!contextPath.startsWith("/")) {
            throw new RuntimeException("Error deploying webapp " + webAppPath + ". Context Path should start with /");
        }
        File webFile = new File(webAppPath);
        if (!webFile.isDirectory()) {
            //Check if it is war
            if (!webAppPath.endsWith(".war")) {
                throw new Exception("Webapplication path specified by " + webAppPath + " is not a directory or a war");
            }
        }
        Context warContext = tomcat.addWebapp(contextPath, webAppPath);
        warContext.setReloadable(true);

        //TODO this should be done only for distributable webapps.
        //The cluster and relevant config is shared. Manager is one per webapp.
        addManager(warContext);
        //TODO this is a problem. Without changing webapp config it is not possible to make it distributable.
        warContext.setDistributable(true);

        RuleServiceProvider RSP = httpChannel.getChannelManager().getRuleServiceProvider();
        //Make the RSP and channel available to webapps in case they want to use it.
        warContext.getServletContext().setAttribute(RuleServiceProvider.class.getName(), RSP);
        warContext.getServletContext().setAttribute(HttpChannel.class.getName(), httpChannel);        
        
        //Use this loader to load shared BE runtime libraries as well as be.jar.
        WebappLoader loader = new WebappLoader(contextClassLoader);
        if (LOGGER.isEnabledFor(Level.INFO)) {
        	LOGGER.log(Level.INFO, "Loader Classpath - %s", loader.getClasspath());
        }
        warContext.setLoader(loader);
        
        setUpMessagesLogger(warContext);
        
        if (setWSEndpointInWar) warContext.addApplicationListener(WSContextListener.class.getName());
    }

    /**
     * Map each destination of the {@link HttpChannel} to the root
     *
     * @param enablePageflowToGenericCtx
     * {@link javax.servlet.ServletContext}.
     */
    private void addDestinationMappings(boolean enablePageflowToGenericCtx) {
        if (httpChannel == null) return;
        
        boolean hasWebSocketDestination = false;
        Collection<HttpDestination> destinations = httpChannel.getDestinations().values();
        for (HttpDestination dest : destinations) {      
        	if (dest.isWebSocketDestination()) {
        		hasWebSocketDestination = true;
        		
        	} else if (dest.isPageFlowDestination()) {
                String contextURI = dest.getContextUri();
                if (!enablePageflowToGenericCtx && GENERIC_CONTEXT_URI.equals(contextURI)) {
                	if (LOGGER.isEnabledFor(Level.WARN)) {
                		LOGGER.log(Level.WARN, "Ignoring mapping for context URI %s", contextURI);
                	}
                    continue;// TODO - Need to verify if changing to continue from return does not break anything
                }
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Adding destination %s page-flow", contextURI);
                }
                rootContext.addServletMappingDecoded(contextURI, "page-flow");
                
            } else {
            	String destinationURI = dest.getURI();
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Adding destination %s http-channel", destinationURI);
                }
                rootContext.addServletMappingDecoded(destinationURI, "http-channel");
            }
           
        	// add security constraints at destination level
        	if (!dest.isWebSocketDestination()) {
        		String destUri = (dest.isPageFlowDestination()) ? dest.getContextUri() : dest.getURI();
        		addSecurityConstraints(destUri, false, dest.isPageFlowDestination());
        	}
        }
        
        if (hasWebSocketDestination) {
        	List<WebApplicationDescriptor> webapps = httpChannel.getConfig().getWebApplicationDescriptors();
        	if (webapps != null && webapps.size() > 0) setWSEndpointInWar = true;
        	else rootContext.addApplicationListener(WSContextListener.class.getName());
        }
    }
    
    /**
     * Add Security constraints to disable Http Methods per destination
     * 
     * @param destinationURI
     * @param isChannel
     * @param isPageFlow
     */
    private void addSecurityConstraints(String destinationURI, boolean isChannel, boolean isPageFlow) {
    	StringBuilder propertyBuffer = null;
    	if (isChannel) {
    		propertyBuffer = new StringBuilder(httpChannel.getURI())
								    	.append(".")
								    	.append(DISABLE_HTTP_METHODS);
    	} else {
    		propertyBuffer = new StringBuilder((isPageFlow) ? httpChannel.getURI() : "")
								    	.append(destinationURI)
								    	.append(".")
								    	.append(DISABLE_HTTP_METHODS);
    	}
    	String propertyToLookUp = propertyBuffer.toString().substring(1).replace("/", ".");
    	
    	String httpMethodsToDisable = httpChannel
				.getChannelManager()
				.getRuleServiceProvider()
				.getProperties()
				.getProperty(propertyToLookUp, null);
    	
    	// cover the case where methods are disabled at the channel level
    	if (!isChannel && httpMethodsToDisable != null && !httpMethodsToDisable.isEmpty()) {
    		propertyBuffer = new StringBuilder(httpChannel.getURI())
								    	.append(".")
								    	.append(DISABLE_HTTP_METHODS);
    		propertyToLookUp = propertyBuffer.toString().substring(1).replace("/", ".");
    		
    		String channelMethods = httpChannel
    				.getChannelManager()
    				.getRuleServiceProvider()
    				.getProperties()
    				.getProperty(propertyToLookUp, null);
    		if (channelMethods != null && !channelMethods.isEmpty()) {
    			httpMethodsToDisable += (httpMethodsToDisable.endsWith(",")) ? channelMethods : "," + channelMethods;
    		}
    	}
    	
    	disableHttpMethods(httpMethodsToDisable, destinationURI);
    }
    
    /**
     * Disable Http Methods
     * 
     * @param httpMethodsToDisable
     * @param destinationURI
     */
    private void disableHttpMethods(String httpMethodsToDisable, String destinationURI) {    	
    	if (httpMethodsToDisable != null && !httpMethodsToDisable.isEmpty()) {
    		HttpConstraintElement httpConstraintElement = new HttpConstraintElement(EmptyRoleSemantic.DENY);
    		List<HttpMethodConstraintElement> httpMethodConstraintList = new ArrayList<HttpMethodConstraintElement>();

    		HttpMethodConstraintElement httpMethodConstraintElement = null;

    		String[] httpMethodList = httpMethodsToDisable.split(",");
    		for (String httpMethod : httpMethodList) { 
    			if (!isMethodConstraintAdded(httpMethodConstraintList, httpMethod)) {
    				httpMethodConstraintElement = new HttpMethodConstraintElement(httpMethod.toUpperCase(), httpConstraintElement);    	        
    				httpMethodConstraintList.add(httpMethodConstraintElement);
    			}
    		}
    		ServletSecurityElement servletSecurityElement = new ServletSecurityElement(httpMethodConstraintList);
    		SecurityConstraint[] securityConstraints = SecurityConstraint.createConstraints(servletSecurityElement, destinationURI);		

    		for (SecurityConstraint sc : securityConstraints) {
    			rootContext.addConstraint(sc);
    		}
    	}
    }
    
    /**
     * Check if a method constraint is added
     * 
     * @param httpMethodConstraintList
     * @param methodName
     * @return
     */
    private boolean isMethodConstraintAdded(List<HttpMethodConstraintElement> httpMethodConstraintList, String methodName) {
    	for (HttpMethodConstraintElement httpMethodConstraint : httpMethodConstraintList) {
    		if (httpMethodConstraint.getMethodName().equals(methodName)) return true;
    	}
    	return false;
    }
    
    /**
     * This is primarily to hide any server details/reports being printed
     * This is more as a security measure
     */
    private void hideServerInfoOnError() {
        ErrorReportValve erValve = new ErrorReportValve();
        erValve.setShowReport(false);
        erValve.setShowServerInfo(false);
        host.getPipeline().addValve(erValve);
    }
    
    /**
     * Adding access log valve for request-response data
     */
    private void setUpRequestDumperAccessLog() {
    	if (debug) {
    		AccessLogValve alv = new AccessLogValve();
    		alv.setDirectory(cInfo.getDebugLogFolder());
    		alv.setPattern(cInfo.getDebugLogPattern());
    		host.addValve(alv);
    	}
    }
    
    /**
     * Adding messages logger
     *
     * @param context
     */
    private void setUpMessagesLogger(Context context) {
        if (debug) {
            if (context instanceof ContainerBase) {
                Pipeline pipeline = host.getPipeline();
                if (pipeline != null) {
                    AccessLogValve accessLogValve = new AccessLogValve();
                    accessLogValve.setEnabled(true);
                    pipeline.addValve(accessLogValve);
                }
            }
        }
    }

	/**
	 * Parse channel properties and based on the number of instances defined add
	 * appropriate connectors
	 *
	 * @param info
	 * @throws Exception
	 */
    private void addHttpConnectors(ConnectorInfo info) throws Exception {
    	int channelInstances = cInfo.getConnectorInstances();

    	// if instance count is overridden via properties file, use that value
    	int channelInstancesOverridden = parseChannelInstances();
    	if (channelInstances != channelInstancesOverridden
    			&& channelInstancesOverridden > 0) {
    		channelInstances = channelInstancesOverridden;
    	}

    	int port = cInfo.getPort();
    	for (int cnt = 0; cnt < channelInstances; cnt++) {
    		// add connector/s
    		addConnector(cInfo, port++);

    		if (channelInstances - 1 > cnt) {
    			while (!TomcatServerUtils.isPortAvailable(port)) {
    				port++;
    			}
    		}
    	}
    }

	/**
	 * Set connector properties and add single connector to tomcat server
	 *
	 * @param info
	 * @param port
	 * @throws Exception
	 */
	private void addConnector(ConnectorInfo info, int port) throws Exception {
        int maxExecutorThreads = (info.getMaxProcessors() != -1) ? info.getMaxProcessors() : TOMCAT_DEFAULT_MAX_THREADS;
        
        // As connector is supposed to bind to all addresses on this server, InetAddress passed as null
        String connectorType = (tomcatProperties.getProperty("protocol") != null) ? tomcatProperties.getProperty("protocol") : info.getConnectorType();
        Connector connector = null;
        if (info.isSecure() == true && info.isKeyStoreType() == false) {
        	connectorType = CustomConnectorFactory.APR_CONNECTOR_PROTOCOL;
        }
        connector = CustomConnectorFactory.createConnector(connectorType, null, port, info.isSecure(), maxExecutorThreads);

        connector.setAttribute("socketBuffer", String.valueOf(info.getSocketBufferSize()));
        connector.setAttribute("compression", String.valueOf(info.getCompression()));
        connector.setAttribute("enableLookups", Boolean.toString(info.isEnableDNSLookups()));
        connector.setAttribute("connectionTimeout", String.valueOf(info.getConnectionTimeout()));
        connector.setAttribute("connectionLinger", String.valueOf(info.getConnectionLinger()));
        connector.setAttribute("tcpNoDelay", Boolean.toString(info.isTcpNoDelay()));
        connector.setAttribute("maxKeepAliveRequests", String.valueOf(info.getMaxKeepAliveRequests()));
        connector.setAttribute("maxHttpHeaderSize", String.valueOf(info.getMaxHttpHeaderSize()));
        connector.setAttribute("maxPostSize", String.valueOf(info.getMaxPostSize()));
        connector.setAttribute("maxSavePostSize", String.valueOf(info.getMaxSavePostSize()));

        if (!info.getCompression().equalsIgnoreCase("off")) {
        	connector.setAttribute("compression", info.getCompression().toLowerCase());
        }
        if (info.getCompressableMimeType() != null && (!info.getCompressableMimeType().isEmpty()))
            connector.setAttribute("compressableMimeType", info.getCompressableMimeType());

        String uriEncoding = info.getUriEncoding();
        boolean useBodyEncodingForURI = info.isUseBodyEncodingForURI();

        if (uriEncoding != null && (!uriEncoding.isEmpty())) {
            useBodyEncodingForURI = false;
        } else {
            uriEncoding = info.getDefaultEncoding();
        }
        connector.setAttribute("URIEncoding", uriEncoding);
        connector.setURIEncoding(uriEncoding);
        connector.setAttribute("useBodyEncodingForURI", Boolean.toString(useBodyEncodingForURI));
        connector.setUseBodyEncodingForURI(Boolean.valueOf(useBodyEncodingForURI));

        String scheme = info.getScheme();
        if (scheme != null && (!scheme.isEmpty())) {
            if (scheme.equals("ajp") || scheme.equals("memory") || scheme.equals("http") || scheme.equals("https")) {
                connector.setAttribute("scheme", scheme);
            }
        }
        connector.setAllowTrace(true);
        connector.setAttribute("maxSpareThreads", info.getMaxSpareThreads());
        connector.setAttribute("minSpareThreads", info.getMinSpareThreads());
        
        if (info.getRestrictedUerAgents() != null && (!info.getRestrictedUerAgents().isEmpty())) {
            connector.setAttribute("restrictedUserAgents", info.getRestrictedUerAgents());
        }

        if (info.getAcceptCount() != -1) {
            connector.setAttribute("acceptCount", String.valueOf(info.getAcceptCount()));
        }
        
        if (info.getServerHeader() != null) {
        	connector.setAttribute("server", info.getServerHeader());
        }
        
        LifecycleListener lifeCycleListener = null;

        /**
         * Check for certificate chain in
         * "**** CertificateRequest " section after a server Hello
         * Refer TLS 1.0 specification
         */
        // Set properties common for both Java and Open SSL
        if (info.isSecure()) {
            connector.setScheme("https");
            connector.setAttribute("SSLEnabled", true);
            connector.setSecure(true);
            boolean clientAuth = info.isClientAuthRequired();
            //Support "Want" style authentication for 2-way
            connector.setAttribute("clientAuth", Boolean.toString(clientAuth));
            
            String enabledSSLProtocols = info.getSslEnabledProtocols();
            if (enabledSSLProtocols != null && ! enabledSSLProtocols.isEmpty()) {
            	connector.setAttribute("sslEnabledProtocols", enabledSSLProtocols);
            }
            
            if (info.isUseServerCipherOrder()) connector.setAttribute("useServerCipherSuitesOrder", info.isUseServerCipherOrder());
            
            String cipherSuites = info.getCipherSuites();
            if (cipherSuites != null && !cipherSuites.isEmpty()) {
            	connector.setAttribute("ciphers", cipherSuites);
            }
            
            // Java SSL properties
            if(info.isKeyStoreType()) {
            	connector.setAttribute("keystoreFile", info.getKeyStoreFile());
            	connector.setAttribute("keystoreType", info.getKeyStoreType());
            	//Use SunX509 for Sun JDK and IbmX509 on AIX
            	connector.setAttribute("algorithm", info.getKeyManagerAlgorithm());
            	String keystorePass = info.getKeyStorePassword();
            	keystorePass = (info.isKeystorePasswordObfuscated()) ?
            			new String(ObfuscationEngine.decrypt(keystorePass)) : keystorePass;
            			connector.setAttribute("keystorePass", keystorePass);
            			
            // OpenSSL properties
            } else {
            	if (connector.getProtocolHandler() instanceof Http11AprProtocol) {
            		connector.setAttribute("SSLCertificateFile", info.getCertUrl());
            		connector.setAttribute("SSLCertificateKeyFile", info.getPrivateKeyUrl());
            		connector.setAttribute("SSLPassword", info.getKeyPassword());

            		lifeCycleListener = new AprLifecycleListener();
            	}
            }
            if (info.getTrustStoreFile() != null) {
            	File trustStoreFile = new File(info.getTrustStoreFile());
            	if (trustStoreFile.exists() && trustStoreFile.canRead()) {
            		connector.setAttribute("truststoreFile", info.getTrustStoreFile());
            	}
            	connector.setAttribute("truststorePass", info.getTrustStorePass());
            	connector.setAttribute("truststoreType", info.getTrustStoreType());
            }
        }

        // Load/Override additional tomcat parameters if any
        for (Map.Entry<Object, Object> e : tomcatProperties.entrySet()) {
            String key = (String) e.getKey();
            if (key.trim().equals("protocol")) continue;

            String val = (String) e.getValue();
            connector.setAttribute(key, val);
        }
        
        if (lifeCycleListener == null) lifeCycleListener = new ChannelLifecycleListener();
        connector.addLifecycleListener(lifeCycleListener);

        tomcat.getService().addConnector(connector);
    }
	
	/**
	 * Life cycle listener to keep track of changing connector/server states
	 */
    private class ChannelLifecycleListener implements LifecycleListener {

        /**
         * Acknowledge the occurrence of the specified event.
         *
         * @param event LifecycleEvent that has occurred
         */
        @Override
        public void lifecycleEvent(LifecycleEvent event) {
        	LifecycleState state = event.getLifecycle().getState();

            Connector currentConnector = null;
            if (event.getSource() instanceof Connector) {
            	currentConnector = (Connector) event.getSource();
            }

            String connectorString = (currentConnector != null) ? "HTTP Channel[Port:" + currentConnector.getPort() +"]" : httpChannel.toString();
            
            String serverStateMsg = null;
            switch (state) {
            case STARTING: serverStateMsg = String.format("Channel server for %s starting", connectorString); break;
            case STARTED: serverStateMsg = String.format("Channel server for %s successfully started", connectorString); break;
            case STOPPING: serverStateMsg = String.format("Channel server for %s stopping", connectorString); break;
            case STOPPED: serverStateMsg = String.format("Channel server for %s successfully stopped", connectorString); break;
            }
            
            if (serverStateMsg != null) {
            	LOGGER.log(Level.INFO, serverStateMsg);
            }
    	}
    }

    @Override
    public void start() throws Exception {
        if (!initialized) {
            init();
        }
        
        if (!started) {
        	// Start the embedded server
        	Runnable runnable = new Runnable() {
        		public void run() {
        			try {
        				// When multiple HTTP channels are started on the same engine,
        				// synchronization seems necessary to avoid config issues causing JMX exceptions.
        				synchronized (TomcatServer.class) {
        					tomcat.start();
        				}
        				//This is a blocking call
        				//Hence need to spawn a new thread
        				Server tomcatServer = tomcat.getServer();
        				tomcatServer.await();
        			} catch (Throwable e) {
        				LOGGER.log(Level.ERROR, "Error Starting the server - " + e.getMessage());
        			}
        		}
        	};
        	new Thread(null, runnable, "HTTP-Channel-Startup").start();
        	started = true;
        } else {
        	// should be the case of stop and start of the channel
        	for (HttpDestination dest : httpChannel.getDestinations().values()) {
        		dest.resume();
        	}
        }
    }

    /**
     * This method Stops the Tomcat server.
     */
    @Override
    public void stop() throws Exception {
        if (started) {
        	// Simply suspending all the destinations
        	for (HttpDestination dest : httpChannel.getDestinations().values()) {
        		dest.suspend();
        	}
        }
    }
    
    @Override
    public boolean isStarted() {
    	return started;
    }

    /**
     * Parse the property file and look for matching channel. If matching channel found return the total number of instance
     * Format for property is <channelpath>.numConnectors=2
     *
     * @return
     */
    private int parseChannelInstances() {
    	String currentChannel = this.httpChannel.getURI().substring(1).replace("/", ".");

    	int instances = -1;
    	String channelInstances = tomcatProperties.getProperty(currentChannel + ".numConnectors");

    	if (channelInstances != null && !channelInstances.isEmpty()) {
    		instances = Integer.parseInt(channelInstances);
    	}

    	return instances;
    }
    
    /**
     * Main method for testing
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
    	Properties p = new Properties();
    	ConnectorInfo cInfo = new ConnectorInfo(null, 8090, p);
    	
    	TomcatServer tServ = new TomcatServer(null, cInfo, null);
    	tServ.init();
    	tServ.deployWebApp("/WebStudio", "S:/be/5.1/designtime/webstudio/WebStudio/war");
    	tServ.start();
    }
}
