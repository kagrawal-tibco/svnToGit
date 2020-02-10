package com.tibco.cep.driver.http.serializer.soap;

import org.w3c.dom.Document;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 18, 2009
 * Time: 1:50:33 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ISOAPMessageBuilder {

    /**
     * Build a soap Header element for the response, and append it to root doc
     * @param soapDoc
     * @param soapMessage
     */
    void buildSOAPHeader(Document soapDoc, SOAPMessage soapMessage);

    /**
     * Build a soap Body element for the response, and append it to root doc
     * @param soapDoc
     * @param soapMessage
     */
    void buildSOAPBody(Document soapDoc, SOAPMessage soapMessage);

    /**
     * Build a soap Envelope element for the response, and append it to root doc
     * @param soapDoc
     */
    void buildSOAPEnvelope(Document soapDoc);
}
