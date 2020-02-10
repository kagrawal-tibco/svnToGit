package com.tibco.cep.driver.http.serializer._retire;

import static com.tibco.cep.driver.http.HttpChannelConstants.MESSAGE_HEADER_EXTID_PROPERTY;
import static com.tibco.cep.driver.http.HttpChannelConstants.MESSAGE_HEADER_NAMESPACE_PROPERTY;
import static com.tibco.cep.driver.http.HttpChannelConstants.MESSAGE_HEADER_NAME_PROPERTY;
import static com.tibco.cep.driver.soap.SoapConstants.PROPERTY_CONTENT_LENGTH;
import static com.tibco.cep.driver.soap.SoapConstants.PROPERTY_METHOD;
import static com.tibco.cep.driver.soap.SoapConstants.PROPERTY_REQUEST_URI;
import static com.tibco.cep.driver.soap.SoapConstants.PROPERTY_SOAP_ACTION;
import static com.tibco.cep.driver.soap.SoapConstants.PROPERTY_SOAP_FAULT_CODE;
import static com.tibco.cep.driver.soap.SoapConstants.PROPERTY_SOAP_FAULT_STRING;
import static com.tibco.cep.driver.soap.SoapConstants.PROPERTY_SOAP_HEADER;
import static com.tibco.cep.driver.soap.SoapConstants.PROPERTY_SOAP_HEADER_MIME_TYPE;
import static com.tibco.cep.driver.soap.SoapConstants.SOAP_BODY;
import static com.tibco.cep.driver.soap.SoapConstants.SOAP_HEADER;
import static com.tibco.cep.driver.soap.SoapConstants.SOAP_NS;

