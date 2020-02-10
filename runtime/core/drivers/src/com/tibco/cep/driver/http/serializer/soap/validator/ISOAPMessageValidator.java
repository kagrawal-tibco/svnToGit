package com.tibco.cep.driver.http.serializer.soap.validator;

import java.util.List;

import com.tibco.cep.driver.http.serializer.soap.exceptions.SOAPMessageValidationException;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 21, 2009
 * Time: 7:31:22 PM
 * <!--
 * Add Description of the class here
 * -->
 */
public interface ISOAPMessageValidator<V extends IValidatable> {

    void validate(V validatable,
                  List<SOAPValidationError> errors) throws SOAPMessageValidationException;
}
