package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.SortCriterion;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 22, 2007
 * Time: 4:37:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class SortCriterionImpl extends AbstractQueryContext implements SortCriterion {


    private Direction direction;
    private Object limitFirst;
    private Object limitOffset;
    protected static final Integer DEFAULT_LIMIT_FIRST = Integer.MAX_VALUE;
    protected static final Integer DEFAULT_LIMIT_OFFSET = 0;


    /**
     * ctor
     *
     * @param parentContext
     * @param tree
     */
    public SortCriterionImpl(ModelContext parentContext, Direction direction, Object limitFirst, Object limitOffset,
                             CommonTree tree) {
        super(parentContext, tree);
        this.direction = direction;
        this.limitFirst = (null == limitFirst) ? DEFAULT_LIMIT_FIRST : limitFirst;
        this.limitOffset = (null == limitOffset) ? DEFAULT_LIMIT_OFFSET :  limitOffset;
    }


    /**
     * @return Direction or null;
     */
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_SORT_CRITERION;
    }

    /**
     * @return Expression part of the sort criterion.
     */
    public Expression getExpression() {
        return (Expression) this.getChild(0);
    }

    /**
     * @return Bindvariable or Integer between 1 and Integer.MAX_VALUE
     */
    public Object getLimitFirst() {
        return this.limitFirst;
    }

    /**
     * @return Object Bindvariable or Integer between 1 and Integer.MAX_VALUE
     */
    public Object getLimitOffset() {
        return this.limitOffset;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof SortCriterion)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final SortCriterion that = (SortCriterion) o;
        return (this.getDirection() == that.getDirection())
                && (this.getLimitFirst() == that.getLimitFirst())
                && (this.getLimitOffset() == that.getLimitOffset())
                && this.getExpression().equals(that.getExpression());
    }

    public int hashCode() {
        long longHash = this.direction.hashCode();
        longHash = 29 * longHash + ((null == this.limitFirst) ? 0 : this.limitFirst.hashCode());
        longHash ^= (longHash >>> 32);
        longHash = 29 * longHash + ((null == this.limitOffset) ? 0 : this.limitOffset.hashCode());
        longHash ^= (longHash >>> 32);
        longHash = 29 * longHash + this.getExpression().hashCode();
        longHash ^= (longHash >>> 32);
        return (int) longHash;
    }


    /**
     * @return true iif this object uses a non-default value in one of the Limit parameters
     */
    public boolean usesNonDefaultLimit() {
        return (!DEFAULT_LIMIT_FIRST.equals(this.limitFirst))
                || (!DEFAULT_LIMIT_OFFSET.equals(this.limitOffset));
    }
}
