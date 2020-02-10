/**
 * 
 */
package com.tibco.cep.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.store.serializer.StoreSerializer;
import com.tibco.cep.store.util.StoreUtil;

/**
 * @author vpatil
 *
 */
public abstract class StoreConnection {
	protected StoreConnectionInfo storeConnectionInfo;
	protected StoreMetadata storeMetadata;
	protected Map<String, StoreContainer<? extends Item>> storeContainerMap = new ConcurrentHashMap<String, StoreContainer<? extends Item>>();

	public StoreConnection(StoreConnectionInfo storeConnectionInfo) {
		this.storeConnectionInfo = storeConnectionInfo;
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public abstract void connect() throws Exception;

	
	/**
	 * 
	 * @throws Exception
	 */
	public abstract void disconnect() throws Exception;
	
	/**
	 * 
	 * @param containerName
	 * @return
	 * @throws Exception
	 */
	public StoreContainer<? extends Item> getContainer(String containerName) throws Exception {
		return storeContainerMap.get(getContainerKey(containerName));
	}
		
	/**
	 * 
	 * @param containerName
	 * @throws Exception
	 */
	public void openContainer(String containerName) throws Exception {
		StoreContainer<? extends Item> storeContainer = getContainer(containerName);
		if (storeContainer == null) {
			storeContainer = createContainer(containerName);

			storeContainerMap.put(getContainerKey(containerName), storeContainer);
		}
	}
	
	/**
	 * 
	 * @param containerName
	 * @return
	 * @throws Exception
	 */
	public abstract StoreContainer<? extends Item> createContainer(String containerName) throws Exception;
	
	/**
	 * 
	 * @param containerName
	 * @throws Exception
	 */
	public void closeContainer(String containerName) throws Exception {
		StoreContainer<? extends Item> storeContainer = storeContainerMap.remove(getContainerKey(containerName));
		if (storeContainer != null) {
			storeContainer.close();
			storeContainer = null;
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	protected String getContainerKeyPrefix() {
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	protected boolean hasContainerKeyPrefix(String containerKey) {
		return false;
	}
	
	/**
	 * 
	 * @param containerName
	 * @return
	 */
	private String getContainerKey(String containerName) {
		if (hasContainerKeyPrefix(containerName)) return containerName;
		String containerKey = getContainerKeyPrefix() != null ? getContainerKeyPrefix() + "_" + containerName.toLowerCase() : containerName.toLowerCase();
		return containerKey;
	}
	
	/**
	 * 
	 * @return
	 */
	public StoreConnectionInfo getStoreConnectionInfo() {
		return this.storeConnectionInfo;
	}
	
	/**
	 * 
	 * @return
	 */
	public StoreMetadata getStoreMetadata() {
		return this.storeMetadata;
	}
	
	/**
	 * 
	 * @return
	 */
	protected void closeSessionContainers(String sessionId) throws Exception {
		for (String containerKey : storeContainerMap.keySet()) {
			if (sessionId != null && containerKey.startsWith(sessionId)) {
				closeContainer(containerKey);
			}
		}
	}
	
	/**
	 * 
	 * @param url
	 * @param putObjects
	 * @throws Exception
	 */
	public void putAll(String url, Object[] putObjects) throws Exception {
		List<Item> putItems = createItemsForBatchOps(putObjects, false);
		
		putAllItems(putItems);
		
		for (int i=0; i<putObjects.length; i++) {
			if (putObjects[i] instanceof Entity) {
				StoreUtil.putConcepts(url, putObjects[i]);
				
				Item putItem = putItems.get(i);
				if (putItem != null) {
					putItem.destroy();
					putItem = null;
				}
			}
		}
		if (putItems != null) {
			putItems.clear();
			putItems = null;
		}
	}
	
	/**
	 * 
	 * @param url
	 * @param getObjects
	 * @return
	 * @throws Exception
	 */
	public Object[] getAll(String url, Object[] getObjects) throws Exception {
		List<Item> getItems = createItemsForBatchOps(getObjects, true);
		
		List<Item> returnedItems = getAllItems(getItems);
		
		List<Object> getAllResults = new ArrayList<Object>();
		for (int i=0; i<getObjects.length; i++) {
			Item item = returnedItems.get(i);
			if (getObjects[i] instanceof Entity) {
				Object entity = StoreSerializer.deserialize(item, ((Entity)getObjects[i]).getType());
				StoreUtil.loadConcepts(url, getObjects[i], entity, false);
				getAllResults.add(entity);
				
				// destroy item returned as part for batch call, since data is moved to the entity
				item.destroy();
				item = null;
				// destroy item created before making the batch call, i.e. entity to item
				Item getItem = getItems.get(i);
				if (getItem != null) {
					getItem.destroy();
					getItem = null;
				}
			} else {
				getAllResults.add(item);
			}
		}
		
		//finally clear all local lists
		if (getItems != null) {
			getItems.clear();
			getItems = null;
		}
		if (returnedItems != null) {
			returnedItems.clear();
			returnedItems = null;
		}
				
		return getAllResults.toArray(new Object[getAllResults.size()]);
	}
	
	/**
	 * 
	 * @param url
	 * @param deleteObjects
	 * @throws Exception
	 */
	public void deleteAll(String url, Object[] deleteObjects) throws Exception {
		List<Item> deleteItems = createItemsForBatchOps(deleteObjects, false);
		
		deleteAllItems(deleteItems);
		
		for (int i=0; i<deleteObjects.length; i++) {
			if (deleteObjects[i] instanceof Entity) {
				StoreUtil.loadConcepts(url, deleteObjects[i], null, true);
				
				Item deleteItem = deleteItems.get(i);
				if (deleteItem != null) {
					deleteItem.destroy();
					deleteItem = null;
				}
			}
		}
		if (deleteItems != null) {
			deleteItems.clear();
			deleteItems = null;
		}
	}
	
	private List<Item> createItemsForBatchOps(Object[] objects, boolean isGet) throws Exception {
		List<Item> items = new ArrayList<Item>();
		
		for (Object getObj : objects) {
			if (getObj instanceof Entity) {
				String containerName = getContainerName(((Entity)getObj).getType());
				
				StoreContainer<? extends Item> storeContainer = getContainer(containerName);
				if (storeContainer != null) {					
					Item storeItem = storeContainer.createItem();
					if (isGet) StoreUtil.setPrimaryKey(storeItem, (Entity)getObj, storeContainer.getPrimaryKeyNames());
					else StoreSerializer.serialize(storeItem, (Entity)getObj);
					items.add(storeItem);
				} else {
					throw new RuntimeException(String.format("Container[%s] isn't open for operations.", containerName));
				}
			} else {
				if (!(getObj instanceof Item)) throw new RuntimeException("Objects for batch operations can be of type Entity(Concept/Event)/Item only");
				items.add((Item)getObj);
			}
		}
		
		return items;
	}
	
	private String getContainerName(String entityType) {
		return entityType.substring(entityType.lastIndexOf("/")+1);
	}
	
	/**
	 * 
	 * @param putItems
	 * @return
	 * @throws Exception
	 */
	public abstract boolean putAllItems(List<Item> putItems) throws Exception;
	
	/**
	 * 
	 * @param getItems
	 * @return
	 * @throws Exception
	 */
	public abstract List<Item> getAllItems(List<Item> getItems) throws Exception;
	
	/**
	 * 
	 * @param putItems
	 * @return
	 * @throws Exception
	 */
	public abstract boolean deleteAllItems(List<Item> putItems) throws Exception;
	
	/**
	 * 
	 * @param query
	 * @param queryParameters
	 * @param queryOptions
	 * @param returnEntityPath
	 * @return
	 * @throws Exception
	 */
	public abstract StoreIterator query(String query, Object[] queryParameters, Object queryOptions, String returnEntityPath) throws Exception;
	
	/**
	 * 
	 * @throws Exception
	 */
	public abstract void enableTransactions() throws Exception;
	
	/**
	 * 
	 * @throws Exception
	 */
	public abstract void disableTransactions() throws Exception;
	
	/**
	 * 
	 * @throws Exception
	 */
	public abstract void commit() throws Exception;
	
	/**
	 * 
	 * @throws Exception
	 */
	public abstract void rollback() throws Exception;
	
	/**
	 * 
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public abstract long executeUpdate(String query) throws Exception;
}
