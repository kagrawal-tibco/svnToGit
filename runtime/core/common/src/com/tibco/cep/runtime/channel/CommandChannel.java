package com.tibco.cep.runtime.channel;

import com.tibco.cep.runtime.model.event.CommandEvent;
import com.tibco.cep.runtime.model.event.CommandListener;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Feb 1, 2009
 * Time: 2:12:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CommandChannel {

    void registerCommand(String key, CommandListener commandListener) throws Exception;

    void sendCommand(String key, CommandEvent cmd, boolean oneToMany) throws Exception;

    void deregisterCommand(String key) throws Exception;

}
