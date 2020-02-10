package com.tibco.cep.container.standalone.hawk.methods.profiler;


import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.kernel.core.rete.ReteListener;
import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.profiler.CSVWriter;
import com.tibco.cep.kernel.service.profiler.SimpleProfiler;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/**
 * Created by IntelliJ IDEA.
 * User: kpang
 * Date: May 22, 2008
 * Time: 2:42:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class StartFileBasedProfiler  extends AmiMethod {


    protected HawkRuleAdministrator m_hma;


    public StartFileBasedProfiler(HawkRuleAdministrator hma) {

        super("startFileBasedProfiler", "Turns on BusinessEvents Profiler and starts collecting data for a specified duration,\n" +
            "at the end of the duration or when the Profiler is turned off before the end of the duration,\n" +
            "profiling data will be output to a file in comma seperated value format.", AmiConstants.METHOD_TYPE_ACTION);
        this.m_hma = hma;
    }//constr

    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Session", "Name of the Session", ""));
        args.addElement(new AmiParameter("FileName", "Name of the file that the profiling data is output to.", ""));
        args.addElement(new AmiParameter("Level", "Level of depth that profiling data will be collected. -1 for all level, 1 for only RTC level.", (int)-1));
        args.addElement(new AmiParameter("Duration", "Time duration in seconds that the profile data will be collected. If <= 0, Profiler will be on until explicitly turned off.", (long)-1L));
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
                    throw new Exception("Profiling is only for BE inference agent (inference rule session).");
            }

            ReteWM wm = (ReteWM)((RuleSessionImpl)ruleSession).getWorkingMemory();
            SimpleProfiler profiler = (SimpleProfiler) wm.getReteListener(SimpleProfiler.class);

            if (profiler != null && profiler.isOn()) { // Profiler is on
                this.m_hma.getLogger().log(Level.WARN,
                        "Trying to turn on Profiler for rule session: %s while it has already been turned on "
                        + "with File name: %s, level: %s, duration: %s",
                        ruleSession.getName(), profiler.getFileName(), profiler.getLevel(), profiler.getDuration());
                throw new Exception("Trying to turn on Profiler for rule session: " + ruleSession.getName() + " while it has already been turned on.");
            } else { // profiler == null || !profiler.isOn(), profiler is not set or previous profiler ended at end of duration. re-initialize profiler anyway.
                String fileName = inParams.getString(1).trim();
                if (fileName == null || fileName.length() == 0 ) {
                    String sName = ruleSession.getName().trim().replace(' ', '_');
                    fileName = new StringBuilder(CSVWriter.DEFAULT_FILE_PREFIX).append('_').append(sName).append(".csv").toString();
                }
                BEProperties beProps = ((BEProperties)this.m_hma.getServiceProvider().getProperties());
                String delim = beProps.getProperty("be.engine.profile.delimiter");
                profiler = new SimpleProfiler(fileName, inParams.getInteger(2), inParams.getLong(3), 0,delim, this.m_hma.getServiceProvider().getLogger(SimpleProfiler.class));
                wm.addReteListener(profiler);
                profiler.on();
                this.m_hma.getLogger().log(Level.INFO,
                        "Profiling is enabled for rule session %s, with file name: %s, level: %d, duration: %d",
                        ruleSession.getName(), profiler.getFileName(), profiler.getLevel(), profiler.getDuration());
            }
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
        return null;
    }//onInvoke
}
