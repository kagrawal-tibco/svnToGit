package com.tibco.rta.client.taskdefs;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.client.tcp.TCPConnectionEvent;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.impl.util.NotifyingQueue;
import com.tibco.rta.impl.util.QueueEventNotificationListener;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.util.ConnectionAwareThreadPoolExecutor;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 2/3/13
 * Time: 6:02 PM
 * <p/>
 * A task manager for {@link IdempotentRetryTask} for every {@link com.tibco.rta.RtaSession}
 */
public class ConnectionAwareTaskManager {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    /**
     * Manager is in starting state.
     */
    private static final int MANAGER_TASK_STARTING = 1 << 1;

    /**
     * Manager is in accept mode.
     */
    private static final int MANAGER_TASK_ACCEPT = 1 << 2;

    /**
     * Manager up but not accepting tasks.
     */
    private static final int MANAGER_TASK_REJECT = 1 << 3;

    /**
     * Manager closed because the owning session closed.
     */
    private static final int MANAGER_CLOSE = 1 << 4;

    /**
     * Manager closed because the owning session closed.
     */
    private static final int MANAGER_CONNECTION_DOWN = 1 << 5;

    /**
     * Manager attempting connection.
     */
    private static final int MANAGER_CONNECTION_TRY = 1 << 6;

    /**
     * Current manager state. Volatile to provide read consistency.
     */
    private volatile int managerState;

    /**
     * Main task processing pool.
     */
    private ConnectionAwareThreadPoolExecutor taskProcessorService;

    /**
     * A completion service to query for result completions
     */
    private ExecutorCompletionService<Object> taskProcessorCompletionService;

    /**
     * Internal queue on which to place completed tasks
     */
    private NotifyingQueue<Future<Object>> taskProcessorCompletionQueue;

    /**
     * Common handler for notifying all rejections.
     */
    private TaskRejectionHandler rejectedExecutionHandler;

    /**
     * Unbounded queue for tasks which could not be successfully completed.
     */
    private BlockingQueue<IdempotentRetryTask> failedTaskQueue = new LinkedBlockingQueue<IdempotentRetryTask>();

    /**
     * Main lock for state transitions.
     */
    private final ReentrantLock stateLock = new ReentrantLock();

    /**
     * General state condition.
     */
    private final Condition stateCondition = stateLock.newCondition();


    public ConnectionAwareTaskManager(DefaultRtaSession session) {

        Map<ConfigProperty, PropertyAtom<?>> configuration = session.getConfiguration();

        /**
         * Minimum pool size
         */
        int threadPoolSize = (Integer) ConfigProperty.TASK_MANAGER_THREADPOOL_SIZE.getValue(configuration);

        int threadPoolKeepAliveTimeout = (Integer) ConfigProperty.TASK_MANAGER_THREAD_KEEPALIVE_TIME.getValue(configuration);

		taskProcessorService = new ConnectionAwareThreadPoolExecutor(threadPoolSize, 2 * threadPoolSize,
				threadPoolKeepAliveTimeout, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
				new TaskDefThreadFactory());

        taskProcessorService.allowCoreThreadTimeOut(true);

        taskProcessorCompletionQueue = new NotifyingQueue<Future<Object>>();

        taskProcessorCompletionService = new ExecutorCompletionService<Object>(taskProcessorService, taskProcessorCompletionQueue);

        //Initialize state for the manager
        this.managerState = MANAGER_TASK_STARTING;
    }

    /**
     * Manager is up indicated by whether it is accepting or rejecting.
     *
     */
    private boolean isManagerUp() {
        return isAccepting() || isRejecting();
    }

    /**
     * Manager is up indicated by whether it is accepting.
     *
     */
    private boolean isAccepting() {
        return (managerState & MANAGER_TASK_ACCEPT) != 0;
    }

    private boolean isRejecting() {
        return (managerState & MANAGER_TASK_REJECT) != 0;
    }

    /**
     * Whether manager is still starting by establishing connection.
     *
     */
    public boolean isStarting() {
        return (managerState & MANAGER_TASK_STARTING) != 0;
    }

    /**
     * Manager is down indicated by whether conn is down.
     *
     */
    public boolean isConnectionDown() {
        return (managerState & MANAGER_CONNECTION_DOWN) != 0 || (managerState & MANAGER_CONNECTION_TRY) != 0;
    }

    /**
     * Register listener for getting notified about async task completions.
     *
     */
    public <Q extends QueueEventNotificationListener> void registerTaskCompletionListener(Q listener) {
        taskProcessorCompletionQueue.registerListener(listener);
    }

