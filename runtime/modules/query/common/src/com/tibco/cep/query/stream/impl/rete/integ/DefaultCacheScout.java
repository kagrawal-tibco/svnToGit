package com.tibco.cep.query.stream.impl.rete.integ;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import com.tibco.cep.query.stream.cache.SharedObjectSource;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.cache.SharedPointer;
import com.tibco.cep.query.stream.cache.SharedPointerImpl;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.impl.rete.GenericReteEntityHandle;
import com.tibco.cep.query.stream.impl.rete.ReteEntityBatchHandle;
import com.tibco.cep.query.stream.impl.rete.ReteEntityHandle;
import com.tibco.cep.query.stream.impl.rete.ReteEntityHandleType;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.service.CacheScout;
import com.tibco.cep.query.stream.monitor.CustomDaemonThreadFactory;
import com.tibco.cep.query.stream.monitor.CustomThreadGroup;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.monitor.ThreadCentral;

/*
* Author: Ashwin Jayaprakash Date: Jul 25, 2008 Time: 5:40:59 PM
*/
public class DefaultCacheScout implements CacheScout {
    protected static final long WAIT_INTERVAL_MILLIS_BW_WARMUPS = 2500;

    protected static final long SLEEP_INTERVAL_MILLIS_DURING_WARMUPS = 250;

    protected static final long MAX_WARMUP_ITEMS_PER_RUN = 5000;

    protected static final long MAX_CONTINUOUS_RUNS = 5;

    protected static final int SKIP_WARMUP_INITIAL_ITEMS = 50;

    protected static final int MIN_PREFETCH_THREADS = 48;

    protected final SharedObjectSourceRepository sourceRepository;

    protected final ConcurrentHashMap<ReteQuery, Future> queries;

    protected final ScheduledThreadPoolExecutor schedulerService;

    protected final ThreadPoolExecutor prefetchService;

    protected final DummyFuture dummyFuture;

    protected final JobRejectionHandler jobRejectionHandler;

    protected final Logger logger;

    protected final ResourceId id;

    public DefaultCacheScout(ResourceId parentId, SharedObjectSourceRepository sourceRepository,
                             boolean aggressivePrefetchSupported) {
        this.sourceRepository = sourceRepository;

        this.id = new ResourceId(parentId, getClass().getName());

        this.queries = new ConcurrentHashMap<ReteQuery, Future>();

        ThreadCentral threadCentral = Registry.getInstance().getComponent(ThreadCentral.class);
        CustomThreadGroup threadGroup = threadCentral.createOrGetThreadGroup(this.id);
        CustomDaemonThreadFactory threadFactory =
                new CustomDaemonThreadFactory(threadGroup, DefaultCacheScout.class.getSimpleName(),
                        Thread.MIN_PRIORITY);


        this.logger = Registry.getInstance().getComponent(Logger.class);

        this.jobRejectionHandler = new JobRejectionHandler(logger);

        this.schedulerService =
                new ScheduledThreadPoolExecutor(1, threadFactory, this.jobRejectionHandler);

        if (aggressivePrefetchSupported) {
            int cpus = Runtime.getRuntime().availableProcessors();
            int maxThreads = cpus * 4;
            maxThreads = Math.max(MIN_PREFETCH_THREADS, maxThreads);

            this.prefetchService = new ThreadPoolExecutor(1, maxThreads, 60L, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(true), threadFactory, this.jobRejectionHandler);
        }
        else {
            this.prefetchService = null;
        }

        this.dummyFuture = new DummyFuture();
    }

    public ResourceId getResourceId() {
        return id;
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
        queries.clear();

        if (prefetchService != null) {
            prefetchService.shutdownNow();
        }

        schedulerService.shutdownNow();

        id.discard();
    }

    //--------------

    public void startWarming(ReteQuery reteQuery) {
        queries.put(reteQuery, dummyFuture);

        trySpawnJob(reteQuery);
    }

    public void stopWarming(ReteQuery reteQuery) {
        Future future = queries.remove(reteQuery);

        if (future != null && future != dummyFuture) {
            future.cancel(true);
        }
    }

    //--------------

    public boolean isAggressivePrefetchSupported() {
        return prefetchService != null;
    }

    /**
     * Call this only if {@link #isAggressivePrefetchSupported()} is <code>true</code>.
     *
     * @param handle
     */
    public void prefetchNow(final GenericReteEntityHandle handle) {
        Runnable quickie = new Runnable() {
            public void run() {
                DefaultCacheScout.this.warmupItem(handle);
            }
        };

        prefetchService.submit(quickie);
    }

