package com.tibco.cep.runtime.channel;


import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.tibco.cep.designtime.model.service.channel.WebApplicationDescriptor;


/**
 * Holds all the configuration data for one <code>Channel</code> instance.
 *
 * @version 2.0
 * @since 2.0
 */
public interface ChannelConfig {


    /**
     * Gets the method of configuration for the properties of the configured <code>Channel</code>.
     *
     * @return A member of <code>ConfigurationMethod.getValues()</code>.
     * @see #getProperties()
     * @see #getReferenceURI()
     * @since 2.0
     */
    ConfigurationMethod getConfigurationMethod();


    /**
     * Returns all the destinations of the configured <code>Channel</code>.
     *
     * @return a Collection of <code>Channel.Destination</code>.
     * @since 2.0
     */
    Collection getDestinations();


    /**
     * Gets the name of the configured <code>Channel</code>.
     *
     * @return a String.
     * @see #getURI()
     * @since 2.0
     */
    String getName();


    /**
     * Gets the <code>Properties</code> describing all channel properties of the configured <code>Channel</code>.
     * The list of properties available is defined in the matching <code>drivers.xml</code>.
     * <p>Valid only when the channel is configured through properties.</p>
     *
     * @return a Properties.
     * @see #getConfigurationMethod()
     * @since 2.0
     */
    Properties getProperties();


    /**
     * Gets the URI of the shared resource used to configure the channel.
     * <p>Valid only when the channel is configured through a reference.</p>
     *
     * @return a String URI.
     * @see #getConfigurationMethod()
     * @since 2.0
     */
    String getReferenceURI();


    /**
     * Gets the name of the <code>ChannelDriver</code> for the configured <code>Channel</code>.
     *
     * @return a String.
     * @since 2.0
     */
    String getType();

    /**
     * Gets the Server Type of the <code>ChannelDriver</code> for the configured <code>Channel</code>.
     * <p>
     * <b>
     * Used for HTTP based channel
     * </b>
     * </p>
     * @return a String.
     * @since 2.0
     */
    String getServerType();

    /**
     * Gets the cannonical path of the configured <code>Channel</code>.
     *
     * @return a String
     * @since 2.0
     */
    String getURI();
    
    
    /**
     * Tests whether the channel has not been marked for deactivation.
     * 
     * @return a boolean
     * @since 5.0.1-HF1
     */
    boolean isActive();
    

    List<WebApplicationDescriptor> getWebApplicationDescriptors();


    /**
     * Type safe enum representing the method of configuration for channel properties.
     *
     * @version 2.0
     * @since 2.0
     */
    public static class ConfigurationMethod {


        /**
         * Represents the method of configuration using a reference to a shared resource.
         *
         * @since 2.0
         */
        public static final ConfigurationMethod REFERENCE = new ConfigurationMethod();


        /**
         * Represents the method of configuration using a set of properties.
         *
         * @since 2.0
         */
        public static final ConfigurationMethod PROPERTIES = new ConfigurationMethod();


        /**
         * Constructs a ConfigurationMethod.
         *
         * @since 2.0
         */
        protected ConfigurationMethod() {
        }


        /**
         * Final version of the default <code>equals</code>.
         *
         * @since 2.0
         */
        public final boolean equals(Object o) {
            return super.equals(o);
        }


        /**
         * Final version of the default <code>hashcode</code>.
         *
         * @since 2.0
         */
        public int hashCode() {
            return super.hashCode();
        }


    }

}
