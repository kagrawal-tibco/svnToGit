package com.tibco.cep.driver.tibrv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.channel.impl.AbstractDestination;
import com.tibco.cep.runtime.channel.impl.DefaultSerializationContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AbstractEventContext;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.util.ResourceManager;
import com.tibco.tibrv.TibrvCmListener;
import com.tibco.tibrv.TibrvDispatcher;
import com.tibco.tibrv.TibrvListener;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvMsgCallback;
import com.tibco.tibrv.TibrvQueue;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: hzhang
 * Date: Jul 26, 2006
 * Time: 9:38:41 PM
 * To change this template use File | Settings | File Templates.
 */

public class TibRvDestination extends AbstractDestination<TibRvChannel> {
    ArrayList listeners = new ArrayList ();
    HashMap senderCache = new HashMap();
    String defaultSubjectName = null;
    String preRegistrationName = null;
    List pendingDeliveryMessages = new LinkedList();
    private boolean resuming = false;

    public TibRvDestination(TibRvChannel channel, DestinationConfig config) {
        super(channel, config);
        defaultSubjectName = channel.getGlobalVariables().substituteVariables(config.getProperties().getProperty(TibRvConstants.DESTINATION_PROPERTY_SUBJECT.getLocalName())).toString();
        if (channel instanceof TibRvcmChannel) {
            preRegistrationName = getSubstitutedStringValueForRvcm(config.getProperties().getProperty(TibRvConstants.DESTINATION_PROPERTY_PREREGISTRATION_NAME .getLocalName()), (TibRvcmChannel)channel);
        }
    }

    protected void destroy(RuleSession session) throws Exception {
        Iterator r = listeners.iterator();
        while (r.hasNext()) {
            TibRvListener lsnr = (TibRvListener)r.next();
            if (lsnr.session == session) {
                lsnr.close();
                listeners.remove(lsnr);
                return;
            }
        }
    }

    public void bind(RuleSession session)  {
        try {
            final TibRvListener lsnr = new TibRvListener(this, session);
            this.listeners.add(lsnr);
            //if (this.channel.getState() == Channel.State.STARTED) {
            //    lsnr.start(ChannelManager.ACTIVE_MODE);
            //}
            super.bind(session);
        }
        catch (Exception e) {
            channel.getLogger().log(Level.WARN, e, "Can't bind session %s", session.getName());
        }
    }

    private void printConfig() {
        StringBuffer sb = new StringBuffer();
        Iterator ite = config.getProperties().entrySet().iterator();
        boolean first = true;
        while(ite.hasNext()) {
            Map.Entry e = (Map.Entry) ite.next();
            if (!first) {
                sb.append(" ");
            } else {
                first = false;
            }
            String value = channel.getGlobalVariables().substituteVariables((String)e.getValue()).toString();
            sb.append(e.getKey()).append("=").append(value);
        }
        if (serializer != null) {
        	sb.append(" ").append("Serializer="+ serializer.getClass().getName());
        } else {
        	sb.append(" ").append("Serializer= NOT FOUND");
        }

        this.channel.getLogger().log(Level.INFO,
                ResourceManager.getInstance().formatMessage("channel.destination.config", this.getURI(), sb));
    }

    public void connect() throws Exception {
        if (getChannel() instanceof TibRvcmChannel) {
            TibRvcmChannel channel = (TibRvcmChannel)getChannel();
            if (channel.m_rvcmTransport == null) return;
            if (!channel.isdq) {
                if ((preRegistrationName != null) && (preRegistrationName.length() > 0)) {
                    String[] names = preRegistrationName.split("\\s*,\\s*");
                    for (int i=0; i<names.length; i++) {
                        String nm = names[i].trim();
                        channel.getLogger().log(Level.INFO, "Pre-registering listener with cm name -%s", nm);
                        channel.m_rvcmTransport.addListener(nm,defaultSubjectName);
                    }
                }
            }
        }
    }

