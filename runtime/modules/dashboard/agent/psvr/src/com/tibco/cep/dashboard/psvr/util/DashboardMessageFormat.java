package com.tibco.cep.dashboard.psvr.util;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.common.data.TupleSchemaSource;
import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;


public class DashboardMessageFormat {

	private static final Logger LOGGER = LoggingService.getChildLogger(LoggingService.getUtilLogger(), "messageformat");

	private static final Pattern MESSAGE_ARG_SPLITTER = Pattern.compile(",");

	private static final Pattern ARGS_AS_FIELDNAME_CHECKER = Pattern.compile("\\{\\D\\p{Graph}*?\\}");

	private static final int[] EMPTY_INT_ARRAY = new int[0];

	private String pattern;

	private boolean patternHasNoArgs;

	public DashboardMessageFormat(String pattern) {
		this.pattern = pattern;
		this.patternHasNoArgs = (this.pattern.indexOf("{") == -1);
	}

	public String format(Tuple tuple, Object[] replacementValues, SimpleDateFormat defaultDateFormat) {
		if (patternHasNoArgs == true) {
			return pattern;
		}
		// replace
		String newPattern = replaceNameWithIndex(pattern, tuple.getSchema());
		FieldValue[] fieldValues = getOutputFieldValues(tuple);
		boolean replacementsAreValid = replacementValues != null && replacementValues.length == fieldValues.length;
		newPattern = getNULLValueCompensatedPattern(newPattern, fieldValues, replacementsAreValid ? replacementValues : null);
		newPattern = getDateCompensatedPattern(newPattern, fieldValues, defaultDateFormat);
		Object[] messageArgs = new Object[fieldValues.length];
		for (int i = 0; i < messageArgs.length; i++) {
			if (replacementsAreValid == true && replacementValues[i] != null) {
				messageArgs[i] = replacementValues[i];
			} else {
				messageArgs[i] = fieldValues[i].getValue();
			}
		}
		return MessageFormat.format(newPattern, messageArgs);
	}

	public String format(Tuple tuple, SimpleDateFormat defaultDateFormat) {
		return format(tuple, null, defaultDateFormat);
	}

	private String replaceNameWithIndex(String pattern,TupleSchema tupleSchema){
		int fieldCnt = tupleSchema.getFieldCount();
		for (int i = 0; i < fieldCnt; i++) {
			TupleSchemaField field = tupleSchema.getFieldByPosition(i);
			String name = field.getFieldName();
			pattern = pattern.replace("{"+name+"}", "{"+i+"}");
			pattern = pattern.replace("{"+name+",", "{"+i+",");
		}
		if (ARGS_AS_FIELDNAME_CHECKER.matcher(pattern).find() == true){
			throw new IllegalArgumentException("["+pattern+"] contains argument names not accessible in supplied tuple");
		}
		return pattern;
	}

	private FieldValue[] getOutputFieldValues(Tuple tuple) {
		int fieldCnt = tuple.getFieldCount();
		FieldValue[] values = new FieldValue[fieldCnt];
		for (int i = 0; i < fieldCnt; i++) {
			values[i] = tuple.getFieldValueByPosition(i);
		}
		return values;
	}

	private String getNULLValueCompensatedPattern(String pattern, FieldValue[] values, Object[] replacementValues) {
		String compensatedDisplayFormat = pattern;
		for (int i = 0; i < values.length; i++) {
			FieldValue value = values[i];
			// we have a null value, let search for it in the message format
			if (value == null || value.isNull() == true) {
				String argSearchPattern = "\\{" + i + "\\D*?\\}";
				String replacementPattern = "{" + i + "}";
				if (replacementValues != null && replacementValues[i] != null){
					replacementPattern = replacementValues[i].toString();
				}
				compensatedDisplayFormat = compensatedDisplayFormat.replaceAll(argSearchPattern, replacementPattern);
			}
		}
		if (compensatedDisplayFormat != pattern) {
			if (LOGGER.isEnabledFor(Level.DEBUG) == true) {
				LOGGER.log(Level.DEBUG, "Compensated " + pattern + " to " + compensatedDisplayFormat + " based on " + Arrays.asList(values).toString());
			}
			return compensatedDisplayFormat;
		}
		return pattern;
	}

