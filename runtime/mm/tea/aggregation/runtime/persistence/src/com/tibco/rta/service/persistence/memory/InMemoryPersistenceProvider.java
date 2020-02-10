package com.tibco.rta.service.persistence.memory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.xml.sax.InputSource;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.MetricKey;
import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.service.RtaTransaction;
import com.tibco.rta.common.service.RtaTransaction.FactHr;
import com.tibco.rta.common.service.RtaTransaction.RtaTransactionElement;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.impl.FactKeyImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.RetentionPolicy.Qualifier;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.impl.SerializationUtils;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.QueryByKeyDef;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.impl.EmptyIterator;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.MutableMetricNode;
import com.tibco.rta.runtime.model.RtaNode;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;
import com.tibco.rta.service.persistence.PersistenceService;
import com.tibco.rta.service.persistence.memory.adapter.SPMDataServiceAdapter;
import com.tibco.rta.service.persistence.memory.adapter.impl.FactMemoryTuple;
import com.tibco.rta.service.persistence.memory.adapter.impl.FactStore;
import com.tibco.rta.service.persistence.memory.adapter.impl.SPMDataServiceAdapterFacade;
import com.tibco.rta.util.LoggerUtil;
import com.tibco.rta.util.ServiceConstants;

public class InMemoryPersistenceProvider extends AbstractStartStopServiceImpl implements PersistenceService {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_PERSISTENCE.getCategory());

	private Map<RtaSchema, FactStore> factStorage = new HashMap<RtaSchema, FactStore>();
	private SPMDataServiceAdapter dataAdapter = SPMDataServiceAdapterFacade.getInstance();
	private boolean storeFacts;
	private boolean usePartitioning;
	private String ruleStoreLocation   ="";
	private String ruleAppNameSeperator="$"; 		//default is '$'
	private String ruleExtension       ="xml";      //default is 'xml'
	
	@Override
	public void start() throws Exception {
		super.start();
		LoggerUtil.log(LOGGER, Level.INFO, "Starting InMemory Persistence Service");
	}

	@Override
	public void stop() throws Exception {
		// NOT TO IMPLEMENT
	}

	@Override
	public void init(Properties config) throws Exception {
		super.init(config);
		LoggerUtil.log(LOGGER, Level.DEBUG, "Initializing InMemory Persistence Service");

		storeFacts = Boolean.parseBoolean((String) ConfigProperty.RTA_STORE_FACTS.getValue(configuration));		
		usePartitioning = Boolean.parseBoolean((String) ConfigProperty.RTA_INMEMORY_USE_PARTITIONING.getValue(configuration));
		
		ruleExtension=config.getProperty(ServiceConstants.RULE_FILE_STORE_EXTENSION);
		ruleStoreLocation=(String)config.getProperty(ServiceConstants.RULES_DATASTORE);
		ruleAppNameSeperator=config.getProperty(ServiceConstants.RULE_APP_NAME_SEPERATOR);
		
		for (RtaSchema schema : ServiceProviderManager.getInstance().getAdminService().getAllSchemas()) {
			createSchema(schema);
		}
	}

	@Override
	public void createSchema(RtaSchema schema) throws Exception {
		dataAdapter.createSchema(schema, usePartitioning);
		LoggerUtil.log(LOGGER, Level.DEBUG, "Creating InMemory Tables for Schema [%s]", schema.getName());
	}

	@Override
	public <N> RtaNode getNode(Key key) {
		if (key instanceof MetricKey) {
			MetricNode mNode = null;
			try {
				mNode = dataAdapter.getMetricNode((MetricKey) key);
			} catch (Exception e) {
				LoggerUtil.log(LOGGER, Level.ERROR, e, "Exception while getting node [%s]", key);
			}
			return mNode;
		}
		return null;
	}

	@Override
	public RtaNode getParentNode(RtaNode node) {
		try {
			if (!checkNull(node)) {
				if (node instanceof MetricNode) {
					return dataAdapter.getMetricNode((MetricKey) node.getParentKey());
				}
			}
		} catch (Exception e) {
			LoggerUtil.log(LOGGER, Level.ERROR, e, "Exception while getting parent node [%s]", node.getKey());
		}
		return null;
	}

	@Override
	public Browser<MetricNode> getMetricNodeBrowser(QueryDef queryDef) throws Exception {
		LoggerUtil.log(LOGGER, Level.DEBUG, "getMetricNodeBrowser(), QueryDef Name [%s]", queryDef.getName());
		if (!checkNull(queryDef)) {
			if (queryDef instanceof QueryByKeyDef) {
				return dataAdapter.getChildMetricNodeBrowser((QueryByKeyDef) queryDef);
			} else {
				return dataAdapter.getFilterBasedMetricNodeBrowser((QueryByFilterDef) queryDef);
			}
		}
		return new EmptyIterator<MetricNode>();
	}

	@Override
	public Browser<Fact> getFactBrowser(RtaNode node, List<MetricFieldTuple> orderByList) {
//		if (node instanceof MetricNode) {
//			String schemaName = ((MetricKey) node.getKey()).getSchemaName();
//			RtaSchema ownerSchema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
//			FactStore fStore = factStorage.get(ownerSchema);
//			if (fStore == null) {
//				return new EmptyIterator<Fact>();
//			}
//			return new FactBrowser(fStore.getChildFactsIterator(node));
//		} else {
//			return new EmptyIterator<Fact>();
//		}
		try {
			return dataAdapter.getFactBrowser(node, orderByList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new EmptyIterator<Fact>();
	}

	@Override
	public Browser<Fact> getUnProcessedFact(String schemaName, String cubeName, String hierarchyName) throws Exception {
		// NOT TO IMPLEMENT
		return null;
	}

	@Override
	public RuleMetricNodeState getRuleMetricNode(String ruleName, String actionName, MetricKey key) {
		try {
			LoggerUtil.log(LOGGER, Level.DEBUG, "getRuleMetricNode(), Rule Name [%s]", ruleName);
			return dataAdapter.getRuleMetricNode(ruleName, actionName, key);
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.log(LOGGER, Level.ERROR, "Exception while getting rule metric node: [%s]", e.getMessage());
		}
		return null;
	}

	@Override
	public Browser<MetricNode> getSnapshotMetricNodeBrowser(QueryDef queryDef) throws Exception {
		LoggerUtil.log(LOGGER, Level.DEBUG, "getSnapshotMetricNodeBrowser(), QueryDef Name : %s", queryDef.getName());

		if (queryDef instanceof QueryByKeyDef) {
			return dataAdapter.getKeyBasedMetricNodeBrowser((QueryByKeyDef) queryDef);
		} else {
			return dataAdapter.getFilterBasedMetricNodeBrowser((QueryByFilterDef) queryDef);
		}
	}

	@Override
	public Browser<MetricNode> getMatchingAssets(String schemaName, String cubeName, String hierarchyName, Fact fact) throws Exception {
		LoggerUtil.log(LOGGER, Level.DEBUG, "getMatchingAssets for Fact [%s]", fact.getKey());
		return dataAdapter.getMatchingAssets(schemaName, cubeName, hierarchyName, fact);
	}

	@Override
	public Browser<RuleMetricNodeState> getScheduledActions(String schemaName, String cubeName, String hierarchyName, long currentTimeMillis) {
		try {
			return dataAdapter.getScheduledActions(schemaName, cubeName, hierarchyName, currentTimeMillis);
		} catch (Exception e) {
			LoggerUtil.log(LOGGER, Level.ERROR, e, "Exception while gettting scheduled actions");
		}
		return new EmptyIterator<RuleMetricNodeState>();
	}

	@Override
	public com.tibco.rta.query.Browser<Fact> getUnProcessedFact(String schemaName) throws Exception {
		// NOT TO IMPLEMENT
		return new EmptyIterator<Fact>();
	}

	@Override
	public void save(RtaNode node) {
		try {
			if (!checkNull(node)) {
				LoggerUtil.log(LOGGER, Level.DEBUG, "Saving node [%s]", node.getKey());
				if (node instanceof MetricNode) {
					dataAdapter.saveMetricNode((MetricNode) node);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.log(LOGGER, Level.ERROR, "Exception while saving node [%s]", e.getMessage());
		}
	}

	@Override
	public void save(Fact fact) {
//		if (!checkNull(fact)) {
//			LoggerUtil.log(LOGGER, Level.DEBUG, "Saving fact [%s]", fact.getKey());
//			FactStore fStore = factStorage.get(fact.getOwnerSchema());
//			if (fStore == null) {
//				fStore = new FactStore(fact.getOwnerSchema());
//				factStorage.put(fact.getOwnerSchema(), fStore);
//			}
//			FactMemoryTuple wrapper = new FactMemoryTuple(fact);
//			wrapper.setProperty(InMemoryConstant.CREATED_DATE_TIME_FIELD, System.currentTimeMillis());
//			wrapper.setProperty(InMemoryConstant.UPDATED_DATE_TIME_FIELD, System.currentTimeMillis());
//			fStore.save(wrapper);
//		}
		dataAdapter.saveFact(fact);
	}

	@Override
	public void save(RtaNode node, boolean isNew) throws Exception {
		if (!(node instanceof MetricNode)) {
			return;
		}
		((MutableMetricNode) node).setIsNew(isNew);
		save(node);
	}

	@Override
	public void saveProcessedFact(Fact fact, String schemaName, String cubeName, String hierarhyName) throws Exception {
		// NOT TO IMPLEMENT
	}

	@Override
	public void saveRuleMetricNode(RuleMetricNodeState rmNode) {
		try {
			if (!checkNull(rmNode)) {
				LoggerUtil.log(LOGGER, Level.DEBUG, "Saving RuleMetricNode [%s]", rmNode.getKey());
				dataAdapter.saveRuleMetricNode(rmNode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.log(LOGGER, Level.ERROR, "Exception while getting rule metric node: [%s]", e.getMessage());
		}
	}

	private boolean checkNull(Object obj) {
		return obj == null;
	}

	@Override
	public void delete(RtaNode node) {
		try {
			if (!checkNull(node)) {
				LoggerUtil.log(LOGGER, Level.DEBUG, "Deleting node [%s]", node.getKey());
				if (node instanceof MetricNode) {
					dataAdapter.deleteMetricNode((MetricNode) node);
				}
			}
		} catch (Exception e) {
			LoggerUtil.log(LOGGER, Level.ERROR, "Exception while deleting node [%s]", e.getMessage());
		}
	}

	@Override
	public void addFact(RtaNode node, Fact fact) {
		if (!checkNull(fact) && !checkNull(node)) {
			LOGGER.log(Level.DEBUG, "Adding fact [%s]", fact.getKey());
			FactStore fStore = getFactStore(fact);
			FactMemoryTuple wrapper = new FactMemoryTuple(fact);
			wrapper.setProperty(InMemoryConstant.CREATED_DATE_TIME_FIELD, System.currentTimeMillis());
			wrapper.setProperty(InMemoryConstant.UPDATED_DATE_TIME_FIELD, System.currentTimeMillis());

			fStore.addFact(node, wrapper);
		}
	}

	private FactStore getFactStore(Fact fact) {
		FactStore fStore = factStorage.get(fact.getOwnerSchema());
		if (fStore == null) {
			fStore = new FactStore(fact.getOwnerSchema());
			factStorage.put(fact.getOwnerSchema(), fStore);
		}
		return fStore;
	}

	@Override
	public void deleteFact(RtaNode node, Fact fact) throws Exception {
		if (!checkNull(fact) && !checkNull(node)) {
			LOGGER.log(Level.DEBUG, "Deleting fact [%s]", fact.getKey());
			FactStore fStore = getFactStore(fact);
			fStore.deleteFact(node, new FactMemoryTuple(fact));
		}
	}

	@Override
	public void delete(Fact fact) {
		if (!checkNull(fact)) {
			FactStore fStore = getFactStore(fact);
			fStore.delete(new FactMemoryTuple(fact));
			LOGGER.log(Level.DEBUG, "Deleting fact [%s]", fact.getKey());
		}
	}

	@Override
	public void beginTransaction() {
		// NOT TO IMPLEMENT
	}

	@Override
	public void commit() {
		// NOT TO IMPLEMENT
	}

	@Override
	public void rollback() {
		// NOT TO IMPLEMENT
	}

	//TODO: handle retryInfinite. May not really matter for in-memory implementation.
	@Override
	public void applyTransaction(boolean retryInfinite) throws Exception {
		int retryCnt = 0;
		RtaTransaction txn = (RtaTransaction) RtaTransaction.get();
		while (true) {
			retryCnt++;
			if (retryCnt > 1) {
				LoggerUtil.log(LOGGER, Level.DEBUG, "Retrying transaction. Retry count [%d]", retryCnt);
			}
			try {
				for (Map.Entry<Key, RtaTransactionElement<?>> e : txn.getTxnList().entrySet()) {
					Key key = e.getKey();
					RtaTransactionElement txnE = e.getValue();

					if (key instanceof MetricKey) {
						MetricNode metricNode = (MetricNode) txnE.getNode();
						if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW)) {
							save(metricNode, true);
						} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
							save(metricNode, false);
						} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE)) {
							delete(metricNode);
						}

					} else if (key instanceof FactKeyImpl) {
						Fact fact = (Fact) txnE.getNode();
						if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW)) {
							save(fact);
						} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
							// no need to update fact
						} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE)) {
							delete(fact);
						}
					} else if (key instanceof RtaTransaction.MetricFact.MFKey) {
						if (storeFacts) {
//							RtaTransaction.MetricFact metricFact = (MetricFact) txnE.getNode();
//							if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW)) {
//								addFact(metricFact.getMetricNode(), metricFact.getFact());
//							} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
//								addFact(metricFact.getMetricNode(), metricFact.getFact());
//							} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.DELETE)) {
//
//							}
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
						} else if (txnE.getTxnType().equals(RtaTransaction.TxnType.UPDATE)) {
						}
					} 
