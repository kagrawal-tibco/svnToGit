package com.tibco.cep.driver.as.internal.consumers;

import com.tibco.as.space.Tuple;
import com.tibco.cep.driver.as.IASDestination;
import com.tibco.cep.driver.as.internal.ASMessageContext;
import com.tibco.cep.driver.as.internal.fillers.IASTupleFiller;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.session.RuleSession;

abstract class AASPayloadConsumer implements IASPayloadConsumer {

	private IASTupleFiller	filler;

	protected AASPayloadConsumer (IASTupleFiller filler) {
        this.filler = filler;
    }

    protected final boolean onMessage(Tuple tuple, IASDestination dest, RuleSession session, Logger logger) throws Exception
	{
    	// Fill up tuple with consumptionMode and eventType
		filler.fill(tuple);
		
		// construct context
		EventContext ctx = new ASMessageContext(dest, tuple);

        try {
            session.getTaskController().processEvent(dest, null, ctx);
            //Count only if this server received the message.
            dest.getStats().addEventIn();
        }
        catch (Throwable e) {
            logger.log(Level.ERROR, e, "onMessage() failed.");
        }
        return false;
	}

}