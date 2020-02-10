package com.tibco.rta.common;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.rta.annotations.Internal;
import com.tibco.rta.annotations.Mandatory;
import com.tibco.rta.model.DataType;

/**
 * Enum to map each config property.
 */
public enum ConfigProperty {

    @Internal
    RTA_ENGINE_NAME("be.tea.agent.engine.name", "", false, "/Engine", "", "Engine name", "Engine name"),

    /**
     * Number of worker threads for common thread pool.
     * boolean isPublic, String category, String parentCategory,
     * String displayName, String description
     */
    RTA_WORKER_THREADS("be.tea.agent.worker.thread.count", "16", true, "/Engine/CommonThreadPool", "", "Worker thread pool maximum thread count", "Number of worker threads for common thread pool"),

    /**
     * Alive Time for threads for common thread pool, default 300 secs
     */
    RTA_WORKER_THREADS_ALIVE_TIME("be.tea.agent.worker.thread.idle.timeout", "300", true, "/Engine/CommonThreadPool", "", "Worker thread pool keep alive timeout(sec)", "Alive Time for threads for common thread pool, default 300 secs"),

    /**
     * Number of core pool size for worker threads pool.
     */
    RTA_WORKER_THREADS_CORE_POOL_SIZE("be.tea.agent.worker.thread.count.min", "1", true, "/Engine/CommonThreadPool", "", "Worker thread pool core pool size", "Number of core pool size for worker threads pool."),

    /**
     * Queue size for common thread pool.
     */
    RTA_WORKER_QUEUE_SIZE("be.tea.agent.worker.queue.size", "4", true, "/Engine/CommonThreadPool", "", "Worker thread pool queue size", "Queue size for common thread pool."),

    /**
     * Number of worker threads for the metrics computations thread pool.
     */
    RTA_STICKY_WORKER_THREADS("be.tea.agent.metric.compute.thread.count", "32", true, "/Engine/MetricThreadPool", "", "Metric thread pool maximum thread size", "Number of worker threads for the metric computations thread pool."),

    /**
     * Queue size for the metrics computation thread pool.
     */
    RTA_STICKY_WORKER_QUEUE_SIZE("be.tea.agent.metric.compute.queue.size", "64", true, "/Engine/MetricThreadPool", "", "Metric thread pool queue size", "Queue size for the metrics computation thread pool."),

    /**
     * Use Caller library thread if set to true for sync ops.
     */
    RTA_USE_CALLERS_THREAD("be.tea.agent.sync.callers.thread", "false", true, "/Engine", "", "Use APIs threads for compute", "Use Caller library thread if set to true for sync ops."),

    /**
     * Property indicating underlying transport
     */
    @Internal
    RTA_TRANSPORT_TYPE("be.tea.agent.config.transport.type", "JMS", false, "/Engine/Transport", "Primary transport", "", "Property indicating underlying transport, Choice of JMS or HTTP"),

    /**
     * Server host name property for HTTP transport.
     */
    @Internal
    RTA_TRANSPORT_HTTP_HOSTNAME("be.tea.agent.config.transport.http.host", "localhost", false, "/Engine/Transport/HTTP", "", "HTTP Host name", "Server host name property for HTTP transport."),

    /**
     * Server port for HTTP transport.
     */
    @Internal
    RTA_TRANSPORT_HTTP_PORT("be.tea.agent.config.transport.http.port", "4448", false, "/Engine/Transport/HTTP", "", "HTTP Port name", "Server port for HTTP transport."),

    /**
     * JMS Transport JNDI context factory.
     */
    RTA_JMS_JNDI_CONTEXT_FACTORY("be.tea.agent.jms.jndi.contextfactory", "com.tibco.tibjms.naming.TibjmsInitialContextFactory", true, "/Engine/Transport/JMS", "JMS Transport JNDI context factory", "", "JMS Transport JNDI context factory."),

    /**
     * JMS provider URL.
     */
    RTA_JMS_JNDI_URL("be.tea.agent.jms.jndi.url", "tibjmsnaming://localhost:7222", true, "/Engine/Transport/JMS", "", "JMS provider URL", "JMS provider URL."),

    /**
     * JMS queue connection factory.
     */
    RTA_JMS_QUEUE_CONN_FACTORY("be.tea.agent.jms.queueconnectionfactory", "SPMQueueConnectionFactory", true, "/Engine/Transport/JMS", "", "JMS queue connection factory", "JMS queue connection factory."),

    /**
     * JMS queue on which SPM server receives facts.
     */
    RTA_JMS_INBOUND_QUEUE("be.tea.agent.jms.inbound.queue", "spm.inbound.queue", true, "/Engine/Transport/JMS", "", "JMS inbound queue name", "JMS queue on which SPM server receives facts."),

    /**
     * JMS queue on which SPM server receives query registrations.
     */
    RTA_JMS_INBOUND_QUERY_QUEUE("be.tea.agent.jms.inbound.query.queue", "spm.inbound.query.queue", true, "/Engine/Transport/JMS", "", "JMS query queue name", "JMS queue on which SPM server receives query registrations."),

    /**
     * JMS queue over which SPM server to sends query results and other asynchronous out-bound messages.
     */
    RTA_JMS_OUTBOUND_QUEUE("be.tea.agent.jms.outbound.queue", "spm.outbound.queue", true, "/Engine/Transport/JMS", "", "JMS out-bound queue name", "JMS queue over which SPM server to sends query results and other asynchronous outbound messages."),

