package com.tibco.cep.query.stream.impl.rete.integ.standalone;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.query.stream.cache.SharedObjectSource;

/*
 * Author: Ashwin Jayaprakash Date: Mar 10, 2008 Time: 11:37:15 AM
 */

public interface QueryTypeInfo extends SharedObjectSource, TypeInfo {
    Entity handleDownloadedEntity(Object o, Long id);
}
