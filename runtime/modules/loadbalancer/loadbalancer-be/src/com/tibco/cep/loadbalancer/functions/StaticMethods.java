package com.tibco.cep.loadbalancer.functions;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigConfig;
import com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigsConfig;
import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.client.Client;
import com.tibco.cep.loadbalancer.client.ClientAdmin;
import com.tibco.cep.loadbalancer.impl.client.ClientMaster;
import com.tibco.cep.loadbalancer.impl.server.ServerMaster;
import com.tibco.cep.loadbalancer.server.BeServerAdmin;
import com.tibco.cep.loadbalancer.server.Server;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Ashwin Jayaprakash / Date: Aug 31, 2010 / Time: 1:10:51 PM
*/
public class StaticMethods {
    public static Client getClient() throws LifecycleException {
        RuleSession rs = RuleSessionManager.getCurrentRuleSession();
        if (rs == null) {
            throw new RuntimeException("There is no RuleSession associated with the current Thread.");
        }

        if (ClientMaster.isInited() == false) {
            throw new RuntimeException(
                    "The LoadBalancer's receiver components have not been initialized for this RuleSession.");
        }

        ResourceProvider resourceProvider = ClientMaster.getResourceProvider();
        ClientAdmin clientAdmin = resourceProvider.fetchResource(ClientAdmin.class);

        return clientAdmin.getClientFor(rs);
    }

    public static Server getServer() throws LifecycleException {
        RuleSession rs = RuleSessionManager.getCurrentRuleSession();
        if (rs == null) {
            throw new RuntimeException("There is no RuleSession associated with the current Thread.");
        }

        if (ServerMaster.isInited() == false) {
            throw new RuntimeException(
                    "The LoadBalancer's router components have not been initialized for this RuleSession.");
        }

        ResourceProvider resourceProvider = ServerMaster.getResourceProvider();
        BeServerAdmin serverAdmin = resourceProvider.fetchResource(BeServerAdmin.class);

        return serverAdmin.getServerFor(rs);
    }

    public static LoadBalancerAdhocConfigConfig extractAdhocConfig(String adhocConfigName, RuleSession rs) {
        ClusterConfig cc = (ClusterConfig) rs.getRuleServiceProvider().getProperties()
                .get(SystemProperty.CLUSTER_CONFIG.getPropertyName());
        LoadBalancerAdhocConfigsConfig adhocConfigs = cc.getLoadBalancerConfigs().getLoadBalancerAdhocConfigs();

        LoadBalancerAdhocConfigConfig selectedLbAdhocConfig = null;

        for (LoadBalancerAdhocConfigConfig adhocConfig : adhocConfigs.getLoadBalancerAdhocConfigs()) {
            if (adhocConfigName.equals(CddTools.getValueFromMixed(adhocConfig.getName()))) {
                selectedLbAdhocConfig = adhocConfig;

                break;
            }
        }

        if (selectedLbAdhocConfig == null) {
            throw new IllegalArgumentException("There is no adhoc configurtation with the name [" + adhocConfigName + "]");
        }

        return selectedLbAdhocConfig;
    }

    public static String extractChannelName(String destinationName) {
        return destinationName.substring(0, destinationName.lastIndexOf('/'));
    }
}