    public void start(int mode) throws Exception {
        printConfig();
        final int listenerMode = (ChannelManager.ACTIVE_MODE == mode) && this.userControlled
                ? ChannelManager.SUSPEND_MODE : mode;
        Iterator i = listeners.iterator();
        while (i.hasNext()) {
            TibRvListener l = (TibRvListener) i.next();
            l.start(listenerMode);
            this.channel.getLogger().log(Level.INFO,
                    ResourceManager.getInstance().formatMessage(
                            "channel.destination.listener.started." + listenerMode,
                            this.getURI(),
                            l.session.getName(),
                            "" ));
        }
        //Send all pending messages
        synchronized(syncObject) {
            Iterator r = this.pendingDeliveryMessages.iterator();
            while (r.hasNext()) {
                QueueEntry entry = (QueueEntry)r.next();
                entry.channel.send(entry.msg);
                r.remove();
            }
        }
        super.start(mode);
    }

    public void close () {
        Iterator i = listeners.iterator();
        while (i.hasNext()) {
            TibRvListener l = (TibRvListener) i.next();
            l.close();
            this.channel.getLogger().log(Level.INFO,
                    ResourceManager.getInstance().formatMessage(
                            "channel.destination.close", this.getURI(), l.session.getName() ));
            //i.remove();
        }
    }

    public void stop() {
        Iterator i = listeners.iterator();
        while (i.hasNext()) {
            TibRvListener l = (TibRvListener) i.next();
            l.stop();
            this.channel.getLogger().log(Level.INFO,
                    ResourceManager.getInstance().formatMessage(
                            "channel.destination.listener.stopped", this.getURI(), l.session.getName(), ""));
        }
    }

    public void resume() {
        final Logger logger = this.channel.getLogger();
        if (!this.userControlled) {
            synchronized (this.syncObject) {
                if (!this.resuming) {
                    this.resuming = true;
                    boolean hasErrors = false;  
                    try {
                        // Now starts listeners for input destinations
                        final boolean canLogInfo = logger.isEnabledFor(Level.INFO);
                        for (final Object listenerObject : this.listeners) {
                            final TibRvListener lsnr = (TibRvListener) listenerObject;
                            try {
                                lsnr.start(ChannelManager.ACTIVE_MODE);
                                if (canLogInfo) {
                                    logger.log(Level.INFO,
                                            ResourceManager.getInstance().formatMessage(
                                                    "channel.destination.listener.started." + ChannelManager.ACTIVE_MODE,
                                                    this.getURI(), lsnr.session.getName(), ""));
                                }
                            }
                            catch (Exception e) {
                                hasErrors = true;
                                logger.log(Level.ERROR, "Cannot resume destination %s - %s",
                                        this.getURI(), e.getMessage());
                            }
                        }
                    } finally {
                        this.resuming = false;
                    }
                    this.suspended = hasErrors;
                }
                this.syncObject.notifyAll();
            }
        } else {
            logger.log(Level.DEBUG, "Destination [%s] is suspended. Listener is not started.", this.getURI());
        }
    }

    public int send(SimpleEvent event, Map overrideData) throws Exception {

        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        SerializationContext ctx = new DefaultSerializationContext(session, this);
        TibrvMsg msg = (TibrvMsg) this.serializer.serialize(event, ctx);
        if (msg != null) {
            this.channel.getLogger().log(Level.DEBUG, "Sending RV msg %s", msg);
            String subject = defaultSubjectName;
            if (overrideData != null) {
                subject = (String) overrideData.get(TibRvConstants.DESTINATION_PROPERTY_OVERRIDESUBJECT);
                if ((subject == null) || (subject.length() == 0)) {
                    subject = defaultSubjectName;
                }
            }
            msg.setSendSubject(subject);

            if ((channel.getState() == Channel.State.CONNECTED) || (channel.getState() == Channel.State.STARTED)) {
                channel.send(msg);
            }
            else if (channel.getState() == Channel.State.INITIALIZED) {
                //Queue it for later use and send log info
                //TODO - Fixed Size queue implementation. and Roll off the top
                this.channel.getLogger().log(Level.DEBUG,
                        "Channel Initialized, but not started. Queuing message for later delivery");
                synchronized(syncObject) {
                    QueueEntry entry = new QueueEntry(channel, msg);
                    pendingDeliveryMessages.add(entry);
                }
                return 1;
            }
            else if (channel instanceof TibRvcmChannel) {
                ((TibRvcmChannel)channel).send(msg);
            }
            return super.send(event, overrideData);
        }
        return -1;
    }

