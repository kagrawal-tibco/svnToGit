package com.tibco.cep.bemm.common.service;

public interface MessageService extends StartStopService {

	String getMessage(String key, Object... args);

}
