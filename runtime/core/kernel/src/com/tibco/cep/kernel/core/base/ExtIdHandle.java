package com.tibco.cep.kernel.core.base;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Nov 20, 2009
 * Time: 6:28:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ExtIdHandle extends BaseHandleWrapper
{
    String getExtId();
    
    ExtIdHandle getNextExtIdEntry();
    
    void setNextExtIdEntry(ExtIdHandle next);

    HandleType getHandleType();
    
    public static enum HandleType {
        EVENT, ELEMENT, ENTITY;
        
        public boolean isType(ExtIdHandle handle) {
            return handle != null && handle.getHandleType() == this;
        }
    }
}
