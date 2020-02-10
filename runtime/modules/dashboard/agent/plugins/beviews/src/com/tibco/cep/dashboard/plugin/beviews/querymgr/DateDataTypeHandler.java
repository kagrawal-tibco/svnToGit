package com.tibco.cep.dashboard.plugin.beviews.querymgr;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.config.SynchronizedSimpleDateFormat;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryBinaryTerm;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryCondition;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryPredicate;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.studio.dashboard.core.query.BEViewsQueryDateTypeInterpreter;

public class DateDataTypeHandler extends DataTypeHandler {

	private static final String CONDITION_DEFINITION_ANNOTATION = DateDataTypeHandler.class.getName();

	// private static final String DELIMITER = "~~";

	private static final String IS_EQUAL_TO_OP = "Is";
	private static final String IS_IN_THE_LAST_HIGH_LVL_OP = "Is In The Last";
	private static final String IS_IN_THE_HISTORICAL_RANGE_LVL_OP = "Is In The Historical Range";
	private static final String IS_IN_THE_RANGE_HIGH_LVL_OP = "Is In The Range";
	private static final String IS_AFTER_HIGH_LVL_OP = "Is After";
	private static final String IS_BEFORE_HIGH_LVL_OP = "Is Before";
	private static final String IS_TODAY = "Is Today";
	private static final String IS_NULL_HIGH_LVL_OP = "Is Null";
	private static final String IS_NOT_NULL_HIGH_LVL_OP = "Is Not Null";

	private List<String> highLvlOps;

	private Map<String, Integer> scaleToCalendarFieldMap = new HashMap<String, Integer>();

	private SynchronizedSimpleDateFormat DATE_TIME_FORMATTER = new SynchronizedSimpleDateFormat("yyyy-MM-dd hh:mm a");

	public DateDataTypeHandler() {
		super(BuiltInTypes.DATETIME);

		highLvlOps = new ArrayList<String>();
		highLvlOps.add(IS_EQUAL_TO_OP);
		highLvlOps.add(IS_TODAY);
		highLvlOps.add(IS_BEFORE_HIGH_LVL_OP);
		highLvlOps.add(IS_AFTER_HIGH_LVL_OP);
		highLvlOps.add(IS_IN_THE_LAST_HIGH_LVL_OP);
		highLvlOps.add(IS_IN_THE_RANGE_HIGH_LVL_OP);
//		highLvlOps.add(IS_IN_THE_HISTORICAL_RANGE_LVL_OP);
		highLvlOps.add(IS_NULL_HIGH_LVL_OP);
		highLvlOps.add(IS_NOT_NULL_HIGH_LVL_OP);

		scaleToCalendarFieldMap.put("years", new Integer(Calendar.YEAR));
		scaleToCalendarFieldMap.put("months", new Integer(Calendar.MONTH));
		scaleToCalendarFieldMap.put("days", new Integer(Calendar.DAY_OF_MONTH));
		scaleToCalendarFieldMap.put("hours", new Integer(Calendar.HOUR));
		scaleToCalendarFieldMap.put("minutes", new Integer(Calendar.MINUTE));
		scaleToCalendarFieldMap.put("seconds", new Integer(Calendar.SECOND));

	}

	@Override
	public List<String> getHighLevelOperators() {
		return highLvlOps;
	}

	@Override
	public String getDefaultHighLevelOperator() {
		return IS_EQUAL_TO_OP;
	}

	@Override
	public List<Map<String, Object>> getDefaultValues() {
		List<Map<String, Object>> values = new LinkedList<Map<String, Object>>();
		values.add(convertToMap(new Date()));
		return values;
	}

