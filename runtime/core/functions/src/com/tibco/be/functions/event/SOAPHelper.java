package com.tibco.be.functions.event;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.BUI;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;
import static com.tibco.be.model.functions.FunctionDomain.QUERY;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.tibco.be.model.functions.Enabled;
import com.tibco.be.model.rdf.XiNodeBuilder;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.driver.http.server.utils.SSLUtils;
import com.tibco.cep.driver.soap.EventNameRegistry;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.PayloadFactory;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.XiNodePayload;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.security.BETrustedCertificateManager;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.security.Cert;
import com.tibco.security.TrustedCerts;
import com.tibco.xml.XiNodeUtilities;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.data.primitive.XmlNodeTest;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.navigation.NameNodeTest;
import com.tibco.xml.datamodel.navigation.NodeKindNodeTest;
import com.tibco.xml.xpath.pattern.filter.NameNodeKindXmlNodeTest;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "SOAP",
        synopsis = "SOAP Helper functions")
public class SOAPHelper {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    private static @interface AppliesTo {

        String[] value();
    }

    public static final String SOAP_NS = "http://schemas.xmlsoap.org/soap/envelope/";

    /**
     * The standard SOAP Envelope element
     */
    public static final String SOAP_ENVELOPE = "Envelope";

    /**
      * The standard SOAP Envelope name
      */
    public static final ExpandedName SOAP_ENVELOPE_NAME = ExpandedName.makeName(SOAP_NS, SOAP_ENVELOPE);

    /**
     * The standard SOAP Body element
     */
    public static final String SOAP_BODY = "Body";

    /**
     * The standard SOAP Body name
     */
    public static final ExpandedName SOAP_BODY_NAME = ExpandedName.makeName(SOAP_NS, SOAP_BODY);

    /**
     * The standard SOAP Header element
     */
    public static final String SOAP_HEADER = "Header";

    /**
     * The standard SOAP Header name
     */
    public static final ExpandedName SOAP_HEADER_NAME = ExpandedName.makeName(SOAP_NS, SOAP_HEADER);

    /**
     * The standard SOAP Actor attribute
     */
    public static final String SOAP_ACTOR = "actor";

    /**
     * The standard SOAP Actor attribute
     */
    public static final String SOAP_ROLE = "role";

    /**
     * The standard SOAP Actor name
     */
    public static final ExpandedName SOAP_ACTOR_NAME = ExpandedName.makeName(SOAP_NS, SOAP_ACTOR);

    /**
     * The standard SOAP Actor name
     */
    public static final ExpandedName SOAP_ROLE_NAME = ExpandedName.makeName("http://www.w3.org/2003/05/soap-envelope", SOAP_ROLE);

    /**
     * The standard SOAP mustUnderstand attribute
     */
    public static final String SOAP_MUST_UNDERSTAND = "mustUnderstand";

    /**
     * The standard SOAP mustUnderstand name
     */
    public static final ExpandedName SOAP_MUST_UNDERSTAND_NAME = ExpandedName.makeName(SOAP_NS, SOAP_MUST_UNDERSTAND);

    /**
     * The standard SOAP Fault element
     */
    public static final String SOAP_FAULT = "Fault";

    /**
     * The standard SOAP Fault name
     */
    public static final ExpandedName SOAP_FAULT_NAME = ExpandedName.makeName(SOAP_NS, SOAP_FAULT);

    /**
     * The standard SOAP Fault Code element
     */
    public static final String SOAP_FAULT_CODE = "faultcode";

    /**
     * The standard SOAP Fault Code name
     */
    public static final ExpandedName SOAP_FAULT_CODE_NAME = ExpandedName.makeName(SOAP_FAULT_CODE);

    /**
     * The standard SOAP Fault String element
     */
    public static final String SOAP_FAULT_STRING = "faultstring";

    /**
     * The standard SOAP Fault String name
     */
    public static final ExpandedName SOAP_FAULT_STRING_NAME = ExpandedName.makeName(SOAP_FAULT_STRING);

    /**
     * The standard SOAP Fault Actor element
     */
    public static final String SOAP_FAULT_ACTOR = "faultactor";

    /**
     * The standard SOAP Fault Actor name
     */
    public static final ExpandedName SOAP_FAULT_ACTOR_NAME = ExpandedName.makeName(SOAP_FAULT_ACTOR);

    /**
     * The standard SOAP Fault Detail element
     */
    public static final String SOAP_FAULT_DETAIL = "detail";

    /**
     * The standard SOAP Fault Detail name
     */
    public static final ExpandedName SOAP_FAULT_DETAIL_NAME = ExpandedName.makeName(SOAP_FAULT_DETAIL);

    /**
     * The <attachments> node
     */
    public static final String PAYLOAD_ATTACHMENTS = "attachments";

    /**
     * The <attachments> node name
     */
    public static final ExpandedName PAYLOAD_ATTACHMENTS_NAME = ExpandedName.makeName(PAYLOAD_ATTACHMENTS);

     /**
     * The <attachment> node
     */
    public static final String PAYLOAD_ATTACHMENT = "attachment";

    /**
     * The <attachment> node name
     */
    public static final ExpandedName PAYLOAD_ATTACHMENT_NAME = ExpandedName.makeName(PAYLOAD_ATTACHMENT);

     /**
     * The <message> node
     */
    public static final String PAYLOAD_MESSAGE = "message";

    /**
     * The <message> node name
     */
    public static final ExpandedName PAYLOAD_MESSAGE_NAME = ExpandedName.makeName(PAYLOAD_MESSAGE);

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


