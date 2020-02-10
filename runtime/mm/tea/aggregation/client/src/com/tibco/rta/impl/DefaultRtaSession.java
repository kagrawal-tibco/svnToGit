package com.tibco.rta.impl;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.ConnectionRefusedException;
import com.tibco.rta.Fact;
import com.tibco.rta.Metric;
import com.tibco.rta.NotificationListenerKey;
import com.tibco.rta.RtaCommandListener;
import com.tibco.rta.RtaConnectionEx;
import com.tibco.rta.RtaException;
import com.tibco.rta.RtaNotificationListener;
import com.tibco.rta.RtaSession;
import com.tibco.rta.annotations.GuardedBy;
import com.tibco.rta.annotations.Idempotent;
import com.tibco.rta.annotations.ThreadSafe;
import com.tibco.rta.client.SessionInitFailedException;
import com.tibco.rta.client.notify.handler.CommandEventNotificationHandler;
import com.tibco.rta.client.notify.handler.QueryResultEventNotificationHandler;
import com.tibco.rta.client.notify.handler.SessionLivenessEventNotificationHandler;
import com.tibco.rta.client.notify.handler.TransactionEventNotificationHandler;
import com.tibco.rta.client.taskdefs.ConnectionAwareTaskManager;
import com.tibco.rta.client.taskdefs.ConnectionTask;
import com.tibco.rta.client.taskdefs.IdempotentRetryTask;
import com.tibco.rta.client.taskdefs.IdempotentRunnableRetryTask;
import com.tibco.rta.client.taskdefs.TaskRejectionHandler;
import com.tibco.rta.client.taskdefs.impl.ClearAlertsTask;
import com.tibco.rta.client.taskdefs.impl.CreateRuleTask;
import com.tibco.rta.client.taskdefs.impl.DeleteRuleTask;
import com.tibco.rta.client.taskdefs.impl.DeleteSchemaTask;
import com.tibco.rta.client.taskdefs.impl.FetchResultsTask;
import com.tibco.rta.client.taskdefs.impl.FetchServerConfigurationTask;
import com.tibco.rta.client.taskdefs.impl.GetAllActionFunctionDescriptorsTask;
import com.tibco.rta.client.taskdefs.impl.GetAllMetricFunctionDescriptorsTask;
import com.tibco.rta.client.taskdefs.impl.GetAllRuleDefsTask;
import com.tibco.rta.client.taskdefs.impl.GetAllSchemasTask;
import com.tibco.rta.client.taskdefs.impl.GetMetricChildrenTask;
import com.tibco.rta.client.taskdefs.impl.GetRuleTask;
import com.tibco.rta.client.taskdefs.impl.GetRulesTask;
import com.tibco.rta.client.taskdefs.impl.GetSchemaTask;
import com.tibco.rta.client.taskdefs.impl.QueryRegistrationTask;
import com.tibco.rta.client.taskdefs.impl.QueryUnregisterationTask;
import com.tibco.rta.client.taskdefs.impl.ResultAvailableTask;
import com.tibco.rta.client.taskdefs.impl.SaveSchemaTask;
import com.tibco.rta.client.taskdefs.impl.SessionClosureTask;
import com.tibco.rta.client.taskdefs.impl.StopBrowserTask;
import com.tibco.rta.client.taskdefs.impl.UpdateRuleTask;
import com.tibco.rta.client.tcp.TCPConnectionEvent;
import com.tibco.rta.client.transport.MessageReceptionStrategy;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.client.transport.StrategyFactory;
import com.tibco.rta.client.utils.ModelValidationUtils;
import com.tibco.rta.client.utils.SessionUtils;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.impl.util.ConnectionExchanger;
import com.tibco.rta.impl.util.DefaultQueueEventNotificationListener;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.metric.MetricChildrenBrowserProxy;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.rule.mutable.MutableRuleDef;
import com.tibco.rta.model.runtime.ServerConfigurationCollection;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.MetricResultTuple;
import com.tibco.rta.query.Query;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.QueryResultTuple;
import com.tibco.rta.query.QueryResultTupleCollection;
import com.tibco.rta.query.QueryType;
import com.tibco.rta.query.impl.QueryImpl;
import com.tibco.rta.query.impl.SnapshotBrowserProxy;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static com.tibco.rta.util.ServiceConstants.BROWSER_ID;
import static com.tibco.rta.util.ServiceConstants.QUERY_NAME;
import static com.tibco.rta.util.ServiceConstants.RULENAME;
import static com.tibco.rta.util.ServiceConstants.SESSION_ID;
import static com.tibco.rta.util.ServiceConstants.SESSION_NAME;

/**
 * Default implementation of RtaSession.
 */

public class DefaultRtaSession implements RtaSession {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    /**
     * Lock to do general protection
     */
    private final ReentrantLock mainLock = new ReentrantLock();

