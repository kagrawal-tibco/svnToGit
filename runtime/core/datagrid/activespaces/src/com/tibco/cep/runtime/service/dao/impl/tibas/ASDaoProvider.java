/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 *  All Rights Reserved.
 *
 *  This software is confidential and proprietary information of
 *  TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.dao.impl.tibas;

import com.tibco.as.space.*;
import com.tibco.as.space.RecoveryOptions.RecoveryPolicy;
import com.tibco.as.space.SpaceDef.SpaceState;
import com.tibco.as.space.impl.ASFileLogOptions;
import com.tibco.as.space.impl.ASMetaspace;
import com.tibco.as.space.listener.SpaceDefListener;
import com.tibco.as.space.security.AuthenticationCallback;
import com.tibco.as.space.security.AuthenticationInfo;
import com.tibco.as.space.security.AuthenticationInfo.Method;
import com.tibco.as.space.security.UserPwdCredential;
import com.tibco.as.space.security.X509V3Credential;
import com.tibco.be.functions.cluster.DataGridFunctions;
import com.tibco.be.functions.cluster.DataGridFunctionsProvider;
import com.tibco.be.util.FileUtils;
import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.BackingStoreConfig;
import com.tibco.be.util.config.cdd.CacheManagerConfig;
import com.tibco.cep.as.kit.tuple.DataType;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.ASMetaspaceReconnectHandler;
import com.tibco.cep.runtime.ASUtil;
import com.tibco.cep.runtime.model.serializers.as.SerializableLiteTupleCodec;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.basic.ShutdownWatcher;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
import com.tibco.cep.runtime.service.cluster.system.DefaultControlKey;
import com.tibco.cep.runtime.service.dao.impl.tibas.backingstore.BEDataGridSharedNothingStore;
import com.tibco.cep.runtime.service.dao.impl.tibas.mm.ASClusterMBeanImpl;
import com.tibco.cep.runtime.service.om.api.*;
import com.tibco.cep.runtime.service.om.impl.AbstractDaoProvider;
import com.tibco.cep.runtime.service.om.impl.GridFunctionsProviderHelper.NativeGridType;
import com.tibco.cep.runtime.session.BEManagedThread;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.util.annotation.LogCategory;
import com.tibco.cep.util.annotation.Optional;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;
import com.tibco.xdc.Collector;
import com.tibco.xdc.Common;
import com.tibco.xdc.Receiver;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import static com.tibco.cep.runtime.service.om.impl.GridFunctionsProviderHelper.$chooseDataGridFunctionsProvider;

/*
 * Author: Ashwin Jayaprakash / Date: Sep 22, 2010 / Time: 10:56:06 AM
 */
@LogCategory("as.runtime.cluster.om.daoprovider")
public class ASDaoProvider extends AbstractDaoProvider {

    ASInvocationService asInvocationService;

    protected Metaspace metaspace;
    private MemberDef memberDef;
    private String metaspaceName;

    protected SerializableLiteTupleCodec tupleCodec;

    protected DynamicSpaceListener dynamicSpaceListener;

    protected ASPersistenceProvider persistenceProvider = null;

    protected boolean sharedAllWithBDB = false;

    protected String sharedAllBDBStorePath = null;

    protected boolean sharedNothing = false;

    protected String persistencePolicy = null;

    protected LogLevel asEnumLogLevel = null;

    //AS security related fields.
    //Is security enabled for the cluster.
    protected boolean securityEnabled;
    
    //Role to be assumed by this PU. "Controller" or "Requester"
    protected String securityRole;
    
    //Controller's policy file.
    protected String controllerPolicyFile;
    
    //Identity password for the policy file.
    protected String controllerIdentity;
    
    //Requester's token file.
    protected String requesterTokenFile;
    
    //Identity password for the token file.
    protected String requesterIdentity; 
    
    //In case of x509 LDAP based authentication, the identity key file of the requester.
    protected String requesterKeyFile;
    
    //In case of system based authentication, an optional domain name.
    protected String domainName;   
    
    //In case of user/pwd based authentication (ldap or system), the username.
    protected String userName;         
    
    //And the password, (identity password in case of ldap/x509)
    protected String password;           
    
    //In BE 5.1, only one property is used and is interpreted as policy or a token file 
    //based on the role. BE 5.2 will also use this property if specified, else fallback to individual
    //property values of controllerPolicyFile or requesterTokenFile as defined in the CDD.
    protected String securityFile;
    
    //Expose only one proerty to the end user to provide the security file password.
    protected String securityFilePwd;
    
    private boolean metaspaceClosingConn;
    
    public ASDaoProvider() {
    }

