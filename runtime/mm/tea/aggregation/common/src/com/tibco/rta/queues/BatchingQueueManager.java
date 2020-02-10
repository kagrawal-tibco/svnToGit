package com.tibco.rta.queues;

import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.queues.event.QueueEavesDropper;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by aathalye
 * Date : 16/12/14
 * Time : 11:25 AM
 */
public class BatchingQueueManager {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_COMMON.getCategory());

    /**
     * Each reader will have its own queue whereas writers would share.
     */
    private static final ThreadLocal<BatchingQueueOwnershipData> BATCH_AWARE_QUEUES = new ThreadLocal<BatchingQueueOwnershipData>();

    /**
     * Each writer maintains the current queue number it has put last fact into.
     */
    private static final ThreadLocal<Integer> CURRENT_QUEUE_COUNTER = new ThreadLocal<Integer>();

    /**
     * Per reader owned queue.
     */
    private BatchingQueueOwnershipData[] batchedBlockingQueueOwnershipData;

    /**
     * General purpose lock.
     */
    private ReentrantLock mainLock = new ReentrantLock();

    private int factBatchSize;

    private long factBatchExpiry;

    private int factQueueDepth;

    private boolean isNonBlocking;

    public BatchingQueueManager(int numQueues,
                                int factBatchSize,
                                long factBatchExpiry,
                                int factQueueDepth) {
        numQueues = (factBatchSize == 1) ? 1 : numQueues;
        isNonBlocking = (numQueues == 1);
        batchedBlockingQueueOwnershipData = new BatchingQueueOwnershipData[numQueues];
        this.factBatchSize = factBatchSize;
        this.factBatchExpiry = factBatchExpiry;
        this.factQueueDepth = factQueueDepth;
        init();
    }

    private void init() {
        //Initialize queues upfront
        for (int loop = 0; loop < batchedBlockingQueueOwnershipData.length; loop++) {
            AbstractBatchAwareQueue batchAwareQueue =
                    BatchAwareQueueFactory.getBatchAwareQueue("Batching-Queue-" + (loop + 1), factQueueDepth, factBatchSize, factBatchExpiry);
            //At construction time there will be no owner thread.
            batchedBlockingQueueOwnershipData[loop] = new BatchingQueueOwnershipData(batchAwareQueue);
            if (!isNonBlocking) {
                //Initialize binary semaphores for each
                batchedBlockingQueueOwnershipData[loop].batchingQueuePermit = new Semaphore(1, true);
            }
            //Register mbeans
            try {
                registerMBean(batchAwareQueue);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            }
        }
    }

    public <Q extends QueueEavesDropper> void registerEavesDropper(Q eavesDropper) {
        for (BatchingQueueOwnershipData batchingQueueOwnershipData : batchedBlockingQueueOwnershipData) {
            batchingQueueOwnershipData.factsQueue.registerEavesDropper(eavesDropper);
        }
    }

    public boolean offer(BatchJob batchJob) throws QueueException {
        return (isNonBlocking) ? offerConcurrent(batchJob) : offerBlocking(batchJob);
    }

    /**
     * Offer in non blocking queue.
     *
     * @throws QueueException
     */
    private boolean offerConcurrent(BatchJob batchJob) throws QueueException {
        BatchAwareQueue batchAwareQueue = batchedBlockingQueueOwnershipData[0].factsQueue;
        return batchAwareQueue.offer(batchJob);
    }

    /**
     * Offer in blocking queue.
     *
     * @throws QueueException
     */
    private boolean offerBlocking(BatchJob batchJob) throws QueueException {
        //Check for each queue to put and for the first one not
        //yet locked by the semaphore.
        //Keep trying this similar to a CAS operation
        while (true) {
            Semaphore lockableSemaphore = null;
            //Try own counter
            int rrCounter = getRRCounter();
            try {
                if (batchedBlockingQueueOwnershipData[rrCounter].batchingQueuePermit.tryAcquire()) {
                    lockableSemaphore = batchedBlockingQueueOwnershipData[rrCounter].batchingQueuePermit;
                    AbstractBatchAwareQueue batchedBlockingQueue = batchedBlockingQueueOwnershipData[rrCounter].factsQueue;
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Putting fact into queue [%s]", batchedBlockingQueue);
                    }
                    return batchedBlockingQueue.offer(batchJob);
                }
                for (BatchingQueueOwnershipData batchingQueueOwnershipData : batchedBlockingQueueOwnershipData) {
                    //Check if appropriate semaphore is locked or not
                    if (batchingQueueOwnershipData.batchingQueuePermit.tryAcquire()) {
                        lockableSemaphore = batchingQueueOwnershipData.batchingQueuePermit;
                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG, "Putting fact into queue [%s]", batchingQueueOwnershipData.factsQueue);
                        }
                        //Lock succeeded
                        AbstractBatchAwareQueue batchAwareQueue = batchingQueueOwnershipData.factsQueue;
                        return batchAwareQueue.offer(batchJob);
                    }
                }
            } finally {
                if (lockableSemaphore != null) {
                    //Release it always
                    lockableSemaphore.release();
                }
            }
        }
    }

    public Collection<BatchJob> take() throws QueueException {
        return (isNonBlocking) ? batchedBlockingQueueOwnershipData[0].factsQueue.take() : takeBlocking();
    }

    @SuppressWarnings("unchecked")
    private Collection<BatchJob> takeBlocking() throws QueueException {
        //Keep trying this similar to a CAS operation
        BatchingQueueOwnershipData ownedBatchAwareQueueData = getOrSetBatchingQueue();
        Semaphore lockableSemaphore = ownedBatchAwareQueueData.batchingQueuePermit;
        //Check if this queue is locked by some writer
        //This will not be null but just putting check to remove warning.
        if (lockableSemaphore != null) {
            while (true) {
                try {
                    //Try to acquire this
                    if (lockableSemaphore.tryAcquire()) {
                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG, "Taking facts from queue [%s]", ownedBatchAwareQueueData.factsQueue);
                        }
                        return ownedBatchAwareQueueData.factsQueue.take();
                    }
                } finally {
                    //Release it always
                    lockableSemaphore.release();
                }
            }
        }
        return new ArrayList<BatchJob>(0);
    }

    /**
     * Round Robin counter.
     */
    private int getRRCounter() {
        Integer value = CURRENT_QUEUE_COUNTER.get();
        if (value == null) {
            //First time set
            CURRENT_QUEUE_COUNTER.set(0);
        }
        int currentRRCounter = CURRENT_QUEUE_COUNTER.get();
        currentRRCounter = (currentRRCounter++ == batchedBlockingQueueOwnershipData.length - 1) ? 0 : currentRRCounter;
        CURRENT_QUEUE_COUNTER.set(currentRRCounter);
        return currentRRCounter;
    }

    private BatchingQueueOwnershipData getOrSetBatchingQueue() {
        BatchingQueueOwnershipData batchedBlockingQueueData = BATCH_AWARE_QUEUES.get();
        if (batchedBlockingQueueData == null) {
            //Need to lock this to make this an atomic operation.
            ReentrantLock mainLock = this.mainLock;
            mainLock.lock();

            try {
                //Assign first available queue to this thread
                for (BatchingQueueOwnershipData batchingQueueOwnershipData : batchedBlockingQueueOwnershipData) {
                    Thread ownerThread = batchingQueueOwnershipData.ownerThread;
                    if (ownerThread == null) {
                        if (LOGGER.isEnabledFor(Level.INFO)) {
                            LOGGER.log(Level.INFO, "Assigning queue [%s] to thread [%s]", batchingQueueOwnershipData.factsQueue, Thread.currentThread().getName());
                        }
                        //Assign this
                        assignQueueToThread(batchingQueueOwnershipData, Thread.currentThread());
                        batchedBlockingQueueData = batchingQueueOwnershipData;
                        break;
                    } else {
                        //Check if owner thread is still alive
                        if (!ownerThread.isAlive()) {
                            //The original owner thread has timed out or is not alive for some other reason.
                            if (LOGGER.isEnabledFor(Level.INFO)) {
                                LOGGER.log(Level.INFO, "Owner thread [%s] for queue [%s] not alive", ownerThread.getName(), batchingQueueOwnershipData.factsQueue);
                                LOGGER.log(Level.INFO, "Reassigning queue [%s] to new thread [%s]", batchingQueueOwnershipData.factsQueue, Thread.currentThread().getName());
                            }
                            //Reassign it to requesting thread
                            //Assign this
                            assignQueueToThread(batchingQueueOwnershipData, Thread.currentThread());
                            batchedBlockingQueueData = batchingQueueOwnershipData;
                            break;
                        }
                    }
                }
            } finally {
                mainLock.unlock();
            }
        }
        return batchedBlockingQueueData;
    }

    private void assignQueueToThread(BatchingQueueOwnershipData batchingQueueOwnershipData, Thread thread) {
        BATCH_AWARE_QUEUES.set(batchingQueueOwnershipData);
        batchingQueueOwnershipData.ownerThread = thread;
    }

    private <Q extends AbstractBatchAwareQueue> void registerMBean(Q factsQueue) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("com.tibco.rta.util:type=" + factsQueue.getQueueName());
        if (!mbs.isRegistered(name)) {
            mbs.registerMBean(factsQueue, name);
        }
    }

    private class BatchingQueueOwnershipData {

        private AbstractBatchAwareQueue factsQueue;

        //Each blocking queue protected by a binary semaphore for writes/reads.
        private Semaphore batchingQueuePermit;

        private Thread ownerThread;

        BatchingQueueOwnershipData(AbstractBatchAwareQueue factsQueue) {
            this.factsQueue = factsQueue;
        }
    }

    /**
     * Close all fact queues.
     */
    public void close() {
        for (BatchingQueueOwnershipData batchingQueueOwnershipData : batchedBlockingQueueOwnershipData) {
            batchingQueueOwnershipData.factsQueue.clear();
        }
    }
}