    protected String getSubstitutedStringValueForRvcm(String value, TibRvcmChannel channel) {
        if (value == null) {
            return "";
        } else {
            RuleServiceProvider rsProvider = channel.getChannelManager().getRuleServiceProvider();
            value = value.replaceAll("%%EngineName%%", rsProvider.getName().replace(' ', '.').replace('/', '.'));
            value = value.replaceAll("%%ChannelURI%%", channel.getURI().substring(1).replace(' ', '.').replace('/', '.')); // to remove the leading slash
            value = value.replaceAll("%%ChannelName%%", channel.getName().replace(' ', '.').replace('/', '.'));
            return channel.getGlobalVariables().substituteVariables(value).toString();
        }
    }
    
    @Override
	public Object requestEvent(SimpleEvent outevent, ExpandedName responseEventURI, String serializerClass, long timeout, Map overrideData)
			throws Exception {
    	throw new Exception("requestEvent() is not supported with RendezVous Channel");
	}

    class QueueEntry {
        protected TibRvChannel channel;
        protected TibrvMsg msg;
        QueueEntry(TibRvChannel producer, TibrvMsg msg) {
            this.channel = producer;
            this.msg = msg;
        }
    }

    public class TibRvListener implements TibrvMsgCallback {

        final static byte ERROR = -1;
        final static byte INIT = 0;
        final static byte STOPPED = 1;
        final static byte CONNECT = 2;
        final static byte STARTED = 3;
        final static byte SUSPENDED = 4;

        TibRvDestination dest;
        RuleSession session;

        TibrvQueue m_queue;
        TibrvDispatcher m_queueDispatcher;
        TibrvListener m_rvListener;
        byte state = 0;

        TibRvListener (TibRvDestination dest, RuleSession session) throws Exception {
            this.dest = dest;
            this.session = session;
        }

        /**
         * Makes sure the sender is initialized.
         * Creates the TibrvQueue and sets the Limit Policy.
         * @throws Exception
         */
        private void connect() throws Exception{
            if (state > CONNECT) {
                return;
            }

            if (!(m_queue != null && m_queue.isValid())) {
                m_queue = new TibrvQueue();
            }
            Properties ps = config.getProperties();
            final String limitPolicyStr   = channel.getGlobalVariables().substituteVariables(ps.getProperty(TibRvConstants.DESTINATION_PROPERTY_LIMITPOLICY.getLocalName())).toString();
            final String maxEventsStr     = channel.getGlobalVariables().substituteVariables(ps.getProperty(TibRvConstants.DESTINATION_PROPERTY_MAXEVENTS.getLocalName())).toString();
            final String discardAmountStr = channel.getGlobalVariables().substituteVariables(ps.getProperty(TibRvConstants.DESTINATION_PROPERTY_DISCARDAMOUNT.getLocalName())).toString();
            int limitPolicy = TibrvQueue.DISCARD_NONE;
            int maxEvents = 0;
            int discardAmount = 0;
            try {
                if (maxEventsStr.trim().length() != 0) maxEvents = Integer.parseInt(maxEventsStr);
                if (discardAmountStr.trim().length() != 0) discardAmount = Integer.parseInt(discardAmountStr);
                if ((0 == maxEvents) || (0 == discardAmount)) {
                    throw new Exception();
                } else if ("DISCARD_FIRST".equalsIgnoreCase(limitPolicyStr)) {
                    limitPolicy = TibrvQueue.DISCARD_FIRST;
                } else if ("DISCARD_LAST".equalsIgnoreCase(limitPolicyStr)) {
                    limitPolicy = TibrvQueue.DISCARD_LAST;
                } else if ("DISCARD_NEW".equalsIgnoreCase(limitPolicyStr)) {
                    limitPolicy = TibrvQueue.DISCARD_NEW;
                } else {
                    throw new Exception();
                }//else
            } catch (Exception e) {
                // Falls back to DISCARD_NONE if maxEvents or discardAmount is zero, or if limitPolicy is DISCARD_NONE,
                // or if a value is not understood.
                limitPolicy = TibrvQueue.DISCARD_NONE;
                maxEvents = 0;
                discardAmount = 0;
            }//catch

            final String subject = defaultSubjectName;
            m_queue.setLimitPolicy(limitPolicy, maxEvents, discardAmount);
            if (channel instanceof TibRvcmChannel) {
                m_rvListener = new TibrvCmListener(m_queue, this, ((TibRvcmChannel)channel).m_rvcmTransport, subject, null);
                ((TibrvCmListener)m_rvListener).setExplicitConfirm();
            } else {
                m_rvListener = new TibrvListener(m_queue, this, channel.m_rvTransport, subject, null);
            }
            state = CONNECT;

        }//connect

