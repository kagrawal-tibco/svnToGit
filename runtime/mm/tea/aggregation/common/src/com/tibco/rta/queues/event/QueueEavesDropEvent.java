package com.tibco.rta.queues.event;

import com.tibco.rta.queues.BatchJob;

import java.util.Collection;
import java.util.EventObject;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 2/3/13
 * Time: 8:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class QueueEavesDropEvent extends EventObject {

    public static final int ENTRY_EVICTED = 1 << 1;

    public static final int BUFFER_CRITERION_MET = 1 << 2;

    private int eventType;

    public QueueEavesDropEvent(BatchJob source, int eventType) {
        super(source);
        this.eventType = eventType;
    }


    public QueueEavesDropEvent(Collection<BatchJob> source, int eventType) {
        super(source);
        this.eventType = eventType;
    }

    public int getEventType() {
        return eventType;
    }
}
