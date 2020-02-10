package com.tibco.rta.client.transport.impl.jms;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.client.SessionInitFailedException;
import com.tibco.rta.client.notify.AsyncNotificationEventHandler;
import com.tibco.rta.client.notify.impl.AsyncJMSMessageNotifier;
import com.tibco.rta.client.taskdefs.IdempotentRunnableRetryTask;
import com.tibco.rta.client.taskdefs.impl.jms.NotificationsPipelineTask;
import com.tibco.rta.client.transport.MessageReceptionStrategy;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.util.ServiceConstants;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import static com.tibco.rta.util.ServiceConstants.SESSION_ID;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 20/3/13
 * Time: 1:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class JMSMessageReceptionStrategy implements MessageReceptionStrategy {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    private DefaultRtaSession session;

    /**
     * Establish pipeline for async notifications upfront.
     */
    private NotificationsPipelineTask notificationsPipelineTask;

    /**
     * Demultiplexer.
     */
    private AsyncJMSMessageNotifier messageNotifier;


    /**
     * Just single thread for starting async connection
     */
    private ExecutorService asyncConnectionExecutorService = Executors.newSingleThreadExecutor(new ThreadFactory() {

        static final String NAME_PREFIX = "JMS-Connection-Thread";

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, NAME_PREFIX);
        }
    });

    public JMSMessageReceptionStrategy(DefaultRtaSession session) {
        this.session = session;
        messageNotifier = new AsyncJMSMessageNotifier(session.getConfiguration());
    }

    @Override
    public void init() throws Exception {
        init(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }

    @Override
    public void init(long timeout, TimeUnit units) throws Exception {
        establishNotificationsPipeline(timeout, units);
    }

    @Override
    public void shutdown() throws Exception {
        notificationsPipelineTask.shutdown();
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Shutting down asynchronous connection thread pool");
        }
        asyncConnectionExecutorService.shutdownNow();
    }

    @Override
    public void setInterestEvents(int interestEvents) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <A extends AsyncNotificationEventHandler> void registerNotificationHandler(A notificationEventHandler) {
        messageNotifier.registerNotificationHandler(notificationEventHandler);
    }

    public NotificationsPipelineTask getConnectionTask() {
        return notificationsPipelineTask;
    }

    private void establishNotificationsPipeline(long timeout, TimeUnit units) throws Exception {
        Map<ConfigProperty, PropertyAtom<?>> configuration = session.getConfiguration();
        //Only try once
        int retryCount = 1;

        long waitTime = (Long) (ConfigProperty.RETRY_WAIT_INTERVAL).getValue(configuration);

        notificationsPipelineTask = new NotificationsPipelineTask((JMSMessageTransmissionStrategy) session.getTransmissionStrategy(), timeout, units);
        //TODO
        notificationsPipelineTask.setNotificationReactor(messageNotifier);
        notificationsPipelineTask.setSession(session);

        //TODO username
        //asyncPipelineTask.addProperty("username", username);
        notificationsPipelineTask.addProperty(SESSION_ID, session.getClientId());
        notificationsPipelineTask.
                addProperty(ConfigProperty.JMS_OUTBOUND_QUEUE.getPropertyName(), (String) ConfigProperty.JMS_OUTBOUND_QUEUE.getValue(configuration));
          //TODO
//        asyncPipelineTask.setConnectionNotificationListener(connectionNotificationListener);
        String name = session.getName();
        if (name != null && !name.isEmpty()) {
            notificationsPipelineTask.addProperty(ServiceConstants.SESSION_NAME, name);
        }
        IdempotentRunnableRetryTask retryTask = new IdempotentRunnableRetryTask(session, retryCount, waitTime, notificationsPipelineTask);

        Future<?> future = asyncConnectionExecutorService.submit(retryTask);

        try {
            future.get();
            //if this call succeeds it is done.
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
            session.performExchange(notificationsPipelineTask);
        } catch (InterruptedException e) {

        } catch (ExecutionException e) {
            if (e.getCause() instanceof RuntimeException) {
                RuntimeException wrappedException = (RuntimeException) e.getCause();
                //Get its cause
                if (wrappedException.getCause() instanceof SessionInitFailedException) {
                    throw (Exception) wrappedException.getCause();
                }
            }
        }
    }
}
