/**
 * 
 */
package com.tibco.be.bemm.functions;

import com.tibco.be.util.config.topology.*;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.management.exception.BEMMException;
import com.tibco.cep.runtime.util.SystemProperty;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Properties;

public class MMTools {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

    private static final String BRK = System.getProperty("line.separator", "\n");
    private static final String DEFAULT_SITE_NAME = "Site";

    private Properties env;
	private Logger logger;
    private String[] argv;

    private BemmSite bemmSite = null;
    private TopologyLoader topologyLoader;

    private File stFilePath;
    private String siteName;
    private String operation;
    private String clusterName;
    private String hostNameOrIp;
    private String dusStId;
    private String duName;
    private String puStId;
    private BemmCluster cluster;
    private BemmMappedHost mappedHost;
    private BemmDeploymentUnit du;
    private BemmPU pu;

    private MMTools(String[] args){
		this.argv = args;
		env = new Properties();
	}

	public static void main(String[] args) {
		MMTools mmTools = new MMTools(args);

		mmTools.init();
		mmTools.start();
	}

    private String getCmdLine() {
        StringBuilder cmdLn = new StringBuilder();
        for (String arg : argv) {
            cmdLn.append(arg).append(" ");
        }
        return cmdLn.toString();
    }
	
	private void init(){
        System.setProperty(SystemProperty.ENGINE_IS_MM_TOOLS.getPropertyName(), "true");

        //Uses the default property file if --propFile NOT specified
        final String defProp = System.getProperty("wrapper.tra.file", "mm-tools.tra");
        env.setProperty("be.mm.tools.tra.file", defProp);

        //Interpret command line
        parseArgumentsAndSetEnv();  //overrides be.mm.tools.tra.file if --propFile is specified

        //we need to setup the logger in here and not before in order to print the CL usage to std output
        logger = LogManagerFactory.getLogManager().getLogger("MM_Tools");
        logger.log(Level.INFO,"");    //one empty line for a cleaner log file

        logger.log(Level.INFO, "Starting command line utility MM-Tools with CL options '%s'", getCmdLine());

        //Load TRA file into env
        loadTraFileIntoEnv();
        logger.log(Level.INFO, "Property file loaded: '%s'", env.getProperty("be.mm.tools.tra.file"));

        validatePropsAndSetState();
    }

    private void validatePropsAndSetState(){
        siteName = env.getProperty("be.mm.tools.site");
        if (siteName == null || siteName.trim().isEmpty()) {
            logger.log(Level.INFO, "Site name not specified. " +
                       "Please specify it using the -s CL option" + BRK);
            System.exit(-1);
        }

        //This prop's value is specified in the TRA file
        final String propStr = env.getProperty("be.mm.topology.file") != null
                               ? "be.mm.topology.file"
                               : "asg.mm.topology.file";

        final String stFileStr = env.getProperty(propStr);

        if ( stFileStr==null || stFileStr.trim().isEmpty() ||
            !(stFilePath = new File(stFileStr)).exists() ) {

            logger.log(Level.ERROR, "Property %s is null, empty, or has the wrong path. " +
                                    "Please open the TRA file '%s' and set " +
                                    "the value of this property to the path of the desired ST file" +
                                    BRK, propStr, env.getProperty("be.mm.tools.tra.file"));

            System.exit(-1);
        }

        operation = env.getProperty("be.mm.tools.operation");
        clusterName = env.getProperty("be.mm.tools.cluster");
        hostNameOrIp = env.getProperty("be.mm.tools.hostname");
        duName = env.getProperty("be.mm.tools.du");
        //PuStId is the id attribute of the PU element in the st file. Do not confuse with puid
        puStId = env.getProperty("be.mm.tools.pu");

        parseSiteTopologyFile();
        setCluster(clusterName);
        setMappedHost();
        setDeploymentUnit();

        if (operation.matches("start|stop") )
            setProcessingUnit();
    }

    private void setCluster(String clusterName) {
        cluster = bemmSite.getCluster(clusterName);

        if (cluster == null) {
            logger.log(Level.ERROR, "No cluster with name '%s' defined in " +
                    "site topology file: '%s'", clusterName, stFilePath.getAbsolutePath());
            logger.log(Level.INFO, "Verify if you associated the correct cluster name with the -c option. Current value is '%s'"+BRK, clusterName);
            System.exit(-1);
        }
    }

