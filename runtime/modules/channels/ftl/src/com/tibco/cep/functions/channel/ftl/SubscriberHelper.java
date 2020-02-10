package com.tibco.cep.functions.channel.ftl;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.be.model.functions.Enabled;

@com.tibco.be.model.functions.BEPackage(
        catalog = "Communication",
        category = "FTL.Subscriber",
        synopsis = "Subscriber Functions")

public class SubscriberHelper {

    @com.tibco.be.model.functions.BEFunction(
        name = "setRuleFunction",
        signature = "void setRuleFunction (Object subscriber, String ruleFunction)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriber", type = "Object", desc = "FTLSubscriber object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleFunction", type = "String", desc = "Rule function to be triggered by the subscriber object referenced.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "FTLSubscriber object"),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method sets the rule function to be triggered by the subscriber object referenced.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static void setRuleFunction (Object subscriber, String ruleFunction) {
        SubscriberHelperDelegate.setRuleFunction(subscriber, ruleFunction);
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "setNumThreads",
        signature = "void setNumThreads (Object subscriber, int numberOfThreads)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriber", type = "Object", desc = "FTLSubscriber object"),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "numberOfThreads", type = "int", desc = "Number of threads to be spawn")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the number of subscriber threads to be started.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static void setNumThreads(Object subscriber, int numberOfThreads){
    	SubscriberHelperDelegate.setNumThreads(subscriber, numberOfThreads);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "setExecuteRules",
        signature = "void setExecuteRules (Object subscriber, boolean executeRules)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriber", type = "Object", desc = "FTLSubscriber Object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "executeRules", type = "boolean", desc = "The boolean value to be set.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method is needed when the method setRuleFunction is being used. Set the value for executeRules to true in order to trigger the rule function.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static void setExecuteRules (Object subscriber, boolean executeRules) {
        SubscriberHelperDelegate.setExecuteRules(subscriber, executeRules);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "startListening",
        signature = "void startListening (Object subscriber)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriber", type = "Object", desc = "FTLSubscriber Object")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = " "),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method makes the FTLSubscriber Object passed to start Listening to the FTL messages being published.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static void startListening (Object subscriber) {
    	SubscriberHelperDelegate.startListening(subscriber);
    }
    
    @com.tibco.be.model.functions.BEFunction(
    	name = "bind",
    	signature = "void bind (Object subscriber, String eventUri, String messageFormat, Object contentMap)",
    	params = {
    			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriber", type = "Object", desc = "FTLSubscriber Object"),
    			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "eventUri", type = "String", desc = "The eventUri String to be used during subscriber binding."),
    			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "messageFormat", type = "String", desc = "The messageFormat String provided to be used during subscriber binding."),
    			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "contentMap", type = "Object", desc = "If filtering is needed then the contentMap value can be set to something like {\"type\":2,\"toBaseStation\":\"ABC123\",...}, else null value can be provided.")
    	},
    	freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = "This method binds the FTLSubscriber using the provided eventUri,messageFormat and the contentMap if provided."),
		version = "5.2",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "This method binds the FTLSubscriber",
		cautions = "none",
		fndomain={ACTION},
		example = ""
    )
    
    public static void bind(Object subscriber, String eventUri, String messageFormat, Object contentMap){
    	SubscriberHelperDelegate.bind(subscriber, eventUri, messageFormat, contentMap);	
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "close",
        signature = "void close ()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method closes down the FTLSubscriber.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    
    public static void close (Object subscriber) {
        SubscriberHelperDelegate.close(subscriber);
    }
}
