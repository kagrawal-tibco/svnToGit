package com.tibco.cep.studio.dashboard.core.notification;

import java.util.List;


/**
 * Notifier is the interface for an object that can send notifications to an adapter.
 * MDObject, LocalElement are the base classes for all MDXXXX object in MDS and LocalXXXX object in Architect,
 * extends this interface.
 *
 * The notifier's list of adapters can be dynamically added and removed.
 * When notify() is called, each adapter is notified via
 * its notifyChanged() method, provided delivery is enabled.
 * The isDeliver() and setDeliver() methods form a pair of accessors to control and test
 * whether this is the case.
 *
 * @author etam
 * @deprecated
 */
public interface Notifier {

    /**
     * Is there any value to notify the adpater from the notifier point of view?
     *
     * @return: a boolean indicates the intention of the nofitier
     */
    public abstract boolean isDeliver();

    /**
     * Set a flag to indicator whether the notification should happen from the notifier point of view.
     *
     * @param deliver
     */
    public abstract void setDeliver(boolean deliver);

    /**
     * Return the list of <code>Adapter</code>.
     * @param adapter
     */
    public abstract List<Adapter> getAdapters();

    /**
     * Associate an <code>Adapter</code> with this <code>Notifier</code>.
     * @param adapter
     */
    public abstract void addAdapter(Adapter adapter);

    /**
     * Disassociate an <code>Adapter</code> from this <code>Notifier</code>.
     * @param adapter
     */
    public abstract void removeAdapter(Adapter adapter);

    /**
     * Notifies a change to a feature of this notifier as described by the
     * notification.
     *
     * @param notification description of the change
     */
    public abstract void notify(Notification notification);
}
