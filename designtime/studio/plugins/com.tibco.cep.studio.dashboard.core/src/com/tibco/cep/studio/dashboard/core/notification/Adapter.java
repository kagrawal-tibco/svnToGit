package com.tibco.cep.studio.dashboard.core.notification;


/**
 * Adapter specifies the interface used throughout MDS for receiving notifications of changes
 * to model objects and for extending the behavior of those objects.
 *
 * The object that an adapter receives notifications for, which must implement the Notifier
 * interface, is called its notifier in Notification object.
 *
 * A notifier notifies its adapter of changes by calling their notify() methods.
 *
 * Because notifiers can have multiple adapters, the isAdapterForType() method is used
 * by and adapter factory to determine which, if any, adapter to return for a given notifier.
 * The type can be any object that is meaningful for the particular adapter.
 *
 * When an adapter is used to support a particular interface, the corresponding instance of
 * java.lang.Class might be an appropriate choice.
 *
 *
 * @author etam
 * @deprecated
 */
public interface Adapter {

    // Public Instance methods

    public abstract boolean isAdapterForType(Object type);

    /**
     * Notifies that a change to some feature has occurred.
     *
     * @param notice a description of the change
     */
    public void notifyChanged(Notification notice);

}
