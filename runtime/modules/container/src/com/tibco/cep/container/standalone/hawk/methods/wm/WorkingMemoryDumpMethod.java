package com.tibco.cep.container.standalone.hawk.methods.wm;


import java.util.ArrayList;

import com.tibco.be.util.NVPair;
import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.container.standalone.hawk.methods.util.Command;
import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Nov 14, 2006
 * Time: 5:31:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkingMemoryDumpMethod implements Command {


    private HawkRuleAdministrator ruleAdmin;


    public WorkingMemoryDumpMethod(HawkRuleAdministrator ruleAdministrator) {
        this.ruleAdmin = ruleAdministrator;
    }


    public NVPair[] invoke(RuleServiceProvider rsp, String inputParams) throws Exception {
        RuleSession[] sessions;
        if ((null == inputParams) || "".equals(inputParams.trim())) {
            sessions = this.ruleAdmin.getServiceProvider().getRuleRuntime().getRuleSessions();
        } else {
            final RuleSession s = this.ruleAdmin.getServiceProvider().getRuleRuntime().getRuleSession(inputParams);
            if (null == s) {
                throw new Exception("Invalid session name: " + inputParams);
            }
            sessions = new RuleSession[]{s};
        }

        final ArrayList lines = new ArrayList();

        for (int i = 0; i < sessions.length; i++) {
            final RuleSessionImpl session = (RuleSessionImpl) sessions[i];
            lines.add(new NVPair(session.getName(), ((ReteWM) session.getWorkingMemory()).getMemoryDump().toString()));
        }//for

        return (NVPair[]) lines.toArray(new NVPair[lines.size()]);
    }
}
