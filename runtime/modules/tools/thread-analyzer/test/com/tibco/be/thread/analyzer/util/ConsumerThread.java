/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.thread.analyzer.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 23, 2009 / Time: 3:58:31 PM
 */
public class ConsumerThread implements Runnable {

    private static final Logger logger = Logger.getLogger(
            ConsumerThread.class.getName());
    private final BlockingQueue<Object> queue;
    private final ExecutorService threadpool;
    private final String name;
    public ConsumerThread(BlockingQueue<Object> queue) {
        this.queue = queue;
        threadpool = null;
        name = "ConsumerThread";
    }

    public ConsumerThread(BlockingQueue<Object> queue, ExecutorService threadpool) {
        this.queue = queue;
        this.threadpool = threadpool;
        name = "RecursiveConsumerThread";
    }

    @Override
    public void run() {
        Thread.currentThread().setName(name);
        if (threadpool != null) {
            int i = 0;
            while(i++ < 20 && !Thread.interrupted()) {
                threadpool.submit(new ProducerThread(queue));
            }
            try {
                this.wait();
            } catch(InterruptedException ex) {
                // ignore
                return;
            }
        } else {
            try {
                queue.take();
            } catch (InterruptedException ex) {
                logger.log(Level.WARNING, "Unable to take from queue.", ex);
            }
            while (!Thread.interrupted()) {
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException ex) {
                        logger.log(Level.FINE, "Exiting consumer thread.", ex);
                        break;
                    }
                }
            }
        }
    }
}
