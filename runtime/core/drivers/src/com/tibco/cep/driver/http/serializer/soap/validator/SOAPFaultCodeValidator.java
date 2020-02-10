package com.tibco.cep.driver.http.serializer.soap.validator;

import java.util.List;

import com.tibco.cep.driver.http.serializer.soap.SOAPFaultCodes;
import com.tibco.cep.driver.http.serializer.soap.SOAPMessage;
import com.tibco.cep.driver.http.serializer.soap.exceptions.SOAPMessageValidationException;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 23, 2009
 * Time: 12:10:30 PM
 * Provides default validation for soap fault codes.
 * <p>
 * Can be overridden to provide a different behaviour
 * </p>
 */
public class SOAPFaultCodeValidator implements ISOAPMessageValidator<SOAPMessage> {

    public void validate(SOAPMessage validatable,
                         List<SOAPValidationError> errors) throws SOAPMessageValidationException {
        //Check that the fault code is valid
        String faultCodeString = validatable.getFaultCode();
        try {
            if (faultCodeString == null || faultCodeString.length() == 0) {
                return;
            }
            boolean b = SOAPFaultCodes.validateLiteral(faultCodeString);
            if (!b) {
                SOAPValidationError error = new SOAPValidationError();
                error.setErrorSource(validatable);
                error.setErrorMessage("Fault code not matching standard");
                errors.add(error);
            }
        } catch (Exception e) {
            throw new SOAPMessageValidationException(e);
        }
    }



    protected boolean validateFaultNS(String faultNS) {
        //TODO
        return true;
    }
}
