package com.tibco.rta.service.transport.jms;

import com.tibco.rta.common.service.AbstractServiceProviderFactory;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.common.service.StartStopService;
import com.tibco.rta.common.service.session.ServerSessionRegistry;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 21/3/13
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class JMSServiceProviderFactory extends AbstractServiceProviderFactory<JMSServiceEndpoint> {

    public static final JMSServiceProviderFactory INSTANCE = new JMSServiceProviderFactory();

    private JMSServiceProviderFactory() {}

    @Override
    protected StartStopService getServiceProviderInstance(JMSServiceEndpoint serviceEndpoint) throws Exception {
        ServiceType serviceType = serviceEndpoint.getServiceType();

        switch (serviceType) {
            case ADMIN : {
                return ServiceProviderManager.getInstance().getAdminService();
            }
            case METRICS : {
                return ServiceProviderManager.getInstance().getMetricsService();
            }
            case RULE : {
                return ServiceProviderManager.getInstance().getRuleService();
            }
            case QUERY : {
                return ServiceProviderManager.getInstance().getQueryService();
            }
            case METRICS_INTROSPECTION : {
                return ServiceProviderManager.getInstance().getMetricIntrospectionService();
            }
            case HEARTBEAT : {
                return ServiceProviderManager.getInstance().getHeartbeatService();
            }
            case ENGINE : {
                return ServiceProviderManager.getInstance().getRuntimeService();
            }
            case SESSION : {
                return ServerSessionRegistry.INSTANCE;
            }

            default :
                throw new UnsupportedOperationException("To be Done");
        }
    }
}
