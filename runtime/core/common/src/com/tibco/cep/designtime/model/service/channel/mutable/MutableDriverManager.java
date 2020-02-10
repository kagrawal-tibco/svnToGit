package com.tibco.cep.designtime.model.service.channel.mutable;


import java.util.List;

import com.tibco.cep.designtime.model.service.channel.ChannelDescriptor;
import com.tibco.cep.designtime.model.service.channel.DestinationDescriptor;
import com.tibco.cep.designtime.model.service.channel.DriverManager;


/*
 * User: nprade
 * Date: Jul 23, 2004
 * Time: 7:13:51 PM
 */

public interface MutableDriverManager extends DriverManager {


    /**
     * Returns a new DriverConfig with the requested type for the Channel specified.
     *
     * @param type    a String identifying the MutableDriverConfig to return.
     * @param channel the MutableChannel for which that MutableDriverConfig is built
     * @return the MutableDriverConfig, or null if no MutableDriverConfig could be created.
     */
    public MutableDriverConfig createDriverConfig(String type, MutableChannel channel);


    /**
     * Removes the DriverConfig associated to the given type from the registry,
     * if it was registered.
     *
     * @param type a String identifying the DriverConfig to deregister.
     * @return the MutableDriverManager itself.
     */
    public MutableDriverManager deregisterDriver(String type);


    /**
     * @param type    the String type of the DriverConfig
     * @param version the String version of the DriverConfig
     * @return true iif this version of this type can be registered.
     */
    public boolean canRegister(String type, String version);


    /**
     * Registers a DriverConfig so it can be later retrieved with {@link #createDriverConfig}
     *
     * @param type          the type under which the DriverConfig is registered.
     * @param label         the label for the DriverConfig.
     * @param version               String version
     * @param description           String description.
     * @param channelDescriptor     ChannelDescriptor that defines the properties of the DriverConfig.
     * @param destinationDescriptor DestinationDescriptor that defines the Destination objects of the DriverConfig.
     * @param resourceTypes an array of allowed resource types, used while configuring the DriverConfig by reference.
     * @return the MutableDriverManager itself.
     */
    public MutableDriverManager registerDriver(
            String type, String label, String version, String description,
            ChannelDescriptor channelDescriptor, DestinationDescriptor destinationDescriptor,
            String[] resourceTypes);


    List getPropertyConfigurations(String driverType);
}//interface
