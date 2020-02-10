package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.utils.DateTimeUtils;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.psvr.data.Threshold;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALFlowLayoutConstraint;
import com.tibco.cep.dashboard.psvr.mal.model.MALLayoutConstraint;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALTwoDimSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.types.ThresholdUnitEnum;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.SeriesDataHolder;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author apatil
 *
 */
public class TimeAxisGenerator {

	private Logger logger;

    private Integer[] maxPlottablePointsArray;

    private TIME_AXIS_STANDARDIZATION_MODE standardizationMode = TIME_AXIS_STANDARDIZATION_MODE.DATASET;

    void init(Logger logger, Properties properties) throws VisualizationException {
    	this.logger = logger;
//    	standardizationMode = TIME_AXIS_STANDARDIZATION_MODE.valueOf(String.valueOf(BEViewsProperties.TIME_SERIES_STANDARDIZATION_MODE.getValue(properties)).toUpperCase());

    	String maxPlottablePointsDefaultValue = (String) BEViewsProperties.TIME_SERIES_MAX_PLOTTABLE_POINTS.getDefaultValue();
    	String[] propValueArray = maxPlottablePointsDefaultValue.split(",");
    	Map<Integer,Integer> convertedValues = new LinkedHashMap<Integer,Integer>(propValueArray.length);
    	for (int i = 0; i < propValueArray.length; i++) {
    		int convertedValue = Integer.parseInt(propValueArray[i]);
			convertedValues.put(i,convertedValue);
		}
    	String plottablePoints = (String)BEViewsProperties.TIME_SERIES_MAX_PLOTTABLE_POINTS.getValue(properties);
    	if (plottablePoints.equals(BEViewsProperties.TIME_SERIES_MAX_PLOTTABLE_POINTS.getDefaultValue()) == false) {
	    	propValueArray = plottablePoints.split(",");
	        for (int i = 0; i < propValueArray.length; i++) {
	            try {
	        		int convertedValue = Integer.parseInt(propValueArray[i]);
	    			convertedValues.put(i,convertedValue);
	    		} catch (NumberFormatException e) {
	    			String msg = "Invalid count["+propValueArray[i]+"] specified for chart spanning "+(i+1)+" columns against "+BEViewsProperties.TIME_SERIES_MAX_PLOTTABLE_POINTS.getName();
	            	if (convertedValues.get(i) == null) {
	            		throw new VisualizationException(msg);
	            	}
	            	else {
		                if (logger.isEnabledFor(Level.WARN) == true) {
		                    logger.log(Level.WARN, msg+", skipping ...");
		                }
	            	}
	            }
	        }
    	}
        maxPlottablePointsArray = convertedValues.values().toArray(new Integer[convertedValues.size()]);
    }

    boolean generateTimeAxis(MALChartComponent component, List<Date> timeAxis, PresentationContext ctx) throws MALException, ElementNotFoundException, VisualizationException {
        if (logger.isEnabledFor(Level.DEBUG) == true) {
            logger.log(Level.DEBUG, "Going to generate the standardized time axis for "+component);
        }
        int colspan = 1;
        MALLayoutConstraint layoutConstraint = component.getLayoutConstraint();
        if (layoutConstraint instanceof MALFlowLayoutConstraint){
            colspan = ((MALFlowLayoutConstraint)layoutConstraint).getComponentColSpan();
        }
        int maxPlottablePoints = maxPlottablePointsArray[maxPlottablePointsArray.length-1];
        if (colspan > maxPlottablePointsArray.length){
        	logger.log(Level.WARN, "No max plottable data points value is provided for colspan of "+colspan+" for "+component+" defaulting to "+maxPlottablePoints);
        }
        else {
        	maxPlottablePoints = maxPlottablePointsArray[colspan-1];
	        if (logger.isEnabledFor(Level.DEBUG) == true) {
	            logger.log(Level.DEBUG, "Maximum number of data points plottable for "+component+" is "+maxPlottablePoints);
	        }
        }
        HashMap<String, SeriesInfo> computedSeriesInfoMap = new HashMap<String, SeriesInfo>();
        SeriesDataHolder dataHolder = new SeriesDataHolder(component, ctx);
        for (MALVisualization visualization : component.getVisualization()) {
			for (MALSeriesConfig seriesConfig : visualization.getSeriesConfig()) {
				SeriesInfo seriesInfo = new SeriesInfo(seriesConfig, ctx);
                seriesInfo.init(dataHolder.getSeriesData(seriesConfig.getId()));
                if (logger.isEnabledFor(Level.DEBUG) == true) {
                    logger.log(Level.DEBUG, "Generated "+seriesInfo+" for "+URIHelper.getURI(seriesConfig));
                }
                computedSeriesInfoMap.put(seriesConfig.getId(), seriesInfo);
			}
		}

        TIME_AXIS_STANDARDIZATION_MODE localStandardizationMode = (TIME_AXIS_STANDARDIZATION_MODE) ctx.getAttribute(BEViewsProperties.TIME_SERIES_MAX_PLOTTABLE_POINTS.getName());

        if (localStandardizationMode == null){
        	localStandardizationMode = standardizationMode;
        }
        else if (localStandardizationMode.equals(standardizationMode) == false){
            if (logger.isEnabledFor(Level.DEBUG) == true) {
                logger.log(Level.DEBUG, "Overriding "+standardizationMode+" to "+localStandardizationMode+" for current processing");
            }
        }
//        if (localStandardizationMode.equals(TIME_AXIS_STANDARDIZATION_MODE.DATA)){
//            return computeAxisValuesUsingData(component, computedSeriesInfoMap, dataHolder,maxPlottablePoints, timeAxis, ctx);
//        }
        return computeAxisValueUsingTimeDataSetLimit(component, computedSeriesInfoMap, dataHolder, maxPlottablePoints, timeAxis, ctx);
    }

