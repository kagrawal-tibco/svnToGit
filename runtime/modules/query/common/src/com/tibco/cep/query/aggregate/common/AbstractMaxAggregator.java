package com.tibco.cep.query.aggregate.common;

import com.tibco.cep.query.aggregate.Aggregate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 11/29/12
 * Time: 1:04 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractMaxAggregator implements Aggregate {
    protected AbstractMaxAggregator() {
    }

    public Map<Object, Object> reduce(Collection<Map<Object, Object>> intermediate) throws Exception {
        Map<Object, Object> resultMap = new HashMap<Object, Object>();
        int type = 0;
        Object key;
        /**
         * Iterate over the collection
         */
        for (Map<Object, Object> m : intermediate) {
            for (Map.Entry mapEntry : m.entrySet()) {
                if (type == 0) {
                    Class c = mapEntry.getValue().getClass();
                    if (c == String.class) {
                        type = 1;
                    } else if (c == Integer.class) {
                        type = 2;
                    } else if (c == Float.class) {
                        type = 3;
                    } else if (c == Long.class) {
                        type = 4;
                    } else if (c == Double.class) {
                        type = 5;
                    }
                }
                key = mapEntry.getKey();
                switch (type) {
                    case 1:
                        String oldStr = (String) resultMap.get(key);
                        String newStr = (String) (mapEntry.getValue());
                        if (oldStr != null) {
                            if (oldStr.compareTo(newStr) < 0) {
                                resultMap.put(key, newStr);
                            }
                        } else {
                            resultMap.put(key, newStr);
                        }
                        break;

                    case 2:
                        Integer i = (Integer) resultMap.get(key);
                        Integer iValue = (Integer) mapEntry.getValue();
                        if (i != null) {
                            if (i < iValue) {
                                resultMap.put(key, iValue);
                            }
                        } else {
                            resultMap.put(key, iValue);
                        }
                        break;

                    case 3:
                        Float f = (Float) resultMap.get(key);
                        Float fValue = (Float) mapEntry.getValue();
                        if (f != null) {
                            if (f < fValue) {
                                resultMap.put(key, fValue);
                            }
                        } else {
                            resultMap.put(key, fValue);
                        }
                        break;

                    case 4:
                        Long l = (Long) resultMap.get(key);
                        Long lValue = (Long) mapEntry.getValue();
                        if (l != null) {
                            if (l < lValue) {
                                resultMap.put(key, lValue);
                            }
                        } else {
                            resultMap.put(key, lValue);
                        }
                        break;

                    case 5:
                        Double d = (Double) resultMap.get(key);
                        Double dValue = (Double) mapEntry.getValue();
                        if (d != null) {
                            if (d < dValue) {
                                resultMap.put(key, dValue);
                            }
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