	@Override
	public QueryCondition createQueryCondition(Logger logger, TupleSchema tupleSchema, String fieldName, String selectedHighLevelOperator, String[] values) throws QueryException {
		if (selectedHighLevelOperator.equals(IS_TODAY) == true) {
			Date startOfDay = BEViewsQueryDateTypeInterpreter.convertBindValue("#" + BEViewsQueryDateTypeInterpreter.PRE_DEFINED_BINDS.STARTOFDAY.toString());
			Date endOfDay = BEViewsQueryDateTypeInterpreter.convertBindValue("#" + BEViewsQueryDateTypeInterpreter.PRE_DEFINED_BINDS.ENDOFDAY.toString());
			QueryCondition startOfDayCondition = new QueryPredicate(tupleSchema, fieldName, QueryPredicate.GE, new FieldValue(dataType, startOfDay));
			QueryCondition endOfDayCondition = new QueryPredicate(tupleSchema, fieldName, QueryPredicate.LE, new FieldValue(dataType, endOfDay));
			QueryBinaryTerm condition = new QueryBinaryTerm(startOfDayCondition, QueryBinaryTerm.AND, endOfDayCondition);
			saveIsTodayParams(condition);
			return condition;
		} else if (selectedHighLevelOperator.equals(IS_EQUAL_TO_OP) == true) {
			try {
				Date date = (Date) BuiltInTypes.DATETIME.valueOf(values[0]);// DATE_TIME_FORMATTER.parse(values[0]);
				QueryPredicate condition = new QueryPredicate(tupleSchema, fieldName, QueryPredicate.EQ, new FieldValue(dataType, date));
				saveIsEqualToParams(condition);
				return condition;
			} catch (IllegalArgumentException e) {
				throw new QueryException("could not convert " + values[0] + " to a valid date", e);
			}
		} else if (selectedHighLevelOperator.equals(IS_BEFORE_HIGH_LVL_OP) == true) {
			try {
				Date date = (Date) BuiltInTypes.DATETIME.valueOf(values[0]);// DATE_TIME_FORMATTER.parse(values[0]);
				QueryPredicate condition = new QueryPredicate(tupleSchema, fieldName, QueryPredicate.LE, new FieldValue(dataType, date));
				saveIsBeforeParams(condition);
				return condition;
			} catch (IllegalArgumentException e) {
				throw new QueryException("could not convert " + values[0] + " to a valid date", e);
			}
		} else if (selectedHighLevelOperator.equals(IS_AFTER_HIGH_LVL_OP) == true) {
			try {
				Date date = (Date) BuiltInTypes.DATETIME.valueOf(values[0]);// DATE_TIME_FORMATTER.parse(values[0]);
				QueryPredicate condition = new QueryPredicate(tupleSchema, fieldName, QueryPredicate.GE, new FieldValue(dataType, date));
				saveIsAfterParams(condition);
				return condition;
			} catch (IllegalArgumentException e) {
				throw new QueryException("could not convert " + values[0] + " to a valid date", e);
			}
		} else if (selectedHighLevelOperator.equals(IS_IN_THE_RANGE_HIGH_LVL_OP) == true) {
			String value = values[0];
			try {
				Date lowerThresholdDate = (Date) BuiltInTypes.DATETIME.valueOf(value);// DATE_TIME_FORMATTER.parse(value);
				value = values[1];
				Date upperThresholdDate = (Date) BuiltInTypes.DATETIME.valueOf(value);// DATE_TIME_FORMATTER.parse(value);
				QueryCondition lowerThresholdCondition = new QueryPredicate(tupleSchema, fieldName, QueryPredicate.GE, new FieldValue(dataType, lowerThresholdDate));
				QueryCondition upperThresholdCondition = new QueryPredicate(tupleSchema, fieldName, QueryPredicate.LT, new FieldValue(dataType, upperThresholdDate));
				QueryCondition condition = new QueryBinaryTerm(lowerThresholdCondition, QueryBinaryTerm.AND, upperThresholdCondition);
				saveIsInTheRangeParams(condition);
				return condition;
			} catch (IllegalArgumentException e) {
				throw new QueryException("could not convert " + value + " to a valid date", e);
			}
		} else if (selectedHighLevelOperator.equals(IS_IN_THE_LAST_HIGH_LVL_OP) == true) {
			Calendar calendar = Calendar.getInstance();
			Date date = adjustCalendar(calendar, "-"+values[0],values[1]);
			QueryCondition condition = new QueryPredicate(tupleSchema, fieldName, QueryPredicate.GE, new FieldValue(dataType, date));
			saveIsInTheLastParams(condition, values[0], values[1]);
			return condition;
		} else if (selectedHighLevelOperator.equals(IS_IN_THE_HISTORICAL_RANGE_LVL_OP) == true) {
			String[] startConditionValues = values[0].split("~~");
			// int howMuchBackShouldIGo = Integer.parseInt(startConditionValues[0]);
			// String startScale = startConditionValues[1];
			String[] endConditionValue = values[1].split("~~");
			Calendar calendar = Calendar.getInstance();
			Date startDate = adjustCalendar(calendar, "-" + startConditionValues[0], startConditionValues[1]);
			Date endDate = adjustCalendar(calendar, endConditionValue[0], endConditionValue[1]);
			// String[] endConditionValue = values[1].split("~~");
			// int next = Integer.parseInt(endConditionValue[0]);
			// String endScale = endConditionValue[1];
			QueryCondition startCondition = new QueryPredicate(tupleSchema, fieldName, QueryPredicate.GE, new FieldValue(dataType, startDate));
			QueryCondition endCondition = new QueryPredicate(tupleSchema, fieldName, QueryPredicate.LE, new FieldValue(dataType, endDate));
			QueryBinaryTerm condition = new QueryBinaryTerm(startCondition, QueryBinaryTerm.AND, endCondition);
			saveIsInHistoricalRangeParams(condition, startConditionValues[0], startConditionValues[1], endConditionValue[0], endConditionValue[1]);
			return condition;
			// if (howMuchBackShouldIGo > 0 && next > 0){
			// //first condition
			// Calendar calendar = Calendar.getInstance();
			// calendar.add(scaleToCalendarFieldMap.get(startScale), -howMuchBackShouldIGo);
			// Date startDate = calendar.getTime();
			// QueryCondition startCondition = new QueryPredicate(tupleSchema, fieldName, QueryPredicate.GE, new FieldValue(dataType, startDate));
			// //second condition
			// calendar.add(scaleToCalendarFieldMap.get(endScale), next);
			// Date endDate = calendar.getTime();
			// if (endDate.after(new Date()) == true){
			// logger.log(Level.WARN, "End date ["+endDate+" is in the future...");
			// }
			// QueryCondition endCondition = new QueryPredicate(tupleSchema, fieldName, QueryPredicate.LE, new FieldValue(dataType, endDate));
			// QueryBinaryTerm condition = new QueryBinaryTerm(startCondition,QueryBinaryTerm.AND,endCondition);
			// saveIsInHistoricalRangeParams(condition,howMuchBackShouldIGo, startScale,next, endScale);
			// return condition;
			// }
		}else if (selectedHighLevelOperator.equals(IS_NULL_HIGH_LVL_OP) == true) {
			return new QueryPredicate(tupleSchema, fieldName, QueryPredicate.IS_NULL, new FieldValue(dataType, true));
		}else if (selectedHighLevelOperator.equals(IS_NOT_NULL_HIGH_LVL_OP) == true) {
			return new QueryPredicate(tupleSchema, fieldName, QueryPredicate.IS_NOT_NULL, new FieldValue(dataType, false));
		}

		throw new QueryException("'" + selectedHighLevelOperator + "' is not valid for " + getDataTypeName() + " and arguments " + values[0] + "," + ((values.length > 1) ? values[1] : "null"));
	}

