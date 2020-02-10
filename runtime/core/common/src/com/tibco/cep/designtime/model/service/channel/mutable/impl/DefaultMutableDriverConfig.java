package com.tibco.cep.designtime.model.service.channel.mutable.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.model.util.EntityNameHelper;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.service.channel.Channel;
import com.tibco.cep.designtime.model.service.channel.ChannelDescriptor;
import com.tibco.cep.designtime.model.service.channel.Destination;
import com.tibco.cep.designtime.model.service.channel.DestinationDescriptor;
import com.tibco.cep.designtime.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.model.service.channel.ExtendedConfiguration;
import com.tibco.cep.designtime.model.service.channel.PropertyDescriptor;
import com.tibco.cep.designtime.model.service.channel.mutable.MutableDestination;
import com.tibco.cep.designtime.model.service.channel.mutable.MutableDriverConfig;

/**
 * User: nprade
 * Date: Jul 26, 2004
 * Time: 8:55:31 PM
 */


public class DefaultMutableDriverConfig implements MutableDriverConfig {


    protected static final String DEFAULT_DESTINATION_PREFIX = "Destination";

    protected Channel m_channel;
    protected String m_type;
    protected String m_ServerType;
    protected String m_configMethod;
    protected Properties m_properties;
    protected Map m_destinations = new LinkedHashMap();
    protected String[] m_allowedResTypes;
    protected String m_reference;
    private String m_label;



    public DefaultMutableDriverConfig(
            Channel channel,
            String type,
            String configMethod,
            Object config,
            Collection destinations)
            throws ModelException {

        if ((type == null) || type.trim().equals("")) {
            throw new IllegalArgumentException("bad type");
        }//if

        if (!(DriverConfig.CONFIG_BY_REFERENCE.equals(configMethod)
                || DriverConfig.CONFIG_BY_PROPERTIES.equals(configMethod))) {
            throw new IllegalArgumentException("bad config type");
        }//if

        this.m_channel = channel;
        this.m_type = type;
        this.m_configMethod = configMethod;
        this.m_allowedResTypes = DefaultMutableChannel.DRIVER_MANAGER.getAllowedResourceTypes(type);
        this.m_label = (String) DefaultMutableChannel.DRIVER_MANAGER.getDriverLabelsByTypes().get(type);

        String reference = "";
        Properties driverProperties = null;
        final ChannelDescriptor channelDescriptor = DefaultMutableChannel.DRIVER_MANAGER.getChannelDescriptor(type);

        // Config by Reference
        if (DriverConfig.CONFIG_BY_REFERENCE.equals(configMethod)) {
            driverProperties = this.getDefaultProperties(channelDescriptor);
            if (config == null) {
                config = "";
            } else if (!(config instanceof String)) {
                throw new IllegalArgumentException("config Object is not a String");
            }//else
            reference = config.toString();

            // Config by Properties
        } else if (DriverConfig.CONFIG_BY_PROPERTIES.equals(configMethod)) {
            if (config == null) {
                driverProperties = this.getDefaultProperties(channelDescriptor);
            } else {
            	driverProperties = new Properties();
            	final Properties props = (Properties) config;
            	for (String validKey : this.getChannelDescriptor().keySet()) {
            		driverProperties.put(validKey, props.get(validKey));
            	}
            }//else
        }//else if

        m_reference = reference;
        m_properties = driverProperties;

        if (destinations != null) {
            for (Iterator it = destinations.iterator(); it.hasNext();) {
                final Destination destination = (Destination) it.next();
                addDestination(destination);
            }//for
        }//else

    }//constr


    private Properties getDefaultProperties(
            ChannelDescriptor channelDescriptor) {
        final Properties props = new Properties();
        for (PropertyDescriptor pDesc: channelDescriptor.values()) {
            props.setProperty(pDesc.getName(), pDesc.getDefaultValue());
        }
        return props;
    }

    public ExtendedConfiguration getExtendedConfiguration() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @return the MutableChannel that is configured by this object.
     */
    public Channel getChannel() {
        return m_channel;
    }//getChannel


