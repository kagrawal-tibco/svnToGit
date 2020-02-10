/**
 *
 */
package com.tibco.be.bemm.functions;

import com.jcraft.jsch.*;
import com.tibco.be.util.config.topology.BemmMappedHost;
import com.tibco.be.util.config.topology.BemmPU;
import com.tibco.be.util.config.topology.BemmSshConfig;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionManager;

import java.io.*;
import java.util.LinkedList;


/**
 * @author Nick Xu
 *
 */
public class RemoteEngineBuilder {
    public final static String MM_TOOLS_PRIVATE_KEY_FILE = "be.mm.ssh.privatekey.file";
    public final static String MM_TOOLS_PRIVATE_KEY_PASS_PHRASE = "be.mm.ssh.privatekey.passphrase";

    private final static String PROP_ENGINE_EXEC_NAME = "be.mm.remote.engine.exec.name";

    private final static String DEFAULT_ENGINE_EXEC_NAME = "be-engine";

	private BemmMappedHost machineConfig;
	private BemmPU puConfig;
	private Logger logger;
	private String local_beHome;
	private String startMethod;
	private RuleServiceProvider currRuleServiceProvider;
    private String targetCdd;
    private String targetEar;

    private String engineExecName;

	public RemoteEngineBuilder(BemmMappedHost machineConfig, BemmPU puConfig, Logger logger){
        this(machineConfig, puConfig, logger, null);
		currRuleServiceProvider = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
		this.local_beHome = currRuleServiceProvider.getProperties().getProperty("tibco.env.BE_HOME");
	}

	public RemoteEngineBuilder(BemmMappedHost machineConfig, BemmPU puConfig, Logger logger, String local_beHome){	//for MM tools
        if (local_beHome!=null)
            this.local_beHome = local_beHome;

		this.machineConfig = machineConfig;
		this.puConfig = puConfig;
		this.logger = logger;
		this.startMethod = this.determineMethod();

        this.targetCdd = machineConfig.getDeployUnit(puConfig.getDuNameQualifiedId().split("~")[0]).getTargetCddFqPath();
        this.targetEar = machineConfig.getDeployUnit(puConfig.getDuNameQualifiedId().split("~")[0]).getTargetEarFqPath();

        //To allow the name of the executable to be something other than be-engine
        engineExecName = System.getProperty(PROP_ENGINE_EXEC_NAME, DEFAULT_ENGINE_EXEC_NAME);
	}

	private String determineMethod(){
		final String startPuMethod = machineConfig.getHostResource().getStartPuMethod().getName();
		if(startPuMethod != null && startPuMethod.equals("hawk")) return startPuMethod;

		String targetOs = machineConfig.getHostResource().getOsType();
		if(targetOs == null || targetOs.isEmpty()){
			logger.log(Level.WARN, "Target OS not specified!");
			return startPuMethod;
		}

		final String localOs = System.getProperty("os.name");
		if(localOs.toLowerCase().startsWith("windows")){
			if(targetOs.toLowerCase().startsWith("windows")){
				if(startPuMethod != null && startPuMethod.equals("pstools"))
					return startPuMethod;
				else
					return "winssh";
			}
			else
				return "ssh";
		}
		else{
			if(targetOs.toLowerCase().startsWith("windows"))
				return "winssh";
			else
				return "ssh";
		}
	}

	private String getBeBinPath(){	//remote be bin path
		String binPath = machineConfig.getBeHome().getHome();
		if(binPath == null || binPath.isEmpty()){
			logger.log(Level.ERROR, "BE Home NOT specified!");
			return null;
		}
		if(startMethod != null && startMethod.equals("pstools")){	//target machine is windows.
			binPath = binPath.replace('/', File.separatorChar);
			if (!binPath.endsWith(File.separator)){
				binPath = binPath + File.separator;
			}
			binPath = binPath + "bin" + File.separator;
		}
		else{
			if (!binPath.endsWith("/")){
				binPath = binPath + "/";
			}
			binPath = binPath + "bin" + "/";
		}
        return binPath;
	}

	private String getPstoolsPath(){	//local psexec bin path
		String binPath = this.local_beHome.replace('/', File.separatorChar);

		if(binPath == null || binPath == ""){
			logger.log(Level.ERROR, "BE Home is not specified!");
			return null;
		}
        if (binPath.endsWith(File.separator) == false){
        	binPath = binPath + File.separator;
        }
        binPath = binPath + "mm" + File.separator + "bin" + File.separator +"pstools" + File.separator;
        return binPath;
	}