	@Override
	public String getHighLevelOperator(Logger logger, QueryCondition condition) throws QueryException {
		String options[] = getQueryParams(condition);
		if (options != null) {
			return options[0];
		}
		if (condition instanceof QueryPredicate) {
			QueryPredicate predicate = (QueryPredicate) condition;
			if (predicate.getComparison().equals(QueryPredicate.EQ) == true) {
				return IS_EQUAL_TO_OP;
			} else if (predicate.getComparison().equals(QueryPredicate.LE) == true) {
				return IS_BEFORE_HIGH_LVL_OP;
			} else if (predicate.getComparison().equals(QueryPredicate.GE) == true) {
				return IS_AFTER_HIGH_LVL_OP;
			} else if (predicate.getComparison().equals(QueryPredicate.IS_NULL) == true) {
				return IS_NULL_HIGH_LVL_OP;
			} else if (predicate.getComparison().equals(QueryPredicate.IS_NOT_NULL) == true) {
				return IS_NOT_NULL_HIGH_LVL_OP;
			}

			throw new QueryException("Unconvertable comparision in " + condition + " for " + getDataTypeName());
		} else if (condition instanceof QueryBinaryTerm) {
			QueryBinaryTerm bTerm = (QueryBinaryTerm) condition;
			if (bTerm.getOperator().equals(QueryBinaryTerm.OR)) {
				throw new QueryException("OR operator in " + condition + " is not supported for " + getDataTypeName());
			}
			QueryCondition lhscondition = bTerm.getLeftTerm();
			QueryCondition rhscondition = bTerm.getRightTerm();
			if ((lhscondition instanceof QueryPredicate) == false || (rhscondition instanceof QueryPredicate) == false) {
				throw new QueryException("complex conditions " + condition + " are not supported for " + getDataTypeName());
			}
			return IS_IN_THE_RANGE_HIGH_LVL_OP;
		}
		throw new QueryException("cannot extract high level operator from " + condition + " for " + getDataTypeName());
	}

