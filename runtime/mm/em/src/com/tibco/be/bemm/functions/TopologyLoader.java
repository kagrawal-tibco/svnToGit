/**
 *
 */
package com.tibco.be.bemm.functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.xml.sax.InputSource;

import com.tibco.be.util.XiSupport;
import com.tibco.be.util.config.topology.BemmAgent;
import com.tibco.be.util.config.topology.BemmCluster;
import com.tibco.be.util.config.topology.BemmDeploymentUnit;
import com.tibco.be.util.config.topology.BemmMappedHost;
import com.tibco.be.util.config.topology.BemmPU;
import com.tibco.be.util.config.topology.BemmSite;
import com.tibco.be.util.config.topology.MMIoXml;
import com.tibco.be.util.config.topology.MMStFilesFinder;
import com.tibco.be.util.config.topology.TopoSchemaProvider;
import com.tibco.be.util.config.topology.TopologyNS;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.management.exception.BEMMException;
import com.tibco.cep.runtime.service.management.exception.BEMMInvalidAccessException;
import com.tibco.cep.runtime.service.management.exception.BEMMInvalidUserRoleException;
import com.tibco.cep.runtime.util.ProcessInfo;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmNamespaceProvider;

public class TopologyLoader {

    static final String SUCCESS = "success";
    private static final String AUTHENTICATION_FAIL_MSG = "FAILED to authenticate user '%s'. ";
    static final String DEPLOY_DU_FAIL_MSG = "FAILED to deploy deployment unit with id '%s' to host '%s'. ";
    static final String START_PU_FAIL_MSG = "FAILED to Start processing unit with id '%s' on host '%s'. ";
    private static final String EXEC_CMD_FAIL_MSG = "Command NOT executed on host '%s'. ";

    private static TopologyLoader instance;
    private Logger logger;
    private static List<File> stFiles;   //File objects for all the st files (files with .st extension) under BE_HOME/mm/config and set with
    private HashMap<String, BemmSite> siteNameToSite;
    private boolean isMMTools;

    public interface  CACHE_MODE {
        final String IN_MEMORY = "In-Memory";
        final String TIBCO = "Tibco";
        final String COHERENCE = "Oracle";
    }

    private TopologyLoader(){
        logger = LogManagerFactory.getLogManager().getLogger(this.getClass());
        siteNameToSite = new HashMap<String, BemmSite>();

        stFiles = MMStFilesFinder.getStFiles();
        parseTplgyFiles();
    }

    public TopologyLoader(File stFile, Logger logger){	//for MM tools
        this.logger = logger;
        siteNameToSite = new HashMap<String, BemmSite>();

        isMMTools = Boolean.parseBoolean(System.getProperty(
                        SystemProperty.ENGINE_IS_MM_TOOLS.getPropertyName()));

        parseTplgyFile(stFile);
    }

    //The first time this method is called it starts the entire process of parsing the site topology files
    static synchronized TopologyLoader getInstance() {
        if (instance == null){
            instance = new TopologyLoader();
        }
        return instance;
    }
    
    public Set<String>
    getSiteNames() {
        return siteNameToSite.keySet();
    }

    public BemmSite getSite(String siteName) {
        return siteName == null ? null : siteNameToSite.get(siteName.trim());
    }

	private void parseTplgyFiles() {
        for (File stFile : stFiles) {
            parseTplgyFile(stFile);
        }
    }

	private void parseTplgyFile(File stFile) {
        InputStream is = null;
        try {
            logger.log(Level.INFO, "Attempting to parse site topology file: " + stFile.getAbsolutePath());

            is = new FileInputStream(stFile);
            final SmNamespaceProvider smNsProvider = new TopoSchemaProvider();
            final XiNode topoDoc = XiSupport.getParser().parse(new InputSource(is), smNsProvider);

            final XiNode siteNode = XiChild.getChild(topoDoc, TopologyNS.Elements.SITE);
            final String siteName = siteNode.getAttributeStringValue(TopologyNS.Attributes.NAME);

            if (siteNameToSite.get(siteName) == null) {
                siteNameToSite.put(siteName, new BemmSite(stFile,siteName));
            }

            siteNameToSite.get(siteName).addCluster(siteNode);

            logger.log(Level.INFO, "Site topology file '%s' SUCCESSFULLY parsed.",
                                    stFile.getAbsolutePath());

        } catch (Exception e) {
            logger.log(Level.ERROR, "Exception occurred while parsing site topology file: "
                    + stFile.getAbsolutePath(),e);

            if(isMMTools)
                System.exit(1);

        } finally {
            if(is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    logger.log(Level.DEBUG, "Could not close file handler for site topology file: " + stFile.getAbsolutePath(), e);
                }
        }
	}

