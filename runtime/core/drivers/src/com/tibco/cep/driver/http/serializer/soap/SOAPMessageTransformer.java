package com.tibco.cep.driver.http.serializer.soap;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.tibco.cep.driver.http.serializer.soap.exceptions.SOAPMessageValidationException;
import com.tibco.cep.driver.http.serializer.soap.validator.SOAPFaultCodeValidator;
import com.tibco.cep.driver.http.serializer.soap.validator.SOAPValidationError;

/**
 * Director class for the builder to manage correct sequence
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 18, 2009
 * Time: 2:35:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class SOAPMessageTransformer<I extends ISOAPMessageBuilder> {

    /**
     * The input {@link SOAPMessage}
     */
    private SOAPMessage inputSOAPMessage;

    /**
     * Attach the {@link ISOAPMessageBuilder}
     */
    private I builder;

    public SOAPMessageTransformer(final SOAPMessage inputSOAPMessage,
                                  final I soapMessageBuilder) {
        this.inputSOAPMessage = inputSOAPMessage;
        //Build default validators
        inputSOAPMessage.addValidator(new SOAPFaultCodeValidator());
        this.builder = soapMessageBuilder;
    }

    /**
     * Build a {@link Document} of the input {@link SOAPMessage}
     * @return the built {@link Document}
     * @throws Exception
     */
    public Document buildSOAPMessage() throws Exception {
        DocumentBuilderFactory documentBuilderFactory =
                            DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        DocumentBuilder documentBuilder =
                        documentBuilderFactory.newDocumentBuilder();
        //Create an empty document
        Document document = documentBuilder.newDocument();
        List<SOAPValidationError> errors = validateSOAPMessage();
        if (errors.isEmpty()) {
            buildSOAPMessage(document);
            return document;
        }
        throw new Exception("SOAP Message validation failed");
    }

    /**
     * The correct sequence of the building is maintained here
     * @param rootDocument
     */
    protected void buildSOAPMessage(Document rootDocument) {
        builder.buildSOAPEnvelope(rootDocument);
        builder.buildSOAPHeader(rootDocument, inputSOAPMessage);
        builder.buildSOAPBody(rootDocument, inputSOAPMessage);
    }

    protected List<SOAPValidationError> validateSOAPMessage() throws SOAPMessageValidationException {
        //Run validators here
        List<SOAPValidationError> errors = new ArrayList<SOAPValidationError>();
        inputSOAPMessage.runValidators(errors);
        return errors;
    }
}
