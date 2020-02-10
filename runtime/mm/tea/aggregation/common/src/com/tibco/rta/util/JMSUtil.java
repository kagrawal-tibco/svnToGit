package com.tibco.rta.util;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 22/3/13
 * Time: 2:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class JMSUtil {

    /**
     *
     * @param bytesMessage
     * @return
     * @throws JMSException
     */
    public static byte[] readBytesFrom(BytesMessage bytesMessage) throws JMSException {
        int bodyLength = (int) bytesMessage.getBodyLength();
        byte[] bytes = new byte[bodyLength];
        bytesMessage.readBytes(bytes);
        return bytes;
    }

    /**
     *
     * @param message
     * @param <M>
     * @return
     * @throws JMSException
     */
    @SuppressWarnings("unchecked")
    public static <M extends Message> Properties readJMSProperties(M message) throws JMSException {
        Properties properties = new Properties();
        Enumeration<String> propertyNames = message.getPropertyNames();
        while (propertyNames.hasMoreElements()) {
            String propertyName = propertyNames.nextElement();
            String propertyValue = message.getStringProperty(propertyName);
            if (propertyValue != null) {
                properties.put(propertyName, propertyValue);
            }
        }
        return properties;
    }
}
