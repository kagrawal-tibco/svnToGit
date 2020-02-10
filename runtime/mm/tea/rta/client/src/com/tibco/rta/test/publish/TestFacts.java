package com.tibco.rta.test.publish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.tibco.rta.Fact;
import com.tibco.rta.RtaSession;
import com.tibco.rta.model.DataType;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.impl.AttributeImpl;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.property.impl.PropertyAtomBoolean;
import com.tibco.rta.property.impl.PropertyAtomInt;
import com.tibco.rta.property.impl.PropertyAtomLong;
import com.tibco.rta.property.impl.PropertyAtomString;
import com.tibco.rta.test.TestConstants;
import com.tibco.rta.test.TestUtilities;

public class TestFacts {
	
	public static Map<String, DataType> getSchemaAttrDataTypeMap(RtaSession session) throws Exception{
		RtaSchema schema = session.getSchema(TestConstants.SCHEMA_NAME);
		Map<String, DataType> map = new HashMap<String, DataType>();
		
		ArrayList<AttributeImpl> attList = (ArrayList)schema.getAttributes();
		for(AttributeImpl attr : attList)
		{
			map.put(attr.getName(), attr.getDataType());
		}
		
		return map;
	}
	
	public static Object getOjectOnDataType(DataType dataType, String value) {
        switch (dataType) {
            case BOOLEAN:
                return Boolean.parseBoolean(value);
            case STRING:
                return value;
            case INTEGER:
                return Integer.parseInt(value);
            case LONG:
                return Long.parseLong(value);
            case DOUBLE:
                return Double.parseDouble(value);
            default:
                return null;
        }
    }
	
	public static Fact getFactInstance(RtaSession session) throws Exception{
		RtaSchema schema = session.getSchema(TestConstants.SCHEMA_NAME);
		Fact fact = schema.createFact();
		return fact;
	}
	
	public static void insertAttToFact(Fact fact, String attrName, Object value) throws Exception{
		fact.setAttribute(attrName, value);
	}
	public static void publishFact(RtaSession session, Fact fact) throws Exception{
		session.publishFact(fact);
	}
	
	public static void publishFactFromCsv(RtaSession session, String filePath) throws Exception
	{
		Map<String, DataType> attDataType = getSchemaAttrDataTypeMap(session);
		List<String> lines = TestUtilities.readCsv(filePath);
		
		boolean firstLine = true;
		String[] headers = null;
		Fact fact = null;
		
		for(String line : lines)
		{
			String[] array = line.split(",");
			if(firstLine){
				firstLine = false;
				headers = array;
			}
			else{
				fact = getFactInstance(session);
				for(int i = 0; i<headers.length; i++)
				{
					if(i == array.length)
					{
						break;
					}
					if((array[i]!=null) && (!array[i].equals(""))){
						insertAttToFact(fact, headers[i], getOjectOnDataType(attDataType.get(headers[i]), array[i]));
					}
				}
				publishFact(session, fact);
				
			}
		}
	}
		
	
	public static void testClusterHealthFact(RtaSession session, int count) throws Exception 
	{
		Fact fact = getFactInstance(session);
		
		insertAttToFact(fact, TestConstants.ATTRIB_APPLICATION, "FraudDetection");
		insertAttToFact(fact, TestConstants.ATTRIB_PU_NAME, "default");
		insertAttToFact(fact, TestConstants.ATTRIB_INSTANCE_NAME, "InferenceAgent");
		insertAttToFact(fact, TestConstants.ATTRIB_AGENT_TYPE, "inference");
		insertAttToFact(fact, TestConstants.ATTRIB_AGENT_NAME, "inference-class");
       
		insertAttToFact(fact, TestConstants.ATTRIB_CPU_USEAGE, 0.001034172);
		insertAttToFact(fact, TestConstants.ATTRIB_CPU_COUNT, 4); 					
		insertAttToFact(fact, TestConstants.ATTRIB_CPU_TIME, 98843750000L); 					
		insertAttToFact(fact, TestConstants.ATTRIB_INIT_MEM_SIZE, 1073741824); 				
		insertAttToFact(fact, TestConstants.ATTRIB_USED_MEM_SIZE, 54248344); 				
		insertAttToFact(fact, TestConstants.ATTRIB_COMMITTED_MEM_SIZE, 1038876672); 
		insertAttToFact(fact, TestConstants.ATTRIB_MAX_MEM_SIZE, 1038876672); 				
		insertAttToFact(fact, TestConstants.ATTRIB_THREAD_COUNT, 52); 				
		insertAttToFact(fact, TestConstants.ATTRIB_DEADLOCKED_THREAD_COUNT, 0); 
		insertAttToFact(fact, TestConstants.ATTRIB_JVM_UP_TIME, 60052070); 
		insertAttToFact(fact, TestConstants.ATTRIB_TOTAL_SUCCESS_TRANSACTION, 0); 	
		insertAttToFact(fact, TestConstants.ATTRIB_TOTAL_SUCCESS_DB_TRANSACTION, 0);
		insertAttToFact(fact, TestConstants.ATTRIB_TOTAL_SUCCESS_DB_TRANSACTION, 0);
       // insertAttToFact(fact, TestConstants.ATTRIB_NO_OF_RULES_FIRED, value); 
        
        publishFact(session, fact);
        
        
	}
	
	
	public static void testInstanceHealthFact(RtaSession session, int count) throws Exception 
	{
		Thread.sleep(2000);
		System.out.println("Publishing Cluster Health facts");
		Fact fact = getFactInstance(session);
		
		insertAttToFact(fact, TestConstants.ATTRIB_APPLICATION, "FraudDetection1");
        insertAttToFact(fact, TestConstants.ATTRIB_PU_NAME, "default");
        insertAttToFact(fact, TestConstants.ATTRIB_INSTANCE_NAME, "InferenceAgent");
        insertAttToFact(fact, TestConstants.ATTRIB_AGENT_TYPE, "inference");
        insertAttToFact(fact, TestConstants.ATTRIB_AGENT_NAME, "inference-class1");
       
        //insertAttToFact(fact, TestConstants.ATTRIB_INSTANCE_HEALTH, value);
		//insertAttToFact(fact, TestConstants.ATTRIB_APP_HEALTH, value);
		//insertAttToFact(fact, TestConstants.ATTRIB_AGENT_HEALTH, value);
		insertAttToFact(fact, TestConstants.ATTRIB_INSTANCE_COUNT, 1);
		insertAttToFact(fact, TestConstants.ATTRIB_AGENT_COUNT, 1);
		insertAttToFact(fact, TestConstants.ATTRIB_INSTANCE_ACTIVE, 1);
		insertAttToFact(fact, TestConstants.ATTRIB_INSTANCE_HEALTH, "ok");
		insertAttToFact(fact, TestConstants.ATTRIB_AGENT_HEALTH, "ok");
		insertAttToFact(fact, TestConstants.ATTRIB_CPU_USEAGE, 10.87);
        
		 publishFact(session, fact);
        
	}
	
