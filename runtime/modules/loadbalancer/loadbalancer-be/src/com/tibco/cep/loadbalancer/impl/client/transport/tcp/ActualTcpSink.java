package com.tibco.cep.loadbalancer.impl.client.transport.tcp;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.driver.ancillary.api.Reader;
import com.tibco.cep.driver.ancillary.api.Session;
import com.tibco.cep.driver.ancillary.api.SessionManager.SessionListener;
import com.tibco.cep.driver.ancillary.api.Writer;
import com.tibco.cep.driver.ancillary.tcp.WriterImpl;
import com.tibco.cep.driver.ancillary.tcp.server.ServerSession;
import com.tibco.cep.driver.ancillary.tcp.server.TCPServer;
import com.tibco.cep.driver.ancillary.tcp.server.TCPServer.Parameters;
import com.tibco.cep.loadbalancer.client.core.ActualSinkStatus;
import com.tibco.cep.loadbalancer.impl.client.core.AbstractActualSink;
import com.tibco.cep.loadbalancer.impl.client.transport.ActualSinkProperty;
import com.tibco.cep.loadbalancer.impl.message.DefaultDistributionStrategy;
import com.tibco.cep.loadbalancer.impl.message.SimpleDistributionResponse;
import com.tibco.cep.loadbalancer.impl.transport.*;
import com.tibco.cep.loadbalancer.impl.transport.tcp.AbstractTcpReaderListener;
import com.tibco.cep.loadbalancer.impl.transport.tcp.TcpSocketProperty;
import com.tibco.cep.loadbalancer.message.DistributableMessage;
import com.tibco.cep.loadbalancer.message.DistributableMessage.KnownHeader;
import com.tibco.cep.loadbalancer.message.MessageCodec;
import com.tibco.cep.loadbalancer.util.Helper;
import com.tibco.cep.loadbalancer.util.Utils;
import com.tibco.cep.runtime.channel.impl.AbstractChannel;
import com.tibco.cep.runtime.channel.impl.AbstractDestination;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AbstractEventContext;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.util.annotation.Copy;
import com.tibco.cep.util.annotation.LogCategory;

import java.io.OutputStream;
import java.net.BindException;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

/*
* Author: Ashwin Jayaprakash / Date: Mar 25, 2010 / Time: 1:18:40 PM
*/

@LogCategory("loadbalancer.be.client.transport.tcp.receiver")
public class ActualTcpSink extends AbstractActualSink implements SessionListener {
    /**
     * {@value}
     */
    public static final String DEFAULT_HOSTNAME = "localhost";

    /**
     * {@value} The default starting number which will be used to then create a random port number.
     */
    public static final int DEFAULT_PORT_START = 41023;

    protected ConcurrentHashMap<String, InternalSessionWorker> sessionWorkers;

    protected TCPServer tcpServer;

    protected boolean started;

    protected EnumMap<TcpSocketProperty, String> socketPropertyMap;

    protected RuleSession ruleSession;

    protected AbstractChannel channel;

    protected AbstractDestination destination;

    protected MessageReceiver messageReceiver;

    protected ConcurrentHashMap<Object, PendingAckContainer> senderIdAndPendingAckContainers;

    protected InternalActualSinkStatus actualSinkStatus;

    protected boolean debugOn;

    protected long likelyDupDeliveryAfterChangeMillis;

    protected Map<Object, Object> propertyGroupConfig;

    public ActualTcpSink() {
        this.actualSinkStatus = new InternalActualSinkStatus();
    }

    public ActualTcpSink(Id id) {
        super(id);

        this.actualSinkStatus = new InternalActualSinkStatus();
    }

    @Override
    public void setResourceProvider(ResourceProvider resourceProvider) {
        super.setResourceProvider(resourceProvider);

        debugOn = logger.isLoggable(Level.FINE);
    }

