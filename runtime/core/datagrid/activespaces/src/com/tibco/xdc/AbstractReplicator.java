package com.tibco.xdc;

import com.tibco.as.space.Tuple;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.tibjms.TibjmsConnectionFactory;
import com.tibco.xdc.Common.MsgHeaders;

import javax.jms.*;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import static com.tibco.xdc.Common.PROP_XDC_EMS_SERVER_URL;
import static com.tibco.xdc.Common.PROP_XDC_RELAY_Q_NAME;

/*
 * Author: Ashwin Jayaprakash / Date: 1/14/13 / Time: 3:28 PM
 */
abstract class AbstractReplicator<X> {
    static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(AbstractReplicator.class);

    Cluster cluster;

    private QueueLink queueLink;

    final ConcurrentHashMap<EntityDao, X> listeners;

    AbstractReplicator() {
        this.listeners = new ConcurrentHashMap<EntityDao, X>();
    }

    final void start(Cluster cluster, boolean pushOrPull) throws JMSException {
        this.cluster = cluster;

        String emsServer = System.getProperty(PROP_XDC_EMS_SERVER_URL, "tcp://localhost:7222");
        this.queueLink = new QueueLink(emsServer, null, null, PROP_XDC_RELAY_Q_NAME, pushOrPull);
        this.queueLink.connect();
        LOGGER.log(Level.INFO, "Connected [%s] to [%s]", getClass().getName(), queueLink.serverUrl);
    }

    final boolean isSeeder() {
        Properties properties = cluster.getRuleServiceProvider().getProperties();
        String seeder = properties.getProperty(SystemProperty.CLUSTER_NODE_ISSEEDER.getPropertyName(), "false");

        return seeder.equalsIgnoreCase("true");
    }


    public final void register(EntityDao entityDao) {
        X x = doRegister(entityDao);

        if (x != null) {
            listeners.put(entityDao, x);
        }
    }

    abstract X doRegister(EntityDao entityDao);

    void send(String spaceName, boolean putOrTake, Tuple tuple) throws Exception {
        queueLink.send(spaceName, putOrTake, tuple);
    }

    void onMessage(String spaceName, boolean putOrTake, byte[] bytes) throws Exception {
    }

    public final void stop() {
        for (Entry<EntityDao, X> entry : listeners.entrySet()) {
            doStop(entry.getKey(), entry.getValue());
        }

        queueLink.disconnect();
    }

    abstract void doStop(EntityDao entityDao, X x);

    //-------------

    private class QueueLink implements MessageListener {
        final String serverUrl;

        final String userName;

        final String password;

        final String queueName;

        final boolean pushOrPull;

        ConnectionFactory factory;

        Connection connection;

        Session session;

        Destination destination;

        MessageProducer producer;

        MessageConsumer consumer;

        private QueueLink(String serverUrl, String userName, String password, String queueName, boolean pushOrPull) {
            this.serverUrl = serverUrl;
            this.userName = userName;
            this.password = password;
            this.queueName = queueName;
            this.pushOrPull = pushOrPull;
        }

        void connect() throws JMSException {
            factory = new TibjmsConnectionFactory(serverUrl);
            connection = factory.createConnection(userName, password);
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(queueName);
            if (pushOrPull) {
                producer = session.createProducer(destination);
            }
            else {
                consumer = session.createConsumer(destination);
                consumer.setMessageListener(this);
            }

            connection.start();

            LOGGER.log(Level.INFO, "Connected to JMS server [%s] and queue [%s] as [%s]",
                    serverUrl, queueName, (pushOrPull ? "pusher" : "puller"));
        }

        void send(String spaceName, boolean putOrTake, Tuple tuple) throws Exception {
            byte[] bytes = tuple.serialize();

            MapMessage message = session.createMapMessage();

            message.setString(MsgHeaders.space_name.name(), spaceName);
            message.setBoolean(MsgHeaders.put_or_take.name(), putOrTake);
            message.setBytes(MsgHeaders.tuple_bytes.name(), bytes);

            producer.send(message);
        }

        @Override
        public void onMessage(Message message) {
            MapMessage mapMessage = (MapMessage) message;

            try {
                String spaceName = mapMessage.getString(MsgHeaders.space_name.name());
                boolean putOrTake = mapMessage.getBoolean(MsgHeaders.put_or_take.name());
                byte[] bytes = mapMessage.getBytes(MsgHeaders.tuple_bytes.name());

                AbstractReplicator.this.onMessage(spaceName, putOrTake, bytes);
            }
            catch (Throwable t) {
                LOGGER.log(Level.ERROR, t, "Error occurred while processing message [%s] from queue", message);
            }
        }

        void disconnect() {
            try {
                if (producer != null) {
                    producer.close();
                }
            }
            catch (JMSException e) {
            }
            try {
                if (consumer != null) {
                    consumer.close();
                }
            }
            catch (JMSException e) {
            }
            try {
                session.close();
            }
            catch (JMSException e) {
            }
            try {
                connection.close();
            }
            catch (JMSException e) {
            }

            LOGGER.log(Level.INFO, "Disconnected from JMS server [%s] and queue [%s]", serverUrl, queueName);
        }
    }
}
