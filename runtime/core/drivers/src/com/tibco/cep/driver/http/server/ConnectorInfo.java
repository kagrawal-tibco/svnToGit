package com.tibco.cep.driver.http.server;

import static com.tibco.cep.driver.http.HttpChannelConstants.ACCEPT_COUNT;
import static com.tibco.cep.driver.http.HttpChannelConstants.COMPRESSIBLE_MIME_TYPE;
import static com.tibco.cep.driver.http.HttpChannelConstants.COMPRESSION;
import static com.tibco.cep.driver.http.HttpChannelConstants.CONNECTION_LINGER;
import static com.tibco.cep.driver.http.HttpChannelConstants.CONNECTION_TIMEOUT;
import static com.tibco.cep.driver.http.HttpChannelConstants.CONNECTOR_INSTANCES;
import static com.tibco.cep.driver.http.HttpChannelConstants.CONNECTOR_TYPE;
import static com.tibco.cep.driver.http.HttpChannelConstants.DEFAULT_ENCODING;
import static com.tibco.cep.driver.http.HttpChannelConstants.DNS_LOOKUPS;
import static com.tibco.cep.driver.http.HttpChannelConstants.MAX_HTTP_HEADER_SIZE;
import static com.tibco.cep.driver.http.HttpChannelConstants.MAX_KEEP_ALIVE_REQS;
import static com.tibco.cep.driver.http.HttpChannelConstants.MAX_POST_SIZE;
import static com.tibco.cep.driver.http.HttpChannelConstants.MAX_PROCESSORS;
import static com.tibco.cep.driver.http.HttpChannelConstants.MAX_SAVE_POST_SIZE;
import static com.tibco.cep.driver.http.HttpChannelConstants.MAX_SPARE_THREADS;
import static com.tibco.cep.driver.http.HttpChannelConstants.MIN_SPARE_THREADS;
import static com.tibco.cep.driver.http.HttpChannelConstants.RESTRICTED_USER_AGENTS;
import static com.tibco.cep.driver.http.HttpChannelConstants.SCHEME;
import static com.tibco.cep.driver.http.HttpChannelConstants.SESSION_TIMEOUT;
import static com.tibco.cep.driver.http.HttpChannelConstants.SOCKET_BUFFER_SIZE;
import static com.tibco.cep.driver.http.HttpChannelConstants.TCP_NO_DELAY;
import static com.tibco.cep.driver.http.HttpChannelConstants.URI_ENCODING;
import static com.tibco.cep.driver.http.HttpChannelConstants.USE_BODY_ENCODING_FOR_URI;
import static com.tibco.cep.driver.http.HttpChannelConstants.HTTP_REQUEST_DEBUG;
import static com.tibco.cep.driver.http.HttpChannelConstants.DEBUG_LOG_FOLDER;
import static com.tibco.cep.driver.http.HttpChannelConstants.DEBUG_LOG_PATTERN;
import static com.tibco.cep.driver.http.HttpChannelConstants.SERVER_HEADER;

