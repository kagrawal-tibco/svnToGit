package com.tibco.cep.runtime.model.event;


import java.util.Calendar;

import com.tibco.cep.kernel.model.entity.Event;


/**
 * Base for time-based events.
 *
 * @version 2.0.0
 * @.category public-api
 * @see SimpleEvent
 * @since 2.0.0
 */
public interface TimeEvent extends Event {


    /**
     * Represents the time unit: milliseconds.
     *
     * @.category public-api
     * @since 2.0.0
     */
    final static int INTERVAL_UNIT_MILLISECONDS = 0;

    /**
     * Represents the time unit: seconds.
     *
     * @.category public-api
     * @since 2.0.0
     */
    final static int INTERVAL_UNIT_SECONDS = 1;

    /**
     * Represents the time unit: minutes.
     *
     * @.category public-api
     * @since 2.0.0
     */
    final static int INTERVAL_UNIT_MINUTES = 2;

    /**
     * Represents the time unit: hours.
     *
     * @.category public-api
     * @since 2.0.0
     */
    final static int INTERVAL_UNIT_HOURS = 3;

    /**
     * Represents the time unit: days.
     *
     * @.category public-api
     * @since 2.0.0
     */
    final static int INTERVAL_UNIT_DAYS = 4;


    /**
     * Specifies whether the event is a repeating event.
     *
     * @return true if the event is a repeating event.
     * @.category public-api
     * @since 2.0.0
     */
    public boolean isRepeating();


    /**
     * Gets the repeating interval in milliseconds.
     *
     * @return repeating interval in millseconds.
     * @.category public-api
     * @since 2.0.0
     */
    public long getInterval();


    /**
     * Sets the closure of this time event.
     *
     * @param closure the <code>String</code> closure to be set.
     * @.category public-api
     * @since 2.0.0
     */
    void setClosure(String closure);


    /**
     * Gets the closure of this event.
     *
     * @return closure of this event.
     * @.category public-api
     * @since 2.0.0
     */
    String getClosure();


    /**
     * Sets the schedule time of this event.
     *
     * @param time a <code>Calendar</code> specifying the time to schedule.
     * @.category public-api
     * @since 2.0.0
     */
    public void setScheduledTime(Calendar time);


    /**
     * Sets the schedule time of this time event.
     *
     * @param time a <code>long</code> specifying the time to schedule in milliseconds since the epoch.
     * @.category public-api
     * @since 2.0.0
     */
    public void setScheduledTime(long time);


    /**
     * Gets the schedule time of this time event.
     *
     * @return a <code>Calendar</code> specifying the scheduled time of this event.
     * @.category public-api
     * @since 2.0.0
     */
    public Calendar getScheduledTime();


    /**
     * Gets the schedule time of this time event in milliseconds since the epoch.
     *
     * @return the schedule time of this time event in milliseconds since the epoch.
     * @.category public-api
     * @since 2.0.0
     */
    public long getScheduledTimeMillis();

    
    void fire();


    boolean isFired();


    void serialize(EventSerializer serializer);


    void deserialize(EventDeserializer deserializer);


}
