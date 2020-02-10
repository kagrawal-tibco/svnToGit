package com.tibco.cep.dashboard.integration.embedded;

import com.tibco.cep.dashboard.psvr.mal.MALTransaction;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;

public class EMALTransaction extends MALTransaction {
	
	public EMALTransaction(){
		super();
	}

	@Override
	public void recordChange(MALElement element, String property, Object oldValue, Object newValue) {
		
	}
	
	@Override
	public void removeChange(MALElement element) {
		
	}
	
	@Override
	public void removeChange(MALElement element, String property) {
		
	}
	
}
