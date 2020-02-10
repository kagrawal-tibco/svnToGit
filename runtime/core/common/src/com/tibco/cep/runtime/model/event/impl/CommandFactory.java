package com.tibco.cep.runtime.model.event.impl;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.runtime.model.event.CommandEvent;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jan 23, 2009
 * Time: 7:54:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandFactory {
    ConcurrentHashMap registrations = new ConcurrentHashMap();

    public CommandFactory() {
    }

    public final void registerCommandType (String uri, Class entityClz) throws Exception{
        Constructor cons=entityClz.getConstructor(new Class[] {});
        if (cons != null)
            registrations.put(uri, cons);
    }

    public final CommandEvent createCommandEvent(String uri, byte [] buf) throws Exception {
        Constructor cons= (Constructor) registrations.get(uri);
        if (cons != null) {
            CommandEvent evt= (CommandEvent) cons.newInstance(new Object[]{});
            evt.deserialize(buf);
            return evt;
        }
        throw new Exception("Command[ID=" + uri + " not registered");
    }


}
