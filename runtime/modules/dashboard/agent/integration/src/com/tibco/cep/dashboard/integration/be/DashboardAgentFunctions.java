package com.tibco.cep.dashboard.integration.be;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.functions.Enabled;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BusinessActionsController;
import com.tibco.cep.dashboard.psvr.biz.XMLBizRequestImpl;
import com.tibco.cep.dashboard.psvr.streaming.Streamer;
import com.tibco.cep.dashboard.psvr.streaming.StreamerRegistry;
import com.tibco.cep.driver.ancillary.api.Session;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.ObjectPayload;
import com.tibco.cep.runtime.model.event.impl.XiNodePayload;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.xml.datamodel.XiNode;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Dashboard",
        category = "Dashboard",
        enabled = @Enabled(value=false),
        synopsis = "Functions For Accessing Dashboard features")

public class DashboardAgentFunctions {

	@com.tibco.be.model.functions.BEFunction(
        name = "process",
        synopsis = "This function process an incoming request to generate an\nappropriate response.",
        signature = "String process(String request)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "request", type = "String", desc = "The incoming request"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "responseEventURI", type = "String", desc = "response")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "SimpleEvent", desc = ""),
        version = "1.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Processes an incoming request to generate an appropriate\nresponse",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static SimpleEvent process(String request, String responseEventURI) {
		BizResponse bizResponse = processRequest(request);
		RuleServiceProvider ruleServiceProvider = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
		if (ruleServiceProvider == null) {
			throw new IllegalStateException("Should be invoked within the context of a BE Session");
		}
		TypeManager typeManager = ruleServiceProvider.getTypeManager();
		SimpleEvent responseEvent;
		try {
			responseEvent = (SimpleEvent) typeManager.createEntity(responseEventURI);
		} catch (Exception e) {
			throw new IllegalArgumentException("could not create a event using " + responseEventURI);
		}
		populateEvent(responseEvent, bizResponse);
		return responseEvent;
	}

	static BizResponse processRequest(String request) {
		String decodedRequest = request;
		//PATCH we should not be decoding the incoming request since we will have issue when taking in base64 encoded image data (tibbr)
		try {
			decodedRequest = URLDecoder.decode(request, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO should we log UnsupportedEncodingException exception?
		} catch (IllegalArgumentException e) {
			// TODO should we log IllegalArgumentException exception?
		}
		BizResponse bizResponse = BusinessActionsController.getInstance().process(decodedRequest);
		return bizResponse;
	}

	static void populateEvent(SimpleEvent event, BizResponse bizResponse) {
		//Modified by Anand on 03/04/2011 to fix BE-8262 , we need to use XiNode to send UTF encoded XML's
		String response = bizResponse.toString();
		try {
			XiNode document = XiSupport.getParser().parse(new InputSource(new StringReader(response)));
			event.setPayload(new XiNodePayload(null,document.getFirstChild()));
		} catch (SAXException e) {
			//we will get an exception when we are sending HTML (since HTML is not well-formed XML)
			event.setPayload(new ObjectPayload(bizResponse.toString()));
		} catch (IOException e) {
			//we will get an exception when we are sending HTML (since HTML is not well-formed XML)
			event.setPayload(new ObjectPayload(bizResponse.toString()));
		}
		for (String header : bizResponse.getHeaderNames()) {
			String value = bizResponse.getHeader(header);
			try {
				event.setProperty(header, value);
			} catch (Exception e) {
				throw new IllegalStateException(event.getExpandedName() + " does not contain a property named '" + header + "'");
			}
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "startStreaming",
        synopsis = "This function starts streaming updates. Each update is fired\nas a event",
        signature = "startStreaming(String name,String streamingEventURI,String\nrequest)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sessionName", type = "String", desc = "A unique name which defines a streaming session"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "streamingEventURI", type = "String", desc = "update")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Starts streaming updates",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void startStreaming(String sessionName, String streamingEventURI, String request) {
		Streamer streamer = new BusinessEventsStreamer(sessionName, streamingEventURI);
		processStartStreamingRequest(sessionName, request, streamer);
	}

	private static void processStartStreamingRequest(String sessionName, String request, Streamer streamer) {
		try {
			streamer.init();
			// register the streamer with the streaming registry
			StreamerRegistry.getInstance().registerStreamer(sessionName, streamer);
			int idx = request.indexOf('\0');
			if (idx != -1) {
				request = request.substring(0, idx);
			}
			if (request.indexOf("policy-file-request") != -1) {
				BizRequest requestXML = new XMLBizRequestImpl(null);
				requestXML.addParameter("command", "policy-file-request");
				// process the request
				String response = BusinessActionsController.getInstance().process(requestXML).toString();
				try {
					streamer.stream(response + '\0');
				} catch (IOException e) {
					// TODO should we throw this IOException as runtimeexception
				}
			} else {
				// generate the request for processing
				BizRequest requestXML = new XMLBizRequestImpl(request);
				// add the streamer's id
				requestXML.addParameter(Streamer.class.getName(), sessionName);
				requestXML.addParameter("command", "startstreaming");
				// process the request
				String response = BusinessActionsController.getInstance().process(requestXML).toString();
				try {
					streamer.stream(response + '\0');
				} catch (IOException e) {
					// TODO should we throw this IOException as runtimeexception
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("could not initialize business event specific streamer for " + sessionName, e);
		}
	}

	public static <T extends SimpleEvent> void startStreamingUsingEventClass(Session session, Class<T> eventClass, String request) {
		Streamer streamer = new BusinessEventsStreamer(session);
		processStartStreamingRequest(session.getId(), request, streamer);
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "stopStreaming",
        synopsis = "This function stops streaming updates",
        signature = "stopStreaming(String name,String request)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "A unique name which defines the streaming session")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Stops streaming updates",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void stopStreaming(String name, String request) {
		// generate the request for processing
		BizRequest requestXML = new XMLBizRequestImpl(request);
		// add the streamer's id
		requestXML.addParameter(Streamer.class.getName(), name);
		requestXML.addParameter("command", "stopstreaming");
		// process the request
		BusinessActionsController.getInstance().process(request);
		// unregister the streamer from the registry
		StreamerRegistry.getInstance().unregisterStreamer(name);
	}

}
