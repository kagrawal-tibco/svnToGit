package com.tibco.cep.dashboard.plugin.beviews.data;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleComparator;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.psvr.common.query.Query;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.Threshold;
import com.tibco.cep.dashboard.psvr.data.Updateable;
import com.tibco.cep.dashboard.psvr.data.Updateable.UpdateType;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author apatil
 *
 */
abstract class DataSet extends Observable {

	public static enum DATA_STATES {
		datatruncated, datarefreshed
	};

	protected Logger logger;
	protected ExceptionHandler exceptionHandler;
	protected MessageGenerator messageGenerator;

	protected String name;
	protected MALSourceElement sourceElement;

	private MALFieldMetaInfo[] matchingGroupByFields;

	private MALFieldMetaInfo[] resettableFields;

	protected Threshold threshold;
	protected int maxFetchCount;

	protected TupleComparator tupleComparator;

	private LinkedHashMap<String, TupleHolder> indexedTupleHolderMap;

	private List<Tuple> data;

	protected DataSet(Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		this.logger = logger;
		this.exceptionHandler = exceptionHandler;
		this.messageGenerator = messageGenerator;
		indexedTupleHolderMap = new LinkedHashMap<String, TupleHolder>();
	}

	protected void init(PresentationContext ctx) throws DataException, MALException, ElementNotFoundException {
		List<MALFieldMetaInfo> matchingGroupByFieldsList = new LinkedList<MALFieldMetaInfo>();
		List<MALFieldMetaInfo> resettableFieldsList = new LinkedList<MALFieldMetaInfo>();

		MALFieldMetaInfo[] fields = sourceElement.getFields();
		for (int i = 0; i < fields.length; i++) {
			MALFieldMetaInfo field = fields[i];
			if (field.isGroupBy() == true) {
				// we have a group-by field, we check if it time based
				if (field.isDate() == false) {
					// no the field is not time based, we add it to matching
					// fields list
					matchingGroupByFieldsList.add(field);
					if (logger.isEnabledFor(Level.DEBUG) == true) {
						logger.log(Level.DEBUG, "Adding [" + field.getName() + "] as a matching groupby field in " + name);
					}
				} else {
					// INFO do we add a date based group-by field to the resettable list ?
					if (logger.isEnabledFor(Level.DEBUG) == true) {
						logger.log(Level.DEBUG, "Skipping [" + field.getName() + "] as a matching groupby field as it is of type date in " + name);
					}
				}
			} else {
				// we reset all non group by fields
				resettableFieldsList.add(field);
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, "Adding [" + field.getName() + "] as a resettable field in " + name);
				}
			}
		}

		matchingGroupByFields = matchingGroupByFieldsList.toArray(new MALFieldMetaInfo[matchingGroupByFieldsList.size()]);
		resettableFields = resettableFieldsList.toArray(new MALFieldMetaInfo[resettableFieldsList.size()]);

		if (logger.isEnabledFor(Level.DEBUG) == true) {
			StringBuilder buffer = new StringBuilder("Going to use [");
			for (int i = 0; i < matchingGroupByFields.length; i++) {
				MALFieldMetaInfo field = matchingGroupByFields[i];
				buffer.append(field.getName());
				if (i + 1 < matchingGroupByFields.length) {
					buffer.append(",");
				}
			}
			buffer.append("] as matching groupby fields for " + sourceElement.getName() + " in " + name);
			logger.log(Level.DEBUG, buffer.toString());
			buffer.setLength(0);
			buffer.append("Going to use [");
			for (int i = 0; i < resettableFields.length; i++) {
				MALFieldMetaInfo field = resettableFields[i];
				buffer.append(field.getName());
				if (i + 1 < resettableFields.length) {
					buffer.append(",");
				}
			}
			buffer.append("] as resettable fields for " + sourceElement.getName() + " in " + name);
			logger.log(Level.DEBUG, buffer.toString());
		}
	}

	protected abstract List<Tuple> readDataFromServer(Query query, PresentationContext ctx) throws DataException;

	void setData(List<Tuple> tuples) {
		indexedTupleHolderMap.clear();
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Received " + tuples.size() + " tuple(s) as initial set in " + name);
		}
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Received " + tuples + " tuple(s) as initial set in " + name);
		}
		this.data = new LinkedList<Tuple>(tuples);
		if (tupleComparator != null) {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Sorting " + data.size() + " tuple(s) in " + name);
			}
			Collections.sort(data, tupleComparator);
		}
		this.data = applyThreshold(data);

		Iterator<Tuple> dataIter = this.data.iterator();
		while (dataIter.hasNext()) {
			Tuple tuple = dataIter.next();
			TupleHolder tupleHolder = new TupleHolder(logger, name, tuple);
			indexedTupleHolderMap.put(tupleHolder.id, tupleHolder);
		}
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Processed dataset is  " + data + " for " + name);
		}
	}

	void updateData(Updateable.UpdateType updateType, List<Tuple> tuples) {
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Received ["+tuples.size()+"]" + tuples + " tuple(s) with update type as "+updateType+" in " + name);
		}
		// 02/19/2007 - We dont need to optimize the reindexing calling, since
		// in the code we skipped
		// over the issue that sometimes reindexing will be needed even if there
		// is not update
		// look @ bug # 7066 - Anand
		// boolean reIndexingNeeded = false;
		if (updateType == UpdateType.CREATE || updateType == UpdateType.UPDATE) {
			Iterator<Tuple> tuplesIterator = tuples.iterator();
			while (tuplesIterator.hasNext()) {
				Tuple tuple = tuplesIterator.next();
				TupleHolder tupleHolder = indexedTupleHolderMap.get(String.valueOf(tuple.getId()));
				if (tupleHolder != null) {
					if (logger.isEnabledFor(Level.DEBUG) == true) {
						logger.log(Level.DEBUG, "Updating " + tupleHolder + " in " + name);
					}
					tupleHolder.updateTuple(tuple);
					if (logger.isEnabledFor(Level.DEBUG) == true) {
						logger.log(Level.DEBUG, "Updated " + tupleHolder + " in " + name);
					}
				} else {
					boolean tupleProcessed = false;
					Iterator<TupleHolder> tupleHoldersIter = indexedTupleHolderMap.values().iterator();
					while (tupleHoldersIter.hasNext()) {
						tupleHolder = tupleHoldersIter.next();
						if (tupleHolder.matchesTuple(tuple, matchingGroupByFields) == true) {
							if (logger.isEnabledFor(Level.DEBUG) == true) {
								logger.log(Level.DEBUG, "Found " + tupleHolder + " as a match for " + tuple + " in " + name);
							}
							if (tupleHolder.reset == true) {
								tupleHolder.updateTuple(tuple);
								tupleProcessed = true;
								if (logger.isEnabledFor(Level.DEBUG) == true) {
									logger.log(Level.DEBUG, "Updated " + tupleHolder + " in " + name);
								}
								break;
							}
						}
					}
					if (tupleProcessed == false) {
						tupleHolder = new TupleHolder(logger, name, tuple);
						if (logger.isEnabledFor(Level.DEBUG) == true) {
							logger.log(Level.DEBUG, "Adding " + tuple + " to " + name);
						}
						indexedTupleHolderMap.put(tupleHolder.id, tupleHolder);
					}
					// 02/19/2007 - Anand Fix for bug # 7066
					// reIndexingNeeded = true;
				}
			}
		}
		else if (updateType == UpdateType.DELETE || updateType == UpdateType.UNUSABLE) {
			Iterator<Tuple> tuplesIterator = tuples.iterator();
			while (tuplesIterator.hasNext()) {
				Tuple tuple = tuplesIterator.next();
				indexedTupleHolderMap.remove(String.valueOf(tuple.getId()));
			}
		}
		// 02/19/2007 - Anand Fix for bug # 7066 - START
		// if (reIndexingNeeded == true){
		reIndexMap();
		// }
		// 02/19/2007 - Anand Fix for bug # 7066 - END
		if (tuples.size() > 0) {
			reBuildData();
		}
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Processed dataset is  " + data + " for " + name);
		}
	}

	void resetData(List<Tuple> tuples) {
		if (tuples == null || tuples.size() == 0) {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Resetting " + indexedTupleHolderMap.size() + " tupleholder(s) in " + name);
			}
			Iterator<TupleHolder> tupleHoldersIter = indexedTupleHolderMap.values().iterator();
			while (tupleHoldersIter.hasNext()) {
				TupleHolder tupleHolder = tupleHoldersIter.next();
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, "Resetting " + tupleHolder + " in " + name);
				}
				tupleHolder.resetTuple(resettableFields);
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, "Resetted " + tupleHolder + " in " + name);
				}
			}
		} else {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Resetting " + indexedTupleHolderMap.size() + " tupleholders(s) using " + tuples.size() + " tuple(s) in " + name);
			}
			List<TupleHolder> processedTupleHolders = new LinkedList<TupleHolder>();
			Iterator<Tuple> tuplesIterator = tuples.iterator();
			while (tuplesIterator.hasNext()) {
				Tuple tuple = tuplesIterator.next();
				boolean tupleAccepted = false;
				Iterator<TupleHolder> tupleHoldersIter = indexedTupleHolderMap.values().iterator();
				TupleHolder matchingTupleHolder = null;
				while (tupleHoldersIter.hasNext()) {
					TupleHolder tupleHolder = tupleHoldersIter.next();
					if (tupleHolder.matchesTuple(tuple, matchingGroupByFields) == true) {
						if (logger.isEnabledFor(Level.DEBUG) == true) {
							logger.log(Level.DEBUG, "Found " + tupleHolder + " as a match for " + tuple + " in " + name);
						}
						matchingTupleHolder = tupleHolder;
						if (tupleHolder.reset == false) {
							tupleHolder.resetTuple(tuple);
							tupleAccepted = true;
							processedTupleHolders.add(tupleHolder);
							if (logger.isEnabledFor(Level.DEBUG) == true) {
								logger.log(Level.DEBUG, "Resetted " + tupleHolder + " in " + name);
							}
							break;
						}
					}
				}
				if (tupleAccepted == false && matchingTupleHolder == null) {
					TupleHolder tupleHolder = new TupleHolder(logger, name, tuple);
					if (logger.isEnabledFor(Level.DEBUG) == true) {
						logger.log(Level.DEBUG, "Adding " + tuple + " to " + name);
					}
					indexedTupleHolderMap.put(tupleHolder.id, tupleHolder);
					processedTupleHolders.add(tupleHolder);
				} else if (tupleAccepted == false && matchingTupleHolder != null) {
					matchingTupleHolder.resetTuple(tuple);
					tupleAccepted = true;
					processedTupleHolders.add(matchingTupleHolder);
					if (logger.isEnabledFor(Level.DEBUG) == true) {
						logger.log(Level.DEBUG, "Repeat resetting on " + matchingTupleHolder + " in " + name);
					}
				}
			}
			Iterator<TupleHolder> tupleHoldersIter = indexedTupleHolderMap.values().iterator();
			while (tupleHoldersIter.hasNext()) {
				TupleHolder tupleHolder = tupleHoldersIter.next();
				if (processedTupleHolders.contains(tupleHolder) == false) {
					tupleHolder.resetTuple(resettableFields);
				}
			}
			processedTupleHolders.clear();
			reIndexMap();
		}
		reBuildData();
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Cache is " + indexedTupleHolderMap + " in " + name);
		}
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "DataSet is  " + data + " in " + name);
		}
	}

	List<Tuple> getData() {
		return data;
	}

	protected void close() {
		if (indexedTupleHolderMap != null) {
			indexedTupleHolderMap.clear();
		}
		if (data != null) {
			data.clear();
		}
		matchingGroupByFields = null;
		resettableFields = null;
		tupleComparator = null;
		deleteObservers();
	}

	private void reIndexMap() {
		LinkedList<TupleHolder> allTupleHolders = new LinkedList<TupleHolder>(indexedTupleHolderMap.values());

		if (tupleComparator != null) {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Sorting " + allTupleHolders.size() + " tupleholder(s) in " + name);
			}
			Collections.sort(allTupleHolders, new Comparator<TupleHolder>() {

				public int compare(TupleHolder tupleHolder1, TupleHolder tupleHolder2) {
					// we use the prior tuple is available , since the reset
					// logic will reset all the fields
					// making comparision incorrect [BUG 2516]
					Tuple tuple1 = tupleHolder1.tuple;
					if (tupleHolder1.reset == true && tupleHolder1.resetPriorTuple != null) {
						tuple1 = tupleHolder1.tuple;
					}
					Tuple tuple2 = tupleHolder2.tuple;
					if (tupleHolder2.reset == true && tupleHolder2.resetPriorTuple != null) {
						tuple2 = tupleHolder2.tuple;
					}
					return tupleComparator.compare(tuple1, tuple2);
				}

			});
		}

		List<TupleHolder> newTupleHolders = applyThreshold(allTupleHolders);

		if (newTupleHolders.size() != allTupleHolders.size()) {
			fireStateChange(DATA_STATES.datatruncated);
		}
		indexedTupleHolderMap.clear();
		Iterator<TupleHolder> tupleHoldersIter = newTupleHolders.iterator();
		while (tupleHoldersIter.hasNext()) {
			TupleHolder tupleHolder = (TupleHolder) tupleHoldersIter.next();
			indexedTupleHolderMap.put(tupleHolder.id, tupleHolder);
		}
	}

	private synchronized void reBuildData() {
		if(data == null) {
			data = new LinkedList<Tuple>();
		} else {
			data.clear();
		}
		Iterator<TupleHolder> tupleHoldersIter = indexedTupleHolderMap.values().iterator();
		while (tupleHoldersIter.hasNext()) {
			TupleHolder tupleHolder = tupleHoldersIter.next();
			data.add(tupleHolder.tuple);
		}
		fireStateChange(DATA_STATES.datarefreshed);
	}

	protected void fireStateChange(DATA_STATES dataState) {
		setChanged();
		notifyObservers(dataState);
	}

	protected abstract <T> List<T> applyThreshold(List<T> tuples);

}