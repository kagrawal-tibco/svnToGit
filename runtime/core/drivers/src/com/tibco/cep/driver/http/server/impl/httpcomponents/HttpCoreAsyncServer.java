package com.tibco.cep.driver.http.server.impl.httpcomponents;

import com.tibco.cep.driver.http.HttpChannel;
import com.tibco.cep.driver.http.HttpChannelConstants;
import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.driver.http.server.ConnectorInfo;
import com.tibco.cep.driver.http.server.HttpChannelServer;
import com.tibco.cep.driver.http.server.impl.httpcomponents.handlers.ASyncHttpComponentsDestinationProcessor;
import com.tibco.cep.driver.http.server.impl.httpcomponents.handlers.AsyncHttpComponentsFileProcessor;
import com.tibco.cep.driver.http.server.impl.httpcomponents.handlers.AsyncHttpComponentsRequestHandler;
import com.tibco.cep.driver.http.server.utils.SSLUtils;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.security.ObfuscationEngine;
import org.apache.http.HttpException;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.nio.DefaultServerIOEventDispatch;
import org.apache.http.impl.nio.SSLServerIOEventDispatch;
import org.apache.http.impl.nio.reactor.DefaultListeningIOReactor;
import org.apache.http.impl.nio.reactor.SSLIOSessionHandler;
import org.apache.http.nio.NHttpConnection;
import org.apache.http.nio.protocol.AsyncNHttpServiceHandler;
import org.apache.http.nio.protocol.EventListener;
import org.apache.http.nio.protocol.NHttpRequestHandlerRegistry;
import org.apache.http.nio.reactor.IOEventDispatch;
import org.apache.http.nio.reactor.ListeningIOReactor;
import org.apache.http.nio.util.HeapByteBufferAllocator;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.*;

import javax.net.ssl.*;
import javax.security.cert.X509Certificate;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.KeyStore;
import java.util.*;


/**
 * HTTP Server Implementation using HTTP Core Asynchronous NIO API's
 *
 * @author vjavere
 */
public class HttpCoreAsyncServer implements HttpChannelServer {

    private HttpChannel httpChannel;
    private boolean started = false;
    private boolean initialized = false;
    private IOEventDispatch ioEventDispatch;
    private ListeningIOReactor ioReactor;
    private AsyncNHttpServiceHandler handler;

    private HttpParams params;
    private ConnectorInfo cInfo;
    private SSLContext sslcontext;
    private Properties beProperties;

    //asynchronous service handler parameters
    private int numberOfWorkers;
    private int socketTimeout;
    private int socketBufferSize;
    private int acceptCount;
    private boolean tcpNoDelay = false;
    private boolean staleConnectionCheck = false;
    private String[] enabledCipherSuites;
    private String[] enabledProtocols;
    private String defaultKeyManagerAlgorithm, defaultTrustManagerAlgorithm;

    private static final String STALE_CONN_CHECK = "be.http.async.staleConnectionCheck";
    private static final int DEFAULT_NUM_OF_WORKERS = 5;

    private static Logger logger = LogManagerFactory.getLogManager().getLogger(HttpCoreAsyncServer.class);

    /**
     * Default Constructor
     */
    public HttpCoreAsyncServer(HttpChannel httpChannel, ConnectorInfo cInfo) throws Exception {


        this.httpChannel = httpChannel;
        this.logger = httpChannel.getLogger();
        beProperties = httpChannel.getConfig().getProperties();
        this.cInfo = cInfo;
        
        Properties props = readPropsFromExternalProps();
        
        if (cInfo.getMaxProcessors() != -1) {
            numberOfWorkers = cInfo.getMaxProcessors();
        } else {
            numberOfWorkers = DEFAULT_NUM_OF_WORKERS;
        }
        String thrds = props.getProperty("maxThreads");
        if (thrds != null) {
        	numberOfWorkers = Integer.parseInt(thrds);
        }
        
        socketTimeout = cInfo.getConnectionTimeout();
        socketBufferSize = cInfo.getSocketBufferSize();
        tcpNoDelay = cInfo.isTcpNoDelay();
        staleConnectionCheck = Boolean.parseBoolean(beProperties.getProperty(STALE_CONN_CHECK, "false"));
        acceptCount = cInfo.getAcceptCount();
        String cipherSuites = beProperties.getProperty(SSLUtils.SSL_SERVER_CIPHERS, "");
        if (cipherSuites != null && cipherSuites.trim().length() > 0) {
            enabledCipherSuites = cipherSuites.split(SSLUtils.DELIMITER_FOR_SSL_PARAMS);
        }
        String enabledProtocols = beProperties.getProperty(SSLUtils.SSL_SERVER_ENABLED_PROTOCOLS, "");
        if (enabledProtocols != null && enabledProtocols.trim().length() > 0) {
            this.enabledProtocols = enabledProtocols.split(SSLUtils.DELIMITER_FOR_SSL_PARAMS);
        }
        defaultKeyManagerAlgorithm = beProperties.getProperty(SSLUtils.SSL_SERVER_KEYMANAGER_ALGO, "SunX509");
        defaultTrustManagerAlgorithm = beProperties.getProperty(SSLUtils.SSL_SERVER_TRUSTMANAGER_ALGO, null);

    }

