/**
 * 
 */
package com.tibco.cep.store.serializer;

import com.tibco.cep.runtime.model.pojo.exim.PortablePojoConstants;
import com.tibco.cep.runtime.model.serializers.FieldType;
import com.tibco.cep.store.Item;

/**
 * @author vpatil
 *
 */
public interface ItemCodec {
	
	/**
	 * Put a field in the Item
	 * 
	 * @param item
	 * @param fieldName
	 * @param fieldType
	 * @param fieldValue
	 */
	void putInItem(Item item, String fieldName, FieldType fieldType, Object fieldValue);

	/**
	 * Get a field from the Item
	 * 
	 * @param item
	 * @param fieldName
	 * @param fieldType
	 * @return
	 */
	Object getFromItem(Item item, String fieldName, FieldType fieldType);
	
	
	/**
	 * Check if the field is one of the system fields
	 * 
	 * @param fieldName
	 * @return
	 */
	default boolean isSystemField(String fieldName) {
		return fieldName.equals(PortablePojoConstants.PROPERTY_CONCEPT_NAME_DELETED) ||
				fieldName.equals(PortablePojoConstants.PROPERTY_CONCEPT_NAME_PARENT) ||
				fieldName.equals(PortablePojoConstants.PROPERTY_CONCEPT_NAME_REV_REFERENCES) ||
				fieldName.equals(PortablePojoConstants.PROPERTY_EVENT_NAME_PAYLOAD) ||
				fieldName.equals(PortablePojoConstants.PROPERTY_EVENT_NAME_PAYLOAD.substring(0, PortablePojoConstants.PROPERTY_EVENT_NAME_PAYLOAD.length()-2)) ||
				fieldName.equals(PortablePojoConstants.PROPERTY_NAME_VERSION) ||
				fieldName.equals(PortablePojoConstants.PROPERTY_EVENT_NAME_RECOVERED) ||
				fieldName.equals(PortablePojoConstants.PROPERTY_NAME_EXT_ID) ||
				fieldName.equals(PortablePojoConstants.PROPERTY_NAME_ID) ||
				fieldName.equals(PortablePojoConstants.PROPERTY_NAME_TYPE_ID) ||
				fieldName.equals(PortablePojoConstants.PROPERTY_NAME_TIME_EVENT_CLOSURE) ||
				fieldName.equals(PortablePojoConstants.PROPERTY_NAME_TIME_EVENT_FIRED) ||
				fieldName.equals(PortablePojoConstants.PROPERTY_NAME_TIME_EVENT_NEXT) ||
				fieldName.equals(PortablePojoConstants.PROPERTY_NAME_TIME_EVENT_TTL);
	}

}
