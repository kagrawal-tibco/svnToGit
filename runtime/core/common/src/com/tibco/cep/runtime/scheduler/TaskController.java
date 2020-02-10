package com.tibco.cep.runtime.scheduler;

import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Feb 5, 2007
 * Time: 5:56:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TaskController {
    
    public final static String SYSTEM_DEFAULT_DISPATCHER_NAME = "$default.be.mt$";

    void start() throws Exception;

    void shutdown();

    void processEvent(Channel.Destination dest, SimpleEvent event,  EventContext ctx) throws Exception;

    void processTask(String dispatcherName, Runnable task) throws Exception;

    boolean isRunning();

    public interface Task {
        void execute();
    }

    int threadCount();

    void suspend();
    void resume();

    long getNumJobsProcessed();

    double getJobRate();
}