    private boolean computeAxisValueUsingTimeDataSetLimit(MALChartComponent component, HashMap<String, SeriesInfo> computedSeriesInfoMap, SeriesDataHolder dataHolder, int maxPlottablePoints, List<Date> timeAxis, PresentationContext ctx) throws MALException, ElementNotFoundException, VisualizationException {
        if (logger.isEnabledFor(Level.DEBUG) == true) {
            logger.log(Level.DEBUG, "Going to generate the standardized time axis using dataset limit for "+component);
        }
        Date lastDate = null;
        long highestDataSetLimit = -1;
        SeriesInfo highestDataSetLimitSeriesInfo = null;
        if (computedSeriesInfoMap.size() == 1){
            highestDataSetLimitSeriesInfo = computedSeriesInfoMap.values().iterator().next();
            lastDate = highestDataSetLimitSeriesInfo.maxDate;
            highestDataSetLimit = Math.abs(highestDataSetLimitSeriesInfo.datsSetLimit);
        }
        else {
            Iterator<SeriesInfo> seriesInfoIterator = computedSeriesInfoMap.values().iterator();
            while (seriesInfoIterator.hasNext()) {
                SeriesInfo seriesInfo = (SeriesInfo) seriesInfoIterator.next();

                List<Tuple> seriesTimeAxisValues = dataHolder.getSeriesData(seriesInfo.seriesConfig.getId());
                LinkedList<Date> seriesCategoryDates = seriesInfo.getCategoryDates(seriesTimeAxisValues);

                long dataSetLimit = seriesInfo.datsSetLimit;
                if (dataSetLimit == 0){
                    //data set limit is zero, we are going to create a data set limit based on the data
                	if (seriesCategoryDates.isEmpty() == false) {
                		dataSetLimit = seriesCategoryDates.getLast().getTime() - seriesCategoryDates.getFirst().getTime();
                		//PORT should we log that we are getting the dataset from the data
                	}
                }
                if (highestDataSetLimit < Math.abs(dataSetLimit)){
                    highestDataSetLimit = Math.abs(dataSetLimit);
                    highestDataSetLimitSeriesInfo = seriesInfo;
                }

                //Anand - We need to check if the category dates list for a series is empty - bug # 7993 - 09/26/2007
                if (seriesCategoryDates.isEmpty() == false){
                    Date date = seriesCategoryDates.get(seriesCategoryDates.size()-1);
                    if (lastDate == null || (date.compareTo(lastDate) > 0)){
                        lastDate = date;
                    }
                }
                else{
                    //Anand - the category dates list is empty, we will process next series info hoping to find a non empty category dates list
                    highestDataSetLimit = -1;
                    highestDataSetLimitSeriesInfo = null;
                    lastDate = null;
                }
            }
            if (logger.isEnabledFor(Level.DEBUG) == true) {
                logger.log(Level.DEBUG, "Going to use "+highestDataSetLimitSeriesInfo+" for standardized time axis generation since the dataset limit for "+component);
            }
        }
        timeAxis.clear();
        //we need to check if the last date is null [this will happen if the chart is being asked for plotting with no data in DB] bug # 7986 & 7993
        if (lastDate == null){
            return false;
        }
        long bucketSize = highestDataSetLimit/(maxPlottablePoints - 1);
        lastDate = roundDateAtConstantInteval(lastDate,bucketSize);
        Date firstDate = new Date(lastDate.getTime() - highestDataSetLimit);
        if (logger.isEnabledFor(Level.DEBUG) == true) {
            logger.log(Level.DEBUG, "Going to use "+lastDate+" as last date, "+firstDate+" as first date and "+bucketSize+" as bucket size for the standardized time axis for "+component);
        }
        timeAxis.add(firstDate);
        for (int i = 1; i < maxPlottablePoints; i++) {
            firstDate = new Date(firstDate.getTime()+bucketSize);
            timeAxis.add(firstDate);
        }
        if (logger.isEnabledFor(Level.DEBUG) == true) {
            logger.log(Level.DEBUG, "Generated "+timeAxis+" as standardized time axis for "+component);
        }
        return true;
    }

