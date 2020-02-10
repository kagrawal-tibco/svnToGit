package com.tibco.rta.common.service.session;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 14/3/13
 * Time: 12:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class SessionCreationException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3829721923407506438L;

	private String sessionId;

    private String sessionName;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public SessionCreationException() {
    }

    public SessionCreationException(String message) {
        super(message);
    }

    public SessionCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionCreationException(Throwable cause) {
        super(cause);
    }
}