	public static void testInstanceHealthFact1(RtaSession session, int count)throws Exception 
	{
		Thread.sleep(2000);
		System.out.println("Publishing Cluster Health facts11");
		Fact fact = getFactInstance(session);
		
		insertAttToFact(fact, TestConstants.ATTRIB_APPLICATION, "FraudDetection1");
        insertAttToFact(fact, TestConstants.ATTRIB_PU_NAME, "default");
        insertAttToFact(fact, TestConstants.ATTRIB_INSTANCE_NAME, "InferenceAgent");
        insertAttToFact(fact, TestConstants.ATTRIB_AGENT_TYPE, "inference");
        insertAttToFact(fact, TestConstants.ATTRIB_AGENT_NAME, "inference-class2");
       
        //insertAttToFact(fact, TestConstants.ATTRIB_INSTANCE_HEALTH, value);
		//insertAttToFact(fact, TestConstants.ATTRIB_APP_HEALTH, value);
		//insertAttToFact(fact, TestConstants.ATTRIB_AGENT_HEALTH, value);
		insertAttToFact(fact, TestConstants.ATTRIB_INSTANCE_COUNT, 1);
		insertAttToFact(fact, TestConstants.ATTRIB_AGENT_COUNT, 1);
		insertAttToFact(fact, TestConstants.ATTRIB_INSTANCE_ACTIVE, 0);
		insertAttToFact(fact, TestConstants.ATTRIB_INSTANCE_HEALTH, "ok");
		insertAttToFact(fact, TestConstants.ATTRIB_AGENT_HEALTH, "ok");
		insertAttToFact(fact, TestConstants.ATTRIB_CPU_USEAGE, 10.87);
        
		 publishFact(session, fact);
        
	}
	
	public static void healthMetricHierFact(RtaSession session) throws Exception{
		System.out.println("Publishing healthMetricHierFact");
		RtaSchema schema = session.getSchema(TestConstants.SCHEMA_NAME);
		 Fact fact = schema.createFact();
		 insertAttToFact(fact, TestConstants.ATTRIB_APPLICATION, "FraudDetection1");
	        insertAttToFact(fact, TestConstants.ATTRIB_PU_NAME, "default");
	        insertAttToFact(fact, TestConstants.ATTRIB_INSTANCE_NAME, "InferenceAgent");
	        insertAttToFact(fact, TestConstants.ATTRIB_AGENT_TYPE, "inference");
	        insertAttToFact(fact, TestConstants.ATTRIB_AGENT_NAME, "inference-class2");
	        insertAttToFact(fact, TestConstants.ATTRIB_AGENT_HEALTH, "critical");
	        publishFact(session, fact);
		 Thread.sleep(1000);
	}
	