import java.io.File;
import java.net.InetAddress;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Mar 28, 2008
 * Time: 3:22:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectorInfo {

    private boolean allowTrace;
    private InetAddress address;
    private int port;
    private boolean isSecure;
    private int acceptCount;
    private int maxProcessors;
    private int socketBufferSize;
    private String compression;
    private int connectionTimeout;
    private int connectionLinger;
    private boolean tcpNoDelay;
    private String restrictedUerAgents;
    private int maxKeepAliveRequests;

    private boolean enableDNSLookups;
    private int sessionTimeout;
    private String connectorType;
    private int connectorInstances;
    private int maxSpareThreads;
    private int minSpareThreads;
    private int maxSavePostSize;
    private int maxPostSize;
    private int maxHttpHeaderSize;
    private String scheme;
    private String compressableMimeType;
    private String defaultEncoding;
    private String uriEncoding;
    private boolean useBodyEncodingForURI;
    
    private boolean debugRequest;
    private String debugLogFolder;
    private String debugLogPattern;
    
    private String serverHeader;
    
    /**
     * SSL Configuration
     */
    private SSLParams sslParams;

    public ConnectorInfo(final InetAddress address, final int port, final Properties connnectionProperties) {
        setAddress(address);
        setPort(port);
        setSecure(false);
        
        initializeHttpConnectionParameters(connnectionProperties);
    }
    
    private void initializeHttpConnectionParameters(final Properties beProperties) {
        acceptCount = Integer.parseInt(beProperties.getProperty(ACCEPT_COUNT,"-1"));
        connectionTimeout = Integer.parseInt(beProperties.getProperty(CONNECTION_TIMEOUT, "-1"));
        connectionLinger = Integer.parseInt(beProperties.getProperty(CONNECTION_LINGER, "-1"));
        socketBufferSize = Integer.parseInt(beProperties.getProperty(SOCKET_BUFFER_SIZE, "9000"));
        tcpNoDelay = Boolean.parseBoolean(beProperties.getProperty(TCP_NO_DELAY, "true"));
        maxProcessors = Integer.parseInt(beProperties.getProperty(MAX_PROCESSORS, "-1"));
        enableDNSLookups = Boolean.parseBoolean(beProperties.getProperty(DNS_LOOKUPS, "false"));
        sessionTimeout = Integer.parseInt(beProperties.getProperty(SESSION_TIMEOUT,"1800"));
        connectorType = beProperties.getProperty(CONNECTOR_TYPE, "NIO");
        connectorInstances = Integer.parseInt(beProperties.getProperty(CONNECTOR_INSTANCES,"1"));
        
        maxKeepAliveRequests = Integer.parseInt(beProperties.getProperty(MAX_KEEP_ALIVE_REQS, "-1"));
        maxHttpHeaderSize = Integer.parseInt(beProperties.getProperty(MAX_HTTP_HEADER_SIZE, "4096"));
        maxPostSize = Integer.parseInt(beProperties.getProperty(MAX_POST_SIZE, "2097152"));
        maxSavePostSize = Integer.parseInt(beProperties.getProperty(MAX_SAVE_POST_SIZE, "4096"));
        scheme = beProperties.getProperty(SCHEME, null);
        maxSpareThreads = Integer.parseInt(beProperties.getProperty(MAX_SPARE_THREADS, "50"));
        minSpareThreads = Integer.parseInt(beProperties.getProperty(MIN_SPARE_THREADS, "4"));
        restrictedUerAgents = beProperties.getProperty(RESTRICTED_USER_AGENTS, null);
        compression = beProperties.getProperty(COMPRESSION, "off");
        compressableMimeType = beProperties.getProperty(COMPRESSIBLE_MIME_TYPE, null);
        
        defaultEncoding = beProperties.getProperty(DEFAULT_ENCODING, "UTF-8");
        uriEncoding = beProperties.getProperty(URI_ENCODING, null);
        useBodyEncodingForURI = Boolean.parseBoolean(beProperties.getProperty(USE_BODY_ENCODING_FOR_URI, "false"));
        
        debugRequest = Boolean.parseBoolean(beProperties.getProperty(HTTP_REQUEST_DEBUG, "false"));
        debugLogFolder = beProperties.getProperty(DEBUG_LOG_FOLDER, System.getProperty("user.dir") + File.separator + "logs");
        debugLogPattern = beProperties.getProperty(DEBUG_LOG_PATTERN, "common");
        serverHeader = beProperties.getProperty(SERVER_HEADER, "Apache-Coyote/1.1");
    }

    public void setSSLParams(SSLParams sslParams) {
        setSecure(true);
        this.sslParams = sslParams;
    }

    public String getKeyAlias() {
        return sslParams.getKeyAlias();
    }

    public String getKeyStoreFile() {
        return sslParams.getKeyStoreFile();
    }

    public String getKeyManagerAlgorithm() {
        return sslParams.getAlgorithm();
    }

    public String getKeyStorePassword() {
        return sslParams.getKeyStorePassword();
    }

    public boolean isKeystorePasswordObfuscated() {
        return sslParams.isPasswordObfuscated();
    }

    public String getKeyStoreType() {
        return sslParams.getKeyStoreType();
    }
    
    public boolean isKeyStoreType() {
    	return sslParams.isKeyStoreType();
    }

    public String getSslEnabledProtocols() {
        return sslParams.getSslEnabledProtocols();
    }

    public String getTrustStoreFile() {
        return sslParams.getTrustStoreFile();
    }

    public String getTrustStorePass() {
        return sslParams.getTrustStorePass();
    }

    public String getTrustStoreType() {
        return sslParams.getTrustStoreType();
    }
    
    public String getCertUrl() {
    	return sslParams.getCertUrl();
    }
    
    public String getPrivateKeyUrl() {
    	return sslParams.getPrivateKey();
    }
    
    public String getKeyPassword() {
    	return sslParams.getKeyPassword();
    }

    public boolean isClientAuthRequired() {
    	return sslParams.isClientAuthRequired();
    }
    
    public String getCipherSuites() {
    	return sslParams.getCiphers();
    }

	public boolean isAllowTrace() {
		return allowTrace;
    }

	public void setAllowTrace(boolean allowTrace) {
		this.allowTrace = allowTrace;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isSecure() {
		return isSecure;
	}

	public void setSecure(boolean isSecure) {
		this.isSecure = isSecure;
	}

	public int getAcceptCount() {
		return acceptCount;
	}

	public void setAcceptCount(int acceptCount) {
		this.acceptCount = acceptCount;
	}

	public int getMaxProcessors() {
		return maxProcessors;
    }

	public void setMaxProcessors(int maxProcessors) {
		this.maxProcessors = maxProcessors;
    }

    public int getSocketBufferSize() {
        return socketBufferSize;
    }

	public void setSocketBufferSize(int socketBufferSize) {
		this.socketBufferSize = socketBufferSize;
    }

	public String getCompression() {
        return compression;
    }

	public void setCompression(String compression) {
        this.compression = compression;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

	public int getConnectionLinger() {
		return connectionLinger;
    }

	public void setConnectionLinger(int connectionLinger) {
		this.connectionLinger = connectionLinger;
    }

	public boolean isTcpNoDelay() {
		return tcpNoDelay;
    }

	public void setTcpNoDelay(boolean tcpNoDelay) {
		this.tcpNoDelay = tcpNoDelay;
    }

	public String getRestrictedUerAgents() {
		return restrictedUerAgents;
	}

	public void setRestrictedUerAgents(String restrictedUerAgents) {
		this.restrictedUerAgents = restrictedUerAgents;
    }

	public int getMaxKeepAliveRequests() {
		return maxKeepAliveRequests;
    }

	public void setMaxKeepAliveRequests(int maxKeepAliveRequests) {
		this.maxKeepAliveRequests = maxKeepAliveRequests;
    }

	public boolean isEnableDNSLookups() {
		return enableDNSLookups;
    }

	public void setEnableDNSLookups(boolean enableDNSLookups) {
		this.enableDNSLookups = enableDNSLookups;
    }
    
	public int getSessionTimeout() {
		return sessionTimeout;
    }

	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
    }

	public String getConnectorType() {
		return connectorType;
    }

	public void setConnectorType(String connectorType) {
		this.connectorType = connectorType;
    }

	public int getConnectorInstances() {
		return connectorInstances;
    }

	public void setConnectorInstances(int connectorInstances) {
		this.connectorInstances = connectorInstances;
    }

	public int getMaxSpareThreads() {
		return maxSpareThreads;
    }

	public void setMaxSpareThreads(int maxSpareThreads) {
		this.maxSpareThreads = maxSpareThreads;
    }

	public int getMinSpareThreads() {
		return minSpareThreads;
    }

	public void setMinSpareThreads(int minSpareThreads) {
		this.minSpareThreads = minSpareThreads;
    }

	public int getMaxSavePostSize() {
		return maxSavePostSize;
    }
    
	public void setMaxSavePostSize(int maxSavePostSize) {
		this.maxSavePostSize = maxSavePostSize;
    }
    
	public int getMaxPostSize() {
		return maxPostSize;
    }
    
	public void setMaxPostSize(int maxPostSize) {
		this.maxPostSize = maxPostSize;
    }

	public int getMaxHttpHeaderSize() {
		return maxHttpHeaderSize;
	}

	public void setMaxHttpHeaderSize(int maxHttpHeaderSize) {
		this.maxHttpHeaderSize = maxHttpHeaderSize;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getCompressableMimeType() {
		return compressableMimeType;
	}

	public void setCompressableMimeType(String compressableMimeType) {
		this.compressableMimeType = compressableMimeType;
	}

	public String getDefaultEncoding() {
		return defaultEncoding;
	}

	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}

	public String getUriEncoding() {
		return uriEncoding;
	}

	public void setUriEncoding(String uriEncoding) {
		this.uriEncoding = uriEncoding;
	}

	public boolean isUseBodyEncodingForURI() {
		return useBodyEncodingForURI;
	}

	public void setUseBodyEncodingForURI(boolean useBodyEncodingForURI) {
		this.useBodyEncodingForURI = useBodyEncodingForURI;
	}
	
	public boolean isDebugRequest() {
		return debugRequest;
	}

	public void setDebugRequest(boolean debugRequest) {
		this.debugRequest = debugRequest;
	}

	public String getDebugLogFolder() {
		return debugLogFolder;
	}

	public void setDebugLogFolder(String debugLogFolder) {
		this.debugLogFolder = debugLogFolder;
	}

	public String getDebugLogPattern() {
		return debugLogPattern;
	}

	public void setDebugLogPattern(String debugLogPattern) {
		this.debugLogPattern = debugLogPattern;
	}
	
	public String getServerHeader() {
		return serverHeader;
	}

	public void setServerHeader(String serverHeader) {
		this.serverHeader = serverHeader;
	}
	
	public boolean isUseServerCipherOrder() {
		return sslParams.isUseServerCipherOrder();
	}
}
