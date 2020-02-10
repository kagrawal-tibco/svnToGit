package com.tibco.store.persistence.model.invm.impl;

import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.service.persistence.memory.InMemoryConstant;
import com.tibco.store.persistence.descriptor.ColumnDescriptor;
import com.tibco.store.persistence.descriptor.TableDescriptor;
import com.tibco.store.persistence.model.MemoryIndex;
import com.tibco.store.persistence.model.MemoryKey;
import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.persistence.model.invm.InMemorySearchResult;
import com.tibco.store.persistence.util.PersistenceConstants;
import com.tibco.store.query.model.BinaryOperator;
import com.tibco.store.query.model.BinaryPredicate;
import com.tibco.store.query.model.Predicate;
import com.tibco.store.query.model.QueryExpression;
import com.tibco.store.query.model.UnaryPredicate;
import com.tibco.store.query.model.impl.ValueExpression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

//TODO More refactoring required.
//TODO later expose operator for range i.e between
public class InMemoryTableImpl extends AbstractInMemoryTable<TableDescriptor> {

	// TODO later separate metricStore and indexStore from inMemoryTable
	private Map<MemoryKey, MemoryTuple> metricStore = new ConcurrentHashMap<MemoryKey, MemoryTuple>();

	private Map<String, MemoryIndex<?>> indexStore = new ConcurrentHashMap<String, MemoryIndex<?>>();

	public InMemoryTableImpl(TableDescriptor tableDescriptor) {
		super(tableDescriptor);
	}

	@Override
	public void put(MemoryTuple memoryTuple) {
		if (memoryTuple != null && memoryTuple.getMemoryKey() != null) {
			// TODO do locking stuff
			putInIndexStore(memoryTuple);
			putInMetricStore(memoryTuple);
			publishTupleAdded(memoryTuple);
		}
	}

	private void putInMetricStore(MemoryTuple memoryTuple) {
		metricStore.put(memoryTuple.getMemoryKey(), memoryTuple);
	}

