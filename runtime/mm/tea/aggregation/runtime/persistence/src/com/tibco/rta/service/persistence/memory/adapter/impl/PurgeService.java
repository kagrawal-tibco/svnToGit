package com.tibco.rta.service.persistence.memory.adapter.impl;

import com.tibco.rta.model.RetentionPolicy.Qualifier;
import com.tibco.rta.service.persistence.memory.InMemoryConstant;
import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.persistence.service.invm.DataServiceFactory;
import com.tibco.store.persistence.service.invm.InMemoryDataStoreService;
import com.tibco.store.query.model.QueryResultSet;

public class PurgeService {

	private static InMemoryDataStoreService dataService = DataServiceFactory.getInstance().getDataStoreService();

	public static long purgeMetricsForQualifier(String schemaName, Qualifier qualifier, long purgeOlderThan, boolean storeFacts) {
		long purgeCount = 0;
		if (qualifier.equals(Qualifier.FACT)) {
			if (storeFacts) {
				purgeCount = purgeFacts(schemaName, purgeOlderThan);
			}
		}
		return purgeCount;
	}

	private static int purgeFacts(String schemaName, long purgeOlderThan) {
		String tableName = TableGenerationUtility.getUniqueIdentifier(InMemoryConstant.FACT_TABLE_PREFIX, InMemoryConstant.SEP, schemaName);

		QueryResultSet tupleIterator = QueryBuilder.purgeFacts(tableName, purgeOlderThan);
		purge(tupleIterator, tableName);
		return tupleIterator.totalCount();
	}

	public static long purgeMetricsForHierarchy(String schemaName, String cubeName, String hierarchyName, long purgeOlderThan) {
		String tableName = TableGenerationUtility.getMemoryTableName(InMemoryConstant.METRIC_TABLE_PREFIX, schemaName, cubeName, hierarchyName);

		QueryResultSet tupleIterator = QueryBuilder.purgeMetricsForHierarchy(tableName, purgeOlderThan);
		purge(tupleIterator, tableName);
		return tupleIterator.totalCount();
	}

	private static void printAllTableValues(String tableName) {
		System.out.println("Total Table count:" + DataServiceFactory.getInstance().getDataStoreService().getSizeOfTable(tableName));
		int count = 1;
		for (MemoryTuple tuple : DataServiceFactory.getInstance().getDataStoreService().getAllTuples(tableName)) {
			System.out.println("\tCount:" + count + "\tKey: " + tuple.getMemoryKey() + "\t" + InMemoryConstant.UPDATED_DATE_TIME_FIELD + "\t"
					+ tuple.getAttributeValue(InMemoryConstant.UPDATED_DATE_TIME_FIELD));
			count = count + 1;
		}
	}

	private static void purge(QueryResultSet tupleIterator, String tableName) {
		while (tupleIterator.hasNext()) {
			dataService.remove(tableName, tupleIterator.next());
		}
	}

}
