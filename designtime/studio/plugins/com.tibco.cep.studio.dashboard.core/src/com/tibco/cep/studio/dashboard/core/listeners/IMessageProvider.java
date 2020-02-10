package com.tibco.cep.studio.dashboard.core.listeners;

import java.util.List;

import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;

/**
 * @
 *  
 */
public interface IMessageProvider {

    public abstract String getProviderType();
    
    public abstract List<String> getProviderTypes();

    public abstract List<String> getTopics();
    
    public abstract boolean hasSubscriber();

    public abstract void subscribe(ISynElementChangeListener subscriber, String topicName);
    
    public abstract void subscribe(ISynElementChangeListener subscriber, List<String> topicList);

    public abstract void subscribeToAll(ISynElementChangeListener subscriber);

    public abstract void unsubscribeAll(ISynElementChangeListener subscriber);

    public abstract void pause(ISynElementChangeListener subscriber);

    public abstract void resume(ISynElementChangeListener subscriber);

    public abstract void unsubscribe(ISynElementChangeListener subscriber, String topicName);

    public abstract void unsubscribe(ISynElementChangeListener subscriber, List<String> topicList);

    public abstract List<String> getSubscribedTopics(ISynElementChangeListener subscriber);
    
    public abstract void fireStatusChanged(IMessageProvider provider, InternalStatusEnum status) throws Exception;
    
    public abstract void fireElementAdded(IMessageProvider provider, IMessageProvider newElement) throws Exception;

    public abstract void fireElementRemoved(IMessageProvider provider, IMessageProvider removedElement) throws Exception;

    public abstract void fireElementChanged(IMessageProvider provider, IMessageProvider changedElement) throws Exception;
    
    public abstract void firePropertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) throws Exception;

    
}
