package com.tibco.rta.client.transport.impl.http;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.NotificationListenerKey;
import com.tibco.rta.client.notify.AsyncNotificationEventHandler;
import com.tibco.rta.client.notify.impl.AsyncNotificationReactor;
import com.tibco.rta.client.taskdefs.IdempotentRunnableRetryTask;
import com.tibco.rta.client.taskdefs.impl.http.AsyncPipelineTask;
import com.tibco.rta.client.tcp.DefaultTCPConnectionNotificationListener;
import com.tibco.rta.client.transport.MessageReceptionStrategy;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.util.ServiceConstants;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.tibco.rta.util.ServiceConstants.SESSION_ID;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 19/3/13
 * Time: 10:11 AM
 * HTTP/TCP based implementation of reception strategy.
 */
public class DefaultMessageReceptionStrategy implements MessageReceptionStrategy {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    private DefaultRtaSession session;

    /**
     * Handle message notifications and route to appropriate handlers. Basically demultiplexer
     */
    private AsyncNotificationReactor messageNotifier;

    /**
     * Special task for async connection establishment.
     */
    private AsyncPipelineTask asyncPipelineTask;

    /**
     * Internal connection listener for async notifications
     */
    private DefaultTCPConnectionNotificationListener connectionNotificationListener;


    /**
     * Just single thread for starting async connection
     */
    private ExecutorService asyncConnectionExecutorService = Executors.newSingleThreadExecutor();


    public DefaultMessageReceptionStrategy(DefaultRtaSession session) {
        this.session = session;
        messageNotifier = new AsyncNotificationReactor(session.getConfiguration());
        connectionNotificationListener = new DefaultTCPConnectionNotificationListener(session);
    }

    @Override
    public void init() throws Exception {
        establishSyncPipeline();
    }

    @Override
    public void init(long timeout, TimeUnit units) throws Exception {
        init();
    }

    @Override
    public void shutdown() throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Attempting to close session");
        }

        asyncConnectionExecutorService.shutdown();
        try {
            asyncPipelineTask.close();
            asyncConnectionExecutorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error shutting down session", e);
        }
    }

    @Override
    public void setInterestEvents(int interestEvents) {
        NotificationListenerKey notificationListenerKey = new NotificationListenerKey();
        notificationListenerKey.setInterestEvents(interestEvents);
        connectionNotificationListener.setNotificationListenerKey(notificationListenerKey);
    }

    @Override
    public <A extends AsyncNotificationEventHandler> void registerNotificationHandler(A notificationEventHandler) {
        messageNotifier.registerNotificationHandler(notificationEventHandler);
    }

    private void establishSyncPipeline() {
        Map<ConfigProperty, PropertyAtom<?>> configuration = session.getConfiguration();
        //Only try once
        int retryCount = 1;

        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        asyncPipelineTask = new AsyncPipelineTask(session);
        asyncPipelineTask.setNotificationReactor(messageNotifier);

        //TODO username
        //asyncPipelineTask.addProperty("username", username);
        asyncPipelineTask.addProperty(SESSION_ID, session.getClientId());

        asyncPipelineTask.setConnectionNotificationListener(connectionNotificationListener);
        String name = session.getName();
        if (name != null && !name.isEmpty()) {
            asyncPipelineTask.addProperty(ServiceConstants.SESSION_NAME, name);
        }
        IdempotentRunnableRetryTask retryTask = new IdempotentRunnableRetryTask(session, retryCount, waitTime, asyncPipelineTask);

        asyncConnectionExecutorService.submit(retryTask);

        /**
         * This is a unique way of exchanging data between threads.
         * TCP connection establishment is essentially an async task
         * because the connection has to keep running till shutdown,
         * but for this sync flavour to work we need to be able to
         * return from the connection which basically terminates the thread.
         * To avoid this we exchange objects with the connection thread.
         * If conn succeeds an event is exchanged else an exception.
         *
         */
        session.performExchange(asyncPipelineTask);
    }
}
