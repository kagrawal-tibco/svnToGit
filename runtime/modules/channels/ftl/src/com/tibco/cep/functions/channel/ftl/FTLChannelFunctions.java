package com.tibco.cep.functions.channel.ftl;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.ftl.Message;

/**
 * @.category FTL
 * @.synopsis FTL Functions
 */
@com.tibco.be.model.functions.BEPackage(
        catalog = "Communication",
        category = "FTL.Message",
        synopsis = "FTL Message functions")
public class FTLChannelFunctions {
	
	
	@com.tibco.be.model.functions.BEFunction(
			name = "getFtlPropertyValue",
			signature = "Object getFtlPropertValue(String propertyName, SimpleEvent event)",
			params = {@com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String",	desc = "the name of FTL property"),
					 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "Event of Receiver")
			       },
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "object", desc = "Object containing property values."),
			version = "5.1",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Get FTL message property value. If property name is passed as null, all properties and values would be returned as a map object.",
			cautions = "none",
			fndomain={ACTION},
			example = "")
	public static Object getFtlPropertyValue(String propertyName, SimpleEvent event) {
		return FTLChannelFunctionsImpl.getFtlPropertyValue(propertyName, event);
	}
	
	/*@com.tibco.be.model.functions.BEFunction(
			name = "getArrayFromXml",
			signature = "Object getArrayFromXml(String xml, SimpleEvent event)",
			params = {@com.tibco.be.model.functions.FunctionParamDescriptor(
					name = "xml",
					type = "String",
					desc = "the xml string of event properties"),
					@com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "Event of Receiver")
			       },
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "object", desc = "the result of array object"),
			version = "5.1",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Get message from event and parse it from xml to arry object",
			cautions = "none",
			fndomain={ACTION},
			example = "")
	public static Object getArrayFromXml(String xml, SimpleEvent event) {
		return FTLChannelFunctionsImpl.getArrayFromXml(xml, event);
	}
	
	@com.tibco.be.model.functions.BEFunction(
			name = "getMessageFromXml",
			signature = "Object getMessageFromXml(String xml, SimpleEvent event)",
			params = {@com.tibco.be.model.functions.FunctionParamDescriptor(
					  name = "xml",
					  type = "String",
					  desc = "the xml string of event properties"),
					  @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "Event of Receiver")
			          },
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "object", desc = "the result of ftl message object"),
			version = "5.1",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Get message from event",
			cautions = "none",
			fndomain={ACTION},
			example = "")
	public static Object getMessageFromXml(String xml, SimpleEvent event) {
		 return FTLChannelFunctionsImpl.getMessageFromXml(xml, event);
	}
	
	@com.tibco.be.model.functions.BEFunction(
			name = "getInboxFromXml",
			signature = "Object getInboxFromXml(String xml)",
			params = {@com.tibco.be.model.functions.FunctionParamDescriptor(
					  name = "xml",
					  type = "String",
					  desc = "the xml string of event properties")
			          },
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "object", desc = "the result of ftl inbox"),
			version = "5.1",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Get inbox message from event",
			cautions = "none",
			fndomain={ACTION},
			example = "")
	public static Object getInboxFromXml(String xml) {
		 return FTLChannelFunctionsImpl.getInboxFromXml(xml);
	}*/
	
	
	
	
	
}
