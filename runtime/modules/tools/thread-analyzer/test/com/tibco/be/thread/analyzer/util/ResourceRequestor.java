/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.thread.analyzer.util;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 23, 2009 / Time: 3:59:45 PM
 */
public class ResourceRequestor implements Runnable {

    private static final Logger logger = Logger.getLogger(
            ResourceRequestor.class.getName());
    private final Object request;
    private final List<Lock> locks = new LinkedList<Lock>();
    private final String name;
    public ResourceRequestor(Object obj) {
        this.request = obj;
        name = "ObjectMonitorRequestor";
    }

    public ResourceRequestor(List<Lock> resources) {
        this.request = null;
        locks.clear();
        locks.addAll(resources);
        name = "ResourceRequestor";
    }

    @Override
    public void run() {
        Thread.currentThread().setName(name);
        if (this.request != null) {
            while (!Thread.interrupted()) {
                synchronized (this.request) {
                    logger.log(Level.INFO, "Acquired the resource.");
                }
                logger.log(Level.INFO, "Released the resource.");
            }
        } else {
            try {
                while (!Thread.interrupted()) {
                    for (Lock lock : locks) {
                        lock.lock();
                    }
                    logger.log(Level.INFO, "Acquired all the locks.");
                }
            } finally {
                for (Lock lock : locks) {
                    lock.unlock();
                }
                logger.log(Level.INFO, "Released all the locks.");
            }
        }
    }
}
