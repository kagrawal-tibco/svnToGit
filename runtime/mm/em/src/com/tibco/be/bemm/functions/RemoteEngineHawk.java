/**
 * 
 */
package com.tibco.be.bemm.functions;

import COM.TIBCO.hawk.console.hawkeye.AgentManager;
import COM.TIBCO.hawk.console.hawkeye.TIBHawkConsole;
import COM.TIBCO.hawk.talon.DataElement;
import COM.TIBCO.hawk.talon.MethodInvocation;
import COM.TIBCO.hawk.talon.MicroAgentException;
import COM.TIBCO.hawk.talon.MicroAgentID;
/**
 * @author Nick
 *
 */
public class RemoteEngineHawk {

	private TIBHawkConsole hawkConsole;
	private AgentManager agentManager;
	private String hawkDomain;
	private String service;
	private String network;
	private String daemon;
	private DataElement[] command;
	
	public RemoteEngineHawk(String hawkDomain, String service, String network, String daemon){
		this.hawkDomain = hawkDomain;
		this.service = service;
		this.network = network;
		this.daemon = daemon;
		this.command = new DataElement[1];
	}
	
	protected void start() throws Exception{
		try {
			hawkConsole = new TIBHawkConsole(hawkDomain, service, network, daemon);
			agentManager = hawkConsole.getAgentManager();
			agentManager.initialize();
			System.out.println(hawkDomain+service+network+daemon);
			hawkExec();
		} catch (Exception e) {
			throw e;
		}
		finally{
			if(agentManager != null)
			agentManager.shutdown();
		}		
	}
	
	private void hawkExec() throws Exception{
		MicroAgentID[] customAgentIDs = agentManager.getMicroAgentIDs("COM.TIBCO.hawk.microagent.Custom", 1);
		if(customAgentIDs == null || customAgentIDs.length == 0){
			System.out.println("Cannot connect to remote hawk agent!");
			throw new MicroAgentException(new Exception("Cannot connect to remote hawk agent."));
		}
		MicroAgentID customAgentID = customAgentIDs[0];
		if (customAgentID != null) {
			MethodInvocation methodInvocation = new MethodInvocation("execute",command);
			agentManager.invoke(customAgentID, methodInvocation);
		}

	}
		
	public void setCommand(String commandstr){
		command[0] = new DataElement("command", commandstr);
	}
	
//	/**
//	 * @param args
//	 * @throws Exception 
//	 */
//	public static void main(String[] args) throws Exception {
//		// TODO Auto-generated method stub
//		String hawkDomain = "hawkDomain.none.Nick-PC";
//		String service = "7474";
//		String network = "";
//		String daemon = "tcp:7474";
//		String commandstr = "cmd /c c:\\tibco\\be\\3.0\\bin\\be-engine --propFile c:\\temp\\myBEtest.tra c:\\temp\\myBEtest.ear";
//		RemoteEngineHawk reh = new RemoteEngineHawk(hawkDomain, service, network, daemon);
//		reh.setCommand(commandstr);
//		reh.start();
//	}

}
