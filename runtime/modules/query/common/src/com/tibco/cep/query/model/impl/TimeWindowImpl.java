package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.PurgeClause;
import com.tibco.cep.query.model.StreamPolicy;
import com.tibco.cep.query.model.TimeWindow;
import com.tibco.cep.query.model.WhereClause;
import com.tibco.cep.query.model.validation.validators.TimeWindowValidator;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 4, 2007
 * Time: 8:53:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimeWindowImpl
        extends AbstractQueryContext
        implements TimeWindow {


    private long size;
    private TimeUnit timeUnit;


    public TimeWindowImpl(StreamPolicy policy, CommonTree tree, long size, String unit) {
        super(policy, tree);
        this.size = size;

        if ("milliseconds".equalsIgnoreCase(unit)) {
            this.timeUnit = TimeUnit.MILLISECONDS;
        } else if ("seconds".equalsIgnoreCase(unit)) {
            this.timeUnit = TimeUnit.SECONDS;
        } else if ("minutes".equalsIgnoreCase(unit)) {
            this.timeUnit = TimeUnit.MINUTES;
        } else if ("hours".equalsIgnoreCase(unit)) {
            this.timeUnit = TimeUnit.HOURS;
        } else if ("days".equalsIgnoreCase(unit)) {
            this.timeUnit = TimeUnit.DAYS;
        } else {
            throw new IllegalArgumentException("Unknown time unit");
        }
    }


    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_TIME_WINDOW;
    }


    /**
     * @return the maximum time an item can stay in the window, expressed using the unit returned by
     *         {@link #getTimeUnit()}.
     */
    public long getMaxTime() {
        return this.size;
    }


    /**
     * @return the maximum time an item can stay in the window, expressed in ms
     */
    public long getMaxTimeInMs() {
        return this.timeUnit.getMillisecondsEquivalent() * this.size;
    }

    /**
     * @return PurgeClause or null.
     */
    public PurgeClause getPurgeClause() {
        ModelContext[] children = this.getDescendantContextsByType(this, ModelContext.CTX_TYPE_PURGE_CLAUSE);
        if (children.length > 0) {
            return (PurgeClause) children[0];
        }
        return null;
    }

    /**
     * @return TimeUnit (not null).
     */
    public TimeUnit getTimeUnit() {
        return this.timeUnit;
    }

    /**
     * @return String or null.
     */
    public Expression getUsingProperty() {
        if(getChildCount() == 0) {
            return null;
        }
        
        return (Expression) getChild(0);
    }

    /**
     * @return WhereClause or null.
     */
    public WhereClause getWhereClause() {
        ModelContext[] children = this.getDescendantContextsByType(this, ModelContext.CTX_TYPE_WHERE);
        if (children.length > 0) {
            return (WhereClause) children[0];
        }
        return null;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof TimeWindowImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final TimeWindowImpl that = (TimeWindowImpl) o;

        return this.size * this.timeUnit.getMillisecondsEquivalent()
                == that.size * that.timeUnit.getMillisecondsEquivalent();
    }

    public int hashCode() {
        final long sizeInMs = this.size * this.timeUnit.getMillisecondsEquivalent();
        return (int) (sizeInMs ^ (sizeInMs >>> 32));
    }


    public void validate() throws Exception {
        new TimeWindowValidator().validate(this);
    }
}
