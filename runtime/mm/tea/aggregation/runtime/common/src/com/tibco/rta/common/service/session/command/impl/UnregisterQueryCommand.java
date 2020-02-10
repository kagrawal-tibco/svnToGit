package com.tibco.rta.common.service.session.command.impl;

import com.tibco.rta.common.service.session.ServerSession;
import com.tibco.rta.common.service.session.SessionStateKeeper;
import com.tibco.rta.common.service.session.command.PersistenceAwareCommandManager;
import com.tibco.rta.common.service.session.command.RemoveCommand;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.QueryType;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 18/4/13
 * Time: 11:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class UnregisterQueryCommand implements RemoveCommand<QueryDef> {

    /**
     * Query to unregister.
     */
    private QueryDef query;

    /**
     * The target object on which to operate.
     */
    private ServerSession<?> serverSession;

    private PersistenceAwareCommandManager commandManager;

    public UnregisterQueryCommand(QueryDef query, ServerSession<?> serverSession, PersistenceAwareCommandManager commandManager) {
        this.query = query;
        this.serverSession = serverSession;
        this.commandManager = commandManager;
    }

    @Override
    public void execute() {
        if (query.getQueryType() == QueryType.STREAMING) {
            SessionStateKeeper sessionStateKeeper = serverSession.getSessionStateKeeper();
            sessionStateKeeper.unregisterQuery(query);
        }
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