    @Override
    public void setAdditionalProperties(Map<?, ?> properties) {
        this.ruleSession = (RuleSession) properties.get(ActualSinkProperty.RuleSession);
        this.channel = (AbstractChannel) properties.get(ActualSinkProperty.AbstractChannel);
        this.destination = (AbstractDestination) properties.get(ActualSinkProperty.AbstractDestination);
        this.messageReceiver = (MessageReceiver) properties.get(ActualSinkProperty.MessageReceiver);
        this.propertyGroupConfig = (Map<Object, Object>) properties.get(ActualSinkProperty.ConfigProperties);

        //---------------

        Object v = properties.get(ActualSinkProperty.FlagLikelyDupDeliveryAfterChangeMillis);
        if (v == null) {
            v = ActualSinkProperty.FlagLikelyDupDeliveryAfterChangeMillis.getDefaultValue();
        }
        this.likelyDupDeliveryAfterChangeMillis = (Long) v;
    }

    @Override
    public Properties getProperties() {
        Properties p = new Properties();

        for (Entry<TcpSocketProperty, String> entry : socketPropertyMap.entrySet()) {
            TcpSocketProperty key = entry.getKey();
            String value = entry.getValue();

            if (value != null) {
                p.put(key.name().toLowerCase(), value);
            }
        }

        p.put(TransportConstants.NAME_TRANSPORT, TransportConfig.tcp.name());

        return p;
    }

    @Override
    public void start() throws LifecycleException {
        if (started) {
            return;
        }

        //---------------

        String sinkName = id.toString();
        setDistributionStrategy(new DefaultDistributionStrategy(sinkName));

        //---------------

        handleSocketProperties();

        //---------------

        String hostName = socketPropertyMap.get(TcpSocketProperty.hostname);
        String portStr = socketPropertyMap.get(TcpSocketProperty.port);

        int port = -1;
        try {
            port = Integer.parseInt(portStr);
        }
        catch (NumberFormatException e) {
            String msg = String.format("Error occurred while starting [%s] because the value [%s]" +
                    " provided for property [%s] is invalid", getId(), portStr, TcpSocketProperty.port.name());

            throw new IllegalArgumentException(msg, e);
        }

        //--------------

        for (; ; ) {
            String msg = String.format(
                    "Applied property [%s:%d] to the Actual TCP Sink %s@%s:%s",
                    ActualSinkProperty.FlagLikelyDupDeliveryAfterChangeMillis.name(),
                    this.likelyDupDeliveryAfterChangeMillis,
                    ActualTcpSink.this.getId(),
                    socketPropertyMap.get(TcpSocketProperty.hostname),
                    socketPropertyMap.get(TcpSocketProperty.port));

            logger.log(Level.FINE, msg);

            //--------------

            sessionWorkers = new ConcurrentHashMap<String, InternalSessionWorker>();

            senderIdAndPendingAckContainers = new ConcurrentHashMap<Object, PendingAckContainer>();

            String name = hostName + "." + port + "." + Helper.$hashMD5(id);
            Parameters tcpServerParams = new Parameters(name, hostName, port);

            tcpServer = new TCPServer();
            tcpServer.setListener(this);

            try {
                tcpServer.init(null, tcpServerParams);
                tcpServer.start();

                break;
            }
            catch (BindException be) {
                port++;

                socketPropertyMap.put(TcpSocketProperty.port, port + "");

                logger.log(Level.FINE,
                        String.format("TCP port [%s:%d] is already in use. Actual TCP Sink [%s] will try the next available port",
                                hostName, port, getId()), be);
            }
            catch (Exception e) {
                throw new LifecycleException(e);
            }
        }

        started = true;

        logger.log(Level.FINE, String.format("Started Actual TCP Sink %s@%s:%d", getId(), hostName, port));
    }

