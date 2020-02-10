package com.tibco.cep.dashboard.plugin.beviews.common.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;

class SeriesDataGenerator {
	
	String id;
	
	List<String> categoryValues;
	double minValue;
	double maxValue;
	double difference;
	
	MALSourceElement sourceElement;
	TupleSchema tupleSchema;
	int fieldCount;
	
	MALFieldMetaInfo categoryField;
	
	long timeIncrement;
	
	private List<Tuple> tuples;
	
	SeriesDataGenerator(SeriesDataGenerationConfiguration configuration, long timeIncrement){
		this.id = configuration.getSeriesConfig().getId();
		this.categoryValues = new LinkedList<String>();
		this.timeIncrement = timeIncrement;
		Entity persistedComp = (Entity) configuration.getSeriesConfig().getParent().getParent().getPersistedObject();
		this.sourceElement = configuration.getSourceElement();
		categoryField = configuration.getCategoryField();			
		PropertyMap extendedPropertiesHolder = persistedComp.getExtendedProperties();
		if (extendedPropertiesHolder != null) {
			List<Entity> extendedProperties = extendedPropertiesHolder.getProperties();
			for (Entity extendedProperty : extendedProperties) {
				if (extendedProperty.getName().equals("Category") == true) {
					PropertyMap categoryExtendedPropertiesHolder = extendedProperty.getExtendedProperties();
					if (categoryExtendedPropertiesHolder != null) {
						List<Entity> categoryValueProperties = categoryExtendedPropertiesHolder.getProperties();
						for (Entity categoryValueProperty : categoryValueProperties) {
							categoryValues.add(categoryValueProperty.getName());
						}
					}
				} else if (extendedProperty.getName().equals("MinValue") == true) {
					minValue = Double.parseDouble(((SimpleProperty) extendedProperty).getValue());
				} else if (extendedProperty.getName().equals("MaxValue") == true) {
					maxValue = Double.parseDouble(((SimpleProperty) extendedProperty).getValue());
				}
			}
			difference = maxValue - minValue;
		}
		tupleSchema = TupleSchemaFactory.getInstance().getTupleSchema(sourceElement.getId());
		fieldCount = tupleSchema.getFieldCount();
	}
	
