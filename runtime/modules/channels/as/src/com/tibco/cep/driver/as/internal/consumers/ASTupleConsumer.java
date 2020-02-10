package com.tibco.cep.driver.as.internal.consumers;

import com.tibco.as.space.Tuple;
import com.tibco.cep.driver.as.IASDestination;
import com.tibco.cep.driver.as.internal.fillers.IASTupleFiller;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleSession;

public class ASTupleConsumer extends AASPayloadConsumer {

	public ASTupleConsumer (IASTupleFiller filler) {
        super(filler);
    }

	@Override
	public boolean consume(
			Object payload,
			IASDestination dest,
			RuleSession session,
			Logger logger) throws Exception {
		boolean consumed = false;
		consumed = onMessage((Tuple) payload, dest, session, logger);
		return consumed;
	}

}