    /**
     * JMS provider connection user name.
     */
    RTA_JMS_CONNECTION_USERNAME("be.tea.agent.jms.connection.username", "admin", true, "/Engine/Transport/JMS", "", "JMS user name", "JMS provider connection user name."),

    /**
     * JMS provider connection password.
     */
    RTA_JMS_CONNECTION_PASSWORD("be.tea.agent.jms.connection.password", "#!I6vDE1jCZJvziHeEoH+4ww==", true, "/Engine/Transport/JMS", "JMS password", "", "JMS provider connection password."),

    /**
     * JMS provider acknowledgment mode.
     */
    RTA_JMS_ACK_MODE("be.tea.agent.jms.ack.mode", "TIBCO_EXPLICIT", true, "/Engine/Transport/JMS", "", "JMS acknowlegemode", "JMS provider acknowledgment mode."),

    /**
     * JMS message expiration for outbound messages from SPM server. Default is 5 days.
     */
    RTA_JMS_OUTBOUND_MESSAGE_EXPIRY("be.tea.agent.jms.outbound.message.expiry", "432000000", true, "/Engine/Transport/JMS", "", "JMS query results expiration time(ms)", "JMS message expiration for outbound messages from SPM server. Default is 5 days."),

    /**
     * JMS connection retry interval. Default is 5 sec.
     */
    RTA_JMS_CONNECTION_RETRY_INTERVAL("be.tea.agent.jms.connection.retry.interval", "5000", true, "/Engine/Transport/JMS", "", "JMS Connection retry interval(ms)", "JMS connection retry interval. Default is 5 sec."),

    /**
     * Server host name property for HTTP transport.
     */
    @Internal
    RTA_TRANSPORT_TCP_HOSTNAME("be.tea.agent.config.transport.tcp.host", "localhost", false, "/Engine/Transport/TCP", "", "Server host name for TCP", "Server host name property for HTTP transport."),

    /**
     * Server port for HTTP transport.
     */
    @Internal
    RTA_TRANSPORT_TCP_PORT("be.tea.agent.config.transport.tcp.port", "4448", false, "/Engine/Transport/TCP", "", "Server port for TCP", "Server port for HTTP transport."),


    /**
     * Persistence layer implementation type.
     */
    RTA_PERSISTENCE_PROVIDER("be.tea.agent.persistence.provider", "MEMORY", true, "/Engine/Persistence", "", "Persistence provider", "Persistence layer implementation type."),

    /**
     * ActionManager implementation.
     */
    @Internal
    RTA_ACTION_MANAGER_POLICY("be.tea.agent.action.manager.policy", "database", false, "/Engine/Persistence", "", "ActionManager policy", "ActionManager implementation."),


    /**
     * Whether SPM engine is running in query mode or not.
     */
    RTA_QUERY_ENGINE("be.tea.agent.query.enabled", "true", true, "/Engine", "", "SPM in query mode", "Whether SPM engine is running in query mode or not."),

    /**
     * Sync/Async persistence policy for persisting session state.
     */
    @Internal
    RTA_FT_SESSION_PERSISTENCE_POLICY("be.tea.agent.ft.session.persistence.policy", "sync", false, "/Engine/Persistence", "", "Session persistence mode", "Sync/Async persistence policy for persisting session state."),

    //Cluster layer properties
    /**
     * Is this a cache node.
     */
    @Internal
    IS_CACHE_NODE("be.tea.agent.cluster.iscachenode", "false", false, "/Engine/Cluster", "", "Is Cache Node", "Cluster layer properties, is this a cache node"),

    //Cluster layer properties
    /**
     * Is this a seeder.
     */
    @Internal
    IS_DATA_SEEDER("be.tea.agent.cluster.isseeder", "true", false, "/Engine/Cluster", "", "Is seeder", "Is this a seeder."),

    /**
     * Cluster name. All nodes/processes/instances with the same cluster name and transport properties form a group that communicates with each other.
     */
    @Internal
    CLUSTER_NAME("be.tea.agent.cluster.name", "default", false, "/Engine/Cluster", "", "Cluster Name", "Cluster name. All nodes/processes/instances with the same cluster name and transport properties form a group that communicates with each other."),


    /**
     * Cluster member name.
     */
    @Internal
    CLUSTER_MEMBER_NAME("be.tea.agent.cluster.member.name", "", false, "/Engine/Cluster", "", "Cluster Member name", "Cluster member name."),

    /**
     * Directory or folder from where SPM schema files are loaded.
     */
    RTA_SCHEMA_STORE("be.tea.agent.schema.store", "../config", true, "/Engine", "", "SPM Schema directory", "Directory or folder from where SPM schema files are loaded."),

    /**
     * ActiveSpaces property: Should wait for space to get ready? -1 for infinite wait
     */
    @Internal
    AS_SPACE_WAIT("as.space.wait", "", false, "/Engine/Persistence/ActiveSpaces", "", "Space wait (ms)", "ActiveSpaces property: Should wait for space to get ready? -1 for infinite wait"),

