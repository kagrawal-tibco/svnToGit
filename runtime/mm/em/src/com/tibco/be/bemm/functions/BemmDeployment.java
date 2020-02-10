/**
 * Remote deployment before remote start
 */
package com.tibco.be.bemm.functions;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.tibco.be.util.config.topology.*;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @author Nick
 *
 */
public class BemmDeployment{
	private BemmMappedHost machineConfig;
    private BemmCluster cluster;
    private Logger logger;
    private String method;

    private String hostIp;
    private boolean isLocalHost;
    private String password;
    private String username;

    private String cddLocal;
    private String earLocal;

    public BemmDeployment(BemmMappedHost machineConfig, Logger logger, BemmCluster cluster){
        this.cluster = cluster;
		this.machineConfig = machineConfig;
		this.logger = logger;
		init();
	}

    private void init() {
        final BemmHostResource hr = machineConfig.getHostResource();

        hostIp = hr.getIpAddress();
        isLocalHost = isLocalhost(hr);
        password = hr.getPassword();
        username = hr.getUsername();

        //To deal with the cases when the user prepends the domain to the username
        if(username != null) {
            String[] usrWithDomain = username.split("\\\\");
            username = usrWithDomain[usrWithDomain.length - 1];
        }

        cddLocal = cluster.getMasterCddPath();
        earLocal = cluster.getMasterEarPath();

        method = determineMethod(hr);
    }

	private boolean isLocalhost(BemmHostResource hr) {
		String localhost = "";
		try {
			localhost = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			logger.log(Level.WARN, "Unknown host: "+e.getMessage());
			return false;
		}
		final String targetHost = hr.getHostFqName();

        return targetHost != null && targetHost.equalsIgnoreCase(localhost);
	}
	
	private String determineMethod(BemmHostResource hr) {
		final String targetOs = hr.getOsType();

        if(targetOs == null || targetOs.isEmpty()){
			logger.log(Level.ERROR, "OS architecture not specified for host %s (%s).",
                    hr.getHostFqName(),
                    hr.getIpAddress() );
			return null;
		}
		if(targetOs.toLowerCase().startsWith("windows"))
			return "windows";
		else
			return "sftp";
	}
	
	private String getWinSshPath(String dir){
		String path = "";
		path = dir.replaceAll("\\\\", "/");
		path = path.replaceAll(":/", "/");
		path = "/cygdrive/"+path;
		return path;
	}

