package com.tibco.cep.bemm.management.functions;

import java.io.File;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;

import javax.security.auth.login.LoginException;

import com.tibco.be.bemm.functions.UtilFunctions;
import com.tibco.be.monitor.thread.Connector;
import com.tibco.cep.bemm.management.util.XMLHandler;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.service.management.exception.BEMMIllegalSetupException;
import com.tibco.cep.runtime.service.management.exception.BEMMInvalidAccessException;
import com.tibco.cep.runtime.service.management.exception.BEMMInvalidUserRoleException;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Jan 29, 2010
 * Time: 12:14:17 PM
 * To change this template use File | Settings | File Templates.
 */


public class RemoteThreadAnalyzer extends RemoteMngmtProvider {

    private static String THREAD_ANALYZER_RELATIVE_DIR = "/mm/logs/thread-analyzer";
    private static String THREAD_ANALYZER_MAX_NUM_LOGS_PROP = "be.threadanalyzer.log.maxnum";
    private static String THREAD_ANALYZER_LOG_SIZE_PROP = "be.threadanalyzer.log.maxsize";
    private static int THREAD_ANALYZER_DEFAULT_LOG_SIZE = 1024*1024*10;   //10MB
    private static int THREAD_ANALYZER_DEFAULT_MAX_NUM_LOGS = 10;
    private static final String BE_HOME_PROP = "tibco.env.BE_HOME";
    private static final String USER_DIR = "user.dir";


    public static String startThreadAnalyzer( String entityType, String monitoredEntityName,
                                      String[] properties, String threadReportDir, String samplingInterval,
                                      String username, String encodedPwd ) {


        String host ="";
        int port =-1;
        final String methodFQName = "/"+entityType+"/startThreadAnalyzer";

        try{
            //Decode to support multibyte and multilingual characters
            UtilFunctions.urlDecodeArray(properties, "UTF-8");
            threadReportDir = URLDecoder.decode(threadReportDir, "UTF-8");
            samplingInterval = URLDecoder.decode(samplingInterval, "UTF-8");
            username = URLDecoder.decode(username, "UTF-8");
            final String decodedPwd = UtilFunctions.decodeBase64Pwd( URLDecoder.decode(encodedPwd, "UTF-8") );

            host = getTopologyProps(entityType, monitoredEntityName, properties).getHostName();
            port = getTopologyProps(entityType, monitoredEntityName, properties).getPort();
            final String processInfo = properties[1];
            String dir;

            //For now threadReportDir is always going to be null or "" because we just want to use the standard directory.
            if (threadReportDir != null && threadReportDir.trim().length() != 0 && !threadReportDir.trim().equals("\"\"") ) {
                dir = threadReportDir;          //user specified a directory location
            } else {
                dir = rsp.getProperties().getProperty(BE_HOME_PROP,USER_DIR);
                dir= dir + THREAD_ANALYZER_RELATIVE_DIR;
            }

            File file = new File(dir);
            if (!file.exists()) {
                if ( new File(dir).mkdirs() )
                    logger.log(Level.DEBUG, "Created directory: " + dir);
                else
                    logger.log(Level.DEBUG, "Creation of directory: " + dir + " failed");
            }

            Connector.setDir(dir);

            Connector.setMaxNumLogs(rsp.getProperties().
                            getProperty(THREAD_ANALYZER_MAX_NUM_LOGS_PROP,String.valueOf(THREAD_ANALYZER_DEFAULT_MAX_NUM_LOGS)));

            Connector.setLogSize(rsp.getProperties().
                            getProperty(THREAD_ANALYZER_LOG_SIZE_PROP,String.valueOf(THREAD_ANALYZER_DEFAULT_LOG_SIZE)) );

            StringBuilder connectionParams = new StringBuilder();
            connectionParams.append(host).append(":").append(port);

            if ( username != null && !username.trim().equals("") && !username.trim().equals("\"\"") ) {
                connectionParams.append(":").append(username).append(":").append(decodedPwd);
            }

            if (UtilFunctions.getInstance().isAuthorized(username, decodedPwd)){
                logger.log(Level.INFO, "Starting Thread Analyzer for process: " + processInfo + " @"+host+":"+port);
                Connector.startThreadAnalyzer(samplingInterval, connectionParams.toString());
                logger.log(Level.INFO, "Thread Analyzer collecting information and storing it in file: " + Connector.getLoggerName(host, port));
                return XMLHandler.successXML(methodFQName,"true");
            } else {
                throw new Exception("Thread Analyzer FAILED to start @"+host+":"+port);
            }

        } //try
        catch(Exception e) {
           String msg="";
           if ( (msg = UtilFunctions.getClassExceptionMsg(e, LoginException.class)) != null ||
                (msg = UtilFunctions.getClassExceptionMsg(e, BEMMInvalidAccessException.class)) != null ||
                (msg = UtilFunctions.getClassExceptionMsg(e, BEMMInvalidUserRoleException.class)) != null ||
                (msg = UtilFunctions.getClassExceptionMsg(e, GeneralSecurityException.class)) != null ) {

              logger.log(Level.WARN, msg);
              return XMLHandler.warningXML(methodFQName, "FAILED!\n\n" + msg);

           } else if (e.getClass().equals(BEMMIllegalSetupException.class)) {
               logger.log(Level.ERROR, e.getMessage());
               return XMLHandler.errorXML(methodFQName, e.getMessage(), null /*todo error code */);
           } else {  //studio does not allow catalog functions that throw checked exceptions, so convert checked except to runtime exception
               logger.log(Level.ERROR, e, e.getMessage());
               throw new RuntimeException(e.getMessage(), e);
           }
       }
    }

