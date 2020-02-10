package com.tibco.cep.loadbalancer.impl.server.transport.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.EnumMap;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.driver.ancillary.api.Reader;
import com.tibco.cep.driver.ancillary.tcp.WriterImpl;
import com.tibco.cep.driver.ancillary.tcp.client.TCPClient;
import com.tibco.cep.driver.ancillary.tcp.client.TCPClient.Parameters;
import com.tibco.cep.impl.common.resource.DefaultId;
import com.tibco.cep.impl.service.DefaultThreadFactory;
import com.tibco.cep.loadbalancer.impl.message.DefaultMessageHandle;
import com.tibco.cep.loadbalancer.impl.message.DistributableJmsMessage;
import com.tibco.cep.loadbalancer.impl.message.SimpleDistributionResponse;
import com.tibco.cep.loadbalancer.impl.server.core.sink.AbstractSink;
import com.tibco.cep.loadbalancer.impl.server.core.sink.DefaultSinkStateSnapshot;
import com.tibco.cep.loadbalancer.impl.server.integ.ForwardingJmsDestination;
import com.tibco.cep.loadbalancer.impl.transport.HelloMessage;
import com.tibco.cep.loadbalancer.impl.transport.StreamMessageCodec;
import com.tibco.cep.loadbalancer.impl.transport.tcp.AbstractTcpReaderListener;
import com.tibco.cep.loadbalancer.impl.transport.tcp.TcpSocketProperty;
import com.tibco.cep.loadbalancer.message.DistributableMessage;
import com.tibco.cep.loadbalancer.message.DistributableMessage.KnownHeader;
import com.tibco.cep.loadbalancer.message.MessageHandle;
import com.tibco.cep.loadbalancer.message.SpecialMessage;
import com.tibco.cep.loadbalancer.server.ServerAdmin;
import com.tibco.cep.loadbalancer.server.core.sink.SinkException;
import com.tibco.cep.loadbalancer.server.core.sink.SinkState;
import com.tibco.cep.loadbalancer.server.core.sink.SinkStateSnapshot;
import com.tibco.cep.loadbalancer.util.Helper;
import com.tibco.cep.loadbalancer.util.Utils;
import com.tibco.cep.util.annotation.LogCategory;

/*
* Author: Ashwin Jayaprakash / Date: Mar 25, 2010 / Time: 1:16:32 PM
*/
@LogCategory("loadbalancer.be.server.transport.tcp.sender")
public class TcpSink extends AbstractSink {
    protected TCPClient tcpClient;

    protected ExecutorService executorService;

    protected ResponseReceiver responseReceiver;

    protected EnumMap<TcpSocketProperty, String> socketPropertyMap;

    public TcpSink() {
        this.socketPropertyMap = new EnumMap<TcpSocketProperty, String>(TcpSocketProperty.class);
    }

    public TcpSink(Id id) {
        super(id);

        this.socketPropertyMap = new EnumMap<TcpSocketProperty, String>(TcpSocketProperty.class);
    }

    //---------------