    /**
     * Maps all the destinations URIs on this channel with the service handlers
     *
     * @param handler
     */
    private void addDestinationMappings(AsyncNHttpServiceHandler handler) {
        // Set up request handlers
        ASyncHttpComponentsDestinationProcessor destProcessor = new ASyncHttpComponentsDestinationProcessor();
        NHttpRequestHandlerRegistry registry = new NHttpRequestHandlerRegistry();
        AsyncHttpComponentsRequestHandler reqHandler = new AsyncHttpComponentsRequestHandler(destProcessor, httpChannel);
        if (this.httpChannel == null) return;
        Collection<Channel> channels = this.httpChannel.getChannelManager().getChannels();
        final Map<String, Channel.Destination> httpDestinations = new HashMap<String, Channel.Destination>();
        for (Channel ch : channels) {
            if (ch instanceof HttpChannel) {
                HttpChannel httpChannel = (HttpChannel) ch;
                Collection<HttpDestination> destinations = httpChannel.getDestinations().values();
                httpDestinations.putAll(httpChannel.getDestinations());
                for (Channel.Destination dest : destinations) {
                    registry.register(dest.getURI(), reqHandler);
                }
            }
        }
        destProcessor.setHttpDestinations(httpDestinations);

        /* Getting the docRoot from system property, as the designer configuration changes
      * have not yet been finalized
      * if(httpChannel.getConfig().getDocRoot() != null && httpChannel.getConfig().getDocRoot().trim().length() > 0)
     if(docRoot == null) {*/
        String docRoot = beProperties.getProperty(HttpChannelConstants.DOC_ROOT_PROPERTY, null);
        //}
        String docPage = beProperties.getProperty(HttpChannelConstants.DEFAULT_DOC_PAGE_PROPERTY);
        if (docRoot != null && docRoot.trim().length() > 0) {
            AsyncHttpComponentsFileProcessor processor = new AsyncHttpComponentsFileProcessor(docRoot, true);
            if (docPage != null && docPage.trim().length() > 0) {
                processor.setDefaultFileName(docPage);
            }
            reqHandler = new AsyncHttpComponentsRequestHandler(processor, httpChannel);
        }

        registry.register("*", reqHandler);
        handler.setHandlerResolver(registry);
        handler.getParams().setParameter("registry", registry);
        handler.setEventListener(new BEEventLogger());
    }

