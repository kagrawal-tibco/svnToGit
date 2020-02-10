package com.tibco.rta.service.transport.http;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.service.transport.AbstractTransportService;
import com.tibco.rta.service.transport.TransportTypes;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 25/10/12
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractHTTPTransportService extends AbstractTransportService<HTTPConnectorInfo> {

    @Override
    public void init(Properties configuration) throws Exception {
        //Get transport type
        String transportType = configuration.getProperty(ConfigProperty.RTA_TRANSPORT_TYPE.getPropertyName(), "HTTP");
        TransportTypes transportTypes = TransportTypes.valueOf(transportType.toUpperCase());

        switch (transportTypes) {
            case HTTP :
            case REST :
                connectorInfo = new HTTPConnectorInfo(configuration);
                break;
        }
    }
}
