package com.tibco.cep.driver.http.server.impl.httpcomponents;


import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadFactory;

import org.apache.http.impl.nio.reactor.AbstractMultiworkerIOReactor;
import org.apache.http.impl.nio.reactor.ChannelEntry;
import org.apache.http.impl.nio.reactor.ListenerEndpointClosedCallback;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.nio.reactor.IOReactorStatus;
import org.apache.http.nio.reactor.ListenerEndpoint;
import org.apache.http.nio.reactor.ListeningIOReactor;
import org.apache.http.params.HttpParams;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;


public class ConfigurableListeningIOReactor extends AbstractMultiworkerIOReactor implements ListeningIOReactor {

    private final Queue<DefaultListenerEndpoint> requestQueue;
    private final Set<DefaultListenerEndpoint> endpoints;
    private final Set<SocketAddress> pausedEndpoints;
    private Logger logger;
    private int numberOfMaxChannels, channelsOpened = 0;
    private final List<SocketChannel> openChannels = new LinkedList<SocketChannel>();
    private int backlog;
    private volatile boolean paused;
    private Properties beProperties;

    private static final String ASYNC_SOCKET_RECEIVE_BUFFER_SIZE = "be.http.socketReceiveBufferSize";
    
    public ConfigurableListeningIOReactor(
            int workerCount, 
            final ThreadFactory threadFactory,
            final HttpParams params, int numberOfMaxChannels, int backlog, Logger logger, Properties properties) throws IOReactorException {
        super(workerCount, threadFactory, params);
        this.requestQueue = new ConcurrentLinkedQueue<DefaultListenerEndpoint>();
        this.endpoints = Collections.synchronizedSet(new HashSet<DefaultListenerEndpoint>());
        this.pausedEndpoints = new HashSet<SocketAddress>();
    	this.numberOfMaxChannels = numberOfMaxChannels;
    	this.backlog = backlog;
    	this.logger = logger;
    	this.beProperties = properties;
      }

    public ConfigurableListeningIOReactor(
            int workerCount, final HttpParams params, int numberOfMaxChannels, int backlog, Logger logger, Properties properties) throws IOReactorException {
        super(workerCount,null, params);
        this.requestQueue = new ConcurrentLinkedQueue<DefaultListenerEndpoint>();
        this.endpoints = Collections.synchronizedSet(new HashSet<DefaultListenerEndpoint>());
        this.pausedEndpoints = new HashSet<SocketAddress>();
    	this.numberOfMaxChannels = numberOfMaxChannels;
    	this.backlog = backlog;
    	this.logger = logger;
    	this.beProperties = properties;
      }

    @Override
    protected void cancelRequests() throws IOReactorException {
        DefaultListenerEndpoint request;
        while ((request = this.requestQueue.poll()) != null) {
            request.cancel();
        }
    }

    @Override
    protected void processEvents(int readyCount) throws IOReactorException {
        if (!this.paused) {
            processSessionRequests();
        }

        if (readyCount > 0) {
            Set<SelectionKey> selectedKeys = this.selector.selectedKeys();
            for (Iterator<SelectionKey> it = selectedKeys.iterator(); it.hasNext(); ) {
                
                SelectionKey key = it.next();
                processEvent(key);
                
            }
            selectedKeys.clear();
        }
    }

  
    private void processEvent(final SelectionKey key) 
            throws IOReactorException {
        try {
        	if(numberOfMaxChannels != -1) {
        		checkForClosedChannels();
        	}
            if (key.isAcceptable() 
            		&& (numberOfMaxChannels == -1 
            				|| channelsOpened < numberOfMaxChannels)) {
                ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = null;
                try {
          			this.logger.log(Level.DEBUG, "Socket Channels Opened: %s", this.channelsOpened);
                    socketChannel = serverChannel.accept();
                } catch (IOException ex) {
                    if (this.exceptionHandler == null || 
                            !this.exceptionHandler.handle(ex)) {
                        throw new IOReactorException(
                                "Failure accepting connection", ex);
                    }
                }
                
                if (socketChannel != null) {
                    try {
                        prepareSocket(socketChannel.socket());
                    } catch (IOException ex) {
                        if (this.exceptionHandler == null || 
                                !this.exceptionHandler.handle(ex)) {
                            throw new IOReactorException(
                                    "Failure initalizing socket", ex);
                        }
                    }
                    ChannelEntry entry = new ChannelEntry(socketChannel); 
                    addChannel(entry);
                    channelsOpened++;
                    if(numberOfMaxChannels != -1) {
	                    openChannels.add(socketChannel);
                    }
                }
            }
            
        } catch (CancelledKeyException ex) {
            ListenerEndpoint endpoint = (ListenerEndpoint) key.attachment();
            this.endpoints.remove(endpoint);
            key.attach(null);
        }
    }
    
