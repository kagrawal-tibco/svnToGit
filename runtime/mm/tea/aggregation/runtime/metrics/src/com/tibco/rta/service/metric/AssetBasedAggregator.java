package com.tibco.rta.service.metric;

import com.tibco.rta.Fact;
import com.tibco.rta.Metric;
import com.tibco.rta.MetricKey;
import com.tibco.rta.MultiValueMetric;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.common.RecoverableException;
import com.tibco.rta.common.exception.PersistenceStoreNotAvailableException;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.common.service.LockService;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.impl.FactKeyImpl;
import com.tibco.rta.impl.MultiValueMetricImpl;
import com.tibco.rta.impl.SingleValueMetricImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.FunctionDescriptor.FunctionParam;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.TimeDimension;
import com.tibco.rta.model.impl.DimensionHierarchyImpl;
import com.tibco.rta.model.impl.MonitoredMeasurement;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.SnapshotQueryExecutor;
import com.tibco.rta.query.filter.AndFilter;
import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.impl.QueryFactory;
import com.tibco.rta.runtime.metric.MetricFunction;
import com.tibco.rta.runtime.metric.MultiValueMetricFunction;
import com.tibco.rta.runtime.metric.SingleValueMetricFunction;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.MutableMetricNode;
import com.tibco.rta.runtime.model.impl.KeyFactory;
import com.tibco.rta.runtime.model.impl.NodeFactory;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;
import com.tibco.rta.service.metric.directive.MetricProcessingDirective;
import com.tibco.rta.service.metric.directive.MetricProcessingDirectiveFactory;
import com.tibco.rta.service.om.ObjectManager;
import com.tibco.rta.service.persistence.PersistenceService;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;