    private void handleSocketProperties() {
        socketPropertyMap = new EnumMap<TcpSocketProperty, String>(TcpSocketProperty.class);

        for (TcpSocketProperty socketProperty : TcpSocketProperty.values()) {
            String value = (String) propertyGroupConfig.get(socketProperty.name());

            if (value == null && socketProperty.isMandatory()) {
                if (socketProperty == TcpSocketProperty.hostname) {
                    value = DEFAULT_HOSTNAME;

                    String msg = String.format(
                            "Applied default value [%s] to property [%s] in the Actual TCP Sink %s",
                            value, TcpSocketProperty.hostname.name(), ActualTcpSink.this.getId());

                    logger.log(Level.FINE, msg);
                }
                else if (socketProperty == TcpSocketProperty.port) {
                    Random random = new Random();
                    int port = DEFAULT_PORT_START + random.nextInt((62500 - DEFAULT_PORT_START));

                    value = Integer.toString(port);

                    String msg = String.format(
                            "Applied random value [%s] to property [%s] in the Actual TCP Sink %s",
                            value, TcpSocketProperty.port.name(), ActualTcpSink.this.getId());

                    logger.log(Level.FINE, msg);
                }
                else {
                    throw new IllegalArgumentException(
                            String.format("Error occurred while starting [%s] because the mandatory property" +
                                    " [%s] has not been provided", getId(), socketProperty.name()));
                }
            }

            socketPropertyMap.put(socketProperty, value);
        }
    }

    @Override
    public void stop() throws LifecycleException {
        try {
            tcpServer.stop();
        }
        catch (Exception e) {
            logger.log(Level.WARNING, String.format("Error occured while stopping Actual TCP Sink %s@%s:%s", getId(),
                    socketPropertyMap.get(TcpSocketProperty.hostname),
                    socketPropertyMap.get(TcpSocketProperty.port)));
        }
        tcpServer = null;

        sessionWorkers.clear();
        sessionWorkers = null;

        actualSinkStatus.reset();
    }

    //------------------

    @Override
    public void onNewSession(Session session) throws Exception {
        try {
            MessageCodec messageCodec = TransportConfig.tcp.getMessageCodecClass().newInstance();

            ClassLoader classLoader = Utils.$findRspClassLoader();
            messageCodec.setClassLoader(classLoader);

            InternalSessionWorker sessionWorker = new InternalSessionWorker(session, (StreamMessageCodec) messageCodec);

            sessionWorkers.put(session.getId(), sessionWorker);
        }
        catch (InstantiationException e) {
            throw new Exception(e);
        }
        catch (IllegalAccessException e) {
            throw new Exception(e);
        }
    }

    /**
     * @param sessionWorker
     * @param helloMessage
     * @return An existing container from a previous session or a new one. If it was from a previous session, then pending acks
     *         and nacks must be processed.
     */
    protected PendingAckContainer onHelloMessage(InternalSessionWorker sessionWorker, HelloMessage helloMessage) {
        String msg =
                String.format("Received a hello message [%s] in the server session [%s] in %s@%s:%s from [%s]",
                        helloMessage.getUniqueId(), sessionWorker.transportSession.getId(),
                        ActualTcpSink.this.getId(),
                        socketPropertyMap.get(TcpSocketProperty.hostname),
                        socketPropertyMap.get(TcpSocketProperty.port),
                        helloMessage.getSenderId());

        logger.log(Level.FINE, msg);

        //------------------

        Object senderId = helloMessage.getSenderId();

        PendingAckContainer ackContainer = senderIdAndPendingAckContainers.get(senderId);

        if (ackContainer == null) {
            ackContainer = new PendingAckContainer();
            senderIdAndPendingAckContainers.put(senderId, ackContainer);
        }

        //todo There could be some old PendingAckContainers. Who cleans them up? Mem leak?

        return ackContainer;
    }

