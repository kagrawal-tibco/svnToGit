package com.tibco.cep.driver.as.internal.consumptionmodes.eventlistener;

import com.tibco.as.space.listener.Listener;
import com.tibco.cep.driver.as.internal.consumptionmodes.IASConsumptionMode;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public abstract class AASListener implements Listener {

	private IASConsumptionMode	mode;
	protected Logger			logger;

	protected AASListener (IASConsumptionMode mode, Logger logger) {
		this.mode = mode;
		this.logger = logger;
    }

    protected void onMessage(Object payload)
	{
    	try
		{
			mode.consume(payload);
		}
		catch (Exception ex)
		{
			logger.log(Level.ERROR, ex, "Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

}
