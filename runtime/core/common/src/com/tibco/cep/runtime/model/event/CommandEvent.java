package com.tibco.cep.runtime.model.event;

import com.tibco.cep.kernel.model.entity.Event;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jan 23, 2009
 * Time: 5:22:14 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CommandEvent extends Event {
    String getURI();
    byte [] serialize();
    void deserialize(byte [] buf);
    void setContext(CommandContext ctx);
    CommandContext getContext();
    void acknowledge();
    interface CommandContext {
        void acknowledge();
    }
}
