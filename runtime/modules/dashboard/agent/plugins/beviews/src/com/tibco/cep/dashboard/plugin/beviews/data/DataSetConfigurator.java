package com.tibco.cep.dashboard.plugin.beviews.data;

import java.util.Observer;
import java.util.Properties;

import com.tibco.cep.dashboard.common.data.TupleComparator;
import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsPlugIn;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.Threshold;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.dashboard.psvr.mal.model.types.ThresholdUnitEnum;
import com.tibco.cep.dashboard.psvr.plugin.PlugIn;
import com.tibco.cep.dashboard.psvr.plugin.PluginFinder;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

class DataSetConfigurator {

	//private static long DATASET_TIME_THRESHOLD = (Long) BEViewsProperties.DATASET_LIMIT_AS_TIME_THRESHOLD_KEY.getDefaultValue();

	private static int MAX_TIME_DATASET_COUNT = (Integer)BEViewsProperties.MAX_TIME_RESULTSET_FETCH_CNT_KEY.getDefaultValue();

	private static int MAX_DATASET_LIMIT = (Integer)BEViewsProperties.MAX_COUNT_RESULTSET_FETCH_CNT_KEY.getDefaultValue();
	
	private static boolean DEMO_MODE_ON = (Boolean)BEViewsProperties.DEMO_MODE.getDefaultValue();
	
	private static PlugIn hostingPlugin;
	
	static {
		hostingPlugin = PluginFinder.getInstance().getPluginById(BEViewsPlugIn.PLUGIN_ID);
		if (hostingPlugin == null) {
			throw new IllegalStateException(BEViewsPlugIn.PLUGIN_ID + " is not initialized");
		}
		Properties plugInProperties = hostingPlugin.getProperties();

		//DATASET_TIME_THRESHOLD = (Long) BEViewsProperties.DATASET_LIMIT_AS_TIME_THRESHOLD_KEY.getValue(plugInProperties);

		MAX_TIME_DATASET_COUNT = (Integer) BEViewsProperties.MAX_TIME_RESULTSET_FETCH_CNT_KEY.getValue(plugInProperties);

		MAX_DATASET_LIMIT = (Integer) BEViewsProperties.MAX_COUNT_RESULTSET_FETCH_CNT_KEY.getValue(plugInProperties);
		
		DEMO_MODE_ON = (Boolean) BEViewsProperties.DEMO_MODE.getValue(plugInProperties);
		
	}

	private String name;
	private MALSourceElement sourceElement;
	private MALSeriesConfig seriesConfig; 
	private Threshold threshold;
	private int maxFetchCount;
	private Observer observer;

	private TupleComparator tupleComparator;

	public DataSetConfigurator() {
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final MALSourceElement getSourceElement() {
		return sourceElement;
	}

	public final void setSourceElement(MALSourceElement sourceElement) {
		this.sourceElement = sourceElement;	
	}
	
	public final MALSeriesConfig getSeriesConfig() {
		return seriesConfig;
	}

	public final void setSeriesConfig(MALSeriesConfig seriesConfig) {
		this.seriesConfig = seriesConfig;
	}

	public final Threshold getThreshold() {
		return threshold;
	}

	public final void setThreshold(Threshold threshold) {
		this.threshold = threshold;
	}

	public final int getMaxFetchCount() {
		return maxFetchCount;
	}

	public DataSet getDataSet(PresentationContext ctx) throws DataException, MALException, ElementNotFoundException {
		if (name == null) {
			throw new IllegalStateException("No name provided");
		}
		if (sourceElement == null) {
			throw new IllegalStateException("No queried element provided");
		}
		if (seriesConfig == null) {
			throw new IllegalStateException("No series config provided");
		}		
		if (threshold == null) {
			throw new IllegalStateException("No threshold provided");
		}
		if (observer == null) {
			throw new IllegalStateException("No observer provided");
		}
		if (tupleComparator == null) {
			throw new IllegalStateException("No tuple comparator provided");
		}
		Logger logger = LoggingService.getChildLogger(hostingPlugin.getLogger(), "data");
		ExceptionHandler exceptionHandler = new ExceptionHandler(logger);
		MessageGenerator messageGenerator = new MessageGenerator(hostingPlugin.getName(),exceptionHandler);
		boolean isTimeSeries = isTimeSeries(logger);
		DataSet dataSet = null;
		maxFetchCount = MAX_DATASET_LIMIT;
		if (DEMO_MODE_ON == true){
			dataSet = new DesignDataBasedDataSet(seriesConfig,logger,exceptionHandler,messageGenerator);
		}
		else if (isTimeSeries == false) {
			dataSet = new CountLimitingDataSet(logger,exceptionHandler,messageGenerator);
		} else {
			dataSet = new TimeLimitingDataSet(logger,exceptionHandler,messageGenerator);
			maxFetchCount = MAX_TIME_DATASET_COUNT;
		}
		configure(dataSet,ctx);
		return dataSet;
	}
	
	private boolean isTimeSeries(Logger logger){
		if (threshold.getThresholdUnit() == ThresholdUnitEnum.COUNT){
			logger.log(Level.DEBUG, name + " is not a time series since the threshold unit is "+ThresholdUnitEnum.COUNT);
			return false;
		}
		if (threshold.getThresholdValue() == 0){
			logger.log(Level.DEBUG, name + " is not a time series since the threshold value is 0 event though the threshold unit is "+threshold.getThresholdUnit());
			return false;
		}
		return true;
	}

	@SuppressWarnings("unused")
	private boolean isTimeSeries(Logger logger, String loggingIndentifier, MALFieldMetaInfo categoryField, Threshold threshold) {
		if (categoryField == null) {
			if (logger.isEnabledFor(Level.DEBUG)) {
				logger.log(Level.DEBUG, loggingIndentifier + " is not a time series since it has no category field projected...");
			}
			return false;
		} else if (categoryField.isDate() == false) {
			if (logger.isEnabledFor(Level.DEBUG)) {
				logger.log(Level.DEBUG, loggingIndentifier + " is not a time series since " + categoryField + " is not date type...");
			}
			return false;
		} else if (threshold.getThresholdUnit().getType() == ThresholdUnitEnum.COUNT_TYPE) {
			if (logger.isEnabledFor(Level.DEBUG)) {
				logger.log(Level.DEBUG, loggingIndentifier + " is not a time series since " + categoryField + " is date type but dataset limit is set to " + ThresholdUnitEnum.COUNT + "...");
			}
			return false;
		}
		if (logger.isEnabledFor(Level.DEBUG)) {
			logger.log(Level.DEBUG, loggingIndentifier + " is a time series since " + categoryField + " is date type with dataset limit as "+threshold.getThresholdValue()+" "+threshold.getThresholdUnit()+"...");
		}
		return true;
	}

	private void configure(DataSet dataSet, PresentationContext ctx) throws DataException, MALException, ElementNotFoundException {
		dataSet.name = name;
		dataSet.sourceElement = sourceElement;
		dataSet.threshold = threshold;
		dataSet.maxFetchCount = maxFetchCount;
		dataSet.tupleComparator = tupleComparator;
		dataSet.addObserver(observer);
		dataSet.init(ctx);
	}

	public final Observer getObserver() {
		return observer;
	}

	public final void setObserver(Observer observer) {
		this.observer = observer;
	}

	public final TupleComparator getTupleComparator() {
		return tupleComparator;
	}

	public final void setTupleComparator(TupleComparator tupleComparator) {
		this.tupleComparator = tupleComparator;
	}

}