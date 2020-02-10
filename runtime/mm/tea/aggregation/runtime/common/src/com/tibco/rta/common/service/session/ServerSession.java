package com.tibco.rta.common.service.session;

import com.tibco.rta.RtaCommand;
import com.tibco.rta.annotations.GuardedBy;
import com.tibco.rta.annotations.ThreadSafe;
import com.tibco.rta.common.ServiceResponse;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.TransactionEvent;
import com.tibco.rta.common.service.session.command.PersistenceAwareCommandManager;
import com.tibco.rta.common.service.session.command.SessionCommand;
import com.tibco.rta.common.service.session.command.impl.RegisterQueryCommand;
import com.tibco.rta.common.service.session.command.impl.SessionCloseCommand;
import com.tibco.rta.common.service.session.command.impl.SessionCreationCommand;
import com.tibco.rta.common.service.session.command.impl.UnregisterQueryCommand;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.impl.ModelJSONSerializer;
import com.tibco.rta.query.QueryResultTuple;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.QueryResultTupleCollection;
import com.tibco.rta.service.query.QueryService;
import com.tibco.rta.util.HeaderConstants;
import com.tibco.rta.util.IOUtils;
import com.tibco.rta.util.ServiceConstants;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 8/1/13
 * Time: 2:30 PM
 * Server equivalent of a client session.
 */
