package com.tibco.cep.driver.tibrv;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.impl.FTAsyncRuleServiceProviderImpl;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.util.ResourceManager;
import com.tibco.tibrv.TibrvCmQueueTransport;
import com.tibco.tibrv.TibrvCmTransport;
import com.tibco.tibrv.TibrvMsg;


public class TibRvcmChannel extends TibRvChannel {
    protected TibrvCmTransport m_rvcmTransport;
    protected boolean isdq;
    protected String cmName;
    protected int mode = ChannelManager.PASSIVE_MODE;
    List pendingDeliveryMessages = new LinkedList();
    final Object syncObject = new Object();
    int reconnectCount = 10;
    boolean isClient = false;
    boolean isFT = false;


    protected TibRvcmChannel(ChannelManager manager, String uri, TibRvChannelConfig config, boolean isdq) {
        super(manager, uri, config);
        this.isdq = isdq;
        this.cmName = config.cmName;
        this.reconnectCount = manager.getRuleServiceProvider().getGlobalVariables().getVariableAsInt("com.tibco.cep.drivers.tibrvcm.reconnect.tries", 10);

//SS Fixed: SRID:1-7V7RD4 ++
        isClient = Boolean.valueOf(this.getServiceProviderProperties().getProperty("com.tibco.cep.rsp.isClient", "false")).booleanValue();

        //SS Fixed: SRID:1-7V7RD4 --

        // Fixed CR:1-9NK26X
        RuleServiceProvider containerRsp = ((RuleServiceProviderImpl)getChannelManager().getRuleServiceProvider()).getContainerRsp();
        if (containerRsp != null && containerRsp instanceof FTAsyncRuleServiceProviderImpl) {
            isFT = true;
        }
    }


    //called from the destination
    protected void send(TibrvMsg msg) throws Exception {
/*        if ((getState() == State.CONNECTED) || (isClient)) {
            m_rvcmTransport.send(msg);
        }
        else {
            synchronized(syncObject) {
                pendingDeliveryMessages.add(msg);
            }
        }
*/
        m_rvcmTransport.send(msg);
    }

    protected void printConfig() throws Exception {
        String service = ((TibRvChannelConfig) channelConfig).getService();
        String network = ((TibRvChannelConfig) channelConfig).getNetwork();
        String daemon  = ((TibRvChannelConfig) channelConfig).getDaemon();
        TibRvChannelConfig rvconfig = (TibRvChannelConfig) channelConfig;
        if (!isdq) {
            this.getLogger().log(Level.INFO,
                    ResourceManager.getInstance().formatMessage("channel.connecting.rvcm",
                            this.getURI(), service, network, daemon, rvconfig.getCmName(),
                            rvconfig.getLedgerFile(), "" + rvconfig.getSyncLedger(),
                            TibrvMsg.getStringEncoding()));

        } else {
            this.getLogger().log(Level.INFO,
                    ResourceManager.getInstance().formatMessage("channel.connecting.rvdq",
                            this.getURI(), service, network, daemon, rvconfig.getCmqName(),
                            "" + rvconfig.getWorkerWeight(), "" + rvconfig.getWorkerTasks(),
                            "" + rvconfig.getSchedulerWeight(), "" + rvconfig.getSchedulerHeartbeat(),
                            "" + rvconfig.getSchedulerActivation(),
                            TibrvMsg.getStringEncoding()));
        }

    }

