package com.tibco.rta.client.tcp;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.RtaConnectionEx;
import com.tibco.rta.RtaException;
import com.tibco.rta.RtaSession;
import com.tibco.rta.client.BytesServiceResponse;
import com.tibco.rta.client.ServiceInvocationListener;
import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.property.impl.PropertyAtomString;
import com.tibco.rta.service.transport.TransportTypes;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 21/1/13
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class TCPRtaConnection implements RtaConnectionEx {

    private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    /**
     * tcp://host:port
     */
    private String connectionUrl;

    private String clientId;

    private String username;

    private Selector selector;

    private ByteBuffer readBuffer = ByteBuffer.allocateDirect(1024);

    private ChunkedInputBuffer chunkedInputBuffer;

    private TCPConnectionNotificationListener connectionNotificationListener;

    public TCPRtaConnection(String connectionUrl, String username, String password) {
        try {
        	this.connectionUrl = connectionUrl;
            this.username = username;            
            initSelector();
        } catch (Exception e) {
        	LOGGER.log(Level.ERROR, "Error establishing connection", e);
        }
    }

    @Override
    public ServiceResponse invokeService(String endpoint,
                                         String serviceOp,
                                         Map<String, String> properties,
                                         String payload) throws Exception {
        return invokeServiceInternal(null, endpoint, serviceOp, properties, payload, null);
    }

    @Override
    public ServiceResponse invokeService(String endpoint,
                                         String serviceOp,
                                         Map<String, String> properties,
                                         byte[] payload) throws Exception {
        throw new UnsupportedOperationException("TBD");
    }

    @Override
    public ServiceResponse invokeService(String endpoint,
                                         String serviceOp,
                                         Map<String, String> properties,
                                         String payload,
                                         ServiceInvocationListener serviceInvocationListener) throws Exception {
        throw new UnsupportedOperationException("TBD");
    }

    /**
     *
     * @param session - The session reference using this connection.
     * @param endpoint
     * @param serviceOp
     * @param properties
     * @param payload
     * @param serviceInvocationListener
     * @return
     * @throws Exception
     */
    public ServiceResponse invokeService(DefaultRtaSession session,
                                         String endpoint,
                                         String serviceOp,
                                         Map<String, String> properties,
                                         String payload,
                                         ServiceInvocationListener serviceInvocationListener) throws Exception {
        chunkedInputBuffer = new ChunkedInputBuffer(serviceInvocationListener);
        return invokeServiceInternal(session, endpoint, serviceOp, properties, payload, serviceInvocationListener);
    }

    @Override
    public ServiceResponse invokeService(String endpoint,
                                         String serviceOp,
                                         Map<String, String> properties,
                                         byte[] payload,
                                         ServiceInvocationListener serviceInvocationListener) throws Exception {
        throw new UnsupportedOperationException("TBD");
    }

    @Override
    public TransportTypes getTransportType() {
        return TransportTypes.TCP;
    }

    @Override
    public RtaSession createSession(Map<ConfigProperty, PropertyAtom<?>> sessionProps) throws RtaException {
        return createSession(null, sessionProps);
    }

    @Override
    public RtaSession createSession(String name, Map<ConfigProperty, PropertyAtom<?>> sessionProps) throws RtaException {
        sessionProps.put(ConfigProperty.CONNECTION_USERNAME, new PropertyAtomString(username));
        return new DefaultRtaSession(this, name, sessionProps);
    }

    public void setConnectionNotificationListener(TCPConnectionNotificationListener connectionNotificationListener) {
        this.connectionNotificationListener = connectionNotificationListener;
    }

    private ServiceResponse invokeServiceInternal(DefaultRtaSession session,
                                                  String endpoint,
                                                  String serviceOp,
                                                  Map<String, String> properties,
                                                  String payload,
                                                  ServiceInvocationListener serviceInvocationListener) throws IOException {

        ServiceResponse serviceResponse = null;

        while (selector.select() > 0) {
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iter = keys.iterator();

            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                SocketChannel clientChannel = (SocketChannel) key.channel();

                if (key.isValid()) {
                    if (key.isConnectable()) {
                        if (clientChannel.isConnectionPending()) {
                            //Finish connection
                            try {
                                if (clientChannel.finishConnect()) {
                                    //Register for write
                                    clientChannel.register(selector, SelectionKey.OP_WRITE);
                                    //Write only once.
                                    write(clientChannel, endpoint, properties);

                                    TCPConnectionEvent tcpConnectionEvent = new TCPConnectionEvent(TCPConnectionEvent.CONNECTION_ESTABLISH_EVENT);

                                    exchange(session, tcpConnectionEvent);
                                    //Send connection established notification.
                                    if (connectionNotificationListener != null) {
                                        connectionNotificationListener.onEvent(tcpConnectionEvent);
                                    }
                                }
                            } catch (ConnectException ce) {
                                LOGGER.log(Level.ERROR, "Failure to establish connection", ce);
                                exchange(session, ce);
                            }
                        }
                    }
                    //Read forever
                    if (key.isReadable()) {
                        serviceResponse = read(clientChannel);
                    }
                }
            }
        }
        return serviceResponse;
    }

    private void write(SocketChannel socketChannel,
                       String endpoint,
                       Map<String, String> properties) throws IOException {

        properties.put("Connection", "Keep-Alive");
        properties.put("Host", socketChannel.socket().getRemoteSocketAddress().toString());
        properties.put("Content-Length", "0");
        properties.put("Content-Type", "text/plain; charset=UTF-8");


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("POST");

        stringBuilder.append(" ");
        stringBuilder.append(endpoint);
        stringBuilder.append(" ");
        stringBuilder.append("HTTP/1.1");
        stringBuilder.append("\n");


        //Append all properties
        for (Map.Entry<String, String> entrySet : properties.entrySet()) {
            appendToBuffer(entrySet.getKey(), entrySet.getValue(), stringBuilder);
        }

        stringBuilder.append("\n");
        ///Write a hello to server
        stringBuilder.append("");

        ByteBuffer byteBuffer =
                ByteBuffer.wrap(stringBuilder.toString().getBytes(Charset.forName("UTF-8")));
        socketChannel.write(byteBuffer);

        if (byteBuffer.hasRemaining()) {
            //Wake up selector for something else
            selector.wakeup();
        }
        //Finished writing
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void exchange(DefaultRtaSession session, Object toExchange) {
        try {
            session.exchange(toExchange);
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "Exception during exchange activity", e);
        }
    }


    private ServiceResponse read(SocketChannel socketChannel) throws IOException {

        BytesServiceResponse bytesServiceResponse = null;
        try {
        	((Buffer)readBuffer).clear();
            byte[] bytes;

            int bytesRead = socketChannel.read(readBuffer);
            if (bytesRead > -1) {
                ((Buffer)readBuffer).flip();
                bytes = new byte[readBuffer.limit()];
                //Read the bulk
                readBuffer.get(bytes);
                readBuffer.compact();
                //Transfer for chunking
                chunkedInputBuffer.transfer(bytes);
            } else {
                //Reached EOF
                socketChannel.close();

                if (connectionNotificationListener != null) {
                    connectionNotificationListener.onEvent(new TCPConnectionEvent(TCPConnectionEvent.CONNECTION_DOWN_EVENT));
                }
                //throw exception so as to allow retry
                throw new IOException("Connection closed by remote host");
            }
        } catch (IOException ioe) {
            LOGGER.log(Level.ERROR, "", ioe);
            throw ioe;
        }
        return bytesServiceResponse;
    }

    private void appendToBuffer(String property, String value, StringBuilder buffer) {
        buffer.append(property);
        buffer.append(":");
        buffer.append(" ");
        buffer.append(value);
        buffer.append("\n");
    }

    @Override
    public void close(){
        try {
            selector.close();
        } catch (IOException ioe) {
            LOGGER.log(Level.ERROR, "Error closing connection", ioe);
        }
    }

    private void initSelector() throws IOException {
        // Create a new selector
        selector = SelectorProvider.provider().openSelector();

        // Create a new non-blocking client socket channel
        SocketChannel clientChannel = SocketChannel.open();

        clientChannel.configureBlocking(false);

        ConnectorInfo connectorInfo = new ConnectorInfo();

        connectorInfo.resolveUrl();

        // Bind the client socket to the specified address and port
        InetSocketAddress socketAddress = new InetSocketAddress(connectorInfo.getHost(), connectorInfo.getPort());

        clientChannel.connect(socketAddress);

        // Register the server socket channel, indicating an interest in
        // connecting to server
        clientChannel.register(selector, SelectionKey.OP_CONNECT);
    }

    @Override
    public String getScheme() {
        throw new UnsupportedOperationException("TBD");
    }

    @Override
    public String getHost() {
        throw new UnsupportedOperationException("TBD");
    }

    @Override
    public int getPort() {
        throw new UnsupportedOperationException("TBD");
    }

    private class ConnectorInfo {

        private String host;

        private int port;

        void resolveUrl() {
            if (connectionUrl.startsWith("tcp")) {
                int lastIndexOfColon = connectionUrl.lastIndexOf(':');
                String portString = connectionUrl.substring(lastIndexOfColon + 1, connectionUrl.length());
                port = Integer.parseInt(portString);
                int lastIndexOfSlash = connectionUrl.lastIndexOf('/');
                host = connectionUrl.substring(lastIndexOfSlash + 1, lastIndexOfColon);
            }
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean shouldSendHeartbeat() {
        return false;
    }
}