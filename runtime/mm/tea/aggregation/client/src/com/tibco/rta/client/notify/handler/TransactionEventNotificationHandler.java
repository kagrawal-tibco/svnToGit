package com.tibco.rta.client.notify.handler;

import com.tibco.rta.RtaNotification;
import com.tibco.rta.RtaNotificationListener;
import com.tibco.rta.client.notify.AsyncNotificationEvent;
import com.tibco.rta.client.notify.AsyncNotificationEventHandler;
import com.tibco.rta.common.service.TransactionEvent;
import com.tibco.rta.impl.RtaNotificationImpl;
import com.tibco.rta.util.CustomByteArrayBuffer;
import com.tibco.rta.util.HeaderConstants;
import com.tibco.rta.util.IOUtils;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 1/2/13
 * Time: 4:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionEventNotificationHandler implements AsyncNotificationEventHandler {

    private RtaNotificationListener notificationListener;

    public TransactionEventNotificationHandler(RtaNotificationListener notificationListener) {
        this.notificationListener = notificationListener;
    }

    @Override
    public void handleNotificationEvent(AsyncNotificationEvent notificationEvent) throws Exception {
        //TODO deserialize the event and send it to notification
        byte[] source = (byte[]) notificationEvent.getSource();
        TransactionEvent transactionEvent = deserialize(source);
        if (transactionEvent != null) {
            RtaNotificationImpl rtaNotification = new RtaNotificationImpl();
            RtaNotification.Status status =
                    RtaNotification.Status.valueOf(transactionEvent.getStatus().name());
            rtaNotification.setStatus(status);
            rtaNotification.setProperty("transactionId", transactionEvent.getTransactionId());
            notificationListener.onNotification(rtaNotification);
        }
    }

    @Override
    public boolean canHandle(byte[] header) throws Exception {
        String headerString = new String(header, "UTF-8");
        return HeaderConstants.TRANSACTION_HEADER.equals(headerString);
    }

    private TransactionEvent deserialize(byte[] buffer) throws Exception {
        CustomByteArrayBuffer byteArrayBuffer = new CustomByteArrayBuffer(buffer.length);
        byteArrayBuffer.append(buffer, 0, buffer.length);

        if (byteArrayBuffer.indexOf((byte)'\r') == 0 && byteArrayBuffer.indexOf((byte)'\n') == 1) {
            byteArrayBuffer = new CustomByteArrayBuffer(buffer.length - 2);
            byteArrayBuffer.append(buffer, 2, buffer.length - 2);
        }
        return IOUtils.deserialize(byteArrayBuffer.toByteArray());
   	}
}
