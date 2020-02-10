package com.tibco.rta.common.service.session.command.impl;

import com.tibco.rta.common.service.session.ServerSession;
import com.tibco.rta.common.service.session.SessionStateKeeper;
import com.tibco.rta.common.service.session.command.AddCommand;
import com.tibco.rta.common.service.session.command.PersistenceAwareCommandManager;
import com.tibco.rta.model.rule.RuleDef;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 13/4/13
 * Time: 8:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class RegisterRuleCommand implements AddCommand<RuleDef> {

    /**
     * Query to register.
     */
    private RuleDef ruleDef;

    /**
     * The target object on which to operate.
     */
    private ServerSession<?> serverSession;

    private PersistenceAwareCommandManager commandManager;

    public RegisterRuleCommand(RuleDef ruleDef, ServerSession<?> serverSession, PersistenceAwareCommandManager commandManager) {
        this.ruleDef = ruleDef;
        this.serverSession = serverSession;
        this.commandManager = commandManager;
    }

    @Override
    public void execute() {
        SessionStateKeeper sessionStateKeeper = serverSession.getSessionStateKeeper();
        sessionStateKeeper.registerRule(ruleDef);
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
    public RuleDef getTargetObject() {
        return ruleDef;
    }
}
