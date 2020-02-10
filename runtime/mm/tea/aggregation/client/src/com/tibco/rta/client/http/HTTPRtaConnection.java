package com.tibco.rta.client.http;

import com.tibco.rta.AbstractRtaConnection;
import com.tibco.rta.ConfigProperty;
import com.tibco.rta.RtaConnectionEx;
import com.tibco.rta.RtaException;
import com.tibco.rta.RtaSession;
import com.tibco.rta.client.ConnectionException;
import com.tibco.rta.client.ServiceInvocationListener;
import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.property.impl.PropertyAtomString;
import com.tibco.rta.service.transport.TransportTypes;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

/**
 * HTTP based implementation of client side {@link RtaConnectionEx}
 */
public class HTTPRtaConnection extends AbstractRtaConnection {

    /**
     * The implementation we are using is {@link DefaultHttpClient} which is thread safe.
     */
    private HttpClient httpClient;

    public HTTPRtaConnection() {
    }

    public HTTPRtaConnection(String connectionUrl, String username, String password) throws RtaException {
        this.connectionUrl = connectionUrl;
        this.username = username;
        this.password = password;
        //Extract host/port
        try {
            URL url = new URL(connectionUrl);
            scheme = url.getProtocol();
            host = url.getHost();
            port = url.getPort();
        } catch (MalformedURLException e) {
            throw new RtaException(e);
        }

        HttpHost httpHost = new HttpHost(host, port);

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(
                new Scheme(scheme, port, PlainSocketFactory.getSocketFactory()));

        PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager(schemeRegistry);
        //Increase max total connection to 200
        //TODO take these params from API/properties?
        connectionManager.setMaxTotal(200);
        connectionManager.setMaxPerRoute(new HttpRoute(httpHost), 50);
        //Increase default max connection per route to 20
        connectionManager.setDefaultMaxPerRoute(20);

        httpClient = new DefaultHttpClient(connectionManager);
    }

    /**
     * Creates the session.
     *
     * @param sessionProps the session props
     *                     <p/>
     *                     Session props
     * @return the rta session
     * @throws com.tibco.rta.RtaException the rta exception
     * @throws NullPointerException       if any key or value in the properties is null
     */
    @Override
    public RtaSession createSession(Map<ConfigProperty, PropertyAtom<?>> sessionProps) throws RtaException {
        return createSession(null, sessionProps);
    }

    /**
     * Creates the session with name
     *
     * @param name         Name for session.
     * @param sessionProps the session props
     * @return the rta session
     * @throws RtaException
     * @throws NullPointerException if any key or value in the properties is null
     */
    @Override
    public RtaSession createSession(String name, Map<ConfigProperty, PropertyAtom<?>> sessionProps) throws RtaException {
        if (username != null) {
            sessionProps.put(ConfigProperty.CONNECTION_USERNAME, new PropertyAtomString(username));
        }
        return new DefaultRtaSession(this, name, sessionProps);
    }

    /**
     * Close.
     */
    @Override
    public void close() {
        httpClient.getConnectionManager().shutdown();
    }


    public ServiceResponse invokeService(String endpoint, String serviceOp, Map<String, String> properties, String payload) throws Exception {
        return invokeService(endpoint, serviceOp, properties, payload, null);
    }

    @Override
    public ServiceResponse invokeService(String endpoint, String serviceOp, Map<String, String> properties, byte[] payload) throws Exception {
        return invokeService(endpoint, serviceOp, properties, payload, null);
    }

    /**
     * Invoke a service on the metric engine.
     * The service is identified using an abstract endpoint and operation
     * to be invoked on the service.
     *
     * @param endpoint                  A url depending on the underlying transport
     * @param serviceOp                 The operation on the service
     * @param properties                Any optional properties which will be sent over
     * @param payload                   Payload string
     * @param serviceInvocationListener Callback notified upon receiving response. Maybe null.
     * @return a response from the service.
     * @throws Exception
     */
    @Override
    public ServiceResponse invokeService(String endpoint, String serviceOp, Map<String, String> properties, String payload, ServiceInvocationListener serviceInvocationListener) throws ConnectionException {
        URI uri = URI.create(connectionUrl + endpoint);
        HttpPost httpPost = new HttpPost(uri);
        Header header = new BasicHeader("op", serviceOp);
        httpPost.addHeader(header);

        if (properties != null) {
            Set<Map.Entry<String, String>> entries = properties.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                Header entryHeader = new BasicHeader(entry.getKey(), entry.getValue());
                httpPost.addHeader(entryHeader);
            }
        }

        if (payload != null) {
            try {
                HttpEntity httpEntity = new StringEntity(payload, Charset.forName("UTF-8").name());
                httpPost.setEntity(httpEntity);
            } catch (Throwable e) {
                throw new ConnectionException(e);
            }
        }
        try {
            return httpClient.execute(httpPost, new HTTPServiceInvocationResponseHandler(serviceInvocationListener));
        } catch (Throwable e) {
            throw new ConnectionException(e);
        }
    }

    /**
     * Invoke a service on the metric engine.
     * The service is identified using an abstract endpoint and operation
     * to be invoked on the service.
     *
     * @param endpoint                  A url depending on the underlying transport
     * @param serviceOp                 The operation on the service
     * @param properties                Any optional properties which will be sent over
     * @param payload                   Payload bytes
     * @param serviceInvocationListener Callback notified upon receiving response. Maybe null.
     * @return a response from the service.
     * @throws Exception
     */
    @Override
    public ServiceResponse invokeService(String endpoint, String serviceOp, Map<String, String> properties, byte[] payload, ServiceInvocationListener serviceInvocationListener) throws ConnectionException {
        URI uri = URI.create(connectionUrl + endpoint);
        HttpPost httpPost = new HttpPost(uri);
        Header header = new BasicHeader("op", serviceOp);
        httpPost.addHeader(header);

        if (properties != null) {
            Set<Map.Entry<String, String>> entries = properties.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                Header entryHeader = new BasicHeader(entry.getKey(), entry.getValue());
                httpPost.addHeader(entryHeader);
            }
        }

        if (payload != null) {
            try {
                HttpEntity httpEntity = new ByteArrayEntity(payload);
                httpPost.setEntity(httpEntity);
            } catch (Throwable e) {
                throw new ConnectionException(e);
            }
        }
        try {
            return httpClient.execute(httpPost, new HTTPServiceInvocationResponseHandler(serviceInvocationListener));
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    /**
     * Get underlying transport type.
     *
     * @return
     */
    public TransportTypes getTransportType() {
        return TransportTypes.HTTP;
    }

    @Override
    public boolean shouldSendHeartbeat() {
        return false;
    }
}