    private BemmMappedHost setMappedHost() {
        try {
            mappedHost = cluster.getMappedHost(hostNameOrIp);
        } catch (UnknownHostException e) {
            mappedHost = null;
        }

        if (mappedHost == null) {
            logger.log(Level.ERROR, "No host with name or IP '%s' defined in " +
                    "site topology file: '%s'", hostNameOrIp, stFilePath.getAbsolutePath());
            logger.log(Level.INFO, "Verify if you associated the correct host name or IP with the -m CL option. Current value is '%s'"+BRK, hostNameOrIp);
            System.exit(-1);
        }
        return mappedHost;
    }

    private void setDeploymentUnit() {
        du = mappedHost.getDeployUnit(duName);

        if (du == null) {
            logger.log(Level.ERROR, "No deployment unit with name '%s' maps to host with id '%s' in " +
                    "site topology file '%s'", duName, mappedHost.getHostResource().getId(), stFilePath.getAbsolutePath());
            logger.log(Level.INFO, "Verify if you associated the correct deployment unit name with the -du CL option. Current value is '%s'"+BRK, duName);
            System.exit(-1);
        }

        dusStId = du.getId();
    }
    
    private void setProcessingUnit() {
        pu = du.getPU(puStId);

        if (pu == null) {
            logger.log(Level.ERROR, "No processing unit with id '%s' maps to deployment unit with id '%s' in " +
                       "site topology file: '%s'", puStId, dusStId, stFilePath.getAbsolutePath());
            logger.log(Level.INFO, "Verify if you associated the correct processing unit id with the -pu CL option. Current value is '%s'"+BRK, puStId);
            System.exit(-1);
        }
    }


    private void start(){
        final String machineFqPath = mappedHost.getHostFqName();
        final String puFqPath = mappedHost.getDeployUnitFromId(dusStId).getFqName();
		String passwd = env.getProperty("be.mm.tools.password");
        String username = env.getProperty("be.mm.tools.username");

        //If username/password are not specified in the CL,
        //then retrieve it from the ST file
        if (passwd == null || passwd.trim().isEmpty())
            passwd = mappedHost.getHostResource().getPassword();

        if (username == null || username.trim().isEmpty())
            username = mappedHost.getHostResource().getUsername();

        if(operation.equalsIgnoreCase("deploy")){
            deployDU(machineFqPath, username, passwd);
		} else if(operation.equalsIgnoreCase("start")){
            startPU(machineFqPath, puFqPath, username, passwd);
		} else if(operation.equalsIgnoreCase("stop")) {
            stopPU(username, passwd);
		}
	}

    private void stopPU(String username, String pwd) {
        final String ip = mappedHost.getHostResource().getIpAddress();
        final String port = pu.getJmxPort();

        logger.log(Level.INFO, "Attempting to Stop processing unit with id '%s' on host '%s:%s'", pu.getId(), ip, port);

        MBeanServerConnection mbsc = null;

        try {
            mbsc = getMBeanServerConn(ip, new Integer(port), username, pwd);
        } catch (IOException e) {
            logger.log(Level.ERROR, "Connection to MBean on remote host %s:%s FAILED", ip, port);
            logger.log(Level.ERROR, "FAILED to Stop processing unit with id '%s' on host '%s:%s'", pu.getId(), ip, port);
            System.exit(1);
        }

        ObjectName on=null;
        try {
            on = new ObjectName("com.tibco.be:dir=Methods,Group=Engine");
        } catch (MalformedObjectNameException ignore) {
            //ignore because this object name is well formed so this should never happen
        }

        try {
            mbsc.invoke(on, "StopEngine", null, null);
        } catch (Exception e) {
            logger.log(Level.ERROR, "'StopEngine' operation FAILED to Stop processing unit with id '%s' on host '%s:%s'", pu.getId(), ip, port);
            System.exit(1);
        }

        logger.log(Level.INFO, "Processing unit with id '%s' SUCCESSFULLY stopped on host %s:%s", pu.getId(), ip, port);
    }

