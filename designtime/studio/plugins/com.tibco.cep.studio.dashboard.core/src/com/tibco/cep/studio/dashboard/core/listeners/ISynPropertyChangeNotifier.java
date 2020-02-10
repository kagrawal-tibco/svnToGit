package com.tibco.cep.studio.dashboard.core.listeners;

import java.util.EventListener;
import java.util.List;

/**
 * @deprecated
 *
 */
public interface ISynPropertyChangeNotifier {

    /*
     * @deprecated
     */
    public abstract void firePropertyChanged(ISynPropertyChangeNotifier prop, Object oldValue, Object newValue);

    /*
     * @deprecated
     */
    public abstract void addListener(EventListener listener);

    /*
     * @deprecated
     */
    public abstract void removeListener(EventListener listener);

    /*
     * @deprecated
     */
    public abstract List<EventListener> getListeners();
}