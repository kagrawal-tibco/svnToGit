package com.tibco.cep.kernel.core.base.tuple.tabletable;

import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Sep 25, 2009
 * Time: 1:01:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ObjectTableNoKeyTable extends ObjectTableTable
{
    boolean add(Handle objHandle);

    Handle[] getAllHandles();
}
