/**
 * 
 */
package com.tibco.cep.store.serializer;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFUberType;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojo;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoConstants;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoManager;
import com.tibco.cep.runtime.model.serializers.FieldType;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.store.Item;

/**
 * @author vpatil
 *
 */
public class StorePortablePojo extends PortablePojo {
	private boolean ignoreContainedOrReference;
	
	protected StorePortablePojo(long id, String extId, int typeId, boolean ignoreContainedOrReference) {
        super(id, extId, typeId);
        this.ignoreContainedOrReference = ignoreContainedOrReference;
    }
    
	public Object get(String name, String type) {
		Object getValue = null;
		
		if (entity != null && name != null && !name.isEmpty() && !ignore(entity, name)) {
			if (type == null) type = getEntityPropertyType(entity, name);
			
			if (type != null) {
				Item storeItem = (Item) getItem();
				try {
					getValue = storeItem.getValue(name, type);
				} catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			}
		}
		
		return getValue;
	}
    
    public void set(String name, Object value, String type) {
    	if (entity != null && value != null && !ignore(entity, name)) {
    		if (type == null) type = getEntityPropertyType(entity, name);
    		
    		if (type != null) {
    			Item storeItem = (Item) getItem();
    			try {
    				storeItem.setValue(name, type, value);
    			} catch (Exception exception) {
    				throw new RuntimeException(exception);
    			}
    		}
    	}
    }

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#getPropertyValue(java.lang.String)
	 */
	@Override
	public Object getPropertyValue(final String name) {
		return getPropertyValue(name, null);
	}
	
