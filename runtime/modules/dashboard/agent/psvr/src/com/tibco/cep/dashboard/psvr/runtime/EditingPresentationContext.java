package com.tibco.cep.dashboard.psvr.runtime;

import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.security.SecurityToken;

public class EditingPresentationContext extends PresentationContext {

	public EditingPresentationContext(SecurityToken token) throws MALException, ElementNotFoundException {
		super(token);
	}

}
