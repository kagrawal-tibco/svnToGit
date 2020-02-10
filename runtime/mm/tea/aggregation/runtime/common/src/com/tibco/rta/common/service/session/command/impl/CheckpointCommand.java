package com.tibco.rta.common.service.session.command.impl;

import com.tibco.rta.common.service.session.ServerSession;
import com.tibco.rta.common.service.session.command.PersistenceAwareCommandManager;
import com.tibco.rta.common.service.session.command.SessionCommand;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 13/4/13
 * Time: 8:30 AM
 * A no-op command used to indicate a milestone in command queue.
 */
public class CheckpointCommand implements SessionCommand<Object> {

    private ServerSession<?> serverSession;

    private PersistenceAwareCommandManager commandManager;

    public CheckpointCommand(ServerSession<?> serverSession, PersistenceAwareCommandManager commandManager) {
        this.serverSession = serverSession;
        this.commandManager = commandManager;
    }

    @Override
    public void execute() {
        //Do nothing command.
    }

    @Override
    public ServerSession<?> getSession() {
        return serverSession;
    }

    @Override
    public PersistenceAwareCommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public Object getTargetObject() {
        return null;
    }
}
