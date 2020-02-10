/**
 * 
 */
package com.tibco.cep.store;

import com.tibco.cep.runtime.model.serializers.FieldType;
import com.tibco.cep.store.serializer.ItemCodec;

/**
 * @author vpatil
 *
 */
public abstract class Item {
	protected ItemCodec itemCodec;
	protected StoreContainer<? extends Item> storeContainer;
	
	public Item() {
		setItemCodec();
	}
	
	protected abstract void setItemCodec();
	
	protected abstract void createItem() throws Exception;
	
	public void create(StoreContainer<? extends Item> storeContainer) throws Exception {
		this.storeContainer = storeContainer;
		createItem();
	}
	
	public void setValue(String fieldName, String fieldType, Object fieldValue) throws Exception {
		itemCodec.putInItem(this, fieldName, FieldType.getByType(fieldType.toLowerCase()), fieldValue);
	}
	
	public Object getValue(String fieldName, String fieldType) throws Exception {
		return itemCodec.getFromItem(this, fieldName, FieldType.getByType(fieldType.toLowerCase()));
	}
	
	public abstract void destroy() throws Exception;
	
	public StoreContainer<? extends Item> getContainer() {
		return this.storeContainer;
	}
	
	public void setContainer(StoreContainer<? extends Item> storeContainer) {
		this.storeContainer = storeContainer;
	}
	
	public abstract void clear() throws Exception;
	
	public abstract void setTTL(long ttl) throws Exception;
	
	public abstract long getExpiration() throws Exception;
}