    /**
     * Cache policy: READ_THROUGH or READ_WRITE_THROUGH. Default is empty.
     */
    @Internal
    AS_SPACE_CACHE_POLICY("as.space.cache.policy", "", false, "/Engine/Persistence/ActiveSpaces", "", "Cache Policy", "Cache policy: READ_THROUGH or READ_WRITE_THROUGH. Default is empty."),

    /**
     * Capacity of the space.
     */
    @Internal
    AS_SPACE_CAPACITY("as.space.capacity", "", false, "/Engine/Persistence/ActiveSpaces", "", "Space cCapacity", "Capacity of the space."),

    /**
     * Space eviction policy. LRU or NONE. Default is empty.
     */
    @Internal
    AS_SPACE_EVICTION_POLICY("as.space.eviction.policy", "", false, "/Engine/Persistence/ActiveSpaces", "", "Eviction Policy", "Space eviction policy. LRU or NONE. Default is empty."),

    /**
     * Forget policy. Set to true to enable setForget.
     */
    @Internal
    AS_SPACE_FORGET_POLICY("as.space.forgetpolicy", "", false, "/Engine/Persistence/ActiveSpaces", "", "Forget Policy", "Forget policy. Set to true to enable setForget."),

    /**
     * Host aware replication. Set to true to enable it.
     */
    @Internal
    AS_SPACE_HOST_AWARE_REPLICATION("as.space.hostaware.replication", "", false, "/Engine/Persistence/ActiveSpaces", "", "Host-aware replication", "Host aware replication. Set to true to enable it."),

    /**
     * Minimum seeders for spaces.
     */
    @Internal
    AS_SPACE_MINSEEDER_COUNT("as.space.minseeder.count", "", false, "/Engine/Persistence/ActiveSpaces", "", "Minimum seeder count", "Minimum seeders for spaces."),

    /**
     * Persistence distribution policy. One of DISTRIBUTED, ADDRESSED, NONDISTRIBUTED. Default is empty.
     */
    @Internal
    AS_SPACE_PERSISTENCE_DISTRIBUTION_POLICY("as.space.persistence.distribution.policy", "", false, "/Engine/Persistence/ActiveSpaces", "", "Distribution Policy", "Persistence distribution policy. One of DISTRIBUTED, ADDRESSED, NONDISTRIBUTED. Default is empty."),

    /**
     * Persistence type. One of SHARE_NOTHING, SHARE_ALL, NONE. Default is empty.
     */
    @Internal
    AS_SPACE_PERSISTENCE_TYPE("as.space.persistence.type", "", false, "/Engine/Persistence/ActiveSpaces", "", "Persistence Type", "Persistence type. One of SHARE_NOTHING, SHARE_ALL, NONE. Default is empty."),

    /**
     * Persistence policy. One of SYNC, ASYNC, NONE. Default is empty.
     */
    @Internal
    AS_SPACE_PERSISTENCE_POLICY("as.space.persistence.policy", "", false, "/Engine/Persistence/ActiveSpaces", "", "Persistence Policy", "Persistence policy. One of SYNC, ASYNC, NONE. Default is empty."),

    /**
     * Directory or folder to store shared nothing data files.
     */
    @Internal
    AS_SPACE_PERSISTENCE_DATASTORE("as.space.persistence.datastore", "", false, "/Engine/Persistence/ActiveSpaces", "", "Shared-nothing DataStore", "Directory or folder to store shared nothing data files."),

    /**
     * Phase count.
     */
    @Internal
    AS_SPACE_PHASE_COUNT("as.space.phase.count", "", false, "/Engine/Persistence/ActiveSpaces", "", "Phase Count", "Phase count."),

    /**
     * Phase interval.
     */
    @Internal
    AS_SPACE_PHASE_INTERVAL("as.space.phase.interval", "", false, "/Engine/Persistence/ActiveSpaces", "", "Phase Interval", "Phase interval."),

    /**
     * Read timeout.
     */
    @Internal
    AS_SPACE_READ_TIMEOUT("as.space.read.timeout", "", false, "/Engine/Persistence/ActiveSpaces", "", "Read Timeout", "Read timeout."),

    /**
     * Replication policy. One of SYNC, ASYNC. Default is empty.
     */
    @Internal
    AS_SPACE_REPLICATION_POLICY("as.space.replication.policy", "", false, "/Engine/Persistence/ActiveSpaces", "", "Replication Policy", "Replication policy. One of SYNC, ASYNC. Default is empty."),

    /**
     * Write timeout.
     */
    @Internal
    AS_SPACE_WRITE_TIMEOUT("as.space.write.timeout", "", false, "/Engine/Persistence/ActiveSpaces", "", "Write Timeout", "Write timeout."),

    /**
     * Transport used for update. One of MULTICAST, UNICAST.
     */
    @Internal
    AS_SPACE_UPDATE_TRANSPORT("as.space.update.transport", "", false, "/Engine/Persistence/ActiveSpaces", "", "Space Update Transport", "Transport used for update. One of MULTICAST, UNICAST."),

    /**
     * Virtual node count.
     */
    @Internal
    AS_SPACE_VIRTUAL_NODE_COUNT("as.space.virtual.node.count", "", false, "/Engine/Persistence/ActiveSpaces", "", "Virtual Node Count", "Virtual node count."),

