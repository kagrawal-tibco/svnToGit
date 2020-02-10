package com.tibco.rta.client.notify.handler;

import com.tibco.rta.RtaCommand;
import com.tibco.rta.RtaCommandListener;
import com.tibco.rta.client.notify.AsyncNotificationEvent;
import com.tibco.rta.client.notify.AsyncNotificationEventHandler;
import com.tibco.rta.model.serialize.impl.ModelJSONDeserializer;
import com.tibco.rta.util.HeaderConstants;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 27/2/13
 * Time: 7:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandEventNotificationHandler implements AsyncNotificationEventHandler {

    private RtaCommandListener commandListener;

    public CommandEventNotificationHandler(RtaCommandListener commandListener) {
        this.commandListener = commandListener;
    }

    @Override
    public void handleNotificationEvent(AsyncNotificationEvent notificationEvent) throws Exception {
        byte[] source = (byte[]) notificationEvent.getSource();
//        RtaCommand command = IOUtils.deserialize(source);
        RtaCommand command = ModelJSONDeserializer.INSTANCE.deserializeCommand(source);
        commandListener.onCommand(command);
    }

    @Override
    public boolean canHandle(byte[] header) throws Exception {
        String headerString = new String(header, "UTF-8");
        return HeaderConstants.COMMAND_HEADER.equals(headerString);
    }
}
