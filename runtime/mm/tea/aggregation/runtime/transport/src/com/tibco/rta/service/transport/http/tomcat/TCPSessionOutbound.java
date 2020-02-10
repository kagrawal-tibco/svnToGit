package com.tibco.rta.service.transport.http.tomcat;

import com.tibco.rta.common.ServiceResponse;
import com.tibco.rta.common.service.session.SessionOutbound;

import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 22/3/13
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class TCPSessionOutbound implements SessionOutbound {

    private OutputStream underlyingStream;

    public TCPSessionOutbound(OutputStream underlyingStream) {
        this.underlyingStream = underlyingStream;
    }

    @Override
    public void write(byte[] bytes) throws Exception {
        underlyingStream.write(bytes);
    }

    @Override
    public void finish() throws Exception {
        underlyingStream.flush();
    }

    @Override
    public ServiceResponse sendAndReceive(Properties properties) throws Exception {
        return null;
    }
}
