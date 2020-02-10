package com.tibco.store.persistence.model.invm.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.tibco.store.persistence.descriptor.ColumnDescriptor;
import com.tibco.store.persistence.descriptor.PartitionTableDescriptor;
import com.tibco.store.persistence.descriptor.RangeDescriptor;
import com.tibco.store.persistence.model.MemoryKey;
import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.persistence.model.invm.InMemorySearchResult;
import com.tibco.store.persistence.model.invm.MemoryPartition;
import com.tibco.store.persistence.model.invm.PartitionedTable;
import com.tibco.store.query.model.Predicate;

/**
 * Created by IntelliJ IDEA. User: aathalye Date: 9/1/14 Time: 11:12 AM
 * 
 * Default implementation for in memory
 * {@link com.tibco.store.persistence.model.invm.PartitionedTable}
 */
public class PartitionedMemoryTable<T> extends AbstractInMemoryTable<PartitionTableDescriptor> implements PartitionedTable<T> {

	/**
	 * Partitions will contain all data and the map below denotes range and
	 * mapping partition.
	 */
	private Map<RangeDescriptor<T>, MemoryPartition> tablePartitions = new ConcurrentHashMap<RangeDescriptor<T>, MemoryPartition>();

	private MemoryPartition commonPartition;

	private Map<String, ColumnDescriptor> columnDescs = new HashMap<String, ColumnDescriptor>();

    //TODO remove the hard coding and close the service
    //TODO add thread factory
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

	public PartitionedMemoryTable(PartitionTableDescriptor partitionDescriptor) {
		super(partitionDescriptor);
		commonPartition = new MemoryPartitionImpl(tableDescriptor);
	}

	@Override
	public MemoryPartition createPartition(RangeDescriptor<T> rangeDescriptor) {
		if (rangeDescriptor == null) {
			throw new IllegalArgumentException("Range descriptor for partition cannot be null");
		}
		MemoryPartition newPartition = new MemoryPartitionImpl(tableDescriptor);
		addIndexesToPartition(newPartition);
		return tablePartitions.put(rangeDescriptor, newPartition);
	}

	private void addIndexesToPartition(MemoryPartition newPartition) {
		for (ColumnDescriptor desc : columnDescs.values()) {
			newPartition.createIndex(desc);
		}
	}

	private MemoryPartition getCommonPartition() {
		if (commonPartition == null) {
			commonPartition = new MemoryPartitionImpl(tableDescriptor);
			addIndexesToPartition(commonPartition);
		}
		return commonPartition;
	}

	@Override
	public MemoryPartition removePartition(RangeDescriptor<T> rangeDescriptor) {
		if (rangeDescriptor == null) {
			throw new IllegalArgumentException("Range descriptor for partition cannot be null");
		}
		return tablePartitions.remove(rangeDescriptor);
	}

	@Override
	public void put(MemoryTuple memoryTuple) {
		if (!checkNull(memoryTuple)) {
			// Check if it has partition key
			String partitionKey = tableDescriptor.getPartitionKey();
			if (!memoryTuple.hasAttribute(partitionKey)) {
				String err = String.format("Partition key [%s] not found in tuple", partitionKey);
				throw new IllegalArgumentException(err);

			}
			@SuppressWarnings("unchecked")
			T partitionKeyValue = (T) memoryTuple.getAttributeValue(partitionKey);
			if (partitionKeyValue == null) {
				commonPartition.put(memoryTuple);
				return;
			}
			RangeDescriptor<T> matchingRangeDescriptor = getRangeDescriptor(partitionKeyValue);
			MemoryPartition memoryPartition = tablePartitions.get(matchingRangeDescriptor);
			memoryPartition.put(memoryTuple);
		}
	}

	/**
	 * Get matching range descriptor for a partition key value. If none is found
	 * create one.
	 * 
	 */
	@SuppressWarnings("unchecked")
	private RangeDescriptor<T> getRangeDescriptor(T partitionKeyValue) {
		RangeDescriptor<T> matchingRangeDescriptor = null;

		for (RangeDescriptor<T> rangeDescriptor : tablePartitions.keySet()) {
			if (rangeDescriptor.encloses(partitionKeyValue)) {
				matchingRangeDescriptor = rangeDescriptor;
				break;
			}
		}
		if (matchingRangeDescriptor == null) {
			matchingRangeDescriptor = (RangeDescriptor<T>) tableDescriptor.createRangeDescriptor(partitionKeyValue, partitionKeyValue);
			// Add partition
			createPartition(matchingRangeDescriptor);
		}
		return matchingRangeDescriptor;
	}

	@Override
	public void remove(MemoryTuple memoryTuple) {
		if (memoryTuple == null) {
			throw new IllegalArgumentException("Tuple to remove cannot be null");
		}
		// Check if it has partition key
		String partitionKey = tableDescriptor.getPartitionKey();
		if (!memoryTuple.hasAttribute(partitionKey)) {
			throw new IllegalArgumentException("Partition key not found in tuple");
		}
		@SuppressWarnings("unchecked")
		T partitionKeyValue = (T) memoryTuple.getAttributeValue(partitionKey);
		if (partitionKeyValue == null) {
			commonPartition.remove(memoryTuple);
			return;
		}
		RangeDescriptor<T> matchingRangeDescriptor = getRangeDescriptor(partitionKeyValue);
		MemoryPartition memoryPartition = tablePartitions.get(matchingRangeDescriptor);
		memoryPartition.remove(memoryTuple);
	}

