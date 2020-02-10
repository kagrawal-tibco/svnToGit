package com.tibco.cep.kernel.core.base.tuple;

import com.tibco.cep.kernel.model.knowledgebase.SetupException;

/*
* Author: Suresh Subramani / Date: 8/22/12 / Time: 6:15 PM
*/
public interface JoinTableCollection {

    short addTable(JoinTable joinTable) throws SetupException;

    JoinTable getJoinTable(short tableid);

    void removeTable(JoinTable joinTable);

    JoinTable[] toArray();

    void clear();


}