    /**
     * Replication count.
     */
    @Internal
    AS_SPACE_REPLICATION_COUNT("as.space.replication.count", "", false, "/Engine/Persistence/ActiveSpaces", "", "Replication Count", "Replication count."),

    /**
     * Distribution policy. One of DISTRIBUTED,NON_DISTRIBUTED.
     */
    @Internal
    AS_SPACE_DISTRIBUTION_POLICY("as.space.distribution.policy", "", false, "/Engine/Persistence/ActiveSpaces", "", "Distribution Policy", "Distribution policy. One of DISTRIBUTED,NON_DISTRIBUTED."),

    /**
     * Lock scope. One of NONE,PROCESS,THREAD.
     */
    @Internal
    AS_SPACE_LOCK_SCOPE("as.space.lock.scope", "", false, "/Engine/Persistence/ActiveSpaces", "", "Lock Scope", "Lock scope. One of NONE,PROCESS,THREAD."),

    /**
     * Time to live in milliseconds. One of NO_WAIT,WAIT_FOREVER.
     */
    @Internal
    AS_SPACE_LOCK_TTL("as.space.lock.ttl", "", false, "/Engine/Persistence/ActiveSpaces", "", "TTL (ms)", "Time to live in milliseconds. One of NO_WAIT,WAIT_FOREVER."),

    /**
     * Time to live for a Lock in milliseconds. One of NO_WAIT,WAIT_FOREVER.
     */
    @Internal
    AS_SPACE_LOCK_WAIT("as.space.lock.wait", "", false, "/Engine/Persistence/ActiveSpaces", "", "Lock TTL(ms)", "Time to live for a Lock in milliseconds. One of NO_WAIT,WAIT_FOREVER."),

    /**
     * Listen URL.
     */
    @Internal
    AS_LISTEN_URL("as.listen.url", "", false, "/Engine/Persistence/ActiveSpaces", "", "Listen URL", "Listen URL."),

    /**
     * Discovery URL.
     */
    @Internal
    AS_DISCOVER_URL("as.discover.url", "", false, "/Engine/Persistence/ActiveSpaces", "", "Discover URL", "Discovery URL."),

    /**
     * Transport Timeout. Time to wait for remote invocations to return.
     */
    @Internal
    AS_TRANSPORT_TIMEOUT("as.transport.timeout", "", false, "/Engine/Persistence/ActiveSpaces", "", "Transport Timeout(ms)", "Transport Timeout. Time to wait for remote invocations to return."),

    /**
     * Internal receive buffer size.
     */
    @Internal
    AS_RECEIVE_BUFFER_SIZE("as.receive.buffer.size", "", false, "/Engine/Persistence/ActiveSpaces", "", "", "Internal receive buffer size."),


    /**
     * ActiveSpaces Worker thread count. Default is 1.
     */
    @Internal
    AS_WORKER_THREAD_COUNT("as.worker.thread.count", "1", false, "/Engine/Persistence/ActiveSpaces", "", "Receive Buffer Size", "ActiveSpaces Worker thread count. Default is 1."),

    /**
     * Create indexes on spaces. Default is true.
     */
    @Internal
    AS_CREATE_INDEX("as.create.index", "true", false, "/Engine/Persistence/ActiveSpaces", "", "Whether to create indexes", "Create indexes on spaces. Default is true."),

    /**
     * Apply transactions. Default is false.
     */
    @Internal
    AS_APPLY_TXN("as.apply.transactions", "true", false, "/Engine/Persistence/ActiveSpaces", "", "Whether to apply transactions", "Apply transactions. Default is false."),

    /**
     * Batch size for put all. Default is 100.
     */
    @Internal
    AS_BATCH_SIZE("as.batch.size", "100", false, "/Engine/Persistence/ActiveSpaces", "", "Batch size for batched AS Persistence ", "Batch size for put all. Default is 100."),
    
    /**
     * Size of the L1 cache used to store metric nodes.
     * Defaults to 10000
     */
    RTA_L1_CACHE_SIZE("be.tea.agent.l1.cache.size", "10000", true, "/Engine", "", "L1 Cache Size for Metric Nodes", "Size of the L1 cache used to store metric nodes."),

    /**
     * Size of the L1 cache used to store rule metric nodes.
     * Defaults to 10000
     */
    RTA_L1_CACHE_SIZE_FOR_RULEMETRIC_NODES("be.tea.agent.l1.cache.size.rule.metric.nodes", "10000", true, "/Engine", "", "L1 Cache Size for RuleMetric Nodes", "Size of the L1 cache used to store rule metric nodes."),

    /**
     * Size of the L1 cache used to store asset nodes.
     * Defaults to 10000
     */
    @Internal
    RTA_L1_CACHE_SIZE_FOR_ASSET_NODES("be.tea.agent.l1.cache.size.asset.nodes", "10000", false, "/Engine", "", "L1 Cache Size for Asset Nodes", "Size of the L1 cache used to store asset nodes."),

    /**
     * Number of times to retry a transaction.
     */
    @Internal
    RTA_TRANSACTION_RETRY_COUNT("be.tea.agent.transaction.retry.count", "1", true, "/Engine", "", "Transaction Retry Count", "Number of times to retry a transaction."),