    protected void onReceiveItselfFailed(Object originalSenderId, AckHandle ackHandle) {
        PendingAckContainer pendingAckContainer = senderIdAndPendingAckContainers.get(originalSenderId);
        if (pendingAckContainer == null) {
            //Ignore. Error will be logged in detail by caller any way.

            return;
        }

        Object uniqueIdOfMessage = ackHandle.getUniqueIdOfMessage();

        pendingAckContainer.removeFromPendingAcks(uniqueIdOfMessage);
        pendingAckContainer.addToNegAcksToSend(uniqueIdOfMessage, ackHandle);
    }

    protected void onAckReceivedButNotSent(
            Object originalSenderId,
            Object uniqueIdOfMessage,
            boolean positiveAck)
    {
        PendingAckContainer pendingAckContainer = senderIdAndPendingAckContainers.get(originalSenderId);
        if (pendingAckContainer == null) {
            String msg = String.format(
                    "Server %s@%s:%s received %s for message [%s] that was originally sent by [%s]." +
                            " This sender does not exist anymore. Acknowledgement cannot be relayed.",
                    ActualTcpSink.this.getId(),
                    socketPropertyMap.get(TcpSocketProperty.hostname),
                    socketPropertyMap.get(TcpSocketProperty.port),
                    (positiveAck ? "acknowledgement" : "roll back"),
                    uniqueIdOfMessage,
                    originalSenderId);

            logger.log(Level.SEVERE, msg);

            return;
        }

        AckHandle ackHandle = pendingAckContainer.removeFromPendingAcks(uniqueIdOfMessage);
        if (ackHandle != null) {
        	if(positiveAck) {
        		pendingAckContainer.addToReceivedButUnsentAcks(uniqueIdOfMessage, ackHandle);
        	} else {
        		pendingAckContainer.addToNegAcksToSend(uniqueIdOfMessage, ackHandle);
        	}
        }
        pendingAckContainer.setLastSuspectedAtMillis(System.currentTimeMillis());
    }

    //todo Even if a connection is dead/dormant?

    @Override
    public void removeSession(Session session) {
        InternalSessionWorker sessionWorker = sessionWorkers.remove(session.getId());
        if (sessionWorker == null) {
            return;
        }

        PendingAckContainer pendingAckContainer = sessionWorker.getPendingAckContainer();

        //------------------

        ConcurrentHashMap<Object, AckHandle> pendingAcks = pendingAckContainer.getPendingAcks();
        int x = 0;
        for (Iterator<Entry<Object, AckHandle>> iter = pendingAcks.entrySet().iterator();
             iter.hasNext(); ) {
            Entry<Object, AckHandle> entry = iter.next();

            AckHandle ackHandle = entry.getValue();
            //Disconnect from the now dead session.
            ackHandle.setCurrentSessionWorker(null);

            x++;

            iter.remove();
        }

        //------------------

        ConcurrentHashMap<Object, AckHandle> receivedButUnsentAcks = pendingAckContainer.getReceivedButUnsentAcks();
        int y = 0;
        for (Iterator<Entry<Object, AckHandle>> iter = receivedButUnsentAcks.entrySet().iterator();
             iter.hasNext(); ) {
            Entry<Object, AckHandle> entry = iter.next();

            AckHandle ackHandle = entry.getValue();
            //Disconnect from the now dead session.
            ackHandle.setCurrentSessionWorker(null);

            y++;

            iter.remove();
        }

        //------------------

        ConcurrentHashMap<Object, AckHandle> negAcksToSend = pendingAckContainer.getNegAcksToSend();
        int z = 0;
        for (Iterator<Entry<Object, AckHandle>> iter = negAcksToSend.entrySet().iterator();
             iter.hasNext(); ) {
            Entry<Object, AckHandle> entry = iter.next();

            entry.getValue();

            z++;

            iter.remove();
        }

        //------------------

        pendingAckContainer.setLastSuspectedAtMillis(System.currentTimeMillis());

        sessionWorker.discard();

        String msg = String.format(
                "Server session [%s] in %s@%s:%s has shut down." +
                        " There are [%d] pending acknowledgements," +
                        " [%d] application acknowledged but unrelayed acknowledgements" +
                        " and [%d] pending negative acknowledgements.",
                session.getId(), ActualTcpSink.this.getId(),
                socketPropertyMap.get(TcpSocketProperty.hostname),
                socketPropertyMap.get(TcpSocketProperty.port), x, y, z);

        logger.log(Level.FINE, msg);
    }

