package com.tibco.cep.loadbalancer.functions.receiver;

/*
* Author: Ashwin Jayaprakash / Date: 4/20/11 / Time: 6:14 PM
*/

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigConfig;
import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.driver.local.LocalChannel;
import com.tibco.cep.driver.local.LocalQueueDestination;
import com.tibco.cep.loadbalancer.impl.client.ClientMaster;
import com.tibco.cep.loadbalancer.impl.client.integ.PseudoLocalDestination;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

import java.util.Map;

import static com.tibco.cep.loadbalancer.functions.StaticMethods.extractAdhocConfig;
import static com.tibco.cep.loadbalancer.functions.StaticMethods.extractChannelName;

@com.tibco.be.model.functions.BEPackage(
		catalog = "CEP LoadBalancer",
        category = "LoadBalancer.Receiver",
        synopsis = "LoadBalancer receive side functions")
public abstract class ReceivingFunctions {
    @com.tibco.be.model.functions.BEFunction(
            name = "createTcpReceiverFor",
            synopsis = "Creates and returns a local receiver that receives load balanced messages from remote senders." +
                    " Messages will arrive on the local destination specified in the configuration.",
            signature = "Object createTcpReceiverFor(String adhocConfigName)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "adhocConfigName", type = "String",
                            desc = "The name of the adhoc load balancer configuration to use to receive messages. Ex: adhoc_1")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object",
                    desc = "An opaque handle to the loadbalancer receiver object."),
            version = "5.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates and returns a local receiver that receives load balanced messages from remote senders." +
                    " Messages will arrive on the local destination specified in the configuration.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public synchronized static Object createTcpReceiverFor(String adhocConfigName) {
        try {
            ClientMaster.init();
        }
        catch (LifecycleException e) {
            e.printStackTrace();
        }

        //---------------

        RuleSession rs = RuleSessionManager.getCurrentRuleSession();

        LoadBalancerAdhocConfigConfig selectedLbAdhocConfig = extractAdhocConfig(adhocConfigName, rs);

        ChannelManager cm = rs.getRuleServiceProvider().getChannelManager();

        String destinationName = CddTools.getValueFromMixed(selectedLbAdhocConfig.getChannel());
        String channelName = extractChannelName(destinationName);

        LocalChannel channel = (LocalChannel) cm.getChannel(channelName);
        Map destinations = channel.getDestinations();
        LocalQueueDestination destination = (LocalQueueDestination) destinations.get(destinationName);

        PseudoLocalDestination localDestination = new PseudoLocalDestination();
        localDestination.setProperties(selectedLbAdhocConfig.toProperties());
        localDestination.start(rs, channel, destination);

        return localDestination;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "discardReceiver",
            synopsis = "Discards the receiver.",
            signature = "discardReceiver(Object loadBalancedReceiver)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "loadBalancedReceiver", type = "Object",
                            desc = "The opaque handle to the loadbalancer receiver object created in the "
                                    + "createTcpReceiverFor(String, int, String, String) call.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Discards the receiver.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void discardReceiver(Object loadBalancedReceiver) {
        if (!(loadBalancedReceiver instanceof PseudoLocalDestination)) {
            throw new IllegalArgumentException(
                    "The parameter [" + loadBalancedReceiver + "] is not a valid load balanced receiver object.");
        }

        PseudoLocalDestination pseudoLocalDestination = (PseudoLocalDestination) loadBalancedReceiver;
        pseudoLocalDestination.stop();
    }
}
