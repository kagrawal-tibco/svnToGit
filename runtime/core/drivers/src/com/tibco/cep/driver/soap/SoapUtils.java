package com.tibco.cep.driver.soap;

import static com.tibco.cep.driver.soap.SoapConstants.PAYLOAD_ATTACHMENT_CONTENT_ID_ATTR_NAME;
import static com.tibco.cep.driver.soap.SoapConstants.PAYLOAD_ATTACHMENT_CONTENT_NAME;
import static com.tibco.cep.driver.soap.SoapConstants.PAYLOAD_ATTACHMENT_CONTENT_TYPE_ATTR_NAME;

import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlNodeTest;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.navigation.NodeKindNodeTest;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 18, 2009
 * Time: 3:01:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class SoapUtils
{

    static {

        //System.setProperty("javax.xml.transform.TransformerFactory", "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");

        //TODO: Can this be avoided?
        System.setProperty("javax.xml.transform.TransformerFactory", "org.apache.xalan.processor.TransformerFactoryImpl");
    }

    /**
     * Check to see that the specified property exists for the event
     * @param propertyNames
     * @param property
     * @return true|false
     */
    public static boolean containsProperty(String[] propertyNames, String property) {
        for (String propertyName : propertyNames) {
            if (propertyName.intern() == property.intern()) {
                return true;
            }
        }
        return false;
    }

     /**
     * Transform an {@link org.w3c.dom.Element} into its {@link String representation.
     * <p>
     * This will recursively convert this node, and its children elements.
     * </p>
     * @param desiredElement
     * @return
     * @throws Exception
     */
    public static String transformNode(final Element desiredElement) throws Exception {
        TransformerFactory transformerFactory = com.tibco.xml.parsers.xmlfactories.TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source source = new DOMSource(desiredElement);
        StringWriter sw = new StringWriter();
        Result stringResult = new StreamResult(sw);
        transformer.transform(source, stringResult);
        return sw.toString();
    }

     /**
     * Write a {@link org.w3c.dom.Node} to the {@link Writer}
     * <p>
     * Typically used to write the soap document as a response
     * </p>
     * @param soapDoc
     * @throws Exception
     */
    public static void writeSOAP(final Node soapDoc,
                                 final Writer writer) throws Exception {
        TransformerFactory transformerFactory = com.tibco.xml.parsers.xmlfactories.TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source source = new DOMSource(soapDoc);
        Result stringResult = new StreamResult(writer);
        transformer.transform(source, stringResult);
    }

    /**
     * Transform a {@link InputStream} carrying XML payload
     * bytes to equivalent {@link Node}
     * <p>
     * The transformation result is stored in outputDoc.
     * </p>
     * <p>
     * The outputDoc parameter <b>should not be null</b> 
     * @param outputDoc
     * @param is
     * @throws Exception
     */
    public static void transformStreamToDoc(final Node outputDoc,
                                            final InputStream is) throws Exception {
        TransformerFactory transformerFactory = com.tibco.xml.parsers.xmlfactories.TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source source = new StreamSource(is);
        DOMResult domResult = new DOMResult(outputDoc);
        transformer.transform(source, domResult);
    }


    /**
     * Build {@linkplain SoapAttachment} list from the &lt;message&gt; node
     * @param rootNode
     * @return a list
     */
    public static List<SoapAttachment> getAllAttachmentsFromPayload(
            XiNode rootNode)
    {
        List<SoapAttachment> soapAttachments = new ArrayList<SoapAttachment>();
        //Get its <attachments> child. Will be only one
        //The call to getParent is a little absurd here
        XiNode attachmentsNode =
                XiChild.getChild(rootNode, SoapConstants.PAYLOAD_ATTACHMENTS_NAME);

        List<XiNode> attachmentNodes = getAttachmentNodes(attachmentsNode);

        for (XiNode attachmentNode : attachmentNodes) {
            soapAttachments.add(buildAttachment(attachmentNode));
        }
        return soapAttachments;
    }

    /**
     * Get all &lt;attachment&gt; nodes in the &lt;message&gt; node
     * @param attachmentsNode
     * @return a list
     */
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

    /**
     * Construct a {@linkplain SoapAttachment} from the attachment {@linkplain XiNode}
     * @param attachmentNode
     * @return a {@linkplain SoapAttachment}
     */
    private static SoapAttachment buildAttachment(
            XiNode attachmentNode)
    {
        SoapAttachment soapAttachment = new SoapAttachment();
        setAttachmentContentID(soapAttachment, attachmentNode);
        setAttachmentContentType(soapAttachment, attachmentNode);
        setAttachmentContent(soapAttachment, attachmentNode);
        return soapAttachment;
    }

    /**
     * Set the content of an attachment node
     * @param soapAttachment
     * @param attachmentNode
     */
    private static void setAttachmentContent(
            SoapAttachment soapAttachment,
            XiNode attachmentNode)
    {
    	byte[] byteContent = new byte[0];
    	String contentType = soapAttachment.getContentType();
    	if(contentType != null && contentType.startsWith("text")){
    		String strContent = XiChild.getString(attachmentNode, PAYLOAD_ATTACHMENT_CONTENT_NAME);
    		byteContent = strContent.getBytes();
    	} else {
    		try {
    			byteContent = XiChild.getBytes(attachmentNode, PAYLOAD_ATTACHMENT_CONTENT_NAME);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	soapAttachment.setContent(byteContent);
    }

    /**
     * Set the content id of an attachment node
     * @param soapAttachment
     * @param attachmentNode
     */
    private static void setAttachmentContentID(
            SoapAttachment soapAttachment,
            XiNode attachmentNode)
    {
        XiNode attrNode =
                attachmentNode.getAttribute(PAYLOAD_ATTACHMENT_CONTENT_ID_ATTR_NAME);

        if (attrNode != null) {
            soapAttachment.setContentID(attrNode.getStringValue());
        }
    }

    /**
     * Set the content type of an attachment node
     * @param soapAttachment
     * @param attachmentNode
     */
    private static void setAttachmentContentType(
            SoapAttachment soapAttachment,
            XiNode attachmentNode)
    {
        XiNode attrNode =
                attachmentNode.getAttribute(PAYLOAD_ATTACHMENT_CONTENT_TYPE_ATTR_NAME);

        if (attrNode != null) {
            soapAttachment.setContentType(attrNode.getStringValue());
        }
    }

    /**
     * Checks to see that if there are any children of soap header
     * and if any, also check if each header child is qualified
     * with a namespace.
     *
     * @param soapRootNode
     * @throws Exception
     */
    public static void validateHeaders(XiNode soapRootNode) throws Exception {

        XiNode soapEnvelopeNode = soapRootNode.getRootNode().getFirstChild();
        //Check if there is a header
        XiNode soapHeaderNode =
                XiChild.getChild(soapEnvelopeNode, SoapConstants.SOAP_HEADER_NAME);
        if (soapHeaderNode == null) {
            return;
        }
        //Get its children
        XmlNodeTest nodeTest = NodeKindNodeTest.ELEMENT;

        Iterator<XiNode> headerChildren = soapHeaderNode.getChildren(nodeTest);

        while (headerChildren.hasNext()) {
            XiNode headerChild = headerChildren.next();

            //Check each header child's namespace
            ExpandedName expandedName = headerChild.getName();
            //Check if it has namespace
            String namespaceURI = expandedName.getNamespaceURI();
            if (namespaceURI == null) {
                throw new Exception ("Header " + expandedName.getLocalName() + " is not namespace qualified");
            }
        }
    }
}