@ThreadSafe
public class ServerSession<S extends SessionOutbound> implements ServerSessionMBean {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_RUNTIME_SESSION.getCategory());

    /**
     * Unique Session id also used for correlation.
     */
    private String serverSessionId;

    /**
     * AN optional name for this session.
     */
    private String sessionName;

    /**
     * Current heartbeat instant as received from client.
     */
    private AtomicLong currentHeartbeatInstant;

    /**
     * Reference to the client socket.
     */
    private S sessionOutbound;

    /**
     * Cache session state here
     */
    private SessionStateKeeper sessionStateKeeper;

    /**
     * Command manager for this server session.
     */
    private PersistenceAwareCommandManager sessionCommandManager;

    /**
     * The current status of the session.
     */
    private SessionStatus sessionStatus;

    /**
     * Main lock for all ops
     */
    private final ReentrantLock mainLock = new ReentrantLock();


    public ServerSession(String serverSessionId, String sessionName) {
        this(serverSessionId, sessionName, true);
    }


    public ServerSession(String serverSessionId, String sessionName, boolean persistable) {
        this.serverSessionId = serverSessionId;
        this.sessionName = sessionName;
        this.sessionStateKeeper = new SessionStateKeeper(this);
        this.sessionCommandManager = new PersistenceAwareCommandManager(this);
        //Sessions with names would need to be persisted in FT
        if (sessionName != null && persistable) {
            SessionCreationCommand sessionCreationCommand = new SessionCreationCommand(this, sessionCommandManager);
            sessionCommandManager.enqueueAndExecute(sessionCreationCommand);
        }
        this.currentHeartbeatInstant = new AtomicLong(System.currentTimeMillis());
        this.sessionStatus = SessionStatus.ACTIVE;
    }


    public void setSessionOutbound(S sessionOutbound) {
        this.sessionOutbound = sessionOutbound;
    }

    public void addTransactionEventId(String transactionEventId) {
        sessionStateKeeper.addTransactionEventId(transactionEventId);
    }

    public boolean isOwner(String transactionEventId) {
        return sessionStateKeeper.isOwner(transactionEventId);
    }

    public SessionStateKeeper getSessionStateKeeper() {
        return sessionStateKeeper;
    }

    /**
     * Needs to be atomically updated
     */
    @GuardedBy("mainLock")
    public void updateHeartbeat(long heartbeatTime) {
        //Needs to wait on lock to avoid updates
        //when session scanner runs.
        ReentrantLock lock = mainLock;
        lock.lock();

        try {
            currentHeartbeatInstant.compareAndSet(currentHeartbeatInstant.get(), heartbeatTime);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @GuardedBy("mainLock")
    public long getLastHeartbeatInstant() {
        ReentrantLock lock = mainLock;
        lock.lock();

        try {
            return currentHeartbeatInstant.get();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }


    public boolean isQueryOwner(String queryName) {
        return sessionStateKeeper.isQueryOwner(queryName);
    }

    public String getSessionName() {
        return sessionName = (sessionName != null) ? sessionName : serverSessionId;
    }

    public String getId() {
        return serverSessionId;
    }

    @Override
    public String getSessionId() {
        return serverSessionId;
    }

    @Override
    public String getStatus() {
        return sessionStatus.name();
    }

    @Override
    public Date getLastHeartbeat() {
        return new Date(getLastHeartbeatInstant());
    }

    @Override
    public long getHeartbeatAbsenceThresholdInterval() {
        return ServerSessionRegistry.INSTANCE.getAllowableHeartbeatAbsenceInterval();
    }

    /**
     * To be called from QueryService
     */
    public void registerQuery(QueryDef queryDef) {
        RegisterQueryCommand registerQueryCommand = new RegisterQueryCommand(queryDef, this, sessionCommandManager);
        sessionCommandManager.enqueueAndExecute(registerQueryCommand);
    }

    /**
     * To be used for recovery
     */
    public void registerRecoveredQuery(QueryDef queryDef) {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Registering recovered query [%s]", queryDef.getName());
        }
        sessionStateKeeper.registerQuery(queryDef);
        ServerSessionManager.serverSessions.set(this);
        try {
            QueryService queryService = ServiceProviderManager.getInstance().getQueryService();
            queryService.registerQuery(queryDef);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error recovering query", e);
        }
    }

    /**
     * To be used for recovery
     *
     */
    public void registerRecoveredRule(RuleDef ruleDef) {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Registering recovered rule [%s]", ruleDef.getName());
        }
        sessionStateKeeper.registerRule(ruleDef);
    }

    /**
     * To be called from QueryService
     *
     */
    public void unregisterQuery(QueryDef queryDef) {
        UnregisterQueryCommand unregisterQueryCommand = new UnregisterQueryCommand(queryDef, this, sessionCommandManager);
        sessionCommandManager.enqueueAndExecute(unregisterQueryCommand);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ServerSession)) {
            return false;
        }
        ServerSession otherSession = (ServerSession) obj;
        return otherSession.serverSessionId.equals(serverSessionId);
    }


    @GuardedBy("mainLock")
    public void publish(QueryResultTuple queryResultTuple) throws Exception {
        ReentrantLock lock = mainLock;
        lock.lock();
        try {
            sessionStateKeeper.appendQueryTuple(queryResultTuple);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }


    @GuardedBy("mainLock")
    public void publish(RtaCommand command) throws Exception {
        byte[] serialized = ModelJSONSerializer.INSTANCE.serializeCommand(command);

        ReentrantLock lock = mainLock;
        lock.lock();
        try {
            sessionOutbound.write("|".getBytes());
            sessionOutbound.write(HeaderConstants.COMMAND_HEADER.getBytes());
            sessionOutbound.write("|".getBytes());
            sessionOutbound.write(serialized);
            sessionOutbound.finish();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * This performs actual publishing and needs to be made thread-safe
     * since the underlying stream is shared
     *
     *
     */
    @GuardedBy("mainLock")
    public void publish(TransactionEvent transactionEvent) throws Exception {
        byte[] bytes = IOUtils.serialize(transactionEvent);

        final ReentrantLock lock = mainLock;
        lock.lock();
        try {
            sessionOutbound.write("|".getBytes());
            sessionOutbound.write(HeaderConstants.TRANSACTION_HEADER.getBytes());
            sessionOutbound.write("|".getBytes());
            sessionOutbound.write(bytes);
            sessionOutbound.finish();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * This performs actual publishing and needs to be made thread-safe
     * since the underlying stream is shared
     *
     * @throws Exception
     */
    @GuardedBy("mainLock")
    public void commit(List<QueryResultTuple> queryResultTuples) throws Exception {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Number of tuples serialized %s", queryResultTuples.size());
        }
        String serialized =
                ModelJSONSerializer.INSTANCE.serializeQueryResults(new QueryResultTupleCollection<QueryResultTuple>(queryResultTuples));
        byte[] bytes = IOUtils.convertStringToByteArray(serialized, "UTF-8");

        final ReentrantLock lock = mainLock;
        lock.lock();

        try {
            sessionOutbound.write("|".getBytes());
            sessionOutbound.write(HeaderConstants.QUERY_TUPLE_HEADER.getBytes());
            sessionOutbound.write("|".getBytes());
            sessionOutbound.write(bytes);
            sessionOutbound.finish();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * This performs actual publishing and needs to be made thread-safe
     * since the underlying stream is shared
     *
     * @throws Exception
     */
    @GuardedBy("mainLock")
    public boolean requestClientConfirmation(Properties properties) throws Exception {
        final ReentrantLock lock = mainLock;
        lock.lock();

        try {
            sessionOutbound.write("|".getBytes());
            sessionOutbound.write(HeaderConstants.SESSION_LIVENESS_HEADER.getBytes());
            sessionOutbound.write("|".getBytes());
            ServiceResponse response = sessionOutbound.sendAndReceive(properties);
            return Boolean.parseBoolean(response.getProperty(ServiceConstants.RESPONSE_CLIENT_ALIVE));
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }


    /**
     * Get delta between checkpoints.
     */
    public Collection<SessionCommand<?>> getCheckpointDelta() {
        return sessionCommandManager.getCheckpointDelta();
    }

    /**
     * Close this session and flush out all residual state
     */
    public void close() {
        SessionCloseCommand sessionCloseCommand = new SessionCloseCommand(this, sessionCommandManager);
        sessionCommandManager.enqueueAndExecute(sessionCloseCommand);
        //Mark status as inactive
        this.sessionStatus = SessionStatus.INACTIVE;
    }


	public void connectionEstablished() {
		sessionStateKeeper.connectionEstablished();
	}


	public void connectionFailure() {
		sessionStateKeeper.connectionFailure();
	}
}
