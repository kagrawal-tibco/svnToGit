package com.tibco.rta.service.transport.http;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.service.transport.AbstractRtaConnectorInfo;

import java.net.InetAddress;
import java.util.Properties;

import static com.tibco.rta.service.transport.http.HTTPTransportServiceConstants.ACCEPT_COUNT;
import static com.tibco.rta.service.transport.http.HTTPTransportServiceConstants.CONNECTION_LINGER;
import static com.tibco.rta.service.transport.http.HTTPTransportServiceConstants.CONNECTION_TIMEOUT;
import static com.tibco.rta.service.transport.http.HTTPTransportServiceConstants.KEEPALIVE_TIMEOUT;
import static com.tibco.rta.service.transport.http.HTTPTransportServiceConstants.MAX_KEEP_ALIVE_REQUESTS;
import static com.tibco.rta.service.transport.http.HTTPTransportServiceConstants.MAX_PROCESSORS;
import static com.tibco.rta.service.transport.http.HTTPTransportServiceConstants.SOCKET_BUFFER_SIZE;
import static com.tibco.rta.service.transport.http.HTTPTransportServiceConstants.TCP_NO_DELAY;
import static com.tibco.rta.service.transport.http.HTTPTransportServiceConstants.DNS_LOOKUPS;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 25/10/12
 * Time: 12:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class HTTPConnectorInfo extends AbstractRtaConnectorInfo {

    private InetAddress address;

    private int port;

    private boolean isSecure;

    private int acceptCount;

    private int maxProcessors;

    private int socketBufferSize;

    private boolean compression;

    private int connectionTimeout;

    private int keepAliveTimeout;

    private int connectionLinger;

    private boolean tcpNoDelay;

    private boolean enableDNSLookups;

    private String restrictedUerAgents;

    private int maxKeepAliveRequests;

    public HTTPConnectorInfo(Properties properties) throws Exception {
        acceptCount = Integer.parseInt(properties.getProperty(ACCEPT_COUNT, "-1"));
        connectionTimeout = Integer.parseInt(properties.getProperty(CONNECTION_TIMEOUT, "-1"));
        keepAliveTimeout = Integer.parseInt(properties.getProperty(KEEPALIVE_TIMEOUT, "-1"));
        connectionLinger = Integer.parseInt(properties.getProperty(CONNECTION_LINGER, "-1"));
        socketBufferSize = Integer.parseInt(properties.getProperty(SOCKET_BUFFER_SIZE, "9000"));
        tcpNoDelay = Boolean.parseBoolean(properties.getProperty(TCP_NO_DELAY, "true"));
        enableDNSLookups = Boolean.parseBoolean(properties.getProperty(DNS_LOOKUPS, "false"));
        maxProcessors = Integer.parseInt(properties.getProperty(MAX_PROCESSORS, "-1"));
        maxKeepAliveRequests = Integer.parseInt(properties.getProperty(MAX_KEEP_ALIVE_REQUESTS, "100"));
        address = InetAddress.getByName(properties.getProperty(ConfigProperty.RTA_TRANSPORT_HTTP_HOSTNAME.getPropertyName(), "localhost"));
        port = Integer.parseInt(properties.getProperty(ConfigProperty.RTA_TRANSPORT_HTTP_PORT.getPropertyName(), "4448"));
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

    public void setSecure(boolean secure) {
        isSecure = secure;
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

    public boolean isCompression() {
        return compression;
    }

    public void setCompression(boolean compression) {
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
}
