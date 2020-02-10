/**
 * 
 */
package com.tibco.cep.store.factory;

import com.tibco.cep.store.StoreQueryOptions;
import com.tibco.cep.store.StoreType;
import com.tibco.cep.store.as.ASQueryOptions;

/**
 * @author vpatil
 *
 */
public class StoreQueryOpionsFactory {
	public static StoreQueryOptions createQueryOptions(final String storeTypeString) {
		StoreQueryOptions storeQueryOptions = null;
		
		StoreType storeType = StoreType.valueOf(storeTypeString);
		switch(storeType) {
		case AS:
			storeQueryOptions = new ASQueryOptions();
			break;
		default: throw new RuntimeException(String.format("Invalid Store Type[%s] specified. Valid store types are %s.", storeTypeString, StoreType.getNames()));
		}
		
		return storeQueryOptions;
	}
}