    //--------------

    protected boolean isValid(ReteQuery query) {
        return (queries.containsKey(query) && query.hasStopped() == false);
    }

    /**
     * @param query
     * @return <code>null</code> if there was nothing scheduled.
     */
    protected QueryWarmerJob trySpawnJob(ReteQuery query) {
        return tryScheduleJob(query, null, 1, TimeUnit.SECONDS);
    }

    /**
     * @param query
     * @param existingJob Can be <code>null<code/>.
     * @param delay       The delay from current time at which job will be scheduled.
     * @param unit
     * @return <code>null</code> if there was nothing scheduled.
     */
    protected QueryWarmerJob tryScheduleJob(ReteQuery query, QueryWarmerJob existingJob, long delay,
                                            TimeUnit unit) {
        if (isValid(query) == false) {
            return null;
        }

        //------------

        QueryWarmerJob job = (existingJob == null) ? new QueryWarmerJob(query) : existingJob;

        try {
            Future future = schedulerService.schedule(job, delay, unit);
            queries.put(query, future);
        }
        catch (Throwable t) {
            String s = "Error occurred while warming up cache for query: " + query.getName();
            s = s + ". Warming will stop for this query.";

            logger.log(Logger.LogLevel.WARNING, s, t);
        }

        return job;
    }

    /**
     * Only warms up {@link com.tibco.cep.query.stream.impl.rete.ReteEntityHandleType#NEW} types.
     *
     * @param handle
     * @return The number of items actually warmed.
     */
    protected int warmupItem(GenericReteEntityHandle handle) {
        if (handle.isWarm()) {
            //Pretend that we warmed a single handle.
            return 1;
        }

        //No need to warm this.
        if (handle.getType() == ReteEntityHandleType.DELETION) {
            return 0;
        }

        //---------------

        int counter = 0;

        if (handle instanceof ReteEntityHandle) {
            counter = warmUpSingleHandle((ReteEntityHandle) handle);
        }
        else {
            counter = warmUpBatchHandle((ReteEntityBatchHandle) handle);
        }

        handle.warmUp();

        return counter;
    }

    private int warmUpBatchHandle(ReteEntityBatchHandle batchHandle) {
        int counter = 0;
        Long[] ids = batchHandle.getReteIds();

        if (ids == null) {
            SharedPointer[] sharedPointers = batchHandle.getSharedPointers();

            for (SharedPointer pointer : sharedPointers) {
                //Fetch thru cache.
                Object o = pointer.getObject();
            }

            counter = sharedPointers.length;
        }
        else {
            SharedPointer[] pointers = new SharedPointer[ids.length];
            Class reteClass = batchHandle.getReteClass();
            SharedObjectSource source = sourceRepository.getSource(reteClass.getName());

            for (Long id : ids) {
                pointers[counter] = new SharedPointerImpl(id, source);
                //Fetch thru cache.
                Object o = pointers[counter].getObject();

                counter++;
            }

            //Switch it so that the query will not have to do it again.
            batchHandle.replaceIdsWithPointers(pointers);
        }

        return counter;
    }

    private int warmUpSingleHandle(ReteEntityHandle singleHandle) {
        SharedPointer pointer = singleHandle.getSharedPointer();

        if (pointer == null) {
            Class reteClass = singleHandle.getReteClass();
            SharedObjectSource source = sourceRepository.getSource(reteClass.getName());

            pointer = new SharedPointerImpl(singleHandle.getReteId(), source);

            //Fetch thru cache.
            Object o = pointer.getObject();

            singleHandle.setSharedPointer(pointer);
        }
        else {
            //Fetch thru cache.
            Object o = pointer.getObject();
        }

        return 1;
    }

    //--------------

    protected class QueryWarmerJob implements Runnable {
        protected final ReteQuery query;

        /**
         * Iterator on a concurrent-queue. If there is a huge backlog, then we process it in
         * batches. If new items get added to the end of the queue, then this iterator will "see"
         * those additions and keeps processing until its "view" is exhausted. Then, the job starts
         * with a new iterator. This way, we don't process any item more than once and all this -
         * without locking.
         */
        protected Iterator<GenericReteEntityHandle> remainingHandles;

        public QueryWarmerJob(ReteQuery query) {
            this.query = query;
        }

        public ReteQuery getQuery() {
            return query;
        }

