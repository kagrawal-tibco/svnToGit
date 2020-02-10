package com.tibco.cep.designtime.model.event.mutable;


import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.mutable.MutableRuleParticipant;


/**
 * The interface used for defining a BusinessEvent Event.
 *
 * @author ishaan
 * @version Apr 23, 2004 5:54:14 PM
 */

public interface MutableEvent extends Event, MutableRuleParticipant {


    public void setSuperEventPath(String path) throws ModelException;


    public void setPayloadSchemaAsString(String schemaString) throws ModelException;


    public void setPayloadImportRegistry(com.tibco.xml.ImportRegistry registry);


    public void setPayloadNamespaceImporter(com.tibco.xml.NamespaceImporter importer);


    public void setSpecifiedTime(long time);


    public void setInterval(String interval);


    public void setIntervalUnit(int intervalUnit);


    public void setTTL(String ttl);


    public void setSchedule(int repeating);


    public void setPropertyType(String propertyName, RDFPrimitiveTerm type);


    /**
     * Replaces existing user properties.
     *
     * @param propertyName
     * @param type
     */
    public void addUserProperty(String propertyName, RDFPrimitiveTerm type) throws ModelException;


    public void deleteUserProperty(String propertyName);


    public void clearUserProperties();


    /**
     * @param uri the URI of the Channel of the Destination associated with this Event.
     */
    public void setChannelURI(String uri);


    /**
     * @param name the String name of the Destination associated with this Event.
     */
    public void setDestinationName(String name);


    /**
     * @param timeEventCount
     */
    void setTimeEventCount(String timeEventCount);


    /**
     * @param serializationFormat
     */
    void setSerializationFormat(int serializationFormat);


    void setTTLUnits(int units);


    void setRetryOnException(boolean retry);

    
}
