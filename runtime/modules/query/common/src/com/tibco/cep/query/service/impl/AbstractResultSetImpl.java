package com.tibco.cep.query.service.impl;

import java.util.Iterator;
import java.util.List;

import com.tibco.cep.query.service.QueryResultSet;
import com.tibco.cep.query.service.QueryStatement;
import com.tibco.cep.query.service.Tuple;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 2, 2007
 * Time: 1:26:32 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class AbstractResultSetImpl implements QueryResultSet {
    protected QueryStatement queryStatement;
    private List tuples;
    //
    //	protected ExecutionPlan executionPlan;
    //
    //	protected CachedRowSet cachedRowSet;

    protected AbstractResultSetImpl(QueryStatement stmt) {
        this.queryStatement = stmt;
    }

    /**
     * Called for each new "delete" Tuple.
     *
     
     */
    public void onDeletedTuple() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Called for each new InsertedTuple.
     *

     */
    public void onInsertedTuple() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns an iterator over a set of elements of type T.
     *
     * @return an Iterator.
     */
    public Iterator iterator() {
        return tuples.iterator();
    }

    /**
     * Add tuples to the resultset list
     * @param t     
     */
    public void addTuple(Tuple t) {
       tuples.add(t);
    }

}