import java.io.ByteArrayInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.tibco.cep.driver.soap.SoapUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.driver.http.HttpMethods;
import com.tibco.cep.driver.http.serializer.HTTPHeaders;
import com.tibco.cep.driver.http.serializer.RESTMessageSerializer;
import com.tibco.cep.driver.http.serializer.soap.DefaultSOAPMessageBuilder;
import com.tibco.cep.driver.soap.MimeTypes;
import com.tibco.cep.driver.http.serializer.soap.SOAPMessage;
import com.tibco.cep.driver.http.serializer.soap.SOAPMessageTransformer;
import com.tibco.cep.driver.http.server.HttpChannelServerRequest;
import com.tibco.cep.driver.http.server.HttpChannelServerResponse;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleSession;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 17, 2009
 * Time: 6:49:58 PM
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public class SOAPMessageSerializer extends RESTMessageSerializer {

    @Override
    public SimpleEvent deserialize(Object message, SerializationContext context) throws Exception {
        SimpleEvent event = null;
        //return super.deserialize(message, context);    //To change body of overridden methods use File | Settings | File Templates.
        if (message == null) {
            return null;
        }
        String s = "<soapenv:Body xmlns:soapenv=\\\"http://schemas.xmlsoap.org/soap/envelope/\\\" xmlns:add=\\\"www.tibco.com/be/ontology/EDS/Model/Concepts/EXTENDED/AddNewQuoteStatusRQ\\\">\\n\" +\n" +
                "                \"            <add:AddNewQuoteStatusRQ Id=\\\"?\\\" extId=\\\"?\\\">\\n\" +\n" +
                "                \"         <!--Optional:-->\\n\" +\n" +
                "                \"                <MessageRequest Id=\\\"?\\\" extId=\\\"?\\\">\\n\" +\n" +
                "                \"            <!--Optional:-->\\n\" +\n" +
                "                \"                    <CorrelationHandleID>?</CorrelationHandleID>\\n\" +\n" +
                "                \"            <!--Optional:-->\\n\" +\n" +
                "                \"                    <HostCarrierCode>?</HostCarrierCode>\\n\" +\n" +
                "                \"                </MessageRequest>\\n\" +\n" +
                "                \"         <!--Zero or more repetitions:-->\\n\" +\n" +
                "                \"                <QuoteGroupStatus Id=\\\"?\\\" extId=\\\"?\\\">\\n\" +\n" +
                "                \"            <!--Optional:-->\\n\" +\n" +
                "                \"                    <QUOTE_GROUP_ID>?</QUOTE_GROUP_ID>\\n\" +\n" +
                "                \"            <!--Optional:-->\\n\" +\n" +
                "                \"                    <QUOTE_STATUS_CD>?</QUOTE_STATUS_CD>\\n\" +\n" +
                "                \"            <!--Optional:-->\\n\" +\n" +
                "                \"                    <HOST_AIRLINE_CD>?</HOST_AIRLINE_CD>\\n\" +\n" +
                "                \"            <!--Optional:-->\\n\" +\n" +
                "                \"                    <QUOTE_STATUS_DT>?</QUOTE_STATUS_DT>\\n\" +\n" +
                "                \"            <!--Optional:-->\\n\" +\n" +
                "                \"                    <USER_ID>?</USER_ID>\\n\" +\n" +
                "                \"            <!--Optional:-->\\n\" +\n" +
                "                \"                    <USER_LOC>?</USER_LOC>\\n\" +\n" +
                "                \"                </QuoteGroupStatus>\\n\" +\n" +
                "                \"            </add:AddNewQuoteStatusRQ>\\n\" +\n" +
                "                \"        </soapenv:Body>\\n\"";
        //Hopefully cast will not cause problems
        HttpDestination destination = (HttpDestination) context.getDestination();
        if (destination == null) {
            return null;
        }
        RuleSession session = context.getRuleSession();
        DestinationConfig config = destination.getConfig();
        //Get the servelet request object
        //TODO make this independent of the underlying request object
        HttpChannelServerRequest request = (HttpChannelServerRequest) message;
        //Deserialize into event
        //Get namespace ns. Should be sent as query string for get request
        String ns;
        String nm;
        String extId;
        String method = request.getMethod();
        if (HttpMethods.METHOD_POST.equals(method)) {
            //Get ns, nm headers
            ns = request.getHeader(MESSAGE_HEADER_NAMESPACE_PROPERTY);
            nm = request.getHeader(MESSAGE_HEADER_NAME_PROPERTY);
            //Send external id as header for POST
            extId = request.getHeader(MESSAGE_HEADER_EXTID_PROPERTY);
            event = createEvent(ns, nm, session, config);
            if (extId != null) {
                event.setExtId(extId);
            }
            //Get the payload from the request's body
            byte[] input = getPostData(request, session);
            SOAPMessage soapMessage = parsePostData(input);
            //Set the headers string as a property
            buildRemnantEventDesiderata(event, session, soapMessage);
        }
        //TODO Do we expect GET request?
        populateEventProperties(event, request);
        String s1 = "<soapenv:Body xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:add=\"www.tibco.com/be/ontology/EDS/Model/Concepts/EXTENDED/AddNewQuoteStatusRQ\">\n" +
                "      <add:AddNewQuoteStatusRQ Id=\"?\" extId=\"?\">\n" +
                "         <!--Optional:-->\n" +
                "         <MessageRequest Id=\"?\" extId=\"?\">\n" +
                "            <!--Optional:-->\n" +
                "            <CorrelationHandleID>?</CorrelationHandleID>\n" +
                "            <!--Optional:-->\n" +
                "            <HostCarrierCode>?</HostCarrierCode>\n" +
                "         </MessageRequest>\n" +
                "         <!--Zero or more repetitions:-->\n" +
                "         <QuoteGroupStatus Id=\"?\" extId=\"?\">\n" +
                "            <!--Optional:-->\n" +
                "            <QUOTE_GROUP_ID>?</QUOTE_GROUP_ID>\n" +
                "            <!--Optional:-->\n" +
                "            <QUOTE_STATUS_CD>?</QUOTE_STATUS_CD>\n" +
                "            <!--Optional:-->\n" +
                "            <HOST_AIRLINE_CD>?</HOST_AIRLINE_CD>\n" +
                "            <!--Optional:-->\n" +
                "            <QUOTE_STATUS_DT>?</QUOTE_STATUS_DT>\n" +
                "            <!--Optional:-->\n" +
                "            <USER_ID>?</USER_ID>\n" +
                "            <!--Optional:-->\n" +
                "            <USER_LOC>?</USER_LOC>\n" +
                "         </QuoteGroupStatus>\n" +
                "      </add:AddNewQuoteStatusRQ>\n" +
                "   </soapenv:Body>";
        return event;
    }

    @Override
    protected void populateEventProperties(final SimpleEvent event,
                                           final HttpChannelServerRequest request) throws Exception {

        super.populateEventProperties(event, request);
        String[] propertyNames = event.getPropertyNames();
        //event.setProperty(PROPERTY_PORT, Integer.toString(request.getRemotePort()));
        if (SoapUtils.containsProperty(propertyNames, PROPERTY_REQUEST_URI)) {
            event.setProperty(PROPERTY_REQUEST_URI, request.getRequestURI());
        }
        if (SoapUtils.containsProperty(propertyNames, PROPERTY_METHOD)) {
            event.setProperty(PROPERTY_METHOD, request.getMethod());
        }
        if (SoapUtils.containsProperty(propertyNames, PROPERTY_SOAP_ACTION)) {
            event.setProperty(PROPERTY_SOAP_ACTION, request.getHeader(PROPERTY_SOAP_ACTION));
        }
        if (SoapUtils.containsProperty(propertyNames, PROPERTY_CONTENT_LENGTH)) {
            event.setProperty(PROPERTY_CONTENT_LENGTH, request
                        .getHeader(HTTPHeaders.CONTENT_LENGTH.value()));
        }
    }


    /**
     * Parse the incoming <code>byte[]</code> post data of this request,
     * and convert it to {@link SOAPMessage}
     * @see #parseSOAPBody(org.w3c.dom.Element, com.tibco.cep.driver.http.serializer.soap.SOAPMessage)
     * @see #parseSOAPHeader(org.w3c.dom.Element, com.tibco.cep.driver.http.serializer.soap.SOAPMessage)
     * @param postData
     * @return
     * @throws Exception
     */
    protected SOAPMessage parsePostData(byte[] postData) throws Exception {
        if (postData == null) {
            throw new IllegalArgumentException("Cannot have POST request without post data");
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.parse(new ByteArrayInputStream(postData));
        //Get the document element here
        //This presumably is the Envelope
        Element docElement = doc.getDocumentElement();
        SOAPMessage soapMessage = new SOAPMessage();
        if (docElement != null) {
            parseSOAPBody(docElement, soapMessage);
            parseSOAPHeader(docElement, soapMessage);
        }
        return soapMessage;
    }

    /**
     * This method parses <b>&lt;SOAP:Body&gt;</b> and then sets the {@link String}
     * representation of its children in this {@link SOAPMessage}
     * @param soapEnvElement
     * @param soapMessage
     * @throws Exception
     */
    private void parseSOAPBody(final Element soapEnvElement,
                               final SOAPMessage soapMessage) throws Exception {
        //Get the soap body children
        NodeList children = soapEnvElement.getElementsByTagNameNS(SOAP_NS, SOAP_BODY);
        if (children != null) {
            for (int loop = 0, length = children.getLength(); loop < length; loop++) {
                //Get the first child
                Element soapBodyElement = (Element)children.item(loop);
                //Transform contents of it to string
                transformSOAPBodyContents(soapBodyElement, soapMessage);
                break;
            }
        }
    }

    /**
     * This method parses <b>&lt;SOAP:Header&gt;</b> and then sets its {@link String}
     * representation in this {@link SOAPMessage}
     * @param soapEnvElement
     * @param soapMessage
     * @throws Exception
     */
    private void parseSOAPHeader(final Element soapEnvElement,
                                 final SOAPMessage soapMessage) throws Exception {
        //Get the soap header children
        NodeList children = soapEnvElement.getElementsByTagNameNS(SOAP_NS, SOAP_HEADER);
        //Get first child
        Element headerElement = (Element)children.item(0);
        if (headerElement == null) {
            return;
        }
        String headerString = obtainChildContents(headerElement);
        soapMessage.setSoapHeaderString(headerString);
    }

    /**
     * Transform children elements of a node to string equivalent
     * @param soapElement
     * @return {@link String} representation of children of the {@link Element}
     * @throws Exception
     */
    private String obtainChildContents(final Element soapElement) throws Exception {
        //get its children nodes
        NodeList childNodes = soapElement.getChildNodes();
        for (int loop = 0, length = childNodes.getLength(); loop < length; loop++) {
            //Get the first element child
            Node child = childNodes.item(loop);
            if (child instanceof Element) {
                Element childElement = (Element)child;
                //Transform the contents to string
                String childContents = SoapUtils.transformNode(childElement);
                return childContents;
            }
        }
        return null;
    }

    /**
     * Transform children elements of this soap body to string,
     * and set it in the {@link SOAPMessage}.
     * @param soapBodyElement
     * @param soapMessage
     * @throws Exception
     */
    private void transformSOAPBodyContents(final Element soapBodyElement,
                                           final SOAPMessage soapMessage) throws Exception {
        if (soapBodyElement == null) {
            throw new Exception("Invalid soap message. SOAP Body is mandatory");
        }
        String soapBodyContents = obtainChildContents(soapBodyElement);
        soapMessage.setSoapBodyContents(soapBodyContents);


    }


    public Object serialize(SimpleEvent event, SerializationContext context) throws Exception {
        if (context instanceof HttpDestination.HttpServerSerializationContext) {
            HttpChannelServerResponse response = ((HttpDestination.HttpServerSerializationContext) context).getResponse();
            //Transform event data into soap message
            SOAPMessage soapMessage = transformEventData(event);
            //Also set response event properties as output headers
            setResponseHeaders(event, response);
            //No need for null check
            /**
             * Build a soap response here
             */
            //TODO This probably can come through a factory
            SOAPMessageTransformer transformer =
                    new SOAPMessageTransformer<DefaultSOAPMessageBuilder>(soapMessage, new DefaultSOAPMessageBuilder());
            Document soapDoc = transformer.buildSOAPMessage();
            //Write this to the response writer
            SoapUtils.writeSOAP(soapDoc, response.getWriter());
            response.sendResponse();
        }
        return null;
    }


    /**
     * Take an input <b>SimpleEvent</b> and transform it to a
     * {@link SOAPMessage}
     * @param event
     * @return the transformed {@link SOAPMessage}
     * @throws Exception
     */
    private SOAPMessage transformEventData(final SimpleEvent event) throws Exception {
        SOAPMessage soapMessage = new SOAPMessage();
        //Check if there is a fault code/string etc.
        processFaultDetails(event, soapMessage);
        processPayload(event, soapMessage);
        processHeader(event, soapMessage);
        return soapMessage;
    }

    private void setResponseHeaders(final SimpleEvent event,
                                    final HttpChannelServerResponse response) throws Exception {
        //Set only non-soap properties
        String pattern = "SOAP_(.*)";
        Pattern p = Pattern.compile(pattern);
        //Set Content-Type header
        response.setHeader(HTTPHeaders.CONTENT_TYPE.value(), MimeTypes.XML_TEXT.getLiteral());
        for (String property : event.getPropertyNames()) {
            //If property starts with SOAP, ignore it
            Matcher matcher = p.matcher(property);
            if (!matcher.matches()) {
                Object propValue = event.getProperty(property);
                if (propValue != null) {
                    response.setHeader(property, propValue.toString());
                }
            }
        }
    }

    /**
     * Process Fault Details of the event. These can be determined
     * by checking <b>SOAP_faultCode</b> property.
     * @see com.tibco.cep.driver.soap.SoapConstants#PROPERTY_SOAP_FAULT_CODE
     * @param event
     * @param soapMessage
     * @throws Exception
     */
    private void processFaultDetails(final SimpleEvent event,
                                     final SOAPMessage soapMessage) throws Exception {
        String[] propertyNames = event.getPropertyNames();
        //Check for property named "faultCode"
        String faultCode =
                (SoapUtils.containsProperty(propertyNames, PROPERTY_SOAP_FAULT_CODE)) ?
                       (String)event.getProperty(PROPERTY_SOAP_FAULT_CODE) : null;
        //If faultcode is null, then no need to check the rest
        if (faultCode == null) {
            return;
        }
        soapMessage.setFaultCode(faultCode);
        //If code is not null, then fault string is mandatory
        String faultString =
                (SoapUtils.containsProperty(propertyNames, PROPERTY_SOAP_FAULT_STRING)) ?
                       (String)event.getProperty(PROPERTY_SOAP_FAULT_STRING) : null;
        if (faultString == null) {
            throw new Exception("Fault Code has to be accompanied with a Fault String");
        }
        soapMessage.setFaultString(faultString);
        //This is preseumably the fault event.
        //Check its payload. That should be the detail
        String eventPayload = event.getPayloadAsString();
        soapMessage.setFaultDetail(eventPayload);
    }

    /**
     * Process payload of this event.
     * <p>
     * A succesful processing will result in this payload directly being attached to
     * <b>&lt;soap:Body&gt;</b>, else payload is <b>&lt;soap:detail&gt;</b>
     * of a <b>&lt;soap:Fault&gt;</b>.
     * </p>
     * @param event
     * @param soapMessage
     * @throws Exception
     */
    private void processPayload(final SimpleEvent event,
                                final SOAPMessage soapMessage) throws Exception {
        String payload = event.getPayloadAsString();
        if (soapMessage.getFaultCode() == null) {
            //Set this as soap body contents string
            soapMessage.setSoapBodyContents(payload);
        }
    }

    private void processHeader(final SimpleEvent event,
                               final SOAPMessage soapMessage) throws Exception {
        String headerString =
                (SoapUtils.containsProperty(event.getPropertyNames(), PROPERTY_SOAP_HEADER)) ?
                       (String)event.getProperty(PROPERTY_SOAP_HEADER) : null;
        String headerMimeType =
                (SoapUtils.containsProperty(event.getPropertyNames(), PROPERTY_SOAP_HEADER_MIME_TYPE)) ?
                       (String)event.getProperty(PROPERTY_SOAP_HEADER_MIME_TYPE) : null;
        soapMessage.setHeaderMimeType(headerMimeType);
        soapMessage.setSoapHeaderString(headerString);
    }

    /**
     * Set the payload on this event which presently is all the contents of the
     * <b>&lt;SOAP:Body&gt;</b>, and the <b>SOAPHeader</b> property.
     * @param event
     * @param session
     * @param soapMessage
     * @throws Exception
     */
    private void buildRemnantEventDesiderata(final SimpleEvent event,
                                             final RuleSession session,
                                             final SOAPMessage soapMessage) throws Exception {
        if (soapMessage == null) {
            throw new IllegalArgumentException("Parameter is null.Error creating soap payload from post data");
        }
        //Get the body contents
        String soapBodyContents = soapMessage.getSoapBodyContents();
        deserializePayload(event, session, soapBodyContents, false);
        String soapHeaderString = soapMessage.getSoapHeaderString();
        if (SoapUtils.containsProperty(event.getPropertyNames(),
                PROPERTY_CONTENT_LENGTH)) {
            event.setProperty(PROPERTY_SOAP_HEADER, soapHeaderString);
        }
        
    }
}