        /**
         * Creates and starts the TibrvDispatcher if the Destination is connected.
         * @throws Exception
         */
        protected void start(int mode) throws Exception {

            if (mode == ChannelManager.ACTIVE_MODE) {
                connect();
                if (state == STARTED) {
                    return;
                } else if (state == SUSPENDED) {
                    dest.resume();
                    state = STARTED;
                    return;
                }
                m_queueDispatcher = new TibrvDispatcher(m_queue);
                state = STARTED;
            }
            else if (mode == ChannelManager.PASSIVE_MODE) {

            }
            else if (mode == ChannelManager.SUSPEND_MODE) {
                connect();
                dest.suspend();
                m_queueDispatcher = new TibrvDispatcher(m_queue);
                state = SUSPENDED;

            }
        }//start

        protected void close()  {
            stop();
            if (null != m_rvListener) {
                m_rvListener.destroy();
                m_rvListener = null;
                m_queue.destroy();
            }//if

            channel.getLogger().log(Level.DEBUG,
                    ResourceManager.getInstance().formatMessage(
                            "be.channel.listener.closed", channel.getURI() + "/" + getURI()));
        }//close

        protected void stop() {
            if (m_queueDispatcher != null) {
                m_queueDispatcher.destroy();

            }//if
            state = STOPPED;
        }

        public void onMsg(TibrvListener tibrvListener, TibrvMsg message){
            try {
                channel.getLogger().log(Level.DEBUG, "%s - Received RV msg %s", session.getName(), message);
                MessageContext ctx = new MessageContext(tibrvListener, message, dest);
                onMessage(session, ctx);

            } catch (Exception e) {
                channel.getLogger().log(Level.WARN, e, "%s - Failed to receive RV msg %s", session.getName(), message);
            }
        }
    }

    class MessageContext extends AbstractEventContext {
        TibrvListener listener;
        TibrvMsg msg;
        TibRvDestination dest;

        MessageContext(TibrvListener listener, TibrvMsg msg, TibRvDestination dest) {
            this.listener = listener;
            this.msg = msg;
            this.dest = dest;
        }
        public void acknowledge() {
            try {
                if (listener instanceof TibrvCmListener) {
                    ((TibrvCmListener) listener).confirmMsg(msg);
                }
            } catch (Exception e) {
                channel.getLogger().log(Level.WARN, e, "Can't acknowledge rvcm msg [%s]", this.msg);
            }
        }

        public boolean reply(SimpleEvent replyEvent) {
            try {
                String subject = msg.getReplySubject();
                if (subject != null) {
                    Map override = new HashMap();
                    override.put(TibRvConstants.DESTINATION_PROPERTY_OVERRIDESUBJECT, subject);
                    dest.send(replyEvent, override);
                    return true;
                }
            }
            catch (Exception e) {
                channel.getLogger().log(Level.WARN, e, "Can't reply rvcm msg [%s]", this.msg);
            }
            return false;
        }

        public boolean canReply() {
            return (null != this.msg.getReplySubject());
        }

        /**
         *
         * @return
         */
        public Channel.Destination getDestination() {
            return dest;
        }

        /**
         *
         * @return
         */
        public Object getMessage() {
        	return msg;
        }

        @Override
		public String replyImmediate(SimpleEvent replyEvent) {
			reply(replyEvent);
			return null;
		}
    }
}
