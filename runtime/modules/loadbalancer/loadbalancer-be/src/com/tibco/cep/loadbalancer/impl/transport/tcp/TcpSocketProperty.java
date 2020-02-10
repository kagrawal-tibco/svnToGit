package com.tibco.cep.loadbalancer.impl.transport.tcp;

import java.net.Socket;
import java.net.SocketException;

import com.tibco.cep.util.annotation.Optional;

/*
* Author: Ashwin Jayaprakash / Date: Jul 6, 2010 / Time: 3:02:30 PM
*/

public enum TcpSocketProperty {
    hostname(),
    port(),

    so_rcvbuf(null, false),
    so_sndbuf(null, false),
    so_timeout(null, false),
    /**
     * Default is true.
     */
    tcp_nodelay(Boolean.TRUE.toString(), false);

    private final String defaultValue;

    private final boolean mandatory;

    /**
     * Mandatory. Default value is null.
     */
    TcpSocketProperty() {
        this(null, true);
    }

    TcpSocketProperty(String defaultValue, boolean mandatory) {
        this.defaultValue = defaultValue;
        this.mandatory = mandatory;
    }

    /**
     * @return <code>null</code> indicates what ever is defined by JVM, if at all.
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    /**
     * @param socket
     * @param propertyValue If <code>null</code>, then the default is used.
     * @return Applied value. Or <code>null</code> if nothing was applied.
     * @throws IllegalArgumentException
     * @throws SocketException
     */
    public String applyProperty(Socket socket, @Optional String propertyValue)
            throws IllegalArgumentException, SocketException {
        String valueToApply = propertyValue;

        if (valueToApply == null || valueToApply.length() == 0) {
            valueToApply = defaultValue;
        }

        if (valueToApply == null) {
            if (mandatory) {
                throw new IllegalArgumentException("Value for property [" + name() + "] has to be provided.");
            }

            return null;
        }

        //---------------

        switch (this) {
            case so_rcvbuf: {
                int rcvSize = Integer.parseInt(valueToApply);
                socket.setReceiveBufferSize(rcvSize);
            }
            break;

            case so_sndbuf: {
                int sndSize = Integer.parseInt(valueToApply);
                socket.setSendBufferSize(sndSize);
            }
            break;

            case so_timeout: {
                int soTimeout = Integer.parseInt(valueToApply);
                socket.setSoTimeout(soTimeout);
            }
            break;

            case tcp_nodelay: {
                boolean noDelay = Boolean.parseBoolean(valueToApply);
                socket.setTcpNoDelay(noDelay);
            }
            break;

            case hostname:
            case port:
            default:
                break;
        }

        return valueToApply;
    }
}
