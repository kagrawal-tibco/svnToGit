package com.tibco.cep.kernel.core.base.tuple;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jul 1, 2004
 * Time: 3:10:29 PM
 * To change this template use Options | File Templates.
 */
public interface TupleElement {

    /**
     * register the TupleRow to this TupleElement
     * @param row
     */
    void registerTupleRow(short tableId, TupleRow row);

    /**
     * unregister the TupleRow from this Tuple Element
     * @param row
     */
    void unregisterTupleRow(short tableId, TupleRow row);

    /**
     * unregister all the TupleRows for specified table from this Tuple Element
     * @param tableId
     */
    void unregisterTupleRows(short tableId);

    /**
     *  remove this Tuple Element and its registered tuples from all the Tuple Tables
     */
    void removeFromTables();

    /** remove this Tuple Element and its registered tuples from the specified Tuple Table
     * @param tableId
     */
    void removeFromTable(short tableId);

}
