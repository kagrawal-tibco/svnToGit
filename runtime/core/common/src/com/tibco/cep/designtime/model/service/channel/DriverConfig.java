package com.tibco.cep.designtime.model.service.channel;


import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;


/*
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 7:04:13 PM
 */


public interface DriverConfig {


    String CONFIG_BY_REFERENCE = "reference";
    String CONFIG_BY_PROPERTIES = "properties";
    /**
     * Drivers use their own Ontology, to prevent a mix with runtime objects.
     */
    DefaultMutableOntology ONTOLOGY = new DefaultMutableOntology();


    /**
     * @return a String representing the type of this driver.
     */
    String getDriverType();


    /**
     * @return a String representing the name of this driver.
     */
    String getLabel();


    /**
     * @return the Channel that is configured by this object.
     */
    Channel getChannel();


    /**
     * Can be used to browse available Destination objects.
     *
     * @return an Iterator of Destination objects.
     */
    Iterator getAvailableDestinations();


    /**
     * Returns the Destination objects that are registered with this DriverConfig
     *
     * @return an Iterator of Destination objects.
     */
    Iterator getDestinations();


    /**
     * Returns a shallow clone of the List of Destination objects that are registered with this DriverConfig.
     *
     * @return a List of Destination objects.
     */
    List getDestinationsList();


    /**
     * Returns the Destination object of the given name registered with this DriverConfig
     *
     * @param name String name of the destination
     * @return a Destination or null if there was no Destination with that name in this DriverConfig
     */
    Destination getDestination(String name);


    /**
     * Returns the DestinationDescriptor.
     *
     * @return DestinationDescriptor.
     */
    DestinationDescriptor getDestinationDescriptor();


    /**
     * @return Properties at the Channel level.
     */
    Properties getChannelProperties();


    /**
     * @return the type of configuration (CONFIG_BY_REFERENCE, CONFIG_BY_PROPERTIES, etc).
     */
    String getConfigMethod();


    /**
     * Makes a best effort at testing the DriverConfig with its current parameters.
     *
     * @return null if the test passed, else a String describing the problem.
     */
    String test();


    /**
     * Returns the types of the resource that can be used as a reference
     * when configuring the driver with <code>CONFIG_BY_REFERENCE</code>.
     *
     * @return an arry of String objects.
     */
    String[] getAllowedResourceTypes();


    /**
     * @return a reference to a resource that represents the DriverConfig config.
     */
    String getReference();


    /**
     * Gets the ChannelDescriptor
     *
     * @return ChannelDescriptor
     */
    ChannelDescriptor getChannelDescriptor();


    String getDefaultSerializer();


    /**
     * This configuration detail captures properties which
     * should be present on the driver irrespective of
     * method of configuration.
     * @return
     */
    ExtendedConfiguration getExtendedConfiguration();
}
