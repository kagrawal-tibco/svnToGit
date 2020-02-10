package com.tibco.cep.decisionproject.persistence.factory;

import com.tibco.cep.decisionproject.persistence.PersistenceManager;
import com.tibco.cep.decisionproject.persistence.impl.DefaultPersistenceManagerImpl;

public class PersistenceManagerFactory {
	
	public static PersistenceManager createPersistenceManager(String protocol) {
		if (protocol == null)
			throw new IllegalArgumentException("URI string can not be null");
	
		if (protocol != null){			
			if (protocol.equalsIgnoreCase("db")){
				
			}
			else {
				return new DefaultPersistenceManagerImpl();
			}
				
		}
		return null;
	}

}
