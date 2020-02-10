package com.tibco.rta.common.service.session.command;

import com.tibco.rta.annotations.ThreadSafe;
import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.session.ServerSession;
import com.tibco.rta.common.service.session.SessionPersistencePolicy;
import com.tibco.rta.common.service.session.command.impl.CheckpointCommand;
import com.tibco.rta.common.service.session.command.impl.SessionSyncPersistenceCommandListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 13/4/13
 * Time: 8:14 AM
 * Command manager for server session commands.
 * <p>
 * One instance per session.
 * </p>
 *
 * @see SessionCommand
 */
@ThreadSafe
public class PersistenceAwareCommandManager {

    private ServerSession<?> ownerSession;

    /**
     * Unbounded blocking queue.
     */
    private BlockingQueue<SessionCommand<?>> commandQueue = new LinkedBlockingQueue<SessionCommand<?>>();

    /**
     * Start with one for now.
     */
    private SessionCommandListener commandListener;

    /**
     * Sync/Async persistence.
     */
    private SessionPersistencePolicy sessionPersistencePolicy;

    public PersistenceAwareCommandManager(ServerSession<?> ownerSession) {
        this.ownerSession = ownerSession;
        init();
    }

    public <S extends SessionCommand<?>> void enqueueAndExecute(S sessionCommand) {
        commandQueue.offer(sessionCommand);
        if (sessionPersistencePolicy == SessionPersistencePolicy.SYNC) {
            //Add dummy checkpoint command
            CheckpointCommand checkpointCommand = new CheckpointCommand(ownerSession, this);
            commandQueue.offer(checkpointCommand);
        }
        //TODO handle errors.
        sessionCommand.execute();
        if (commandListener != null) {
            commandListener.onExecute(sessionCommand);
        }
    }

    public void init() {
        Map<?, ?> configuration = ServiceProviderManager.getInstance().getConfiguration();
        String policy = (String) configuration.get(ConfigProperty.RTA_FT_SESSION_PERSISTENCE_POLICY.getPropertyName());

        sessionPersistencePolicy = (policy == null) ? SessionPersistencePolicy.SYNC : SessionPersistencePolicy.valueOf(policy.toUpperCase());
        switch (sessionPersistencePolicy) {
            case SYNC :
                this.commandListener = new SessionSyncPersistenceCommandListener();
                break;
            case ASYNC :
                throw new UnsupportedOperationException("TBD");
        }
    }

    public Collection<SessionCommand<?>> getCheckpointDelta() {
        int commandCounter = 0;

        for (SessionCommand<?> sessionCommand : commandQueue) {
            if (!(sessionCommand instanceof CheckpointCommand)) {
                commandCounter = commandCounter + 1;
            }
        }
        List<SessionCommand<?>> checkpointDelta = new ArrayList<SessionCommand<?>>(commandCounter);
        //Drain the queue along with the checkpointer.
        commandQueue.drainTo(checkpointDelta, commandCounter + 1);
        return checkpointDelta;
    }
}
