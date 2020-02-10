/**
 * 
 */
package com.tibco.cep.store.serializer;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.serializers.FieldType;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.store.Item;

/**
 * @author vpatil
 *
 */
public class StoreSerializer {
	private static boolean ignoreConceptOrReference;
	
	public static void serialize(Item storeItem, Entity entity) throws Exception {
		getEntityRowAdapter(entity.getType()).setValue(storeItem, entity);
	}
	
	public static Entity deserialize(Item item, String entityType) throws Exception {
		return getEntityRowAdapter(entityType).extractValue(item);
	}
	
	private static EntityItemAdapter getEntityRowAdapter(String entityType) throws Exception {
		if (entityType.startsWith(TypeManager.DEFAULT_BE_NAMESPACE_URI)) {
			entityType = entityType.substring(TypeManager.DEFAULT_BE_NAMESPACE_URI.length());
		}
		RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
		
		EntityItemAdapter entityRowAdapter = new EntityItemAdapter("id", FieldType.LONG,
				rsp.getTypeManager(), entityType, ignoreConceptOrReference);
		return entityRowAdapter;
	}
	
	public static void setIgnoreConceptOrReference(boolean ignoreConceptOrReference) {
		StoreSerializer.ignoreConceptOrReference = ignoreConceptOrReference;
	}
}