    public static String stopThreadAnalyzer (String entityType, String monitoredEntityName, String[] properties,
                                             String username, String encodedPwd) {

        String host ="";
        int port =-1;
        final String methodFQName = "/"+entityType+"/stopThreadAnalyzer";
        try {

             //Decode to support multibyte and multilingual characters
            UtilFunctions.urlDecodeArray(properties, "UTF-8");
            username = URLDecoder.decode(username, "UTF-8");
            final String decodedPwd = UtilFunctions.decodeBase64Pwd( URLDecoder.decode(encodedPwd, "UTF-8") );

            host = getTopologyProps(entityType, monitoredEntityName, properties).getHostName();
            port = getTopologyProps(entityType, monitoredEntityName, properties).getPort();

            if (UtilFunctions.getInstance().isAuthorized(username, decodedPwd)) {
                logger.log(Level.DEBUG, "Stopping Thread Analyzer @"+host+":"+port);
                Connector.stopThreadAnalyzer(host,port,username,decodedPwd);
                logger.log(Level.DEBUG, "Thread Analyzer successfully stopped");
                return XMLHandler.successXML(methodFQName,"true");
            } else {
                throw new Exception("Thread Analyzer FAILED to stop @"+host+":"+port);
            }
        } catch(Exception e) {
           String msg="";
           if ( (msg = UtilFunctions.getClassExceptionMsg(e, LoginException.class)) != null ||
                (msg = UtilFunctions.getClassExceptionMsg(e, BEMMInvalidAccessException.class)) != null ||
                (msg = UtilFunctions.getClassExceptionMsg(e, BEMMInvalidUserRoleException.class)) != null ||
                (msg = UtilFunctions.getClassExceptionMsg(e, GeneralSecurityException.class)) != null ) {

                logger.log(Level.WARN, msg);
                return XMLHandler.warningXML(methodFQName, "FAILED!\n\n" + msg);

           } else if (e.getClass().equals(BEMMIllegalSetupException.class)) {
               logger.log(Level.ERROR, e.getMessage());
               return XMLHandler.errorXML(methodFQName, e.getMessage(), null /*todo error code */);
           } else {  //studio does not allow catalog functions that throw checked exceptions, so convert checked except to runtime exception
               logger.log(Level.ERROR, e, e.getMessage());
               throw new RuntimeException(e.getMessage(), e);
           }
        }
    }

    /*public static void main (String[] args) throws BEMMIllegalSetupException {
//        startThreadAnalyzer(null, null, null, null);
        stopThreadAnalyzer(null, null, null);
    }*/

}