    @Override
    protected void initHook() throws Exception {
        if (Common.isCollector()) {
            Collector.INSTANCE.start(cluster);
        } else if (Common.isReceiver()) {
            Receiver.INSTANCE.start(cluster);
        }
        
        metaspaceName = sanitizeName(cluster.getClusterName());
                
        final Properties properties = cluster.getRuleServiceProvider().getProperties();
        final GlobalVariables gVs = cluster.getRuleServiceProvider().getGlobalVariables();
        
        if(isGlobalVar(metaspaceName)){
        	GlobalVariableDescriptor gv =gVs.getVariable(stripGvMarkers(metaspaceName));
        	metaspaceName=gv.getValueAsString();
        }
        String listenUrl = gVs.substituteVariables(properties.getProperty(ASConstants.PROP_LISTEN_URL)).toString();
        String remoteListenUrl = gVs.substituteVariables(properties.getProperty(ASConstants.PROP_REMOTE_LISTEN_URL)).toString();
        String discoverUrl = gVs.substituteVariables(properties.getProperty(ASConstants.PROP_DISCOVER_URL)).toString();
        String protocolTimeout = gVs.substituteVariables(properties.getProperty(ASConstants.PROP_AS_PROTOCOL_TIMEOUT)).toString();
        String connectionTimeout = properties.getProperty(ASConstants.PROP_AS_CONNECTION_TIMEOUT, "30000"); //default set to 30 seconds

        if (protocolTimeout == null) {
            System.setProperty(ASConstants.PROP_AS_PROTOCOL_TIMEOUT, "-1");
        }
        String workerThreads = gVs.substituteVariables(properties.getProperty(ASConstants.PROP_MEMBER_WORKERTHREADS_COUNT, "4")).toString();

        logger.log(Level.INFO, String.format("Connecting to [%s:%s] on ports , listen=[%s], remote-listen=[%s] and discovery=[%s] with connection timeout=[%s] ms",
                Metaspace.class.getSimpleName(), metaspaceName,
                (listenUrl == null ? "default" : listenUrl), (remoteListenUrl == null ? "default" : remoteListenUrl),
                (discoverUrl == null ? "default" : discoverUrl), connectionTimeout));

        String engineName = cluster.getRuleServiceProvider().getName();
        //---Setting the File Logging options---------------
        //Set the AS log level and directory
        //the default file name is : as-pid.log
        //the default log level is INFO
        String asLogDir = gVs.substituteVariables(properties.getProperty(ASConstants.PROP_AS_LOG_DIR)).toString();
        String asLogFileName = gVs.substituteVariables(properties.getProperty(ASConstants.PROP_AS_LOG_FILE_NAME, "")).toString();
        String asLogLevel = gVs.substituteVariables(properties.getProperty(ASConstants.PROP_AS_LOG_LEVEL, LogLevel.INFO.name())).toString();

        // Process the directory name
        // If null, the BE directory is used
        if (asLogDir == null || asLogDir.equals("")) {
            asLogDir = System.getProperty(SystemProperty.TRACE_FILE_DIR.getPropertyName(), "logs");
        }
        FileUtils.createWritableDirectory(asLogDir);

        // If the log file name is null, create the file handle
        // Getting the process id for the current process
        //String process = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        //String[] pId = process.split("@");
        StringBuilder filenameBuilder = null;

        if (asLogFileName == null || asLogFileName.equals("")) {
            asLogFileName = System.getProperty(SystemProperty.TRACE_FILE_NAME.getPropertyName());

            //the default file name is : as-engineName.log
            filenameBuilder = new StringBuilder();
            if (asLogFileName == null || asLogFileName.equals("")) {
                filenameBuilder.append(engineName);
                filenameBuilder.append("-as");
                filenameBuilder.append(".log");
                asLogFileName = filenameBuilder.toString();
            } else {
                //use the be-log file name and add a suffix
                //otherwise as-BElogfilename.log
                filenameBuilder.append(asLogFileName);
                filenameBuilder.append("-as");
                filenameBuilder.append(".log");
                asLogFileName = filenameBuilder.toString();
            }
        }

        try {
            this.asEnumLogLevel = LogLevel.valueOf(asLogLevel.toUpperCase());
        } catch (IllegalArgumentException e) {
            //In case of invalid arguments, default to INFO level.
            logger.log(Level.INFO, "Invalid AS log level '%s', defaulting to INFO", asLogLevel.toUpperCase());
            this.asEnumLogLevel = LogLevel.INFO;
        }

        FileLogOptions fileLogOptions = new ASFileLogOptions();

        File logFile = new File(asLogDir + "/" + asLogFileName);
        fileLogOptions.setFile(logFile);

        fileLogOptions.setLogLevel(asEnumLogLevel);

        //Using default logging values from BE

        int fileSizeLimit = Integer.parseInt(properties.getProperty(ASConstants.PROP_AS_LOGFILE_SIZE, System.getProperty(SystemProperty.TRACE_FILE_MAX_SIZE.getPropertyName())));
        fileLogOptions.setLimit(fileSizeLimit);

        int fileCount = Integer.parseInt(properties.getProperty(ASConstants.PROP_AS_LOGFILE_COUNT, System.getProperty(SystemProperty.TRACE_FILE_MAX_NUM.getPropertyName())));
        fileLogOptions.setFileCount(fileCount);

        boolean append = Boolean.parseBoolean(properties.getProperty(ASConstants.PROP_AS_LOGFILE_APPEND, System.getProperty(SystemProperty.TRACE_FILE_APPEND.getPropertyName())));
        fileLogOptions.setAppend(append);

        ASCommon.setFileLogging(fileLogOptions);

        //--------------------------------------

        //initialize the codec hook before the metaspace connect
        //this is to prevent a race condition in ASInvocation accessing a null codec [BE-17223]
        tupleCodec = new SerializableLiteTupleCodec(cluster.getRuleServiceProvider().getClassLoader(), logger);

        MemberDef cd = memberDef = MemberDef.create()
                .setDiscovery(discoverUrl)
                .setListen(listenUrl)
                .setRemoteListen(remoteListenUrl)
                .setWorkerThreadCount(Integer.parseInt(workerThreads))
                .setConnectTimeout(Long.parseLong(connectionTimeout));

        //------ GMP Improvements for AS 2.1.4 -------
        
        String memberTimeout = gVs.substituteVariables(properties.getProperty(ASConstants.PROP_AS_MEMBER_TIMEOUT)).toString(); //default set to "30000" or 30 seconds
        String remoteMemberTimeout = gVs.substituteVariables(properties.getProperty(ASConstants.PROP_AS_REMOTE_MEMBER_TIMEOUT, "120000")).toString(); //default set to "120000" or 2mins
        String clusterSuspendThreshold = gVs.substituteVariables(properties.getProperty(ASConstants.PROP_AS_CLUSTER_SUSPEND_THRESHOLD)).toString(); //default is "-1" or no limit
        
        if (memberTimeout != null && !memberTimeout.equals("")) {
            cd.setMemberTimeout(Integer.parseInt(memberTimeout));
        }

        if (discoverUrl != null && discoverUrl.contains("remote=true") && remoteMemberTimeout != null && !remoteMemberTimeout.isEmpty()) {
        	logger.log(Level.INFO, "Setting remote member timeout to - " + remoteMemberTimeout);
    		cd.setClientTimeout(Integer.parseInt(remoteMemberTimeout));
    	}
        
        if (clusterSuspendThreshold != null && !clusterSuspendThreshold.equals("")) {
            cd.setClusterSuspendThreshold(Integer.parseInt(clusterSuspendThreshold));
        }
        
        //-------------------------------------------

        //Host-aware replication requires the member name to be a 2 part name
        //<group-name>.<member-name>

        boolean hostAware = Boolean.valueOf(System.getProperty(SystemProperty.AS_HOST_AWARE_REPLICATION_ENABLE.getPropertyName(),
                SystemProperty.AS_HOST_AWARE_REPLICATION_ENABLE.getValidValues()[0].toString()));


        String hostName = null;
        // If host aware is set to true by user
        if (hostAware) {
            String hostNameProperty = System.getProperty(SystemProperty.AS_HOST_AWARE_HOSTNAME.getPropertyName(), "localhost");
            if (hostNameProperty.trim() != "") {
                // Hostname is provided explicitly, or to be acquired from system.
                if (hostNameProperty.equalsIgnoreCase("localhost")) {
                    hostNameProperty = getHostname();
                }
                hostName = normalize(hostNameProperty);
                if (engineName != null || engineName != "") {
                    logger.log(Level.INFO, "Setting AS member name : HostName.EngineName=" + hostName + "." + engineName);
                    cd.setMemberName(hostName + "." + engineName);
                }
            } else {
                // Hostname is provided, but it is not valid.
                logger.log(Level.WARNING, "Cannot set invalid HostName [" + hostNameProperty + "]. " +
                        "Host-aware replication is disabled for EngineName=" + engineName);
                cd.setMemberName(engineName);
                hostAware = false;
            }
        } else {
            logger.log(Level.WARNING, "Host-aware replication is disabled for EngineName=" + engineName);
            cd.setMemberName(engineName);
        }

        // Enable AS-security
        configureSecurity(properties, cd);

        long trycount = 0;
        long tryCountLimit = Long.parseLong(properties.getProperty(SystemProperty.AS_CONNECTION_RETRY_COUNT.getPropertyName(), "5"));
        boolean connected = false;
        while (!connected && trycount < tryCountLimit) {
            try {
                CacheManagerConfig cacheManagerConfig = getCacheManagerConfig();
                BackingStoreConfig backingStoreConfig = cacheManagerConfig.getBackingStore();
                persistencePolicy = CddTools.getValueFromMixed(backingStoreConfig.getPersistencePolicy());
                String persistenceOption = CddTools.getValueFromMixed(backingStoreConfig.getPersistenceOption());
                if ((persistenceOption.compareToIgnoreCase(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_NOTHING) == 0) ||
                    (persistenceOption.compareToIgnoreCase(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_NOTHING_ALT) == 0)) {

                    logger.log(Level.INFO, "Using Shared Nothing persistence type");

                    //Create the shared nothing store here.
                    //This is a special case. SN store creation will not happen in initializeBackingstoreConfig
                    sharedNothing = true;
                    backingStore = new BEDataGridSharedNothingStore(cluster.getRuleServiceProvider(),
                            CddTools.getValueFromMixed(backingStoreConfig.getDataStorePath()));

                    //Set the datastore path for SN
                    /* The default datastore path for AS-sharednothing in 2.0.1 is the user's home directory */
                    String dataStorePath = "";
                    if (backingStore instanceof BEDataGridSharedNothingStore) {
                        dataStorePath = ((BEDataGridSharedNothingStore) backingStore).getSharedNothingPath();
                    }
                    cd.setDataStore(dataStorePath);
                    logger.log(Level.INFO, "Shared Nothing persistence datastore path=[" + dataStorePath + "]");
                }

                connectMetaspaceAfresh();

                if (metaspace.getName().equals(metaspaceName)) {
                    connected = true;
                }
            } catch (Exception asex) {
                trycount++;

                // This means the listen url has failed
                if (asex.getMessage().contains("command_line_arg_invalid") &&
                        asex.getMessage().contains("none of the listen URL is available")) {
                    logger.log(Level.WARNING,
                            String.format("Failed connecting to [%s:%s] with error %s. Retrying upto %s times.",
                                    Metaspace.class.getSimpleName(), metaspaceName, asex.getMessage(), tryCountLimit));
                } else if (asex.getMessage().contains("name_not_available") && (sharedNothing == false)) {
                    if (hostAware) {
                        cd.setMemberName(hostName + "." + engineName + "_" + trycount);
                    } else {
                        cd.setMemberName(engineName + "_" + trycount);
                    }
                    logger.log(Level.WARNING,
                            String.format("Failed connecting to [%s:%s] due to member name conflict. Trying generated name [%s].",
                                    Metaspace.class.getSimpleName(), metaspaceName, cd.getMemberName()));
                } else {
                    // Come here if discover url has failed
                    logger.log(Level.WARNING,
                            String.format("Failed connecting to [%s:%s] with error %s. Retrying upto %s times.",
                                    Metaspace.class.getSimpleName(), metaspaceName, asex.getMessage(), tryCountLimit));
                }
            }
        }

        if (trycount >= tryCountLimit && !connected) {
            throw new RuntimeException("Unable to connect to metaspace [" + metaspaceName + "] even after " + tryCountLimit + " tries");
        }

        listenUrl = metaspace.getMemberDef().getListen();
        discoverUrl = metaspace.getMemberDef().getDiscovery();

        logger.log(Level.INFO, String.format("Connected to [%s:%s] member-id=%s on ports [%s] and [%s] - version %s",
                Metaspace.class.getSimpleName(), metaspaceName, metaspace.getSelfMember().getName(),
                (listenUrl == null ? "default" : listenUrl), (discoverUrl == null ? "default" : discoverUrl),
                Metaspace.version()));
        logger.log(Level.INFO, String.format("Member details name=%s id=%s worker-threads=%s host-aware=%s",
                metaspace.getSelfMember().getName(), metaspace.getSelfMember().getId(), workerThreads, hostAware));

        //Metaspace is up and ready, init the AS remote member reconnect handler.
        ASMetaspaceReconnectHandler.initReconnectHandlerInstance(this);
        
        //----------------

        DataGridFunctionsProvider dgfp = (DataGridFunctionsProvider) $chooseDataGridFunctionsProvider(NativeGridType.datagrid);
        DataGridFunctions.setProvider(dgfp);

        initializeBackingstoreConfig();

        //----------------

        asInvocationService = new ASInvocationService(this);

        String seeder = properties.getProperty(SystemProperty.CLUSTER_NODE_ISSEEDER.getPropertyName(), "false");
        if (seeder.equalsIgnoreCase("true")) {
            dynamicSpaceListener = new DynamicSpaceListener();
            metaspace.listenSpaceDefs(dynamicSpaceListener);
        }

        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        ShutdownWatcher watcher = registry.getShutdownWatcher();
        if (watcher != null) {
            watcher.addPreExitJob(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                            	metaspaceClosingConn = true;
                            	
                                // BE-16674: BEManagedThreads acquire a shared lock before performing any work. 
                                // When Ctrl-C is hit this special thread acquires a write lock which blocks all 
                                // BEManagedThread shared locks. So the engine can safely call ASMetaspace.close()
                                String metaspaceCloseWaitMillis =
                                        gVs.substituteVariables(properties.getProperty(ASConstants.PROP_METASPACE_SHUTDOWN_WAIT_MILLIS, "8500")).toString();
                                Thread.sleep(Long.parseLong(metaspaceCloseWaitMillis));
                                String strategy = 
                                        gVs.substituteVariables(properties.getProperty(ASConstants.PROP_METASPACE_SHUTDOWN_STRATEGY, "disconnect")).toString();

                                if ("IMMEDIATE".equals(strategy.toUpperCase()) == false) {
                                    BEManagedThread.acquireWriteLock();

                                    logger.log(Level.INFO, "Disconnecting from metaspace " + metaspace.getName());
                                    if ("DISCONNECT".equals(strategy.toUpperCase()) == false) {
                                        Collection<String> spaceNames = metaspace.getUserSpaceNames();
                                        for (String spaceName: spaceNames) {
                                            logger.log(Level.INFO, "Disconnecting from space " + spaceName);
                                            metaspace.getSpace(spaceName).close();
                                            logger.log(Level.INFO, "Disconnected from space " + spaceName);
                                        }
                                    }
                                    
                                    metaspace.closeAll();

                                    logger.log(Level.INFO, "Disconnected from metaspace " + metaspace.getName());
                                }
                            } catch (Throwable t) {
                                logger.log(Level.WARNING, "Error occurred while disconnecting from metaspace", t);
                            }
                        }
                    });
        }

        //----------------

        new ASClusterMBeanImpl(this.cluster);
    }
    
    public void connectMetaspaceAfresh() throws ASException {
    	if (cluster.getClusterConfig().isStorageEnabled()) {
            Map<String, String> p = new HashMap<String, String>();
            String autoJoinStr = System.getProperty("be.engine.as.seeder.autojoin", "true");
            if (autoJoinStr.equals("true")) {
                p.put("-autojoin", "true");
            } else if (!autoJoinStr.equals("none")) {
                p.put("-autojoin", "false");
            }
            metaspace = new ASMetaspace(metaspaceName, memberDef, p);
        } else {
            metaspace = Metaspace.connect(metaspaceName, memberDef);
        }
    }

	private void configureSecurity(final Properties properties, MemberDef cd)
			throws AXSecurityException {
		
        final GlobalVariables gVs = cluster.getRuleServiceProvider().getGlobalVariables();
		String secEnable     = gVs.substituteVariables(properties.getProperty(ASConstants.PROP_AS_SECURITY_ENABLE)).toString();
        securityEnabled      = Boolean.valueOf(secEnable);
        securityRole         = gVs.substituteVariables(properties.getProperty(SystemProperty.AS_SECURITY_MODE_ROLE.getPropertyName())).toString();                     
        controllerPolicyFile = gVs.substituteVariables(properties.getProperty(SystemProperty.AS_SECURITY_POLICY_FILE.getPropertyName())).toString();                  
        controllerIdentity   = gVs.substituteVariables(properties.getProperty(SystemProperty.AS_SECURITY_CONTROLLER_IDENTITY.getPropertyName())).toString();            
        requesterTokenFile   = gVs.substituteVariables(properties.getProperty(SystemProperty.AS_SECURITY_TOKEN_FILE.getPropertyName())).toString(); 
        requesterIdentity    = gVs.substituteVariables(properties.getProperty(SystemProperty.AS_SECURITY_TOKEN_IDENTITY.getPropertyName())).toString(); 
        requesterKeyFile     = gVs.substituteVariables(properties.getProperty(SystemProperty.AS_SECURITY_REQUESTER_CERTKEYFILE.getPropertyName())).toString();   
        domainName           = gVs.substituteVariables(properties.getProperty(SystemProperty.AS_SECURITY_REQUESTER_DOMAINNAME.getPropertyName())).toString();
        userName             = gVs.substituteVariables(properties.getProperty(SystemProperty.AS_SECURITY_REQUESTER_USERNAME.getPropertyName())).toString(); 
        password             = gVs.substituteVariables(properties.getProperty(SystemProperty.AS_SECURITY_REQUESTER_PASSWORD.getPropertyName())).toString(); 
        securityFile         = gVs.substituteVariables(properties.getProperty(SystemProperty.AS_SECURITY_FILE.getPropertyName())).toString();
        securityFilePwd      = gVs.substituteVariables(properties.getProperty(SystemProperty.AS_SECURITY_FILE_IDENTITY.getPropertyName())).toString();
        

        boolean isController = "controller".equalsIgnoreCase(securityRole);
        // (5.1 support: if explicit property for security file is set/used, use it, else go by CDD setting)        
        if (null == securityFile || "".equals(securityFile)) {
        	if (isController) {
            	securityFile = controllerPolicyFile;
        	} else {
        		securityFile = requesterTokenFile;
        	}
        }
        // Similarly, use only one property for the security file identity password
        if (null == securityFilePwd || "".equals(securityFilePwd)) {
        	if (isController) {
        		securityFilePwd = controllerIdentity;
        	} else {
        		securityFilePwd = requesterIdentity;
        	}
        }
        
        if (securityEnabled) {
            logger.log(Level.INFO, "Enabling AS security");
            logger.log(Level.INFO, SystemProperty.AS_SECURITY_ENABLE.getPropertyName() + ": " + securityEnabled);              
            logger.log(Level.INFO, SystemProperty.AS_SECURITY_MODE_ROLE.getPropertyName() + ": "  + securityRole);
            logger.log(Level.INFO, SystemProperty.AS_SECURITY_FILE.getPropertyName()  + ": " +  securityFile);  
            logger.log(Level.INFO, SystemProperty.AS_SECURITY_REQUESTER_CERTKEYFILE.getPropertyName() + ": " + requesterKeyFile);
            logger.log(Level.INFO, SystemProperty.AS_SECURITY_REQUESTER_DOMAINNAME.getPropertyName()  + ": " +  domainName);
            logger.log(Level.INFO, SystemProperty.AS_SECURITY_REQUESTER_USERNAME.getPropertyName() + ": "  + userName);   

            //get the security file location
            securityFile = ASUtil.sanitizeSecurityFilePath(securityFile, cluster);

            //set the security file identity password
            if (securityFilePwd != null) {
            	securityFilePwd = decrypt(securityFilePwd);
            	cd.setIdentityPassword(securityFilePwd.toCharArray());
            }

            if (isController) {
            	//set the security file as the policy file
                cd.setSecurityPolicyFile(securityFile);
            } else {
            	//set the security file as the token file
                cd.setSecurityTokenFile(securityFile);
                password = decrypt(password);
                cd.setAuthenticationCallback(new ASAuthCallback());
            }
        }
	}

	private String decrypt(String password) throws AXSecurityException {
		if (password != null && password.startsWith("#!")) {
			password = new String(ObfuscationEngine.decrypt(password));
		}
		return password;
	}

    public LogLevel getASLogLevel() {
        return asEnumLogLevel;
    }

    public Metaspace getMetaspace() {
        return metaspace;
    }

    public SerializableLiteTupleCodec getTupleCodec() {
        return tupleCodec;
    }

    private void initializeBackingstoreConfig() throws Exception {
        CacheManagerConfig cacheManagerConfig = getCacheManagerConfig();
        BackingStoreConfig backingStoreConfig = cacheManagerConfig.getBackingStore();
        boolean sharedAll = false;
        boolean enabled = false;

        String persistenceOption = CddTools.getValueFromMixed(backingStoreConfig.getPersistenceOption());
        if (persistenceOption.equals(BackingStoreConfig.PERSISTENCE_OPTION_NONE)) {
            enabled = false;
        } else if ((persistenceOption.compareToIgnoreCase(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_ALL) == 0) ||
                   (persistenceOption.compareToIgnoreCase(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_ALL_ALT) == 0)) {
            enabled = true;
            sharedAll = true;
        }

        String databaseType = CddTools.getValueFromMixed(backingStoreConfig.getType());
        if (backingStoreConfig != null) {
            if (enabled) {
                backingStoreEnabled = true;
                //Shared-all
                if (sharedAll) {
                    //BDB
                    if (databaseType.equals(BackingStoreConfig.TYPE_BDB)) {
                        sharedAllWithBDB = true;
                        sharedAllBDBStorePath = CddTools.getValueFromMixed(backingStoreConfig.getDataStorePath());

                        try {
                            Class<?> persistentProviderClass = Class.forName("com.tibco.cep.persister.ASBDBPersistenceProviderImpl");
                            persistenceProvider = (ASPersistenceProvider) persistentProviderClass.newInstance();
                            persistenceProvider.initializeControlSpace(metaspace, cluster, sharedAllBDBStorePath);
                            //persistenceProvider.initDBProvider(metaspace,sharedAllBDBStorePath);
                            backingStore = persistenceProvider.getBackingStore();
                        } catch (Throwable e) {
                            logger.log(Level.SEVERE, "Unable to initialize the BDB provider", e);
                            throw new RuntimeException("Unable to initialize the BDB provider! Please verify your Berkeley DB configuration.");
                        }
                    } else {
                        //Traditional DB
                        isCacheAside = Boolean.valueOf(CddTools.getValueFromMixed(backingStoreConfig.getCacheAside()));
                        cacheLoaderClassname = CddTools.getValueFromMixed(backingStoreConfig.getCacheLoaderClass());

                        //Shared-all with traditional Database ( Oracle/SqlServer )
                        if (cacheLoaderClassname == null || cacheLoaderClassname.length() == 0) {
                            cacheLoaderClassname = "com.tibco.cep.runtime.service.dao.impl.tibas.backingstore.BEDataGridJdbcStore";
                        } else {
                            if (!cacheLoaderClassname.equals("com.tibco.cep.runtime.service.dao.impl.tibas.backingstore.BEDataGridJdbcStore") &&
                                !cacheLoaderClassname.equals("com.tibco.cep.runtime.service.dao.impl.tibas.backingstore.BEDataGridOracleStore")) {
                                logger.log(Level.WARNING, "Ignoring user defined cache loader class : " + cacheLoaderClassname);
                                cacheLoaderClassname = "com.tibco.cep.runtime.service.dao.impl.tibas.backingstore.BEDataGridJdbcStore";
                            }
                        }

                        //---------------

                        System.setProperty("be.backingstore.cacheLoaderClass", cacheLoaderClassname);
                        logger.log(Level.INFO, "Using cache loader class : " + cacheLoaderClassname);

                        //---------------

                        try {
                            logger.log(Level.INFO, String.format("Creating BackingStore (cache-aside=%s class=%s)",
                                    isCacheAside, cacheLoaderClassname));

                            Class recClz = Class.forName(cacheLoaderClassname);
                            Constructor cons = recClz.getConstructor(new Class[]{});
                            backingStore = (BackingStore) cons.newInstance(new Object[]{});
                        } catch (Exception ex) {
                            throw new RuntimeException("Creating BackingStore failed (class=" + cacheLoaderClassname + ")", ex);
                        }
                    }
                }
            }
        }
    }

    public boolean isSharedAllWithBDB() {
        return sharedAllWithBDB;
    }

    public ASPersistenceProvider getBDBPersistenceProvider() {
        return persistenceProvider;
    }

    public boolean isSharedNothing() {
        return sharedNothing;
    }

    public String getPersistencePolicy() {
        return persistencePolicy;
    }

    public boolean isMetaspaceReady() throws Exception {
    	for (String name : metaspace.getUserSpaceNames()) {
    		if (metaspace.getSpaceState(name) != SpaceState.READY) {
    			return false;
    		}
    	}
        return true;
    }

    @Override
    public void start() throws Exception {
        logger.log(Level.INFO, "Started [" + getClass().getSimpleName() + "]");
    }

    @Override
    public void recoverCluster(Cluster cluster) throws Exception {
    	if (this.isMetaspaceReady()) {
    		// Ignore cluster recovery if all spaces are Ready/Recovered
            logger.log(Level.INFO, String.format("Skipping metaspace [%s] recovery already in READY state", 
            		metaspace.getName()));
    		return;
    	}
    	if (this.isSharedNothing() == false) {
    		// Ignore cluster recovery for Shared-All
            logger.log(Level.FINE, String.format("Skipping metaspace [%s] recovery for %s persistence", 
            		metaspace.getName(), getPersistencePolicy()));
    		return;
    	}
    	
        RecoveryOptions recoveryOptions = RecoveryOptions.create().setLoadWithData(true);

        int threadCount = Integer.parseInt(System.getProperty(SystemProperty.DATAGRID_RECOVERY_THREADS.getPropertyName(), "5"));
        String strategy = System.getProperty(SystemProperty.DATAGRID_RECOVERY_GENERALIZED_STRATEGY.getPropertyName(), /* alternative */
                          System.getProperty(SystemProperty.DATAGRID_RECOVERY_DISTRIBUTED_STRATEGY.getPropertyName(), "no_data_loss"));
        // Validate settings to avoid exception
        if ("NO_DATA_LOSS,EXACT_CLUSTER,FAST_LOAD_ONLY,ROBUST_LOAD_ONLY,FORCE_LOAD".indexOf(strategy.toUpperCase()) < 0) {
            strategy = "no_data_loss";
        }
        recoveryOptions.setRecoveryPolicy(RecoveryPolicy.valueOf(strategy.toUpperCase()));
        recoveryOptions.setThreadCount(threadCount);
        
        int quorum = cluster.getClusterConfig().getMinCacheServers();
        recoveryOptions.setQuorum(quorum);

        logger.log(Level.INFO, String.format("Metaspace [%s] recovery with quorum=%s policy=%s threads=%s", metaspace.getName(), 
                recoveryOptions.getQuorum(), recoveryOptions.getRecoveryPolicy(), recoveryOptions.getThreadCount()));

        metaspace.recover(recoveryOptions);
    }

    //-----------------

    public String sanitizeName(String s) {
        return ASUtil.asFriendlyEncode(s, logger);
    }

    @Override
    protected DaoSeed createDaoSeed(CacheType cacheType, String clusterName, @Optional String agentName,
                                    String cacheName, Object... props) {
    	String name;

    	if (descriptiveNames) {
	        name = cacheType.getAlias()
	                + "-" + sanitizeName(clusterName)
	                + "-" + (agentName == null ? "" : sanitizeName(agentName))
	                + "-" + sanitizeName(cacheName);
    	}
    	else {
	        name = sanitizeName(clusterName)
	                + "-" + (agentName == null ? "" : sanitizeName(agentName))
	                + "-" + sanitizeName(cacheName);
    	}
    	
        String entityCacheSizeStr = System.getProperty(SystemProperty.CLUSTER_LIMITED_SIZE.getPropertyName(), "10000");
        entityCacheSizeStr = cluster.getRuleServiceProvider().getGlobalVariables().substituteVariables(entityCacheSizeStr).toString();
        return new DaoSeed(cacheType, name, Integer.parseInt(entityCacheSizeStr), props);
    }

    @Override
    protected <K, E extends Entity> EntityDao makeNew(Class<E> entityClass, EntityDaoConfig daoConfig,
                                                      DaoSeed daoSeed) {
        ASEntityDao<E> entityDao = ASEntityDao.<E>newASEntityDao(this, metaspace);

        entityDao.init(cluster, entityClass, daoSeed.getName(), daoConfig);

        return entityDao;
    }

    @Override
    protected <K, V> ControlDao<K, V> makeNew(ControlDaoType daoType, DaoSeed daoSeed) {
        return ASControlDao.<K, V>newASControlDao(this, daoType, daoSeed, metaspace, tupleCodec, cluster);
    }

    @Override
    public GroupMemberMediator newGroupMemberMediator() {
        return new ASGroupMemberMediator(this);
    }

    @Override
    public InvocationService getInvocationService() {
        return asInvocationService;
    }

    @Override
    public GenericBackingStore getBackingStore() {
        return backingStore;
    }

    @Override
    public void modelChanged() {

        final ControlDao<Object, Object> masterControlDao = this.cluster.getGroupMembershipService().getMasterDao();

        if (!masterControlDao.lock(DefaultControlKey.ModelChangedKey, 10000)) {
            this.logger.log(Level.SEVERE, "Failed to acquire cluster lock after model change!");
            return;
        }

        try {
            if (!this.cluster.getGroupMembershipService().suspendAll()) {
                this.logger.log(Level.SEVERE, "Failed to suspend all agents.");
                return; // after finally
            }

            try {
                this.cluster.getMetadataCache().reloadTypes();
            } finally {
                if (!this.cluster.getGroupMembershipService().resumeAll()) {
                    this.logger.log(Level.SEVERE, "Failed to resume all agents.");
                }
            }
        } catch (Throwable t) {
            this.logger.log(Level.SEVERE, "Failure during model change.", t);
        } finally {
            if (!masterControlDao.unlock(DefaultControlKey.ModelChangedKey)) {
                this.logger.log(Level.SEVERE, "Failed to release cluster lock after model change!");
            }
        }
    }

    private String getHostname() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        String hostName = addr.getHostName();

        return hostName;
    }

    private String normalize(String input) {
        String output;
        output = input.replaceAll("\\.", "_");

        return output;
    }
    
    public Cluster getCluster() {
    	return this.cluster;
    }
    
    public boolean isMetaspaceClosingConn() {
		return metaspaceClosingConn;
	}


    //-----------------

    protected class DynamicSpaceListener implements SpaceDefListener {
        @Override
        public void onDefine(SpaceDef spaceDef) {
            String spaceName = spaceDef.getName();

            logger.log(Level.FINE, "Received new spacedef notification [" + spaceName + "]");

            //-----------------

            ControlDao controlDao = controlDaos.get(spaceName);
            if (controlDao != null) {
                return;
            }

            EntityDao entityDao = entityDaos.get(spaceName);
            if (entityDao != null) {
                return;
            }

            Tuple tuple = spaceDef.getContext();
            if (tuple == null) {
                return;
            }

            //-----------------

            if (tuple.getBoolean(ASControlDao.CTX_PROP_IS_CONTROL_DAO)) {
                String creatorMemberId = tuple.getString(ASControlDao.CTX_PROP_WHO);
                Member self = metaspace.getSelfMember();

                if (self.toString().equals(creatorMemberId)) {
                    return;
                }

                ControlDaoType daoType = (ControlDaoType) tupleCodec
                        .getFromTuple(tuple, ASControlDao.CTX_PROP_CONTROL_DAO_TYPE, DataType.SerializedBlob);

                DaoSeed daoSeed = (DaoSeed) tupleCodec
                        .getFromTuple(tuple, ASControlDao.CTX_PROP_DAO_SEED, DataType.SerializedBlob);

                ASDaoProvider.this.internalCreate(daoType, daoSeed);

                logger.log(Level.FINE, "Created new spacedef from notification [" + spaceName + "]");
            }
        }

        // @Override (API of E19)
        public void onDrop(SpaceDef spaceDef) {
        }

        // @Override (API of E16-through-E18)
        public void onDrop(String spaceDef) {
        }

        // @Override (API of V7)
        public void onAlter(SpaceDef oldSpaceDef, SpaceDef newSpaceDef) {
        }
    }
    
    // AS security module callback class
	class ASAuthCallback implements AuthenticationCallback {
		@Override
		public void createUserCredential(AuthenticationInfo info) {
			// Currently the authentication methods are: USERPWD and X509V3
			// as set in the security policy file.
			if (info.getAuthenticationMethod() == Method.USERPWD) {
				UserPwdCredential userCredential = (UserPwdCredential) info
						.getUserCredential();
				if (domainName != null && !"".equals(domainName)) {
					userCredential.setDomain(domainName);
				}
				userCredential.setUserName(userName);
				userCredential.setPassword(password.toCharArray());
			} else if (info.getAuthenticationMethod() == Method.X509V3) {
				X509V3Credential userCredential = (X509V3Credential) info
						.getUserCredential();
				userCredential.setKeyFile(requesterKeyFile);
				userCredential.setPassword(password.toCharArray());
			}
		}

		@Override
		public void onCleanup() {
			// perform any necessary object clean up here
		}
	}
	
	public static boolean isGlobalVar(String str) {
		if (str==null)
			return false;
		
		if (str.startsWith("%%") && str.endsWith("%%")) {
			String[] tokens = str.split("%%");
			if (tokens.length==2)
				return true;
		}
		return false;
	}
    
    public static String stripGvMarkers(String variable) {
		int firstIndex = variable.indexOf("%%");
		String stripVal = variable.substring(firstIndex + 2);
		if(stripVal.indexOf("%%")!=-1){
			stripVal = stripVal.substring(0, stripVal.indexOf("%%"));
		}
		return	stripVal;
	}


}
