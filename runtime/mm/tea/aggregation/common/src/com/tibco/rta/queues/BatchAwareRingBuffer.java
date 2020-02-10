package com.tibco.rta.queues;

import com.tibco.rta.annotations.GuardedBy;
import com.tibco.rta.annotations.NotThreadSafe;
import com.tibco.rta.log.Level;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by aathalye
 * Date : 31/12/14
 * Time : 11:31 AM
 *
 * A ring-buffer style queue.
 */
@NotThreadSafe
public class BatchAwareRingBuffer extends AbstractBatchAwareQueue implements BatchAwareRingBufferMBean {

    private int takeIndex;
    /**
     * items index for next put, offer, or add.
     */
    private int putIndex;

    /**
     * Current number of non null elements
     */
    private int currentCount;

    /**
     * Internal main collection.
     */
    private BatchJob[] wrappedCollection;

    private BufferCriterion bufferCriterion;

    public BatchAwareRingBuffer(String name, int highUnits, BufferCriterion bufferCriterion) {
        super(name, highUnits);
        this.wrappedCollection = new BatchJob[highUnits];
        this.bufferCriterion = bufferCriterion;
    }


    /**
     * Inserts the specified element into this queue, waiting if necessary
     * for space to become available.
     *
     * @param e the element to add
     * @throws InterruptedException     if interrupted while waiting
     * @throws ClassCastException       if the class of the specified element
     *                                  prevents it from being added to this queue
     * @throws NullPointerException     if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *                                  element prevents it from being added to this queue
     */

    public void put(Collection<BatchJob> e) throws InterruptedException {
        throw new UnsupportedOperationException("" + e);
    }

    /**
     * Inserts the specified element in the ring buffer.
     * <p>
     *     Invoke this only from within a lock or any such guard.
     * </p>
     *
     * @return {@code true} if successful, or {@code false} if
     * the specified waiting time elapses before space is available.
     * @throws com.tibco.rta.queues.QueueException {@inheritDoc}
     */

    @GuardedBy("lockableSemaphore")
    public boolean offer(BatchJob batchJob) throws QueueException {
        if (batchJob == null) {
            throw new QueueException("Null value not permitted");
        }
        return add(batchJob);
    }


    /**
     * Add to queue and return true.
     * @see com.tibco.rta.queues.BatchingQueueManager
     */
    private boolean add(BatchJob batchJob) throws QueueException {
        //Set creation time just before adding it
        batchJob.setCreationTime(System.currentTimeMillis());
        wrappedCollection[putIndex] = batchJob;
        //Increment
        putIndex = inc(putIndex);
        //Wrap around for counter as well.
        currentCount = (currentCount == wrappedCollection.length) ? currentCount : ++currentCount;
        notifyEavesDroppers(batchJob);
        incOffer();
        return true;
    }

    @Override
    public boolean isBlocking() {
        return true;
    }


    /**
     * @see com.tibco.rta.queues.BatchingQueueManager
     */
    @GuardedBy("lockableSemaphore")
    private Collection<BatchJob> takeOrPoll() {
        if (!bufferCriterion.isMet(this)) {
            //Batch size condition not met
            return null;
        }
        Queue<BatchJob> batchDrainQueue = new LinkedList<BatchJob>();
        //Drain to an internal list
        for (int loop = 0; loop < bufferCriterion.getDrainCount(); loop++) {
            if (wrappedCollection[takeIndex] == null) {
                continue;
            }
            batchDrainQueue.add(wrappedCollection[takeIndex]);
            wrappedCollection[takeIndex] = null;
            //Increment take index to next entry
            takeIndex = inc(takeIndex);
            --currentCount;
        }
        if (batchDrainQueue.size() > 0) {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Batch buffer criterion met. Draining [%d] elements from queue", batchDrainQueue.size());
            }
            incTake(batchDrainQueue.size());
        }
        return batchDrainQueue;
    }

    /**
     * Retrieves and removes the head of this queue, waiting if necessary
     * until an element becomes available.
     * <p>
     *     Invoke this only from within a lock or any such guard.
     * </p>
     *
     * @return the head of this queue
     * @throws com.tibco.rta.queues.QueueException if interrupted while waiting
     */

    public Collection<BatchJob> take() throws QueueException {
        return takeOrPoll();
    }

    /**
     * Circularly increment i.
     */
    final int inc(int index) {
        return (++index == wrappedCollection.length) ? 0 : index;
    }

    /**
     * Returns the number of elements in this queue.
     *
     * @return the number of elements in this queue
     */

    public int size() {
        //Return total number of non null elements
        return currentCount;
    }

    @Override
    public int getCurrentPutIndex() {
        return putIndex;
    }

    @Override
    public int getCurrentTakeIndex() {
        return takeIndex;
    }

    /**
     * Return first element in the internal collection
     * which can be taken.
     *
     * @return first element
     */
    public BatchJob peek() {
        return (currentCount == 0) ? null : wrappedCollection[takeIndex];
    }

    /**
     * Returns an array containing all of the elements in this queue, in
     * proper sequence; the runtime type of the returned array is that of
     * the specified array.  If the queue fits in the specified array, it
     * is returned therein.  Otherwise, a new array is allocated with the
     * runtime type of the specified array and the size of this queue.
     * <p/>
     * <p>If this queue fits in the specified array with room to spare
     * (i.e., the array has more elements than this queue), the element in
     * the array immediately following the end of the queue is set to
     * {@code null}.
     * <p/>
     * <p>Like the {@link #} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     * <p/>
     * <p>Suppose {@code x} is a queue known to contain only strings.
     * The following code can be used to dump the queue into a newly
     * allocated array of {@code String}:
     * <p/>
     * <pre>
     *     String[] y = x.toArray(new String[0]);</pre>
     *
     * Note that {@code toArray(new Object[0])} is identical in function to
     * {@code toArray()}.
     *
     *
     * @return an array containing all of the elements in this queue
     * @throws ArrayStoreException  if the runtime type of the specified array
     *                              is not a supertype of the runtime type of every element in
     *                              this queue
     * @throws NullPointerException if the specified array is null
     */

    public BatchJob[] toArray() {
        return Arrays.copyOf(wrappedCollection, wrappedCollection.length);
    }

    /**
     * Close queue and free any resources.
     */
    public void clear() {
        //Clean up internal collection
        int i = takeIndex;
        int k = currentCount;
        while (k-- > 0) {
            wrappedCollection[i] = null;
            i = inc(i);
        }
        currentCount = 0;
        putIndex = 0;
        takeIndex = 0;
    }
}
