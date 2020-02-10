/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.thread.analyzer.util;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
public class BottleneckGeneratorUtil {

    public static enum BottleneckType {
        BOUND_QUEUE,
        INSUFFICIENT_RESOURCE,
        RECURSIVE_RESOURCE_REQUEST,
        DEADLOCKED_RESOURCE_DEPENDENCY;
    }

    private static final List<ProducerThread> getProducers(int count,
            BlockingQueue<Object> queue) {
        List<ProducerThread> producers = new LinkedList<ProducerThread>();
        for (int i = 0; i < count; i++) {
            producers.add(new ProducerThread(queue));
        }
        return producers;
    }

    private static final List<ConsumerThread> getConsumer(int count,
            BlockingQueue<Object> queue) {
        List<ConsumerThread> consumers = new LinkedList<ConsumerThread>();
        for (int i = 0; i < count; i++) {
            consumers.add(new ConsumerThread(queue));
        }
        return consumers;
    }

    /**
     * This method creates a Producer-Consumer based bound Queue bottleneck
     * @param threadPool threadPool
     * @param consumers Consumer threads
     * @param producers Producer threads
     */
    private static final void createBoundQBottleneck(ExecutorService threadPool,
            List<ConsumerThread> consumers, List<ProducerThread> producers) {
        for (ProducerThread producer : producers) {
            threadPool.submit(producer);
        }
        for (ConsumerThread consumer : consumers) {
            threadPool.submit(consumer);
        }
    }

    private static final ExecutorService createResourceBottleneck(int count) {
        final ExecutorService threadpool = Executors.newFixedThreadPool(count);
        for (int i = 0; i < (count + 1); i++) {
            threadpool.submit(new SleeperThread());
        }
        return threadpool;
    }

    public static final ExecutorService generateBottleneck(
            BottleneckType type, int count) {
        ExecutorService threadpool = null;
        BlockingQueue<Object> queue = null;
        switch (type) {
            case BOUND_QUEUE:
                threadpool = Executors.newFixedThreadPool(count);
                queue = new ArrayBlockingQueue<Object>(count);
                createBoundQBottleneck(threadpool, getConsumer(count, queue),
                        getProducers(count, queue));
                break;
            case INSUFFICIENT_RESOURCE:
                threadpool = createResourceBottleneck(count);
                break;
            case RECURSIVE_RESOURCE_REQUEST:
                threadpool = Executors.newFixedThreadPool(count + 1);
                queue = new ArrayBlockingQueue<Object>(count);
                
                List<ProducerThread> producers = getProducers(count, queue);
                for(ProducerThread producer : producers) {
                    threadpool.submit(producer);
                }
                threadpool.submit(new ConsumerThread(queue, threadpool));
                break;
            case DEADLOCKED_RESOURCE_DEPENDENCY:
                List<DeadlockThread> threads = DeadlockGeneratorUtil.getDeadlockThreads(count);
                ResourceRequestor requestor = requestDeadlockedResource(threads);
                threadpool = Executors.newFixedThreadPool(threads.size() + 1);
                for (DeadlockThread thread : threads) {
                    threadpool.submit(thread);
                }
                final byte[] lock = new byte[0];
                synchronized (lock) {
                    try {
                        lock.wait(1000);
                    } catch (InterruptedException ex) {
                        //ignore and continue.
                    }
                }
                threadpool.submit(requestor);
                break;
        }
        return threadpool;
    }

    private static final ResourceRequestor requestDeadlockedResource(
            List<DeadlockThread> threads) {
        ResourceRequestor resRequestor = null;
        DeadlockThread thread = threads.get(0);
        if (thread.getOwnedObject() != null) {
            resRequestor = new ResourceRequestor(thread.getOwnedObject());
        } else {
            List<Lock> locks = thread.getOwnedLocks();
            resRequestor = new ResourceRequestor(locks);
        }
        return resRequestor;
    }
}
