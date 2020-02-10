package com.tibco.rta.client.taskdefs.impl.http;

import com.tibco.rta.RtaConnectionEx;
import com.tibco.rta.RtaConnectionFactory;
import com.tibco.rta.client.notify.impl.AsyncNotificationReactor;
import com.tibco.rta.client.taskdefs.ConnectionAwareTaskManager;
import com.tibco.rta.client.taskdefs.ConnectionTask;
import com.tibco.rta.client.tcp.TCPConnectionNotificationListener;
import com.tibco.rta.client.tcp.TCPRtaConnection;
import com.tibco.rta.client.transport.impl.http.DefaultMessageTransmissionStrategy;
import com.tibco.rta.client.util.RtaSessionUtil;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.impl.DefaultRtaSession;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/2/13
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class AsyncPipelineTask extends ConnectionTask<DefaultMessageTransmissionStrategy, AsyncNotificationReactor> {

    private TCPConnectionNotificationListener connectionNotificationListener;

    private TCPRtaConnection asyncConnection;

    public AsyncPipelineTask(DefaultRtaSession session) {
        super((DefaultMessageTransmissionStrategy) session.getTransmissionStrategy());
        this.session = session;
    }

    public void setConnectionNotificationListener(TCPConnectionNotificationListener connectionNotificationListener) {
        this.connectionNotificationListener = connectionNotificationListener;
    }


    @Override
    public Object perform() throws Exception {
        return establishAsyncPipeline();
    }

    private TCPRtaConnection establishAsyncPipeline() throws Exception {

        ConnectionAwareTaskManager taskManager = session.getTaskManager();

        //This check is required to avoid race condition
        //generated because the reconnect timer fires multiple
        //times resulting in multiple connection requests with
        //same session info.
        if (taskManager.isStarting() || taskManager.isConnectionDown()) {

            RtaConnectionEx ownerConnection = messageTransmissionStrategy.getOwnerConnection();
            String host = ownerConnection.getHost();
            int port = ownerConnection.getPort();
            String connectionUsername = properties.get("username");
            String asyncConnectionUrl = "tcp://" + host + ":" + port;

            asyncConnection =
                    (TCPRtaConnection)new RtaConnectionFactory().getConnection(asyncConnectionUrl, connectionUsername, "", session.getConfiguration());
            asyncConnection.setConnectionNotificationListener(connectionNotificationListener);
            String endpoint = RtaSessionUtil.getEndpoint(ServiceType.ASYNC, asyncConnection);
            asyncConnection.invokeService(session, endpoint, null, properties, "", messageNotifier);

        }
        return asyncConnection;
    }

    @Override
    public String getTaskName() {
        return "AsyncConnection";  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public void salvage() throws Exception {
        session.triggerRetry(this);
    }

    public void close() {
        //TODO What should we close?
    }
}