    private MBeanServerConnection getMBeanServerConn(String IP,
                                                     int port,
                                                     String username,
                                                     String pwd) throws IOException {
       logger.log(Level.DEBUG, "Trying to establish remote connection to MBean on host %s:%s", IP, port);

       JMXServiceURL url = new JMXServiceURL(
               "service:jmx:rmi:///jndi/rmi://" + IP + ":" + port + "/jmxrmi");

        final String[] credentials = {username, pwd};

        HashMap<String, Object> jmxEnv = new HashMap<String, Object>();
        jmxEnv.put(JMXConnector.CREDENTIALS, credentials);

        JMXConnector jmxc = JMXConnectorFactory.connect(url, jmxEnv);

       logger.log(Level.DEBUG, "ESTABLISHED connection to remote MBean on host %s:%s", IP, port);
       return jmxc.getMBeanServerConnection();
    }
    
    private void deployDU(String machineFqPath, String username, String passwd) {
        final String result;
        try {
            logger.log(Level.INFO, "Attempting to Deploy deployment unit '%s' to host '%s'", duName, hostNameOrIp);

            result = topologyLoader.deployDU(machineFqPath, dusStId, username, passwd);
        } catch (BEMMException e) {
            logger.log(Level.ERROR, TopologyLoader.DEPLOY_DU_FAIL_MSG + "Cause: %s", dusStId, machineFqPath, e.getCause().getMessage());
            return;
        }

        final String duName = result.split("\\$")[0].split("#")[0];
        final String depStatus = result.split("\\$")[0].split("#")[1];

        if(depStatus.equalsIgnoreCase(TopologyLoader.SUCCESS)) {
            logger.log(Level.INFO, "Deployment unit '%s' SUCCESSFULLY deployed to host %s", duName, hostNameOrIp);
        } else{
            logger.log(Level.ERROR, TopologyLoader.DEPLOY_DU_FAIL_MSG, dusStId, machineFqPath);
        }
    }

	private void loadTraFileIntoEnv() {
        File propertyFile=null;
        try {
            propertyFile = new File(env.getProperty("be.mm.tools.tra.file"));
        } catch (Exception e) {
            logger.log(Level.ERROR, e, "TRA file path incorrectly specified");
            System.exit(-1);
        }

        final Properties prop = new Properties();
        if (propertyFile.exists()) {
            try {
                prop.load(new FileInputStream(propertyFile));
            } catch (IOException e) {
                throw new RuntimeException("Cannot load TRA file: "
                                            + propertyFile.getAbsolutePath(), e);
            }
        }
        env.putAll(prop);

        final String BE_HOME = env.getProperty(SystemProperty.BE_HOME.getPropertyName());
        if (BE_HOME!=null) {
            System.setProperty(SystemProperty.BE_HOME.getPropertyName(),BE_HOME);
        }
	}

	private void parseSiteTopologyFile() {
    	try{
            topologyLoader = new TopologyLoader(stFilePath, logger);
            bemmSite = topologyLoader.getSite(siteName);

            if (bemmSite == null) {
                logger.log(Level.ERROR,"No site with name: '" +
                            siteName + "'exists in ST file: " + stFilePath.getAbsolutePath());
                logger.log(Level.INFO, "Verify if you associated the correct site name with the -s CL option" + BRK);
                System.exit(-1);
            }
        }
        catch(Exception e){
            logger.log(Level.ERROR, e, "Site topology file '%s' not loaded correctly. Cause: %s" + BRK,
                    stFilePath.getAbsolutePath(), e.getMessage());
            System.exit(-1);
    	}
    }
	