	@SuppressWarnings("unchecked")
	private void putInIndexStore(MemoryTuple memoryTuple) {
		if (indexes != null) {
			for (String index : indexes) {
				MemoryIndex tMap = indexStore.get(index);
				if (tMap != null) {
					MemoryKey mKey = memoryTuple.getMemoryKey();
					MemoryTuple previousTuple = metricStore.get(mKey);
					if (previousTuple != null) {
						if(index.equalsIgnoreCase(InMemoryConstant.CREATED_DATE_TIME_FIELD)){
							Object ct = previousTuple.getAttributeValue(InMemoryConstant.CREATED_DATE_TIME_FIELD);
							memoryTuple.setAttribute(InMemoryConstant.CREATED_DATE_TIME_FIELD, ct);
						}
						removeReferencesFromIndex(mKey, previousTuple, tMap, index);
					}
					tMap.putValueToKey(memoryTuple.getAttributeValue(index), mKey);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void removeReferencesFromIndex(MemoryKey mKey, MemoryTuple previousTuple, MemoryIndex tMap, String index) {
		tMap.removeValueForKey(previousTuple.getAttributeValue(index), mKey);
	}

	@Override
	public void remove(MemoryTuple memoryTuple) {
		if (memoryTuple != null) {
			// TODO locking stuff
			removeFromMetricStore(memoryTuple);
			removeFromIndexStore(memoryTuple);
			publishTupleRemoved(memoryTuple);
		}
	}

	@SuppressWarnings("unchecked")
	private void removeFromIndexStore(MemoryTuple memoryTuple) {

		for (String index : indexes) {
			MemoryKey mKey = memoryTuple.getMemoryKey();
			MemoryIndex tMap = indexStore.get(index);
			if (tMap != null) {
				tMap.removeValueForKey(memoryTuple.getAttributeValue(index), mKey);
			}
		}
	}

	private void removeFromMetricStore(MemoryTuple memoryTuple) {
		metricStore.remove(memoryTuple.getMemoryKey());
	}

	@Override
	public MemoryTuple get(MemoryKey key) {
		return metricStore.get(key);
	}

	@Override
	public Collection<MemoryTuple> getAllTuples() {
		return metricStore.values();
	}

	@Override
	public int getTuplesCount() {
		return metricStore.size();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void createIndex(ColumnDescriptor columnDescriptor) {
		indexStore.put(columnDescriptor.getName(), new MemoryIndexImpl(columnDescriptor.getDataType().getDataClass()));
		indexes.add(columnDescriptor.getName());
	}

	/**
	 * Given a collection of sets for every indexed key lookup and populate a
	 * tuple collection.
	 * 
	 */
	private void lookupMainStore(Collection<HashSet<MemoryKey>> sets, Collection<MemoryTuple> memoryTuples) {
		if (sets != null) {
			for (HashSet<MemoryKey> set : sets) {
				for (MemoryKey key : set) {
					MemoryTuple memoryTuple = metricStore.get(key);
					if (memoryTuple != null) {
						memoryTuples.add(memoryTuple);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Collection<MemoryTuple> getGtResult(String columnName, Object columnValue) {
		Collection<MemoryTuple> memoryTuples = new ArrayList<MemoryTuple>();

		MemoryIndex tMap = indexStore.get(columnName);
		if (tMap != null) {
			Collection<HashSet<MemoryKey>> sets = tMap.greaterThan(columnValue);
			lookupMainStore(sets, memoryTuples);
		} else { // given column name is not indexed do linear search
			for (Entry<MemoryKey, MemoryTuple> entry : metricStore.entrySet()) {
				Object valueTobeCompared = entry.getValue().getAttributeValue(columnName);
				if (valueTobeCompared != null && CompareFactory.compareGT(valueTobeCompared, columnValue)) {
					memoryTuples.add(entry.getValue());
				}
			}
		}
		return memoryTuples;
	}

	@SuppressWarnings("unchecked")
	private Collection<MemoryTuple> getGEResult(String columnName, Object columnValue) {
		Collection<MemoryTuple> memoryTuples = new ArrayList<MemoryTuple>();

		MemoryIndex tMap = indexStore.get(columnName);
		if (tMap != null) {
			Collection<HashSet<MemoryKey>> sets = tMap.greaterThanEqualTo(columnValue);
			lookupMainStore(sets, memoryTuples);
		} else { // given column name is not indexed do linear search
			for (Entry<MemoryKey, MemoryTuple> entry : metricStore.entrySet()) {
				Object valueTobeCompared = entry.getValue().getAttributeValue(columnName);
				if (valueTobeCompared != null && CompareFactory.compareGE(valueTobeCompared, columnValue)) {
					memoryTuples.add(entry.getValue());
				}
			}
		}
		return memoryTuples;
	}

	@SuppressWarnings("unchecked")
	private Collection<MemoryTuple> getLtResult(String columnName, Object columnValue) {
		Collection<MemoryTuple> memoryTuples = new ArrayList<MemoryTuple>();

		MemoryIndex tMap = indexStore.get(columnName);
		if (tMap != null) {
			Collection<HashSet<MemoryKey>> sets = tMap.lessThan(columnValue);
			lookupMainStore(sets, memoryTuples);
		} else { // given column name is not indexed do linear search
			for (Entry<MemoryKey, MemoryTuple> entry : metricStore.entrySet()) {
				Object valueTobeCompared = entry.getValue().getAttributeValue(columnName);
				if (valueTobeCompared != null && CompareFactory.compareLT(valueTobeCompared, columnValue)) {
					memoryTuples.add(entry.getValue());
				}
			}
		}
		return memoryTuples;
	}

	@SuppressWarnings("unchecked")
	private Collection<MemoryTuple> getLEResult(String columnName, Object columnValue) {
		Collection<MemoryTuple> memoryTuples = new ArrayList<MemoryTuple>();

		MemoryIndex tMap = indexStore.get(columnName);
		if (tMap != null) {
			Collection<HashSet<MemoryKey>> sets = tMap.lessThanEqualTo(columnValue);
			lookupMainStore(sets, memoryTuples);
		} else { // given column name is not indexed do linear search
			for (Entry<MemoryKey, MemoryTuple> entry : metricStore.entrySet()) {
				Object valueTobeCompared = entry.getValue().getAttributeValue(columnName);
				if (valueTobeCompared != null && CompareFactory.compareLE(valueTobeCompared, columnValue)) {
					memoryTuples.add(entry.getValue());
				}
			}
		}
		return memoryTuples;
	}

	@SuppressWarnings("unchecked")
	private Collection<MemoryTuple> getEqResult(String columnName, Object columnValue) {
		if (PersistenceConstants.MATCH_ALL.equals(columnValue)) {
			// Return all results matching *
			return getAllResults(columnName);
		}
		Collection<MemoryTuple> mList = new ArrayList<MemoryTuple>();

		MemoryIndex tMap = indexStore.get(columnName);
		if (tMap != null) {
			Collection<MemoryKey> set = tMap.get(columnValue);
			if (set != null) {
				for (MemoryKey mKey : set) {
					MemoryTuple mNode = metricStore.get(mKey);
					if (mNode != null) {
						mList.add(mNode);
					}
				}
			}
		} else { // given column name is not indexed do linear search
			for (Entry<MemoryKey, MemoryTuple> entry : metricStore.entrySet()) {
				Object valueTobeCompared = entry.getValue().getAttributeValue(columnName);
				if (valueTobeCompared != null && CompareFactory.compareEQ(valueTobeCompared, columnValue)) {
					mList.add(entry.getValue());
				}
			}
		}
		return mList;
	}

	@SuppressWarnings("unchecked")
	private Collection<MemoryTuple> getAllResults(String columnName) {
		Collection<MemoryTuple> results = new ArrayList<MemoryTuple>();

		MemoryIndex tMap = indexStore.get(columnName);
		if (tMap != null) {
			Collection<MemoryKey> set = tMap.getValues();
			if (set != null) {
				for (MemoryKey mKey : set) {
					MemoryTuple memoryTuple = metricStore.get(mKey);
					if (memoryTuple != null) {
						results.add(memoryTuple);
					}
				}
			}
		} else { // given column name is not indexed do linear search
			for (Entry<MemoryKey, MemoryTuple> entry : metricStore.entrySet()) {
				results.add(entry.getValue());
			}
		}
		return results;
	}

	private InMemorySearchResult lookupBinaryFilter(BinaryPredicate<?> binaryPredicate) {
		Collection<MemoryTuple> memoryTuples = Collections.emptyList();

		String leftOperand = binaryPredicate.getLeftExpression().getOperand();
		QueryExpression rightExpression = binaryPredicate.getRightExpression();
		if (rightExpression instanceof ValueExpression) {
			Object rightOperand = ((ValueExpression) rightExpression).getValue();

			if (binaryPredicate.getBinaryOperator().equals(BinaryOperator.EQ)) {
				memoryTuples = getEqResult(leftOperand, rightOperand);
			} else if (binaryPredicate.getBinaryOperator() == BinaryOperator.NEQ) {
				memoryTuples = getNOTEQResult(leftOperand, rightOperand);
			} else if (binaryPredicate.getBinaryOperator() == BinaryOperator.GT) {
				memoryTuples = getGtResult(leftOperand, rightOperand);
			} else if (binaryPredicate.getBinaryOperator() == BinaryOperator.GE) {
				memoryTuples = getGEResult(leftOperand, rightOperand);
			} else if (binaryPredicate.getBinaryOperator() == BinaryOperator.LT) {
				memoryTuples = getLtResult(leftOperand, rightOperand);
			} else if (binaryPredicate.getBinaryOperator() == BinaryOperator.LE) {
				memoryTuples = getLEResult(leftOperand, rightOperand);
			} else if (binaryPredicate.getBinaryOperator() == BinaryOperator.NOTEQ) {
				memoryTuples = getNOTEQResult(leftOperand, rightOperand);
			}
		}

		return new InMemorySearchResultImpl(memoryTuples);
	}

	@SuppressWarnings("unchecked")
	private Collection<MemoryTuple> getNOTEQResult(String columnName, Object columnValue) {
		List<MemoryTuple> mList = new ArrayList<MemoryTuple>();

		MemoryIndex tMap = indexStore.get(columnName);
		if (tMap != null) {
			Collection<MemoryKey> set = tMap.getNot(columnValue);
			if (set != null) {
				for (MemoryKey mKey : set) {
					MemoryTuple mNode = metricStore.get(mKey);
					if (mNode != null) {
						mList.add(mNode);
					}
				}
			}
		} else { // given column name is not indexed do linear search
			for (Entry<MemoryKey, MemoryTuple> entry : metricStore.entrySet()) {
				if (entry.getValue().getAttributeValue(columnName) != columnValue) {
					mList.add(entry.getValue());
				}
			}
		}
		return mList;
	}

	private InMemorySearchResult lookupUnaryFilter(UnaryPredicate unaryPredicate) {
		List<MemoryTuple> mList = null;

		return null;
	}

	@Override
	public boolean isIndexed(String indexName) {
		// TODO case sensitive or not decide later
		for (String index : indexes) {
			if (index.equals(indexName)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public <R extends Predicate> InMemorySearchResult lookup(R predicate) {
		if (predicate instanceof BinaryPredicate) {
			return lookupBinaryFilter((BinaryPredicate<?>) predicate);
		} else if (predicate instanceof UnaryPredicate) {
			return lookupUnaryFilter((UnaryPredicate) predicate);
		}
		return null;
	}

	@Override
	public int getCardinality(String indexName) {
		MemoryIndex index = indexStore.get(indexName);
		if (index != null) {
			return index.getTotalKeys();
		}
		return 0;
	}

	@Override
	public void remove(MemoryKey key) {
		// TODO locking
		removeFromMetricStore(key);
		removeFromIndexStore(key);
		MemoryTuple memTuple = metricStore.get(key);
		publishTupleRemoved(memTuple);
	}

	private void removeFromIndexStore(MemoryKey key) {
		for (String index : indexes) {
			MemoryIndex<?> tMap = indexStore.get(index);
			if (tMap != null) {
				tMap.removeValueReference(key);
			}
		}
	}

	private void removeFromMetricStore(MemoryKey key) {
		metricStore.remove(key);
	}

	@Override
	public void clear() {
		super.clear();
		metricStore.clear();
		indexStore.clear();
	}

	@Override
	public int getTupleCount(Predicate predicate) {
		return getBinaryTupleCount((BinaryPredicate<?>) predicate);
	}

	private int getBinaryTupleCount(BinaryPredicate<?> binaryPredicate) {
		String leftOperand = binaryPredicate.getLeftExpression().getOperand();
		QueryExpression rightExpression = binaryPredicate.getRightExpression();
		if (rightExpression instanceof ValueExpression) {
			Object rightOperand = ((ValueExpression) rightExpression).getValue();

			if (binaryPredicate.getBinaryOperator() == BinaryOperator.EQ) {
				return getEqResultCount(leftOperand, rightOperand);
			} else if (binaryPredicate.getBinaryOperator() == BinaryOperator.NEQ) {
				return getNOTEQResultCount(leftOperand, rightOperand);
			} else if (binaryPredicate.getBinaryOperator() == BinaryOperator.GT) {
				return getGtResultCount(leftOperand, rightOperand);
			} else if (binaryPredicate.getBinaryOperator() == BinaryOperator.GE) {
				return getGEResultCount(leftOperand, rightOperand);
			} else if (binaryPredicate.getBinaryOperator() == BinaryOperator.LT) {
				return getLtResultCount(leftOperand, rightOperand);
			} else if (binaryPredicate.getBinaryOperator() == BinaryOperator.LE) {
				return getLEResultCount(leftOperand, rightOperand);
			} else if (binaryPredicate.getBinaryOperator() == BinaryOperator.NOTEQ) {
				return getNOTEQResultCount(leftOperand, rightOperand);
			}
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	private int getNOTEQResultCount(String columnName, Object columnValue) {

		MemoryIndex tMap = indexStore.get(columnName);
		if (tMap != null) {
			Collection<MemoryKey> set = tMap.getNot(columnValue);
			if (set != null) {
				return set.size();
			}
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	private int getLEResultCount(String columnName, Object columnValue) {
		MemoryIndex tMap = indexStore.get(columnName);
		if (tMap != null) {
			return tMap.lessThanEqualToCount(columnValue);
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	private int getLtResultCount(String columnName, Object columnValue) {
		MemoryIndex tMap = indexStore.get(columnName);
		if (tMap != null) {
			return tMap.lessThanCount(columnValue);
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	private int getGEResultCount(String columnName, Object columnValue) {
		MemoryIndex tMap = indexStore.get(columnName);
		if (tMap != null) {
			return tMap.greaterThanEqualToCount(columnValue);
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	private int getGtResultCount(String columnName, Object columnValue) {
		MemoryIndex tMap = indexStore.get(columnName);
		if (tMap != null) {
			return tMap.greaterThanCount(columnValue);
		}
		return 0;

	}

	@SuppressWarnings("unchecked")
	private int getEqResultCount(String columnName, Object columnValue) {
		MemoryIndex tMap = indexStore.get(columnName);
		if (tMap != null) {
			return tMap.equalsCount(columnValue);
		}
		return 0;
	}

}
