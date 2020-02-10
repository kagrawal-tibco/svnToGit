/**
 * 
 */
package com.tibco.cep.store.serializer;

import com.tibco.cep.runtime.model.pojo.exim.PortablePojo;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoManager;

/**
 * @author vpatil
 *
 */
public class StorePortablePojoManager implements PortablePojoManager {
	private boolean ignoreContainedOrReference;
	
	public StorePortablePojoManager(boolean ignoreContainedOrReference) {
		this.ignoreContainedOrReference = ignoreContainedOrReference;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojoManager#createPojo(long, java.lang.String, int, boolean)
	 */
	@Override
	public PortablePojo createPojo(long id, String extId, int typeId) {
		return new StorePortablePojo(id, extId, typeId, ignoreContainedOrReference);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.model.pojo.exim.PortablePojoManager#reset()
	 */
	@Override
	public PortablePojo[] reset() {
		return null;
	}
}
