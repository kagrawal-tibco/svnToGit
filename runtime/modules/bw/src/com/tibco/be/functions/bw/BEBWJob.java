package com.tibco.be.functions.bw;

import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 3, 2008
 * Time: 12:29:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEBWJob implements Runnable {

    RuleSession session;
    RuleFunction rfn;
    Object[] parameters;
    boolean bSync;

    BEBWJob(RuleSession session, RuleFunction rfn, Object[] parameters, boolean bSync) {
        this.session = session;
        this.rfn = rfn;
        this.parameters = parameters;
        this.bSync = bSync;
    }

    public void run() {
        try {
            ((RuleSessionImpl)session).invokeFunction(rfn, parameters, bSync);
        } catch (Exception ex) {
            ((WorkingMemoryImpl)((RuleSessionImpl)session).getWorkingMemory()).handleException(ex, "Exception while executing callback " + (rfn == null ? null : rfn.getSignature()));
        }
    }

    public Object getClosure() {
        return parameters[3];
    }
}
