package com.tibco.cep.driver.jms.serializer;

import javax.jms.BytesMessage;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;


/**
 * User: nicolas
 * Date: 8/28/14
 * Time: 12:59 PM
 */
public class DeserializerProvider
{

    private final AbstractMessageSerializer bytesMessageDeserializer = new BytesMessageSerializer();
    private final AbstractMessageSerializer mapMessageDeserializer = new MapMessageSerializer();
    private final AbstractMessageSerializer objectMessageDeserializer = new ObjectMessageSerializer();
    private final AbstractMessageSerializer TextMessageDeserializer = new TextMessageSerializer();


    AbstractMessageSerializer getDeserializer(
            Object message)
    {
        if (message instanceof BytesMessage) {
            return this.bytesMessageDeserializer;
        }
        else if (message instanceof MapMessage) {
            return this.mapMessageDeserializer;
        }
        else if (message instanceof ObjectMessage) {
            return this.objectMessageDeserializer;
        }
        else if (message instanceof TextMessage) {
            return this.TextMessageDeserializer;
        }
        else {
            return null;
        }
    }


    public static DeserializerProvider getInstance()
    {
        return LazySingletonHolder.INSTANCE;
    }



    private static class LazySingletonHolder
    {

        private static final DeserializerProvider INSTANCE = new DeserializerProvider();

    }


}


