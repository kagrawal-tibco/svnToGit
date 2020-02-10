package com.tibco.cep.designtime.model;


import com.tibco.cep.designtime.model.exception.BEException;


/**
 * This is the Exception thrown by methods in the com.tibco.be.model package.
 *
 * @author ishaan
 * @version Mar 17, 2004 10:22:37 AM
 */
public class ModelException extends BEException {


    public ModelException(String msg) {
        super(msg);
    }


    public ModelException(String msg, Throwable cause) {
        super(msg, cause);
    }


    public ModelException(Throwable cause) {
        super(cause);
    }
}
