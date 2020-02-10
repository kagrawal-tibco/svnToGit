package com.tibco.cep.container.standalone.hawk.methods.profiler;


import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.kernel.core.rete.ReteListener;
import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.profiler.SimpleProfiler;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;


/**
 * Created by IntelliJ IDEA.
 * User: kpang
 * Date: May 22, 2008
 * Time: 2:42:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class StopFileBasedProfiler  extends AmiMethod {


    protected HawkRuleAdministrator m_hma;


    public StopFileBasedProfiler(HawkRuleAdministrator hma) {

        super("stopFileBasedProfiler",
                "Turns off the BusinessEvents Profiler and writes the profile data into a file specified when the Profiler was turned on.",
                AmiConstants.METHOD_TYPE_ACTION);
        this.m_hma = hma;
    }//constr

    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Session", "Name of the Session", ""));
        return args;
    }

     public AmiParameterList getReturns() {
        return null;
    }

    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final String sessionName = inParams.getString(0);
            RuleSession ruleSession = null;
            if (sessionName == null || sessionName.trim().length() == 0) {
                RuleSession[] ruleSessions = this.m_hma.getServiceProvider().getRuleRuntime().getRuleSessions();
                if (ruleSessions.length == 1) {
                    ruleSession = ruleSessions[0];
                } else { // multiple sessions, user needs to provide session name
                    throw new Exception("Please provide a session name.");
                }
            } else {
                ruleSession = this.m_hma.getServiceProvider().getRuleRuntime().getRuleSession(sessionName);
                if (ruleSession == null) {
                    throw new Exception("Please provide a valid session name.");
                }
            }

            if (!ruleSession.getClass().isAssignableFrom(com.tibco.cep.runtime.session.impl.RuleSessionImpl.class)) {
                    throw new Exception("Profiling is only for BE inference agent (rule session).");
            }

            ReteWM wm = (ReteWM)((RuleSessionImpl)ruleSession).getWorkingMemory();
            SimpleProfiler profiler = (SimpleProfiler) wm.getReteListener(SimpleProfiler.class);

            if(profiler != null) {
                profiler.off();
                wm.removeReteListener(profiler);
            } else {
                this.m_hma.getLogger().log(Level.WARN,
                        "Trying to turn off Profiler for rule session: %s while it was not turned on.",
                        ruleSession.getName());
                throw new Exception("Trying to turn off Profiler for rule session: "
                        + ruleSession.getName() + " while it was not turned on.");
            }

        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
        return null;
    }//onInvoke
}
