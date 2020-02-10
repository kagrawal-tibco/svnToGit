package com.tibco.cep.kernel.core.base.tuple.tabletable;

import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.TableIterator;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Oct 6, 2009
 * Time: 3:26:52 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TableTable
{
    static final TableIterator NULL_ITER = new ZeroLengthIterator();
    
    void lock();

    void unlock();

    String contentHashForm(JoinTable container);

    String contentListForm(JoinTable container);

    TableIterator iterator();
    
    boolean isEmpty();

    int size();

    void reset();
}
