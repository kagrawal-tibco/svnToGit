package com.tibco.cep.bemm.management.functions;

import com.tibco.be.bemm.functions.RemoteMetricsCollector;
import com.tibco.be.bemm.functions.TopologyEntityProperties;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.management.exception.BEMMIllegalSetupException;
import com.tibco.cep.runtime.service.management.exception.JMXConnClientException;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnClient;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnUtil;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionManager;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class RemoteMngmtProvider {

    //Constants
    private static final String TOKEN = "#";
    private static final String PROCESS_OBJ_NAME_PATTERN = "com.tibco.be:dir=Methods,Group={0}";
    private static final String AGENT_OBJ_NAME_PATTERN = "com.tibco.be:type=Agent,agentId={0},dir=Methods,Group={1}";

    //create only one instance of this class when the class is loaded
    protected static RemoteMngmtProvider instance = new RemoteMngmtProvider();

    //The 'key' of the maps is the monitored entity key as returned by the method getMonitoredKey(..,..) 
    private static Map<String, JMXConnClient> userMonitoredToJMXConnClient = new HashMap<String, JMXConnClient>();
    private static Map<String, TopologyEntityProperties> userMonitoredToTopologyProps =
                                                                        new HashMap<String, TopologyEntityProperties>();

    protected static RuleServiceProvider rsp;
    protected static Logger logger;

    public RemoteMngmtProvider() {
        rsp = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
        logger = rsp.getLogger(this.getClass());
        //Necessary to allow the invocation of getter methods
        System.setProperty(JMXConnUtil.JAVA_PROPERTIES.JMX_INVOKE_GETTERS,"true");
    }

    //============== Establish Client Connection to Remote MBean Server ===============

    /** @return True if connection to remote MBean server successfully established, False otherwise*/
    protected static boolean establishRemoteMBeanConnection(String userMonitoredKey, String entityType, String monitoredEntityName,
                                                            String[] properties, ObjectName objectName, String user, String pwd)
                                                            throws IOException, BEMMIllegalSetupException, JMXConnClientException {
        boolean isConnectionValid=false;

        if (userMonitoredToJMXConnClient == null || !userMonitoredToJMXConnClient.containsKey(userMonitoredKey)) { //client connection not established
            establishJMXConnClient(userMonitoredKey, entityType, monitoredEntityName, properties, user, pwd);
            isConnectionValid = true;
        } else {   //client connection to connector server exists, but let's check if it is still valid
            try {
                isConnectionValid = getMBeanServerConnection(userMonitoredKey).isRegistered(objectName);
            } catch (IOException e) {
                isConnectionValid=false;
            }
        }
        if (!isConnectionValid) {
            removeJMXClientConnection(userMonitoredKey);   //connection no longer valid so remove the old connection handles

            establishJMXConnClient(userMonitoredKey, entityType, monitoredEntityName, properties, user, pwd);    //Get a new MBean server connection
                                                                // (it will get the new JMXConnector and the new properties)
            isConnectionValid = true;
        }
        return isConnectionValid;    //if an exception occurs it should return false
   }  //establishRemoteMBeanConnection


    private static void establishJMXConnClient(String userMonitoredKey, String entityType, String monitoredEntityName,
                                                                    String[] properties, String user, String pwd)
                                                 throws IOException, BEMMIllegalSetupException, JMXConnClientException {   //todo verify exceptions throw

        TopologyEntityProperties topologyProps = getTopologyProps(userMonitoredKey, monitoredEntityName, properties);

        JMXConnClient jmcc =  new JMXConnClient(topologyProps.getHostName(),
                                                topologyProps.getPort(),
                                                user, pwd,true);
        jmcc.connect();

        userMonitoredToJMXConnClient.put(userMonitoredKey, jmcc);
    } //establishJMXConnClient

    // ================== Topology Properties ==========================

    protected static TopologyEntityProperties getTopologyProps(String userMonitoredKey, String monitoredEntityName,
                                                               String[] properties) throws BEMMIllegalSetupException {

        if (!userMonitoredToTopologyProps.containsKey(userMonitoredKey))
            registerTopologyProps(userMonitoredKey, monitoredEntityName, properties);

        return userMonitoredToTopologyProps.get(userMonitoredKey);
    }

    private static void registerTopologyProps(String userMonitoredKey, String monitoredEntityName, String[] properties)
                                                                                    throws BEMMIllegalSetupException {

        TopologyEntityProperties entityProps = new TopologyEntityProperties(logger, monitoredEntityName, properties);

        if (!entityProps.parse(RemoteMetricsCollector.COLLECTOR_TYPE.JMX)) {
            throw new BEMMIllegalSetupException("Invalid properties. Possible causes are: \nThe JMX properties for BEMM in the " +
                    "be-engine.tra file are not set or are commented out. \nThe agent you are trying to manage is " +
                    "no longer active in the cluster");
        }
        userMonitoredToTopologyProps.put(userMonitoredKey, entityProps);
    }

    /** Remove entries from HashMaps and close the JMX Client connector to avoid exceptions to get printed in the console */
    protected static void removeJMXClientConnection(String userMonitoredKey) throws JMXConnClientException {
        logger.log(Level.INFO, "Removing JMX client connection for user monitored entity: %s",userMonitoredKey);

        try {
            JMXConnector jmxc = getJMXClientConnector(userMonitoredKey);
            if (jmxc == null)   // the key no longer exists in the map, so there is nothing to do
                    return;
            else
                jmxc.close();   //closes connection before removing keys from the maps
        } catch (IOException ignore) { //ignore
            //this exception can happen if the remote entity is no longer active when the method close() is called
        }
        userMonitoredToJMXConnClient.remove(userMonitoredKey);    //Remove JMX client connection to connector server because it is no longer valid
        userMonitoredToTopologyProps.remove(userMonitoredKey);    //Remove old properties to guarantee that the properties
                                                          // associated with this key are up to date
        logger.log(Level.INFO, "JMX client connection SUCCESSFULLY removed for monitored entity: %s", userMonitoredKey);
    }

    /** This method is exposed as catalog function. It is used in the MM Studio project to eliminate the JMXConnector
     * related exceptions that are printed in the console when BE engines are shutdown using MM*/
    public static void removeJMXClientConnection(String monitoredEntityName, String entityType) {
        try {
            //remove from the map the entries that each logged in 'user' has for this 'monitoredEntity'
            final String monitoredKey = getMonitoredKey(monitoredEntityName,entityType);

//            Set<String> keys = userMonitoredToJMXConnClient.keySet();
            // put the keys in an array to get rid of ConcurrentModificationException
            Object[] uKeys = userMonitoredToJMXConnClient.keySet().toArray();

            for (int i = 0; i < uKeys.length; i++) {
                String uKey = uKeys[i].toString();

                if (uKey.contains(monitoredKey)) {       //found one user monitoring this 'monitoredEntity'
                    //remove connection for that user, and remove entry from the map
                    removeJMXClientConnection(uKey);
                }
            }
        } catch (JMXConnClientException e) {
            //wrap checked exception as
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    // ========== Getter methods ===========

    /** Determines the key (monitored entity full path) which is a substring of the this class' Maps.
     *  The Maps' keys follow the pattern: MONITORED_ENTITY_FULL_PATH:USERNAME.
     *  @returns Map key: The entity full path up to and including the process ID if entity is of type 'process'.
     *                    If the entity is of type 'agent', it strips the agent name and agent ID from the entity
     *                    full path before returning.
     * */
    protected static String getMonitoredKey(String monitoredEntityName, String entityType) {
        if ( entityType.equals("process") )
            return monitoredEntityName;

        //agent node or one of its children selected
        if ( entityType.equals("inference") || entityType.equals("query") ||
                entityType.equals("cache") || entityType.equals("dashboard") ) {
            return monitoredEntityName.substring(0, monitoredEntityName.lastIndexOf(":"));
        } else {
            logger.log(Level.WARN, "Returning monitored entity key: '%s' for unexpected entity type: %s.",monitoredEntityName, entityType);
            return monitoredEntityName;
        }
    }

    /** Determines the key to be used in this class' Maps:
     *  The Maps' keys follow the pattern: MONITORED_ENTITY_FULL_PATH:USERNAME.
     *  USERNAME is the username used to login into the UI.
     *  @returns Map key:  The entity full path up to and including the process ID if entity is of type 'process',
     *                    followed by ':USERNAME'.
     *                    If the entity is of type 'agent', it strips the agent name and agent ID from the entity
     *                    full path before concatenating ':USERNAME'.
     * */
    protected static String getUserMonitoredKey(String monitoredEntityName, String entityType, String userName) {
        return getMonitoredKey(monitoredEntityName, entityType)+":"+userName;
    }

    protected static MBeanServerConnection getMBeanServerConnection(String userMonitoredKey) throws JMXConnClientException {
        JMXConnClient jmxcc = userMonitoredToJMXConnClient.get(userMonitoredKey);
        if (jmxcc!=null)
            return jmxcc.getMBeanServerConnection();
        else
            return null;
    }

    protected static String getMethodQualifiedName(String methodGroup, String methodName) {
           return methodGroup.toLowerCase() + TOKEN +  methodName.toLowerCase();
    }

    protected static JMXConnector getJMXClientConnector(String userMonitoredKey) throws JMXConnClientException {
        JMXConnClient jmxcc = userMonitoredToJMXConnClient.get(userMonitoredKey);
        if (jmxcc!=null)
            return jmxcc.getJMXConnector();
        else
            return null;
    }

    /** Returns the agentID if it exists, or -1 if entity is of type process
    *   @return agentID of the agent with the specified monitoredEntityName, or -1 for entities of type process */
    protected static int getAgentID(String monitoredEntityName, String entityType) {
        if ( entityType.equals("process") )
            return -1;

        //For entities of type agent, the monitoredEntityName has the agentID after the last # token.
        //There should be only one "#" token in the string, unless one of the names contains the "#" token.
        final String[] splitStr = monitoredEntityName.split(TOKEN);
        return Integer.parseInt(splitStr[splitStr.length-1]);
    }

    protected static ObjectName getObjName(int agentID, String entityType, String methodGroup) throws IOException {
        String objNamePattern=null;

        try {
            if ( entityType.equals("process") ) {   //process
                objNamePattern = PROCESS_OBJ_NAME_PATTERN;
                return new ObjectName(MessageFormat.format(objNamePattern, methodGroup));
            } else {    //agent
                objNamePattern = AGENT_OBJ_NAME_PATTERN;
                return new ObjectName(MessageFormat.format(objNamePattern, agentID, methodGroup));
            }
        } catch (MalformedObjectNameException e) {
            final String errMsg = "Object name pattern: " + objNamePattern + " is malformed";
//            logger.log(Level.ERROR, e, errMsg);
            throw new RuntimeException(errMsg,e);
        }
    }
} //class
