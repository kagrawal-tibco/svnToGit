package com.tibco.cep.studio.wizard.as.internal.services;

import com.tibco.cep.studio.wizard.as.internal.services.api.ASService;
import com.tibco.cep.studio.wizard.as.services.api.IASService;

public class ServiceFactory {

	private static final IASService _AS_SERVICE = new ASService();

	public static IASService getASService() {
		return _AS_SERVICE;
	}

}
