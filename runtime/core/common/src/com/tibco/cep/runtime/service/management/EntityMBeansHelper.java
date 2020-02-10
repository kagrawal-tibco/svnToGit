package com.tibco.cep.runtime.service.management;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.management.exception.BEMMUserActivityException;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Jan 25, 2010
 * Time: 11:32:48 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class EntityMBeansHelper {
    protected String agentName;

    protected Logger logger;
    protected static final String BE_HOME_PROP = "tibco.env.BE_HOME";
    protected static final String USER_DIR = "user.dir";


    /** Returns this agent's session name
     * @param cacheAgent BE agent instance whose rule session name is to be obtained
     * @return  This agent's rule session name, which is the "key" provided by the user in the CDD file (if specified),
     *          or the name of the reference class (previously called AgentGroup name)
     * @throws  RuntimeException if this method is called by a process object or by an unexpected agent type */
    protected String getSessionName(CacheAgent cacheAgent) {
        try {
            //This situation occurs when it's an agent running In-Memory mode
            if (agentName != null)
                return agentName;

            RuleSession ruleSession;
            Class cacheAgentClass = cacheAgent.getClass();
            Class queryAgentClass=null;
            Class inferenceAgentClass=null;
            Class dashboardAgentClass=null;

            //this block of code is put here just for clarity and to be easier to extend in the future. It could go in the
            //block of code corresponding to the inference agent.
            try {
                dashboardAgentClass = Class.forName("com.tibco.cep.dashboard.integration.be.DashboardAgent");
                if ( dashboardAgentClass.isAssignableFrom(cacheAgentClass) ) {
                    return ((InferenceAgent)cacheAgent).getRuleSession().getName();
                }
            } catch (ClassNotFoundException e) {
                //ignore it is not a Dashboard Agent
            }

            try {
                inferenceAgentClass = Class.forName(InferenceAgent.class.getName());
                if (inferenceAgentClass.isAssignableFrom(cacheAgentClass))
                    return ((InferenceAgent)cacheAgent).getRuleSession().getName();
            } catch (ClassNotFoundException e) {
                //ignore it is not an Inference Agent or Cache Server
            }

            try {
                queryAgentClass = Class.forName("com.tibco.cep.query.stream.impl.rete.service.QueryAgent");
                if ( queryAgentClass.isAssignableFrom(cacheAgentClass) ) {
                    ruleSession = (RuleSession)queryAgentClass.getMethod("getRuleSession").invoke(cacheAgent);
                    return ruleSession.getName();
                }
            } catch (ClassNotFoundException e) {
                //ignore it is not a Query Agent
            }

        } catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }

        logger.log(Level.WARN, "Unexpected agent type found for agent class: %s", cacheAgent.toString());
        return null;
    }

    /** Returns an array with every active rule session, or with the rule session active for the given sessionName.
     * @param  ruleServiceProvider This agent's rule service provider
     * @param  sessionName  session from which to retrieve the rule session (Optional). If not provided, iterates over every
     *          session
     * @return Array with rule sessions. If a valid session name is provided the array contains only one element. 
     * @throws BEMMUserActivityException     if invalid session name provided */
    protected RuleSession[] getRuleSessions(RuleServiceProvider ruleServiceProvider, String sessionName) throws BEMMUserActivityException {
        RuleSession[] sessions;
            if ((null == sessionName) || "".equals(sessionName.trim())) {
                sessions = ruleServiceProvider.getRuleRuntime().getRuleSessions();
            } else {
                final RuleSession s = ruleServiceProvider.getRuleRuntime().getRuleSession(sessionName);
                if (null == s) {
                    throw new BEMMUserActivityException("Invalid session name: " + sessionName);
                }
                sessions = new RuleSession[]{s};
            }
        return sessions;
    } //getRuleSessions

    protected CacheAgent.Type getAgentType(RuleSession session) {
        Class sessionClass = session.getClass();
        Class dashboardClass = null;
        Class queryClass = null;
        try {
            try { //Query Agent
                queryClass = Class.forName("com.tibco.cep.query.service.impl.QueryRuleSessionImpl");
                if ( queryClass.isAssignableFrom(sessionClass))
                    return CacheAgent.Type.QUERY;
            } catch (ClassNotFoundException e) {
                //ignore. It means it is not a Query agent.
            }
            try { //Dashboard Agent
                dashboardClass = Class.forName("com.tibco.cep.dashboard.integration.be.DashboardSessionImpl");
                if (dashboardClass.isAssignableFrom(sessionClass))
                   return CacheAgent.Type.DASHBOARD;
             } catch (ClassNotFoundException e) {
                //ignore. It means it is not a Dashboard agent.
            }
            //it is either inference or cache-server...
            if (session instanceof RuleSessionImpl) {
                if(session.getRuleServiceProvider().isCacheServerMode())
                    return CacheAgent.Type.CACHESERVER;
                else
                    return CacheAgent.Type.INFERENCE;
            } else {
                logger.log(Level.WARN, "Unexpected agent type found for session %s", session.getName());
                return CacheAgent.Type.UNDEFINED;
            }
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    } //getAgentType

    /** Checks if the input argument is null or empty string. In case it is throws exception with the message referenced
     * by parameter outMsg
     * @param inputArg input argument to validate
     * @param outMsg message to attach to exception if input is null or empty
     * @throws BEMMUserActivityException If inputArg is empty or null. Message is defined in outMsg
     **/
    protected static void validateInputArg(String inputArg, String outMsg) throws BEMMUserActivityException {
        if (inputArg == null || inputArg.trim().equals(""))
            throw new BEMMUserActivityException(outMsg);
    }

    /** creates the directory specified in 'dir' and returns its absolute path. Returns null if directory creation fails.
     * @param dir path of the directory to be created.
     * @return the absolute path to the directory specified in 'dir', or null if directory creation fails
     */
    protected String createDir(String dir) throws BEMMUserActivityException {
        return createDir(new File(dir));
    }

    /** creates the directory specified in 'file' and returns its absolute path. Returns null if directory creation fails.
     * @param dirFile path of the directory to be created.
     * @return the absolute path to the directory specified in 'dir', or null if directory creation fails
     */
    protected String createDir(File dirFile) throws BEMMUserActivityException {
        try {
            if (!dirFile.exists()) {  // if dir does not exist, creates it.
                if ( dirFile.mkdirs() ) {
                    logger.log(Level.INFO, "Created directory: " + dirFile.getAbsolutePath());
                } else {
                    logger.log(Level.WARN, "Creation of directory: \'" + dirFile.getAbsolutePath() + "\' FAILED");
                    return null;
                }
            }
            return dirFile.getAbsolutePath();
        } catch (Exception e) {
            throw new BEMMUserActivityException("Error creating directory: \'" + dirFile + "\' .Check permissions.", e);
        }
    }

    /** returns the relative path of the file or directory specified in 'path' */
    protected static String getRelPath(String path) {
        return new File(path).getPath();
    }

    /** returns the absolute path of the file or directory specified in 'path' */
    protected static String getAbsolutePath(String path) {
        return new File(path).getAbsolutePath();
    }

    /** Creates the directory specified in 'defaultDir'. If successful returns its path. Otherwise returns the path of
     * the user's current directory.
     * @param defaultDir path of the directory to be created.
     * @return the absolute path to the directory specified in 'defaultDir', if the directory is successfully created.
     * Otherwise returns the path to the user's current directory.
     */
    protected String createDefaultDir(String defaultDir) throws BEMMUserActivityException {
        String defDir = createDir(defaultDir);
        if (defDir != null)
            return defDir;
        else
            return System.getProperty(USER_DIR);
    }

} //class
