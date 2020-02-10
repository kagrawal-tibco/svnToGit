package com.tibco.cep.driver.as.internal.consumptionmodes.router;

import java.util.Collection;

import com.tibco.as.space.impl.data.router.ASRouterActionResult;
import com.tibco.as.space.router.Router;
import com.tibco.as.space.router.RouterActionResult;
import com.tibco.as.space.router.RouterAlterAction;
import com.tibco.as.space.router.RouterCloseAction;
import com.tibco.as.space.router.RouterOpenAction;
import com.tibco.as.space.router.RouterWriteAction;
import com.tibco.as.space.router.RouterWriteOp;
import com.tibco.cep.driver.as.internal.consumptionmodes.IASConsumptionMode;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class ASRouter implements Router {
	
	private IASConsumptionMode	mode;
	protected Logger			logger;

	public ASRouter (IASConsumptionMode mode, Logger logger) {
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
	

	@Override
	public RouterActionResult onAlter(RouterAlterAction arg0) {
		return RouterActionResult.create();
	}

	@Override
	public RouterActionResult onClose(RouterCloseAction arg0) {
		return RouterActionResult.create();
	}

	@Override
	public RouterActionResult onOpen(RouterOpenAction arg0) {
		return RouterActionResult.create();
	}

	@Override
	public RouterActionResult onWrite(RouterWriteAction writerAction) {
		Collection<RouterWriteOp> ops = writerAction.getOps();
		for (RouterWriteOp routerWriteOp : ops) {
				logger.log(Level.INFO, routerWriteOp.getType().toString());
				onMessage(routerWriteOp);
		}
		return new ASRouterActionResult();
	}
	
}