    public MutableDestination createDestination() {
        try {
            final String name = findNewDestinationName();
            final MutableDestination destination = new DefaultMutableDestination(name, this);
            m_destinations.put(destination.getName(), destination);
            return destination;
        } catch (ModelException e) {
            e.printStackTrace();
            return null;
        }//catch
    }//create Destination


    public String findNewDestinationName() {
        for (long count = 0; ; count++) {
            final String name = EntityNameHelper.convertToEntityIdentifier(m_type + '_' + count);
            final Object existingDestination = getDestination(name);
            if (null == existingDestination) {
                return name;
            }//if
        }//for
    }//findNewDestinationName


    /**
     * Adds a Destination to this DriverConfig.
     *
     * @return the new Destination itself.
     */
    public MutableDriverConfig addDestination(Destination destination) throws ModelException {
//    	if (!this.getDestinationDescriptor().keySet().contains(destination.getProperties().keySet())) {
//            throw new ModelException("Destination properties do not match expected set.");
//        }//if
        m_destinations.put(destination.getName(), destination);
        return this;
    }//addDestination


    /**
     * Deletes all Destinations from this DriverConfig.
     *
     * @return the DriverConfig itself.
     */
    public MutableDriverConfig clearDestinations() {
        for (Iterator it = m_destinations.values().iterator(); it.hasNext();) {
            final MutableDestination destination = (MutableDestination) it.next();
            destination.delete();
        }//for
        m_destinations.clear();
        return this;
    }//clearDestinations


    public String[] getAllowedResourceTypes() {
        return m_allowedResTypes;
    }

    /*
    public String getUrl() {
        return m_url;
    }

    public DriverConfig setUrl(String url) {
        if (url == null) {
            throw new IllegalArgumentException("invalid url");
        }//if
        m_url = url;
        return this;
    }
    */


    public String getReference() {
        return m_reference;
    }


    public MutableDriverConfig setReference(String ref) {
        if (ref == null) {
            throw new IllegalArgumentException("invalid reference");
        }//if
        m_reference = ref;
        return this;
    }

    public void setServerType(String serverType) {
        m_ServerType = serverType;
    }

    public String getServerType() {
        return m_ServerType;
    }

    public ChannelDescriptor getChannelDescriptor() {
        return DefaultMutableChannel.DRIVER_MANAGER.getChannelDescriptor(this.m_type);
    }//getPropertiesConcept

    public String getDefaultSerializer() {
         return DefaultMutableChannel.DRIVER_MANAGER.getDefaultSerializer(m_type);
    }


    public Properties getChannelProperties() {
        return m_properties;
    }


    public Iterator getAvailableDestinations() {
        return null;
    }


    public DestinationDescriptor getDestinationDescriptor() {
        return DefaultMutableChannel.DRIVER_MANAGER.getDestinationDescriptor(this.m_type);
    }//getDestinationDescriptor


    public Iterator getDestinations() {
        return m_destinations.values().iterator();
    }//getDestinations


    /**
     * Returns a shallow clone of the List of Destination objects that are registered with this DriverConfig.
     *
     * @return a List of Destination objects.
     */
    public List getDestinationsList() {
        return new ArrayList(m_destinations.values());
    }


    /**
     * Returns the Destination object of the given name registered with this DriverConfig
     *
     * @return a Destination or null if there was no MutableDestination with that name in this DriverConfig
     */
    public Destination getDestination(String name) {
        return (MutableDestination) m_destinations.get(name);
    }//getDestination


    public String getDriverType() {
        return m_type;
    }


    /**
     * @return a String representing the name of this driver.
     */
    public String getLabel() {
        return m_label;
    }

    public String getConfigMethod() {
        return m_configMethod;
    }


    public MutableDriverConfig removeDestination(Destination destination) {
        if (destination != null) {
            final Object removedDestination = m_destinations.remove(destination.getName());
            if (removedDestination != null) {
                ((MutableDestination) destination).delete();
            }//if
        }//if
        return this;
    }//removeDestination


    public MutableDriverConfig setConfigMethod(String type) {
        if (!(DriverConfig.CONFIG_BY_PROPERTIES.equals(type) || DriverConfig.CONFIG_BY_REFERENCE.equals(type))) {
            throw new IllegalArgumentException("bad type");
        }//if
        m_configMethod = type;
        return this;
    }


    public String test() {
        return null;
    }


}//class
