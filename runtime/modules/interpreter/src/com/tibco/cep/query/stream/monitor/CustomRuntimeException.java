package com.tibco.cep.query.stream.monitor;

/*
 * Author: Ashwin Jayaprakash Date: Nov 27, 2007 Time: 2:31:52 PM
 */

public class CustomRuntimeException extends RuntimeException implements KnownResource {
    private static final long serialVersionUID = 1L;

    protected final ResourceId resourceId;

    public CustomRuntimeException(ResourceId resourceId) {
        this.resourceId = resourceId;
    }

    public CustomRuntimeException(ResourceId resourceId, String message) {
        super(message);

        this.resourceId = resourceId;
    }

    public CustomRuntimeException(ResourceId resourceId, Throwable cause) {
        super(cause);

        this.resourceId = resourceId;
    }

    public CustomRuntimeException(ResourceId resourceId, String message, Throwable cause) {
        super(message, cause);

        this.resourceId = resourceId;
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    @Override
    public String getMessage() {
        String s = super.getMessage();
        return getResourceId().generateSequenceToParentString() + ((s == null) ? "" : " : " + s);
    }
}
