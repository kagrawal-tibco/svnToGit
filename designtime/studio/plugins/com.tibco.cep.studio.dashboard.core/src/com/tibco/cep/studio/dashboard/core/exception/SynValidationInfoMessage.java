package com.tibco.cep.studio.dashboard.core.exception;

/**
 * @
 *  
 */
public class SynValidationInfoMessage extends SynValidationMessage {

    /**
     * @param message
     */
    public SynValidationInfoMessage(String message) {
        super(message);
        setMessageType(MSG_TYPE_INFO);
    }

    /**
     * @param message
     * @param data
     */
    public SynValidationInfoMessage(String message, Object data) {
        super(message, MSG_TYPE_INFO, data);

    }
    
    public Object cloneThis() {
    	SynValidationInfoMessage clone = new SynValidationInfoMessage(this.getMessage());
    	super.cloneThis(clone);
    	return clone;
    }

}