    @Override
    public void start() throws LifecycleException {
        if (sinkStatus.getStateSnapshot().getSinkState() == SinkState.on) {
            return;
        }

        socketPropertyMap.clear();

        logger.log(Level.FINE, String.format("Starting %s", getId()));

        for (Entry<Object, Object> entry : properties.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();

            try {
                TcpSocketProperty socketProperty = TcpSocketProperty.valueOf(key);

                socketPropertyMap.put(socketProperty, value);
            }
            catch (IllegalArgumentException e) {
                logger.log(Level.WARNING, String.format("[%s] is ignoring property [%s:%s]", getId(), key, value));
            }
        }

        String hostName = socketPropertyMap.get(TcpSocketProperty.hostname);
        String portStr = socketPropertyMap.get(TcpSocketProperty.port);

        int port = -1;
        try {
            port = Integer.parseInt(portStr);
        }
        catch (NumberFormatException e) {
            String msg = String.format("Error occurred while starting [%s] because the value [%s]" +
                    " provided for property [%s] is invalid", getId(), portStr, TcpSocketProperty.port.name());

            changeState(e, SinkState.off);

            throw new IllegalArgumentException(msg, e);
        }

        Parameters parameters = new Parameters(getId().toString(), hostName, port);

        //---------------

        String executorName = getClass().getSimpleName() + "." + Helper.$hashMD5(id);
        executorService = Executors.newSingleThreadExecutor(new DefaultThreadFactory(executorName));

        tcpClient = new TCPClient();
        try {
            tcpClient.init(parameters, executorService);

            Socket socket = tcpClient.getSocket();
            applySocketProperties(socket);
        }
        catch (IOException e) {
            changeState(e, SinkState.off);

            throw new LifecycleException(e);
        }

        //---------------

        Reader reader = tcpClient.getReader();
        if (messageCodec != null) {
            messageCodec.reset();
            messageCodec.setClassLoader(Utils.$findRspClassLoader());
        }
        responseReceiver = new ResponseReceiver(tcpClient.getId(), (StreamMessageCodec) messageCodec, logger);
        reader.setOptionalListener(responseReceiver);

        try {
            tcpClient.start();

            sendHelloMessage();
        }
        catch (Exception e) {
            changeState(e, SinkState.off);

            throw new LifecycleException(e);
        }

        //---------------

        sinkStatus.setStateSnapshot(new DefaultSinkStateSnapshot(System.currentTimeMillis(), SinkState.on));

        //---------------

        logger.log(Level.FINE, String.format("Started %s@%s:%s", getId(),
                socketPropertyMap.get(TcpSocketProperty.hostname),
                socketPropertyMap.get(TcpSocketProperty.port)));
    }

    protected void applySocketProperties(Socket socket) {
        for (Entry<TcpSocketProperty, String> entry : socketPropertyMap.entrySet()) {
            TcpSocketProperty key = entry.getKey();
            String value = entry.getValue();

            try {
                String result = key.applyProperty(socket, value);

                if (result != null) {
                    String msg = String.format(
                            "Applied property [%s:%s] to the client socket in [%s]", key.name(), result, getId());

                    logger.log(Level.FINE, msg);
                }
            }
            catch (SocketException e) {
                String msg = String.format(
                        "Error occurred while applying property [%s] to the client socket in [%s]",
                        key.name(), getId());

                logger.log(Level.WARNING, msg, e);
            }
        }
    }

    public void sendHelloMessage() throws SinkException {
        ServerAdmin serverAdmin = resourceProvider.fetchResource(ServerAdmin.class);
        Id serverId = serverAdmin.getResourceId();
        Id uniqueId = new DefaultId(serverId, System.currentTimeMillis());

        HelloMessage helloMessage = new HelloMessage(uniqueId, serverId);

        try {
            WriterImpl writer = (WriterImpl) tcpClient.getWriter();
            OutputStream outputStream = writer.getOutputStream();

            messageCodec.write(helloMessage, outputStream);
        }
        catch (Exception e) {
            String msg = String.format("Error occurred while sending hello message over %s@%s:%s",
                    getId(),
                    socketPropertyMap.get(TcpSocketProperty.hostname),
                    socketPropertyMap.get(TcpSocketProperty.port));

            throw new SinkException(msg, e);
        }
    }

