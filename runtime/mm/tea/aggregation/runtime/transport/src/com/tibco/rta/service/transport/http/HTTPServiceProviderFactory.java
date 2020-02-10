package com.tibco.rta.service.transport.http;

import com.tibco.rta.common.service.AbstractServiceProviderFactory;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.StartStopService;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 30/10/12
 * Time: 6:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class HTTPServiceProviderFactory extends AbstractServiceProviderFactory<HTTPServiceEndpoints> {

    public static final HTTPServiceProviderFactory INSTANCE = new HTTPServiceProviderFactory();

    private HTTPServiceProviderFactory() {
    }


    /**
     * @param serviceEndpoint
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public StartStopService getServiceProviderInstance(HTTPServiceEndpoints serviceEndpoint) throws Exception {
        switch (serviceEndpoint) {
            case ADMIN_SERVICE: {
                return ServiceProviderManager.getInstance().getAdminService();
            }
            case METRICS_SERVICE: {
                return ServiceProviderManager.getInstance().getMetricsService();
            }
            case RULE_SERVICE: {
                return ServiceProviderManager.getInstance().getRuleService();
            }
            case QUERY_SERVICE: {
                return ServiceProviderManager.getInstance().getQueryService();
            }
            case METRIC_INTROSPECTION_SERVICE: {
                return ServiceProviderManager.getInstance().getMetricIntrospectionService();
            }
            case RUNTIME_SERVICE: {
                return ServiceProviderManager.getInstance().getRuntimeService();
            }

            default:
                throw new UnsupportedOperationException("To be Done");
        }
    }
}
