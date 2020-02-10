package com.tibco.cep.designtime.model.service.channel;


import java.util.Iterator;
import java.util.Map;

/*
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 8:42:39 PM
 */


public interface DriverManager {


    /**
     * Finds the allowed resource types for the requested type.
     *
     * @param type a String identifying the DriverConfig type.
     * @return a String[], or null if no DriverConfig was found for this type.
     */
    String[] getAllowedResourceTypes(String type);


    /**
     * Finds a description of the Driver registered under the given type.
     *
     * @param type a String identifying the DriverConfig type.
     * @return a String, or null if no DriverConfig was found for this type.
     */
    String getDescription(String type);


    /**
     * Finds all types registered with this DriverManager.
     *
     * @return an Iterator of Strings.
     */
    Iterator getDriverTypes();


    /**
     * Finds the labels for all types registered with this DriverManager.
     *
     * @return a Map of String type to String label.
     */
    Map getDriverLabelsByTypes();


    /**
     * Gets the ChannelDescriptor for the requested type.
     *
     * @param type a String identifying the DriverConfig.
     * @return ChannelDescriptor
     */
    ChannelDescriptor getChannelDescriptor(String type);
    

    /**
     * Returns the DestinationDescriptor for the requested type.
     *
     * @param type a String identifying the DriverConfig.
     * @return DestinationDescriptor.
     */
    DestinationDescriptor getDestinationDescriptor(String type);


    /**
     * Finds the version of the Driver registration for the given type.
     *
     * @param type a String identifying the DriverConfig type.
     * @return a String, or null if no DriverConfig was found for this type.
     */
    String getVersion(String type);

    ExtendedConfiguration getExtendedConfiguration();


    boolean isSerializerUserDefined(String type);


    String[] getSerializerTypes(String type);


    String[] getSerializerClasses(String type);

    String getDefaultSerializer(String type);
}