	private LinkedList<String> formPsExecCommand() throws Exception{
		LinkedList<String> commandList = new LinkedList<String>();
		String binPath = getBeBinPath();
		if(binPath == null || binPath == "") return null;
		String hostname = machineConfig.getHostResource().getHostFqName();
		String user = machineConfig.getHostResource().getUsername();
		String passwd = machineConfig.getHostResource().getPassword();
		if(hostname == null || hostname == "")
			throw new Exception("Hostname for target machine is not specified.");
		if(user == null || user == "")
			throw new Exception("Username for target machine is not specified.");
		if(passwd == null || passwd == "")
			throw new Exception("Password for target machine is not specified.");
		//local command for psexec
		commandList.add(getPstoolsPath()+"psexec");
		commandList.add("\\\\"+hostname);
		commandList.add("-d");
		commandList.add("-u");
		commandList.add(user);
		commandList.add("-p");
		commandList.add(passwd);
		commandList.add("-w");
		commandList.add(binPath);

		//form the remote command to start be engine
		commandList.add(binPath+engineExecName);
		commandList.add("--propFile");
		commandList.add(machineConfig.getBeHome().getTra());
		if( puConfig.getUseAsEngineName() ) {
			commandList.add("-n");
			commandList.add(puConfig.getId());
		}
		commandList.add("-c");
		commandList.add(targetCdd);
		commandList.add("-u");
		commandList.add(puConfig.getPuid());
		commandList.add(targetEar);
		if(puConfig.getJmxPort()!=null){
			commandList.add("--propVar");
			commandList.add("jmx_port="+puConfig.getJmxPort());
		}

		return commandList;
	}
	private String convertCygPath(String path){
		String p = "";
		p = path.replaceAll("\\\\", "/");
		p = p.replaceAll(":/", "/");
		p = "/cygdrive/"+p;
		return p;
	}
	private String formWinSshCommand(){
		String binPath = machineConfig.getBeHome().getHome();
		if(binPath == null || binPath.isEmpty()){
			logger.log(Level.ERROR, "BE Home NOT specified!");
			return null;
		}

		binPath = convertCygPath(binPath);
		if (binPath.endsWith("/") == false){
			binPath = binPath + "/";
		}
		binPath = binPath + "bin" + "/";
		StringBuffer command = new StringBuffer();
		command.append(binPath);
		command.append(engineExecName);
		command.append(" ");
		command.append("--propFile");
		command.append(" ");
		command.append(machineConfig.getBeHome().getTra().replaceAll("\\\\", "/"));
		if( puConfig.getUseAsEngineName() ){
			command.append(" ");
			command.append("-n");
			command.append(" ");
			command.append(puConfig.getId());
		}
		command.append(" ");
		command.append("-c");
		command.append(" ");
		command.append(targetCdd.replaceAll("\\\\", "/"));
		command.append(" ");
		command.append("-u");
		command.append(" ");
		command.append(puConfig.getPuid());
		command.append(" ");
		command.append(targetEar.replaceAll("\\\\", "/"));
		if(puConfig.getJmxPort()!=null){
			command.append(" ");
			command.append("--propVar");
			command.append(" ");
			command.append("jmx_port="+puConfig.getJmxPort());
		}
		return command.toString();
	}

	private String formUnixCommand(){
		String bepath = getBeBinPath();
		if(bepath == null || bepath =="") return null;
		StringBuffer command = new StringBuffer();
		command.append("nohup");
		command.append(" ");
		command.append(bepath);
		command.append(engineExecName);
		command.append(" ");
		command.append("--propFile");
		command.append(" ");
		command.append(machineConfig.getBeHome().getTra());
		if( puConfig.getUseAsEngineName() ){
			command.append(" ");
			command.append("-n");
			command.append(" ");
			command.append(puConfig.getId());
		}
		command.append(" ");
		command.append("-c");
		command.append(" ");
		command.append(targetCdd);
		command.append(" ");
		command.append("-u");
		command.append(" ");
		command.append(puConfig.getPuid());
		command.append(" ");
		command.append(targetEar);
		if(puConfig.getJmxPort()!=null){
			command.append(" ");
			command.append("--propVar");
			command.append(" ");
			command.append("jmx_port="+puConfig.getJmxPort());
		}
		command.append(" &");
		return command.toString();
	}

