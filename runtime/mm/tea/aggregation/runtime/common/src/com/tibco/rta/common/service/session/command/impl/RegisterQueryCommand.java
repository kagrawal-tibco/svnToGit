package com.tibco.rta.common.service.session.command.impl;

import com.tibco.rta.common.service.session.ServerSession;
import com.tibco.rta.common.service.session.SessionStateKeeper;
import com.tibco.rta.common.service.session.command.AddCommand;
import com.tibco.rta.common.service.session.command.PersistenceAwareCommandManager;
import com.tibco.rta.query.QueryDef;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 13/4/13
 * Time: 8:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class RegisterQueryCommand implements AddCommand<QueryDef> {

    /**
     * Query to register.
     */
    private QueryDef query;

    /**
     * The target object on which to operate.
     */
    private ServerSession<?> serverSession;

    private PersistenceAwareCommandManager commandManager;

    public RegisterQueryCommand(QueryDef query, ServerSession<?> serverSession, PersistenceAwareCommandManager commandManager) {
        this.query = query;
        this.serverSession = serverSession;
        this.commandManager = commandManager;
    }

    @Override
    public void execute() {
        SessionStateKeeper sessionStateKeeper = serverSession.getSessionStateKeeper();
        sessionStateKeeper.registerQuery(query);
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
    public QueryDef getTargetObject() {
        return query;
    }
}
