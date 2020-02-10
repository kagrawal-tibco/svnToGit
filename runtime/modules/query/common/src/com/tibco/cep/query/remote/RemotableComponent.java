package com.tibco.cep.query.remote;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 16, 2007
 * Time: 7:12:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RemotableComponent {
    /**
     *
     * @param serializer
     */
    public void serialize(ComponentSerializer serializer);
    /**
     *
     * @param deserializer
     */
    public void deserialize(ComponentDeSerializer deserializer);
    
}
