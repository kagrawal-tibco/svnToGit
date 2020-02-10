package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleComparator;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALTwoDimSeriesConfig;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author apatil
 * 
 */
class TimeSeriesBucketedData {
	
	//PATCH check if we can replace this with a concurrency API
	private static final Object LOCK = new Object();
	
	private static boolean initialized = false;

	private static List<TIME_SERIES_BUCKET_KEY> CONFIGURED_BUCKET_KEYS;

	static void init(Properties properties) {
		if (initialized == false){
			synchronized (LOCK) {
				if (initialized == false) {
					String[] propertyValues = ((String) BEViewsProperties.TIME_SERIES_VISIBLE_BUCKET_VALUES.getValue(properties)).split(",");
					if (propertyValues.length == 1 && propertyValues[0].equalsIgnoreCase("all") == true) {
						CONFIGURED_BUCKET_KEYS = Arrays.asList(TIME_SERIES_BUCKET_KEY.EARLIEST, TIME_SERIES_BUCKET_KEY.MIN, TIME_SERIES_BUCKET_KEY.MAX, TIME_SERIES_BUCKET_KEY.LATEST);
					} else {
						CONFIGURED_BUCKET_KEYS = new ArrayList<TIME_SERIES_BUCKET_KEY>();
						for (String propertyValue : propertyValues) {
							try {
								CONFIGURED_BUCKET_KEYS.add(TIME_SERIES_BUCKET_KEY.valueOf(propertyValue.toUpperCase()));
							} catch (IllegalArgumentException e) {

							}
						}
						if (CONFIGURED_BUCKET_KEYS.isEmpty() == true) {
							CONFIGURED_BUCKET_KEYS = Arrays.asList(TIME_SERIES_BUCKET_KEY.EARLIEST, TIME_SERIES_BUCKET_KEY.MIN, TIME_SERIES_BUCKET_KEY.MAX, TIME_SERIES_BUCKET_KEY.LATEST);
						}
					}
					initialized = true;
				}
			}
		}
	}

	static TimeSeriesBucketedData getTimeSeriesBucketedData(Logger logger, MALTwoDimSeriesConfig seriesConfig, List<Date> timeBuckets, List<Tuple> data, PresentationContext ctx) throws MALException, ElementNotFoundException {
		String seriesCfgURI = URIHelper.getURI(seriesConfig);
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Going to bucket data for " + seriesCfgURI);
			logger.log(Level.DEBUG, "The data for " + seriesCfgURI + " is " + data);
		}