	private String getXMLContent(){                                       //TODO: Send the info of multiple ST files. Add note <sites> and then add all the 'sites'
        StringBuilder stFilesContents = new StringBuilder();

        for (File stFile : stFiles) {
            try {
              BufferedReader input =  new BufferedReader(new FileReader(stFile));
              try {
                String line = null;
                while (( line = input.readLine()) != null){
                    if (line.contains("hostname")) {
                        String name = line.split("<(.*?)>")[1];
                        String ipLine = input.readLine();           //this line contains the ip address
                        String ip = ipLine.split("<(.*?)>")[1];

                        //convert localhost to the actual IP                         //TODO: Verify if it's OK for multiple network cards
                        if (ip.equalsIgnoreCase("localhost")) {
                            ip = InetAddress.getLocalHost().getHostAddress();
                            ipLine = ipLine.replaceFirst("<ip>.*</ip>", "<ip>" + ip + "</ip>");
                        }

                        try {
                            line = line.replaceFirst("<hostname>.*</hostname>",
                                    "<hostname>"+ ProcessInfo.ensureFQDN(name, ip)+"</hostname>");
                        } catch (UnknownHostException e) {
                            //IP does not resolve to the hostname specified, hence replace hostname with IP
                            line = line.replaceFirst("<hostname>.*</hostname>",
                                    "<hostname>" + ip + "</hostname>");
                        }

                        stFilesContents.append(line.replaceAll("\\\\", "\\\\\\\\")).
                                append(System.getProperty("line.separator")).
                                append(ipLine.replaceAll("\\\\", "\\\\\\\\"));
                    }  else {
                        stFilesContents.append(line.replaceAll("\\\\", "\\\\\\\\"));
                    }
                      stFilesContents.append(System.getProperty("line.separator"));
                }
              }
              finally {
                input.close();
              }
            }
            catch (IOException ex){ //this exception can occur in the finally block.
                logger.log(Level.WARN, "Could not read site topologyfile %s. " +
                        "Proceeding into next file.", stFile.getAbsolutePath());
            }
        }
        return "<sites>" + stFilesContents.toString() + "</sites>";
    }

	private String[] getSiteClustersFqns(BemmSite site){
		final Collection<BemmCluster> clusterList = site.getClusters();

		String[] clusterFqns = new String[clusterList.size()];

        int i=0;
        for (BemmCluster cluster : clusterList) {
            clusterFqns[i++] = cluster.getFqname();
        }

        return clusterFqns;
	}

	private String[] getClusterHostsFqns(BemmCluster cluster){
        final Collection<BemmMappedHost> clusterHosts = cluster.getClusterHosts();

		String[] hostFqns = new String[clusterHosts.size()];

        int i=0;
        for (BemmMappedHost host : clusterHosts) {
            hostFqns[i++] = host.getHostFqName();
        }

        return hostFqns;
	}

	private String[] getHostPUs(BemmMappedHost machine){
        ArrayList<String> pus = new ArrayList<String>();

        Object[] machineMappedDus = machine.getMappedDUs();
        for (Object du : machineMappedDus) {
            Object[] duMappedPus = ((BemmDeploymentUnit)du).getMappedPus();
            for (Object pu : duMappedPus)
                //The pu.DuNameQualifiedId is used to differentiate the PUs with the same name,
                //but from different DU's, that are deployed to this host
                pus.add( machine.getHostFqName()+"/"+ ((BemmPU)pu).getDuNameQualifiedId()+"," + ((BemmPU)pu).getJmxPort() );
        }
        return pus.toArray(new String[pus.size()]);
	}