	List<Tuple> generateData(){
		Random random = new Random();
		tuples = new ArrayList<Tuple>(categoryValues.size());
		long time = System.currentTimeMillis();
		for (String categoryValue : categoryValues) {
			Map<String, FieldValue> fieldIdToFieldValueMap = new HashMap<String, FieldValue>();
			for (int i = 0; i < fieldCount; i++) {
				TupleSchemaField schemaField = tupleSchema.getFieldByPosition(i);
				DataType dataType = schemaField.getFieldDataType();
				if (schemaField.getFieldName().equals(categoryField.getName()) == true) {
					fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,dataType.valueOf(categoryValue)));
				}
				else {
					if (dataType.getDataTypeID().equals(BuiltInTypes.BOOLEAN.getDataTypeID()) == true){
						fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,random.nextBoolean()));
					}
					else if (dataType.getDataTypeID().equals(BuiltInTypes.DATETIME.getDataTypeID()) == true){
						fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,new Date(time)));
					}
					else if (dataType.getDataTypeID().equals(BuiltInTypes.DOUBLE.getDataTypeID()) == true){
						fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,minValue + difference * random.nextDouble()));
					}
					else if (dataType.getDataTypeID().equals(BuiltInTypes.FLOAT.getDataTypeID()) == true){
						fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,(float)(minValue + difference * random.nextDouble())));
					}	
					else if (dataType.getDataTypeID().equals(BuiltInTypes.INTEGER.getDataTypeID()) == true){
						fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,(int)(minValue + difference * random.nextDouble())));
					}
					else if (dataType.getDataTypeID().equals(BuiltInTypes.LONG.getDataTypeID()) == true){
						fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,(long)(minValue + difference * random.nextDouble())));
					}
					else if (dataType.getDataTypeID().equals(BuiltInTypes.SHORT.getDataTypeID()) == true){
						fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,(short)(minValue + difference * random.nextDouble())));
					}				
					else if (dataType.getDataTypeID().equals(BuiltInTypes.STRING.getDataTypeID()) == true){
						fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,categoryValue));
					}
					else {
						fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,true));
					}
				}
			}
			tuples.add(new ReusableTuple(tupleSchema,fieldIdToFieldValueMap));
			time = time + timeIncrement;
		}
		return tuples;
	}	
	
	List<Tuple> changeData() {
		Random random = new Random();
		long time = System.currentTimeMillis();
		for (Tuple tuple : tuples) {
			Map<String, FieldValue> fieldIdToFieldValueMap = new HashMap<String, FieldValue>();
			for (int i = 0; i < fieldCount; i++) {
				TupleSchemaField schemaField = tupleSchema.getFieldByPosition(i);
				DataType dataType = schemaField.getFieldDataType();
				if (tupleSchema.getIDField() == schemaField) {
					//re use the id field
					fieldIdToFieldValueMap.put(schemaField.getFieldID(),tuple.getFieldValueByName(schemaField.getFieldName()));
				}
				else if (sourceElement.getField(schemaField.getFieldName()).isGroupBy() == true) {
					//re use the group by values
					fieldIdToFieldValueMap.put(schemaField.getFieldID(),tuple.getFieldValueByName(schemaField.getFieldName()));
				}
				else {
					if (dataType.getDataTypeID().equals(BuiltInTypes.BOOLEAN.getDataTypeID()) == true){
						fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,random.nextBoolean()));
					}
					else if (dataType.getDataTypeID().equals(BuiltInTypes.DATETIME.getDataTypeID()) == true){
						fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,new Date(time)));
					}
					else if (dataType.getDataTypeID().equals(BuiltInTypes.DOUBLE.getDataTypeID()) == true){
						fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,minValue + difference * random.nextDouble()));
					}
					else if (dataType.getDataTypeID().equals(BuiltInTypes.FLOAT.getDataTypeID()) == true){
						fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,(float)(minValue + difference * random.nextDouble())));
					}	
					else if (dataType.getDataTypeID().equals(BuiltInTypes.INTEGER.getDataTypeID()) == true){
						fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,(int)(minValue + difference * random.nextDouble())));
					}
					else if (dataType.getDataTypeID().equals(BuiltInTypes.LONG.getDataTypeID()) == true){
						fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,(long)(minValue + difference * random.nextDouble())));
					}
					else if (dataType.getDataTypeID().equals(BuiltInTypes.SHORT.getDataTypeID()) == true){
						fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,(short)(minValue + difference * random.nextDouble())));
					}				
					else if (dataType.getDataTypeID().equals(BuiltInTypes.STRING.getDataTypeID()) == true){
						fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,tuple.getFieldValueByName(categoryField.getName())));
					}
					else {
						fieldIdToFieldValueMap.put(schemaField.getFieldID(),new FieldValue(dataType,true));
					}
				}
			}
			((ReusableTuple) tuple).update(fieldIdToFieldValueMap);
			time = time + timeIncrement;
		}
		return tuples;
	}
	
	private class ReusableTuple extends Tuple {

		private static final long serialVersionUID = 1092863798555624881L;
		
		ReusableTuple(TupleSchema schema, Map<String, FieldValue> fieldIdToFieldValueMap) {
			super(schema, fieldIdToFieldValueMap);
		}
		
		void update(Map<String, FieldValue> fieldIdToFieldValueMap){
			validateFieldValueMap(fieldIdToFieldValueMap);
			this.fieldIdToFieldValueMap = new HashMap<String, FieldValue>(fieldIdToFieldValueMap);
		}
	}
	
}