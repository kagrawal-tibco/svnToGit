package com.tibco.cep.loadbalancer.functions.receiver.membership;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.loadbalancer.client.Client;
import com.tibco.cep.loadbalancer.client.core.ActualMember;
import com.tibco.cep.loadbalancer.client.core.ActualMemberStatus;

import static com.tibco.cep.loadbalancer.functions.StaticMethods.getClient;

/*
* Author: Ashwin Jayaprakash / Date: Aug 31, 2010 / Time: 10:35:19 AM
*/

@com.tibco.be.model.functions.BEPackage(
		catalog = "CEP LoadBalancer",
        category = "LoadBalancer.Receiver.Membership",
        synopsis = "LoadBalancer receiver side membership functions")
public abstract class MembershipFunctions {
    /**
     * {@value}
     */
    public static final long DEFAULT_FLUX_DURATION_MILLIS = 5 * 60 * 1000 /*5 mins*/;

    @com.tibco.be.model.functions.BEFunction(
        name = "isInFlux",
        synopsis = "Returns true of the loadbalancer node membership is in a state of change - i.e nodes joining and/or leaving\ncurrently or in the recent past.",
        signature = "boolean isInFlux()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true|false"),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns true if the loadbalancer node membership is in a state of change - i.e nodes joining and/or leaving currently or in the recent past.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public synchronized static boolean isInFlux() {
        long lastAt = getRecentChangeAt();
        long duration = System.currentTimeMillis() - lastAt;

        return (duration <= DEFAULT_FLUX_DURATION_MILLIS);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getRecentChangeAt",
        synopsis = "Returns the timestamp (1970 epoch milliseconds) at which the most recent membership change occurred.",
        signature = "long getRecentChangeAt()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "Timestamp in milliseconds"),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the timestamp (1970 epoch milliseconds) at which the most recent membership change occured.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public synchronized static long getRecentChangeAt() {
        Client client = null;
        try {
            client = getClient();
        }
        catch (LifecycleException e) {
            throw new RuntimeException(e);
        }

        ActualMember member = client.getMember();
        ActualMemberStatus memberStatus = member.getMemberStatus();

        return memberStatus.getRecentMembershipChangeAt();
    }
}
