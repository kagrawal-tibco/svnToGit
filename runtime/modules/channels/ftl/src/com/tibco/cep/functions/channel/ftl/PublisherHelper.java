package com.tibco.cep.functions.channel.ftl;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

@com.tibco.be.model.functions.BEPackage(
        catalog = "Communication",
        category = "FTL.Publisher",
        synopsis = "Publisher Functions")

public class PublisherHelper {
	
	@com.tibco.be.model.functions.BEFunction(
			name = "bind",
			signature = "void bind (Object publisher, String eventUri, String messageFormat)",
			params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "publisher", type = "Object", desc = "FTLPublisher Object"),
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "eventUri", type = "String", desc = "The eventUri String to be used during publisher binding."),
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "messageFormat", type = "String", desc = "The messageFormat String provided to be used during publisher binding.")
			},
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
			version = "5.2",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "This method binds the FTLPublisher using the provided eventUri and messageFormat.",
			cautions = "none",
			fndomain={ACTION},
			example = ""
		)
	public static void bind(Object publisher, String eventUri, String messageFormat){
		PublisherHelperDelegate.bind(publisher, eventUri, messageFormat);
	}
	 
	 @com.tibco.be.model.functions.BEFunction(
		        name = "publishMessage",
		        signature = "void publishMessage (Object publisher, Object event)",
		        params = {
		        @com.tibco.be.model.functions.FunctionParamDescriptor(name = "publisher", type = "Object", desc = "FTLPublisher Object"),
		        @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "Object", desc = "SimpleEvent Oject used to trigger the event.")
		        },
		        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
		        version = "5.2",
		        see = "",
		        mapper = @com.tibco.be.model.functions.BEMapper(),
		        description = "This method uses the provided publisher Object to publish the message.",
		        cautions = "none",
		        fndomain={ACTION},
		        example = ""
		    )
	 public static void publishMessage (Object publisher, Object event) {
		 	PublisherHelperDelegate.publishMessage(publisher, event);
	    }
}
