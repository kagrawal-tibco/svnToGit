package com.tibco.cep.driver.http;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tibco.cep.designtime.model.service.channel.WebApplicationDescriptor;
import com.tibco.cep.driver.http.client.HttpChannelClient;
import com.tibco.cep.driver.http.client.impl.httpcomponents.HttpComponentsClient;
import com.tibco.cep.driver.http.server.ConnectorInfo;
import com.tibco.cep.driver.http.server.HttpChannelServer;
import com.tibco.cep.driver.http.server.HttpChannelServerFactory;
import com.tibco.cep.driver.http.server.impl.TomcatServerUtils;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.impl.AbstractChannel;
import com.tibco.cep.runtime.channel.impl.AbstractDestination;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.util.ResourceManager;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Mar 27, 2008
 * Time: 12:19:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpChannel extends AbstractChannel<HttpDestination> {
    private HttpChannelServer httpChannelServer;
    private HttpChannelClient httpChannelClient;
    private ArrayList dynamicDestinations = new ArrayList(5);   //This specifically for BW - SR:1-7UOFOB ++

    private boolean asyncFlag = true;

    public void setAsync(boolean asyncOption) {
    	asyncFlag = asyncOption;
    }

    protected HttpChannel(ChannelManager manager, String uri, HttpChannelConfig httpConfig) {
        super(manager, uri, httpConfig);

        Iterator i = httpConfig.getDestinations().iterator();
        while (i.hasNext()) {
            HttpDestination dest = new HttpDestination(this, (DestinationConfig) i.next());
            this.destinations.put(dest.getURI(), dest);
        }
    }

    public HttpChannelConfig getConfig() {
        return (HttpChannelConfig) channelConfig;
    }

    public HttpChannelServer getHttpChannelServer() {
        return httpChannelServer;
    }

    public HttpChannelClient getHttpChannelClient() {
        return httpChannelClient;
    }

    // AbstractChannel implementation

    public void init() throws Exception {

        HttpChannelConfig config = getConfig();

        ConnectorInfo cInfo = config.getConnectorInfo();


        String serverType = config.getServerType();
        httpChannelServer = HttpChannelServerFactory.INSTANCE.create(serverType,
                this,
                cInfo);

        httpChannelServer.init();
        //Deploy webapps if any
        if (httpChannelServer.supportsWebApp()) {
            //Read system property for now.
            //TODO this should come from channel config
            /**
             * Web-apps deployment code here.
             * call httpChannelServer.deployWebApp(.., ...)
             */
            List<WebApplicationDescriptor> webApplicationDescriptors = config.getWebApplicationDescriptors();
            if (webApplicationDescriptors != null) {
                for (WebApplicationDescriptor webApplicationDescriptor : webApplicationDescriptors) {
                    httpChannelServer.deployWebApp(webApplicationDescriptor.getContextURI(),
                                                   webApplicationDescriptor.getWebAppResourcePath());
                }
            }
        }
        httpChannelClient = new HttpComponentsClient(getConfig().getHost(), getConfig().getPort(), HttpMethods.METHOD_POST, null);
        httpChannelClient.init();
        Iterator<HttpDestination> i = getDestinations().values().iterator();
        while (i.hasNext()) {
            (i.next()).init();
        }
        super.init();
    }

    public HttpDestination createDestination(DestinationConfig config) {
        HttpDestination dest = new HttpDestination(this, config);
        dynamicDestinations.add(dest);
        return dest;
    }

    public void connect() throws Exception {

        State state = getState();

        if (state == State.UNINITIALIZED) {
            init();
        }

        if ((state == State.STARTED) || (state == State.CONNECTED) || (state == State.STOPPED)) {
            return;
        }

        if ((state == State.INITIALIZED) || (state == State.RECONNECTING)) {
            synchronized (state) {

                state = getState(); //Check the state again, call the getState, as opposed to the state variable
                if ((state == State.STARTED) || (state == State.CONNECTED) || (state == State.STOPPED)) {
                    return;
                }


                if (this.getLogger().isEnabledFor(Level.INFO)) {
                    this.getLogger().log(Level.INFO, "%s [Thread]%s [State]%s",
                            this.toString(), Thread.currentThread().getName(), state);
                }
                rebindDestinations(state);
                //SR:1-7UOFOB --
                setState(State.CONNECTED);
                return;
            }
        }

        if (state == State.EXCEPTION) {
            HttpChannelConfig config = (HttpChannelConfig) channelConfig;
            if (this.getLogger().isEnabledFor(Level.INFO)) {
                this.getLogger().log(Level.INFO, "%s [Thread]%s [State]%s",
                        this.toString(), Thread.currentThread().getName(), state);
            }
            rebindDestinations(State.RECONNECTING);
            setState(State.CONNECTED);
            return;
        }
        throw new Exception("Invalid state to connect..." + state);
    }

    private void rebindDestinations(State state) throws Exception {
        Iterator<HttpDestination> i = getDestinations().values().iterator();
        while (i.hasNext()) {
            HttpDestination dest = i.next();
            dest.connect();
            if (state == State.RECONNECTING) {
                dest.rebind();
            }
        }
        //SR:1-7UOFOB ++
        Iterator itr = dynamicDestinations.iterator();
        while (itr.hasNext()) {
            HttpDestination dest = (HttpDestination) itr.next();
            dest.connect();
            if (state == State.RECONNECTING) {
                dest.rebind();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HTTP Channel");
        ConnectorInfo connectorInfo = httpChannelServer.getConnectorInfo();
        connectorInfo.getPort();
        stringBuilder.append("[Port:");
        stringBuilder.append(connectorInfo.getPort());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
    
    

    public void send(SimpleEvent event, String destinationURI, Map overrideData) throws Exception {

    }

    public void start(int mode) throws Exception {
        if (getState() == State.STARTED) return;

        if ((getState() == State.INITIALIZED) || (getState() == State.UNINITIALIZED)) {
            connect();
        }
        boolean startHttpChannelServer = false;
        if ((getState() == State.CONNECTED) || (getState() == State.STOPPED)) {
            Iterator<HttpDestination> i = getDestinations().values().iterator();
            while (i.hasNext()) {
                AbstractDestination d = i.next();
                d.start(mode);
                if (d.getBoundedRuleSessions().size() >= 1) {
                    startHttpChannelServer = true;
                }

            }

            if (mode != ChannelManager.PASSIVE_MODE) {
                if (startHttpChannelServer) {
                    ConnectorInfo info = this.getConfig().getCInfo();
                    if (!httpChannelServer.isStarted() && !TomcatServerUtils.isPortAvailable(info.getPort())) {
                    	throw new Exception(String.format("Port [%s] is already bound to other process.", info.getPort()));
                    } else {
                    	httpChannelServer.start();
                    }
                }
            } else {
                return;
            }


        }
        super.start(mode);
        this.getLogger().log(Level.INFO, ResourceManager.getInstance().formatMessage("channel.started", this.getURI()));
    }


    public void close() throws Exception {
        if ((getState() == State.STARTED) || (getState() == State.STOPPED)) {
            stop();
        }
    }

    /**
     * Temporarily call stop on the channel
     */
    public void stop() {

        try {
            if ((getState() == State.STARTED)) {

                Iterator<HttpDestination> i = getDestinations().values().iterator();
                while (i.hasNext()) {
                    (i.next()).stop();
                }
                httpChannelServer.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.stop();
    }

    public void shutdown() throws Exception {

        for (Iterator<HttpDestination> it = getDestinations().values().iterator(); it.hasNext(); ) {
            final Destination dest = it.next();
            dest.close();
        }

        if (httpChannelClient != null)
            ((HttpComponentsClient) httpChannelClient).shutDown();

        if (this.getState() != State.RECONNECTING) {
            this.setState(State.UNINITIALIZED);
        }
    }

    /**
     * HttpChannel is async is false by default
     *
     * @return
     */
    public boolean isAsync() {
        //return true;
    	return asyncFlag;
    }
}
