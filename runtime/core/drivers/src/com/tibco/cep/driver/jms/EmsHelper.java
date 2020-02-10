package com.tibco.cep.driver.jms;

import javax.jms.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Properties;


/**
 * User: nprade
 * Date: 5/21/12
 * Time: 3:34 PM
 */
public class EmsHelper {


    public static final int NO_ACKNOWLEDGE = 22; // com.tibco.tibjms.Tibjms.NO_ACKNOWLEDGE
    public static final int EXPLICIT_CLIENT_ACKNOWLEDGE = 23; // com.tibco.tibjms.Tibjms.EXPLICIT_CLIENT_ACKNOWLEDGE
    public static final int EXPLICIT_CLIENT_DUPS_OK_ACKNOWLEDGE = 24;

    public static final int DEFAULT_ACK_MODE;
    public static final Class<? extends Connection> CONNECTION_CLASS;
    public static final Class<? extends ConnectionFactory> CONNECTION_FACTORY_CLASS;


    private static final boolean EMS_AVAILABLE;


    static {
        int defaultAckMode;
        boolean emsAvailable;
        Class<? extends Connection> connectionClass;
        Class<? extends ConnectionFactory> connectionFactoryClass;
        try {
            defaultAckMode = EXPLICIT_CLIENT_DUPS_OK_ACKNOWLEDGE;
            connectionClass = com.tibco.tibjms.TibjmsConnection.class;
            connectionFactoryClass = com.tibco.tibjms.TibjmsConnectionFactory.class;
            emsAvailable = true;
        }
        catch (Throwable t) {
            emsAvailable = false;
            defaultAckMode = Session.CLIENT_ACKNOWLEDGE;
            connectionClass = null;
            connectionFactoryClass = null;
        }
        DEFAULT_ACK_MODE = defaultAckMode;
        CONNECTION_CLASS = connectionClass;
        CONNECTION_FACTORY_CLASS = connectionFactoryClass;
        EMS_AVAILABLE = emsAvailable;
    }


    // Copied from EMS sample
    private static SecureRandom createUnsecureRandom()
            throws JMSSecurityException {

        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(System.currentTimeMillis());
            return sr;

        }
        catch (NoSuchAlgorithmException e) {
            JMSSecurityException jmse =
                    new JMSSecurityException("Error creating SecureRandom object: " + e.getMessage());
            jmse.setLinkedException(e);
            throw jmse;
        }
    }


    public static void initRandomNumberGenerator()
            throws JMSSecurityException {

        if (EMS_AVAILABLE) {
            com.tibco.tibjms.TibjmsSSL.setSecureRandom(createUnsecureRandom());
        }
    }


    public static boolean isTibcoJMS(
            Connection tConnection,
            Connection qConnection) {

        if (null != tConnection) {
            return isTibcoJMS(tConnection);
        }
        else {
            return isTibcoJMS(qConnection);
        }
    }


    public static boolean isTibcoJMS(
            Connection connection) {

        return EMS_AVAILABLE
                && (null != connection)
                && CONNECTION_CLASS.isAssignableFrom(connection.getClass());
    }


    public static ConnectionFactory newConnectionFactory(
            String providerURL,
            Properties sslProperties) {
        return new com.tibco.tibjms.TibjmsConnectionFactory(providerURL, null, sslProperties);
    }


    public static QueueConnectionFactory newQueueConnectionFactory(
            String providerURL,
            Properties sslProperties) {

        return new com.tibco.tibjms.TibjmsQueueConnectionFactory(providerURL, null, sslProperties);
    }


    public static TopicConnectionFactory newTopicConnectionFactory(
            String providerURL,
            Properties sslProperties) {

        return new com.tibco.tibjms.TibjmsTopicConnectionFactory(providerURL, null, sslProperties);
    }


    public static void setAllowCloseInCallback() {

        if (EMS_AVAILABLE) {
            com.tibco.tibjms.Tibjms.setAllowCloseInCallback(true);
        }
    }


    public static void setExceptionOnFtEvents(
            boolean callExceptionListener) {

        if (EMS_AVAILABLE) {
            try {
                com.tibco.tibjms.Tibjms.class
                        .getMethod("setExceptionOnFTEvents", boolean.class)
                        .invoke(null, callExceptionListener);
            } catch (Exception ignored) {
                // Old EMS with no Tibjms.setExceptionOnFTEvents(boolean)
            }
        }
    }


    public static void setSslPassword(
            Object connectionFactoryFromJndi,
            String admFactorySslPassword) {

        if (EMS_AVAILABLE
                && (null != admFactorySslPassword)
                && (!admFactorySslPassword.isEmpty())
                && (null != connectionFactoryFromJndi)
                && CONNECTION_FACTORY_CLASS.isAssignableFrom(connectionFactoryFromJndi.getClass())) {

            ((com.tibco.tibjms.TibjmsConnectionFactory) connectionFactoryFromJndi)
                    .setSSLPassword(admFactorySslPassword);
        }

    }

}
