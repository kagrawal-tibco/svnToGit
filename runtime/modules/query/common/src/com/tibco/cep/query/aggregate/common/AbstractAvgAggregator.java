package com.tibco.cep.query.aggregate.common;

import com.tibco.cep.query.aggregate.Aggregate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 11/16/12
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */

public abstract class AbstractAvgAggregator implements Aggregate {

    public Map<Object, Object> reduce(Collection<Map<Object, Object>> intermediate) throws Exception {
        Map<Object, Object> resultMap = new HashMap<Object, Object>();
        Map<Object, Object> tmpMap = new HashMap<Object, Object>();
        int type = 0;
        int conditionFlag = 0;
        RawAverage rawAverage;

        for (Map<Object, Object> m : intermediate) {
            for (Map.Entry mapEntry : m.entrySet()) {
                rawAverage = (RawAverage) mapEntry.getValue();

                if (type == 0) {
                    Class classType = rawAverage.getSum().getClass();
                    if (classType == Integer.class) {
                        type = 1;
                    } else if (classType == Float.class) {
                        type = 2;
                    } else if (classType == Long.class) {
                        type = 3;
                    } else if (classType == Double.class) {
                        type = 4;
                    }
                }
                RawAverage rawAverageOld = (RawAverage) tmpMap.get(mapEntry.getKey());

                switch (type) {
                    case 1:
                        if (rawAverageOld != null) {
                            conditionFlag = 1;
                            int sum = (Integer) rawAverage.getSum() + (Integer) rawAverageOld.getSum();
                            double cnt = rawAverage.getCount() + rawAverageOld.getCount();
                            tmpMap.put(mapEntry.getKey(), new RawAverage(sum, cnt));
                        }
                        break;

                    case 2:
                        if (rawAverageOld != null) {
                            conditionFlag = 1;
                            float sum = (Float) rawAverage.getSum() + (Float) rawAverageOld.getSum();
                            double cnt = rawAverage.getCount() + rawAverageOld.getCount();
                            tmpMap.put(mapEntry.getKey(), new RawAverage(sum, cnt));
                        }
                        break;

                    case 3:
                        if (rawAverageOld != null) {
                            conditionFlag = 1;
                            long sum = (Long) rawAverage.getSum() + (Long) rawAverageOld.getSum();
                            double cnt = rawAverage.getCount() + rawAverageOld.getCount();
                            tmpMap.put(mapEntry.getKey(), new RawAverage(sum, cnt));
                        }
                        break;

                    case 4:
                        if (rawAverageOld != null) {
                            conditionFlag = 1;
                            double sum = (Double) rawAverage.getSum() + (Double) rawAverageOld.getSum();
                            double cnt = rawAverage.getCount() + rawAverageOld.getCount();
                            tmpMap.put(mapEntry.getKey(), new RawAverage(sum, cnt));
                        }
                        break;
                }

                if (conditionFlag != 1) {
                    tmpMap.put(mapEntry.getKey(), mapEntry.getValue());
                }
                conditionFlag = 0;
            }
        }
        for (Map.Entry mEntry : tmpMap.entrySet()) {
            rawAverage = (RawAverage) mEntry.getValue();
            resultMap.put(mEntry.getKey(), rawAverage.getAvg());
        }
        return resultMap;
    }
}

