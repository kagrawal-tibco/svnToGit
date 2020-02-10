package com.tibco.rta.common.service.session;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.rta.RtaCommand;
import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.GMPActivationListener;
import com.tibco.rta.common.service.CustomWorkItemService;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.TransactionEvent;
import com.tibco.rta.common.service.WorkItem;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.common.taskdefs.ClientLivenessConfirmationTask;
import com.tibco.rta.common.taskdefs.IdempotentRetryTask;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.query.QueryResultTuple;
import com.tibco.rta.service.cluster.GroupMember;
import com.tibco.rta.service.cluster.GroupMembershipListener;
import com.tibco.rta.service.cluster.GroupMembershipService;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 8/1/13
 * Time: 3:22 PM
 * Maintain all user sessions.
 */
public class ServerSessionRegistry extends AbstractStartStopServiceImpl implements GroupMembershipListener, GMPActivationListener {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_RUNTIME_SESSION.getCategory());

    /**
     * Milliseconds for heartbeat.
     */
    private long ALLOWABLE_HEARTBEAT_ABSENCE_INTERVAL;

    /**
     * SessionId to user session
     */
    private Map<String, ServerSession<?>> serverSessions = new ConcurrentHashMap<String, ServerSession<?>>();

    /**
     *
     */
    private CustomWorkItemService workItemService;

    /**
     * Should session scanner be activated or not.
     */
    private boolean startSessionScanner;

    /**
     * Timer to check session validity.
     */
    private ScheduledExecutorService sessionScanner;


    private ServerSessionRegistry() {}

    /**
     *
     */
    private void startSessionLivenessScanner() {

        sessionScanner = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {

            private static final String NAME_PREFIX = "Session-Validity-Thread";

            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, NAME_PREFIX);
            }
        });
    }

    /**
     *
     */
    private void stopSessionLivenessScanner() {

        if (sessionScanner != null) {
            sessionScanner.shutdown();
        }
        sessionScanner = null;
    }

    public void init(boolean startSessionScanner) throws Exception {
        this.startSessionScanner = startSessionScanner;

        GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();
        groupMembershipService.addMembershipListener(this);
        groupMembershipService.addActivationListener(this);

        if (startSessionScanner) {
            Map<?, ?> configuration = ServiceProviderManager.getInstance().getConfiguration();

            Object threshold = configuration.get(ConfigProperty.RTA_HEARTBEAT_THRESHOLD.getPropertyName());

            ALLOWABLE_HEARTBEAT_ABSENCE_INTERVAL = (threshold == null) ? 60000L : Long.parseLong((String) threshold);

            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Allowable heartbeat threshold level [%s]", ALLOWABLE_HEARTBEAT_ABSENCE_INTERVAL);
            }
            startSessionLivenessScanner();
        }
    }

    public void start() throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Starting Session Registry service..");
        }
        try {
            workItemService = ServiceProviderManager.getInstance().newCustomWorkItemService("outbound-thread");
            workItemService.init((Properties) ServiceProviderManager.getInstance().getConfiguration());

            if (startSessionScanner) {
                // Start validity scanner
                if (sessionScanner == null) {
                    startSessionLivenessScanner();
                }
                sessionScanner.scheduleWithFixedDelay(new SessionScanner(), 0, 5000L, TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error starting server session registry.", e);
        }
        super.start();
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Starting Session Registry service Complete.");
        }
    }

    public void stop() throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Stopping Session Registry service..");
        }
        try {
            if (workItemService != null) {
                workItemService.stop();
            }
        } catch (Exception e) {

        }

        try {
            if (sessionScanner != null) {
                stopSessionLivenessScanner();
            }
        } catch (Exception e) {

        }

        super.stop();
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Stopping Session Registry service Complete.");
        }
    }

    public static final ServerSessionRegistry INSTANCE = new ServerSessionRegistry();


    public ServerSession<?> addOrCreateSession(String sessionId, String sessionName) throws SessionCreationException {
        return addOrCreateSession(sessionId, sessionName, true);
    }

    /**
     * @param persistable - If true session info can be persisted in FT
     */
    public ServerSession<?> addOrCreateSession(String sessionId, String sessionName, boolean persistable) throws SessionCreationException {
        ServerSession<?> serverSession = serverSessions.get(sessionId);

        if (serverSession == null) {
            //Check for uniqueness
            for (ServerSession<?> tempSession : serverSessions.values()) {
                if (tempSession.getSessionName().equals(sessionName)) {
                    String error = String.format("Session with name [%s] already registered", sessionName);
                    SessionCreationException sessionCreationException = new SessionCreationException(error);
                    sessionCreationException.setSessionName(sessionName);
                    sessionCreationException.setSessionId(sessionId);
                    throw sessionCreationException;
                }
            }
            serverSession = new ServerSession(sessionId, sessionName, persistable);
            serverSessions.put(sessionId, serverSession);
            //Register mbean for session
            try {
                registerSessionMBean(serverSession);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            }
        }
        return serverSession;
    }


    public ServerSession<?> getServerSession(String sessionId) {
        return serverSessions.get(sessionId);
    }

    public List<String> getServerSessionNames() {
        List<String> sessionNames = new ArrayList<String>(serverSessions.size());
        for (ServerSession<?> serverSession : serverSessions.values()) {
            sessionNames.add(serverSession.getSessionName());
        }
        return sessionNames;
    }

    public ServerSession<?> getServerSessionByName(String sessionName) {
        for (ServerSession<?> tempSession : serverSessions.values()) {
            if (tempSession.getSessionName().equals(sessionName)) {
                return tempSession;
            }
        }
        return null;
    }

    public void removeServerSession(String sessionId) {
        ServerSession<?> serverSession = serverSessions.remove(sessionId);
        //Handle closing of server session.
        if (serverSession == null) {
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "No session found with id [%s]", sessionId);
            }
        } else {
            serverSession.close();
        }
    }

    /**
     * Invoke on owner registered user session
     *
     */
    public void invoke(QueryResultTuple queryResultTuple) throws Exception {
        for (ServerSession<?> serverSession : serverSessions.values()) {
            if (serverSession.isQueryOwner(queryResultTuple.getQueryName())) {
                QueryResponseItem responseItem = new QueryResponseItem(serverSession, queryResultTuple);
                workItemService.addWorkItem(responseItem);
                break;
            }
        }
    }

    /**
     * Invoke on all registered user sessions
     *
     */
    public void invoke(TransactionEvent transactionEvent) throws Exception {
        for (ServerSession<?> serverSession : serverSessions.values()) {
            if (serverSession.isOwner(transactionEvent.getTransactionId())) {
                TransactionContextWorkItem responseItem = new TransactionContextWorkItem(serverSession, transactionEvent);
                workItemService.addWorkItem(responseItem);
                break;
            }
        }
    }


    public void sendToNamedSession(String sessionName, RtaCommand command) throws Exception {
        ServerSession<?> serverSession = getServerSessionByName(sessionName);
        if (serverSession != null) {
            sendToNamedSession(serverSession, command);
        } else {
            String error = String.format("Named session [%s] not found", sessionName);
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, error);
            }
            throw new Exception(error);
        }
    }

    /**
     * Update session heartbeat as received from client.
     *
     */
    public void updateHeartbeat(String sessionId, long heartbeatTime) {
        ServerSession<?> serverSession = getServerSession(sessionId);
        if (serverSession != null) {
            serverSession.updateHeartbeat(heartbeatTime);
        }
    }


    private void sendToNamedSession(ServerSession<?> serverSession, RtaCommand command) throws Exception {
        CommandWorkItem commandWorkItem = new CommandWorkItem(serverSession, command);
        workItemService.addWorkItem(commandWorkItem);
    }


    public void sendToAllSessions(RtaCommand command) throws Exception {
        for (ServerSession<?> serverSession : serverSessions.values()) {
            sendToNamedSession(serverSession, command);
        }
    }

    public void close() {
        sessionScanner.shutdownNow();
    }

    private class TransactionContextWorkItem implements WorkItem<TransactionEvent> {

        private ServerSession<?> serverSession;

        private TransactionEvent transactionEvent;

        private TransactionContextWorkItem(ServerSession<?> serverSession, TransactionEvent transactionEvent) {
            this.serverSession = serverSession;
            this.transactionEvent = transactionEvent;
        }

        @Override
        public TransactionEvent get() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public TransactionEvent call() throws Exception {
            try {
                serverSession.publish(transactionEvent);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Error during publishing transaction event", e);
                throw e;
            }
            return null;
        }
    }

    private class CommandWorkItem implements WorkItem<RtaCommand> {

        private ServerSession<?> serverSession;

        private RtaCommand command;

        private CommandWorkItem(ServerSession<?> serverSession, RtaCommand command) {
            this.serverSession = serverSession;
            this.command = command;
        }

        @Override
        public RtaCommand get() {
            return command;
        }

        @Override
        public RtaCommand call() throws Exception {
            try {
                serverSession.publish(command);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Error during publishing command", e);
                throw e;
            }
            return null;
        }
    }

    private class QueryResponseItem implements WorkItem<QueryResultTuple> {

        private ServerSession<?> serverSession;

        private QueryResultTuple queryResultTuple;

        QueryResponseItem(ServerSession<?> serverSession, QueryResultTuple queryResultTuple) {
            this.serverSession = serverSession;
            this.queryResultTuple = queryResultTuple;
        }

        @Override
        public QueryResultTuple get() {
            return null;
        }

        @Override
        public QueryResultTuple call() throws Exception {
            try {
                serverSession.publish(queryResultTuple);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Error during publishing streaming query result tuple for query [%s]", e, queryResultTuple.getQueryName());
            }
            return null;
        }
    }

    long getAllowableHeartbeatAbsenceInterval() {
        return ALLOWABLE_HEARTBEAT_ABSENCE_INTERVAL;
    }

    private class SessionScanner implements Runnable {

        @Override
        public void run() {
            List<ServerSession<?>> copyList = new ArrayList<ServerSession<?>>(serverSessions.values());


            for (ServerSession<?> serverSession : copyList) {
                long lastHeartbeatInstant = serverSession.getLastHeartbeatInstant();
                //If this instant is too old flag it for removal
                long diff = System.currentTimeMillis() - lastHeartbeatInstant;

                if (diff >= ALLOWABLE_HEARTBEAT_ABSENCE_INTERVAL) {

                    if (LOGGER.isEnabledFor(Level.INFO)) {
                        LOGGER.log(Level.INFO, "No hearbeat received in stipulated time for session named [%s]", serverSession.getSessionName());
                        LOGGER.log(Level.INFO, "Requesting client confirmation for session [%s]", serverSession.getSessionName());
                    }
                    if (!requestClientConfirmation(serverSession)) {
                        if (LOGGER.isEnabledFor(Level.INFO)) {
                            LOGGER.log(Level.INFO, "No confirmation received for session named [%s]", serverSession.getSessionName());
                            LOGGER.log(Level.INFO, "Cleaning up and flushing session named [%s]", serverSession.getSessionName());
                        }
                        serverSession.close();
                        //Remove from main map
                        serverSessions.remove(serverSession.getId());
                    } else {
                        if (LOGGER.isEnabledFor(Level.INFO)) {
                            LOGGER.log(Level.INFO, "Liveness confirmation received for session named [%s]", serverSession.getSessionName());
                        }
                        //Update heartbet to current time
                        serverSession.updateHeartbeat(System.currentTimeMillis());
                    }
                }
            }
        }
    }

    private void registerSessionMBean(ServerSession<?> serverSession) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        String mbeanPrefix = (String) ConfigProperty.BE_TEA_AGENT_SERVICE_MBEANS_PREFIX.getValue(BEMMServiceProviderManager.getInstance().getConfiguration());
        ObjectName name = new ObjectName(mbeanPrefix + ".client.session:type=Session-" + serverSession.getId());
        if (!mbs.isRegistered(name)) {
            mbs.registerMBean(serverSession, name);
        }
    }

    private boolean requestClientConfirmation(ServerSession<?> serverSession) {
        boolean confirmed = false;
        //TODO make this configurable.
        IdempotentRetryTask clientConfirmationTask = new IdempotentRetryTask(3, 1000L, new ClientLivenessConfirmationTask(serverSession));
        try {
            confirmed = (Boolean) clientConfirmationTask.perform();
        } catch (Throwable throwable) {
            LOGGER.log(Level.ERROR, "", throwable);
        }
        return confirmed;
    }

    @Override
    public <G extends GroupMember> void memberJoined(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void memberLeft(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void onPrimary(G member) {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Engine activated to primary. Starting session liveness validation service");
        }
        try {
            if (!isStarted()) {
                start();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public <G extends GroupMember> void onSecondary(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void quorumComplete(GroupMember... groupMembers) {

    }

    @Override
    public <G extends GroupMember> void networkFailed() {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Network failure detected. Stopping session liveness scanner");
        }
        stopSessionLivenessScanner();
    }

    @Override
    public <G extends GroupMember> void networkEstablished() {
        //Also update all session heartbeats
        for (ServerSession<?> serverSession : serverSessions.values()) {
            serverSession.updateHeartbeat(System.currentTimeMillis());
        }
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Network re-established");
        }
        if (startSessionScanner) {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Restarting session liveness scanner");
            }
            startSessionLivenessScanner();
            sessionScanner.scheduleWithFixedDelay(new SessionScanner(), 0, 5000L, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public <G extends GroupMember> void onFenced(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void onUnfenced(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void onConflict(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onActivate() {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Engine activated to primary");
        }
        try {
            if (!isStarted()) {
                start();
            }
            for (ServerSession<?> serverSession : serverSessions.values()) {
                serverSession.updateHeartbeat(System.currentTimeMillis());
                serverSession.connectionEstablished();
            }
            if (sessionScanner == null && startSessionScanner) {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Starting session liveness validation service");
                }
                startSessionLivenessScanner();
                sessionScanner.scheduleWithFixedDelay(new SessionScanner(), 0, 5000L, TimeUnit.MILLISECONDS);
            }
            workItemService.resumeExecutor();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onDeactivate() {
        // TODO Auto-generated method stub
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Network failure detected. Stopping session liveness scanner");
        }
        try {
            for (ServerSession<?> serverSession : serverSessions.values()) {
                serverSession.connectionFailure();
            }
            stop();
            workItemService.pauseExecutor();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
