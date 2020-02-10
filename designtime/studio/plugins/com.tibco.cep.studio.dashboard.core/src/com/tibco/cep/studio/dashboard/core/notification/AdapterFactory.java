package com.tibco.cep.studio.dashboard.core.notification;

/**
 * The Adapter Factory interface defines the behavior of factories that create adapters and
 * associate them with notifiers. To obtain an adapter for a given notifier and of a given
 * type, adapt() is called. If the notifier already has the correct type of adapter associated
 * with it, that adapter is returned; otherwise, a new adapter is created by calling
 * adaptNew(), which also adds the adapter to the notifier's adapter list and calls setTarget()
 * on the adapter.
 *
 * Like adapters themselves, adapter factories may specify if they support a particular type,
 * via the isFactoryForType() method.
 *
 * @author etam
 * @deprecated
 */
public interface AdapterFactory {

    // Public Instance methods

    public abstract Object adapt(Object object, Object type);

    public abstract Adapter adapt(Notifier target, Object type);

    public abstract void adaptAllNew(Notifier notifier);

    public abstract Adapter adaptNew(Notifier target, Object type);

    public abstract boolean isFactoryForType(Object type);
}