	@Override
	public String getQueryConditionAsString(Logger logger, QueryCondition condition) throws QueryException {
		String options[] = getQueryParams(condition);
		if (options != null) {
			String highLevelOp = options[0];
			if (IS_TODAY.equals(highLevelOp) == true) {
				return IS_TODAY;
			} else if (IS_EQUAL_TO_OP.equals(highLevelOp) == true) {
				FieldValue dateValue = ((QueryPredicate) condition).evalRightArgument();
				return IS_EQUAL_TO_OP + " " + DATE_TIME_FORMATTER.format(dateValue.getValue());
			} else if (IS_AFTER_HIGH_LVL_OP.equals(highLevelOp) == true) {
				FieldValue dateValue = ((QueryPredicate) condition).evalRightArgument();
				return IS_AFTER_HIGH_LVL_OP + " " + DATE_TIME_FORMATTER.format(dateValue.getValue());
			} else if (IS_BEFORE_HIGH_LVL_OP.equals(highLevelOp) == true) {
				FieldValue dateValue = ((QueryPredicate) condition).evalRightArgument();
				return IS_BEFORE_HIGH_LVL_OP + " " + DATE_TIME_FORMATTER.format(dateValue.getValue());
			} else if (IS_IN_THE_LAST_HIGH_LVL_OP.equals(options[0])) {
				return options[0] + " " + options[1] + " " + options[2] + "(s)";
			} else if (IS_IN_THE_HISTORICAL_RANGE_LVL_OP.equals(options[0])) {
				return "Is Starting from last " + options[1] + " " + options[2] + " to next " + options[3] + " " + options[4];
			} else if (IS_IN_THE_RANGE_HIGH_LVL_OP.equals(options[0])) {
				QueryBinaryTerm bTerm = (QueryBinaryTerm) condition;
				if (bTerm.getOperator().equals(QueryBinaryTerm.OR)) {
					throw new QueryException("date with or condition is not supported.");
				}
				QueryCondition lhscondition = bTerm.getLeftTerm();
				QueryCondition rhscondition = bTerm.getRightTerm();
				if ((lhscondition instanceof QueryPredicate) == false || (rhscondition instanceof QueryPredicate) == false) {
					throw new QueryException("date can have an AND condition only between two predicates");
				}
				FieldValue lhsDateValue = ((QueryPredicate) lhscondition).evalRightArgument();
				FieldValue rhsDateValue = ((QueryPredicate) rhscondition).evalRightArgument();
				return "Is Between " + DATE_TIME_FORMATTER.format(lhsDateValue.getValue()) + " and " + DATE_TIME_FORMATTER.format(rhsDateValue.getValue());
			}
		} else {
			if (condition instanceof QueryPredicate) {
				QueryPredicate predicate = (QueryPredicate) condition;
				if (predicate.getComparison().equals(QueryPredicate.EQ) == true) {
					FieldValue dateValue = ((QueryPredicate) condition).evalRightArgument();
					return IS_EQUAL_TO_OP + " " + DATE_TIME_FORMATTER.format(dateValue.getValue());
				} else if (predicate.getComparison().equals(QueryPredicate.LE) == true) {
					FieldValue dateValue = ((QueryPredicate) condition).evalRightArgument();
					return IS_BEFORE_HIGH_LVL_OP + " " + DATE_TIME_FORMATTER.format(dateValue.getValue());
				} else if (predicate.getComparison().equals(QueryPredicate.GE) == true) {
					FieldValue dateValue = ((QueryPredicate) condition).evalRightArgument();
					return IS_AFTER_HIGH_LVL_OP + " " + DATE_TIME_FORMATTER.format(dateValue.getValue());
				} else if (predicate.getComparison().equals(QueryPredicate.IS_NULL) == true) {
					return IS_NULL_HIGH_LVL_OP;
				} else if (predicate.getComparison().equals(QueryPredicate.IS_NOT_NULL) == true) {
					return IS_NOT_NULL_HIGH_LVL_OP;
				}

				throw new QueryException("Unconvertable comparision in " + condition + " for " + getDataTypeName());
			} else if (condition instanceof QueryBinaryTerm) {
				QueryBinaryTerm bTerm = (QueryBinaryTerm) condition;
				if (bTerm.getOperator().equals(QueryBinaryTerm.OR)) {
					throw new QueryException("OR operator in " + condition + " is not supported for " + getDataTypeName());
				}
				QueryCondition lhscondition = bTerm.getLeftTerm();
				QueryCondition rhscondition = bTerm.getRightTerm();
				if ((lhscondition instanceof QueryPredicate) == false || (rhscondition instanceof QueryPredicate) == false) {
					throw new QueryException("complex conditions " + condition + " are not supported for " + getDataTypeName());
				}
				FieldValue lhsDateValue = ((QueryPredicate) lhscondition).evalRightArgument();
				FieldValue rhsDateValue = ((QueryPredicate) rhscondition).evalRightArgument();
				return "Is Between " + DATE_TIME_FORMATTER.format(lhsDateValue) + " and " + DATE_TIME_FORMATTER.format(rhsDateValue);
			}
		}
		throw new QueryException("cannot convert " + condition + " to a string format for " + getDataTypeName());
	}

