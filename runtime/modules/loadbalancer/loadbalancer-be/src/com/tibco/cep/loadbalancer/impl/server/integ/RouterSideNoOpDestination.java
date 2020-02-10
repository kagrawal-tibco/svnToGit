package com.tibco.cep.loadbalancer.impl.server.integ;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.impl.common.resource.DefaultId;
import com.tibco.cep.loadbalancer.impl.jmx.RouterDestinationMbeanImpl;
import com.tibco.cep.loadbalancer.impl.server.ServerConstants;
import com.tibco.cep.loadbalancer.impl.server.ServerMaster;
import com.tibco.cep.loadbalancer.impl.server.core.DefaultDistributionTable;
import com.tibco.cep.loadbalancer.impl.server.core.DefaultLoadBalancer;
import com.tibco.cep.loadbalancer.message.DistributableMessage;
import com.tibco.cep.loadbalancer.message.MessageHandle;
import com.tibco.cep.loadbalancer.server.BeServerAdmin;
import com.tibco.cep.loadbalancer.server.Server;
import com.tibco.cep.loadbalancer.server.ServerAdmin;
import com.tibco.cep.loadbalancer.server.core.DistributionTable;
import com.tibco.cep.loadbalancer.server.core.Kernel;
import com.tibco.cep.loadbalancer.server.core.LoadBalancer;
import com.tibco.cep.loadbalancer.server.core.LoadBalancerException;
import com.tibco.cep.runtime.channel.impl.AbstractChannel;
import com.tibco.cep.runtime.channel.impl.AbstractDestination;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.util.annotation.LogCategory;
import com.tibco.cep.util.annotation.Optional;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.tibco.cep.util.Helper.*;

/*
* Author: Ashwin Jayaprakash / Date: 4/20/11 / Time: 4:23 PM
*/

@LogCategory("loadbalancer.be.server.destination.noop")
public class RouterSideNoOpDestination<C extends AbstractChannel, D extends AbstractDestination<C>> {
    protected RuleSession session;

    protected C channel;

    protected D destination;

    protected Map<Object, Object> properties;

    protected LoadBalancer loadBalancer;

    protected String routingKeys[];

    protected Logger logger;

    protected RouterDestinationMbeanImpl routerDestinationMbean;

    @Optional
    protected ExecutorService ackProcessingExecutorService;

    public RouterSideNoOpDestination() {
        //todo There has to be a better place for this.
        try {
            ServerMaster.init();
        }
        catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }

    public RuleSession getSession() {
        return session;
    }

    public C getChannel() {
        return channel;
    }

    public D getDestination() {
        return destination;
    }

    public LoadBalancer getLoadBalancer() {
        return loadBalancer;
    }

    public String[] getRoutingKeys() {
        return routingKeys;
    }

    public void setProperties(Map<Object, Object> properties) {
        this.properties = properties;
    }

    public void start(RuleSession session, C channel, D destination) {
        hookFirstStepInStart(session, channel, destination);

        //--------------

        logger.log(Level.FINE,
                String.format("Starting [%s : %s : %s : %s]", session.getName(),
                        channel.getName(), destination.getName(), getClass().getSimpleName()));

        //--------------

        ResourceProvider resourceProvider = ServerMaster.getResourceProvider();

        ServerAdmin serverAdmin = resourceProvider.fetchResource(BeServerAdmin.class);

        OverrideConfig oc = (OverrideConfig) properties.get(CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__KEY);
        if (oc != null) {
            String routingKeyName = CddTools.getValueFromMixed(oc);
            if (routingKeyName == null || routingKeyName.trim().isEmpty()) {
                throw new RuntimeException(
                        "Loadbalancer does not have a routing key property defined in configuration");
            }

            if (routingKeyName.contains(",")) {
                routingKeys = routingKeyName.split(",");
                Arrays.sort(routingKeys);
            } else {
                routingKeys = new String[1];
                routingKeys[0] = routingKeyName;
            }

        } else {
            routingKeys = new String[1];
            routingKeys[0] = (String) properties.get(ServerConstants.PROPERTY_DESTINATION_ROUTING_KEY);
        }

        //--------------

        try {
            Server server = serverAdmin.getServerFor(session);
            Kernel kernel = server.getKernel();

            String sourceName = readSourceName(session, channel, destination, resourceProvider);
            Id sourceId = $id(sourceName);

            String lbName = $eval(resourceProvider, ServerConstants.NAME_LOADBALANCER,
                    "rulesession", session, "destination", destination).toString();
            Id loadBalancerId = $id(lbName);

            Id tableId = new DefaultId(loadBalancerId, DistributionTable.class.getSimpleName());

            DistributionTable distributionTable = new DefaultDistributionTable(tableId);
            distributionTable.setResourceProvider(resourceProvider);
            loadBalancer = new DefaultLoadBalancer(loadBalancerId, sourceId, distributionTable);
            loadBalancer.setResourceProvider(resourceProvider);
            kernel.addLoadBalancer(loadBalancer);

            //--------------

            hookJustAfterAddingLB(server, kernel, loadBalancer);

            server.getMembershipChangeProvider().refresh();

            kernel.start();

            //--------------

            routerDestinationMbean = new RouterDestinationMbeanImpl();
            routerDestinationMbean.setParentName(session.getName());
            routerDestinationMbean.setName(
                    String.format("%s_%s_%s", channel.getName(), destination.getName(), getClass().getSimpleName()));

            try {
                routerDestinationMbean.register();
            }
            catch (Exception e) {
                logger.log(Level.WARNING, "Error occurred while registering mbean", e);
            }

            //--------------

            logger.log(Level.FINE,
                    String.format("Started [%s : %s : %s : %s] with routing key [%s]", session.getName(),
                            channel.getName(), destination.getName(), getClass().getSimpleName(), Arrays.toString(routingKeys)));
        }
        catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }

