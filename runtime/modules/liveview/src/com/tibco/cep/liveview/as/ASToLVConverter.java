/**
 * 
 */
package com.tibco.cep.liveview.as;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import com.streambase.sb.ByteArrayView;
import com.streambase.sb.Schema;
import com.streambase.sb.Schema.Field;
import com.streambase.sb.Timestamp;
import com.tibco.as.space.DateTime;
import com.tibco.as.space.FieldDef.FieldType;
import com.tibco.as.space.Tuple;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.as.kit.map.SpaceMapConstants;
import com.tibco.cep.as.kit.tuple.DataType;
import com.tibco.cep.as.kit.tuple.DataTypeRefMap;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.model.pojo.exim.EximParent;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoConstants;
import com.tibco.cep.runtime.model.serializers.as.SerializableLiteTupleCodec;
import com.tibco.cep.runtime.service.cluster.system.IDEncoder;
import com.tibco.cep.runtime.service.cluster.system.impl.ObjectTableCache;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASDaoProvider;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * @author vpatil
 *
 */
public class ASToLVConverter {
	
	private static final String BE_CLASS_PREFIX = "be_gen_";
	
	private static String getEntityClass(String spaceName) {
		String[] spaceNameTokens = spaceName.split("--");
		
		//pick out the last one, which is suppose to be the class
		String entityClass = spaceNameTokens[spaceNameTokens.length-1];
		
		return entityClass;
	}
	
	public static String spaceToLVTableName(String spaceName) {
		String entityClass = getEntityClass(spaceName);
		
		// remove any BE class prefixes
		String lvTableName = entityClass.substring(BE_CLASS_PREFIX.length());
		return lvTableName;
	}
	
	public static void spaceToLVTuple(String spaceName, Tuple spaceTuple, com.streambase.sb.Tuple lvTuple) throws Exception {
		Schema schema = lvTuple.getSchema();
		
		// check for explicit tuple = false
		Object asTupleEntity = null;
		if (!isExplictTuple() && spaceTuple.getFieldNames().size() == 2) {
			asTupleEntity = getASTupleCodec().getFromTuple(spaceTuple, SpaceMapConstants.TUPLE_COLUMN_NAME_VALUE, DataType.SerializedBlob);
		}
		
		Field[] fields = schema.getFields();
		for (Field field : fields) {
			updateTuple(lvTuple, fields, field, spaceName, spaceTuple, asTupleEntity);
		}
	}
	
	private static void updateTuple(com.streambase.sb.Tuple lvTuple, Field[] fields, Field field, String spaceName, Tuple spaceTuple, Object asTupleEntity) throws Exception {
		if (null != lvTuple) {
			DataType asFieldType = (asTupleEntity != null) ? DataType.SerializedBlob : getMatchingASFieldType(field.getName(), spaceTuple);
			if (null != asFieldType) {
				Object asTupleValue = (asTupleEntity != null) ? getEntityPropertyValue(asTupleEntity, field.getName()) : getASTupleCodec().getFromTuple(spaceTuple, field.getName(), asFieldType);
				
				if (asTupleValue != null) {
					if (isHistoryType(spaceName, field.getName())) {
						Object[] valueHistory = (Object[])asTupleValue;
						Object latestHistoryValue = valueHistory[valueHistory.length-1];
						if (latestHistoryValue != null && ((Object[])latestHistoryValue).length == 2) {
							asTupleValue = ((Object[])latestHistoryValue)[0];
						}
					}
					com.streambase.sb.DataType lvFieldDataType = field.getDataType();
					switch (lvFieldDataType) {
					case BOOL:
						lvTuple.setBoolean(field, (boolean) asTupleValue);
						break;
					case DOUBLE:
						lvTuple.setDouble(field, (double) asTupleValue);
						break;
					case INT:
						lvTuple.setInt(field, (int) asTupleValue);
						break;
					case LONG:
						lvTuple.setLong(field, (long) asTupleValue);
						break;
					case TIMESTAMP:
						Calendar cal = null;
						if (asTupleValue instanceof DateTime) {
							cal = ((DateTime) asTupleValue).getTime();
						} else if (asTupleValue instanceof Calendar) {
							cal = (Calendar) asTupleValue;
						}
						if (cal != null) {
							lvTuple.setTimestamp(field, getCalendarToTimeStamp(cal, spaceTuple, field.getName()));
						}
						break;
					case STRING: // primitive/complex arrays, rrf & plain strings
						String tupleValue = null;
						if (field.getName().equals(PortablePojoConstants.PROPERTY_CONCEPT_NAME_REV_REFERENCES)) {
							tupleValue = getReferenceTableString(spaceName, field.getName(), asTupleValue);
						} else {
							// array related work, covers primitive/contained/reference arrays
							Result result = arrayToString(spaceName, field.getName(), asTupleValue);
							if (result != null && result.isArray) {
								tupleValue = result.getValue();
								//						} else if (result != null && (result.getType() == RDFTypes.CONCEPT_TYPEID || result.getType() == RDFTypes.CONCEPT_REFERENCE_TYPEID)) {
								//							// case for contained/reference concept
								//							if (result.getValue() != null && !result.getValue().isEmpty()) {
								//								tupleValue = result.getValue() + ":" + asTupleValue;
								//							}
							} else {
								// final case for simple string
								tupleValue = (String) asTupleValue;
							}
						}

						if (tupleValue != null) lvTuple.setString(field, tupleValue);

						break;
					case BLOB:
						lvTuple.setBlobBuffer(field, ByteArrayView.makeView((byte[]) asTupleValue));
					default:
						break;
					}
				}
			}
		}
	}
	
