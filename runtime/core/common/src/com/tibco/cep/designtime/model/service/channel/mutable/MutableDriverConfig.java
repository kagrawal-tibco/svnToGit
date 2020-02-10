package com.tibco.cep.designtime.model.service.channel.mutable;


import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.service.channel.Destination;
import com.tibco.cep.designtime.model.service.channel.DriverConfig;

/*
 * User: nprade
 * Date: Jul 23, 2004
 * Time: 7:09:15 PM
 */


/**
 * Represents the medium used by a MutableChannel to send/receive data.
 */
public interface MutableDriverConfig extends DriverConfig {


    /**
     * @return a String that is an unused valid name for a new Destination
     */
    public String findNewDestinationName();


    /**
     * Adds a new empty Destination to this DriverConfig and returns it.
     *
     * @return the new MutableDestination.
     */
    public MutableDestination createDestination();


    /**
     * Adds a Destination to this DriverConfig.
     *
     * @param destination the Destination to add.
     * @return the MutableDriverConfig itself.
     * @throws com.tibco.cep.designtime.model.ModelException
     *          if this MutableDestination's properties do not match the Destination Concept.
     */
    public MutableDriverConfig addDestination(Destination destination) throws ModelException;


    /**
     * Changes the method of configuration (CONFIG_BY_REFERENCE, CONFIG_BY_PROPERTIES, etc).
     *
     * @param type String
     * @return the MutableDriverConfig itself.
     */
    public MutableDriverConfig setConfigMethod(String type);


    /**
     * Deregisters a Destination from this DriverConfig.
     *
     * @param destination an Instance of destination.
     * @return the MutableDriverConfig itself.
     */
    public MutableDriverConfig removeDestination(Destination destination);


    /**
     * @param ref a reference to a resource that represents the DriverConfig config.
     * @return the MutableDriverConfig itself.
     */
    public MutableDriverConfig setReference(String ref);


    /**
     * Deletes all Destinations from this DriverConfig.
     *
     * @return the MutableDriverConfig itself.
     */
    public MutableDriverConfig clearDestinations();

    void setServerType(String serverType);

}//class
