/**
 * 
 */
package com.tibco.cep.store.factory;

import com.tibco.cep.store.StoreConnectionInfo;
import com.tibco.cep.store.StoreType;
import com.tibco.cep.store.as.ASConnectionInfo;

/**
 * @author vpatil
 *
 */
public class StoreConnectionInfoFactory {
	
	public static StoreConnectionInfo createStoreConnectionInfo(final String storeTypeString, String url) {
		StoreConnectionInfo storeConnectionInfo = null;
		
		StoreType storeType = StoreType.valueOf(storeTypeString);
		switch(storeType) {
		case AS:
			storeConnectionInfo = new ASConnectionInfo(storeType, url);
			break;
		default: throw new RuntimeException(String.format("Invalid Store Type[%s] specified. Valid store types are %s.", storeTypeString, StoreType.getNames()));
		}
		
		return storeConnectionInfo;
	}

}