	@Override
	public List<Map<String, Object>> getValues(Logger logger, QueryCondition condition) throws QueryException {
		List<Map<String, Object>> values = new LinkedList<Map<String, Object>>();
		String options[] = getQueryParams(condition);
		if (options != null) {
			String highLevelOp = options[0];
			if (IS_TODAY.equals(highLevelOp) == true) {
				values.add(convertToMap(BEViewsQueryDateTypeInterpreter.convertBindValue("#" + BEViewsQueryDateTypeInterpreter.PRE_DEFINED_BINDS.TODAY)));
			} else if (IS_EQUAL_TO_OP.equals(highLevelOp) == true) {
				FieldValue dateValue = ((QueryPredicate) condition).evalRightArgument();
				values.add(convertToMap((Date) dateValue.getValue()));
			} else if (IS_AFTER_HIGH_LVL_OP.equals(highLevelOp) == true) {
				FieldValue dateValue = ((QueryPredicate) condition).evalRightArgument();
				values.add(convertToMap((Date) dateValue.getValue()));
			} else if (IS_BEFORE_HIGH_LVL_OP.equals(highLevelOp) == true) {
				FieldValue dateValue = ((QueryPredicate) condition).evalRightArgument();
				values.add(convertToMap((Date) dateValue.getValue()));
			} else if (IS_IN_THE_LAST_HIGH_LVL_OP.equals(highLevelOp)) {
				Calendar calendar = Calendar.getInstance();
				Date date = adjustCalendar(calendar, "-"+options[1],options[2]);
				Map<String, Object> value = convertToMap(date);
				value.put("adjustment", options[1]);
				value.put("scale", options[2]);
				values.add(value);
			} else if (IS_IN_THE_HISTORICAL_RANGE_LVL_OP.equals(options[0])) {
				Calendar calendar = Calendar.getInstance();

				Date startDate = adjustCalendar(calendar, "-" + options[1], options[2]);
				Map<String, Object> startPnt = convertToMap(startDate);
				startPnt.put("adjustment", options[1]);
				startPnt.put("scale", options[2]);

				Date endDate = adjustCalendar(calendar, options[3], options[4]);
				Map<String, Object> endPnt = convertToMap(endDate);
				endPnt.put("adjustment", options[3]);
				endPnt.put("scale", options[4]);

				values.add(startPnt);
				values.add(endPnt);
			} else if (IS_IN_THE_RANGE_HIGH_LVL_OP.equals(highLevelOp)) {
				QueryBinaryTerm bTerm = (QueryBinaryTerm) condition;
				if (bTerm.getOperator().equals(QueryBinaryTerm.OR)) {
					throw new QueryException("OR operator in " + condition + " is not supported for " + getDataTypeName());
				}
				QueryCondition lhscondition = bTerm.getLeftTerm();
				QueryCondition rhscondition = bTerm.getRightTerm();
				if ((lhscondition instanceof QueryPredicate) == false || (rhscondition instanceof QueryPredicate) == false) {
					throw new QueryException("complex conditions " + condition + " are not supported for " + getDataTypeName());
				}
				FieldValue lhsDateValue = ((QueryPredicate) lhscondition).evalRightArgument();
				FieldValue rhsDateValue = ((QueryPredicate) rhscondition).evalRightArgument();
				values.add(convertToMap((Date) lhsDateValue.getValue()));
				values.add(convertToMap((Date) rhsDateValue.getValue()));
			}
		} else {
			if (condition instanceof QueryPredicate) {
				QueryPredicate predicate = (QueryPredicate) condition;
				if (predicate.getComparison().equals(QueryPredicate.EQ) == true) {
					FieldValue dateValue = ((QueryPredicate) condition).evalRightArgument();
					values.add(convertToMap((Date) dateValue.getValue()));
				} else if (predicate.getComparison().equals(QueryPredicate.LE) == true) {
					FieldValue dateValue = ((QueryPredicate) condition).evalRightArgument();
					values.add(convertToMap((Date) dateValue.getValue()));
				} else if (predicate.getComparison().equals(QueryPredicate.GE) == true) {
					FieldValue dateValue = ((QueryPredicate) condition).evalRightArgument();
					values.add(convertToMap((Date) dateValue.getValue()));
				} else {
					throw new QueryException("Unconvertable comparision in " + condition + " for " + getDataTypeName());
				}
			} else if (condition instanceof QueryBinaryTerm) {
				QueryBinaryTerm bTerm = (QueryBinaryTerm) condition;
				if (bTerm.getOperator().equals(QueryBinaryTerm.OR)) {
					throw new QueryException("OR operator in " + condition + " is not supported for " + getDataTypeName());
				}
				QueryCondition lhscondition = bTerm.getLeftTerm();
				QueryCondition rhscondition = bTerm.getRightTerm();
				if ((lhscondition instanceof QueryPredicate) == false || (rhscondition instanceof QueryPredicate) == false) {
					throw new QueryException("complex conditions " + condition + " are not supported for " + getDataTypeName());
				}
				FieldValue lhsDateValue = ((QueryPredicate) lhscondition).evalRightArgument();
				FieldValue rhsDateValue = ((QueryPredicate) rhscondition).evalRightArgument();
				values.add(convertToMap((Date) lhsDateValue.getValue()));
				values.add(convertToMap((Date) rhsDateValue.getValue()));
			}
		}
		return values;
	}

