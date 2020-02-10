package com.tibco.cep.functions.channel.as;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.cep.functions.channel.as.ASHelper.MetaspaceInfo;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleSessionManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/*
* Author: Suresh Subramani / Date: 9/14/12 / Time: 10:52 AM
* Invocation service helper
*/
@com.tibco.be.model.functions.BEPackage(
		catalog = "ActiveSpaces",
        category = "Metaspace.InvocationService",
        synopsis = "Invocation Service functions")

public class InvocationServiceHelper  {

    static ConcurrentMap<String, InvocationServiceDefinition> serviceDefinitionMap = new ConcurrentHashMap<String, InvocationServiceDefinition>();

    @com.tibco.be.model.functions.BEFunction(
            name = "createInvocationServiceDefinition",
            synopsis = "create and publish a Invocation Service that Clients can use to call",
            signature = "void createInvocationServiceDefinition(String serviceName, String metaspace, String ruleFunctionName, String requestEventType, boolean executeRules)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "serviceName", type = "String", desc = "The name of service "),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "metaspace", type = "String", desc = "Metaspace name "),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleFunctionName", type = "String", desc = "The rulefunction that is bound with the service. The rulefunction gets invoked."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "requestEventType", type = "String", desc = "The input event type that the rulefunction takes as a parameter"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "executeRules", type = "boolean", desc = "Execute rules after invoking the rulefunction")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "create and publish a Invocation Service that Clients can use to call",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void createInvocationServiceDefinition(String serviceName, String metaspace, String ruleFunctionName, String requestEventType, boolean executeRules) {
        try {
            ASHelper.MetaspaceInfo msInfo = (MetaspaceInfo) ASHelper.getMetaspaceInfo(metaspace);
            InvocationServiceDefinition serviceDefinition   = new InvocationServiceDefinition(msInfo.getMetaSpace(), serviceName, ruleFunctionName, requestEventType, executeRules, RuleSessionManager.getCurrentRuleSession());
            serviceDefinitionMap.putIfAbsent(String.format("%s.%s", metaspace, serviceName), serviceDefinition);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getCurrentInvocationServiceRequest",
            synopsis = "Get the current Invocation Service Request Handle",
            signature = "Object getCurrentInvocationServiceRequest()",
            params = {
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The current service instance"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Get the current Invocation Service Request Handle",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object getCurrentInvocationServiceRequest() {
        return InvocationService.getCurrentService();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setInvocationResponse",
            synopsis = "set the invocation Response for the request handle specified",
            signature = "void setInvocationResponse(Object svcInstance, SimpleEvent responseEvent)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "svcRequest", type = "Object", desc = "The service request handle retrieved from getCurrentInvocationRequest"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "responseEvent", type = "Event", desc = "The response event")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "set the invocation Response for the request handle specified",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void setInvocationResponse(Object svcRequest, SimpleEvent responseEvent) {
        InvocationService invSvc = (InvocationService) svcRequest;
        invSvc.setResponse(responseEvent);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "cancelRequest",
            synopsis = "Cancel the service request with handle specified",
            signature = "void cancelRequest(Object svcInstance, String reason)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "svcRequest", type = "Object", desc = "The service request handle retrieved from getCurrentInvocationRequest"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "responseEvent", type = "Event", desc = "The response event")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Cancel the service request with handle specified",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void cancelRequest(Object svcRequest, String reason) {
        InvocationService invSvc = (InvocationService) svcRequest;
        invSvc.cancel(reason);
    }

    public static Object getServiceDefinition(String serviceName) {
        return serviceDefinitionMap.get(serviceName);
    }
}
