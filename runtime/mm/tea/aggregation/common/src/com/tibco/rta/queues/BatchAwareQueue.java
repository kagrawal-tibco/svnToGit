package com.tibco.rta.queues;

import java.util.Collection;

/**
 * Created by aathalye
 * Date : 18/12/14
 * Time : 11:55 AM
 */
public interface BatchAwareQueue {

    public boolean offer(BatchJob batchJob) throws QueueException;

    public Collection<BatchJob> take() throws QueueException;

    public int size();

    public void clear();
}
