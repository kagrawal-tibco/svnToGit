package com.tibco.cep.driver.http.server;

import java.lang.reflect.Constructor;
import java.util.Properties;

import com.tibco.cep.driver.http.HttpChannel;
import com.tibco.cep.driver.http.security.HttpChannelRealm;
import com.tibco.cep.driver.http.server.impl.TomcatServer;
import com.tibco.cep.driver.http.server.impl.TomcatServerUtils;
import com.tibco.cep.driver.http.server.impl.httpcomponents.HttpCoreAsyncServer;
import com.tibco.cep.driver.http.server.impl.httpcomponents.HttpCoreSyncServer;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 19, 2009
 * Time: 10:06:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpChannelServerFactory {

    public static final HttpChannelServerFactory INSTANCE =
            new HttpChannelServerFactory();

    private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(HttpChannelServerFactory.class);

    private HttpChannelServerFactory() {}

    @SuppressWarnings("unchecked")
    public <H extends HttpChannelServer> H create(String serverType,
                                                  HttpChannel channel,
                                                  ConnectorInfo connectorInfo) throws Exception {
        if (serverType == null) {
            serverType = "BUILT-IN";
        }

        Properties props = TomcatServerUtils.readPropsFromExternalProps("be.tomcat.props");
        String serverTypeProp = props.getProperty("protocol");
        if (serverTypeProp != null && serverTypeProp.equalsIgnoreCase("built-in")) {
            serverType = "BUILT-IN";
        }


        HttpChannelServerTypes serverTypes = HttpChannelServerTypes.getByLiteral(serverType);
        switch (serverTypes) {
            case HC_SYNC: {
                Constructor<HttpCoreSyncServer> constructor =
                        HttpCoreSyncServer.class.getConstructor(HttpChannel.class,
                                ConnectorInfo.class);
                return (H)constructor.newInstance(channel, connectorInfo);
            }
            case HC_ASYNC: {
                Constructor<HttpCoreAsyncServer> constructor =
                        HttpCoreAsyncServer.class.getConstructor(HttpChannel.class,
                                ConnectorInfo.class);
                return (H)constructor.newInstance(channel, connectorInfo);
            }
            case TOMCAT: {
                Constructor<TomcatServer> constructor =
                        TomcatServer.class.getConstructor(HttpChannel.class,
                                ConnectorInfo.class,
                                HttpChannelRealm.class);
                return (H)constructor.newInstance(channel, connectorInfo, null);
            }
            default: {
                throw new UnsupportedOperationException("Invalid channel server type");
            }
        }
    }
}
