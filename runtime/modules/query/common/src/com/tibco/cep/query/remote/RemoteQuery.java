package com.tibco.cep.query.remote;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 12, 2007
 * Time: 12:38:50 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RemoteQuery extends RemotableComponent {
    /**
     *
     * @param serializer
     */
    public void serializeQuery(RemoteQuerySerializer serializer);
    /**
     *
     * @param deserializer
     */
    public void deserializeQuery(RemoteQueryDeSerializer deserializer);
}
