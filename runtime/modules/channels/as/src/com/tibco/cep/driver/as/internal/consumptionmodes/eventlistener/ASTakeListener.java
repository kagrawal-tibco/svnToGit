package com.tibco.cep.driver.as.internal.consumptionmodes.eventlistener;

import com.tibco.as.space.event.TakeEvent;
import com.tibco.as.space.listener.TakeListener;
import com.tibco.cep.driver.as.internal.consumptionmodes.IASConsumptionMode;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class ASTakeListener extends AASListener implements TakeListener {

	public ASTakeListener(IASConsumptionMode mode, Logger logger)
	{
		super(mode, logger);
	}

	public void onTake (TakeEvent event)
    {
        logger.log(Level.DEBUG,"ASTakeListener [%s] Take:%s", event.getSpaceName(), event.getTuple());
        onMessage(event);
    }

}
