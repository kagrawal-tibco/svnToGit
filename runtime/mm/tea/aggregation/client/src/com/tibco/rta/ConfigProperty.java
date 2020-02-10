package com.tibco.rta;


import com.tibco.rta.annotations.Internal;
import com.tibco.rta.annotations.Mandatory;
import com.tibco.rta.log.impl.DefaultLogManager;
import com.tibco.rta.model.DataType;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.property.impl.PropertyAtomBoolean;
import com.tibco.rta.property.impl.PropertyAtomInt;
import com.tibco.rta.property.impl.PropertyAtomLong;
import com.tibco.rta.property.impl.PropertyAtomObject;
import com.tibco.rta.property.impl.PropertyAtomString;

import java.util.Map;
import java.util.Properties;

/**
 * An enumeration of the client API configuration properties.
 */
public enum ConfigProperty {

    /**
     * Minimum number of threads to be used for fact publishing per session.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.INTEGER, defaultValue = "5")
    TASK_MANAGER_THREADPOOL_SIZE("tea.agent.client.taskmgr.threadpool.size", DataType.INTEGER, new PropertyAtomInt(5)),

    /**
     * Keep alive time in seconds for task manager threads. When this time elapses,
     * idle threads will be timed out.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.INTEGER, defaultValue = "60")
    TASK_MANAGER_THREAD_KEEPALIVE_TIME("tea.agent.client.taskmgr.threadpool.keepalive.time", DataType.INTEGER, new PropertyAtomInt(60)),

    /**
     * TRANSPORT TYPE :: JMS, HTTP, TCP
     */
    @Internal
    @com.tibco.rta.annotations.DataType(dataType = DataType.STRING, defaultValue = "JMS")
    TRANSPORT_TYPE("tea.agent.config.transport.type", DataType.STRING, new PropertyAtomString("JMS")),

    /**
     * Server hostname property for HTTP transport.
     */
    @Internal
    @com.tibco.rta.annotations.DataType(dataType = DataType.STRING, defaultValue = "localhost")
    TRANSPORT_HTTP_HOSTNAME("tea.agent.config.transport.http.host",  DataType.STRING, new PropertyAtomString("localhost")),

    /**
     * Server port for HTTP transport.
     */
    @Internal
    @com.tibco.rta.annotations.DataType(dataType = DataType.INTEGER, defaultValue = "4448")
    TRANSPORT_HTTP_PORT("tea.agent.config.transport.http.port", DataType.INTEGER, new PropertyAtomInt(4448)),

    /**
     * Whether to publish fact over transport
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.BOOLEAN, defaultValue = "true")
    @Internal
    FACT_PUBLISH("tea.agent.client.fact.publish", DataType.BOOLEAN, new PropertyAtomBoolean(true)),
    
    /**
     * Queue depth for the internal queue for batching facts.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.INTEGER, defaultValue = "1000")
    FACT_QUEUE_DEPTH("tea.agent.client.fact.queue.depth", DataType.INTEGER, new PropertyAtomInt(1000)),

    /**
     * Number of facts to batch before publishing to the server.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.INTEGER, defaultValue = "1")
    FACT_BATCH_SIZE("tea.agent.client.fact.batch.size", DataType.INTEGER, new PropertyAtomInt(1)),

    /**
     * Enable/Disable async eviction of facts once queue depth is full. True by default.
     * If disabled, eviction is done by thread putting the fact.
     * <p>
     * Disabling this may result in more facts being lost if fact batch size is on the higher
     * side and put rate is much more than the consumption rate.
     * </p>
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.BOOLEAN, defaultValue = "true")
    FACT_EVICTION_ENABLED("tea.agent.client.fact.eviction.enabled", DataType.BOOLEAN, new PropertyAtomBoolean(true)),

    /**
     * Delay in milliseconds for eviction thread to run. Only used if
     * #FACT_EVICTION_ENABLED is set to true.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.INTEGER, defaultValue = "100")
    FACT_EVICTION_FREQUENCY("tea.agent.client.fact.eviction.frequency", DataType.INTEGER, new PropertyAtomInt(100)),

    /**
     * If batch size condition is not met, the amount of time
     * in milliseconds to wait to flush residual facts.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.INTEGER, defaultValue = "5000")
    FACT_BATCH_EXPIRY("tea.agent.client.fact.batch.expiry", DataType.INTEGER, new PropertyAtomInt(5000)),

    /**
     * Time interval in milliseconds used for ping with the server.
     */
    @Internal
    @com.tibco.rta.annotations.DataType(dataType = DataType.LONG, defaultValue = "1000L")
    PING_INTERVAL("tea.agent.client.ping.interval", DataType.LONG, new PropertyAtomLong(1000L)),

