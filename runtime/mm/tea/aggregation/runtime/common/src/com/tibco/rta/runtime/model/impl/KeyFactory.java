package com.tibco.rta.runtime.model.impl;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.MetricKey;
import com.tibco.rta.impl.FactKeyImpl;
import com.tibco.rta.impl.MetricKeyImpl;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.TimeDimension;
import com.tibco.rta.runtime.model.MetricNode;

public class KeyFactory {

    public static MetricKey createMetricNodeKey(Fact fact, DimensionHierarchy hierarchy, int level, boolean isStrict) {

        //measurement field of metric node will always be null...
        MetricKey key = new MetricKeyImpl(fact.getOwnerSchema().getName(),
                hierarchy.getOwnerCube().getName(),
                hierarchy.getName(), (level < 0 ? "root" : hierarchy.getDimension(level).getName()));

        for (int i = 0; i <= level; i++) {
            Dimension d = hierarchy.getDimension(i);
            String dimName = d.getName();
            String attrName = d.getAssociatedAttribute().getName();
            Object value = fact.getAttribute(attrName);
            if (d instanceof TimeDimension) {
                TimeDimension td = (TimeDimension) d;
                long timestamp = 0;
                if (value == null) {
                    timestamp = System.currentTimeMillis();
                } else if (value instanceof Long) {
                    timestamp = (Long) value;
                }
                value = td.getTimeUnit().getTimeDimensionValue(timestamp);
            }
            if (value != null) {
                key.addDimensionValueToKey(dimName, value);
            } else if (isStrict) {
                throw new RuntimeException(String.format("Attribute value null: %s", attrName));
            } else {
                key.addDimensionValueToKey(dimName, value);
            }
        }


        //add null values to rest of the dimensions
        for (int i = level + 1; i <= hierarchy.getDepth() - 1; i++) {
            String dimName = hierarchy.getDimension(i).getName();
            key.addDimensionValueToKey(dimName, null);
        }

        return key;
    }

    public static MetricKey createMetricNodeKeyForLevel(MetricKey key, DimensionHierarchy hierarchy, int level) {

        MetricKeyImpl key1 = new MetricKeyImpl(key);
        key1.setDimensionLevelName((level < 0 ? "root" : hierarchy.getDimension(level).getName()));
        for (int i = level + 1; i <= hierarchy.getDepth() - 1; i++) {
            String dimName = hierarchy.getDimension(i).getName();
            key1.addDimensionValueToKey(dimName, null);
        }
        return key1;
    }

    public static Key createFactKey(Fact fact) {
        return new FactKeyImpl(fact.getOwnerSchema().getName());
    }

    public static Key createRtaContextNodeKey(MetricNode node) {
        return node.getKey();
    }

    public static MetricKey createParentMetricNodeKey(MetricKey key, DimensionHierarchy hierarchy) {
        String dimLevelName = key.getDimensionLevelName();
        int level = hierarchy.getLevel(dimLevelName);
        return createMetricNodeKeyForLevel(key, hierarchy, level - 1);
    }
}
