/**
 * 
 */
package com.tibco.cep.store.factory;

import com.tibco.cep.store.StoreConnection;
import com.tibco.cep.store.StoreConnectionInfo;
import com.tibco.cep.store.StoreType;
import com.tibco.cep.store.as.ASConnection;

/**
 * @author vpatil
 *
 */
public class StoreConnectionFactory {
	
	public static StoreConnection createStoreConnection(StoreConnectionInfo storeConnectionInfo) {
		StoreConnection storeConnection = null;
		
		switch(storeConnectionInfo.getStoreType()) {
		case AS:
			storeConnection = new ASConnection(storeConnectionInfo);
			break;
		default: throw new RuntimeException(String.format("Invalid Store Type[%s] specified. Valid store types are %s.", storeConnectionInfo.getStoreType(), StoreType.getNames()));
		}
		
		return storeConnection;
	}
}
