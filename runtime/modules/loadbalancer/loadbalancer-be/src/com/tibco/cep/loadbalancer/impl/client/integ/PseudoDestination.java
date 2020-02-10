package com.tibco.cep.loadbalancer.impl.client.integ;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.client.Client;
import com.tibco.cep.loadbalancer.client.ClientAdmin;
import com.tibco.cep.loadbalancer.client.core.ActualMember;
import com.tibco.cep.loadbalancer.client.core.ActualSink;
import com.tibco.cep.loadbalancer.client.core.MessageCustomizer;
import com.tibco.cep.loadbalancer.impl.client.ClientConstants;
import com.tibco.cep.loadbalancer.impl.client.ClientMaster;
import com.tibco.cep.loadbalancer.impl.client.transport.ActualSinkProperty;
import com.tibco.cep.loadbalancer.impl.client.transport.tcp.AckHandle;
import com.tibco.cep.loadbalancer.impl.client.transport.tcp.MessageReceiver;
import com.tibco.cep.loadbalancer.impl.jmx.ReceiverDestinationMbeanImpl;
import com.tibco.cep.loadbalancer.impl.transport.TransportConfig;
import com.tibco.cep.loadbalancer.membership.MembershipPublisher;
import com.tibco.cep.loadbalancer.message.DistributableMessage;
import com.tibco.cep.loadbalancer.message.DistributableMessage.KnownHeader;
import com.tibco.cep.runtime.channel.impl.AbstractChannel;
import com.tibco.cep.runtime.channel.impl.AbstractDestination;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AbstractEventContext;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.util.annotation.LogCategory;
import com.tibco.cep.util.annotation.Optional;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.tibco.cep.util.Helper.*;

/*
* Author: Ashwin Jayaprakash / Date: Apr 14, 2010 / Time: 11:32:29 AM
*/