    /**
     * Server batching property. Batches of client messages will further be batched with this batch size.
     * Default is 100
     */
    RTA_TRANSACTION_BATCH_SIZE("be.tea.agent.server.batch.size", "1", true, "/Engine", "", "Server Batch Size", "Server batching property. Batches of client messages will further be batched with this batch size."),

    /**
     * Query Fetch size for PostgreSQL.
     */
    @Internal
    RTA_POSTGRE_QUERY_FETCH_SIZE("be.tea.agent.server.postgre.query.fetch.size", "1", false, "/Engine/Query", "Query Fetch Size (for PostgreSQL)", "", "Query Fetch size for PostgreSQL."),

    /**
     * Client batch size. Should be the same as set by server.
     */
    @Internal
    RTA_TRANSACTION_CLIENT_BATCH_SIZE("be.tea.agent.transaction.client.batch.size", "1", false, "/Engine", "", "Client Batch Size", "Client batch size. Should be the same as set by server."),

    /**
     * Whether to partitioning for in-memory data-store implementation.
     */
    @Internal
    RTA_INMEMORY_USE_PARTITIONING("be.tea.agent.inmemory.use.partitioning", "false", false, "/Engine/Persistence/Memory", "", "Memory Partitioning Enabled", "Whether to partitioning for in-memory data-store implementation."),

    /**
     * Whether to use transaction batching for improved performance.
     */
    @Internal
    RTA_TRANSACTION_USE_BATCHING("be.tea.agent.server.use.batching", "true", false, "/Engine/Persistence", "", "Transaction Batching Enabled", "Whether to use transaction batching for improved performance."),

    /**
     * Duration in milliseconds to wait for a server batch to complete. If not, the incomplete batch will be flushed at this interval.
     * Default is 5000 (5 seconds).
     */
    RTA_TRANSACTION_BATCH_FLUSH_PERIOD("be.tea.agent.server.batch.flush.period", "5000", true, "/Engine/Persistence", "", "Server Batch Timeout(ms)", "Duration in milliseconds to wait for a server batch to complete. If not, the incomplete batch will be flushed at this interval."),

    /**
     * Defines the implementation strategy. if true, uses parallel strategy.
     */
    @Internal
    RTA_METRIC_SERVICE_STRATEGY("be.tea.agent.metric.service.strategy.parallel", "true", false, "/Engine", "", "Metric Compuration Parallel Strategy", "Defines the implementation strategy. if true, uses parallel strategy."),

    /**
     * Should rule evaluation happen in a separate thread than metric calculation.
     */
    @Internal
    RTA_ASYNC_RULES_EVAL("be.tea.agent.rules.eval.strategy.parallel", "true", false, "/Engine", "", "Rule Computation Parallel Strategy", "Should rule evaluation happen in a separate thread than metric calculation."),

    /**
     * Time in milliseconds how often to scan for scheduled actions.
     */
    RTA_ACTIONS_SCAN_FREQUENCY("be.tea.agent.rules.actions.scan.frequency", "5000", true, "/Engine", "", "Action Scan Frequency(ms)", "Time in milliseconds how often to scan for scheduled actions."),

    /**
     * Maximum time in milliseconds that a named session can be kept alive since the last heart beat from client.
     */
    RTA_HEARTBEAT_THRESHOLD("be.tea.agent.config.session.heartbeat.threshold", "60000", true, "/Engine/Session", "", "Client Session Timeout(ms)",
            "Maximum time in milliseconds that a named session can be kept alive since the last heart beat from client."),

    /**
     * JDBC driver name.
     */
    @Mandatory
    RTA_JDBC_DRIVER("be.tea.agent.jdbc.driver", "", true, "/Engine/Persistence/Database", "", "JDBC Driver Name", "JDBC driver name."),

    /**
     * JDBC connection URL.
     */
    @Mandatory
    RTA_JDBC_URL("be.tea.agent.jdbc.url", "", true, "/Engine/Persistence/Database", "", "JDBC Connection URL", "JDBC connection URL."),

    /**
     * Database user name.
     */
    @Mandatory
    RTA_JDBC_USER("be.tea.agent.jdbc.user", "", true, "/Engine/Persistence/Database", "", "Database User Name", "Database User Name."),

    /**
     * Database password.
     */
    RTA_JDBC_PASSWORD("be.tea.agent.jdbc.password", "", true, "/Engine/Persistence/Database", "", "Database User password", "Database User password."),

    /**
     * Database JDBC connection pool initial connection size.
     */
    RTA_JDBC_INIT_CONNECTION("be.tea.agent.jdbc.initial.connection.size", "10", true, "/Engine/Persistence/Database", "", "JDBC Connection Pool Initial Size", "Database JDBC connection pool initial connection size."),

    /**
     * Database JDBC connection pool maximum connection size.
     */
    RTA_JDBC_MAX_CONNECTION("be.tea.agent.jdbc.max.connection.size", "100", true, "/Engine/Persistence/Database", "", "JDBC Connection Pool Maximum Size", "Database JDBC connection pool maximum connection size."),

    /**
     * Database batch size.
     */
    RTA_JDBC_BATCH_SIZE("be.tea.agent.jdbc.batch.size", "1000", true, "/Engine/Persistence/Database", "", "Database Batch Size", "Database batch size."),

