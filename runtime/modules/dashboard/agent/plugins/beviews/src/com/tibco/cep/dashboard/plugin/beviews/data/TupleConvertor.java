package com.tibco.cep.dashboard.plugin.beviews.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.FieldValueArray;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.plugin.beviews.mal.ReferenceDataType;
import com.tibco.cep.dashboard.psvr.common.FatalException;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;

public class TupleConvertor {

	private static TupleConvertor instance;

	public static final synchronized TupleConvertor getInstance() {
		if (instance == null) {
			instance = new TupleConvertor();
		}
		return instance;
	}

	private TupleConvertor() {

	}

	public Tuple convertToTuple(Concept runtimeConcept) throws FatalException {
		if (runtimeConcept == null){
			throw new IllegalArgumentException();
		}
		ConceptTupleSchema schema = (ConceptTupleSchema) TupleSchemaFactory.getInstance().getTupleSchema(runtimeConcept.getExpandedName());
		return makeTuple(runtimeConcept, schema, new HashMap<String, FieldValue>(schema.getFieldCount()));
	}

	public List<Tuple> convertToTuple(Concept[] runtimeConcepts) throws FatalException{
		if (runtimeConcepts == null){
			throw new IllegalArgumentException();
		}
		ArrayList<Tuple> tuples = new ArrayList<Tuple>(runtimeConcepts.length);
		if (runtimeConcepts.length == 0){
			return tuples;
		}
		ConceptTupleSchema schema = (ConceptTupleSchema) TupleSchemaFactory.getInstance().getTupleSchema(runtimeConcepts[0].getExpandedName());
		HashMap<String, FieldValue> fieldValues = new HashMap<String, FieldValue>(schema.getFieldCount());
		for (Concept runtimeConcept : runtimeConcepts) {
			tuples.add(makeTuple(runtimeConcept, schema, fieldValues));
			fieldValues.clear();
		}
		return tuples;
	}

	@SuppressWarnings("rawtypes")
	private Tuple makeTuple(Concept runtimeConcept, ConceptTupleSchema schema, Map<String, FieldValue> values) throws FatalException {
		for (int i = 0; i < schema.getFieldCount(); i++) {
			TupleSchemaField schemaField = schema.getFieldByPosition(i);
			DataType dataType = schemaField.getFieldDataType();
			FieldValue fieldValue = null;
			try {
				if (schema.getIDField() == schemaField){
					//the field is the id field
					fieldValue = new FieldValue(dataType, runtimeConcept.getId());
				}
				else if (schema.getExtIdField() == schemaField){
					//the field is the extid field
					fieldValue = new FieldValue(dataType, runtimeConcept.getExtId());
				}
				else if (schema.getParentIdField() == schemaField){
					//we are dealing with a parent id field (which means we either are dealing with a contained concept or a referred concept)
					fieldValue = new FieldValueArray(dataType, true);
					if (runtimeConcept instanceof ConceptImpl){
						ConceptImpl runtimeConceptImpl = (ConceptImpl) runtimeConcept;
						//check if the concept is contained
						ConceptOrReference parentReference = runtimeConceptImpl.getParentReference();
						if (parentReference != null){
							//yes it is contained since it's parent reference is not null
							fieldValue = new FieldValueArray(dataType, new Long[]{parentReference.getId()});
						}
						else {
							//no, the parent reference is null, so we are dealing with a referred concept
							//get the reverse references
							long[] reverseReferences = runtimeConceptImpl.getReverseReferences();
							if (reverseReferences != null && reverseReferences.length != 0){
								Long[] parentReferences = new Long[reverseReferences.length];
								for (int j = 0; j < reverseReferences.length; j++) {
									parentReferences[j] = reverseReferences[j];
								}
								fieldValue = new FieldValueArray(dataType, parentReferences);
							}
						}
					}
				}
				else {
					//we are dealing with normal fields, is it a array field
					if (schemaField.isArray() == false) {
						//no, we are dealing with the simplest single value normal field
						//is it set
						boolean fieldIsSet = runtimeConcept.getPropertyAtom(schemaField.getFieldName()).isSet();
						if (fieldIsSet == true){
							//the field has been set, get the actual value (apparently it can still be null)
							Object value = runtimeConcept.getPropertyValue(schemaField.getFieldName());
							if (value != null) {
								//the value is not null, is it date time ?
								if (dataType == BuiltInTypes.DATETIME){
									//yes it is, it can either be a Calendar or Date, get time in msecs from either
									if (value instanceof Calendar){
										value = ((Calendar)value).getTimeInMillis();
									}
									else if (value instanceof Date){
										value = ((Date)value).getTime();
									}
								}
								//is the data type reference data type as in a contained or referred concept
								else if (dataType instanceof ReferenceDataType){
									//yes it is , so we just store the id of the contained or referred concept
									value = ((Entity)value).getId();
								}
								fieldValue = new FieldValue(dataType, dataType.valueOf(String.valueOf(value)));
							}
							else {
								//the field is set , but the value is null
								//assign a data type specific NULL value
								fieldValue = new FieldValue(dataType, true);
							}
						}
						else {
							//no, it is not set assign a data type specific NULL value
							fieldValue = new FieldValue(dataType,true);
						}
					}
					else {
						//we are dealing with array fields
						//get the property meta information
						PropertyArray propertyArray = runtimeConcept.getPropertyArray(schemaField.getFieldName());
						int length = propertyArray.length();
						if (length != 0){
							Comparable[] valueArray = new Comparable[length];
							for (int j = 0; j < length; j++) {
								//get the actual value
								Object value = propertyArray.get(j).getValue();
								if (value != null) {
									//value is not null, is it date time ?
									if (dataType == BuiltInTypes.DATETIME){
										//yes it is, it can either be a Calendar or Date, get time in msecs from either
										if (value instanceof Calendar){
											value = ((Calendar)value).getTimeInMillis();
										}
										else if (value instanceof Date){
											value = ((Date)value).getTime();
										}
									}
									//is the data type reference data type as in a contained or referred concept
									else if (dataType instanceof ReferenceDataType){
										//yes it is , so we just store the id of the contained or referred concept
										value = ((Entity)value).getId();
									}
									valueArray[j] = dataType.valueOf(String.valueOf(value));
								}
								else {
									//the value is null, assign null to that element in the array
									valueArray[j] = null;
								}
							}
							fieldValue = new FieldValueArray(dataType, valueArray);
						}
						else {
							fieldValue = new FieldValueArray(dataType,true);
						}
					}
				}
				values.put(schemaField.getFieldID(), fieldValue);
			} catch (NoSuchFieldException e) {
				throw new FatalException("could not find field "+schemaField.getFieldName()+" in "+runtimeConcept);
			} catch (IllegalArgumentException e) {
				throw new FatalException("could not convert value for field "+schemaField.getFieldName()+" in "+runtimeConcept, e);
			}
		}
		return new Tuple(schema,values);
	}
}