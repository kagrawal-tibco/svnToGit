package com.tibco.cep.driver.http.serializer.soap.validator;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 23, 2009
 * Time: 11:47:25 AM
 * <!--
 * Add Description of the class here
 * -->
 */
public class SOAPValidationError<V extends IValidatable> {

    private String errorCode;

    private String errorMessage;

    private V errorSource;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public V getErrorSource() {
        return errorSource;
    }

    public void setErrorSource(V errorSource) {
        this.errorSource = errorSource;
    }
}
