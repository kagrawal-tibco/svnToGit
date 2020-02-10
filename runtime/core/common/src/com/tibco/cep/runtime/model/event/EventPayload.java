package com.tibco.cep.runtime.model.event;


import com.tibco.xml.datamodel.XiNode;


/**
 * Represents the payload of a {@link SimpleEvent}.
 * Every implementation of this interface should have a public constuctor with these parameters:
 * <ul>
 * <li><code>TypeManager.TypeDescriptor</code> - describes the type of the event,</li>
 * <li><code>byte[]</code> - contains the serialized form of the payload.</li>
 * </ul>
 * <p>For example:</p>
 * <blockquote><code>public XyzPayload(TypeManager.TypeDescriptor descriptor, byte[] buf) throws Exception {<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;...<br />
 * }</code></blockquote>
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface EventPayload {


    /**
     * Name of the payload property in the event schema.
     *
     * @.category public-api
     * @since 2.0.0
     */
    public static final String PAYLOAD_PROPERTY = "payload";


    void toXiNode(XiNode root);


    /**
     * Serializes this <code>EventPayload</code> to a byte array.
     *
     * @return a serialization of this <code>EventPayload</code>.
     * @throws Exception
     * @.category public-api
     * @since 2.0.0
     */
    byte[] toBytes() throws Exception;


    /**
     * Gets the payload as received by the underlying transport
     * of the associated {@link com.tibco.cep.runtime.channel.Channel Channel}.
     *
     * @return the payload as received by the underlying transport
     *         of the associated {@link com.tibco.cep.runtime.channel.Channel Channel}.
     * @since 2.0.0
     */
    //TODO - SS: need to confirm or correct the description, maybe make it public
    Object getObject();
}
