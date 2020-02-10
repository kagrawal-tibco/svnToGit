package com.tibco.cep.driver.as.internal.consumers;

import com.tibco.as.space.Tuple;
import com.tibco.as.space.event.SpaceEvent;
import com.tibco.cep.driver.as.IASDestination;
import com.tibco.cep.driver.as.internal.fillers.IASTupleFiller;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleSession;

public class ASEventConsumer extends AASPayloadConsumer {

	private Class<?>		interestingEventTypeClazz;

	public ASEventConsumer (Class<?> interestingEventTypeClazz, IASTupleFiller filler) {
		super(filler);
        this.interestingEventTypeClazz = interestingEventTypeClazz;
    }

	@Override
	public boolean consume(
			Object payload,
			IASDestination dest,
			RuleSession session,
			Logger logger) throws Exception {
		boolean consumed = false;
		if (isMine(payload)) {
			SpaceEvent event = (SpaceEvent) payload;
			Tuple tuple = event.getTuple();
			consumed = onMessage(tuple, dest, session, logger);
		}
		return consumed;
	}

    private boolean isMine(Object payload) {
    	return interestingEventTypeClazz.isAssignableFrom(payload.getClass());
    }

}