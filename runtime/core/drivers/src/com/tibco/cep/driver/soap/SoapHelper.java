package com.tibco.cep.driver.soap;


import com.tibco.be.model.rdf.XiNodeBuilder;
import com.tibco.be.util.BEStringUtilities;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.XiNodePayload;
import com.tibco.net.mime.MutableBodyMimePart;
import com.tibco.xml.XiNodeUtilities;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.soap.api.transport.TransportEntity;
import com.tibco.xml.soap.api.transport.TransportMessage;
import com.tibco.xml.soap.helpers.HttpHelper;
import com.tibco.xml.soap.helpers.MimeHelper;
import com.tibco.xml.soap.impl.transport.DefaultTransportEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.tibco.cep.driver.http.HttpChannelConstants.UTF8_ENCODING;
import static com.tibco.net.mime.MimeConstants.*;
import static com.tibco.net.mime.MimeConstants.PARAMETER_BOUNDARY;
import static com.tibco.net.mime.MimeConstants.PARAMETER_DELIMITER;


public class SoapHelper
{

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(SoapHelper.class);


    private static void buildAttachmentContent(
            XiNode attachmentNode,
            byte[] contentBytes,
            String contentType)
            throws Exception
    {
        attachmentNode.appendChild(XiSupport.getXiFactory().createElement(SoapConstants.XNAME_CONTENT));

        if (contentType.toLowerCase().startsWith("text")) {
            XiChild.setString(attachmentNode, SoapConstants.XNAME_CONTENT,
                    BEStringUtilities.convertByteArrayToString(contentBytes, UTF8_ENCODING));
        } else {
            XiChild.setBytes(attachmentNode, SoapConstants.XNAME_CONTENT, contentBytes);
        }
    }


    public static void buildAttachments(
            XiNode rootNode,
            TransportMessage outputMessage)
            throws Exception
    {
        final List<TransportEntity> transportEntities = new ArrayList<TransportEntity>();
        for (final SoapAttachment attachment : SoapUtils.getAllAttachmentsFromPayload(rootNode)) {
            transportEntities.add(createAttachmentTransportEntity(attachment));
        }

        outputMessage.setAttachments(transportEntities.iterator());
    }


    public static XiNode buildAttachmentsXiNode(
            TransportMessage message,
            XiNode root)
            throws Exception
    {

        final Set<String> usedContentIds = new HashSet<String>();
        final XiFactory xiFactory = XiSupport.getXiFactory();
        final XiNode rootAttachmentsNode = xiFactory.createElement(SoapConstants.PAYLOAD_ATTACHMENTS_NAME);

        @SuppressWarnings("unchecked")
        final Iterator<TransportEntity> attachmentsIterator = message.getAttachments();
        if (attachmentsIterator.hasNext()) {
            root.appendChild(rootAttachmentsNode);
        }
        while (attachmentsIterator.hasNext()) {
            final TransportEntity attachment = attachmentsIterator.next();

            final String contentType = attachment.getContentType();
            if (contentType == null) {
                throw new Exception("Content type is missing on the attachment");
            }
            LOGGER.log(Level.INFO, "Content Type %s", contentType);

            final String contentID = attachment.getContentID();
            if (contentID == null) {
                throw new Exception("Content ID is missing on the attachment");
            }
            if (!usedContentIds.add(contentID)) {
                throw new Exception("Duplicate content id " + contentID + " on attachment");
            }
            LOGGER.log(Level.INFO, "Content ID %s", contentID);

            final byte[] content = attachment.getContent();
            if (content == null) {
                throw new Exception("Content is missing on the attachment");
            }

            final XiNode attachmentNode = xiFactory.createElement(SoapConstants.PAYLOAD_ATTACHMENT_NAME);
            attachmentNode.setAttribute(xiFactory.createAttribute(SoapConstants.PAYLOAD_ATTACHMENT_CONTENT_TYPE_ATTR, contentType));
            attachmentNode.setAttribute(xiFactory.createAttribute(SoapConstants.PAYLOAD_ATTACHMENT_CONTENT_ID_ATTR, contentID));
            buildAttachmentContent(attachmentNode, content, contentType);
            rootAttachmentsNode.appendChild(attachmentNode);
        }

        return rootAttachmentsNode;
    }


    public static XiNode buildPayloadAsXiNode(
            SimpleEvent event,
            TransportMessage message)
            throws Exception
    {
        final XiNode messageNode = XiSupport.getXiFactory().createElement(
                ExpandedName.makeName(event.getExpandedName().getNamespaceURI(), "message"));

        final TransportEntity envelopeEntity = message.getBody();
        envelopeEntity.loadContent();

        final XiNode rootNode = XiNodeBuilder.parse(envelopeEntity.getContentStream());
        if (rootNode != null) {
            XiNodeUtilities.cleanTextNodes(rootNode);
            SoapUtils.validateHeaders(rootNode);

            messageNode.appendChild(rootNode.getRootNode().getFirstChild());

            buildAttachmentsXiNode(message, messageNode);
        }

        return messageNode;
    }


    public static void buildSoapEnvelope(
            XiNode rootNode,
            TransportMessage outputMessage)
            throws Exception
    {
        // Get its first child which will be <Envelope>
        XiNode envelopeNode = null;
        if (rootNode.getName().localName.equals("message")) {
            envelopeNode = XiChild.getChild(rootNode, SoapConstants.SOAP_ENVELOPE_NAME);
        }
        if (null == envelopeNode) {
            throw new Exception("Envelope not present in the event payload");
        }

        final TransportEntity envelopeEntity = new DefaultTransportEntity();
        envelopeEntity.setContent(XiNodeUtilities.toBytes(envelopeNode));
        envelopeEntity.setContentID("<soap-envelope>");
        envelopeEntity.setContentType('"' + MimeTypes.XML_TEXT.toString() + "\"; charset=UTF-8");

        outputMessage.setBody(envelopeEntity);
    }


    public static void buildSoapResponse(
            XiNodePayload eventPayload,
            TransportMessage outputMessage)
            throws Exception
    {
        final XiNode rootNode = eventPayload.getNode();
        buildSoapEnvelope(rootNode, outputMessage);
        buildAttachments(rootNode, outputMessage);
    }


    private static TransportEntity createAttachmentTransportEntity(
            SoapAttachment soapAttachment)
            throws Exception
    {
        final TransportEntity attachmentEntity = new DefaultTransportEntity();
        attachmentEntity.setContent(soapAttachment.getContent());
        attachmentEntity.setContentType(soapAttachment.getContentType());
        attachmentEntity.setContentID(soapAttachment.getContentID());
        return attachmentEntity;
    }


    public static String getContentTypeValue(
            TransportMessage outputMessage)
    {
        return MULTIPART_TYPE + TYPE_SEPARATOR + RELATED_SUBTYPE + PARAMETER_DELIMITER + " type="
                + outputMessage.getBody().getContentType()
                + PARAMETER_DELIMITER + " " + PARAMETER_BOUNDARY + "=\""
                + outputMessage.getContentBoundary() + "\"";
    }


    public static byte[] getTransportMessageAsBytes(
            TransportMessage transportMessage)
            throws IOException
    {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            final MutableBodyMimePart mimePart = MimeHelper.createMimePart(transportMessage, false);
            mimePart.setOutputStream(baos);
            mimePart.writePart();
        } finally {
            baos.close();
        }
        return baos.toByteArray();
    }


    public static void readTransportMessageFromStream(
            InputStream is,
            TransportMessage transportMessage)
            throws IOException
    {
        HttpHelper.readTransportMessage(is, transportMessage); // Not HTTP-specific => wrapped to avoid confusion.
    }


}