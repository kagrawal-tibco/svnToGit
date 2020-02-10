package com.tibco.rta.service.persistence.memory.adapter.impl;

import static com.tibco.rta.service.persistence.memory.InMemoryConstant.CREATED_DATE_TIME_FIELD;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.DIMENSION_LEVEL_FIELD;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.FACT_TABLE_PREFIX;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.METRIC_KEY_FIELD;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.METRIC_TABLE_PREFIX;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.PARTITION_ON_ATTRIBUTE;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.RULE_ACTION_NAME_FIELD;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.RULE_CLEAR_CONDITION_KEY_FILED;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.RULE_LAST_FIRED_TIME_FIELD;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.RULE_NAME_FIELD;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.RULE_SCHEDULED_TIME_FIELD;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.RULE_SET_CONDITION_KEY_FILED;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.RULE_SET_COUNT_FIELD;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.RULE_STATE_TABLE_PREFIX;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.SEP;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.UPDATED_DATE_TIME_FIELD;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.IS_PROCESSED;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.rta.model.Cube;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.store.persistence.descriptor.ColumnDescriptor;
import com.tibco.store.persistence.descriptor.impl.ColumnDescriptorImpl;
import com.tibco.store.persistence.descriptor.impl.DescriptorFactory;
import com.tibco.store.persistence.exceptions.DuplicateColumnException;
import com.tibco.store.persistence.exceptions.DuplicateTableException;
import com.tibco.store.persistence.exceptions.NoSuchTableException;
import com.tibco.store.persistence.service.invm.DataServiceFactory;
import com.tibco.store.persistence.service.invm.InMemoryMetadataService;
import com.tibco.store.query.model.DataType;

public class PartitionedTableGenerationUtility {

	private static InMemoryMetadataService metaService = DataServiceFactory.getInstance().getMetaDataService();

	public static void createTables(RtaSchema schema) throws DuplicateTableException, DuplicateColumnException, NoSuchTableException {
		// Fact Table
		String tableName = getUniqueIdentifier(FACT_TABLE_PREFIX, SEP, schema.getName());

		Collection<ColumnDescriptor> factColumns = new ArrayList<ColumnDescriptor>();
		factColumns.add(DescriptorFactory.createColumnDescriptor(CREATED_DATE_TIME_FIELD, DataType.TIMESTAMP, true));
		factColumns.add(DescriptorFactory.createColumnDescriptor(UPDATED_DATE_TIME_FIELD, DataType.TIMESTAMP, true));
		//TODO fact partitioning
		metaService.createTable(DescriptorFactory.createMemoryTableDescriptor(tableName));
		metaService.createIndexes(tableName, factColumns);

		List<ColumnDescriptor> indexes = new ArrayList<ColumnDescriptor>();
		List<ColumnDescriptor> ruleStateIndexes = new ArrayList<ColumnDescriptor>();

		for (Cube cube : schema.getCubes()) {
			for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {

				// create metric memory tables
				tableName = getMemoryTableName(METRIC_TABLE_PREFIX, schema.getName(), cube.getName(), dh.getName());

				indexes.clear();
				ruleStateIndexes.clear();
				for (Dimension dim : dh.getDimensions()) {
					indexes.add(new ColumnDescriptorImpl(dim.getName(), DataType.valueOf(dim.getAssociatedAttribute().getDataType().name().toUpperCase()), true));
				}

				indexes.add(DescriptorFactory.createColumnDescriptor(DIMENSION_LEVEL_FIELD, DataType.STRING, true));
				indexes.add(DescriptorFactory.createColumnDescriptor(CREATED_DATE_TIME_FIELD, DataType.TIMESTAMP, true));
				indexes.add(DescriptorFactory.createColumnDescriptor(UPDATED_DATE_TIME_FIELD, DataType.TIMESTAMP, true));
				indexes.add(DescriptorFactory.createColumnDescriptor(IS_PROCESSED, DataType.BOOLEAN, true));
				metaService.createTable(DescriptorFactory.createPartitionTableDescriptor(tableName, dh.getProperty(PARTITION_ON_ATTRIBUTE)));
				metaService.createIndexes(tableName, indexes);

				// Create rule state tables

				ruleStateIndexes.add(DescriptorFactory.createColumnDescriptor(METRIC_KEY_FIELD, DataType.STRING, true));
				ruleStateIndexes.add(DescriptorFactory.createColumnDescriptor(RULE_NAME_FIELD, DataType.STRING, true));
				ruleStateIndexes.add(DescriptorFactory.createColumnDescriptor(RULE_ACTION_NAME_FIELD, DataType.STRING, true));
				ruleStateIndexes.add(DescriptorFactory.createColumnDescriptor(RULE_SET_COUNT_FIELD, DataType.INTEGER, true));
				ruleStateIndexes.add(DescriptorFactory.createColumnDescriptor(RULE_SCHEDULED_TIME_FIELD, DataType.LONG, true));
				ruleStateIndexes.add(DescriptorFactory.createColumnDescriptor(RULE_LAST_FIRED_TIME_FIELD, DataType.LONG, true));
				ruleStateIndexes.add(DescriptorFactory.createColumnDescriptor(DIMENSION_LEVEL_FIELD, DataType.STRING, true));
				ruleStateIndexes.add(DescriptorFactory.createColumnDescriptor(RULE_SET_CONDITION_KEY_FILED, DataType.STRING, true));
				ruleStateIndexes.add(DescriptorFactory.createColumnDescriptor(RULE_CLEAR_CONDITION_KEY_FILED, DataType.STRING, true));

				tableName = getMemoryTableName(RULE_STATE_TABLE_PREFIX, schema.getName(), cube.getName(), dh.getName());
				ruleStateIndexes.addAll(indexes);
				metaService.createTable(DescriptorFactory.createPartitionTableDescriptor(tableName, dh.getProperty(PARTITION_ON_ATTRIBUTE)));
				metaService.createIndexes(tableName, ruleStateIndexes);
			}
		}

	}

	public static String getUniqueIdentifier(String... strings) {
		StringBuilder identifier = new StringBuilder();
		for (String str : strings) {
			identifier.append(str);
		}
		return identifier.toString();
	}

	public static String getMemoryTableName(String prefix, String schemaName, String cubeName, String hierarchyName) {
		return getUniqueIdentifier(prefix, SEP, schemaName, SEP, cubeName, SEP, hierarchyName);
	}

}
