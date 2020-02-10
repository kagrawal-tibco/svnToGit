/**
 * 
 */
package com.tibco.cep.store;

import java.util.Iterator;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.store.serializer.StoreSerializer;

/**
 * @author vpatil
 *
 */
public abstract class StoreContainer<T extends Item> {
	
	protected String name;
	
	public StoreContainer(String name) {
		this.name = name;
	}
	
	public void put(Object putObject) throws Exception {
		Item storeItem = null;
		boolean isEntity = (putObject instanceof Entity);
		if (isEntity) {
			storeItem = createItem();
			StoreSerializer.serialize(storeItem, (Entity)putObject);
		} else {
			storeItem = (Item) putObject;
		}
		putItem((T)storeItem);
		if (isEntity) storeItem.destroy();
	}
	
	public Object get(Object getObject) throws Exception {
		Item storeItem = null;
		boolean isEntity = (getObject instanceof Entity);
		if (isEntity) {
			storeItem = createItem();
			setPrimaryKey((T)storeItem, (Entity)getObject);
		} else {
			storeItem = (Item) getObject;
		}
		
		T returnItem = getItem((T)storeItem);
		
		Object returnObject = null;
		if (isEntity) {
			storeItem.destroy();
			if (returnItem != null) {
				returnObject = StoreSerializer.deserialize((T)returnItem, ((Entity)getObject).getType());
				// contents of return item are already moved to return object, can be cleaned up now
				returnItem.destroy();
			}
		} else {
			returnObject = returnItem;
			// end user calling this will have to manually call destroy
		}
		return returnObject;
	}
	
	public void delete(Object deleteObject) throws Exception {
		Item storeItem = null;
		boolean isEntity = (deleteObject instanceof Entity);
		if (isEntity) {
			storeItem = createItem();
			setPrimaryKey((T)storeItem, (Entity)deleteObject);
		} else {
			storeItem = (Item) deleteObject;
		}
		deleteItem((T)storeItem);
		if (isEntity) storeItem.destroy();
	}
	
	public Iterator<Object> query(String filter) throws Exception {
		return null;
	}
	
	public abstract T createItem() throws Exception;
	
	protected abstract void putItem(T item) throws Exception;
	
	protected abstract T getItem(T item) throws Exception;
	
	protected abstract void deleteItem(T item) throws Exception;
	
	protected abstract void close() throws Exception;
	
	protected abstract String[] getPrimaryKeyNames() throws Exception;
	
	private void setPrimaryKey(T item, Entity entity) throws Exception {
		String[] primaryKeyNames = getPrimaryKeyNames();
		
		for (String pkey : primaryKeyNames) {
			pkey = pkey.toLowerCase();
			Object propertyDef = getProperty(entity.getType(), pkey);

			String primaryKeyType = null;
			Object primaryKeyValue = null;
			if (propertyDef instanceof PropertyDefinition) {
				primaryKeyType = RDFTypes.getTypeString(((PropertyDefinition)propertyDef).getType()).toLowerCase();
				primaryKeyValue = entity.getPropertyValue(((PropertyDefinition)propertyDef).getName());

			} else if (propertyDef instanceof EventPropertyDefinition) {
				primaryKeyType = ((EventPropertyDefinition)propertyDef).getType().getName().toLowerCase();
				primaryKeyValue = entity.getPropertyValue(((EventPropertyDefinition)propertyDef).getPropertyName());
			} 

			if (primaryKeyType != null && primaryKeyValue != null) {
				item.setValue(pkey, primaryKeyType, primaryKeyValue);
			}
		}
	}
	
	private Object getProperty(String entityType, String propertyName) {
		if (entityType.startsWith(TypeManager.DEFAULT_BE_NAMESPACE_URI)) {
			entityType = entityType.substring(TypeManager.DEFAULT_BE_NAMESPACE_URI.length());
		}
		RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
		Ontology ontology = rsp.getProject().getOntology();

		com.tibco.cep.designtime.model.Entity designTimEntity = ontology.getEntity(entityType);

		Object propertyDef = null;
		if (designTimEntity instanceof Concept) {
			for (Object pd : ((Concept)designTimEntity).getAllPropertyDefinitions()) {
				if (pd instanceof PropertyDefinition && ((PropertyDefinition)pd).getName().toLowerCase().equals(propertyName)) {
					propertyDef = pd;
					break;
				}
			}
		} else {
			propertyDef = ((Event)designTimEntity).getPropertyDefinition(propertyName, true);
			for (Object pd : ((Event)designTimEntity).getAllUserProperties()) {
				if (pd instanceof EventPropertyDefinition && ((EventPropertyDefinition)pd).getPropertyName().toLowerCase().equals(propertyName)) {
					propertyDef = pd;
					break;
				}
			}
		}

		return propertyDef;
	}
}