	private String[] getPuAgents(BemmPU pu){
		ArrayList<BemmAgent> agentList = pu.getAgentList();
		if(agentList == null){
			logger.log(Level.ERROR, "No Agent found for Processing Unit ID: "+pu.getPuid()+
                    ". Please check your site topology file and make sure the PU element is correctly set.");
			return new String[0];
		}

		String[] agents = new String[agentList.size()];
		for(int i=0;i<agentList.size();i++){
			agents[i] = agentList.get(i).getFqNameWithType();
		}
		return agents;
	}

	private String[] getChildren(String entityFqPath) {
        Object entityObject = getEntityObject(entityFqPath);

        if (entityObject instanceof String) //if it's String it's because an error occurred
            return new String[]{entityObject.toString()};

		String[] fqPath = entityFqPath.substring(1).split("/"); //remove the prefixing "/"
		switch(fqPath.length){
			case 1: return getSiteClustersFqns((BemmSite) entityObject);
			case 2: return getClusterHostsFqns((BemmCluster) entityObject);
			case 3: return getHostPUs((BemmMappedHost) entityObject);
			case 4: return getPuAgents((BemmPU) entityObject);
			default: return new String[]{};
		}
	}

    /**
     * This method parses the entity FqPath and returns its associated object or null
     * if entity path is invalid or no associated object exists
     * @param entityFqPath FQPath of the entity for which the representing object is to be obtained
     * @return The object representing the entity specified by its fully qualified path
     *         <p\> null if object not found or entity path is invalid
     * @throws RuntimeException if some exception occurs
     * */
      public Object getEntityObject(String entityFqPath) {
          if(entityFqPath == null || entityFqPath.trim().isEmpty())
              return "Entity path not specified";

          //remove the prefixing "/" is it exists
          //fpPath is of the form: SITE_NAME/CLUSTER_NAME/HOST_NAME-or-IP/DU_NAME~PU-ST-ID
          String[] fqPath = entityFqPath.startsWith("/") ?
                            entityFqPath.substring(1).split("/") :
                            entityFqPath.split("/");

          if (fqPath.length==0)
              return "Entity path not specified";

          return getEntityObject(fqPath);
      }

    public Object getEntityObject(String[] entityFqPath) {
        String msg="";
        try {
            final BemmSite site = siteNameToSite.get(entityFqPath[0].trim());

            if (site == null) {
                msg = String.format("Could not find site topology configuration for entity path: %s", Arrays.toString(entityFqPath));
                logger.log(Level.WARN, msg);
                return msg;
            }

            if (entityFqPath.length == 1) return site;

            final BemmCluster cluster = site.getCluster(entityFqPath[1]);
            if (cluster == null) {
                msg = String.format("Could not find cluster configuration for entity path: %s", Arrays.toString(entityFqPath));
                logger.log(Level.WARN, msg);
                return msg;
            }

            if (entityFqPath.length == 2) return cluster;

            final BemmMappedHost machine = cluster.getMappedHost(entityFqPath[2]);
            if (machine == null) {
                msg = String.format("Could not find host configuration for entity path: %s", Arrays.toString(entityFqPath));
                logger.log(Level.WARN, msg);
                return msg;
            }

            if (entityFqPath.length == 3) return machine;

            //PuStId is the id attribute of the PU element in the st file. Do not confuse with puid
            final String[] duNameAndPuStId = entityFqPath[3].trim().split("~");

            if(duNameAndPuStId.length != 2) {
                msg = String.format("Malformed DU~PU-id pair for entity path: %s", Arrays.toString(entityFqPath));
                logger.log(Level.WARN, msg);
                return msg;
            }

            final BemmDeploymentUnit du = machine.getDeployUnit(duNameAndPuStId[0]);
            if (du == null) {
                msg = String.format("Could not find deployment unit configuration for entity path: %s", Arrays.toString(entityFqPath));
                logger.log(Level.WARN, msg);
                return msg;
            }

            final BemmPU pu = du.getPU(duNameAndPuStId[1]);
            if (pu == null) {
                msg = String.format("Could not find processing unit configuration for entity path: %s", Arrays.toString(entityFqPath));
                logger.log(Level.WARN, msg);
                return msg;
            }

            if (entityFqPath.length == 4) return pu;


            msg = String.format("UNEXPECTED entity found for entity path: %s. " +
            "Entity path should be of the form: SITE_NAME/CLUSTER_NAME/HOST_NAME-or-IP/DU_NAME~PU-ST-ID", Arrays.toString(entityFqPath));

            logger.log(Level.WARN, msg);
            return msg;

        } catch (Exception e) {
            msg = String.format("Exception occurred while parsing entity: %s",Arrays.toString(entityFqPath));
            logger.log(Level.ERROR, e, msg);
            return msg;
        }
    }

