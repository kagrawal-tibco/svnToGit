package com.tibco.rta.service.persistence.as;

import com.tibco.as.space.ASException;
import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.Browser;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.impl.MetricNodeImpl;

public class AbstractMetricNodeBrowser implements com.tibco.rta.query.Browser<MetricNode> {

    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_PERSISTENCE.getCategory());

    protected Space space;
    protected Browser browser;

    protected MetricNodeImpl metricNode;
    protected boolean isStopped;

    protected String schemaName;
    protected String cubeName;
    protected String hierarchyName;
//    protected String measurementName;

    protected ASPersistenceService pServ;



    AbstractMetricNodeBrowser(ASPersistenceService pServ, String schemaName, String cubeName, String hierarchyName) throws ASException {
        this.pServ = pServ;
        this.schemaName = schemaName;
        this.cubeName = cubeName;
        this.hierarchyName = hierarchyName;
//        this.measurementName = measurementName;
        this.space = pServ.getSchemaManager().getOrCreateMetricSchema(schemaName, cubeName, hierarchyName);
    }

    @Override
    public boolean hasNext() {
        try {
            if (isStopped) {
                return false;
            }

            if (metricNode == null) {
                Tuple metricTuple = browser.next();
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Query tuple = {%s} ", metricTuple);
                }
                metricNode = pServ.fetchMetricNodeFromTuple(metricTuple, schemaName, cubeName, hierarchyName);

                if (metricNode == null) {
                    browser.stop();
                    isStopped = true;
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } catch (Exception e) {
           LOGGER.log(Level.ERROR, "Error while iterating the child browser", e);
        }
        return false;
    }

    @Override
    public MetricNodeImpl next() {
        if (isStopped) {
            return null;
        }
        MetricNodeImpl next = metricNode;
        metricNode = null;
        return next;
    }

    @Override
    public void remove() {
    }

    @Override
    public void stop() {
        try {
            browser.stop();
        } catch (ASException e) {
            LOGGER.log(Level.ERROR, "Error while stopping the browser", e);
        }
        isStopped = true;
    }

//    protected MetricNodeImpl createMetricNode(Tuple metricTuple) throws Exception {
//        if (metricTuple == null) {
//            return null;
//        }
//
//
//        RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
//        Cube cube = schema.getCube(cubeName);
//        DimensionHierarchy hierarchy = cube.getDimensionHierarchy(hierarchyName);
//
//        String dimensionNm = metricTuple.getString(ASPersistenceService.DIMENSION_LEVEL_NAME_FIELD);
//
//        MetricKeyImpl key = (MetricKeyImpl) KeyFactory.newMetricKey(schemaName, cubeName, hierarchyName, dimensionNm);
//        int level = hierarchy.getLevel(dimensionNm);
//        for (int i = 0; i <= level; i++) {
//            Dimension d = hierarchy.getDimension(i);
//            String dimName = d.getName();
//
//            Object value = metricTuple.get(dimName);
//            key.addDimensionValueToKey(dimName, value);
//        }
//        //add null values to rest of the dimensions
//        for (int i = level + 1; i <= hierarchy.getDepth() - 1; i++) {
//            String dimName = hierarchy.getDimension(i).getName();
//            key.addDimensionValueToKey(dimName, null);
//        }
//
//        MetricNodeImpl metricNodeImpl = new MetricNodeImpl(key);
//
//        //loop over metric descriptor
//        for (Measurement measurement : hierarchy.getMeasurements()) {
//
//            MetricFunctionDescriptor md = measurement.getMetricFunctionDescriptor();
//            boolean isMultiValued = md.isMultiValued();
//
//            Object value = metricTuple.get(measurement.getName());
//            BaseMetricImpl bmi = null;
//            if (value == null) {
//                continue;
//            }
//
//            if (!isMultiValued) {
//                SingleValueMetricImpl svm = new SingleValueMetricImpl();
//                svm.setValue(value);
//                bmi = svm;
//            } else {
//                MultiValueMetricImpl mvm = new MultiValueMetricImpl();
//
//                Tuple metricValuesTuple = Tuple.create();
//                metricValuesTuple.deserialize((byte[]) value);
//                int size = metricValuesTuple.getInt("size");
//                List<Object> vals = new ArrayList<Object>();
//                for (int i = 0; i < size; i++) {
//                    Object val = metricValuesTuple.get("v" + i);
//                    vals.add(val);
//                }
//                mvm.setValues(vals);
//                bmi = mvm;
//            }
//
//
//            MetricValueDescriptorImpl mvd = new MetricValueDescriptorImpl(dimensionNm, hierarchyName, cubeName, schemaName, measurement.getName());
//            bmi.setDescriptor(mvd);
//            bmi.setDimensionValue(metricTuple.get(dimensionNm));
//            bmi.setKey(key);
//            bmi.setMetricName(measurement.getName());
//            metricNodeImpl.setMetric(measurement.getName(), bmi);
//        }
//
//        return metricNodeImpl;
//    }
}
