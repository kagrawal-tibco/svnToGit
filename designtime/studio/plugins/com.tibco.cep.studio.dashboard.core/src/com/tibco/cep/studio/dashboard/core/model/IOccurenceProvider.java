package com.tibco.cep.studio.dashboard.core.model;


/**
 * @
 *
 */
public interface IOccurenceProvider {

    public abstract long getMaxOccurs() throws Exception;

    public abstract void setMaxOccurs(long maxOccurs) throws Exception;

    public abstract long getMinOccurs() throws Exception;

    public abstract void setMinOccurs(long minOccurs) throws Exception;
}
