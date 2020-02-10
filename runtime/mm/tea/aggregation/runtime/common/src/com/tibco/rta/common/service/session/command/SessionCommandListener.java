package com.tibco.rta.common.service.session.command;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 13/4/13
 * Time: 8:39 AM
 * Register for notifications on command execute.
 */
public interface SessionCommandListener {

    /**
     *
     * @param sessionCommand
     * @param <C>
     */
    public <C extends SessionCommand<?>> void onExecute(C sessionCommand);
}
