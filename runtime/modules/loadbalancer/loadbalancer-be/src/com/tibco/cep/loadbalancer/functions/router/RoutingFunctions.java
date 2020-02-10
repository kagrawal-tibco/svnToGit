package com.tibco.cep.loadbalancer.functions.router;

/*
* Author: Ashwin Jayaprakash / Date: 4/20/11 / Time: 6:14 PM
*/

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigConfig;
import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.impl.message.SimpleDistributableMessage;
import com.tibco.cep.loadbalancer.impl.server.ServerConstants;
import com.tibco.cep.loadbalancer.impl.server.ServerMaster;
import com.tibco.cep.loadbalancer.impl.server.integ.DummyChannel;
import com.tibco.cep.loadbalancer.impl.server.integ.DummyDestination;
import com.tibco.cep.loadbalancer.impl.server.integ.RouterSideNoOpDestination;
import com.tibco.cep.loadbalancer.message.MessageHandle;
import com.tibco.cep.loadbalancer.server.Server;
import com.tibco.cep.loadbalancer.util.Utils;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

import java.io.IOException;
import java.util.HashMap;

import static com.tibco.cep.loadbalancer.functions.StaticMethods.*;
import static com.tibco.cep.util.Helper.$eval;

@com.tibco.be.model.functions.BEPackage(
		catalog = "CEP LoadBalancer",
        category = "LoadBalancer.Router",
        synopsis = "LoadBalancer router side functions")
public abstract class RoutingFunctions {
    @com.tibco.be.model.functions.BEFunction(
            name = "createLoadBalancerTo",
            synopsis = "Creates and returns a load balancer that can be used to send messages to remote, load balanced destinations.",
            signature = "Object createLoadBalancerTo(String adhocConfigName)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "adhocConfigName", type = "String",
                            desc = "The name of the adhoc load balancer configuration to use to send messages to. Ex: adhoc_1")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object",
                    desc = "An opaque handle to the loadbalancer object that can be used to send messages."),
            version = "5.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates and returns a load balancer that can be used to send messages to remote, load balanced destinations.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public synchronized static Object createLoadBalancerTo(String adhocConfigName) {
        try {
            ServerMaster.init();
        }
        catch (LifecycleException e) {
            e.printStackTrace();
        }

        Server server = null;
        try {
            server = getServer();
        }
        catch (LifecycleException e) {
            throw new RuntimeException(e);
        }

        //---------------

        RuleSession rs = RuleSessionManager.getCurrentRuleSession();

        LoadBalancerAdhocConfigConfig selectedLbAdhocConfig = extractAdhocConfig(adhocConfigName, rs);

        ChannelManager cm = rs.getRuleServiceProvider().getChannelManager();

        String dummyPrefix =
                "/Internal/DynamicRouterNoOp/" + System.currentTimeMillis() + "/" + System.nanoTime();

        DummyChannel dummyRouterChannel = new DummyChannel(cm, dummyPrefix + "/Channel");
        DummyDestination dummyRouterDestination =
                new DummyDestination(dummyRouterChannel, dummyPrefix + "/Destination");

        String destinationName = CddTools.getValueFromMixed(selectedLbAdhocConfig.getChannel());
        String channelName = extractChannelName(destinationName);

        DummyChannel dummyReceiverChannel = new DummyChannel(cm, channelName);
        DummyDestination dummyReceiverDestination = new DummyDestination(dummyReceiverChannel, destinationName);

        HashMap<Object, Object> properties = new HashMap<Object, Object>();

        //Dummy routing key that is not used.
        ResourceProvider resourceProvider = ServerMaster.getResourceProvider();
        String dummyRoutingKeyName = dummyPrefix + "/RoutingKey";
        properties.put(ServerConstants.PROPERTY_DESTINATION_ROUTING_KEY, dummyRoutingKeyName);

        String sourceOverride = $eval(resourceProvider, ServerConstants.NAME_SOURCE_OVERRIDE,
                "destination", dummyRouterDestination).toString();
        String source = $eval(resourceProvider, ServerConstants.NAME_SOURCE,
                "destination", dummyReceiverDestination).toString();
        properties.put(sourceOverride, source);

        RouterSideNoOpDestination noOpDestination = new RouterSideNoOpDestination();
        noOpDestination.setProperties(properties);
        noOpDestination.start(rs, dummyRouterChannel, dummyRouterDestination);

        return noOpDestination;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "send",
            synopsis = "Sends an event to a remote receiver. The routing decision is made using the routing key.",
            signature = "void send(Object loadBalancer, SimpleEvent event, String routingKey)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "loadBalancer", type = "Object",
                            desc = " The opaque handle to the loadbalancer that was created in the "
                                    + "createLoadBalancerTo(String, String) call."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "SimpleEvent",
                            desc = "The event to send the message to."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "routingKey", type = "String",
                            desc = "The key that will be used to choose a receiver. " +
                                    "If the topology has not changed, then messages with the same key " +
                                    "sent from any sender will be received by the same receiver.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sends an event to a remote receiver. The routing decision is made using the routing key.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void send(Object loadBalancer, SimpleEvent event, String routingKey) {
        if (!(loadBalancer instanceof RouterSideNoOpDestination)) {
            throw new IllegalArgumentException(
                    "The parameter [" + loadBalancer + "] is not a valid load balanced server object.");
        }

        SimpleDistributableMessage distributableMessage = null;
        try {
            byte[] bytes = Utils.$serializeEvent(event);
            distributableMessage = new SimpleDistributableMessage(bytes, Long.toString(event.getId()), routingKey, 4);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        RouterSideNoOpDestination noOpDestination = (RouterSideNoOpDestination) loadBalancer;

        try {
            MessageHandle messageHandle = noOpDestination.sendDirect(distributableMessage);

            Exception ex = messageHandle.getPostSendException();
            if (ex != null) {
                throw new RuntimeException(ex);
            }
        }
        catch (RuntimeException re) {
            throw re;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "discardLoadBalancer",
            synopsis = "Discards the load balancer.",
            signature = "void discardLoadBalancer(Object loadBalancer)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "loadBalancer", type = "Object",
                            desc = " The opaque handle to the loadbalancer that was created in the "
                                    + "createLoadBalancerTo(String, String) call.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Discards the load balancer.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void discardLoadBalancer(Object loadBalancer) {
        if (!(loadBalancer instanceof RouterSideNoOpDestination)) {
            throw new IllegalArgumentException(
                    "The parameter [" + loadBalancer + "] is not a valid load balancer object.");
        }

        RouterSideNoOpDestination noOpDestination = (RouterSideNoOpDestination) loadBalancer;
        noOpDestination.stop();
    }
}
