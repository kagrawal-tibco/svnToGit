package com.tibco.cep.driver.as.internal.consumers;

import com.tibco.as.space.router.RouterWriteOp;
import com.tibco.cep.driver.as.IASDestination;
import com.tibco.cep.driver.as.internal.fillers.IASTupleFiller;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleSession;

public class ASRouterConsumer extends AASPayloadConsumer {
	private RouterWriteOp.OpType optype;

	public ASRouterConsumer (IASTupleFiller filler, RouterWriteOp.OpType optype) {
        super(filler);
        this.optype = optype;
    }

	@Override
	public boolean consume(
			Object payload,
			IASDestination dest,
			RuleSession session,
			Logger logger) throws Exception {
		boolean consumed = false;
		RouterWriteOp rop = (RouterWriteOp)payload;
		if(optype.equals(rop.getType())){
			consumed = onMessage(rop.getTuple(), dest, session, logger);
		}
		return consumed;
	}

}