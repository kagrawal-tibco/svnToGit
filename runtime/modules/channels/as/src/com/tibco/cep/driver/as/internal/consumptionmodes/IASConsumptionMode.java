package com.tibco.cep.driver.as.internal.consumptionmodes;

import java.util.Map;

public interface IASConsumptionMode
{

	void consume(Object payload) throws Exception;

	void start(Map<Object, Object> condition) throws Exception;

	void stop() throws Exception;
	
	void suspend() throws Exception;

	void resume() throws Exception;

	void close() throws Exception;

}