	public BemmMappedHost getMappedHost(String machineFq) throws BEMMException {
        Object entityObject = getEntityObject(machineFq);

        if (entityObject instanceof BemmMappedHost)
            return (BemmMappedHost) getEntityObject(machineFq);

        if (entityObject instanceof String) {
            throw new BEMMException((String) entityObject);
        }

        throw new BEMMException(String.format("Entity with FQ path '%s' is not of type machine.", machineFq));
	}

    private String getDecodedString(String encoded) {
        if (encoded == null || encoded.trim().isEmpty())
              return ("");
        String pwd = encoded;
        try {
              if (ObfuscationEngine.hasEncryptionPrefix(encoded)) {
            	  pwd = new String(ObfuscationEngine.decrypt(encoded));
              }
        } catch (AXSecurityException e) {
              e.printStackTrace();
        }
        return pwd;
    }

    String startPU(String machineFqPath, String puStIdFqPath, String username, String passwd) throws BEMMException {
		final RemoteEngineBuilder reb = getRemoteEngineBuilder(machineFqPath, puStIdFqPath, username, passwd, null);
		return reb.launchRemoteCall();
	}

    RemoteEngineBuilder getRemoteEngineBuilder(String machineFqPath, String puStIdFqPath,
                                               String username, String passwd, String localBeHome) throws BEMMException {

        final BemmMappedHost bm = getMappedHost(machineFqPath);
        setMappedHostUsernameAndPwd(bm, username, passwd);

        //After the split, position 3 of the array contains a string of the form DU_NAME~PU_ID (id - do not confuse with puid)
        //PuStId is the id attribute of the PU element in the st file. Do not confuse with puid
        final String[] duNameAndPuStId = puStIdFqPath.substring(1).split("/")[3].split("~");

        BemmPU pu = bm.getDeployUnit(duNameAndPuStId[0]).getPU(duNameAndPuStId[1]);

        if (localBeHome == null)
            return new RemoteEngineBuilder(bm, pu, this.logger);
        else
            return new RemoteEngineBuilder(bm, pu, this.logger,localBeHome);  //used by MM Tools
    }

    //NOTE:  This method was renamed from deployPU to deployDU because we are actually deploying a DU, not a PU
    String deployDU(String machineFqPath, String dusStIdsTokenStr, String username, String passwd) throws BEMMException {
        final BemmMappedHost bm = getMappedHost(machineFqPath);
        setMappedHostUsernameAndPwd(bm, username, passwd);

        final String[] machineFqPathSplit = machineFqPath.substring(1).split("/");
        BemmDeployment deploy = new BemmDeployment(bm, logger,
                siteNameToSite.get(machineFqPathSplit[0]).getCluster(machineFqPathSplit[1]));

        return deploy.doDeployment(dusStIdsTokenStr);
	}

    // Executed at the Machine level
    private String executeCMD(String machineFqPath, String command, String username, String passwd) throws BEMMException {
        final BemmMappedHost bm = getMappedHost(machineFqPath);
        setMappedHostUsernameAndPwd(bm, username, passwd);

		RemoteCommandBuilder rcb = new RemoteCommandBuilder(bm, command);
		return rcb.launchRemoteCommand();
	}