	private static Timestamp getCalendarToTimeStamp(Calendar cal, Tuple spaceTuple, String fieldName) {
		cal.getTimeInMillis();

		if (EximParent.separateTimezone) {
			String tzId = (String)getASTupleCodec().getFromTuple(spaceTuple, EximParent.timeZoneName(fieldName), DataType.String);
			if (tzId != null) {
				TimeZone tz = TimeZone.getTimeZone(tzId);
				if (tz != null) {
					cal.setTimeZone(tz);
				}
			}
		}
		return new Timestamp(cal.getTime());
	}
	
	private static DataType getMatchingASFieldType(String lvTableFieldName, Tuple spaceTuple) {
		if (lvTableFieldName != null && !lvTableFieldName.isEmpty()) {			
			for (String fieldName : spaceTuple.getFieldNames()) {
				if (lvTableFieldName.equals(fieldName)) {
					if (spaceTuple.getOrDefault(fieldName, null) != null) {
						FieldType fieldType = spaceTuple.getFieldType(fieldName);
						return DataTypeRefMap.mapToDataType(fieldType);
					}
					break;
				}
			}
		}
		return null;
	}
	
	private static SerializableLiteTupleCodec getASTupleCodec() {
		DaoProvider daoProvider = getDAOProvider();
		if (null != daoProvider && daoProvider instanceof ASDaoProvider) {
			return ((ASDaoProvider)daoProvider).getTupleCodec();
		}
		return null;
	}
	
	private static DaoProvider getDAOProvider() {
		RuleServiceProvider RSP = RuleServiceProviderManager.getInstance().getDefaultProvider();
		if (null != RSP) {
			return RSP.getCluster().getDaoProvider();
		}
		return null;
	}

	private static String getReferenceTableString(String spaceName, String fieldName, Object asTupleValue) throws Exception {
		String referenceLVTableContent = null;

		if (fieldName.equals(PortablePojoConstants.PROPERTY_CONCEPT_NAME_PARENT)) {
			com.tibco.cep.designtime.model.element.Concept cept = getEntityBySpaceName(spaceName);
			if (cept != null) referenceLVTableContent = entityUriToLVTable(cept.getParentConcept().getFullPath()) + ":" + asTupleValue;
			
		} else if (fieldName.equals(PortablePojoConstants.PROPERTY_CONCEPT_NAME_REV_REFERENCES)) {
			if (asTupleValue.getClass().isArray()) {
				StringBuffer rrfBuffer = new StringBuffer();
				Object[] asTupleArray = (Object[]) asTupleValue;
				for (int i=0; i < asTupleArray.length; i+=2) {
					String lvReferenceTable = getIdToLvTable((Long) asTupleArray[i]);
					if (lvReferenceTable != null && !lvReferenceTable.isEmpty()) {
						lvReferenceTable += "@" + (String) asTupleArray[i+1];
					}
					rrfBuffer.append(lvReferenceTable + ":" + asTupleArray[i]).append(",");
				}
				referenceLVTableContent = rrfBuffer.toString().substring(0, rrfBuffer.length()-1);
			}
		}

		return ((referenceLVTableContent != null && !referenceLVTableContent.isEmpty()) ? referenceLVTableContent : null);
	}
	
