/**
 * 
 */
package com.tibco.cep.store.factory;

import com.tibco.cep.store.StoreExt;
import com.tibco.cep.store.StoreType;
import com.tibco.cep.store.as.ASExt;

/**
 * @author vpatil
 *
 */
public class StoreExtFactory {
	public static StoreExt createStoreExt(final String storeTypeString) {
		StoreExt storeExt = null;
		
		StoreType storeType = StoreType.valueOf(storeTypeString);
		switch(storeType) {
		case AS:
			storeExt = new ASExt();
			break;
		default: throw new RuntimeException(String.format("Invalid Store Type[%s] specified. Valid store types are %s.", storeTypeString, StoreType.getNames()));
		}
		
		return storeExt;
	}
}
