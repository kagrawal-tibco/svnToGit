package com.tibco.rta.model.serialize.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.Unmarshaller;

import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.impl.MetricKeyImpl;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.rule.impl.ActionDefImpl;
import com.tibco.rta.model.rule.mutable.MutableActionDef;
import com.tibco.rta.query.QueryByKeyDef;
import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.filter.LogicalFilter;

public class JAXBQueryUnMarshllerListener extends Unmarshaller.Listener {

    @Override
    public void beforeUnmarshal(Object target, Object parent) {
    }

    @Override
    public void afterUnmarshal(Object target, Object parent) {
        deserialize(target, parent);
    }

    private void deserialize(Object target, Object parent) {
        deserializeFilter(target, parent);
        deserializeMetricKey(target, parent);
        deserializeActionDef(target, parent);
    }

    private void deserializeActionDef(Object target, Object parent) {
        if (target instanceof ActionDef && parent instanceof RuleDef) {
            MutableActionDef action = (MutableActionDef) target;
            RuleDef ruleDef = (RuleDef) parent;
            ((ActionDefImpl)action).setRuleDef(ruleDef);
        }
    }

    private void deserializeMetricKey(Object target, Object parent) {
        if (target instanceof MetricKeyImpl && parent instanceof QueryByKeyDef) {
            MetricKeyImpl key = (MetricKeyImpl) target;
            Map<String, Object> sortedMap = getSortedMap(key.getDimensionValuesMap(), key);
            key.setDimensionValuesMap(sortedMap);
        }
    }

    private void deserializeFilter(Object target, Object parent) {
        if (target instanceof Filter && parent instanceof LogicalFilter) {
            LogicalFilter logical = (LogicalFilter) parent;
            Filter childFilter = (Filter) target;
            logical.addFilter(childFilter);
        }
    }

    private Map<String, Object> getSortedMap(Map<String, Object> dimensionValueMap, MetricKeyImpl key) {
        Map<String, Object> sortedMap = new LinkedHashMap<String, Object>();
        RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(key.getSchemaName());
        if (schema != null) {
            DimensionHierarchy dh = schema.getCube(key.getCubeName()).getDimensionHierarchy(key.getDimensionHierarchyName());
            for (int i = 0; i < dh.getDepth(); i++) {
                sortedMap.put(dh.getDimension(i).getName(), dimensionValueMap.get(dh.getDimension(i).getName()));
            }
            dimensionValueMap.clear();
            return sortedMap;
        }
        return null;
    }
}
