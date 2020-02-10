package com.tibco.cep.driver.soap;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 17, 2009
 * Time: 8:33:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class SoapConstants
{

    /**
     * The standard soap namespace
     */
    public static final String SOAP_NS = "http://schemas.xmlsoap.org/soap/envelope/";

    /**
     * The standard soap Envelope element
     */
    public static final String SOAP_ENVELOPE = "Envelope";

    /**
      * The standard soap Envelope name
      */
    public static final ExpandedName SOAP_ENVELOPE_NAME = ExpandedName.makeName(SOAP_NS, SOAP_ENVELOPE);

    /**
     * The standard SOAP Signature element
     */
    public static final String SOAP_SIGNATURE = "Signature";

    /**
      * The standard SOAP Signature name
      */
    public static final ExpandedName SOAP_SIGNATURE_NAME = ExpandedName.makeName(SOAP_NS, SOAP_SIGNATURE);

    /**
     * The standard soap Body element
     */
    public static final String SOAP_BODY = "Body";

    /**
     * The standard soap Body name
     */
    public static final ExpandedName SOAP_BODY_NAME = ExpandedName.makeName(SOAP_NS, SOAP_BODY);

    /**
     * The standard soap Header element
     */
    public static final String SOAP_HEADER = "Header";

    /**
     * The standard soap Header name
     */
    public static final ExpandedName SOAP_HEADER_NAME = ExpandedName.makeName(SOAP_NS, SOAP_HEADER);

    /**
     * The standard soap Actor attribute
     */
    public static final String SOAP_ACTOR = "actor";

    /**
     * The standard soap AActor name
     */
    public static final ExpandedName SOAP_ACTOR_NAME = ExpandedName.makeName(SOAP_NS, SOAP_ACTOR);

    /**
     * The standard soap mustUnderstand attribute
     */
    public static final String SOAP_MUST_UNDERSTAND = "mustUnderstand";

    /**
     * The standard soap mustUnderstand name
     */
    public static final ExpandedName SOAP_MUST_UNDERSTAND_NAME = ExpandedName.makeName(SOAP_NS, SOAP_MUST_UNDERSTAND);

    /**
     * The standard soap Fault element
     */
    public static final String SOAP_FAULT = "Fault";

    /**
     * The standard soap Fault name
     */
    public static final ExpandedName SOAP_FAULT_NAME = ExpandedName.makeName(SOAP_NS, SOAP_FAULT);

    /**
     * The standard soap Fault Code element
     */
    public static final String SOAP_FAULT_CODE = "faultcode";

    /**
     * The standard soap Fault Code name
     */
    public static final ExpandedName SOAP_FAULT_CODE_NAME = ExpandedName.makeName(SOAP_NS, SOAP_FAULT_CODE);

    /**
     * The standard soap Fault String element
     */
    public static final String SOAP_FAULT_STRING = "faultstring";

    /**
     * The standard soap Fault String name
     */
    public static final ExpandedName SOAP_FAULT_STRING_NAME = ExpandedName.makeName(SOAP_NS, SOAP_FAULT_STRING);

    /**
     * The standard soap Fault Actor element
     */
    public static final String SOAP_FAULT_ACTOR = "faultactor";

    /**
     * The standard soap Fault Actor name
     */
    public static final ExpandedName SOAP_FAULT_ACTOR_NAME = ExpandedName.makeName(SOAP_NS, SOAP_FAULT_ACTOR);

    /**
     * The standard soap Fault Detail element
     */
    public static final String SOAP_FAULT_DETAIL = "detail";

    /**
     * The standard soap Fault Detail name
     */
    public static final ExpandedName SOAP_FAULT_DETAIL_NAME = ExpandedName.makeName(SOAP_NS, SOAP_FAULT_DETAIL);

    /**
     * The <attachments> node
     */
    public static final String PAYLOAD_ATTACHMENTS = "attachments";

    /**
     * The <attachments> node name
     */
    public static final ExpandedName PAYLOAD_ATTACHMENTS_NAME = ExpandedName.makeName(PAYLOAD_ATTACHMENTS);

    public static final String PAYLOAD_ATTACHMENT = "attachment";

    public static final ExpandedName PAYLOAD_ATTACHMENT_NAME = ExpandedName.makeName(PAYLOAD_ATTACHMENT);

    /**
     * The <content> node
     */
    public static final String PAYLOAD_ATTACHMENT_CONTENT = "content";

    /**
     * The <content> node name
     */
    public static final ExpandedName PAYLOAD_ATTACHMENT_CONTENT_NAME = ExpandedName.makeName(PAYLOAD_ATTACHMENT_CONTENT);

    /**
     * The @contentType node
     */
    public static final String PAYLOAD_ATTACHMENT_CONTENT_TYPE_ATTR = "contentType";

    /**
     * The @contentType node name
     */
    public static final ExpandedName PAYLOAD_ATTACHMENT_CONTENT_TYPE_ATTR_NAME = ExpandedName.makeName(PAYLOAD_ATTACHMENT_CONTENT_TYPE_ATTR);

    /**
     * The @contentType node
     */
    public static final String PAYLOAD_ATTACHMENT_CONTENT_ID_ATTR = "contentID";

    /**
     * The @contentType node name
     */
    public static final ExpandedName PAYLOAD_ATTACHMENT_CONTENT_ID_ATTR_NAME = ExpandedName.makeName(PAYLOAD_ATTACHMENT_CONTENT_ID_ATTR);

    /**
     * The port property of the event
     */
    public static final String PROPERTY_PORT = "Port";

    /**
     * The RequestURI property of the event
     */
    public static final String PROPERTY_REQUEST_URI = "RequestURI";
    public static final String PROPERTY_REQUEST_URI_W3C = "SOAPJMS_requestURI";

    /**
     * The Method property of the event (Typically HTTP method GET/POST etc.)
     */
    public static final String PROPERTY_METHOD = "Method";

    /**
     * The SOAP Action if present
     */
    public static final String PROPERTY_SOAP_ACTION = "SoapAction";

    /**
     * The SOAP Action if present
     */
    public static final String HEADER_SOAP_ACTION = "SOAPAction";

    /**
     * The SOAP Action if present
     */
    public static final String URI_SOAP_PREFIX = "$soap$";

    /**
     * The SOAP Fault code property
     */
    public static final String PROPERTY_SOAP_FAULT_CODE = "SOAP_faultCode";

    /**
     * The SOAP Fault String property
     */
    public static final String PROPERTY_SOAP_FAULT_STRING = "SOAP_faultString";

    /**
     * The Content-Length Header
     */
    public static final String PROPERTY_CONTENT_LENGTH = "ContentLength";

    public static final String PROPERTY_SOAP_HEADER = "SOAP_Header";

    /**
     * The Mime Type property for header string
     */
    public static final String PROPERTY_SOAP_HEADER_MIME_TYPE = "SOAP_Header_MimeType";


    public static final String BINDING_VERSION = "SOAPJMS_bindingVersion";
    public static final String CONTENT_TYPE = "Content_Type";
    public static final String CONTENT_TYPE_W3C = "SOAPJMS_contentType";
    public static final String DEFAULT_CONTENT_TYPE = "application/xml; charset=utf-8";
    public static final String SOAP_ACTION = "SoapAction";
    public static final String SOAP_ACTION_TIBCO = "SoapAction";
    public static final String SOAP_ACTION_W3C = "SOAPJMS_soapAction";
    public static final ExpandedName XNAME_CONTENT = new ExpandedName("content");

    public static final String TARGET_SERVICE = "SOAPJMS_targetService";
}
