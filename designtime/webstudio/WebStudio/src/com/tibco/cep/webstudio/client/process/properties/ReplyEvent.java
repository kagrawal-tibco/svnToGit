package com.tibco.cep.webstudio.client.process.properties;

import java.io.Serializable;

/**
 * This class stores the reply event details.
 * 
 * @author dijadhav
 * 
 */
public class ReplyEvent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8728991176977554072L;
	private String msgStarterId;
	private boolean replyTo;
	private boolean isConsumed;

	/**
	 * @return the msgStarterId
	 */
	public String getMsgStarterId() {
		return msgStarterId;
	}

	/**
	 * @param msgStarterId
	 *            the msgStarterId to set
	 */
	public void setMsgStarterId(String msgStarterId) {
		this.msgStarterId = msgStarterId;
	}

	/**
	 * @return the replyTo
	 */
	public boolean isReplyTo() {
		return replyTo;
	}

	/**
	 * @param replyTo
	 *            the replyTo to set
	 */
	public void setReplyTo(boolean replyTo) {
		this.replyTo = replyTo;
	}

	/**
	 * @return the isConsumed
	 */
	public boolean isConsumed() {
		return isConsumed;
	}

	/**
	 * @param isConsumed the isConsumed to set
	 */
	public void setConsumed(boolean isConsumed) {
		this.isConsumed = isConsumed;
	}
	
}
