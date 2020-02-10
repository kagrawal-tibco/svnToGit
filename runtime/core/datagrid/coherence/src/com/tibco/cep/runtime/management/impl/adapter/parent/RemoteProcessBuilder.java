package com.tibco.cep.runtime.management.impl.adapter.parent;


import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitConfig;
import com.tibco.be.util.config.topology.CddConfigUtil;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.management.impl.adapter.RemoteClusterSpecialPropKeys;
import com.tibco.cep.runtime.management.impl.adapter.RemoteProcessManager;
import com.tibco.cep.runtime.management.impl.adapter.child.RemoteClusterBroker;
import com.tibco.cep.runtime.util.SystemProperty;

import java.io.File;
import java.util.*;

/*
* Author: Ashwin Jayaprakash Date: Mar 25, 2009 Time: 5:04:19 PM
*/
public class RemoteProcessBuilder {
    protected static final String[] OS_PATH_ALIASES =
            {"PATH", "LIBPATH", "SHLIB_PATH", "LD_LIBRARY_PATH"};
    
    protected final Properties properties;

    protected final Logger logger;

    //-------------

    protected String clusterName;

    protected Properties newSysProperties;

    protected int port;

    //-------------

    protected RemoteCoherenceClusterAdapter adapter;

    //-------------

    protected Process optionalRemoteProcess;

    protected RemoteProcessOutputReader optionalOutputReader;

    protected String masterCddPath;

    //-------------

    public RemoteProcessBuilder(Properties properties, Logger logger, 
                                String masterCddPath, String clusterName) {
        this.properties = properties;
        this.logger = logger;
        this.masterCddPath = masterCddPath;
        this.clusterName = clusterName;
    }

    public void firstStep() throws Exception {
        newSysProperties = null;
        port = 0;

        //-------------

        //Force the custom properties for this internal cluster.
        newSysProperties = new Properties(properties);


        //Read Coherence props from master cdd file and
        //add them to the new broker process properties list
        addMasterCddProps(RemoteClusterSpecialPropKeys.TANGOSOL_PREFIX);

        //Add broker properties to the new broker process properties list.
        //Broker properties are read from the MM.cdd (and converted to the new
        //property names for backwards compatibility) and from the Master CDD file.
        //Property values in the Master.cdd file will override props in MM.cdd
        addMmCddBrokerProps();
        addMasterCddProps(RemoteClusterSpecialPropKeys.BROKER_PREFIX);

        setRemClustBrokerMinNumProps();

        setBrokerPort();

        //------------

        this.logger.log(Level.INFO, "Initializing [%s] with properties [%s] for cluster '%s'",
                RemoteCoherenceClusterAdapter.class.getSimpleName(),
                newSysProperties.toString(), clusterName);
    }

    private void setBrokerPort() {
        //TODO validate if port is free
        String portStr = RemoteClusterSpecialPropKeys.BROKER_PORT.getKey();

        if (newSysProperties.getProperty(portStr) == null) {
            port = 11200;
            logger.log(Level.WARN, "Using default port %d for broker " +
                    "handling connection to cluster %s", port, clusterName);
        } else {
            port = Integer.parseInt(newSysProperties.getProperty(portStr));
        }
    }

    public void attemptBrokerConnection() throws Exception {
        adapter = null;

        RemoteCoherenceClusterAdapter localAdapter = new RemoteCoherenceClusterAdapter();
        EnumMap<RemoteClusterSpecialPropKeys, String> remoteClusterProps =
                localAdapter.initIfRequired(port);

        if (remoteClusterProps == null) {
            throw new Exception(
                    "Remote process seems to be running but is NOT responding correctly for cluster "+ clusterName);
        }

        RemoteProcessManager rpm = localAdapter.getProcessManager();
        rpm.ping();
        adapter = localAdapter;

        logger.log(Level.INFO, "Remote process for cluster '%s' SUCCESSFULLY connected.", clusterName);
        logger.log(Level.DEBUG, "Properties: %s", remoteClusterProps);
    }

