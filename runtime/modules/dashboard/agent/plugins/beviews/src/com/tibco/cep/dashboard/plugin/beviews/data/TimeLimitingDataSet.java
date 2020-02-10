package com.tibco.cep.dashboard.plugin.beviews.data;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.plugin.beviews.common.query.ViewsQueryExecutorFactory;
import com.tibco.cep.dashboard.psvr.common.query.Query;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.common.query.QueryExecutor;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.Updateable;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.types.ThresholdUnitEnum;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author apatil
 *
 */
class TimeLimitingDataSet extends DataSet {

	// what field is being used as the key field
	private String timeFieldName;

	// are we dealing with a positive threshold or negative
	private boolean positiveLimit;

	// is the data set order ascending/descending
	private boolean descendingDataOrder;

	// quick lookup value for threshold
	private int absThresholdValue;

	// the Calender field value
	private int thresholdTimeField;

	protected TimeLimitingDataSet(Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		super(logger, exceptionHandler, messageGenerator);
	}

	@Override
	protected void init(PresentationContext ctx) throws DataException, MALException, ElementNotFoundException {
		super.init(ctx);

		// store values
		absThresholdValue = threshold.getThresholdValue();
		thresholdTimeField = getField(threshold.getThresholdUnit());

		// find positive or negative threshold
		positiveLimit = absThresholdValue > 0;

		// get absolute value once we decide which type of threshold
		absThresholdValue = Math.abs(absThresholdValue);

		// validate that we have one & only one order by date field
		String[] fieldNames = this.tupleComparator.getFieldNames();
		if (fieldNames.length == 0) {
			throw new DataException("No order by spec specified in " + name);
		}
		if (fieldNames.length != 1) {
			throw new DataException("Invalid order by spec specified in " + name);
		}
		if (sourceElement.getField(fieldNames[0]).isDate() == false) {
			throw new DataException("Incorrect non date order by field specified in " + name);
		}
		timeFieldName = fieldNames[0];

		// find sort order of the data set
		descendingDataOrder = !tupleComparator.isSortAscending(timeFieldName);
	}