	private String formHawkCommand(){
		String targetOs = machineConfig.getHostResource().getOsType();
		if(targetOs == null || targetOs.isEmpty()) {
			logger.log(Level.ERROR, "Target OS NOT specified!");
			return null;
		}

		String bepath = getBeBinPath();
		if(bepath == null || bepath.isEmpty()) return null;

		if(targetOs.toLowerCase().startsWith("windows")){
			StringBuilder command = new StringBuilder();
			command.append("cmd /c");
			command.append(" ");
			command.append(bepath);
			command.append(engineExecName);
			command.append(" ");
			command.append("--propFile");
			command.append(" ");
			command.append(machineConfig.getBeHome().getTra());
			if( puConfig.getUseAsEngineName() ){
				command.append(" ");
				command.append("-n");
				command.append(" ");
				command.append(puConfig.getId());
			}
			command.append(" ");
			command.append("-c");
			command.append(" ");
			command.append(targetCdd);
			command.append(" ");
			command.append("-u");
			command.append(" ");
			command.append(puConfig.getPuid());
			command.append(" ");
			command.append(targetEar);
			if(puConfig.getJmxPort()!=null){
				command.append(" ");
				command.append("--propVar");
				command.append(" ");
				command.append("jmx_port="+puConfig.getJmxPort());
			}
			return command.toString();
		}
		else{
			String cmd = formUnixCommand();
			if(cmd != null){
				return cmd.split("&")[0];
			}
			return cmd;
		}
	}

	private String checkFileSSH(){
		StringBuffer command = new StringBuffer();
		command.append("ls ");
		if(startMethod.equals("winssh"))
			command.append(convertCygPath(targetCdd));
		else
			command.append(targetCdd);
		command.append(" ");
		if(startMethod.equals("winssh"))
			command.append(convertCygPath(targetEar));
		else
			command.append(targetEar);
		return command.toString();
	}

	private LinkedList<String> checkFilePsExec(){
		LinkedList<String> commandList = new LinkedList<String>();

		//local command for psexec
		commandList.add(getPstoolsPath()+"psexec");
		commandList.add("\\\\"+machineConfig.getHostResource().getHostFqName());
		commandList.add("-u");
		commandList.add(machineConfig.getHostResource().getUsername());
		commandList.add("-p");
		commandList.add(machineConfig.getHostResource().getPassword());

		//form the remote command to start be engine
		commandList.add("cmd");
		commandList.add("/c");
		commandList.add("dir");
		commandList.add("/b");
		commandList.add(targetCdd);
		commandList.add(targetEar);

		return commandList;
	}

	private String getFileName(String path){
		if(path == null) return null;
		int idx = path.lastIndexOf('/');
		int idx1 = path.lastIndexOf('\\');
		if(idx == -1 && idx1 == -1) return null;
		if(idx != -1){
			return path.substring(idx+1).toLowerCase();
		}
		else{
			return path.substring(idx1+1).toLowerCase();
		}
	}
	private LinkedList<String> checkPIDCommand(String pid){
		LinkedList<String> commandList = new LinkedList<String>();

		//local command for pslist
		commandList.add(getPstoolsPath()+"pslist");
		commandList.add("\\\\"+machineConfig.getHostResource().getHostFqName());
		commandList.add("-u");
		commandList.add(machineConfig.getHostResource().getUsername());
		commandList.add("-p");
		commandList.add(machineConfig.getHostResource().getPassword());
		commandList.add(pid);
		return commandList;
	}
	private boolean checkRemotePID(int pid) throws Exception{
		//Thread.sleep(2000);
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.redirectErrorStream(true);

		processBuilder.command(checkPIDCommand(""+pid));
		Process localProcess = processBuilder.start();
		InputStream is = localProcess.getInputStream();
		OutputStream os = localProcess.getOutputStream();
		try{
			String op = getOutput(localProcess.getInputStream());
			int result = -1;
			try{
				result = localProcess.exitValue();
			}
			catch(Exception pe){
				//ignore
			}

			if(op.toLowerCase().indexOf("was not found")>0){
				throw new Exception("Engine PID "+pid+" is not found! ");
			}
			if(!op.contains(""+pid))
				throw new Exception(op);
			logger.log(Level.DEBUG, "Remote engine PID info: \n"+op);
		}
		catch(Exception ee){
			throw ee;
		}
		finally{
			os.close();
			is.close();
		}
		return true;
	}