    /**
     * @param dusStIdsTokenStr Tokenized string with the site topology id's
     * of the deployment units selected for deployment. The token is the char '#'
     * */
	public String doDeployment(String dusStIdsTokenStr) {
		if(method == null || method.isEmpty()){
            final String errMsg = "Deployment FAILED. Cannot determine OS architecture.";
			logger.log(Level.ERROR, errMsg );
			return errMsg;
		}

        final MMIoTableXml table = new MMIoTableXml();

        final String[] colNames = {
                MMIoNS.Table.DeployDuColNames.DU_NAME,
                MMIoNS.Table.DeployDuColNames.DEPLOY_STATUS,
                MMIoNS.Table.DeployDuColNames.REMOTE_DIR,
                MMIoNS.Table.DeployDuColNames.INFO };

        //All info goes in the variable statusTokenStr:
        //'$' is the token separating TRIPLETS DU_NAME#DEPLOYMENT_STATUS#DEPLOY_TIME
        //'#' is the token separating the items DU_NAME#DEPLOYMENT_STATUS#DEPLOY_TIME
        //The last substring after the last '$' token contains the XML with the
        //deployment status for all the DU's selected for deployment
        final StringBuilder statusTokenStr = new StringBuilder();

        final Object[] portAndExptMsg = getPortAndExptMsg();

        final JschClient jschCli =
                new JschClient(username, password, hostIp,
                               (Integer)portAndExptMsg[0], logger);

        ChannelSftp channel = null;

        final String[] duIds = dusStIdsTokenStr.split("#");

        //iterate over all the DU's selected for deployed
        for (String duId : duIds) {
            String errMsg=null;
            Exception exception=null;

            String[] colValues = {"","","",""};

            MMIoTableXml.Row row =null;

            row = table.addRow();

            BemmDeploymentUnit du = machineConfig.getDeployUnitFromId(duId);

            try{
                colValues[0] = du.getFqName();

                final String cddTarget = du.getTargetCddFqPath();
                final String earTarget = du.getTargetEarFqPath();

                if(cddTarget == null || cddTarget.isEmpty() || earTarget == null || earTarget.isEmpty()) {

                    colValues[1] = MMIoNS.OperationStatus.DeployDu.FAIL;

                    if (cddTarget == null || cddTarget.isEmpty())
                        colValues[3]+= "No deployment CDD location was specified in the site topology file";
                    else
                        colValues[3]+= "No deployment EAR location was specified in the site topology file";

                    continue;
                }

                if(isLocalHost) {
                    if (cddLocal.equalsIgnoreCase(cddTarget) &&
                            earLocal.equalsIgnoreCase(earTarget) ) {
                        colValues[1] = MMIoNS.OperationStatus.DeployDu.SUCCESS;
                        colValues[2] = du.getTargetCddDir();
                        continue;
                    }
                }

                //There is some msg so add it
                if (portAndExptMsg[1] != null) {
                    logger.log(Level.WARN, (String)portAndExptMsg[1]);
                    colValues[3]+= portAndExptMsg[1];
                }

                if (channel == null)
                    channel = jschCli.establishSftpChannel();


                final String cddFileName = du.getCddFileName();
                final String cddRemoteDir = method.equals("windows") ?
                                            getWinSshPath(du.getTargetCddDir()) :
                                            du.getTargetCddDir();

                channel.cd(cddRemoteDir);

                //When the code gets here, if nothing went wrong, the Global variables
                //were already appended to the CDD file and saved following a request sent by the UI
                String cddContentWithGVs = du.getCddWithSavedGVs();

                if(cddContentWithGVs != null) {
                    channel.put(new ByteArrayInputStream(cddContentWithGVs.getBytes()), cddFileName);
                    logger.log(Level.INFO, "Deploying CDD file with new GVs values appended for DU: %s", du.getFqName());
                    colValues[3]+= "Global Variables Saved";
                } else {
                    channel.put(new FileInputStream(cddLocal), cddFileName);
                    logger.log(Level.DEBUG, "No GV values changes detected. Deploying master CDD file for DU: %s", du.getFqName());
                }

                //transfer ear file
                final String earRemoteDir = method.equals("windows") ?
                                            getWinSshPath(du.getTargetEarDir()) :
                                            du.getTargetEarDir();

                channel.cd(earRemoteDir);

                channel.put(new FileInputStream(earLocal), du.getEarFileName());

                colValues[1] = MMIoNS.OperationStatus.DeployDu.SUCCESS;
                colValues[2] = du.getTargetCddDir();
            }
            catch(JSchException e) {
                errMsg = e.getMessage();
                if(errMsg != null && errMsg.toLowerCase().contains("auth fail")){
                    errMsg = "Authentication Failed.";
                }
                errMsg = "Cannot connect to remote host at "+hostIp+" through SSH. " + errMsg;
                exception = e;
            }
            catch(SftpException e) {
                errMsg = "Exception occurred when performing SFTP operation: "+e.getMessage();
                exception = e;
            }
            catch(FileNotFoundException e){
                errMsg = "Cannot find file to deploy: "+e.getMessage();
                exception = e;
            }
            catch(Exception e){
                errMsg = "Deployment Exception: " + e.getMessage();
                exception = e;
            }
            finally {
                if (errMsg != null) { //only happens if there was an exception
                    logger.log(Level.ERROR, errMsg);
                    colValues[1] = MMIoNS.OperationStatus.DeployDu.FAIL;
                    colValues[3]+=errMsg;
                }

                String depTime="";
                if (colValues[1].equals(MMIoNS.OperationStatus.DeployDu.SUCCESS)) {
                    depTime = MMDeployTime.saveDeployTime(cluster.getName(), machineConfig.getHostName(), du.getName());
                }
                
                row.addColumns(colNames, colValues);

                statusTokenStr.append(du.getName()).append("#").
                               append(colValues[1]).append("#").
                               append(depTime).append("$");

                if (exception != null) {
                    logger.log(Level.DEBUG, getStackTrace(exception));
                    jschCli.disconnectJschSession();
                    channel = null;
                }
            }
        }   //for loop iterating over every DU selected for deployment
        statusTokenStr.append(table.serialize("Deploy PU"));

        try {
            jschCli.disconnectJschSession();
        } catch (Exception e) {
            logger.log(Level.WARN, "Error occurred when closing JSCH session.");
        }

        return statusTokenStr.toString();
	}


    private Object[] getPortAndExptMsg() {
        int port=-1;
        String msg=null;
        try {
            String portStr = ((BemmSshConfig)machineConfig.
                    getHostResource().getStartPuMethod()).getPort();

            if(portStr == null || portStr.isEmpty()) {
                port = 22;
                msg = "No SSH port specified. Using default port: 22. ";
            }
            else {
                port = Integer.parseInt(portStr);
            }
        } catch (Exception ex) {
            msg = "Illegal or no SSH port specified: " + port +
                    ". Attempting connection with default port: 22. ";

            port =22;
        }

        return new Object[]{port,msg};
    }


    private String getStackTrace(Throwable aThrowable) {
		Writer result = new StringWriter();
		PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}
}