    protected void onMembershipChangeMessage(InternalSessionWorker sessionWorker,
                                             MembershipChangeMessage changeMessage) {
        String msg =
                String.format(
                        "Received a membership change message [%s:%s] in the server session [%s] in %s@%s:%s from [%s]",
                        changeMessage.getUniqueId(), changeMessage.isJoining(),
                        sessionWorker.transportSession.getId(), ActualTcpSink.this.getId(),
                        socketPropertyMap.get(TcpSocketProperty.hostname),
                        socketPropertyMap.get(TcpSocketProperty.port),
                        changeMessage.getSenderId());

        logger.log(Level.FINE, msg);

        //------------------

        if (changeMessage.isJoining()) {
            actualSinkStatus.addMemberId(changeMessage.getMemberId());
        }
        else {
            actualSinkStatus.removeMemberId(changeMessage.getMemberId());
        }
    }

    //------------------

    protected class InternalSessionWorker extends AbstractTcpReaderListener {
        protected Reader reader;

        protected Writer writer;

        protected Session transportSession;

        protected PendingAckContainer pendingAckContainer;

        protected HelloMessage receivedHello;

        public InternalSessionWorker(Session transportSession, StreamMessageCodec messageCodec) {
            super(transportSession.getId(), messageCodec, ActualTcpSink.this.logger);

            this.transportSession = transportSession;
            applysocketProperties(transportSession);

            this.reader = transportSession.getReader();
            this.reader.setOptionalListener(this);

            this.writer = transportSession.getWriter();
        }

        private void applysocketProperties(Session session) {
            ServerSession serverSession = (ServerSession) session;
            Socket socket = serverSession.getSocket();

            for (Entry<TcpSocketProperty, String> entry : socketPropertyMap.entrySet()) {
                TcpSocketProperty key = entry.getKey();
                String value = entry.getValue();

                try {
                    String result = key.applyProperty(socket, value);

                    if (result != null) {
                        String msg = String.format(
                                "Applied property [%s:%s] to the server session [%s] in %s@%s:%s",
                                key.name(), result, session.getId(),
                                ActualTcpSink.this.getId(),
                                socketPropertyMap.get(TcpSocketProperty.hostname),
                                socketPropertyMap.get(TcpSocketProperty.port));

                        logger.log(Level.FINE, msg);
                    }
                }
                catch (SocketException e) {
                    String msg = String.format(
                            "Error occurred while applying property [%s] to the server session [%s] in %s@%s:%s",
                            key.name(), session.getId(),
                            ActualTcpSink.this.getId(),
                            socketPropertyMap.get(TcpSocketProperty.hostname),
                            socketPropertyMap.get(TcpSocketProperty.port));

                    logger.log(Level.WARNING, msg, e);
                }
            }
        }

        /**
         * Non-null only after {@link ActualTcpSink#onHelloMessage(InternalSessionWorker, HelloMessage)}
         *
         * @return
         */
        public PendingAckContainer getPendingAckContainer() {
            return pendingAckContainer;
        }

        @Override
        protected void onMessage(Object message) {
            if (message instanceof DistributableMessage) {
                onDistributableMessage((DistributableMessage) message);
            }
            else if (message instanceof HelloMessage) {
                receivedHello = (HelloMessage) message;

                pendingAckContainer = ActualTcpSink.this.onHelloMessage(this, receivedHello);

                pendingAckContainer.setLastWorkedAtMillis(System.currentTimeMillis());

                processAcksFromPrevSession(pendingAckContainer);
            }
            else if (message instanceof MembershipChangeMessage) {
                pendingAckContainer.setLastWorkedAtMillis(System.currentTimeMillis());

                ActualTcpSink.this.onMembershipChangeMessage(this, (MembershipChangeMessage) message);
            }
        }

