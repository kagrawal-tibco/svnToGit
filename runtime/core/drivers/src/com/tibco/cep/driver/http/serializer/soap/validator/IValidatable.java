package com.tibco.cep.driver.http.serializer.soap.validator;

import java.util.List;

import com.tibco.cep.driver.http.serializer.soap.exceptions.SOAPMessageValidationException;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 23, 2009
 * Time: 11:48:32 AM
 * <!--
 * Add Description of the class here
 * -->
 */
public interface IValidatable {

    /**
     * It is presumed that the list of {@link ISOAPMessageValidator} s will
     * be provided at construction time.
     * @param validationErrors
     */
    void runValidators(List<SOAPValidationError> validationErrors) throws SOAPMessageValidationException;
}
