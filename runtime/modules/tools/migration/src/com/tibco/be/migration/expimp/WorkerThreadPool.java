package com.tibco.be.migration.expimp;

import java.util.LinkedList;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 22, 2008
 * Time: 7:07:39 PM
 * To change this template use File | Settings | File Templates.
 */
public  class WorkerThreadPool {
        private int numberOfJobs;
        private Integer numberOfJobsCompleted= new Integer(0);

        private final Object lock = new Object();
        private  PoolWorker[] threads;
        private final LinkedList queue;
        private RuleServiceProvider rsp;
    private Logger logger;


    public WorkerThreadPool(int numberOfThreads, int numberOfJobs, RuleServiceProvider rsp) {
            this.rsp=rsp;
            this.logger = rsp.getLogger(WorkerThreadPool.class);
            this.numberOfJobs = numberOfJobs;
            queue = new LinkedList();
            threads = new PoolWorker[numberOfThreads];

            for (int i=0; i<numberOfThreads; i++) {
                threads[i] = new PoolWorker("Migration Thread " + i );
                threads[i].setDaemon(true);
                threads[i].start();
            }
        }

        public WorkerThreadPool(String poolName, int numberOfThreads, RuleServiceProvider rsp) {
            this.rsp=rsp;
            queue = new LinkedList();
            threads = new PoolWorker[numberOfThreads];

            for (int i=0; i<numberOfThreads; i++) {
                threads[i] = new PoolWorker(poolName + i );
                threads[i].setDaemon(true);
                threads[i].start();
            }
        }

        public void waitOnJobs() {
            while (true) {
                synchronized(queue) {
                    if (queue.size() > 0) {
                        try {
                            queue.wait(5000);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        return;
                    }
                }
            }
        }
        /**
         *
         */
        public void shutdown() {
            for (int i=0; i<threads.length; i++) {
                threads[i].interrupt();
            }
            queue.clear();
            threads=null;
        }

        public void execute(Runnable r) {
            synchronized(queue) {
                queue.addLast(r);
                queue.notifyAll();
            }
        }

        public void afterExecute() {
            synchronized (lock) {
                numberOfJobsCompleted = new Integer(numberOfJobsCompleted.intValue() + 1);
                this.logger.log(Level.INFO, "Number of jobs completed: %d", this.numberOfJobsCompleted);
                lock.notify();
            }
        }

        public void join() {
            while (true) {
                synchronized(lock) {
                    if (numberOfJobsCompleted.intValue() == numberOfJobs) {
                        numberOfJobsCompleted= new Integer(0);
                        return;
                    }
                    try {
                        lock.wait();
                    } catch (InterruptedException te) {
                        te.printStackTrace();
                    }
                }
            }
        }

        public void join(int waitFor) {
            while (true) {
                synchronized(lock) {
                    if (numberOfJobsCompleted.intValue() == waitFor) {
                        numberOfJobsCompleted= new Integer(0);
                        return;
                    }
                    try {
                        lock.wait();
                    } catch (InterruptedException te) {
                        te.printStackTrace();
                    }
                }
            }
        }

        public  class PoolWorker extends Thread {

            public PoolWorker(String threadName) {
                super(threadName);
            }

            public void run() {
                setContextClassLoader((ClassLoader) rsp.getTypeManager());
                Runnable r;

                while (true) {
                    synchronized(queue) {
                        while (queue.isEmpty()) {
                            try {
                                queue.wait();
                            }
                            catch (InterruptedException ignored) {
                                return;
                            }
                        }
                        r = (Runnable) queue.removeFirst();
                    }

                    // If we don't catch RuntimeException,
                    // the pool could leak threads
                    try {
                        r.run();
                    }
                    catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
