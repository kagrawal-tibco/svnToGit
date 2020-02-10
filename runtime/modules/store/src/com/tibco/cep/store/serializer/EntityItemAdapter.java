/**
 * 
 */
package com.tibco.cep.store.serializer;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.managed.EximHelper;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojo;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoConstants;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoManager;
import com.tibco.cep.runtime.model.serializers.FieldType;
import com.tibco.cep.store.Item;

/**
 * @author vpatil
 *
 */
public class EntityItemAdapter {
	private StoreEximHelper eximHelper;
	
	public EntityItemAdapter(String keyFieldName, FieldType keyType, 
             TypeManager tm, String entityType, boolean ignoreContainedOrReference) throws Exception {

		this.eximHelper = new StoreEximHelper(tm, entityType, ignoreContainedOrReference);
	}
	
	public Entity extractValue(Item item) {
		return eximHelper.translate(item);
	}

	public Item setValue(Item item, Entity value) {
		return eximHelper.translate(value, item);
	}
	
	private class StoreEximHelper extends EximHelper<Item> {
		private String entityType;
		private StorePortablePojoManager portablePojoManager;

		public StoreEximHelper(TypeManager tm, String entityType, boolean ignoreContainedOrReference) throws Exception {
            super(tm);
            
            this.entityType = entityType;
            this.portablePojoManager = new StorePortablePojoManager(ignoreContainedOrReference);
        }

        @Override
        protected StorePortablePojo transform(Item storeItem) {
            try {
            	return StorePortablePojo.toPojo(storeItem, entityType, portablePojoManager);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected Item transform(PortablePojo intermediate, Item destination) {
        	try {
        		// add system properties i.e. id, extId, typeId
        		destination.setValue(PortablePojoConstants.PROPERTY_NAME_ID, FieldType.LONG.toString(), intermediate.getId());
        		if (intermediate.getExtId() != null) destination.setValue(PortablePojoConstants.PROPERTY_NAME_EXT_ID, FieldType.STRING.toString(), intermediate.getExtId());
        		destination.setValue(PortablePojoConstants.PROPERTY_NAME_TYPE_ID, FieldType.INTEGER.toString(), intermediate.getTypeId());
        		
        	} catch (Exception exception) {
        		throw new RuntimeException(exception);
        	}
        	
            return destination;
        }
        
        @Override
        public PortablePojoManager getPojoManager() {
        	return portablePojoManager;
        }
	}
}
