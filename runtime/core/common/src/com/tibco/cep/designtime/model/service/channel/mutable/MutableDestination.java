package com.tibco.cep.designtime.model.service.channel.mutable;


import com.tibco.cep.designtime.model.mutable.MutableEntity;
import com.tibco.cep.designtime.model.service.channel.Destination;

/*
 * User: nprade
 * Date: Jul 22, 2004
 * Time: 8:35:50 PM
 */

/**
 *
 */
public interface MutableDestination extends Destination, MutableEntity {


    /**
     * Stores the URI of the event associated to this Destination
     *
     * @param uri a String.
     * @return the Destination itself
     */
    public MutableDestination setEventURI(String uri);


    /**
     * Stores the URI of the event associated to this Destination
     *
     * @param sdc a String.
     */
    public void setSerializerDeserializerClass(String sdc);


    /**
     * Must be called before the object is destroyed.
     */
    public void delete();


}//interface