	@Override
	public void remove(MemoryKey key) {
		if (key == null) {
			throw new IllegalArgumentException("key to remove cannot be null");
		}
		if (tablePartitions.size() == 0) {
			return;
		}
		for (MemoryPartition memoryPartition : tablePartitions.values()) {
			executorService.execute(new RemovePartition(memoryPartition, key));
		}
		executorService.execute(new RemovePartition(commonPartition, key));
	}

	@Override
	public void createIndex(ColumnDescriptor columnDescriptor) {
		if (!checkNull(columnDescriptor)) {
			for (MemoryPartition memoryPartition : tablePartitions.values()) {
				memoryPartition.createIndex(columnDescriptor);
			}
			commonPartition.createIndex(columnDescriptor);
			columnDescs.put(columnDescriptor.getName(), columnDescriptor);
			indexes.add(columnDescriptor.getName());
		}
	}

	private boolean checkNull(Object obj) {
		return obj == null;
	}

	@Override
	public MemoryTuple get(MemoryKey key) {
		try {
			if (!checkNull(key)) {
				if (tablePartitions.size() == 0) {
					return null;
				}
				for (MemoryPartition memoryPartition : tablePartitions.values()) {
					Future<MemoryTuple> future = executorService.submit(new GetPartition(memoryPartition, key));

					MemoryTuple tuple = future.get();
					if (tuple != null) {						
						return tuple;
					}
				}

				Future<MemoryTuple> future = executorService.submit(new GetPartition(commonPartition, key));
				MemoryTuple tuple = future.get();
				if (tuple != null) {
					return tuple;
                }
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getCardinality(String indexName) {
		int count = 0;
		for (MemoryPartition memoryPartition : tablePartitions.values()) {
			count += memoryPartition.getCardinality(indexName);
		}
		count += commonPartition.getCardinality(indexName);
		return count;
	}

	@Override
	public int getTupleCount(Predicate predicate) {
		if (tablePartitions.size() == 0) {
			return 0;
		}
		int count = 0;
		try {
			for (MemoryPartition memoryPartition : tablePartitions.values()) {
				Future<Integer> future = executorService.submit(new PartitionLookUpCount(memoryPartition, predicate));
				count += future.get();
			}
			Future<Integer> future = executorService.submit(new PartitionLookUpCount(commonPartition, predicate));
			count += future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Collection<MemoryTuple> getAllTuples() {
		ArrayList<MemoryTuple> searchResult = new ArrayList<MemoryTuple>();

		for (MemoryPartition memoryPartition : tablePartitions.values()) {
			searchResult.addAll(memoryPartition.getAllTuples());
		}
		searchResult.addAll(commonPartition.getAllTuples());
		return searchResult;
	}

	@Override
	public int getTuplesCount() {
		int count = 0;
		for (MemoryPartition memoryPartition : tablePartitions.values()) {
			count += memoryPartition.getTuplesCount();
		}
		count += commonPartition.getTuplesCount();
		return count;
	}

	@Override
	public void clear() {
		super.clear();
		for (MemoryPartition memoryPartition : tablePartitions.values()) {
			memoryPartition.clear();
		}
		commonPartition.clear();
	}

	@Override
	public <R extends Predicate> InMemorySearchResult lookup(R predicate) {
		// TODO revisit this.
		InMemorySearchResult searchResult = new InMemorySearchResultImpl(new ArrayList<MemoryTuple>());
		try {
			if (tablePartitions.size() == 0) {
				return searchResult;
			}
			for (MemoryPartition memoryPartition : tablePartitions.values()) {
				Future<InMemorySearchResult> future = executorService.submit(new PartitionLookUp<R>(memoryPartition, predicate));
				// TODO we are exposing internals. Can we avoid this?
				searchResult.getTuples().addAll(future.get().getTuples());
			}
			Future<InMemorySearchResult> future = executorService.submit(new PartitionLookUp<R>(commonPartition, predicate));
			searchResult.getTuples().addAll(future.get().getTuples());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return searchResult;
	}

	private class PartitionLookUp<P extends Predicate> implements Callable<InMemorySearchResult> {

		private MemoryPartition memoryPartition;

		private P predicate;

		PartitionLookUp(MemoryPartition memoryPartition, P predicate) {
			this.memoryPartition = memoryPartition;
			this.predicate = predicate;
		}

		@Override
		public InMemorySearchResult call() {
			return memoryPartition.lookup(predicate);
		}
	}

	private class PartitionLookUpCount implements Callable<Integer> {

		private MemoryPartition memoryPartition;

		private Predicate predicate;

		PartitionLookUpCount(MemoryPartition memoryPartition, Predicate predicate) {
			this.memoryPartition = memoryPartition;
			this.predicate = predicate;
		}

		@Override
		public Integer call() {
			return memoryPartition.getTupleCount(predicate);
		}
	}

	private class RemovePartition implements Runnable {

		private MemoryPartition memoryPartition;

		private MemoryKey key;

		RemovePartition(MemoryPartition memoryPartition, MemoryKey key) {
			this.memoryPartition = memoryPartition;
			this.key = key;
		}

		@Override
		public void run() {
			memoryPartition.remove(key);
		}
	}

	private class GetPartition implements Callable<MemoryTuple> {

		private MemoryPartition memoryPartition;

		private MemoryKey key;

		GetPartition(MemoryPartition memoryPartition, MemoryKey key) {
			this.memoryPartition = memoryPartition;
			this.key = key;
		}

		@Override
		public MemoryTuple call() {
			return memoryPartition.get(key);
		}
	}
}
