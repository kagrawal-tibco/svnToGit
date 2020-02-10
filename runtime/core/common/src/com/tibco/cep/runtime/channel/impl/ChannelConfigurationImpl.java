package com.tibco.cep.runtime.channel.impl;


import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.service.channel.*;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.EventSerializer;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.xml.data.primitive.ExpandedName;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 4, 2006
 * Time: 11:22:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChannelConfigurationImpl implements ChannelConfig {


    String uri;
    ConfigurationMethod method;
    ArrayList destinations = new ArrayList();
    String refUri;
    Properties properties = new Properties();
    String driverType;
    private String serverType;
    protected String defaultSerializerClass = null;
    // should channel be deactivated (default false)
    boolean deactivateChannel;


    //TODO Need to figure out a way to avoid having one class load all driver specific config.
    //Only used for HTTP channel
    private List<WebApplicationDescriptor> webApplicationDescriptors;


    public ChannelConfigurationImpl(GlobalVariables globalVariables, Channel channel) {
        uri = channel.getFullPath();
        DriverConfig driverConfig = channel.getDriver();
        driverType = driverConfig.getDriverType();

        if (driverConfig instanceof HTTPDriverConfig) {
            //Load webapp descriptors
            HTTPDriverConfig httpDriverConfig = (HTTPDriverConfig)driverConfig;
            webApplicationDescriptors = httpDriverConfig.getWebApplicationDescriptors();
        }
        defaultSerializerClass = driverConfig.getDefaultSerializer();
        ExtendedConfiguration extendedConfiguration = driverConfig.getExtendedConfiguration();
        
        if (extendedConfiguration != null) {
            // Check if channel needs to be deactivated.
            PropertyDescriptor deActivatePropertyDescriptor = 
            	extendedConfiguration.get(SystemProperty.CHANNEL_DEACTIVATE);
            if (deActivatePropertyDescriptor != null) {
                String defaultValue = deActivatePropertyDescriptor.getDefaultValue();
                defaultValue = globalVariables.substituteVariables(defaultValue).toString();
                deactivateChannel = (defaultValue == null || defaultValue.isEmpty()) ? false : Boolean.parseBoolean(defaultValue);
            }
        	
            PropertyDescriptor propertyDescriptor = extendedConfiguration.get("serverType");

            if (propertyDescriptor != null) {
                serverType = propertyDescriptor.getDefaultValue();
            }
        }
        if (DriverConfig.CONFIG_BY_REFERENCE.equalsIgnoreCase(driverConfig.getConfigMethod())) {
            method = ConfigurationMethod.REFERENCE;
            refUri = driverConfig.getReference();
        } else {
            method = ConfigurationMethod.PROPERTIES;
            refUri = null;
        }

        this.properties = new Properties();
        this.properties.putAll(driverConfig.getChannelProperties());

        Iterator r = driverConfig.getDestinations();
        while (r.hasNext()) {
            Destination dest = (Destination) r.next();
            DestinationConfig dconfig = new DestinationConfigImpl(this, dest);
            destinations.add(dconfig);
        }
    }
    
    
    /**
     * Tests whether the channel has not been marked for deactivation.
     *
     * @return a boolean
     * @since 5.0.1-HF1
     */
    @Override
    public boolean isActive() {
        return !deactivateChannel;
    }


    static public String diff(ChannelConfigurationImpl oldConfig, ChannelConfigurationImpl newConfig) {
        if(!oldConfig.uri.equals(newConfig.uri))
            return "URI [" + oldConfig.uri + "] changed to [" + newConfig.uri + "]";

        if(!oldConfig.method.equals(newConfig.method))
            return "Method of Config [" + oldConfig.method + "] changed to [" + newConfig.method + "]";

        if(oldConfig.refUri == null) {
            if(newConfig.refUri != null)
                return "Resource [] changed to [" + newConfig.refUri + "]";
        }
        else if(!oldConfig.refUri.equals(newConfig.refUri))
            return "Resource [" + oldConfig.refUri + "] changed to [" + newConfig.refUri + "]";

        if(!oldConfig.driverType.equals(newConfig.driverType))
            return "Driver [" + oldConfig.driverType + "] changed to [" + newConfig.driverType + "]";

        if(!oldConfig.properties.equals(newConfig.properties))
            return "Properties [" + oldConfig.properties + "] changed to [" + newConfig.properties + "]";

        if(oldConfig.destinations.size() != newConfig.destinations.size())
            return "Adding or Deleting Destination is not allowed";

        for(int i = 0; i < oldConfig.destinations.size(); i++) {
            DestinationConfigImpl oldDestConfig = (DestinationConfigImpl) oldConfig.destinations.get(i);
            boolean found = false;
            for(int j = 0; j < newConfig.destinations.size(); j++) {
                DestinationConfigImpl newDestConfig = (DestinationConfigImpl) newConfig.destinations.get(j);
                if(oldDestConfig.getName().equals(newDestConfig.getName())) {
                    found = true;
                    String msg = diffDest(oldDestConfig,newDestConfig);
                    if(msg != null)
                        return "Destination [" + oldDestConfig.getName() + "] " + msg;
                    else
                        break;
                }
            }
            if (!found) {
                return "Destination [" + oldDestConfig.getName() + "] is removed";
            }
        }
        return null;
   }


    static private String diffDest(DestinationConfigImpl oldConfig, DestinationConfigImpl newConfig) {
        if(!oldConfig.chConfig.getURI().equals(newConfig.chConfig.getURI()))
            return  "Channel URI [" + oldConfig.chConfig.getURI() + "] changed to [" + newConfig.chConfig.getURI() + "]";

        if(!oldConfig.name.equals(newConfig.name))
            return  "Name [" + oldConfig.name + "] changed to [" + newConfig.name + "]";

        if(oldConfig.serializer == null) {
            if(newConfig.serializer != null)
                return  "Serializer [] changed to [" + newConfig.serializer.getClass().getName() + "]";
        }
        else {  //oldConfig.serializer != null
            if (newConfig.serializer == null)
                return  "Serializer [" + oldConfig.serializer.getClass().getName() + "] changed to []";
            else if(!oldConfig.serializer.getClass().getName().equals(newConfig.serializer.getClass().getName()))
                return  "Serializer [" + oldConfig.serializer.getClass().getName() + "] changed to ["+ newConfig.serializer.getClass().getName() + "]";
        }

        if(oldConfig.eventURI == null) {
            if(newConfig.eventURI != null)
                return  "Default Event [] changed to [" + newConfig.eventURI + "]";
        }
        else if(!oldConfig.eventURI.equals(newConfig.eventURI))
            return "Default Event [" + oldConfig.eventURI + "] changed to [" + newConfig.eventURI + "]";

        if(!oldConfig.properties.equals(newConfig.properties))
            return "Properties [" + oldConfig.properties + "] changed to [" + newConfig.properties + "]";

        if(oldConfig.filterEvent == null) {
            if(newConfig.filterEvent != null)
                return "Filer [] changed to [" + newConfig.filterEvent + "]";
        }
        else {
            if(newConfig.filterEvent == null )
                return "Filer [" + oldConfig.filterEvent + "] changed to []";

            if(!oldConfig.filterEvent.getFullPath().equals(newConfig.filterEvent.getFullPath()))
                return "Filer [" + oldConfig.filterEvent.getFullPath() + "] changed to [" + newConfig.filterEvent.getFullPath() + "]";
        }
        return null;
    }

    public String getName() {
       return uri.substring(uri.lastIndexOf('/') + 1); 
    }

    public ConfigurationMethod getConfigurationMethod() {
        return method;
    }

    @Override
    public List<WebApplicationDescriptor> getWebApplicationDescriptors() {
        return webApplicationDescriptors;
    }

    public Collection getDestinations() {
        return destinations;
    }

    /**
     * Gets the Server Type of the <code>ChannelDriver</code> for the configured <code>Channel</code>.
     * <p/>
     * <b>
     * Used for HTTP based channel
     * </b>
     * </p>
     *
     * @return a String.
     * @since 2.0
     */
    public String getServerType() {
        return serverType;
    }

    public Properties getProperties() {
        return properties;
    }


    public String getReferenceURI() {
        return refUri;
    }


    public String getType() {
        return driverType;
    }


    public String getURI() {
        return uri;
    }


    class DestinationConfigImpl implements DestinationConfig {


        ChannelConfig chConfig;
        String name;
        EventSerializer serializer;

        ExpandedName eventURI;
        Properties properties = new Properties();
        Event filterEvent;


        DestinationConfigImpl(ChannelConfig chConfig, Destination dest) {
            this.chConfig = chConfig;
            name = dest.getName();

            String defaultEventURI = dest.getEventURI();
            //Not every destination will have a default event
            if (defaultEventURI != null) {
                String eventName = defaultEventURI.substring(defaultEventURI.lastIndexOf('/') + 1);

                eventURI = ExpandedName.makeName(TypeManager.DEFAULT_BE_NAMESPACE_URI + defaultEventURI, eventName);
            }

            this.properties = new Properties();
            this.properties.putAll(dest.getProperties());

            this.setSerializer(dest.getSerializerDeserializerClass());
        }


        private void setSerializer(
                String className) {

            if ((className == null) || className.isEmpty()) {
                className = defaultSerializerClass;
            }

            if ((className != null) && !className.isEmpty()) {
                try {
                    serializer = (EventSerializer) Class.forName(className).newInstance();
                }
                catch (Exception e) {
                    System.err.println(this.getName() + " Destination's serializer class not found: " + className);
                }
            }
        }


        public EventSerializer getEventSerializer() {
            return serializer;
        }


        public ExpandedName getDefaultEventURI() {
            return eventURI;
        }


        public String getName() {
            return name;
        }


        public Properties getProperties() {
            return properties;
        }


        public String getURI() {
            return chConfig.getURI() + "/" + name;
        }


        public ChannelConfig getChannelConfig() {
            return chConfig;
        }


        public void setFilter(Event event) {
            filterEvent = event;
        }


        public Event getFilter() {
            return filterEvent;
        }
    }


}
