package com.tibco.cep.query.stream.impl.rete.integ.filter.coherence;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.InvocationService;
import com.tangosol.net.Member;
import com.tangosol.net.NamedCache;
import com.tangosol.util.ValueExtractor;
import com.tangosol.util.extractor.ChainedExtractor;
import com.tangosol.util.extractor.ReflectionExtractor;

/*
* Author: Karthikeyan Subramanian / Date: Apr 7, 2010 / Time: 11:29:01 AM
*/
public class IndexManagerImpl implements IndexManager {
    
    public static final String INVOCATION_SERVICE_NAME = "InvocationService";
    private final ConcurrentMap<IndexInfo, Set<String>> indices =
            new ConcurrentHashMap<IndexInfo, Set<String>>();
    
    public IndexManagerImpl(NamedCache cache) {
        List<IndexInfo> indexList = discoverIndexInfos(cache);
        if(indexList != null) {
            for(IndexInfo indexInfo : indexList) {
                Set<String> extractors = new HashSet<String>();
                if(indexInfo.getExtractor() instanceof ReflectionExtractor) {
                    ReflectionExtractor reflectionExtractor = (ReflectionExtractor)indexInfo.getExtractor();
                    extractors.add(reflectionExtractor.getMethodName());
                } else if(indexInfo.getExtractor() instanceof ChainedExtractor) {
                    ChainedExtractor chainedExtractor = (ChainedExtractor) indexInfo.getExtractor();
                    for(ValueExtractor extractor : chainedExtractor.getExtractors()) {
                        ReflectionExtractor reflectionExtractor = (ReflectionExtractor)extractor;
                        extractors.add(reflectionExtractor.getMethodName());
                    }
                }
                indices.putIfAbsent(indexInfo, extractors);
            }
        }
    }

    @Override
    public void addIndex(IndexInfo index, Set<String> extractors) {
        indices.putIfAbsent(index, extractors);
    }

    public List<IndexInfo> discoverIndexInfos(NamedCache cache) {
        InvocationService invocationService = (InvocationService) CacheFactory.getService(INVOCATION_SERVICE_NAME);
        invocationService.setContextClassLoader(cache.getCacheService().getContextClassLoader());
        invocationService.setUserContext(cache.getCacheService().getUserContext());
        String resultKey = Thread.currentThread().getName() + ":" + System.nanoTime();
        Member member = CacheFactory.getCluster().getLocalMember();
        IndexFinderFilter filter = new IndexFinderFilter(resultKey, member);
        if(cache.isActive() == true) {
            cache.keySet(filter);
            return filter.getIndices(resultKey);
        }
        return Collections.emptyList();
    }    

    @Override
    public Map<IndexInfo, Set<String>> getAllIndices() {
        return Collections.unmodifiableMap(indices);
    }

    @Override
    public IndexInfo getIndexInfo(String columnName) {
        String method = "get" + columnName;
        for(IndexInfo indexInfo : indices.keySet()) {
            Set<String> methods = indices.get(indexInfo);
            if(methods.contains(method)) {
                return indexInfo;
            }
        }
        return null;
    }
}
