package com.tibco.rta;

/**
 * A key to indicate which events clients would be interested in
 * for receiving asynchronous notifications from metric engine.
 */
public class NotificationListenerKey {

    private int interestEvents;

    /**
     * Key for registering interest in server up/down notifications.
     */
    public static final int EVENT_SERVER_HEALTH = 1 << 1;

    /**
     * Key for registering interest in Fact publish notifications.
     */
    public static final int EVENT_FACT_PUBLISH = 1 << 2;

    /**
     * Key for registering interest in Fact publish rejections.
     */
    public static final int EVENT_FACT_DROP = 1 << 3;

    /**
     * Key for registering interest in task submission rejections.
     * @see com.tibco.rta.client.taskdefs.ConnectionAwareTaskManager
     */
    public static final int EVENT_TASK_REJECT = 1 << 4;

    /**
     * Tests whether this key is interested in receiving server health notifications.
     * @return  <tt>true</tt> if, and only if,
     *          <tt>interestEvents()</tt>&nbsp;<tt>&</tt>&nbsp;<tt>EVENT_SERVER_HEALTH</tt> is
     *          nonzero
     */
    public final boolean isInterestedInServerHealth() {
	    return (interestEvents & EVENT_SERVER_HEALTH) != 0;
    }

    /**
     * Tests whether this key is interested in receiving fact publish notifications.
     * @return  <tt>true</tt> if, and only if,
     *          <tt>interestEvents()</tt>&nbsp;<tt>&</tt>&nbsp;<tt>EVENT_FACT_PUBLISH</tt> is
     *          nonzero
     */
    public final boolean isInterestedInFactPublish() {
	    return (interestEvents() & EVENT_FACT_PUBLISH) != 0;
    }

    /**
     * Tests whether this key is interested in receiving fact publish rejections.
     * @return  <tt>true</tt> if, and only if,
     *          <tt>interestEvents()</tt>&nbsp;<tt>&</tt>&nbsp;<tt>EVENT_FACT_DROP</tt> is
     *          nonzero
     */
    public final boolean isInterestedInFactDrops() {
	    return (interestEvents() & EVENT_FACT_DROP) != 0;
    }

    /**
     * Tests whether this key is interested in receiving task submission rejections.
     * <p>
     *     It is recommended that API users always register interest in this key since
     *     tasks may get rejected for various reasons.
     * </p>
     * @return  <tt>true</tt> if, and only if,
     *          <tt>interestEvents()</tt>&nbsp;<tt>&</tt>&nbsp;<tt>EVENT_TASK_REJECT</tt> is
     *          nonzero
     *
     */
    public final boolean isInterestedInTaskRejections() {
	    return (interestEvents() & EVENT_TASK_REJECT) != 0;
    }


    /**
     * Set list of events interested in.
     */
    public void setInterestEvents(int interestEvents) {
        this.interestEvents = interestEvents;
    }

    /**
     * Get current list of events interested in.
     */
    public int interestEvents() {
        return interestEvents;
    }
}
