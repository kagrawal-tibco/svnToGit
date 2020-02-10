package com.tibco.rta.model.serialize.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.tibco.cep.bemm.monitoring.util.MonitoringUtils;

/*
 * @author:vasharma
 * 
 */
public class FilterValueTypeResolver extends JsonDeserializer<Object> {


@Override
public Object deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
	
	JsonNode node = p.getCodec().readTree(p);
    String value = node.get("value").asText();
    
    String datatype = node.get("datatype").asText();
    
    Object parsedValue=parseValue(value, datatype);
    
    return parsedValue;
  }


public static Object parseValue(Object value,String type) {
	
	if("DOUBLE".equals(type))
	{	
		if(value instanceof Long)
			value=Double.parseDouble(((Long)value)+"");
		else
			value=Double.parseDouble((value)+"");
	}
	else if("INTEGER".equals(type))
	{
		value=Integer.parseInt(value+"");
	}
	else if("LONG".equals(type))
	{
		value=Long.parseLong(value+"");
	}
	else if("STRING".equals(type))
	{
		value=value+"";
	}
	else if("INTEGER".equals(type))
	{
		value=Integer.parseInt(value+"");
	}
	else if("LONG".equals(type))
	{
		value=Long.parseLong(value+"");
	}
	else if("STRING".equals(type))
	{
		value=value+"";
	}
	else if("BOOLEAN".equals(type))
	{
		value=Boolean.parseBoolean(value+"");
	}
	else if("CHAR".equals(type))
	{
		value=(value+"").charAt(0);
	}
	else if("SHORT".equals(type))
	{
		value=Short.parseShort(value+"");
	}
	else if("BYTE".equals(type))
	{
		value=Byte.parseByte(value+"");
	}
	else if("FLOAT".equals(type))
	{
		value=Float.parseFloat(value+"");
	}

	return value;
}

}