package com.tibco.cep.driver.jms;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.driver.util.IncludeEventType;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.EventSerializer;
import com.tibco.xml.data.primitive.ExpandedName;

import javax.jms.DeliveryMode;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Properties;


/**
 * User: nprade
 * Date: 10/11/12
 * Time: 3:22 PM
 */
public class JmsDestinationConfig
        implements DestinationConfig {


    private static final int DEFAULT_ACK_MODE = Session.CLIENT_ACKNOWLEDGE;
    private static final int DEFAULT_DELIVERY_MODE = DeliveryMode.PERSISTENT;
    private static final int DEFAULT_PRIORITY = Message.DEFAULT_PRIORITY;
    private static final long DEFAULT_TTL = Message.DEFAULT_TIME_TO_LIVE;

    private final DestinationConfig baseConfig;
    private final BaseJMSChannel channel;
    private final boolean isEms;
    private final boolean isQueue;
    private final int jmsAckMode;
    private final int jmsDeliveryMode;
    private final String jmsDestinationName;
    private final String jmsDurableName;
    private final String jmsSelector;
    private final int jmsPriority;
    private final long jmsTtl;
    private final boolean isDeserializingEventType;
    private final boolean isSerializingEventType;
    private final String sharedSubscriptionName;


    public JmsDestinationConfig(
            BaseJMSChannel channel,
            DestinationConfig baseConfig) {

        this.baseConfig = baseConfig;
        this.channel = channel;
        this.isEms = channel.isTibcoJMS();
        this.isQueue = "true".equalsIgnoreCase(this.getSubstitutedPropertyValue("Queue", true));
        this.jmsDestinationName = this.getSubstitutedPropertyValue("Name", true);
        this.jmsDurableName = this.getSubstitutedPropertyValue("DurableSuscriberName", true);
        this.jmsSelector = this.getSubstitutedPropertyValue("Selector", false);
        this.sharedSubscriptionName = this.getSubstitutedPropertyValue("SharedSubscriptionName", false);

        final IncludeEventType includeEventType = IncludeEventType.valueOf(
                this.getSubstitutedPropertyValue("IncludeEventType", IncludeEventType.ALWAYS.toString(), false));
        this.isDeserializingEventType = includeEventType.isOkOnDeserialize();
        this.isSerializingEventType = includeEventType.isOkOnSerialize();

        final String deliveryModeString = this.getSubstitutedPropertyValue("DeliveryMode", false);
        this.jmsDeliveryMode = (deliveryModeString != null) && !deliveryModeString.trim().isEmpty()
                ? Integer.parseInt(deliveryModeString)
                : DEFAULT_DELIVERY_MODE;

        final String priorityString = this.getSubstitutedPropertyValue("Priority", false);
        this.jmsPriority = (priorityString != null) && !priorityString.trim().isEmpty()
                ? Integer.parseInt(priorityString)
                : DEFAULT_PRIORITY;

        final String ttlString = this.getSubstitutedPropertyValue("TTL", false);
        this.jmsTtl = (ttlString != null) && !ttlString.trim().isEmpty()
                ? Long.parseLong(ttlString)
                : DEFAULT_TTL;

        final String ackModeString = this.getSubstitutedPropertyValue("AckMode", false);
        if ((ackModeString == null) || ackModeString.trim().isEmpty()) {
            this.jmsAckMode = (((BEProperties) channel.getServiceProviderProperties())).getInt(
                    (this.isQueue ? "be.channel.tibjms.queue.ack.mode" : "be.channel.tibjms.topic.ack.mode"),
                    (this.isEms ? EmsHelper.DEFAULT_ACK_MODE : DEFAULT_ACK_MODE));
        }
        else {
            final int requestedMode = Integer.parseInt(ackModeString);
            if (this.isEms) {
                switch (requestedMode) {
                    case Session.CLIENT_ACKNOWLEDGE:
                        this.jmsAckMode = EmsHelper.EXPLICIT_CLIENT_ACKNOWLEDGE;
                        break;
                    case Session.DUPS_OK_ACKNOWLEDGE:
                        this.jmsAckMode = EmsHelper.EXPLICIT_CLIENT_DUPS_OK_ACKNOWLEDGE;
                        break;
                    default:
                        this.jmsAckMode = requestedMode;
                }
            }
            else {
                this.jmsAckMode = requestedMode;
            }
        }
    }


    @Override
    public JMSChannelConfig getChannelConfig() {
        return this.channel.getConfig();
    }


    @Override
    public ExpandedName getDefaultEventURI() {
        return this.baseConfig.getDefaultEventURI();
    }


    @Override
    public EventSerializer getEventSerializer() {
        return this.baseConfig.getEventSerializer();
    }


    @Override
    public Event getFilter() {
        return this.baseConfig.getFilter();
    }


    public int getJmsAckMode() {
        return this.jmsAckMode;
    }


    public int getJmsDeliveryMode() {
        return this.jmsDeliveryMode;
    }


    public String getJmsDestinationName() {
        return this.jmsDestinationName;
    }


    public String getJmsDurableName() {
        return this.jmsDurableName;
    }


    public int getJmsPriority() {
        return this.jmsPriority;
    }


    public String getJmsSelector() {
        return this.jmsSelector;
    }


    public long getJmsTtl() {
        return this.jmsTtl;
    }


    @Override
    public String getName() {
        return this.baseConfig.getName();
    }


    @Override
    public Properties getProperties() {
        return this.baseConfig.getProperties();
    }


    private String getSubstitutedPropertyValue(
            String propertyName,
            boolean replaceSpaceWithUnderScore)
    {
        return this.getSubstitutedPropertyValue(propertyName, null, replaceSpaceWithUnderScore);
    }


    private String getSubstitutedPropertyValue(
            String propertyName,
            String defaultValue,
            boolean replaceSpaceWithUnderScore) {

        String value = this.channel.getGlobalVariables()
                .substituteVariables(this.baseConfig.getProperties().getProperty(propertyName, defaultValue))
                .toString()
                .replaceAll("%%EngineName%%", this.channel.getChannelManager().getRuleServiceProvider().getName())
                .replaceAll("%%ChannelURI%%", channel.getURI())
                .replaceAll("%%ChannelName%%", channel.getName())
                .replaceAll("%%DestinationName%%", this.getName())
                .replaceAll("%%DestinationURI%%", this.getURI());

        if (replaceSpaceWithUnderScore) {
            value = value.replace(' ', '_');
        }

        return value;
    }


    @Override
    public String getURI() {
        return this.baseConfig.getURI();
    }


    public boolean isEms() {
        return this.isEms;
    }



    public boolean isQueue() {
        return this.isQueue;
    }


    public boolean isDeserializingEventType() {
        return this.isDeserializingEventType;
    }


    public boolean isSerializingEventType() {
        return this.isSerializingEventType;
    }


    public boolean isTransacted() {
        return this.channel.getConfig().isTransacted();
    }


    @Override
    public void setFilter(
            Event event) {

        throw new UnsupportedOperationException();
    }


	public String getSharedSubscriptionName() {
		return sharedSubscriptionName;
	}
    
}
