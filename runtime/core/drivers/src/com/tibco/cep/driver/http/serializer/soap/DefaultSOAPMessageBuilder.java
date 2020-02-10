package com.tibco.cep.driver.http.serializer.soap;

import static com.tibco.cep.driver.soap.SoapConstants.SOAP_BODY;
import static com.tibco.cep.driver.soap.SoapConstants.SOAP_ENVELOPE;
import static com.tibco.cep.driver.soap.SoapConstants.SOAP_FAULT;
import static com.tibco.cep.driver.soap.SoapConstants.SOAP_FAULT_CODE;
import static com.tibco.cep.driver.soap.SoapConstants.SOAP_FAULT_DETAIL;
import static com.tibco.cep.driver.soap.SoapConstants.SOAP_FAULT_STRING;
import static com.tibco.cep.driver.soap.SoapConstants.SOAP_HEADER;
import static com.tibco.cep.driver.soap.SoapConstants.SOAP_NS;

import java.io.ByteArrayInputStream;

import com.tibco.cep.driver.soap.MimeTypes;
import com.tibco.cep.driver.soap.SoapUtils;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 18, 2009
 * Time: 1:57:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultSOAPMessageBuilder implements ISOAPMessageBuilder {

    /**
     * Build a <b>&lt;soap:Body&gt;</b> element for the response, and append it to root doc
     * @param soapDoc
     * @param soapMessage
     */
    public void buildSOAPBody(Document soapDoc, SOAPMessage soapMessage) {
        Element soapBodyElement = buildEnvelopeElement(soapDoc, SOAP_BODY);
        //The body can be successful response body or a fault
        //Look for fault code
        String faultCode = soapMessage.getFaultCode();
        if (faultCode != null) {
            buildSOAPFaultParts(soapDoc, soapMessage);
        }  else {
            //Append the body contents as XML content to the body
            String bodyContents = soapMessage.getSoapBodyContents();
            Node childElement =
                    buildSOAPElementChildXMLContent(soapDoc, bodyContents);
            if (childElement != null) {
                soapBodyElement.appendChild(childElement);
            }
        }
    }

    /**
     * Build <b>&lt;soap:Fault&gt;</b> elements if any and append it to <b>&lt;soap:Body&gt;</b>
     * @param soapDoc
     * @param soapMessage
     */
    private void buildSOAPFaultParts(Document soapDoc, SOAPMessage soapMessage) {
        //Create Fault element
        Element faultElement = soapDoc.createElementNS(SOAP_NS, SOAP_FAULT);
        //Append it to body
        NodeList soapBodyElementChildren =
                soapDoc.getDocumentElement().getElementsByTagNameNS(SOAP_NS, SOAP_BODY);
        //Get first child
        Element soapBodyElement = (Element)soapBodyElementChildren.item(0);
        soapBodyElement.appendChild(faultElement);
        String faultCode = soapMessage.getFaultCode();
        buildSOAPFaultCode(soapDoc, faultElement, faultCode);
        //Check now if fault string is present
        String faultString = soapMessage.getFaultString();
        buildSOAPFaultString(soapDoc, faultElement, faultString);
        //Get fault detail if any
        String faultDetails = soapMessage.getFaultDetail();
        buildSOAPFaultDetail(soapDoc, faultElement, faultDetails);
    }

    /**
     * Build <b>&lt;soap:faultcode&gt;</b> element and append it to <b>&lt;soap:Fault&gt;</b>
     * @param soapDoc
     * @param soapFaultElement
     * @param faultCode
     */
    private void buildSOAPFaultCode(Document soapDoc,
                                    Element soapFaultElement,
                                    String faultCode) {
        buildSOAPFaultPart(soapDoc, soapFaultElement, SOAP_FAULT_CODE, faultCode);
    }

    /**
     * Construct either a <b>&lt;soap:Body&gt;</b> or <b>&lt;soap:Header&gt;</b> element
     * as a child of <b>&lt;soap:Envelope&gt;</b>
     * @param soapDoc
     * @param elementName
     * @return <b>&lt;soap:Body&gt;</b> or <b>&lt;soap:Header&gt;</b> element
     */
    private Element buildEnvelopeElement(Document soapDoc,
                                         String elementName) {
        //Create an envelope element first
        Element soapEnvElementChild = soapDoc.createElementNS(SOAP_NS, elementName);
        //Append it to envelope
        Element soapEnvElement = soapDoc.getDocumentElement();
        soapEnvElement.appendChild(soapEnvElementChild);
        return soapEnvElementChild;
    }

    /**
     * Build a <b>&lt;soap:faultcode&gt;</b> or <b>&lt;soap:faultstring&gt;</b>
     * or <b>&lt;soap:detail&gt;</b> element and append it to <b>&lt;soap:Fault&gt;</b>
     * @param soapDoc
     * @param soapFaultElement
     * @param faulePartName
     * @param faultPartValue
     */
    private void buildSOAPFaultPart(Document soapDoc ,
                                    Element soapFaultElement,
                                    String faulePartName,
                                    String faultPartValue) {
        Element faultPartElement = soapDoc.createElementNS(SOAP_NS, faulePartName);
        //Set the code in it
        faultPartElement.setTextContent(faultPartValue);
        //Append it to fault
        soapFaultElement.appendChild(faultPartElement);
    }

    /**
     * Build <b>&lt;soap:faultstring&gt;</b> element and append it to <b>&lt;soap:Fault&gt;</b>
     * @param soapDoc
     * @param soapFaultElement
     * @param faultString
     */
    private void buildSOAPFaultString(Document soapDoc ,
                                      Element soapFaultElement,
                                      String faultString) {
        buildSOAPFaultPart(soapDoc, soapFaultElement, SOAP_FAULT_STRING, faultString);
    }

    /**
     * Build <b>&lt;soap:detail&gt;</b> element and append it to <b>&lt;soap:Fault&gt;</b>
     * @param soapDoc
     * @param soapFaultElement
     * @param faultDetail
     */
    private void buildSOAPFaultDetail(Document soapDoc ,
                                      Element soapFaultElement,
                                      String faultDetail) {
        buildSOAPFaultPart(soapDoc, soapFaultElement, SOAP_FAULT_DETAIL, faultDetail);
    }

    /**
     * Build a soap Envelope element for the response, and append it to root doc
     * @param soapDoc
     */
    public void buildSOAPEnvelope(Document soapDoc) {
        Element soapEnvElement = soapDoc.createElementNS(SOAP_NS, SOAP_ENVELOPE);
        //Add this as a child to the document to create the base envelope
        soapDoc.appendChild(soapEnvElement);
    }

    /**
     * Build a soap Header element for the response, and append it to root doc
     * @param soapDoc
     * @param soapMessage
     */
    public void buildSOAPHeader(Document soapDoc, SOAPMessage soapMessage) {
        //Create a soap Header element first
        Element soapHeaderElement = buildEnvelopeElement(soapDoc, SOAP_HEADER);
        //Assuming that the contents will be children of soap:Header
        String headerString = soapMessage.getSoapHeaderString();
        String headerMimeType = soapMessage.getHeaderMimeType();
        headerMimeType = (headerMimeType == null) ? "text/xml" : headerMimeType;
        buildSOAPHeaderContent(soapDoc,
                               soapHeaderElement,
                               headerString,
                               headerMimeType);
    }

    /**
     * Builds child content of a <b>&lt;soap:Header&gt;</b>
     * based on Mime Type
     * @see com.tibco.cep.driver.soap.MimeTypes
     * @param soapDoc
     * @param soapHeaderElement
     * @param headerString
     * @param headerMimeType
     */
    private void buildSOAPHeaderContent(Document soapDoc,
                                        Element soapHeaderElement,
                                        String headerString,
                                        String headerMimeType) {
        MimeTypes mimeType = MimeTypes.getByLiteral(headerMimeType);
        switch (mimeType) {
            case PLAIN_TEXT: {
                soapHeaderElement.setTextContent(headerString);
                return;
            }
            case XML_TEXT: {
                //Convert the String to Element
                Node childNode = buildSOAPElementChildXMLContent(soapDoc, headerString);
                //Append this to soap header
                if (childNode != null) {
                    soapHeaderElement.appendChild(childNode);
                }
                return;
            }
            default: {
                //TODO Support more types
                throw new UnsupportedOperationException("Mime Type not yet supported");
            }
        }
    }

    /**
     * Builds XML based child content of a <b>&lt;soap:Header&gt;</b>
     * or <b>&lt;soap:Body&gt;</b> based on Mime Type.
     * @see MimeTypes#XML_TEXT
     * @param soapDoc
     * @param stringtoXMlize
     * @return the {@link Node} representing this child content
     */
    private Node buildSOAPElementChildXMLContent(Document soapDoc,
                                                 String stringtoXMlize) {
        //TODO Add Charset
        if (stringtoXMlize != null) {
            //Null check for an empty header for example.
            byte[] bytes = stringtoXMlize.getBytes();
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            DocumentFragment childElementDoc = soapDoc.createDocumentFragment();
            //Transform the InputStream and place the Transform contents in this
            try {
                SoapUtils.transformStreamToDoc(childElementDoc, bis);
                return childElementDoc;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