	private static String getIdToLvTable(long id) throws Exception {
		RuleServiceProvider RSP = RuleServiceProviderManager.getInstance().getDefaultProvider();
		
		int typeId = -1;
		if (RSP.getCluster().getClusterConfig().useObjectTable()) {
			ObjectTableCache.Tuple objTuple = RSP.getCluster().getObjectTableCache().getById(id);
			if (objTuple != null && !objTuple.isDeleted()) {
				typeId = objTuple.getTypeId();
			}
		} else {
			typeId = IDEncoder.decodeTypeId(id);
		}
		if (typeId > -1) {
			Class klass = RSP.getCluster().getMetadataCache().getClass(typeId);
			if (klass != null) {
				TypeDescriptor typeDescriptor = RSP.getTypeManager().getTypeDescriptor(klass);
				if (typeDescriptor != null) {
					return entityUriToLVTable(typeDescriptor.getURI());
				}
			}
		}
		return null;
	}

	private static com.tibco.cep.designtime.model.element.Concept getEntityBySpaceName(String spaceName) throws ClassNotFoundException {
		String entityURI = "_" + spaceToLVTableName(spaceName);
		Entity entity = getEntity(entityURI.replace("_", "/"));
		if (null != entity) {
			if (entity instanceof com.tibco.cep.designtime.model.element.Concept) {
	            com.tibco.cep.designtime.model.element.Concept cept =
	                    (com.tibco.cep.designtime.model.element.Concept) entity;
	            return cept;
			}
		}
		return null;
	}
	
	private static String entityUriToLVTable(String entityURI) {
		return entityURI.substring(1).replace("/", "_");
	}
	
	private static Entity getEntity(String entityURI) throws ClassNotFoundException {
		RuleServiceProviderManager RSPM = RuleServiceProviderManager.getInstance();
		return RSPM.getDefaultProvider().getProject().getOntology().getEntity(entityURI);
	}
	
	private static Result arrayToString(String spaceName, String fieldName, Object asTupleData) throws Exception {
		Result result = isArrayType(spaceName, fieldName);
		if (result != null && result.isArray) {
			Object[] adaptedArrayTuple = null;
			if (asTupleData.getClass().isArray()) {
				adaptedArrayTuple = (Object[]) asTupleData;
			} else {
				Object[] singleValueToArray = {asTupleData};
				adaptedArrayTuple = singleValueToArray;
			}
			if (adaptedArrayTuple != null && adaptedArrayTuple.length > 0) {
				Object firstElement = getFirstNonNullObject(adaptedArrayTuple);
				if (firstElement instanceof Calendar) {
					adaptedArrayTuple = calendarArrayToMSecArray(adaptedArrayTuple);
				}
				String arrayValue = join(adaptedArrayTuple);
				if (arrayValue != null && !arrayValue.isEmpty()) {
					result.setValue(arrayValue);
				}
			}
		}
		
		return result;
	}
	
	private static Object getFirstNonNullObject(Object[] objectArray) {
		if (objectArray != null && objectArray.length > 0) {
			for (Object object : objectArray) {
				if (object != null) return object;
			}
		}
		return null;
	}
	
	private static Result isArrayType(String spaceName, String fieldName) throws ClassNotFoundException {
		com.tibco.cep.designtime.model.element.Concept cept = getEntityBySpaceName(spaceName);
		ASToLVConverter asToLvConverter = new ASToLVConverter();
		if (cept != null) {
			List<PropertyDefinition> allProperties = cept.getLocalPropertyDefinitions();
			if (allProperties != null) {
				for (PropertyDefinition pd : allProperties) {
					if (pd.getName().equals(fieldName)) {
						Result result = asToLvConverter.new Result(pd.getType(), pd.isArray());
						result.setValue((pd.getType() == RDFTypes.CONCEPT_TYPEID || pd.getType() == RDFTypes.CONCEPT_REFERENCE_TYPEID) ? entityUriToLVTable(pd.getConceptTypePath()) : entityToLVDataTypeInString(pd.getType()));
						return result;
					}
				}
			}
		}
		
		return null;
	}
	