    public void launchChildProcess() throws Exception {
        optionalRemoteProcess = null;

        //-------------

        ProcessBuilder processBuilder = new ProcessBuilder();
        customizeEnv(processBuilder);

        //-----------

        LinkedList<String> commandList = customizeCommand(processBuilder);
        processBuilder.command(commandList);

        //-----------

        String s = String.format("Starting remote process for cluster '%s'%nCommand: %s%nEnvironment: %s",
                clusterName,
                processBuilder.command(),
                processBuilder.environment().toString());

        logger.log(Level.INFO, s);

        processBuilder.redirectErrorStream(true);
        optionalRemoteProcess = processBuilder.start();

        //-----------

        optionalOutputReader = new RemoteProcessOutputReader();
        optionalOutputReader.init(optionalRemoteProcess, ("Remote-" + port), logger);
        optionalOutputReader.start();

        //-----------

        logger.log(Level.INFO, "Started remote process.");
    }

    /**
     * @throws Exception If the process has died.
     */
    public void testChildProcessAlive() throws Exception {
        if (optionalRemoteProcess != null) {
            try {
                int status = optionalRemoteProcess.exitValue();
                String relPath = newSysProperties.getProperty(RemoteClusterSpecialPropKeys.BROKER_LOG_FILE.getKey());
                File file = new File(relPath);

                logger.log(Level.ERROR, "Remote process died for cluster '%s'.", clusterName);
                logger.log(Level.ERROR, "If exceptions occur please try using different values for clusteraddress and clusterport " +
                        "in your deployed project CDD file. Check the broker log: %s", file.getAbsolutePath());
                throw new Exception("Remote process has died with exit status [" + status + "].");
            }
            catch (IllegalThreadStateException e) {
                //Ignore. Still alive.
            }
        }
    }

    protected LinkedList<String> customizeCommand(ProcessBuilder processBuilder) {
        LinkedList<String> commandList = new LinkedList<String>();

        Map<String, String> env = processBuilder.environment();
        String javaCommand = env.get("TIB_JAVA_HOME");
        String fsp = System.getProperty("file.separator");
        if (javaCommand != null && javaCommand.length() > 0) {
            javaCommand = javaCommand + fsp + "bin" + fsp + "java";
        }
        else {
            javaCommand = "java";
        }

        commandList.add(javaCommand);

        String osname = System.getProperty("os.name").toLowerCase();
        String minusD = "";

        for (Object o : newSysProperties.keySet()) {
            String key = o.toString();
            String value = newSysProperties.getProperty(key);
            if(osname.startsWith("windows")){
            	minusD = "-D\"" + key + "\"" + "=" + value;
            }
            else{
            	minusD = "-D" + key + "=" + value;
            }
            commandList.add(minusD);
        }

        //---- For Remote Debugging
        final String debugPort =
                newSysProperties.getProperty(RemoteClusterSpecialPropKeys.BROKER_DEBUG_PORT.getKey());

        if ( debugPort != null && !debugPort.isEmpty()) {
            String suspend =
                    newSysProperties.getProperty(RemoteClusterSpecialPropKeys.BROKER_DEBUG_SUSPEND.getKey());

            suspend = (suspend == null || !suspend.matches("(?i)y|yes|true")) ? "n" : "y";

            commandList.add("-Xdebug");
            commandList.add( String.format("-Xrunjdwp:transport=dt_socket,server=y,suspend=%s,address=%s",suspend ,debugPort) );
        }
        //----

        commandList.add(RemoteClusterBroker.class.getName());
        
        commandList.add(port+"");

        return commandList;
    }

    protected void customizeEnv(ProcessBuilder processBuilder) {
        Properties properties = System.getProperties();
        Map<String, String> env = processBuilder.environment();

        for (Object o : properties.keySet()) {
            String key = o.toString();
            String value = properties.getProperty(key);

            if (key.equals("java.class.path")) {
                appendOrSetEnv(env, value, "CLASSPATH");
                if(System.getProperty("os.name").toLowerCase().startsWith("windows")){
	                //to fix JIRA issue BE-9120
	                appendOrSetEnv(env, value, "Classpath");
	                appendOrSetEnv(env, value, "classpath");
                }
            }
            //Standard is j.l.p. However, TRA sets j.l only. So, try both.
            else if (key.equals("java.library.path") || key.equals("java.library")) {
                LinkedList<String> prefixProps = new LinkedList<String>();
                String p = properties.getProperty("JVM_LIB_PATH");
                if (p != null && p.length() > 0) {
                    prefixProps.add(p);
                }
                p = properties.getProperty("JVM_LIB_DIR");
                if (p != null && p.length() > 0) {
                    prefixProps.add(p);
                }

                for (String pathAlias : OS_PATH_ALIASES) {
                    appendOrSetEnv(env, value, pathAlias, prefixProps);
                }
            }
        }
    }