    private void startPU(String machineFqPath, String puFqPath, String username, String passwd) {
		final String localBeHome = env.getProperty("tibco.env.BE_HOME");
        RemoteEngineBuilder reb = null;

        try {
            logger.log(Level.INFO, "Attempting to Start processing unit with id '%s' on host '%s'", puStId, hostNameOrIp);
            reb = topologyLoader.getRemoteEngineBuilder(machineFqPath, puFqPath+"~"+puStId, username, passwd,localBeHome);
        } catch (BEMMException e) {
            logger.log(Level.ERROR, TopologyLoader.START_PU_FAIL_MSG + "Cause: %s", puFqPath, machineFqPath, e.getCause().getMessage());
        }

        String result="";

        if(reb != null && reb.getStartMethod().equalsIgnoreCase("hawk")){
			String domain = env.getProperty("be.mm.tools.Domain", "");
			String service = env.getProperty("be.mm.tools.TIBHawkService", "7474");
			String network = env.getProperty("be.mm.tools.TIBHawkNetwork", "");
			String daemon = env.getProperty("be.mm.tools.TIBHawkDaemon", "tcp:7474");

            result = reb.remoteStartHawk(domain, service, network, daemon);
		} else if (reb != null) {
            result = reb.launchRemoteCall();
        }

        if(result.equalsIgnoreCase(TopologyLoader.SUCCESS)) {
            logger.log(Level.INFO, "Processing unit with id '%s' SUCCESSFULLY started on host '%s'", puStId, hostNameOrIp);
        } else {
            logger.log(Level.ERROR, TopologyLoader.START_PU_FAIL_MSG + result, puFqPath, machineFqPath);
        }
	}
	
    private void parseArgumentsAndSetEnv() {

        try {
        	boolean foundCluster = false;
            boolean foundMachine = false;
            boolean foundOP = false;
            boolean foundDU = false;
            boolean foundPU = false;
            boolean foundEngName = false;

            //On each iteration of the loop the variable i is incremented twice.
            //The second increment is to move onto the value assigned to the Command Line option
            for (int i = 0; i < argv.length; i++) {
                String key = argv[i];

                //-h or /h or -help or /help
                if (key.matches("(?i)-h|/h|-help|/help|--help|/--help")) {
                    printUsage();
                    System.exit(0);
                } else if (key.matches("(?i)-n|/n|-name|/name")) {
                    System.setProperty(SystemProperty.ENGINE_NAME.getPropertyName(), argv[++i]);
                    foundEngName = true;
                } else if (key.matches("(?i)-property|/property|-system:propFile|--propFile|/--propFile")) {
                    ++i;
                    env.put("be.mm.tools.tra.file", argv[i]);
                } else if (key.equalsIgnoreCase("-s")) {
                    ++i;
                    env.put("be.mm.tools.site", argv[i]);
                } else if (key.equalsIgnoreCase("-op")) {
                    if (foundOP) {
                        printUsage();
                        System.exit(-1);
                    }
                    env.put("be.mm.tools.operation", argv[++i]);
                    foundOP = true;
                } else if (key.equalsIgnoreCase("-c")) {
                    if (foundCluster) {
                        printUsage();
                        System.exit(-1);
                    }
                    ++i;
                    env.put("be.mm.tools.cluster", argv[i]);
                    foundCluster = true;
                } else if (key.equalsIgnoreCase("-m")) {
                    if (foundMachine) {
                        printUsage();
                        System.exit(-1);
                    }
                    ++i;
                    env.put("be.mm.tools.hostname", argv[i]);
                    foundMachine = true;
                } else if (key.equalsIgnoreCase("-user")) {
                	++i;
                	env.put("be.mm.tools.username", argv[i]);
                } else if (key.equalsIgnoreCase("-pwd")) {
                	++i;
                	env.put("be.mm.tools.password", argv[i]);
                } else if (key.equalsIgnoreCase("-pkf")) {
                    System.setProperty(JschClient.MM_TOOLS_PRIVATE_KEY_FILE, argv[++i]);
                } else if (key.equalsIgnoreCase("-pph")) {
                    System.setProperty(JschClient.MM_TOOLS_PRIVATE_KEY_PASS_PHRASE, argv[++i]);
                } else if (key.equalsIgnoreCase("-du")) {                //TODO: Build tokenized string of duStIds
                    if (foundDU) {
                        printUsage();
                        System.exit(-1);
                    }
                    ++i;
                    env.put("be.mm.tools.du", argv[i]);
                    foundDU = true;
                } else if (key.equalsIgnoreCase("-pu")) {
                    if (foundPU) {
                        printUsage();
                        System.exit(-1);
                    }
                    ++i;
                    env.put("be.mm.tools.pu", argv[i]);
                    foundPU = true;
                } else {
//                    System.out.println(String.format("Illegal CL option specified: '%s'. Option IGNORED.", argv[i++]));
                }
            }

            if (!foundOP || !foundMachine || !foundCluster || !foundDU) {
                printUsage();
                System.exit(-1);
            }

            final String oper = env.getProperty("be.mm.tools.operation");
            if (oper==null || !oper.matches("(?i)start|stop|deploy")) {
                printUsage();
                logger = LogManagerFactory.getLogManager().getLogger("MM_Tools");
                logger.log(Level.ERROR, "Illegal operation specified: '%s'. " +
                "The -op CL option must be one of [deploy,start,stop]." + BRK, oper);
                System.exit(-1);
            }

            if (oper.matches("(?i)start|stop") && !foundPU) {
                printUsage();
                logger = LogManagerFactory.getLogManager().getLogger("MM_Tools");
                logger.log(Level.ERROR, "-pu CL option not specified but required for operation '%s'"+ BRK, oper);
                System.exit(-1);
            }

            //If engine name not specified, default to MM-Tools
            if (!foundEngName)
                System.setProperty(SystemProperty.ENGINE_NAME.getPropertyName(), "MM-Tools");

        } catch (ArrayIndexOutOfBoundsException e) {
            printUsage();
            System.exit(-1);
        }
    }