    private String readSourceName(RuleSession session, C channel, D destination, ResourceProvider resourceProvider) {
        String propSourceNameOverride = $eval(resourceProvider, ServerConstants.NAME_SOURCE_OVERRIDE,
                "destination", destination).toString();

        String sourceName = (String) properties.get(propSourceNameOverride);

        if (sourceName != null) {
            logger.log(Level.FINE,
                    String.format("[%s : %s : %s : %s] has been explicitly set up to connect to the source [%s]",
                            session.getName(), channel.getName(), destination.getName(), getClass().getSimpleName(),
                            sourceName));
        }
        else {
            sourceName = $eval(resourceProvider, ServerConstants.NAME_SOURCE,
                    "destination", destination).toString();

            logger.log(Level.FINE,
                    String.format("[%s : %s : %s : %s] has been set up to connect to the source [%s]",
                            session.getName(), channel.getName(), destination.getName(), getClass().getSimpleName(),
                            sourceName));
        }

        return sourceName;
    }

    protected void hookFirstStepInStart(RuleSession session, C channel, D destination) {
        this.session = session;
        this.channel = channel;
        this.destination = destination;

        ResourceProvider resourceProvider = ServerMaster.getResourceProvider();
        this.logger = $logger(resourceProvider, getClass());
    }

    protected void hookJustAfterAddingLB(Server server, Kernel kernel, LoadBalancer loadBalancer) {
    }

    public MessageHandle sendDirect(DistributableMessage distributableMessage) throws LoadBalancerException {
        MessageHandle messageHandle = loadBalancer.send(distributableMessage);

        Exception ex = messageHandle.getPostSendException();
        if (ex == null) {
            routerDestinationMbean.incrementTotalMessagesSent();
        }

        return messageHandle;
    }

    public void stop() {
        logger.log(Level.FINE, String.format("Stopping [%s : %s : %s : %s]", session.getName(), channel.getName(),
                destination.getName(), getClass().getSimpleName()));

        try {
            ResourceProvider resourceProvider = ServerMaster.getResourceProvider();
            BeServerAdmin serverAdmin = resourceProvider.fetchResource(BeServerAdmin.class);

            Server server = serverAdmin.getServerFor(session);
            Kernel kernel = server.getKernel();

            hookJustBeforeRemovingLB(server, kernel, loadBalancer);

            kernel.removeLoadBalancer(loadBalancer.getSourceId());
            loadBalancer = null;
            
            kernel.stop();

            hookJustAfterRemovingLB(server, kernel);

            logger.log(Level.FINE, String.format("Stopped [%s : %s : %s : %s]", session.getName(), channel.getName(),
                    destination.getName(), getClass().getSimpleName()));
        }
        catch (Exception e) {
            logger.log(Level.SEVERE,
                    String.format("Error occurred while stopping [%s : %s : %s : %s]", session.getName(),
                            channel.getName(), destination.getName(), getClass().getSimpleName()), e);
        }
    }

    protected void hookJustBeforeRemovingLB(Server server, Kernel kernel, LoadBalancer loadBalancer) {
    }

    protected void hookJustAfterRemovingLB(Server server, Kernel kernel) {
    }
}
