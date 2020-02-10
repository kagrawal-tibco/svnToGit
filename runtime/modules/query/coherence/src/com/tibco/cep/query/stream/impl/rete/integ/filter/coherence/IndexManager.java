package com.tibco.cep.query.stream.impl.rete.integ.filter.coherence;

import java.util.Map;
import java.util.Set;

/*
* Author: Karthikeyan Subramanian / Date: Apr 7, 2010 / Time: 3:05:00 PM
*/
public interface IndexManager {

    Map<IndexInfo, Set<String>> getAllIndices();

    IndexInfo getIndexInfo(String columnName);

    void addIndex(IndexInfo index, Set<String> extractors);
}