    /**
     * Transmission strategy bound to the connection.
     */
    private MessageTransmissionStrategy transmissionStrategy;

    /**
     * Reception strategy bound to the connection.
     */
    private MessageReceptionStrategy receptionStrategy;

    /**
     * Exchange condition to wait on for async connection establishment.
     */
    private final Condition exchangeCondition = mainLock.newCondition();

    /**
     * Condition for every operation to wait on for async connection establishment.i.e session in init state.
     */
    private final Condition sessionStateCondition = mainLock.newCondition();

    /**
     * Schema names to schema map
     */
    private ConcurrentHashMap<String, RtaSchema> namesToSchemasMap = new ConcurrentHashMap<String, RtaSchema>();


    /**
     * System schema names to schema map
     */
    private ConcurrentHashMap<String, RtaSchema> systemSchemas = new ConcurrentHashMap<String, RtaSchema>();

    /**
     * Handler for transaction notifications
     */
    private TransactionEventNotificationHandler transactionNotificationHandler;

    /**
     * Common listener for all async notifications on this session.
     */
    private RtaNotificationListener notificationListener;

    /**
     * Session ID which can be set on connection.
     *
     * @see RtaConnectionEx#setClientId(String)
     */
    private String sessionId;

    /**
     * Name of the session
     */
    private String name;

    /**
     * Flag to indicate metric functions loaded.
     */
    private AtomicBoolean hasLoadedFunctions = new AtomicBoolean(false);

    /**
     * The connection used to create this session.
     */
    @ThreadSafe
    private RtaConnectionEx ownerConnection;

    /**
     * Common funnel for all sync/async tasks performed by this session.
     */
    private ConnectionAwareTaskManager taskManager;

    /**
     * Meant for connection retries.
     */
    private ConnectionTimer retryTimer;

