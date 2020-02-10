package com.tibco.cep.driver.as.internal.consumers;

import com.tibco.cep.driver.as.IASDestination;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleSession;

public interface IASPayloadConsumer
{

	/**
	 * Handle space-event if it's the interesting one.
	 * @param payload
	 * @param dest
	 * @param session
	 * @param logger
	 * @return whether payload is consumed or not, returns false by default.
	 * @throws Exception 
	 */
	boolean consume(
			Object payload,
			IASDestination dest,
			RuleSession session,
			Logger logger) throws Exception;
}
