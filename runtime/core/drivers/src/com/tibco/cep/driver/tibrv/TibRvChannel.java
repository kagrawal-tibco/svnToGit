package com.tibco.cep.driver.tibrv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.impl.AbstractChannel;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.util.ResourceManager;
import com.tibco.tibrv.Tibrv;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvRvdTransport;

public class TibRvChannel extends AbstractChannel<TibRvDestination> {
    protected TibrvRvdTransport m_rvTransport;

    protected HashMap eventListeners = new HashMap();
    protected ArrayList registrations;

    protected TibRvChannel(ChannelManager manager, String uri, TibRvChannelConfig config) {
        super(manager, uri, config);

        Iterator i = config.getDestinations().iterator();
        while (i.hasNext()) {
            addDestination(createDestination((DestinationConfig) i.next()));
        }
    }

    public TibRvDestination createDestination(DestinationConfig config) {
        return new TibRvDestination(this, config);
    }

    public void send(SimpleEvent event, String destURI, Map overrideData) throws Exception {
        //todo - Suresh?
    }

    protected void send(TibrvMsg msg) throws Exception {
        m_rvTransport.send(msg);
    }

    public void init() throws Exception {

        Tibrv.open(Tibrv.IMPL_NATIVE);

        final TibRvChannelConfig channelConfig = (TibRvChannelConfig) this.channelConfig;
        if (channelConfig.isUseSsl()) {
            TibrvSslHelper.initializeRvForSsl(channelConfig);
        }
        Iterator<TibRvDestination> i = getDestinations().values().iterator();
        while (i.hasNext()) {
            (i.next()).init();
        }
        super.init();
    }

    protected void printConfig() throws Exception {
        String service = ((TibRvChannelConfig) channelConfig).getService();
        String network = ((TibRvChannelConfig) channelConfig).getNetwork();
        String daemon  = ((TibRvChannelConfig) channelConfig).getDaemon();
        this.getLogger().log(Level.INFO,
                ResourceManager.getInstance().formatMessage(
                        "channel.connecting.rv", this.getURI(), service, network, daemon,
                        TibrvMsg.getStringEncoding()));
    }

    public void connect() throws Exception {
        synchronized(this) {
            if (getState().equals(State.CONNECTED)) {
                return;
            }

            if ((getState() == State.INITIALIZED) || getState() == State.RECONNECTING || getState() == State.STOPPED) {
                createTransport();
                Iterator<TibRvDestination> i = getDestinations().values().iterator();
                while (i.hasNext()) {
                    TibRvDestination dest = i.next();
                    dest.connect();
                }
                setState(State.CONNECTED);
            }
        }
    }

    protected void createTransport() throws Exception{
        final TibRvChannelConfig channelConfig = (TibRvChannelConfig) this.channelConfig;
        final String service = channelConfig.getService();
        final String network = channelConfig.getNetwork();
        final String daemon  = channelConfig.getDaemon();

        printConfig();
        m_rvTransport = new TibrvRvdTransport(service, network, daemon, "TIBCOAdministrator 123704e never " + getEmbeddedTicket());
    }

    // Courtesy of Jean Marie
    protected static String getEmbeddedTicket() {
        String a = "wGP";
        StringBuffer buf = new StringBuffer("c3M8");  // What are we achieving here??? Not obfuscation, certainly.
        buf.append('-');
        buf.append('G');
        buf.append("PV");
        buf.append("-Jmu-");
        buf.append("J");
        buf.append(a);
        buf.append("-a");
        buf.append("B5-JW8-sb4J");
        return buf.toString();
    } //getEmbeddedTicket

    public void start(int mode) throws Exception {
        State state = getState();

        if (state.equals(State.INITIALIZED) || state.equals(State.STOPPED)) {
            connect();

        }
        if (!(state.equals(State.CONNECTED) || state.equals(State.STARTED))) {
            throw new Exception("Inconsistent state to start channel: " + state.toString());
        }
        Iterator<TibRvDestination> i = getDestinations().values().iterator();
        while (i.hasNext()) {
            (i.next()).start(mode);
        }
        super.start(mode);
        this.getLogger().log(Level.INFO, ResourceManager.getInstance().formatMessage("channel.started", this.getURI()));
    }

    public void stop() {
        Iterator<TibRvDestination> i = getDestinations().values().iterator();
        while (i.hasNext()) {
            (i.next()).stop();
        }
        super.stop();
    }

    public void close() throws Exception {
        Iterator<TibRvDestination> i = getDestinations().values().iterator();
        while (i.hasNext()) {
            (i.next()).close();
        }

        if (m_rvTransport != null) {
            m_rvTransport.destroy();
            m_rvTransport = null;
        }
        setState(State.INITIALIZED); //ie only the RVChannel Object is created
    }

    public boolean isAsync() {
        return true;
    }
}
