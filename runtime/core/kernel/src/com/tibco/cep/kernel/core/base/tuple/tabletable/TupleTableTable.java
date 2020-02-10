package com.tibco.cep.kernel.core.base.tuple.tabletable;

import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.TupleRow;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Sep 25, 2009
 * Time: 6:55:30 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TupleTableTable extends TableTable
{
    static final Handle[][] NULL_ARR = new Handle[0][];

    boolean add(TupleRow row, JoinTable container);
    
    TupleRow remove(TupleRow row);

    void clearAllElements(JoinTable container);
}