    public String getProvider() {
       final boolean inMemoryMode =
               Boolean.parseBoolean(System.getProperty("be.mm.monitor.in.mem"));

        if (inMemoryMode)
            return CACHE_MODE.IN_MEMORY;

        String className = System.getProperty(SystemProperty.VM_DAOPROVIDER_CLASSNAME.getPropertyName(),
                SystemProperty.VM_DAOPROVIDER_CLASSNAME.getValidValues()[0].toString());

        if(className.equals(SystemProperty.VM_DAOPROVIDER_CLASSNAME.getValidValues()[0].toString()))
        	return CACHE_MODE.TIBCO;

        if(className.equals(SystemProperty.VM_DAOPROVIDER_CLASSNAME.getValidValues()[1].toString()))
        	return CACHE_MODE.COHERENCE;

        return CACHE_MODE.IN_MEMORY;
    }

    private void setMappedHostUsernameAndPwd(BemmMappedHost machine, String username, String passwd) {
        if(username != null && !username.trim().isEmpty() && !username.trim().equals("\"\""))
			machine.getHostResource().setUsername(username);

        if(passwd != null && !passwd.trim().isEmpty() && !passwd.trim().equals("\"\""))
			machine.getHostResource().setPassword(getDecodedString(passwd));
    }

    private String handleExceptions(String operFqName, String msg, Exception e) {
        String exMsg;
        if (msg == null) msg = "";

        // if it's an authentication exception, extract the message inside the stacktrace
        if ( (exMsg = UtilFunctions.getClassExceptionMsg(e, BEMMInvalidAccessException.class)) != null ||
             (exMsg = UtilFunctions.getClassExceptionMsg(e, BEMMInvalidUserRoleException.class)) != null ||
             (exMsg = UtilFunctions.getClassExceptionMsg(e, GeneralSecurityException.class)) != null ) {

            logger.log(Level.ERROR, msg + exMsg);
        } else {
            logger.log(Level.ERROR, e, msg + e.getMessage());
        }
        return MMIoXml.errorXML(operFqName, msg + exMsg, null);
    }
    
    public String[] getTopologyChildren(String parent){
    	return getInstance().getChildren(parent);
    }

    public String startProcessUnit(String machineFqPath, String puStIdFqPath, String username,
    		String encodedPwd, String uiUserName, String encodedUiPwd ) {
    	final String operFqName = buildOperFqName(puStIdFqPath, "Start PU");
    	try {
    		//remote host username and password
    		username = URLDecoder.decode(username, "UTF-8");
    		final String decodedPwd = UtilFunctions.decodeBase64Pwd(URLDecoder.decode(encodedPwd, "UTF-8"));

    		//ui username and password
    		uiUserName = URLDecoder.decode(uiUserName, "UTF-8");
    		final String decodedUiPwd = UtilFunctions.decodeBase64Pwd(URLDecoder.decode(encodedUiPwd, "UTF-8"));

    		if (UtilFunctions.getInstance().isAuthorized(uiUserName, decodedUiPwd)) {
    			String result = getInstance().startPU(machineFqPath, puStIdFqPath, username, decodedPwd);

    			if (result.equals(TopologyLoader.SUCCESS)) {
    				getInstance().logger.log(Level.INFO, "Processing Unit '%s' SUCCESSFULLY started.",puStIdFqPath);
    				return MMIoXml.successXML(operFqName,"true");
    			} else {
    				final String msg = String.format(START_PU_FAIL_MSG + result, puStIdFqPath, machineFqPath);
    				getInstance().logger.log(Level.ERROR, msg);
    				return MMIoXml.errorXML(operFqName, msg, null);
    			}
    		} else { //should never get here, but just in case
    			throw new GeneralSecurityException(String.format(AUTHENTICATION_FAIL_MSG, uiUserName));
    		}
    	} catch (Exception e) {
    		return getInstance().handleExceptions(operFqName, String.format(START_PU_FAIL_MSG, puStIdFqPath, machineFqPath), e);
    	}
    }

