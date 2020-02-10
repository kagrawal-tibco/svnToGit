/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.thread.analyzer.util;

import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
public class DeadlockThread implements Runnable {

    private final Object request;
    private final Object objToOwn;
    private final List<Lock> locksToOwn;
    private final Lock reqLock;
    private final Object waitObj;
    private final String name;

    /**
     * 
     * @param name
     * @param reqLock
     * @param request
     * @param locksToOwn
     * @param objToOwn
     * @param waitObj
     */
    public DeadlockThread(String name, Lock reqLock, Object request, List<Lock> locksToOwn,
            Object objToOwn, Object waitObj) {
        this.name = name;
        this.objToOwn = objToOwn;
        this.request = request;
        this.reqLock = reqLock;
        this.locksToOwn = locksToOwn;
        this.waitObj = waitObj;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(name);
        try {
            // own the Obj resources.
            if (objToOwn != null) {
                synchronized (objToOwn) {
                    waitOrAcquireLock();
                }
            } else if (locksToOwn != null) {
                // lock the LOCK resources
                try {
                    for (Lock lock : locksToOwn) {
                        lock.lock();
                    }
                    waitOrAcquireLock();
                } finally {
                    for (Lock lock : locksToOwn) {
                        lock.unlock();
                    }
                }
            } else {
                waitOrAcquireLock();
            }
        } catch (InterruptedException ex) {
            // ignore
            return;
        }
    }

    private void waitOrAcquireLock() throws InterruptedException {
        waitOn(request);
        acquireLock(reqLock);
        waitOn(waitObj);
        doNOOP();
    }

    private void waitOn(Object obj) {
        if (obj == null) {
            return;
        }
        while (true) {
            synchronized (obj) {
                for (int i = 0; i < 1000 * 1000; i++) {
                    // NO-OP
                }
            }
        }
    }

    private void acquireLock(Lock lock) {
        while (true) {
            try {
                lock.lock();
                for (int i = 0; i < 1000 * 1000; i++) {
                    // NO-OP
                }
            } finally {
                lock.unlock();
            }
        }
    }

    private void doNOOP() throws InterruptedException {
        synchronized (this) {
            wait();
        }
    }

    public Object getOwnedObject() {
        return objToOwn;
    }

    public List<Lock> getOwnedLocks() {
        return locksToOwn;
    }
}