        protected void processAcksFromPrevSession(PendingAckContainer pendingAckContainer) {
            ConcurrentHashMap<Object, AckHandle> pendingAcks = pendingAckContainer.getPendingAcks();

            if (pendingAcks.size() > 0) {
                String msg = String.format("Server session [%s] in %s@%s:%s has claimed ownership of [%d] pending" +
                        " acknowledgements from a previous session.",
                        transportSession.getId(), ActualTcpSink.this.getId(),
                        socketPropertyMap.get(TcpSocketProperty.hostname),
                        socketPropertyMap.get(TcpSocketProperty.port), pendingAcks.size());

                logger.log(Level.WARNING, msg);

                for (Entry<Object, AckHandle> entry : pendingAcks.entrySet()) {
                    AckHandle ackHandle = entry.getValue();

                    //Take ownership.
                    ackHandle.setCurrentSessionWorker(this);
                }
            }

            //------------------

            ConcurrentHashMap<Object, AckHandle> receivedButUnsentAcks = pendingAckContainer.getReceivedButUnsentAcks();

            if (receivedButUnsentAcks.size() > 0) {
                String msg = String.format("Server session [%s] in %s@%s:%s has claimed ownership of [%d] " +
                        " acknowledgements that were received from a previous session but not sent. They will be sent now.",
                        transportSession.getId(), ActualTcpSink.this.getId(),
                        socketPropertyMap.get(TcpSocketProperty.hostname),
                        socketPropertyMap.get(TcpSocketProperty.port), receivedButUnsentAcks.size());

                logger.log(Level.WARNING, msg);

                for (Entry<Object, AckHandle> entry : receivedButUnsentAcks.entrySet()) {
                    AckHandle ackHandle = entry.getValue();

                    //Take ownership.
                    ackHandle.setCurrentSessionWorker(this);

                    sendAck(ackHandle, true);
                }
            }

            //------------------

            ConcurrentHashMap<Object, AckHandle> negAcksToSend = pendingAckContainer.getNegAcksToSend();

            if (negAcksToSend.size() > 0) {
                String msg = String.format("Server session [%s] in %s@%s:%s has claimed ownership of [%d] " +
                        " negative acknowledgements that were accumulated in a previous session.",
                        transportSession.getId(), ActualTcpSink.this.getId(),
                        socketPropertyMap.get(TcpSocketProperty.hostname),
                        socketPropertyMap.get(TcpSocketProperty.port), negAcksToSend.size());

                logger.log(Level.WARNING, msg);

                for (Entry<Object, AckHandle> entry : negAcksToSend.entrySet()) {
                	AckHandle ackHandle = entry.getValue();

                	//Take ownership.
                    ackHandle.setCurrentSessionWorker(this);

                    sendAck(ackHandle, false);
                }
            }
        }

