package com.tibco.cep.runtime.service.management;

import java.io.File;
import java.util.Properties;

import com.tibco.cep.kernel.core.rete.ReteListener;
import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.profiler.CSVWriter;
import com.tibco.cep.kernel.service.profiler.SimpleProfiler;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.management.exception.BEMMException;
import com.tibco.cep.runtime.service.management.exception.BEMMUserActivityException;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Mar 8, 2010
 * Time: 11:55:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProfilerMethodsImpl extends EntityMBeansHelper{
    protected RuleServiceProvider ruleServiceProvider;
    private static String PROFILER_RELATIVE_DIR = "/mm/logs/profiler";

    //Profiler
    public void StartFileBasedProfiler(String sessionName, String fPath, int level, long duration) throws Exception {
        try {
            sessionName = sessionName.trim();
            fPath = fPath.trim();

            RuleSession ruleSession = getAndValidateRuleSession(sessionName, "Please provide the name of the inference " +
                                                                             "session you want to profile.");
                    
            ReteWM wm = (ReteWM)((RuleSessionImpl)ruleSession).getWorkingMemory();
            ReteListener profiler = wm.getReteListener(SimpleProfiler.class);

            if (profiler != null && profiler.isOn()) { // Profiler is already running
                throw new BEMMUserActivityException(getMessage("Profiler was already started for session",ruleSession, profiler));
            } else {
                // Profiler is not set or previous profiler ended at the end of 'duration'. Re-start profiler...
                Properties beProps = ruleServiceProvider.getProperties();
                String defaultDir = beProps.getProperty(BE_HOME_PROP,USER_DIR);
                defaultDir+= PROFILER_RELATIVE_DIR;
                fPath = getRelPath(fPath);      //normalize the file separator to the running OS
                String absPath = "";            //Absolute path to where profiling data is to be saved

                //no file name or dir specified. Saves to the default directory using the file name pattern bellow
                if (fPath == null || fPath.length() == 0 ) {
                    defaultDir = createDefaultDir(defaultDir);
                    String sName = sessionName.replace(' ', '_');
                    absPath = new StringBuilder(defaultDir).append(File.separator).append(CSVWriter.DEFAULT_FILE_PREFIX)
                                                            .append('_').append(sName).append(".csv").toString();
                }
                else if (fPath.indexOf(File.separator)!=-1) {
                    //if it has a '\' or '/' then the directory path was specified.
                    // if the word after the last '\' or '/' has a '.' it means it's a file name. In that case
                    //everything up to last / or \ is used as the directory path. If directory does not exist, creates it.
                    String dir =fPath;
                    String fileName ="";
                    if ( fPath.substring(fPath.lastIndexOf(File.separator)+1,fPath.length()).contains(".") ) {
                        dir = fPath.substring(0, fPath.lastIndexOf(File.separator));
                        fileName = fPath.substring(fPath.lastIndexOf(File.separator)+1,fPath.length());
                    }

                    dir = createDir(dir);
                    // if user specified dir cannot be created, tries creating default dir. If it fails uses current dir.
                    if (dir == null) {
                        dir = createDefaultDir(defaultDir);
                    }

                    //file name not specified, uses default file name
                    if(fileName.trim().equals("")) {
                        String sName = sessionName.replace(' ', '_');
                        fileName = new StringBuilder(CSVWriter.DEFAULT_FILE_PREFIX).append('_')
                                .append(sName).append(".csv").toString();
                    }
                    absPath = new StringBuilder(dir).append(File.separator).append(fileName).toString();
                } else { //Only the file name was specified. Put file under the default dir.
                    defaultDir = createDefaultDir(defaultDir);
                    absPath = new StringBuilder(defaultDir).append(File.separator).append(fPath).toString();
                }

                if (level != 0) { //level is specified by user
                    profiler = new SimpleProfiler(absPath, level, duration, 0,beProps.getProperty("be.engine.profile.delimiter"),
                                                  ruleServiceProvider.getLogger(SimpleProfiler.class));
                }
                else { //if level is not specified, put -1 as default
                    profiler = new SimpleProfiler(absPath, -1, duration, 0,beProps.getProperty("be.engine.profile.delimiter"),
                                                  ruleServiceProvider.getLogger(SimpleProfiler.class));
                }

                logger.log(Level.INFO,"Starting profiler for rule session: \'" + sessionName + "\'");
                wm.addReteListener(profiler);
                profiler.on();
                logger.log(Level.INFO,getMessage("Profiling is enabled for rule session",ruleSession, profiler));
                logger.log(Level.INFO, "Saving profiler files to: " + absPath);
            }
        } //try
        catch (BEMMException bemme) {
            logger.log(Level.WARN, bemme.getMessage());
            throw bemme;
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw e;
        }
    } //startFileBasedProfiler

    private String getMessage(String msg, RuleSession ruleSession, ReteListener profiler) {

        if( profiler instanceof SimpleProfiler ) {
        return msg+": \'" + ruleSession.getName() + "\'." +
                                   "\nFile Name: " + ((SimpleProfiler)profiler).getFileName() +
                                   "\nLevel: " + ((SimpleProfiler)profiler).getLevel() +
                                   "\nDuration: " + ((SimpleProfiler)profiler).getDuration();
        }
        return null;
    }

    private RuleSession getAndValidateRuleSession(String sessionName, String msg) throws BEMMUserActivityException {
        RuleSession ruleSession;
        RuleSession[] ruleSessions = getRuleSessions(ruleServiceProvider, sessionName);
        if (ruleSessions.length > 1) {
            throw new BEMMUserActivityException("There are " + ruleSessions.length + " rule sessions running. " + msg);
        } else {
            ruleSession = ruleSessions[0];
        }

        if (getAgentType(ruleSession) != CacheAgent.Type.INFERENCE) {
                throw new BEMMUserActivityException("Profiling is available only for BE inference agents. Session \'" +
                        sessionName + "\' is of type " + getAgentType(ruleSession).name() +
                        " .Please provide a valid inference session name.");
        }
        return ruleSession;
    }

    public void StopFileBasedProfiler(String sessionName) throws Exception {
        try {
            sessionName = sessionName.trim();
            RuleSession ruleSession = getAndValidateRuleSession(sessionName, "Please provide the name of the inference session " +
                                                                             "you want to stop profiling.");
            
            ReteWM wm = (ReteWM)((RuleSessionImpl)ruleSession).getWorkingMemory();
            ReteListener profiler = wm.getReteListener(SimpleProfiler.class);

            if(profiler != null) {
                logger.log(Level.INFO,"Stopping profiler for rule session " + sessionName);
                profiler.off();
                logger.log(Level.INFO, getMessage("Profiler SUCCESSFULLY Stopped for rule session", ruleSession, profiler));
                wm.removeReteListener(profiler);
            } else {
                throw new BEMMUserActivityException("Cannot stop profiler for rule session: \'"
                        + ruleSession.getName() + "\' because no profiler instance is running for this session.");
            }
        }//try
       catch (BEMMException bemme) {
            logger.log(Level.WARN, bemme.getMessage());
            throw bemme;
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw e;
        }
    } //stopFileBasedProfiler
}