    /**
     * Register handler for task rejection notifications.
     *
     */
    public void registerTaskRejectionHandler(TaskRejectionHandler handler) {
        this.rejectedExecutionHandler = handler;
        taskProcessorService.setRejectedExecutionHandler(handler);
    }

    /**
     * Synchronous task to be executed on same thread as that supplied by caller.
     *
     */
    public <I extends IdempotentRetryTask> Object submitTask(I retryTask) throws Throwable {
        AbstractClientTask wrappedTask = retryTask.getWrappedTask();
        ReentrantLock lock = stateLock;
        lock.lock();

        try {
            while (isStarting()) {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Manager in init state. Need to wait for it to start accepting");
                }
                //Wait because it is still in starting.
                stateCondition.await();
            }
            if (isManagerUp()) {
                if (wrappedTask.isSync()) {
                    return retryTask.perform();
                } else {
                    throw new IllegalArgumentException(String.format("Cannot invoke this method on asynchronous task [%s]", wrappedTask.getTaskName()));
                }
            } else {
                //Handle connection down reject any task submissions.
                if (isConnectionDown()) {
                    if (LOGGER.isEnabledFor(Level.WARN)) {
                        LOGGER.log(Level.WARN, "Connection closed. No more tasks accepted");
                    }
                    rejectedExecutionHandler.rejectedExecution(retryTask, null);
                }
                throw new RejectedExecutionException("Connection closed. No more tasks accepted");
            }
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * Add the failed task to failed queue.
     *
     */
    public <I extends IdempotentRetryTask> void requeue(I retryTask) {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Failed to execute task. Adding [%s] to failed task queue", retryTask.getTaskName());
        }
        AbstractClientTask wrappedTask = retryTask.getWrappedTask();
        if (!wrappedTask.isSync()) {
            failedTaskQueue.add(retryTask);
        }
    }

    /**
     * Asynchronous task (e.g fact publish) to be executed on different thread pool.
     *
     */
    public <I extends IdempotentRunnableRetryTask> Future<Object> submitAsyncTask(I retryTask) throws Exception {
        AbstractClientTask wrappedTask = retryTask.getWrappedTask();

        if (!wrappedTask.isSync()) {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Accepting task counter [%s]", taskProcessorService.getTaskCount());
            }
            return taskProcessorCompletionService.submit(retryTask);
        } else {
            throw new IllegalArgumentException("Cannot invoke this method on synchronous task");
        }
    }


    /**
     * Set manager status depending on server connection status
     *
     */
    public void setManagerStatus(int serverStatus) {
        ReentrantLock lock = stateLock;
        lock.lock();

        try {
            if ((serverStatus & TCPConnectionEvent.CONNECTION_DOWN_EVENT) != 0) {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Connection closed. Setting manager state to down");
                }
                managerState = MANAGER_CONNECTION_DOWN;
                taskProcessorService.pause();
            } else if ((serverStatus & TCPConnectionEvent.CONNECTION_TRY_EVENT) != 0) {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Manager attempting to establish connection.");
                }
                managerState = MANAGER_CONNECTION_TRY;
            } else if ((serverStatus & TCPConnectionEvent.CONNECTION_ESTABLISH_EVENT) != 0) {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Connection established. Setting manager state to accept");
                }
                managerState = MANAGER_TASK_ACCEPT;
                //Signal waiters
                stateCondition.signalAll();
                taskProcessorService.resume();
            }
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        //Drain queue
        try {
            while (!failedTaskQueue.isEmpty()) {
                IdempotentRetryTask retryTask = failedTaskQueue.take();
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Fetching task [%s] from failed task queue for re-execution", retryTask.getTaskName());
                }
                IdempotentRunnableRetryTask runnableRetryTask =
                        new IdempotentRunnableRetryTask(retryTask.getSession(), retryTask.getRetryCount(), retryTask.getWaitTime(), retryTask.getWrappedTask());
                submitAsyncTask(runnableRetryTask);
            }
        } catch (Exception ie) {
            LOGGER.log(Level.ERROR, "", ie);
        }
    }

    /**
     * Close this task manager and perform cleanup.
     */
    public void close() {
        this.managerState = MANAGER_CLOSE;
        failedTaskQueue.clear();
        //This will be called through the rtasession.close, we can simply discard any residual tasks.
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Shutting down connection manager thread pool");
        }
        taskProcessorService.shutdownNow();
    }


    private static class TaskDefThreadFactory implements ThreadFactory {

        private final ThreadGroup group = new ThreadGroup("Fact Publishers");

        private AtomicInteger threadCounter = new AtomicInteger(1);

        static final String NAME_PREFIX = "Async-Task-Executor";

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(group, runnable, NAME_PREFIX + "-" + threadCounter.getAndIncrement());
        }
    }
}