    /**
     * Database query fetch size.
     */
    RTA_JDBC_QUERY_FETCH_SIZE("be.tea.agent.jdbc.query.fetch.size", "500", true, "/Engine/Persistence/Database", "", "Database Query Fetch Size", "Database query fetch size."),

    /**
     * Database connection pool internal identifier.
     */
    RTA_JDBC_KEY("be.tea.agent.jdbc.key", "", true, "/Engine/Persistence/Database", "", "JDBC Connection Pool Identifier", "Database connection pool internal identifier."),

    /**
     * Extra connection properties as supported by the underlying driver. Value is a comma separated tuple of prop=value
     * <p/>
     * For Example: rta.jdbc.connection.properties=oracle.jdbc.ReadTimeout=600000,property-x=value-y
     */
    RTA_JDBC_CONNECTION_PROPERTY("be.tea.agent.jdbc.connection.properties", "", true, "/Engine/Persistence/Database", "", "JDBC Connection Properties",
            "Extra connection properties as supported by the underlying driver. Value is a comma separated tuple of prop=value\n" +
                    "For Example: rta.jdbc.connection.properties=oracle.jdbc.ReadTimeout=600000,property-x=value-y"),

    /**
     * Whether to store multi-valued metric values explicitly in different database tables or to store them as Blob.
     */
    RTA_METRICS_MULTIVALUES_EXPLICIT("be.tea.agent.metrics.multivalues.explicit", "false", true, "/Engine/Persistence/Database", "", "Multi-valued Metrics as Separate Rows", "Whether to store multi-valued metric values explicitly in different database tables or to store them as Blob."),

    /**
     * Whether to log an Alert or not.
     * Defaults to false, dont log all alerts in log files by default since in SPM2.2, we maintain them in the system as alert hierarchy.
     */
    @Internal
    RTA_LOG_ALERT("be.tea.agent.log.alert", "false", false, "/Engine", "", "Alert Logs Enabled", "Whether to log an Alert or not."),

    /**
     * Alert Log format.
     */
    RTA_LOG_ALERT_FILE_FORMAT("be.tea.agent.log.alert.format", "TEXT", true, "/Engine/Actions", "", "Alert Logs Format", "Alert Log format."),

    /**
     * Mail SMTP host name.
     */
    RTA_MAIL_SMTP_HOST("be.tea.agent.mail.smtp.host", "", true, "/Engine/Actions/Email", "", "SMTP Host", "Mail SMTP host name."),

    /**
     * Mail SMTP port.
     */
    RTA_MAIL_SMTP_PORT("be.tea.agent.mail.smtp.port", "25", true, "/Engine/Actions/Email", "", "SMTP Port", "Mail SMTP port."),

    /**
     * Mail message to be send from.
     */
    RTA_MAIL_FROM("be.tea.agent.mail.from", "", true, "/Engine/Actions/Email", "", "Email Sent By Email-Id", "Mail message to be send from."),

    /**
     * Email Retry count.
     */
	RTA_MAIL_RETRY_COUNT("be.tea.agent.mail.retry.count", "3", true, "/Engine/Actions/Email", "", "Email retry count", "Maximumb number of retries to be attempeted while sending email."),

    /**
     * Email Retry interval.
     */
    RTA_MAIL_RETRY_INTERVAL("be.tea.agent.mail.retry.interval", "2000", true, "/Engine/Actions/Email", "", "Email retry interval (milliseconds)", "While sending email, time to wait before reattempting to send email."),

    /**
     * Whether the SMTP server authentication is required or not.
     */
    RTA_MAIL_SMTP_AUTHENTICATION("be.tea.agent.mail.smtp.authentication", "false", true, "/Engine/Actions/Email", "", "SMTP Authentication Enabled", "Whether the SMTP server authentication is required or not."),

    /**
     * SMTP server user name.
     */
    RTA_MAIL_SMTP_USER("be.tea.agent.mail.smtp.user", "", true, "/Engine/Actions/Email", "", "SMTP User Name", "SMTP server user name."),

    /**
     * SMTP server user password.
     */
    RTA_MAIL_SMTP_PASSWORD("be.tea.agent.mail.smtp.password", "", true, "/Engine/Actions/Email", "", "SMTP Password", "SMTP server user password."),

    /**
     * Hawk enabled
     */
    RTA_HAWK_ENABLED("be.tea.agent.hawk.enabled", "false", true, "/Engine/Hawk", "", "Hawk Enabled", "Hawk enabled"),

    /**
     * Hawk RV service
     */
    RTA_HAWK_RV_SERVICE("rv_service", "7474", true, "/Engine/Hawk/Service", "", "Service", "Service name for RV"),

    /**
     * Hawk RV network
     */
    RTA_HAWK_RV_NETWORK("rv_network", ";", true, "/Engine/Hawk/Network", "", "Network", "Network name for RV"),

    /**
     * Hawk RV network
     */
    RTA_HAWK_RV_DAEMON("rv_daemon", "tcp:7474", true, "/Engine/Hawk/Daemon", "", "Daemon", "Daemon name for RV"),

    /**
     * SMTP server ping timeout in seconds.
     */
    @Internal
    RTA_MAIL_SMTP_PING_TIMEOUT("be.tea.agent.mail.smtp.ping.timeout", "10", false, "/Engine/Actions/Email", "", "SMTP Ping Timeout(seconds)", "SMTP server ping timeout in seconds."),