        protected void onDistributableMessage(DistributableMessage distributableMessage) {
            long time = System.currentTimeMillis();
            Object uniqueIdOfMessage = distributableMessage.getUniqueId();
            AckHandle ackHandle = null;

            try {
                Object eventContextOrEvent = prepareForDelivery(distributableMessage);

                //------------------

                if (eventContextOrEvent instanceof SimpleEvent) {
                    messageReceiver.receive(ruleSession, (SimpleEvent) eventContextOrEvent);
                }
                else {
                    Boolean ackExpected = (Boolean) distributableMessage.getHeaderValue(KnownHeader.__ack_expected__);
                    if (ackExpected) {
                        ackHandle = new AckHandle(uniqueIdOfMessage, receivedHello.getSenderId(), sessionId, time,
                                ActualTcpSink.this);

                        ackHandle.setCurrentSessionWorker(this);

                        ((AbstractEventContext) eventContextOrEvent).setOptionalAckInterceptor(ackHandle);

                        pendingAckContainer.addToPendingAcks(uniqueIdOfMessage, ackHandle);
                    }

                    //------------------

                    messageReceiver.receive(ruleSession, (AbstractEventContext) eventContextOrEvent, ackExpected);
                }

                pendingAckContainer.setLastWorkedAtMillis(time);
            }
            catch (Throwable t) {
                String msg = String.format("Error occurred while processing incoming message" +
                        " in the server session [%s] in %s@%s:%s",
                        transportSession.getId(), ActualTcpSink.this.getId(),
                        socketPropertyMap.get(TcpSocketProperty.hostname),
                        socketPropertyMap.get(TcpSocketProperty.port));

                if (ackHandle != null) {
                    onReceiveItselfFailed(receivedHello.getSenderId(), ackHandle);

                    msg = msg + ". A request for message [" + uniqueIdOfMessage +
                            "] has been queued for a redelivery request.";
                }

                logger.log(Level.SEVERE, msg, t);
            }
        }

        /**
         * @param distributableMessage
         * @return {@link AbstractEventContext} or {@link SimpleEvent}
         * @throws Exception
         */
        protected Object prepareForDelivery(DistributableMessage distributableMessage) throws Exception {
            long lastAt = actualSinkStatus.getRecentMembershipChangeAt();
            long duration = System.currentTimeMillis() - lastAt;
            if (duration <= likelyDupDeliveryAfterChangeMillis) {
                distributableMessage.setHeaderValue(KnownHeader.__likely_dup_delivery__, true);

                if (debugOn) {
                    String msg = String.format("Flag [%s] has been set on incoming message" +
                            " in the server session [%s] in %s@%s:%s",
                            KnownHeader.__likely_dup_delivery__.name(),
                            transportSession.getId(), ActualTcpSink.this.getId(),
                            socketPropertyMap.get(TcpSocketProperty.hostname),
                            socketPropertyMap.get(TcpSocketProperty.port));

                    logger.log(Level.FINE, msg);
                }
            }

            if (messageCustomizer != null) {
                distributableMessage = messageCustomizer.customize(distributableMessage);
            }

            Object message = distributableMessage.getContent();

            Object retVal = messageReceiver.unpackAsEventOrEventContext(message);

            //------------------

            if (debugOn) {
                String messageAsStr = null;

                try {
                    messageAsStr = distributableMessage.toString();
                }
                catch (Exception e) {
                    //Ignore.
                }

                String msg = String.format("Received message [%s : %s : %s : %s]%n{{%n%s%n}}",
                        ruleSession.getName(), channel.getName(), destination.getName(), getClass().getSimpleName(),
                        messageAsStr);

                logger.log(Level.FINE, msg);
            }

            return retVal;
        }

