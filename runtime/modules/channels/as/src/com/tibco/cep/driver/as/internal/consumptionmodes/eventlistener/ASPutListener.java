package com.tibco.cep.driver.as.internal.consumptionmodes.eventlistener;

import com.tibco.as.space.event.PutEvent;
import com.tibco.as.space.listener.PutListener;
import com.tibco.cep.driver.as.internal.consumptionmodes.IASConsumptionMode;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class ASPutListener extends AASListener implements PutListener {

	public ASPutListener(IASConsumptionMode mode, Logger logger)
	{
		super(mode, logger);
	}

    public void onPut (PutEvent event)
    {
        logger.log(Level.DEBUG,"ASPutListener [%s] Put:%s", event.getSpaceName(), event.getTuple());
        if (event.hasOldTuple())
        {
        	logger.log(Level.DEBUG,"ASPutListener Old: %s", event.getOldTuple());
        }
        onMessage(event);
    }

}