    private void checkForClosedChannels() {
    	
    	Iterator<SocketChannel> socketChannels = openChannels.iterator();
    	List<SocketChannel> closedChannels = new LinkedList<SocketChannel>();
    	while(socketChannels.hasNext()) {
    		SocketChannel channel = socketChannels.next();
    		if(!channel.isOpen() || !channel.isConnected()) {
    			closedChannels.add(channel);
    		}
    	}
    	for(int i=0 ; i < closedChannels.size(); i++) {
			channelsOpened--;
    		openChannels.remove(closedChannels.get(i));
    	}
        this.logger.log(Level.DEBUG, "Closing Channels -- Currently Open Count: %s", this.channelsOpened);
    }


    private DefaultListenerEndpoint createEndpoint(final SocketAddress address) {
        DefaultListenerEndpoint endpoint = new DefaultListenerEndpoint(
                address,
                new ListenerEndpointClosedCallback() {

                    public void endpointClosed(final ListenerEndpoint endpoint) {
                        endpoints.remove(endpoint);
                    }
                    
                });
        return endpoint;
    }
    
    public ListenerEndpoint listen(final SocketAddress address) {
        if (this.status.compareTo(IOReactorStatus.ACTIVE) > 0) {
            throw new IllegalStateException("I/O reactor has been shut down");
        }
        DefaultListenerEndpoint request = createEndpoint(address);
        this.requestQueue.add(request);
        this.selector.wakeup();
        return request;
    }

    private void processSessionRequests() throws IOReactorException {
        DefaultListenerEndpoint request;
        while ((request = this.requestQueue.poll()) != null) {
            SocketAddress address = request.getAddress();
            ServerSocketChannel serverChannel;
            try {
                serverChannel = ServerSocketChannel.open();
                serverChannel.configureBlocking(false);
            } catch (IOException ex) {
                throw new IOReactorException("Failure opening server socket", ex);
            }
            try {
            	int currentBufferSize = serverChannel.socket().getReceiveBufferSize();
            	int confBufferSize = Integer.parseInt(beProperties.getProperty(ASYNC_SOCKET_RECEIVE_BUFFER_SIZE,"-1"));
        	    this.logger.log(Level.DEBUG, "Current receive buffer size: %s", currentBufferSize);
            	if(confBufferSize != -1) {
                    this.logger.log(Level.DEBUG, "Setting receive buffer size to: %s", confBufferSize);
	            	serverChannel.socket().setReceiveBufferSize(confBufferSize);
            	}
            	if(backlog != -1) {
            		serverChannel.socket().bind(address,backlog);
            	} else {
            		serverChannel.socket().bind(address);
            	}
            } catch (IOException ex) {
                request.failed(ex);
                if (this.exceptionHandler == null || !this.exceptionHandler.handle(ex)) {
                    throw new IOReactorException("Failure binding socket to address " 
                            + address, ex);
                } else {
                    return;
                }
            }
            try {
                SelectionKey key = serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);
                key.attach(request);
                request.setKey(key);
            } catch (IOException ex) {
                throw new IOReactorException("Failure registering channel " +
                        "with the selector", ex);
            }
            
            this.endpoints.add(request);
            request.completed(serverChannel.socket().getLocalSocketAddress());
        }
    }
    
    public Set<ListenerEndpoint> getEndpoints() {
        Set<ListenerEndpoint> set = new HashSet<ListenerEndpoint>();
        synchronized (this.endpoints) {
            Iterator<DefaultListenerEndpoint> it = this.endpoints.iterator();
            while (it.hasNext()) {
                ListenerEndpoint endpoint = it.next();
                if (!endpoint.isClosed()) {
                    set.add(endpoint);
                } else {
                    it.remove();
                }
            }
        }
        return set;
    }

    public void pause() throws IOException {
        if (this.paused) {
            return;
        }
        this.paused = true;
        synchronized (this.endpoints) {
            Iterator<DefaultListenerEndpoint> it = this.endpoints.iterator();
            while (it.hasNext()) {
                ListenerEndpoint endpoint = it.next();
                if (!endpoint.isClosed()) {
                    endpoint.close();
                    this.pausedEndpoints.add(endpoint.getAddress());
                }
            }
            this.endpoints.clear();
        }
    } 

    public void resume() throws IOException {
        if (!this.paused) {
            return;
        }
        this.paused = false;
        for (SocketAddress address: this.pausedEndpoints) {
            DefaultListenerEndpoint request = createEndpoint(address);
            this.requestQueue.add(request);
        }
        this.pausedEndpoints.clear();
        this.selector.wakeup();
    }

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
    
}
