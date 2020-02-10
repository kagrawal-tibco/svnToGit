package com.tibco.rta.service.persistence.memory.adapter.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.tibco.rta.Fact;
import com.tibco.rta.Metric;
import com.tibco.rta.MetricKey;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RetentionPolicy.Qualifier;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.QueryByKeyDef;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.SortOrder;
import com.tibco.rta.query.impl.EmptyIterator;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNode;
import com.tibco.rta.runtime.model.impl.MetricNodeImpl;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;
import com.tibco.rta.service.persistence.db.DBConstant;
import com.tibco.rta.service.persistence.memory.FactBrowser;
import com.tibco.rta.service.persistence.memory.InMemoryConstant;
import com.tibco.rta.service.persistence.memory.MetricNodeBrowser;
import com.tibco.rta.service.persistence.memory.RuleMetricNodeStateBrowser;
import com.tibco.rta.service.persistence.memory.adapter.SPMDataServiceAdapter;
import com.tibco.store.persistence.exceptions.DuplicateColumnException;
import com.tibco.store.persistence.exceptions.DuplicateTableException;
import com.tibco.store.persistence.exceptions.NoSuchTableException;
import com.tibco.store.persistence.model.MemoryKey;
import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.persistence.service.invm.DataServiceFactory;
import com.tibco.store.persistence.service.invm.InMemoryDataStoreService;
import com.tibco.store.query.model.QueryResultSet;

public class SPMDataServiceAdapterFacade implements SPMDataServiceAdapter {
	
	protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_PERSISTENCE.getCategory());

	public static SPMDataServiceAdapterFacade instance;
	private InMemoryDataStoreService dataService = DataServiceFactory.getInstance().getDataStoreService();

	private SPMDataServiceAdapterFacade() {
	}

	public static SPMDataServiceAdapterFacade getInstance() {
		if (instance == null) {
			instance = new SPMDataServiceAdapterFacade();
		}
		return instance;
	}

	@Override
	public void saveMetricNode(MetricNode node) throws Exception {
		String tableName = RuntimeModelConvertor.getMemoryTableName(node.getKey());
		MemoryTuple tuple = RuntimeModelConvertor.getMemoryTuple(node);

		if (node.isNew()) {
			tuple.setAttribute(InMemoryConstant.CREATED_DATE_TIME_FIELD, new Timestamp(System.currentTimeMillis()));
		}
		if (node.isProcessed())
			tuple.setAttribute(InMemoryConstant.IS_PROCESSED, true);
		else
			tuple.setAttribute(InMemoryConstant.IS_PROCESSED, false);
		
		tuple.setAttribute(InMemoryConstant.UPDATED_DATE_TIME_FIELD, new Timestamp(System.currentTimeMillis()));
		dataService.put(tableName, tuple);
	}

	@Override
	public void deleteMetricNode(MetricNode node) throws Exception {
		String tableName = RuntimeModelConvertor.getMemoryTableName(node.getKey());
		MemoryTuple tuple = RuntimeModelConvertor.getMemoryTuple(node);
		dataService.remove(tableName, tuple);
	}

	@Override
	public MetricNode getMetricNode(MetricKey key) throws Exception {
		String tableName = RuntimeModelConvertor.getMemoryTableName(key);
		MemoryKey mKey = RuntimeModelConvertor.getMemoryKey(key);
		MemoryTuple mTuple = dataService.get(tableName, mKey);

		if (mTuple != null) {
			return RuntimeModelConvertor.getMetricNode(mTuple);
		}
		return null;
	}

	@Override
	public void createSchema(RtaSchema schema, boolean useParitioning) {
		try {
			if (useParitioning) {
				PartitionedTableGenerationUtility.createTables(schema);
				return;
			}
			TableGenerationUtility.createTables(schema);
		} catch (DuplicateTableException e) {
			e.printStackTrace();
		} catch (DuplicateColumnException e) {
			e.printStackTrace();
		} catch (NoSuchTableException e) {
			e.printStackTrace();
		}
	}

	private void checkName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Region cannot have null or empty name");
		}
	}

	@Override
	public Browser<MetricNode> getMatchingAssets(String schemaName, String cubeName, String hierarchyName, Fact fact) throws Exception {

		RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
		if (schema == null) {
			throw new Exception("Schema [" + schemaName + "] not Found");
		}
		Cube cube = schema.getCube(cubeName);
		if (cube == null) {
			throw new Exception("Cube [" + cubeName + "] not Found");
		}

		DimensionHierarchy dh = cube.getDimensionHierarchy(hierarchyName);
		if (dh == null) {
			throw new Exception("Hierarchy [" + hierarchyName + "] not Found");
		}

		QueryDef queryDef = QueryBuilder.buildQueryForAsset(schema, cube, dh, fact);
		return getFilterBasedMetricNodeBrowser((QueryByFilterDef) queryDef);
	}

	@Override
	public void saveRuleMetricNode(RuleMetricNodeState rmNode) throws Exception {
		RuleMetricNodeStateKey rmKey = rmNode.getKey();
		MetricKey mKey = rmKey.getMetricKey();

		String tableName = TableGenerationUtility.getMemoryTableName(InMemoryConstant.RULE_STATE_TABLE_PREFIX, mKey.getSchemaName(), mKey.getCubeName(),
				mKey.getDimensionHierarchyName());

		MemoryKey memoryKey = RuntimeModelConvertor.getMemoryKeyForRuleNode(rmKey);
		MemoryTuple mTuple = RuntimeModelConvertor.getMemoryTuple(rmNode, (RuleStateMemoryKey) memoryKey);

		// add key attributes to tuple also for future use
		mTuple.setAttribute(InMemoryConstant.METRIC_KEY_FIELD, rmKey.getMetricKey().toString());
		mTuple.setAttribute(InMemoryConstant.RULE_NAME_FIELD, rmKey.getRuleName());
		mTuple.setAttribute(InMemoryConstant.RULE_ACTION_NAME_FIELD, rmKey.getActionName());

		mTuple.setAttribute(InMemoryConstant.RULE_SET_COUNT_FIELD, rmNode.getCount());
		mTuple.setAttribute(InMemoryConstant.RULE_SCHEDULED_TIME_FIELD, rmNode.getScheduledTime());
		mTuple.setAttribute(InMemoryConstant.RULE_LAST_FIRED_TIME_FIELD, rmNode.getLastFireTime());

		if (rmNode.isNew()) {
			mTuple.setAttribute(InMemoryConstant.CREATED_DATE_TIME_FIELD, new Timestamp(System.currentTimeMillis()));
		}
		mTuple.setAttribute(InMemoryConstant.UPDATED_DATE_TIME_FIELD, new Timestamp(System.currentTimeMillis()));

		dataService.put(tableName, mTuple);
	}

	@Override
	public RuleMetricNodeState getRuleMetricNode(String ruleName, String actionName, MetricKey key) throws Exception {
		RuleMetricNodeState rmNode = null;
		String tableName = TableGenerationUtility.getMemoryTableName(InMemoryConstant.RULE_STATE_TABLE_PREFIX, key.getSchemaName(), key.getCubeName(),
				key.getDimensionHierarchyName());

		MemoryKey memoryKey = RuntimeModelConvertor.getMemoryKeyForRuleNode(new RuleMetricNodeStateKey(ruleName, actionName, key));

		MemoryTuple mTuple = dataService.get(tableName, memoryKey);

		if (mTuple != null) {
			rmNode = RuntimeModelConvertor.getRuleMetricNode(mTuple);
		} else {
			// log rule not found
		}

		return rmNode;
	}

	@Override
	public Browser<MetricNode> getFilterBasedMetricNodeBrowser(QueryByFilterDef queryDef) throws Exception {
		QueryResultSet tupleIterator = QueryBuilder.executeMetricQuery(queryDef);
		Collection<MetricNode> results = new ArrayList<MetricNode>();
		while (tupleIterator.hasNext()) {
			MetricNode mNode = RuntimeModelConvertor.getMetricNode(tupleIterator.next());
			results.add(mNode);
		}
				
		List<MetricFieldTuple> orderByTuples = queryDef.getOrderByTuples();
		if (orderByTuples != null && !orderByTuples.isEmpty()) {
			performOrderBy(results, orderByTuples, queryDef.getSortOrder());
		}

		return new MetricNodeBrowser(results.iterator());
	}
	
	private void performOrderBy(Collection<MetricNode> results, List<MetricFieldTuple> metricTuples, SortOrder sortOrder) { 
		
		String sortField = null;
		final List<String> sortAttrList = new ArrayList<String>();
		for (MetricFieldTuple tuple : metricTuples) {
			if (tuple.getMetricQualifier() != null ) {
				if (tuple.getMetricQualifier().equals(MetricQualifier.DIMENSION_LEVEL)) {
					sortField = DBConstant.DIMENSION_LEVEL_NAME_FIELD;
				} else if (tuple.getMetricQualifier().equals(MetricQualifier.CREATED_TIME)) {
					sortField = DBConstant.CREATED_DATE_TIME_FIELD;
				} else if (tuple.getMetricQualifier().equals(MetricQualifier.UPDATED_TIME)) {
					sortField = DBConstant.UPDATED_DATE_TIME_FIELD;
				} else if (tuple.getMetricQualifier().equals(MetricQualifier.DIMENSION_LEVEL_NO)) {
					sortField = DBConstant.DIMENSION_LEVEL_FIELD;
				}
				sortAttrList.add(sortField);
			}/*else if (tuple.getKeyQualifier() != null
					&& tuple.getKeyQualifier().equals(FilterKeyQualifier.DIMENSION_NAME)) {
				sortField = tuple.getKey();
				sortAttrList.add(sortField);
			}*/else if (tuple.getKeyQualifier() != null) {
				sortField = tuple.getKey();
				sortAttrList.add(sortField);
			}
		}
		
		Comparator<MetricNode> comparator = new Comparator<MetricNode>() {
			@Override
			public int compare(MetricNode o1, MetricNode o2) {
				ComparisonChain comparisonChain = ComparisonChain.start();
				for(String sortAttr : sortAttrList){
					if(sortAttr.equals(DBConstant.DIMENSION_LEVEL_NAME_FIELD)){
						comparisonChain = comparisonChain.compare(((MetricKey)((MetricNodeImpl)o1).getKey()).getDimensionLevelName(), 
								((MetricKey)((MetricNodeImpl)o2).getKey()).getDimensionLevelName());
					}else if(sortAttr.equals(DBConstant.CREATED_DATE_TIME_FIELD)){
						comparisonChain = comparisonChain.compare(o1.getCreatedTime(), o2.getCreatedTime());
					}else if(sortAttr.equals(DBConstant.UPDATED_DATE_TIME_FIELD)){
						comparisonChain = comparisonChain.compare(o1.getLastModifiedTime(), o2.getLastModifiedTime());
					}else if(sortAttr.equals(DBConstant.DIMENSION_LEVEL_FIELD)){
						comparisonChain = comparisonChain.compare(getLevelNo(o1), getLevelNo(o2));
					}else{
						try{
							List<String> dimNames = ((MetricKey)((MetricNodeImpl)o1).getKey()).getDimensionNames();
							List<String> metricNames = ((MetricNodeImpl)o1).getMetricNames();
							
							if(dimNames.contains(sortAttr)){
								Object s1 = getClassVal(((MetricKey)((MetricNodeImpl)o1).getKey()).getDimensionValue(sortAttr));
								Object s2 =  getClassVal(((MetricKey)((MetricNodeImpl)o2).getKey()).getDimensionValue(sortAttr));
								comparisonChain = comparisonChain.compare((Comparable)s1, (Comparable)s2);
							}
							
							if(metricNames.contains(sortAttr)){
								Object s1 = getClassVal( ((SingleValueMetric)((MetricNodeImpl)o1).getMetric(sortAttr)).getValue() );
								Object s2 = getClassVal( ((SingleValueMetric)((MetricNodeImpl)o2).getMetric(sortAttr)).getValue() );
								comparisonChain = comparisonChain.compare((Comparable)s1, (Comparable)s2, Ordering.natural().nullsLast());
							}
						}catch(NullPointerException e){
							LOGGER.log(Level.ERROR, "Nullpointer while sorting the result tuples in memory", e);
						}
					}
				}
				return comparisonChain.result();
			}
		};
		
		if((sortOrder==null) || (sortOrder == SortOrder.ASCENDING)){
			Collections.sort((List<MetricNode>)results,comparator);
		}else{
			Collections.sort((List<MetricNode>)results, Collections.reverseOrder(comparator));
		}
				
	}
	
	private int getLevelNo(MetricNode node){
		
		MetricKey key = (MetricKey)((MetricNodeImpl)node).getKey();
		RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(key.getSchemaName());
		Cube cube = schema.getCube(key.getCubeName());
		DimensionHierarchy hierarchy = cube.getDimensionHierarchy(key.getDimensionHierarchyName());
		return hierarchy.getLevel(key.getDimensionLevelName());
	}
	
	private Object getClassVal(Object value) {
		if (value instanceof Comparable<?> ) {
			if (value instanceof String) {
				return (String) value;
			} else if (value instanceof Long) {
				return (Long) value;
			} else if (value instanceof Double) {
				return (Double) value;
			} else if (value instanceof Integer) {
				return (Integer) value;
			} else if (value instanceof Float) {
				return (Float) value;
			}else if (value instanceof Boolean) {
				return (Boolean) value;
			}
		}
		return null;
	}

	@Override
	public Browser<MetricNode> getChildMetricNodeBrowser(QueryByKeyDef queryDef) throws Exception {
		// TODO add order by
		if (queryDef.getQueryKey() != null) {
			QueryByFilterDef filterQuery = (QueryByFilterDef) QueryBuilder.buildQueryFromKey(queryDef.getQueryKey());
			return getFilterBasedMetricNodeBrowser(filterQuery);
		}

		return new EmptyIterator<MetricNode>();
	}

	@Override
	public void deleteRuleMetricNode(RuleMetricNodeStateKey rmKey) throws Exception {
		MetricKey mKey = rmKey.getMetricKey();

		String tableName = TableGenerationUtility.getMemoryTableName(InMemoryConstant.RULE_STATE_TABLE_PREFIX, mKey.getSchemaName(), mKey.getCubeName(),
				mKey.getDimensionHierarchyName());
		MemoryKey memoryKey = RuntimeModelConvertor.getMemoryKeyForRuleNode(rmKey);

		dataService.remove(tableName, memoryKey);
	}

	@Override
	public Browser<MetricNode> getKeyBasedMetricNodeBrowser(QueryByKeyDef queryDef) throws Exception {
		MetricKey key = queryDef.getQueryKey();
		MemoryKey memoryKey = RuntimeModelConvertor.getMemoryKey(key);

		String tableName = TableGenerationUtility.getMemoryTableName(InMemoryConstant.METRIC_TABLE_PREFIX, queryDef.getSchemaName(), queryDef.getCubeName(),
				queryDef.getHierarchyName());
		Collection<MetricNode> results = new ArrayList<MetricNode>();
		MemoryTuple mTuple = dataService.get(tableName, memoryKey);

		if (mTuple != null) {
			results.add(RuntimeModelConvertor.getMetricNode(mTuple));
			return new MetricNodeBrowser(results.iterator());
		}
		return new MetricNodeBrowser(results.iterator());
	}

	@Override
	public Browser<RuleMetricNodeState> getScheduledActions(String schemaName, String cubeName, String hierarchyName, long currentTimeMillis) throws Exception {
		QueryResultSet tupleIterator = QueryBuilder.getScheduledActions(schemaName, cubeName, hierarchyName, currentTimeMillis);
		Collection<RuleMetricNodeState> results = new ArrayList<RuleMetricNodeState>();

		while (tupleIterator.hasNext()) {
			RuleMetricNodeState rmNode = RuntimeModelConvertor.getRuleMetricNode(tupleIterator.next());
			if (rmNode != null) {
				results.add(rmNode);
			} else {
				// TODO log rule not found
			}
		}

		return new RuleMetricNodeStateBrowser(results.iterator());
	}

	@Override
	public long purgeMetricsForHierarchy(String schemaName, String cubeName, String hierarchyName, long purgeOlderThan) throws Exception {
		return PurgeService.purgeMetricsForHierarchy(schemaName, cubeName, hierarchyName, purgeOlderThan);
	}

	@Override
	public long purgeMetricsForQualifier(String schemaName, Qualifier qualifier, long purgeOlderThan, boolean storeFacts) throws Exception {
		return PurgeService.purgeMetricsForQualifier(schemaName, qualifier, purgeOlderThan, storeFacts);
	}

	@Override
	public void saveFact(Fact fact) {
		String tableName = RuntimeModelConvertor.getMemoryTableName(fact.getKey());
		MemoryTuple tuple = RuntimeModelConvertor.getMemoryTuple(fact);
		tuple.setAttribute(InMemoryConstant.CREATED_DATE_TIME_FIELD, new Timestamp(System.currentTimeMillis()));
		tuple.setAttribute(InMemoryConstant.UPDATED_DATE_TIME_FIELD, new Timestamp(System.currentTimeMillis()));
		dataService.put(tableName, tuple);
	}

	@Override
	public Browser<Fact> getFactBrowser(RtaNode node, List<MetricFieldTuple> orderByList) throws Exception {
		if (node instanceof MetricNode) {
			QueryByFilterDef queryDef = (QueryByFilterDef) QueryBuilder.buildQueryForFactBrowser((MetricNode) node, orderByList);
			QueryResultSet tupleIterator = QueryBuilder.executeFactQuery(queryDef);
			Collection<Fact> results = new ArrayList<Fact>();

			while (tupleIterator.hasNext()) {
				Fact fact = RuntimeModelConvertor.getFact(tupleIterator.next());
				results.add(fact);
			}

			return new FactBrowser(results.iterator());
		}
		return new EmptyIterator<Fact>();
	}

}
