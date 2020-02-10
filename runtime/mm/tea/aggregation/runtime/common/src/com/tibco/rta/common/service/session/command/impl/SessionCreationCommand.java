package com.tibco.rta.common.service.session.command.impl;

import com.tibco.rta.common.service.session.ServerSession;
import com.tibco.rta.common.service.session.command.AddCommand;
import com.tibco.rta.common.service.session.command.PersistenceAwareCommandManager;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/5/13
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class SessionCreationCommand implements AddCommand<ServerSession<?>> {

    /**
     * The target object on which to operate.
     */
    private ServerSession<?> serverSession;

    private PersistenceAwareCommandManager commandManager;

    public SessionCreationCommand(ServerSession<?> serverSession, PersistenceAwareCommandManager commandManager) {
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
