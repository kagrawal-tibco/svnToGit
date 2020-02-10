package com.tibco.rta.client.notify.impl;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.client.notify.AsyncNotificationEventHandler;
import com.tibco.rta.client.notify.MessageReceptionNotifier;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.util.CustomByteArrayBuffer;
import com.tibco.rta.util.ParserCursor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 23/3/13
 * Time: 12:11 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseMessageReceptionNotifier implements MessageReceptionNotifier {

    protected List<AsyncNotificationEventHandler> notificationEventHandlers = new CopyOnWriteArrayList<AsyncNotificationEventHandler>();

    protected ThreadPoolExecutor notificationDispatcherService;

    /**
     * Transient parser cursor
     */
    protected ParserCursor parserCursor;

    /**
     * Transient data chunk
     */
    protected CustomByteArrayBuffer headerChunk;

    protected static final byte HEADER_DELIM = '|';

    public void registerNotificationHandler(AsyncNotificationEventHandler notificationEventHandler) {
        if (!notificationEventHandlers.contains(notificationEventHandler)) {
            notificationEventHandlers.add(notificationEventHandler);
        }
    }

    protected BaseMessageReceptionNotifier(Map<ConfigProperty, PropertyAtom<?>> configuration) {

        int maxPoolSize = (Integer) ConfigProperty.ASYNC_RESULT_DISPATCHER_MAX_POOL_SIZE.getValue(configuration);
        long keepAliveTime = (Long) ConfigProperty.ASYNC_RESULT_DISPATCHER_POOL_TIMEOUT.getValue(configuration);

        notificationDispatcherService = new ThreadPoolExecutor(0, maxPoolSize,
                                 keepAliveTime, TimeUnit.MILLISECONDS,
                                 new SynchronousQueue<Runnable>(),
                                 new AsyncNotificationThreadFactory());
        notificationDispatcherService.allowCoreThreadTimeOut(true);
    }

    @Override
    public void close() throws Exception {
        notificationDispatcherService.shutdownNow();
    }

    private static class AsyncNotificationThreadFactory implements ThreadFactory {

        private AtomicInteger threadCounter = new AtomicInteger(0);

        static final String NAME_PREFIX = "Async-Dispatcher-Pool";

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, NAME_PREFIX + "-" + threadCounter.getAndIncrement());
        }
    }
}