public class AssetBasedAggregator {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_METRIC.getCategory());

    enum Qualifier {
        LT, LE, EQ, GE, GT
    }


    protected Fact fact;
    protected DimensionHierarchy hierarchy;
    protected boolean isStrict = true;

    protected RtaSchema schema;
    protected Cube cube;
    protected ObjectManager omService;
    protected MetricService metricService;

    protected LockService lockService;
    protected MetricNode startNode;

    protected PersistenceService pServ;

    protected Map<String, MetricFunction> measurements = new HashMap<String, MetricFunction>();

    //keep track of locks acquired by this job so they can be unlocked later on.
    protected Map<String, String> locksAcquired = new LinkedHashMap<String, String>();
	
    protected static String SINGLE_KEY = "1";

    public AssetBasedAggregator() {
    }

    public AssetBasedAggregator(Fact fact, DimensionHierarchy hierarchy, boolean isStrict, MetricNode startNode) throws Exception {
        this.fact = fact;
        this.hierarchy = hierarchy;
        this.isStrict = isStrict;
        this.startNode = startNode;

        this.schema = hierarchy.getOwnerSchema();
        this.cube = hierarchy.getOwnerCube();
        this.omService = ServiceProviderManager.getInstance().getObjectManager();
        this.metricService = ServiceProviderManager.getInstance().getMetricsService();
        this.pServ = ServiceProviderManager.getInstance().getPersistenceService();
        this.lockService = ServiceProviderManager.getInstance().getLockService();
    }

    public AssetBasedAggregator(Fact fact, DimensionHierarchy hierarchy) throws Exception {
        this(fact, hierarchy, true, null);
    }

    public void initAggregator() throws Exception {

        outer:
        for (Measurement measurement : hierarchy.getMeasurements()) {

            MetricFunctionDescriptor md = measurement.getMetricFunctionDescriptor();
            for (FunctionParam fParam : md.getFunctionParams()) {
                String attribName = measurement.getFunctionParamBinding(fParam.getName());
                if (fact.getAttribute(attribName) == null) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Cannot compute [%s] for hierarchy [%s] in schema [%s]. fact-id [%s]. Attribute [%s] not found", measurement.getName(), hierarchy.getName(),
                                hierarchy.getOwnerSchema().getName(), ((FactKeyImpl) fact.getKey()).getUid(), attribName);
                    }
                    if (measurement instanceof MonitoredMeasurement) {
                        ((MonitoredMeasurement) measurement).incrementRejectedFacts();
                    }
                    continue outer;
                }
            }
            if (measurement instanceof MonitoredMeasurement) {
                ((MonitoredMeasurement) measurement).incrementProcessedFacts();
            }

            MetricFunction metricFunction = MetricFunctionFactory.getInstance().newMetricFunction(md);
            metricFunction.init(fact, measurement, startNode, hierarchy);
            measurements.put(measurement.getName(), metricFunction);
        }
    }

    public Fact getFact() {
        return fact;
    }

    public void setFact(Fact fact) {
        this.fact = fact;
    }

    public RtaSchema getSchema() {
        return schema;
    }

    public void setSchema(RtaSchema schema) {
        this.schema = schema;
    }

    public Cube getCube() {
        return cube;
    }

    public void setCube(Cube cube) {
        this.cube = cube;
    }

    public DimensionHierarchy getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(DimensionHierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    public void computeMetric() throws Exception {

       computeMetric(true);

        if (DimensionHierarchyImpl.isAssetHierarchy(hierarchy)) {

            //Do not propagate to other hierarchies if it has a time dimension.
            if (hasTimeDimension(hierarchy)) {
                return;
            }
            
            MetricProcessingDirective directive = MetricProcessingDirectiveFactory.INSTANCE.
                    getMetricProcessingDirectives(hierarchy.getOwnerSchema().getName());
            
            for (Cube cube : hierarchy.getOwnerSchema().getCubes()) {
                for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {
                    if (dh.getName().equals(hierarchy.getName()) || DimensionHierarchyImpl.isAssetHierarchy(dh)) {
                        continue; //TODO: Also check cube/schema equality..
                    }
                    boolean allowNulls = directive.allowNullDimensionValues(dh, fact);

                    if (directive.visitParentNodes(dh, fact)) {
                        visitParentNodes(dh, allowNulls);
                    }

                    if (directive.deleteChildNodes(dh, fact)) {
                        deleteChildNodes(dh);
                    }
                }
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public boolean computeMetric(MetricFunction metricFunction, MutableMetricNode metricNode, Metric metric, Measurement measurement) throws Exception {

        try {
            boolean recompute = false;
            Object value;
            if (metricFunction instanceof SingleValueMetricFunction) {

                SingleValueMetricFunction svmf = (SingleValueMetricFunction) metricFunction;
                SingleValueMetric svm = (SingleValueMetric) metric;
                value = svmf.compute(metricNode, svm, metricNode.getContext(measurement.getName()));
                if (value == null) {
                	recompute = true;
                } else if (!value.equals(svm.getValue())) {
                    ((SingleValueMetricImpl) svm).setValue(value);
                    metricNode.setContext(measurement.getName(), metricNode.getContext(measurement.getName()));
                    recompute = true;
                }

                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "[%s]=[%s] for hierachy [%s] key[%s]",
                            measurement.getName(), "" + value, hierarchy.getName(), metricNode.getKey());
                }
            } else if (metricFunction instanceof MultiValueMetricFunction) {
                MultiValueMetricFunction mvmf = (MultiValueMetricFunction) metricFunction;
                MultiValueMetric mvm = (MultiValueMetric) metric;
                Collection values = mvmf.compute(metricNode, mvm, metricNode.getContext(measurement.getName()));
                if (!values.equals(mvm.getValues())) {
                    ((MultiValueMetricImpl) mvm).setValues(values);
                    metricNode.setContext(measurement.getName(), metricNode.getContext(measurement.getName()));
                    recompute = true;
                }

                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    String allVals = "";
                    for (Object val : values) {
                        allVals += ", " + val;
                    }
                    LOGGER.log(Level.DEBUG, "[%s]=[%s] for hierarchy[%s], key[%s]",
                            measurement.getName(), "" + allVals, hierarchy.getName(), metricNode.getKey());
                }
            }
            return recompute;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error while computing [%s] for hierarchy[%s], key[%s]", measurement.getName(), hierarchy.getName(),
                    metricNode.getKey());
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
            	LOGGER.log(Level.DEBUG, "" , e);
            }
        }
        return false;
    }

    //this is the place to take various locks, validate, etc.
    protected boolean preProcess() throws Exception {

        boolean writeLock = false;

        boolean success = true;

        //this ensures normal processing.
        if (!metricService.useAssetLocking()) {
            return true;
        }
        
        if (metricService.useSingleLock()) {
        	if (DimensionHierarchyImpl.isAssetHierarchy(hierarchy)) {
        		writeLock = true;
        	}
        	
        	boolean lockStatus = false;
            if (writeLock) {
                lockStatus = lockService.acquireWriteLock(SINGLE_KEY, -1);
            } else {
                lockStatus = lockService.acquireReadLock(SINGLE_KEY, -1);
            }
            if (lockStatus) {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Successfully acquired a [%s] lock for [%s].", writeLock ? "writeLock" : "readLock", SINGLE_KEY);
                }
                locksAcquired.put(SINGLE_KEY, SINGLE_KEY);
            } else {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Failed to acquire a [%s] lock for [%s].", writeLock ? "writeLock" : "readLock", SINGLE_KEY);
                }
            }
            
        	return true;
        }


        Collection<String> strKeysToLock = new HashMap<String, String>().values();
        if (!DimensionHierarchyImpl.isAssetHierarchy(hierarchy)) {
            strKeysToLock = getSortedKeysToLockForDimensionHierachy(hierarchy);
        } else {
            strKeysToLock = getSortedKeysToLockForAssetDimensionHierachy(hierarchy);
            writeLock = true;
        }

        try {
            for (String strKey : strKeysToLock) {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Trying to acquire a [%s] lock for [%s].", writeLock ? "writeLock" : "readLock", strKey);
                }
                boolean lockStatus;
                if (writeLock) {
                    lockStatus = lockService.acquireWriteLock(strKey, -1);
                } else {
                    lockStatus = lockService.acquireReadLock(strKey, -1);
                }
                if (lockStatus) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Successfully acquired a [%s] lock for [%s].", writeLock ? "writeLock" : "readLock", strKey);
                    }
                    locksAcquired.put(strKey, strKey);
                } else {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Failed to acquire a [%s] lock for [%s].", writeLock ? "writeLock" : "readLock", strKey);
                    }
                }
            }
        } catch (Exception e) {
            //clean up any acquired locks first..
            postProcess();
            throw e;
        }

        return success;
    }

    //this is the place to release all locks.
    protected void postProcess() {
        // this ensures normal processing.
        if (!metricService.useAssetLocking()) {
            return;
        }

        for (Entry<String, String> entry : locksAcquired.entrySet()) {
            try {// unlock it...
                lockService.unlock(entry.getKey());
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
        locksAcquired.clear();
    }

    private void computeMetric(boolean breakOnNulls) throws Exception {

        int depthHr = hierarchy.getDepth();
        int minLevel = hierarchy.computeRoot() ? -1 : 0;
        int startLevel = -100; //some unusual value
        MetricKey key;
        if (startNode != null) {
            key = (MetricKey) startNode.getKey();
            //start computations from one level upper than incoming node's level.
            startLevel = hierarchy.getLevel(key.getDimensionLevelName());
        } else {
            startLevel = depthHr - 1;
            key = KeyFactory.createMetricNodeKey(fact, hierarchy, startLevel, isStrict);
        }
        
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Started compute cycle for hierarchy[%s], fact [%s], startLevel [%d]", hierarchy.getName(), fact.getKey().toString(), startLevel);
        }


        if (!DimensionHierarchyImpl.isAssetHierarchy(hierarchy)) {
            if (DimensionHierarchyImpl.checkForAssets(hierarchy)) {
                if (!relatedAssetsExist()) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Related Assets do not exist. Will not compute for hierarchy[%s], fact [%s]", hierarchy.getName(), fact.getKey().toString());
                    }
                    return;
                }
            }
        }

        if (measurements.size() > 0) {

            for (int level = startLevel; level >= minLevel; level--) {

                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Computing metrics for level [%d], hierarchy[%s], key[%s], fact [%s]", level, hierarchy.getName(),
                            key.toString(), fact.getKey().toString());
                }

                // Check for null if value of the dimension at this level is
                // null, break out.
                String dimNameAtLevel = hierarchy.getDimension(level).getName();
                if (key.getDimensionValue(dimNameAtLevel) == null && isStrict) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {

                        LOGGER.log(Level.DEBUG,
                                "Dimension [%s] value at level [%d] is null. Breaking out of compute cycle for hierarchy[%s], key[%s], fact [%s]",
                                dimNameAtLevel, level, hierarchy.getName(), key.toString(), fact.getKey().toString());
                    }
                    break;
                }

                if (hierarchy.getComputeForLevel(level)) {
                    key = computeMetricForLevel(depthHr, key, level, true, level == startLevel);
                } else {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG,
                                "Compute=false for Dimension [%s] value at level [%d] for hierarchy[%s]. Skipping over to the next level.",
                                dimNameAtLevel, level, hierarchy.getName());
                    }
                }
            }
        } else {
        	if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "No measurements can be computed for hierarchy[%s], key[%s], fact [%s]", hierarchy.getName(),
                        key.toString(), fact.getKey().toString());
            }
        }
    }


    private MetricKey computeMetricForLevel(int depth, MetricKey key, int level, boolean toCreate, boolean isFirstTime) throws Exception {

        if (!isFirstTime && hierarchy.getLevel(key.getDimensionLevelName()) > 0 && hierarchy.getComputeForLevel(hierarchy.getLevel(key.getDimensionLevelName()) - 1)) {
            key = KeyFactory.createParentMetricNodeKey(key, hierarchy);
        }

        compute(depth, key, level, toCreate);
        return key;
    }


    private void compute(int depth, MetricKey key, int level, boolean toCreate) throws Exception {
        MutableMetricNode metricNode = (MutableMetricNode) omService.getNode(key);

        if (metricNode == null && toCreate) {
            metricNode = NodeFactory.newMetricNode(key, fact, hierarchy, level, isStrict);
            metricNode.setIsNew(true);
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Metric node CREATED hierarchy[%s], key[%s]", hierarchy.getName(), key);
            }

        } else {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Metric node UPDATED hierarchy[%s], key[%s]", hierarchy.getName(), key);
            }

            if (metricNode != null) {
                metricNode.setIsNew(false);
            }
        }
        
        if (DimensionHierarchyImpl.isAssetHierarchy(hierarchy)) {
            MetricProcessingDirective directive = MetricProcessingDirectiveFactory.INSTANCE.getMetricProcessingDirectives(hierarchy.getOwnerSchema().getName());
            
            if (directive.isDeleteFact(fact)) {
            	// **** IMPORTANT change: Need an option whether to delete the node. Directive might override processNode() method to selectively 
            	// process delete for certain hierarchies
            	if(directive.processNode(this.hierarchy, fact)) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Deleting node for hierarchy [%s], key[%s]", hierarchy.getName(), metricNode.getKey());
                    }
                    
                    if (metricNode != null && !metricNode.isNew()) {
                        omService.delete(metricNode);
                    }
            	}
                return;
            }
        }
        

        if (metricNode != null) {
            boolean isSaved = false;
            for (Measurement measurement : hierarchy.getMeasurements()) {
                if (hierarchy.isExcluded(level, measurement.getName())) {
                	 if (LOGGER.isEnabledFor(Level.DEBUG)) {
                         LOGGER.log(Level.DEBUG, "Excluded measurement [%s] for fact [%s] for hierarchy [%s], key[%s]", measurement.getName(), fact.getKey(), hierarchy.getName(), metricNode.getKey());
                     }
                    continue;
                }

                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Computing measurement [%s] for fact [%s] for hierarchy [%s], key[%s]", measurement.getName(), fact.getKey(), hierarchy.getName(), metricNode.getKey());
                }
                Metric metric = metricNode.getMetric(measurement.getName());
                if (metric == null && toCreate) {
                    metric = NodeFactory.getOrCreateMetric(metricNode, fact, hierarchy, level, measurement);
                }

                MetricFunction metricFunction = measurements.get(measurement.getName());
                if (metricFunction != null) {

                    computeMetric(metricFunction, metricNode, metric, measurement);
                    if (metricNode.isDeleted()) {
                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG, "Metric node DELETED hierarchy[%s], key[%s]", hierarchy.getName(), key);
                        }

                        omService.delete(metricNode);
                        // once its deleted no need to compute anything else.
                        break;
                    } else {
                        if (!isSaved) {
                            omService.save(metricNode);
                            isSaved = true;
                        }
                    }
                }
            }
            if (toCreate) { // this means online message
                omService.addChildFact(metricNode, fact);
            }
        }

    }

    private void visitParentNodes(DimensionHierarchy dh, boolean allowNulls) throws RecoverableException, InterruptedException {
        visitParentOrDeleteNodes(dh, allowNulls, Qualifier.EQ, false);
    }


    private void deleteChildNodes(DimensionHierarchy dh) throws RecoverableException, InterruptedException {
        visitParentOrDeleteNodes(dh, false, Qualifier.GE, true);
    }


    private boolean hasTimeDimension(DimensionHierarchy hierarchy2) {
        for (Dimension d : hierarchy2.getDimensions()) {
            if (d instanceof TimeDimension) {
                return true;
            }
        }
        return false;
    }

    private boolean relatedAssetsExist() throws Exception {

        RtaSchema schema = hierarchy.getOwnerSchema();

        for (Cube cube : schema.getCubes()) {
            for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {
                if (DimensionHierarchyImpl.isAssetHierarchy(dh)) {
                    MetricKey key = KeyFactory.createMetricNodeKey(fact, dh, dh.getDepth() - 1, isStrict);

                    if (!isAssetPartOfHierarchy(dh, hierarchy, key)) {
                        continue;
                    }

                    if (omService.getNode(key) == null) {
                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG, "Asset [%s] does not exist, will not compute", key.toString());
                        }
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean isAssetPartOfHierarchy(DimensionHierarchy assetHr, DimensionHierarchy hierarchy2,
                                           MetricKey key) {

        for (String name : key.getDimensionNames()) {
            String attribName = assetHr.getDimension(name).getAssociatedAttribute().getName();

            boolean found = false;
            //check if this attrib exists in hierarchy2
            for (Dimension d : hierarchy2.getDimensions()) {
                if (d.getAssociatedAttribute().getName().equals(attribName)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    private void visitParentOrDeleteNodes(DimensionHierarchy dh, boolean allowNulls, Qualifier qualifier, boolean toDelete) throws RecoverableException, InterruptedException {
        try {
            QueryDef queryDef = queryForAllNodesWithMatchingAsset(dh, fact, qualifier);
            if (queryDef == null) {
                return;
            }
            SnapshotQueryExecutor executor = new SnapshotQueryExecutor(queryDef);
            List<MetricNode> nodes = executor.getNextBatch();

            while (nodes.size() > 0) {
                for (MetricNode node : nodes) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Will visit node [%s] [%s]", dh.getName(), node.getKey());
                    }

                    if (toDelete) {
                        ((MutableMetricNode) node).setDeleted(true);
                        deleteAssociatedRuleMetricNodes(node);
                        omService.delete(node);
                    } else {
                        AssetBasedAggregator agg = new AssetBasedAggregator(fact, dh, !allowNulls, node);
                        agg.initAggregator();
                        agg.computeMetric();
                    }
                }
                nodes = executor.getNextBatch();
            }
        } catch (SQLRecoverableException e) {
            logAndThrowException();
        } catch (PersistenceStoreNotAvailableException e) {
            logAndThrowException();
        } catch (SQLException e) {
            if (((SQLException) e).getSQLState() != null && ((SQLException) e).getSQLState().startsWith("08")) {
                logAndThrowException();
            }
        } catch (Exception e) {
        	if (e.getCause() instanceof SQLRecoverableException) {
        		logAndThrowException();
        	} else if (e.getCause() instanceof SQLException) {        		
                logAndThrowException();                
        	} else if (e.getCause() instanceof IOException || e.getCause() instanceof SocketTimeoutException) {
        		logAndThrowException();
        	}        	
            LOGGER.log(Level.ERROR, "" + e);
        }
    }

    private void deleteAssociatedRuleMetricNodes(MetricNode node) throws Exception {
    	
    	List<RuleDef> ruleDefs = ServiceProviderManager.getInstance().getRuleService().getRules();
    	for (RuleDef ruleDef : ruleDefs) {
    		for(ActionDef actionDef:ruleDef.getSetActionDefs()){
    			RuleMetricNodeStateKey rKey = new RuleMetricNodeStateKey(ruleDef.getName(), actionDef.getName(), (MetricKey)node.getKey());
    			omService.getRuleMetricCache().remove(rKey);
    			pServ.deleteRuleMetricNode(rKey);
    		}
    	}
	}

	private void logAndThrowException() throws InterruptedException, RecoverableException {
        LOGGER.log(Level.ERROR, "Database Exception, will retry operation in 10 seconds..");
        Thread.sleep(10000);
        throw new RecoverableException("Database exception. Will retry operation.");
    }

    private QueryDef queryForAllNodesWithMatchingAsset(DimensionHierarchy hierarchy2, Fact fact, Qualifier qualifier) throws InterruptedException, Exception {

        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Querying for all nodes with matching Assets hierarchy [%s]", hierarchy2.getName());
        }
//		Collection<String> assetAttribList = getAssetAttribList(hierarchy2, fact);
        boolean toProcess = false;

        Map<String, DimensionHierarchy> myAssets = ModelRegistry.INSTANCE.getMatchingAssetHierarchies(hierarchy2);
        String key = hierarchy2.getOwnerSchema().getName() + DimensionHierarchyImpl.getAssetName(hierarchy);
        if (myAssets.containsKey(key)) {
            toProcess = true;
            //AND hierarchy is the same as the member variable hierarchy!!
        }

        if (!toProcess) {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Not all asset attributes are present in hierarchy : [%s], will not process this fact : [%s]", hierarchy2.getName(), fact);
            }
            return null;
        }


        int level = 1000;
        int lowestTdLevel = 1000;
        List<Dimension> dimNames = new ArrayList<Dimension>();

        TimeDimension lowestTimeDimension = null;
        //get the lowest dimension in this hierarchy that matches *any of the asset attributes.
        for (Dimension dim : hierarchy2.getDimensions()) {
            for (String attribName : hierarchy.getDimensionAttribNames()) {
                if (dim.getAssociatedAttribute().getName().equals(attribName)) {
                    level = hierarchy2.getLevel(dim.getName());
                    dimNames.add(dim);
                    break;
                }
            }
            if (dim instanceof TimeDimension) {
                lowestTimeDimension = (TimeDimension) dim;
                lowestTdLevel = hierarchy2.getLevel(dim.getName());
            }
        }

        MetricProcessingDirective directive = MetricProcessingDirectiveFactory.INSTANCE.getMetricProcessingDirectives(hierarchy.getOwnerSchema().getName());
        boolean applyToCurrentTimeWindowOnly = directive.shouldApplyAssetStatusToCurrentTimeWindowOnly(hierarchy2);
        if (applyToCurrentTimeWindowOnly) {
            if (lowestTimeDimension != null) {
                dimNames.add(lowestTimeDimension);
            }
            //set the final level as the greater (deeper) of the two levels.
            level = lowestTdLevel < level ? level : lowestTdLevel;
        }

        if (dimNames.size() > 0) {
            return getMatchingNodesQueryDef(hierarchy2, fact, dimNames, level, qualifier);
        }

        return null;

    }

    private Collection<String> getAssetKeysSorted(Collection<DimensionHierarchy> assetDhList) {

        SortedMap<String, Integer> stringKeys = new TreeMap<String, Integer>();
        
        for (DimensionHierarchy dh : assetDhList) {
            MetricKey key = KeyFactory.createMetricNodeKey(fact, dh, dh.getDepth() - 1, false);
            stringKeys.put(key.toString(), 1);
        }
        return stringKeys.keySet();
    }

//    private Collection<String> getSortedStringKeys(List<Key> keyList) {
//        for (Key k : keyList) {
//            MetricKey mk = (MetricKey) k;
//            String stringKey = mk.toString();
//            stringKeys.put(stringKey, stringKey);
//        }
//        return stringKeys.keySet();
//    }

    private Collection<String> getSortedKeysToLockForDimensionHierachy(DimensionHierarchy dh) {
        Collection<DimensionHierarchy> assetDhs = ModelRegistry.INSTANCE.getMatchingAssetHierarchies(dh).values();
        Collection<String> keys = getAssetKeysSorted(assetDhs);
        return keys;
    }

    private Collection<String> getSortedKeysToLockForAssetDimensionHierachy(DimensionHierarchy assetDh) {
        DimensionHierarchy assetDh2 = ModelRegistry.INSTANCE.getAssetHierarchyForAsset(assetDh);
        if (assetDh2 != null) {
            ArrayList<DimensionHierarchy> wrapper = new ArrayList<DimensionHierarchy>();
            wrapper.add(assetDh2);
            Collection<String> keys = getAssetKeysSorted(wrapper);
            return keys;
        } else {
            return new ArrayList<String>(0);
        }
    }


    private QueryByFilterDef getMatchingNodesQueryDef(
            DimensionHierarchy hierarchy2, Fact fact, List<Dimension> dimensions,
            int level, Qualifier qualifier) throws Exception {
        QueryFactory queryFac = QueryFactory.INSTANCE;

        QueryByFilterDef queryDef = queryFac.newQueryByFilterDef(hierarchy2.getOwnerSchema().getName(), hierarchy2.getOwnerCube().getName(),
                hierarchy2.getName(), null);
        queryDef.setBatchSize(5000);

        AndFilter andFilter = queryFac.newAndFilter();

        for (Dimension dimension : dimensions) {
            String attribName = dimension.getAssociatedAttribute().getName();
            Object value = fact.getAttribute(attribName);

            if (value != null) {
                if (dimension instanceof TimeDimension) {
                    TimeDimension td = (TimeDimension) dimension;
                    value = td.getTimeUnit().getTimeDimensionValue((Long) value);
                }
                Filter eqFilter = queryFac.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, dimension.getName(), value);
                andFilter.addFilter(eqFilter);
            }
        }
        Filter levelFilter = getFilerBasedOnQualifier(qualifier, level);
        andFilter.addFilter(levelFilter);
        queryDef.setFilter(andFilter);
        return queryDef;
    }

    private Filter getFilerBasedOnQualifier(Qualifier qualifier, int level) throws Exception {
        QueryFactory queryFac = QueryFactory.INSTANCE;
        //Bala: Add a level qualifier also. --> where level >= incoming level, where incoming level is the bottom-most in the hierarchy.
        Filter filter = null;
        if (qualifier.equals(Qualifier.LT)) {
            filter = queryFac.newLtFilter(MetricQualifier.DIMENSION_LEVEL_NO, level);
        } else if (qualifier.equals(Qualifier.LE)) {
            filter = queryFac.newLEFilter(MetricQualifier.DIMENSION_LEVEL_NO, level);
        } else if (qualifier.equals(Qualifier.EQ)) {
            filter = queryFac.newEqFilter(MetricQualifier.DIMENSION_LEVEL_NO, level);
        } else if (qualifier.equals(Qualifier.GE)) {
            filter = queryFac.newGEFilter(MetricQualifier.DIMENSION_LEVEL_NO, level);
        } else if (qualifier.equals(Qualifier.GT)) {
            filter = queryFac.newGtFilter(MetricQualifier.DIMENSION_LEVEL_NO, level);
        }
        return filter;
    }
}
