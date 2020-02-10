package com.tibco.cep.query.model;

/**
 * Window which keeps a maximum number of entities.
 */
public interface SlidingWindow
    extends TumblingWindow {


    /**
     * @return PurgeClause or null.
     */
    PurgeClause getPurgeClause();

}
