package com.tibco.rta.impl.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 28/2/13
 * Time: 12:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class NotifyingQueue<T> extends LinkedBlockingQueue<T> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6706030494628992795L;

	protected final ReentrantLock mainLock = new ReentrantLock();

    protected List<QueueEventNotificationListener> notificationListeners = new ArrayList<QueueEventNotificationListener>();

    @Override
    public boolean add(T t) {
        QueueEvent queueEvent = new QueueEvent(t);
        for (QueueEventNotificationListener notificationListener : notificationListeners) {
            try {
                notificationListener.notifyQueueEvent(queueEvent);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return false;//super.add(t);
    }


    public void registerListener(QueueEventNotificationListener notificationListener) {
        ReentrantLock lock = this.mainLock;
        lock.lock();

        try {
            notificationListeners.add(notificationListener);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
