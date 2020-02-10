package com.tibco.cep.query.stream.impl.rete.integ.standalone;

import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;

/*
* Author: Ashwin Jayaprakash Date: Apr 10, 2009 Time: 3:20:37 PM
*/
public interface DelegateRepository extends SharedObjectSourceRepository {
    void init(QueryWorkingMemory wm);

    @Override
    QueryTypeInfo getSource(String name);
}
