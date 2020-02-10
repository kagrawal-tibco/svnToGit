package com.tibco.cep.driver.http.server.impl.httpcomponents;

import com.tibco.cep.driver.http.HttpChannel;
import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.driver.http.server.ConnectorInfo;
import com.tibco.cep.driver.http.server.HttpChannelServer;
import com.tibco.cep.driver.http.server.impl.httpcomponents.handlers.HttpChannelSyncRequestHandler;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpException;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.DefaultHttpServerConnection;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.*;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP Server Implementation using HTTP Core Synchronous API's
 * @author vjavere
 *
 */
public class HttpCoreSyncServer implements HttpChannelServer {

    /**
     * This thread is used to open a new server socket, open a new http connection
     *  and waits for http requests.   
     * 
     */
    private static Logger logger = LogManagerFactory.getLogManager().getLogger(HttpCoreAsyncServer.class);
    private class RequestListenerThread extends Thread {

        private ServerSocket serversocket;
        private HttpParams params;
        private HttpService httpService;
        private DefaultHttpServerConnection serverConnection;


        public RequestListenerThread(int port, InetAddress addr) throws IOException {
            if (addr == null
                    || addr.getHostAddress() == null
                    || addr.getHostName() == null
                    || addr.getHostAddress().equals("127.0.0.1")
                    || addr.getHostName().equals("localhost")) {
                this.serversocket = new ServerSocket(port, 0);
            } else {
                this.serversocket = new ServerSocket(port, 0, addr);
            }
        }

        /**
         * Maps all the destinations on this channel with the service handler 
         * @param service - service handler
         */
        private void addDestinationMappings(HttpService httpService) {
            // Set up request handlers
            HttpRequestHandlerRegistry reqistry = new HttpRequestHandlerRegistry();
            HttpChannelSyncRequestHandler reqHandler = new HttpChannelSyncRequestHandler();
            if (httpChannel == null) {
                return;
            }
            Collection<Channel> channels =
                    httpChannel.getChannelManager().getChannels();
            final Map<String, Channel.Destination> httpDestinations =
                    new HashMap<String, Channel.Destination>();
            for (Channel ch : channels) {
                if (ch instanceof HttpChannel) {
                    HttpChannel selHttpChannel = (HttpChannel) ch;
                    Collection<HttpDestination> destinations = selHttpChannel.getDestinations().values();
                    httpDestinations.putAll(httpChannel.getDestinations());
                    for (Channel.Destination dest : destinations) {
                        reqistry.register(dest.getURI(), reqHandler);
                    }
                }
            }
            reqHandler.setHttpDestinations(httpDestinations);
            reqistry.register("/", reqHandler);
            httpService.setHandlerResolver(reqistry);
        }

        /**
         * Initializes the HTTP Core Synchronous HTTP Server
         * Sets the HTTP socket parameters, adds request handlers for mapping destination
         * URIs to Request URIs and interceptors to add standard headers on response
         */
        private void initialize() {
            this.params = new BasicHttpParams();
            this.params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 0).setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 8 * 1024).setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, false).setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true).setParameter(CoreProtocolPNames.ORIGIN_SERVER, "HttpComponents/1.1");

            // Set up the HTTP protocol processor
            BasicHttpProcessor httpproc = new BasicHttpProcessor();
            httpproc.addInterceptor(new ResponseDate());
            httpproc.addInterceptor(new ResponseServer());
            httpproc.addInterceptor(new ResponseContent());
            httpproc.addInterceptor(new ResponseConnControl());
            // Set up the HTTP service
            this.httpService = new HttpService(
                    httpproc,
                    new DefaultConnectionReuseStrategy(),
                    new DefaultHttpResponseFactory());
            this.httpService.setParams(this.params);
            this.serverConnection = new DefaultHttpServerConnection();
            addDestinationMappings(httpService);

        }

        /**
         * Starts a server socket to listen for synchronous HTTP Connection 
         */
        public void run() {
            initialize();
            System.out.println("Listening on port " + this.serversocket.getLocalPort());
            while (!Thread.interrupted() && started) {
                try {
                    // Set up HTTP connection
                    Socket socket = this.serversocket.accept();
                    System.out.println("Incoming connection from " + socket.getInetAddress());
                    serverConnection.bind(socket, this.params);
                    System.out.println("New connection thread");
                    HttpContext context = new BasicHttpContext(null);
                    context.setAttribute("serverConnection", serverConnection);
                    try {
                        while (!Thread.interrupted() && this.serverConnection.isOpen()) {
                            this.httpService.handleRequest(this.serverConnection, context);
                        }
                    } catch (ConnectionClosedException ex) {
                        System.err.println("Client closed connection");
                    } catch (IOException ex) {
                        System.err.println("I/O error: " + ex.getMessage());
                    } catch (HttpException ex) {
                        System.err.println("Unrecoverable HTTP protocol violation: " + ex.getMessage());
                    } finally {
                        try {
                            this.serverConnection.shutdown();
                        } catch (IOException ignore) {
                        }
                    }

                } catch (InterruptedIOException ex) {
                    break;
                } catch (IOException e) {
                    System.err.println("I/O error initialising connection thread: "
                            + e.getMessage());
                    break;
                }
            }
        }
    }
    // HTTP Channel for this server
    private HttpChannel httpChannel;
    private boolean initialized = false, started = false;
    ConnectorInfo cInfo;
    private RequestListenerThread requestListener;

    @Override
    public ConnectorInfo getConnectorInfo() {
        return cInfo;
    }
    
    

    /**
     * Initialize the HTTP server with a channel and connector info
     * @param httpChannel -- HTTP Channel
     * @param cInfo -- Connector Info
     * @throws Exception
     */
    public HttpCoreSyncServer(HttpChannel httpChannel, ConnectorInfo cInfo) throws Exception {
        this.httpChannel = httpChannel;
        this.cInfo = cInfo;
    }

    public void init() {
        try {
            logger.log(Level.OFF, "Initializing HTTP Core Asynchronous HTTP Server ");
            requestListener = new RequestListenerThread(cInfo.getPort(), cInfo.getAddress());
        } catch (IOException io) {
            throw new RuntimeException(io);
        }
        this.initialized = true;

    }

    /**
     * Starts the server
     */
    public void start() throws Exception {
        if (!initialized) {
            init();
        }
        requestListener.start();
        System.out.println("Server started ");
        started = true;
    }

    /**
     * Stops the server
     */
    public void stop() throws Exception {
        started = false;
    }

    @Override
    public boolean supportsWebApp() {
        return false;
    }

    @Override
    public void deployWebApp(String contextPath, String webAppPath) throws Exception {
        throw new UnsupportedOperationException("Webapp deployment not supported");
    }
    
    @Override
    public boolean isStarted() {
    	return false;
    }
}
