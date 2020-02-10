/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 27/8/2010
 */

package com.tibco.be.functions.event;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.BUI;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;
import static com.tibco.be.model.functions.FunctionDomain.QUERY;

import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.tibco.be.functions.object.JSONHelper;
import com.tibco.be.functions.trax.dom.builder.impl.GVXMLNodeBuilder;
import com.tibco.be.functions.trax.transform.XSLTransformer;
import com.tibco.be.functions.xpath.EventSequenceHandler;
import com.tibco.be.functions.xpath.XSLT2Helper;
import com.tibco.be.model.functions.Enabled;
import com.tibco.be.model.functions.MapperElementType;
import com.tibco.be.model.functions.Variable;
import com.tibco.be.model.functions.VariableList;
import com.tibco.be.model.rdf.XiNodeBuilder;
import com.tibco.be.util.EngineTraxSupport;
import com.tibco.be.util.TemplatesArgumentPair;
import com.tibco.be.util.TraxSupport;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.driver.jms.JMSDestination;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.PayloadValidationHelper;
import com.tibco.cep.runtime.channel.impl.ChannelManagerImpl;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl;
import com.tibco.cep.runtime.model.event.impl.XiNodePayload;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.agent.tasks.AgentAction;
import com.tibco.cep.runtime.service.cluster.agent.tasks.AgentActionManager;
import com.tibco.cep.runtime.session.ProcessingContext;
import com.tibco.cep.runtime.session.ProcessingContextProvider;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiSerializer;

