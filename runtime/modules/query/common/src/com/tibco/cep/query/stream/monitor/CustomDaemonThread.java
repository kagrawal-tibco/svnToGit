package com.tibco.cep.query.stream.monitor;

import java.util.concurrent.atomic.AtomicBoolean;

import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.monitor.Logger.LogLevel;

/*
 * Author: Ashwin Jayaprakash Date: Dec 4, 2007 Time: 5:13:53 PM
 */

public class CustomDaemonThread extends Thread implements KnownResource {
    protected final AtomicBoolean stopFlag;

    protected final ResourceId resourceId;

    protected Runnable optionalRunnable;

    /**
     * @param group
     * @param threadId Uses {@link ResourceId#getId()} for {@link Thread#setName(String)}.
     * @param runnable Can be <code>null</code>. If not, then use {@link #hasStopBeenRequested()}
     *                 and do not override {@link #run()} and {@link #doWorkLoop()}.
     */
    public CustomDaemonThread(CustomThreadGroup group, ResourceId threadId, Runnable runnable) {
        super(group, threadId.getId());
        setDaemon(true);

        this.stopFlag = new AtomicBoolean(false);
        this.resourceId = threadId;
        this.optionalRunnable = runnable;
    }

    /**
     * Override {@link #doWorkLoop()} or {@link #run()}.
     *
     * @param group
     * @param threadId Uses {@link ResourceId#getId()} for {@link Thread#setName(String)}.
     */
    public CustomDaemonThread(CustomThreadGroup group, ResourceId threadId) {
        this(group, threadId, null);
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public boolean hasStopBeenRequested() {
        return stopFlag.get();
    }

    /**
     * Thread is interrupted and will stop on its own. This method does not wait for the Thread to
     * stop.
     */
    public void signalStop() {
        attemptStop();
    }

    /**
     * @param joinMillis Thread is interrupted and this method waits for this period to see if the
     *                   Thread stops.
     */
    public void signalStop(long joinMillis) {
        attemptStop();

        try {
            join(joinMillis);
        }
        catch (InterruptedException e) {
            Logger logger = Registry.getInstance().getComponent(Logger.class);
            logger.log(LogLevel.ERROR, e);
        }
    }

    private void attemptStop() {
        stopFlag.set(true);

        interrupt();
    }

    /**
     * <p> {@inheritDoc} </p> <p> <b>Note:</b> If this method is over-ridden, then invoke {@link
     * #runCompleted()} before exiting the Thread/{@link #run()}. </p>
     */
    public void run() {
        if (optionalRunnable != null) {
            try {
                optionalRunnable.run();
            }
            catch (Throwable t) {
                Logger logger = Registry.getInstance().getComponent(Logger.class);
                logger.log(LogLevel.ERROR, t);
            }
        }
        else {
            while (stopFlag.get() == false) {
                try {
                    doWorkLoop();
                }
                catch (InterruptedException e) {
                    // Do nothing.
                }
                catch (Throwable t) {
                    Logger logger = Registry.getInstance().getComponent(Logger.class);
                    logger.log(LogLevel.ERROR, t);
                }
            }
        }

        runCompleted();
    }

    protected void runCompleted() {
        resourceId.discard();
    }

    /**
     * Gets invoked by {@link #run()} in a loop - until {@link #signalStop()} is invoked.
     *
     * @throws Exception
     */
    protected void doWorkLoop() throws Exception {
    }
}
