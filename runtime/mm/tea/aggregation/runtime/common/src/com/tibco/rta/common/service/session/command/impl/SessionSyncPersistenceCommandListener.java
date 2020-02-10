package com.tibco.rta.common.service.session.command.impl;

import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.session.command.AddCommand;
import com.tibco.rta.common.service.session.command.RemoveCommand;
import com.tibco.rta.common.service.session.command.SessionCommand;
import com.tibco.rta.common.service.session.command.SessionCommandListener;
import com.tibco.rta.service.persistence.SessionPersistenceService;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 13/4/13
 * Time: 8:44 AM
 * Perform persistence of this session. Only applicable for FT mode.
 */
public class SessionSyncPersistenceCommandListener implements SessionCommandListener {

    @Override
    public <C extends SessionCommand<?>> void onExecute(C sessionCommand) {
        try {
            SessionPersistenceService persistenceService = ServiceProviderManager.getInstance().getSessionPersistenceService();
            if (persistenceService != null) {
                if (sessionCommand instanceof RemoveCommand) {
                    persistenceService.cleanup(sessionCommand.getSession());
                } else if (sessionCommand instanceof AddCommand) {
                    persistenceService.persist(sessionCommand.getSession());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
