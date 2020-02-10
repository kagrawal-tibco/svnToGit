package com.tibco.cep.container.standalone.hawk.methods.recorder;

import java.util.ArrayList;

import com.tibco.be.util.NVPair;
import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.container.standalone.hawk.methods.util.Command;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.util.FileBasedRecorder;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 13, 2007
 * Time: 4:04:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class StopFileBasedRecorder implements Command {
    private HawkRuleAdministrator ruleAdmin;


    public StopFileBasedRecorder(HawkRuleAdministrator ruleAdministrator) {
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
            if(FileBasedRecorder.stop(session))
                lines.add(new NVPair(session.getName(), "Stopped recording"));
        }//for

        return (NVPair[]) lines.toArray(new NVPair[lines.size()]);
    }

}
