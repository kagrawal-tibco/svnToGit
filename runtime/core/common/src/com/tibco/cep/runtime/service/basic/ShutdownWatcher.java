package com.tibco.cep.runtime.service.basic;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
* Author: Ashwin Jayaprakash Date: Oct 13, 2008 Time: 1:20:02 PM
*/

/**
 * Simple watchdog like service that prevents any unauthorized threads from invoking {@link System#exit(int)}. The only way to
 * exit is through {@link #exitSystem(int)}.
 */
public class ShutdownWatcher {
    protected final SystemExitCallerThread exitCallerThread;


    public ShutdownWatcher() {
        this.exitCallerThread = new SystemExitCallerThread();
    }

    public void init() {
        exitCallerThread.start();
    }

    public void addPreExitJob(final Runnable runnable) {
        Runtime.getRuntime().addShutdownHook(
                new Thread("shutdown-hook-job") {
                    @Override
                    public void run() {
                        runnable.run();
                    }
                });
    }

    public void exitSystem(int status) {
        exitCallerThread.requestSystemExit(status);
    }

    /**
     * @throws UnsupportedOperationException
     */
    public void stop() {
        throw new UnsupportedOperationException("Service cannot be stopped once it has started.");
    }

    //----------

    static class SystemExitCallerThread extends Thread {
        protected final ReentrantLock exitWaiterLock;

        protected final Condition exitWaitCondition;

        protected volatile boolean exitRequired;

        protected volatile int exitStatus;

        public SystemExitCallerThread() {
            super(SystemExitCallerThread.class.getName());
            //Has to be non-daemon. See Runtime.addShutdownHook(...).
            setDaemon(false);

            this.exitWaiterLock = new ReentrantLock();
            this.exitWaitCondition = this.exitWaiterLock.newCondition();
            this.exitRequired = false;
        }

        @Override
        public void run() {
            exitWaiterLock.lock();
            try {
                while (!exitRequired) {
                    try {
                        exitWaitCondition.await();
                    }
                    catch (InterruptedException e) {
                        //Ignore.
                    }
                }
            }
            finally {
                exitWaiterLock.unlock();
            }

            String msg = "System.exit(..) will be invoked now with status: " + exitStatus;
            System.out.println(msg);

            System.exit(exitStatus);
        }

        protected void requestSystemExit(int exitStatus) {
            //Already requested.
            if (exitRequired) {
                return;
            }

            //-------------

            Thread t = Thread.currentThread();
            String msg = "System exit requested by thread [" + t.getName() + "] with status: " +
                    exitStatus;
            System.out.println(msg);

            exitWaiterLock.lock();
            try {
                exitRequired = true;
                this.exitStatus = exitStatus;

                exitWaitCondition.signalAll();
            }
            finally {
                exitWaiterLock.unlock();
            }
        }
    }
}
