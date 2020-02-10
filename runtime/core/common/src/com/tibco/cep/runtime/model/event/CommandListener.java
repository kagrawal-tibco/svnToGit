package com.tibco.cep.runtime.model.event;


/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jan 23, 2009
 * Time: 5:55:33 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CommandListener {
    void onCommand(CommandEvent event);
}
