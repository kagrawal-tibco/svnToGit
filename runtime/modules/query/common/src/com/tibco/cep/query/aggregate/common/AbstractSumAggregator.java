package com.tibco.cep.query.aggregate.common;

import com.tibco.cep.query.aggregate.Aggregate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 11/29/12
 * Time: 11:00 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractSumAggregator implements Aggregate {

    public Map<Object, Object> reduce(Collection<Map<Object, Object>> intermediate) throws Exception {
        Map<Object, Object> resultMap = new HashMap<Object, Object>();
        int type = 0;
        /**
         * Iterate over the collection
         */
        Object key;
        for (Map<Object, Object> m : intermediate) {
            for (Map.Entry mapEntry : m.entrySet()) {
                if (type == 0) {
                    Class c = mapEntry.getValue().getClass();
                    if (c == Integer.class) {
                        type = 1;
                    } else if (c == Float.class) {
                        type = 2;
                    } else if (c == Long.class) {
                        type = 3;
                    } else if (c == Double.class) {
                        type = 4;
                    }
                }
                key = mapEntry.getKey();

                switch (type) {
                    case 1:
                        Integer i = (Integer) resultMap.get(key);
                        Integer iValue = (Integer) mapEntry.getValue();
                        if (i != null) {
                            int iSum = i + iValue;
                            resultMap.put(key, iSum);
                        } else {
                            resultMap.put(key, iValue);
                        }
                        break;

                    case 2:
                        Float f = (Float) resultMap.get(key);
                        Float fValue = (Float) mapEntry.getValue();
                        if (f != null) {
                            float fSum = f + fValue;
                            resultMap.put(key, fSum);
                        } else {
                            resultMap.put(key, fValue);
                        }
                        break;

                    case 3:
                        Long l = (Long) resultMap.get(key);
                        Long lValue = (Long) mapEntry.getValue();
                        if (l != null) {
                            long lSum = l + lValue;
                            resultMap.put(key, lSum);
                        } else {
                            resultMap.put(key, lValue);
                        }

                        break;

                    case 4:
                        Double d = (Double) resultMap.get(key);
                        Double dValue = (Double) mapEntry.getValue();
                        if (d != null) {
                            double dSum = d + dValue;
                            resultMap.put(key, dSum);
                        } else {
                            resultMap.put(key, dValue);
                        }
                        break;
                }
            }
        }

        return resultMap;
    }
}