    @SuppressWarnings("unused")
	private boolean computeAxisValuesUsingData(MALChartComponent chartComponent, HashMap<String, SeriesInfo> computedSeriesInfoMap, SeriesDataHolder dataHolder, int maxPlottablePoints, List<Date> timeAxis, PresentationContext ctx) {
        if (logger.isEnabledFor(Level.DEBUG) == true) {
            logger.log(Level.DEBUG, "Going to generate the standardized time axis using actual data for "+chartComponent);
        }
        boolean timeAxisBucketed = false;
        //sort all series info by time scale
        LinkedList<SeriesInfo> seriesInfoList = new LinkedList<SeriesInfo>(computedSeriesInfoMap.values());
        Collections.sort(seriesInfoList, new Comparator<SeriesInfo> (){

            public int compare(SeriesInfo s1, SeriesInfo s2) {
                if (s1.scaleUnit == s2.scaleUnit){
                    return 0;
                }
                if (s1.scaleUnit > s2.scaleUnit){
                    return 1;
                }
                return -1;
            }

        });
        //merge all category dates by scale
        LinkedHashMap<Integer, HashSet<Date>> scaleWiseCategoryDates = new LinkedHashMap<Integer, HashSet<Date>>();
        Iterator<SeriesInfo> seriesInfoIterator = seriesInfoList.iterator();
        while (seriesInfoIterator.hasNext()) {
            SeriesInfo seriesInfo = (SeriesInfo) seriesInfoIterator.next();
            Integer scaleUnitObj = new Integer(seriesInfo.scaleUnit);
            HashSet<Date> mergedCategoryDates = scaleWiseCategoryDates.get(scaleUnitObj);
            if (mergedCategoryDates == null){
                mergedCategoryDates = new HashSet<Date>();
                scaleWiseCategoryDates.put(scaleUnitObj, mergedCategoryDates);
            }
            List<Tuple> seriesTimeAxisValues = dataHolder.getSeriesData(seriesInfo.seriesConfig.getId());
            List<Date> seriesCategoryDates = seriesInfo.getCategoryDates(seriesTimeAxisValues);
            if (logger.isEnabledFor(Level.DEBUG) == true) {
                logger.log(Level.DEBUG, "Got "+seriesCategoryDates+" as time axis values for "+seriesInfo.seriesConfig);
            }
            mergedCategoryDates.addAll(seriesCategoryDates);
        }

        //merge all the category axis values across scales
        List<Date>standardizedTimeAxisValues = Collections.emptyList();
        if (scaleWiseCategoryDates.size() == 1){
            //we have only one scale unit , we are lucky
            Integer scaleUnitKey = scaleWiseCategoryDates.keySet().iterator().next();
            if (logger.isEnabledFor(Level.DEBUG) == true) {
                logger.log(Level.DEBUG, chartComponent+" has only "+SeriesInfo.getScaleUnitName(scaleUnitKey.intValue())+" scale");
            }
            LinkedList<Date>possibleTimeBuckets = new LinkedList<Date>(scaleWiseCategoryDates.get(scaleUnitKey));
            Collections.sort(possibleTimeBuckets);
            if (logger.isEnabledFor(Level.DEBUG) == true) {
                logger.log(Level.DEBUG, "The actual time axis values for "+chartComponent+" are "+possibleTimeBuckets);
            }
            if (possibleTimeBuckets.size() > maxPlottablePoints){
                Date firstDate = roundDate(possibleTimeBuckets.getFirst(),scaleUnitKey.intValue(),false);
                Date lastDate = roundDate(possibleTimeBuckets.getLast(),scaleUnitKey.intValue(),true);
                long range = lastDate.getTime() - firstDate.getTime();
                long bucketSize = range/(maxPlottablePoints-1);
                if (logger.isEnabledFor(Level.DEBUG) == true) {
                    logger.log(Level.DEBUG, "Going to use "+firstDate+" as first date, "+lastDate+" as last date and "+bucketSize+" as bucket size for the standardized time axis for "+chartComponent);
                }
                standardizedTimeAxisValues = new LinkedList<Date>();
                standardizedTimeAxisValues.add(firstDate);
                for (int i = 1; i < maxPlottablePoints; i++) {
                    firstDate = new Date(firstDate.getTime()+bucketSize);
                    standardizedTimeAxisValues.add(firstDate);
                }
                timeAxisBucketed = true;
            }
            else{
                if (logger.isEnabledFor(Level.DEBUG) == true) {
                    logger.log(Level.DEBUG, chartComponent+" does not need standard time axis generation since it's time axis value["+possibleTimeBuckets.size()+"] are less then maximum plottable data points["+maxPlottablePoints+"]..");
                }
                standardizedTimeAxisValues = possibleTimeBuckets;
                timeAxisBucketed = false;
            }
        }
        else {
            //we have more then one scale unit
            if (logger.isEnabledFor(Level.DEBUG) == true) {
                logger.log(Level.DEBUG, chartComponent+" has "+scaleWiseCategoryDates.size()+" scale units..");
            }
            Iterator<Integer> scaleUnitsIterator = scaleWiseCategoryDates.keySet().iterator();
            Date firstDate = null;
            Date lastDate = null;
            while (scaleUnitsIterator.hasNext()) {
                Integer scaleUnit = (Integer) scaleUnitsIterator.next();
                LinkedList<Date> categoryDates = new LinkedList<Date>(scaleWiseCategoryDates.get(scaleUnit));
                Collections.sort(categoryDates);
                if (logger.isEnabledFor(Level.DEBUG) == true) {
                    logger.log(Level.DEBUG, "The actual time axis values for "+SeriesInfo.getScaleUnitName(scaleUnit.intValue())+" in "+chartComponent+" are "+categoryDates);
                }
                Date date = categoryDates.getFirst();
                if (firstDate == null || (date.compareTo(firstDate) < 0)){
                    firstDate = date;
                }
                date = categoryDates.getLast();
                if (lastDate == null || (date.compareTo(lastDate) > 0)){
                    lastDate = date;
                }
                if (logger.isEnabledFor(Level.DEBUG) == true) {
                    logger.log(Level.DEBUG, "The first date is "+categoryDates.getFirst()+" and last date is "+categoryDates.getLast()+" for "+SeriesInfo.getScaleUnitName(scaleUnit.intValue())+" in "+chartComponent);
                }
            }
            Integer scaleUnitKey = scaleWiseCategoryDates.keySet().iterator().next();
            firstDate = roundDate(firstDate,scaleUnitKey.intValue(),false);
            lastDate = roundDate(lastDate,scaleUnitKey.intValue(),true);
            long range = lastDate.getTime() - firstDate.getTime();
            long bucketSize = range/(maxPlottablePoints - 1);
            if (logger.isEnabledFor(Level.DEBUG) == true) {
                logger.log(Level.DEBUG, "Going to use "+firstDate+" as first date, "+lastDate+" as last date and "+bucketSize+" as bucket size for the standardized time axis for "+chartComponent);
            }
            standardizedTimeAxisValues = new LinkedList<Date>();
            standardizedTimeAxisValues.add(firstDate);
            for (int i = 1; i < maxPlottablePoints; i++) {
                firstDate = new Date(firstDate.getTime()+bucketSize);
                standardizedTimeAxisValues.add(firstDate);
            }

            timeAxisBucketed = true;
        }
        timeAxis.clear();
        timeAxis.addAll(standardizedTimeAxisValues);
        if (logger.isEnabledFor(Level.DEBUG) == true) {
            logger.log(Level.DEBUG, "Generated "+timeAxis+" as standardized time axis for "+chartComponent);
        }
        return timeAxisBucketed;
    }

