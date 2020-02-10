package com.tibco.be.functions.event.ext;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: 7/19/11
 * Time: 6:06 PM
 * To change this template use File | Settings | File Templates.
 */

import com.tibco.be.functions.event.EventHelper;
import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.functions.Enabled;

import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.impl.ChannelManagerImpl;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.exception.impl.BEExceptionImpl;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

import java.util.Map;


@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Event.Ext",
        synopsis = "Functions to operate on Events")

public class EventHelperExt extends EventHelper
{
    @com.tibco.be.model.functions.BEFunction(
        name = "sendEventExt",
        synopsis = "Sends the SimpleEvent <code>evt</code> to the configured channel and destination.",
        signature = "SimpleEvent sendEventExt(SimpleEvent evt, boolean forceImmediate) throws Exception",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "evt", type = "SimpleEvent", desc = "An SimpleEvent to send."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "forceImmediate", type = "boolean", desc = "If true, the event will be immediately passed off to the default destination to be sent.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "SimpleEvent", desc = "The same value as the argument evt, or null if the event was not sent."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sends the SimpleEvent <code>evt</code> to the configured default channel and destination for this SimpleEvent.\nThis method will fail if the SimpleEvent has not been configured with any default channel and destination information.",
        cautions = "This method will fail if the SimpleEvent has not been configured with any default channel and destination information.  This method may throw an Exception.",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static SimpleEvent sendEventExt(SimpleEvent evt, boolean forceImmediate) {
        try {
        	if(evt == null) return null;
            return sendEvent_Impl(evt, forceImmediate, RuleSessionManager.getCurrentRuleSession());
        } catch (Exception ex) {
            throw BEExceptionImpl.wrapThrowableAsRE(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
    	name="sendEventImmediate",
    	synopsis = "Sends the SimpleEvent <code>evt</code> to the configured channel and destination immediately,",
    	signature = "Object sendEventImmediate(SimpleEvent evt) throws Exception",
    	params = {
    		 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "evt", type = "SimpleEvent", desc = "An SimpleEvent to send.")
    	},
    	freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name ="", type = "Object", desc = "The String JMSMessageID of the sent message in case of JMS destinations, null otherwise"),
    	version="4.0.2-HF2",
    	see="",
    	mapper = @com.tibco.be.model.functions.BEMapper(),
    	description = "Immediately sends the SimpleEvent <code>evt</code> to the configured default channel and destination for this SimpleEvent. This method will fail if the SimpleEvent has not been configured with any default channel and destination information.",
    	cautions = "This method will fail if the SimpleEvent has not been configured with any default channel and destination information.",
    	fndomain = {ACTION, BUI},
    	example = ""
    )
    public static Object sendEventImmediate(SimpleEvent evt) {
        try {
        	if(evt == null) return null;
            return sendEventImmediate(evt, RuleSessionManager.getCurrentRuleSession());
        } catch (Exception ex) {
            throw BEExceptionImpl.wrapThrowableAsRE(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "replyEventExt",
        synopsis = "Replies with a <code>reply</code> SimpleEvent to a <code>request</code> SimpleEvent.\nIf a reply destination on the <code>request</code> SimpleEvent is specified, it will be\nused to send the <code>reply</code> SimpleEvent, else no action is taken.",
        signature = "boolean replyEventExt(SimpleEvent request, SimpleEvent reply, boolean forceImmediate)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "request", type = "SimpleEvent", desc = "The original request SimpleEvent."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "reply", type = "SimpleEvent", desc = "The Reply SimpleEvent."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "forceImmediate", type = "boolean", desc = "If true, the reply event will be immediately passed off to the reply destination to be sent.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the SimpleEvent is sent; false otherwise."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sends a SimpleEvent to the Destination specified within the <code>request</code>\nSimpleEvent such as an inbox in case of RV or a JMSReplyTo property in case of JMS.\nThe Destination in turn specifies the Channel where the SimpleEvent will be sent\nin the JMS Message. If the original SimpleEvent does not have a reply destination or a subject set,\nthis method will return false.",
        cautions = "",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean replyEventExt(SimpleEvent request, SimpleEvent reply, boolean forceImmediate) {
        return replyEvent_Impl(request, reply, forceImmediate);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "routeToExt",
        synopsis = "Routes a SimpleEvent to a Destination specified in <code>destinationPath</code>\nwith a list of Properties <code>properties</code>.  These properties will override the\ndefault properties specified in the Destination.",
        signature = "SimpleEvent routeToExt(SimpleEvent event, String destinationPath, String properties, boolean forceImmediate) throws Exception",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "The simpleEvent to be sent."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "destinationPath", type = "String", desc = "destination in it."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "properties", type = "String",
            desc = "A list of property name-value pairs in the format " +
                    "<code>[name1=value1;name2=value2;...;]</code>\n" +
                    "When present in the name or value each character " +
                    "[<code> =, or \\</code>] should be escaped with two antislash [<code> \\ </code>] " +
                    "characters. " +
                    "E.G. 2=1+1 should be written as <code>2\\\\=1+1</code> " +
                    "and <code>2\\3\\4</code> should be written as <code>2\\\\\\\\3\\\\\\\\4.</code>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "forceImmediate", type = "boolean", desc = "If true, the event will be immediately passed off to the specified destination to be sent.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "SimpleEvent", desc = "The same value as the argument evt, or null if the event was not sent."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sends a SimpleEvent to a Destination with custom destination properties.\nThe Destination in turn specifies the Channel where the Event will be sent.",
        cautions = "This method may throw an Exception.\nCustom channels may not support overriding of destination properties.",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static SimpleEvent routeToExt(SimpleEvent event, String destinationPath, String properties, boolean forceImmediate) {
        try {
            return routeTo_Impl(event, destinationPath, properties, forceImmediate);
        } catch (Exception ex) {
            throw BEExceptionImpl.wrapThrowableAsRE(ex);
        }
    }//routeTo

    @com.tibco.be.model.functions.BEFunction(
            name = "routeToWithDynamicProperties",
            synopsis = "Routes a SimpleEvent to a Destination specified in <code>destinationPath</code>\nwith a list of <code>properties</code>.  These properties will override the\ndefault properties specified in the destination and in the event.",
            enabled = @Enabled(property="TIBCO.BE.function.catalog.event.dynamic.properties", value=false),
            signature = "SimpleEvent routeToWithDynamicProperties(SimpleEvent event, String destinationPath, Object properties, boolean forceImmediate) throws Exception",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "The simpleEvent to be sent."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "destinationPath", type = "String", desc = "destination in it."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "properties", type = "Object", desc = "The properties to override."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "forceImmediate", type = "boolean", desc = "If true, the event will be immediately passed off to the specified destination to be sent.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "SimpleEvent", desc = "The same value as the argument evt, or null if the event was not sent."),
            version = "3.0.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sends a SimpleEvent to a Destination with custom properties.\nThe Destination in turn specifies the Channel where the Event will be sent.",
            cautions = "This method may throw an Exception.\nCustom channels may not support overriding of destination properties.",
            domain = "action",
            example = ""
    )
    public static SimpleEvent routeToWithDynamicProperties(
            SimpleEvent event,
            String destinationPath,
            Object properties,
            boolean forceImmediate) {
        try {
            return routeTo_Impl(event, destinationPath, (Map<String, Object>) properties, forceImmediate);
        } catch (Exception ex) {
            throw BEExceptionImpl.wrapThrowableAsRE(ex);
        }
    }//routeToWithDynamicProperties

    @com.tibco.be.model.functions.BEFunction(
            name = "routeToWithDynamicPropertiesImmediate",
            synopsis = "Immediately routes a SimpleEvent to a Destination specified in <code>destinationPath</code> with a list of <code>properties</code>.  These properties will override the default properties specified in the destination andin the event.",
            enabled = @Enabled(property="TIBCO.BE.function.catalog.event.dynamic.properties", value=false),
            signature = "Object routeToWithDynamicPropertiesImmediate(SimpleEvent event, String destinationPath, Object properties)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name="event", type="SimpleEvent", desc = "The simpleEvent to be sent."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name="destinationPath", type="String", desc = "The path to the Destination that will be used to send the Event."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name="properties", type="Object", desc="The properties to override.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="Object", desc="The String JMSMessageID of the sent message in case of JMS destinations, null otherwise."),
            version = "4.0.2-HF1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sends a SimpleEvent to a Destination with custom properties. The Destination in turn specifies the Channel where the Event will be sent.",
            cautions = "Custom channels may not support overriding of destination properties.",
            domain ="action",
            example = ""
    )

    public static Object routeToWithDynamicPropertiesImmediate(
            SimpleEvent event,
            String destinationPath,
            Object properties) {
        try {
            if (event == null)
                return null;
            return ((ChannelManagerImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getChannelManager())
                    .sendEventImmediate(event, destinationPath, (Map) properties);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw BEExceptionImpl.wrapThrowableAsRE(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
    		name = "replyEventImmediate",
    		synopsis = "Replies with a <code>reply</code> SimpleEvent to a <code>request</code> SimpleEvent. If a reply destination on the <code>request</code> SimpleEvent is specified, it will be used to send the <code>reply</code> SimpleEvent, else no action is taken.",
    		signature = "Object replyEventImmediate(SimpleEvent request, SimpleEvent reply)",
    		params = {
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name= "request", type = "SimpleEvent",  desc = "The original request SimpleEvent."),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "reply", type = "SimpleEvent", desc = "The Reply SimpleEvent.")
    		},
    		freturn =  @com.tibco.be.model.functions.FunctionParamDescriptor( name = "", type = "Object", desc = "The String JMSMessageID of the reply message in case of JMS destinations, null otherwise."),
    		version = "4.0.2-HF2",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "Immediately replies with a SimpleEvent to the Destination specified within the <code>request</code> SimpleEvent such as an inbox in case of RV or a JMSReplyTo property in case of JMS.     * The Destination in turn specifies the Channel where the SimpleEvent will be sent     * in the JMS Message. Returns the JMSMessageID of the reply message in case of JMS Destinations, null otherwise.",
    		cautions = "",
    		fndomain = {ACTION, BUI},
    		example = ""
    )
    public static Object replyEventImmediate(SimpleEvent request,
			SimpleEvent reply) {
		if (request == null || reply == null)
			return null;
		EventContext ctx = (request).getContext();
		if (ctx == null)
			return null;

		RuleSession session = RuleSessionManager.getCurrentRuleSession();
		ChannelManager chManager = session.getRuleServiceProvider()
				.getChannelManager();
		Channel.Destination destination = chManager.getDestination(reply
				.getDestinationURI());
		if (destination == null) {
			// The destination used is the request event's destination
			destination = chManager.getDestination(request.getDestinationURI());
		}
		Channel channel = destination.getChannel();

		/**
		 * Executing a reply action in a different thread will not work in case
		 * of a sync request-response type protocol like HTTP. In case of the
		 * HTTP Channel which works with caller's thread option the caller's
		 * thread is managed by the container, and there is not much we can do
		 * to control it as it is not a BE managed thread.
		 **/
		return ctx.replyImmediate(reply);
	}
    
    @com.tibco.be.model.functions.BEFunction(
    		name = "routeToImmediate",
    		synopsis = "Immediately routes a SimpleEvent to a Destination specified in <code>destinationPath</code> with a list of Properties <code>properties</code>.  These properties will override the default properties specified in the Destination.",
    		signature = "Object routeToImmediate(SimpleEvent event, String destinationPath, String properties)",
    		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name="event", type="SimpleEvent", desc = "The simpleEvent to be sent."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name="destinationPath", type="String", desc = "The path to the Destination that will be used to send the Event."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name="properties", type="String", desc="A list of property name-value pairs in the format <code>[name1=value1;name2=value2;...;]</code> when present in the name or value each character [<code> =, ; or \\</code> ] should be escaped with two antislash [<code> \\ </code>] characters for e.g. 2=1+1 should be written as <code>2\\\\=1+1</code> (proper string value being <code>2\\=1+1</code>)and <code>2\\3\\4</code> should be written as <code>2\\\\\\\\3\\\\\\\\4</code> (proper string value being <code>2\\\\3\\\\4</code>).")
    		},
     freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="Object", desc="The String JMSMessageID of the sent message in case of JMS destinations, null otherwise."),
     version = "4.0.2-HF1",
     see = "",
     mapper = @com.tibco.be.model.functions.BEMapper(),
     description = "Sends a SimpleEvent to a Destination with custom destination properties. The Destination in turn specifies the Channel where the Event will be sent.",
     cautions = "Custom channels may not support overriding of destination properties.",
     fndomain = {ACTION, BUI},
     example = ""
     )

    public static Object routeToImmediate(SimpleEvent event,
			String destinationPath, String properties) {
		try {
			if (event == null)
				return null;
			RuleSession session = RuleSessionManager.getCurrentRuleSession();
			ChannelManager manager = session.getRuleServiceProvider()
						.getChannelManager();
			return manager.sendEventImmediate(event, destinationPath, properties);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 throw BEExceptionImpl.wrapThrowableAsRE(e);
		}
    }
}
