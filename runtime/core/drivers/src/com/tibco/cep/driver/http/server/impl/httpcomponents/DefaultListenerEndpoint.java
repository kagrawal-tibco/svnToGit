package com.tibco.cep.driver.http.server.impl.httpcomponents;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;

import org.apache.http.impl.nio.reactor.ListenerEndpointClosedCallback;
import org.apache.http.nio.reactor.ListenerEndpoint;

/**
 * Implementation of ListenerEndpointImpl in Http Core 4.0.
 * This class is required because ConfigurableListeningIOReactor uses some 
 * protected methods of this class so need to make them available in this package 
 */
public class DefaultListenerEndpoint implements ListenerEndpoint {

    private volatile boolean completed;
    private volatile boolean closed;
    private volatile SelectionKey key;
    private volatile SocketAddress address;
    private volatile IOException exception;

    private final ListenerEndpointClosedCallback callback;
    
    public DefaultListenerEndpoint(
            final SocketAddress address,
            final ListenerEndpointClosedCallback callback) {
        super();
        if (address == null) {
            throw new IllegalArgumentException("Address may not be null");
        }
        this.address = address;
        this.callback = callback;
    }
    
    public SocketAddress getAddress() {
        return this.address;
    }
    
    public boolean isCompleted() {
        return this.completed;
    }
    
    public IOException getException() {
        return this.exception;
    }
    
    public void waitFor() throws InterruptedException {
        if (this.completed) {
            return;
        }
        synchronized (this) {
            while (!this.completed) {
                wait();
            }
        }
    }
    
    public void completed(final SocketAddress address) {
        if (address == null) {
            throw new IllegalArgumentException("Address may not be null");
        }
        if (this.completed) {
            return;
        }
        this.completed = true;
        synchronized (this) {
            this.address = address;
            notifyAll();
        }
    }
 
    public void failed(final IOException exception) {
        if (exception == null) {
            return;
        }
        if (this.completed) {
            return;
        }
        this.completed = true;
        synchronized (this) {
            this.exception = exception;
            notifyAll();
        }
    }
 
    public void cancel() {
        if (this.completed) {
            return;
        }
        this.completed = true;
        this.closed = true;
        synchronized (this) {
            notifyAll();
        }
    }
 
    protected void setKey(final SelectionKey key) {
        this.key = key;
    }

    public boolean isClosed() {
        return this.closed || (this.key != null && !this.key.isValid());
    }

    public void close() {
        if (this.closed) {
            return;
        }
        this.completed = true;
        this.closed = true;
        if (this.key != null) {
            this.key.cancel();
            Channel channel = this.key.channel();
            if (channel.isOpen()) {
                try {
                    channel.close();
                } catch (IOException ignore) {}
            }
        }
        if (this.callback != null) {
            this.callback.endpointClosed(this);
        }
        synchronized (this) {
            notifyAll();
        }
    }
    
}
