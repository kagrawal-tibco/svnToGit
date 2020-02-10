package com.tibco.cep.studio.dashboard.core.listeners;

/**
 * 
 *  
 */
public interface ISynElementChangeNotifier extends ISynPropertyChangeNotifier {
    

    public abstract void fireElementAdded(ISynElementChangeNotifier parent, Object newElement);


    public abstract void fireElementRemoved(ISynElementChangeNotifier parent, Object removedElement);


    public abstract void fireElementChanged(ISynElementChangeNotifier parent, Object removedElement);

}