    private static String buildOperFqName(String enitityFqPath, String operName) {
    	return enitityFqPath + "/" + operName;
    }

    //username, password are the credentials to login to the remote host
    //uiUserName, uiPwd are the credentials the user used to login the MM UI.
    public String deployDeploymentUnits(String machineFqPath, String dusStIdsTokenStr, String username,
    		String encodedPwd, String uiUserName, String encodedUiPwd) {
    	final String operFqName = buildOperFqName(machineFqPath, "Deploy DU");
    	try {
    		if (dusStIdsTokenStr == null || dusStIdsTokenStr.isEmpty())
    			return MMIoXml.warningXML("Deploy DU", String.format(
    					"No DU's selected for deployment. " +
    							DEPLOY_DU_FAIL_MSG, null, machineFqPath));

    		dusStIdsTokenStr = URLDecoder.decode(dusStIdsTokenStr, "UTF-8");
    		//remote host username and password
    		final String decodedPwd =
    				UtilFunctions.decodeBase64Pwd(URLDecoder.decode(encodedPwd, "UTF-8"));
    		username = URLDecoder.decode(username, "UTF-8");

    		//ui username and password
    		final String decodedUiPwd =
    				UtilFunctions.decodeBase64Pwd(URLDecoder.decode(encodedUiPwd, "UTF-8"));
    		uiUserName = URLDecoder.decode(uiUserName, "UTF-8");

    		if (UtilFunctions.getInstance().isAuthorized(uiUserName, decodedUiPwd)){
    			return getInstance().deployDU(machineFqPath, dusStIdsTokenStr, username, decodedPwd);
    		} else { //should never get here, but just in case
    			throw new GeneralSecurityException(String.format(AUTHENTICATION_FAIL_MSG, uiUserName));
    		}
    	} catch (Exception e) {
    		return getInstance().handleExceptions(operFqName, String.format(DEPLOY_DU_FAIL_MSG, dusStIdsTokenStr, machineFqPath), e);
    	}
    }

    //username, password are the credentials to login to the remote host
    //uiUserName, uiPwd are the credentials the user used to login the MM UI.
    public String executeCommand(String machineFqPath, String command, String username,
    		String encodedPwd, String uiUserName, String encodedUiPwd ){

    	final String operFqName = buildOperFqName(machineFqPath, "Execute Cmd");
    	try {
    		//remote host username and password
    		username = URLDecoder.decode(username, "UTF-8");
    		final String decodedPwd = UtilFunctions.decodeBase64Pwd(URLDecoder.decode(encodedPwd, "UTF-8"));

    		//ui username and password
    		uiUserName = URLDecoder.decode(uiUserName, "UTF-8");
    		final String decodedUiPwd = UtilFunctions.decodeBase64Pwd(URLDecoder.decode(encodedUiPwd, "UTF-8"));

    		command = URLDecoder.decode(command, "UTF-8");

    		if (UtilFunctions.getInstance().isAuthorized(uiUserName, decodedUiPwd)){
    			String result = getInstance().executeCMD(machineFqPath, command, username, decodedPwd);

    			if (result.equalsIgnoreCase(SUCCESS)) {
    				getInstance().logger.log(Level.INFO, "Command SUCCESSFULLY executed on host: ", machineFqPath);
    				return MMIoXml.successXML(operFqName,"true");
    			} else {
    				final String msg = String.format(EXEC_CMD_FAIL_MSG, machineFqPath) + result;
    				getInstance().logger.log(Level.ERROR, msg);
    				return MMIoXml.errorXML(operFqName, msg, null);
    			}

    		} else { //should never get here, but just in case
    			throw new GeneralSecurityException(String.format(TopologyLoader.AUTHENTICATION_FAIL_MSG, uiUserName));
    		}
    	} catch (Exception e) {
    		return getInstance().handleExceptions(operFqName, String.format(EXEC_CMD_FAIL_MSG, machineFqPath), e);
    	}
    }

    public String getCacheProvider() {
    	return getInstance().getProvider();
    }

    public String getTopoXMLContent(){
    	return getInstance().getXMLContent();
    }
}