    public void connect() throws Exception {

        if (isFT && (mode != ChannelManager.ACTIVE_MODE) && (!isClient)){ //Do not connect channel when running as a be-engine and in a FT-Pair and not being activated
            return;
        }
        
        if (getState() == State.CONNECTED) return;

        synchronized(this) {
//           if (super.m_rvTransport == null) {
                createTransport();

                if (super.m_rvTransport == null) {
                    throw new Exception ("Transport could not be created.");
                }
//            }

            TibRvChannelConfig rvconfig = (TibRvChannelConfig) channelConfig;
            //printConfig();

            if (!isdq) {
                for (int i=0; i < reconnectCount; i++) {
                    try {
                           m_rvcmTransport = new TibrvCmTransport( m_rvTransport,
                                rvconfig.getCmName(),
                                true,
                                ("".equals(rvconfig.getLedgerFile())) ? null : rvconfig.getLedgerFile(),
                                rvconfig.getSyncLedger());
                        if (m_rvcmTransport != null) break;

                    }

                    catch (Exception e) {
                        this.getLogger().log(Level.ERROR, "Exception when creating TibrvCmTransport", e);
                        this.getLogger().log(Level.INFO, "Trying to reconnect - count[%d]", i);
                        Thread.sleep(3000);
                    }
                }
            } else {

                m_rvcmTransport = new TibrvCmQueueTransport(m_rvTransport, rvconfig.getCmqName(),
                        rvconfig.getWorkerWeight(),
                        rvconfig.getWorkerTasks(),
                        rvconfig.getSchedulerWeight(),
                        rvconfig.getSchedulerHeartbeat(),
                        rvconfig.getSchedulerActivation());
            }
            if (m_rvcmTransport == null) {
                throw new Exception ("Could not create the CM Transport...");
            }

            Iterator i = getDestinations().values().iterator();
            while (i.hasNext()) {
                TibRvDestination dest = (TibRvDestination)i.next();
                dest.connect();
            }

            setState(State.CONNECTED);
        }
        this.getLogger().log(Level.INFO,
                ResourceManager.getInstance().formatMessage("channel.connected", this.getURI()));
    }

    public void start(int mode) throws Exception {
        State state = getState();
        this.mode = mode;
        switch (mode) {
            case ChannelManager.ACTIVE_MODE:
                if (state.equals(State.INITIALIZED) || state.equals(State.STOPPED)) {
                    connect();
                }
                if (!(state.equals(State.CONNECTED) || state.equals(State.STARTED))) {
                    throw new Exception("Inconsistent state to start channel: " + state.toString());
                }
                startDestinations();
                sendQueuedMessages();
                setState(State.STARTED);
                break;
            case ChannelManager.PASSIVE_MODE:
                stop();
                break;
            case ChannelManager.SUSPEND_MODE:
                if (isFT) {
                    setState(State.UNINITIALIZED);
                } else {
                    if (state.equals(State.INITIALIZED) || state.equals(State.STOPPED)) {
                         connect();
                    }
                    if (!(state.equals(State.CONNECTED) || state.equals(State.STARTED))) {
                        throw new Exception("Inconsistent state to start channel: " + state.toString());
                    }
                    startDestinations(); // Start destinations in suspented mode
                    sendQueuedMessages(); // Send pending msgs
                    setState(State.STARTED);
                }
                break;
        }



        //Send all pending messages
    }

    private void sendQueuedMessages() throws Exception {
        synchronized(syncObject) {
            Iterator r = this.pendingDeliveryMessages.iterator();
            while (r.hasNext()) {
                TibrvMsg msg = (TibrvMsg)r.next();
                m_rvcmTransport.send(msg);
                r.remove();
            }
        }

    }

    private void startDestinations() throws Exception {
        Iterator i = getDestinations().values().iterator();
        while (i.hasNext()) {
            ((Destination) i.next()).start(mode);
        }

        this.getLogger().log(Level.INFO, ResourceManager.getInstance().formatMessage("channel.started", this.getURI()));
    }


    public void close() throws Exception {


        if (m_rvcmTransport != null) {
            m_rvcmTransport.destroy();
            this.getLogger().log(Level.DEBUG, "CMtransport destroyed....");
            m_rvcmTransport = null;
        }//if

        super.close();
    }

    public void stop() {
        try {
            close();
        }
        catch (Exception e) {
            //e.printStackTrace();
            this.getLogger().log(Level.ERROR, e, null);
        }
        super.stop();

    }

    public void resume() {
        try {
            if (isFT) {
                if (getState() != State.STARTED && getState() != State.STOPPED) {
                    start(ChannelManager.ACTIVE_MODE);
                }
            } else {
                super.resume();
            }
        }
        catch (Exception e) {
            //e.printStackTrace();
            getLogger().log(Level.ERROR, e, null);
        }

    }

    // only destroys TibrvCmTransport for inactive engine, keep TibrvdTransport
/*    public void closeRvcm() throws Exception {

        if (m_rvcmTransport != null) {
            m_rvcmTransport.destroy();
            getLogger().logDebug("CMtransport destroyed....");
            m_rvcmTransport = null;
        }//if
        setState(State.INITIALIZED);
    }
*/
}