    public RemoteProcessInfo finalStep() throws Exception {
        RemoteProcessInfo info = new RemoteProcessInfo();

        boolean success = false;

        try {
            if (adapter == null) {
                throw new Exception("Connection to remote process has not been established.");
            }

            RemoteProcessManager rpm = adapter.getProcessManager();
            //Just to make sure.
            rpm.ping();

            RemoteProcessPingerTask pingerTask =
                    new RemoteProcessPingerTask(rpm, RemoteProcessManager.PING_DELAY_MILLIS,
                            logger);
            info.setPingerTask(pingerTask);

            info.setPort(port);
            info.setAdapter(adapter);

            info.setOptionalRemoteProcess(optionalRemoteProcess);
            info.setOptionalRemoteProcessOutputReader(optionalOutputReader);

            success = true;
        }
        finally {
            if (success == false) {
                try {
                    info.discard();
                }
                catch (Exception e) {
                    //Ignore.
                }
            }
        }

        return info;
    }

    protected static void appendOrSetEnv(Map<String, String> env, String fromValue, String toKey) {
        appendOrSetEnv(env, fromValue, toKey, null);
    }

    /**
     * @param env
     * @param fromValue
     * @param toKey
     * @param toPrefixProps Can be <code>null</code>. Otherwise prefixes all these under the
     *                      "toKey".
     */
    protected static void appendOrSetEnv(Map<String, String> env, String fromValue, String toKey,
                                         Collection<String> toPrefixProps) {
        String toValue = env.get(toKey);
        String psp = System.getProperty("path.separator");

        if (fromValue == null) {
            fromValue = "";
        }

        if (toValue != null) {
            toValue = fromValue + psp + toValue;
        }
        else {
            toValue = fromValue;
        }

        if (toPrefixProps != null) {
            for (String prefixProp : toPrefixProps) {
                toValue = prefixProp + psp + toValue;
            }
        }

        env.put(toKey, toValue);
    }

    private void addMmCddBrokerProps() throws Exception {
        String mmCddPath = System.getProperty(SystemProperty.CLUSTER_CONFIG_PATH.getPropertyName());

        final Properties allMmAgentLvlProps = getCddAgentLvlProps(mmCddPath);

        convertOldMmCddBrokerToNewProps(allMmAgentLvlProps);

        updateNewSysProps(allMmAgentLvlProps, RemoteClusterSpecialPropKeys.BROKER_PREFIX);
    }

    private void convertOldMmCddBrokerToNewProps(Properties mmAgentLvlProps) {
        String[] OLD_PATTERN = {RemoteClusterSpecialPropKeys.DEPRECATED_REMOTE_CLUSTER_PROPERTY+"broker.",
                RemoteClusterSpecialPropKeys.DEPRECATED_REMOTE_CLUSTER_PROPERTY,
                RemoteClusterSpecialPropKeys.DEPRECATED_BROKER_PROPERTY}        ;

        List<String> oldKeys = new ArrayList<String>();

        for (Object key: mmAgentLvlProps.keySet()) {
            String keyStr = key.toString();

            if (keyStr.startsWith(OLD_PATTERN[1]) ||       //case of index [0] is included in [1]
                keyStr.startsWith(OLD_PATTERN[2]) ) {

                oldKeys.add(keyStr);
            }
        }

        for (String oldKey : oldKeys) {
            String newKey = null;

            for (int i = 0; i < OLD_PATTERN.length; i++) {
                if (oldKey.startsWith(OLD_PATTERN[i])) {
                    newKey = oldKey.replaceFirst(OLD_PATTERN[i],
                            RemoteClusterSpecialPropKeys.BROKER_PREFIX);
                    Object val = mmAgentLvlProps.remove(oldKey);
                    mmAgentLvlProps.put(newKey, val);
                    break;
                }
            }
        }
    }