//					else if (key instanceof AssetKeyImpl) {
//						AssetFact asset = (AssetFact) txnE.getNode();
//						if (txnE.getTxnType().equals(RtaTransaction.TxnType.NEW)) {
//							saveAsset(asset);
//						}
//					}
				}
				commit();
				break;
			} catch (Exception e) {
				LoggerUtil.log(LOGGER, Level.ERROR, "Error while applying transaction.", e);
				throw e;
			}
		}
	}

	@Override
	public void deleteRuleMetricNode(RuleMetricNodeStateKey key) {
		try {
			if (!checkNull(key)) {
				dataAdapter.deleteRuleMetricNode(key);
			}
		} catch (Exception e) {
			LoggerUtil.log(LOGGER, Level.ERROR, e, "Exception while deleting rule metric node : [%s]", key);
		}
	}

	@Override
	public void updateProcessedFact(Fact fact, String schemaName, String cubeName, String hierarhyName) throws Exception {
		// NOT TO IMPLEMENT
	}

	@Override
	public long purgeMetricsForHierarchy(String schemaName, String cubeName, String hierarchyName, long purgeOlderThan) {
		try {
			long purgeCount = dataAdapter.purgeMetricsForHierarchy(schemaName, cubeName, hierarchyName, purgeOlderThan);
			LoggerUtil.log(LOGGER, Level.DEBUG, "Number of metrics purged  for hierarchy [%s] = [%s]", hierarchyName, purgeCount);
			return purgeCount;
		} catch (Exception e) {
			LoggerUtil.log(LOGGER, Level.ERROR, e, "Exception while purging for hierarchy [%s]", hierarchyName);
		}
		return 0;
	}

	@Override
	public long purgeMetricsForQualifier(String schemaName, Qualifier qualifier, long purgeOlderThan, boolean storeFacts, boolean storeProcessedFacts) {
		try {
			long purgeCount = 0;
			if (qualifier.equals(Qualifier.FACT)) {
				if (storeFacts) {
					purgeCount = purgeFacts(schemaName, purgeOlderThan);
				}
				LoggerUtil.log(LOGGER, Level.DEBUG, "Number of Facts purged [%s]", purgeCount);
			}
			return purgeCount;
		} catch (Exception e) {
			LoggerUtil.log(LOGGER, Level.ERROR, e, "Exception while purging for qualifier [%s]", qualifier);
		}
		return 0;
	}

	private long purgeFacts(String schemaName, long purgeOlderThan) {
		long purgeCount = 0;
		for (FactStore factStore : factStorage.values()) {
			purgeCount = purgeCount + factStore.purgeFacts(purgeOlderThan);
		}
		return purgeCount;
	}

	@Override
	public RuleDef getRule(String ruleName) throws Exception {
		// DO NOT STORE HERE LET IT BE IN RULE-REGISTRY
		return null;
	}

	@Override
	public List<RuleDef> getAllRuleNames() throws Exception {
		ArrayList<RuleDef> allRules=new ArrayList<RuleDef>();
		if(ruleStoreLocation!=null&&!ruleStoreLocation.isEmpty()) {
			File ruleStore=new File(ruleStoreLocation);
			for (final File application : ruleStore.listFiles()) {
				if (application!=null&&application.isDirectory()) {
					
					File[] filteredFiles = application.listFiles(new FilenameFilter() {
						@Override
						public boolean accept(File paramFile, String fileName) {
							return fileName.endsWith(ruleExtension) ? true : false;
						}
					});
					
					for (final File ruleEntry : filteredFiles) {
						if (ruleEntry!=null&&!ruleEntry.isDirectory()) {
							InputSource source=new InputSource(new FileInputStream(ruleEntry));
							RuleDef rule=SerializationUtils.deserializeRule(source);
							allRules.add(rule);
						}
					}
				}
			}
		}
		return allRules;
	}
    
	@Override
	public void insertRule(RuleDef ruleDef) throws Exception {
		if(ruleDef!=null) {	
			int index=ruleDef.getName().indexOf(ruleAppNameSeperator);
			String appName="";
			if(index>0) {
				appName = ruleDef.getName().substring(0,index);
			}
			else {	
				LoggerUtil.log(LOGGER, Level.DEBUG, "Aborting.App name not present in the RuleName : [%s]", ruleDef.getName());
				return;
			}
			
			File fileDir=new File(ruleStoreLocation+File.separator+appName);
			
			if(fileDir!=null && !fileDir.isDirectory()){
				fileDir.mkdirs();
			}
			
			File file=new File(ruleStoreLocation+File.separator+appName+File.separator+ruleDef.getName()+"."+ruleExtension);
			SerializationUtils.serializeRule(ruleDef,file);
		}
	}

	@Override
	public boolean deleteRule(String ruleName) throws Exception {
		if(ruleName!=null&&!ruleName.isEmpty()) {	
			int index=ruleName.indexOf(ruleAppNameSeperator);
			String appName="";
			if(index>0) {
				appName = ruleName.substring(0,index);
			}
			else {	
				LoggerUtil.log(LOGGER, Level.DEBUG, "Aborting.App name not present in the RuleName : [%s]", ruleName);
				return false;
			}
			File file=new File(ruleStoreLocation+File.separator+appName+File.separator+ruleName+"."+ruleExtension);
			file.delete();
			ServiceProviderManager.getInstance().getObjectManager().deleteRuleMetricNodeState(ruleName);
			return true;
		}
		return false;
	}

	@Override
	public void updateRule(RuleDef ruleDef) throws Exception {
		if(ruleDef!=null) {	
			int index=ruleDef.getName().indexOf(ruleAppNameSeperator);
			String appName="";
			if(index>0) {
				appName = ruleDef.getName().substring(0,index);
			}
			else {	
				LoggerUtil.log(LOGGER, Level.DEBUG, "Aborting.App name not present in the RuleName : [%s]", ruleDef.getName());
				return;
			}
			File file=new File(ruleStoreLocation+File.separator+appName+File.separator+ruleDef.getName()+"."+ruleExtension);
			SerializationUtils.serializeRule(ruleDef,file);
		}
	}

	@Override
	public boolean isSortingProvided() {
		return true;
	}

	@Override
	public void printAllNodes() {
		// TODO later
	}

}
