package com.tibco.rta.service.transport.tcp;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.service.transport.AbstractRtaConnectorInfo;

import java.net.InetAddress;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 14/1/13
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class TCPConnectorInfo extends AbstractRtaConnectorInfo {

    private InetAddress address;

    private int port;

    public TCPConnectorInfo(Properties properties) throws Exception {
        address = InetAddress.getByName(properties.getProperty(ConfigProperty.RTA_TRANSPORT_TCP_HOSTNAME.getPropertyName(), InetAddress.getLocalHost().getHostAddress()));
        port = Integer.parseInt(properties.getProperty(ConfigProperty.RTA_TRANSPORT_TCP_PORT.getPropertyName(), "4448"));
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
