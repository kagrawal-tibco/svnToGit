package com.tibco.rta.impl;

import com.tibco.rta.RtaException;
import com.tibco.rta.RtaNotification;

public class RtaNotificationImpl extends RtaAsyncMessageImpl implements RtaNotification {
	
	private static final long serialVersionUID = -3451561638707945084L;

	protected Status status;
	protected RtaException exception;

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public RtaException getException() {
		return exception;
	}

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setException(RtaException exception) {
        this.exception = exception;
    }
}
