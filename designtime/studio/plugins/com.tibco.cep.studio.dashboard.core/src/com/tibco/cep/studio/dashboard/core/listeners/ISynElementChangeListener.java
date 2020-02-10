package com.tibco.cep.studio.dashboard.core.listeners;

import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;


/**
 * The element change listener
 *
 */
public interface ISynElementChangeListener extends ISynPropertyChangeListener {

    /**
     * Called when an element is added
     * @throws Exception
     */
    public abstract void elementAdded(IMessageProvider parent, IMessageProvider newElement);

    /**
     * Called when an element is removed
     * @throws Exception
     */
    public abstract void elementRemoved(IMessageProvider parent, IMessageProvider removedElement);

    /**
     * Called when an element has been changed
     * @throws Exception
     */
    public abstract void elementChanged(IMessageProvider parent, IMessageProvider changedElement);

    /**
     * Called when an element's status is changed
     * @throws Exception
     */
    public abstract void elementStatusChanged(IMessageProvider parent, InternalStatusEnum status);

    public abstract String getName();

}
