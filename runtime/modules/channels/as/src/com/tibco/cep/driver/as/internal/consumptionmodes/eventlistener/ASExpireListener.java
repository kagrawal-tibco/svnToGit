package com.tibco.cep.driver.as.internal.consumptionmodes.eventlistener;

import com.tibco.as.space.event.ExpireEvent;
import com.tibco.as.space.listener.ExpireListener;
import com.tibco.cep.driver.as.internal.consumptionmodes.IASConsumptionMode;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class ASExpireListener extends AASListener implements ExpireListener {

	public ASExpireListener(IASConsumptionMode mode, Logger logger)
	{
		super(mode, logger);
	}

    public void onExpire (ExpireEvent event)
    {
        logger.log(Level.DEBUG,"[" + event.getSpaceName() + "]" + " Expire:" + event.getTuple());
        onMessage(event);
    }

}