	private static boolean isHistoryType(String spaceName, String fieldName) throws ClassNotFoundException {
		com.tibco.cep.designtime.model.element.Concept cept = getEntityBySpaceName(spaceName);
		if (cept != null) {
			List<PropertyDefinition> allProperties = cept.getLocalPropertyDefinitions();
			if (allProperties != null) {
				for (PropertyDefinition pd : allProperties) {
					if (pd.getName().equals(fieldName)) {
						return (pd.getHistorySize() > 0);
					}
				}
			}
		}
		
		return false;
	}
	
	private static String entityToLVDataTypeInString(final int entityType) {
		String lvDataType = null;
		
		switch(entityType) {
		case RDFTypes.INTEGER_TYPEID: lvDataType = com.streambase.sb.DataType.INT.getName(); break;
		case RDFTypes.LONG_TYPEID: lvDataType = com.streambase.sb.DataType.LONG.getName(); break;
		case RDFTypes.BOOLEAN_TYPEID: lvDataType = com.streambase.sb.DataType.BOOL.getName(); break;
		case RDFTypes.DOUBLE_TYPEID: lvDataType = com.streambase.sb.DataType.DOUBLE.getName(); break;
		case RDFTypes.DATETIME_TYPEID: lvDataType = com.streambase.sb.DataType.TIMESTAMP.getName(); break;
		case RDFTypes.STRING_TYPEID: lvDataType = com.streambase.sb.DataType.STRING.getName(); break;
		}
		
		return lvDataType;
	}
	
	private static Long[] calendarArrayToMSecArray(Object[] calendarArray) {
		if (calendarArray != null) {
			List<Long> calMSecList = new ArrayList<Long>();
			for (Object object : calendarArray) {
				if (object == null) calMSecList.add(null);
				else if (object instanceof Calendar) {
					calMSecList.add(((Calendar)object).getTimeInMillis());
				}
				
			}
			return calMSecList.toArray(new Long[calMSecList.size()]);
		}
		return null;
	}
	
	
	private static String join(Object[] array) {
		StringBuffer joinedArray = new StringBuffer();
		for (Object obj : array) {
			joinedArray.append(obj).append(",");
		}
		return joinedArray.toString().substring(0, joinedArray.length()-1);
	}
	
	private static boolean isExplictTuple() {
		RuleServiceProviderManager RSPM = RuleServiceProviderManager.getInstance();
		Properties properties = RSPM.getDefaultProvider().getProperties();
        String codecExplicitStr = properties.getProperty(SystemProperty.PROP_TUPLE_EXPLICIT.getPropertyName(), Boolean.FALSE.toString());
        return Boolean.parseBoolean(codecExplicitStr);
	}
	
	private static Object getEntityPropertyValue(Object asTupleEntity, String propertyName) {
		if (asTupleEntity != null && asTupleEntity instanceof com.tibco.cep.kernel.model.entity.Entity) {
			try {
				if (propertyName.equals(com.tibco.cep.kernel.model.entity.Entity.ATTRIBUTE_ID.toLowerCase())) return ((com.tibco.cep.kernel.model.entity.Entity)asTupleEntity).getId();
				if (propertyName.equals(com.tibco.cep.kernel.model.entity.Entity.ATTRIBUTE_EXTID)) return ((com.tibco.cep.kernel.model.entity.Entity)asTupleEntity).getExtId();
				if (propertyName.equals(com.tibco.cep.kernel.model.entity.Entity.ATTRIBUTE_TYPE)) return ((com.tibco.cep.kernel.model.entity.Entity)asTupleEntity).getType();
				else return ((com.tibco.cep.kernel.model.entity.Entity)asTupleEntity).getPropertyValue(propertyName);
			} catch (Exception exception) {
				return null;
			}
		}
		return null;
	}
	
	class Result {
		private int type;
		private String value;
		private boolean isArray;
		
		Result(int type, boolean isArray) {
			this.type = type;
			this.isArray = isArray;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public boolean isArray() {
			return isArray;
		}

		public void setArray(boolean isArray) {
			this.isArray = isArray;
		}
	}
}
