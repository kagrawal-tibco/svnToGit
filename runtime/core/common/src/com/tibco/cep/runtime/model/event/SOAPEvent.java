package com.tibco.cep.runtime.model.event;

public interface SOAPEvent extends SimpleEvent {

    /**
     * 
     * @return soapAction if this event extends from a soap event 
     */
    public String getSoapAction();

    /**
     * set soapAction
     * @return  
     */
    public void setSoapAction(String soapAction); 
}
