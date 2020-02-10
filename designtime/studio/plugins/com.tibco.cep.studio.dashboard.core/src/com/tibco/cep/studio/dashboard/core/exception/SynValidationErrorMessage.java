package com.tibco.cep.studio.dashboard.core.exception;

/**
 * @
 *  
 */
public class SynValidationErrorMessage extends SynValidationMessage {

    /**
     * @param message
     */
    public SynValidationErrorMessage(String message) {
        super(message);
        setMessageType(MSG_TYPE_ERROR);
    }

    /**
     * @param message
     * @param data
     */
    public SynValidationErrorMessage(String message, Object data) {
        super(message, MSG_TYPE_ERROR, data);

    }

    public Object cloneThis() {
    	SynValidationErrorMessage clone = new SynValidationErrorMessage(this.getMessage());
    	super.cloneThis(clone);
    	return clone;
    }
}
