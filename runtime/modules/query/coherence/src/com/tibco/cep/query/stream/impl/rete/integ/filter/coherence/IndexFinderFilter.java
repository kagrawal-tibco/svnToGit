package com.tibco.cep.query.stream.impl.rete.integ.filter.coherence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.tangosol.net.AbstractInvocable;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.InvocationService;
import com.tangosol.net.Member;
import com.tangosol.util.Filter;
import com.tangosol.util.SimpleMapIndex;
import com.tangosol.util.filter.IndexAwareFilter;

/**
 * This implementation is taken from OTN forum thread.
 * http://forums.oracle.com/forums/thread.jspa?messageID=2440167&#2440167
 */

public class IndexFinderFilter implements IndexAwareFilter, Serializable {
    private static final long serialVersionUID = 1L;

    private static ConcurrentMap<String, List<IndexInfo>> results =
            new ConcurrentHashMap<String, List<IndexInfo>>();

    public static class ResultSettingInvocable extends AbstractInvocable {

        private String resultKey;
        private List<IndexInfo> indexInfos;

        public ResultSettingInvocable(String resultKey, List<IndexInfo> indexInfos) {
            super();
            this.resultKey = resultKey;
            this.indexInfos = indexInfos;
        }

        @Override
        public void run() {
            System.err.println("ResultSettingInvocable");
            results.putIfAbsent(resultKey, indexInfos);
        }
    }

    private String resultKey;

    private Member member;

    public IndexFinderFilter() {
    }

    public IndexFinderFilter(String resultKey, Member member) {
        super();
        this.resultKey = resultKey;
        this.member = member;
    }

    @Override
    public Filter applyIndex(Map mapIndexes, Set keys){
        System.err.println("applyIndex");
        sendExtractors(mapIndexes);
        return null;
    }

    @Override
    public int calculateEffectiveness(Map mapIndexes, Set keys){
        System.err.println("calculateEffectiveness");
        sendExtractors(mapIndexes);
        return 0;
    }

    private void sendExtractors(Map mapIndexes) {
        List<IndexInfo> indexInfos = new ArrayList<IndexInfo>(mapIndexes.size());
        for(Object closure : mapIndexes.values()) {
            SimpleMapIndex mapIndex = (SimpleMapIndex)closure;
            indexInfos.add(new IndexInfo(mapIndex.isOrdered(), mapIndex.getValueExtractor()));
        }
        InvocationService service =
                (InvocationService) CacheFactory.getService(IndexManagerImpl.INVOCATION_SERVICE_NAME);
        service.query(new ResultSettingInvocable(resultKey, indexInfos), Collections.singleton(member));
    }

    /**
     * This is the code that determines if the given map is sorted in ascending
     * or descending order.
     * @param entry
     * @return
     */
//    private boolean isAscending(Map contentMap) {
//        if(contentMap.size() <= 1) {
//            return false;
//        }
//        if(contentMap instanceof SortedMap) {
//            SortedMap sortedContents = (SortedMap)contentMap;
//            if(sortedContents.keySet().isEmpty() == true) {
//                return false;
//            }
//            Iterator keyIterator = sortedContents.keySet().iterator();
//            Comparator comparator = sortedContents.comparator();
//            Object first = keyIterator.next();
//            Object second = keyIterator.next();
//            if(comparator.compare(first, second) > 0) {
//                return false;
//            } else {
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    public boolean evaluateEntry(Entry entry) {
        return false;
    }

    @Override
    public boolean evaluate(Object value){
        return false;
    }

    public List<IndexInfo> getIndices(String resultKey) {
        return results.remove(resultKey);
    }
}
