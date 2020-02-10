package com.tibco.cep.driver.http;

import com.tibco.cep.runtime.service.security.BEIdentityObjectFactory;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Mar 27, 2008
 * Time: 7:06:48 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HttpChannelConstants {
    public final static String PROXY_NAMESPACE = "www.tibco.com/shared/ProxyConnection";
    public final static String HTTP_NAMESPACE =  "www.tibco.com/shared/HTTPConnection";
    public final static String REPOS_NAMESPACE = "http://www.tibco.com/xmlns/repo/types/2002";
    public final static String CONTENT_ENCODING_HEADER = "Content-Encoding";
    public final static String HTTP_STATUS_CODE_HEADER = "HttpStatusCode";
    public final static String DEFAULT_HTTP_CONTENT_TYPE_HEADER = "text/xml; charset=UTF-8";
    public final static ExpandedName PROXY_ROOT = ExpandedName.makeName(PROXY_NAMESPACE,"proxySharedResource");
    public final static ExpandedName HTTP_ROOT = ExpandedName.makeName(HTTP_NAMESPACE,"httpSharedResource");
    public final static String CUSTOM_HEADER_PREFIX = "CUSTOM-HEADER-";

    public final static String PROXY_HOST = "proxyHost";
    public final static String PROXY_PORT = "proxyPort";
    public final static String NODE_CONFIG = "config";
    public final static String NODE_REPOS = "repository";
    public final static String PROPERTY_HOST = "Host";
    public final static String PROPERTY_PORT = "Port";
    public final static String PROPERTY_USE_SSL ="useSsl";
    public final static String PROPERTY_ENABLE_LOOKUPS = "enableDNSLookups";
    public final static String NODE_SSL = "ssl";
    public final static String PROPERTY_SSL_STRONGCIPHERSONLY = "strongCipherSuitesOnly";
    public final static String PROPERTY_SSL_REQUIRESCLIENTAUTH = "requiresClientAuthentication";
    public final static String PROPERTY_SSL_CERT_URI = "cert";
    public final static String PROPERTY_SSL_TRUSTSTORE_PASSWORD = "trustStorePassword";
    public final static String PROPERTY_SSL_IDENTITY_URI = "identity";
    public final static String NODE_IDENTITY = "identity";

    public final static String MESSAGE_HEADER_NAMESPACE_PROPERTY="_ns_";
    public final static String MESSAGE_HEADER_NAME_PROPERTY="_nm_";
    public final static String MESSAGE_HEADER_PAYLOAD_PROPERTY = "_payload_";
    public final static String MESSAGE_HEADER_EXTID_PROPERTY="_extId_";
    public final static String MESSAGE_POST_PROPERTIES_KEY="Properties";
    public final static String MESSAGE_POST_PAYLOAD_KEY="Payload";
    public final static String MESSAGE_HEADER_SOAPACTION_PROPERTY="SOAPAction";
    public static final String SOAPACTION_WRAP_QUOTES_PROPERTY = "be.soapserializer.soapaction.wrap.quotes";


    public final static ExpandedName XML_NODE_CONFIG = ExpandedName.makeName(NODE_CONFIG);
    public final static ExpandedName DEST_PROPERTY_PROXY_HOST = ExpandedName.makeName(PROXY_HOST);
    public final static ExpandedName DEST_PROPERTY_PROXY_PORT = ExpandedName.makeName(PROXY_PORT);
    public final static ExpandedName DEST_PROPERTY_PROXY_IDENTITY = ExpandedName.makeName(NODE_IDENTITY);
    public final static ExpandedName DEST_PROPERTY_PORT = ExpandedName.makeName(PROPERTY_PORT);
    public final static ExpandedName DEST_PROPERTY_HOST = ExpandedName.makeName(PROPERTY_HOST);
    public final static ExpandedName DEST_PROPERTY_ENABLE_LOOKUPS = ExpandedName.makeName(PROPERTY_ENABLE_LOOKUPS);
    public final static ExpandedName DEST_PROPERTY_USE_SSL = ExpandedName.makeName(PROPERTY_USE_SSL);
    public final static ExpandedName XML_NODE_SSL = ExpandedName.makeName(BEIdentityObjectFactory.SSL_NODE_NS, NODE_SSL);
    public final static ExpandedName DEST_PROPERTY_SSL_STRONGCIPHERSONLY = ExpandedName.makeName(BEIdentityObjectFactory.SSL_NODE_NS, PROPERTY_SSL_STRONGCIPHERSONLY);
    public final static ExpandedName DEST_PROPERTY_SSL_REQUIRESCLIENTAUTH = ExpandedName.makeName(BEIdentityObjectFactory.SSL_NODE_NS, PROPERTY_SSL_REQUIRESCLIENTAUTH);    
    public final static ExpandedName DEST_PROPERTY_SSL_CERT_URI = ExpandedName.makeName(BEIdentityObjectFactory.SSL_NODE_NS, PROPERTY_SSL_CERT_URI);
    
    public final static ExpandedName DEST_PROPERTY_SSL_TRUSTSTORE_PASSWORD = ExpandedName.makeName(BEIdentityObjectFactory.SSL_NODE_NS, PROPERTY_SSL_TRUSTSTORE_PASSWORD);
    
    public final static ExpandedName DEST_PROPERTY_SSL_IDENTITY_URI = ExpandedName.makeName(BEIdentityObjectFactory.SSL_NODE_NS, PROPERTY_SSL_IDENTITY_URI);
    public final static ExpandedName XML_NODE_RESPOSITORY = ExpandedName.makeName(REPOS_NAMESPACE, NODE_REPOS);
    public final static ExpandedName XML_NODE_IDENTITY = ExpandedName.makeName(NODE_IDENTITY);

    public final static String DEFAULT_DOC_PAGE_PROPERTY = "be.http.docPage";
    public final static String DOC_ROOT_PROPERTY = "be.http.docRoot";

    public final static String MAX_KEEP_ALIVE_REQS = "be.http.maxKeepAliveRequests";
    public final static String HTTP_REQUEST_DEBUG = "be.http.debug";
    public final static String DEBUG_LOG_FOLDER = "be.http.debugFolder";
    public final static String DEBUG_LOG_PATTERN = "be.http.debugLogPattern";
    public final static String MAX_HTTP_HEADER_SIZE = "be.http.maxHttpHeaderSize";
    public final static String MAX_POST_SIZE = "be.http.maxPostSize";
    public final static String MAX_SAVE_POST_SIZE = "be.http.maxSavePostSize";
    public final static String MAX_SPARE_THREADS = "be.http.maxSpareThreads";
    public final static String MIN_SPARE_THREADS = "be.http.minSpareThreads";
    public final static String RESTRICTED_USER_AGENTS = "be.http.restrictedUserAgents";
    
    public final static String URI_ENCODING= "be.http.URIEncoding";
    public final static String USE_BODY_ENCODING_FOR_URI= "be.http.useBodyEncodingForURI";
    public final static String DEFAULT_ENCODING= "be.http.defaultEncoding";
    
    public final static String COMPRESSION= "be.http.compression";
    public final static String COMPRESSIBLE_MIME_TYPE= "be.http.compressableMimeType";

    public final static String UTF8_ENCODING = "UTF-8";
    public final static String SCHEME = "be.http.scheme";
    public final static String SESSION_TIMEOUT = "be.http.sessionTimeout";
    public final static String CONNECTOR_TYPE = "be.http.connectorType";
    public final static String CONNECTOR_INSTANCES = "be.http.connectorInstances";
        
    public final static String ACCEPT_COUNT = "be.http.acceptCount";
	public final static String CONNECTION_TIMEOUT = "be.http.connectionTimeout";
	public final static String CONNECTION_LINGER = "be.http.connectionLinger";
	public final static String SOCKET_BUFFER_SIZE = "be.http.socketBufferSize";
	public final static String TCP_NO_DELAY = "be.http.tcpNoDelay";
	public final static String MAX_PROCESSORS = "be.http.maxProcessors";
	public final static String DNS_LOOKUPS = "be.http.enableDNSLookups";
	
	public final static String SERVER_HEADER = "be.http.serverHeader";

    public final static String GENERIC_CONTEXT_URI = "/*";
    
    /**
     * Channel cluster config for tomcat.
     */
    public static final String AJP_CONNECTOR_PORT = "ajp.connector.port";
    public static final String CLUSTER_MULTICAST_ADDRESS = "cluster.mcastAddress";
    public static final String CLUSTER_MULTICAST_PORT = "cluster.mcastPort";
    public static final String CLUSTER_MULTICAST_FREQUENCY = "cluster.mcastFrequency";
    public static final String CLUSTER_MULTICAST_MEMBER_DROP_THRESHOLD = "cluster.memberDropTime";
    public static final String CLUSTER_MULTICAST_TCP_LISTEN_PORT = "cluster.tcpListenPort";
    public static final String CLUSTER_MULTICAST_TCP_LISTEN_HOST = "cluster.tcpListenHost";
    public static final String CLUSTER_MULTICAST_RECEIVER_PORT = "cluster.receiver.port";
    public static final String CLUSTER_MULTICAST_RECEIVER_SELECTOR_TIMEOUT = "cluster.receiver.selectorTimeout";
    public static final String CLUSTER_MULTICAST_RECEIVER_MAX_THREADS = "cluster.receiver.maxThreads";
}
