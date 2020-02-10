package com.tibco.cep.dashboard.tools.streamer;

import java.util.Date;
import java.util.Random;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.DataType;

class EventConfigInfo {
	
	private String subject;
	
	private String[] fieldNames;
	
	private DataType[] dataTypes;
	
	private ValueGenerator[] generators;
	
	EventConfigInfo(String subject, String subjectConfig) {
		this.subject = subject;
		String[] splits = subjectConfig.split(",");
		fieldNames = new String[splits.length];
		dataTypes = new DataType[splits.length];
		generators = new ValueGenerator[splits.length];
		int i = 0;
		for (String split : splits) {
			String[] fieldInfo = split.split("#");
			fieldNames[i] = fieldInfo[0];
			dataTypes[i] = BuiltInTypes.resolve(fieldInfo[1]);
			generators[i] = new ValueGenerator(dataTypes[i],fieldInfo[2]);
			i++;
		}
	}

	EventInfo createEventInfo() {
		EventInfo eventInfo = new EventInfo();
		eventInfo.subject = subject;
		eventInfo.fieldnames = fieldNames;
		eventInfo.dataTypes = dataTypes;
		eventInfo.values = new String[fieldNames.length];
		for (int i = 0; i < generators.length; i++) {
			eventInfo.values[i] = generators[i].getValue().toString();
		}
		return eventInfo;
	}

	class ValueGenerator {
		
		private Random random;
		
		private DataType dataType;
		
		private String[] values;
		
		private double min;
		
		private double max;
		
		private double diff;
		
		ValueGenerator(DataType dataType,String generatorDefinition){
			this.dataType = dataType;
			random = new Random();
			String[] split = generatorDefinition.split("/");
			if (dataType == BuiltInTypes.DOUBLE){
				min = new Double(split[0]);
				max = new Double(split[1]);
				diff = max - min;
			}
			else if (dataType == BuiltInTypes.FLOAT){
				min = new Double(split[0]);
				max = new Double(split[1]);
				diff = max - min;
			}		
			else if (dataType == BuiltInTypes.INTEGER){
				min = new Double(split[0]);
				max = new Double(split[1]);
				diff = max - min;
			}	
			else if (dataType == BuiltInTypes.LONG){
				min = new Double(split[0]);
				max = new Double(split[1]);
				diff = max - min;
			}	
			else if (dataType == BuiltInTypes.STRING){
				values = split;
			}			
		}
		
		Comparable<?> getValue(){
			if (dataType == BuiltInTypes.DATETIME){
				return new Date();
			}
			if (dataType == BuiltInTypes.BOOLEAN){
				return random.nextBoolean();
			}
			if (dataType == BuiltInTypes.STRING){
				return values[random.nextInt(values.length)];
			}
			if (dataType == BuiltInTypes.DOUBLE){
				return min + diff * random.nextDouble();
			}
			else if (dataType == BuiltInTypes.FLOAT){
				return (float)(min + diff * random.nextFloat());			
			}		
			else if (dataType == BuiltInTypes.INTEGER){
				return (int)(min + random.nextInt((int)diff));			
			}	
			else if (dataType == BuiltInTypes.LONG){
				return (long)(min + random.nextInt((int)diff));				
			}
			return -1;
		}
	}
}