    /**
     * Just single thread for starting async connection
     */
    private ExecutorService asyncConnectionExecutorService = Executors.newSingleThreadExecutor(new ThreadFactory() {

        private static final String NAME_PREFIX = "Connection-Retry-Thread";

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, NAME_PREFIX);
        }
    });

    /**
     * A synchronizer object
     */
    private ConnectionExchanger exchanger = new ConnectionExchanger();

    /**
     * Heartbeats for named sessions.
     */
    private HeartbeatSender heartbeatSender;

    /**
     * Whether heartbeats are being sent or not.
     */
    private volatile boolean heartbeatsStopped;

    /**
     * Session state protected by "this" lock but volatile to allow read consistency during updates.
     */
    @GuardedBy("mainLock")
    private volatile SessionStates state;

    private ConcurrentHashMap<ConfigProperty, PropertyAtom<?>> configuration = null;

    private enum SessionStates {
        OPEN, INITED, CLOSED
    }


    public DefaultRtaSession(RtaConnectionEx ownerConnection, String sessionName, Map<ConfigProperty, PropertyAtom<?>> configuration) {
        this.name = sessionName;

        // Wrap into thread safe map
        this.configuration = new ConcurrentHashMap<ConfigProperty, PropertyAtom<?>>(configuration);

        this.ownerConnection = ownerConnection;

        //Use this id to identify the calling session
        //Generate new for every session.
        this.sessionId = UUID.randomUUID().toString();

        this.transmissionStrategy = StrategyFactory.INSTANCE.getTransmissionStrategy(this, ownerConnection);

        this.receptionStrategy = StrategyFactory.INSTANCE.getReceptionStrategy(this, ownerConnection);

        taskManager = new ConnectionAwareTaskManager(this);

        retryTimer = new ConnectionTimer(this);

        state = SessionStates.OPEN;
    }

    @Override
    public void init() throws ConnectionRefusedException, SessionInitFailedException {
        init(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }

    @Override
    public void init(long timeout, TimeUnit units) throws ConnectionRefusedException, SessionInitFailedException {
        if (state != SessionStates.INITED) {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Performing initialization of session [%s]", sessionId);
            }
            try {
                if (LOGGER.isEnabledFor(Level.INFO)) {
//                    LOGGER.log(Level.INFO, ClientComponentVersion.printVersionInfo());
                }
                transmissionStrategy.init();
                receptionStrategy.init(timeout, units);
                if (ownerConnection.shouldSendHeartbeat() && name != null && !name.isEmpty()) {
                    startHeartbeatSender();
                    //Register for session liveness checks
                    SessionLivenessEventNotificationHandler livenessEventNotificationHandler = new SessionLivenessEventNotificationHandler(this);
                    receptionStrategy.registerNotificationHandler(livenessEventNotificationHandler);
                }

            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
                if (e instanceof SessionInitFailedException) {
                    //Rethrow
                    throw (SessionInitFailedException) e;
                }
                // Get cause of cause of exception
                throw (ConnectionRefusedException) e.getCause().getCause();
            }
        }
    }


    public DefaultRtaSession(RtaConnectionEx ownerConnection, Map<ConfigProperty, PropertyAtom<?>> configuration) {
        this(ownerConnection, null, configuration);
    }

    public Map<ConfigProperty, PropertyAtom<?>> getConfiguration() {
        return configuration;
    }

    /**
     * Close.
     */
    @Override
    public void close() {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Attempting to close session");
        }

        try {
            //Null check to ensure that heartbeat sender is initialized because it will be null for un-named sessions.
            if (ownerConnection.shouldSendHeartbeat() && heartbeatSender != null) {
                // Stop heartbeats
                heartbeatSender.stop();
            }
            asyncConnectionExecutorService.shutdownNow();

            if (name != null) {
                closeRemoteSession();
            }

            taskManager.close();
            transmissionStrategy.shutdown();
            receptionStrategy.shutdown();

            setSessionState(SessionStates.CLOSED);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error shutting down session", e);
        }
    }

    /**
     * Close remote equivalent of named client session.
     *
     * @throws RtaException
     */
    private void closeRemoteSession() throws RtaException {
        checkSessionState();

        int retryCount = 1;
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {
            SessionClosureTask sessionClosureTask = new SessionClosureTask(transmissionStrategy);

            sessionClosureTask.addProperty(SESSION_ID, sessionId);

            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, sessionClosureTask);

            taskManager.submitTask(retryTask);
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "Session Closure failed", e);
            throw new RtaException(e);
        }
    }

    private void startHeartbeatSender() {
        heartbeatSender = new HeartbeatSender(this);
        heartbeatSender.start();
        heartbeatsStopped = false;
    }

    @Override
    public String getClientId() {
        return sessionId;
    }

    /**
     * Return the session name
     */
    public String getName() {
        return name;
    }


    public MessageTransmissionStrategy getTransmissionStrategy() {
        return transmissionStrategy;
    }

    public MessageReceptionStrategy getReceptionStrategy() {
        return receptionStrategy;
    }


    public <C extends ConnectionTask<?, ?>> void performExchange(C connectionTask) {
        ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            Object exchanged = getExchanged();
            if (exchanged instanceof TCPConnectionEvent) {
                retryTimer.setConnectionState(ConnectionTimer.CONNECTION_UP);
                //Set session to init state which means operations can be performed.
                setSessionState(SessionStates.INITED);
            } else if (exchanged instanceof Exception) {
                throw (Exception) exchanged;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Async connection threw exception", e);
            triggerRetry(connectionTask);
            informServerStatus(ConnectionTimer.CONNECTION_DOWN);
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }


    /**
     * Get exchanged object
     *
     * @return the object exchanged.
     */
    @GuardedBy("mainLock")
    public Object getExchanged() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            while (exchanger.getExchanged() == null) {
                //Wait
                exchangeCondition.await();
            }
            return exchanger.getExchanged();
        } catch (InterruptedException ie) {
            LOGGER.log(Level.ERROR, "", ie);
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
        return null;
    }

    /**
     * Exchange object once connection establish is successful/failed.
     *
     * @see TCPConnectionEvent
     * @see ConnectionRefusedException
     */
    @GuardedBy("mainLock")
    public void exchange(Object toExchange) throws InterruptedException {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            exchanger.setExchanged(toExchange);
            //Signal
            exchangeCondition.signalAll();
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }

    /**
     * This is meant to be called during a connection retry.
     */
    @Idempotent
    <C extends ConnectionTask<?, ?>> Future<Object> establishConnectionPipeline(C connectionTask) {
        // Retry once
        int retryCount = 1;

        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        IdempotentRunnableRetryTask retryTask = new IdempotentRunnableRetryTask(this, retryCount, waitTime, connectionTask);

        return asyncConnectionExecutorService.submit(retryTask);
    }

    /**
     * Get reference to task manager
     */
    public ConnectionAwareTaskManager getTaskManager() {
        return taskManager;
    }

    /**
     * Get underlying connection which created this session.
     */
    public RtaConnectionEx getOwnerConnection() {
        return ownerConnection;
    }

    /**
     * Set task manager status depending on connection status.
     */
    public void informServerStatus(int serverStatus) {
        retryTimer.setConnectionState(serverStatus);
        taskManager.setManagerStatus(serverStatus);

        if (serverStatus == TCPConnectionEvent.CONNECTION_ESTABLISH_EVENT) {
            //Set session to init state which means operations can be performed.
            setSessionState(SessionStates.INITED);

            if (ownerConnection.shouldSendHeartbeat() && heartbeatsStopped && name != null && !name.isEmpty()) {
                //Also start heartbeats
                startHeartbeatSender();
            }
        } else if (serverStatus == TCPConnectionEvent.CONNECTION_DOWN_EVENT) {
            //Stop heartbeats if on
            if (ownerConnection.shouldSendHeartbeat() && heartbeatSender != null) {
                // Stop heartbeats
                heartbeatSender.stop();
                //Set the flag to true
                heartbeatsStopped = true;
            }
        }
    }

    /**
     * Set session state.
     */
    private void setSessionState(SessionStates sessionState) {
        ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            this.state = sessionState;
            //Signal waiters
            if (this.state == SessionStates.INITED) {
                //Signal ops that session is inited
                sessionStateCondition.signalAll();
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Session [%s] initialized and ready to perform operations.", sessionId);
                }
            }
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }

    /**
     * To be called to initiate the retry connection process.
     */
    @SuppressWarnings("unchecked")
    public <C extends ConnectionTask<?, ?>> void triggerRetry(C retryTask) {
        retryTimer.setRetryTask(retryTask);
    }

    /**
     * Gets the schema.
     *
     * @param name the name
     * @return the schema
     */
    @Override
    @SuppressWarnings("unchecked")
    @GuardedBy("mainLock")
    @Idempotent
    public <T extends RtaSchema> T getSchema(String name) throws RtaException {
        checkSessionState();

        int retryCount = (Integer) ConfigProperty.NUM_RETRIES_SYNC_OPS.getValue(configuration);
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {
            if (!hasLoadedFunctions.get()) {
                getAllFunctionDescriptors();
                hasLoadedFunctions.compareAndSet(false, true);
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Metric Function Descriptors are taken from session");
                }
            }

            RtaSchema localSchema = namesToSchemasMap.get(name);
            if (localSchema != null) {
                return (T) localSchema;
            }
            GetSchemaTask getSchemaTask = new GetSchemaTask(transmissionStrategy);
            getSchemaTask.addProperty("schemaname", name);
            getSchemaTask.addProperty(SESSION_ID, sessionId);

            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, getSchemaTask);

            RtaSchema schema = (RtaSchema) taskManager.submitTask(retryTask);
            if (schema != null) {
                namesToSchemasMap.put(name, schema);
            }
            return (T) schema;
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "getSchema failed", e);
            throw new RtaException(e);
        }
    }

    @GuardedBy("mainLock")
    @Idempotent
    private void checkSessionState() throws RtaException {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            if (state == SessionStates.CLOSED) {
                throw new RtaException("Session already closed");
            }
            while (state != SessionStates.INITED) {
                //Wait for session to be inited which will happen once connection is established.
                //This will block any threads from using this session for ops before init is completed.
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Session not initialized completely. Waiting for connection establishment to complete");
                }
                sessionStateCondition.await();
            }
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "", e);
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }


    @Idempotent
    public void saveSchema(RtaSchema schema) throws RtaException {
        checkSessionState();

        int retryCount = (Integer) ConfigProperty.NUM_RETRIES_SYNC_OPS.getValue(configuration);
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {
            SaveSchemaTask saveSchemaTask = new SaveSchemaTask(transmissionStrategy);
            saveSchemaTask.addProperty(SESSION_ID, sessionId);

            saveSchemaTask.setSchema(schema);
            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, saveSchemaTask);

            taskManager.submitTask(retryTask);
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "saveSchema failed", e);
            throw new RtaException(e);
        }
    }

    /**
     * Register schema.
     *
     * @param schema the schema
     */
    @Override
    public RtaSchema registerSchema(RtaSchema schema) throws RtaException {
        checkSessionState();
        // Check if this schema exists already. If yes do nothing
        boolean schemaExists = namesToSchemasMap.containsValue(schema);
        if (!schemaExists) {
            namesToSchemasMap.put(schema.getName(), schema);
            return schema;
        } else {
            return schema;
        }
    }

    /**
     * Delete schema.
     *
     * @param schemaName the name of schema to delete
     */
    @Idempotent
    public void deleteSchema(String schemaName) throws RtaException {
        checkSessionState();

        int retryCount = (Integer) ConfigProperty.NUM_RETRIES_SYNC_OPS.getValue(configuration);
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {
            DeleteSchemaTask deleteSchemaTask = new DeleteSchemaTask(transmissionStrategy);
            deleteSchemaTask.addProperty("schemaname", schemaName);
            deleteSchemaTask.addProperty(SESSION_ID, sessionId);

            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, deleteSchemaTask);

            taskManager.submitTask(retryTask);
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "removeSchema failed", e);
            throw new RtaException(e);
        }
    }

    /**
     * Publish a fact to the metric/aggregation engine.
     * <p>
     * Facts will be buffered and asynchronously asserted to the metric engine in a manner driven by the buffer criterion
     * </p>
     *
     * @param fact The fact instance to be published.
     * @return a boolean indicating whether fact was submitted for publish operation or not.
     * @throws RtaException
     * @see com.tibco.rta.queues.BufferCriterion
     */
    @Override
    @GuardedBy("mainLock")
    @Idempotent
    public Future<Object> publishFact(Fact fact) throws RtaException {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "In Publish Fact");
        }
        if (fact == null) {
            throw new RtaException("Invalid Argument, fact is null ");
        }

        checkSessionState();

        transmissionStrategy.enqueueForTransmission(fact);
        // Return null for now.
        return null;
    }

    /**
     * Creates a new {@link Query} nad returns a handle to it.
     *
     * @throws RtaException
     */
    @Override
    public Query createQuery() throws RtaException {
        checkSessionState();
        return new QueryImpl(this);
    }

    /**
     * @throws Exception
     */

    @SuppressWarnings("unchecked")
    @Idempotent
    public <T extends MetricResultTuple> Browser<T> registerQuery(QueryImpl query) throws Exception {
        checkSessionState();

        int retryCount = (Integer) ConfigProperty.NUM_RETRIES_SYNC_OPS.getValue(configuration);
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {
            if (query.getQueryType() == QueryType.STREAMING) {
                QueryResultEventNotificationHandler resultEventNotificationHandler = QueryResultEventNotificationHandler.INSTANCE;
                resultEventNotificationHandler.registerQuery(query);
                receptionStrategy.registerNotificationHandler(resultEventNotificationHandler);
            }
            ModelValidationUtils.validateQuery(query, this);

            QueryRegistrationTask queryRegistrationTask = new QueryRegistrationTask(transmissionStrategy);
            queryRegistrationTask.setQuery(query);
            queryRegistrationTask.addProperty(SESSION_ID, sessionId);
            queryRegistrationTask.addProperty(SESSION_NAME, name);
            queryRegistrationTask.addProperty(QUERY_NAME, query.getName());

            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, queryRegistrationTask);

            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Registering query [%s]", query.getName());
            }

            SnapshotBrowserProxy proxy = (SnapshotBrowserProxy) taskManager.submitTask(retryTask);

            if (proxy != null) {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Query registered [%s] with browser id [%s]", query.getName(), proxy.getId());
                }
                proxy.setSession(this);
            }
            return proxy;
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "Failed to create query", e);
            throw new RtaException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Idempotent
    public void unregisterQuery(QueryImpl query) throws Exception {
        checkSessionState();

        int retryCount = (Integer) ConfigProperty.NUM_RETRIES_SYNC_OPS.getValue(configuration);
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {
            QueryUnregisterationTask queryUnregisterationTask = new QueryUnregisterationTask(transmissionStrategy);
            queryUnregisterationTask.setQuery(query);
            queryUnregisterationTask.addProperty(SESSION_ID, sessionId);

            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, queryUnregisterationTask);
            taskManager.submitTask(retryTask);
            // Assuming unregister went through. Also unregister from handler if streaming query
            if (query.getQueryType() == QueryType.STREAMING) {
                QueryResultEventNotificationHandler.INSTANCE.unregisterQuery(query);
            }
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "Failed to unregister query", e);
            throw new RtaException(e);
        }
    }


    @Idempotent
    public boolean hasNextResult(String browserId) throws Exception {
        checkSessionState();

        int retryCount = 1;
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {
            ResultAvailableTask resultAvailableTask = new ResultAvailableTask(transmissionStrategy, "hasNextResult");
            resultAvailableTask.addProperty(BROWSER_ID, browserId);
            resultAvailableTask.addProperty(SESSION_ID, sessionId);
            resultAvailableTask.setServiceType(ServiceType.QUERY);
            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, resultAvailableTask);

            return (Boolean) taskManager.submitTask(retryTask);
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "Failed to perform remote op", e);
            throw new RtaException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Idempotent
    public Collection<QueryResultTuple> nextResult(String browserId) throws Exception {
        checkSessionState();

        int retryCount = 1;
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {

            FetchResultsTask fetchResultsTask = new FetchResultsTask(transmissionStrategy, "nextResult");
            fetchResultsTask.addProperty(BROWSER_ID, browserId);
            fetchResultsTask.addProperty(SESSION_ID, sessionId);
            fetchResultsTask.setServiceType(ServiceType.QUERY);
            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, fetchResultsTask);

            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Requesting query next operation for browser id [%s]", browserId);
            }

            QueryResultTupleCollection queryResultTupleCollection = (QueryResultTupleCollection) taskManager.submitTask(retryTask);

            return queryResultTupleCollection.getQueryResultTuples();
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "Failed to fetch result", e);
            throw new RtaException(e);
        }
    }


    public void stopBrowser(String browserId) throws Exception {
        checkSessionState();

        int retryCount = 1;
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {

            StopBrowserTask stopBrowserTask = new StopBrowserTask(transmissionStrategy, "stopBrowser");
            stopBrowserTask.addProperty(BROWSER_ID, browserId);
            stopBrowserTask.addProperty(SESSION_ID, sessionId);
            stopBrowserTask.setServiceType(ServiceType.QUERY);
            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, stopBrowserTask);

            taskManager.submitTask(retryTask);
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "Failed to stop browser", e);
            throw new RtaException(e);
        }
    }

    @Override
    public void setNotificationListener(RtaNotificationListener listener) {
        setNotificationListener(listener, NotificationListenerKey.EVENT_SERVER_HEALTH | NotificationListenerKey.EVENT_FACT_DROP | NotificationListenerKey.EVENT_FACT_PUBLISH | NotificationListenerKey.EVENT_TASK_REJECT);
    }

    @Override
    @GuardedBy("mainLock")
    public void setNotificationListener(RtaNotificationListener notificationListener, int interestEvents) {
        this.notificationListener = notificationListener;
        NotificationListenerKey notificationListenerKey = new NotificationListenerKey();
        notificationListenerKey.setInterestEvents(interestEvents);

        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            taskManager.registerTaskCompletionListener(new DefaultQueueEventNotificationListener(notificationListener, notificationListenerKey));
            taskManager.registerTaskRejectionHandler(new TaskRejectionHandler(notificationListener, notificationListenerKey));
            // Register for connection events
            // TODO
            receptionStrategy.setInterestEvents(interestEvents);
            new DefaultWorkQueueEavesDropper(notificationListener, notificationListenerKey);

            if (transactionNotificationHandler == null) {
                transactionNotificationHandler = new TransactionEventNotificationHandler(notificationListener);
                receptionStrategy.registerNotificationHandler(transactionNotificationHandler);
            }
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }

    /**
     * TODO should we wait for the setter to complete when this is called?
     */
    public RtaNotificationListener getNotificationListener() {
        return notificationListener;
    }

    @Override
    @GuardedBy("mainLock")
    public void setCommandListener(RtaCommandListener commandListener) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            CommandEventNotificationHandler commandEventNotificationHandler = new CommandEventNotificationHandler(commandListener);
            receptionStrategy.registerNotificationHandler(commandEventNotificationHandler);
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }

    @Override
    @Idempotent
    public <S, T> Browser<T> getChildMetrics(Metric<S> metric, List<MetricFieldTuple> orderByList) throws RtaException {
        if (orderByList != null && !orderByList.isEmpty()) {
            try {
                ModelValidationUtils.validateDimensionOrderedList(orderByList, metric, this);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Failed to get browser for metric children", e);
                throw new RtaException(e);
            }
        }
        return getMetricChildrenBrowser(metric, orderByList, "getChildMetricsBrowser");
    }

    @SuppressWarnings("unchecked")
    // TODO ordering
    private <S, T> Browser<T> getMetricChildrenBrowser(Metric<S> metric, List<MetricFieldTuple> orderByList, String taskName) throws RtaException {
        checkSessionState();

        int retryCount = (Integer) ConfigProperty.NUM_RETRIES_SYNC_OPS.getValue(configuration);
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {
            GetMetricChildrenTask metricChildFactsTask = new GetMetricChildrenTask(transmissionStrategy, taskName);
            metricChildFactsTask.addProperty(SESSION_ID, sessionId);
            metricChildFactsTask.setMetric(metric);
            metricChildFactsTask.setOrderList(orderByList);
            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, metricChildFactsTask);

            MetricChildrenBrowserProxy proxy = (MetricChildrenBrowserProxy) taskManager.submitTask(retryTask);
            if (proxy != null) {
                proxy.setSession(this);
            }
            return proxy;
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "Failed to get browser for metric children", e);
            throw new RtaException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @Idempotent
    public <S, T> Browser<T> getConstituentFacts(Metric<S> metric, List<MetricFieldTuple> orderByList) throws RtaException {
        if (orderByList != null && !orderByList.isEmpty()) {
            try {
                ModelValidationUtils.validateAttributeOrderedList(orderByList, metric, this);
            } catch (Throwable e) {
                LOGGER.log(Level.ERROR, "Failed to create child fact browser", e);
                throw new RtaException(e);
            }
        }
        return getMetricChildrenBrowser(metric, orderByList, "getMetricChildFactsBrowser");
    }


    @SuppressWarnings("unchecked")
    @Idempotent
    public boolean hasNextChild(String browserId) throws Exception {
        checkSessionState();

        int retryCount = 1;
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {
            ResultAvailableTask resultAvailableTask = new ResultAvailableTask(transmissionStrategy, "hasNextChild");
            resultAvailableTask.addProperty(BROWSER_ID, browserId);
            resultAvailableTask.addProperty(SESSION_ID, sessionId);
            resultAvailableTask.setServiceType(ServiceType.METRICS_INTROSPECTION);
            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, resultAvailableTask);

            return (Boolean) taskManager.submitTask(retryTask);
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "Failed to fetch result", e);
            throw new RtaException(e);
        }
    }


    @Idempotent
    @SuppressWarnings("unchecked")
    public <T> T nextChild(String browserId) throws Exception {
        checkSessionState();

        int retryCount = 1;
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {

            FetchResultsTask fetchResultsTask = new FetchResultsTask(transmissionStrategy, "nextChild");
            fetchResultsTask.addProperty(BROWSER_ID, browserId);
            fetchResultsTask.addProperty(SESSION_ID, sessionId);
            fetchResultsTask.setServiceType(ServiceType.METRICS_INTROSPECTION);
            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, fetchResultsTask);

            //TODO this will break. Need to fix.
            QueryResultTupleCollection queryResultTupleCollection = (QueryResultTupleCollection) taskManager.submitTask(retryTask);
            //There will be only one
            return (T) queryResultTupleCollection.get(0);
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "Failed to fetch result", e);
            throw new RtaException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ActionFunctionDescriptor> getAllActionFunctionDescriptors() throws RtaException {
        checkSessionState();

        int retryCount = (Integer) ConfigProperty.NUM_RETRIES_SYNC_OPS.getValue(configuration);
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {
            GetAllActionFunctionDescriptorsTask getFunctionTask = new GetAllActionFunctionDescriptorsTask(transmissionStrategy);

            getFunctionTask.addProperty(SESSION_ID, sessionId);

            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, getFunctionTask);

            return (List<ActionFunctionDescriptor>) taskManager.submitTask(retryTask);
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "getAllMetricFunctionDescriptorsTask failed", e);
            throw new RtaException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MetricFunctionDescriptor> getAllFunctionDescriptors() throws RtaException {
        checkSessionState();

        int retryCount = (Integer) ConfigProperty.NUM_RETRIES_SYNC_OPS.getValue(configuration);
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {
            GetAllMetricFunctionDescriptorsTask getFunctionTask = new GetAllMetricFunctionDescriptorsTask(transmissionStrategy, hasLoadedFunctions.get());
            getFunctionTask.addProperty(SESSION_ID, sessionId);

            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, getFunctionTask);

            return (List<MetricFunctionDescriptor>) taskManager.submitTask(retryTask);

        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "getAllMetricFunctionDescriptorsTask failed", e);
            throw new RtaException(e);
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<String> getRules() throws RtaException {
        checkSessionState();

        int retryCount = (Integer) ConfigProperty.NUM_RETRIES_SYNC_OPS.getValue(configuration);
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {
            GetRulesTask getRulesTask = new GetRulesTask(transmissionStrategy);
            getRulesTask.addProperty(SESSION_ID, sessionId);

            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, getRulesTask);

            return (List<String>) taskManager.submitTask(retryTask);
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "getRules failed", e);
            throw new RtaException(e);
        }
    }

    @Override
    public RuleDef getRule(String ruleName) throws RtaException {
        checkSessionState();

        int retryCount = (Integer) ConfigProperty.NUM_RETRIES_SYNC_OPS.getValue(configuration);
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {
            GetRuleTask getRuleTask = new GetRuleTask(transmissionStrategy);
            getRuleTask.addProperty(RULENAME, ruleName);
            getRuleTask.addProperty(SESSION_ID, sessionId);

            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, getRuleTask);
            Object obj = taskManager.submitTask(retryTask);

            if (obj instanceof RuleDef) {
                return (RuleDef) obj;
            }
            return null;
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "getRule failed", e);
            throw new RtaException(e);
        }
    }

    @Override
    public void createRule(RuleDef rule) throws Exception {
        checkSessionState();

        int retryCount = (Integer) ConfigProperty.NUM_RETRIES_SYNC_OPS.getValue(configuration);
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {
            CreateRuleTask createRuleTask = new CreateRuleTask(transmissionStrategy);
            createRuleTask.addProperty(SESSION_ID, sessionId);
            ((MutableRuleDef) rule).setCreatedDate(Calendar.getInstance());
            ((MutableRuleDef) rule).setModifiedDate(Calendar.getInstance());
            ModelValidationUtils.validateRule(rule);

            createRuleTask.setRule(rule);

            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, createRuleTask);

            taskManager.submitTask(retryTask);
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "createRule failed", e);
            throw new RtaException(e);
        }
    }

    @Override
    public void updateRule(RuleDef rule) throws RtaException {
        checkSessionState();

        int retryCount = (Integer) ConfigProperty.NUM_RETRIES_SYNC_OPS.getValue(configuration);
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);       

        try {
            UpdateRuleTask updateRuleTask = new UpdateRuleTask(transmissionStrategy);
            updateRuleTask.addProperty(SESSION_ID, sessionId);
            if (rule == null) {
                LOGGER.log(Level.ERROR, "Rule is null");
                throw new Exception("Rule is null");
            }

            Double updatedVersion = SessionUtils.getUpdatedVersion(rule.getVersion());
            ((MutableRuleDef) rule).setVersion("" + updatedVersion);
            ((MutableRuleDef) rule).setModifiedDate(Calendar.getInstance());
            updateRuleTask.setRule(rule);

            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, updateRuleTask);

            taskManager.submitTask(retryTask);

        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "updateRule failed", e);
            throw new RtaException(e);
        }
    }

    @Override
    public void deleteRule(String ruleName) throws RtaException {
        checkSessionState();

        int retryCount = (Integer) ConfigProperty.NUM_RETRIES_SYNC_OPS.getValue(configuration);
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {
            if (ruleName == null || ruleName.isEmpty()) {
                throw new Exception("Rule name is null");
            }
            DeleteRuleTask deleteRuleTask = new DeleteRuleTask(transmissionStrategy);
            deleteRuleTask.addProperty(RULENAME, ruleName);
            deleteRuleTask.addProperty(SESSION_ID, sessionId);

            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, deleteRuleTask);

            taskManager.submitTask(retryTask);

        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "deleteRule failed", e);
            throw new RtaException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RuleDef> getAllRuleDefs() throws Exception {
        checkSessionState();

        int retryCount = (Integer) ConfigProperty.NUM_RETRIES_SYNC_OPS.getValue(configuration);
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {
            GetAllRuleDefsTask getRuleTask = new GetAllRuleDefsTask(transmissionStrategy);
            getRuleTask.addProperty(SESSION_ID, sessionId);
            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, getRuleTask);

            Object obj = taskManager.submitTask(retryTask);
            if (obj instanceof List<?>) {
                return (List<RuleDef>) obj;
            }
            return null;
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "getAllRuleDefs failed", e);
            throw new RtaException(e);
        }
    }

    @Override
    public ServerConfigurationCollection getServerConfiguration() throws RtaException {
        checkSessionState();

        int retryCount = (Integer) ConfigProperty.NUM_RETRIES_SYNC_OPS.getValue(configuration);
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {
            FetchServerConfigurationTask serverConfigurationTask = new FetchServerConfigurationTask(transmissionStrategy);
            serverConfigurationTask.addProperty(SESSION_ID, sessionId);
            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, serverConfigurationTask);

            return (ServerConfigurationCollection) taskManager.submitTask(retryTask);

        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "getServerConfiguration failed", e);
            throw new RtaException(e);
        }
    }

    private void clearAlerts(QueryDef queryDef) throws RtaException {
        int retryCount = (Integer) ConfigProperty.NUM_RETRIES_SYNC_OPS.getValue(configuration);
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {
            ClearAlertsTask clearAlertTask = new ClearAlertsTask(transmissionStrategy);
            clearAlertTask.addProperty(SESSION_ID, sessionId);
            clearAlertTask.setQuery(queryDef);

            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, clearAlertTask);
            taskManager.submitTask(retryTask);

        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "clear alerts failed", e);
            throw new RtaException(e);
        }
    }

    @Override
    public void clearAlerts(Collection<String> alert_ids) throws RtaException {
        checkSessionState();

        if (systemSchemas.size() == 0) {
            getAllSchemas();
        }

        for (QueryDef queryDef : SessionUtils.createClearAlertsQuerys(systemSchemas.values(), alert_ids)) {
            clearAlerts(queryDef);
        }
    }

    public Collection<RtaSchema> getAllSchemas() throws RtaException {
        checkSessionState();

        int retryCount = (Integer) ConfigProperty.NUM_RETRIES_SYNC_OPS.getValue(configuration);
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        try {
            GetAllSchemasTask getSchemasTask = new GetAllSchemasTask(transmissionStrategy);
            getSchemasTask.addProperty(SESSION_ID, sessionId);
            IdempotentRetryTask retryTask = new IdempotentRetryTask(this, retryCount, waitTime, getSchemasTask);

            Object obj = taskManager.submitTask(retryTask);
            if (obj instanceof Collection) {
                @SuppressWarnings("unchecked")
                Collection<RtaSchema> schemas = (Collection<RtaSchema>) obj;
                for (RtaSchema schema : schemas) {
                    namesToSchemasMap.putIfAbsent(schema.getName(), schema);
                    if (schema.isSystemSchema()) {
                        systemSchemas.putIfAbsent(schema.getName(), schema);
                    }
                }
                return schemas;
            }
            return null;
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "getSchemasTask failed", e);
            throw new RtaException(e);
        }
    }
}