		TIME_SERIES_BUCKET_KEY bucketKey = (TIME_SERIES_BUCKET_KEY) ctx.getAttribute(BEViewsProperties.TIME_SERIES_VISIBLE_BUCKET_VALUES.getName());
		if (bucketKey != null) {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Overriding " + BEViewsProperties.TIME_SERIES_VISIBLE_BUCKET_VALUES.getName() + " to " + bucketKey + " for current processing");
			}
		} else {
			bucketKey = null;
		}

		MALFieldMetaInfo categoryField = ctx.resolveFieldRef(seriesConfig.getCategoryDataConfig().getExtractor().getSourceField());
		MALFieldMetaInfo valueField = ctx.resolveFieldRef(seriesConfig.getValueDataConfig(0).getExtractor().getSourceField());

		TupleComparator categoryFieldComparator = new TupleComparator();
		categoryFieldComparator.addSortSpec(categoryField.getName(), true);

		TupleComparator valueFieldComparator = new TupleComparator();
		valueFieldComparator.addSortSpec(valueField.getName(), true);

		// remove duplicate tuples
		Map<String, Tuple> indexedData = new LinkedHashMap<String, Tuple>();
		Iterator<Tuple> tupleIterator = data.iterator();
		while (tupleIterator.hasNext()) {
			Tuple tuple = (Tuple) tupleIterator.next();
			indexedData.put(tuple.getId(), tuple);
		}

		TimeSeriesBucketedData bucketedData = new TimeSeriesBucketedData();
		Iterator<Date> timeBucketsIterator = timeBuckets.iterator();
		Date timeBucket = null;
		while (timeBucketsIterator.hasNext()) {
			timeBucket = (Date) timeBucketsIterator.next();
			if (timeBucketsIterator.hasNext() == true) {
				Map<String, Tuple> bucketedTuples = bucketTuples(logger, categoryField, indexedData, timeBucket.getTime());
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, "Bucketed " + bucketedTuples + " against " + timeBucket + " for " + seriesCfgURI);
				} else if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, "Bucketed " + bucketedTuples.size() + " against " + timeBucket + " for " + seriesCfgURI);
				}
				if (bucketedTuples.size() > 0) {
					if (bucketedTuples.size() == 1) {
						Tuple tuple = bucketedTuples.get(bucketedTuples.keySet().iterator().next());
						bucketedData.addTupleToSet(logger, seriesCfgURI, TIME_SERIES_BUCKET_KEY.EXACT, timeBucket, tuple);
					} else {
						if (bucketKey != null) {
							bucketedData.setValueSetKey(bucketKey);
						}
						LinkedList<Tuple> tuples = new LinkedList<Tuple>(bucketedTuples.values());
						Collections.sort(tuples, categoryFieldComparator);
						bucketedData.addTupleToSet(logger, seriesCfgURI, TIME_SERIES_BUCKET_KEY.EARLIEST, timeBucket, tuples.getFirst());
						bucketedData.addTupleToSet(logger, seriesCfgURI, TIME_SERIES_BUCKET_KEY.LATEST, timeBucket, tuples.getLast());
						Collections.sort(tuples, valueFieldComparator);
						bucketedData.addTupleToSet(logger, seriesCfgURI, TIME_SERIES_BUCKET_KEY.MIN, timeBucket, tuples.getFirst());
						bucketedData.addTupleToSet(logger, seriesCfgURI, TIME_SERIES_BUCKET_KEY.MAX, timeBucket, tuples.getLast());
					}
				}
				indexedData.keySet().removeAll(bucketedTuples.keySet());
			}
		}
		int remainingDataSize = indexedData.size();
		if (remainingDataSize != 0) {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Bucketed " + indexedData + " against " + timeBucket + " for " + seriesCfgURI);
			} else if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Bucketed " + indexedData.size() + " against " + timeBucket + " for " + seriesCfgURI);
			}
			if (remainingDataSize == 1) {
				Tuple tuple = indexedData.get(indexedData.keySet().iterator().next());
				bucketedData.addTupleToSet(logger, seriesCfgURI, TIME_SERIES_BUCKET_KEY.EXACT, timeBucket, tuple);
			} else if (remainingDataSize > 1) {
				LinkedList<Tuple> tuples = new LinkedList<Tuple>(indexedData.values());
				Collections.sort(tuples, categoryFieldComparator);
				bucketedData.addTupleToSet(logger, seriesCfgURI, TIME_SERIES_BUCKET_KEY.EARLIEST, timeBucket, tuples.getFirst());
				bucketedData.addTupleToSet(logger, seriesCfgURI, TIME_SERIES_BUCKET_KEY.LATEST, timeBucket, tuples.getLast());
				Collections.sort(tuples, valueFieldComparator);
				bucketedData.addTupleToSet(logger, seriesCfgURI, TIME_SERIES_BUCKET_KEY.MIN, timeBucket, tuples.getFirst());
				bucketedData.addTupleToSet(logger, seriesCfgURI, TIME_SERIES_BUCKET_KEY.MAX, timeBucket, tuples.getLast());
			}
		}
		return bucketedData;
	}

	private static Map<String, Tuple> bucketTuples(Logger logger, MALFieldMetaInfo categoryField, Map<String, Tuple> seriesData, long bucketEnd) {
		Map<String, Tuple> map = new LinkedHashMap<String, Tuple>();
		Iterator<Tuple> tuplesIterator = seriesData.values().iterator();
		while (tuplesIterator.hasNext()) {
			Tuple tuple = tuplesIterator.next();
			Date categoryDate = (Date) tuple.getFieldValueByName(categoryField.getName()).getValue();
			long categoryTupleTime = categoryDate.getTime();
			if (categoryTupleTime > bucketEnd) {
				break;
			}
			map.put(tuple.getId(), tuple);
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Adding " + tuple.toString() + " against " + bucketEnd + " since its " + categoryField.getName() + " is " + categoryTupleTime + " msec(s)");
			}
		}
		return map;
	}

	private Map<TIME_SERIES_BUCKET_KEY, LinkedHashMap<Date, Tuple>> bucketedData;

	private List<TIME_SERIES_BUCKET_KEY> bucketValueSet;

	TimeSeriesBucketedData() {
		bucketedData = new HashMap<TIME_SERIES_BUCKET_KEY, LinkedHashMap<Date, Tuple>>();
		bucketValueSet = new LinkedList<TIME_SERIES_BUCKET_KEY>(CONFIGURED_BUCKET_KEYS);
	}

	private void setValueSetKey(TIME_SERIES_BUCKET_KEY bucketKey) {
		bucketValueSet.removeAll(bucketValueSet);
		bucketValueSet.add(bucketKey);
	}

	private void addTupleToSet(Logger logger, String loggingIndentifier, TIME_SERIES_BUCKET_KEY setKey, Date categoryDate, Tuple tuple) {
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Adding " + tuple + " as " + setKey + " against " + categoryDate + " for " + loggingIndentifier);
		} else if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Adding one tuple as " + setKey + " against " + categoryDate + " for " + loggingIndentifier);
		}
		LinkedHashMap<Date, Tuple> actualSet = bucketedData.get(setKey);
		if (actualSet == null) {
			actualSet = new LinkedHashMap<Date, Tuple>();
			bucketedData.put(setKey, actualSet);
		}
		actualSet.put(categoryDate, tuple);
	}

	Map<Date, Tuple> getExactMatch() {
		return bucketedData.get(TIME_SERIES_BUCKET_KEY.EXACT);
	}

	List<TIME_SERIES_BUCKET_KEY> getSetKeys() {
		return bucketValueSet;
	}

	Map<Date, Tuple> getSet(TIME_SERIES_BUCKET_KEY setKey) {
		return bucketedData.get(setKey);
	}
}