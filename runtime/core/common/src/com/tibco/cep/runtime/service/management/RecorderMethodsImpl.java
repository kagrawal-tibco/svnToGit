package com.tibco.cep.runtime.service.management;

import java.io.File;
import java.util.HashMap;

import javax.management.openmbean.TabularDataSupport;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.management.exception.BEMMException;
import com.tibco.cep.runtime.service.management.exception.BEMMUserActivityException;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.util.FileBasedRecorder;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Mar 8, 2010
 * Time: 11:56:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class RecorderMethodsImpl extends EntityMBeansHelper{
    protected RuleServiceProvider ruleServiceProvider;

    private static final String RECORDER_RELATIVE_DIR = "/mm/logs/recorder";
    private static String pattern = "c u d s r a x f e";

    private HashMap<String,Boolean> sessNameToRecording= new HashMap<String, Boolean>();
    private HashMap<String,String> sessNameToOutputDir = new HashMap<String, String>();
    private HashMap<String,String> sessNameToMode = new HashMap<String, String>();

    public TabularDataSupport  StartFileBasedRecorder(String sessionName, String outputDir, String mode) throws Exception {
        try {
            final String INVOKED_METHOD = "startFileBasedRecorder";
            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(INVOKED_METHOD);

            sessionName = sessionName.trim();
            outputDir = outputDir.trim();
            mode = mode.trim().toLowerCase();      //because Recorder code only recognizes lower case mode options.

            validateInputArg(sessionName, "The \'session name\' argument cannot be empty. Please provide a valid inference session name.");

            //check if recorder is already running
            if (sessNameToRecording.get(sessionName)!=null && sessNameToRecording.get(sessionName))
                throw new BEMMUserActivityException("Recorder was already started for session: \'" + sessionName + "\'\n" +
                        "Mode: " + sessNameToMode.get(sessionName) +
                        "\nOutput directory: " + sessNameToOutputDir.get(sessionName));
                
            validateInputArg(mode, "The \'mode\' argument cannot be empty. Please provide valid mode options separated by space ' '.");

            RuleSession ruleSession = getAndValidateRuleSession(sessionName, "Please provide the name of the inference " +
                                                                             "session you want to record.");

            final String tempMode = mode;
            if ( (mode=validateMode(mode)).equals("") )
                    throw new BEMMUserActivityException("Invalid mode: \'" + tempMode + "\'\n " +
                            "A valid mode is any string consisting of a space combination of the chars in \'" + pattern + "\'");

            //if dir not specified use default dir
            if( outputDir == null || outputDir.equals("") || !outputDir.contains(File.separator) /*not a dir*/
                                                          || (outputDir=createDir(outputDir)) == null) /*cannot create dir*/ {
                outputDir = ruleServiceProvider.getProperties().getProperty(BE_HOME_PROP,USER_DIR);
                outputDir+=RECORDER_RELATIVE_DIR;
                outputDir = createDefaultDir(outputDir);
                logger.log(Level.WARN, "User specified directory not valid. Using default directory: " + outputDir);
            }

            logger.log(Level.INFO, "Starting recorder for session \'" +sessionName+"\'");
            FileBasedRecorder.start((RuleSessionImpl)ruleSession, outputDir, mode);
            sessNameToRecording.put(sessionName,true);
            sessNameToOutputDir.put(sessionName,outputDir);
            sessNameToMode.put(sessionName, mode);
            logger.log(Level.INFO, "Recorder for session \'" + sessionName + "\' SUCCESSFULLY started\n"
                                    + "Mode: " + mode
                                    + "\nOutput directory: "+ outputDir);

            putStartFileBasedRecorderInfoInTableRow(tabularDataHandler, 0, ruleSession.getName(), outputDir, mode, "Recording");

            return tabularDataHandler.getTabularData(INVOKED_METHOD);
        } catch (BEMMException bemme) {
            logger.log(Level.WARN, bemme.getMessage());
            throw bemme;
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw e;
        }
    } //startFileBasedRecorder

    private static String validateMode(String mode) {
        String validMode ="";
        String[] modeSplit = mode.split(" ",-1);

        for(String m: modeSplit) {
            if (pattern.contains(m))
                validMode+=m+" ";
        }
        return validMode.trim();
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


    private void putStartFileBasedRecorderInfoInTableRow(MBeanTabularDataHandler tabularDataHandler, int row, String rsName,
                                                    String outputDir, String mode, String status) {
        Object[] itemValues = new Object[tabularDataHandler.getNumItems()];
        itemValues[0] = row;
        itemValues[1] = rsName;
        itemValues[2] = mode;
        itemValues[3] = outputDir;
        itemValues[4] = status;
        //ads current row to the table
        tabularDataHandler.put(itemValues);
    } //putStartFileBasedRecorderInfoInTableRow

    public TabularDataSupport StopFileBasedRecorder(String sessionName) throws Exception {
        try {
            sessionName = sessionName.trim();
            final String INVOKED_METHOD = "stopFileBasedRecorder";
            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(INVOKED_METHOD);

            validateInputArg(sessionName, "The \'session name\' argument cannot be empty. Please provide a valid inference session name.");

            RuleSession ruleSession = getAndValidateRuleSession(sessionName, "Please provide the name of the inference " +
                                                                             "session you want to stop recording.");

            if(sessNameToRecording.get(sessionName) == null || sessNameToRecording.get(sessionName) == false)
                throw new BEMMUserActivityException("Cannot stop recorder for rule session \'"
                    + ruleSession.getName() + "\' because no recorder instance is running for this session.");

            RuleSessionImpl rsi = (RuleSessionImpl) ruleSession;

            String msg="";
            String outDir = sessNameToOutputDir.get(sessionName);
            String mode = sessNameToMode.get(sessionName);

            logger.log(Level.INFO, "Stopping recorder for session: \'" +sessionName+"\'");
            if(FileBasedRecorder.stop(rsi)) {
                msg = "Recorder stopped";
                sessNameToRecording.remove(sessionName);
                sessNameToOutputDir.remove(sessionName);
                sessNameToMode.remove(sessionName);
            } else {
                msg = "FAILED to stop recorder";
            }

            logger.log(Level.INFO, msg + " for session: \'" +sessionName+"\'\n" +
                    "Mode: " + mode +
                    "\nOutput Directory: " + outDir);

            putStopFileBasedRecorderInfoInTableRow(tabularDataHandler, 0, rsi.getName(),mode,outDir,msg);

            return tabularDataHandler.getTabularData(INVOKED_METHOD);
        } catch (BEMMException bemme) {
            logger.log(Level.WARN, bemme.getMessage());
            throw bemme;
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw e;
        }
    } //stopFileBasedRecorder

    private void putStopFileBasedRecorderInfoInTableRow(MBeanTabularDataHandler tabularDataHandler, int row, String rsName,
                                                        String mode, String outputDir, String status) {
        Object[] itemValues = new Object[tabularDataHandler.getNumItems()];
        itemValues[0] = row;
        itemValues[1] = rsName;
        itemValues[2] = mode;
        itemValues[3] = outputDir;
        itemValues[4] = status;
        //ads current row to the table
        tabularDataHandler.put(itemValues);
    }

}
