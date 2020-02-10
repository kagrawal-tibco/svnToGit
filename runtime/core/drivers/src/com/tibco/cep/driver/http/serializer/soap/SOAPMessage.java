package com.tibco.cep.driver.http.serializer.soap;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.driver.http.serializer.soap.exceptions.SOAPMessageValidationException;
import com.tibco.cep.driver.http.serializer.soap.validator.ISOAPMessageValidator;
import com.tibco.cep.driver.http.serializer.soap.validator.IValidatable;
import com.tibco.cep.driver.http.serializer.soap.validator.SOAPValidationError;

/**
 *
 * A class representing essentials of the SOAP we are interested in
 *
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 18, 2009
 * Time: 1:43:59 PM
 * This class represents a Value Object of all SOAP related
 * information sent in the request or response.
 * <p>
 * Since it implements {@link IValidatable} and is used both in
 * request, and response; Validation routines can be run
 * on both request/response.
 * </p>
 */
public class SOAPMessage<I extends ISOAPMessageValidator> implements IValidatable {

    private String soapBodyContents;

    private List<I> validators = new ArrayList<I>();

    private SOAPHeaderPart soapHeaderContent = new SOAPHeaderPart();

    private String faultCode;

    private String faultString;

    private String faultDetail;

    private class SOAPHeaderPart {

        private String soapHeaderString;

        private String mimeType;

        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }

        public String getSoapHeaderString() {
            return soapHeaderString;
        }

        public void setSoapHeaderString(String soapHeaderString) {
            this.soapHeaderString = soapHeaderString;
        }
    }

    public String getSoapBodyContents() {
        return soapBodyContents;
    }

    public void setSoapBodyContents(String soapBodyContents) {
        this.soapBodyContents = soapBodyContents;
    }

    public String getSoapHeaderString() {
        return soapHeaderContent.getSoapHeaderString();
    }

    public void setSoapHeaderString(String soapHeaderString) {
        this.soapHeaderContent.setSoapHeaderString(soapHeaderString);
    }

    public void setHeaderMimeType(String mimeType) {
        this.soapHeaderContent.setMimeType(mimeType);
    }

    public String getHeaderMimeType() {
        return soapHeaderContent.getMimeType();
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        //Validate fault code
        this.faultCode = faultCode;
    }

    public String getFaultDetail() {
        return faultDetail;
    }

    public void setFaultDetail(String faultDetail) {
        this.faultDetail = faultDetail;
    }

    public String getFaultString() {
        return faultString;
    }

    public void setFaultString(String faultString) {
        this.faultString = faultString;
    }

    public void addValidator(I validator) {
        validators.add(validator);
    }

    public void runValidators(List<SOAPValidationError> validationErrors) throws SOAPMessageValidationException {
        for (I validator : validators) {
            validator.validate(this, validationErrors);
        }
    }
}