    @com.tibco.be.model.functions.BEFunction(
        name = "getHeaders",
        signature = "String[] getHeaders(SimpleEvent soapEvent, String actor, boolean removeHeaders)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "The request SOAP event"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "actor", type = "String", desc = "The actor attribute. Use null to get all headers"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "removeHeaders", type = "boolean", desc = "from the SOAP headers. Setting this parameter value to true will remove the header.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = ""),
        version = "4.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets a list of headers inside the &lt;Header&gt; element of a SOAP message.\n<p>\nThe actor value of null means all immediate children of the SOAP header are retrieved.\n</p>\n<p>\nA non-null actor means only those headers having <b>SOAP actor</b>\nattribute same as this actor are retrieved.\n</p>",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )

    @AppliesTo("deserializer")
    public static String[] getHeaders (SimpleEvent soapEvent,
                                       String actor,
                                       boolean removeHeaders) {
        XiNode headerNode = getHeaderNode(soapEvent);
        String[] headerChildren = null;

        if (headerNode != null) {

            List<XiNode> headerChildrenNodes =
                    getHeadersInternal(headerNode, actor, removeHeaders);

            headerChildren = new String[headerChildrenNodes.size()];
            int counter = 0;
            for (XiNode headerChild : headerChildrenNodes) {
                headerChildren[counter++] = XiNodeUtilities.toString(headerChild);
            }
        }
        return headerChildren;
    }

    /**
     * Returns an {@linkplain XiNode} representing the standard SOAP header
     * @param soapEvent
     * @return {@linkplain XiNode}
     */
    @AppliesTo("deserializer")
    private static XiNode getHeaderNode (SimpleEvent soapEvent) {
        XiNode envelopeNode = getEnvelopeNode(soapEvent);

        if (envelopeNode == null) {
            throw new RuntimeException("Envelope not found");
        }
        //Get the <header> node

        return XiChild.getChild(envelopeNode, SOAP_HEADER_NAME);
    }

    /**
     *
     * @param headerNode
     * @param actor
     * @param removeHeaders
     * @return {@linkplain List}
     */
    @AppliesTo("deserializer")
    private static List<XiNode> getHeadersInternal(XiNode headerNode,
                                                   String actor,
                                                   boolean removeHeaders) {

        List<XiNode> headerChildren = new ArrayList<XiNode>();
        //Get all children first
        Iterator<XiNode> children = XiChild.getIterator(headerNode);

        List<ExpandedName> toRemove = new ArrayList<ExpandedName>();

        while (children.hasNext()) {
            XiNode headerChild = children.next();
            boolean canRemove =
                    addHeader(headerChildren, headerChild , actor);
            if (canRemove && removeHeaders) {
                toRemove.add(headerChild.getName());
            }
        }
        //Remove all those to be removed
        for (ExpandedName name : toRemove) {
            XiNode toRemoveChild = XiChild.getChild(headerNode, name);
            //Remove it
            headerNode.removeChild(toRemoveChild);
        }

        return headerChildren;
    }

    /**
     * Adds a header element to the list if it matches the actor
     * @param headerChildren
     * @param child
     * @param actor
     * @return true if header added can be removed from its parent
     */
    @AppliesTo("deserializer")
    private static boolean addHeader(List<XiNode> headerChildren,
                                     XiNode child,
                                     String actor) {
        if (actor == null) {
            headerChildren.add(child);
            //Null actor nodes may not be removed
            return true;
        }
        //Get actor attribute on this child node

        XiNode actorNode = child.getAttribute(SOAP_ACTOR_NAME);
        if(actorNode == null)
        	actorNode = child.getAttribute(SOAP_ROLE_NAME);
        String actorValue;
        if (actorNode != null) {
            actorValue = actorNode.getStringValue();
            if (actorValue != null) {
                //If actor attribute is found, check if this actor is same
                if (actorValue.equals(actor)) {
                    headerChildren.add(child);
                    //As per  SOAP spec, the header with matching actor should be removed
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the root node for the event payload.
     * <p>
     * The payload is of type {@linkplain XiNodePayload}
     * @param soapEvent
     * @return {@linkplain XiNode}
     */
    @AppliesTo({"deserializer", "serializer"})
    private static XiNode getPayloadRootNode(SimpleEvent soapEvent) {

        if (soapEvent == null) {
            throw new IllegalArgumentException("Input event cannot be null");
        }
        //Get payload
        EventPayload eventPayload = soapEvent.getPayload();

        if (!(eventPayload instanceof XiNodePayload)) {
            throw new RuntimeException("Event payload is not an XiNode");
        }
        //Get the <Header> node
        XiNodePayload xiNodePayload = (XiNodePayload)eventPayload;
        //Get root <payload> element
        return xiNodePayload.getNode();
    }

    /**
     * Returns the &lt;message&gt; node which is top child in the
     * payload hierarchy.
     * @param soapEvent
     * @return {@linkplain XiNode}
     */
    @AppliesTo({"deserializer", "serializer"})
    private static XiNode getContainerNode(SimpleEvent soapEvent) {
        //Get payload node from the event
        XiNode messageNode = getPayloadRootNode(soapEvent);

        if (messageNode == null) {
            throw new RuntimeException("Invalid payload format");
        }

        /*XiNode messageChild = messageNode.getRootNode().getFirstChild();
        //Check for null
        if (messageChild == null) {
            throw new RuntimeException("Invalid payload format");
        }

        return messageChild;*/
        return messageNode;
    }

//    /**
//     * @.name getActor
//     * @.synopsis Gets the value of the actor attribute for the header
//     * at the specified index in the {@linkplain List} passed in the
//     * allHeadersObject.
//     *
//     * <p>
//     * The list can be obtained using getHeaders catalog function.
//     * </p>
//     *
//     * @.signature String getActor (SimpleEvent soapEvent,
//                                    int index)
//     * @param soapEvent SimpleEvent The SOAP Event
//     * @param index int The index of the header required which should be in the range of the list.
//     * @return String the value of "@actor" if the actor attribute is set for this XiNode
//     * @see #getHeaders(com.tibco.cep.runtime.model.event.SimpleEvent, String, boolean)
//     */
//    @AppliesTo("deserializer")
//    public static String getActor (SimpleEvent soapEvent,
//                                   int index) {
//        //Get the root <header>
//        XiNode headerNode = getHeaderNode(soapEvent);
//
//        //Get all its children
//        List<XiNode> headerChildren = getHeadersInternal(headerNode, null, false);
//
//        int length = headerChildren.size();
//
//        if (length == 0) {
//			throw new RuntimeException(
//					" SOAP Message does not have any headers");
//		}
//        if (index >= length) {
//            throw new IllegalArgumentException("Number of headers is lesser than index");
//        }
//        if (index < 0) {
//            throw new IllegalArgumentException("Index value cannot be negative");
//        }
//        XiNode headerChildNode = headerChildren.get(index);
//
//        return getActor(headerChildNode);
//    }

//   /**
//     * @.name getMustUnderstand
//     * @.synopsis Gets the value of the mustUnderstand attribute for the header
//     * at the specified index in the Simple event
//     *
//     * <p>
//     * The list can be obtained using getHeaders catalog function.
//     * </p>
//     *
//     * @.signature boolean getMustUnderstand (SimpleEvent soapEvent,
//                                             int index)
//     * @param soapEvent SimpleEvent The SOAP Event
//     * @param index int The index of the header required which should be in the range of the list.
//     * @return boolean the value of "@mustUnderstand" if the mustUnderstand attribute is set for this XiNode
//     * @see #getHeaders(com.tibco.cep.runtime.model.event.SimpleEvent, String, boolean)
//     */
//    @AppliesTo("deserializer")
//    public static boolean getMustUnderstand (SimpleEvent soapEvent,
//                                             int index) {
//        //Get the root <header>
//        XiNode headerNode = getHeaderNode(soapEvent);
//
//        //Get all its children
//        List<XiNode> headerChildren = getHeadersInternal(headerNode, null, false);
//
//        int length = headerChildren.size();
//
//        if (length == 0) {
//			throw new RuntimeException(
//					" SOAP Message does not have any headers");
//		}
//
//        if (index > length) {
//            throw new IllegalArgumentException("Number of headers is lesser than index");
//        }
//        if (index < 0) {
//            throw new IllegalArgumentException("Index value cannot be negative");
//        }
//
//        XiNode headerChildNode = headerChildren.get(index);
//        //Get its must understand value
//
//        XiNode mustUnderstandNode =
//                headerChildNode.getAttribute(SOAP_MUST_UNDERSTAND_NAME);
//
//        if (mustUnderstandNode == null) {
//            return false;
//        }
//        //If found get its value
//        String mustUnderstandVal = mustUnderstandNode.getStringValue();
//        //Numeric value is also acceptable
//        if ("1".equals(mustUnderstandVal) == true
//              || Boolean.parseBoolean(mustUnderstandVal)) {
//            return true;
//        }
//        return false;
//    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getSOAPHeaderAttribute",
        signature = "String getSOAPHeaderAttribute (SimpleEvent soapEvent, int index, String attribute)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "The SOAP Event"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index of the header required which should be in the range of the list."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "attribute", type = "String", desc = "Name of the header attribute")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The value of the specified SOAP header attribute"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the given attribute for the header part\nat the specified index in header body of the Simple event",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    @AppliesTo("deserializer")
    public static String getSOAPHeaderAttribute (SimpleEvent soapEvent,
                                             int index, String attribute) {
        //Get the root <header>
        XiNode headerNode = getHeaderNode(soapEvent);

        //Get all its children
        List<XiNode> headerChildren = getHeadersInternal(headerNode, null, false);

        int length = headerChildren.size();

        if (length == 0) {
			throw new RuntimeException(
					" SOAP Message does not have any headers");
		}

        if (index > length) {
            throw new IllegalArgumentException("Number of headers is lesser than index");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Index value cannot be negative");
        }
        if (attribute == null) {
            throw new IllegalArgumentException("attribute name can not be negative");
        }

        XiNode headerChildNode = headerChildren.get(index);
        //Get its must understand value

        XiNode attributeNode =
                headerChildNode.getAttribute(ExpandedName.makeName(SOAP_NS, attribute));

        if (attributeNode == null) {
			if (attribute.trim().equalsIgnoreCase(SOAP_MUST_UNDERSTAND))
				return Boolean.toString(false);
			else
				return null;
		}
        //If found get its value
        String attrVal = attributeNode.getStringValue();
        if(attribute.trim().equalsIgnoreCase(SOAP_MUST_UNDERSTAND)){
            //Numeric value is also acceptable for mustunderstand
            if ("1".equals(attrVal) == true
                  || Boolean.parseBoolean(attrVal)) {
                return Boolean.toString(true);
            }
            else
            	return Boolean.toString(false);
        }

        return attrVal;
    }

    /**
     * Internal method to retrieve the actor value for this header child
     * @param headerNode -> The header child
     * @return the actor value
     */
    private static String getActor(XiNode headerNode) {
        XiNode actorNode = headerNode.getAttribute(SOAP_ACTOR_NAME);

        if (actorNode == null)
        	actorNode = headerNode.getAttribute(SOAP_ROLE_NAME);
        String actorValue = null;

        if (actorNode != null) {
            actorValue = actorNode.getStringValue();
        }
        return actorValue;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getEnvelope",
        signature = "String getEnvelope (SimpleEvent soapEvent)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "The request SOAP event")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The matching a SOAP envelope"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the standard SOAP envelope xml node from the\npayload of this SOAP event.",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    @AppliesTo("deserializer")
    public static String getEnvelope (SimpleEvent soapEvent) {
        XiNode envelopeNode = getEnvelopeNode(soapEvent);

        if (envelopeNode != null) {
            return XiNodeUtilities.toString(envelopeNode);
        }
        return null;
    }

    /**
     *
     * @param soapEvent
     * @return {@linkplain XiNode}
     */
    private static XiNode getEnvelopeNode (SimpleEvent soapEvent) {
        XiNode messageNode = getContainerNode(soapEvent);

        //Get its first child which will be <Envelope>
        XiNode envelopeNode =
                XiChild.getChild(messageNode, SOAP_ENVELOPE_NAME);

        return envelopeNode;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getBody",
        signature = "String getBody (SimpleEvent soapEvent)",
        enabled = @Enabled(value=false),
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "The SOAP event")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The XML fragment of SOAP &lt;Body&gt;"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the SOAP &lt;Body&gt; XML from the payload of this SOAP event.",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    @AppliesTo("deserializer")
    public static String getBody (SimpleEvent soapEvent) {
        //Get envelope Node
        XiNode envelopeNode = getEnvelopeNode(soapEvent);

        if (envelopeNode == null) {
            throw new RuntimeException("Envelope not found");
        }
        //Get the <body> node

        XiNode body =
                XiChild.getChild(envelopeNode, SOAP_BODY_NAME);

        if (body == null) {
            throw new RuntimeException("Body absent in incoming SOAP message");
        }
        return XiNodeUtilities.toString(body);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getBodyPartAtIndex",
        signature = "String getBodyPartAtIndex (SimpleEvent event, int index)",
        enabled = @Enabled(value=false),
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "The SOAP event"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "Body part at this index will be returned.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "XiNode", desc = "for the Body part at the index. null if index out of bound"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets string form of a body part lying at position = index in the\nbody's children",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static String getBodyPartAtIndex (SimpleEvent event,
                                             int index) {
        //Get envelope Node
        XiNode envelopeNode = getEnvelopeNode(event);

        if (envelopeNode == null) {
            throw new RuntimeException("Envelope not found");
        }
        //Get the <body> node

        XiNode body =
                XiChild.getChild(envelopeNode, SOAP_BODY_NAME);

        if (body == null) {
            throw new RuntimeException("Body absent in incoming SOAP message");
        }
        XiNode childAtIndex = XiNodeUtilities.getNthChild(body, index);

        return (childAtIndex != null) ? XiNodeUtilities.toString(childAtIndex) : null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getSOAPBodyParts",
        signature = "String[] getSOAPBodyParts (SimpleEvent soapEvent, String name, String namespace)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "The request SOAP event"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "The local name of the part."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "namespace", type = "String", desc = "The namespace of the body part.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "An array of matching SOAP body parts."),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the SOAP &lt;Body&gt; parts matching the name and namespace from the SOAP event",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = "<p>\ngetSOAPBodyParts(event, $1localname$1, $1http://abc.com/namespace$1) will match all children of body\nwith this name, and namespace\n</p>"
    )
    public static String[] getSOAPBodyParts(SimpleEvent soapEvent,
                                            String name,
                                            String namespace) {
        if (name == null || namespace == null) {
            throw new IllegalArgumentException("Name, and namespace parameters cannot be null");
        }
        //Get envelope Node
        XiNode envelopeNode = getEnvelopeNode(soapEvent);

        if (envelopeNode == null) {
            throw new RuntimeException("Envelope not found");
        }
        //Get the <body> node

        XiNode body =
                XiChild.getChild(envelopeNode, SOAP_BODY_NAME);

        if (body == null) {
            throw new RuntimeException("Body absent in incoming SOAP message");
        }
        //Create an Expanded Name
        ExpandedName exName = ExpandedName.makeName(namespace, name);
        //Create a Node Test
        XmlNodeTest nodeTest = new NameNodeTest(exName);

        ArrayList<String> list = new ArrayList<String>();
        Iterator<XiNode> children = body.getChildren(nodeTest);
        while (children.hasNext()) {
            String xmlStr = XiNodeUtilities.toString(children.next());
            list.add(xmlStr);
        }

        return list.toArray(new String[list.size()]);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getAllSOAPBodyParts",
        signature = "String[] getAllSOAPBodyParts (SimpleEvent soapEvent)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "The request SOAP event")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets all SOAP &lt;Body&gt; parts from the SOAP event",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static String[] getAllBodyParts (SimpleEvent soapEvent) {
        //Get envelope Node
        XiNode envelopeNode = getEnvelopeNode(soapEvent);

        if (envelopeNode == null) {
            throw new RuntimeException("Envelope not found");
        }
        //Get the <body> node

        XiNode body =
                XiChild.getChild(envelopeNode, SOAP_BODY_NAME);

        if (body == null) {
            throw new RuntimeException("Body absent in incoming SOAP message");
        }
        ArrayList<String> list = new ArrayList<String>();
        XiNode child = body.getFirstChild();
        while (child != null) {
            if (child.getNodeKind() == XmlNodeKind.ELEMENT) {
                String xmlStr = XiNodeUtilities.toString(child);
                list.add(xmlStr);
            }
            child = child.getNextSibling();
        }

        return list.toArray(new String[list.size()]);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getFault",
        signature = "String getFault (SimpleEvent soapEvent)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "The request SOAP event")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "matching a SOAP fault"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the SOAP &lt;Fault&gt; element from the SOAP event",
        cautions = "The fault is located only in the body and not anywhere else",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    @AppliesTo("deserializer")
    public static String getFault (SimpleEvent soapEvent) {
        XiNode faultNode = getFaultXiNode(soapEvent);

        if (faultNode != null) {
            return XiNodeUtilities.toString(faultNode);
        }
        return null;
    }

    /**
     * Get an {@linkplain XiNode} representing &lt;Fault&gt;
     * element inside the body of soap
     * @param soapEvent
     * @return {@linkplain XiNode}
     */
    private static XiNode getFaultXiNode(SimpleEvent soapEvent) {
        //Get Body. Assuming that fault is part of body
        XiNode envelopeNode = getEnvelopeNode(soapEvent);

        if (envelopeNode == null) {
            throw new RuntimeException("Envelope not found");
        }
        //Get the <body> node

        XiNode bodyNode =
                XiChild.getChild(envelopeNode, SOAP_BODY_NAME);

        //Get its <fault> child
        XiNode faultNode =
                XiChild.getChild(bodyNode, SOAP_FAULT_NAME);

        return faultNode;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getFaultCode",
        signature = "String getFaultCode (SimpleEvent soapEvent)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "The request SOAP event")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The matching fault code"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the &lt;faultcode&gt; from the SOAP event.",
        cautions = "The fault is located only in the body and not anywhere else",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    @AppliesTo("deserializer")
    public static String getFaultCode (SimpleEvent soapEvent) {
        XiNode faultXiNode = getFaultXiNode(soapEvent);
        return getFaultElementValue(faultXiNode, SOAP_FAULT_CODE_NAME, "<faultcode>");
    }

    /**
     * A generic method to return string values of fault node children
     * @param faultNodeXiNode
     * @param elementName
     * @param elementToRetreive
     * @return String
     *
     * @comment Not exposed to function catalog
     *
     */
    @AppliesTo("deserializer")
    private static String getFaultElementValue (XiNode faultNodeXiNode,
                                                ExpandedName elementName,
                                                String elementToRetreive) {
        //Putting a null check here even though the below check should have sufficed
        //because for a null we should return null.
        if (faultNodeXiNode == null) {
            return null;
        }
        //Get its fault code child
        XiNode faultCodeNode = XiChild.getChild(faultNodeXiNode, elementName);

        if (faultCodeNode == null) {
            throw new RuntimeException(elementToRetreive + " absent on the <fault>");
        }
        return faultCodeNode.getStringValue();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getFaultString",
        signature = "String getFaultString (SimpleEvent soapEvent)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "The request SOAP event")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "matching fault String"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the &lt;faultstring&gt; from the SOAP event.",
        cautions = "The fault is located only in the body and not anywhere else",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    @AppliesTo("deserializer")
    public static String getFaultString (SimpleEvent soapEvent) {
        XiNode faultXiNode = getFaultXiNode(soapEvent);
        return getFaultElementValue(faultXiNode, SOAP_FAULT_STRING_NAME, "<faultstring>");
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getFaultActor",
        signature = "String getFaultActor (SimpleEvent soapEvent)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "The request SOAP event")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "matching &lt;faultactor&gt; value"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the &lt;faultactor&gt; from the SOAP event.",
        cautions = "The fault is located only in the body and not anywhere else",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    @AppliesTo("deserializer")
    public static String getFaultActor (SimpleEvent soapEvent) {
        XiNode faultXiNode = getFaultXiNode(soapEvent);
        return getFaultElementValue(faultXiNode, SOAP_FAULT_ACTOR_NAME, "<faultactor>");
    }
    
    /**
     *
     * @param faultNode
     * @return array of fault parts as XiNodes
     *
     * @comment Not exposed to function catalog
     */
    public static XiNode[] getFaultParts (XiNode faultNode) {
        return null;
    }

    /**
     * @param event SOAP event
     * @param index
     * @return Object XML fragment of the fault part
     * @comment Not exposed to function catalog
     */
    public static XiNode getFaultPart (SimpleEvent event, int index) {
        return null;
    }

    /**
     * Gets SOAP Fault
     * @param event SOAP event
     * @param namespace Fault node for this namespace will be returned
     * @return XiNode of Fault part with this namespace
     * @comment Not exposed to function catalog
     */
    public static XiNode getFaultPart (SimpleEvent event, String namespace) {
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "addHeaderPart",
        signature = "void addHeaderPart (SimpleEvent soapEvent, String headerPartXml)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "SOAP event"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "headerPartXml", type = "String", desc = "XML string of header to set")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Adds a header fragment as a child of the standard SOAP header.\nThis fragment should be a well formed XML.\n<p>\nThe actor, and mustUnderstand attribute values are added for the header element.\n</p>",
        cautions = "",
        fndomain = {ACTION, BUI},
        example = ""
    )
    @AppliesTo("serializer")
    public static void addHeaderPart (SimpleEvent soapEvent,
                                      String headerPartXml) {
        //Get envelope node
        XiNode envelopenode = createEnvelopeNode(soapEvent);
        //Check if it has a <Header> already
        XiNode headerNode = XiChild.getChild(envelopenode, SOAP_HEADER_NAME);
        if (headerNode == null) {
        	headerNode = addSOAPHeader(soapEvent);
        }
        //Convert XML to XiNode
        if (headerPartXml == null) {
            throw new IllegalArgumentException("XML string cannot be null");
        }

        try {
        	ByteArrayInputStream bis = new ByteArrayInputStream(headerPartXml.getBytes("UTF-8"));
            XiNode headerPart = XiNodeBuilder.parse(bis);
            if (headerPart != null) {
                XiNode headerChild = headerPart.getRootNode().getFirstChild();
                headerNode.appendChild(headerChild);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates an empty SOAP Header Node, if it does not exist already.
     * @param soapEvent
     * @return an {@linkplain XiNode}
     */
    @AppliesTo("serializer")
    private static XiNode addSOAPHeader(SimpleEvent soapEvent) {
    	//Get envelope node
        XiNode envelopenode = createEnvelopeNode(soapEvent);
        //Check if it has a <Header> already
        XiNode headerNode = XiChild.getChild(envelopenode, SOAP_HEADER_NAME);
        if (headerNode == null) {
            // Create an empty SOAP Header Node
            try {
                headerNode = XiSupport.getXiFactory().createElement(SOAP_HEADER_NAME);
                //Check if there is a body node here because the <header> has to be added
                //before the <body> irrespective of what sequence the functions are called in.
                XiNode bodyNodeEx = XiChild.getChild(envelopenode, SOAP_BODY_NAME);
                if (bodyNodeEx != null) {
                    //Add header before this node
                    envelopenode.insertBefore(headerNode, bodyNodeEx);
                } else {
                    envelopenode.appendChild(headerNode);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return headerNode;
    }

    /**
     * Create an XiNode for the standard SOAP envelope
     * @param soapEvent
     * @return an {@linkplain XiNode} representing a SOAP envelope
     */
    @AppliesTo("serializer")
    private static XiNode createEnvelopeNode(SimpleEvent soapEvent) {

        XiNode messageNode = createPayloadContainerNode(soapEvent);

        //Create an envelope node if not present
        XiNode envelopeNode =
                XiChild.getChild(messageNode, SOAP_ENVELOPE_NAME);

        if (envelopeNode == null) {
            //Create an add it to message
            try {
                envelopeNode =
                        XiSupport.getXiFactory().createElement(SOAP_ENVELOPE_NAME);

                messageNode.appendChild(envelopeNode);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return envelopeNode;
    }

    /**
     * Creates the root element of the event payload is one does not exist already.
     * @param soapEvent
     * @return an {@linkplain XiNode} matching the &lt;message&gt;
     */
    @AppliesTo("serializer")
    private static XiNode createPayloadContainerNode(SimpleEvent soapEvent) {
        //Get payload
        //It is presumed that the payload created will be null
        XiNodePayload payload = (XiNodePayload)soapEvent.getPayload();

        XiNode messageNode;

        BEClassLoader classLoader =
                (BEClassLoader)RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader();

        PayloadFactory payloadFactory = classLoader.getPayloadFactory();
        if (payload == null) {
            try {
                //Create the root node then
                messageNode = XiSupport.getXiFactory().createElement(ExpandedName.makeName(soapEvent.getExpandedName().getNamespaceURI(), PAYLOAD_MESSAGE));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            payload = (XiNodePayload)payloadFactory.createPayload(soapEvent.getExpandedName(), messageNode);
            soapEvent.setPayload(payload);
        } else {
            messageNode = payload.getNode();
        }
        return messageNode;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "removeHeaderPart",
        signature = "void removeHeaderPart (SimpleEvent soapEvent, String namespace, String name)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "SOAP event"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "namespace", type = "String", desc = "part with this namespace to be removed."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "part with this element name to be removed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Removes 0 or more children of a SOAP &lt;Header&gt; of a SOAP message.",
        cautions = "",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void removeHeaderPart (SimpleEvent soapEvent,
                                         String namespace,
                                         String name) {
        if (name == null || namespace == null) {
            throw new IllegalArgumentException("Name and Namespace parameters cannot be null");
        }
        //Get <header> node
        XiNode headerNode = getHeaderNode(soapEvent);
        //Get all children
        if (headerNode != null) {
            //Create an Expanded Name for this
            ExpandedName exname = ExpandedName.makeName(namespace, name);

            XmlNodeTest nodeTest =
                    new NameNodeKindXmlNodeTest(exname, XmlNodeKind.ELEMENT);

            Iterator<XiNode> children = headerNode.getChildren(nodeTest);

            while (children.hasNext()) {
                XiNode headerChild = children.next();
                //Remove it
                headerNode.removeChild(headerChild);
            }
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "removeHeaderParts",
        signature = "void removeHeaderParts (SimpleEvent soapEvent, String actor)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "SOAP event")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Removes 0 or more children of a SOAP &lt;Header&gt; of a SOAP message.\n<p>\nA null actor value will remove all headers without an actor\n</p>",
        cautions = "",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void removeHeaderParts (SimpleEvent soapEvent,
                                          String actor) {
        if (actor == null) {
            actor = "";
        }
        //Get <header> node
        XiNode headerNode = getHeaderNode(soapEvent);
        //Get all children
        if (headerNode != null) {
            //Get all header children matching this actor attribute value
            XmlNodeTest nodeTest = NodeKindNodeTest.ELEMENT;

            Iterator<XiNode> children = headerNode.getChildren(nodeTest);

            while (children.hasNext()) {
                XiNode headerChild = children.next();
                //Get its actor attribute if it has one
                String actorValue = getActor(headerChild);
                actorValue = (actorValue == null) ? "" : actorValue;
                if (actorValue.equals(actor)) {
                    headerNode.removeChild(headerChild);
                }
            }
        }
    }

    /**
     * Sets the header in SOAP schema
     * @param event SOAP event
     * @param headerXml XML string pertaining to SOAP Header
     */
    public static void setHeader (SimpleEvent event, String headerXml) {

    }

    @com.tibco.be.model.functions.BEFunction(
        name = "addSOAPBodyPart",
        signature = "void addSOAPBodyPart (SimpleEvent soapEvent,String bodyXml)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "SOAP event"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bodyXml", type = "String", desc = "XML string of body to set")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Adds a body fragment as a child of the standard SOAP body.\nThis fragment should be a well formed xml.",
        cautions = "",
        fndomain = {ACTION, BUI},
        example = ""
    )
    @AppliesTo("serializer")
    public static void addSOAPBodyPart (SimpleEvent soapEvent,
                                    String bodyXml) {
        //Get envelope node
        XiNode envelopenode = createEnvelopeNode(soapEvent);

        //Check if it has a <Body> already
        XiNode bodyNode =
                XiChild.getChild(envelopenode, SOAP_BODY_NAME);
        if (bodyNode == null) {
            //Create one
            try {
                bodyNode = addSOAPBody(soapEvent);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
        	if (faultExist(bodyNode)) {
        		throw new RuntimeException("SOAP Fault already exist, cannot add SOAP Body part");
        	}
        }
        //Convert XML to XiNode
        if (bodyXml == null) {
            throw new IllegalArgumentException("XML string cannot be null");
        }

        try {
        	ByteArrayInputStream bis = new ByteArrayInputStream(bodyXml.getBytes("UTF-8"));
            XiNode body = XiNodeBuilder.parse(bis);

            if (body != null) {
                //First child is needed as rootnode is a document
                XiNode child = body.getRootNode().getFirstChild();
                //Append it to <body>
                bodyNode.appendChild(child);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates an empty SOAP Body Node, if it does not exist already.
     * @param soapEvent
     * @return an {@linkplain XiNode}
     */
    @AppliesTo("serializer")
    private static XiNode addSOAPBody(SimpleEvent soapEvent) {
    	//Get envelope node
        XiNode envelopenode = createEnvelopeNode(soapEvent);

        //Check if it has a <Body> already
        XiNode bodyNode = XiChild.getChild(envelopenode, SOAP_BODY_NAME);
        if (bodyNode == null) {
            // Create an empty SOAP Body Node
            try {
                bodyNode = XiSupport.getXiFactory().createElement(SOAP_BODY_NAME);
                // Append it to envelope
                envelopenode.appendChild(bodyNode);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return bodyNode;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "addFaultPart",
        signature = "void addFaultPart (SimpleEvent soapEvent,String faultCode,String faultMessage,String faultActor,String faultDetailString)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "SOAP event"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "faultCode", type = "String", desc = "The mandatory fault code"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "faultMessage", type = "String", desc = "The mandatory fault message"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "faultActor", type = "String", desc = "The optional Fault actor"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "faultDetailString", type = "String", desc = "Optional XML string representing the fault detail")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Adds a &lt;fault&gt; element to the standard SOAP body\n<p>\nThis can be used to build response SOAP body\n</p>",
        cautions = "",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void addFaultPart (SimpleEvent soapEvent,
                                     String faultCode,
                                     String faultMessage,
                                     String faultActor,
                                     String faultDetailString) {
        if (faultCode == null || faultMessage == null) {
            throw new IllegalArgumentException("Fault code, and Actor are mandatory");
        }
        //Get envelope node
        XiNode envelopenode = createEnvelopeNode(soapEvent);

        //Check if it has a <Body> already
        XiNode bodyNode =
                XiChild.getChild(envelopenode, SOAP_BODY_NAME);
        if (bodyNode == null) {
            //Create one
            try {
                bodyNode =
                        XiSupport.getXiFactory().createElement(SOAP_BODY_NAME);
                //Append it to envelope
                envelopenode.appendChild(bodyNode);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
        	if (bodyPartExist(bodyNode)) {
        		throw new RuntimeException("SOAP Body part(s) already exist, cannot add SOAP Fault");
        	} else if(faultExist(bodyNode)) {
        		throw new RuntimeException("SOAP Fault can be added only once");
        	}
        }
        //Create a <fault>
        XiNode faultNode = XiChild.getChild(bodyNode, SOAP_FAULT_NAME);
        //If one exists already, use it
        if (faultNode == null) {
            faultNode =
                    XiSupport.getXiFactory().createElement(SOAP_FAULT_NAME);
            bodyNode.appendChild(faultNode);
        }

        //Append subelements
        createFaultSubElement(faultNode, faultCode, SOAP_FAULT_CODE_NAME);
        createFaultSubElement(faultNode, faultMessage, SOAP_FAULT_STRING_NAME);

        if (faultActor != null) {
            createFaultSubElement(faultNode, faultActor, SOAP_FAULT_ACTOR_NAME);
        }

        if (faultDetailString == null) {
            return;
        }
        //Create fault detail
        XiNode faultDetailNode = XiChild.getChild(faultNode, SOAP_FAULT_DETAIL_NAME);

        if (faultDetailNode == null) {
            faultDetailNode =
                    XiSupport.getXiFactory().createElement(SOAP_FAULT_DETAIL_NAME);
        }
        faultNode.appendChild(faultDetailNode);
        //Append the details xml string
        faultDetailString = faultDetailString.replaceAll("\\<\\?xml (.*)\\?\\>", "");

        try {
            ByteArrayInputStream bis =
                    new ByteArrayInputStream(faultDetailString.getBytes("UTF-8"));
            XiNode detailsXiNode = XiNodeBuilder.parse(bis);

            //If not null append it
            if (detailsXiNode != null) {
                faultDetailNode.appendChild(detailsXiNode.getRootNode().getFirstChild());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean faultExist(XiNode bodyNode){
        //Check for SOAP:Fault Node
        XiNode faultNode = XiChild.getChild(bodyNode, SOAP_FAULT_NAME);
        //If exists return true
        if (faultNode != null) {
        	return true;
        }
    	return false;
    }

    private static boolean bodyPartExist(XiNode bodyNode){
        //Check how many nodes added
        int nodeCount = XiChild.getChildCount(bodyNode);
        //If exists return true
        if (nodeCount > 0) {
        	return true;
        }
    	return false;
    }

    /**
     * Create a sub-element of <b>&lt;fault&gt;</b> and set its string
     * value to the one passed to it.
     * @param faultNode
     * @param faultSubElementValue
     * @param exName
     */
    private static void createFaultSubElement(XiNode faultNode,
                                              String faultSubElementValue,
                                              ExpandedName exName) {
        //Create fault sub-element if one does not exist
        XiNode faultSubNode = XiChild.getChild(faultNode, exName);
        if (faultSubNode == null) {
            faultSubNode =
                    XiSupport.getXiFactory().createElement(exName);
            faultNode.appendChild(faultSubNode);
        }
        if(SOAP_FAULT_CODE_NAME.equals(exName)){
        	String soapNS = faultNode.getName().getNamespaceURI();
        	XiChild.setExpandedName(faultNode, SOAP_FAULT_CODE_NAME, ExpandedName.makeName(soapNS, faultSubElementValue));
        } else {
        	//Set text on this
        	faultSubNode.setStringValue(faultSubElementValue);
        }
    }

    /**
     *
     * @param event SOAP event
     * @param namespace remove fault part with this namespace. can be null
     * @param name remove fault part with this element name
     */
    public static void removeFaultPart (SimpleEvent event, String namespace, String name) {

    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getNumberOfAttachments",
        signature = "int getNumberOfAttachments(SimpleEvent soapEvent)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "SOAP event")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "number of attachments"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets a count of SOAP attachments in the incoming SOAP message.",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    @AppliesTo("deserializer")
    public static int getNumberOfAttachments(SimpleEvent soapEvent) {
        if (soapEvent == null) {
            throw new IllegalArgumentException("Cannot pass a null parameter");
        }
        XiNode attachmentsNode = getAttachmentsNode(soapEvent);
        int counter = 0;

        if (attachmentsNode != null) {
            //Get immediate children. All <attachment> nodes
            counter = XiChild.getChildCount(attachmentsNode);
        }
        return counter;
    }

    /**
     * Returns the &lt;attachments&gt; xml element in the event payload
     * @param soapEvent
     * @return {@linkplain XiNode}
     */
    @AppliesTo("deserializer")
    private static XiNode getAttachmentsNode(SimpleEvent soapEvent) {
        //Get its container node
        XiNode messageNode = getContainerNode(soapEvent);

        //Get its <attachments> child. Will be only one
        //The call to getParent is a little absurd here
        XiNode attachmentsNode =
                XiChild.getChild(messageNode, PAYLOAD_ATTACHMENTS_NAME);

        return attachmentsNode;
    }

    /**
     * Get a list of all &lt;attachment&gt; children of the &lt;attachments&gt;
     * @param attachmentsNode
     * @return {@linkplain List}
     */
    @AppliesTo("deserializer")
    private static List<XiNode> getAttachmentNodes(XiNode attachmentsNode) {
        List<XiNode> attachmentNodes = new ArrayList<XiNode>();

        if (attachmentsNode != null) {
            //Get its children
            Iterator<XiNode> children = attachmentsNode.getChildren();

            while (children.hasNext()) {
                attachmentNodes.add(children.next());
            }
        }
        return attachmentNodes;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getAttachmentContent",
        signature = "Object getAttachmentContent(SimpleEvent soapEvent,int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "SOAP event"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "attachment index")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "attachment content as byte[]"),
        version = "4.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the content of the attachment in byte[] form.\nThe attachment is specified using the index. will return null in case of no attachment",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    @AppliesTo("deserializer")
    public static Object getAttachmentContent(SimpleEvent soapEvent,
                                              int index) {
        if (soapEvent == null) {
            throw new IllegalArgumentException("Cannot pass a null parameter");
        }
        XiNode attachmentsNode = getAttachmentsNode(soapEvent);
        //Get all <attachment> children
        List<XiNode> attachmentNodes = getAttachmentNodes(attachmentsNode);

        int numOfAttachments = attachmentNodes.size();

        if (numOfAttachments == 0)
        	return null;

        if (index >= numOfAttachments) {
            throw new IllegalArgumentException("Number of attachments is lesser than or equal to index");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Index value cannot be negative");
        }
        XiNode attachmentNode = attachmentNodes.get(index);

        XiNode attrNode = attachmentNode.getAttribute(PAYLOAD_ATTACHMENT_CONTENT_TYPE_ATTR_NAME);
        String contentType = attrNode.getStringValue();


    	byte[] byteContent = null;
		if (contentType != null && contentType.startsWith("text")) {
    		String strContent = XiChild.getString(attachmentNode, PAYLOAD_ATTACHMENT_CONTENT_NAME);
    		try {
    			byteContent  = strContent.getBytes("UTF-8");
            } catch (UnsupportedEncodingException uex) {
            	byteContent = null;
            }
    	} else {
    		try {
    			byteContent = XiChild.getBytes(attachmentNode, PAYLOAD_ATTACHMENT_CONTENT_NAME);
    		} catch (Exception e) {
    			byteContent = null;
    		}
    	}

        return byteContent;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getAttachmentContentType",
        signature = "String getAttachmentContentType(SimpleEvent soapEvent,int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "SOAP event"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "attachment index")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "attachment content type"),
        version = "4.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the String value content type of the attachment.\nThe attachment is specified using the index.",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    @AppliesTo("deserializer")
    public static String getAttachmentContentType(SimpleEvent soapEvent,
                                                  int index) {
        return getAttachmentContentAttr(soapEvent, index, PAYLOAD_ATTACHMENT_CONTENT_TYPE_ATTR_NAME);
    }

    /**
     *
     * @param soapEvent
     * @param index
     * @return attachment content encoding
     */
    public static String getAttachmentContentEncoding(SimpleEvent soapEvent,
                                                      int index) {
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getAttachmentContentID",
        signature = "String getAttachmentContentID(SimpleEvent soapEvent,int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "SOAP event"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "attachment index")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "attachment content ID"),
        version = "4.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the String value content ID of the attachment.\nThe attachment is specified using the index.",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static String getAttachmentContentID(SimpleEvent soapEvent,
                                                int index) {
        String contentId =
                getAttachmentContentAttr(soapEvent, index, PAYLOAD_ATTACHMENT_CONTENT_ID_ATTR_NAME);
        //Remove the entity references
        contentId = contentId.replaceAll("&lt;", "<");
        contentId = contentId.replaceAll("&gt;", ">");
        return contentId;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getAttachmentContentByContentID",
        signature = "Object getAttachmentContentByContentID(SimpleEvent soapEvent,String contentID)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "SOAP event"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "contentID", type = "String", desc = "The contentID to search for")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "attachment content in byte[] form"),
        version = "4.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the content of the attachment in byte[] form.\nReturns a null if no attachment is found with the given content ID.",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    @AppliesTo("deserializer")
    public static Object getAttachmentContentByContentID(SimpleEvent soapEvent,
                                                         String contentID) {
        if (soapEvent == null) {
            throw new IllegalArgumentException("Cannot pass a null parameter for SOAP event");
        }
        if (contentID == null) {
            throw new IllegalArgumentException("Cannot pass a null parameter for contentID");
        }
        XiNode attachmentsNode = getAttachmentsNode(soapEvent);
        //Get all <attachment> children
        List<XiNode> attachmentNodes = getAttachmentNodes(attachmentsNode);

        int counter = 0;
        for (XiNode attachmentNode : attachmentNodes) {
            //Look for content ID
            String nodeContentID = getAttachmentContentID(soapEvent, counter++);
            if (contentID.equals(nodeContentID)) {
                //Get its <content> child
                XiNode contentNode = XiChild.getChild(attachmentNode, PAYLOAD_ATTACHMENT_CONTENT_NAME);
                //Get its text value
                String contentVal = contentNode.getStringValue();

                if (contentVal != null) {
                    try {
                        return contentVal.getBytes("UTF-8");
                    } catch (UnsupportedEncodingException uex) {
                        throw new RuntimeException(uex);
                    }
                }
            }
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "addAttachment",
        signature = "void addAttachment(SimpleEvent soapEvent,String contentID, Object content,String contentType)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "To add attachment for this event"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "contentID", type = "String", desc = "Content ID for this attachment"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "content", type = "Object", desc = "String/byte[] representation of the content"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "contentType", type = "String", desc = "Content Type for this attachment")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "4.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Adds a new attachment to the SOAP message.",
        cautions = "",
        fndomain = {ACTION, BUI},
        example = ""
    )
    @AppliesTo("serializer")
    public static void addAttachment(SimpleEvent soapEvent,
                                     String contentID,
                                     Object content,
                                     String contentType) {
        if (contentID == null || contentType == null) {
            throw new IllegalArgumentException("ContentID, and ContentType cannot be null");
        }
        contentID = wrapContentID(contentID);

        XiNode attachmentsNode = createAttachmentsNode(soapEvent);
        //Create an <attachment> element
        //Before that check whether there is already one with that contentid
        if (checkAttachmentExistence(attachmentsNode,
                                     contentID)) {
            throw new RuntimeException("Attachment exists with same content id");
        }
        //Create an <attachment>
        XiNode attachmentNode =
                XiSupport.getXiFactory().createElement(PAYLOAD_ATTACHMENT_NAME);
        setContentAttr(attachmentNode, PAYLOAD_ATTACHMENT_CONTENT_ID_ATTR_NAME, contentID);
        setContentAttr(attachmentNode, PAYLOAD_ATTACHMENT_CONTENT_TYPE_ATTR_NAME, contentType);

        //Create the attachment content
        XiNode contentChild =
                XiSupport.getXiFactory().createElement(PAYLOAD_ATTACHMENT_CONTENT_NAME);
        //Append it to main node
        attachmentNode.appendChild(contentChild);
        byte[] bytes ;
        String strContent;
        if (content instanceof String) {
        	strContent = (String)content;
        	try {
				bytes = ((String)content).getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
        } else if(content instanceof byte[]) {
        	bytes = (byte[])content;
        	ByteBuffer bb = ByteBuffer.wrap(bytes);
        	Charset charset = Charset.defaultCharset();
        	CharBuffer cb = charset.decode(bb);
        	strContent = cb.toString();
        } else {
			throw new RuntimeException(
					"content representation of type :"
							+ content.getClass()
							+ "is not supported.\n Only String/byte[] representation supported");
		}

        if (contentType != null && contentType.toLowerCase().startsWith("text")) {
        	XiChild.setString(attachmentNode, PAYLOAD_ATTACHMENT_CONTENT_NAME, strContent);
        } else {
        	XiChild.setBytes(attachmentNode, PAYLOAD_ATTACHMENT_CONTENT_NAME, bytes);
        }

        attachmentsNode.appendChild(attachmentNode);
    }

    /**
     * Check to see if an attachment exists in this event payload with the
     * contentID or not
     * @param attachmentsNode
     * @param contentID
     * @return true|false
     */
    @AppliesTo("serializer")
    private static boolean checkAttachmentExistence(XiNode attachmentsNode,
                                                    String contentID) {

        //Get its children
        Iterator<XiNode> children = attachmentsNode.getChildren();

        while (children.hasNext()) {
            XiNode child = children.next();
            //Get its contentid value
            XiNode contentIdAttr = child.getAttribute(PAYLOAD_ATTACHMENT_CONTENT_ID_ATTR_NAME);
            //No need for null check..If element exists this will exist
            if (contentIdAttr.getStringValue().intern() ==
                    contentID.intern()) {
                //Attachment exists
                return true;
            }
        }
        return false;
    }

    /**
     * Wrap a content ID using the standard convention
     * @param contentID
     * @return wrapped contentID
     */
    private static String wrapContentID(String contentID) {
        StringBuilder sb = new StringBuilder();
        if (!contentID.startsWith("<")) {
            sb.append("<");
        }
        sb.append(contentID);
        if (!contentID.endsWith(">")) {
            sb.append(">");
        }
        return sb.toString();
    }

    /**
     *
     * @param attachmentNode
     * @param contentAttrName
     * @param contentAttrValue
     */
    @AppliesTo("serializer")
    private static void setContentAttr(XiNode attachmentNode,
                                       ExpandedName contentAttrName,
                                       String contentAttrValue) {
        //Create the attribute and set it on the node
        attachmentNode.setAttributeStringValue(contentAttrName, contentAttrValue);
    }

    /**
     * Create an &lt;attachments&gt; xml in this event's payload if one does not exist already
     * @param soapEvent
     * @return {@linkplain XiNode} for &lt;attachments&gt;
     */
    @AppliesTo("serializer")
    private static XiNode createAttachmentsNode(SimpleEvent soapEvent) {
        //Create the root <message> node
        XiNode messageNode = createPayloadContainerNode(soapEvent);

        //It may already have an <attachments>
        XiNode attachmentsChild =
                XiChild.getChild(messageNode, PAYLOAD_ATTACHMENTS_NAME);

        if (attachmentsChild == null) {
            //Create a <attachments> node
            attachmentsChild = messageNode.appendElement(PAYLOAD_ATTACHMENTS_NAME);
        }
        return attachmentsChild;
    }

    /**
     * Return the value of any attachment related attribute like contentID, contentType etc.
     * @param soapEvent
     * @param index
     * @param attrName
     * @return the string value
     */
    @AppliesTo("deserializer")
    private static String getAttachmentContentAttr(SimpleEvent soapEvent,
                                                   int index,
                                                   ExpandedName attrName) {
        if (soapEvent == null) {
            throw new IllegalArgumentException("Cannot pass a null parameter");
        }
        XiNode attachmentsNode = getAttachmentsNode(soapEvent);
        //Get all <attachment> children
        List<XiNode> attachmentNodes = getAttachmentNodes(attachmentsNode);

        int numOfAttachments = attachmentNodes.size();

        if (index > numOfAttachments) {
            throw new IllegalArgumentException("Number of attachments is lesser than index");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Index value cannot be negative");
        }
        XiNode attachmentNode = attachmentNodes.get(index);
        //Get its content type attr
        XiNode attrNode = attachmentNode.getAttribute(attrName);

        if (attrNode != null) {
            return attrNode.getStringValue();
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "addSOAPHeaderAttribute",
        signature = "void addSOAPHeaderAttribute(SimpleEvent soapEvent,int index, String attribute, String value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "To add attachment for this event"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index of the header required which should be in the range of the header parts list."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "attribute", type = "String", desc = "attribute name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "String", desc = "attribute's value")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "4.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Add attribute to SOAP header",
        cautions = "",
        fndomain = {ACTION, BUI},
        example = ""
    )
    @AppliesTo("serializer")
    public static void addSOAPHeaderAttribute(SimpleEvent soapEvent,int index, String attribute, String value){
    	if(soapEvent == null)
    		throw new IllegalArgumentException("SOAP event is null");

        if(index < 0 )
        	throw new IllegalArgumentException("index should not be negative");

        if(attribute == null || attribute.trim().isEmpty())
    		throw new IllegalArgumentException("null of empty attribute name is not valid");

        if(value == null)
    		throw new IllegalArgumentException("null value is not valid");

        if(attribute.trim().equalsIgnoreCase(SOAP_MUST_UNDERSTAND)){
        	try {
        		Boolean.valueOf(value);
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("For mustunderstand attributes valid value is true/false only");
			}
        }
    	XiNode headerNode = getHeaderNode(soapEvent);

        Iterator<XiNode> children = XiChild.getIterator(headerNode);
        List<XiNode> childList = new ArrayList<XiNode>();
        while (children.hasNext()) {
            XiNode headerChild = children.next();
            childList.add(headerChild);
        }

        int size = childList.size();
        if(index >= size)
        	throw new IllegalArgumentException("Number of headers is lesser than index");

        XiNode xiNode = childList.get(index);

        xiNode.setAttributeStringValue(ExpandedName.makeName(SOAP_NS, attribute), value);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "registerEventUri",
            signature = "void registerEventUri(String eventUri, String destinationUri, String serviceName, String soapAction, String preprocessor)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "eventUri", type = "String", desc = "Path of an event type in the project."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "destinationUri", type = "String", desc = "Path of the BusinessEvents destination receiving the message. If empty, matches all destinations."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "serviceName", type = "String", desc = "(Optional) name of the target service declared in the message received. If empty, matches all service names."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapAction", type = "String", desc = "(Optional) value of the soapAction parameter in the message received."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "preprocessor", type = "String", desc = "(Optional) path to a preprocessor RuleFunction.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.3",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Associates an event type to a given input destination, target service, and SOAP action. "
                    + "This is used by some SOAP deserializers to generate events of specific types, "
                    + "instead of using the default event type resolution mechanisms.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
    )
    @AppliesTo("deserializer")
    public static void registerEventUri(
            String eventUri,
            String destinationUri,
            String... args)
    {
        final String serviceName = (args.length < 1) ? null : args[0];
        final String soapAction = (args.length < 2) ? null : args[1];
        final String preProcessorUri = (args.length < 3) ? null : args[2];
        
        final TypeManager typeManager = RuleSessionManager.getCurrentRuleSession()
                .getRuleServiceProvider().getTypeManager();

        final RuleFunction rf;
        if ((null == preProcessorUri) || preProcessorUri.trim().isEmpty()) {
            rf = null;
        }
        else {
            rf = ((BEClassLoader) typeManager).getRuleFunctionInstance(preProcessorUri);
            if (null == rf) {
                throw new IllegalArgumentException("Cannot find RuleFunction from URI: " + preProcessorUri);
            }
        }

        final ExpandedName eventName;
        if ((null == eventUri) || eventUri.trim().isEmpty()) {
        	eventName = null;
        }
        else {
            final TypeManager.TypeDescriptor eventTypeDescriptor = typeManager.getTypeDescriptor(eventUri);
            eventName = (null == eventTypeDescriptor)
                    ? null
                    : eventTypeDescriptor.getExpandedName();
            if (null == eventName) {
                throw new IllegalArgumentException("Cannot find event from URI: " + eventUri);
            }        	
        }

        EventNameRegistry.getInstance().registerEventName(
                eventName, destinationUri, serviceName, soapAction, rf);
        

        RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getLogger(SOAPHelper.class).log(
                Level.INFO,
                "Registered event [%s] and preprocessor [%s] @ [uri=%s service=%s soapAction=%s]",
                eventUri, preProcessorUri, destinationUri, serviceName, soapAction);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "newCorrelationId",
            signature = "void newCorrelationId(String responseEventUri)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "responseEventUri", type = "String", desc = "Path of an event type in the project."),
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Correlation ID."),
            version = "5.1.3",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates a new correlation ID for use in request/response. "
                    + "This is used by some SOAP deserializers to generate events of specific types, "
                    + "instead of using the default event type resolution mechanisms.",
            cautions = "",
            fndomain = {ACTION, BUI},
            example = ""
    )
    @AppliesTo("deserializer")
    public static String newCorrelationId(
            String responseEventUri)
    {
        if (null == responseEventUri) {
            responseEventUri = "";
        }

        return "SOAP" + responseEventUri + ","
                + RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()
                .getIdGenerator().nextEntityId();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "signSOAPEnvelope",
            signature = "void signSOAPEnvelope (SimpleEvent soapEvent, Object clientKeyStore, String trustedCertsFolder, String ksPassword, String alias)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapEvent", type = "SimpleEvent", desc = "SOAP event"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "keystore", type = "Object", desc = "Keystore object"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "trustedCertsFolder", type = "String", desc = "The relative path of the folder containing the certificates. Certificates folder has to be inside project"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ksPassword", type = "String", desc = "Obfuscated password for the keystore"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "alias", type = "String", desc = "Alias associated to the keystore")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.6.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Signs the entire SOAP Request envelope using the certificate information provided.",
            cautions = "",
            fndomain = {ACTION, QUERY, BUI},
            example = ""
    )
    @AppliesTo("serializer")
    public static void signSOAPEnvelope(SimpleEvent soapEvent, Object clientKeyStore, 
    		String trustedCertsFolder, String ksPassword, String alias) {
        if (clientKeyStore == null || alias == null || ksPassword == null || ksPassword.trim().length() == 0) {
            throw new IllegalArgumentException("Cannot pass a null parameter");
        }
        if (!(clientKeyStore instanceof KeyStore)) {
            throw new IllegalArgumentException("Invalid keystore argument");
        }

        try {
        	signSOAPEnvelopePart(soapEvent, (KeyStore) clientKeyStore, trustedCertsFolder, ksPassword, alias);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Signs the specified part of SOAP Request envelope using the certificate information provided.
     * 
     * @param soapEvent
     * @param clientKeyStore
     * @param trustedCertsFolder
     * @param ksPassword
     * @param alias
     * @throws IllegalArgumentException
     * @throws RuntimeException
     */
    @AppliesTo("serializer")
    private static void signSOAPEnvelopePart(SimpleEvent soapEvent, KeyStore clientKeyStore, 
    		String trustedCertsFolder, String ksPassword, String alias) 
    				throws IllegalArgumentException, RuntimeException {
        if (soapEvent == null || clientKeyStore == null || alias == null || 
        		ksPassword == null || ksPassword.trim().length() == 0) {
            throw new IllegalArgumentException("Cannot pass a null parameter");
        }
        try {
        	DeployedProject project = RuleServiceProviderManager.getInstance().getDefaultProvider().getProject();
            ArchiveResourceProvider provider = project.getSharedArchiveResourceProvider();
            TrustedCerts trustedCerts =
            	BETrustedCertificateManager.getInstance().getTrustedCerts(
                    provider, project.getGlobalVariables(), trustedCertsFolder, true);
            Cert[] certListCertificates = trustedCerts.getCertificateList();
            for (Cert cert : certListCertificates) {
            	//Assuming DNs will be unique.
            	String subjectDN = cert.getSubjectDN().toString();
            	clientKeyStore.setCertificateEntry(subjectDN, cert.getCertificate());
            }
            
            // Get certificate private key
			PrivateKey privateKey = SSLUtils.getCertificatePrivateKey(clientKeyStore, ksPassword, alias);
			
			// Instantiate the XML document to be signed.
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(getEnvelope(soapEvent))));
			Node headerNode = doc.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "SOAP-ENV:Header");
						
			// Create a DOMSignContext specifying the PrivateKey and the
            // location of the resulting XMLSignature's parent element.
            DOMSignContext signContext = new DOMSignContext(privateKey, headerNode);

            Certificate[] chain = SSLUtils.getCertificateChain(clientKeyStore, alias);
            if (chain == null) {
            	throw new IllegalArgumentException("Invalid keystore alias or keystore does not contain a certificate chain");
            }
			Certificate cert = (X509Certificate) chain[0];
			
			// Create the XMLSignature, but don't sign it yet.
            XMLSignature signature = SSLUtils.createXMLSignature(cert);
            // Marshal, generate, and sign the document with signature.
            signature.sign(signContext);
			
			// Add the digitally signed headerXML to the event
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter buffer = new StringWriter();
            transformer.transform(new DOMSource(headerNode.getFirstChild()), new StreamResult(buffer));
            String headerPartXml = buffer.toString();
	        addHeaderPart(soapEvent, headerPartXml);
		} catch (InvalidAlgorithmParameterException | KeyStoreException | NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
		} catch (UnrecoverableEntryException e) {
            throw new IllegalArgumentException("Invalid keystore password");
        } catch (Exception e) {
//			e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