	private String getDateCompensatedPattern(String pattern, FieldValue[] values, SimpleDateFormat defaultDateFormat) {
		if (patternHasNoArgs == true) {
			// no '{' which means the pattern has no arguments , it is a simple
			// text , just return
			return pattern;
		}
		// find out if any of the arguments in the pattern are date type
		int[] dateTypeFieldIndexes = getIndexesForDateTypeFields(values);
		if (dateTypeFieldIndexes.length == 0) {
			// no date types used in the pattern, return the pattern
			return pattern;
		}

		String massagedPattern = pattern;

		StringBuffer builder = new StringBuffer();

		for (int i = 0; i < dateTypeFieldIndexes.length; i++) {
			int dateTypeFldIdx = dateTypeFieldIndexes[i];
			// creating a reg-ex to find all arguments matching the date argument in question
			Pattern dateTypeFldArgPattern = Pattern.compile("\\{" + dateTypeFldIdx + "\\D*?\\}");
			Matcher dateTypeFldArgMatcher = dateTypeFldArgPattern.matcher(massagedPattern);
			boolean found = dateTypeFldArgMatcher.find();
			if (found == true) {
				// iterate through all the found matches
				while (found) {
					DateFormat dateFormat = null;
					// extract the actual argument something like "{...}"
					String actualArgument = massagedPattern.substring(dateTypeFldArgMatcher.start(), dateTypeFldArgMatcher.end());
					// split the argument
					String[] argPieces = MESSAGE_ARG_SPLITTER.split(actualArgument, 3);
					// validity check
					if (argPieces.length == 0 || argPieces.length > 3) {
						throw new IllegalArgumentException(actualArgument + " in " + pattern + " is invalid");
					}
					// we have 3 arguments which means
					// argindex,date(time),subformat
					if (argPieces.length == 3) {
						// we convert the real argument to either
						// {0,date,subformat} or {0,time,subformat}
						if ("time".equalsIgnoreCase(argPieces[1])) {
							actualArgument = "{0,time," + argPieces[2] + "}";
						} else {
							actualArgument = "{0,date," + argPieces[2] + "}";
						}
						MessageFormat generalizedFormat = new MessageFormat(actualArgument);
						dateFormat = (DateFormat) generalizedFormat.getFormatsByArgumentIndex()[0];
						if (defaultDateFormat != null){
							dateFormat.setTimeZone(defaultDateFormat.getTimeZone());
						}
					} else {
						// we don't have sub-format, so we forcefully use the
						// default date formatter or jvm default
						if (defaultDateFormat == null){
							dateFormat = DateFormat.getDateInstance();
						}
						else {
							dateFormat = defaultDateFormat;
						}
					}
					String replacement = dateFormat.format(values[dateTypeFldIdx].getValue());
					// append to buffer
					dateTypeFldArgMatcher.appendReplacement(builder, replacement);
					// get next hit
					found = dateTypeFldArgMatcher.find();
				}
				dateTypeFldArgMatcher.appendTail(builder);
				massagedPattern = builder.toString();
				builder.setLength(0);
			}
		}
		if (LOGGER.isEnabledFor(Level.DEBUG) == true && pattern.equals(massagedPattern) == false) {
			LOGGER.log(Level.DEBUG, "Converted '" + pattern + "' to '" + massagedPattern + "' based on " + Arrays.asList(values).toString());
		}
		return massagedPattern;
	}

	private int[] getIndexesForDateTypeFields(FieldValue[] values) {
		int[] tempIndex = new int[values.length];
		int j = -1;
		for (int i = 0; i < values.length; i++) {
			if (values[i].getDataType().getDataTypeID().equals(BuiltInTypes.DATETIME.getDataTypeID()) == true) {
				j++;
				tempIndex[j] = i;
			}
		}
		if (j == -1) {
			return EMPTY_INT_ARRAY;
		}
		int[] index = new int[j + 1];
		System.arraycopy(tempIndex, 0, index, 0, j + 1);
		return index;
	}

	public static String format(String pattern, Tuple tuple, SimpleDateFormat defaultDateFormat) {
		return new DashboardMessageFormat(pattern).format(tuple, defaultDateFormat);
	}

