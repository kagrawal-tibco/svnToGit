package com.tibco.cep.studio.dashboard.core.model;

import java.util.List;

import com.tibco.cep.studio.dashboard.core.listeners.ISynElementChangeNotifier;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

/**
 * @
 *  
 */
public interface ILocalParticle extends ISynValidationProvider, ISynElementChangeNotifier, IOccurenceProvider {

    public abstract String getName();

    public abstract void setName(String name);

    /**
     * Returns all the elements (com.tibco.cep.designer.dashboard.core.model.impl.LocalElement)
     * in this partile
     * 
     * @return
     * @see com.tibco.cep.designer.dashboard.core.model.impl.models.LocalElement
     */
    public abstract List<LocalElement> getElements();

    /**
     * Add a LocalElement to this particle
     * 
     * @param localElement
     * @return true is successfull; false if the attempt has failed
     */
    public abstract boolean addElement(LocalElement localElement);

    /**
     * Removes a LocalElement from this particle
     * 
     * @param localElement
     * @return true is successfull; false if the attempt has failed
     */
    public abstract boolean removeElement(String elementName);

    /**
     * Returns the count of elements in this particle
     * 
     * @return
     */
    public abstract int getElementCount();
    
    public abstract boolean isReference();


}
