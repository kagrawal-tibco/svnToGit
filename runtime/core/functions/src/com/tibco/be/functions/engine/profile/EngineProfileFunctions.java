package com.tibco.be.functions.engine.profile;


import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.kernel.service.profiler.CSVWriter;
import com.tibco.cep.kernel.service.profiler.SimpleProfiler;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/**
 * Created by IntelliJ IDEA.
 * User: kpang
 * Date: May 12, 2008
 * Time: 9:00:26 PM
 * To change this template use File | Settings | File Templates.
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Engine.Profiler",
        synopsis = "Functions to profile the engine.")
public class EngineProfileFunctions {

    @com.tibco.be.model.functions.BEFunction(
        name = "startCollectingToFile",
        synopsis = "Turns on the BusinessEvents Profiler and starts collecting data for a specified duration. "
                + "At the end of the duration or when the Profiler is turned off before the end of the duration, "
                + "profiling data will be output to a file in comma separated value format.",
        signature = "void startCollectingToFile(String fileName, int level, long duration)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fileName", type = "String", desc = "The name of output file that the Profiler writes to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "level", type = "int", desc = "If 1, only RTC level of profile data will be collected."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "duration", type = "long", desc = "Time duration in seconds that the profile data will be collected.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Turns on the BusinessEvents Profiler and starts collecting data for a specified duration. "
                + "At the end of the duration or when the Profiler is turned off before the end of the duration, "
                + "profiling data will be output to a file in comma separated value format.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void startCollectingToFile(String fileName, int level, long duration) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        final Logger logger = session.getRuleServiceProvider().getLogger(EngineProfileFunctions.class);
        if(session == null ) throw new RuntimeException("Current RuleSession is not set");
        if (session.getClass().isAssignableFrom(com.tibco.cep.runtime.session.impl.RuleSessionImpl.class)) {
            ReteWM wm = (ReteWM)((RuleSessionImpl) session).getWorkingMemory();

            SimpleProfiler profiler = (SimpleProfiler)wm.getReteListener(SimpleProfiler.class);
            if (profiler != null && profiler.isOn()) { // profiler is on
                logger.log(Level.WARN,
                        "Trying to turn on Profiler while it has already be turned on for rule session: " +
                        session.getName() + ", with File name: " + profiler.getFileName() + ", level: " + profiler.getLevel() + ", duration: " + profiler.getDuration());
            }
            else { // profiler == null || !profiler.isOn(), profiler is not set or previous profiler ended at end of duration. re-initialize profiler anyway.
                String sessionName = session.getName().trim().replace(' ', '_');
                if (fileName == null || fileName.trim().length() == 0 ) {
                    fileName = new StringBuilder(CSVWriter.DEFAULT_FILE_PREFIX).append('_').append(sessionName).append(".csv").toString();
                } else {
                    int index = fileName.lastIndexOf('.');
                    if (index != -1) {
                        fileName = new StringBuilder(fileName.substring(0, index)).append('_').append(sessionName).append(fileName.substring(index)).toString();
                    } else {
                        fileName = new StringBuilder(fileName).append('_').append(sessionName).toString();
                    }
                }
                BEProperties beProps = ((BEProperties)session.getRuleServiceProvider().getProperties());
                String delim = beProps.getProperty("be.engine.profile.delimiter");
                profiler = new SimpleProfiler(fileName, level, duration, 0,delim, session.getRuleServiceProvider().getLogger(SimpleProfiler.class));
                wm.addReteListener(profiler);
                profiler.on();
                logger.log(Level.INFO,
                        "Profiling is enabled for rule session " + session.getName()
                        + ", with File name: " + profiler.getFileName() + ", level: " + profiler.getLevel() + ", duration: " + profiler.getDuration());
            }
        } else {
            logger.log(Level.ERROR, "Profiling is only for BE inference agent (inference rule session).");
        }
    }

/*    @com.tibco.be.model.functions.BEFunction(
        name = "startCollectingToRuleFunction",
        synopsis = "Turns on the BusinessEvents Profiler and starts collecting data for a specified duration.\nProfile data will be output to a callback rulefunction in a string with comma separated value format\nin the end of the duration, or when the Profiler is turned off before the end of the duration.",
        signature = "void startCollectingToRuleFunction()",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleFunctionName", type = "String", desc = "The name of the callback rulefunction that the Profiler writes to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "level", type = "int", desc = "If 1, only RTC level of profile data will be collected."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "duration", type = "long", desc = "Time duration in seconds that the profile data will be collected.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Turns on the BusinessEvents Profiler and starts collecting data for a specified duration.\nProfile data will be output to a callback rulefunction in a string with comma separated value format\nin the end of the duration, or when the Profiler is turned off before the end of the duration.",
        cautions = "none",
        domain = "action",
        example = ""
    )
    public static void startCollectingToRuleFunction(String ruleFunctionName, int level, long duration) {
        RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
        if(session == null ) throw new RuntimeException("Current RuleSession is not set");

        ReteListener profiler = ((ReteWM)session.getWorkingMemory()).getReteListener();
        if (profiler == null) {
            profiler = new SimpleProfiler(ruleFunctionName, level, duration, 0, session.getRuleServiceProvider().getLogger());
            ((ReteWM)session.getWorkingMemory()).setReteListener(profiler);
            profiler.on();
        }
        else if (!profiler.isOn()) {
            profiler.on();
        } else {
            session.getRuleServiceProvider().getLogger().logWarn("Trying to turn on Profiler while it has already been turned on" +
                    "with File name: " + profiler.getFileName() + ", level: " + profiler.getLevel() + ", duration: " + profiler.getDuration());
        }
    }
*/
    @com.tibco.be.model.functions.BEFunction(
        name = "stopCollecting",
        synopsis = "Turns off the BusinessEvents Profiler and writes the profile data into a file specified\nwhen the Profiler was turned on.",
        signature = "void stopCollecting()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Turns off the BusinessEvents Profiler and writes the profile data into a file specified\nwhen the Profiler was turned on.",
        cautions = "Profiler is not for BE Query.",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void stopCollecting() {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        final Logger logger = session.getRuleServiceProvider().getLogger(EngineProfileFunctions.class);
        if(session == null ) throw new RuntimeException("Current RuleSession is not set");

        if (session.getClass().isAssignableFrom(com.tibco.cep.runtime.session.impl.RuleSessionImpl.class)) {
            ReteWM wm = (ReteWM)((RuleSessionImpl) session).getWorkingMemory();
            SimpleProfiler profiler = (SimpleProfiler)wm.getReteListener(SimpleProfiler.class);
            if(profiler != null) {
                if (profiler.isOn()) {
                    profiler.off();
                    logger.log(Level.INFO, "Profiling is turned off for rule session " + session.getName()
                            + ", data has been output to: " + profiler.getFileName());
                }
                wm.removeReteListener(profiler);
            } else {
                logger.log(Level.WARN, "Trying to turn off Profiler for rule session: " + session.getName() + " while it was not turned on");
            }
        } else {
            logger.log(Level.ERROR, "Profiling is not designed for BE query rule session.");
        }
    }
}
