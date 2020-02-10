package com.tibco.cep.studio.dashboard.core.exception;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The class for a validation message
 *
 *
 */
public abstract class SynValidationMessage {

    /**
     * Validation Error messages are used in the event that the element is
     * syntatically/structurally incorrect. This message signals that the
     * element can not be persisted because of incomplete or incorrect
     * structural information.
     */
    public static final int MSG_TYPE_ERROR = 1;

    /**
     * Validation Info messages are those that can provide hints to the user for
     * the appropriate use of particular element. Validation Info messages are
     * used in the event that the element is syntatically correct but may be
     * logically imprecise or ambiguous.
     */
    public static final int MSG_TYPE_INFO = 2;


    /**
     * The default message type is MSG_TYPE_ERROR
     */
    private int messageType = MSG_TYPE_ERROR;

    private String message = "";

    private Object data = null;

    private List<SynValidationMessage> subMessages = null;

    /**
     * A constructor for this class
     *
     * @param message
     *            the message to be displayed to the user
     */
    public SynValidationMessage(String message) {
        this.message = message;
    }

    /**
     * A convenience constructor for this class
     *
     * @param message
     *            the message to be displayed to the user
     * @param messageType
     *            the type of this message
     * @param data
     *            any reference data object that can be used by implementors to
     *            pass additional information about the validation message to
     *            the consumer of the class. Data can be null;
     * @see MSG_TYPE_ERROR
     * @see MSG_TYPE_INFO
     */
    public SynValidationMessage(String message, int messageType, Object data) {
        this.message = message;
        this.messageType = messageType != MSG_TYPE_INFO ? MSG_TYPE_ERROR : messageType;
        this.data = data;
    }

    /**
     * Returns the data associated with this message
     *
     * @return
     */
    public final Object getData() {
        return data;
    }

    /**
     * Returns the message string for this message
     *
     * @return
     */
    public final String getMessage() {
        return getMessage(false);
    }

    /**
     * TODO: Try to consolidate getMessage and getFilteredMessage
     *
     * Returns the message string and append the message strings of the sub
     * messages recursively
     *
     * @param includeSubMessages
     * @return
     */
    public final String getMessage(boolean includeSubMessages) {

        if (false == includeSubMessages) {
            return message;
        }
        StringBuffer messageBuffer = new StringBuffer(message);
        for (Iterator<SynValidationMessage> iter = getSubMessages().iterator(); iter.hasNext();) {
            SynValidationMessage subMessage = iter.next();
            if (this.getClass().getName().equals(subMessage.getClass().getName())) {
                messageBuffer.append("\n");
                messageBuffer.append(subMessage.getMessage(includeSubMessages));
            }

        }

        return messageBuffer.toString();
    }

    public final String getFilteredMessage(boolean errorMessageOnly) {

        StringBuffer messageBuffer = new StringBuffer();
        if (message != null) {
        	if ( (true == errorMessageOnly && this instanceof SynValidationErrorMessage)
        			|| (false == errorMessageOnly) ) {
                messageBuffer.append(message);
        	}
        }
        for (Iterator<SynValidationMessage> iter = getSubMessages().iterator(); iter.hasNext();) {
            SynValidationMessage subMessage = iter.next();
        	if ( (true == errorMessageOnly && subMessage instanceof SynValidationErrorMessage)
        			|| (false == errorMessageOnly) ) {
	            messageBuffer.append("\n");
	            messageBuffer.append(subMessage.getFilteredMessage(errorMessageOnly));
        	}
        }

        return messageBuffer.toString();
    }

    /**
     * Returns true if this message or one of its submessages is an error type
     *
     * @return
     */
    public final boolean hasError() {

        if(this instanceof SynValidationErrorMessage) {
            return true;
        }

        for (Iterator<SynValidationMessage> iter = getSubMessages().iterator(); iter.hasNext();) {
            SynValidationMessage subMessage = iter.next();
            if(subMessage instanceof SynValidationErrorMessage) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the message type of this message
     *
     * @return the message type constant
     * @see MSG_TYPE_ERROR
     * @see MSG_TYPE_INFO
     */
    public final int getMessageType() {
        return messageType;
    }

    /**
     * Sets the message type of this message
     *
     *
     * @see MSG_TYPE_ERROR
     * @see MSG_TYPE_INFO
     */
    public final void setMessageType(int messageType) {
        this.messageType = messageType != MSG_TYPE_INFO ? MSG_TYPE_ERROR : messageType;
    }

    /**
     * Add a submessage
     *
     * @param message
     */
    public final void addSubMessage(SynValidationMessage message) {
        if (null == subMessages) {
            subMessages = new ArrayList<SynValidationMessage>();
        }
        subMessages.add(message);
    }

    /**
     *
     * @return List The list of SynValidationMessage that are sub messages of
     *         this;
     */
    public final List<SynValidationMessage> getSubMessages() {
        return getSubMessages(false);
    }

    /**
     *
     * @return List The list of SynValidationMessage that are sub messages of
     *         this; also include deeper recursion. The resultant List is a
     *         falttened hierarchy
     */
    public final List<SynValidationMessage> getSubMessages(boolean includeSubMessages) {

        /*
         * If subMessages is null then return an emty list
         */
        if (null == subMessages) {
            return new ArrayList<SynValidationMessage>();
        }

        List<SynValidationMessage> deepSubMessages = new ArrayList<SynValidationMessage>();

        for (Iterator<SynValidationMessage> iter = subMessages.iterator(); iter.hasNext();) {
            SynValidationMessage subMessage = iter.next();
            deepSubMessages.add(subMessage);
            if (true == includeSubMessages) {
                deepSubMessages.addAll(getSubMessages(includeSubMessages));
            }
        }
        return deepSubMessages;
    }

    public final boolean hasSubMessages() {
        return !getSubMessages().isEmpty();
    }

    public abstract Object cloneThis();

    protected void cloneThis(SynValidationMessage clone) {
//    	clone.message = this.message;
    	clone.messageType = this.messageType;
    	clone.subMessages = this.subMessages;
    	clone.data = this.data;
    }

    public List<String> getReportableMessages(){
    	List<String> messages = new LinkedList<String>();
    	if (isReportable() == true) {
    		messages.add(message);
    	}
    	if (this.subMessages != null) {
			for (SynValidationMessage subMessage : this.subMessages) {
				messages.addAll(subMessage.getReportableMessages());
			}
		}
		return messages;
    }

	public boolean isReportable() {
		//FIXME hard coding to check for One or more see com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle.isValid(boolean)
		return message != null && message.startsWith("One or more ") == false;
	}
}