package com.tibco.cep.functions.channel.as;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.as.space.listener.ListenerDef;
import com.tibco.be.model.functions.BEPackage;
import com.tibco.be.model.functions.Enabled;

@BEPackage(
        catalog = "ActiveSpaces",
        category = "Metaspace.Subscriber",
        synopsis = "AS Subscriber Functions",
        enabled = @Enabled(value=true))
public class ASSubscriberHelper {

    @com.tibco.be.model.functions.BEFunction(
        name = "create",
        synopsis = "Creates an AS subscriber",
        signature = "Object create (Object spaceOrName, Object listenerDef, Object listenerType, String tupleFilter)",
        params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "spaceOrName", type = "Object", desc = "Space object returned from Space.getSpace() "),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listenerDef", type = "Object", desc = " Space listener definition object for the subscriber"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listenertype", type = "Object", desc = " Space listener type, valid values TAKE,PUT,EXPIRE,EVICT. Default value used is PUT if null specified"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tupleFilter", type = "String", desc = " a tuple filter expression. See Activespaces documentation for details.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "subscriber object"),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates an AS subscriber",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object create (Object spaceOrName, Object listenerDef ,String listenerType,String tupleFilter ) {
        return  new ASSpaceSubscriber(spaceOrName, listenerDef, listenerType, tupleFilter);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setRuleFunction",
        synopsis = "Sets the subscriber rule function",
        signature = "void setRuleFunction (Object subscriber, String ruleFunction)",
        params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriber", type = "Object", desc = "Activespaces subscriber object"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleFunction", type = "String",   desc = "call back/pre-processor rulefunction, signature fn(Object o) where o is an Event or Tuple if event is not specified")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the subscriber rule function",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void setRuleFunction (Object subscriber, String ruleFunction) {
        ASSpaceSubscriber asSubscriber = (ASSpaceSubscriber) subscriber;
        asSubscriber.setRuleFunctionName(ruleFunction);
        return;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setRequestEventType",
        synopsis = "Sets request Event's type",
        signature = "void setRequestEventType(Object subscriber, String requestEventType)",
        params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriber", type = "Object", desc = "Activespaces subscriber object"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "requestEventType", type = "String",   desc = "event URI for event type which is created as a result of a AS listener notification")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets request Event's type",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void setRequestEventType (Object subscriber, String requestEventType) {
        ASSpaceSubscriber asSubscriber = (ASSpaceSubscriber) subscriber;
        asSubscriber.setRequestEventType(requestEventType);
        return;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setExecuteRules",
        synopsis = "Enable (or disables) pre-processor rulefunction",
        signature = "void setExecuteRules(Object subscriber, boolean executeRules)",
        params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriber", type = "Object", desc = "Activespaces subscriber object"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "executeRules", type = "boolean", desc = "if true the event is sent through the pre-processor rulefunction else the attached rulefunction is called directly")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Enable (or disables) pre-processor rulefunction",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void setExecuteRules (Object subscriber, boolean executeRules) {
        ASSpaceSubscriber asSubscriber = (ASSpaceSubscriber) subscriber;
        asSubscriber.setExecuteRules(executeRules);
        return;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "startListening",
        synopsis = "Starts listening to subscriber object",
        signature = "void startListening(Object subscriber)",
        params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriber", type = "Object", desc = "Activespaces subscriber object")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Starts listening to subscriber object",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void startListening (Object subscriber) {
        ASSpaceSubscriber asSubscriber = (ASSpaceSubscriber) subscriber;
        try {
            asSubscriber.startListening();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "close",
        synopsis = "Closes a subscriber",
        signature = "void close ()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Closes a subscriber",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void close (Object subscriber) {
        ASSpaceSubscriber asSubscriber = (ASSpaceSubscriber) subscriber;
        try {
            asSubscriber.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

     /**
     * Creates a Listener Definition
     * @param timeScope
     * @param distributionScope
     * @return
     */
    @com.tibco.be.model.functions.BEFunction(
        name = "createListenerDef",
        synopsis = "Creates a listener definition",
        signature = "Object createListenerDef (String timeScope, String distributionScope)",
        params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "timeScope", type = "String", desc = "The timed scope of the space entries, valid values are ALL, NEW, NEW_EVENTS, SNAPSHOT<br/> see AS documentation for details. "),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "distributionScope", type = "String", desc = "The distribution scope of the space entries, valid values are ALL, SEEDED <br/> see AS documentation for details. ")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "ListenerDef object"),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a listener definition",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )

    public static Object createListenerDef(String timeScope,String distributionScope ) {
        ListenerDef.TimeScope ts = ListenerDef.TimeScope.ALL;
        ListenerDef.DistributionScope ds = ListenerDef.DistributionScope.ALL;
        if (timeScope != null && !timeScope.isEmpty()) {
            ts = ListenerDef.TimeScope.valueOf(timeScope);
        }
        if (distributionScope != null && !distributionScope.isEmpty()) {
            ds = ListenerDef.DistributionScope.valueOf(distributionScope);
        }
        return ListenerDef.create(ts, ds);
    }

}
