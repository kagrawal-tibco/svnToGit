/**
 * 
 */
package com.tibco.be.bemm.functions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedList;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.tibco.be.util.config.topology.BemmMappedHost;
import com.tibco.be.util.config.topology.BemmSshConfig;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionManager;


/**
 * @author Nick Xu
 *
 */
public class RemoteCommandBuilder {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

	private BemmMappedHost machineConfig;
	private String command;
	private Logger logger;
	private String local_beHome;
	private RuleServiceProvider currRuleServiceProvider;
	private String method;

	public RemoteCommandBuilder(BemmMappedHost machineConfig, String command){
		currRuleServiceProvider = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
		this.local_beHome = currRuleServiceProvider.getProperties().getProperty("tibco.env.BE_HOME");
		this.machineConfig = machineConfig;
		this.command = command;
		this.logger = currRuleServiceProvider.getLogger(this.getClass());
		method = determineMethod();
	}

	private String determineMethod(){
		String temp = machineConfig.getHostResource().getStartPuMethod().getName();
		if(temp != null && temp.equals("hawk")) return temp;
		
		String targetOs = machineConfig.getHostResource().getOsType();
		if(targetOs == null || targetOs == ""){
			logger.log(Level.ERROR, "Target OS is not specified!");
			return temp;
		}
		String localOs = System.getProperty("os.name");
		if(localOs.toLowerCase().startsWith("windows")){
			if(targetOs.toLowerCase().startsWith("windows"))
				return "pstools";
			else
				return "ssh";
		}
		else{
			if(targetOs.toLowerCase().startsWith("windows"))
				return null;
			else
				return "ssh";
		}

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
		if(this.command == null || this.command == "" || this.command.equals("\"\""))
			return null;
		LinkedList<String> commandList = new LinkedList<String>();
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
		
		String[] commands = command.split(" ");
		for(int i=0;i<commands.length;i++){
			commandList.add(commands[i]);
		}

		return commandList;
	}
	
	private String formHawkCommand(){
		if(this.command == null || this.command == "" || this.command.equals("\"\""))
			return null;
		String targetOs = machineConfig.getHostResource().getOsType();
		if(targetOs == null || targetOs == "") return null;

		if(targetOs.toLowerCase().startsWith("windows")){
			String tempCommand = "cmd /c " + this.command;
			return tempCommand;
		}
		else{
			return this.command;
		}
	}

	public String launchRemoteCommand(){
		try {
			if(method != null && method.equals("hawk")){
				String hawkDomain = currRuleServiceProvider.getProject().getGlobalVariables().getVariableAsString("Domain", "");//machineConfig.getReferedMachine().getHawkConfig().getDomain();
				String service = currRuleServiceProvider.getProject().getGlobalVariables().getVariableAsString("TIBHawkService", "7474");//machineConfig.getReferedMachine().getHawkConfig().getService();
				String network = currRuleServiceProvider.getProject().getGlobalVariables().getVariableAsString("TIBHawkNetwork", "");//machineConfig.getReferedMachine().getHawkConfig().getNetwork();
				String daemon = currRuleServiceProvider.getProject().getGlobalVariables().getVariableAsString("TIBHawkDaemon", "tcp:7474");//machineConfig.getReferedMachine().getHawkConfig().getDaemon();
				String commandstr = formHawkCommand();
				//System.out.println(commandstr);
				if(commandstr == null || commandstr == ""){
					logger.log(Level.ERROR, "Remote Command is empty!");
					throw new Exception();				
				}
				logger.log(Level.INFO, "The remote Hawk command is: "+commandstr);
				RemoteEngineHawk reh = new RemoteEngineHawk(hawkDomain, service, network, daemon);
				reh.setCommand(commandstr);
				reh.start();			
				return "success";
			}
		}
		catch(Exception hawkException){
			logger.log(Level.ERROR, "Remote command encountered Hawk Exception: "+hawkException.getMessage());
			logger.log(Level.DEBUG, getStackTrace(hawkException));	
			return "Remote command encountered Hawk Exception: "+hawkException.getMessage();
		}
		
		try{
			if (method == null){
				throw new Exception("Remote command on Windows from non-windows platform is not supported.");
			}
			else if(method.equals("pstools")){
				LinkedList<String> commandList = formPsExecCommand();
				if(commandList == null){
					throw new Exception("Remote Command is empty!");
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
					if(op.toLowerCase().indexOf(success)<0){
						logger.log(Level.ERROR, "Remote command using PsExec failed with output: \n"+op);
						throw new Exception("Remote command using PsExec failed. "+op);
					}
					logger.log(Level.DEBUG, "Remote command using PsExec succeeds with output: \n"+op);
				}
				catch(Exception ee){
					throw ee;
				}
				finally{
					os.close();
					is.close();
				}
			}
			else{	//ssh
				String sshCommand = this.command;
				if(sshCommand == null || sshCommand == ""){
					throw new Exception("Remote command is empty");				
				}
				logger.log(Level.INFO, "The remote ssh command is: "+sshCommand);
			    String host = machineConfig.getHostResource().getIpAddress();
			    String userName = machineConfig.getHostResource().getUsername();
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

			    JSch jsch = new JSch(); 
			    Session session = null; 
			    Channel channel = null; 
			    try{
			    session = jsch.getSession(userName, host, port); 
			    session.setPassword(password); 

			    session.setConfig("StrictHostKeyChecking", "no"); 
			    session.connect(); 
			    
			    channel=session.openChannel("exec");
			    ((ChannelExec)channel).setCommand(sshCommand);
			    channel.setInputStream(null);
			    InputStream err=((ChannelExec)channel).getErrStream();
			    InputStream in=channel.getInputStream();
			    channel.connect();
			    for(int count=0;count<5;count++){
			    	if(in.available()>0 || err.available()>0)break;
			    	Thread.sleep(500);
			    }
			    byte[] tmp=new byte[512];
			    while(in.available()>0 || err.available()>0){
			    	int i=-1;
			    	if(in.available()>0)
			    		i=in.read(tmp, 0, 512);
			    	else if(err.available()>0)
			    		i=err.read(tmp, 0, 512);
			        if(i<0)break;
			        String temp = new String(tmp, 0, i);
			        logger.log(Level.INFO, "The output from remote command: "+temp);
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
			logger.log(Level.ERROR, "Remote Command Exception: Cannot connect to remote machine through SSH. "+jschExp.getMessage());
			logger.log(Level.DEBUG, getStackTrace(jschExp));
			return "Cannot connect to remote machine through SSH. "+jschExp.getMessage();
		}
		catch (Exception e) {
			logger.log(Level.ERROR,"Remote Command Exception encountered: " + e.getMessage());
			logger.log(Level.ERROR,"Remote Command failed or timed out.");
			logger.log(Level.DEBUG, getStackTrace(e));
			return "Remote Command Exception encountered: " + e.getMessage();
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
	
	
	public static void main(String[] args) {
		RemoteCommandBuilder pcb = new RemoteCommandBuilder(null,null);
		try {
			pcb.launchRemoteCommand();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}
	
}