    /**
     * Time interval in milliseconds used by named client sessions to send heartbeat.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.LONG, defaultValue = "10000L")
    HEARTBEAT_INTERVAL("tea.agent.client.heartbeat.interval", DataType.LONG, new PropertyAtomLong(10000L)),

    /**
     * Username for metric engine.
     */
    @Mandatory
    @com.tibco.rta.annotations.DataType(dataType = DataType.STRING, defaultValue = "admin")
    CONNECTION_USERNAME("tea.agent.client.connection.username", DataType.STRING, new PropertyAtomString("admin")),

    /**
     * Password for metric engine.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.STRING, defaultValue = "")
    CONNECTION_PASSWORD("tea.agent.client.connection.password", DataType.STRING, new PropertyAtomString("#!I6vDE1jCZJvziHeEoH+4ww==")),

    /**
     * Maximum retries for a synchronous operation in case it fails.
     * Default is 3 times.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.INTEGER, defaultValue = "3")
    NUM_RETRIES_SYNC_OPS("tea.agent.client.retry.count", DataType.INTEGER, new PropertyAtomInt(3)),

    /**
     * Maximum retries for a synchronous operation in case it fails.
     * Default is 3 times.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.INTEGER, defaultValue = "2147483647")
    NUM_RETRIES_FACT_OPS("tea.agent.client.facts.retry.count", DataType.INTEGER, new PropertyAtomInt(Integer.MAX_VALUE)),


    /**
     * Maximum retries for a for establishing connection to transport provider if any.
     * Default is 0x7fffffff times.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.INTEGER, defaultValue = "2147483647")
    NUM_RETRIES_CONNECTION_ESTABLISH("tea.agent.client.connection.establish", DataType.INTEGER, new PropertyAtomInt(Integer.MAX_VALUE)),

    /**
     * Time in milliseconds to wait before retrying the operation
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.LONG, defaultValue = "10000L")
    RETRY_WAIT_INTERVAL("tea.agent.client.retry.wait", DataType.LONG, new PropertyAtomLong(10000L)),

    /**
     * Time in milliseconds to wait for rta engine to wait for server
     * to send response for a sync operation like query registration.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.LONG, defaultValue = "10000L")
    SYNC_RESPONSE_TIMEOUT("tea.agent.client.sync.response.timeout", DataType.LONG, new PropertyAtomLong(10000L)),

    /**
     * Time in milliseconds for JMS provider to keep a message before
     * it expires.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.LONG, defaultValue = "300000L")
    SYNC_JMS_MESSAGE_EXPIRY("tea.agent.client.sync.jms.msg.expiry", DataType.LONG, new PropertyAtomLong(300000L)),

    /**
     * Specify external log manager impl class.
     *
     * @see com.tibco.rta.log.LogManager
     * @see com.tibco.rta.log.LogManagerFactory
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.STRING, defaultValue = "com.tibco.rta.log.impl.DefaultLogManager")
    LOG_MANAGER_CLASS_NAME("tea.agent.client.logmanager.class", DataType.STRING, new PropertyAtomString("com.tibco.rta.log.impl.DefaultLogManager")),

    /**
     * Specify external log manager impl. Either #LOG_MANAGER_CLASS_NAME or #LOG_MANAGER should be used.
     *
     * @see com.tibco.rta.log.LogManager
     * @see com.tibco.rta.log.LogManagerFactory
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.OBJECT, defaultValue = "")
    LOG_MANAGER("tea.agent.client.logmanager", DataType.OBJECT, new PropertyAtomObject(new DefaultLogManager())),

    /**
     * JMS JNDI URL.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.STRING, defaultValue = "tibjmsnaming://localhost:7222")
    JMS_PROVIDER_JNDI_URL("tea.agent.client.jms.jndi.url", DataType.STRING, new PropertyAtomString("tibjmsnaming://localhost:7222")),

    /**
     * Context factory class for the JMS provider.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.STRING, defaultValue = "com.tibco.tibjms.naming.TibjmsInitialContextFactory")
    JMS_PROVIDER_JNDI_FACTORY("tea.agent.client.jms.jndi.contextfactory", DataType.STRING, new PropertyAtomString("com.tibco.tibjms.naming.TibjmsInitialContextFactory")),

    /**
     * Queue connection factory name. Needs to be pre-created.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.STRING, defaultValue = "SPMQueueConnectionFactory")
    JMS_QUEUE_CONN_FACTORY("tea.agent.client.jms.queueconnectionfactory", DataType.STRING, new PropertyAtomString("SPMQueueConnectionFactory")),

    /**
     * Queue name on JMS to perform sync/async operations.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.STRING, defaultValue = "spm.inbound.queue")
    JMS_INBOUND_QUEUE("tea.agent.client.jms.inbound.queue", DataType.STRING, new PropertyAtomString("spm.inbound.queue")),

    /**
     * Queue name on JMS to perform snapshot query operations.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.STRING, defaultValue = "spm.inbound.query.queue")
    JMS_INBOUND_QUERY_QUEUE("tea.agent.client.jms.inbound.query.queue", DataType.STRING, new PropertyAtomString("spm.inbound.query.queue")),

    /**
     * Queue name on JMS for client to receive notifications from metric engine.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.STRING, defaultValue = "spm.outbound.queue")
    JMS_OUTBOUND_QUEUE("tea.agent.client.jms.outbound.queue", DataType.STRING, new PropertyAtomString("spm.outbound.queue")),

    /**
     * JMS compress property.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.BOOLEAN, defaultValue = "true")
    JMS_MESSAGE_COMPRESS("tea.agent.client.tibjms.compress", DataType.BOOLEAN, new PropertyAtomBoolean(true)),

    /**
     * The thread pool used for dispatching results from outbound queue to clients. For instance streaming queries/commands.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.INTEGER, defaultValue = "64")
    ASYNC_RESULT_DISPATCHER_MAX_POOL_SIZE("tea.agent.client.async.dispatcher.max.pool.size", DataType.INTEGER, new PropertyAtomInt(64)),

    /**
     * The thread pool used for dispatching results from outbound queue to clients has an idle timeout in milliseconds.
     */
    @com.tibco.rta.annotations.DataType(dataType = DataType.LONG, defaultValue = "300000L")
    ASYNC_RESULT_DISPATCHER_POOL_TIMEOUT("tea.agent.client.async.dispatcher.timeout", DataType.LONG, new PropertyAtomLong(300000L));
    
    private final String propertyName;

    private DataType dataType;

    private PropertyAtom<?> defaultValue;

    ConfigProperty(String propertyName, DataType dataType, PropertyAtom<?> defaultValue) {
        this.propertyName = propertyName;
        this.dataType = dataType;
        this.defaultValue = defaultValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public static ConfigProperty getByPropertyName(String propertyName) {
        for (ConfigProperty configProperty : ConfigProperty.values()) {
            if (configProperty.getPropertyName().equals(propertyName)) {
                return configProperty;
            }
        }
        return null;
    }

    public DataType getDataType() {
        return dataType;
    }

    public PropertyAtom<?> getDefaultValue() {
        return defaultValue;
    }

    public Object getValue(Properties props) {
        Object val = props.get(propertyName);
        if (val == null) {
            return defaultValue.getValue();
        }
        if (val instanceof PropertyAtom) {
            return ((PropertyAtom) val).getValue();
        }
        return val;
    }

    public Object getValue(Map<?, ?> props) {
        Object val = props.get(this);
        if (val == null) {
            return defaultValue.getValue();
        }
        if (val instanceof PropertyAtom) {
            return ((PropertyAtom) val).getValue();
        }
        return val;
    }

}
