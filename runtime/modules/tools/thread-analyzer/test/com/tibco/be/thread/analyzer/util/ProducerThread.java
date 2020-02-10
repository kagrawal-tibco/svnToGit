/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.thread.analyzer.util;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 23, 2009 / Time: 3:57:15 PM
 */
public class ProducerThread implements Runnable {

    private static final Logger logger = Logger.getLogger(
            ProducerThread.class.getName());
    
    private final BlockingQueue<Object> queue;

    public ProducerThread(BlockingQueue<Object> obj) {
        this.queue = obj;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("ProducerThread");
        while (!Thread.interrupted()) {
            try {
                queue.put(new Object());
            } catch (InterruptedException ex) {
                logger.log(Level.FINE,
                        "Bottlneck - Producer thread interrupted.", ex);
                break;
            }
        }
    }
}
