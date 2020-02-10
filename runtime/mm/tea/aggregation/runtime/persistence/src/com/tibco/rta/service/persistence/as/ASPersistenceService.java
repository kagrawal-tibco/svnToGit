
package com.tibco.rta.service.persistence.as;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.as.space.ASException;
import com.tibco.as.space.ASStatus;
import com.tibco.as.space.DateTime;
import com.tibco.as.space.InvokeOptions;
import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.Browser;
import com.tibco.as.space.browser.BrowserDef;
import com.tibco.as.space.browser.BrowserDef.BrowserType;
import com.tibco.as.space.browser.BrowserDef.DistributionScope;
import com.tibco.as.space.browser.BrowserDef.TimeScope;
import com.tibco.as.space.remote.InvokeResult;
import com.tibco.as.space.remote.InvokeResultList;
import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.KeyFactory;
import com.tibco.rta.Metric;
import com.tibco.rta.MetricKey;
import com.tibco.rta.MultiValueMetric;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.as.kit.ASResourceFactory;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.common.service.RtaTransaction;
import com.tibco.rta.common.service.RtaTransaction.FactHr;
import com.tibco.rta.common.service.RtaTransaction.MetricFact;
import com.tibco.rta.common.service.RtaTransaction.RtaTransactionElement;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.impl.BaseMetricImpl;
import com.tibco.rta.impl.FactImpl;
import com.tibco.rta.impl.FactKeyImpl;
import com.tibco.rta.impl.MetricKeyImpl;
import com.tibco.rta.impl.MultiValueMetricImpl;
import com.tibco.rta.impl.SingleValueMetricImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.FunctionDescriptor.FunctionParam;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.RetentionPolicy.Qualifier;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.impl.MetricValueDescriptorImpl;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.QueryByKeyDef;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.impl.EmptyIterator;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.MutableMetricNode;
import com.tibco.rta.runtime.model.RtaNode;
import com.tibco.rta.runtime.model.RtaNodeContext;
import com.tibco.rta.runtime.model.impl.MetricNodeImpl;
import com.tibco.rta.runtime.model.impl.RtaNodeContextImpl;
import com.tibco.rta.runtime.model.rule.AlertNodeState;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateImpl;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;
import com.tibco.rta.service.persistence.PersistenceService;
import com.tibco.rta.util.IOUtils;

public class ASPersistenceService extends AbstractStartStopServiceImpl implements PersistenceService {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(
			LoggerCategory.RTA_SERVICES_PERSISTENCE.getCategory());

	static final String SEP = "_";
	// static final String METRICVALUE_SUFFIX = "_VALUE";
	static final String METRICCONTEXT_SUFFIX = "_CONTEXT";
	static final String FACT_KEY_FIELD = "fact_key";
	static final String METRIC_KEY_FIELD = "metric_key";
	static final String OWNER_SCHEMA_FIELD = "owner_schema";
	static final String PK_KEY = "pk_key";

	static final String METRICS_PREFIX = "METRIC_";
	// static final String DIMHR_NAME = "DIMHR_NAME";
	static final String METRIC_SPACE_PREFIX = "METRIC_";
	static final String RULE_METRIC_SPACE_PREFIX = "RULE_STATE_";
	static final String FACT_SPACE_PREFIX = "FACT_";
	static final String METRICS_FACT_SPACE_PREFIX = "METRIC_CHILD_FACTS_";
	static final String DIMENSION_LEVEL_NAME_FIELD = "DIMENSION_LEVEL";
	static final String PROCESSED_FACTS = "PROCESSED_FACTS_";
	static final String RULE_NAME_FIELD = "rule_name";
	static final String RULE_ACTION_NAME_FIELD = "action_name";
	static final String RULE_SET_COUNT_FIELD = "set_count";
	static final String RULE_SCHEDULED_TIME_FIELD = "scheduled_time";
	static final String RULE_LAST_FIRED_TIME_FIELD = "last_fired_time";

	static final String RULE_SET_CONDITION_KEY_FIELD = "set_condition_key";

	static final String RULE_CLEAR_CONDITION_KEY_FIELD = "clear_condition_key";

	static final String RULE_TABLE_NAME = "rule_info";

	static final String SESSION_RULE_NAME_FIELD = "rule_name";

	static final String SESSION_RULE_DETAIL_FIELD = "rule_detail";

	static final String SESSION_INFO_SPACE_NAME = "session_info";

	static final String SESSION_ID_FIELD = "session_id";

	static final String SESSION_NAME_FIELD = "session_name";

	static final String SESSION_QUERY_NAME_FIELD = "query_name";

	static final String SESSION_QUERY_DETAIL_FIELD = "query_detail";

	static final String UPDATED_DATE_TIME_FIELD = "updated_time";

	static final String CREATED_DATE_TIME_FIELD = "created_time";

	static final String PURGE_QUERY_FIELD = "purge_query";

	static final String PURGED_ITEM_COUNT = "purge_count";

