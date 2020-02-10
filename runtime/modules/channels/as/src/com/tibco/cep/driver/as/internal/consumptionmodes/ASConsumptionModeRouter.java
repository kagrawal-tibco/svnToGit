package com.tibco.cep.driver.as.internal.consumptionmodes;


import java.util.Map;

import com.tibco.as.space.Space;
import com.tibco.as.space.router.Router;
import com.tibco.cep.driver.as.IASDestination;
import com.tibco.cep.driver.as.internal.consumers.IASPayloadConsumer;
import com.tibco.cep.driver.as.internal.consumptionmodes.router.ASRouter;
import com.tibco.cep.kernel.service.logging.Logger;

public class ASConsumptionModeRouter extends AASConsumptionMode {
	
	private Router router;

	ASConsumptionModeRouter(IASDestination asDest, Logger logger, IASPayloadConsumer... consumers) {
		super(asDest, logger, consumers);
	}

	@Override
	protected void doStart(Map<Object, Object> condition) throws Exception {
	    if (!initialized) {
		    Space space = asDest.getSpace();
            if (null != space) {
    			router = new ASRouter(this, logger);
    			space.setRouter(router);
    			initialized = true;
            }
	    }
	}

	@Override
	public void stop() throws Exception {
		Space space = asDest.getSpace();
		if (null != space) {
		    space.stopRouter(router);
		}
		super.stop();
	}

	@Override
	protected void doResume() throws Exception {
        Space space = asDest.getSpace();
        space.setRouter(router);
        isAttachedToSpace = true;
	}

}
