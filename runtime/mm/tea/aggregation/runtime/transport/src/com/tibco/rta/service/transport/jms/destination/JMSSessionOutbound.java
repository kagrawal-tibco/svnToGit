package com.tibco.rta.service.transport.jms.destination;

import com.tibco.rta.common.ServiceResponse;
import com.tibco.rta.common.service.session.ServerSession;
import com.tibco.rta.common.service.session.SessionOutbound;
import com.tibco.rta.util.CustomByteArrayBuffer;
import com.tibco.rta.util.ServiceConstants;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 22/3/13
 * Time: 4:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class JMSSessionOutbound implements SessionOutbound {

    private ServerSession<JMSSessionOutbound> serverSession;

    private JMSOutboundDestination notificationsDestination;
    
    CustomByteArrayBuffer byteBuffer;

    public JMSSessionOutbound(ServerSession<JMSSessionOutbound> serverSession, JMSOutboundDestination notificationsDestination) {
        this.serverSession = serverSession;
        this.notificationsDestination = notificationsDestination;
    }

    @Override
    public void write(byte[] bytes) throws Exception {
    	if (byteBuffer == null) {
            byteBuffer = new CustomByteArrayBuffer(1024);
        }
        byteBuffer.append(bytes, 0, bytes.length);
//        notificationsDestination.write(bytes);
    }

    @Override
    public void finish() throws Exception {
        //Add sessionid to it
        Properties properties = new Properties();
        properties.setProperty(ServiceConstants.SESSION_ID, serverSession.getId());
        notificationsDestination.flush(properties, byteBuffer);
        byteBuffer = null;
    }

    @Override
    public ServiceResponse sendAndReceive(Properties properties) throws Exception {
        ServiceResponse serviceResponse = notificationsDestination.sendAndReceive(properties, byteBuffer);
        byteBuffer = null;
        return serviceResponse;
    }
}
