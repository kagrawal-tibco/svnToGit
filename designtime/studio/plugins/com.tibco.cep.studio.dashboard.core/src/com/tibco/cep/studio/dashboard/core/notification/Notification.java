package com.tibco.cep.studio.dashboard.core.notification;


/**
 * The Notification interface defines the type of object that describes a change to a notifier.
 * It is passed to an adapter's notifyChanged() method.
 *
 * A notification can specify the notifier object, the type of change event, the feature that changed,
 * the position whithin a list-based or array-based feature at which the change occurred, and
 * the old and new vaues of the feature. Notification defines a number of constants for event types,
 * such as CREATE, SET, UNSET, and ADD.
 *
 * For efficiency, special primitive-typed methods are included for accessing the old and
 * new values, avoiding the need to wrap these values in objects. Also, getFeatureID() can be
 * used to return a numeric fature ID to enable use of efficient switch statements in adapters.
 * Otherwise, the NO_FEATURE_ID constant should be returned.
 *
 * The isTouch() method specifies whether the notification represents an event in which
 * the state of notifier did not change; isReset() specifies whether a feature was set to its default
 * value.
 * The merge() method supports merging compatible notifications. If possible, it modifies a
 * notification to include the information contained in the other notification,
 * specified as a parameter, and returns true.
 *
 * In Release 4.0, we just implement the enough feature to do Architect refresh. However,
 * the above represents the road map of local notification of MDS.
 *
 * @author etam
 * @deprecated
 */
public class Notification {


    // The first 10,000 numbers are reserved for Notification class.
    // The next 100 numbers starting from 10,000 are reserved for MetaDataEvent
    // which is a subclass of Notification.

    /**
     * An {@link Notification#getEventType event type} indicating that a value
     * has been inserted into a list-based feature of the notifier.
     *
     * @see Notification#getEventType
     */
    public final static int ADD = 1;
    /**
     * An {@link Notification#getEventType event type} indicating that a value
     * has been removed from a list-based feature of the notifier.
     *
     * @see Notification#getEventType
     */
    public final static int REMOVE = 2;
    /**
     * An {@link Notification#getEventType event type} indicating that a feature
     * of the notifier has been set. This applies for simple features.
     *
     * @see Notification#getEventType
     */

    public final static int SET = 3;
    /**
     * An {@link Notification#getEventType event type} indicating that a feature
     * of the notifier has been refreshed. This applies for simple features.
     *
     * @see Notification#getEventType
     */

    public final static int LOCAL_REFRESH = 4;

    public final static int REMOTE_REFRESH = 5;

    // The first 10,000 numbers are reserved for Notification class.
    // The next 100 numbers starting from 10,000 are reserved for MetaDataEvent
    // which is a subclass of Notification.

    protected Object _oldValue;
    protected Object _newValue;
    protected Notifier _notifier; // which object triggers this notification.
    protected String _featureID; // Normally, it is particle name in LocalXXXX land.
    protected int _type; // ADD, REMOVE, SET, REFRESH

    public Notification(Notifier notifier) {
        _notifier = notifier;
    }

    public Notification(int type, Notifier notifier, String featureID, Object oldValue, Object newValue) {
        _type = type;
        _oldValue = oldValue;
        _newValue = newValue;
        _notifier = notifier;
        _featureID = featureID;
    }
    /**
     * Returns the type of change that has occurred. The valid types of events
     * are defined by the constants in this class.
     *
     * @return the type of change that has occurred.
     * @see Notifier
     */
    public int getEventType() {
        return _type;
    }

    public String getFeatureID() {
        return _featureID;
    }
    /**
     * Returns the object affected by the change.
     *
     * @return the object affected by the change.
     */
    public Object getNotifier() {
        return _notifier;
    }
    /**
     * Returns the value of the notifier's feature before the change occurred.
     * For a list-based feature, this represents a value, or a list of values,
     * removed from the list.
     *
     * @return the old value of the notifier's feature.
     */
    public Object getOldValue() {
        return _oldValue;
    }
    /**
     * Returns the value of the notifier's feature after the change occurred.
     * For a list-based feature, this represents a value, or a list of values,
     * added to the list.
     *
     * @return the new value of the notifier's feature.
     */
    public Object getNewValue() {
        return _newValue;
    }

    public String toString() {
        String notificationType = "?";
        switch (getEventType()) {
        case ADD:
            notificationType = "Add";
            break;
        case REMOVE:
            notificationType = "Remove";
            break;
        case SET:
            notificationType = "Set";
            break;
        case LOCAL_REFRESH:
            notificationType = "Local Refresh";
            break;
        case REMOTE_REFRESH:
            notificationType = "Remote Refresh";
            break;
        }
        return "Notification: type=" + notificationType + ", notifier=" + getNotifier()
                + ", feature ID=" + getFeatureID() + ", old value="
                + getOldValue() + ", new value=" + getNewValue();
    }
}