    private void addMasterCddProps(String propPrefix) throws Exception {
    	final Properties allClustLvlProps = getCddClustLvlProps(masterCddPath);

        updateNewSysProps(allClustLvlProps, propPrefix);
    }

    private Properties getCddAgentLvlProps(String cddPath) throws Exception{
        final CddConfigUtil cddConfigUtil = CddConfigUtil.getInstance(cddPath);
        final ProcessingUnitConfig puConfig = cddConfigUtil.getPUConfig("default");

        return (Properties)puConfig.getAgents().toProperties();
    }

    private Properties getCddClustLvlProps(String cddPath) throws Exception{
        final CddConfigUtil cddConfigUtil = CddConfigUtil.getInstance(cddPath);
        final ClusterConfig config = cddConfigUtil.getClusterConfig();

        return (Properties)config.toProperties();
    }

    //TODO: Make this method a bit more efficient
    private void updateNewSysProps(Properties props, String propPrefix) {
        final String TANGOSOL_PROP = RemoteClusterSpecialPropKeys.TANGOSOL_PREFIX;
        final String JAVA_PROP = RemoteClusterSpecialPropKeys.JAVA_PREFIX;

        for (Object keyo : props.keySet()) {
            String key = keyo.toString();
            String skey = key;

            if (key!=null && key.startsWith(propPrefix)) {

                //Remove the prefix from the Tangosol and Java properties
                int pi = key.indexOf(TANGOSOL_PROP); //prop index

                if (pi == -1)
                    pi = key.indexOf(JAVA_PROP);

                if (pi != -1) {
                    skey = key.substring(pi);
                }

                String val = props.get(key).toString();
                if (val != null && !val.trim().isEmpty())
                    newSysProperties.put(skey,val.trim());
            }
        }
    }

    //Set tangosol.coherence.localhost and tangosol.coherence.localport
    //to the values of tangosol.coherence.wka and tangosol.coherence.wka.port
    //(this is necessary to make BEMM work with WKA)
    //Ensure other properties needed for the Broker process to start correctly
    private void setRemClustBrokerMinNumProps() {
        final String wkaVal =
                (String)newSysProperties.get(RemoteClusterSpecialPropKeys.WKA_ADDRESS.getKey());

        if (wkaVal != null) {
            newSysProperties.put(
                    (RemoteClusterSpecialPropKeys.LOCAL_HOST.getKey()), wkaVal);

            final String wkaPortVal =
                    (String) newSysProperties.get(RemoteClusterSpecialPropKeys.WKA_PORT.getKey());

            if (wkaPortVal != null) {
                newSysProperties.put(
                        (RemoteClusterSpecialPropKeys.LOCAL_PORT.getKey()), wkaPortVal);
            } else {
                logger.log(Level.WARN, "Property '%s' is missing associated property '%s'",
                        RemoteClusterSpecialPropKeys.WKA_ADDRESS.getKey(),
                        RemoteClusterSpecialPropKeys.WKA_PORT.getKey() );
            }
        }

        setRemClustBrokerDefaultPropVals();

        //To eliminate the need to specify the property
        //tangosol.coherence.cluster in the master CDD files.
        newSysProperties.setProperty(
                RemoteClusterSpecialPropKeys.CLUSTER.getKey(),clusterName);

        //Enable JMX for management
        newSysProperties.setProperty(
                SystemProperty.JMX_ENABLED.getPropertyName(), Boolean.TRUE.toString());

    }

    private void setRemClustBrokerDefaultPropVals() {
        for (RemoteClusterSpecialPropKeys tangosolKey :
                RemoteClusterSpecialPropKeys.values()) {

            //Use get instead of getProperty such that the default property lists are NOT searched
            String value = (String)newSysProperties.get(tangosolKey.getKey());

            if (value == null) {
                if (tangosolKey.getDefaultValue() != null ) {
                        newSysProperties.setProperty(tangosolKey.getKey(), tangosolKey.getDefaultValue());
                }
            }
        }
    }

    public void shutdownBroker(int status) throws Exception {
        if(adapter == null)
            return;

        adapter.getProcessManager().shutdown(status);
    }
}