@LogCategory("loadbalancer.be.client.destination.pseudo")
public abstract class PseudoDestination<C extends AbstractChannel, D extends AbstractDestination<C>>
        implements MessageReceiver {
    protected RuleSession session;

    protected Logger logger;

    protected C channel;

    protected D destination;

    protected ActualSink actualSink;

    protected ReceiverDestinationMbeanImpl receiverDestinationMbean;

    protected Map<Object, Object> properties;

    public PseudoDestination() {
        //todo There has to be a better place for this.
        try {
            ClientMaster.init();
        }
        catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }

    public void setProperties(Map<Object, Object> properties) {
        this.properties = properties;
    }

    public void start(RuleSession session, C channel, D destination) {
        this.session = session;
        this.channel = channel;
        this.destination = destination;

        //--------------

        ResourceProvider resourceProvider = ClientMaster.getResourceProvider();
        logger = $logger(resourceProvider, getClass());

        logger.log(Level.INFO,
                String.format("Starting [%s : %s : %s : %s]", session.getName(), channel.getName(),
                        destination.getName(), getClass().getSimpleName()));

        //--------------

        ClientAdmin clientAdmin = resourceProvider.fetchResource(ClientAdmin.class);

        try {
            Client client = clientAdmin.getClientFor(session);
            ActualMember member = client.getMember();

            //--------------

            String transport = TransportConfig.tcp.getName();
            if (properties != null) {
                transport = (String) properties.get(ClientConstants.PROPERTY_TRANSPORT);
            }


            TransportConfig chosenTransport = TransportConfig.valueOf(transport);

            ActualSink actualSink = chosenTransport.getActualSinkClass().newInstance();

            String sinkName = $eval(resourceProvider, ClientConstants.NAME_SINK,
                    "member", member, "destination", destination).toString();
            Id sinkId = $id(sinkName);

            actualSink.setId(sinkId);
            actualSink.setResourceProvider(resourceProvider);
            actualSink.setMessageCustomizer(createMessageCustomizer());

            String sourceName = $eval(resourceProvider, ClientConstants.NAME_SOURCE,
                    "destination", destination).toString();
            Id sourceId = $id(sourceName);

            actualSink.setSourceId(sourceId);

            HashMap<ActualSinkProperty, Object> additionalParams = new HashMap<ActualSinkProperty, Object>();
            additionalParams.put(ActualSinkProperty.RuleSession, session);
            additionalParams.put(ActualSinkProperty.AbstractChannel, channel);
            additionalParams.put(ActualSinkProperty.AbstractDestination, destination);
            additionalParams.put(ActualSinkProperty.MessageReceiver, this);
            additionalParams.put(ActualSinkProperty.ConfigProperties, properties);
            actualSink.setAdditionalProperties(additionalParams);

            //--------------

            //Member has already started. So, we have to start this sink on own.
            try {
                actualSink.start();
            }
            catch (Exception e) {
                String s = String.format("Error occurred while starting the actual sink for [%s : %s : %s : %s]",
                        session.getName(), channel.getName(), destination.getName(), getClass().getSimpleName());

                throw new RuntimeException(s, e);
            }

            this.actualSink = actualSink;

            member.addSink(actualSink);

            //--------------

            MembershipPublisher membershipPublisher = client.getMembershipPublisher();
            try {
                membershipPublisher.refreshPublication();
            }
            catch (Exception e) {
                String s = String.format("Error occurred while while refreshing publication for [%s : %s : %s : %s]",
                        session.getName(), channel.getName(), destination.getName(), getClass().getSimpleName());

                throw new RuntimeException(s, e);
            }

            //--------------

            receiverDestinationMbean = new ReceiverDestinationMbeanImpl();
            receiverDestinationMbean.setParentName(session.getName());
            receiverDestinationMbean.setName(
                    String.format("%s_%s_%s", channel.getName(), destination.getName(), getClass().getSimpleName()));

            try {
                receiverDestinationMbean.register();
            }
            catch (Exception e) {
                logger.log(Level.WARNING, "Error occurred while registering mbean", e);
            }

            //--------------

            logger.log(Level.INFO,
                    String.format("Started [%s : %s : %s : %s]", session.getName(), channel.getName(),
                            destination.getName(), getClass().getSimpleName()));
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Optional
    protected MessageCustomizer createMessageCustomizer() {
        return null;
    }

    @Override
    public void receive(RuleSession ruleSession, SimpleEvent event) throws Exception {
        destination.onMessage(ruleSession, event);

        receiverDestinationMbean.incrementTotalMessagesReceived();
    }

    @Override
    public void receive(RuleSession ruleSession, AbstractEventContext abstractEventContext, boolean ackExpected)
            throws Exception {
        destination.onMessage(ruleSession, abstractEventContext);

        receiverDestinationMbean.incrementTotalMessagesReceived();

        if (ackExpected) {
            receiverDestinationMbean.incrementTotalPendingAcks();
        }
    }

    @Override
    public void ackSent(AckHandle ackHandle) {
        receiverDestinationMbean.decrementTotalPendingAcks();
    }

    public void stop() {
        logger.log(Level.INFO, String.format("Stopping [%s : %s : %s : %s]", session.getName(), channel.getName(),
                destination.getName(), getClass().getSimpleName()));

        ResourceProvider resourceProvider = ClientMaster.getResourceProvider();
        ClientAdmin clientAdmin = resourceProvider.fetchResource(ClientAdmin.class);

        try {
            Client client = clientAdmin.getClientFor(session);
            ActualMember member = client.getMember();

            member.removeSink(actualSink);

            actualSink.stop();
            actualSink = null;

            MembershipPublisher membershipPublisher = client.getMembershipPublisher();
            membershipPublisher.refreshPublication();

            logger.log(Level.INFO, String.format("Stopped [%s : %s : %s : %s]", session.getName(), channel.getName(),
                    destination.getName(), getClass().getSimpleName()));
        }
        catch (Exception e) {
            logger.log(Level.SEVERE,
                    String.format("Error occurred while stopping [%s : %s : %s : %s]", session.getName(),
                            channel.getName(), destination.getName(), getClass().getSimpleName()), e);
        }
    }

    //--------------

    protected abstract static class AbstractMessageCustomizer implements MessageCustomizer {
        protected abstract void setBoolean(DistributableMessage distributableMessage,
                                           KnownHeader knownHeader, boolean value) throws Exception;

        protected abstract void setString(DistributableMessage distributableMessage,
                                          KnownHeader knownHeader, String value) throws Exception;

        protected abstract void setLong(DistributableMessage distributableMessage,
                                        KnownHeader knownHeader, long value) throws Exception;

        @Override
        public DistributableMessage customize(DistributableMessage distributableMessage) throws Exception {
            for (KnownHeader knownHeader : KnownHeader.values()) {
                Object value = distributableMessage.getHeaderValue(knownHeader);
                switch (knownHeader) {
                    //todo Force set _likely_dup_ if this server has started just now.
                    //todo Force set if there are other members that have joined and started receiving similar messages
                    //todo Make a catalog function available to track messages still in the SharedQueue to set this flag
                    case __likely_dup_delivery__:
                    case __ack_expected__:
                        if (value != null) {
                            setBoolean(distributableMessage, knownHeader, (Boolean) value);
                        }
                        break;

                    //todo Perhaps this should be for debug mode only.
                    case __content_id__:
                        if (value != null) {
                            setString(distributableMessage, knownHeader, (String) value);
                        }
                        break;

                    //todo Perhaps this should be for debug mode only.
                    case __version_id__:
                        if (value != null) {
                            setLong(distributableMessage, knownHeader, (Long) value);
                        }
                        break;

                    default:
                }
            }

            return distributableMessage;
        }
    }
}