    /**
     * SMTP server ping retry time in seconds.
     */
    @Internal
    RTA_MAIL_SMTP_RETRY_TIME("be.tea.agent.mail.smtp.retry.time", "15", false, "/Engine/Actions/Email", "", "SMTP Ping Retty interval(seconds)", "SMTP server ping retry time in seconds."),

    /**
     * Whether to use processed facts table or EMS for bookkeeping.
     */
    @Internal
    RTA_STORE_PROCESSED_FACTS("be.tea.agent.store.processed.facts", "false", false, "/Engine", "", "Store Processed Facts", "Whether to use processed facts table or EMS for bookkeeping."),

    /**
     * Whether to process or skip duplicate facts.
     */
    RTA_PROCESS_DUPLICATE_FACTS("be.tea.agent.process.duplicate.facts", "true", true, "/Engine", "", "Skip Duplicate Facts", "Whether to process or skip duplicate facts."),

    /**
     * Whether to store facts at all.
     */
    RTA_STORE_FACTS("be.tea.agent.store.facts", "false", true, "/Engine", "", "Store All Facts", "Whether to store facts at all."),

    /**
     * If set to true, will infer asset status from the live feed of other hierarchies.
     */
    @Internal
    RTA_IMPLICIT_ASSET_STATUS("be.tea.agent.implicit.asset.status", "false", false, "/Engine", "", "Infer Asset Status from FACTs", "If set to true, will infer asset status from the live feed of other hierarchies."),

    /**
     * Whether to compute in parallel wherever possible.
     */
    @Internal
    RTA_PARALLEL_ROOT_NODE_COMPUTE("be.tea.agent.parallel.root.node.compute", "true", false, "/Engine", "", "Compute in Parallel", "Whether to compute in parallel whererver possible."),

    /**
     * ----------------------------
     * FT properties
     * ----------------------------
     */
    /**
     * Whether SPM engine is running in FT mode or not.
     */
    RTA_FT_ENABLED("be.tea.agent.ft.enabled", "true", true, "/Engine/FT", "", "SPM FT Mode Enabled", "Whether SPM engine is running in FT mode or not."),

    /**
     * Whether SPM engine is running in FT-GMP mode or not.
     */
    @Internal
    RTA_FT_GMP_ENABLED("be.tea.agent.ft.gmp.enabled", "false", false, "/Engine/FT/GMP", "", "SPM GMP Mode Enabled", "Whether SPM engine is running in FT-GMP mode or not."),

    /**
     * Whether SPM engine uses Bootstrap Queue for FT.
     */
    @Internal
    RTA_USE_FT_QUEUE("be.tea.agent.use.ft.queue", "true", false, "/Engine/FT/GMP/JMS", "", "JMS Fault-Tolerence Queue Name", "Whether SPM engine uses Bootstrap Queue for FT."),

    /**
     * JMS Bootstrap Queue for FT.
     */
    RTA_JMS_FT_QUEUE("be.tea.agent.ft.queue.name", "spm.ft.queue", true, "/Engine/FT/GMP/JMS", "", "JMS Bootstrap FT Queue Name", "JMS Bootstrap Queue for FT."),

    /**
     * GMP Minimum quorum of members required for Group membership protocol at cluster startup.
     */
    @Internal
    GMP_STARTUP_MIN_QUORUM_COUNT("be.tea.agent.ft.startup.minquorum", "1", false, "/Engine/FT/GMP", "", "Minimum GMP Quorum Size", "Minimum quorum of members required for Group membership protocol at cluster startup."),

    /**
     * GMP Time in milliseconds to wait for the quorum to be satisfied.
     */
    @Internal
    GMP_QUORUM_EXPIRY("be.tea.agent.ft.startup.quorumExpiry", "60000", false, "/Engine/FT/GMP", "", "GMP Quorum Timeout(ms)", "Time in milliseconds to wait for the quorum to be satisfied."),

    /**
     * GMP JMS Subject to subscribe to.
     */
    @Internal
    GMP_SUBJECT("be.tea.agent.ft.gmp.subject", "spm.ft.topic", false, "/Engine/FT/GMP", "", "GMT JMS Subject Name", "GMP JMS Subject to subscribe to."),

    /**
     * GMP Frequency of member sending heart beats.
     */
    @Internal
    GMP_MEMBER_HB_INTERVAL("be.tea.agent.ft.gmp.member.hb.interval", "1000", false, "/Engine/FT/GMP", "", "GMP Heartbeat Interval", "GMP Frequency of member sending heart beats."),

    /**
     * GMP Frequency of member heart beats being scanned.
     */
    @Internal
    GMP_MEMBER_HB_SCAN_INTERVAL("be.tea.agent.ft.gmp.member.hb.scan.interval", "5000", false, "/Engine/FT/GMP", "", "GMP Heartbeat Scan Frequency(ms)", "GMP Frequency of member heart beats being scanned."),

    /**
     * GMP Threshold for member to wait in leader absent state before attempting to begin election.
     */
    @Internal
    GMP_LEADER_ABSENCE_THRESHOLD("be.tea.agent.ft.gmp.leader.absent.threshold", "20000", false, "/Engine/FT/GMP", "", "GMP Wait Threshold", "GMP Threshold for member to wait in leader absent state before attempting to begin election."),

