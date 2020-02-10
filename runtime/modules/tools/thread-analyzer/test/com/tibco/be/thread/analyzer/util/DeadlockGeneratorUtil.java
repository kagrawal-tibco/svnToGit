/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.thread.analyzer.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
public class DeadlockGeneratorUtil {

    private DeadlockGeneratorUtil() {
    }

    /**
     * Generates the deadlock consisting of the "count" number of threads.
     * @param count
     * @return list of threads that are deadlocked.
     */
    public static final ExecutorService generateDeadlock(int count) {
        ExecutorService threadpool = Executors.newCachedThreadPool();
        List<DeadlockThread> threads = getDeadlockThreads(count);
        for(DeadlockThread thread : threads) {
            threadpool.submit(thread);
        }
        return threadpool;
    }

    public static final List<DeadlockThread> getDeadlockThreads(int count) {
        List<DeadlockThread> threads = new LinkedList<DeadlockThread>();
        threads.addAll(generateLockDeadlock(count, 0));
        threads.addAll(generateMonitorDeadlock(count, count));
        return threads;
    }

    private static final List<DeadlockThread> generateLockDeadlock(
            int count, int start) {
        List<DeadlockThread> threads = new LinkedList<DeadlockThread>();
        List<Lock> locks = new LinkedList<Lock>();
        for(int i = 0; i < count; i++) {
            locks.add(new ReentrantLock());
        }
        for(int i = 0; i < count; i++) {
            DeadlockThread thread = new DeadlockThread(
                    "ConcLockThread-" + (start + i),
                    locks.get(((i + 1) % count)), null,
                    Arrays.asList(locks.get(i)), null, null);
            threads.add(thread);
        }
        return threads;

    }

    private static final List<DeadlockThread> generateMonitorDeadlock(int count, int start) {
        List<DeadlockThread> threads = new LinkedList<DeadlockThread>();
        List<Object> locks = new LinkedList<Object>();
        for(int i = 0; i < count; i++) {
            locks.add(new Object());
        }
        for(int i = 0; i < count; i++) {
            DeadlockThread thread = new DeadlockThread(
                    "MonitorLockThread-" + (start + i),
                    null, locks.get(((i + 1) % count)),
                    null, locks.get(i), null);
            threads.add(thread);
        }
        return threads;
    }
}
