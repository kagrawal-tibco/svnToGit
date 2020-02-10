package com.tibco.rta.runtime.model.impl;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.Metric;
import com.tibco.rta.MetricKey;
import com.tibco.rta.impl.MetricKeyImpl;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.MutableMetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bgokhale
 *         <p/>
 *         A MetricNode implementation.
 *         <p/>
 *         A metric node holds a Metric and provides links to navigate and calculate related metrics
 */

public class MetricNodeImpl extends RtaNodeImpl implements MutableMetricNode {

    private static final long serialVersionUID = -4534077467477722426L;

    protected Map<String, Metric> metricsMap = new LinkedHashMap<String, Metric>();
    protected Map<String, RtaNodeContext> contextMap = new LinkedHashMap<String, RtaNodeContext>();

    transient protected boolean isNew;

    transient protected boolean isDeleted;
    
    protected boolean isProcessed=false;

    public MetricNodeImpl() {
    }

    public MetricNodeImpl(Key key) {
        //TODO: Parent key?
        super(key);
    }

    @Override
    public <N> Metric<N> getMetric(String metricName) {
        return metricsMap.get(metricName);
    }

    @Override
    public <N> void setMetric(String metricName, Metric<N> metric) {
//		Map<String, Metric> metrics = metricsMap.get(measurementName);
//		if (metrics == null) {
//			metrics = new LinkedHashMap<String, Metric>();
//			metricsMap.put(metricName, metrics);
//		}
//		metricsMap.put(metricName, metrics);
        metricsMap.put(metricName, metric);
    }

    @Override
    public void addFact(Fact fact) throws Exception {
        pService.addFact(this, fact);

    }

    @Override
    public Browser<MetricNode> getChildNodeBrowser() {
        //TODO
        return null;
    }

//	@Override
//	public void addChildNode(MetricNode metricNode) {
//		pService.addChildNode(this, metricNode);
//		
//	}

    @Override
    public List<String> getMetricNames() {
        return new ArrayList<String>(metricsMap.keySet());
    }

    @Override
    public void setContext(String metricName, RtaNodeContext value) {
//		Map<String, RtaNodeContext> metrics = contextMap.get(measurementName);
//		if (metrics == null) {
//			metrics = new LinkedHashMap<String, RtaNodeContext>();
//			contextMap.put(metricName, metrics);
//		}
//		metrics.put(metricName, value);
        contextMap.put(metricName, value);
    }

    @Override
    public RtaNodeContext getContext(String metricName) {
//		Map <String, RtaNodeContext> ctxMap = contextMap.get(measurementName);
//		if (ctxMap != null) {
//			return ctxMap.get(metricName);
//		}
//		return null;
        return contextMap.get(metricName);
    }

    @Override
    public void setKey(Key key) {
        this.key = key;

    }

    public String getDimensionHierarchyName() {
        return ((MetricKey) key).getDimensionHierarchyName();
    }

    public void setDimensionHierarchy(String dimHrName) {
        ((MetricKeyImpl) key).setDimensionHierarchyName(dimHrName);
    }


    public String toString() {
        StringBuilder sb = new StringBuilder("key=");
        sb.append(key);
        return sb.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Fact> Browser<T> getChildFactsBrowser(List<MetricFieldTuple> orderByList) {
        try {
            if (pService.isSortingProvided()) {
                return (Browser<T>) new MetricAllFactsBrowser(pService, this, orderByList);
            } else {
                return new InMemorySortedFactsBrowser(new MetricAllFactsBrowser(pService, this, orderByList));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
//			LOGGER.log(Level.ERROR, "Error while getting MetricNode browser", e);
        }
        return null;
    }

    //two nodes are considered equal if their keys are equal
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MetricNodeImpl)) {
            return false;
        }
        MetricNodeImpl otherNode = (MetricNodeImpl) object;
        return !((otherNode.getKey() == null && key != null) ||
                        key == null && otherNode.getKey() != null) && key.equals(otherNode.getKey());
    }


    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    @Override
    public MetricNodeImpl deepCopy() {
        MetricNodeImpl cloned = new MetricNodeImpl();

        //key is immutable, copy reference
        cloned.key = key;

        //key is immutable, copy reference
        cloned.parentKey = parentKey;
        if (cloned.parent != null) {
            cloned.parent = (MetricNodeImpl) parent.deepCopy();
        }

        cloned.createdTime = createdTime;
        cloned.lastModifiedTime = lastModifiedTime;
        
        cloned.isProcessed = isProcessed;

        //is immutable, copy reference
        cloned.pService = pService;

        //deep clone the metricsMap
        cloned.metricsMap = new LinkedHashMap<String, Metric>();
        for (Map.Entry<String, Metric> entry : metricsMap.entrySet()) {
            String key = entry.getKey();
            Metric value = entry.getValue();
            Metric clonedMetric = null;
            if (value != null) {
                clonedMetric = value.deepCopy();
            }
            cloned.metricsMap.put(key, clonedMetric);
        }

        //deep clone the contextMap
        cloned.contextMap = new LinkedHashMap<String, RtaNodeContext>();
        for (Map.Entry<String, RtaNodeContext> entry : contextMap.entrySet()) {
            String key = entry.getKey();
            RtaNodeContext value = entry.getValue();
            RtaNodeContext clonedContext = null;
            if (value != null) {
                clonedContext = value.deepCopy();
            }
            cloned.contextMap.put(key, clonedContext);
        }
        return cloned;
    }

    @Override
    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;

    }

    @Override
    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
    
    @Override
    public void setProcessed(boolean isProcessed) {
        this.isProcessed = isProcessed;

    }

    @Override
    public boolean isProcessed() {
        return isProcessed;
    }

}
