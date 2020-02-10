package com.tibco.cep.query.model;

/**
 * Window which keeps entities for a period of time.
 */
public interface TimeWindow
    extends Window {

    /**
     * Time units usable in a TimeWindow.
     */
    enum TimeUnit {

        MILLISECONDS(1),
        SECONDS(1000),
        MINUTES(60*1000),
        HOURS(60*60*1000),
        DAYS(24*60*60*1000);

        private long millisecondsEquivalent;

        TimeUnit(long millisecondsEquivalent) {
            this.millisecondsEquivalent = millisecondsEquivalent;
        }

        public long getMillisecondsEquivalent() {
            return this.millisecondsEquivalent;
        }

    }


    /**
     * @return the maximum time an item can stay in the window, expressed using the unit returned by
     * {@link #getTimeUnit()}.
     */
    long getMaxTime();


    /**
     * @return the maximum time an item can stay in the window, expressed in ms
     */
    long getMaxTimeInMs();


    /**
     * @return PurgeClause or null.
     */
    PurgeClause getPurgeClause();


    /**
     * @return TimeUnit (not null).
     */
    TimeUnit getTimeUnit();


    /**
     * @return Expression or null.
     */
    Expression getUsingProperty();
}
