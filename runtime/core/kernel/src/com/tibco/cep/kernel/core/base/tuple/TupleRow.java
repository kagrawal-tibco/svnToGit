package com.tibco.cep.kernel.core.base.tuple;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 7, 2006
 * Time: 1:11:03 AM
 * To change this template use File | Settings | File Templates.
 */
public interface TupleRow {

    void removeTupleFromTable(short tableId);

    void associateTupleElements(short tableId);

    void unassociateTupleElements(short tableId);

    boolean isThreadLocal();
}
