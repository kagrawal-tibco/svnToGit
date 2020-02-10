package com.tibco.cep.studio.dashboard.core.model.XSD.interfaces;

/**
 * @
 *
 */
public interface ISynXSDAnnotation {

    
    /**
     * 
     * @return String A message content meant for mechanical processing.
     * This is annalogous to processing hints
     */
    public abstract String getAppInfo();
    
    
    /**
     * 
     * @param value
     */
    public abstract void setAppInfo(String appInfo);
    
    /**
     * 
     * @return String A message content meant for human consumption.
     * This is annalogous to 'description'
     */
    public abstract String getDocumentation();
    
    
    /**
     * 
     *
     */
    public abstract void setDocumentation(String documentation);
    
    public abstract Object cloneThis() ;
}
