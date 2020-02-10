package com.tibco.rta.service.metric;

import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.rta.Fact;
import com.tibco.rta.KeyFactory;
import com.tibco.rta.Metric;
import com.tibco.rta.MetricKey;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.QueryByKeyDef;
import com.tibco.rta.query.QueryType;
import com.tibco.rta.query.impl.QueryFactory;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNode;
import com.tibco.rta.service.persistence.PersistenceService;
import com.tibco.rta.service.query.QueryUtils;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 18/2/13
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class MetricIntrospectionServiceImpl extends AbstractStartStopServiceImpl implements MetricIntrospectionService {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_METRIC.getCategory());

    private PersistenceService persistenceService;

    /**
     * Map of browser id to fact browser
     */
    private ConcurrentHashMap<String, Browser<?>> proxyToBrowserMap = new ConcurrentHashMap<String, Browser<?>>();

    @Override
    public <S, T extends Metric<S>> String getChildMetricBrowser(Metric<S> metric, List<MetricFieldTuple> orderByList) throws Exception {
        MetricKey metricKey = (MetricKey) metric.getKey();
        QueryByKeyDef queryByKeyDef = QueryFactory.INSTANCE.newQueryByKeyDef();
        MetricKey partialKey = createChildPartialKey(metricKey);
        String browserId = null;

        if (partialKey != null) {
            queryByKeyDef.setQueryKey(partialKey);
            queryByKeyDef.setQueryType(QueryType.SNAPSHOT);

			if (orderByList != null) {
				for (MetricFieldTuple metricFieldTuple : orderByList) {
					if (metricFieldTuple.getMetricQualifier() != null) {
						queryByKeyDef.addOrderByTuple(metricFieldTuple.getMetricQualifier());
					}
					if (metricFieldTuple.getKeyQualifier() != null) {
						queryByKeyDef.addOrderByTuple(metricFieldTuple.getKeyQualifier(), metricFieldTuple.getKey());
					}
				}
			}
            Browser<?> metricNodeBrowser = persistenceService.getMetricNodeBrowser(queryByKeyDef);
            browserId = UUID.randomUUID().toString();
            proxyToBrowserMap.put(browserId, metricNodeBrowser);
        }
        return browserId;
    }

    @Override
    public <S, T extends Fact> String getConstituentFactsBrowser(Metric<S> metric, List<MetricFieldTuple> orderByList) throws Exception {
        RtaNode matchingNode = persistenceService.getNode(metric.getKey());
        String browserId = null;

        if (matchingNode instanceof MetricNode) {
            MetricNode metricNode = (MetricNode) matchingNode;
            Browser<T> factBrowser = metricNode.getChildFactsBrowser(orderByList);
            browserId = UUID.randomUUID().toString();
            proxyToBrowserMap.put(browserId, factBrowser);
        }
        return browserId;
    }

    public void init(Properties configuration) throws Exception {
        super.init(configuration);
        persistenceService = ServiceProviderManager.getInstance().getPersistenceService();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object next(String correlationId) throws Exception {
        Browser<?> browser = proxyToBrowserMap.get(correlationId);
        Object next = browser.next();
        if (next instanceof MetricNode) {
            MetricNode metricNode = (MetricNode) next;
            next = QueryUtils.convertMetricNodeToMetricResultTuple(metricNode);
        }
        return next;
    }
    
    @Override
    public boolean hasNext(String correlationId) throws Exception {
        Browser<?> browser = proxyToBrowserMap.get(correlationId);
        boolean hasNext = (browser != null) && browser.hasNext();
        if (!hasNext) {
            removeBrowserMapping(correlationId);
        }
        return hasNext;
    }

    public void removeBrowserMapping(String correlationId) {
		Browser<?> browser = proxyToBrowserMap.remove(correlationId);
		if (browser != null) {
			browser.stop();
		}
    }


    private MetricKey createChildPartialKey(MetricKey parentKey) {
        String schemaName = parentKey.getSchemaName();
        MetricKey partialKey = null;
        RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);

        if (schema != null) {
            Cube cube = schema.getCube(parentKey.getCubeName());
            //Get hierarchy
            DimensionHierarchy dimensionHierarchy = cube.getDimensionHierarchy(parentKey.getDimensionHierarchyName());
            //Get parent dimension name
            String parentDimensionName = parentKey.getDimensionLevelName();
            int currentLevel = dimensionHierarchy.getLevel(parentDimensionName);
            //Get dimension at next level
            int requiredLevel = currentLevel + 1;

            if (requiredLevel < dimensionHierarchy.getDepth()) {
                Dimension requiredChildDimension = dimensionHierarchy.getDimension(requiredLevel);
                partialKey = KeyFactory.newMetricKey(schemaName,
                                                     parentKey.getCubeName(),
                                                     parentKey.getDimensionHierarchyName(),
                                                     requiredChildDimension.getName()
                                                     );

                for (String dimension : parentKey.getDimensionNames()) {
                    //Add all dimensions in key as is just level will be incremented.
                    partialKey.addDimensionValueToKey(dimension, parentKey.getDimensionValue(dimension));
                }
            }
        }
        return partialKey;
    }
}
