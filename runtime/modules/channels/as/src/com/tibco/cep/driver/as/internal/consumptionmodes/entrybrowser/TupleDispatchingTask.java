package com.tibco.cep.driver.as.internal.consumptionmodes.entrybrowser;

import com.tibco.as.space.Tuple;
import com.tibco.cep.driver.as.internal.consumptionmodes.IASConsumptionMode;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

class TupleDispatchingTask implements Runnable {

	private IASConsumptionMode	consumptionMode;
	private Tuple				tuple;
	private Logger				logger;

	TupleDispatchingTask(IASConsumptionMode consumptionMode, Tuple tuple, Logger logger) {
		this.consumptionMode = consumptionMode;
		this.tuple = tuple;
		this.logger = logger;
	}

	@Override
	public void run()
	{
		try
		{
			consumptionMode.consume(tuple);
		}
		catch (Exception ex)
		{
			logger.log(Level.ERROR, ex, "ConsumptionMode consume tuple failed.");
		}
	}

}