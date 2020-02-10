package com.tibco.store.persistence.model;


import com.tibco.store.query.model.Predicate;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 30/11/13
 * Time: 7:02 PM
 *
 * Memory tables are loose equivalents of database tables.
 */
public interface MemTable<S extends SearchResult> {

    /**
     *
     * @param filter
     * @param
     * @return
     */
    public <R extends Predicate> S lookup(R filter);
}