    /**
     * Initializes the HTTP Core Asynchronous HTTP Server
     * Configures an IOReactor
     * Adds request handlers for mapping destination
     * URIs to Request URIs and interceptors to add standard headers on response
     * and does SSL initialization
     */
    public void init() throws Exception {

        logger.log(Level.OFF, "Initializing HTTP Core Asynchronous HTTP Server ");
        String defaultEncoding = beProperties.getProperty(HttpChannelConstants.DEFAULT_ENCODING, "UTF-8");
        params = new BasicHttpParams();
        params
                .setIntParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeout) //this should be configurable, a value of 0 means server will never timeout
                .setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, socketBufferSize) // can be set from connector info's buffer size
                .setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, staleConnectionCheck)
                .setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, tcpNoDelay)
                .setParameter(CoreProtocolPNames.ORIGIN_SERVER, "HttpComponents/1.1")
                .setParameter(CoreProtocolPNames.HTTP_ELEMENT_CHARSET, defaultEncoding);

        BasicHttpProcessor httpproc = new BasicHttpProcessor();
        httpproc.addInterceptor(new ResponseDate());
        httpproc.addInterceptor(new ResponseServer());
        httpproc.addInterceptor(new ResponseContent());
        httpproc.addInterceptor(new ResponseConnControl());
        handler = new AsyncNHttpServiceHandler(httpproc,
                new DefaultHttpResponseFactory(),
                new DefaultConnectionReuseStrategy(),
                new HeapByteBufferAllocator(), params);
        if (cInfo.isSecure()) {
            KeyStore keystore = KeyStore.getInstance(cInfo.getKeyStoreType());
            String keystorePass;
            if (cInfo.isKeystorePasswordObfuscated()) {
                keystorePass = new String(ObfuscationEngine.decrypt(cInfo.getKeyStorePassword()));
            } else {
                keystorePass = cInfo.getKeyStorePassword();
            }
            String keystoreFilePath = cInfo.getKeyStoreFile();
            File keystoreFile = new File(keystoreFilePath);

            if (keystoreFile.exists() && keystoreFile.canRead() && !keystoreFile.isDirectory()) {
                logger.log(Level.INFO, "Loading keystore from path %s", keystoreFilePath);
                keystore.load(new FileInputStream(keystoreFile), keystorePass.toCharArray());
            } else {
                logger.log(Level.WARN, "Keystore file path specified by %s cannot be read. SSL may not work as expected.", keystoreFilePath);
            }
            String keyManagerAlgo = null;
            if (defaultKeyManagerAlgorithm != null && defaultKeyManagerAlgorithm.trim().length() > 0) {
                keyManagerAlgo = defaultKeyManagerAlgorithm;
            } else {
                keyManagerAlgo = KeyManagerFactory.getDefaultAlgorithm();
            }
            KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(keyManagerAlgo);
            kmfactory.init(keystore, keystorePass.toCharArray());
            KeyManager[] keymanagers = kmfactory.getKeyManagers();
            TrustManager[] trustmanagers = null;
            if (cInfo.getTrustStoreFile() != null) {
                KeyStore truststore = KeyStore.getInstance(SSLUtils.KEYSTORE_JKS_TYPE);
                String trustManagerAlgo = null;
                if (defaultTrustManagerAlgorithm != null &&
                        defaultTrustManagerAlgorithm.trim().length() > 0) {
                    trustManagerAlgo = defaultTrustManagerAlgorithm;
                } else {
                    trustManagerAlgo = TrustManagerFactory.getDefaultAlgorithm();
                }
                truststore.load(new FileInputStream(cInfo.getTrustStoreFile()), SSLUtils.PASSWORD_NOT_SET.toCharArray());
                TrustManagerFactory trustmanager = TrustManagerFactory.getInstance(trustManagerAlgo);
                trustmanager.init(truststore);
                trustmanagers = trustmanager.getTrustManagers();
            }
            sslcontext = SSLContext.getInstance(SSLUtils.SSLProtocol.TLS.getProtocol());
            sslcontext.init(keymanagers, trustmanagers, null);
        }
        initialized = true;
    }

    @Override
    public boolean supportsWebApp() {
        return false;
    }

    @Override
    public ConnectorInfo getConnectorInfo() {
        return cInfo;
    }
    
    

    @Override
    public void deployWebApp(String contextPath, String webAppPath) throws Exception {
        throw new UnsupportedOperationException("Webapp deployment not supported");
    }

    /**
     * Initializes the HTTP/HTTPS IOReactors and adds destination mappings
     *
     * @throws Exception
     */
    public void initializeAndCreateIOReactors() throws Exception {
        addDestinationMappings(handler);
        if (cInfo.isSecure()) {
            if (cInfo.isClientAuthRequired())
                ioEventDispatch = new SSLServerIOEventDispatch(handler, sslcontext, new HttpChannelSSLIOSessionHandler(), params);
            else
                ioEventDispatch = new SSLServerIOEventDispatch(handler, sslcontext, params);

        } else {
            ioEventDispatch = new DefaultServerIOEventDispatch(handler, params);
        }
        if (acceptCount == -1) {
            ioReactor = new DefaultListeningIOReactor(numberOfWorkers, params);
        } else {
            this.ioReactor = new ConfigurableListeningIOReactor(this.numberOfWorkers, this.params, -1,
                    this.acceptCount, this.logger, this.beProperties);
        }
    }

    /**
     * SSLIOSessionHandler to initialize the SSLEngine for Client Authentication Check
     *
     * @author vjavere
     */
    private class HttpChannelSSLIOSessionHandler implements SSLIOSessionHandler {
        /**
         * Sets the need client authentication flag on the SSLEngine before handshake
         */
        public void initalize(SSLEngine sslengine, HttpParams params) throws SSLException {
            sslengine.setNeedClientAuth(cInfo.isClientAuthRequired());
            if (enabledCipherSuites != null) {
                String[] suppCipherSuites = sslengine.getSupportedCipherSuites();
                List<String> suppSuites = Arrays.asList(suppCipherSuites);
                List<String> configuredEnabledCiphers = Arrays.asList(enabledCipherSuites);
                List<String> enabledSuites = new ArrayList<String>();
                for (String cipher : configuredEnabledCiphers) {
                    if (suppSuites.contains(cipher)) {
                        enabledSuites.add(cipher);
                    }
                }
                if (enabledSuites.size() > 0) {
                    sslengine.setEnabledCipherSuites(enabledSuites.toArray(new String[enabledSuites.size()]));
                }
            }
            if (enabledProtocols != null) {
                String[] suppProtocols = sslengine.getSupportedProtocols();
                List<String> supportedProtocolList = Arrays.asList(suppProtocols);
                List<String> enabledProtocolList = new ArrayList<String>();
                List<String> configuredEnableProtocols = Arrays.asList(enabledProtocols);

                for (String enabledProtocol : configuredEnableProtocols) {
                    if (supportedProtocolList.contains(enabledProtocol)) {
                        enabledProtocolList.add(enabledProtocol);
                    }
                }
                if (enabledProtocolList.size() > 0) {
                    sslengine.setEnabledProtocols(enabledProtocolList.toArray(new String[enabledProtocolList.size()]));
                }
            }
        }

        /**
         * Currently this method just logs the peer certificate chain, we may require to implement
         * it if any verification is required
         */
        public void verify(SocketAddress remoteAddress, SSLSession session) throws SSLException {
            X509Certificate[] certs = session.getPeerCertificateChain();
            // Examine peer certificate chain
            for (X509Certificate cert : certs) {
                if (logger.isEnabledFor(Level.INFO)) {
                    logger.log(Level.INFO, cert.toString());
                }
            }
        }
    }

    /**
     * Starts the IO Reactor in a separate thread
     */
    public void start() throws Exception {
        Thread t = new Thread("HTTP_Core_Listener_Thread") {
            @Override
            public void run() {
                try {
                    if (!initialized) {
                        init();
                    }
                    initializeAndCreateIOReactors();
                    final InetAddress address = cInfo.getAddress();
                    if (address == null ||
                            address.getHostAddress() == null ||
                            address.getHostName() == null ||
                            address.getHostAddress().equals("127.0.0.1") ||
                            address.getHostName().equals("localhost")) {
                        ioReactor.listen(new InetSocketAddress(cInfo.getPort()));
                    } else {
                        ioReactor.listen(new InetSocketAddress(cInfo.getAddress(), cInfo.getPort()));
                    }
                    ioReactor.execute(ioEventDispatch);
                } catch (InterruptedIOException ex) {
                    if (logger.isEnabledFor(Level.FATAL)) {
                        logger.log(Level.FATAL, ex, ex.getMessage());
                    }
                } catch (IOException e) {
                    if (logger.isEnabledFor(Level.FATAL)) {
                        logger.log(Level.FATAL, e, e.getMessage());
                    }
                } catch (Exception e) {
                    if (logger.isEnabledFor(Level.FATAL)) {
                        logger.log(Level.FATAL, e, e.getMessage());
                    }
                }
            }
        };
        t.start();
        logger.log(Level.DEBUG, "Http Components Server started");
        started = true;
    }


    /**
     * Stops the HTTP Server
     */
    public void stop() throws Exception {
        if (started)
            ioReactor.shutdown();
    }

    /**
     * This class will listen to the connection events and log messages in the logger
     *
     * @author vjavere
     */
    public class BEEventLogger implements EventListener {

        private int connectionCounter = 0;

        public void connectionOpen(final NHttpConnection conn) {
            ++this.connectionCounter;
            logger.log(Level.DEBUG, "Connection open:%s counter:%s", conn, this.connectionCounter);
        }

        public void connectionTimeout(final NHttpConnection conn) {
            logger.log(Level.DEBUG, "Connection timed out: %s", conn);
        }

        public void connectionClosed(final NHttpConnection conn) {
            --this.connectionCounter;
            logger.log(Level.DEBUG, "Connection closed:%s counter:%s", conn, this.connectionCounter);
        }

        public void fatalIOException(final IOException ex, final NHttpConnection conn) {
            logger.log(Level.DEBUG, "I/O Error %s", ex.getMessage());
        }

        public void fatalProtocolException(final HttpException ex, final NHttpConnection conn) {
            logger.log(Level.DEBUG, "HTTP Error %s", ex.getMessage());
        }

    }

	private Properties readPropsFromExternalProps() {
		// Temporary Hack to support BIO connector for Apple
		        Properties props = new Properties();
		        String filename = null;
		        try {
		            // Add additional properties
		            filename = System.getProperty("be.tomcat.props");
		            if (filename != null) {
		                FileInputStream fis = new FileInputStream(filename);
		                props.load(fis);
		                fis.close();
		            }
		        } catch (Exception e) {
		            logger.log(Level.WARN, String.format("Not loading the properties. Check path specified for System property [be.tomcat.props]=%s", filename));
		        }
		        return props;
	}
	
	@Override
	public boolean isStarted() {
		return started;
	}

}