	@Override
	public Object getPropertyValue(String name, String type) {
		Object getValue = get(name, type);
		
		if (getValue == null) {
			switch(name) {
			case PortablePojoConstants.PROPERTY_NAME_VERSION: 
				Integer version = 0;
				if (entity instanceof Event) version = PROPERTY_VALUE_VERSION_DEFAULT;
				getValue = version;
				break;
			case PortablePojoConstants.PROPERTY_EVENT_NAME_RECOVERED:
			case PortablePojoConstants.PROPERTY_CONCEPT_NAME_DELETED:
				getValue = false;
				break;
			}
		}
		return getValue;
	}

//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setPropertyValue(java.lang.String, boolean)
//	 */
//	@Override
//	public void setPropertyValue(String name, boolean value) {
//		set(name, value);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setPropertyValue(java.lang.String, int)
//	 */
//	@Override
//	public void setPropertyValue(String name, int value) {
//		set(name, value);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setPropertyValue(java.lang.String, long)
//	 */
//	@Override
//	public void setPropertyValue(String name, long value) {
//		set(name, value);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setPropertyValue(java.lang.String, double)
//	 */
//	@Override
//	public void setPropertyValue(String name, double value) {
//		set(name, value);
//	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setPropertyValue(String name, Object value) {
		set(name, value, null);
	}
	
	@Override
	public void setPropertyValue(String name, Object value, String type) {
		set(name, value, type);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#getArrayPropertyValue(java.lang.String, int)
	 */
//	@Override
//	public Object getArrayPropertyValue(String name, int position) {
//		Object[] array = (Object[]) get(name);
//
//        return (array == null) ? null : array[position];
//	}

//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#getArrayPropertyValues(java.lang.String)
//	 */
//	@Override
//	public Object[] getArrayPropertyValues(String name) {
//		return (Object[]) get(name, null);
//	}
	
	@Override
	public Object[] getArrayPropertyValues(String name, String type) {
		return (Object[]) get(name, type);
	}
	
	
//	protected void setArrayPropertyValueInternal(String name, int totalLength, Object value, int position) {
//		Object[] array = (Object[]) get(name);
//		if (array == null) {
//			array = new Object[totalLength];
//			set(name, array);
//		}
//
//		array[position] = value;
//	}

//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setArrayPropertyValue(java.lang.String, int, boolean, int)
//	 */
//	@Override
//	public void setArrayPropertyValue(String name, int totalLength, boolean value, int position) {
//		setArrayPropertyValueInternal(name, totalLength, value, position);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setArrayPropertyValue(java.lang.String, int, int, int)
//	 */
//	@Override
//	public void setArrayPropertyValue(String name, int totalLength, int value, int position) {
//		setArrayPropertyValueInternal(name, totalLength, value, position);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setArrayPropertyValue(java.lang.String, int, long, int)
//	 */
//	@Override
//	public void setArrayPropertyValue(String name, int totalLength, long value, int position) {
//		setArrayPropertyValueInternal(name, totalLength, value, position);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setArrayPropertyValue(java.lang.String, int, double, int)
//	 */
//	@Override
//	public void setArrayPropertyValue(String name, int totalLength, double value, int position) {
//		setArrayPropertyValueInternal(name, totalLength, value, position);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setArrayPropertyValue(java.lang.String, int, java.lang.Object, int)
//	 */
//	@Override
//	public void setArrayPropertyValue(String name, int totalLength, Object value, int position) {
//		setArrayPropertyValueInternal(name, totalLength, value, position);
//	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#getHistoryPropertyValue(java.lang.String, int)
	 */
//	@Override
//	public Object getHistoryPropertyValue(String name, int position) {
//		Object[] array = (Object[]) get(name);
//
//		return (array == null) ? null : array[position];
//	}

//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#getHistoryPropertyValues(java.lang.String)
//	 */
//	@Override
//	public Object[] getHistoryPropertyValues(String name) {
//		return (Object[]) get(name, null);
//	}
	
	@Override
	public Object[] getHistoryPropertyValues(String name, String type) {
		return (Object[]) get(name, type);
	}

//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setHistoryPropertyValue(java.lang.String, int, boolean, long, int)
//	 */
//	@Override
//	public void setHistoryPropertyValue(String name, int totalLength, boolean value, long time, int position) {
//		setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setHistoryPropertyValue(java.lang.String, int, int, long, int)
//	 */
//	@Override
//	public void setHistoryPropertyValue(String name, int totalLength, int value, long time, int position) {
//		setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setHistoryPropertyValue(java.lang.String, int, long, long, int)
//	 */
//	@Override
//	public void setHistoryPropertyValue(String name, int totalLength, long value, long time, int position) {
//		setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setHistoryPropertyValue(java.lang.String, int, double, long, int)
//	 */
//	@Override
//	public void setHistoryPropertyValue(String name, int totalLength, double value, long time, int position) {
//		setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setHistoryPropertyValue(java.lang.String, int, java.lang.Object, long, int)
//	 */
//	@Override
//	public void setHistoryPropertyValue(String name, int totalLength, Object value, long time, int position) {
//		setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#getArrayHistoryPropertyValue(java.lang.String, int)
	 */
//	@Override
//	public Object getArrayHistoryPropertyValue(String name, int position) {
//		Object[] array = (Object[]) get(name);
//
//        return (array == null) ? null : array[position];
//	}

//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#getArrayHistoryPropertyValues(java.lang.String)
//	 */
//	@Override
//	public Object[] getArrayHistoryPropertyValues(String name) {
//		return (Object[]) get(name, null);
//	}
	
	@Override
	public Object[] getArrayHistoryPropertyValues(String name, String type) {
		return (Object[]) get(name, type);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setArrayHistoryPropertyValue(java.lang.String, int, boolean[], long[], int)
	 */
//	@Override
//	public void setArrayHistoryPropertyValue(String name, int totalLength, boolean[] value, long[] time, int position) {
//		setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setArrayHistoryPropertyValue(java.lang.String, int, int[], long[], int)
//	 */
//	@Override
//	public void setArrayHistoryPropertyValue(String name, int totalLength, int[] value, long[] time, int position) {
//		setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setArrayHistoryPropertyValue(java.lang.String, int, long[], long[], int)
//	 */
//	@Override
//	public void setArrayHistoryPropertyValue(String name, int totalLength, long[] value, long[] time, int position) {
//		setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setArrayHistoryPropertyValue(java.lang.String, int, double[], long[], int)
//	 */
//	@Override
//	public void setArrayHistoryPropertyValue(String name, int totalLength, double[] value, long[] time, int position) {
//		setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojo#setArrayHistoryPropertyValue(java.lang.String, int, java.lang.Object[], long[], int)
//	 */
//	@Override
//	public void setArrayHistoryPropertyValue(String name, int totalLength, Object[] value, long[] time, int position) {
//		setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//	}
	
	/**
	 * 
	 * @param storeItem
	 * @param entityType
	 * @param portablePojoManager
	 * @return
	 */
	public static StorePortablePojo toPojo(Item storeItem, String entityType, PortablePojoManager portablePojoManager) {
		StorePortablePojo storePortablePojo = null;
		try {
			Long id = (Long) storeItem.getValue(PortablePojoConstants.PROPERTY_NAME_ID, FieldType.LONG.toString());
			if (id == null) {
				RuleSession session = RuleSessionManager.getCurrentRuleSession();
				id =  new Long(session.getRuleServiceProvider().getIdGenerator().nextEntityId(retrieveTypeClass(entityType)));
			}
			String extId = (String) storeItem.getValue(PortablePojoConstants.PROPERTY_NAME_EXT_ID, FieldType.STRING.toString());
			Integer typeId = (Integer) storeItem.getValue(PortablePojoConstants.PROPERTY_NAME_TYPE_ID, FieldType.INTEGER.toString());
			if (typeId == null) typeId = retrieveTypeId(entityType);

			storePortablePojo = (StorePortablePojo) portablePojoManager.createPojo(id, extId, typeId);
			storePortablePojo.setItem(storeItem);
			storePortablePojo.setEntityType(entityType);
			
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
		return storePortablePojo;
	}
	
	private boolean ignore(com.tibco.cep.designtime.model.Entity entity, String propertyName) {
		boolean ignore = false;
		if (ignoreContainedOrReference && entity instanceof Concept) {
			PropertyDefinition propertyDef = ((Concept)entity).getPropertyDefinition(propertyName, false);
			if (propertyDef != null) {
				RDFUberType currentType = RDFTypes.getType(RDFTypes.getTypeString(propertyDef.getType()));
				if (currentType == RDFTypes.CONCEPT ||currentType == RDFTypes.CONCEPT_REFERENCE) ignore = true;
			}
		}
		return ignore;
	}
	 
	 protected static Object getProperty(com.tibco.cep.designtime.model.Entity entity, String propertyName) {
		 Object propertyDef = null;
		 
		 if (entity instanceof Concept) {
			 propertyDef = ((Concept)entity).getPropertyDefinition(propertyName, false);
		 } else {
			 propertyDef = ((Event)entity).getPropertyDefinition(propertyName, true);
		 }
		 
		 return propertyDef;
	 }
	 
	 protected static String getEntityPropertyType(com.tibco.cep.designtime.model.Entity entity, String propertyName) {
		 String type = getSystemType(propertyName);
		 if (type != null && !type.isEmpty()) return type;
		 
		 Object propertyDef = getProperty(entity, propertyName);
		 
		 if (propertyDef instanceof PropertyDefinition) {
			 return getType(((PropertyDefinition)propertyDef));
			 
		 } else if (propertyDef instanceof EventPropertyDefinition) {
			 return ((EventPropertyDefinition)propertyDef).getType().getName().toLowerCase();
		 }
		 return null;
	 }
	 
	 protected static String getType(PropertyDefinition pd) {
		 String columnType = null;
		 if (pd.isArray()) {
			 columnType = FieldType.BLOB.name().toLowerCase();
		 } else {
			 RDFUberType currentType = RDFTypes.getType(RDFTypes.getTypeString(pd.getType()));
			 if (currentType == RDFTypes.CONCEPT ||currentType == RDFTypes.CONCEPT_REFERENCE) {
				 columnType = FieldType.LONG.name().toLowerCase();
			 } else {
				 columnType = RDFTypes.getTypeString(pd.getType()).toLowerCase();
			 }
		 }
		 return columnType;
	 }
	 
	 private static int retrieveTypeId(String fullPath) {
		 RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
		 return rsp.getTypeManager().getTypeDescriptor(fullPath).getTypeId();
	 }
	 
	 private static Class retrieveTypeClass(String fullPath) {
		 RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
		 return rsp.getTypeManager().getTypeDescriptor(fullPath).getImplClass();
	 }
	 
	 private static String getSystemType(final String propertyName) {
		 String returnType = null;
		 switch (propertyName) {
		 case PortablePojoConstants.PROPERTY_EVENT_NAME_PAYLOAD:
			 returnType = FieldType.BLOB.toString();
			 break;
		 case PortablePojoConstants.PROPERTY_CONCEPT_NAME_PARENT:
			 returnType = FieldType.LONG.toString();
			 break;
		 case PortablePojoConstants.PROPERTY_CONCEPT_NAME_REV_REFERENCES:
			 returnType = FieldType.BLOB.toString();
			 break;
		 case PortablePojoConstants.PROPERTY_NAME_VERSION:
			 returnType = FieldType.INTEGER.toString();
			 break;
		 case PortablePojoConstants.PROPERTY_EVENT_NAME_RECOVERED:
			 returnType = FieldType.BOOLEAN.toString();
			 break;
		 case PortablePojoConstants.PROPERTY_CONCEPT_NAME_DELETED:
			 returnType = FieldType.BOOLEAN.toString();
			 break;
		 }
		 
		 return returnType;
	 }
}
