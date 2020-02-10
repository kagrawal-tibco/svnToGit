package com.tibco.rta.queues.event;

import com.tibco.rta.queues.event.QueueEavesDropEvent;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 1/3/13
 * Time: 8:59 PM
 * To change this template use File | Settings | File Templates.
 */
public interface QueueEavesDropper {

    /**
     *
     */
    public void notifyEavesDropper(QueueEavesDropEvent eavesDropEvent);
}