	public String remoteStartHawk(String domain, String service, String network, String daemon){
		try {
			String commandstr = formHawkCommand();
			if(commandstr == null || commandstr == ""){
				throw new Exception("Failed to form remote command for engine startup.");
			}
			logger.log(Level.INFO, "The remote Hawk command is: "+commandstr);
			RemoteEngineHawk reh = new RemoteEngineHawk(domain, service, network, daemon);
			reh.setCommand(commandstr);
			reh.start();
			return "success";
		}
		catch(Exception hawkException){
			logger.log(Level.ERROR, "Remote Start encountered Hawk Exception: "+hawkException.getMessage());
			logger.log(Level.DEBUG, getStackTrace(hawkException));
			return "Hawk Exception: "+hawkException.getMessage();
		}
	}

	public String launchRemoteCall(){
        if(targetCdd == null || targetCdd.trim().isEmpty())
        	return "Remote CDD target not specified in topology.xml";
        if(targetCdd == null || targetCdd.trim().isEmpty())
        	return "Remote EAR target not specified in topology.xml";

		try {
			if(startMethod != null && startMethod.equals("hawk")){
				String hawkDomain = currRuleServiceProvider.getProject().getGlobalVariables().getVariableAsString("Domain", "");
				String service = currRuleServiceProvider.getProject().getGlobalVariables().getVariableAsString("TIBHawkService", "7474");
				String network = currRuleServiceProvider.getProject().getGlobalVariables().getVariableAsString("TIBHawkNetwork", "");
				String daemon = currRuleServiceProvider.getProject().getGlobalVariables().getVariableAsString("TIBHawkDaemon", "tcp:7474");
				String commandstr = formHawkCommand();
				//System.out.println(commandstr);
				if(commandstr == null || commandstr.isEmpty()){
					throw new Exception("Failed to form remote command for engine startup.");
				}

				logger.log(Level.INFO, "The remote Hawk command is: "+commandstr);
				RemoteEngineHawk reh = new RemoteEngineHawk(hawkDomain, service, network, daemon);
				reh.setCommand(commandstr);
				reh.start();
				return "success";
			}
		}
		catch(Exception hawkException){
			logger.log(Level.ERROR, "Remote Start encountered Hawk Exception: "+hawkException.getMessage());
			logger.log(Level.DEBUG, getStackTrace(hawkException));
			return "Hawk Exception: "+hawkException.getMessage();
		}

		try{
			if (startMethod == null){
				throw new Exception("Remote start engine exception: cannot determine OS pattern.");
			}
			else if(startMethod.equals("pstools")){
				LinkedList<String> commandList = formPsExecCommand();
				if(commandList == null){
					throw new Exception("Failed to form remote command for engine startup.");
				}
				StringBuffer sb = new StringBuffer();
				try {
					boolean pwdFound = false;
					for(int i=0;i<commandList.size();i++){
						String pwd = commandList.get(i);
						if(pwd != null && pwd.equals("-p") && !pwdFound){
							sb.append(pwd);
							sb.append(" *****");
							i++;
							pwdFound = true;
						}
						else
							sb.append(pwd);
						sb.append(" ");
					}
				} catch (Exception e) {}
				logger.log(Level.INFO, "The PsExec command is: "+sb.toString());
				ProcessBuilder processBuilder = new ProcessBuilder();
				processBuilder.redirectErrorStream(true);

				//System.out.println(commandList.toString());
				processBuilder.command(commandList);
				Process localProcess = processBuilder.start();
				InputStream is = localProcess.getInputStream();
				OutputStream os = localProcess.getOutputStream();
				try{
					String op = getOutput(localProcess.getInputStream());
					if(op != null && op.contains("www.sysinternals.com")){
						String[] s = op.split("www.sysinternals.com");
						if(s.length > 1)
							op = s[1];
					}
					int result = -1;
					try{
						result = localProcess.exitValue();
					}
					catch(Exception pe){
						//ignore
					}
					String success = "process id "+result;
                    String errMessage;
					if(!op.toLowerCase().contains(success)){
                        errMessage = "Login failure: unknown userName or incorrect password.";
                        if(!op.contains(errMessage)) {
                            errMessage = "Remote Start using PsExec failed with output: \n"+op;
                        }
						throw new Exception(errMessage);
					}
					checkRemotePID(result);
					logger.log(Level.DEBUG, "Remote Start using PsExec succeeded with output: \n"+op);
				}
				catch(Exception ee){
					throw ee;
				}
				finally{
					os.close();
					is.close();
				}
			}
			else{	//ssh or winssh
				String sshCommand = "";
				if(startMethod.equals("ssh"))
					sshCommand = formUnixCommand();
				else
					sshCommand = formWinSshCommand();
				if(sshCommand == null || sshCommand == ""){
					throw new Exception("Failed to form remote command for engine startup.");
				}
			    String hostIp = machineConfig.getHostResource().getIpAddress();
			    String userName = machineConfig.getHostResource().getUsername();
			    //To deal with domain user
			    if(userName != null){
			    	String[] usrWithDomain = userName.split("\\\\");
			    	userName = usrWithDomain[usrWithDomain.length - 1];
			    }
			    String password = machineConfig.getHostResource().getPassword();

			    int port;
			    try {
					String portStr = ((BemmSshConfig)machineConfig.getHostResource().getStartPuMethod()).getPort();
					if(portStr == null || portStr == "") port = 22;
					else{
						port = Integer.parseInt(portStr);
					}
				} catch (Exception e) {
					port = 22;
				}

                Session session = null;
                Channel channel = null;
                try{
                JSch jsch = new JSch();

                String privateKeyFileLoc = System.getProperty(MM_TOOLS_PRIVATE_KEY_FILE);
                final String passPhrase = System.getProperty(MM_TOOLS_PRIVATE_KEY_PASS_PHRASE);

                //Do authentication using private/public keys.
                if (privateKeyFileLoc != null) {
                    try {
                        logger.log(Level.INFO, "Attempting authentication to %s@%s:%s using PRIVATE KEY '%s'",
                                                userName,hostIp,port,privateKeyFileLoc);

                        if (passPhrase == null)
                            jsch.addIdentity(privateKeyFileLoc);
                        else
                            jsch.addIdentity(privateKeyFileLoc,passPhrase);

                        session = jsch.getSession(userName, hostIp, port);
                        session.setConfig("StrictHostKeyChecking", "no");
                        session.connect();
                    } catch (JSchException e) {
                        final String emsg = "Check if you provided the correct passphrase using the -pph CL option. ";

                        logger.log(Level.WARN, "Authentication to %s@%s:%s using PRIVATE KEY '%s' FAILED. %s%s ",
                               userName, hostIp, port, privateKeyFileLoc, emsg, e.getMessage());

                        jsch.removeAllIdentity();
                        privateKeyFileLoc = null;
                    }
                }


                    //if private key authentication not set or failed proceed with password authentication
                    if (privateKeyFileLoc == null) {
                        logger.log(Level.INFO, "Attempting PASSWORD authentication to %s@%s:%s",userName,hostIp,port);
                        session = jsch.getSession(userName, hostIp, port);
                        session.setConfig("StrictHostKeyChecking", "no");
                        session.setPassword(password);
                        session.connect();
                    }

                logger.log(Level.INFO, "Authentication SUCCESSFUL and session connected");

                //add file check before start remote engine
                channel=session.openChannel("exec");
                String lsCommand = checkFileSSH();
                ((ChannelExec)channel).setCommand(lsCommand);
                channel.setInputStream(null);
                InputStream in=channel.getInputStream();
                InputStream es=((ChannelExec)channel).getErrStream();
                channel.connect();
                logger.log(Level.DEBUG, "Channel connected");


			    byte[] tmp=new byte[1024];
			    String tempOutput = "";
			    for(int count=0;count<5;count++){
			    	try{Thread.sleep(1000);}
			        catch(Exception ee){}
			        if(es.available()>0){
			        	int i=es.read(tmp,0,1024);
			        	logger.log(Level.ERROR, "The ls command for checking deployment file is: "+lsCommand);
			        	logger.log(Level.ERROR, "The error result for checking deployment file is: "+new String(tmp, 0, i));
			        	//break;
			        }
			        if(in.available()>0){
			            int i=in.read(tmp, 0, 1024);
			            if(i>0){
			            	tempOutput = tempOutput + new String(tmp, 0, i);
			            }
			          }
			          if(channel.isClosed()){
			            break;
			          }
			    }

				logger.log(Level.DEBUG, "The output from remote file check: "+tempOutput);

//			    String[] failedMsg = {"no such file","file not found"};
//			    for(int idx=0;idx<failedMsg.length;idx++){
//			    	if(tempOutput.toLowerCase().indexOf(failedMsg[idx])>0)
//			    		throw new FileNotFoundException("Deployment is not done or incomplete");
//			    }
				String targetCdd = "";
				String targetEar = "";
				if(startMethod.equals("ssh")){
					targetCdd = this.targetCdd;
					targetEar = this.targetEar;
				}
				else {
					targetCdd = convertCygPath(this.targetCdd);
					targetEar = convertCygPath(this.targetEar);
				}

			    if(tempOutput.toLowerCase().indexOf(targetCdd.toLowerCase())<0
				    	|| tempOutput.toLowerCase().indexOf(targetEar.toLowerCase())<0){
			    	if(tempOutput!="")
			    	logger.log(Level.ERROR, "The result for checking deployment is: "+tempOutput);
				    throw new FileNotFoundException("Cannot find file: "+targetCdd+" or "+targetEar);
			    }

			    channel.disconnect();

			    channel=session.openChannel("exec");
			    logger.log(Level.INFO, "The remote ssh command is: "+sshCommand);
			    ((ChannelExec)channel).setCommand(sshCommand);
			    System.out.println(sshCommand);
			    channel.setInputStream(null);
			    in=channel.getInputStream();
			    InputStream err=((ChannelExec)channel).getErrStream();
			    channel.connect();
			    for(int count=0;count<5;count++){
			    	if(in.available()>0 || err.available()>0)break;
			    	Thread.sleep(500);
			    }
			    byte[] tmp1=new byte[1024];
			    boolean ifErr = false;
			    while(in.available()>0 || err.available()>0){
			    	int i=-1;
			    	if(in.available()>0)
			    		i=in.read(tmp1, 0, 1024);
			    	else if(err.available()>0){
			    		i=err.read(tmp1, 0, 512);
			    		ifErr = true;
			    	}
			        if(i<0)break;
			        String temp = new String(tmp1, 0, i);
			        logger.log(Level.DEBUG, "The output from remote engine: "+temp);
			        if(ifErr){
			        	throw new Exception(temp);
			        }
			        String errorMsg = "failed to open properties file";
			        if(temp != null && temp.toLowerCase().contains(errorMsg)){
			        	throw new Exception(temp);
			        }
			        errorMsg = "no such file or directory";
			        if(temp != null && temp.toLowerCase().contains(errorMsg)){
			        	throw new Exception(temp);
			        }
			        break;
			      }
				}
				finally{
					if(channel!=null)channel.disconnect();
				    if(session!=null)session.disconnect();
				}

			}
		}
		catch(JSchException jschExp){
			String errorMsg = jschExp.getMessage();
			if("Auth Fail".equalsIgnoreCase(errorMsg))
				errorMsg = "Authentication Failed.";
			logger.log(Level.ERROR, "Remote Start Exception: Cannot connect to remote machine: "+machineConfig.getHostResource().getIpAddress()
					+" through SSH. "+errorMsg);
			logger.log(Level.DEBUG, getStackTrace(jschExp));
			return "Ssh exception when conecting to "+machineConfig.getHostResource().getIpAddress()+": "+errorMsg;
		}
		catch(FileNotFoundException fe){
			logger.log(Level.ERROR, "Remote Start Exception: CDD or EAR File Not Found. Make sure deployment is done. "+fe.getMessage());
			logger.log(Level.DEBUG, getStackTrace(fe));
			return fe.getMessage();
		}
		catch (Exception e) {
			logger.log(Level.ERROR,"Remote Start Exception encountered: " + e.getMessage());
			logger.log(Level.ERROR,"Remote Start failed or timed out.");
			logger.log(Level.DEBUG, getStackTrace(e));
			return "Remote Start Exception encountered: "+e.getMessage();
		}
		return "success";
	}
	public String getStackTrace(Throwable aThrowable) {
		Writer result = new StringWriter();
		PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}
	private String getOutput(InputStream inputStream) throws IOException {
		StringBuffer sb = new StringBuffer();
		char c = (char) inputStream.read();
		while ((c != -1) && (c != 65535)) {
			sb.append(c);
			c = (char) inputStream.read();
		}
		return sb.toString();
	}

    public String getStartMethod() {
        return startMethod;
    }



}
