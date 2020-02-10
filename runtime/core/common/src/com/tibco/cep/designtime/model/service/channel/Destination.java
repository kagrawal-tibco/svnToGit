package com.tibco.cep.designtime.model.service.channel;


import java.util.Properties;

import com.tibco.cep.designtime.model.Entity;

/*
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 7:10:27 PM
 */


public interface Destination extends Entity {


    /**
     * @return the DriverConfig that contains this Destination.
     */
    DriverConfig getDriverConfig();


    /**
     * Returns Properties of that destination.
     *
     * @return Properties.
     */
    Properties getProperties();


    /**
     * Returns the name of the Destination
     *
     * @return a String
     */
    String getName();


    /**
     * Returns the URI of the Event associated to this Destination.
     *
     * @return a String
     */
    String getEventURI();


    /**
     * Returns the class which acts as a serializer/deserializer for this destination
     *
     * @return a String
     */
    String getSerializerDeserializerClass();
    
    /**
     * Returns true if the destination is enabled for input
     * @return boolean
     */
    public boolean isInputEnabled();
    
    /**
     * @return true if this Destination is enabled for Output.
     */
    public boolean isOutputEnabled();

}
