package com.tibco.cep.runtime.model.event;


import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.element.ExtIdAlreadyBoundException;
import com.tibco.cep.runtime.model.serializers.SerializableLite;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.datamodel.XiNode;

import java.util.Set;


/**
 * Base for events that have properties and/or a payload.
 *
 * @version 2.0.0
 * @.category public-api
 * @see TimeEvent
 * @since 2.0.0
 */
public interface SimpleEvent extends Event, SerializableLite {


    void setExtId(String extId) throws ExtIdAlreadyBoundException;


    /**
     * Sets the value of a property by name.
     *
     * @param name  name of the event property.
     * @param value the <code>Object</code> value to be set.
     *              The type of value must be consistent with the type defined in the model.
     * @throws NoSuchFieldException if there is no property in the event with the specified name.
     * @.category public-api
     * @since 2.0.0
     */
    public void setProperty(String name, Object value) throws Exception;


    /**
     * Sets the value of a property by name.
     *
     * @param name  name of the event property.
     * @param value the <code>String</code> value to be set.
     *              The type of value must be consistent with the type defined in the model.
     * @throws NoSuchFieldException if there is no property in the event with the specified name.
     * @.category public-api
     * @since 2.0.0
     */
    public void setProperty(String name, String value) throws Exception;


    public void setProperty(String name, XmlTypedValue value) throws Exception;


    /**
     * Gets the value of a property by name.
     *
     * @param name name of the event property.
     * @return the value of that property.
     * @throws NoSuchFieldException if there is no property in the event with the specified name.
     * @.category public-api
     * @since 2.0.0
     */
    public Object getProperty(String name) throws NoSuchFieldException;


    public XmlTypedValue getPropertyAsXMLTypedValue(String name) throws Exception;


    /**
     * Gets all the property names in this event.
     *
     * @return all the Property names in String[]
     * @.category public-api
     * @since 2.0.0
     */
    public String[] getPropertyNames();


    /**
     * Gets the payload of this event.
     *
     * @return the payload of this event, if any, else <code>null</code>.
     * @.category public-api
     * @since 2.0.0
     */
    public EventPayload getPayload();


    /**
     * Gets the <code>String</code> representation of the payload of this event.
     *
     * @return the <code>String</code> representation of the payload of this event, if any, else <code>null</code>.
     * @.category public-api
     * @since 2.0.0
     */
    public String getPayloadAsString();


    /**
     * Sets the payload of this event.
     *
     * @param payload the <code>EventPayload</code> to set.
     * @.category public-api
     * @since 2.0.0
     */
    public void setPayload(EventPayload payload);


    /**
     * Gets the <code>EventContext</code> of this event.
     *
     * @return the <code>EventContext</code> of this event.
     * @.category public-api
     * @since 2.0.0
     */
    public EventContext getContext();


    /**
     * Sets the <code>EventContext</code> of this event.
     *
     * @param ctx the <code>EventContext</code> to set.
     * @.category public-api
     * @since 2.0.0
     */
    public void setContext(EventContext ctx);


    public void toXiNode(XiNode root) throws Exception;


    /**
     * Gets the URI of the default destination of this event.
     *
     * @return the URI of the default destination of this event.
     * @.category public-api
     * @since 2.0.0
     */
    public String getDestinationURI();


    /**
     * Gets the <code>ExpandedName</code> of this event.
     *
     * @return the <code>ExpandedName</code> of this event.
     * @.category public-api
     * @since 2.0.0
     */
    public ExpandedName getExpandedName();


    public void acknowledge();


    void serialize(EventSerializer serializer);


    void deserialize(EventDeserializer deserializer);


    boolean getRetryOnException();

    boolean isAcknowledged();

    Set getPropertyNamesAsSet();

    
}

