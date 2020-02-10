package com.tibco.rta.queues;

import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.queues.event.QueueEavesDropEvent;
import com.tibco.rta.queues.event.QueueEavesDropper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by aathalye
 * Date : 18/12/14
 * Time : 12:21 PM
 */
public abstract class AbstractBatchAwareQueue implements BatchAwareQueue, BatchAwareQueueMBean {

    protected static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_COMMON.getCategory());

    protected String queueName;

    /**
     * Queue depth for max number of elements.
     */
    protected int highUnits;

    /**
     * Used for calculating rate metric.
     */
    private final long creationTime;

    /**
     * Number of elements added to this queue from the time it was created.
     */
    private AtomicLong elementsAdded = new AtomicLong(0);

    /**
     * Number of elements drained from this queue from the time it was created.
     */
    private AtomicLong elementsTaken = new AtomicLong(0);

    /**
     * List of eaves droppers interested in queue events
     */
    protected List<QueueEavesDropper> eavesDroppers = new ArrayList<QueueEavesDropper>();

    public AbstractBatchAwareQueue(String queueName, int highUnits) {
        this.queueName = queueName;
        this.highUnits = highUnits;
        this.creationTime = System.currentTimeMillis();
    }

    public abstract BatchJob peek();

    public String getQueueName() {
        return queueName;
    }

    @Override
    public int getCurrentSize() {
        return size();
    }

    @Override
    public int getQueueDepth() {
        return highUnits;
    }

    @Override
    public long getDrainedCount() {
        return elementsTaken.get();
    }

    @Override
    public long getAddCount() {
        return elementsAdded.get();
    }

    @Override
    public double getInflowRate() {
        double diff = (System.currentTimeMillis() - creationTime) / 1000;
        return getAddCount() / diff;
    }

    @Override
    public double getOutflowRate() {
        double diff = (System.currentTimeMillis() - creationTime) / 1000;
        return getDrainedCount() / diff;
    }

    /**
     * Increment batch addition counter.
     */
    protected void incOffer() throws QueueException {
        //Increment element added
        elementsAdded.getAndIncrement();
    }

    /**
     * Increment batch take counter.
     */
    protected void incTake(int takeCount) {
        //Increment element taken
        elementsTaken.getAndAdd(takeCount);
    }

    /**
     * Register eaves droppers interested in queue events
     *
     */
    public <E extends QueueEavesDropper> void registerEavesDropper(E eavesDropper) {
        eavesDroppers.add(eavesDropper);
    }

    protected void notifyEavesDroppers(BatchJob batchJob) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Notifying eaves droppers of job addition");
        }
        //Notify
        for (QueueEavesDropper eavesDropper : eavesDroppers) {
            //Send dummy job
            eavesDropper.notifyEavesDropper(new QueueEavesDropEvent(batchJob, QueueEavesDropEvent.BUFFER_CRITERION_MET));
        }
    }

    @Override
    public String toString() {
        return queueName;
    }
}