	@Override
	protected List<Tuple> readDataFromServer(Query query, PresentationContext ctx) throws DataException {
		QueryExecutor queryExecutor = null;
		ResultSet result = null;
		try {
			// get query executor
			queryExecutor = ViewsQueryExecutorFactory.getInstance().createImplementation();
			result = queryExecutor.executeQuery(query);
			if (positiveLimit == true) {
				return readDataForPositiveThreshold(result);
			}
			return readDataForNegativeThreshold(result);
		} catch (QueryException e) {
			throw new DataException(messageGenerator.getMessage("viewdatasrchandler.query.cursoriteration.failure", new MessageGeneratorArgs(e, name)), e);
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (QueryException ignore) {
				}
			}
			if (queryExecutor != null) {
				queryExecutor.close();
			}
		}
	}

	private List<Tuple> readDataForPositiveThreshold(ResultSet resultset) throws QueryException {
		Tuple tuple = null;
		if (descendingDataOrder == true) {
			if (resultset.next() == true) {
				tuple = resultset.getTuple();
			}
		} else {
			while (resultset.next() == true) {
				tuple = resultset.getTuple();
			}
		}
		List<Tuple> tuples = new LinkedList<Tuple>();
		if (tuple != null){
			tuples.add(tuple);
		}
		return tuples;
	}

	private List<Tuple> readDataForNegativeThreshold(ResultSet result) throws QueryException {
		Date maxTime = null;
		Date minTime = null;
		List<Tuple> tuples = new LinkedList<Tuple>();
		if (descendingDataOrder == true) {
			while (result.next() == true) {
				Tuple tuple = result.getTuple();
				if (tuples.size() == 0) {
					FieldValue fieldValueByName = tuple.getFieldValueByName(timeFieldName);
					maxTime = (Date) fieldValueByName.getValue();
					minTime = getLowerDateCompensatedByThreshold(maxTime);
					if (logger.isEnabledFor(Level.DEBUG) == true) {
						logger.log(Level.DEBUG, "Max date for " + timeFieldName + " is " + fieldValueByName.getDataType().toString(maxTime) + " in " + name);
						logger.log(Level.DEBUG, "Min date for " + timeFieldName + " is " + fieldValueByName.getDataType().toString(minTime) + " in " + name);
					}
				}
				else {
					Date tupleDate = (Date) tuple.getFieldValueByName(timeFieldName).getValue();
					if (tupleDate.before(minTime) == true){
						if (logger.isEnabledFor(Level.DEBUG) == true) {
							logger.log(Level.DEBUG, "Reached " + threshold + " threshold in " + name);
						}
						break;
					}
				}
				tuples.add(tuple);
				if (tuples.size() == maxFetchCount) {
					logger.log(Level.WARN, "Max row count limit reached before reaching the " + threshold + " threshold, limiting the number of rows  to " + maxFetchCount + " in " + name);
					break;
				}
			}
		} else {
			while (result.next() == true) {
				Tuple tuple = result.getTuple();
				tuples.add(tuple);
				if (tuples.size() == maxFetchCount) {
					logger.log(Level.WARN, "Max row count limit reached before reaching the " + threshold + " threshold, limiting the number of rows  to " + maxFetchCount + " in " + name);
					break;
				}
			}
			tuples = applyThreshold(tuples);
		}
		return tuples;
	}

	@Override
	void resetData(List<Tuple> tuples) {
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Appending existing cache with " + tuples.size() + " , since " + sourceElement + " is time based in " + name);
		}
		updateData(Updateable.UpdateType.CREATE, tuples);
	}

	@Override
	protected <T> List<T> applyThreshold(List<T> data) {
		if (data == null || data.size() == 0) {
			return data;
		}
		if (data.size() > maxFetchCount) {
			logger.log(Level.WARN, "The number of rows[" + data.size() + "] is exceeding " + maxFetchCount + ", limiting to " + maxFetchCount + " in " + name);
			int size = data.size();
			data = new LinkedList<T>(data.subList(size - (int) maxFetchCount, size));
			return data;
		}
		T referenceObject = null;
		if (descendingDataOrder == true) {
			referenceObject = data.get(0);
		} else {
			referenceObject = data.get(data.size() - 1);
		}
		Tuple referenceTuple = null;
		if (referenceObject instanceof TupleHolder) {
			referenceTuple = ((TupleHolder) referenceObject).tuple;
		} else {
			referenceTuple = (Tuple) referenceObject;
		}
		FieldValue categoryFieldValue = referenceTuple.getFieldValueByName(timeFieldName);
		Date maxTime = (Date) categoryFieldValue.getValue();
		Date minTime = getLowerDateCompensatedByThreshold(maxTime);
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Max date for " + timeFieldName + " is " + categoryFieldValue.getDataType().toString(maxTime) + " in " + name);
			logger.log(Level.DEBUG, "Min date for " + timeFieldName + " is " + categoryFieldValue.getDataType().toString(minTime) + " in " + name);
		}
		List<T> reducedDataList = new LinkedList<T>(data);
		ListIterator<T> tuplesIterator = reducedDataList.listIterator();
		while (tuplesIterator.hasNext()) {
			Object object = tuplesIterator.next();
			Tuple tuple = null;
			if (object instanceof TupleHolder) {
				tuple = ((TupleHolder) object).tuple;
			} else {
				tuple = (Tuple) object;
			}
			Date tupleDate = (Date) tuple.getFieldValueByName(timeFieldName).getValue();
			if (tupleDate.before(minTime) == true){
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, tuple + " is removable, since it's " + timeFieldName + "'s value [" + BuiltInTypes.DATETIME.toString(tupleDate) + "] is older then "
							+ BuiltInTypes.DATETIME.toString(maxTime) + " by " + threshold + " in " + name);
				}
				tuplesIterator.remove();
			}
		}
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			int tuplesRemoved = data.size() - reducedDataList.size();
			logger.log(Level.DEBUG, "Original cache had " + data.size() + " element(s) in " + name);
			logger.log(Level.DEBUG, "Removed " + tuplesRemoved + " element from cache since they are " + threshold + " older in " + name);
			logger.log(Level.DEBUG, "Reduced cache has " + reducedDataList.size() + " element(s) in " + name);
		}
		return reducedDataList;
	}

	private Date getLowerDateCompensatedByThreshold(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(thresholdTimeField, -absThresholdValue);
		Date compensatedDate = calendar.getTime();
		if (compensatedDate.after(date) == true){
			throw new IllegalStateException("Incorrect value ["+BuiltInTypes.DATETIME.toString(compensatedDate)+"] obtained when ["+BuiltInTypes.DATETIME.toString(date)+"] was adjusted by "+threshold);
		}
		return compensatedDate;
	}

	@SuppressWarnings("unused")
	private boolean isRemovable(Date maxTime, Tuple tuple) {
		FieldValue timeFieldValue = tuple.getFieldValueByName(timeFieldName);
		Date tupleDate = (Date) timeFieldValue.getValue();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(tupleDate);
		calendar.roll(thresholdTimeField, absThresholdValue);
		tupleDate = calendar.getTime();
		if (tupleDate.after(maxTime) == true) {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, tuple + " is removable, since it's " + timeFieldName + "'s value [" + timeFieldValue.getDataType().toString(tupleDate) + "] is older then "
						+ timeFieldValue.getDataType().toString(maxTime) + " by " + threshold + " in " + name);
			}
			return true;
		}
		return false;
	}

	private int getField(ThresholdUnitEnum thresholdUnit) {
		switch (thresholdUnit.getType()) {
			case ThresholdUnitEnum.SECOND_TYPE:
				return Calendar.SECOND;
			case ThresholdUnitEnum.MINUTE_TYPE:
				return Calendar.MINUTE;
			case ThresholdUnitEnum.HOUR_TYPE:
				return Calendar.HOUR_OF_DAY;
			case ThresholdUnitEnum.DAY_TYPE:
				return Calendar.DAY_OF_YEAR;
			case ThresholdUnitEnum.MONTH_TYPE:
				return Calendar.DAY_OF_MONTH;
			case ThresholdUnitEnum.YEAR_TYPE:
				return Calendar.YEAR;
			default:
				throw new IllegalArgumentException("Non understandable threshold unit " + thresholdUnit);
		}
	}
}