    private Date roundDateAtConstantInteval(Date date, long interval) {
        //Round the time at constant intervals from epoch
        long roundedTime = date.getTime();
        roundedTime = roundedTime - (roundedTime % interval);
        //Return next rounded time value (it's always later than input date)
        return new Date(roundedTime+interval);
    }

    private Date roundDate(Date date, int scaleUnit, boolean up) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int compensation = 0;
        switch (scaleUnit) {
            case Calendar.MILLISECOND:
                //we round by 50 msecs with a garunteed gap of atleast 50 msecs
                int scaleUnitValue = calendar.get(scaleUnit);
                do {
                    compensation = (up == true) ? compensation+1 : compensation-1;
                    scaleUnitValue = (up == true) ? scaleUnitValue+1 : scaleUnitValue-1;
                } while (Math.abs(compensation) < 50 || scaleUnitValue % 50 != 0);
                calendar.add(scaleUnit,compensation);
                break;
            case Calendar.SECOND:
                //we round by 15 secs with a garunteed gap of atleast 15 secs
                scaleUnitValue = calendar.get(scaleUnit);
                do {
                    compensation = (up == true) ? compensation+1 : compensation-1;
                    scaleUnitValue = (up == true) ? scaleUnitValue+1 : scaleUnitValue-1;
                } while (Math.abs(compensation) < 15 || scaleUnitValue % 15 != 0);
                calendar.add(scaleUnit,compensation);
                calendar.set(Calendar.MILLISECOND, 0);
                break;
            case Calendar.MINUTE:
                //we round by 5 mins with a garunteed gap of atleast 5 mins
                scaleUnitValue = calendar.get(scaleUnit);
                do {
                    compensation = (up == true) ? compensation+1 : compensation-1;
                    scaleUnitValue = (up == true) ? scaleUnitValue+1 : scaleUnitValue-1;
                } while (Math.abs(compensation) <= 5 || scaleUnitValue % 5 != 0);
                calendar.add(scaleUnit,compensation);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.SECOND, 0);
                break;
            case Calendar.HOUR:
                //we round by 15 mins
                scaleUnitValue = calendar.get(Calendar.MINUTE);
                do {
                    compensation = (up == true) ? compensation+1 : compensation-1;
                    scaleUnitValue = (up == true) ? scaleUnitValue+1 : scaleUnitValue-1;
                } while (scaleUnitValue % 15 != 0);
                calendar.add(Calendar.MINUTE,compensation);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                break;
            case Calendar.DAY_OF_MONTH:
                //we round by 1 day
                compensation = (up == true) ? compensation+1 : compensation;
                calendar.add(Calendar.DAY_OF_MONTH,compensation);
                calendar.set(Calendar.AM_PM, Calendar.AM);
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                break;
            case Calendar.MONTH:
                //we round by 1 month
                compensation = (up == true) ? compensation+1 : compensation;
                calendar.add(Calendar.MONTH,compensation);
                calendar.set(Calendar.AM_PM, Calendar.AM);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                break;
            case Calendar.YEAR:
                //we round by 1 year
                compensation = (up == true) ? compensation+1 : compensation-1;
                calendar.add(Calendar.YEAR,compensation);
                calendar.set(Calendar.MONTH, 0);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                break;
            default:
                break;
        }
        return calendar.getTime();
    }

