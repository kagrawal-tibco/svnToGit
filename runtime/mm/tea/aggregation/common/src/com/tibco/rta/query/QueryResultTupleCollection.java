package com.tibco.rta.query;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 21/2/14
 * Time: 11:59 AM
 * <p/>
 * A wrapper collection for tuples.
 */
public class QueryResultTupleCollection<R extends ResultTuple> implements Serializable {

    private List<R> queryResultTuples;

    public QueryResultTupleCollection() {
    }

    public QueryResultTupleCollection(R... queryResultTuples) {
        this(Arrays.asList(queryResultTuples));
    }

    public QueryResultTupleCollection(List<R> queryResultTuples) {
        this.queryResultTuples = queryResultTuples;
    }

    public void setQueryResultTuples(List<R> queryResultTuples) {
        this.queryResultTuples = queryResultTuples;
    }

    public Collection<R> getQueryResultTuples() {
        return Collections.unmodifiableList(queryResultTuples);
    }

    public R get(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index value cannot be less than 0");
        }
        if (index >= queryResultTuples.size()) {
            throw new IllegalArgumentException("Index value out of bounds");
        }
        return queryResultTuples.get(index);
    }
}