	ASResourceFactory resourceFactory;
	SchemaManager schemaManager;
	boolean usePK;

	@Override
	public void init(Properties configuration) throws Exception {
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Initializing Persistence service..Using Tibco Data Grid");
		}
		usePK = System.getProperty("use_pk", "true").equals("true");

		super.init(configuration);
		resourceFactory = ASResourceFactory.getInstance(configuration);
		resourceFactory.init();

		schemaManager = new SchemaManager(this);

		schemaManager.getOrCreateSessionSchema();
		for (RtaSchema schema : ServiceProviderManager.getInstance().getAdminService().getAllSchemas()) {
			createSchema(schema);
		}
		ServiceProviderManager.getInstance().getAdminService().addModelChangeListener(schemaManager);
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Initializing Persistence service Complete.");
		}
	}

	@Override
	public void start() throws Exception {
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Started Persistence service.");
		}

	}

	@Override
	public void stop() throws Exception {
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Stopped Persistence service.");
		}

	}

	@Override
	public <N> RtaNode getNode(Key key) throws Exception {

		MutableMetricNode metricNode = null;

		if (key instanceof MetricKey) {

			MetricKey metricKey = (MetricKey) key;
			Space space = schemaManager.getMetricSchema(metricKey.getSchemaName(), metricKey.getCubeName(),
					metricKey.getDimensionHierarchyName());

			if (space != null) {
				Tuple tuple = Tuple.create();
				if (usePK) {
					tuple.put(METRIC_KEY_FIELD, key.toString());
				} else {
					tuple.put(DIMENSION_LEVEL_NAME_FIELD, metricKey.getDimensionLevelName());
					for (String mKeyStr : metricKey.getDimensionNames()) {
						Object value = metricKey.getDimensionValue(mKeyStr);
						tuple.put(mKeyStr, value);
					}
				}
				tuple = getTuple(space, tuple);
				if (tuple == null) {
					return null;
				}

				RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(metricKey.getSchemaName());
				Cube cube = schema.getCube(metricKey.getCubeName());
				DimensionHierarchy dh = cube.getDimensionHierarchy(metricKey.getDimensionHierarchyName());
				metricNode = fetchMetricNodeFromTuple(tuple, schema.getName(), cube.getName(), dh.getName());
			}
		}
		return metricNode;
	}

	private Tuple getTuple(Space space, Tuple queryTuple) throws ASException {
		int attempt = 0;
		while (true) {
			try {
				attempt++;
				return space.get(queryTuple);
			} catch (ASException exception) {
				if (exception.getStatus() == ASStatus.OPERATION_TIMEOUT) {
					LOGGER.log(Level.ERROR, "Get operation timed out. Space [%s] not ready for operation",
							space.getName());
					LOGGER.log(Level.ERROR,
							"Attempting retry to put operation, counter [%s] times after waiting for [%s] milis",
							attempt, space.getSpaceDef().getSpaceWait());
				} else {
					throw exception;
				}
			}
		}
	}

	public MetricNodeImpl fetchMetricNodeFromTuple(Tuple tuple, String schemaName, String cubeName,
			String dimensionHierarchyName) throws ASException {
		// MutableMetricNode metricNode;
		// metricNode = new MetricNodeImpl(key);

		RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
		Cube cube = schema.getCube(cubeName);
		DimensionHierarchy hierarchy = cube.getDimensionHierarchy(dimensionHierarchyName);

		if (tuple == null) {
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, "No more results to be fetched");
			}
			return null;
		}

		String dimLevelName = tuple.getString(DIMENSION_LEVEL_NAME_FIELD);
		MetricKeyImpl metricKey = (MetricKeyImpl) KeyFactory.newMetricKey(schemaName, cubeName, dimensionHierarchyName,
				dimLevelName);
		int level = hierarchy.getLevel(dimLevelName);
		for (int i = 0; i <= level; i++) {
			Dimension d = hierarchy.getDimension(i);
			String dimName = d.getName();
			Object value = tuple.get(dimName);
			metricKey.addDimensionValueToKey(dimName, value);
		}
		// add null values to rest of the dimensions
		for (int i = level + 1; i <= hierarchy.getDepth() - 1; i++) {
			String dimName = hierarchy.getDimension(i).getName();
			metricKey.addDimensionValueToKey(dimName, null);
		}

		MetricNodeImpl metricNode = new MetricNodeImpl(metricKey);

		for (Measurement measurement : hierarchy.getMeasurements()) {
			BaseMetricImpl metric = null;
			String metricName = measurement.getName();

			Object metricVal = tuple.get(metricName);
			if (metricVal == null) {
				continue;
			}

			MetricFunctionDescriptor md = measurement.getMetricFunctionDescriptor();
			if (md.isMultiValued()) {
				MultiValueMetricImpl mvm = new MultiValueMetricImpl();
				if (metricVal != null) {
					Tuple metricValuesTuple = Tuple.create();
					metricValuesTuple.deserialize((byte[]) metricVal);
					int size = metricValuesTuple.getInt("size");
					List<Object> vals = new ArrayList<Object>();
					for (int i = 0; i < size; i++) {
						Object val = metricValuesTuple.get("v" + i);
						vals.add(val);
					}
					mvm.setValues(vals);
				}
				metric = mvm;
			} else {
				SingleValueMetricImpl svm = new SingleValueMetricImpl();
				svm.setValue(metricVal);
				metric = svm;
			}

			RtaNodeContextImpl nodeContext = new RtaNodeContextImpl();
			for (FunctionParam param : measurement.getMetricFunctionDescriptor().getFunctionContexts()) {
				Object value = tuple.get(measurement.getName() + ASPersistenceService.SEP + param.getName());
				nodeContext.setTuple(param.getName(), value);
			}

			metric.setKey(metricKey);
			MetricValueDescriptorImpl metricValueDesc = new MetricValueDescriptorImpl(
					metricKey.getDimensionLevelName(), metricKey.getDimensionHierarchyName(), metricKey.getCubeName(),
					metricKey.getSchemaName(), measurement.getName());
			metric.setDescriptor(metricValueDesc);
			metric.setMetricName(metricName);
			Object dimensionValue = metricKey.getDimensionValue(metricKey.getDimensionLevelName());
			metric.setDimensionValue(dimensionValue);

			metricNode.setMetric(metricName, metric);
			metricNode.setContext(metricName, nodeContext);

		}

		MetricKey parentKey = new MetricKeyImpl(metricKey);
		parentKey.addDimensionValueToKey(metricKey.getDimensionLevelName(), null);

		metricNode.setParentKey(parentKey);
		return metricNode;
	}

	// @Override
	// public Fact getFact(Key key) throws Exception {
	// Fact fact = null;
	// if (key instanceof FactKeyImpl) {
	// FactKeyImpl factKey = (FactKeyImpl) key;
	// Space factSpace = schemaManager.getFactSchema(factKey.getSchemaName());
	// fact = getFactFromFactTuple(factSpace, factKey.getUid());
	//
	// }
	// return fact;
	// }

	@Override
	public void save(Fact fact) throws Exception {
		RtaSchema schema = fact.getOwnerSchema();
		Space space = schemaManager.getFactSchema(schema.getName());
		Tuple tuple = factToTuple(fact);
		saveTuple(space, tuple);
	}

	protected Tuple factToTuple(Fact fact) throws Exception {
		RtaSchema schema = fact.getOwnerSchema();
		Space space = schemaManager.getFactSchema(schema.getName());

		Tuple tuple = Tuple.create();
		tuple.putString(FACT_KEY_FIELD, ((FactKeyImpl) fact.getKey()).getUid());
		tuple.putString(OWNER_SCHEMA_FIELD, fact.getOwnerSchema().getName());
		tuple.putDateTime(CREATED_DATE_TIME_FIELD, DateTime.create(System.currentTimeMillis()));
		tuple.putDateTime(UPDATED_DATE_TIME_FIELD, DateTime.create(System.currentTimeMillis()));

		for (Attribute attribute : schema.getAttributes()) {
			Object value = fact.getAttribute(attribute.getName());
			tuple.put(attribute.getName(), value);
		}
		return tuple;
	}

	static void saveTuple(Space space, Tuple tuple) throws Exception {

		int attempt = 0;
		while (true) {
			try {
				attempt++;

				// PutOptions po = PutOptions.create();
				// po.setLockWait(SpaceDef.DEFAULT_LOCK_WAIT);
				// // po.setLockWait(10000);
				space.put(tuple);
				break;
			} catch (ASException exception) {
				// ASPersistenceService.LOGGER.log(Level.ERROR,
				// "Failed to lock key " + attempt);
				if (exception.getStatus() == ASStatus.LOCKED) {
					if (attempt == 10) {
						throw new Exception("failed to lock key 10 times");
					}
				} else if (exception.getStatus() == ASStatus.OPERATION_TIMEOUT) {
					LOGGER.log(Level.ERROR, "Put operation timed out. Space [%s] not ready for operation",
							space.getName());
					LOGGER.log(Level.ERROR,
							"Attempting retry to put operation, counter [%s] times after waiting for [%s] milis",
							attempt, space.getSpaceDef().getSpaceWait());
				} else {
					throw exception;
				}
			}
		}
	}

	@Override
	public void save(RtaNode node) throws Exception {

		Tuple tuple = nodeToTuple(node);

		MetricNode metricNode = (MetricNode) node;
		MetricKey mKey = (MetricKey) metricNode.getKey();

		Space space = schemaManager.getMetricSchema(mKey.getSchemaName(), mKey.getCubeName(),
				mKey.getDimensionHierarchyName());

		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, "Saving tuple %s to OM", tuple);
		}
		saveTuple(space, tuple);
	}

	protected Tuple nodeToTuple(RtaNode node) throws Exception {
		if (!(node instanceof MetricNode)) {
			return null;
		}

		MetricNode metricNode = (MetricNode) node;
		MetricKey mKey = (MetricKey) metricNode.getKey();
		Tuple tuple = Tuple.create();

		Space space = schemaManager.getMetricSchema(mKey.getSchemaName(), mKey.getCubeName(),
				mKey.getDimensionHierarchyName());

		DimensionHierarchy dh = ModelRegistry.INSTANCE.getRegistryEntry(mKey.getSchemaName())
				.getCube(mKey.getCubeName()).getDimensionHierarchy(mKey.getDimensionHierarchyName());

		if (space != null) {
			// tuple.put(DIMHR_NAME, mKey.getDimensionHierarchyName());

			// simple key
			tuple.put(DIMENSION_LEVEL_NAME_FIELD, mKey.getDimensionLevelName());
			StringBuilder sb = new StringBuilder();
			sb.append(mKey.getDimensionLevelName());

			if (usePK) {
				tuple.put(METRIC_KEY_FIELD, mKey.toString());
			}

			for (Dimension dimension : dh.getDimensions()) {
				tuple.put(dimension.getName(), mKey.getDimensionValue(dimension.getName()));
			}
			for (Measurement measurement : dh.getMeasurements()) {
				Object value = null;
				Metric metric = metricNode.getMetric(measurement.getName());
				// if the measurement is excluded for the level, then
				if(metric == null) {
					continue;
				}
				if (!metric.isMultiValued()) {
					SingleValueMetric svm = (SingleValueMetric) metric;
					value = svm.getValue();
				} else {
					MultiValueMetric mvm = (MultiValueMetric) metric;
					List<Object> vals = mvm.getValues();
					Tuple multiVals = Tuple.create();

					for (int i = 0; i < vals.size(); i++) {
						multiVals.put("v" + i, vals.get(i));
					}
					multiVals.putInt("size", vals.size());
					value = multiVals.serialize();
				}
				if (value instanceof BigDecimal) {
					tuple.put(measurement.getName(), ((BigDecimal) value).doubleValue());
				} else {
					tuple.put(measurement.getName(), value);
				}
				RtaNodeContext context = metricNode.getContext(measurement.getName());
				MetricFunctionDescriptor md = measurement.getMetricFunctionDescriptor();
				for (FunctionParam param : md.getFunctionContexts()) {
					value = context.getTupleValue(param.getName());
					if (value instanceof BigDecimal) {
						tuple.put(measurement.getName() + ASPersistenceService.SEP + param.getName(),
								((BigDecimal) value).doubleValue());
					} else {
						tuple.put(measurement.getName() + ASPersistenceService.SEP + param.getName(), value);
					}
				}
			}
			long t = System.currentTimeMillis();
			tuple.put(CREATED_DATE_TIME_FIELD, DateTime.create(t));
			tuple.put(UPDATED_DATE_TIME_FIELD, DateTime.create(t));
		}
		return tuple;
	}

	@Override
	public void delete(RtaNode node) {
		// TODO Auto-generated method stub
		MetricKey metricKey = (MetricKey) node.getKey();
		try {
			Space space = schemaManager.getOrCreateMetricSchema(metricKey.getSchemaName(), metricKey.getCubeName(),
					metricKey.getDimensionHierarchyName());
			Tuple queryTuple = Tuple.create();
			queryTuple.putString(METRIC_KEY_FIELD, metricKey.toString());
			Tuple removedTuple = space.take(queryTuple);
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, "Metric node removed with metric key [%s]", removedTuple.get(METRIC_KEY_FIELD));
			}
		} catch (ASException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public RtaNode getParentNode(RtaNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFact(RtaNode node, Fact fact) throws Exception {

		if (node instanceof MetricNode) {
			MetricNode metricNode = (MetricNode) node;

			Space space = schemaManager.getMetricFactSchema(metricNode);
			if (space != null) {
				Tuple tuple = Tuple.create();
				if (usePK) {
					StringBuilder sb = new StringBuilder();

					MetricKey mKey = (MetricKey) node.getKey();
					tuple.putString(DIMENSION_LEVEL_NAME_FIELD, mKey.getDimensionLevelName());
					sb.append(mKey.getDimensionLevelName());

					for (String dimensionName : mKey.getDimensionNames()) {
						Object value = mKey.getDimensionValue(dimensionName);
						tuple.put(dimensionName, value);
						sb.append("/");
						if (value == null) {
							sb.append("null");
						} else {
							sb.append(value);
						}
					}
					tuple.putString(FACT_KEY_FIELD, ((FactKeyImpl) fact.getKey()).getUid());

					sb.append("/").append(((FactKeyImpl) fact.getKey()).getUid());

					tuple.putString(PK_KEY, sb.toString());

					saveTuple(space, tuple);

				} else {
					MetricKey mKey = (MetricKey) node.getKey();
					tuple.putString(DIMENSION_LEVEL_NAME_FIELD, mKey.getDimensionLevelName());
					for (String dimensionName : mKey.getDimensionNames()) {
						Object value = mKey.getDimensionValue(dimensionName);
						tuple.put(dimensionName, value);
					}
					tuple.putString(FACT_KEY_FIELD, ((FactKeyImpl) fact.getKey()).getUid());

					// Tuple t2 = space.get(tuple);
					// if (t2 == null) {
					// if (LOGGER.isEnabledFor(Level.DEBUG)) {
					// LOGGER.log(Level.DEBUG, "METRIC-FACT %s", tuple);
					// }
					saveTuple(space, tuple);
					// }
				}
			}
		}
	}

	// @Override
	// public void addChildNode(RtaNode parent, RtaNode childNode) {
	// // TODO Auto-generated method stub
	//
	// }

	@Override
	public com.tibco.rta.query.Browser<MetricNode> getMetricNodeBrowser(QueryDef queryDef) throws Exception {
		if (queryDef instanceof QueryByKeyDef) {
			return new KeyBasedMetricNodeBrowser((QueryByKeyDef) queryDef, this);
		} else {
			return new FilterBasedMetricNodeBrowser((QueryByFilterDef) queryDef, this);
		}
	}

	@Override
	public com.tibco.rta.query.Browser<Fact> getFactBrowser(RtaNode node, List<MetricFieldTuple> orderByList) {

		try {
			if (node instanceof MetricNode) {
				return new FactNodeBrowser(this, (MetricNode) node);
			}
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "", e);
		}
		return new EmptyIterator<Fact>();

	}

	@Override
	public void createSchema(RtaSchema schema) throws Exception {
		schemaManager.createSchema(schema);
	}

	@Override
	public void printAllNodes() {
		try {
			for (Space space : schemaManager.getAllSchemas()) {
				if (LOGGER.isEnabledFor(Level.INFO)) {
					LOGGER.log(Level.INFO, "Space Name %s", space.getName());
				}
				BrowserType browserType = BrowserType.GET;
				BrowserDef def = BrowserDef.create();
				def.setDistributionScope(DistributionScope.ALL);
				def.setTimeScope(TimeScope.SNAPSHOT);

				Browser browser = space.browse(browserType, def);

				Tuple tuple = null;
				// System.out.println("**********************************************************************");
				while ((tuple = browser.next()) != null) {
					if (LOGGER.isEnabledFor(Level.DEBUG)) {
						LOGGER.log(Level.DEBUG, "%s", tuple);
					}
				}
				// System.out.println("**********************************************************************");

			}
		} catch (ASException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Fact getFactFromFactTuple(Space factSpace, String factKey) throws ASException, Exception {
		FactImpl fact;

		Tuple keyTuple = Tuple.create();
		keyTuple.putString(FACT_KEY_FIELD, factKey);

		Tuple valueTuple = factSpace.get(keyTuple);
		byte[] bytes = valueTuple.getBlob("FACT");

		fact = IOUtils.deserialize(bytes);
		RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(fact.getOwnerSchemaName());
		fact.setOwnerSchema(schema);
		//
		// for (Cube c : schema.getCubes()) {
		// if
		// (c.getMeasurement().getName().equals(fact.getOwnerMeasurementName()))
		// {
		// fact.setMeasurement(c.getMeasurement());
		// }
		// }
		return fact;
	}

	SchemaManager getSchemaManager() {
		return schemaManager;
	}

	Object deserialize(byte[] bytes) throws Exception {
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
		Object o = ois.readObject();
		ois.close();
		return o;
	}

	ASResourceFactory getResourceFactory() {
		return resourceFactory;
	}

	Logger getLogger() {
		return LOGGER;
	}

	@Override
	public void delete(Fact node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteFact(RtaNode metricNode, Fact fact) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beginTransaction() throws Exception {
		if(resourceFactory.isApplyTxn()) {
		resourceFactory.getMetaspace().beginTransaction();
		}
	}

	@Override
	public void commit() throws Exception {
		if(resourceFactory.isApplyTxn()) {
		resourceFactory.getMetaspace().commitTransaction();
		}		
	}

	@Override
	public void rollback() throws Exception {
		if(resourceFactory.isApplyTxn()) {
		resourceFactory.getMetaspace().rollbackTransaction();
		}
	}

	@Override
	public void saveProcessedFact(Fact fact, String schemaName, String cubeName, String hierarhyName) throws Exception {

		Tuple tuple = Tuple.create();

		FactKeyImpl fk = (FactKeyImpl) fact.getKey();
		tuple.putString("factid", fk.getUid());
		tuple.putString("schema", schemaName);
		tuple.putString("cube", cubeName);
		tuple.putString("hierarchy", hierarhyName);
		// tuple.putString("measurement", measurementName);

		Space space = schemaManager.getProcessedFactsSchema(schemaName, cubeName, hierarhyName);
		saveTuple(space, tuple);
	}

	@Override
	public RuleMetricNodeState getRuleMetricNode(String ruleName, String actionName, MetricKey key) {
		RuleMetricNodeState rmNode = null;
		try {
			Space space = schemaManager.getRuleMetricSchema(key);
			Tuple queryTuple = Tuple.create();
			queryTuple.putString(METRIC_KEY_FIELD, key.toString());
			queryTuple.putString(RULE_NAME_FIELD, ruleName);
			queryTuple.putString(RULE_ACTION_NAME_FIELD, actionName);

			Tuple valueTuple = getTuple(space, queryTuple);
			rmNode = fetchRuleMetricNodeFromTuple(valueTuple, key.getSchemaName(), key.getCubeName(),
					key.getDimensionHierarchyName());

		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "An error while fetching RuleMetricNode. MetricKey =  %s ", e, key);
		}
		return rmNode;
	}

	RuleMetricNodeState fetchRuleMetricNodeFromTuple(Tuple valueTuple, String schemaName, String cubeName,
			String hierarchyName) {
		// if (valueTuple != null) {
		// rmNode = new RuleMetricNodeStateImpl(ruleName,
		// valueTuple.getString(RULE_ACTION_NAME_FIELD), key);
		// rmNode.setCount(valueTuple.getInt(RULE_SET_COUNT_FIELD));
		// rmNode.setLastFireTime(valueTuple
		// .getLong(RULE_LAST_FIRED_TIME_FIELD));
		// rmNode.setScheduledTime(valueTuple
		// .getLong(RULE_SCHEDULED_TIME_FIELD));
		// }
		// return rmNode;

		RuleMetricNodeState ruleNode = null;
		try {
			if (valueTuple == null) {
				return null;
			}

			String dimLevelName = valueTuple.getString(DIMENSION_LEVEL_NAME_FIELD);
			MetricKeyImpl key = (MetricKeyImpl) KeyFactory.newMetricKey(schemaName, cubeName, hierarchyName,
					dimLevelName);
			RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
			Cube cube = schema.getCube(cubeName);
			DimensionHierarchy hierarchy = cube.getDimensionHierarchy(hierarchyName);

			int level = hierarchy.getLevel(dimLevelName);
			for (int i = 0; i <= level; i++) {
				Dimension d = hierarchy.getDimension(i);
				String dimName = d.getName();
				Object value = valueTuple.get(dimName);
				key.addDimensionValueToKey(dimName, value);
			}
			// add null values to rest of the dimensions
			for (int i = level + 1; i <= hierarchy.getDepth() - 1; i++) {
				String dimName = hierarchy.getDimension(i).getName();
				key.addDimensionValueToKey(dimName, null);
			}

			String ruleName = valueTuple.getString(RULE_NAME_FIELD);
			String ruleActionName = valueTuple.getString(RULE_ACTION_NAME_FIELD);

			ruleNode = new RuleMetricNodeStateImpl(ruleName, ruleActionName, key);
			ruleNode.setCount(valueTuple.getInt(RULE_SET_COUNT_FIELD));
			ruleNode.setLastFireTime(valueTuple.getLong(RULE_LAST_FIRED_TIME_FIELD));
			ruleNode.setScheduledTime(valueTuple.getLong(RULE_SCHEDULED_TIME_FIELD));

		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "An error occurred, while fetching rule metric node state from Database.", e);
		}
		return ruleNode;
	}

	@Override
	public void saveRuleMetricNode(RuleMetricNodeState rmNode) {
		RuleMetricNodeStateKey rmKey = rmNode.getKey();
		MetricKey mKey = rmKey.getMetricKey();
		try {
			Space space = schemaManager.getRuleMetricSchema(mKey);
			Tuple tuple = Tuple.create();
			tuple.putString(DIMENSION_LEVEL_NAME_FIELD, mKey.getDimensionLevelName());
			tuple.putString(METRIC_KEY_FIELD, mKey.toString());
			tuple.putString(RULE_NAME_FIELD, rmKey.getRuleName());
			tuple.putString(RULE_ACTION_NAME_FIELD, rmKey.getActionName());
			tuple.putInt(RULE_SET_COUNT_FIELD, rmNode.getCount());
			tuple.putLong(RULE_SCHEDULED_TIME_FIELD, rmNode.getScheduledTime());
			tuple.putLong(RULE_LAST_FIRED_TIME_FIELD, rmNode.getLastFireTime());
			tuple.putString(RULE_SET_CONDITION_KEY_FIELD, (rmNode.getSetConditionKey() == null) ? "" : rmNode
					.getSetConditionKey().toString());
			tuple.putString(RULE_CLEAR_CONDITION_KEY_FIELD, (rmNode.getClearConditionKey() == null) ? "" : rmNode
					.getClearConditionKey().toString());

			DimensionHierarchy dh = ModelRegistry.INSTANCE.getRegistryEntry(mKey.getSchemaName())
					.getCube(mKey.getCubeName()).getDimensionHierarchy(mKey.getDimensionHierarchyName());

			for (Dimension dimension : dh.getDimensions()) {
				String dimensionName = dimension.getName();
				tuple.put(dimensionName, mKey.getDimensionValue(dimensionName));
			}

			// Metric value part
			Collection<Measurement> measurements = dh.getMeasurements();
			for (Measurement measurement : measurements) {
				Object value = null;
				Metric metric = rmNode.getMetricNode().getMetric(measurement.getName());
				if (metric == null) {
				} else if (!metric.isMultiValued()) {
					SingleValueMetric svm = (SingleValueMetric) metric;
					value = svm.getValue();
				} else {
					MultiValueMetric mvm = (MultiValueMetric) metric;
					List<Object> vals = mvm.getValues();
					value = IOUtils.serialize((ArrayList) vals);
				}
				if (value instanceof BigDecimal) {
					tuple.put(measurement.getName(), ((BigDecimal) value).doubleValue());
				} else {
					tuple.put(measurement.getName(), value);
				}
				// pst.setObject(index++,
				// IOUtils.serialize(metricNode.getContext(measurement.getName())));
				RtaNodeContext context = rmNode.getMetricNode().getContext(measurement.getName());
				MetricFunctionDescriptor md = measurement.getMetricFunctionDescriptor();
				for (FunctionParam param : md.getFunctionContexts()) {
					Object paramValue = context.getTupleValue(param.getName());
					if (paramValue instanceof BigDecimal) {
						tuple.put(measurement.getName() + ASPersistenceService.SEP + param.getName(),
								((BigDecimal) paramValue).doubleValue());
					} else {
						tuple.put(measurement.getName() + ASPersistenceService.SEP + param.getName(), paramValue);
					}
				}
			}
			saveTuple(space, tuple);
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "An error while saving RuleMetricNode. MetricKey =  %s ", e, mKey);
		}
	}

	@Override
	public void deleteRuleMetricNode(RuleMetricNodeStateKey key) throws Exception {
		MetricKey mKey = key.getMetricKey();
		Space space = schemaManager.getOrCreateRuleMetricSchema(mKey.getSchemaName(), mKey.getCubeName(),
				mKey.getDimensionHierarchyName());
		Tuple querytuple = Tuple.create();
		querytuple.putString(METRIC_KEY_FIELD, mKey.toString());
		querytuple.putString(RULE_NAME_FIELD, key.getRuleName());
		querytuple.putString(RULE_ACTION_NAME_FIELD, key.getActionName());
		space.take(querytuple);
	}

	/**
	 * Given a dimension value, return all the distinct combination of assets
	 * having this dimension If dimension is null, return all sets
	 * 
	 * This will be used in 2 places: return all assets and return all assets
	 * matching the given asset.
	 */
	@Override
	public com.tibco.rta.query.Browser<MetricNode> getMatchingAssets(String schemaName, String cubeName,
			String hierarchyName, Fact fact) {
		/**
		 * Select distinct (a, b, c..) from fact_schema where a != null && b !=
		 * null && c != null
		 * 
		 * if (dimensionvalue == null) ==> let it imply all values, translates
		 * to no where clause if (dimensionvalue != null) ==> translates to
		 * where dimensionname = dimensionvalue.
		 */
		return new EmptyIterator<MetricNode>();
	}

	// @Override
	// public com.tibco.rta.query.Browser<MetricNode> getDistinctAssets(
	// String schemaName, String cubeName, String hierarchyName)
	// throws Exception {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public void updateProcessedFact(Fact fact, String schemaName, String cubeName, String hierarhyName)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public com.tibco.rta.query.Browser<Fact> getUnProcessedFact(String schemaName) throws Exception {
		// TODO Auto-generated method stub
		return new EmptyIterator<Fact>();
	}

	//TODO: handle retryInfinite.
	@Override
	public void applyTransaction(boolean retryInfinite) throws Exception {

		RtaTransaction txn = (RtaTransaction) RtaTransaction.get();

		while (true) {
			try {
				for (Map.Entry<Key, RtaTransactionElement<?>> e : txn.getTxnList().entrySet()) {

					Key key = e.getKey();
					RtaTransactionElement txnE = e.getValue();

					if (key instanceof MetricKey) {
						MetricNode metricNode = (MetricNode) txnE.getNode();
						if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW)) {
							save(metricNode);
						} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
							save(metricNode);
						} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE)) {
							delete(metricNode);
						}

					} else if (key instanceof FactKeyImpl) {
						Fact fact = (Fact) txnE.getNode();
						if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW)) {
							save(fact);
						} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
							save(fact);
						} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE)) {
							delete(fact);
						}
					} else if (key instanceof RtaTransaction.MetricFact.MFKey) {
						RtaTransaction.MetricFact metricFact = (MetricFact) txnE.getNode();
						if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW)) {
							// saveMetricFactToDB(metricFact.getMetricNode(),
							// metricFact.getFact());
						} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
							// saveMetricFactToDB(metricFact.getMetricNode(),
							// metricFact.getFact());
						} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE)) {

						}
					} else if (key instanceof RuleMetricNodeStateKey) {
						RuleMetricNodeState ruleMetricNodeState = (RuleMetricNodeState) txnE.getNode();
						if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW)) {
							saveRuleMetricNode(ruleMetricNodeState);
						} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
							saveRuleMetricNode(ruleMetricNodeState);
						} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE)) {
							deleteRuleMetricNode(ruleMetricNodeState.getKey());
						}
					} else if (key instanceof FactHr) {
						FactHr factHr = (FactHr) txnE.getNode();
						if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW)) {
							updateProcessedFact(factHr.getFact(), factHr.getSchemaName(), factHr.getCubeName(),
									factHr.getHierarchyName());
						} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
							updateProcessedFact(factHr.getFact(), factHr.getSchemaName(), factHr.getCubeName(),
									factHr.getHierarchyName());
						} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE)) {

						}
					}

				}
				commit();
				break;
			} catch (ASException ex) {
				LOGGER.log(Level.WARN, "SQLException encountered");
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
					LOGGER.log(Level.ERROR, "Database not available.. Waiting for reconnect..");

					LOGGER.log(Level.DEBUG, "Database reconnected. Retrying operation..");
				}
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, "Other error", e);
				throw e;
			} finally {
			}
		}

	}

	@Override
	public com.tibco.rta.query.Browser<MetricNode> getSnapshotMetricNodeBrowser(QueryDef queryDef) throws Exception {
		if (queryDef instanceof QueryByKeyDef) {
			return new KeyBasedMetricNodeBrowser((QueryByKeyDef) queryDef, this);
		} else {
			return new FilterBasedMetricNodeBrowser((QueryByFilterDef) queryDef, this);
		}
	}

	@Override
	public void save(RtaNode node, boolean isNew) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public long purgeMetricsForHierarchy(String schemaName, String cubeName, String hierarchyName, long purgeOlderThan)
			throws Exception {
		DateTime dateTime = DateTime.create(System.currentTimeMillis() - purgeOlderThan);
		String query = UPDATED_DATE_TIME_FIELD + "<" + dateTime.toFilterString();
		Space space = schemaManager.getMetricSchema(schemaName, cubeName, hierarchyName);
		Tuple context = Tuple.create();
		context.put(PURGE_QUERY_FIELD, query);

		InvokeOptions invokeOptions = InvokeOptions.create();
		invokeOptions.setInvocable("com.tibco.rta.service.persistence.as.PurgeInvocableHandler");
		invokeOptions.setContext(context);
		InvokeResultList resultList = space.invokeSeeders(invokeOptions);
		int purge_count = 0;

		for (InvokeResult invokeResult : resultList) {
			purge_count += invokeResult.getReturn().getInt(PURGED_ITEM_COUNT);
		}
		if(LOGGER.isEnabledFor(Level.DEBUG)){
			LOGGER.log(Level.INFO, "Total metric purge count for [%s] : - %d", schemaName + "/" + cubeName + "/" + hierarchyName, purge_count);
		}
		return purge_count;
	}

	@Override
	public long purgeMetricsForQualifier(String schemaName, Qualifier qualifier, long purgeOlderThan,
			boolean storeFacts, boolean storeProcessedFacts) throws Exception {
		DateTime dateTime = DateTime.create(System.currentTimeMillis() - purgeOlderThan);
		String query = CREATED_DATE_TIME_FIELD + "<" + dateTime.toFilterString();
		Space space = schemaManager.getFactSchema(schemaName);
		Tuple context = Tuple.create();
		context.put(PURGE_QUERY_FIELD, query);

		InvokeOptions invokeOptions = InvokeOptions.create();
		invokeOptions.setInvocable("com.tibco.rta.service.persistence.as.PurgeInvocableHandler");
		invokeOptions.setContext(context);
		InvokeResultList resultList = space.invokeSeeders(invokeOptions);
		int purge_count = 0;

		for (InvokeResult invokeResult : resultList) {
			purge_count += invokeResult.getReturn().getInt(PURGED_ITEM_COUNT);
		}
		if(LOGGER.isEnabledFor(Level.DEBUG)){
			LOGGER.log(Level.INFO, "Total facts purge count for [%s] : - %d", schemaName, purge_count);
		}		return purge_count;
	}

	@Override
	public void insertRule(RuleDef ruleDef) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public RuleDef getRule(String ruleName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RuleDef> getAllRuleNames() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteRule(String ruleName) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public com.tibco.rta.query.Browser<Fact> getUnProcessedFact(String schemaName, String cubeName, String hierarchyName)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateRule(RuleDef ruleDef) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public com.tibco.rta.query.Browser<RuleMetricNodeState> getScheduledActions(String schemaName, String cubeName,
			String hierarchyName, long currentTimeMillis) {
		// TODO Auto-generated method stub
		try {
			return new ASRuleMetricNodeStateBrowser(this, schemaName, cubeName, hierarchyName, currentTimeMillis);
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "An error occurred, while fetching Scheduled Actions.", e);
		}
		return new EmptyIterator<RuleMetricNodeState>();
	}


	@Override
	public boolean isSortingProvided() {
		return false;
	}

}