//    private Date roundDateDownwards(Date date,int scaleUnit) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        int scaleUnitValue = calendar.get(scaleUnit);
//        int compensation = 0;
//        while (scaleUnitValue % 5 != 0){
//            compensation--;
//            scaleUnitValue--;
//        }
//        calendar.add(scaleUnit,compensation);
//        return calendar.getTime();
//    }

    private static final class SeriesInfo {

        MALTwoDimSeriesConfig seriesConfig;

        MALFieldMetaInfo categoryField;

        MALFieldMetaInfo valueField;

        Date minDate;

        Date maxDate;

        long scale;

        int scaleUnit;

        String scaleUnitName;

        long range;

        int dataPointsCount;

        long datsSetLimit;

        SeriesInfo(MALSeriesConfig seriesConfig, PresentationContext pCtx) throws MALException, ElementNotFoundException, VisualizationException {
            scale = -1;
            scaleUnit = -1;
            range = -1;
            this.seriesConfig = (MALTwoDimSeriesConfig) seriesConfig;
            this.categoryField = pCtx.resolveFieldRef(this.seriesConfig.getCategoryDataConfig().getExtractor().getSourceField());
            // PATCH we are looking @ the first value field projection shd be OK
            this.valueField = pCtx.resolveFieldRef(this.seriesConfig.getValueDataConfig(0).getExtractor().getSourceField());
            Threshold thresholdObj = pCtx.getDataSourceHandler(seriesConfig).getThreshold();
            switch (thresholdObj.getThresholdUnit().getType()) {
            	case ThresholdUnitEnum.SECOND_TYPE :
            		datsSetLimit = thresholdObj.getThresholdValue() * DateTimeUtils.SECOND;
            		break;
            	case ThresholdUnitEnum.MINUTE_TYPE :
            		datsSetLimit = thresholdObj.getThresholdValue() * DateTimeUtils.MINUTE;
            		break;
            	case ThresholdUnitEnum.HOUR_TYPE:
            		datsSetLimit = thresholdObj.getThresholdValue() * DateTimeUtils.HOUR;
            		break;
            	case ThresholdUnitEnum.DAY_TYPE :
            		datsSetLimit = thresholdObj.getThresholdValue() * DateTimeUtils.DAY;
            		break;
            	case ThresholdUnitEnum.MONTH_TYPE :
            		datsSetLimit = thresholdObj.getThresholdValue() * DateTimeUtils.MONTH;
            		break;
            	case ThresholdUnitEnum.YEAR_TYPE :
            		datsSetLimit = thresholdObj.getThresholdValue() * DateTimeUtils.YEAR;
            		break;
            }
        }

        void init(List<Tuple> tuples) {
            this.dataPointsCount = tuples.size();
            if (dataPointsCount > 0){
                minDate = (Date) tuples.get(0).getFieldValueByName(categoryField.getName()).getValue();
                maxDate = (Date) tuples.get(tuples.size() - 1).getFieldValueByName(categoryField.getName()).getValue();
                range = maxDate.getTime() - minDate.getTime();
                scale = range / dataPointsCount;
                scaleUnit = computeMinimumScale();
                scaleUnitName = getScaleUnitName(scaleUnit);
            }
        }

        public LinkedList<Date> getCategoryDates(List<Tuple> tuples) {
            LinkedList<Date> categoryDates = new LinkedList<Date>();
            Iterator<Tuple> tuplesIterator = tuples.iterator();
            while (tuplesIterator.hasNext()) {
                Tuple tuple = tuplesIterator.next();
                categoryDates.add((Date) tuple.getFieldValueByName(categoryField.getName()).getValue());
            }
            return categoryDates;
        }

        /**
         * This should be recomputed
         * @return
         */
        private int computeMinimumScale() {
            long scaleFactor = scale / DateTimeUtils.YEAR;
            if (scaleFactor > 0) {
                return Calendar.YEAR;
            }
            scaleFactor = scale / DateTimeUtils.MONTH;
            if (scaleFactor > 0) {
                return Calendar.MONTH;
            }
//            scaleFactor = scale / WEEKS;
//            if (scaleFactor > 0) {
//                return Calendar.WEEK_OF_MONTH;
//            }
            scaleFactor = scale / DateTimeUtils.DAY;
            if (scaleFactor > 0) {
                return Calendar.DAY_OF_MONTH;
            }
            scaleFactor = scale / DateTimeUtils.HOUR;
            if (scaleFactor > 0) {
                return Calendar.HOUR;
            }
            scaleFactor = scale / DateTimeUtils.MINUTE;
            if (scaleFactor > 0) {
                return Calendar.MINUTE;
            }
            scaleFactor = scale / DateTimeUtils.SECOND;
            if (scaleFactor > 0) {
                return Calendar.SECOND;
            }
            scaleFactor = scale / DateTimeUtils.MILLISECOND;
            if (scaleFactor > 0) {
                return Calendar.MILLISECOND;
            }
            //throw new RuntimeException("Cannot scale below milliseconds");
            return Calendar.MILLISECOND;
        }

        static final String getScaleUnitName(int scaleUnit){
            switch (scaleUnit) {
                case Calendar.YEAR:
                    return "year";
                case Calendar.MONTH:
                    return "month";
                case Calendar.DAY_OF_MONTH:
                    return "day";
                case Calendar.HOUR:
                    return "hour";
                case Calendar.MINUTE:
                    return "minute";
                case Calendar.SECOND:
                    return "second";
                case Calendar.MILLISECOND:
                    return "millisecond";
                default:
                    return "unkown";
            }
        }

        public String toString() {
            StringBuilder buffer = new StringBuilder();
            buffer.append("SeriesInfo[");
            buffer.append("seriesconfig="+seriesConfig);
            buffer.append(",categoryfield="+categoryField.getName());
            buffer.append(",valuefield="+valueField.getName());
            buffer.append(",mindate="+minDate);
            buffer.append(",maxdate="+maxDate);
            buffer.append(",range="+range);
            buffer.append(",scale="+scale);
            buffer.append(",scaleunit="+scaleUnitName);
            buffer.append("]");
            return buffer.toString();
        }
    }

    public static void main(String[] args) {
        TimeAxisGenerator gen = new TimeAxisGenerator();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss:SSS a");
        String dateFormatted = formatter.format(date);
        System.out.println(SeriesInfo.getScaleUnitName(Calendar.MILLISECOND)+" downwards rounding for "+dateFormatted+" is "+formatter.format(gen.roundDate(date, Calendar.MILLISECOND, false)));
        System.out.println(SeriesInfo.getScaleUnitName(Calendar.SECOND)+" downwards rounding for "+dateFormatted+" is "+formatter.format(gen.roundDate(date, Calendar.SECOND, false)));
        System.out.println(SeriesInfo.getScaleUnitName(Calendar.MINUTE)+" downwards rounding for "+dateFormatted+" is "+formatter.format(gen.roundDate(date, Calendar.MINUTE, false)));
        System.out.println(SeriesInfo.getScaleUnitName(Calendar.HOUR)+" downwards rounding for "+dateFormatted+" is "+formatter.format(gen.roundDate(date, Calendar.HOUR, false)));
        System.out.println(SeriesInfo.getScaleUnitName(Calendar.DAY_OF_MONTH)+" downwards rounding for "+dateFormatted+" is "+formatter.format(gen.roundDate(date, Calendar.DAY_OF_MONTH, false)));
        System.out.println(SeriesInfo.getScaleUnitName(Calendar.MONTH)+" downwards rounding for "+dateFormatted+" is "+formatter.format(gen.roundDate(date, Calendar.MONTH, false)));
        System.out.println(SeriesInfo.getScaleUnitName(Calendar.YEAR)+" downwards rounding for "+dateFormatted+" is "+formatter.format(gen.roundDate(date, Calendar.YEAR, false)));

        System.out.println(SeriesInfo.getScaleUnitName(Calendar.MILLISECOND)+" upwards rounding for "+dateFormatted+" is "+formatter.format(gen.roundDate(date, Calendar.MILLISECOND, true)));
        System.out.println(SeriesInfo.getScaleUnitName(Calendar.SECOND)+" upwards rounding for "+dateFormatted+" is "+formatter.format(gen.roundDate(date, Calendar.SECOND,true)));
        System.out.println(SeriesInfo.getScaleUnitName(Calendar.MINUTE)+" upwards rounding for "+dateFormatted+" is "+formatter.format(gen.roundDate(date, Calendar.MINUTE,true)));
        System.out.println(SeriesInfo.getScaleUnitName(Calendar.HOUR)+" upwards rounding for "+dateFormatted+" is "+formatter.format(gen.roundDate(date, Calendar.HOUR,true)));
        System.out.println(SeriesInfo.getScaleUnitName(Calendar.DAY_OF_MONTH)+" upwards rounding for "+dateFormatted+" is "+formatter.format(gen.roundDate(date, Calendar.DAY_OF_MONTH,true)));
        System.out.println(SeriesInfo.getScaleUnitName(Calendar.MONTH)+" upwards rounding for "+dateFormatted+" is "+formatter.format(gen.roundDate(date, Calendar.MONTH,true)));
        System.out.println(SeriesInfo.getScaleUnitName(Calendar.YEAR) + " upwards rounding for " + dateFormatted + " is " + formatter.format(gen.roundDate(date, Calendar.YEAR, true)));
	}
}