	public static String format(String pattern, Tuple tuple, Object[] defaultNullValues, SimpleDateFormat defaultDateFormat) {
		return new DashboardMessageFormat(pattern).format(tuple, defaultNullValues, defaultDateFormat);
	}
	
	public static void main(String[] args) {
		DataType[] types = new DataType[]{
				BuiltInTypes.BOOLEAN,
				BuiltInTypes.DATETIME,
				BuiltInTypes.DOUBLE,
				BuiltInTypes.FLOAT,
				BuiltInTypes.INTEGER,
				BuiltInTypes.LONG,
				BuiltInTypes.SHORT,
				BuiltInTypes.STRING
		};
		
		TupleSchema schema = new TupleSchema(new TupleSchemaSource(){

			@Override
			public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
								
			}

			@Override
			public void writeExternal(ObjectOutput out) throws IOException {
								
			}
			
		}, false);
		
		for (int i = 0; i < types.length; i++) {
			schema.addField(i, "Field#"+i, String.valueOf(i), types[i], false, Long.MAX_VALUE);
		}
		Random random = new Random();
		Map<String, FieldValue> fieldIdToFieldValueMap = new HashMap<String, FieldValue>();
		for (int i = 0; i < types.length; i++) {
			TupleSchemaField schemaField = schema.getFieldByPosition(i);
			DataType dataType = schemaField.getFieldDataType();
			if (dataType.getDataTypeID().equals(BuiltInTypes.BOOLEAN.getDataTypeID()) == true){
				fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,random.nextBoolean()));
//				fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,true));
			}
			else if (dataType.getDataTypeID().equals(BuiltInTypes.DATETIME.getDataTypeID()) == true){
				fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,new Date(System.currentTimeMillis())));
//				fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,true));
			}
			else if (dataType.getDataTypeID().equals(BuiltInTypes.DOUBLE.getDataTypeID()) == true){
				fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,random.nextDouble()));
//				fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,true));
			}
			else if (dataType.getDataTypeID().equals(BuiltInTypes.FLOAT.getDataTypeID()) == true){
				fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,random.nextFloat()));
			}	
			else if (dataType.getDataTypeID().equals(BuiltInTypes.INTEGER.getDataTypeID()) == true){
				fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,random.nextInt()));
			}
			else if (dataType.getDataTypeID().equals(BuiltInTypes.LONG.getDataTypeID()) == true){
				fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,random.nextLong()));
			}
			else if (dataType.getDataTypeID().equals(BuiltInTypes.SHORT.getDataTypeID()) == true){
				fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,(short)(random.nextDouble())));
			}				
			else if (dataType.getDataTypeID().equals(BuiltInTypes.STRING.getDataTypeID()) == true){
				//fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,schemaField.getFieldName()));
				fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,true));
			}
			else {
				fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,true));
			}
		}
		Tuple tuple = new Tuple(schema,fieldIdToFieldValueMap);
		//System.out.println(DashboardMessageFormat.format("ABC", tuple, null));
		//System.out.println(DashboardMessageFormat.format("{0}", tuple, null));
		//System.out.println(DashboardMessageFormat.format("{Field#0},{Field#1},{Field#2},{Field#3},{Field#4},{Field#5},{Field#6},{Field#7},{Field8}", tuple, null));
		System.out.println(DashboardMessageFormat.format("{Field#0},{Field#1,time,medium},{Field#2},{Field#3},{Field#4},{Field#5},{Field#6},{Field#7}", tuple, null));
		System.out.println(DashboardMessageFormat.format("{Field#0},{Field#1},{Field#2},{Field#3},{Field#4},{Field#5},{Field#6},{Field#7}", tuple, (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT)));
		System.out.println(DashboardMessageFormat.format("{Field#0},{Field#1},{Field#2},{Field#3},{Field#4},{Field#5},{Field#6},{Field#7}", tuple, new String[]{"null","null","null","null","null","null","null","null"},(SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT)));
		
		//System.out.println(DashboardMessageFormat.format("{Field#10}", tuple, null));
		//System.out.println(DashboardMessageFormat.format("{Field#10}", tuple, null));
		//System.out.println(DashboardMessageFormat.format("{Field#0}", tuple, null));
	}
}