    /**
     * GMP Threshold for member heart beats to determine liveness.
     */
    @Internal
    GMP_MEMBER_HB_THRESHOLD("be.tea.agent.ft.gmp.member.hb.threshold", "10000", false, "/Engine/FT/GMP", "", "GMP Liveliness Threshold", "GMP Threshold for member heart beats to determine liveness."),

    /**
     * Topic connection factory name.
     */
    @Internal
    GMP_JMS_TOPIC_CONN_FACTORY("be.tea.agent.jms.topicconnectionfactory", "SPMTopicConnectionFactory", false, "/Engine/Transport/JMS", "", "JMS ToppicConnection Factory", "Topic connection factory name."),

    /**
     * Set to "thread" if locking and unlocking would be at the thread level, else set to "member".
     */
    @Internal
    RTA_LOCK_LEASE_GRANULARITY("be.tea.agent.lock.lease.granularity", "thread", false, "/Engine", "", "SPM Lock Granularity", "Set to 'thread' if locking and unlocking would be at the thread level, else set to 'member'."),

    /**
     * Reentrant read-write lock fairness policy".
     */
    @Internal
    RTA_LOCK_LEASE_FAIRNESS_POLICY("be.tea.agent.lock.lease.fair.policy", "true", false, "/Engine", "", "SPM Lock Fairness Policy", "Reentrant read-write lock fairness policy"),


    /**
     * An internal property to switch on/off asset locking behavior so as to allow a fall back.
     */
    @Internal
    RTA_USE_ASSET_FEATURE("be.tea.agent.use.asset.feature", "true", false, "/Engine", "", "SPM Asset Feature Enabled", "An implementation that has a notion of asset hierarchies."),

    @Internal
    RTA_USE_ASSET_LOCKING("be.tea.agent.use.asset.locking", "false", false, "/Engine", "", "SPM Asset Locking Feature Enabled", "An internal property to switch on/off asset locking behavior so as to allow a fall back."),

    @Internal
    RTA_USE_SINGLE_LOCK("be.tea.agent.use.asset.single.lock", "false", false, "/Engine", "", "SPM uses a single key to lock", "Use a single key to lock."),

    /**
     * Timeout in milliseconds for snapshot queries unresponsiveness. After this timeout, the snapshot query state will be discarded.
     */
    RTA_SNAPSHOT_QUERY_TIMEOUT("be.tea.agent.snapshot.query.timeout", "300000", true, "/Engine/Query", "", "SPM Snapshot Query Timeout(ms)",
            "Timeout in milliseconds for snapshot queries unresponsiveness. After this timeout, the snapshot query state will be discarded."),
    
    @Internal
    BE_TEA_AGENT_SERVICE_MBEANS_PREFIX("be.tea.agent.service.mbeans.prefix", "be.teagent.service", false, "/Engine", "", "The prefix for all service MBeans",
            "The prefix for all service MBeans"),
    /**
     * Set it to true if delete metric events needs to be processed for Rule evaluation.
     */
    RTA_RULE_PROCESS_DELETE_METRIC_EVENTS("be.tea.agent.rule.process.delete.events", "false", true, "/Engine", "", "Process Delete Events", "Signifies whether to process delete metric events for rules or not."),
    
    RTA_RULE_SNAPSHOT_EVAL_FREQUENCY("be.tea.agent.rule.eval.frequency", "30000", true, "/Engine", "", "Snapshot Rule Evaluation Frequency", "The frequency with which the Snapshot rule evaluation should run");


    protected final String propertyName;

    protected String defaultValue;

    protected String description;

    protected String displayName;

    protected boolean isPublic = true;

    protected String category;

    protected String parentCategory;

    ConfigProperty(String propertyName, String defaultValue, boolean isPublic, String category, String parentCategory,
                   String displayName, String description) {
        this.propertyName = propertyName;
        this.defaultValue = defaultValue;
        this.isPublic = isPublic;
        this.category = category;
        this.parentCategory = parentCategory;
        this.displayName = displayName;
        this.description = description;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getCategory() {
        return category;
    }

    public String getParentCategory() {
        return parentCategory;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public static ConfigProperty getByPropertyName(String propertyName) {
        for (ConfigProperty configProperty : ConfigProperty.values()) {
            if (configProperty.getPropertyName().equals(propertyName)) {
                return configProperty;
            }
        }
        return null;
    }

    public Object getValue(Properties props) {
        Object val = props.get(propertyName);
        if (val == null) {
            return defaultValue;
        }
        return val;
    }

    public Object getValue(Map props) {
        Object val = props.get(propertyName);
        if (val == null) {
            return defaultValue;
        }
        return val;
    }

    public static ConfigProperty[] getPublicValues() {
        ConfigProperty[] allConfigProperties = ConfigProperty.values();
        List<ConfigProperty> publicConfigProperties = new ArrayList<ConfigProperty>();
        for (ConfigProperty configProperty : allConfigProperties) {
            if (configProperty.isPublic()) {
                publicConfigProperties.add(configProperty);
            }
        }
        return publicConfigProperties.toArray(new ConfigProperty[publicConfigProperties.size()]);
    }

    public DataType getDataType() {
        // TODO Auto-generated method stub
        return null;
    }
}