    private void printUsage() {
        System.out.println("mm-tools -op <'deploy','start','stop'> -c <cluster name> -m <host name or IP> -du <deployment-unit name>" + BRK
                + "         [-pu <processing-unit id>] [-s <site name>] [-user <username>] [-pwd <password>]" + BRK
                + "         [-pkf <private key file>] [-pph <passphrase>] [--propFile <tra file>] [-n engine name] [-h for help]"+ BRK);

        System.out.println("--propFile Fully qualified path to the TRA file. [Optional]"+ BRK);

        System.out.println("-op  The operation to be performed. It must be one of:" + BRK
        + "     'deploy' to deploy a deployment unit to the host specified" + BRK
        + "     'start'  to start a processing-unit on the host specified"+ BRK
        + "     'stop'   to stop a processing-unit on the host specified"+ BRK);

        System.out.println("-c   The cluster name for which the operation will be performed"+ BRK
        + "     The cluster name must match the 'name' attribute of the <cluster> element in the ST file"+ BRK);

        System.out.println("-m   The identifier (name or IP) of the host where the operation will be performed"+ BRK
        + "     The host identifier must match the value of a <hostname> or <ip> element in the ST file"+ BRK);

        System.out.println("-du  The name of the deployment unit to deploy, to remote start, or to remote stop"+ BRK
        + "     The deployment unit name must match the 'name' attribute of a <deployment-unit> element in the ST file"+ BRK);

        System.out.println("-pu  Processing unit config id. [Optional] but required if the operation is 'start' or 'stop'" + BRK
        + "     The Processing unit id must match the 'id' attribute of the <processing-unit-config> element, NOT the 'puid' attribute"+ BRK);

        System.out.println("-s   The site name for which the operation will be performed." + BRK
        + "     The site name must match the 'name' attribute of the <site> element in the ST file" + BRK);

        System.out.println("-user  username used to login onto the remote machine. [Optional]" + BRK
        + "       If not specified, the value of the 'username' attribute of the <user-credentials> element "
        + "in the in ST file will be used"+ BRK);

        System.out.println("-pwd  password used to login onto the remote machine. [Optional]" + BRK
        + "      If not specified, the value of the 'password' attribute of the <user-credentials> element "
        + "in the in ST file will be used"+ BRK);

        System.out.println("-pkf  Fully qualified path to the file containing the private key certificate. [Optional]" + BRK
        + "      The corresponding public key is assumed to be in the same directory, in a file with the same name but with extension .pub"+ BRK
        + "      If not specified password based authentication will be used. If specified the password will be ignored"+ BRK);

        System.out.println("-pph  Passphrase associated with the private key certificate. Do not specify if passphrase not defined. [Optional]" + BRK);

        System.out.println("-n   Name of the MM-Tools engine and corresponding log file name. [Optional]" + BRK
        + "     If the -n option is not specified the default name 'MM-Tools' will be used"+ BRK);

        System.out.println("-h   Displays this usage. [Optional]" + BRK
        + "      Same as -help or /help or /h."+ BRK);
    }
}
