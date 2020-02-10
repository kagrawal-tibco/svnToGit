package com.tibco.rta.common.service.session.command;

import com.tibco.rta.common.service.session.ServerSession;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 13/4/13
 * Time: 8:07 AM
 * Any crud operation on {@link com.tibco.rta.common.service.session.ServerSession} should
 * go via command.
 * <p>
 *     There is no need for undo hence we need not provide that.
 * </p>
 */
public interface SessionCommand<T> {

    /**
     * Execute the command.
     */
    public void execute();

    /**
     * Get underlying session.
     * @return
     */
    public ServerSession<?> getSession();

    /**
     * Get hold of command manager.
     * @return
     */
    PersistenceAwareCommandManager getCommandManager();

    /**
     * The target object affected by this command execution.
     * @return
     */
    T getTargetObject();
}