/**
 * User: ssubrama
 * Date: Jul 7, 2004
 * Time: 4:02:14 PM
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Event",
        synopsis = "Functions to operate on Events")

public class EventHelper extends XSLTransformer {

    @com.tibco.be.model.functions.BEFunction(
        name = "sendEvent",
        synopsis = "Sends the SimpleEvent <code>evt</code> to the configured channel and destination.",
        signature = "SimpleEvent sendEvent (SimpleEvent evt)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "evt", type = "SimpleEvent", desc = "An SimpleEvent to send.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "SimpleEvent", desc = "The same value as the argument evt, or null if there was an error."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sends the SimpleEvent <code>evt</code> to the configured default channel and destination for this SimpleEvent.\nThis method will fail if the SimpleEvent has not been configured with any default channel and destination information.",
        cautions = "This method will fail if the SimpleEvent has not been configured with any default channel and destination information.",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static SimpleEvent sendEvent(SimpleEvent evt) {
        try {
            if(evt == null) return null;
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            try {
				return sendEvent_Impl(evt, false, session);
            } catch (Exception e) {
                final Logger logger = session.getRuleServiceProvider().getLogger(EventHelper.class);
                logger.log(Level.ERROR, e, "Event not sent [id=%s] %s", evt.getId(), e.getMessage());
                logger.log(Level.DEBUG, "Event not sent [id=%s]", e, evt.getId());
                return null;
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
		}
	}

    protected static SimpleEvent sendEvent_Impl(SimpleEvent evt, boolean forceImmediate, RuleSession session) throws Exception {
        if (!forceImmediate && AgentActionManager.hasActionManager(session)) {
            final ProcessingContext pc = session.getProcessingContextProvider().getProcessingContext();
            AgentActionManager.addAction(session, new SendEventAction(evt, null, (String) null, pc));
            return evt;
        } else {
            ChannelManager manager = session.getRuleServiceProvider().getChannelManager();
            return manager.sendEvent(evt,evt.getDestinationURI(), null);
        }
    }
    
    @com.tibco.be.model.functions.BEFunction(
			name = "requestEvent",
			synopsis = "Make synchronous request/response calls using JMS. Sends an event and waits for a response to be returned." +
					"Each request creates and listens on a JMS temporary queue for a response.",
			signature = "Object requestEvent(SimpleEvent requestEvent, String responseEventURI, String requestEventDestinationURI, long timeout, String properties)",
			params = {
					@com.tibco.be.model.functions.FunctionParamDescriptor(name = "requestEvent", type = "SimpleEvent", desc = "The SimpleEvent to use to make the request."),
					@com.tibco.be.model.functions.FunctionParamDescriptor(name = "responseEventURI", type = "String", desc = "The Event URI of the expected response event. "+
							"The serializer set on the responseEvent's default destination will be used. "+
							"If set to <code>null</code>, the underlying response <code>javax.jmx.Message</code> will be returned as an <code>Object</code>"),
					@com.tibco.be.model.functions.FunctionParamDescriptor(name = "requestEventDestinationURI", type = "String", desc = "The URI (path) of the destination to use, to make the request." +
							"Eg. \"/MyChannel/MyDestination\"<br/>If set to <code>null</code> the requestEvent's default destination will be used."),
					@com.tibco.be.model.functions.FunctionParamDescriptor(name = "timeout", type = "long", desc = "The duration in milliseconds to wait for a response. Use -1 to wait forever."),
		            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "properties", type = "String",
                    desc = "A list of property name-value pairs in the format " +
                            "<code>[name1=value1;name2=value2;...;]</code>\n" +
                            "When present in the name or value each character " +
                            "[<code> =, ; or \\</code>] should be escaped with two antislash [<code> \\ </code>] " +
                            "characters. " +
                            "E.G. 2=1+1 should be written as <code>2\\\\=1+1</code> " +
                            "and <code>2\\3\\4</code> should be written as <code>2\\\\\\\\3\\\\\\\\4.</code>")
			},
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "response", type = "Object", desc = "If <code>responseEventURI</code> is specified, a SimpleEvent of type <code>responseEventURI</code>. " +
					"If set to null, then the underlying <code>javax.jms.Message</code> response message."),
			version = "5.2.1",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Make synchronous request/response calls using JMS. Sends an event and waits for a response to be returned." +
                    "Each request creates and listens on a JMS temporary queue for a response.",
			cautions = "This method will fail if <code>requestEvent</code> has not been configured with a default destination and if <code>requestEventDestinationURI</code> is null.",
			fndomain = {ACTION, BUI},
			example = ""
			)
	public static Object requestEvent(SimpleEvent requestEvent, String responseEventURI, String requestEventDestinationURI , long timeoutMillis, String properties) {
		RuleSession session = RuleSessionManager.getCurrentRuleSession();
		try {
			if(requestEvent == null) {
				throw new Exception("'requestEvent' cannot be null.");
			}
			if (requestEventDestinationURI == null && requestEvent.getDestinationURI() == null) {
	    		throw new Exception("'requestEventDestinationURI' is null and requestEvent is not configured with a default destination.");
	    	}
			if (timeoutMillis < -1) {
	    		throw new Exception("Invalid value '" + timeoutMillis + "' for timeout.");
	    	}
			return requestEvent_Impl(requestEvent, responseEventURI, requestEventDestinationURI, timeoutMillis, properties, session);
		} catch (Exception e) {
			final Logger logger = session.getRuleServiceProvider().getLogger(EventHelper.class);
			logger.log(Level.ERROR, e, "Event not sent [id=%s] %s", requestEvent.getId(), e.getMessage());
			return null;
		}
	}
    
    /**
     * Internal requestEvent method.
     * @param outevent
     * @param responseEventURI
     * @param outgoingDestinationPath
     * @param timeout
     * @param properties
     * @param session
     * @return
     * @throws Exception
     */
    private static Object requestEvent_Impl(SimpleEvent outevent, String responseEventURI, String outgoingDestinationPath, long timeout, String properties, RuleSession session) throws Exception {
    	ChannelManager manager = session.getRuleServiceProvider().getChannelManager();
    	
    	if (outgoingDestinationPath == null) {
    		outgoingDestinationPath = outevent.getDestinationURI();
    	}
        return manager.requestEvent(outevent, responseEventURI, outgoingDestinationPath, timeout, properties);
    }
    
    public static SimpleEvent createEvent2(String xslt, VariableList varlist) {
        return createEvent(xslt, xslt, varlist);
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "createEvent",
            synopsis = "Create a SimpleEvent using XSLT EventBuilder.",
            signature = "SimpleEvent createEvent (String xslt-template)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xslt-template", type = "String", desc = "formed using XSLT EventBuilder.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "SimpleEvent", desc = "The newly created SimpleEvent."),
            version = "1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(
            		enabled=@com.tibco.be.model.functions.Enabled(value=true),
            		type=MapperElementType.XSLT,
            		inputelement="<xsd:schema xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
            				"			<xsd:element name=\"createEvent\">\n" +
            				"				<xsd:complexType>\n" +
            				"					<xsd:sequence>\n" +
            				"						<xsd:element name=\"event\" type=\"xsd:anyType\"\n" +
            				"							minOccurs=\"1\" maxOccurs=\"1\" />\n" +
            				"					</xsd:sequence>\n" +
            				"				</xsd:complexType>\n" +
            				"			</xsd:element>\n" +
            				"</xsd:schema>\n"),
            description = "Create a new SimpleEvent using the an XSLT EventBuilder.",
            cautions = "none",
            fndomain = {ACTION, BUI},
            example = ""
    )
    public static SimpleEvent createEvent(String key, String xslt, VariableList varlist) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        return createEvent3(session, key, xslt, varlist);
    }

    public static SimpleEvent createEvent3(RuleSession session, String key, String xslt, VariableList varlist) {
        Logger logger = session.getRuleServiceProvider().getLogger(EventHelper.class);
        try {
            GlobalVariables gVars = session.getRuleServiceProvider().getGlobalVariables();
            TemplatesArgumentPair tap = EngineTraxSupport.getTemplates(key, xslt, session.getRuleServiceProvider().getTypeManager());
            List<String> paramNames = tap.getParamNames();
            XiNode nodes[] = new XiNode[paramNames.size()];
            int i = 0;
            for(String paramName : paramNames) {
                if (paramName.equalsIgnoreCase(GlobalVariablesProvider.NAME)) {
                    nodes[i] = gVars.toXiNode();
                }
                else {
                    Variable v = varlist.getVariable(paramName);
                    if (v != null)
                        nodes[i] = XiNodeBuilder.makeXiNode(v);
                    else
                        nodes[i] = null;
                }
                ++i;
            }
            Class clz = tap.getRecvParameterClass();
            SimpleEvent evt = null;
            if (clz != null) {
                if(SimpleEvent.class.isAssignableFrom(clz)) {
                    long id = session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz);
                    Constructor cons = clz.getConstructor(new Class[] {long.class});
                    evt = (SimpleEvent)cons.newInstance(new Object[] {new Long(id)});
                }
            }
            if (TraxSupport.isXPath2()) {
            	EventSequenceHandler sh = new EventSequenceHandler(evt, logger);
            	XSLT2Helper.doTransform(tap, nodes, xslt, varlist, sh, evt);
            	evt = sh.getEvent();
            } else {
            	SAX2EventInstance ei = new SAX2EventInstance(evt, logger);
            	TraxSupport.doTransform(tap.getTemplates(), nodes, ei);
            	evt = ei.getEvent();
            }
            
            // check if this need to be converted to JSON
            if (checkForJSONPayloadConversion(evt)) {
            	XiNodePayload payloadNode = (XiNodePayload)evt.getPayload();
            	payloadNode.setJSONPayload(true);
//            	EventPayload jsonEventPayload = JSONXiNodeConversionUtil.convertXiNodeToJSON(payloadNode.getNode(), true);
//            	if (jsonEventPayload != null) {
//            		evt.setPayload(jsonEventPayload);
//            	}
            }
            
            if (PayloadValidationHelper.ENABLED) {
                PayloadValidationHelper.validate(evt, session.getRuleServiceProvider());
            }
            return evt;
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e, "Exception while creating an Event using XSLT.");
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "consumeEvent",
        synopsis = "Consume the event <code>evt</code> passed. This method removes the event\nfrom working memory.",
        signature = "void consumeEvent (Event evt)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "evt", type = "Event", desc = "The event to consume.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Consume the Event passed (removes the Event from working memory).",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void consumeEvent(Event evt) {
        if(evt == null) return;
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
                session.retractObject(evt, true);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "acknowledgeEvent",
        synopsis = "Manually and immediately acknowledge the event <code>evt</code> passed.",
        signature = "void acknowledgeEvent (SimpleEvent evt)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "evt", type = "SimpleEvent", desc = "The event to acknowledge.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Acknowledge the event passed.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void acknowledgeEvent(SimpleEvent evt) {
        if(evt == null) return;
        try {
            evt.acknowledge();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "replyEvent",
        synopsis = "Replies with a <code>reply</code> SimpleEvent to a <code>request</code> SimpleEvent.\nIf a reply destination on the <code>request</code> SimpleEvent is specified, it will be\nused to send the <code>reply</code> SimpleEvent, else no action is taken.",
        signature = "boolean replyEvent(SimpleEvent request, SimpleEvent reply)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "request", type = "SimpleEvent", desc = "The original request SimpleEvent."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "reply", type = "SimpleEvent", desc = "The Reply SimpleEvent.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the SimpleEvent is sent; false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sends a SimpleEvent to the Destination specified within the <code>request</code>\nSimpleEvent such as an inbox in case of RV or a JMSReplyTo property in case of JMS.\nThe Destination in turn specifies the Channel where the SimpleEvent will be sent\nin the JMS Message. If the original SimpleEvent does not have a reply destination or a subject set,\nthis method will return false.",
        cautions = "",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean replyEvent (SimpleEvent request, SimpleEvent reply) {
        try {
            return replyEvent_Impl(request, reply, false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean replyEvent_Impl(SimpleEvent request, SimpleEvent reply, boolean forceImmediate) {
        if(request == null || reply == null) return false;
        EventContext ctx = (request).getContext();
        if (ctx == null) return false;
        
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        ChannelManager chManager = session.getRuleServiceProvider().getChannelManager();
        Channel.Destination destination = chManager.getDestination(reply.getDestinationURI());
        if (destination == null) {
            //The destination used is the request event's destination
            destination = chManager.getDestination(request.getDestinationURI());
        }
        Channel channel = destination.getChannel();

        /**
         * Executing a reply action in a different thread will not
         * work in case of a sync request-response type protocol like HTTP.
         * In case of the HTTP Channel which works with caller's thread option
         * the caller's thread is managed by the container, and there is not
         * much we can do to control it as it is not a BE managed thread.
         **/
    	if (!forceImmediate && AgentActionManager.hasActionManager(session) && channel.isAsync()) {
            AgentActionManager.addAction(session, new ReplyEventAction(reply, ctx,
                    session.getProcessingContextProvider().getProcessingContext()));
            return true;
        } else {
            return ctx.reply(reply);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "assertEvent",
        synopsis = "Asserts a SimpleEvent into the working memory.",
        signature = "SimpleEvent assertEvent (SimpleEvent event)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "The simpleEvent to be asserted.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "SimpleEvent", desc = "The SimpleEvent. Null if there is an error, of the argument was null."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Asserts a SimpleEvent into the working memory.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static SimpleEvent assertEvent(SimpleEvent event) {
        if(event == null) return null;
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        try {
            session.assertObject(event, true);
        } catch (DuplicateExtIdException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return event;
    }//assertEvent

    @com.tibco.be.model.functions.BEFunction(
        name = "getDestinationURI",
        synopsis = "Returns the destination URI of the specified Event.",
        signature = "String getDestinationURI (SimpleEvent event)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "The event whose destination URI is to be returned.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The destination URI on which the event was received. For internally generated events,\nreturns the event's default destination if defined, otherwise returns <code>null</code>."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns The destination URI of the specified event.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String getDestinationURI(SimpleEvent event) {
        if(event == null) return null;
        return event.getDestinationURI();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setPayload",
        synopsis = "Makes the target SimpleEvent use the same payload object as the source SimpleEvent.",
        signature = "void setPayload(SimpleEvent target, SimpleEvent source)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "target", type = "SimpleEvent", desc = "The SimpleEvent whose payload is being set."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "source", type = "SimpleEvent", desc = "The SimpleEvent from which the payload is obtained")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Makes the target SimpleEvent use the same payload as the source SimpleEvent.",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void setPayload(SimpleEvent target, SimpleEvent source) {
        if(target == null || source == null) return ;
        target.setPayload(source.getPayload());
        try {
            if (PayloadValidationHelper.ENABLED) {
                PayloadValidationHelper.validate(target);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
    		name = "validatePayload",
    		signature = "void validatePayload(SimpleEvent event)",
    		params = {
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "The SimpleEvent whose payload will be validated, if present."),
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "Validates the payload of the event in the current RuleSession. Validation will throw an Exception if the validation failed.",
    		cautions = "",
    		fndomain = {ACTION, CONDITION, BUI},
    		example = ""
    		)
    public static void validatePayload(SimpleEvent event) {
    	try {
    		PayloadValidationHelper.validateEvent(event);
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getPayload",
        synopsis = "Returns the payload associated with the event",
        signature = "Object getPayload(SimpleEvent event)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "The SimpleEvent whose payload is being returned.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Payload Object"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the payload associated with the event",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static Object getPayload(SimpleEvent event) {
        if(event == null) return null;
        return event.getPayload();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "routeTo",
        synopsis = "Routes a SimpleEvent to a Destination specified in <code>destinationPath</code>\nwith a list of Properties <code>properties</code>.  These properties will override the\ndefault properties specified in the Destination.",
        signature = "SimpleEvent routeTo(SimpleEvent event, String destinationPath, String properties) {",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "The simpleEvent to be sent."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "destinationPath", type = "String",
                    desc = "The path to the Destination that will be used to send the Event. " +
                            "E.G. \"/MyChannel/MyDestination\" where MyChannel is the Channel in the model " +
                            "and MyDestination is the destination in it."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "properties", type = "String",
                    desc = "A list of property name-value pairs in the format " +
                            "<code>[name1=value1;name2=value2;...;]</code>\n" +
                            "When present in the name or value each character " +
                            "[<code> =, or \\</code>] should be escaped with two antislash [<code> \\ </code>] " +
                            "characters. " +
                            "E.G. 2=1+1 should be written as <code>2\\\\=1+1</code> " +
                            "and <code>2\\3\\4</code> should be written as <code>2\\\\\\\\3\\\\\\\\4.</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "SimpleEvent", desc = "The same value as the argument evt, or null if there was an error."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sends a SimpleEvent to a Destination with custom destination properties.\nThe Destination in turn specifies the Channel where the Event will be sent.",
        cautions = "Custom channels may not support overriding of destination properties.",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static SimpleEvent routeTo(SimpleEvent event, String destinationPath, String properties) {
        try {
            return routeTo_Impl(event, destinationPath, properties, false);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }//catch;

    }//routeTo

    public static SimpleEvent routeTo_Impl(SimpleEvent event, String destinationPath, String properties, boolean forceImmediate) throws Exception {
            if(event == null) return null;
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if (!forceImmediate && AgentActionManager.hasActionManager(session)) {
            final ProcessingContext pc = session.getProcessingContextProvider().getProcessingContext();
            AgentActionManager.addAction(session, new SendEventAction(event, destinationPath, properties, pc));
                return event;
            } else {
            ChannelManager manager = session.getRuleServiceProvider().getChannelManager();
            return manager.sendEvent(event, destinationPath, properties);
            }
    }

    public static SimpleEvent routeTo_Impl(
            SimpleEvent event,
            String destinationPath,
            Map<String, Object> properties,
            boolean forceImmediate)
            throws Exception
    {
        if (null == event) {
            return null;
        }

        final RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if (!forceImmediate && AgentActionManager.hasActionManager(session)) {
            final ProcessingContext pc = session.getProcessingContextProvider().getProcessingContext();
            AgentActionManager.addAction(session, new SendEventAction(event, destinationPath, properties, pc));
            return event;
        } else {
            return ((ChannelManagerImpl) session.getRuleServiceProvider().getChannelManager())
                    .sendEvent(event, destinationPath, properties);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getByExtId",
        synopsis = "Returns the SimpleEvent existing in Working Memory using <code>extId</code> as the external ID.",
        signature = "SimpleEvent getByExtId (String extId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "The external ID.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "SimpleEvent", desc = "The SimpleEvent with the external ID as <code>extId</code>. Returns <code>null</code> if no such SimpleEvent\nexists in Working Memory."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the SimpleEvent existing in Working Memory using <code>extId</code> as the external ID.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static SimpleEvent getByExtId(String extId) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        return (SimpleEvent)session.getObjectManager().getEvent(extId);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getById",
        synopsis = "Returns the Event using <code>Id</code> as the internal ID.",
        signature = "Event getById (long Id)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "Id", type = "long", desc = "The internal ID.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Event", desc = "The event with the internal ID as <code>Id</code>.  Returns <code>null</code> if no such event\nexists in Working Memory."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Event with <code>Id</code> as the internal ID.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Event getById(long Id) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        return session.getObjectManager().getEvent(Id);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getRaw",
        synopsis = "Returns the Raw SimpleEvent Payload",
        enabled = @Enabled(value=false),
        signature = "Object getRaw(SimpleEvent e)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "e", type = "SimpleEvent", desc = "The SimpleEvent for which payload is required")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Raw payload object"),
        version = "1.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Raw SimpleEvent Payload",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static Object getRaw(SimpleEvent e) {
        try {
            //TODO: SS - return (Object) e.getPayload().getObject();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "setRaw",
        synopsis = "Sets the payload of the SimpleEvent to the given Object",
        enabled = @Enabled(value=false),
        signature = "Object setRaw(SimpleEvent e, Object raw)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "e", type = "SimpleEvent", desc = "The Event for which payload is required."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "raw", type = "Object", desc = "The raw Object for the payload.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the payload of the SimpleEvent to the given Object",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void setRaw(SimpleEvent e, Object raw) {
        try {
            //TODO: SS - Set the SmElement appropriately
            //EventPayload payload = new XiNodePayload(raw, null);
            //((SimpleEvent)e).setPayload(payload);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "cancelExpiry",
        synopsis = "Prevents the SimpleEvent from expiring; it's TTL is effectively -1.",
        enabled = @Enabled(value=false),
        signature = "boolean cancelExpiry(SimpleEvent e)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "e", type = "SimpleEvent", desc = "The Event to cancel.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.5",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Prevents the SimpleEvent from expiring; it's TTL is effectively -1.",
        cautions = "Doesn't have an effect on Events with zero TTL",
        fndomain = {ACTION},
        example = ""
    )
    public static void cancelExpiry(SimpleEvent e) {
        //TODOSS : commented on Nick's behalf
//        AbstractEventImpl aei = (AbstractEventImpl) e;
//        if(aei.expireTimer != null) {
//            aei.expireTimer.cancel();
//        }
//        aei.expireTimer = TTLManager.CANCELLED_EXPIRE_TIMER;
    }

    public static SimpleEvent newEventInstance (RuleSession session, Class clz, XiNode node) throws ClassCastException, InstantiationException, Exception {

        SimpleEvent evt = makeNewEvent(clz, node, session);
        SAX3EventInstance ei = new SAX3EventInstance(session, evt);
        ei.deserialize(node);

        return evt;
    }

    private static SimpleEvent makeNewEvent(Class clz, XiNode node, RuleSession session) throws InstantiationException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        SimpleEvent evt = null;

        if (Modifier.isAbstract(clz.getModifiers())) {

            ExpandedName name = node.getType().getExpandedName();
            TypeManager.TypeDescriptor td = session.getRuleServiceProvider().getTypeManager().getTypeDescriptor(name);
            if (td == null) {

            }
            clz = td.getImplClass();
            if (Modifier.isAbstract(clz.getModifiers())) throw new InstantiationException("Cannot instantiate abstract Concept class, provide a concrete Type substitution");
        }

        if (clz != null) {
            if(SimpleEvent.class.isAssignableFrom(clz)) {
                long id = session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz);
                Constructor cons = clz.getConstructor(new Class[] {long.class});
                evt = (SimpleEvent)cons.newInstance(new Object[] {new Long(id)});
            }
            else {
                throw new ClassCastException("Invalid class type specified - "  + clz);
            }
        }
        return evt;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getPayloadAsBytes",
        synopsis = "This function returns event payload as a <code>byte[]</code>",
        signature = "Object getPayloadAsBytes(SimpleEvent input)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "input", type = "SimpleEvent", desc = "The input event")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "byte[]", desc = "as Object"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This function returns event payload as a <code>byte[]</code>",
        cautions = "The input event cannot be null.",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static Object getPayloadAsBytes(SimpleEvent input) {
        if (input == null) {
            throw new IllegalArgumentException("Input Event cannot be null");
        }
        EventPayload eventPayload = input.getPayload();
        if (eventPayload != null) {
            try {
                return eventPayload.toBytes();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "createEventFromXML",
        synopsis = "This function returns an event instance using the <code> xml </code>. The XML String should adhere to the\nXSD schema corresponding to the event definition.",
        signature = "Event createEventFromXML (String uri, String xml)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "will use the XML to parse the namespace and create the corresponding event."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xml", type = "String", desc = "XML string to be parsed")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Event", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This function returns an event instance using the <code> xml </code>. The XML String should adhere to the\nXSD schema corresponding to the event definition.<br/>If it is desired to strip the whitespace from source XML then set the property java.property.tibco.be.createEventFromXML.stripwhitespace to true in be-engine.tra, the default value of this property is false.",
        cautions = "The following exceptions are thrown when Event.createEventFromXML() is used with wrong arguments to create events from XML."
        		+ "<br/>(1) java.lang.RuntimeException: com.tibco.xml.data.cursor.UndefinedPropertyException: [attributes] is not defined for the node type \"document\"."
        		+ "<br/>(2) java.lang.RuntimeException: org.xml.sax.SAXParseException; lineNumber: 1; columnNumber: 41; The processing instruction target matching \"[xX][mM][lL]\" is not allowed.",
        fndomain = {ACTION, BUI},
        example = "(1) The second argument is used as shown below."
        		+ "<br/><code>Event.createEventFromXML(\"&ltONTOLOGY_PATH_OF_THE_EVENT&gt\", \"&ltEventName&gt&ltpayload&gt\"+xmlpayload+\"&lt/payload&gt&lt/EventName&gt\");</code>"
        		+ "<br/>"
        		+ "Creating a orderline event from the XML argument as payload."
        		+ "<br/><code>Events.OrderlineEvent Orderline = Event.createEventFromXML(\"/Events/OrderlineEvent\", \"&ltOrderlineEvent&gt&ltpayload&gt\" + xmlpayload + \"&lt/payload&gt&lt/OrderlineEvent&gt\");</code>"
        		+ "<br/>Note : The XML header tag present in String XML might cause parsing issues. In this case, remove the XML version from the input XML string<br/>Part to remove :&lt?xml version=\"1.0\" encoding=\"UTF-8\"?&gt"
        		+ "<br/>"
        		+ "<br/>(2) Pass the event properties and payload XML. "
        		+ "<br/><code>Event.createEventFromXML(\"&ltONTOLOGY_PATH_OF_THE_EVENT&gt\", \"&ltEventName&gt&ltproperty1&gt123&lt/property1&gt&ltproperty2&gt456&lt/property2&gt&ltproperty3&gt789&lt/property3&gt&lt/EventName&gt\");</code>"
        		+ "<br/>"
        		+ "Creating an event by passing the second argument with the event properties XML as shown below."        		
        		+ "<br/><code>Events.TestEve TestProperty = Event.createEventFromXML(\"/Events/TestEve\", \"&ltTestEve&gt&ltlineId&gt4&lt/lineId&gt&ltproductId&gtabc&lt/productId&gt&ltqty&gt4&lt/qty&gt&lt/TestEve&gt\");</code>"
    )
    public static Event createEventFromXML (String uri, String xml) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
                if (uri != null) {
                    TypeManager.TypeDescriptor type=session.getRuleServiceProvider().getTypeManager().getTypeDescriptor(uri);
                    if (type != null) {
                        Class clz=type.getImplClass();
                        Constructor cons = clz.getConstructor(new Class[] {long.class});
                        SimpleEvent evt=(SimpleEvent) cons.newInstance(new Object[] {new Long(session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz))});
                        SAX4EventInstance tmp= new SAX4EventInstance(session, evt);
                        if (RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties()
                        		.getProperty("tibco.be.createEventFromXML.stripwhitespace", "false").
                        		equalsIgnoreCase("true")) {
                        	xml = xml.replaceAll("[\\r\\n]+\\s*", "");
                        }
                        XiParserFactory.newInstance().parse(new InputSource(new StringReader(xml)), tmp);
                        
                        if (PayloadValidationHelper.ENABLED) {
                            PayloadValidationHelper.validate(tmp.evt, session.getRuleServiceProvider());
                        }
                        return tmp.evt;
                    } else {
                        throw new RuntimeException("Invalid Entity URI " + uri);
                    }
                } else {
                    SAX4EventInstance tmp= new SAX4EventInstance(session, null);
                    XiParserFactory.newInstance().parse(new InputSource(new StringReader(xml)), tmp);
                    if (PayloadValidationHelper.ENABLED) {
                        PayloadValidationHelper.validate(tmp.evt, session.getRuleServiceProvider());
                    }
                    return tmp.evt;
                }
            } else {
                throw new RuntimeException("createEventFromXML not allowed outside of a rule session");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "serializeToXML",
        signature = "String serializeToXML(SimpleEvent event)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "The input event")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Serialized XML"),
        version = "5.5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This function serializes the event to XML string",
        cautions = "The input event cannot be null.",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static String serializeToXML(SimpleEvent event) {
    	if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
    	final XiNode docRoot = XiSupport.getXiFactory().createDocument();
    	final XiNode rootNode = docRoot.appendElement(event.getExpandedName());
    	try {
    		event.toXiNode(rootNode);
    		return XiSerializer.serialize(docRoot);
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "serializeToJSON",
        signature = "String serializeToJSON(SimpleEvent event, boolean pretty)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "The input event"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pretty", type = "boolean", desc = "If true, the output will be formatted for human-readability.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Serialized JSON"),
        version = "5.5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This function serializes the event to JSON string",
        cautions = "The input event cannot be null.",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static String serializeToJSON(SimpleEvent event, boolean pretty) {
    	if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
    	try {
    		return JSONEventHelper.serializeToJSON(event, pretty);
    	} catch(Exception e) {
    		throw new RuntimeException(e);
    	}
    }

    protected static class SendEventAction implements AgentAction {
        SimpleEvent evt;
        String destinationURI;
        private final Object properties;
        private final ProcessingContext processingContext;

        public SendEventAction(
                SimpleEvent evt,
                String destinationURI,
                String properties,
                ProcessingContext processingContext) {
            this.evt=evt;
            this.destinationURI=destinationURI;
            this.properties=properties;
            this.processingContext = processingContext;
        }

        public SendEventAction(
                SimpleEvent evt,
                String destinationURI,
                Map<?,?> properties,
                ProcessingContext processingContext) {
            this.evt = evt;
            this.destinationURI = destinationURI;
            this.properties = properties;
            this.processingContext = processingContext;
        }

        public void run(CacheAgent cacheAgent) throws Exception{
            if (cacheAgent instanceof InferenceAgent) {
                InferenceAgent ia= (InferenceAgent) cacheAgent;
                ChannelManager manager = ia.getRuleSession().getRuleServiceProvider().getChannelManager();
                ProcessingContextProvider pcp = ia.getRuleSession().getProcessingContextProvider();
                try {
	                pcp.setProcessingContext(this.processingContext);
	                final String uri = (null == this.destinationURI) ? this.evt.getDestinationURI() : this.destinationURI;
	                if (this.properties instanceof Map) {
	                    ((ChannelManagerImpl) manager).sendEvent(this.evt, uri, (Map) this.properties);
	                } else {
	                    manager.sendEvent(this.evt, uri, (String) this.properties);
	                }
                } finally {
                	pcp.setProcessingContext(null);
                }
            }
        }
    }

    protected static class ReplyEventAction implements AgentAction {
        SimpleEvent evt;
        EventContext ctx;
        private final ProcessingContext processingContext;

        public ReplyEventAction(
                SimpleEvent evt,
                EventContext ctx,
                ProcessingContext processingContext) {

            this.evt=evt;
            this.ctx=ctx;
            this.processingContext = processingContext;
        }

        public void run(CacheAgent cacheAgent) throws Exception{

            RuleSession rs = null;

            if (cacheAgent instanceof InferenceAgent) {
                rs = ((InferenceAgent) cacheAgent).getRuleSession();
            }

            try {
	            if (null != rs) {
	                rs.getProcessingContextProvider().setProcessingContext(this.processingContext);
	            }
	            ctx.reply(evt);
            } finally {
            	if (null != rs) {
	                rs.getProcessingContextProvider().setProcessingContext(null);
	            }
            }
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getByExtIdByUri",
        synopsis = "Returns the SimpleEvent existing in Working Memory using <code>extId</code> as the external ID.",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.getbyuri", value=true),
        signature = "SimpleEvent getByExtIdByUri (String extId, String uri)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "The external ID."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI of the event")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "SimpleEvent", desc = "The SimpleEvent with the external ID as <code>extId</code>. Returns <code>null</code> if no such SimpleEvent\nexists in Working Memory."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the SimpleEvent existing in Working Memory using <code>extId</code> as the external ID.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static SimpleEvent getByExtIdByUri(String extId, String uri) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        return (SimpleEvent)session.getObjectManager().getEventByUri(extId, uri);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getByIdByUri",
        synopsis = "Returns the SimpleEvent existing in Working Memory using <code>id</code> as the internal ID.",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.getbyuri", value=true),
        signature = "SimpleEvent getByIdByUri (long id, String uri)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "id", type = "String", desc = "The internal ID."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI of the event")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "SimpleEvent", desc = "The SimpleEvent with the internal ID as <code>id</code>. Returns <code>null</code> if no such SimpleEvent\nexists in Working Memory."),
        version = "3.0.2-HF1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the SimpleEvent existing in Working Memory using <code>id</code> as the internal ID.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static SimpleEvent getByIdByUri(long id, String uri) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        return (SimpleEvent)session.getObjectManager().getEventByUri(id, uri);
    }
    
    protected static String sendEventImmediate(SimpleEvent evt,
			RuleSession session) throws Exception {
			ChannelManager manager = session.getRuleServiceProvider()
					.getChannelManager();
			return manager.sendEventImmediate(evt, evt.getDestinationURI(), null);
	}
    
    @com.tibco.be.model.functions.BEFunction(
        name = "createEventUsingXSLT",
        synopsis = "Create a SimpleEvent using the given xslt, xml source and parameters in scope",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.createevent.xslt", value=false),
        signature = "SimpleEvent createEventUsingXSLT (String xslt, String eventURI, String xmlSource, Object[] xsltParams, String[] xsltXMLParams, String[] varName, boolean[] isArray)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xslt", type = "String", desc = "XSLT as string."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "eventURI", type = "String", desc = "Event URI"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xmlSource", type = "String", desc = "A xml string to be used as source. It should be a valid xml or a null."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xsltParams", type = "Object[]", desc = "An array of objects to be used as xslt parameters"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xsltXMLParams", type = "String[]", desc = "An array of xml strings to be used as xslt parameters"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String[]", desc = "This will be a part of VariableList. The VariableList will contain only the parameters added to xsltParams & xsltXMLParams."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "isArray", type = "boolean[]", desc = "This will be a part of VariableList")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "SimpleEvent", desc = "A SimpleEvent created after transformation."),
        version = "3.0.2-HF1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "A SimpleEvent created after transformation.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static SimpleEvent createEventUsingXSLT(String xslt, String eventURI, String xmlSource, Object[] xsltParams, String[] xsltXMLParams, String[] varName, boolean[] isArray) throws RuntimeException {
		try {
			
			DocumentBuilder docBuilder = getDocumentBuilderFactory().newDocumentBuilder();
	
			XSLTransformer t = new XSLTransformer();
			
			xsltParams = paramToXMLNode(xsltParams, varName, isArray,
					docBuilder);
			
			Node[] xmlParams = xmlStringToXMLNode(xsltXMLParams, docBuilder);

			RuleSession session = RuleSessionManager.getCurrentRuleSession();
			RuleServiceProvider provider = session.getRuleServiceProvider();
			GlobalVariables gVars = provider.getGlobalVariables();
			GVXMLNodeBuilder builder = new GVXMLNodeBuilder();
			Element gvDocElem = builder.build(docBuilder.newDocument(), gVars, "globalVariables");
	
			SimpleEvent event = t.transform2Event(xslt, eventURI, varName, xsltParams, gvDocElem, xmlParams);
	
	        return event;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
    
    @com.tibco.be.model.functions.BEFunction(
        name = "createEventFromJSON",
        synopsis = "This function returns an event instance using the <code> json </code>. The JSON String should adhere to the structure corresponding to the event definition.",
        signature = "Event createEventFromJSON(String uri, String json)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI of the corresponding event."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "json", type = "String", desc = "JSON string to be parsed")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Event", desc = "returns an event instance"),
        version = "5.2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This function returns an event instance using the <code> json </code>. The JSON String should adhere to the structure corresponding to the event definition.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Event createEventFromJSON (String uri, String json) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
                if (uri != null) {
                    TypeManager.TypeDescriptor type=session.getRuleServiceProvider().getTypeManager().getTypeDescriptor(uri);
                    if (type != null) {
                        Class clz=type.getImplClass();
                        Constructor cons = clz.getConstructor(new Class[] {long.class});
                        SimpleEvent evt = (SimpleEvent) cons.newInstance(new Object[] {new Long(session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz))});                        
                        SimpleEvent event = JSONHelper.deserializeToEvent(evt, session, json);
                        if (PayloadValidationHelper.ENABLED) {
                            PayloadValidationHelper.validate(event, session.getRuleServiceProvider());
                        }
                        return event;
                    } else {
                        throw new RuntimeException("Invalid Entity URI " + uri);
                    }
                } else {
                	throw new RuntimeException("URI missing.");
                }
            } else {
                throw new RuntimeException("createEventFromJSON not allowed outside of a rule session");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "assertAdvisoryEvent",
            synopsis = "Assert an AdvisoryEvent with user defined fields.",
            signature = "void assertAdvisoryEvent(String category, String type, String message)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "category", type = "String", desc = "New AdvisoryEvent's category attribute.  The broad category of advisory."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "type", type = "String", desc = "New AdvisoryEvent's category attribute. Type of advisory within the category."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "String", desc = "New AdvisoryEvent's message attribute."),
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.3.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Assert an AdvisoryEvent with user defined fields.",
            cautions = "none",
            fndomain = {ACTION, BUI},
            example = ""
    )
    public static void assertAdvisoryEvent(String category, String type, String message) {
        try {
        	RuleSession session = RuleSessionManager.getCurrentRuleSession();
            AdvisoryEventImpl adv = new AdvisoryEventImpl(
            		session.getRuleServiceProvider().getIdGenerator().nextEntityId(AdvisoryEventImpl.class)
                , null, category, type, message);
            session.assertObject(adv, true);
        } catch (DuplicateExtIdException dupex) {
            //no extId specified so this should never happen
            throw new RuntimeException(dupex);
        }
    }
    
    /**
     * Check for destination property for setting JSON payload
     * 
     * @param evt
     * @throws Exception
     */
    private static boolean checkForJSONPayloadConversion(SimpleEvent evt) throws Exception {
    	RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
    	if (rsp == null) {
    		// this can happen in API mode
    		return false;
    	}
    	Channel.Destination destination = rsp.getChannelManager().getDestination(evt.getDestinationURI());
    	if (destination instanceof HttpDestination) {
    		return (((HttpDestination)destination).isJSONPayload() && evt.getPayload() instanceof XiNodePayload);
    	} else if(destination instanceof JMSDestination) {
    		return (((JMSDestination)destination).isJSONPayload() && evt.getPayload() instanceof XiNodePayload);
    	}

    	return false;
    }
}
