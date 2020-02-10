package com.tibco.cep.driver.local;



import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.impl.AbstractDestination;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.util.ResourceManager;

/**
 * Created by IntelliJ IDEA.
 * User: hzhang
 * Date: Apr 25, 2006
 * Time: 4:32:34 PM
 * To change this template use File | Settings | File Templates.
 */

public class LocalEventSubscriber implements Runnable {
    protected final BlockingQueue queue;
    protected Map handlers = new HashMap ();
    protected boolean start = true;
    protected AbstractDestination destination;
     protected Logger logger;

    LocalEventSubscriber(BlockingQueue queue, AbstractDestination destination, Logger logger) {
        this.destination = destination;
        this.queue = queue;
        this.logger = logger;
    }

    public void run() {
        start();
        try {
            while(start) {
                consume(queue.take());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void consume(Object x) throws Exception {
        if(logger.isEnabledFor(Level.DEBUG)) {
            logger.log(Level.DEBUG,
                    ResourceManager.getInstance().formatMessage("be.channel.receive", destination.getURI(), x));
        }
        Iterator i = handlers.values().iterator();
        while (i.hasNext()) {
            ((RuleSession) i.next()).assertObject(x, true);
        }
    }

    public void addHandler(RuleSession handler) {
        handlers.put(handler.getName(), handler);
    }

    public void stop() {
        start = false;
    }

    protected void start() {
        start = true;
    }

    public RuleSession getHandler(String name) {
        return (RuleSession) handlers.get(name);
    }

    public RuleSession removeHandler(String name) {
        return (RuleSession) handlers.remove(name);
    }

    public void removeHandlers() {
        handlers.clear();
    }
}//class
