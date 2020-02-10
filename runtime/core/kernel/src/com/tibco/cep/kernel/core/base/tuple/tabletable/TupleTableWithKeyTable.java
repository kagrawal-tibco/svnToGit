package com.tibco.cep.kernel.core.base.tuple.tabletable;

import com.tibco.cep.kernel.core.base.tuple.TableIterator;
import com.tibco.cep.kernel.core.base.tuple.TupleRowWithKey;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Sep 25, 2009
 * Time: 1:01:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TupleTableWithKeyTable extends TupleTableTable
{
    TableIterator keyIterator(int key);

    Handle[][] getAllRows(int key);
    
    MigrationIterator<TupleRowWithKey> migrationIterator();
}