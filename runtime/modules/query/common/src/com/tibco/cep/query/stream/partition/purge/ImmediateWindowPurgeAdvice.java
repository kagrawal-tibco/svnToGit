package com.tibco.cep.query.stream.partition.purge;

import java.sql.Timestamp;

/*
 * Author: Ashwin Jayaprakash Date: Nov 20, 2007 Time: 4:10:15 PM
 */

/**
 * <p> Purge advice is requested at the beginning of the cycle, however the {@link WindowStats} are
 * from the previous cycle. </p> <p> Either {@link #getFirstX()} or {@link
 * #getOldestAllowedTimestamp()} can be <code>null</code> but <b>never both</b> - Use {@link
 * #useFirstXInsteadOfTimestamp()} to find out. </p> <p> Refrain from sending {@link
 * #getOldestAllowedTimestamp()} advise if there are no Tuples, as it would result in unnecessary
 * Query cycles. </p>
 */
public class ImmediateWindowPurgeAdvice implements WindowPurgeAdvice {
    protected int firstX;

    protected boolean firstXInsteadOfTimestamp;

    protected final Timestamp oldestAllowedTimestamp;

    public ImmediateWindowPurgeAdvice(Integer firstX) {
        this(firstX, null);
    }

    public ImmediateWindowPurgeAdvice(Timestamp oldestAllowedTimestamp) {
        this(null, oldestAllowedTimestamp);
    }

    protected ImmediateWindowPurgeAdvice(Integer firstX, Timestamp oldestAllowedTimestamp) {
        this.firstX = firstX;
        this.firstXInsteadOfTimestamp = (firstX != null);
        this.oldestAllowedTimestamp = oldestAllowedTimestamp;
    }

    public boolean useFirstXInsteadOfTimestamp() {
        return firstXInsteadOfTimestamp;
    }

    /**
     * Should not be invoked if {@link #useFirstXInsteadOfTimestamp()} returns <code>false</code>.
     *
     * @return
     */
    public int getFirstX() {
        return firstX;
    }

    /**
     * Can be <code>null</code>.
     *
     * @return
     */
    public Timestamp getOldestAllowedTimestamp() {
        return oldestAllowedTimestamp;
    }

    /**
     * <p>To be used only if {@link #useFirstXInsteadOfTimestamp()} returns <code>true</code>.</p>
     * <p>Stops decrementing if value has reached 0.</p>
     *
     * @return <code>true</code> if value was decremented.
     */
    public boolean tryDecrementingFirstX() {
        if (firstX > 0) {
            firstX--;

            return true;
        }

        return false;
    }
}