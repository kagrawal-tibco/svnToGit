/**
 * 
 */
package com.tibco.cep.store;

import java.util.Iterator;

import com.tibco.cep.store.serializer.StoreSerializer;

/**
 * @author vpatil
 *
 */
public abstract class StoreIterator implements Iterator<Object> {
	
	protected String returnEntityPath;
	protected Iterator<?> internalIterator;
	
	public StoreIterator(Iterator<?> internalIterator, String returnEntityPath) {
		this.internalIterator = internalIterator;
		this.returnEntityPath = returnEntityPath;
	}

	public String getReturnEntityPath() {
		return this.returnEntityPath;
	}
	
	@Override
	public boolean hasNext() {
		boolean isAvailable = false;
		try {
			isAvailable = internalIterator.hasNext();
			if (!isAvailable) cleanup();
		} catch (Exception e) {
			throw new RuntimeException("Error traversing the store iterator", e);
		}
		return isAvailable;
	}
	
	@Override
	public Object next() {
		try {
			Object result = internalIterator.next();

			Item storeItem = null;
			storeItem = createItem(result);

			Object returnObject = null;
			if (returnEntityPath != null && !returnEntityPath.isEmpty()) {
				returnObject = StoreSerializer.deserialize(storeItem, returnEntityPath);
				storeItem.destroy();
				storeItem = null;
			} else {
				returnObject = storeItem;
			}
			
			return returnObject;
		} catch (Exception exception) {
			throw new RuntimeException("Error fetching the next item from the iterator", exception);
		}	
	}
	
	protected abstract Item createItem(Object result) throws Exception;
	
	public abstract void cleanup() throws Exception;
	
	@Override
	public void remove() {
		internalIterator.remove();
	}
}