	private Map<String, Object> convertToMap(Date date) {
		Map<String, Object> value = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance();
		//add current year
		value.put("today_year", new Integer(calendar.get(Calendar.YEAR)));
		calendar.setTime(date);
		// year
		value.put("year", new Integer(calendar.get(Calendar.YEAR)));
		// month
		value.put("month", calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
		// day of month
		value.put("dayofmonth", new Integer(calendar.get(Calendar.DAY_OF_MONTH)));
		// hour
		value.put("hour", new Integer(calendar.get(Calendar.HOUR) == 0 ? 12 : calendar.get(Calendar.HOUR)));
		// minute
		value.put("minute", new Integer(calendar.get(Calendar.MINUTE)));
		// seconds
		value.put("second", new Integer(calendar.get(Calendar.SECOND)));
		// msecs
		value.put("msecond", new Integer(calendar.get(Calendar.MILLISECOND)));
		// ampm
		value.put("ampm", calendar.get(Calendar.AM_PM) == Calendar.AM ? "am" : "pm");
		return value;
	}

	private void saveIsTodayParams(QueryCondition condition) {
		String queryParam = IS_TODAY;
		condition.addAnnotation(CONDITION_DEFINITION_ANNOTATION, queryParam);
		// condition.getLeftTerm().addAnnotation(CONDITION_DEFINITION_ANNOTATION, queryParam);
		// condition.getRightTerm().addAnnotation(CONDITION_DEFINITION_ANNOTATION, queryParam);
	}

	private void saveIsEqualToParams(QueryCondition condition) {
		String queryParam = IS_EQUAL_TO_OP;
		condition.addAnnotation(CONDITION_DEFINITION_ANNOTATION, queryParam);
	}

	private void saveIsBeforeParams(QueryCondition condition) {
		String queryParam = IS_BEFORE_HIGH_LVL_OP;
		condition.addAnnotation(CONDITION_DEFINITION_ANNOTATION, queryParam);
	}

	private void saveIsAfterParams(QueryCondition condition) {
		String queryParam = IS_AFTER_HIGH_LVL_OP;
		condition.addAnnotation(CONDITION_DEFINITION_ANNOTATION, queryParam);
	}

	private void saveIsInTheRangeParams(QueryCondition condition) {
		String queryParam = IS_IN_THE_RANGE_HIGH_LVL_OP;
		condition.addAnnotation(CONDITION_DEFINITION_ANNOTATION, queryParam);
	}

	private void saveIsInTheLastParams(QueryCondition condition, String howMuchBackShouldIGo, String scale) {
		String queryParam = IS_IN_THE_LAST_HIGH_LVL_OP + ":" + howMuchBackShouldIGo + ":" + scale;
		condition.addAnnotation(CONDITION_DEFINITION_ANNOTATION, queryParam);
	}

	private void saveIsInHistoricalRangeParams(QueryCondition condition, String howMuchBackShouldIGo, String startScale, String upto, String endScale) {
		String queryParam = IS_IN_THE_HISTORICAL_RANGE_LVL_OP + ":" + howMuchBackShouldIGo + ":" + startScale + ":" + upto + ":" + endScale;
		condition.addAnnotation(CONDITION_DEFINITION_ANNOTATION, queryParam);
		// condition.getLeftTerm().addAnnotation(CONDITION_DEFINITION_ANNOTATION, queryParam);
		// condition.getRightTerm().addAnnotation(CONDITION_DEFINITION_ANNOTATION, queryParam);
	}

	private String[] getQueryParams(QueryCondition condition) {
		String savedParam = condition.getAnnotation(CONDITION_DEFINITION_ANNOTATION);
		if (StringUtil.isEmptyOrBlank(savedParam) == false) {
			String[] options = savedParam.split(":");
			return options;
		}
		return null;
	}

	private Date adjustCalendar(Calendar calendar, String adjustment, String scale) throws QueryException {
		try {
			int numericAdjustment = Integer.parseInt(adjustment);
			calendar.add(scaleToCalendarFieldMap.get(scale), numericAdjustment);
			return calendar.getTime();
		} catch (NumberFormatException e) {
			throw new QueryException("could not convert " + adjustment + " to a valid integer", e);
		}
	}

	@Override
	public List<Map<String, Object>> getRawValues(Logger logger,QueryCondition condition) throws QueryException {
		List<Map<String, Object>> values = new LinkedList<Map<String, Object>>();
		String options[] = getQueryParams(condition);
		if (options != null) {
			String highLevelOp = options[0];
			if (IS_TODAY.equals(highLevelOp) == true) {
				values.add(convertToRawMap(BEViewsQueryDateTypeInterpreter.convertBindValue("#" + BEViewsQueryDateTypeInterpreter.PRE_DEFINED_BINDS.TODAY)));
			} else if (IS_EQUAL_TO_OP.equals(highLevelOp) == true) {
				FieldValue dateValue = ((QueryPredicate) condition).evalRightArgument();
				values.add(convertToRawMap((Date) dateValue.getValue()));
			} else if (IS_AFTER_HIGH_LVL_OP.equals(highLevelOp) == true) {
				FieldValue dateValue = ((QueryPredicate) condition).evalRightArgument();
				values.add(convertToRawMap((Date) dateValue.getValue()));
			} else if (IS_BEFORE_HIGH_LVL_OP.equals(highLevelOp) == true) {
				FieldValue dateValue = ((QueryPredicate) condition).evalRightArgument();
				values.add(convertToRawMap((Date) dateValue.getValue()));
			} else if (IS_IN_THE_LAST_HIGH_LVL_OP.equals(highLevelOp)) {
				//Calendar calendar = Calendar.getInstance();
				//Date date = adjustCalendar(calendar, "-"+options[1],options[2]);
				//Map<String, Object> value = convertToRawMap(date);
				//value.put("adjustment", options[1]);
				//value.put("scale", options[2]);
				//values.add(value);
				// directly add scale adjustment and scale
				Map<String, Object> adjustmentMap = new HashMap<String, Object>();
				Map<String, Object> scaleMap = new HashMap<String, Object>();
				adjustmentMap.put("value",options[1]);
				scaleMap.put("value", options[2]);
				values.add(adjustmentMap);
				values.add(scaleMap);
			}
			if (IS_IN_THE_RANGE_HIGH_LVL_OP.equals(highLevelOp)) {
				QueryBinaryTerm bTerm = (QueryBinaryTerm) condition;
				if (bTerm.getOperator().equals(QueryBinaryTerm.OR)) {
					throw new QueryException("OR operator in " + condition + " is not supported for " + getDataTypeName());
				}
				QueryCondition lhscondition = bTerm.getLeftTerm();
				QueryCondition rhscondition = bTerm.getRightTerm();
				if ((lhscondition instanceof QueryPredicate) == false || (rhscondition instanceof QueryPredicate) == false) {
					throw new QueryException("complex conditions " + condition + " are not supported for " + getDataTypeName());
				}
				FieldValue lhsDateValue = ((QueryPredicate) lhscondition).evalRightArgument();
				FieldValue rhsDateValue = ((QueryPredicate) rhscondition).evalRightArgument();

				values.add(convertToRawMap((Date) lhsDateValue.getValue()));
				values.add(convertToRawMap((Date) rhsDateValue.getValue()));

				return values;
			}
		}else {
			if (condition instanceof QueryPredicate) {
				QueryPredicate predicate = (QueryPredicate) condition;
				if (predicate.getComparison().equals(QueryPredicate.EQ) == true) {
					FieldValue dateValue = ((QueryPredicate) condition).evalRightArgument();
					values.add(convertToRawMap((Date) dateValue.getValue()));
				} else if (predicate.getComparison().equals(QueryPredicate.LE) == true) {
					FieldValue dateValue = ((QueryPredicate) condition).evalRightArgument();
					values.add(convertToRawMap((Date) dateValue.getValue()));
				} else if (predicate.getComparison().equals(QueryPredicate.GE) == true) {
					FieldValue dateValue = ((QueryPredicate) condition).evalRightArgument();
					values.add(convertToRawMap((Date) dateValue.getValue()));
				} else {
					throw new QueryException("Unconvertable comparision in " + condition + " for " + getDataTypeName());
				}
			} else if (condition instanceof QueryBinaryTerm) {
				QueryBinaryTerm bTerm = (QueryBinaryTerm) condition;
				if (bTerm.getOperator().equals(QueryBinaryTerm.OR)) {
					throw new QueryException("OR operator in " + condition + " is not supported for " + getDataTypeName());
				}
				QueryCondition lhscondition = bTerm.getLeftTerm();
				QueryCondition rhscondition = bTerm.getRightTerm();
				if ((lhscondition instanceof QueryPredicate) == false || (rhscondition instanceof QueryPredicate) == false) {
					throw new QueryException("complex conditions " + condition + " are not supported for " + getDataTypeName());
				}
				FieldValue lhsDateValue = ((QueryPredicate) lhscondition).evalRightArgument();
				FieldValue rhsDateValue = ((QueryPredicate) rhscondition).evalRightArgument();
				values.add(convertToRawMap((Date) lhsDateValue.getValue()));
				values.add(convertToRawMap((Date) rhsDateValue.getValue()));
			}
		}
		return values;
	}

	private Map<String, Object> convertToRawMap(Date date) {
		Map <String, Object>valueMap = new HashMap<String, Object>();
		valueMap.put("value",String.valueOf(date.getTime()));
		return valueMap;
	}

	@Override
	public List<Map<String, Object>> getDefaultRawValues() {
		List<Map<String, Object>> values = new LinkedList<Map<String, Object>>();
		values.add(convertToRawMap(new Date()));
		return values;
	}
}