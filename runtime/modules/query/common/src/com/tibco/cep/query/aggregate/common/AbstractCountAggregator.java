package com.tibco.cep.query.aggregate.common;

import com.tibco.cep.query.aggregate.Aggregate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 11/29/12
 * Time: 1:25 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractCountAggregator implements Aggregate {

    public Map<Object, Object> reduce(Collection<Map<Object, Object>> intermediate) throws Exception {
        Map<Object, Object> resultMap = new HashMap<Object, Object>();

        for (Map<Object, Object> m : intermediate) {
            for (Map.Entry mapEntry : m.entrySet()) {
                Object key = mapEntry.getKey();
                Integer i = (Integer) resultMap.get(key);
                if (i != null) {
                    int cnt = i + (Integer) mapEntry.getValue();
                    resultMap.put(key, cnt);
                } else {
                    resultMap.put(key, mapEntry.getValue());
                }
            }
        }
        return resultMap;
    }

}
