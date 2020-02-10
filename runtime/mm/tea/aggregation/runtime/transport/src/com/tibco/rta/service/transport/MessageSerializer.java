package com.tibco.rta.service.transport;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/11/12
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MessageSerializer<M extends MessageSerializationContext> {

    /**
     *
     * @param toSerialize
     * @param context
     * @return
     */
    public Object serialize(Message toSerialize, M context, boolean commit) throws Exception;
}