        protected void sendAck(
                AckHandle ackHandle,
                boolean positiveAck)
        {
            Object uniqueIdOfMessage = ackHandle.getUniqueIdOfMessage();

            //Remove with the hope that this is expected.
            AckHandle existingAckHandle = pendingAckContainer.removeFromPendingAcks(uniqueIdOfMessage);
            if (existingAckHandle == null) {
                //We were not even expecting this ack.

                String msg = String.format(
                        "Ignoring acknowledgement of message [%s] received by server session [%s] in %s@%s:%s",
                        uniqueIdOfMessage, transportSession.getId(), ActualTcpSink.this.getId(),
                        socketPropertyMap.get(TcpSocketProperty.hostname),
                        socketPropertyMap.get(TcpSocketProperty.port));

                logger.log(Level.WARNING, msg);

                return;
            }

            //------------------

            final SimpleDistributionResponse response = new SimpleDistributionResponse(uniqueIdOfMessage, positiveAck);

            try {
                if (debugOn) {
                    String msg = String.format(
                            "Attempting to send acknowledgement for message [%s] received by server session [%s] in %s@%s:%s",
                            uniqueIdOfMessage, transportSession.getId(), ActualTcpSink.this.getId(),
                            socketPropertyMap.get(TcpSocketProperty.hostname),
                            socketPropertyMap.get(TcpSocketProperty.port));

                    logger.log(Level.FINE, msg);
                }

                WriterImpl tcpWriter = (WriterImpl) writer;

                OutputStream outputStream = tcpWriter.getOutputStream();

                synchronized (outputStream) {
                    messageCodec.write(response, outputStream);
                }

                pendingAckContainer.setLastWorkedAtMillis(System.currentTimeMillis());

                messageReceiver.ackSent(ackHandle);
            }
            catch (Exception e) {
                String msg = String.format(
                        "Error occured while sending acknowledgement for message [%s] received by server session [%s] in %s@%s:%s",
                        uniqueIdOfMessage, transportSession.getId(), ActualTcpSink.this.getId(),
                        socketPropertyMap.get(TcpSocketProperty.hostname),
                        socketPropertyMap.get(TcpSocketProperty.port));

                logger.log(Level.SEVERE, msg, e);

                //Put it back.
                pendingAckContainer.addToPendingAcks(uniqueIdOfMessage, ackHandle);

                ActualTcpSink.this.onAckReceivedButNotSent(ackHandle.getOriginalSenderId(), ackHandle, positiveAck);
            }
        }

        @Override
        public void onException(Exception e) {
            try {
                //Stop the session here and let the client reconnect and re-establish the connection.

                String msg = String.format("Error occurred in the server session [%s] in %s@%s:%s."
                        + " This session will be disconnected to avoid further errors.",
                        transportSession.getId(), ActualTcpSink.this.getId(),
                        socketPropertyMap.get(TcpSocketProperty.hostname),
                        socketPropertyMap.get(TcpSocketProperty.port));

                if (logger.isLoggable(Level.FINE)) {
                    logger.log(Level.FINE, msg, e);
                }
                else {
                    logger.log(Level.WARNING, msg);
                }

                transportSession.stop();
            }
            catch (Exception e1) {
                String msg = String.format("Error occurred while stopping the server session [%s] in %s@%s:%s",
                        transportSession.getId(), ActualTcpSink.this.getId(),
                        socketPropertyMap.get(TcpSocketProperty.hostname),
                        socketPropertyMap.get(TcpSocketProperty.port));

                logger.log(Level.WARNING, msg, e1);
            }
        }

        @Override
        public void onEnd() {
            removeSession(transportSession);
        }

        @Override
        public void discard() {
            super.discard();

            reader = null;
            writer = null;
            transportSession = null;

            pendingAckContainer = null;
        }
    }

    //------------------

    protected class InternalActualSinkStatus implements ActualSinkStatus {
        protected volatile long lastMembershipChangeAt;

        protected HashSet<Object> lastKnownMemberIds;

        public InternalActualSinkStatus() {
            this.lastMembershipChangeAt = System.currentTimeMillis();
            this.lastKnownMemberIds = new HashSet<Object>();
        }

        protected synchronized void addMemberId(Object memberId) {
            lastKnownMemberIds.add(memberId);
            lastMembershipChangeAt = System.currentTimeMillis();
        }

        protected synchronized void removeMemberId(Object memberId) {
            lastKnownMemberIds.remove(memberId);
            lastMembershipChangeAt = System.currentTimeMillis();
        }

        @Override
        public long getRecentMembershipChangeAt() {
            return lastMembershipChangeAt;
        }

        @Copy
        @Override
        public synchronized Collection<Object> getLastKnownMemberIds() {
            return new LinkedList<Object>(lastKnownMemberIds);
        }

        protected synchronized void reset() {
            lastMembershipChangeAt = System.currentTimeMillis();
            lastKnownMemberIds = new HashSet<Object>();
        }
    }
}