    @Override
    public MessageHandle send(DistributableMessage message) throws SinkException {
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, String.format("Sending message [%s] over %s@%s:%s",
                    message.getUniqueId(), getId(),
                    socketPropertyMap.get(TcpSocketProperty.hostname),
                    socketPropertyMap.get(TcpSocketProperty.port)));
        }

        //---------------

        final long sentAt = System.currentTimeMillis();

        //---------------

        DefaultMessageHandle messageHandle = new DefaultMessageHandle();
        messageHandle.setDistributableMessage(message);
        messageHandle.setSentAt(sentAt);

        //---------------

        Boolean ackExpected = (Boolean) message.getHeaderValue(KnownHeader.__ack_expected__);
        if (ackExpected) {
            sinkStatus.addPendingMessage(messageHandle);
        }

        //---------------

        try {
            WriterImpl writer = (WriterImpl) tcpClient.getWriter();
            OutputStream outputStream = writer.getOutputStream();

            messageCodec.write(message, outputStream);
        }
        catch (Exception e) {
            String msg = String.format("Error occurred while sending message [%s] over %s@%s:%s",
                    message.getUniqueId(), getId(),
                    socketPropertyMap.get(TcpSocketProperty.hostname),
                    socketPropertyMap.get(TcpSocketProperty.port));

            changeState(e, SinkState.suspect);

            throw new SinkException(msg, e);
        }

        return messageHandle;
    }

    @Override
    public void send(SpecialMessage specialMessage) throws SinkException {
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, String.format("Sending special message [%s] over %s@%s:%s",
                    specialMessage.getSenderId(), getId(),
                    socketPropertyMap.get(TcpSocketProperty.hostname),
                    socketPropertyMap.get(TcpSocketProperty.port)));
        }

        //---------------

        try {
            WriterImpl writer = (WriterImpl) tcpClient.getWriter();
            OutputStream outputStream = writer.getOutputStream();

            messageCodec.write(specialMessage, outputStream);
        }
        catch (Exception e) {
            String msg = String.format("Error occurred while sending special message [%s] over %s@%s:%s",
                    specialMessage.getSenderId(), getId(),
                    socketPropertyMap.get(TcpSocketProperty.hostname),
                    socketPropertyMap.get(TcpSocketProperty.port));

            changeState(e, SinkState.suspect);

            throw new SinkException(msg, e);
        }
    }

    private SinkStateSnapshot changeState(Exception e, SinkState sinkState) {
        sinkStatus.setStateSnapshot(new DefaultSinkStateSnapshot(System.currentTimeMillis(), e, sinkState));

        return sinkStatus.getStateSnapshot();
    }

    @Override
    public SinkStateSnapshot checkState() {
        SinkState sinkState = sinkStatus.getStateSnapshot().getSinkState();

        switch (sinkState) {
            case suspect: {
                logger.log(Level.WARNING,
                        String.format("Attempting to repair connection %s@%s:%s by forcibly closing it", getId(),
                                socketPropertyMap.get(TcpSocketProperty.hostname),
                                socketPropertyMap.get(TcpSocketProperty.port)));

                stop();

                /*
                By stopping and restarting the connection, the senderId will still be the same. This way the
                ActualTcpSink will know to resend those pending acks to this same client. 
                */

                logger.log(Level.INFO, String.format("Attempting to restore connection %s@%s:%s", getId(),
                        socketPropertyMap.get(TcpSocketProperty.hostname),
                        socketPropertyMap.get(TcpSocketProperty.port)));

                try {
                    start();
                }
                catch (Exception e) {
                    return changeState(e, SinkState.off);
                }
            }
            break;

            case on:
            case off:
            default:
        }

        return sinkStatus.getStateSnapshot();
    }

    @Override
    public void stop() {
        if (sinkStatus.getStateSnapshot().getSinkState() == SinkState.off) {
            return;
        }

        logger.log(Level.FINE, String.format("Stopping %s@%s:%s", getId(),
                socketPropertyMap.get(TcpSocketProperty.hostname),
                socketPropertyMap.get(TcpSocketProperty.port)));

        try {
            if (tcpClient != null) {
                tcpClient.stop();
                tcpClient = null;
            }
        }
        catch (Exception e) {
            logger.log(Level.WARNING, String.format("Error occurred while closing TCP client in %s@%s:%s", getId(),
                    socketPropertyMap.get(TcpSocketProperty.hostname),
                    socketPropertyMap.get(TcpSocketProperty.port)), e);
        }

        if (executorService != null) {
            executorService.shutdown();
            executorService = null;
        }

        if (responseReceiver != null) {
            responseReceiver.discard();
            responseReceiver = null;
        }

        sinkStatus.setStateSnapshot(new DefaultSinkStateSnapshot(System.currentTimeMillis(), SinkState.off));

        logger.log(Level.FINE, String.format("Stopped %s@%s:%s", getId(),
                socketPropertyMap.get(TcpSocketProperty.hostname),
                socketPropertyMap.get(TcpSocketProperty.port)));

        socketPropertyMap.clear();
    }

    //---------------

    protected class ResponseReceiver extends AbstractTcpReaderListener {
        private boolean debugOn;

        public ResponseReceiver(String sessionId, StreamMessageCodec messageCodec, Logger logger) {
            super(sessionId, messageCodec, logger);

            this.debugOn = logger.isLoggable(Level.FINE);
        }

        @Override
        protected void onMessage(Object message) {
            if (message instanceof SimpleDistributionResponse) {
                SimpleDistributionResponse response = (SimpleDistributionResponse) message;
                Object uniqueIdOfMessage = response.getUniqueIdOfMessage();
                boolean positiveAck = response.isPositiveAck();

                MessageHandle pendingMsgHandle = sinkStatus.getPendingMessage(uniqueIdOfMessage);
                if (pendingMsgHandle == null) {
                    logger.log(Level.WARNING, String.format("Unexpected acknowledgement received for message [%s]" +
                            " with positive ack status [%s] in connection %s@%s:%s. Ignoring receipt.",
                            uniqueIdOfMessage, positiveAck, getId(),
                            socketPropertyMap.get(TcpSocketProperty.hostname),
                            socketPropertyMap.get(TcpSocketProperty.port)));

                    return;
                }

                //---------------

                if (debugOn) {
                    logger.log(Level.FINE, String.format("Acknowledgement received for message [%s]" +
                            " with positive ack status [%s] in connection %s@%s:%s.",
                            uniqueIdOfMessage, positiveAck, getId(),
                            socketPropertyMap.get(TcpSocketProperty.hostname),
                            socketPropertyMap.get(TcpSocketProperty.port)));
                }

                if (positiveAck) {
                    final DistributableMessage distributableMessage = pendingMsgHandle.getDistributableMessage();
                    if ((distributableMessage instanceof DistributableJmsMessage)
                        && !ForwardingJmsDestination.JmsAckProcessor.INSTANCE.ackMessage(
                            (DistributableJmsMessage) distributableMessage)) {
                        logger.log(
                                Level.WARNING,
                                String.format("Failed to process positive acknowledgement message [%s] "
                                                + " in connection %s@%s:%s. Ignoring receipt.",
                                        uniqueIdOfMessage,
                                        getId(),
                                        socketPropertyMap.get(TcpSocketProperty.hostname),
                                        socketPropertyMap.get(TcpSocketProperty.port)));
                    }
                    pendingMsgHandle.setAckReceivedAt(System.currentTimeMillis());
                    sinkStatus.confirmMessageSent(pendingMsgHandle);
                }
                else {
                	final DistributableMessage distributableMessage = pendingMsgHandle.getDistributableMessage();
                    if ((distributableMessage instanceof DistributableJmsMessage)
                        && !ForwardingJmsDestination.JmsAckProcessor.INSTANCE.rollback(
                            (DistributableJmsMessage) distributableMessage)) {
                        logger.log(
                                Level.WARNING,
                                String.format("Failed to process negative acknowledgement message [%s] "
                                                + " in connection %s@%s:%s. Ignoring receipt.",
                                        uniqueIdOfMessage,
                                        getId(),
                                        socketPropertyMap.get(TcpSocketProperty.hostname),
                                        socketPropertyMap.get(TcpSocketProperty.port)));
                    }
                    pendingMsgHandle.setException(new NegAckLiteException());
                    sinkStatus.confirmMessageFailed(pendingMsgHandle);
                }
            }
        }

        @Override
        public void onException(Exception e) {
            //todo Call Sink.stop() or restart by checkState()?

            logger.log(Level.WARNING, String.format("Error occurred in [%s]", getId()), e);
        }

        @Override
        public void onEnd() {
            //todo

        }
    }
}