	 public void testfact(RtaSession session, int count) throws Exception {
	        RtaSchema schema = session.getSchema(TestConstants.SCHEMA_NAME);
	        for (int i = 0; i < count; i++) {
	            Fact fact = schema.createFact();

	            insertAttToFact(fact, TestConstants.ATTRIB_APPLICATION, "app1");
	            insertAttToFact(fact, TestConstants.ATTRIB_PU_NAME, "pu1");
	            insertAttToFact(fact, TestConstants.ATTRIB_INSTANCE_NAME, "ins1");
	            insertAttToFact(fact, TestConstants.ATTRIB_AGENT_TYPE, "inference");
	            insertAttToFact(fact, TestConstants.ATTRIB_AGENT_NAME, "inference_1");
	            
	            /*insertAttToFact(fact, TestConstants.ATTRIB_SPACE, value);
	            insertAttToFact(fact, TestConstants.ATTRIB_HOST, value); 						
	            insertAttToFact(fact, TestConstants.ATTRIB_INSTANCE_COUNT, value); 				
	            insertAttToFact(fact, TestConstants.ATTRIB_AGENT_COUNT, value); 				
	            insertAttToFact(fact, TestConstants.ATTRIB_ENGINE_COUNT, value); 				
	            insertAttToFact(fact, TestConstants.ATTRIB_INSTANCE_ACTIVE, value); 			
	            insertAttToFact(fact, TestConstants.ATTRIB_HOST_IS_PREDEFINED, value);			
	            insertAttToFact(fact, TestConstants.ATTRIB_INSTANCE_IS_PREDEFINED, value); 		
	            insertAttToFact(fact, TestConstants.ATTRIB_TOTAL_INFERENCE_AGENT, value); 		
	            insertAttToFact(fact, TestConstants.ATTRIB_TIMESTAMP, value); 					
	            insertAttToFact(fact, TestConstants.ATTRIB_JVM_UP_TIME, value); 				
	            insertAttToFact(fact, TestConstants.ATTRIB_CPU_COUNT, value); 					
	            insertAttToFact(fact, TestConstants.ATTRIB_CPU_TIME, value); 					
	            insertAttToFact(fact, TestConstants.ATTRIB_CPU_USEAGE, value); 					
	            insertAttToFact(fact, TestConstants.ATTRIB_INIT_MEM_SIZE, value); 				
	            insertAttToFact(fact, TestConstants.ATTRIB_USED_MEM_SIZE, value); 				
	            insertAttToFact(fact, TestConstants.ATTRIB_COMMITTED_MEM_SIZE, value); 			
	            insertAttToFact(fact, TestConstants.ATTRIB_MAXT_MEM_SIZE, value); 				
	            insertAttToFact(fact, TestConstants.ATTRIB_THREAD_COUNT, value); 				
	            insertAttToFact(fact, TestConstants.ATTRIB_DEADLOCKED_THREAD_COUNT, value); 	
	            insertAttToFact(fact, TestConstants.ATTRIB_TOTAL_SUCCESS_TRANSACTION, value); 	
	            insertAttToFact(fact, TestConstants.ATTRIB_TOTAL_SUCCESS_DB_TRANSACTION, value);
	            insertAttToFact(fact, TestConstants.ATTRIB_PUT_COUNT, value); 					
	            insertAttToFact(fact, TestConstants.ATTRIB_NO_OF_RULES_FIRED, value); 			
	            insertAttToFact(fact, TestConstants.ATTRIB_CHANNEL_URI, value); 				
	            insertAttToFact(fact, TestConstants.ATTRIB_NO_EVENTS_RECEIVED, value); 			
	            insertAttToFact(fact, TestConstants.ATTRIB_NO_EVENTS_SENT, value); 				
	            insertAttToFact(fact, TestConstants.ATTRIB_DEST_URI, value); 					


	            insertAttToFact(fact, TestConstants.ATTRIB_APP_HEALTH, value);			 		
	            insertAttToFact(fact, TestConstants.ATTRIB_INSTANCE_HEALTH, value);			 	
	            insertAttToFact(fact, TestConstants.ATTRIB_AGENT_HEALTH, value);			 	
	            insertAttToFact(fact, TestConstants.ATTRIB_OK_COUNT, value);			 		
	            insertAttToFact(fact, TestConstants.ATTRIB_WARNING_COUNT, value);			 	
	            insertAttToFact(fact, TestConstants.ATTRIB_CRITICAL_COUNT, value);	*/		 	

	            

	            publishFact(session, fact);
//	            Thread.sleep(1000);
	        }
	        Thread.sleep(1000);
	    }

}
