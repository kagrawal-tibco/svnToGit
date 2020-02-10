package com.tibco.rta.common.service.session.command.impl;

import com.tibco.rta.common.service.session.ServerSession;
import com.tibco.rta.common.service.session.command.PersistenceAwareCommandManager;
import com.tibco.rta.common.service.session.command.RemoveCommand;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 16/4/13
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class SessionCloseCommand implements RemoveCommand<ServerSession<?>> {

    /**
     * The target object on which to operate.
     */
    private ServerSession<?> serverSession;

    private PersistenceAwareCommandManager commandManager;

    public SessionCloseCommand(ServerSession<?> serverSession, PersistenceAwareCommandManager commandManager) {
        this.serverSession = serverSession;
        this.commandManager = commandManager;
    }

    @Override
    public void execute() {
        serverSession.getSessionStateKeeper().cleanup();
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
    public ServerSession<?> getTargetObject() {
        return serverSession;
    }
}
