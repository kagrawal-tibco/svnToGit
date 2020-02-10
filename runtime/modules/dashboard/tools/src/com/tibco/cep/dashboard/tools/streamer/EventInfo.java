package com.tibco.cep.dashboard.tools.streamer;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.DataType;

class EventInfo {
	
	String subject;
	String[] fieldnames;
	DataType[] dataTypes;
	String[] values;
	
	EventInfo() {
	}
	
	//subject:fieldname#datatype#value,fieldname#datatype#value
	EventInfo(String data) throws Exception {
		String[] splits = data.split(":");
		if (splits.length != 2){
			throw new Exception("Incorrect event data");
		}
		subject = splits[0];
		splits = splits[1].split(",");
		if (splits.length == 0){
			throw new Exception("Incorrect event data");
		}
		fieldnames = new String[splits.length];
		dataTypes = new DataType[splits.length];
		values = new String[splits.length];
		int i = 0;
		for (String split : splits) {
			String[] fieldInfo = split.split("#");
			if (fieldInfo.length != 3){
				throw new Exception("Incorrect event data");	
			}
			fieldnames[i] = fieldInfo[0];
			dataTypes[i] = BuiltInTypes.resolve(fieldInfo[1]);
			values[i] = fieldInfo[2];
			i++;
		}
	}
}