        public void run() {
            //Not valid anymore. Quit.
            if (DefaultCacheScout.this.isValid(query) == false) {
                return;
            }

            //-------------

            try {
                //We don't want to run too far ahead and pollute the Cache. Let the query catchup.
                for (int numContinuousRuns = 0; numContinuousRuns <= MAX_CONTINUOUS_RUNS;) {
                    int warmedItems = doWork();

                    //Nothing pending.
                    if (warmedItems == 0 || remainingHandles == null) {
                        break;
                    }

                    numContinuousRuns++;

                    try {
                        Thread.sleep(SLEEP_INTERVAL_MILLIS_DURING_WARMUPS);
                    }
                    catch (InterruptedException e) {
                        //Ignore.
                    }
                }
            }
            catch (Throwable t) {
                String s = "Error occurred while warming up cache for query: " + query.getName();
                logger.log(Logger.LogLevel.WARNING, s, t);
            }

            if (remainingHandles != null) {
                remainingHandles = null;
            }

            DefaultCacheScout.this.tryScheduleJob(query, this, WAIT_INTERVAL_MILLIS_BW_WARMUPS,
                    TimeUnit.MILLISECONDS);
        }

        private int doWork() {
            Iterator<GenericReteEntityHandle> iterator = remainingHandles;
            if (iterator == null || iterator.hasNext() == false) {
                //Need a new iterator.
                ConcurrentLinkedQueue<GenericReteEntityHandle> queue = query.getQueuedInput();
                iterator = queue.iterator();

                remainingHandles = iterator;
            }

            //----------

            /*
            On Linux, sometimes, the Warmup thread trails just 1 step BEHIND the query! Without
            knowing that the query has already moved on ahead - probably while we were napping.
            In such cases, this thread still tries to warm up the old elements. To avoid this, 
            we move a few steps ahead of the query.
            */
            int skipInitial = 0;
            for (; iterator.hasNext();) {
                skipInitial++;

                if (skipInitial == SKIP_WARMUP_INITIAL_ITEMS) {
                    break;
                }
            }

            //----------

            int itemsWarmed = 0;

            for (; iterator.hasNext();) {
                GenericReteEntityHandle handle = iterator.next();

                itemsWarmed += DefaultCacheScout.this.warmupItem(handle);

                if ((itemsWarmed & 63) == 0) {
                    //Be nice.
                    Thread.yield();
                }

                if (itemsWarmed == MAX_WARMUP_ITEMS_PER_RUN) {
                    break;
                }
            }

            //----------

            //Ok, we've exhausted this iter.
            if (iterator.hasNext() == false) {
                remainingHandles = null;
            }

            return itemsWarmed;
        }
    }

    protected static class DummyFuture implements Future {
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        public boolean isCancelled() {
            return true;
        }

        public boolean isDone() {
            return true;
        }

        public Object get() throws InterruptedException, ExecutionException {
            return null;
        }

        public Object get(long timeout, TimeUnit unit)
                throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    }

    protected class JobRejectionHandler implements RejectedExecutionHandler {
        protected static final int WARN_AT_NUM = 128;

        protected final AtomicInteger rejectionCount;

        protected final Logger logger;

        public JobRejectionHandler(Logger logger) {
            this.logger = logger;
            this.rejectionCount = new AtomicInteger();
        }

        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            //Time to warn.
            if (rejectionCount.incrementAndGet() >= WARN_AT_NUM) {
                rejectionCount.set(0);

                String b = null;
                if (executor == DefaultCacheScout.this.schedulerService) {
                    int threads = DefaultCacheScout.this.schedulerService.getActiveCount();
                    long tasks = DefaultCacheScout.this.schedulerService.getTaskCount();

                    b = " Current Scheduled-Warmup service thread count: " + threads;

                    b = b + " Backlog of objects to be pre-fetched is too high: " + tasks;
                }
                else {
                    int threads = DefaultCacheScout.this.prefetchService.getActiveCount();
                    long tasks = DefaultCacheScout.this.prefetchService.getTaskCount();

                    b = " Current Aggressive-Prefetch service thread count: " + threads;

                    b = b + " Backlog of objects to be pre-fetched is too high: " + tasks;
                }

                String a = "Prefetch jobs have been rejected almost " + WARN_AT_NUM +
                        " times recently.";
                String c = " Try throttling the input rate or using more CPUs.";

                logger.log(Logger.LogLevel.ERROR, (a + b + c));
            }
        }
    }
}
