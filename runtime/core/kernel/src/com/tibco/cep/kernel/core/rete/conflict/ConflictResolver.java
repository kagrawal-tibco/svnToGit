package com.tibco.cep.kernel.core.rete.conflict;

import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.rule.Rule;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 26, 2006
 * Time: 2:52:21 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ConflictResolver {

    int size();

    AgendaItem put(Rule rule, Handle[] handles);

    boolean isEmpty();

    AgendaItem getNext();

    void objectRemoved(Handle objHandle);

    void objectModified(Handle objHandle, int[] overrideDirtyBitArray);

    void reset();

    void printAgenda(StringBuffer buffer);

    AgendaItem[] agendaToArray();
}
